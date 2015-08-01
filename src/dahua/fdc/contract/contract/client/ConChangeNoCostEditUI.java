/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillEntryCollection;
import com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitInfo;
import com.kingdee.eas.fdc.contract.ConNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ConNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 变更拆分（非成本类）
 */
public class ConChangeNoCostEditUI extends AbstractConChangeNoCostEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ConChangeNoCostEditUI.class);
    
    /**
     * output class constructor
     */
    public ConChangeNoCostEditUI() throws Exception
    {
        super();
    }

    /**
     * 引入合同拆分
     */
    public void actionImpContrSplit_actionPerformed(ActionEvent e) throws Exception
    {
    	verfiyIsConNotSplit();
    	this.verifyConSplitNotAudit();
		checkBeforeImp();
        super.actionImpContrSplit_actionPerformed(e);
        setOneEntryAmt(txtAmount.getBigDecimalValue());
    }
    
    public void actionAcctSelect_actionPerformed(ActionEvent arg0)
			throws Exception {
		super.actionAcctSelect_actionPerformed(arg0);
		setOneEntryAmt(txtAmount.getBigDecimalValue());
	}
    
    /**
     * 描述：针对一个科目的情况增加自动填入变更金额的功能
     * 后续可能会抽象到基类中支持所有拆分
     * 
     * @param shouldSplitAmt:应拆金额
     */
	private void setOneEntryAmt(BigDecimal shouldSplitAmt) throws Exception {
		if (kdtEntrys.getRowCount() == 1) {
			KDTEditEvent event = new KDTEditEvent(kdtEntrys, null, null, 0,
					kdtEntrys.getColumnIndex("amount"), true, 1);
			final IRow row = kdtEntrys.getRow(0);
			row.getCell("amount")
					.setValue(shouldSplitAmt);
			event.setValue(shouldSplitAmt);
			kdtEntrys_editStopped(event);
		}
	}
    protected ICoreBase getBizInterface() throws Exception {
    	return ConChangeNoCostSplitFactory.getRemoteInstance();
    }
    
    protected IObjectValue createNewData() {
    	ConChangeNoCostSplitInfo objectValue = new ConChangeNoCostSplitInfo();
    	String contractChange = (String)this.getUIContext().get("costBillID");
    	ContractChangeBillInfo contractChangeInfo=null;
    	        
        SelectorItemCollection selectors = new SelectorItemCollection();
		//selectors.add("*");
        selectors.add("id");
		selectors.add("number");
		selectors.add("name");
		selectors.add("amount");
		selectors.add("balanceAmount");
		selectors.add("hasSettled");
		selectors.add("contractBill.id");
		selectors.add("contractBill.number");
		selectors.add("contractBill.name");
		selectors.add("curProject.id");
		selectors.add("curProject.longNumber");
        if(contractChange != null){
	        try {
	        	contractChangeInfo = ContractChangeBillFactory.getRemoteInstance().getContractChangeBillInfo(new ObjectUuidPK(BOSUuid.read(contractChange)),selectors);
			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			}
			objectValue.setContractChange(contractChangeInfo);
			if(contractChangeInfo.getContractBill()!=null)
				objectValue.setContractBill(contractChangeInfo.getContractBill());
			if(contractChangeInfo.getCurProject()!=null)
				objectValue.setCurProject(contractChangeInfo.getCurProject());
			this.txtCostBillNumber.setText(contractChangeInfo.getNumber());
			this.txtCostBillName.setText(contractChangeInfo.getName());
			this.txtSplitedAmount.setValue(FDCHelper.ZERO);
			this.txtAmount.setValue(getChangeAmount(contractChangeInfo));
        }
		objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
		objectValue.setCreateTime(new Timestamp(System.currentTimeMillis()));
		objectValue.setIsInvalid(false);
    	return objectValue;
    }
    
    protected KDTable getDetailTable() {
        return this.kdtEntrys;
	}
    
    protected IObjectValue createNewDetailData(KDTable table) {
    	return new ConChangeNoCostSplitEntryInfo();
    }
    
    public void onLoad() throws Exception {
		/* TODO 自动生成方法存根 */
		super.onLoad();
		initWorkButton();
		setSplitBillType(CostSplitBillTypeEnum.CNTRCHANGESPLIT);
	}
    
    public void loadFields() {
    	super.loadFields();
    	String billID = null;
		if(this.editData.getContractChange() != null){
			String contractChange = this.editData.getContractChange().getId().toString();
			SelectorItemCollection selectors = new SelectorItemCollection();
				//selectors.add("*");
		    selectors.add("id");
		    selectors.add("contractBill.id");
		    try {
		        //ContractChangeBillInfo info = ContractChangeBillFactory.getRemoteInstance().getContractChangeBillInfo(new ObjectUuidPK(BOSUuid.read(contractChange)),selectors);
		    	ContractChangeBillInfo info = ContractChangeBillFactory.getRemoteInstance().getContractChangeBillInfo(new ObjectUuidPK(BOSUuid.read(contractChange)));
		    	billID = info.getContractBill().getId().toString();
		        this.txtCostBillNumber.setText(info.getNumber());
				this.txtCostBillName.setText(info.getName());				
				this.txtAmount.setValue(getChangeAmount(info));
				//金额变化后更新未拆分金额  by sxhong 2009-06-05 11:08:09
				DataChangeEvent event=new DataChangeEvent(txtSplitedAmount);
				event.setNewValue(txtSplitedAmount.getBigDecimalValue());
				event.setOldValue(null);
				txtSplitedAmount_dataChanged(event);
			} catch (EASBizException e) {
				handUIExceptionAndAbort(e);
			} catch (BOSException e) {
				handUIExceptionAndAbort(e);
			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			}
		}
		setContractBillId(billID);
		setDisplay();
    }
    
    protected void initWorkButton() {
    	super.initWorkButton();
    	actionImpContrSplit.setVisible(true);
    	actionImpContrSplit.setEnabled(true);
    }
    
    protected String getSplitBillEntryClassName() {
    	return ConChangeNoCostSplitEntryInfo.class.getName();
    }
    
    /**
     * 检查对应的合同是否已经拆分，如果没有拆分，给出错误提示：对应的合同还未进行拆分，不能进行此操作！
     * @Modified By Owen_wen 添加方法注释，重命名了方法名以及改用exists方式判断。
     * @throws Exception
     */
    protected void verfiyIsConNotSplit() throws Exception{
		FilterInfo filter = new FilterInfo();	
		String filterField = "Parent.contractBill.id";
    	filter.getFilterItems().add(new FilterItemInfo(filterField, getContractBillId()));
    	filter.getFilterItems().add(new FilterItemInfo("parent.state",FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));
    	boolean isExistConSplit = ConNoCostSplitEntryFactory.getRemoteInstance().exists(filter);
    	if (!isExistConSplit){
    		MsgBox.showError(this,FDCSplitClientHelper.getRes("conNotSplit"));
			SysUtil.abort();
    	}
	}
    
    /**
     * 检查对应的合同拆分是否已经审批，如果没有审批，
     * 根据参数FDC5014_NEXTSPLITISBASEONPREAUDITED决定是否要给出提示
     * @author owen_wen 2010-11-29
     * @throws BOSException 
     * @throws EASBizException 
     */
    private void verifyConSplitNotAudit() throws EASBizException, BOSException{
		FilterInfo filter = new FilterInfo();	
    	filter.getFilterItems().add(new FilterItemInfo("contractBill.id", getContractBillId()));
    	filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));
    	filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE,CompareType.NOTEQUALS));
    	boolean isConSplitNotAudited = ConNoCostSplitFactory.getRemoteInstance().exists(filter);
    	if (isConSplitNotAudited){
    		boolean paramValue = FDCUtils.getBooleanValue4FDCParamByKey(null, SysContext.getSysContext().getCurrentOrgUnit().getId().toString(),
    				FDCConstants.FDC_PARAM_FDC5014_NEXTSPLITISBASEONPREAUDITED);
    		if (paramValue){
	    		MsgBox.showInfo(this,FDCSplitClientHelper.getRes("conSplitNotAudit"));
				SysUtil.abort();
    		}
    	}
    }

	protected void checkBeforeImp() throws Exception{
		if(this.kdtEntrys.getRowCount3() == 0){
			return;
		}
		FDCNoCostSplitBillEntryInfo entry = null;
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			entry = (FDCNoCostSplitBillEntryInfo) kdtEntrys.getRow(i).getUserObject();
			FDCNoCostSplitBillEntryCollection test = getCostSplitEntryCollection(CostSplitBillTypeEnum.CONTRACTSPLIT);
			FDCNoCostSplitBillEntryInfo entrytemp = null;
			for (Iterator iter = test.iterator(); iter.hasNext();) {
				entrytemp = (FDCNoCostSplitBillEntryInfo) iter.next();
				if (entry.getAccountView().getId().equals(entrytemp.getAccountView().getId())) {
					MsgBox.showError(this, entry.getAccountView().getName()
							+ FDCSplitClientHelper.getRes("cannotImp"));
					SysUtil.abort();
				}
			}
		}
	}   

	public void updateDetailTable(IObjectCollection entrys){
    	getDetailTable().removeRows(false);
    	IRow row;
    	FDCNoCostSplitBillEntryInfo entry;
    	for(Iterator iter=entrys.iterator();iter.hasNext();){
    		entry=(FDCNoCostSplitBillEntryInfo)iter.next();
    		row=getDetailTable().addRow();
    		loadLineFields(getDetailTable(), row, entry);
    	}
    }
    
    public void updateAmountColumn(IObjectCollection entrys){
//    	getDetailTable().removeRows(false);
    	IRow row;
    	FDCNoCostSplitBillEntryInfo entry;
    	int rowIndex=0;
    	for(Iterator iter=entrys.iterator();iter.hasNext();rowIndex++){
    		entry=(FDCNoCostSplitBillEntryInfo)iter.next();
    		row=getDetailTable().getRow(rowIndex);
    		row.getCell("amount").setValue(entry.getAmount());
    		row.getCell("apportionValue").setValue(entry.getApportionValue());
    		row.getCell("apportionValueTotal").setValue(entry.getApportionValueTotal());
    		row.getCell("directAmountTotal").setValue(entry.getDirectAmountTotal());
    		row.getCell("directAmount").setValue(entry.getDirectAmount());
    		row.getCell("otherRatioTotal").setValue(entry.getOtherRatioTotal());
//    		row.getCell("otherRatio").setValue(entry.getOtherRatio());
    	}
    }
    
    public SelectorItemCollection getSelectors() {
    	SelectorItemCollection sic=super.getSelectors();		
		//sic.add(new SelectorItemInfo("paymentBill.contractBillId"));
		sic.add("state");
		return setSelectors(sic);
    }
    
    protected void updateButtonStatus() {
    	super.updateButtonStatus();
		actionImpContrSplit.setVisible(true);
		actionImpContrSplit.setEnabled(true);
    	if (!SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit()) {
    		actionImpContrSplit.setEnabled(false);
    	}
    }
    
	private BigDecimal getChangeAmount(ContractChangeBillInfo info){
		if(info.isHasSettled()){
			return info.getBalanceAmount();
		}else{
			return info.getAmount();
		}
	}
	
	protected void getAttachMentItem(KDTable table){
		if (table==null)
			return;
		//		KDTMenuManager tm= this.getMenuManager(table);
		//		if (tm==null)
		//			tm = new UIPopupManager(this,table);
		KDMenuItem item = new KDMenuItem();
		item.setName("menuItemAttachment");
		item.setAction(new ActionAttachMent());
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeRemove();
		super.actionRemove_actionPerformed(e);
	}
    private void checkBeforeRemove() throws Exception {
    	if (editData == null || editData.getId() == null || editData.getId().toString().equals("")) {
			return;
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", editData.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		if (getBizInterface().exists(filter)) {
			MsgBox.showWarning(FDCSplitClientHelper.getRes("listRemove"));
			SysUtil.abort();
		}
    }
	public  KDMenuItem getAttachMenuItem( KDTable table )
	{
//		if (getTableForCommon()!=null||getDetailTable()!=null)
//		{
//		KDMenuItem item = new KDMenuItem();
//		item.setName("menuItemAttachment");
//		item.setAction(new ActionAttachMent());
//		return item;
//		}
		return null;
		
	}
