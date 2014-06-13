package com.kingdee.eas.port.equipment.uitl;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.KeyStroke;

import com.kingdee.bos.ctrl.swing.KDButton;
import com.kingdee.bos.ctrl.swing.KDDialog;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDPromptSelector;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ctrl.swing.KDTextArea;
import com.kingdee.bos.ctrl.swing.KDLayout.Constraints;
import com.kingdee.bos.ctrl.swing.util.CtrlSwingUtilities;

/**
 * @version 1.0
 * @author emanon (管骥宇)
 * 
 *         相关控件 {@link KDDetailedArea}<br>
 *         KDDetailedArea的展示UI，也可用于单独展示单元格内容，<br>
 *         在table_tableClicked中使用KDDetailedAreaUtil.showDialog(owner,table)
 */
public class KDDetailedAreaDialog extends KDDialog implements KDPromptSelector,
		ActionListener {

	private static final long serialVersionUID = 4797111933971286876L;

	// 确定或取消
	private boolean canceled = true;

	// 返回的数据
	private Object data;

	// 文本长度
	protected int maxLength = 255;
	protected int minLength = 0;

	// 父UI
	private JComponent parent = null;

	// 父UI大小
	private static final int PREFERRED_WIDTH = 192;
	private static final int PREFERRED_HEIGHT = 108;

	// 父UI位置，好计算弹出窗口位置
	private int PRENTE_X = 0;
	private int PRENTE_Y = 0;

	// 是否可编辑
	private boolean editable = true;

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	// 弹出框大小，可调整
	private int DIALOG_WIDTH = 200;
	private int DIALOG_HEIGHT = 140;

	public int getDIALOG_WIDTH() {
		return DIALOG_WIDTH;
	}

	public void setDIALOG_WIDTH(int dIALOGWIDTH) {
		DIALOG_WIDTH = dIALOGWIDTH;
	}

	public int getDIALOG_HEIGHT() {
		return DIALOG_HEIGHT;
	}

	public void setDIALOG_HEIGHT(int dIALOGHEIGHT) {
		DIALOG_HEIGHT = dIALOGHEIGHT;
	}

	// 控件间隔
	private static final int SPAN = 5;

	// 按钮大小
	private static final int BUTTON_WIDTH = 50;
	private static final int BUTTON_HEIGHT = 20;

	// 弹出框面板
	private KDScrollPane sp = null;
	private KDPanel allContentPanel = null;
	private KDPanel contentPanel = null;

	// 界面控件
	private KDTextArea txtArea = null;
	private KDButton btnOK = null;
	private KDButton btnCancel = null;

	public KDDetailedAreaDialog(boolean b) {
		super((Frame) null, b);
		setUndecorated(true);
		dialogInit();
	}

	public KDDetailedAreaDialog(Dialog owner, int dialog_w, int dialog_h,
			boolean b) {
		super(owner, b);
		this.DIALOG_WIDTH = dialog_w;
		this.DIALOG_HEIGHT = dialog_h;
		setUndecorated(true);
		dialogInit();
	}

	public KDDetailedAreaDialog(Frame owner, int dialog_w, int dialog_h,
			boolean b) {
		super(owner, b);
		this.DIALOG_WIDTH = dialog_w;
		this.DIALOG_HEIGHT = dialog_h;
		setUndecorated(true);
		dialogInit();
	}

	public KDDetailedAreaDialog(JComponent view, int dialog_w, int dialog_h) {
		this.parent = view;
		this.DIALOG_WIDTH = dialog_w;
		this.DIALOG_HEIGHT = dialog_h;
		setUndecorated(true);
		dialogInit();
	}

	public int getPRENTE_X() {
		return PRENTE_X;
	}

	public void setPRENTE_X(int pRENTEX) {
		PRENTE_X = pRENTEX;
	}

	public int getPRENTE_Y() {
		return PRENTE_Y;
	}

	public void setPRENTE_Y(int pRENTEY) {
		PRENTE_Y = pRENTEY;
	}

	protected void dialogInit() {
		super.dialogInit();
		initDialog();
		initControls();
	}

	/**
	 * 初始化面板
	 */
	private void initDialog() {
		setDefaultCloseOperation(KDDialog.DISPOSE_ON_CLOSE);
		sp = new KDScrollPane();
		// sp.setBorder(null);
		add(initContentPanel());
		// setContentPane(sp);
	}

	/**
	 * 设置面板在屏幕中位置
	 */
	private void initBounds() {
		setResizable(false);
		int X = PRENTE_X;
		int Y = PRENTE_Y;
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();

		int cur_width = screensize.width;
		int cur_height = screensize.height;

		if (X < 0) {
			X = 0;
		} else if (X + DIALOG_WIDTH + SPAN > cur_width) {
			X = cur_width - DIALOG_WIDTH - SPAN;
		}
		if (Y < 0) {
			Y = 0;
		} else if (Y + DIALOG_HEIGHT + SPAN > cur_height) {
			Y = cur_height - DIALOG_HEIGHT - SPAN;
		}

		setBounds(X, Y, DIALOG_WIDTH, DIALOG_HEIGHT);
	}

	/**
	 * 设置监听及快捷键
	 */
	private void initControls() {

		addWindowFocusListener(new WindowFocusListener() {

			public void windowGainedFocus(WindowEvent e) {
			}

			public void windowLostFocus(WindowEvent e) {
				btnCancel_actionPerformed(null);
			}
		});

		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnOK_actionPerformed(e);
			}
		});
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCancel_actionPerformed(e);
			}
		});
		txtArea.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.isAltDown()
						&& (e.getKeyCode() == KeyEvent.VK_ENTER || e
								.getKeyCode() == KeyEvent.VK_D)) {
					btnOK_actionPerformed(null);
				}
			}
		});
	}

	/**
	 * 设置面板控件位置
	 * 
	 * @return
	 */
	private Component initContentPanel() {
		allContentPanel = new KDPanel();
		allContentPanel.setLayout(new BorderLayout());
		allContentPanel.putClientProperty("OriginalBounds", new Rectangle(0, 0,
				DIALOG_WIDTH, DIALOG_HEIGHT));
		allContentPanel.setPreferredSize(new Dimension(PREFERRED_WIDTH,
				PREFERRED_HEIGHT));

		txtArea = new KDTextArea();
		KDPanel btnPanel = new KDPanel();
		btnPanel.setLayout(new KDLayout());
		btnPanel.putClientProperty("OriginalBounds", new Rectangle(0, 0,
				DIALOG_WIDTH, BUTTON_HEIGHT + 2));
		btnPanel
				.setPreferredSize(new Dimension(DIALOG_WIDTH, BUTTON_HEIGHT + 2));
		btnOK = new KDButton();
		btnCancel = new KDButton();
		btnOK.setLimitedSize(false);
		btnCancel.setLimitedSize(false);
		btnOK.setCustomInsets(new Insets(0, 0, 0, 0));
		btnCancel.setCustomInsets(new Insets(0, 0, 0, 0));
		btnOK.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		btnCancel.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		// TODO 此处需支持多语言
		btnOK.setText("确定");
		btnCancel.setText("取消");
		btnOK.setToolTipText("Alt+Enter 或 Alt+D 确定");
		btnCancel.setToolTipText("Esc 关闭编辑面板");

		btnPanel.add(btnOK);
		btnPanel.add(btnCancel);
		KDScrollPane spTxt = new KDScrollPane(txtArea);
		allContentPanel.add(spTxt, BorderLayout.CENTER);
		allContentPanel.add(btnPanel, BorderLayout.SOUTH);

		int x;
		int y;
		int width;
		int height;

		// 取消按钮位置
		x = DIALOG_WIDTH - SPAN - BUTTON_WIDTH;
		y = 0;
		width = BUTTON_WIDTH;
		height = BUTTON_HEIGHT;
		KDLayout.Constraints cons = new KDLayout.Constraints();
		cons.originalBounds = new Rectangle(x, y, width, height);
		cons.anchor = Constraints.ANCHOR_BOTTOM | Constraints.ANCHOR_RIGHT;
		// btnCancel.setBounds(new Rectangle(x, y, width, height));
		btnCancel.putClientProperty("KDLayoutConstraints", cons);
		// 确定按钮位置
		x = x - SPAN - BUTTON_WIDTH;
		cons = new KDLayout.Constraints();
		cons.originalBounds = new Rectangle(x, y, width, height);
		cons.anchor = Constraints.ANCHOR_BOTTOM | Constraints.ANCHOR_RIGHT;
		// btnOK.setBounds(new Rectangle(x, y, width, height));
		btnOK.putClientProperty("KDLayoutConstraints", cons);

		return allContentPanel;
	}

	/**
	 * 确定并关闭
	 * 
	 * @param e
	 */
	protected void btnOK_actionPerformed(ActionEvent e) {
		data = txtArea.getText();
		canceled = false;
		dispose();
	}

	/**
	 * 取消并关闭
	 * 
	 * @param e
	 */
	protected void btnCancel_actionPerformed(ActionEvent e) {
		// data = null;
		canceled = true;
		dispose();
	}

	public Object getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
		txtArea.setText(data);
	}

	public boolean isCanceled() {
		return canceled;
	}

	/**
	 * 展示
	 */
	public void show() {
		initBounds();
		txtArea.setMinLength(minLength);
		txtArea.setMaxLength(maxLength);
		txtArea.setEditable(editable);
		btnOK.setEnabled(editable);
		super.show();
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
