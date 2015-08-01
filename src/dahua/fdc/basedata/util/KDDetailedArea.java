package com.kingdee.eas.fdc.basedata.util;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import com.kingdee.bos.ctrl.swing.DefaultTextField;
import com.kingdee.bos.ctrl.swing.IKDComponent;
import com.kingdee.bos.ctrl.swing.IKDEditor;
import com.kingdee.bos.ctrl.swing.IKDTextComponent;
import com.kingdee.bos.ctrl.swing.ITextLengthLimit;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDPromptSelector;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.KDLayout.Constraints;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.util.CtrlSwingUtilities;
import com.kingdee.bos.ctrl.swing.util.TextComponentUtilities;

/**
 * 明细输入框控件
 * 
 * @version 1.0
 * @author emanon (管骥宇)
 * 
 * 
 *         1、目前由于KDTable中，单元格值包含‘换行符’时，<br>
 *         会自动setWrapText(true)， 导致不能正确显示省略号<br>
 *         请在table_editStopped中使用KDDetailedAreaUtil.setWrapFalse(cell)重新设置
 * 
 *         2、由于框架设定，在单据查看状态时，KDTable选中单元格不会激活editor，<br>
 *         所以明细面板不能弹出，无法查看单元格详细值<br>
 *         请在table_tableClicked中使用KDDetailedAreaUtil.showDialog(owner,table)方法<br>
 *         使鼠标在选中单元格以后，使用KDDetailedAreaDialog显示单元格的值
 * 
 */
