/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeEntryCollection;
import com.kingdee.eas.fdc.contract.ContractChangeEntryInfo;
import com.kingdee.eas.fdc.contract.IContractChangeBill;
import com.kingdee.eas.fi.gl.client.InitClientHelp;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ContractChangeVisaBatchUI extends AbstractContractChangeVisaBatchUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractChangeVisaBatchUI.class);
    
	// 列索引
	static final String MAIN_ID = "id";
	
	static final String ENTRY_ID = "id";
	static final String ENTRY_CONTENT= "number";
	
	static final String ENTRY_ALL  = "isAllExe";
	static final String ENTRY_PART  = "isPartExe";
	static final String ENTRY_NO  = "isNoExe";
	static final String ENTRY_DES  = "discription";
	
    private boolean isOk = false;
    
    //需要签证单据ID Set
    private Set idSet = null;
    
    //需要签证的变更单
    private ContractChangeBillCollection cols = null;
    
    //当前表格行索引
    private int curIndex = -1;
    
    /**
     * output class constructor
     */
    public ContractChangeVisaBatchUI() throws Exception
    {
        super();
    }
    
    private void initMyElement() {
    	
    	btnConfirm.setEnabled(true);
    	btnCancel.setEnabled(true);
    	
    	txtImpleCondition.setMaxLength(80);
    	txtDisThisTime.setMaxLength(80);
    	
    	tblMain.checkParsed();
        InitClientHelp.setTableKeyBoardManager(tblMain);
        tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
        
        
        tblMain.setEditable(false);
        
    	tblEntries.checkParsed();
    	tblEntries.getColumn(ENTRY_CONTENT).getStyleAttributes().setLocked(true);
    	
        InitClientHelp.setTableKeyBoardManager(tblEntries);
        tblEntries.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
        
        
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	initMyElement(); 	
    	
    	loadData();
    }

	public void onShow() throws Exception
    {
        super.onShow();
        
    }
	/**
	 * 主表界面加载数据
	 * @throws Exception 
	 */
	public void loadData() throws Exception {
		//super.loadFields();
		
		if (getUIContext().get(UIContext.IDLIST) != null) {
			List selectedIdValues = (List)getUIContext().get(UIContext.IDLIST);
			
			IContractChangeBill bill = (IContractChangeBill) getBizInterface();
			idSet = ContractClientUtils.listToSet(selectedIdValues);
			
			EntityViewInfo view = new EntityViewInfo();
			
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
			view.setFilter(filter);
			view.getSelector().addObjectCollection(genBillQuerySelector() );
			//view.getSelector().add("state");
			ContractChangeBillCollection coll = bill.getContractChangeBillCollection(view);
			
			Iterator itr = coll.iterator();
			while (itr.hasNext()) {
				ContractChangeBillInfo billInfo = (ContractChangeBillInfo)itr.next();
				
				IRow row = tblMain.addRow();
				loadTblMain( row , billInfo);
			}
			
			tblMain.getSelectManager().select(0,-1);
		}
	}
	
	protected void loadTblMain(IRow row ,ContractChangeBillInfo billInfo){
		//tblMain.addRow();
		row.setUserObject(billInfo);
		row.getCell(ENTRY_ID).setValue(billInfo.getId().toString());
		row.getCell("changeType.name").setValue(billInfo.getChangeType());
		row.getCell("number").setValue(billInfo.getNumber());
		row.getCell("name").setValue(billInfo.getName());
		//hasSettled
		row.getCell("hasSettled").setValue(Boolean.valueOf(billInfo.isHasSettled()));
		row.getCell("amount").setValue(billInfo.getAmount());
		row.getCell("settleAmount").setValue(billInfo.getBalanceAmount());
	}
	
	protected void loadTblEntries(IRow row ,ContractChangeEntryInfo entryInfo){
		row.setUserObject(entryInfo);
		if(entryInfo.getId()!=null){
			row.getCell(ENTRY_ID).setValue(entryInfo.getId().toString());
		}
		row.getCell(ENTRY_CONTENT).setValue(entryInfo.getChangeContent());
		row.getCell(ENTRY_ALL).setValue(Boolean.valueOf(entryInfo.isIsAllExe()));
		row.getCell(ENTRY_PART).setValue(Boolean.valueOf(entryInfo.isIsPartExe()));
		row.getCell(ENTRY_NO).setValue(Boolean.valueOf(entryInfo.isIsNoExe()));
		row.getCell(ENTRY_DES).setValue(entryInfo.getDiscription());
	}

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    	//先暂存数据
    	saveEntries();
    	
    	//然后删除
    	tblEntries.removeRows();    	
    	
    	
		//int line = e.get();
    	KDTable table = (KDTable) e.getSource();
    	KDTSelectBlock block = e.getSelectBlock();
    	int rowIndex = block.getTop();
    	
    	ContractChangeBillInfo billInfo = (ContractChangeBillInfo) table.getRow(rowIndex).getUserObject();
    	if(billInfo==null){
    		return ;
    	}
    	
    	ContractChangeEntryCollection coll = billInfo.getEntrys();
    	if(coll!=null && coll.size()>0){
			Iterator itr = coll.iterator();
			while (itr.hasNext()) {
				ContractChangeEntryInfo entryInfo = (ContractChangeEntryInfo)itr.next();
				
				IRow row = tblEntries.addRow();
				loadTblEntries( row , entryInfo);
			}
    	}else{
    		//k3导入的数据批量签证
    		ContractChangeEntryInfo entryInfo = new ContractChangeEntryInfo();
    		IRow row = tblEntries.addRow();
    		loadTblEntries( row , entryInfo);
    	}
		
    	txtImpleCondition.setText(billInfo.getImpleCondition());
    	txtDisThisTime.setText(billInfo.getDisThisTime());
    	
    	curIndex = rowIndex;
    }

    /**
     * output tblEntries_tableSelectChanged method
     */
    protected void tblEntries_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        
    }

    protected void tblEntries_editStarting(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    	Object newValue = e.getValue();
		KDTable table = (KDTable) e.getSource();
		int row = e.getRowIndex();
		int col = e.getColIndex();
		String colKey = table.getColumnKey(col);
		IRow curRow = table.getRow(row);
		
		if((newValue instanceof Boolean) && !((Boolean)newValue).booleanValue()){
			if(colKey.equals(ENTRY_ALL)){
				curRow.getCell(ENTRY_PART).setValue(Boolean.FALSE);
				curRow.getCell(ENTRY_NO).setValue(Boolean.FALSE);
			}else if(colKey.equals(ENTRY_PART)){
				curRow.getCell(ENTRY_ALL).setValue(Boolean.FALSE);
				curRow.getCell(ENTRY_NO).setValue(Boolean.FALSE);
			}else if(colKey.equals(ENTRY_NO)){
				curRow.getCell(ENTRY_ALL).setValue(Boolean.FALSE);
				curRow.getCell(ENTRY_PART).setValue(Boolean.FALSE);
			}
		}
    }
    
    /**
     * output tblEntries_editStopped method
     */
    protected void tblEntries_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
		//Object oldValue = e.getOldValue();
    	chaged (e);
		
    }
    
    private void chaged(KDTEditEvent e){
    	Object newValue = e.getValue();
		KDTable table = (KDTable) e.getSource();
		int row = e.getRowIndex();
		int col = e.getColIndex();
		String colKey = table.getColumnKey(col);
		IRow curRow = table.getRow(row);
		
		if((newValue instanceof Boolean) && ((Boolean)newValue).booleanValue()){
			if(colKey.equals(ENTRY_ALL)){
				curRow.getCell(ENTRY_PART).setValue(Boolean.FALSE);
				curRow.getCell(ENTRY_NO).setValue(Boolean.FALSE);
			}else if(colKey.equals(ENTRY_PART)){
				curRow.getCell(ENTRY_ALL).setValue(Boolean.FALSE);
				curRow.getCell(ENTRY_NO).setValue(Boolean.FALSE);
			}else if(colKey.equals(ENTRY_NO)){
				curRow.getCell(ENTRY_ALL).setValue(Boolean.FALSE);
				curRow.getCell(ENTRY_PART).setValue(Boolean.FALSE);
			}
		}
    }
    
    private void saveEntries(){
    	if(curIndex<0){
    		return ;
    	}
    	ContractChangeBillInfo billInfo = (ContractChangeBillInfo) tblMain.getRow(curIndex).getUserObject();
    	
    	int rowCount = tblEntries.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			// 添加分录行的各对方科目数据
			IRow cfOppEntryRow = tblEntries.getRow(i);
			
			String id = (String)cfOppEntryRow.getCell(ENTRY_ID).getValue();
			ContractChangeEntryCollection coll = billInfo.getEntrys();
			if(id!=null){		        
				Iterator itr = coll.iterator();
				while (itr.hasNext()) {
					ContractChangeEntryInfo entryInfo = (ContractChangeEntryInfo)itr.next();
					if(id.equals(entryInfo.getId().toString())){
						
						//entryInfo.setChangeContent((String)cfOppEntryRow.getCell(ENTRY_CONTENT).getValue());
						entryInfo.setIsAllExe(((Boolean)cfOppEntryRow.getCell(ENTRY_ALL).getValue()).booleanValue());
						entryInfo.setIsPartExe(((Boolean)cfOppEntryRow.getCell(ENTRY_PART).getValue()).booleanValue());
						entryInfo.setIsNoExe(((Boolean)cfOppEntryRow.getCell(ENTRY_NO).getValue()).booleanValue());
						entryInfo.setDiscription((String)cfOppEntryRow.getCell(ENTRY_DES).getValue());
						
						break;
					}
				}
			}else{
				billInfo.getEntrys().clear();
				ContractChangeEntryInfo entryInfo = (ContractChangeEntryInfo)cfOppEntryRow.getUserObject();
				entryInfo.setIsAllExe(((Boolean)cfOppEntryRow.getCell(ENTRY_ALL).getValue()).booleanValue());
				entryInfo.setIsPartExe(((Boolean)cfOppEntryRow.getCell(ENTRY_PART).getValue()).booleanValue());
				entryInfo.setIsNoExe(((Boolean)cfOppEntryRow.getCell(ENTRY_NO).getValue()).booleanValue());
				entryInfo.setDiscription((String)cfOppEntryRow.getCell(ENTRY_DES).getValue());
				coll.add(entryInfo);
			}
		}
		
		billInfo.setImpleCondition(txtImpleCondition.getText());
		billInfo.setDisThisTime(txtDisThisTime.getText());
    }
    
	private void checkBeforeConfirm() throws Exception{
		
		cols = new  ContractChangeBillCollection();
    	int rowCount = tblMain.getRowCount();
    	 idSet = new HashSet();
		for (int i = 0; i < rowCount; i++) {
			// 添加分录行的各对方科目数据
			IRow curRow = tblMain.getRow(i);
			ContractChangeBillInfo billInfo = (ContractChangeBillInfo)curRow.getUserObject();
			
	        ContractChangeEntryCollection coll = billInfo.getEntrys();
			Iterator itr = coll.iterator();
			
			boolean canSigned = false;
			while (itr.hasNext()) {
				ContractChangeEntryInfo entryInfo = (ContractChangeEntryInfo)itr.next();
				
				if(entryInfo.isIsAllExe() || entryInfo.isIsPartExe() || entryInfo.isIsNoExe()){
					canSigned = true;
				}			
			}
			
			if(canSigned){
				cols.add(billInfo);
				
				idSet.add(billInfo.getId().toString());
			}
		}
		
		if(cols.size()==0){
			MsgBox.showWarning(this,"没有单据已签证");
			
			SysUtil.abort();
		}
	}
	
    private void save() throws Exception{
    	//服务端需要传入PK[]才能记录日志
    	IObjectPK[] pks = new IObjectPK[idSet.size()];
    	int i=0;
    	for(Iterator it=idSet.iterator();it.hasNext();){
    		pks[i++] = new ObjectUuidPK((String)it.next());
    	}
    	boolean isSave = ContractChangeBillFactory.getRemoteInstance().visa(pks,cols);
    	
    	if(isSave){
    		MsgBox.showInfo(this,"签证成功");
    	}
    }
    
   /**
     * output actionComfirm_actionPerformed
     */
    public void actionComfirm_actionPerformed(ActionEvent e) throws Exception
    {
    	saveEntries();
    	
    	//数据检查
    	checkBeforeConfirm() ;
    	//保存
    	save();
    	
    	setConfirm(true, e);
    }


	
    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	setConfirm(false, e);
    }
    
	protected ICoreBase getBizInterface() throws Exception {
		return ContractChangeBillFactory.getRemoteInstance();
	}
	
	public void setConfirm(boolean isOk,ActionEvent e) throws Exception {
		this.isOk = isOk;
		//actionExitCurrent_actionPerformed(e);
		
		this.destroyWindow();
	}
	
	protected SelectorItemCollection genBillQuerySelector() {
		SelectorItemCollection selectors = new SelectorItemCollection();

		selectors.add("number");
		selectors.add("name");
		selectors.add("amount");
		selectors.add("balanceAmount");
		selectors.add("hasSettled");	
		selectors.add("changeType.name");
		selectors.add("changeType.name");
		
		//impleCondition
		selectors.add("impleCondition");
		selectors.add("disThisTime");
		
//		selectors.add("contractBill.number");
//		selectors.add("contractBill.number");
		
		//changeContent
		selectors.add("entrys.changeContent");
		selectors.add("entrys.isAllExe");
		selectors.add("entrys.isPartExe");
		selectors.add("entrys.isNoExe");
		selectors.add("entrys.discription");
		
		return selectors;
	}

}