/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDNumberTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.BITargetCollection;
import com.kingdee.eas.fdc.basedata.BITargetFactory;
import com.kingdee.eas.fdc.basedata.BITargetInfo;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.TargetDataTypeEnum;
import com.kingdee.eas.fdc.basedata.TargetGroupEnum;
import com.kingdee.eas.fdc.basedata.TargetWarningEntryCollection;
import com.kingdee.eas.fdc.basedata.TargetWarningEntryInfo;
import com.kingdee.eas.fdc.basedata.TargetWarningFactory;
import com.kingdee.eas.fdc.basedata.TargetWarningInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class TargetWarningEditUI extends AbstractTargetWarningEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(TargetWarningEditUI.class);
    
    /**
     * output class constructor
     */
    public TargetWarningEditUI() throws Exception
    {
        super();
    }
    
    protected void verifyInput(ActionEvent e) throws Exception {
    	verifyTableFields(operTable);
    	verifyTableFields(saleTable);
    }
    
    private void verifyTableFields(KDTable table) {
    	FDCClientVerifyHelper.verifyInput(this, table, "baseValue");
    	FDCClientVerifyHelper.verifyInput(this, table, "lowerLimit");
    	FDCClientVerifyHelper.verifyInput(this, table, "upperLimit");
    }
    
    Map editorMap = new HashMap();	//数据类型-->编辑器;
    /**
     * 初始化编辑器Map
     */
    private void initEditorMap() {
    	KDDatePicker datePicker = new KDDatePicker();
    	datePicker.setDatePattern("yyyy-MM-dd");
		KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(datePicker);
//		datePicker.setTimeEnabled(false);
		
		KDNumberTextField numText = new KDNumberTextField();
		KDTDefaultCellEditor numEditor = new KDTDefaultCellEditor(numText);
		
		editorMap.put(TargetDataTypeEnum.DATETYPE_VALUE, dateEditor);
		editorMap.put(TargetDataTypeEnum.PERCENTAGE_VALUE, numEditor);
		editorMap.put(TargetDataTypeEnum.NUMBER_VALUE, numEditor);
    }
    
    private KDTDefaultCellEditor getEditor(String dataType) {
    	return (KDTDefaultCellEditor) editorMap.get(dataType);
    }
    
    
    public void onLoad() throws Exception {
    	chkIsEnabled.setVisible(false);
    	this.operTable.checkParsed();
    	this.saleTable.checkParsed();
    	setNumberEditor(operTable);
    	setNumberEditor(saleTable);
    	initEditorMap();
    	super.onLoad();
    }
    
    /**
     * 设置数字型编辑器。
     * 上限和下限值都为数字型
     * @param table
     */
    private void setNumberEditor(KDTable table) {
    	KDTDefaultCellEditor numEditor = (KDTDefaultCellEditor) editorMap.get(TargetDataTypeEnum.NUMBER_VALUE);
    	table.getColumn("lowerLimit").setEditor(numEditor);
    	table.getColumn("upperLimit").setEditor(numEditor);
    }
    
    public void loadFields() {
    	clearTable();
    	super.loadFields();
    	loadEntry();
    }
    
    /**
     * 加载运营和营销数据
     */
	private void loadEntry() {
		Map entryMap = getGroupedEntryMap();
		TargetWarningEntryCollection operaEntryColl = (TargetWarningEntryCollection) entryMap.get(TargetGroupEnum.OPERATIONINFO_VALUE);
		TargetWarningEntryCollection saleEntryColl = (TargetWarningEntryCollection) entryMap.get(TargetGroupEnum.INVENTORYANDSALE_VALUE);
    	try {
			this.loadEntry(operaEntryColl, this.operTable);
			this.loadEntry(saleEntryColl, this.saleTable);
		} catch (ParseException e) {
			this.handUIExceptionAndAbort(e);
		}
    }
    
	private void loadEntry(TargetWarningEntryCollection entrys, KDTable table) throws ParseException {
    	if(entrys==null || table==null) {
    		return;
    	}
    	
    	TargetWarningEntryInfo entry = null;
    	for(int i=0; i<entrys.size(); i++) {
    		entry = entrys.get(i);
    		this.addRow(table, entry);
    	}
    }
	
	 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	 /**
     * 根据entry信息增加一行记录
     * @param table	表
     * @param entry	数据源
	 * @throws ParseException 
     */
    private void addRow(KDTable table, TargetWarningEntryInfo entry) throws ParseException {
    	IRow row = table.addRow();
    	row.setUserObject(entry);
    	row.getCell("id").setValue(entry.getId()==null?null:entry.getId().toString());
    	row.getCell("targetId").setValue(entry.getTargetId());
    	row.getCell("name").setValue(entry.getTargetName());
    	row.getCell("lowerLimit").setValue(entry.getLowerLimit());
    	row.getCell("upperLimit").setValue(entry.getUpperLimit());
    	
    	//基准值可能是日期，也可能是数字。
    	TargetDataTypeEnum dataType = entry.getDataType();
    	row.getCell("baseValue").setEditor(getEditor(dataType.getValue()));
    	if(dataType==TargetDataTypeEnum.DateType) {//把String的日期数据转回Date
    		if(entry.getBaseValue() != null) {
    			Date date = dateFormat.parse(entry.getBaseValue());
    			row.getCell("baseValue").setValue(date);
    		}
    		row.getCell("baseValue").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
    	}else {
    		row.getCell("baseValue").setValue(entry.getBaseValue());
    	}
    }
	
	 /**
     * 把editdata中的entrys分为运营和营销2组
     * @return
     */
    private Map getGroupedEntryMap() {
    	HashMap result = new HashMap();
    	TargetWarningEntryCollection coll = this.editData.getEntry();
    	TargetWarningEntryInfo info = null;
    	TargetGroupEnum group = null;
    	TargetWarningEntryCollection operList = new TargetWarningEntryCollection();
    	TargetWarningEntryCollection saleList = new TargetWarningEntryCollection();
    	
    	for(int i=0; i<coll.size(); i++) {
    		info = coll.get(i);
    		group = info.getTargetGroup();
    		if(group==TargetGroupEnum.OperationInfo) {
    			operList.add(info);
    		}else {
    			saleList.add(info);
    		}
    	}
    	
    	result.put(TargetGroupEnum.OPERATIONINFO_VALUE, operList);
    	result.put(TargetGroupEnum.INVENTORYANDSALE_VALUE, saleList);
    	return result;
    }
	
    private void clearTable() {
    	operTable.removeRows(false);
    	saleTable.removeRows(false);
    }
    
    public void storeFields()
    {
    	  super.storeFields();
          storeTableFields(operTable);
          storeTableFields(saleTable);
          
          
    }

    private void storeTableFields(KDTable table) {
    	int rowCount = table.getRowCount();
    	for(int i=0; i<rowCount; i++) {
    		IRow row = table.getRow(i);
    		TargetWarningEntryInfo info = (TargetWarningEntryInfo) row.getUserObject();
    		info.setLowerLimit((String) row.getCell("lowerLimit").getValue());
    		info.setUpperLimit((String) row.getCell("upperLimit").getValue());
    		
    		if(info.getDataType()==TargetDataTypeEnum .DateType) {//数据库里为String,,,只能转换了。。
    			Date date = (Date) row.getCell("baseValue").getValue();
    			if(date!=null) {
    				info.setBaseValue(dateFormat.format(date));
    			}
    		}else {
    			info.setBaseValue((String) row.getCell("baseValue").getValue());
    		}
    	}
    }
    
    protected IObjectValue createNewData() {
    	TargetWarningInfo info = new TargetWarningInfo();
		try {
			info.getEntry().addCollection(createEntry());
		} catch (BOSException e) {
			MsgBox.showError("新增失败");
			this.handUIExceptionAndAbort(e);
		}
		return info;
    }
    
    /**
	 * 建立分录表
	 * @throws BOSException 
	 * @throws EASBizException
	 * @throws BOSException
	 * @throws UuidException
	 */
	public TargetWarningEntryCollection createEntry() throws BOSException {
		BITargetCollection coll = getBITargetCollection();
		TargetWarningEntryCollection entrys = new TargetWarningEntryCollection();
    	for(int i=0;i<coll.size();i++){
    		TargetWarningEntryInfo entry = new TargetWarningEntryInfo();
    		BITargetInfo bITarget = coll.get(i);
    		entry.setTargetName(bITarget.getTargetName());
    		entry.setTargetId(bITarget.getTargetId());
    		entry.setTargetGroup(bITarget.getFromWhichGroup());
    		entry.setDataType(bITarget.getDataType());
    		entrys.add(entry);
    	}
    	
    	return entrys;
    }
    
	/**
	 * 取得需录入的指标
	 * @return
	 * @throws BOSException
	 */
	public BITargetCollection getBITargetCollection() throws BOSException{
		 String sql = " Select id,TargetName,TargetId,targetGroup, dataType, fromWhichGroup where TargetGroup in ('10') ";
		 BITargetCollection coll = BITargetFactory.getRemoteInstance().getBITargetCollection(sql);
		 return coll;
	}
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
//		setIsEnable(this.chkIsEnabled.isSelected());
		super.actionSubmit_actionPerformed(e);
	}
	
	protected void setIsEnable(boolean flag) throws Exception {
		baseDataInfo = getEditData();
		getCtrler().checkPermission(FDCBaseDataClientCtrler.ACTION_MODIFY);
		if (!flag && !canCancel)
			if (isSystemDefaultData()) {
				MsgBox.showError(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "operate_SysData"));
				return;
			}
		baseDataInfo.setIsEnabled(flag);
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("isEnabled"));
		String message = null;
		if (flag) {
			TargetWarningFactory.getRemoteInstance().enable(new ObjectUuidPK(baseDataInfo.getId()));
			message = EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK");
		} else {
			getBizInterface().updatePartial(baseDataInfo, sic);
			message = EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK");
		}
		setMessageText(message);
		showMessage();

		setDataObject(getValue(new ObjectUuidPK(baseDataInfo.getId())));
		loadFields();
		setSave(true);
		setSaved(true);

		this.btnCancelCancel.setVisible(!flag);
		this.btnCancelCancel.setEnabled(!flag);
		this.btnCancel.setVisible(flag);
		this.btnCancel.setEnabled(flag);
		// this.chkIsEnabled.setSelected(flag);

	}
	
	
	protected FDCDataBaseInfo getEditData() {
		return editData;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return bizName;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return TargetWarningFactory.getRemoteInstance();
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("id");
		sels.add("name");
		sels.add("number");
		sels.add("isEnabled");
		sels.add("entry.*");
		return sels;
	}
}