/**
 * output package name
 */
package com.kingdee.eas.fdc.material.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.event.KDTCellEditorListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.PayRequestBillConfirmEntryCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillConfirmEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.client.PayRequestBillEditUI;
import com.kingdee.eas.fdc.material.MaterialConfirmBillFactory;
import com.kingdee.eas.fdc.material.MaterialConfirmBillInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 付款申请单 -> 查看材料确认单
 */
public class MaterialConfirmBillSimpleListUI extends AbstractMaterialConfirmBillSimpleListUI
{
    private static final Logger logger = CoreUIObject.getLogger(MaterialConfirmBillSimpleListUI.class);
    
    private PayRequestBillInfo editData = null;
    private PayRequestBillEditUI editUI = null;
    public ICellEditor chkEditor = null;
    public boolean isBtnOK = false;
    public boolean isChanged = false;
    
    /**
     * output class constructor
     */
    public MaterialConfirmBillSimpleListUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception{
    	editData = (PayRequestBillInfo)this.getUIContext().get("PayRequestBillInfo");
    	if(editData!=null){
    		PayRequestBillConfirmEntryCollection oldConfirmEntry = (PayRequestBillConfirmEntryCollection)editData.getConfirmEntry().clone();
    		editData.put("oldConfirmEntry", oldConfirmEntry);
    	}
    	editUI   = (PayRequestBillEditUI)this.getUIContext().get(UIContext.OWNER);
    	Map confirmEntryMap = new HashMap(); 
    	if(editData!=null&&editData.getConfirmEntry()!=null){
    		for(Iterator it = editData.getConfirmEntry().iterator();it.hasNext();){
    			PayRequestBillConfirmEntryInfo info = (PayRequestBillConfirmEntryInfo)it.next();
    			if(info.getConfirmBill()!=null)
    				confirmEntryMap.put(info.getConfirmBill().getId().toString(), info);
    		}
    	}
    	editData.put("confirmEntryMap", confirmEntryMap);
    	if(editData.containsKey("HasReqNotPaid")){
    		this.chkHasReqNotPaid.setSelected(((Boolean)editData.get("HasReqNotPaid")).booleanValue());
    	}
    	super.onLoad();
    	if("EDIT".equals(this.getOprtState())||"ADDNEW".equals(getOprtState()))
    	{
    		this.tblMain.getColumn("isSelect").getStyleAttributes().setLocked(false);
    		this.tblMain.getColumn("reqAmount").getStyleAttributes().setLocked(false);
    		this.chkHasReqNotPaid.setEnabled(true);
    	}else{
    		this.chkHasReqNotPaid.setEnabled(false);
    	}
    	FDCHelper.formatTableNumber(tblMain, "reqAmount");
    	FDCHelper.formatTableNumber(tblMain, "notReqAmount");
    	FDCHelper.formatTableNumber(tblMain, "toDateReqAmt");
    	KDCheckBox chkField = new KDCheckBox();
    	chkEditor = new KDTDefaultCellEditor(chkField);
    	chkEditor.addCellEditorListener(new KDTCellEditorListener(){

			public void editingCanceled() {
				
			}

			public void editingChanged() {
				KDCheckBox chkField = (KDCheckBox)chkEditor.getComponent();
				Object newValue = Boolean.valueOf(chkField.isSelected());
				int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
            	isSelectChanged(newValue, tblMain.getRow(rowIndex));
			}

			public void editingStopped() {
				
			}
    		
    	});
		
		this.tblMain.getColumn("isSelect").setEditor(chkEditor);
		isBtnOK = false;
//    	this.btnCC.setVisible(false);
    }