public class KDDetailedArea extends JComponent implements IKDComponent,
		IKDEditor, IKDTextComponent, ITextLengthLimit {

	private static final long serialVersionUID = -3058672091928600665L;

	protected Object userObject = null;
	protected Object value = null;
	protected boolean isVisble = true;
	protected boolean isEditable = true;
	protected int maxLength = 255;
	protected int minLength = 0;

	// 推荐控件大小
	private static final int PREFERRED_WIDTH = 170;
	private static final int PREFERRED_HEIGHT = 19;

	// 按钮大小
	private int BUTTON_WIDTH = 30;
	private int BUTTON_HEIGHT = 19;

	// 弹出的明细面板大小
	private int CUS_DIALOG_WIDTH = 200;
	private int CUS_DIALOG_HEIGHT = 140;

	protected JTextComponent txtGeneral = null;
	protected JButton btnShow = null;
	protected KDPromptSelector selector;

	public KDDetailedArea() {
		super();
		createUI();
		createControl();
	}

	public KDDetailedArea(int dialog_Width, int dialog_Height) {
		super();
		this.CUS_DIALOG_WIDTH = dialog_Width;
		this.CUS_DIALOG_HEIGHT = dialog_Height;
		createUI();
		createControl();
	}

	public int getCUS_DIALOG_WIDTH() {
		return CUS_DIALOG_WIDTH;
	}

	public void setCUS_DIALOG_WIDTH(int cUSDIALOGWIDTH) {
		CUS_DIALOG_WIDTH = cUSDIALOGWIDTH;
	}

	public int getCUS_DIALOG_HEIGHT() {
		return CUS_DIALOG_HEIGHT;
	}

	public void setCUS_DIALOG_HEIGHT(int cUSDIALOGHEIGHT) {
		CUS_DIALOG_HEIGHT = cUSDIALOGHEIGHT;
	}

	/**
	 * 创建控件外观
	 */
	protected void createUI() {
		this.setLayout(new KDLayout());
		this.putClientProperty("OriginalBounds", new Rectangle(0, 0,
				PREFERRED_WIDTH, PREFERRED_HEIGHT));
		this.setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));

		int x = 0;
		int y = 0;
		int width = PREFERRED_WIDTH - BUTTON_WIDTH - 1;
		int height = PREFERRED_HEIGHT;
		KDLayout.Constraints cons = new KDLayout.Constraints();
		cons.originalBounds = new Rectangle(x, y, width, height);
		cons.anchor = Constraints.ANCHOR_TOP | Constraints.ANCHOR_LEFT
				| Constraints.ANCHOR_BOTTOM | Constraints.ANCHOR_RIGHT;
		txtGeneral = createEditor();
		add(txtGeneral);
		txtGeneral.putClientProperty("KDLayoutConstraints", cons);

		x = width + 1;
		width = BUTTON_WIDTH;
		height = BUTTON_HEIGHT;
		cons = new KDLayout.Constraints();
		cons.originalBounds = new Rectangle(x, y, width, height);
		cons.anchor = Constraints.ANCHOR_TOP | Constraints.ANCHOR_BOTTOM
				| Constraints.ANCHOR_RIGHT;
		btnShow = createButton();
		add(btnShow);
		btnShow.putClientProperty("KDLayoutConstraints", cons);

		btnShow.setToolTipText("Alt+Enter 或 Alt+D 打开编辑面板");
	}

	/**
	 * 添加控件监听
	 */
	protected void createControl() {
		btnShow.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				btnShow_actionPerformed(e);
			}
		});
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.isAltDown()
						&& (e.getKeyCode() == KeyEvent.VK_ENTER || e
								.getKeyCode() == KeyEvent.VK_D)) {
					btnShow_actionPerformed(null);
				}
			}
		});
		txtGeneral.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.isAltDown()
						&& (e.getKeyCode() == KeyEvent.VK_ENTER || e
								.getKeyCode() == KeyEvent.VK_D)) {
					btnShow_actionPerformed(null);
				}
			}
		});
		btnShow.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.isAltDown()
						&& (e.getKeyCode() == KeyEvent.VK_ENTER || e
								.getKeyCode() == KeyEvent.VK_D)) {
					btnShow_actionPerformed(null);
				}
			}
		});
	}

	/**
	 * 显示明细面板
	 * 
	 * @param e
	 */
	protected void btnShow_actionPerformed(ActionEvent e) {
		showDialog();
		String data = (String) selector.getData();
		setText(data);
		txtGeneral.setText(data);
	}

	public Object getUserObject() {
		return userObject;
	}

	public void setUserObject(Object userObject) {
		this.userObject = userObject;
	}

	public void addDataChangeListener(DataChangeListener listener) {
		listenerList.add(DataChangeListener.class, listener);
	}

	public void removeDataChangeListener(DataChangeListener listener) {
		listenerList.remove(DataChangeListener.class, listener);
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
		if (value == null) {
			txtGeneral.setText(null);
		} else if (value instanceof String) {
			txtGeneral.setText((String) value);
		} else {
			txtGeneral.setText(value.toString());
		}
	}

	public String getText() {
		if (value instanceof String) {
			return (String) value;
		} else {
			return value.toString();
		}
	}

	public void setText(String text) {
		setValue(text);
	}

	public boolean isDisplay() {
		return isVisble;
	}

	public void setDisplay(boolean isDisplay) {
		this.isVisble = isDisplay;
	}

	public int getAccessAuthority() {
		return TextComponentUtilities.getAccessAuthority(this);
	}

	public void setAccessAuthority(int authority) {
		if (getAccessAuthority() != authority)
			TextComponentUtilities.setAccessAuthority(this, authority);
	}

	/**
	 * 创建明细面板，位置在当前按钮右边
	 * 
	 * @param param
	 * @return
	 */
	protected KDPromptSelector createSelector(Object param) {
		Window parent = SwingUtilities.getWindowAncestor(this);
		if (parent instanceof Dialog) {
			selector = new KDDetailedAreaDialog((Dialog) parent,
					CUS_DIALOG_WIDTH, CUS_DIALOG_HEIGHT, true);
		} else if (parent instanceof Frame) {
			selector = new KDDetailedAreaDialog((Frame) parent,
					CUS_DIALOG_WIDTH, CUS_DIALOG_HEIGHT, true);
		} else {
			selector = new KDDetailedAreaDialog(true);
		}
		((KDDetailedAreaDialog) selector).setData((String) value);
		Point loc = btnShow.getLocationOnScreen();
		((KDDetailedAreaDialog) selector)
				.setPRENTE_X((int) (loc.getX() + BUTTON_WIDTH));
		((KDDetailedAreaDialog) selector).setPRENTE_Y((int) (loc.getY()));
		((KDDetailedAreaDialog) selector).setMinLength(minLength);
		((KDDetailedAreaDialog) selector).setMaxLength(maxLength);
		return selector;
	}

	public void setDataBySelector() {
		value = selector.getData();
	}

	public boolean isEditable() {
		return isEditable;
	}

	public void setEditable(boolean editable) {
		this.isEditable = editable;
		if (editable) {

		}
	}

	public boolean isRequired() {
		return false;
	}

	public void setRequired(boolean required) {
		// TODO Auto-generated method stub

	}

	public int getMaxLength() {
		return this.maxLength;
	}

	public int getMinLength() {
		return this.minLength;
	}

	public void setMaxLength(int len) {
		this.maxLength = len;
	}

	public void setMinLength(int len) {
		this.minLength = len;
	}

	/**
	 * 创建文本框
	 * 
	 * @return
	 */
	protected DefaultEditor createEditor() {
		DefaultEditor tf = new DefaultEditor();
		// tf.setSize(50, 20);
		tf.setEditable(false);
		return tf;
	}

	/**
	 * 创建button
	 * 
	 * @return
	 */
	protected JButton createButton() {
		JButton btn = new JButton();
		btn.setVisible(true);
		btn.setMargin(new Insets(0, 0, 0, 0));
		btn.setText("详细");
		// btn.setBounds(50, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
		return btn;
	}

	public void showDialog() {
		createSelector(null);
		selector.show();
	}

	// 复制自F7控件
	public static class DefaultEditor extends DefaultTextField {

		/**
		 * Comment for <code>serialVersionUID</code>
		 */
		private static final long serialVersionUID = -6592404262363541282L;

		private String customTooltip;

		public DefaultEditor() {
			super();
			// 去除框架的按回车键跳转，试验性解决潘柱军的问题 -pianyao,2008.6.24
			CtrlSwingUtilities.removeManagingFocusForwardTraversalKeys(this,
					KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
		}

		/**
		 * @param text
		 */
		public DefaultEditor(String text) {
			super(text);
			// 去除框架的按回车键跳转，试验性解决潘柱军的问题 -pianyao,2008.6.24
			CtrlSwingUtilities.removeManagingFocusForwardTraversalKeys(this,
					KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
		}

		/**
		 * @param columns
		 */
		public DefaultEditor(int columns) {
			super(columns);
			// 去除框架的按回车键跳转，试验性解决潘柱军的问题 -pianyao,2008.6.24
			CtrlSwingUtilities.removeManagingFocusForwardTraversalKeys(this,
					KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
		}

		/**
		 * @param text
		 * @param columns
		 */
		public DefaultEditor(String text, int columns) {
			super(text, columns);
			// 去除框架的按回车键跳转，试验性解决潘柱军的问题 -pianyao,2008.6.24
			CtrlSwingUtilities.removeManagingFocusForwardTraversalKeys(this,
					KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
		}

		/**
		 * @param doc
		 * @param text
		 * @param columns
		 */
		public DefaultEditor(Document doc, String text, int columns) {
			super(doc, text, columns);
			// 去除框架的按回车键跳转，试验性解决潘柱军的问题 -pianyao,2008.6.24
			CtrlSwingUtilities.removeManagingFocusForwardTraversalKeys(this,
					KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
		}

		public void setToolTipText(String text) {
			super.setToolTipText(text);
			if (!StringUtils.isEmpty(text) && !"F7".equals(text))
				customTooltip = text;
		}

		public String getToolTipText() {
			if (customTooltip != null)
				return customTooltip;

			if (isEnabled()) {
				// ----editor,label宽度不够显示全部文字时，在tooltip上显示。add by pianyao,
				// 2009.3.0----//
				String text = getText();
				double tw = getFontMetrics(getFont()).stringWidth(text);
				double ew = getVisibleRect().width;
				if (tw > ew) {
					return text;
				}
			}

			return super.getToolTipText();
		}

		public String getToolTipText(MouseEvent event) {
			return getToolTipText();
		}
	}
}
