package com.kingdee.eas.fdc.basedata.util;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputListener;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.KDTextField;

/**
 * <p>
 * TODO 1. 线程处理时失去焦点时不显示， 3. 更多的参数控制，{支持线程(filter中可能涉及ui操作时不能启用线程)；} 5.
 * 设置为AutoCompletionField的控件不能绑定对象（需手动设置其值）
 * </p>
 * 
 * @author zhiqiao_yang
 * 
 */
public class REAutoCompletionField implements DocumentListener, MouseListener, ListSelectionListener, ActionListener, KeyListener {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.basedata.util.REAutoCompletionField");
	
	public static void initAutoCompletion(KDTextField textField, REAutoCompletionDataFilter filter) {
		initAutoCompletion(textField, filter, null);
	}

	/**
	 * 
	 * 
	 * @param textField
	 *            不可为null，需要使用自动完成功能的文本控件
	 * @param filter
	 *            不可为null，文本输入时得到的数据集合，将在线程中调用此接口，因此在filter方法中不可调用ui操作
	 * @param setter
	 *            选择下拉数据后调用的
	 * @param rendererFieldName
	 *            显示在下拉框中的字段
	 */
	public static void initAutoCompletion(final KDTextField textField, REAutoCompletionDataFilter filter, REAutoCompletionProperty properties) {
		new REAutoCompletionField(textField, filter, properties);
	}

	private ListPopup popup;
	private KDTextField textField;
	private REAutoCompletionDataFilter filter;
	private REAutoCompletionProperty properties;

	private REAutoCompletionField(KDTextField textField, REAutoCompletionDataFilter filter, REAutoCompletionProperty properties) {
		this.properties = properties;
		initDefaultProperties();
		this.textField = textField;
		this.textField.setSelectAllOnFocus(false);
		this.filter = filter;
		this.popup = new ListPopup();
		this.textField.getDocument().addDocumentListener(this);
		this.textField.addMouseListener(this);
		popup.addListSelectionListener(this);
		this.textField.addActionListener(this);
		this.textField.addKeyListener(this);
		this.textField.setFocusTraversalKeysEnabled(false);
	}

	private void initDefaultProperties() {
		if (properties == null) {
			this.properties = new REAutoCompletionProperty();
		}
		if (properties.getSetter() == null) {
			properties.setSetter(new REAutoCompletionDataSetter() {
				public void setFieldData(Object data) {
					try {
						if (properties.getRendererFieldName() != null) {
							textField.setText(BeanUtils.getProperty(data, properties.getRendererFieldName()));
						} else {
							textField.setText(data == null ? "" : data.toString());
						}
					} catch (Exception e) {
						// @AbortException
						logger.error(e.getMessage(), e);
						textField.setText(data == null ? "" : data.toString());
					}
					textField.setUserObject(data);
				}
			});
		}
	}

