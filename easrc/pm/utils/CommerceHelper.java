package com.kingdee.eas.port.pm.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.io.kds.KDSBookToBook;
import com.kingdee.bos.ctrl.excel.model.struct.Sheet;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ctrl.kdf.kds.KDSBook;
import com.kingdee.bos.ctrl.kdf.read.POIXlsReader;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDComboColor;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDMenu;
import com.kingdee.bos.ctrl.swing.KDPasswordField;
import com.kingdee.bos.ctrl.swing.KDTimePicker;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.PropertyCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.uiframe.client.UIModelDialog;
import com.kingdee.eas.basedata.assistant.CountryCollection;
import com.kingdee.eas.basedata.assistant.CountryFactory;
import com.kingdee.eas.basedata.assistant.CountryInfo;
import com.kingdee.eas.basedata.assistant.IndustryCollection;
import com.kingdee.eas.basedata.assistant.IndustryFactory;
import com.kingdee.eas.basedata.assistant.IndustryInfo;
import com.kingdee.eas.basedata.assistant.ProvinceCollection;
import com.kingdee.eas.basedata.assistant.ProvinceFactory;
import com.kingdee.eas.basedata.assistant.ProvinceInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fm.common.client.AbstractHidedMenuItem;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseInfo;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.framework.client.mutex.DataObjectMutex;
import com.kingdee.eas.framework.client.mutex.IDataObjectMutex;
import com.kingdee.eas.hr.base.WorkAreaCollection;
import com.kingdee.eas.hr.base.WorkAreaFactory;
import com.kingdee.eas.hr.base.WorkAreaInfo;
import com.kingdee.eas.scm.crm.customer.SexEnum;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.sun.xml.messaging.saaj.util.ByteInputStream;
import com.sun.xml.messaging.saaj.util.ByteOutputStream;

public class CommerceHelper {
	
	
	private static final Logger logger = CoreUIObject.getLogger(CommerceHelper.class);
	
	public static Color BK_COLOR_MUST = new Color(0xFCFBDF);
	
	
	

	/**
	 * 获得当前营销人员能够看到的商机的view  (针对商机的f7控件)
	 * @return EntityViewInfo
	 */
	public static EntityViewInfo getPermitCommerceChanceView(){	
		UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();
		
		EntityViewInfo viewInfo = new EntityViewInfo();		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("saleMan.id", currentUserInfo.getId().toString()));
		