//	分录附件
	class ActionAttachMent extends ItemAction {
		public ActionAttachMent() {
//			String _tempStr =EASResource.getString(FrameWorkClientUtils.strResource +"SubAttachMentText");
//			String _tempStr =EASResource.getString(FrameWorkClientUtils.strResource);
//			this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
//			this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
//			this.putValue(ItemAction.NAME, _tempStr);
		}
//		public void actionPerformed(ActionEvent e) {
//		showSubAttacheMent(null);
//		}
	}
	protected void loadData() throws Exception {
		handleSplitWorkFlow();
		super.loadData();
	}
	/**
	 *  变更签证确认拆分工作流处理方法,达到变更签证确认拆分不与工作流的配置相关，只要传放合同ID，即可以正常拆分 by Cassiel_peng 2009-9-19
	 */
	private void handleSplitWorkFlow() {
		//重载处理工作流问题，将工作流的参数进行转化
        Boolean isFromWorkflow =(Boolean)getUIContext().get("isFromWorkflow");
        if(isFromWorkflow!=null&&isFromWorkflow.booleanValue()){
        	if(this.getUIContext().get(UIContext.ID)!=null){
        		String costBillID=this.getUIContext().get(UIContext.ID).toString();
        		getUIContext().remove(UIContext.ID);
        		getUIContext().put("costBillID",costBillID);
        		setOprtState(OprtState.ADDNEW);
        		//如果合同已经拆分还要再次拆分的话则在原有的基础上进行拆分
        		FDCSQLBuilder builder=new FDCSQLBuilder();
        		builder.appendSql("select top 1 fid from T_CON_ConChangeNoCostSplit where FContractChangeID=? and fstate<>?");
        		builder.addParam(costBillID);
        		builder.addParam(FDCBillStateEnum.INVALID_VALUE);
        		try{
        			IRowSet rowSet=builder.executeQuery();
        			if(rowSet.next()){
        				String splitId=rowSet.getString("fid");
        				this.getUIContext().put(UIContext.ID,splitId);
        				setOprtState(OprtState.EDIT);
        			}
        		}catch (Exception e) {
					logger.error(e.getMessage(), e);
					handUIExceptionAndAbort(e);
				}
        		
        		logger.info("conChangeBill costbill id="+costBillID);
        	}else{
        		logger.error("工作流中取不到变更签证确认ID，can't get conChangeBill id");
        	}
        }
	}
}