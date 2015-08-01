package com.kingdee.eas.fdc.contract.client;

import java.util.Iterator;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.contract.ConNoCostSplitCollection;
import com.kingdee.eas.fdc.contract.ConNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConNoCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 拆分调用抽象策略类
 * @author liupd
 *
 */
public abstract class AbstractSplitInvokeStrategy {
	
	public static final String CONTRACT_BILL_ID = "contractBill.id";

	/**
	 * 源单据ID
	 */
	private String billId;
	
	/**
	 * 父UI（源单据EditUI）实例
	 */
	private CoreUIObject parentUI;
	
	private final static AbstractSplitInvokeStrategy emptySplitInvokeStrategy=new AbstractSplitInvokeStrategy("empty",null){
		protected String getBillPropName() {
			return null;
		}
		public void invokeSplit() throws Exception {
		}
		protected ICoreBase getBizInterface() throws Exception {
			return null;
		}
		protected String getSplitEditUIName() throws Exception {
			return null;
		}
		
	};
	
	public static AbstractSplitInvokeStrategy getEmptySplitInvokeStrategy(){
		return emptySplitInvokeStrategy;
	}
	
	public AbstractSplitInvokeStrategy(String billId, CoreUIObject parentUI) {
		super();
		this.billId = billId;
		this.parentUI = parentUI;
	}

	
//	public AbstractSplitInvokeStrategy(String billId, CoreUIObject parentUI, boolean isCostSplit) {
//		super();
//		this.billId = billId;
//		this.parentUI = parentUI;
//		this.isCostSplit = isCostSplit;
//	}


	protected EntityViewInfo getView() {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(getBillPropName(), billId));
    	view.setFilter(filter);
    	view.getSelector().add("id");
    	view.getSelector().add("state");
    	view.getSelector().add("paymentBill.state");
    	view.getSelector().add("paymentBill.fiVouchered");
    	