    public void onGetRowSet(IRowSet rowSet) {
		super.onGetRowSet(rowSet);
		
		try {
			Map confirmEntryMap = null;
			if(editData!=null&&editData.containsKey("confirmEntryMap")){
				confirmEntryMap = (HashMap)editData.get("confirmEntryMap");
			}
			if(confirmEntryMap!=null){
				while(rowSet.next()){
					String confirmBillId = rowSet.getString("id");
					if(confirmEntryMap.containsKey(confirmBillId)){
						PayRequestBillConfirmEntryInfo info = (PayRequestBillConfirmEntryInfo)confirmEntryMap.get(confirmBillId);
						if(info!=null){
							rowSet.updateBigDecimal("reqAmount", info.getReqAmount());
							rowSet.updateBoolean("isSelect", true);
							if("ADDNEW".equals(getOprtState())){
								BigDecimal toDateReqAmount = FDCHelper.toBigDecimal(rowSet.getBigDecimal("toDateReqAmt")).add(FDCHelper.toBigDecimal(info.getReqAmount()));
								rowSet.updateBigDecimal("toDateReqAmt", toDateReqAmount);
								BigDecimal confirmAmt = FDCHelper.toBigDecimal(rowSet.getBigDecimal("confirmAmt"));
								rowSet.updateBigDecimal("notReqAmt", confirmAmt.subtract(toDateReqAmount));
							}else{
								if(!info.containsKey("notIncludeThisReqAmount")){
									BigDecimal notIncludeThisReqAmount = FDCHelper.toBigDecimal(rowSet.getBigDecimal("toDateReqAmt")).subtract(FDCHelper.toBigDecimal(info.getReqAmount()));
									info.put("notIncludeThisReqAmount", notIncludeThisReqAmount);
								}
								else{
									BigDecimal notIncludeThisReqAmount = FDCHelper.toBigDecimal(info.get("notIncludeThisReqAmount"));
									BigDecimal toDateReqAmount = notIncludeThisReqAmount.add(FDCHelper.toBigDecimal(info.getReqAmount()));
									rowSet.updateBigDecimal("toDateReqAmt", toDateReqAmount);
									BigDecimal confirmAmt = FDCHelper.toBigDecimal(rowSet.getBigDecimal("confirmAmt"));
									rowSet.updateBigDecimal("notReqAmt", confirmAmt.subtract(toDateReqAmount));
								}
								
							}
							
						}
						
					}
				}
			}
			rowSet.beforeFirst();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
//        super.tblMain_tableClicked(e);
    }  

    public boolean destroyWindow() {    	
    	if(isChanged==true&&!isBtnOK){
    		if(FDCMsgBox.OK==FDCMsgBox.showConfirm2(this, "数据已经发生改变，确定取消已经修改的数据？")){
    			reverseEditData();
    		}else{
    			abort();
    		}
    	}
    	
    	isBtnOK = false;
		return super.destroyWindow();
	}

    protected void tblMain_editStopped(KDTEditEvent e) throws Exception {
    	isChanged = true;
    	if(e.getColIndex()==tblMain.getColumnIndex("isSelect")){
    		Object newValue = e.getValue();
    		Object oldValue = e.getOldValue();
    		if(e.getOldValue()!=null&&oldValue.equals(newValue))
				return;
    		IRow row = tblMain.getRow(e.getRowIndex());
    		
//    		isSelectChanged(newValue, row);
    	}
    	if(e.getColIndex()==tblMain.getColumnIndex("reqAmount")){
    		if(e.getOldValue()!=null&&e.getOldValue().equals(e.getValue()))
				return;
    		if(e.getValue()==null)
    			return;
    		if(FDCHelper.toBigDecimal(e.getOldValue()).compareTo(FDCHelper.toBigDecimal(e.getValue()))==0)
    			return;
    		IRow row = tblMain.getRow(e.getRowIndex());
    		BigDecimal reqAmount = FDCHelper.toBigDecimal(e.getValue());
    		BigDecimal confirmAmt = FDCHelper.toBigDecimal(row.getCell("confirmAmt").getValue());
    		BigDecimal paidAmt = FDCHelper.toBigDecimal(row.getCell("paidAmt").getValue());
    	
			if(reqAmount.add(paidAmt).compareTo(confirmAmt)>0){
				row.getCell("reqAmount").setValue(e.getOldValue());
				return;
    		}
    		
    		//add by david_yang PT048268 2011.04.07  查看材料确认单”时，所有材料确认单应当均能看到
			if(reqAmount.compareTo(FDCHelper.ZERO)<0&&reqAmount.add(paidAmt).compareTo(confirmAmt)<0){
				row.getCell("reqAmount").setValue(e.getOldValue());
				return;
			}
    		Map confirmEntryMap = null;
			if(editData!=null&&editData.containsKey("confirmEntryMap")){
				confirmEntryMap = (HashMap)editData.get("confirmEntryMap");
			}
			if(confirmEntryMap!=null){
	    		String confirmBillId = (String)row.getCell("id").getValue();
	    		PayRequestBillConfirmEntryInfo info = null;
	    		if(confirmEntryMap.containsKey(confirmBillId)){
	    			info = (PayRequestBillConfirmEntryInfo)confirmEntryMap.get(confirmBillId);
	    		}else{
	    			info = new PayRequestBillConfirmEntryInfo();
	    			MaterialConfirmBillInfo confirmBillInfo = new MaterialConfirmBillInfo();
	    			confirmBillInfo.setId(BOSUuid.read(confirmBillId));
	    			info.setConfirmBill(confirmBillInfo);
	    			info.put("notIncludeThisReqAmount", FDCHelper.toBigDecimal(row.getCell("toDateReqAmt").getValue()));
	    		}
	    		
	    		BigDecimal notIncludeThisReqAmount = FDCHelper.toBigDecimal(info.get("notIncludeThisReqAmount"));
	    	
    			if(confirmAmt.subtract(notIncludeThisReqAmount.add(reqAmount)).compareTo(FDCHelper.ZERO)<0){
	    			if(FDCMsgBox.YES!=FDCMsgBox.showConfirm2(this, "本确认单累计申请  大于  确认金额,是否继续？")){
	    				row.getCell("reqAmount").setValue(e.getOldValue());
	    				return;
	    			}
	    		}
	    		
	    		if(!confirmEntryMap.containsKey(confirmBillId)){
	    			editData.getConfirmEntry().add(info);
	    			confirmEntryMap.put(confirmBillId, info);
	    		}
	    		if(info!=null){
					info.setReqAmount(reqAmount);
				}
	    		
	    		row.getCell("toDateReqAmt").setValue(notIncludeThisReqAmount.add(reqAmount));
	    		
	    		row.getCell("notReqAmount").setValue(confirmAmt.subtract(notIncludeThisReqAmount.add(reqAmount)));
			}
    		
    	}
    	
	}

	private void isSelectChanged(Object newValue, IRow row) {
		ICell cell = row.getCell("reqAmount");
		if(newValue==Boolean.TRUE){
			BigDecimal paidAmt      = FDCHelper.toBigDecimal(row.getCell("paidAmt").getValue());
			BigDecimal confirmAmt   = FDCHelper.toBigDecimal(row.getCell("confirmAmt").getValue());
			BigDecimal notPiadAmt   = confirmAmt.subtract(paidAmt);
			BigDecimal notReqAmount = FDCHelper.toBigDecimal(row.getCell("notReqAmount").getValue());
			KDFormattedTextField amtTextField = new KDFormattedTextField(
					KDFormattedTextField.DECIMAL);
			amtTextField.setPrecision(2);
			amtTextField.setSupportedEmpty(false);
			
			//update by david_yang PT048268 2011.04.07  查看材料确认单”时，所有材料确认单应当均能看到
			if(notPiadAmt.compareTo(FDCHelper.ZERO)>=0){
				amtTextField.setNegatived(false);
				amtTextField.setMaximumValue(notPiadAmt);
			}
			if(notPiadAmt.compareTo(FDCHelper.ZERO)<0){
				amtTextField.setNegatived(true);
				amtTextField.setMinimumValue(notPiadAmt);
			}
			amtTextField.setHorizontalAlignment(KDFormattedTextField.RIGHT);
			ICellEditor amtEditor = new KDTDefaultCellEditor(amtTextField);
			cell.getStyleAttributes().setNumberFormat("#,###.00");
			cell.setEditor(amtEditor);
			cell.getStyleAttributes().setLocked(false);
			cell.setValue(notReqAmount);
			BigDecimal reqAmount = notReqAmount;
			
			Map confirmEntryMap = null;
			if(editData!=null&&editData.containsKey("confirmEntryMap")){
				confirmEntryMap = (HashMap)editData.get("confirmEntryMap");
			}
			if(confirmEntryMap!=null){
				String confirmBillId = (String)row.getCell("id").getValue();
				PayRequestBillConfirmEntryInfo info = null;
				if(confirmEntryMap.containsKey(confirmBillId)){
					info = (PayRequestBillConfirmEntryInfo)confirmEntryMap.get(confirmBillId);
				}else{
					info = new PayRequestBillConfirmEntryInfo();
					MaterialConfirmBillInfo confirmBillInfo = new MaterialConfirmBillInfo();
					confirmBillInfo.setId(BOSUuid.read(confirmBillId));
					info.setConfirmBill(confirmBillInfo);
					editData.getConfirmEntry().add(info);
					confirmEntryMap.put(confirmBillId, info);
					info.put("notIncludeThisReqAmount", FDCHelper.toBigDecimal(row.getCell("toDateReqAmt").getValue()));
				}
				
				if(info!=null){
					info.setReqAmount(reqAmount);
				}
			}
			row.getCell("notReqAmount").setValue(FDCHelper.ZERO);
			row.getCell("toDateReqAmt").setValue(confirmAmt);
		}
		else{
			cell.setValue(null);
			cell.getStyleAttributes().setLocked(true);
			BigDecimal confirmAmt   = FDCHelper.toBigDecimal(row.getCell("confirmAmt").getValue());
			Map confirmEntryMap = null;
			if(editData!=null&&editData.containsKey("confirmEntryMap")){
				confirmEntryMap = (HashMap)editData.get("confirmEntryMap");
			}
			String confirmBillId = (String)row.getCell("id").getValue();
			if(confirmEntryMap!=null){
				PayRequestBillConfirmEntryInfo info = (PayRequestBillConfirmEntryInfo)confirmEntryMap.get(confirmBillId);
				if(info!=null){
					editData.getConfirmEntry().remove(info);
					confirmEntryMap.remove(confirmBillId);
					BigDecimal notIncludeThisReqAmount = FDCHelper.toBigDecimal(info.get("notIncludeThisReqAmount"));
					row.getCell("notReqAmount").setValue(confirmAmt.subtract(notIncludeThisReqAmount));
					row.getCell("toDateReqAmt").setValue(notIncludeThisReqAmount);
				}
			}			
		}
	}

	protected void chkHasReqNotPaid_actionPerformed(ActionEvent e)
			throws Exception {
		actionRefresh_actionPerformed(e);
	}

    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
    	reverseEditData();
        super.actionRefresh_actionPerformed(e);
    }