	private void textChanged() {
		if (filter != null) {
			final String text = textField.getText();
			if (!properties.isUseThread4Filter()) {
				ArrayList array = filter.filter(text);
				changeList(text, array);
			} else {
				new Thread(new Runnable() {
					public void run() {
						final ArrayList array = filter.filter(text);
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								changeList(text, array);
							}
						});
					}
				}).start();
			}
		}
	}

	private void changeList(String text, ArrayList array) {
		if (text != null && text.equals(textField.getText())) {
			if (!popup.isVisible() && array != null && !array.isEmpty()) {
				showPopup();
				textField.requestFocus();
			}
			if (array == null) {
				array = new ArrayList();
			}
			if (array.size() == 0) {
				if (popup.isVisible()) {
					popup.setVisible(false);
				}
			} else {
				if (!popup.isVisible()) {
					showPopup();
				}
			}
			int max = properties.getMaxShowLine() > array.size() ? array.size() : properties.getMaxShowLine();
			popup.setPopupSize(textField.getWidth(), max * 20 + 4);
			if (isListChange(array) && array.size() != 0) {
				popup.setList(text, array);
			}
		}
	}

	private boolean isListChange(ArrayList array) {
		if (array.size() != popup.getItemCount()) {
			return true;
		}
		for (int i = 0; i < array.size(); i++) {
			if (!array.get(i).equals(popup.getItem(i))) {
				return true;
			}
		}
		return false;
	}

	private void showPopup() {
		popup.setPopupSize(textField.getWidth(), properties.getMaxShowLine() * 20 + 4);
		popup.show(textField, 0, textField.getHeight() - 1);
	}

	public void insertUpdate(DocumentEvent e) {
		textChanged();
	}

	public void removeUpdate(DocumentEvent e) {
		textChanged();
	}

	public void changedUpdate(DocumentEvent e) {
		textChanged();
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() > 1 && !popup.isVisible())
			textChanged();
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void setTextField() {
		if (!popup.list.isSelectionEmpty()) {
			Object selectedValue = popup.list.getModel().getElementAt(popup.list.getSelectedIndex());
			try {
				this.textField.getDocument().removeDocumentListener(this);
				properties.getSetter().setFieldData(selectedValue);
			} finally {
				this.textField.getDocument().addDocumentListener(this);
			}
			if (textField.getText() != null && textField.getText().length() > 0) {
				textField.setCaretPosition(textField.getText().length());
			}
		}
	}

	public void valueChanged(ListSelectionEvent e) {
		setTextField();
		popup.setVisible(false);
	}

	public void actionPerformed(ActionEvent e) {
		if (popup.isVisible()) {
			setTextField();
			popup.setVisible(false);
		}
		// textField.selectAll();
		textField.requestFocus();
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_TAB) {
			popup.setVisible(false);
			javax.swing.FocusManager.getCurrentManager().focusNextComponent();
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (popup.isVisible()) {
				if (!popup.isSelected())
					popup.setSelectedIndex(0);
				else
					popup.setSelectedIndex(popup.getSelectedIndex() + 1);
			}
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (popup.isVisible()) {
				if (!popup.isSelected())
					popup.setLastOneSelected();
				else
					popup.setSelectedIndex(popup.getSelectedIndex() - 1);
			}
		} else if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
			if (popup.isVisible()) {
				if (!popup.isSelected())
					popup.setSelectedIndex(0);
				else
					popup.setSelectedIndex(popup.getSelectedIndex() + 5);
			}
		} else if (e.getKeyCode() == KeyEvent.VK_PAGE_UP) {
			if (popup.isVisible()) {
				if (!popup.isSelected())
					popup.setLastOneSelected();
				else
					popup.setSelectedIndex(popup.getSelectedIndex() - 5);
			}
		}
	}

	public void keyReleased(KeyEvent e) {
	}

	public class ListPopup extends JPopupMenu implements MouseInputListener {

		private JList list;
		private JScrollPane pane;
		private ArrayList listeners = new ArrayList();
		private String indexText;

		public ListPopup() {
			setLayout(new BorderLayout());
			list = new JList();
			if (properties.getRendererFieldName() != null) {
				list.setCellRenderer(new DefaultListCellRenderer() {
					public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
						super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
						try {
							if (properties.getRendererFieldName() != null) {
								setText(BeanUtils.getProperty(value, properties.getRendererFieldName()));
							} else {
								setText(value == null ? "" : value.toString());
							}
						} catch (Exception e) {
							// @AbortException
							logger.error(e.getMessage(), e);
							setText(value == null ? "" : value.toString());
						}
						return this;
					}
				});
			}
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list.addMouseListener(this);
			list.addMouseMotionListener(this);
			list.setModel(new DefaultListModel());
			pane = new JScrollPane(list);
			pane.setBorder(null);
			add(pane, BorderLayout.CENTER);
		}

		public void addListSelectionListener(ListSelectionListener l) {
			if (!listeners.contains(l))
				listeners.add(l);
		}

		public void setSelectedIndex(int index) {
			if (index >= list.getModel().getSize())
				index = 0;
			if (index < 0)
				index = list.getModel().getSize() - 1;
			list.ensureIndexIsVisible(index);
			list.setSelectedIndex(index);
		}

		public Object getSelectedValue() {
			return list.getSelectedValue();
		}

		public int getSelectedIndex() {
			return list.getSelectedIndex();
		}

		public boolean isSelected() {
			return list.getSelectedIndex() != -1;
		}

		public void setLastOneSelected() {
			int count = list.getModel().getSize();
			if (count > 0) {
				list.ensureIndexIsVisible(count - 1);
				list.setSelectedIndex(count - 1);
			}
		}

		public void removeListSelectionListener(ListSelectionListener l) {
			if (listeners.contains(l))
				listeners.remove(l);
		}

		private void fireValueChanged(ListSelectionEvent e) {
			for (int i = 0; i < listeners.size(); ++i) {
				ListSelectionListener l = (ListSelectionListener) listeners.get(i);
				l.valueChanged(e);
			}
		}

		public int getItemCount() {
			DefaultListModel model = (DefaultListModel) list.getModel();
			return model.getSize();
		}

		public Object getItem(int index) {
			DefaultListModel model = (DefaultListModel) list.getModel();
			return model.get(index);
		}

		public void addItem(Object o) {
			DefaultListModel model = (DefaultListModel) list.getModel();
			model.addElement(o);
			list.repaint();
		}

		public void removeItem(Object o) {
			DefaultListModel model = (DefaultListModel) list.getModel();
			model.removeElement(o);
			list.repaint();
		}

		public void setList(String indexText, List datas) {
			this.indexText = indexText;
			DefaultListModel model = new DefaultListModel();
			for (int i = 0; i < datas.size(); ++i) {
				model.addElement(datas.get(i));
			}
			list.setModel(model);
			list.repaint();
		}

		public String getIndexText() {
			return indexText;
		}

		public void mouseClicked(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
			if (list.getSelectedIndex() != -1)
				fireValueChanged(new ListSelectionEvent(list, list.getSelectedIndex(), list.getSelectedIndex(), true));
		}

		public void mouseReleased(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mouseMoved(MouseEvent anEvent) {
			if (anEvent.getSource() == list) {
				Point location = anEvent.getPoint();
				Rectangle r = new Rectangle();
				list.computeVisibleRect(r);
				if (r.contains(location)) {
					updateListBoxSelectionForEvent(anEvent, false);
				}
			}
		}

		protected void updateListBoxSelectionForEvent(MouseEvent anEvent, boolean shouldScroll) {

			Point location = anEvent.getPoint();
			if (list == null) {
				return;
			}
			int index = list.locationToIndex(location);
			if (index == -1) {
				if (location.y < 0) {
					index = 0;
				} else {
					index = list.getModel().getSize() - 1;
				}
			}
			if (list.getSelectedIndex() != index) {
				list.setSelectedIndex(index);
				if (shouldScroll) {
					list.ensureIndexIsVisible(index);
				}
			}
		}

		public void mouseDragged(MouseEvent e) {
		}

	}
}
