/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.contract.ConNoCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ConNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConNoCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class ConNoCostSplitEditUI extends AbstractConNoCostSplitEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ConNoCostSplitEditUI.class);
    
    /**
     * output class constructor
     */
    public ConNoCostSplitEditUI() throws Exception
    {
    	super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
    	super.storeFields();
    }

    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
    	//处理分录保存顺序
    	/*ConNoCostSplitEntryInfo entry=null;    	
        for(int i=0; i<kdtEntrys.getRowCount(); i++){
    		entry = (ConNoCostSplitEntryInfo)kdtEntrys.getRow(i).getUserObject();
        	entry.setIndex(i+1);
        }*/
//    	getDetailTable().setUserObject(editData.getEntrys());
    	super.actionSave_actionPerformed(e);
    }
    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception {
    	checkConInWF();
		super.actionRemoveLine_actionPerformed(e);
	}
    private void checkConInWF() throws EASBizException, BOSException {
    	//是否必须审核前拆分
		boolean splitBeforeAudit = FDCUtils.getBooleanValue4FDCParamByKey(null, editData.getContractBill().getId().toString(),
				FDCConstants.FDC_PARAM_SPLITBFAUDIT);
		if (splitBeforeAudit && FDCUtils.isRunningWorkflow(editData.getContractBill().getId().toString())
				&& CostSplitStateEnum.ALLSPLIT.equals(editData.getSplitState())) {
			MsgBox.showWarning(this, "合同在工作流，不能删除合同拆分！");
			SysUtil.abort();
		}
    }
  
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	checkConInWF();
    	checkBeforeRemove();
        super.actionRemove_actionPerformed(e);
    }
    private void checkBeforeRemove() throws Exception {
    	if (editData == null || editData.getId() == null || editData.getId().toString().equals("")) {
    		return;
    	}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", editData.getId().toString()));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("state",
								FDCBillStateEnum.AUDITTED_VALUE));
		if (getBizInterface().exists(filter)) {
			MsgBox.showWarning(FDCSplitClientHelper.getRes("listRemove"));
			SysUtil.abort();
		}
    }
 
    public void actionImpContrSplit_actionPerformed(ActionEvent e) throws Exception
    {
    }

    //------------------------------------------------------------
    

	/**
	 * output getEditUIName method
	 */
	protected String getEditUIName() {
		return com.kingdee.eas.fdc.contract.client.ConNoCostSplitEditUI.class.getName();
	}
		
	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
//		return ConNoCostSplitFactory.getRemoteInstance();
		return ConNoCostSplitFactory.getRemoteInstance();
	}

	/**
	 * output getDetailTable method
	 */
	protected KDTable getDetailTable() {
        return this.kdtEntrys;
	}

	/**
	 * output createNewDetailData method
	 */
	protected IObjectValue createNewDetailData(KDTable table) {
		//return null;
		
		return new com.kingdee.eas.fdc.contract.ConNoCostSplitEntryInfo();
	}
	

	/* （非 Javadoc）
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#createNewData()
	 */
	protected IObjectValue createNewData() {
		// TODO 自动生成方法存根
		//return super.createNewData();
				
		ConNoCostSplitInfo objectValue=new ConNoCostSplitInfo();
		
        //objectValue.setCompany((com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentFIUnit()));
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
        
        String costBillID;
        costBillID = (String)getUIContext().get("costBillID");
        
        // modified by zhaoqin for R130617-0191 on 2013/07/31 start
		// 如果是通过工作流，则拿不到 costBillID, 改为取流程变量 billId
        Boolean isFromWorkflow = (Boolean) getUIContext().get("isFromWorkflow");
		if (StringUtils.isEmpty(costBillID) && null != isFromWorkflow && isFromWorkflow.booleanValue()) {
			costBillID = ((BOSUuid) getUIContext().get("billId")).toString();
			getUIContext().remove(UIContext.ID);
		}
		// modified by zhaoqin for R130617-0191 on 2013/07/31 end
        
        ContractBillInfo costBillInfo=null;
              
        SelectorItemCollection selectors = new SelectorItemCollection();
		//selectors.add("*");
        selectors.add("id");
		selectors.add("number");
		selectors.add("name");
		selectors.add("amount");
		selectors.add("orgUnit.id");
		selectors.add("curProject.id");
		selectors.add("curProject.longNumber");
		selectors.add("orgUnit.id");
        try {
        	costBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(costBillID)),selectors);
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
			
		}
