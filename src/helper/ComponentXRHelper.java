package com.kingdee.eas.xr.helper;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.BasicNumberTextField;
import com.kingdee.bos.ctrl.swing.IKDTextComponent;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLabelContainer;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDRadioButton;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.eas.base.uiframe.client.NewMainFrame;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
public class ComponentXRHelper {
	
	private static Logger logger = Logger.getLogger(ComponentXRHelper.class);
	
	public static final BigDecimal Zero_BigDecimal = new BigDecimal(0);
	public static final String Zero_String = "0";
	public static final String Null_String = "";
	private static final int MAX_LENGTH_TXT = 80;
    public static final BigDecimal ZERO = new BigDecimal("0");
    public static final BigDecimal ONE = new BigDecimal("1");
    public static final BigDecimal _ONE = new BigDecimal("-1");
    public static final BigDecimal TEN = new BigDecimal("10");
    public static final BigDecimal _TEN = new BigDecimal("-10");
    public static final BigDecimal ONE_HUNDRED;
    public static final BigDecimal _ONE_HUNDRED = new BigDecimal("-100");
    public static final BigDecimal ONE_THOUSAND;
    public static final BigDecimal _ONE_THOUSAND = new BigDecimal("-1000");
    public static final BigDecimal TEN_THOUSAND = new BigDecimal("10000");
    public static final BigDecimal _TEN_THOUSAND = new BigDecimal("-10000");
    public static final BigDecimal ONE_HUNDRED_MILLION = new BigDecimal("100000000");
    public static final BigDecimal _ONE_HUNDRED_MILLION = new BigDecimal("-100000000");
    public static final BigDecimal MAX_VALUE;
    public static final BigDecimal MIN_VALUE;
    public static final BigDecimal MAX_TOTAL_VALUE;
    public static final BigDecimal MIN_TOTAL_VALUE;
    public static final BigDecimal MAX_TOTAL_VALUE2;
    public static final BigDecimal MIN_TOTAL_VALUE2;
    public static final Color KDTABLE_TOTAL_BG_COLOR = new Color(16185014);
    public static final Color KDTABLE_SUBTOTAL_BG_COLOR = new Color(16119270);
    public static final String KDTABLE_NUMBER_FTM = "%-.2n";
    public static final String KDTABLE_PERCENT_FTM = "%r{0.00}p";
    public static final String KDTABLE_DATE_FMT = "%{yyyy-MM-dd}t";
    public static final String ACTUAL_DIGIT_FMT = "#,##0.###########";
    private static String strDataFormat = "#,##0.00;-#,##0.00";
    public static final BigDecimal MAX_DECIMAL = new BigDecimal("999999999999999.9999");
    public static final BigDecimal MIN_DECIMAL = new BigDecimal("-999999999999999.9999");
    public static final DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
    public static final DateFormat FORMAT_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final DateFormat FORMAT_MONTH = new SimpleDateFormat("yyyy-MM");
    public static final String SEPARATOR = ",";
	static 
	{
	    ONE_HUNDRED = new BigDecimal("100");
	    ONE_THOUSAND = new BigDecimal("1000");
	    MAX_VALUE = GlUtils.maxBigDecimal.divide(ONE_HUNDRED, 4);
	    MIN_VALUE = GlUtils.minBigDecimal.divide(ONE_HUNDRED, 4);
	    MAX_TOTAL_VALUE = MAX_VALUE.multiply(ONE_HUNDRED);
	    MIN_TOTAL_VALUE = MIN_VALUE.multiply(ONE_HUNDRED);
	    MAX_TOTAL_VALUE2 = MAX_VALUE.multiply(ONE_THOUSAND);
	    MIN_TOTAL_VALUE2 = MIN_VALUE.multiply(ONE_THOUSAND);
	}
	public ComponentXRHelper() {
		super();
	}
	/**
	 * 构建一个金额的cellEditor
	 * */
	public static ICellEditor getKDTDefaultCellEditor() {
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		return numberEditor;
	}
	