    	return view;
	}
	
	abstract protected String getBillPropName();
	protected CoreBaseCollection getColl() throws Exception {
		CoreBaseCollection coll = getBizInterface().getCollection(getView());
		return coll;
	}
	
	abstract protected ICoreBase getBizInterface() throws Exception;
	
	public void invokeSplit() throws Exception {
		
		checkBeforeInvoke();
		
		CoreBaseCollection coll = getColl();
		boolean isSplited=false;
    	boolean isAudited=false;
    	boolean isVouchered = false;
    	FDCBillInfo billInfo=null;
    	String splitBillID=null;
		if(coll.size()>0){
    		billInfo=(FDCBillInfo)coll.get(0);
    		splitBillID=billInfo.getId().toString();
    		isSplited=true;
    		
    		if(billInfo.getState().equals(FDCBillStateEnum.AUDITTED)){
    			isAudited=true;
    		}
    		//生成凭证后只能查看
    		PaymentBillInfo payInfo = (PaymentBillInfo)billInfo.get("paymentBill");
    		if(payInfo!=null&&payInfo.isFiVouchered()){
    			isVouchered=true;
    		}
    	}

        UIContext uiContext = new UIContext(parentUI);
    	String oprtState;
    	
    	if(isSplited){
            uiContext.put(UIContext.ID, splitBillID);
    		
    		if(isAudited){
        		oprtState=OprtState.VIEW;    			
    		}else if(isVouchered){
        		oprtState=OprtState.EDIT;    			
    		}else{
    			oprtState=OprtState.EDIT;
    		}
    	}else{
			uiContext.put("costBillID", billId);
    		oprtState=OprtState.ADDNEW;
    	}

        //测试提BUG:付款单审批后进入付款拆分，现在实现形式为窗口形式，但点击付款拆分界面的“查看付款单”和“查看合同”后，付款单和合同均以页签形式显示，付款拆分窗口无法最小化，导致付款单和合同无法查看，建议付款拆分界面也改为页签形式或者可以最小化
    	//实际上系统中的拆分界面都有类似此类BUG，现修改窗口模式，使其可以最小化，解决BUG  by cassiel_peng 2010-03-23
		IUIWindow uiWin = UIFactory.createUIFactory(UIFactoryName.NEWWIN).create(
				getSplitEditUIName(), uiContext , null , oprtState);
        
		uiWin.show();
	}
	
	abstract protected String getSplitEditUIName() throws Exception;

	public String getBillId() {
		return billId;
	}


	public CoreUIObject getParentUI() {
		return parentUI;
	}




	/**
	 * 调用前的检查
	 * @throws Exception
	 */
	protected void checkBeforeInvoke() throws Exception {	
		
		
	}

	/**
	 * 检查关联的合同是否完全拆分
	 * @throws Exception
	 */
	protected void checkIsContractAllCostSplit() throws Exception {
		if(!isContractAllCostSplit()) {
			MsgBox.showWarning(getParentUI(),FDCSplitClientHelper.getRes("conNotSplited"));
			SysUtil.abort();
		}
	}

	protected void checkIsContractAllNoCostSplit() throws Exception {
		if(!isContractAllNoCostSplit()) {
			MsgBox.showWarning(getParentUI(),FDCSplitClientHelper.getRes("conNotSplited"));
			SysUtil.abort();
		}
	}

	protected boolean isContractAllCostSplit() throws Exception {
		String contractBillId = getContractBillId();
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractBill",contractBillId));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));
		view.setFilter(filter);	
		view.getSelector().add("splitState");
		
		ContractCostSplitCollection contcoll = ContractCostSplitFactory.getRemoteInstance().getContractCostSplitCollection(view);
		Iterator iter = contcoll.iterator(); 
		if(iter.hasNext()){
			ContractCostSplitInfo cont = (ContractCostSplitInfo)iter.next();
			if(cont.getSplitState() == CostSplitStateEnum.ALLSPLIT){
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
		
	}
	
	protected boolean isContractAllNoCostSplit() throws Exception {
		String contractBillId = getContractBillId();
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractBill",contractBillId));
		view.setFilter(filter);	
		view.getSelector().add("splitState");
		
		ConNoCostSplitCollection contcoll = ConNoCostSplitFactory.getRemoteInstance().getConNoCostSplitCollection(view);
		Iterator iter = contcoll.iterator(); 
		if(iter.hasNext()){
			ConNoCostSplitInfo cont = (ConNoCostSplitInfo)iter.next();
			if(cont.getSplitState() == CostSplitStateEnum.ALLSPLIT){
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
		
	}
	/**
	 * 获取关联的合同ID
	 * @return
	 * @throws Exception
	 */
	protected String getContractBillId() throws Exception {
		return null;
	}

	/**
	 * 检查是否成本拆分，如果不是成本拆分，提示不能成本拆分
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws Exception
	 */
	protected void checkIsCostSplit() throws Exception {
		/*
		 * 所有单据都有成本与非成本拆分，此检查可以不进行
		 * by sxhong 2007-12-14	
		 */
		if(true) return ;
		if(!isContractCostSplit()) {
			MsgBox.showWarning(getParentUI(),FDCSplitClientHelper.getRes("conNoSplit"));
			SysUtil.abort();
		}
	}


	protected boolean isContractCostSplit() throws Exception {
		String contractId = getContractBillId();
		BOSObjectType objectType = BOSUuid.read(contractId).getType();
		
		boolean isCostSplit = false;
		if(objectType.equals(new ContractBillInfo().getBOSType())) {
			SelectorItemCollection selectors =new SelectorItemCollection();
			selectors.add("isCoseSplit");
			ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectStringPK(contractId), selectors);
			isCostSplit = contractBillInfo.isIsCoseSplit();
		}
		else {
			SelectorItemCollection selectors =new SelectorItemCollection();
			selectors.add("isCostSplit");
			ContractWithoutTextInfo contractWithoutTextInfo = ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextInfo(new ObjectStringPK(contractId), selectors);
			isCostSplit = contractWithoutTextInfo.isIsCostSplit();
		}
		
		return isCostSplit;
	}
	
	/**
	 * 检查是否单独计算，如果不是单独计算，则提示不能拆分
	 * @throws Exception
	 */
	protected void checkIsAmtWithoutCost() throws Exception {
		
		SelectorItemCollection selectors = new SelectorItemCollection();
		
		selectors.add("isAmtWithoutCost");
		
		ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectStringPK(getContractBillId()), selectors);
		
		if(contractBillInfo.isIsAmtWithoutCost()) {
			MsgBox.showWarning(getParentUI(),FDCSplitClientHelper.getRes("amtWithOutCostNotSplit"));
			SysUtil.abort();
		}
	}
}
