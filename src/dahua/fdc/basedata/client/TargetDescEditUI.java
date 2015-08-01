/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.BITargetCollection;
import com.kingdee.eas.fdc.basedata.BITargetFactory;
import com.kingdee.eas.fdc.basedata.BITargetInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.TargetDescEntryCollection;
import com.kingdee.eas.fdc.basedata.TargetDescEntryInfo;
import com.kingdee.eas.fdc.basedata.TargetDescFactory;
import com.kingdee.eas.fdc.basedata.TargetDescInfo;
import com.kingdee.eas.fdc.basedata.TargetGroupEnum;
import com.kingdee.eas.fdc.basedata.util.KDDetailedArea;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class TargetDescEditUI extends AbstractTargetDescEditUI
{
    public TargetDescEditUI() throws Exception {
		super();
	}

	private static final Logger logger = CoreUIObject.getLogger(TargetDescEditUI.class);

    public void onLoad() throws Exception {
    	this.operTable.checkParsed();
    	this.saleTable.checkParsed();
    	super.onLoad();
    	initEditor();
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
    		TargetDescEntryInfo info = (TargetDescEntryInfo) row.getUserObject();
    		info.setDesc((String) row.getCell("desc").getValue());
    		info.setIsHighLight((Boolean) row.getCell("isHighLight").getValue());
    	}
    }
    
    public void loadFields() {
    	clearTable();
    	super.loadFields();
    	loadEntry();
    	initControlDate();
    }

    private void clearTable() {
    	operTable.removeRows(false);
    	saleTable.removeRows(false);
    }
    
    /**
     * 把editdata中的entrys分为运营和营销2组
     * @return
     */
    private Map getGroupedEntryMap() {
    	HashMap result = new HashMap();
    	TargetDescEntryCollection coll = this.editData.getEntry();
    	TargetDescEntryInfo info = null;
    	TargetGroupEnum group = null;
    	TargetDescEntryCollection operList = new TargetDescEntryCollection();
    	TargetDescEntryCollection saleList = new TargetDescEntryCollection();
    	
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
    
    /**
     * 根据entry信息增加一行记录
     * @param table	表
     * @param entry	数据源
     */
    private void addRow(KDTable table, TargetDescEntryInfo entry) {
    	IRow row = table.addRow();
    	row.setUserObject(entry);
    	row.getCell("id").setValue(entry.getId()==null?null:entry.getId().toString());
    	row.getCell("targetId").setValue(entry.getTargetId());
    	row.getCell("name").setValue(entry.getTargetName());
    	row.getCell("desc").setValue(entry.getDesc());
    	row.getCell("isHighLight").setValue(entry.isIsHighLight());
    }
    
    private void loadEntry(TargetDescEntryCollection entrys, KDTable table) {
    	if(entrys==null || table==null) {
    		return;
    	}
    	
    	TargetDescEntryInfo entry = null;
    	for(int i=0; i<entrys.size(); i++) {
    		entry = entrys.get(i);
    		this.addRow(table, entry);
    	}
    }
    

    /**
     * 加载运营和营销数据
     */
	private void loadEntry() {
		Map entryMap = getGroupedEntryMap();
		TargetDescEntryCollection operaEntryColl = (TargetDescEntryCollection) entryMap.get(TargetGroupEnum.OPERATIONINFO_VALUE);
		TargetDescEntryCollection saleEntryColl = (TargetDescEntryCollection) entryMap.get(TargetGroupEnum.INVENTORYANDSALE_VALUE);
    	this.loadEntry(operaEntryColl, this.operTable);
    	this.loadEntry(saleEntryColl, this.saleTable);
    }
    
    
    private void initEditor() {
    	KDDetailedArea explain = new KDDetailedArea(250, 200);
		explain.setMaxLength(300);
		KDTDefaultCellEditor textEditor = new KDTDefaultCellEditor(explain);
		this.operTable.getColumn("desc").setEditor(textEditor);
		this.saleTable.getColumn("desc").setEditor(textEditor);
		
		KDCheckBox checkBox = new KDCheckBox();
		KDTDefaultCellEditor checkBoxEditor = new KDTDefaultCellEditor(checkBox);
		this.operTable.getColumn("isHighLight").setEditor(checkBoxEditor);
		this.saleTable.getColumn("isHighLight").setEditor(checkBoxEditor);
    }
    
    /**
	 * 初始化年和月份
	 */
	private void initControlDate() {
		int year = 0;
		int month = 1;
		if(OprtState.ADDNEW.equals(this.getOprtState())) {
			year = Calendar.getInstance().get(Calendar.YEAR);
			month = Calendar.getInstance().get(Calendar.MONTH) + 1;
		}else {
			year = this.editData.getYear();
			month = this.editData.getMonth();
		}
		
		SpinnerNumberModel yearMoFrom = new SpinnerNumberModel(year, 1900, 3100, 1);
		spiYear.setModel(yearMoFrom);
		SpinnerNumberModel monthMoFrom = new SpinnerNumberModel(month, 1, 12, 1);
		spiMonth.setModel(monthMoFrom);
	}
    
	
	/**
	 * 取得需录入的指标
	 * @return
	 * @throws BOSException
	 */
	public BITargetCollection getBITargetCollection() throws BOSException{
		 String sql = " Select id,TargetName,TargetId,targetGroup where TargetGroup in ('2','3') ";
		 BITargetCollection coll = BITargetFactory.getRemoteInstance().getBITargetCollection(sql);
		 return coll;
	}
	
	/**
	 * 建立分录表
	 * @throws BOSException 
	 * @throws EASBizException
	 * @throws BOSException
	 * @throws UuidException
	 */
	public TargetDescEntryCollection createEntry() throws BOSException {
		BITargetCollection coll = getBITargetCollection();
		TargetDescEntryCollection entrys = new TargetDescEntryCollection();
    	for(int i=0;i<coll.size();i++){
    		TargetDescEntryInfo entry = new TargetDescEntryInfo();
    		BITargetInfo bITarget = coll.get(i);
    		entry.setTargetName(bITarget.getTargetName());
    		entry.setTargetId(bITarget.getTargetId());
    		entry.setTargetGroup(bITarget.getTargetGroup());
    		entrys.add(entry);
    	}
    	
    	return entrys;
    }
	
	protected IObjectValue createNewData() {
		TargetDescInfo info = new TargetDescInfo();
		CurProjectInfo curProject = (CurProjectInfo) this.getUIContext().get("curProject");
		info.setCurProject(curProject);
		try {
			info.getEntry().addCollection(createEntry());
		} catch (BOSException e) {
			MsgBox.showError("新增失败");
			this.handUIExceptionAndAbort(e);
		}
		return info;
	}
	
	
	//处理弹出“数据已修改，是否保存并退出”的提示
    public boolean isModify() {
    	 if(OprtState.ADDNEW.equals(getOprtState())){
    		 return false;
    	 }
    	return super.isModify();
    }
	
	protected ICoreBase getBizInterface() throws Exception {
		return TargetDescFactory.getRemoteInstance();
	}

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