//		if(costBillInfo.getCurProject()!=null)
//			objectValue.setCurProject(costBillInfo.getCurProject());
    	objectValue.setContractBill(costBillInfo);
        txtCostBillNumber.setText(costBillInfo.getNumber());
        //txtCostBillName.setText(costBillInfo());    	
        txtAmount.setValue(costBillInfo.getAmount());
        objectValue.setIsInvalid(false);
		setContractBillId(costBillInfo.getId().toString());
		objectValue.setIsConfirm(true);    
        return objectValue;
	}

	public void onLoad() throws Exception {
		/* TODO 自动生成方法存根 */
		super.onLoad();
		initWorkButton();
//		tHelper.getDisabledTables().add(getDetailTable());
		setSplitBillType(CostSplitBillTypeEnum.CONTRACTSPLIT);
		
		//是否必须审核前拆分
		boolean splitBeforeAudit = FDCUtils.getBooleanValue4FDCParamByKey(null, editData.getContractBill().getId().toString(),
				FDCConstants.FDC_PARAM_SPLITBFAUDIT);
		String id = "";
		if(editData.getContractBill()!=null){
			id = editData.getContractBill().getId().toString();
		}
		
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("state"));
        sic.add(new SelectorItemInfo("isAmtWithoutCost"));
        sic.add(new SelectorItemInfo("isCoseSplit"));
        
        //说明补丁有一条内容确认有问题.如果不进入成本的，或者进入成本不是单独核算，按钮应该是亮
		ContractBillInfo contractBillInfo = ContractBillFactory
			.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(id)),sic);
		if(splitBeforeAudit && !FDCBillStateEnum.SUBMITTED.equals(contractBillInfo.getState())
		 && contractBillInfo.isIsCoseSplit() &&  !contractBillInfo.isIsAmtWithoutCost()		
		){
			super.setOprtState(OprtState.VIEW); 
		}
	}
	
	/**
	 * 
	 * 描述：返回编码控件（子类必须实现）
	 * @return
	 * @author:liupd
	 * 创建时间：2006-10-13 <p>
	 */
	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	
	public void setOprtState(String oprtType) {
			super.setOprtState(oprtType);
	}


	/* （非 Javadoc）
	 * @see com.kingdee.eas.fdc.basedata.client.AbstractFDCSplitBillEditUI#loadFields()
	 */
	public void loadFields() {
		// TODO 自动生成方法存根
		super.loadFields();
		
		//setContractBillId(editData.getContractBill().toString());bug....
		setContractBillId(editData.getContractBill().getId().toString());
		setDisplay();
	}

	/* （非 Javadoc）
	 * @see com.kingdee.eas.fdc.finance.client.AbstractConNoCostSplitEditUI#getSelectors()
	 */
	public SelectorItemCollection getSelectors() {
		// TODO 自动生成方法存根
		//return super.getSelectors();
		
		SelectorItemCollection sic=super.getSelectors();
		
		//sic.add(new SelectorItemInfo("paymentBill.contractBillId"));
		sic.add("state");
		sic.add(new SelectorItemInfo("contractBill.state"));
		sic.add(new SelectorItemInfo("contractBill.orgUnit.id"));
		return setSelectors(sic);
	}

	protected String getSplitBillEntryClassName() {
		
		return ConNoCostSplitEntryInfo.class.getName();
	}

	protected void initWorkButton() {
		// TODO 自动生成方法存根
		super.initWorkButton();
		
		btnImpContrSplit.setVisible(false);
		actionImpContrSplit.setEnabled(false);
	}
    public void updateDetailTable(IObjectCollection entrys){
    	getDetailTable().removeRows(false);
    	IRow row;
    	FDCSplitBillEntryInfo entry;
    	for(Iterator iter=entrys.iterator();iter.hasNext();){
    		entry=(FDCSplitBillEntryInfo)iter.next();
    		row=getDetailTable().addRow();
    		loadLineFields(getDetailTable(), row, entry);
    	}
    }
    
    public void updateAmountColumn(IObjectCollection entrys){
//    	getDetailTable().removeRows(false);
    	IRow row;
    	FDCSplitBillEntryInfo entry;
    	int rowIndex=0;
    	for(Iterator iter=entrys.iterator();iter.hasNext();rowIndex++){
    		entry=(FDCSplitBillEntryInfo)iter.next();
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

    protected void updateButtonStatus() {
    	super.updateButtonStatus();
    	if (!SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit()) {
    		actionImpContrSplit.setEnabled(false);
    	}
    }
    
    protected void getAttachMentItem(KDTable table){
    	if (table==null)
    		return;
		//    	KDTMenuManager tm= this.getMenuManager(table);
		//    	if (tm==null)
		//    	   tm = new UIPopupManager(this,table);
        KDMenuItem item = new KDMenuItem();
        item.setName("menuItemAttachment");
        item.setAction(new ActionAttachMent());
//        tm.getMenu().addSeparator();
//        tm.getMenu().add(item);
    }
    
    public  KDMenuItem getAttachMenuItem( KDTable table )
    {
//    	if (getTableForCommon()!=null||getDetailTable()!=null)
//    	{
//    		KDMenuItem item = new KDMenuItem();
//            item.setName("menuItemAttachment");
//            item.setAction(new ActionAttachMent());
//            return item;
//    	}
    	return null;

    }
//  分录附件
    class ActionAttachMent extends ItemAction {
    	public ActionAttachMent() {
//            String _tempStr =EASResource.getString(FrameWorkClientUtils.strResource +"SubAttachMentText");
//            String _tempStr =EASResource.getString(FrameWorkClientUtils.strResource);
//            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
//            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
//            this.putValue(ItemAction.NAME, _tempStr);
         }
//        public void actionPerformed(ActionEvent e) {
//        	showSubAttacheMent(null);
//        }
    }
}