		viewInfo.setFilter(filter);
		return viewInfo;
	}
	
	
	
	
	/**
	 * 以ModelDialog模式u打开指定的ui查看界面;  如果id为空则打开其新增界面
	 * @param owner
	 * @param uiClassName
	 * @param id
	 */
	public static void openTheUIWindow(Object owner,String uiClassName,String id) {		
		UIContext uiContext = new UIContext(owner); 		
		
		String opera = OprtState.ADDNEW;
		if(id!=null && !id.trim().equals(""))  {
			uiContext.put("ActionView", "OnlyView");
			uiContext.put(UIContext.ID, id);
			opera = OprtState.VIEW;
		}
		try {
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(uiClassName, uiContext, null, opera);
			uiWindow.show();
		} catch (UIException e) {
			e.printStackTrace();
			SysUtil.abort();
		}
	}
	
	
	
	
	/**
	 * 判断f7控件的值是否改变 (当f7的值被重新选择时)  若id一直就认为没变
	 */
	public static boolean isF7DataChanged(DataChangeEvent e){
		CoreBaseInfo oldValue = (CoreBaseInfo)e.getOldValue();
		CoreBaseInfo newValue = (CoreBaseInfo)e.getNewValue();
		if(oldValue==null && newValue!=null)
			return true;
		else if(oldValue!=null && newValue==null)
			return true;
		else if(!oldValue.getId().equals(newValue.getId()))
			return true;
		
		return false;
	}
	
	
	
	
	
	
	public static ICellEditor getKDBizPromptBoxEditor(String queryInfoStr,EntityViewInfo viewInfo){
		return getKDBizPromptBoxEditor(queryInfoStr,viewInfo,null);
	}
	
	/**
	 * 绑定的控件为KDBizPromptBox的cellEditor
	 * @param queryInfoStr
	 * @param viewInfo
	 * @return
	 */
	public static ICellEditor getKDBizPromptBoxEditor(String queryInfoStr,EntityViewInfo viewInfo,String defaultF7UIName){
		KDBizPromptBox f7Prompt = new KDBizPromptBox();
		f7Prompt.setQueryInfo(queryInfoStr);
		f7Prompt.setEditable(true);
		f7Prompt.setDisplayFormat("$name$");
		f7Prompt.setEditFormat("$name$");
		f7Prompt.setCommitFormat("$number$");
		if(viewInfo!=null)
			f7Prompt.setEntityViewInfo(viewInfo);
		if(defaultF7UIName!=null)
			f7Prompt.setDefaultF7UIName(defaultF7UIName);
		ICellEditor f7editor = new KDTDefaultCellEditor(f7Prompt);
		return f7editor;
	}
	
	/**
	 * 绑定的控件为KDComboBox的cellEditor
	 * @param enumList   枚举的list 例如：CertifacateNameEnum.getEnumList()
	 * @return
	 */
	public static ICellEditor getKDComboBoxEditor(List enumList){
		KDComboBox comboField = new KDComboBox();
		if(enumList!=null)
			for (int i = 0; i < enumList.size(); i++) {
				comboField.addItem(enumList.get(i));
			}
		//comboField.addItems(EnumUtils.getEnumList("**").toArray());
		ICellEditor comboEditor = new KDTDefaultCellEditor(comboField);
		return comboEditor;		
	}
	
	/**
	 * 绑定的控件为KDDatePicker的cellEditor
	 */
	public static ICellEditor getKDDatePickerEditor(){
		KDDatePicker datePicker = new KDDatePicker();
		datePicker.setMilliSecondEnable(false);
		datePicker.setTimeEnabled(false);
		ICellEditor comboEditor = new KDTDefaultCellEditor(datePicker);
		return comboEditor;		
	}
	
	/**
	 * 绑定的控件为KDTimePicker的cellEditor
	 */
	public static ICellEditor getKDTimePickerEditor(){
		KDTimePicker timePicker = new KDTimePicker();
		timePicker.setMilliSecondEnable(false);
		timePicker.setTimeEnabled(false);
		ICellEditor comboEditor = new KDTDefaultCellEditor(timePicker);
		return comboEditor;		
	}
	
	/**
	 * 绑定的控件为KDFormattedTextField的cellEditor 数字类型，精度2，不准为负数
	 * @return
	 */
	public static ICellEditor getKDFormattedTextDecimalEditor(){
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor formatTextEditor = new KDTDefaultCellEditor(formattedTextField);
		return formatTextEditor;		
	}
	
	
	/**
	 * 绑定的控件为KDComboColor
	 * @return
	 */
	public static ICellEditor getKDComboColorEditor(){
		KDComboColor comboColor = new KDComboColor();
		ICellEditor formatTextEditor = new KDTDefaultCellEditor(comboColor);
		return formatTextEditor;		
	}
	
	
	/**
	 * 当前组织下是否存在编码规则
	 * @param editData
	 * @return
	 */
	public static boolean isExistNumberRule(IObjectValue editData)
	{
		boolean isExist = false;
		
		OrgUnitInfo crrOu = SysContext.getSysContext().getCurrentSaleUnit();   //当前销售组织;
		if(crrOu==null)
			crrOu = SysContext.getSysContext().getCurrentOrgUnit();            //当前组织
		
		try{
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
			isExist = iCodingRuleManager.isExist(editData, crrOu.getId().toString());
		}catch(Exception e) {
			e.printStackTrace();
			SysUtil.abort();
		}
		
		return isExist;
	}
	
	
	/**
	 * 获得当前组织下的编码 -- 按编码规则 
	 * @param editData
	 * @return
	 */
	public static String getNumberByRule(IObjectValue editData)
	{
		String retNumber = "";
		
		OrgUnitInfo crrOu = SysContext.getSysContext().getCurrentSaleUnit();   //当前销售组织;
		if(crrOu==null)
			crrOu = SysContext.getSysContext().getCurrentOrgUnit();            //当前组织
		
		try{
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
			retNumber = iCodingRuleManager.getNumber(editData, crrOu.getId().toString());
		}catch(Exception e) {
			e.printStackTrace();
			SysUtil.abort();
		}
		
		return retNumber;
	}
	
	
	/**
	 * 批量获得当前组织下的编码 -- 按编码规则 
	 * @param editData
	 * @return
	 */
	public static String[] getBatchNumberByRule(IObjectValue editData,int count)
	{
		String[] retNumber = null;
		
		OrgUnitInfo crrOu = SysContext.getSysContext().getCurrentSaleUnit();   //当前销售组织;
		if(crrOu==null)
			crrOu = SysContext.getSysContext().getCurrentOrgUnit();            //当前组织
		
		try{
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
			retNumber = iCodingRuleManager.getBatchNumber(editData, crrOu.getId().toString(),count);
		}catch(Exception e) {
			e.printStackTrace();
			SysUtil.abort();
		}
		
		return retNumber;
	}
	
	
	/**
	 * 
	 * @param str_input		
	 * @param rDateFormat	格式串 比如：yyyy-MM-dd 或 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static  boolean isDateFormat(String str_input,String rDateFormat){
		if (str_input!=null) {
			SimpleDateFormat formatter = new SimpleDateFormat(rDateFormat);
			try {
				formatter.parse(str_input);
			} catch (Exception e) {
				return false;
			}
			return true;
		}
		return false;
	}
	
	
    public static boolean isNumber(String input)
    {
       if(input ==null || input.equals(""))
           return false;

        boolean havestr = false;
        String tmp = "";
        for (int j = 0; j < input.length(); j++)
        {
            havestr = false;
            tmp = input.substring(j, j + 1);
            if (tmp.getBytes()[0] < 48 || tmp.getBytes()[0] > 57)
            {
                havestr = true;
            }
            if (havestr)
            {
                return false;
            }
        }
        return true;
    }
	
	

}