	/**
	 * 构建一个整数的cellEditor
	 * */
	public static ICellEditor getIntegerCellEditor() {
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.INTEGER);
		// formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		return numberEditor;
	}
	/**
     * 描述：使容器所有控件不可编辑
     * 
     * @param source
     * @param enable
     * @see com.kingdee.eas.fi.fa.basedata.FaUtils#enableTextField(JComponent source, boolean enable)
     */
    public static void enableTextField(JComponent source, boolean enable) {
        if(source instanceof KDBizPromptBox) return;
        for (int i = 0; i < source.getComponentCount(); i++) {
            final Component c = source.getComponent(i);
            enableComponent(c, enable);
            if (c instanceof JComponent && ((JComponent) c).getComponentCount() > 0) {
                enableTextField((JComponent) c, enable);
            }
        }
    }

    /**
     * 描述：使容器所有控件不可编辑
     * 
     * @param source
     * @param enable
     * @see com.kingdee.eas.fi.fa.basedata.FaUtils#enableTextField(JComponent[] source, boolean enable)
     */
    public static void enableTextField(JComponent[] source, boolean enable) {
        for (int i = 0; i < source.length; i++) {
            if(source[i] instanceof KDBizPromptBox) continue;
            final Component c = source[i];
            enableComponent(c, enable);
            if (c instanceof JComponent && ((JComponent) c).getComponentCount() > 0) {
                enableTextField((JComponent) c, enable);
            }
        }
    }

    /**
     * 描述：指定控件设置编辑
     * 
     * @param c
     * @param enable
     * @see com.kingdee.eas.fi.fa.basedata.FaUtils#enableComponent(Component c, boolean enable)
     */
    public static void enableComponent(Component c, boolean enable) {
        if (c instanceof KDTable) {
            // ((KDTable) c).setEnabled(enable);
            ((KDTable) c).setEditable(enable); // editable 设置就可以了，enabled=true 会导致列大小更改的鼠标箭头失效
            // ((KDTable) c).getStyleAttributes().setLocked(!enable);
        }
        if (c instanceof KDDatePicker) {
            ((KDDatePicker) c).setAccessAuthority(enable ? CtrlCommonConstant.AUTHORITY_COMMON : CtrlCommonConstant.AUTHORITY_READ_ONLY);
            ((KDDatePicker) c).setEnabled(enable);
            ((KDDatePicker) c).setEditable(enable);
        } else if (c instanceof KDBizPromptBox) {
            ((KDBizPromptBox) c).setAccessAuthority(enable ? CtrlCommonConstant.AUTHORITY_COMMON : CtrlCommonConstant.AUTHORITY_READ_ONLY);
            
        } else if (c instanceof IKDTextComponent) {
            ((IKDTextComponent) c).setAccessAuthority(enable ? CtrlCommonConstant.AUTHORITY_COMMON : CtrlCommonConstant.AUTHORITY_READ_ONLY);
//            ((IKDTextComponent) c).setEnabled(enable);
//            ((IKDTextComponent) c).setEditable(enable);
        }
        if (c instanceof KDWorkButton) {
            ((KDWorkButton) c).setEnabled(enable);
        }
        if (c instanceof KDRadioButton) {
            ((KDRadioButton) c).setEnabled(enable);
        }
    }
    
    /**
     * 给文本框增加全选事件处理
     * 
     * @param source
     * @param selectAll 是否要求选中全部，true选中
     * @param removeZero 是否在编辑和显示时去0，true去0
     * @see com.kingdee.eas.fi.fa.manage.client.FaClientUtils#changeTextField(JComponent source, final boolean selectAll, final boolean removeZero)
     */
    public static void changeTextField(JComponent source, final boolean selectAll, final boolean removeZero) {
        for (int i = 0; i < source.getComponentCount(); i++) {
            final Component c = source.getComponent(i);
            if (c instanceof JComponent) {
                if (((JComponent) c).getComponentCount() > 0)
                    changeTextField((JComponent) c, selectAll, removeZero);
                else {
                    if (c instanceof KDFormattedTextField) {
                        ((KDFormattedTextField) c).setSelectAllOnFocus(selectAll);
                        ((KDFormattedTextField) c).setRemoveingZeroInDispaly(removeZero);
                        ((KDFormattedTextField) c).setRemoveingZeroInEdit(removeZero);
                    }
                    if (c instanceof KDTextField) {
                        ((KDTextField) c).addFocusListener(new FocusListener() {

                            public void focusGained(FocusEvent e) {
                                if (selectAll)
                                    ((KDTextField) c).selectAll();
                            }

                            public void focusLost(FocusEvent e) {
                            }
                        });
                    }
                }
            }
        }
    }
    
    /**
	 * 格式化文本控件 控制输入的大小和精度
	 * @param names
	 * @param precision
	 * @param kdtEntrys
	 */
	public static void setFormattedTextField(String[] names, int precision,KDTable kdtEntrys) {

		int size = names.length;
		for (int i = 0; i < size; i++) {
			String name = names[i];
			KDFormattedTextField textField = new KDFormattedTextField();
			textField.setName("kdtEntrys_" + name + "_TextField");
			textField.setVisible(true);
			textField.setEditable(true);
			textField.setHorizontalAlignment(2);
			textField.setDataType(1);
			if (precision == 0) {
				textField.setMinimumValue(new Integer(0));
				textField.setPrecision(0);
			} else {
				textField.setMinimumValue(new java.math.BigDecimal("0.00"));
				textField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
				textField.setPrecision(precision);
			}
			KDTDefaultCellEditor kdtEntrys_price_CellEditor = new KDTDefaultCellEditor(textField);
			kdtEntrys.getColumn(name).setEditor(kdtEntrys_price_CellEditor);
		}
	}
	
	/**
	 * 检查开始日期和结束日期
	 * @param ui调用UI界面
	 * @param pkstartDate开始日期
	 * @param pkendDate结束日期
	 */
	public static void checkDate(KDDatePicker pkstartDate,KDDatePicker pkendDate, String msg) {
		Date startDate = (Date) pkstartDate.getValue();
		Date endDate = (Date) pkendDate.getValue();

		if (startDate != null && endDate != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(startDate);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			startDate = c.getTime();

			c = Calendar.getInstance();
			c.setTime(endDate);
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			endDate = c.getTime();

			if (startDate.after(endDate)) {
				if(msg != null && !"".equals(msg)) {
					MsgBox.showError(msg);
				} else {
					MsgBox.showError("结束日期必须晚于开始日期!");
				}
				pkendDate.requestFocus();
				SysUtil.abort();
			}
		}
	}
	/**数据控件设置精度
     * 2009-10-09 by wp
     */
	public static void setComponentPrecision(Object c[], int scale)
    throws Exception
	{
	    int i = 0;
	    for(int size = c.length; i < size; i++)
	    {
	        Object kdc = c[i];
	        JComponent comp = null;
	        if(kdc instanceof KDLabelContainer)
	            comp = ((KDLabelContainer)kdc).getBoundEditor();
	        else
	            comp = (JComponent)kdc;
	        if((comp instanceof KDPanel) || (comp instanceof KDTabbedPane))
	            setComponentPrecision(((Object []) (((JComponent)kdc).getComponents())), scale);
	        if(comp instanceof KDTextField)
	            ((KDTextField)comp).setMaxLength(80);
	        if(comp instanceof BasicNumberTextField)
	        {
	            ((BasicNumberTextField)comp).setDataType(6);
	            ((BasicNumberTextField)comp).setPrecision(scale);
	            ((BasicNumberTextField)comp).setHorizontalAlignment(4);
	            ((BasicNumberTextField)comp).setRemoveingZeroInDispaly(false);
	            ((BasicNumberTextField)comp).setMaximumNumber(MAX_VALUE);
	            ((BasicNumberTextField)comp).setMinimumNumber(MIN_VALUE);
	        }
	        if(comp instanceof KDFormattedTextField)
	        {
	            ((KDFormattedTextField)comp).setDataType(1);
	            ((KDFormattedTextField)comp).setPrecision(scale);
	            ((KDFormattedTextField)comp).setHorizontalAlignment(4);
	            ((KDFormattedTextField)comp).setRemoveingZeroInDispaly(false);
	            ((KDFormattedTextField)comp).setMaximumValue(MAX_VALUE);
	            ((KDFormattedTextField)comp).setMinimumValue(MIN_VALUE);
	            ((KDFormattedTextField)comp).setHorizontalAlignment(4);
	        }
	        if(comp instanceof IColumn)
	        {
	            KDFormattedTextField formattedTextField = new KDFormattedTextField(1);
	            formattedTextField.setPrecision(scale);
	            formattedTextField.setRemoveingZeroInDispaly(false);
	            formattedTextField.setRemoveingZeroInEdit(false);
	            formattedTextField.setNegatived(false);
	            formattedTextField.setMaximumValue(MAX_VALUE);
	            formattedTextField.setMinimumValue(MIN_VALUE);
	            com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
	            ((IColumn)comp).getStyleAttributes().setNumberFormat(getDecimalFormat(scale));
	            ((IColumn)comp).getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.RIGHT);
	            ((IColumn)comp).setEditor(numberEditor);
	        }
	        if(comp instanceof ICell)
	        {
	            KDFormattedTextField formattedTextField = new KDFormattedTextField(1);
	            formattedTextField.setPrecision(scale);
	            formattedTextField.setRemoveingZeroInDispaly(false);
	            formattedTextField.setRemoveingZeroInEdit(false);
	            formattedTextField.setNegatived(false);
	            formattedTextField.setMaximumValue(MAX_VALUE);
	            formattedTextField.setMinimumValue(MIN_VALUE);
	            com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
	            ((ICell)comp).getStyleAttributes().setNumberFormat(getDecimalFormat(scale));
	            ((ICell)comp).getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.RIGHT);
	            ((ICell)comp).setEditor(numberEditor);
	        }
	    }
	
	}
	public static Frame getCurrMainFrame(Component component) {
		if (component == null)
			throw new IllegalArgumentException("Argument component is null");
		Component window = SwingUtilities.getRoot(component);
		if (window instanceof Applet)
			return JOptionPane.getFrameForComponent(window);
		if (window instanceof Frame)
			return (Frame) window;
		if (window instanceof Dialog)
			return getCurrMainFrame(((Component) (((Dialog) window).getOwner())));
		if (window instanceof NewMainFrame)
			return (NewMainFrame) window;
		if (window instanceof IUIWindow) {
			IUIWindow uiWindow = (IUIWindow) window;
			IUIObject ui = uiWindow.getUIObject();
			Map context = ui.getUIContext();
			Component c = (Component) context.get("OwnerWindow");
			if (c != null)
				return getCurrMainFrame(c);
		}
		throw new IllegalArgumentException("Component is not associated mainframe");
	}

	/**
	 * 移除控件上的key事件
	 * 
	 * @param component
	 */
	public static void removeAllKeyListeners(JComponent component) {
		KeyListener[] keyListeners = component.getKeyListeners();
		for (int i = 0; i < keyListeners.length; i++) {
			KeyListener listener = keyListeners[i];
			component.removeKeyListener(listener);
		}
	}

	/**
	 * 移除控件上的鼠标事件
	 * @param component
	 */
	public static void removeClickListeners(JComponent component) {
		MouseListener[] keyListeners = component.getMouseListeners();
		for (int i = 0; i < keyListeners.length; i++) {
			MouseListener listener = keyListeners[i];
			component.removeMouseListener(listener);
		}
	}

	/**
	 * 根据控件名称找窗体上对应的控件
	 * 
	 * @param name
	 * @param window
	 * @return
	 */
	public static JComponent getComponentByName(String name, JComponent window) {
		if (window == null) {
			logger.error("窗体不能为空！");
			return null;
		} else if (name == null) {
			logger.error("窗体不能为空！");
			return null;
		}
		List list = getAllComponents(window);
		for (int i = list.size(); --i >= 0;) {
			JComponent comp = (JComponent) list.get(i);
			if (name.equals(comp.getName())) {
				return comp;
			}
		}
		return null;
	}

	/**
	 * 获得控件对象上的所有子控件
	 * 
	 * @param component
	 * @return
	 */
	public static List getAllComponents(JComponent component) {
		ArrayList list = new ArrayList();
		return getAllComponents(component, list);
	}

	private static List getAllComponents(JComponent component, List list) {
		Component[] components = component.getComponents();
		if (components != null && components.length != 0) {
			for (int i = 0; i < components.length; i++) {
				if (components[i] instanceof JComponent) {
					list.add(components[i]);
					JComponent child = (JComponent) components[i];
					if (child != null && child.getComponents() != null && child.getComponents().length > 0) {
						getAllComponents((JComponent) components[i], list);
					}
				} else {
					logger.info("控件" + components[i] + "未能载入，因为不属于JComponent!");
				}
			}
		}
		return list;
	}
	public static String getDecimalFormat(int quantityScale)
    {
        StringBuffer sbff = new StringBuffer();
        StringBuffer sbffN = new StringBuffer();
        if(quantityScale == 0)
        {
            sbff.append("#,##0");
            sbffN.append("-#,##0");
        } else
        {
            sbff.append("#,##0.");
            sbffN.append("-#,##0.");
        }
        for(int i = 0; i < quantityScale; i++)
        {
            sbff.append("0");
            sbffN.append("0");
        }

        return sbff.append(";").append(sbffN).toString();
    }
	
}