	private void reverseEditData() {
		Map confirmEntryMap = null;
    	if(editData.containsKey("confirmEntryMap"))
    		confirmEntryMap = (HashMap)editData.get("confirmEntryMap");
    	else
    		confirmEntryMap = new HashMap();
    	confirmEntryMap.clear();
    	if(editData.containsKey("oldConfirmEntry"))
    	{
    		PayRequestBillConfirmEntryCollection oldConfirmEntry = (PayRequestBillConfirmEntryCollection)editData.get("oldConfirmEntry");
    		editData.getConfirmEntry().clear();
    		editData.getConfirmEntry().addCollection(oldConfirmEntry);
    	}
    	if(editData!=null&&editData.getConfirmEntry()!=null){
    		for(Iterator it = editData.getConfirmEntry().iterator();it.hasNext();){
    			PayRequestBillConfirmEntryInfo info = (PayRequestBillConfirmEntryInfo)it.next();
    			if(info.getConfirmBill()!=null)
    				confirmEntryMap.put(info.getConfirmBill().getId().toString(), info);
    		}
    	}
    	editData.put("confirmEntryMap", confirmEntryMap);
    	editData.put("HasReqNotPaid", Boolean.valueOf(this.chkHasReqNotPaid.isSelected()));
	}

    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionQuery.setVisible(false);
		FilterInfo filter = null;
		if(viewInfo.getFilter()==null)
		{
			filter = new FilterInfo();
			viewInfo.setFilter(filter);
		}else{
			filter = viewInfo.getFilter();
			filter.getFilterItems().clear();
		}
		if("VIEW".equals(this.getOprtState())||"FINDVIEW".equals(this.getOprtState())){
			if(editData.containsKey("confirmEntryMap")){
				Map confirmEntryMap = (HashMap)editData.get("confirmEntryMap");
				Set ids = new HashSet();
				ids.addAll(confirmEntryMap.keySet());
				if(confirmEntryMap.size()>0){
					filter.getFilterItems().add(new FilterItemInfo("id",ids,CompareType.INCLUDE));
					filter.setMaskString("#0");
				}
				else{
					filter.getFilterItems().add(new FilterItemInfo("id",null,CompareType.EQUALS));
					filter.setMaskString("#0");
				}
				
			}
		}else{
			//update by david_yang PT048268 2011.04.07  查看材料确认单”时，所有材料确认单应当均能看到
			filter.getFilterItems().add(new FilterItemInfo("notPayAmt",FDCHelper.ZERO,CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
			String contractBillId = (String)this.getUIContext().get("contractBillId");
			filter.getFilterItems().add(new FilterItemInfo("materialContractBill.id",contractBillId));
			int filterCounts = 3;
			if(this.chkHasReqNotPaid.isSelected()){
				filter.getFilterItems().add(new FilterItemInfo("notReqAmt",FDCHelper.ZERO,CompareType.EQUALS));
				filter.setMaskString("#0 and #1 and #2 and #"+filterCounts);
				filterCounts ++;
			}else{
				filter.setMaskString("#0 and #1 and #2 ");
			}
			if(editData.containsKey("confirmEntryMap")){
				Map confirmEntryMap = (HashMap)editData.get("confirmEntryMap");
				Set ids = new HashSet();
				ids.addAll(confirmEntryMap.keySet());
				if(confirmEntryMap.size()>0){
					filter.getFilterItems().add(new FilterItemInfo("id",ids,CompareType.INCLUDE));
					String maskString = filter.getMaskString();
					filter.setMaskString("("+maskString+") or #"+filterCounts);
					filterCounts++;
				}
			}
		}		
		
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	protected ICoreBase getBizInterface() throws Exception {
		return MaterialConfirmBillFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return MaterialConfirmBillEditUI.class.getName();
	}

	protected void btnCC_actionPerformed(ActionEvent e) throws Exception {
		this.destroyWindow();
	}

	protected void btnOK_actionPerformed(ActionEvent e) throws Exception {
		if (editData != null && editData.getConfirmEntry() != null) {
			BigDecimal sumReq = FDCHelper.ZERO;
			for (int i = 0; i < editData.getConfirmEntry().size(); i++) {
				sumReq = FDCHelper.add(sumReq, editData.getConfirmEntry().get(i).getReqAmount());
			}
			editUI.setConfirmAmts(sumReq);
			editUI.setConfirmBillEntryAndPrjAmt();
		}
		isBtnOK = true;
		this.destroyWindow();
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		uiContext.put("contractBillId", getSelectedContractId());		
	}
	protected String getSelectedContractId() {
		KDTSelectBlock selectBlock = getMainTable().getSelectManager().get();
		
	    if (selectBlock != null) {
	        int rowIndex = selectBlock.getTop();
	        IRow row = getMainTable().getRow(rowIndex);
	        if (row == null) 
	            return null;
	        
	        ICell cell = row.getCell("materialContractBill.id");
	
	        if (cell == null) {
	            MsgBox.showError(EASResource.getString(FrameWorkClientUtils.strResource + "Error_KeyField_Fail"));
	            SysUtil.abort();
	        }
	
	        Object keyValue = cell.getValue();
	
	        if (keyValue != null) {
	            return keyValue.toString();
	        }
	    }
	
	    return null;
	}
}