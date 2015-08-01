/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDNumberTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.BITargetCollection;
import com.kingdee.eas.fdc.basedata.BITargetFactory;
import com.kingdee.eas.fdc.basedata.BITargetInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.TargetDataTypeEnum;
import com.kingdee.eas.fdc.basedata.TargetGroupEnum;
import com.kingdee.eas.fdc.basedata.TargetInfoCollection;
import com.kingdee.eas.fdc.basedata.TargetInfoEntryCollection;
import com.kingdee.eas.fdc.basedata.TargetInfoEntryInfo;
import com.kingdee.eas.fdc.basedata.TargetInfoFactory;
import com.kingdee.eas.fdc.basedata.TargetInfoInfo;
import com.kingdee.eas.fdc.sales.client.SHEHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class TargetInfoEditUI extends AbstractTargetInfoEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(TargetInfoEditUI.class);
    private FullOrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
    /**
     * output class constructor
     */
    public TargetInfoEditUI() throws Exception
    {
        super();
    }

    private void clearTable() {
    	operTable.removeRows(false);
    	saleTable.removeRows(false);
    }
    
    
    public void loadFields()
    {
    	clearTable();
    	super.loadFields();
    	loadEntry();
    	initControlDate();
    	/* super.loadFields();
    	 if(OprtState.ADDNEW.equals(getOprtState())){
    		 kdtEntry.removeRows();
		 	 this.initControlDate();
    		 try {
 	 			this.createEntry();
 	 		} catch (EASBizException e) {
 	 			e.printStackTrace();
 	 		} catch (BOSException e) {
 	 			e.printStackTrace();
 	 		} catch (UuidException e) {
 	 			e.printStackTrace();
 	 		}
    	 }else{
    		 try {
  	 			 this.viewEntry();
  	 		} catch (EASBizException e) {
  	 			e.printStackTrace();
  	 		} catch (BOSException e) {
  	 			e.printStackTrace();
  	 		} catch (UuidException e) {
  	 			e.printStackTrace();
  	 		}
    	 }*/
    }
    
    /**
     * 加载运营和营销数据
     * @throws ParseException 
     */
	private void loadEntry() {
		Map entryMap = getGroupedEntryMap();
		TargetInfoEntryCollection operaEntryColl = (TargetInfoEntryCollection) entryMap.get(TargetGroupEnum.OPERATIONINFO_VALUE);
		TargetInfoEntryCollection saleEntryColl = (TargetInfoEntryCollection) entryMap.get(TargetGroupEnum.INVENTORYANDSALE_VALUE);
    	try {
			this.loadEntry(operaEntryColl, this.operTable);
			this.loadEntry(saleEntryColl, this.saleTable);
		} catch (Exception e) {
			this.handUIExceptionAndAbort(e);
		}
    }
    
	 /**
     * 把editdata中的entrys分为运营和营销2组
     * @return
     */
    private Map getGroupedEntryMap() {
    	HashMap result = new HashMap();
    	TargetInfoEntryCollection coll = this.editData.getEntry();
    	TargetInfoEntryInfo info = null;
    	TargetGroupEnum group = null;
    	TargetInfoEntryCollection operList = new TargetInfoEntryCollection();
    	TargetInfoEntryCollection saleList = new TargetInfoEntryCollection();
    	
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
    
    private void loadEntry(TargetInfoEntryCollection entrys, KDTable table) throws ParseException {
    	if(entrys==null || table==null) {
    		return;
    	}
    	
    	TargetInfoEntryInfo entry = null;
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
    private void addRow(KDTable table, TargetInfoEntryInfo entry) throws ParseException {
    	IRow row = table.addRow();
    	row.setUserObject(entry);
    	row.getCell("id").setValue(entry.getId()==null?null:entry.getId().toString());
    	row.getCell("targetId").setValue(entry.getTargetId());
    	row.getCell("name").setValue(entry.getTargetName());
    	
    	TargetDataTypeEnum dataType = entry.getDataType();
    	row.getCell("value").setEditor(getEditor(dataType.getValue()));
    	
    	if(dataType==TargetDataTypeEnum.DateType ) {//把String的日期数据转回Date
    		if(entry.getValue()!=null) {
    			Date date = dateFormat.parse(entry.getValue());
    			row.getCell("value").setValue(date);
    		}
    		row.getCell("value").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
    	}else {
    		row.getCell("value").setValue(entry.getValue());
    	}
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
    	this.operTable.checkParsed();
    	this.saleTable.checkParsed();
    	initEditorMap();
		super.onLoad();
		this.initCurrProject();
		if (this.getUIContext().get("curProject") == null) {
			this.getUIContext().put("curProject", editData.getCurProject());
		}
    }
    
    private void initCurrProject() {
		String query = "com.kingdee.eas.fdc.basedata.app.F7ProjectQuery";
		FilterInfo filterInfo = new FilterInfo();
		if (org != null && org.getId() != null) {
			filterInfo.getFilterItems().add(
					new FilterItemInfo("fullOrgUnit.id",
							org.getId().toString(), CompareType.EQUALS));
		}
		SHEHelper.initF7(this.prmtCurProject, query, filterInfo);
	}
    /**
     * output storeFields method
     */
    public void storeFields()
    {
    	/*TargetInfoInfo info = (TargetInfoInfo) this.editData;
    	TargetInfoEntryCollection entrys =info.getEntry();
    	entrys.clear();
    	for (int i = 0; i < kdtEntry.getRowCount(); i++) {
    		IRow row = kdtEntry.getRow(i);
    		TargetInfoEntryInfo entry = (TargetInfoEntryInfo) row.getUserObject();
    		if(row.getCell("entry.value").getValue() != null){
    			String value = row.getCell("entry.value").getValue().toString();
    			if(row.getCell("entry.BITarget").getValue().toString().indexOf("时间") > -1){
    				entry.setValue(changeDateType(value));
    			}else{
    				entry.setValue(value);
    			}
    			
    		}
    		entrys.add(entry);
    	}*/
    	super.storeFields();
    	storeTableFields(operTable);
        storeTableFields(saleTable);
    }

    private void storeTableFields(KDTable table) {
    	int rowCount = table.getRowCount();
    	for(int i=0; i<rowCount; i++) {
    		IRow row = table.getRow(i);
    		TargetInfoEntryInfo info = (TargetInfoEntryInfo) row.getUserObject();
    		if(info.getDataType()==TargetDataTypeEnum .DateType) {//数据库里为String,,,只能转换了。。
    			Date date = (Date) row.getCell("value").getValue();
    			if(date!=null) {
    				info.setValue(dateFormat.format(date));
    			}
    		}else {
    			info.setValue((String) row.getCell("value").getValue());
    		}
    	}
    }
    
    
    //处理弹出“数据已修改，是否保存并退出”的提示
    public boolean isModify() {
    	 if(OprtState.ADDNEW.equals(getOprtState())){
    		 return false;
    	 }
    	return super.isModify();
    }
    
	protected IObjectValue createNewData() {
		TargetInfoInfo info = new TargetInfoInfo();
		CurProjectInfo curProject = (CurProjectInfo) this.getUIContext().get("curProject");
		info.setCurProject(curProject);
		try {
			info.getEntry().addCollection(createEntry(curProject.getId().toString()));
		} catch (Exception e) {
			MsgBox.showError("新增失败");
			this.handUIExceptionAndAbort(e);
		} 
		
		return info;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return TargetInfoFactory.getRemoteInstance();
	}

	/**
	 * 保存
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		/*if(this.kdtEntry != null && this.kdtEntry.getRowCount() > 0){
			for(int i = 0;i <this.kdtEntry.getRowCount();i++){
			   if(this.kdtEntry.getRow(i).getCell("entry.value").getValue() == null
			     || this.kdtEntry.getRow(i).getCell("entry.value").getValue().toString().trim().equals("")){
				   MsgBox.showWarning(this, this.kdtEntry.getRow(i).getCell("entry.BITarget").getValue()+"的指标值不能为空！");
					return;
			   }
			}
		}*/
       super.actionSubmit_actionPerformed(e);
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyInput(this, operTable, "value");
		FDCClientVerifyHelper.verifyInput(this, saleTable, "value");
		super.verifyInput(e);
	}
    
	/**
	 * 取得需录入的指标
	 * @return
	 * @throws BOSException
	 */
	public BITargetCollection getBITargetCollection() throws BOSException{
		 String sql = " Select id,TargetName,TargetId,dataType,fromWhichGroup,statisticsType where TargetGroup = '9'  ";
		 BITargetCollection coll = BITargetFactory.getRemoteInstance().getBITargetCollection(sql);
		 return coll;
	}
	
	/**
	 * 建立分录表
	 * @throws BOSException 
	 */
	public TargetInfoEntryCollection createEntry(String prjId) throws BOSException{
//		BITargetCollection coll = getBITargetCollection();
		TargetInfoEntryCollection coll = TargetInfoFactory.getRemoteInstance().getInitedCollection(prjId);
		return coll;
		
		/*TargetInfoEntryCollection entrys = new TargetInfoEntryCollection();
    	for(int i=0;i<coll.size();i++){
    		TargetInfoEntryInfo entry = new TargetInfoEntryInfo();
    		BITargetInfo bITarget = coll.get(i);
    		entry.setTargetName(bITarget.getTargetName());
    		entry.setTargetId(bITarget.getTargetId());
    		entry.setDataType(bITarget.getDataType());
    		entry.setTargetGroup(bITarget.getFromWhichGroup());
    		entry.setStatisticsType(bITarget.getStatisticsType());
    		entrys.add(entry);
    	}
    	return entrys;*/
    }
	
	/**
	 * 如果是双击打开查看和编辑时
	 * @throws EASBizException
	 * @throws BOSException
	 * @throws UuidException
	 */
	public void viewEntry() throws EASBizException, BOSException, UuidException{
	/*	if(this.editData.getId() != null){
			 String sql = " Select id,value,BItarget.id,BItarget.targetName,targetId where head.id = '"+this.editData.getId().toString()+"'  ";
			 TargetInfoEntryCollection coll = TargetInfoEntryFactory.getRemoteInstance().getTargetInfoEntryCollection(sql);
			    if(coll != null && coll.size() > 0){
			    	this.kdtEntry.removeRows();
			    	for(int i=0;i<coll.size();i++){
			    		this.addEntryRow(coll.get(i));
			    	}
			    }
		}*/
	}

	
	/**
	 * 初始化年和月份
	 */
	private void initControlDate() {
		if(this.getOprtState()==OprtState.ADDNEW) {
			try {
				Timestamp time = FDCCommonServerHelper.getServerTimeStamp();
				Calendar cal = Calendar.getInstance();
				cal.setTime(time);
				spiYear.setValue(cal.get(Calendar.YEAR));
				spiMonth.setValue(cal.get(Calendar.MONTH) + 1);
			} catch (BOSException e) {
				handUIExceptionAndAbort(e);
			}
		}else {
			spiYear.setValue(this.editData.getYear());
			spiMonth.setValue(editData.getMonth());
		}
		/*SpinnerNumberModel yearMoFrom = new SpinnerNumberModel(Calendar.getInstance().get(Calendar.YEAR), 1900, 2100, 1);
		spiYear.setModel(yearMoFrom);
		SpinnerNumberModel monthMoFrom = new SpinnerNumberModel(Calendar.getInstance().get(Calendar.MONTH) + 1, 1, 12, 1);
		spiMonth.setModel(monthMoFrom);*/
	}

	/**
	 * 要查询的字段
	 */
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("id");
		sels.add("year");
		sels.add("month");
		sels.add("curProject.id");
		sels.add("curProject.name");
		sels.add("entry.*");
		return sels;
	}
}