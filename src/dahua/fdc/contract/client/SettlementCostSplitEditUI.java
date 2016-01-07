/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.Action;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryCollection;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.basedata.client.ViewCostInfoUI;
import com.kingdee.eas.fdc.contract.ConChangeSplitCollection;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitInfo;
import com.kingdee.eas.fdc.contract.ConNoCostSplitCollection;
import com.kingdee.eas.fdc.contract.ConNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConNoCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 结算单拆分 编辑界面
 */
public class SettlementCostSplitEditUI extends
		AbstractSettlementCostSplitEditUI {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = CoreUIObject.getLogger(SettlementCostSplitEditUI.class);

	private boolean hasDirectAmt = false;

	//by tim_gao 参数
	private  Map params = null;
	
	public Map getParams() {
		return params;
	}

	public void setParams(Map params) {
		this.params = params;
	}

	
	private void initParam() throws EASBizException, BOSException{
		// modified by zhaoqin on 2013/10/25, start
		// 取结算单对应的组织id，如果为空，则取当前组织id
		String comPK = null;
		if(null != this.editData && null != this.editData.getCurProject()
				&& null != this.editData.getCurProject().getCostCenter()) {
			comPK = this.editData.getCurProject().getCostCenter().getId().toString();
		} else {
			comPK=SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		}
		// modified by zhaoqin on 2013/10/25, end
		
		params= new HashMap();
		params.put(FDCConstants.FDC_PARAM_NOSETTLEMENTSPLITACCTCTRL, comPK);
        params.put(FDCConstants.FDC_PARAM_IMPORTCONSPLIT, comPK);
        params = ParamControlFactory.getRemoteInstance().getParamHashMap((HashMap)params);
        
//        this.kdtEntrys.getColumn("programming").getStyleAttributes().setHided(true);
	}
	
	/**
	 * output class constructor
	 */
	public SettlementCostSplitEditUI() throws Exception {
		super();
	}

	public void loadFields() {
		super.loadFields();
		setDisplay();
		setAmtDisplay();
		isMeasure=this.editData.getContractBill().isIsMeasureContract();
		
		try {
			updateEntryProgramming();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 结算工作流处理方法,达到结算拆分不与工作流的配置相关，只要传放结算ID，即可以正常拆分，参照合同拆分实现
	 */
	private void handleSplitWorkFlow() {
		//重载处理工作流问题，将工作流的参数进行转化
        Boolean isFromWorkflow =(Boolean)getUIContext().get("isFromWorkflow");
        if(isFromWorkflow!=null&&isFromWorkflow.booleanValue()){
        	if(this.getUIContext().get(UIContext.ID)!=null){
        		String costBillID=this.getUIContext().get(UIContext.ID).toString();
        		if (!BOSUuid.read(costBillID).getType().equals(new ContractSettlementBillInfo().getBOSType())) {
					return;
				}
        		//根据costBillID得到contractBill.id,isCostSplit
    			SelectorItemCollection selector = new SelectorItemCollection();
    			selector.add("contractBill.id");
    			selector.add("isCostSplit");
    			ContractSettlementBillInfo settleInfo = null;
    			try{
	    			settleInfo = ContractSettlementBillFactory
	    					.getRemoteInstance().getContractSettlementBillInfo(
	    							new ObjectUuidPK(costBillID), selector);
	       		
	            	//结算拆分之前需要判断合同及变更是否已拆分
	            	checkContract(settleInfo.getContractBill().getId().toString(),settleInfo.isIsCostSplit());
	            	//修改结算拆分之前需要判断是否被付款拆分引用
	        		checkImp(settleInfo.getContractBill().getId().toString());
    			}catch(com.kingdee.eas.util.AbortException e) {
					logger.error(e.getMessage(), e);
					handUIExceptionAndAbort(e);
				}catch (Exception e) {
					logger.error(e.getMessage(), e);
					handUIExceptionAndAbort(e);
				}
        		getUIContext().remove(UIContext.ID);
        		getUIContext().put("costBillID",costBillID);
        		setOprtState(OprtState.ADDNEW);
        		//如果结算已经拆分还要再次拆分的话则在原有的基础上进行拆分
        		FDCSQLBuilder builder=new FDCSQLBuilder();
        		builder.appendSql("select top 1 fid from T_Con_SettlementCostSplit where fsettlementbillid=? and fstate<>?");
        		builder.appendSql(" union ");
        		builder.appendSql("select top 1 fid from t_Con_Settnocostsplit where fsettlementbillid=? and fstate<>?");
        		builder.addParam(costBillID);
        		builder.addParam(FDCBillStateEnum.INVALID_VALUE);
        		builder.addParam(costBillID);
        		builder.addParam(FDCBillStateEnum.INVALID_VALUE);		
        		try{
        			IRowSet rowSet=builder.executeQuery();
        			if(rowSet.next()){
        				String splitId=rowSet.getString("fid");
        				this.getUIContext().put(UIContext.ID,splitId);
        				setOprtState(OprtState.EDIT);
        			}else if(settleInfo.isIsCostSplit()) {
                		FDCSplitClientHelper.autoChangeSettle(costBillID);
        			}
        		}catch (Exception e) {
					logger.error(e.getMessage(), e);
					handUIExceptionAndAbort(e);
				}
        		       		
        		logger.info("SettlementCostSplit costbill id="+costBillID);
        	}else{
        		logger.error("工作流中取不到结算单ID，can't get settlement id");
        	}
        }
	}
	protected void loadData() throws Exception {
		handleSplitWorkFlow();
		super.loadData();
	}

	
	public void actionViewCostInfo_actionPerformed(ActionEvent e) throws Exception {
		if(kdtEntrys.getRowCount()==0){
			return;
		}
		int[] selectedRows = KDTableUtil.getSelectedRows(kdtEntrys);
		SettlementCostSplitEntryCollection c=new SettlementCostSplitEntryCollection();
		for(int i=0;i<selectedRows.length;i++){
			IRow row=kdtEntrys.getRow(selectedRows[i]);
			SettlementCostSplitEntryInfo entry=(SettlementCostSplitEntryInfo)row.getUserObject();
			c.add(entry);
		}
		boolean isAddThisAmt = false;
    	if(getOprtState().equals(OprtState.ADDNEW) || editData.getState() == null){
    		isAddThisAmt = true;
    	}
		UIContext uiContext = new UIContext(this);
		uiContext.put("entryCollection", c);
		uiContext.put("isAddThis", Boolean.valueOf(isAddThisAmt));
		uiContext.put("src", "settSplit");
		IUIWindow dlg =  UIFactory.createUIFactory(UIFactoryName.MODEL).create(ViewCostInfoUI.class.getName(), uiContext,null,OprtState.VIEW);
		dlg.show();
	}

	boolean isOneSplit = true;
	public void onLoad() throws Exception {
		
		super.onLoad();
		initParam();
		getDetailTable().getColumn("amount").setEditor(FDCSplitClientHelper._getTotalCellNumberEdit());
		String contractId = FDCHelper.getId(ContractSettlementBillFactory
				.getRemoteInstance().getContractSettlementBillInfo(
						new ObjectUuidPK(editData.getSettlementBill().getId()))
				.getContractBill());

		setContractBillId(contractId);

		setSplitBillType(CostSplitBillTypeEnum.SETTLEMENTSPLIT);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractBill.id",contractId));
		isOneSplit = !ConChangeSplitFactory.getRemoteInstance().exists(filter);
		
		/* modified by zhaoqin for R140403-0024 on 2014/04/17 */
		Object temp = this.getParams().get(FDCConstants.FDC_PARAM_IMPORTCONSPLIT);
		boolean noCtrl=Boolean.valueOf(temp.toString()).booleanValue();
		if (STATUS_ADDNEW.equals(getOprtState()) && noCtrl) {
			 importCostSplist();
		}
		
		getDetailTable().getColumn("directAmt").setEditor(FDCSplitClientHelper._getCellNumberEdit());
		getDetailTable().getColumn("grtSplitAmt").getStyleAttributes().setLocked(true);
		setAmtDisplay();
		
		/*产品行不让录入量价*/
		if(isMeasureContract()){
			for(int i=0;i<getDetailTable().getRowCount();i++){
				IRow row=getDetailTable().getRow(i);
				if(row.getUserObject() instanceof FDCSplitBillEntryInfo){
					FDCSplitBillEntryInfo entry=(FDCSplitBillEntryInfo)row.getUserObject();
					if(isProdSplitLeaf(entry)){
						row.getCell("workLoad").getStyleAttributes().setLocked(true);
						row.getCell("price").getStyleAttributes().setLocked(true);
					}
					
				}
			}
			
			getDetailTable().getColumn("contractPrice").getStyleAttributes().setLocked(true);
			getDetailTable().getColumn("contractWorkLoad").getStyleAttributes().setLocked(true);
			getDetailTable().getColumn("changePrice").getStyleAttributes().setLocked(true);
			getDetailTable().getColumn("changeWorkLoad").getStyleAttributes().setLocked(true);
		}
		
		getDetailTable().getColumn("splitScale").setEditor(getScaleCellNumberEdit());
		
		/* modified by zhaoqin for R140403-0024 on 2014/04/17 */
		if (OprtState.ADDNEW.equals(getOprtState()) && noCtrl) {
			btnSplit.doClick();
		}
		
		if(getUIContext().get("AUDITBILLVIEW")!=null){
			this.kDLabelContainer8.setVisible(false);
			this.kDLabelContainer1.setVisible(false);
			this.kDLabelContainer10.setVisible(false);
			this.kDLabelContainer11.setVisible(false);
			kdtEntrys.setBounds(new Rectangle(10, 54, 993, 487));
			this.add(kdtEntrys, new KDLayout.Constraints(10, 59, 993, 532, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
	        
		}
	}

	public void onShow() throws Exception {
		getDetailTable().getColumn("contractPrice").getStyleAttributes().setHided(!isMeasureContract());
		getDetailTable().getColumn("contractWorkLoad").getStyleAttributes().setHided(!isMeasureContract());
		getDetailTable().getColumn("changePrice").getStyleAttributes().setHided(!isMeasureContract());
		getDetailTable().getColumn("changeWorkLoad").getStyleAttributes().setHided(!isMeasureContract());
		super.onShow();
		//setFirstLine();	/* modified by zhaoqin for R140403-0024 on 2014/04/17 */
		setHasDirectAmt();
		if (getOprtState().equals(OprtState.VIEW)) {
			actionRemove.setEnabled(false);
			actionSplit.setEnabled(false);
		}
		
		/* modified by zhaoqin for R130927-0088 on 2013/12/12 start */
		Object temp = this.getParams().get(FDCConstants.FDC_PARAM_IMPORTCONSPLIT);
		boolean noCtrl=Boolean.valueOf(temp.toString()).booleanValue();
		if(noCtrl) {
			actionAcctSelect.setEnabled(false);
			actionRemoveLine.setEnabled(false);
			actionImpContrSplit.setEnabled(false);
		}
		/* modified by zhaoqin for R130927-0088 on 2013/12/12 end */
	}
	protected void initWorkButton() {
		super.initWorkButton();
		actionSplit.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_showparent"));
		actionSplitBotUp.setVisible(false);
//		actionSplitProd.setVisible(false);
		actionSplitProj.setVisible(false);
		//actionAcctSelect.setVisible(false);
		//actionRemoveLine.setVisible(false);
		//actionRemoveLine.setEnabled(false);
		btnRemove.setVisible(true);
		btnRemove.setEnabled(true);
		menuItemRemove.setVisible(true);
		menuItemRemove.setEnabled(true);
		actionRemove.setVisible(true);
		actionRemove.setEnabled(true);

		actionSplit.setEnabled(true);
		actionSplit.setVisible(true);
		
		actionSplitBotUp.setEnabled(false);
		actionSplitProj.setEnabled(false);
		//actionAcctSelect.setVisible(false);
		//actionRemoveLine.setVisible(false);
		//actionRemoveLine.setEnabled(false);
//		actionSplitProd.setEnabled(false);
		
		/* modified by zhaoqin for R130927-0088 on 2013/12/12 start */
		actionAcctSelect.setVisible(true);
		actionRemoveLine.setVisible(true);
		actionImpContrSplit.setVisible(true);
		if((getOprtState().equals(OprtState.ADDNEW) || getOprtState().equals(OprtState.EDIT))) {
			actionAcctSelect.setEnabled(true);
			actionRemoveLine.setEnabled(true);
			actionImpContrSplit.setEnabled(true);
		} else {
			actionAcctSelect.setEnabled(false);
			actionRemoveLine.setEnabled(false);
			actionImpContrSplit.setEnabled(false);
		}
		/* modified by zhaoqin for R130927-0088 on 2013/12/12 end */
	}

	public SelectorItemCollection getSelectors() {

		SelectorItemCollection selector = super.getSelectors();
		selector.add("*");
		selector.add("contractBill.isMeasureContract");
		
		//modified by zhaoqin for R130927-0088 on 2013/12/13
		selector.add("curProject.costCenter.id");
		selector.add("curProject.id");
		
		return setSelectors(selector);
	}

	protected ICoreBase getBizInterface() throws Exception {
		return com.kingdee.eas.fdc.contract.SettlementCostSplitFactory
				.getRemoteInstance();
	}

	private boolean isAmtEqual=false; 
	protected IObjectValue createNewData() {
		//by tim_gao 得到初始参数
		
		// modified by zhaoqin on 2013/10/25
		/*
		try {
			initParam();
		} catch (EASBizException e1) {
			logger.error("初始化参数报错!");
			this.handUIExceptionAndAbort(e1);
		} catch (BOSException e1) {
			logger.error("初始化参数报错!");
			this.handUIExceptionAndAbort(e1);
		}
		*/
		com.kingdee.eas.fdc.contract.SettlementCostSplitInfo objectValue = new com.kingdee.eas.fdc.contract.SettlementCostSplitInfo();
		objectValue
				.setCreator((com.kingdee.eas.base.permission.UserInfo) (com.kingdee.eas.common.client.SysContext
						.getSysContext().getCurrentUserInfo()));
		objectValue.setCreateTime(new Timestamp(System.currentTimeMillis()));
		objectValue.setIsInvalid(false);
		String costBillID;
		
		Boolean isFromWorkflow =(Boolean)getUIContext().get("isFromWorkflow");
        if(isFromWorkflow!=null&&isFromWorkflow.booleanValue()&&this.getUIContext().get("billID")!=null){
        	costBillID=this.getUIContext().get("billID").toString();
        	getUIContext().remove(UIContext.ID);
        }else{
        	costBillID = (String)getUIContext().get("costBillID");
        }
		ContractSettlementBillInfo settlementBillInfo = null;

		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("id");
		selectors.add("number");
		selectors.add("name");
		selectors.add("settlePrice");
		selectors.add("contractBill.id");
		selectors.add("contractBill.number");
		selectors.add("contractBill.name");
		selectors.add("contractBill.amount");
		selectors.add("curProject.id");
		selectors.add("curProject.longNumber");
		selectors.add("curProject.costCenter.id");	//modified by zhaoqin for R130927-0088 on 2013/12/13
		selectors.add("qualityGuarante");
		selectors.add("contractBill.isMeasureContract");
		selectors.add("curSettlePrice");
		selectors.add("guaranteAmt");
		selectors.add("qualityGuaranteRate");
		selectors.add("orgUnit.id");	// modified by zhaoqin on 2013/10/25
		try {
			settlementBillInfo = ContractSettlementBillFactory
					.getRemoteInstance().getContractSettlementBillInfo(
							new ObjectUuidPK(BOSUuid.read(costBillID)),
							selectors);

			objectValue.setSettlementBill(settlementBillInfo);
			if(settlementBillInfo.getContractBill()!=null)
				objectValue.setContractBill(settlementBillInfo.getContractBill());
			if(settlementBillInfo.getCurProject()!=null)
				objectValue.setCurProject(settlementBillInfo.getCurProject());
			txtCostBillNumber.setText(settlementBillInfo.getNumber());
			txtCostBillName.setText(settlementBillInfo.getName());
			
			// modified by zhaoqin for R130815-0249 on 2013/09/10 start
			// 启用多次结算时,getSettlePrice()拿到是累计结算金额
			// 现在改为取结算单的当前结算金额
			// txtAmount.setValue(settlementBillInfo.getSettlePrice());
			txtAmount.setValue(settlementBillInfo.getCurSettlePrice());
			objectValue.setAmount(settlementBillInfo.getCurSettlePrice());
			txtGrtSplitAmt.setValue(settlementBillInfo.getGuaranteAmt());
			objectValue.setGrtSplitAmt(settlementBillInfo.getGuaranteAmt());
			// modified by zhaoqin for R130815-0249 on 2013/09/10 end
			
			isMeasure=settlementBillInfo.getContractBill().isIsMeasureContract();

		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
		
		//变更金额
		BigDecimal changeAmt=FDCHelper.ZERO;
		FDCSQLBuilder builder=new FDCSQLBuilder();
		builder.appendSql("select case when fhassettled =1 then fbalanceamount  else famount end  as amount from T_Con_ContractChangeBill change  where fcontractbillId=?");
		builder.addParam(objectValue.getContractBill().getId().toString());
		try{
			IRowSet rowSet=builder.executeQuery();
			if(rowSet!=null){
				while(rowSet.next()){
					changeAmt=FDCHelper.add(changeAmt, rowSet.getBigDecimal("amount"));
				}
			}
		}catch(Exception e){
			handUIExceptionAndAbort(e);
		}
		
		// modified by zhaoqin for R130815-0249 on 2013/09/26
		// isAmtEqual=FDCHelper.toBigDecimal(objectValue.getSettlementBill().getSettlePrice(),2).compareTo(FDCHelper.toBigDecimal(FDCHelper.add(objectValue.getContractBill().getAmount(), changeAmt),2))==0;
		isAmtEqual=FDCHelper.toBigDecimal(objectValue.getSettlementBill().getCurSettlePrice(),2).compareTo(FDCHelper.toBigDecimal(FDCHelper.add(objectValue.getContractBill().getAmount(), changeAmt),2))==0;
		return objectValue;

	}

	protected IObjectValue createNewDetailData(KDTable table) {
		return new com.kingdee.eas.fdc.contract.SettlementCostSplitEntryInfo();
	}

	protected String getSplitBillEntryClassName() {

		return com.kingdee.eas.fdc.contract.SettlementCostSplitEntryInfo.class
				.getName();
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	protected KDTable getDetailTable() {
		return this.kdtEntrys;
	}

	protected void txtSplitedAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception {
		super.txtSplitedAmount_dataChanged(e);
		BigDecimal value = txtSplitedAmount.getBigDecimalValue();
		if (value != null && getDetailTable().getRow(0) != null) {
			//setFirstLine之前调用了，汇总方式不对 by hpw 2010.7.1
			editData.setAmount(value);
		}
	}

	/**
	 * output kdtEntrys_editStopped method
	 */
	protected void kdtEntrys_editStopped(
			com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e)
			throws Exception {
		final IRow row = kdtEntrys.getRow(e.getRowIndex());
		if (e.getColIndex() == kdtEntrys.getColumnIndex("amount")) {
			if (e.getValue() != e.getOldValue()) {
				BigDecimal amount = FDCHelper.ZERO;//new BigDecimal(0);
				BigDecimal splitScale = FDCHelper.ZERO;
				FDCSplitBillEntryInfo entry;
				entry = (FDCSplitBillEntryInfo) row.getUserObject();
				Object cellVal = row.getCell(
						"amount").getValue();
				amount = FDCHelper.toBigDecimal(cellVal);
				//拆分比例
				if(entry.getLevel()==0){
					if(FDCHelper.toBigDecimal(txtAmount.getBigDecimalValue()).compareTo(FDCHelper.ZERO)!=0){
						splitScale=FDCHelper.divide(FDCHelper.multiply(amount, FDCHelper.ONE_HUNDRED), txtAmount.getBigDecimalValue(),10,BigDecimal.ROUND_HALF_UP);
					}
					entry.setSplitScale(splitScale);
					row.getCell("splitScale").setValue(splitScale);
				}else{
					row.getCell("splitScale").setValue(null);
				}
				if (cellVal != null) {
					if (amount.compareTo(FDCHelper.ZERO) == 0) {
						row.getCell("workLoad").getStyleAttributes().setLocked(false);
						row.getCell("price").getStyleAttributes().setLocked(false);
						row.getCell("workLoad").getStyleAttributes().setBackground(Color.WHITE);
						row.getCell("price").getStyleAttributes().setBackground(Color.WHITE);
						entry.setAmount(FDCHelper.ZERO);
						apptAmount(e.getRowIndex());
						calcAmount(0);
						setQuaAmt();
						return;
					}
				}
				entry.setAmount(amount);
				setFirstLine();
			}
		}
		if (e.getColIndex()==kdtEntrys.getColumnIndex("splitScale")){
			if (e.getValue()!=e.getOldValue()){
				
				BigDecimal amount = FDCHelper.ZERO;
				BigDecimal splitScale = FDCHelper.ZERO;
				FDCSplitBillEntryInfo entry;
				entry = (FDCSplitBillEntryInfo)row.getUserObject();
				Object cellVal=row.getCell("splitScale").getValue();
				if(cellVal!=null){
					splitScale=FDCHelper.toBigDecimal(cellVal);
				}
				if(entry.getLevel()==0){
					entry.setSplitScale(splitScale);
					amount = FDCHelper.divide(FDCHelper.multiply(txtAmount.getBigDecimalValue(), splitScale),FDCHelper.ONE_HUNDRED,10,BigDecimal.ROUND_HALF_UP);
					entry.setAmount(amount);
					row.getCell("amount").setValue(amount);
				}else{
					row.getCell("splitScale").setValue(null);
				}
				calcAmount(0);
				setQuaAmt();
				setFirstLine();
			}
		}
		if (e.getColIndex() == kdtEntrys.getColumnIndex("directAmt")) {
			if (e.getValue() != e.getOldValue()) {
				BigDecimal amount = FDCHelper.ZERO;//new BigDecimal(0);
				SettlementCostSplitEntryInfo entry;
				entry = (SettlementCostSplitEntryInfo) row.getUserObject();
				Object cellVal = row.getCell(
						"directAmt").getValue();
				if (cellVal != null) {
					amount = new BigDecimal(cellVal.toString());
				}
				entry.setDirectAmt(amount);

				if (amount.compareTo(FDCHelper.ZERO) == 0 && hasDirectAmt) {
					setHasDirectAmt();
					if (!hasDirectAmt) {
						// 清空
						for (int i = 0; i < getDetailTable().getRowCount(); i++) {
							((FDCSplitBillEntryInfo) getDetailTable().getRow(i)
									.getUserObject()).put("amount",
									FDCHelper.ZERO);
							getDetailTable().getCell(i, "amount").setValue(
									FDCHelper.ZERO);
							((FDCSplitBillEntryInfo) getDetailTable().getRow(i)
									.getUserObject()).put("grtSplitAmt",
									FDCHelper.ZERO);
							getDetailTable().getCell(i, "grtSplitAmt").setValue(
									FDCHelper.ZERO);
						}
					}
				} else {
					setHasDirectAmt();
				}
			}
		}
		
		handleMeasureCalc(e, row);
		isSave = false;
	}

	/**
	 * output kdtEntrys_editValueChanged method
	 */
	protected void kdtEntrys_editValueChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e)
			throws Exception {
		super.kdtEntrys_editValueChanged(e);
	}

	/**
	 * output kdtEntrys_activeCellChanged method
	 */
	protected void kdtEntrys_activeCellChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e)
			throws Exception {
		super.kdtEntrys_activeCellChanged(e);
	}

	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		btnSplit.doClick();
		checkDirAmt();
		checkAcctSum();
		super.actionSubmit_actionPerformed(e);
		setFirstLine();
		isSave = true;
	}
	private boolean isSave = false;
	/**
	 * output actionRemoveLine_actionPerformed
	 */
	public void actionRemoveLine_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionRemoveLine_actionPerformed(e);
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
		filter.getFilterItems()
				.add(
						new FilterItemInfo("state",
								FDCBillStateEnum.AUDITTED_VALUE));
		if (getBizInterface().exists(filter)) {
			MsgBox.showWarning(FDCSplitClientHelper.getRes("listRemove"));
			SysUtil.abort();
		}
    }
	private void importCostSplist()  throws Exception{
		importContractCostSplist();
		importChangeCostSplist();
		afterImport();
		setDisplay();
		setAmtDisplay();
	}
	
	private void afterImport(){
		FDCSplitBillEntryCollection entrys=getEntrys(); 
		FDCSplitBillEntryInfo entry = null;
		BigDecimal totalAmout = FDCHelper.ZERO;
		BOSObjectType contractType = new ContractBillInfo().getBOSType();
		/**
		 * 当直接以合同或变更的金额除以结算金额时 (合同或变更)结算=拆分比例，如果变更金额为0则不能100%拆分。
		 * 算法修改：(合同或变更/合同+变更)*结算金额=拆分比例
		 * 
		 * by hpw 2011.12.28
		 */
		for (Iterator iter = entrys.iterator(); iter.hasNext();) {
			entry = (FDCSplitBillEntryInfo) iter.next();
			if (entry.getLevel() == 0) {
				totalAmout = FDCHelper.add(totalAmout, entry.get("contractAmt"));
				totalAmout = FDCHelper.add(totalAmout, entry.get("changeAmt"));
			}
		}
		//按顺序取的分录，按顺序设置值
		int i=0;
		//除最后一行外的归属金额，因为四舍五入的归属可能导致总拆分金额比结算金额有0.01的差额
		BigDecimal amtTotal = FDCHelper.ZERO;
		//记录最后一个级次为0的行数
		int lastLevefZero = 0;
		for (Iterator iter = entrys.iterator(); iter.hasNext();) {
			IRow row = kdtEntrys.getRow(i);
			entry = (FDCSplitBillEntryInfo) iter.next();
			//拆分比例
			BigDecimal splitScale = null;
			BigDecimal amt = null;
			if(entry.getLevel()==0){
				if(isOneSplit){
					//entry.setAmount(FDCHelper.toBigDecimal(entry.get("contractAmt")));
					//此时拆分比例是0。故带不出拆分比例和拆分值
					splitScale = FDCHelper.divide(FDCHelper.multiply(entry.get("contractAmt"), FDCHelper.ONE_HUNDRED), totalAmout, 10,
							BigDecimal.ROUND_HALF_UP);
					
					// modified by zhaoqin on 2013/10/25, start
					if(FDCHelper.compareTo(totalAmout, FDCHelper.ZERO) == 0) {
						splitScale = entry.getSplitScale();
					}
					// modified by zhaoqin on 2013/10/25, end
					
					row.getCell("splitScale").setValue(splitScale);
					
					/* modified by zhaoqin for R140403-0024 on 2014/04/17 */
					entry.setSplitScale(splitScale);
					
					// modified by zhaoqin for R130815-0249 on 2013/09/26
					// BigDecimal amount = FDCHelper.divide(FDCHelper.multiply(FDCHelper.toBigDecimal(editData.getSettlementBill()
						//	.getSettlePrice()), splitScale), FDCHelper.ONE_HUNDRED, 10, BigDecimal.ROUND_HALF_UP);
					BigDecimal amount = FDCHelper.divide(FDCHelper.multiply(FDCHelper.toBigDecimal(editData.getSettlementBill()
							.getCurSettlePrice()), splitScale), FDCHelper.ONE_HUNDRED, 10, BigDecimal.ROUND_HALF_UP);
					row.getCell("amount").setValue(amount);
					entry.setAmount(amount);
					amtTotal = FDCHelper.add(amtTotal, amount);
				}else{
					if(FDCHelper.toBigDecimal(txtAmount.getBigDecimalValue()).compareTo(FDCHelper.ZERO)!=0){
						
						if(BOSUuid.read(entry.getCostBillId().toString()).getType().equals(contractType)){
							splitScale=FDCHelper.divide(FDCHelper.multiply(entry.get("contractAmt"), FDCHelper.ONE_HUNDRED), totalAmout,10,BigDecimal.ROUND_HALF_UP);	
						}else{
							splitScale=FDCHelper.divide(FDCHelper.multiply(entry.get("changeAmt"), FDCHelper.ONE_HUNDRED), totalAmout,10,BigDecimal.ROUND_HALF_UP);
						}
					}
					entry.setSplitScale(splitScale);
					amt = FDCHelper.divide(FDCHelper.multiply(txtAmount.getBigDecimalValue(), splitScale), FDCHelper.ONE_HUNDRED, 2,
							BigDecimal.ROUND_HALF_UP);
					if(iter.hasNext()){
						amtTotal = FDCHelper.add(amtTotal, amt);
						lastLevefZero = i;
					}else{
						amt = FDCHelper.subtract(txtAmount.getBigDecimalValue(), amtTotal);
					}
					row.getCell("splitScale").setValue(splitScale);
					entry.setAmount(amt);
					row.getCell("amount").setValue(amt);
				}
			}else{
				entry.setSplitScale(null);
				if (!iter.hasNext()) {
					BigDecimal amount = FDCHelper.subtract(txtAmount.getBigDecimalValue(), amtTotal);
					kdtEntrys.getCell(lastLevefZero, "amount").setValue(
							FDCHelper.add(kdtEntrys.getCell(lastLevefZero, "amount").getValue(), amount));
				}
			}
			i++;
		}
	}
	
	private void importContractCostSplist() {
		loadCostSplit(CostSplitBillTypeEnum.CONTRACTSPLIT,
				getCostSplitEntryCollection(
						CostSplitBillTypeEnum.CONTRACTSPLIT, null));
	}

	private void importChangeCostSplist() {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractChange.contractBill.id", getContractBillId()));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
		view.setFilter(filter);
		SelectorItemCollection sic = view.getSelector();
		sic.add(new SelectorItemInfo("contractChange.contractBill.id"));

		ConChangeSplitCollection coll = null;
		try {
			coll = ConChangeSplitFactory.getRemoteInstance()
					.getConChangeSplitCollection(view);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}

		if (coll == null || coll.size() == 0) {
			return;
		}

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			ConChangeSplitInfo item = (ConChangeSplitInfo) iter.next();
			loadCostSplit(CostSplitBillTypeEnum.CNTRCHANGESPLIT, getCostSplitEntryCollection(CostSplitBillTypeEnum.CNTRCHANGESPLIT, item
					.getContractChange().getId()));
		}

	}

	/**
	 * 载入成本拆分数据
	 * @param costBillType 拆分类型，有合同拆分、变更拆分等
	 * @param entrys 
	 */
	protected void loadCostSplit(CostSplitBillTypeEnum costBillType, FDCSplitBillEntryCollection entrys) {
		FDCSplitBillEntryInfo entry = null;
		//by tim_gao 参数((Object)((HashMap)this.getParams()).get(FDCConstants.FDC_PARAM_IMPORTCONSPLIT))
		Object temp = this.getParams().get(FDCConstants.FDC_PARAM_IMPORTCONSPLIT);
		boolean noCtrl=Boolean.valueOf(temp.toString()).booleanValue();
		if (costBillType.equals(CostSplitBillTypeEnum.CONTRACTSPLIT)) {
			// contractAmt
			for (Iterator iter = entrys.iterator(); iter.hasNext();) {
				entry = (FDCSplitBillEntryInfo) iter.next();
				Object amount = entry.get("amount");
				entry.put("contractAmt", amount);
				entry.put("apportionValue", amount);
				entry.put("apportionValueTotal", amount);
				entry.put("otherRatioTotal", amount);
				entry.put("contractWorkLoad", FDCHelper.toBigDecimal(entry.getWorkLoad()).signum()==0?null:entry.getWorkLoad());
				entry.put("contractPrice", FDCHelper.toBigDecimal(entry.getPrice()).signum()==0?null:entry.getPrice());
				
				if(!isAmtEqual){
					//R101103-450归属默认为0
					entry.put("amount", FDCHelper.ZERO);
					entry.put("workLoad", null);
					entry.put("price", null);
				}
				if(!noCtrl){
					entry.put("splitScale", FDCHelper.ZERO);
				}
			}
		} else if (costBillType.equals(CostSplitBillTypeEnum.CNTRCHANGESPLIT)) {
			// changeAmt
			for (Iterator iter = entrys.iterator(); iter.hasNext();) {
				entry = (FDCSplitBillEntryInfo) iter.next();
				Object amount = entry.get("amount");
				entry.put("changeAmt", amount);
				entry.put("apportionValue", amount);
				entry.put("apportionValueTotal", amount);
				entry.put("otherRatioTotal", amount);
				entry.put("changeWorkLoad", FDCHelper.toBigDecimal(entry.getWorkLoad()).signum()==0?null:entry.getWorkLoad());
				entry.put("changePrice", FDCHelper.toBigDecimal(entry.getPrice()).signum()==0?null:entry.getPrice());

				if(!isAmtEqual){
					//R101103-450归属默认为0
					entry.put("amount", FDCHelper.ZERO);
					entry.put("workLoad", null);
					entry.put("price", null);
				}
				if(!noCtrl){
					entry.put("splitScale", FDCHelper.ZERO);
				}
			}
		}
		super.loadCostSplit(entrys);
	}

	//优化后的反写必须写
	protected void setTransSelector(){
	
		Map temp = this.getEnumEntrySelecMap();
		Map transSelector = new HashMap();
		if(temp==null){
			temp =new  HashMap();
		}
		transSelector.put("changeAmt", "amount");
		transSelector.put("apportionValue", "amount");
		transSelector.put("apportionValueTotal", "amount");
		transSelector.put("otherRatioTotal", "amount");
		transSelector.put("changeWorkLoad", "workLoad");
		transSelector.put("changePrice", "price");
		if(!isAmtEqual){
			transSelector.put("amount", FDCHelper.ZERO);
			transSelector.put("workLoad", null);
			transSelector.put("price", null);
		}
		temp.put(CostSplitBillTypeEnum.CNTRCHANGESPLIT, transSelector);
		
		temp.put(CostSplitBillTypeEnum.CONTRACTSPLIT, transSelector);
		this.setEnumEntrySelecMap(temp);
	}
	
	private void setFirstLine() {

		if (getDetailTable().getRowCount() > 0) {
			IRow row = getDetailTable().getRow(0);
			Object obj = row.getUserObject();
			if (!(obj instanceof SettlementCostSplitEntryInfo)
					|| ((SettlementCostSplitEntryInfo) obj).getLevel() > -1) {
				// 没有总计行
				row = getDetailTable().addRow(0);
				SettlementCostSplitEntryInfo entry = new SettlementCostSplitEntryInfo();
				entry.setLevel(-1000);
				row.setUserObject(entry);
			}

			row.getCell(2).setValue(FDCSplitClientHelper.getRes("total"));// 合计
			BigDecimal contractAmt = FDCHelper.ZERO;
			BigDecimal changeAmt = FDCHelper.ZERO;
			BigDecimal grtSplitAmt = FDCHelper.ZERO;
			BigDecimal contractWorkLoad = FDCHelper.ZERO;
			BigDecimal changeWorkLoad = FDCHelper.ZERO;
			BigDecimal workLoad = FDCHelper.ZERO;
			BigDecimal splitScale = FDCHelper.ZERO;
			BigDecimal amount = FDCHelper.ZERO;
			for (int i = 0; i < getDetailTable().getRowCount(); i++) {
				obj = getDetailTable().getRow(i).getUserObject();
				if (obj instanceof SettlementCostSplitEntryInfo) {
					SettlementCostSplitEntryInfo entry = (SettlementCostSplitEntryInfo) obj;
					if (entry.getLevel() == 0) {
						if (entry.getContractAmt() instanceof BigDecimal) {
							contractAmt = contractAmt.add(entry
									.getContractAmt());
						}
						if (entry.getChangeAmt() instanceof BigDecimal) {
							changeAmt = changeAmt.add(entry.getChangeAmt());
						}
						if (entry.getGrtSplitAmt() != null) {
							grtSplitAmt = grtSplitAmt.add(entry
									.getGrtSplitAmt());
						}
						if (entry.getContractWorkLoad() != null) {
							contractWorkLoad = contractWorkLoad.add(entry.getContractWorkLoad());
						}
						if (entry.getChangeWorkLoad() != null) {
							changeWorkLoad = changeWorkLoad.add(entry.getChangeWorkLoad());
						}
						if (entry.getWorkLoad() != null) {
							workLoad = workLoad.add(entry.getWorkLoad());
						}
						if(entry.getSplitScale()!=null){
							splitScale= splitScale.add(entry.getSplitScale());
						}
						if(entry.getAmount()!=null){
							amount = amount.add(entry.getAmount());
						}
					}
				}
			}
			row.getCell("contractAmt").setValue(contractAmt);
			row.getCell("changeAmt").setValue(changeAmt);
			row.getCell("grtSplitAmt").setValue(grtSplitAmt);
			row.getCell("contractWorkLoad").setValue(contractWorkLoad);
			row.getCell("changeWorkLoad").setValue(changeWorkLoad);
			row.getCell("workLoad").setValue(workLoad);
			row.getCell("splitScale").setValue(splitScale);
			row.getCell("amount").setValue(amount);
			txtSplitedAmount.setValue(amount);
			txtUnSplitAmount.setValue(FDCHelper.subtract(txtAmount.getBigDecimalValue(), amount));
			row.getCell("contractPrice").setValue(FDCHelper.divide(contractAmt, contractWorkLoad));
			row.getCell("changePrice").setValue(FDCHelper.divide(changeAmt, changeWorkLoad));
			row.getCell("price").setValue(FDCHelper.divide(row.getCell("amount").getValue(), workLoad));
			row.getStyleAttributes().setLocked(true);
			row.getStyleAttributes().setBackground(
					FDCHelper.KDTABLE_TOTAL_BG_COLOR);

		}
	}

	// 响应拆分按钮，当不存在直接金额的时候，按输入的归属金额拆分，如果有直接金额，就按照直接金额进行拆分。
	public void actionSplit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSplit_actionPerformed(e);
		boolean temp = hasDirectAmt;
		if (temp == true) {
			if (getDetailTable().getRowCount() > 0) {
				for (int i = 0; i < getDetailTable().getRowCount(); i++) {
					Object obj = getDetailTable().getRow(i).getUserObject();
					if ((obj instanceof SettlementCostSplitEntryInfo)
							&& ((SettlementCostSplitEntryInfo) obj).getLevel() > -1) {
						SettlementCostSplitEntryInfo entry = (SettlementCostSplitEntryInfo) obj;
						if (getDetailTable().getRow(i).getCell("directAmt")
								.getValue() != null) {
							getDetailTable().getRow(i).getCell("amount")
									.setValue(
											getDetailTable().getRow(i).getCell(
													"directAmt").getValue());
							entry.setAmount(entry.getDirectAmt());
						} else {
							getDetailTable().getRow(i).getCell("amount")
									.setValue(FDCHelper.ZERO);
							entry.setAmount(FDCHelper.ZERO);
						}
					}
				}
			}
			calDirAmt();
			calcAmount(0);
		} else {
			if (getDetailTable().getRowCount() > 0) {
				for (int i = 0; i < getDetailTable().getRowCount(); i++) {
					Object obj = getDetailTable().getRow(i).getUserObject();
					if ((obj instanceof SettlementCostSplitEntryInfo)
							&& ((SettlementCostSplitEntryInfo) obj).getLevel() > -1) {
						SettlementCostSplitEntryInfo entry = (SettlementCostSplitEntryInfo) obj;
						if (entry.getLevel() == 0) {
							apptAmount(i);
						}
					}
				}
			}
			calcAmount(0);
		}
		setQuaAmt();
		setFirstLine();
	}

	protected void setQuaAmt() throws Exception {
		if (getDetailTable().getRowCount() > 0) {
			for (int i = 0; i < getDetailTable().getRowCount(); i++) {
				Object obj = getDetailTable().getRow(i).getUserObject();
				if ((obj instanceof SettlementCostSplitEntryInfo)
						&& ((SettlementCostSplitEntryInfo) obj).getLevel() > -1) {
					SettlementCostSplitEntryInfo entry = (SettlementCostSplitEntryInfo) obj;
					if (entry.getAmount() != null
							&& editData.getSettlementBill()
									.getQualityGuarante() != null) {
						BigDecimal cmpAmt = FDCHelper.ZERO;
						// modified by zhaoqin for R130925-0255 on 2013/09/27
						// cmpAmt=FDCHelper.divide(FDCHelper.multiply(entry.getAmount(), editData.getSettlementBill().getQualityGuarante()), editData.getSettlementBill().getSettlePrice(), 2,BigDecimal.ROUND_HALF_EVEN);
						cmpAmt=FDCHelper.divide(FDCHelper.multiply(entry.getAmount(), editData.getSettlementBill().getQualityGuaranteRate()), FDCHelper.ONE_HUNDRED, 2,BigDecimal.ROUND_HALF_EVEN);
						entry.setGrtSplitAmt(cmpAmt);
						getDetailTable().getRow(i).getCell("grtSplitAmt").setValue(cmpAmt);
					}
				}
			}
			// modified by zhaoqin for R130925-0255 on 2013/09/26
			// if (editData.getAmount().compareTo(editData.getSettlementBill().getSettlePrice()) == 0) {
			if (FDCHelper.compareTo(editData.getAmount(), editData.getSettlementBill().getCurSettlePrice()) == 0) {				
				int idx = 0;
				BigDecimal amountMax = FDCHelper.ZERO;//new BigDecimal(0);
				BigDecimal amount = FDCHelper.ZERO;// null;
				BigDecimal amountTotal = FDCHelper.ZERO;
				amountTotal = amountTotal.setScale(10);
				for (int i = 0; i < getDetailTable().getRowCount(); i++) {
					Object obj = getDetailTable().getRow(i).getUserObject();
					if ((obj instanceof SettlementCostSplitEntryInfo)
							&& ((SettlementCostSplitEntryInfo) obj).getLevel() > -1) {
						SettlementCostSplitEntryInfo entry = (SettlementCostSplitEntryInfo) obj;
						if (entry.getLevel() == 0) {
							amount = entry.getGrtSplitAmt();
							if (amount == null) {
								amount = FDCHelper.ZERO;
							}
							amountTotal = amountTotal.add(amount);
							if (amount.compareTo(amountMax) >= 0) {
								amountMax = amount;
								idx = i;
							}
						} else {
							continue;
						}
					}
				}
				// modified by zhaoqin for R130925-0255 on 2013/09/27
				// if (idx > 0	&& editData.getSettlementBill().getQualityGuarante().compareTo(amountTotal) != 0) {
				if (idx > 0	&& editData.getSettlementBill().getGuaranteAmt().compareTo(amountTotal) != 0) {
					SettlementCostSplitEntryInfo entry = (SettlementCostSplitEntryInfo) getEntrys()
							.getObject(idx);
					if (entry.getGrtSplitAmt() != null) {
						if (!(entry.getGrtSplitAmt().equals(FDCHelper.ZERO))) {
							if (txtAmount.getBigDecimalValue() != null
									&& txtSplitedAmount.getBigDecimalValue() != null)
								if (txtAmount.getBigDecimalValue().equals(
										txtSplitedAmount.getBigDecimalValue())) {
									amount = entry.getGrtSplitAmt();
									if (amount == null) {
										amount = FDCHelper.ZERO;//new BigDecimal(0);
									}
									amount = amount.add(editData
											.getSettlementBill()
											// .getQualityGuarante().subtract(
											.getGuaranteAmt().subtract(
													amountTotal));
									entry.setGrtSplitAmt(amount);
									getDetailTable().getRow(idx).getCell(
											"grtSplitAmt").setValue(amount);
								}
						}
					}
				}
				for (int i = 0; i < getDetailTable().getRowCount(); i++) {
					Object obj = getDetailTable().getRow(i).getUserObject();
					if ((obj instanceof SettlementCostSplitEntryInfo)
							&& ((SettlementCostSplitEntryInfo) obj).getLevel() > -1) {
						SettlementCostSplitEntryInfo entry = (SettlementCostSplitEntryInfo) obj;
						if (entry.getLevel() == 0) {
							int curIndex = getEntrys().indexOf(entry);
							SettlementSplitHelper.adjustQuaAmount(getEntrys(),
									entry);
							// 汇总生成非明细工程项目中附加科目的成本 jelon 12/29/2006
							SettlementSplitHelper.totAmountQuaAddlAcct(
									getEntrys(), curIndex);
						}
					}
				}
				for (int i = 0; i < getDetailTable().getRowCount(); i++) {
					IRow row = getDetailTable().getRow(i);
					Object obj = getDetailTable().getRow(i).getUserObject();
					if ((obj instanceof SettlementCostSplitEntryInfo)
							&& ((SettlementCostSplitEntryInfo) obj).getLevel() > -1) {
						SettlementCostSplitEntryInfo entry = (SettlementCostSplitEntryInfo) obj;
						if(entry.getGrtSplitAmt()!=null){
							row.getCell("grtSplitAmt").setValue(entry.getGrtSplitAmt());
						}
					}
				}
			}
		}
	}

	// 设置直接金额与金额的可录入
	private void setAmtDisplay() {
		if (getDetailTable().getRowCount() > 0) {
			for (int i = 0; i < getDetailTable().getRowCount(); i++) {
				Object obj = getDetailTable().getRow(i).getUserObject();
				if ((obj instanceof SettlementCostSplitEntryInfo)
						&& ((SettlementCostSplitEntryInfo) obj).getLevel() > -1) {
					SettlementCostSplitEntryInfo entry = (SettlementCostSplitEntryInfo) obj;
					if (entry.getLevel() == 0) {
						getDetailTable().getRow(i).getStyleAttributes()
								.setBackground(new Color(0xF6F6BF));
						getDetailTable().getRow(i).getCell("amount")
								.getStyleAttributes().setBackground(
										new Color(0xFFFFFF));
					} else {
						getDetailTable().getRow(i).getStyleAttributes()
								.setBackground(new Color(0xF5F5E6));
						getDetailTable().getRow(i).getCell("amount")
								.getStyleAttributes().setLocked(true);
					}
					if ((entry.getCostAccount().getCurProject().isIsLeaf())
							&& (entry.isIsLeaf())) {
						getDetailTable().getRow(i).getCell("directAmt")
								.getStyleAttributes().setBackground(
										new Color(0xFFFFFF));
					} else {
						getDetailTable().getRow(i).getCell("directAmt")
								.getStyleAttributes().setBackground(
										FDCHelper.KDTABLE_TOTAL_BG_COLOR);
						getDetailTable().getRow(i).getCell("directAmt")
								.getStyleAttributes().setLocked(true);
					}
				}
			}
		}
	}

	// 有直接金额，拆分后汇总
	private void calDirAmt() {
		for (int i = 1; i < kdtEntrys.getRowCount(); i++) {
			SettlementCostSplitEntryInfo entry = (SettlementCostSplitEntryInfo) kdtEntrys
					.getRow(i).getUserObject();
			int curIndex = getEntrys().indexOf(entry);
			if (curIndex == -1) {
				return;
			}
			if (entry.getLevel() == 0) {
				sumAccount(i);
				fdcCostSplit.totAmountAddlAcct(getEntrys(), curIndex);
			}
		}
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			IRow row = kdtEntrys.getRow(i);
			FDCSplitBillEntryInfo entry = (FDCSplitBillEntryInfo) row
					.getUserObject();
			BigDecimal amount = entry.getAmount();
			if (amount == null) {
				amount = FDCHelper.ZERO;
			}
			row.getCell("amount").setValue(amount);
		}
	}

	// 对level=0的进行汇总
	private void sumAccount(int index) {
		SettlementCostSplitEntryInfo curEntry = (SettlementCostSplitEntryInfo) kdtEntrys
				.getRow(index).getUserObject();
		int curLevel = curEntry.getLevel();
		int prjLevel = curEntry.getCostAccount().getCurProject().getLevel();
		SettlementCostSplitEntryInfo entry;
		if ((curEntry.getCostAccount().getCurProject().isIsLeaf())
				&& (curEntry.isIsLeaf())) {
			return;
		} else {
			if (!curEntry.getCostAccount().getCurProject().isIsLeaf()) {
				BigDecimal amtTotal = FDCHelper.ZERO;
				for (int i = index + 1; i < kdtEntrys.getRowCount(); i++) {
					entry = (SettlementCostSplitEntryInfo) kdtEntrys.getRow(i)
							.getUserObject();
					if (fdcCostSplit.isChildProjSameAcct(entry, curEntry)) {
						sumAccount(i);
						if (entry.getAmount() != null)
							amtTotal = amtTotal.add(entry.getAmount());
					} else if (entry.getLevel() < curLevel + 1) {
						break;
					} else {
						continue;
					}
				}
				curEntry.setAmount(amtTotal);
				kdtEntrys.getRow(index).getCell("amount").setValue(amtTotal);
			} else {
				BigDecimal amtTotal = FDCHelper.ZERO;
				for (int i = index + 1; i < kdtEntrys.getRowCount(); i++) {
					entry = (SettlementCostSplitEntryInfo) kdtEntrys.getRow(i)
							.getUserObject();
					if (entry.getCostAccount().getCurProject().getId().equals(
							curEntry.getCostAccount().getCurProject().getId())) {
						if (entry.getLevel() == curLevel + 1) {
							sumAccount(i);
							if (entry.getAmount() != null)
								amtTotal = amtTotal.add(entry.getAmount());
						} else if (entry.getLevel() > curLevel + 1) {
							continue;
						} else {
							break;
						}
					} else {
						break;
					}
				}
				curEntry.setAmount(amtTotal);
				kdtEntrys.getRow(index).getCell("amount").setValue(amtTotal);
			}
		}
	}

	private void checkDirAmt() {
		if (getDetailTable().getRowCount() > 0) {
			for (int i = 0; i < getDetailTable().getRowCount(); i++) {
				if (getDetailTable().getRow(i).getCell("directAmt").getValue() != null) {
					BigDecimal dir = toBigDecimal(getDetailTable().getRow(i)
							.getCell("directAmt").getValue());
					if (dir.compareTo(FDCHelper.ZERO) > 0) {
						BigDecimal dirAmt = toBigDecimal(getDetailTable()
								.getRow(i).getCell("amount").getValue());
						if (dir.compareTo(dirAmt) != 0) {
							MsgBox.showWarning(this, FDCSplitClientHelper
									.getRes("haveDirAmt"));
							SysUtil.abort();
						}
					}
				}
			}
		}
	}

	/**
	 * Object对象转换为BigDecimal对象
	 */
	private BigDecimal toBigDecimal(Object obj) {
		return FDCHelper.toBigDecimal(obj);
	}

	private void setHasDirectAmt() {
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			IRow row = kdtEntrys.getRow(i);
			if (row.getUserObject() != null) {
				SettlementCostSplitEntryInfo entry = (SettlementCostSplitEntryInfo) row
						.getUserObject();
				if (entry.getDirectAmt() != null
						&& entry.getDirectAmt().compareTo(FDCHelper.ZERO) > 0) {
					hasDirectAmt = true;
					return;
				}
			}
		}
		hasDirectAmt = false;
	}
	
    public  KDMenuItem getAttachMenuItem( KDTable table )
    {
    	return null;

    }
    
	private boolean isMeasure=false;
	protected boolean isMeasureContract() {
		return isMeasure;
	}
	
	/**
	 *参数控制是否允许科目总和为负的结算拆分 
	 */
	private void checkAcctSum(){
		if(SysContext.getSysContext().getCurrentOrgUnit()!=null&&SysContext.getSysContext().getCurrentOrgUnit().getId()!=null	){
			////--参数：允许科目总和为负的结算拆分
			boolean noCtrl=false;
	        try{
	        	Object theValue = this.getParams().get(FDCConstants.FDC_PARAM_NOSETTLEMENTSPLITACCTCTRL);
	            	
	          
	            if(theValue != null){
	            	noCtrl=Boolean.valueOf(theValue.toString()).booleanValue();
	    		}
	            if(noCtrl){
	            	return;
	            }
	        }catch(Exception e){
	        	this.logger.error("check acct sum err:"+e.getMessage(), e);
	        	handUIExceptionAndAbort(e);
	        }
	        
		}

        
		FDCSplitBillEntryCollection entrys = getEntrys();
		Map map=new HashMap();
		Map acctMap=new HashMap();//用来保存描述
		for(Iterator iter=entrys.iterator();iter.hasNext();){
			FDCSplitBillEntryInfo entry=(FDCSplitBillEntryInfo)iter.next();
			if((!entry.isIsLeaf()&&entry.getProduct()==null&&entry.getSplitType()==CostSplitTypeEnum.PRODSPLIT)||(entry.isIsLeaf()&&(entry.getProduct()==null||entry.getSplitType()!=CostSplitTypeEnum.PRODSPLIT))){
				String key=entry.getCostAccount().getId().toString();
				BigDecimal amt=entry.getAmount();
				map.put(key, FDCNumberHelper.add(map.get(key), amt));
				acctMap.put(key, entry.getCostAccount());
			}
		}
		Map treeMap=new TreeMap(new Comparator(){
			public int compare(Object arg0, Object arg1) {
				int retValue=0;
				if(arg0 instanceof CostAccountInfo &&arg1 instanceof CostAccountInfo){
					CostAccountInfo info1=(CostAccountInfo)arg0;
					CostAccountInfo info2=(CostAccountInfo)arg1;
					if(info1.getCurProject().getLongNumber()!=null&&info2.getCurProject().getLongNumber()!=null){
						retValue=info1.getCurProject().getLongNumber().compareTo(info2.getCurProject().getLongNumber());
					}
					if(retValue==0){
						if(info1.getLongNumber()!=null&&info2.getLongNumber()!=null){
							retValue=info1.getLongNumber().compareTo(info2.getLongNumber());
						}
					}
				}
				return retValue;
			}
		});
		for(Iterator iter=map.keySet().iterator();iter.hasNext();){
			String key=(String)iter.next();
			if(FDCHelper.toBigDecimal(map.get(key)).signum()<0){
				CostAccountInfo info=(CostAccountInfo)acctMap.get(key);
				String displayName = info.getDisplayName();
				if(displayName==null){
					displayName="";
				}
				String desc=info.getCurProject().getDisplayName()+"\t"+displayName.replace('_', '\\')+"\n";
				treeMap.put(info, desc);
			}
		}
		if(treeMap.size()>0){
			StringBuffer sb=new StringBuffer();
			for(Iterator iter=treeMap.values().iterator();iter.hasNext();){
				sb.append(iter.next());
			}
			FDCMsgBox.showDetailAndOK(this, "拆分存在科目金额之和小于零", "如下科目金额之和小于零:\n"+sb.toString(), 0);
			SysUtil.abort();
		}
	}
	public boolean isModify() {
		if(isSave){
			return false;
		}
		return super.isModify();
	}
	private void checkContract(String contractId,boolean isCostSplit) throws Exception {
		if(isCostSplit) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("contractBill", contractId));
			filter.getFilterItems().add(
					new FilterItemInfo("state",
							FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			view.setFilter(filter);
			ContractCostSplitCollection contcoll = ContractCostSplitFactory
					.getRemoteInstance().getContractCostSplitCollection(
							view);
			Iterator iter = contcoll.iterator();
			if (iter.hasNext()) {
				ContractCostSplitInfo cont = (ContractCostSplitInfo) iter
						.next();
				String splitState = cont.getSplitState().toString();
				if (splitState.equals(CostSplitStateEnum.ALLSPLIT
						.toString())) {
					// 变更必须全部进行了拆分 by sxhong
					checkConChange(contractId, true);
					return;
				}
			} else {
				MsgBox.showWarning(this, FDCSplitClientHelper
						.getRes("conNotSplit"));
				SysUtil.abort();
			}
			// 0金额合同只要求有变更拆分
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("amount");
			final ContractBillInfo contractBillInfo = ContractBillFactory
					.getRemoteInstance().getContractBillInfo(
							new ObjectUuidPK(contractId), selector);
			if (contractBillInfo.getAmount() == null
					|| contractBillInfo.getAmount().compareTo(
							FDCHelper.ZERO) == 0) {

				// 变更必须全部进行了拆分 by sxhong
				checkConChange(contractId, true);
			} else {
				MsgBox.showWarning(this, FDCSplitClientHelper
						.getRes("conNotSplit"));
				SysUtil.abort();
			}

		} else {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("contractBill", contractId));
			filter.getFilterItems().add(
					new FilterItemInfo("state",
							FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			view.setFilter(filter);
			ConNoCostSplitCollection contcoll = ConNoCostSplitFactory
					.getRemoteInstance().getConNoCostSplitCollection(view);
			Iterator iter = contcoll.iterator();
			if (iter.hasNext()) {
				ConNoCostSplitInfo cont = (ConNoCostSplitInfo) iter.next();
				String splitState = cont.getSplitState().toString();
				if (splitState.equals(CostSplitStateEnum.ALLSPLIT
						.toString())) {
					checkConChange(contractId, false);
					return;
				}
			} else {
				MsgBox.showWarning(this, FDCSplitClientHelper
						.getRes("conNotSplit"));
				SysUtil.abort();
			}

			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("amount");
			final ContractBillInfo contractBillInfo = ContractBillFactory
					.getRemoteInstance().getContractBillInfo(
							new ObjectUuidPK(contractId), selector);
			if (contractBillInfo.getAmount() == null
					|| contractBillInfo.getAmount().compareTo(
							FDCHelper.ZERO) == 0) {

				checkConChange(contractId, false);
			} else {
				MsgBox.showWarning(this, FDCSplitClientHelper
						.getRes("conNotSplit"));
				SysUtil.abort();
			}
		}
	}

	/**
	 * 检查变更是否拆分
	 * 
	 * @param contractBillId
	 */
	private void checkConChange(String contractBillId, boolean isCostSplit)
			throws Exception {
		String table;
		if (isCostSplit) {
			table = "T_CON_ConChangeSplit";
		} else {
			table = "T_CON_ConChangeNoCostSplit";
		}
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
				.appendSql("select fid from T_CON_ContractChangeBill where fcontractbillid=?");
		builder.addParam(contractBillId);
		IRowSet rowSet = builder.executeQuery();
		int changeCount = rowSet.size();
		if (changeCount == 0) {
			return;
		}
		String ids[] = new String[changeCount];
		int i = 0;
		while (rowSet.next()) {
			ids[i++] = rowSet.getString("fid");
		}

		builder.clear();
		builder.appendSql("select count(*) as count from " + table
				+ " where FIsInvalid=0 and ");
		builder.appendParam("FContractChangeID", ids);
		rowSet = builder.executeQuery();
		int splitCount = 0;
		if (rowSet.size() == 1) {
			rowSet.next();
			splitCount = rowSet.getInt("count");
		}
		if (splitCount < changeCount) {
			MsgBox.showWarning(this, "存在未拆分的变更签证确认，不允许进行结算拆分");
			SysUtil.abort();
		}
	}
	
	protected void checkImp(String contractBillId) throws Exception {
		if (contractBillId == null)	return;
		
		FilterInfo filter = new FilterInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("paymentbill.contractBillId", contractBillId));
		filter.getFilterItems().add(
				new FilterItemInfo("hasEffected", Boolean.TRUE,
						CompareType.EQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
						CompareType.NOTEQUALS));

		boolean hasPaymentSplit = PaymentSplitFactory.getRemoteInstance()
				.exists(filter);
		if (hasPaymentSplit) {
			MsgBox.showError(this, FDCSplitClientHelper.getRes("impByPay"));
			SysUtil.abort();
		}
		boolean hasPaymentNoCostSplit = PaymentNoCostSplitFactory
				.getRemoteInstance().exists(filter);
		if (hasPaymentNoCostSplit) {
			MsgBox.showError(this, FDCSplitClientHelper.getRes("impByPay"));
			SysUtil.abort();
		}
	}
	
	/**
	 * R130927-0088: 引入合同/变更拆分
	 * @author zhaoqin
	 * @date 2013/12/12
	 */
	public void actionImpContrSplit_actionPerformed(ActionEvent e) throws Exception {
		checkCostAccountntIsDuplicateBeforeImp(CostSplitBillTypeEnum.CONTRACTSPLIT);
		importCostSplist();
		
		/* modified by zhaoqin for R140403-0024 on 2014/04/17 */
		if (OprtState.ADDNEW.equals(getOprtState()) || OprtState.EDIT.equals(getOprtState())) {
			btnSplit.doClick();
		}
	}
	
	/**
	 * 是否重复引入合同拆分
	 * @throws Exception
	 */
	protected void checkCostAccountntIsDuplicateBeforeImp(CostSplitBillTypeEnum splitType) throws Exception{
		if(this.kdtEntrys.getRowCount3() == 0){
			return;
		}
		
		FDCSplitBillEntryInfo entry=null;
		FDCSplitBillEntryCollection fDCSplitBillEntryColl = getCostSplitEntryCollection(splitType, null);
		for(int i=0; i<kdtEntrys.getRowCount(); i++) {	
			entry=(FDCSplitBillEntryInfo)kdtEntrys.getRow(i).getUserObject();
			if(null == entry || null == entry.getCostAccount())
				continue;
			FDCSplitBillEntryInfo entrytemp=null;
			for (Iterator iter = fDCSplitBillEntryColl.iterator(); iter.hasNext();)	{
				entrytemp = (FDCSplitBillEntryInfo) iter.next();	
				if(entry.getCostAccount().getId().equals(entrytemp.getCostAccount().getId())) {
					// 成本科目存在重复，不能引入合同拆分！
					MsgBox.showError(this,entry.getCostAccount().getName()+FDCSplitClientHelper.getRes("cannotImp"));
					SysUtil.abort();
				}
			}
		}
	}
	
	/**
	 * R130927-0088: 选择成本科目
	 * @author zhaoqin
	 * @date 2013/12/19
	 */
	public void actionAcctSelect_actionPerformed(ActionEvent arg0)
			throws Exception {
		getUIContext().put("curProject", this.editData.getCurProject());
		int oldCount = this.kdtEntrys.getRowCount();
		
		super.actionAcctSelect_actionPerformed(arg0);
		
		Set costAccountIDs = new HashSet();
		for(int i= oldCount; i < kdtEntrys.getRowCount(); i++) {
			SettlementCostSplitEntryInfo entry = (SettlementCostSplitEntryInfo)kdtEntrys.getRow(i).getUserObject();
			if(null != entry && entry.isIsLeaf() && null != entry.getCostAccount()) {
				costAccountIDs.add(entry.getCostAccount().getId());
			}
		}
		
		if(costAccountIDs.size() == 0)
			return;
		
		updateConAndChangeSplitData(costAccountIDs);
	}
	
	/**
	 * 更新合同拆分/变更拆分的数据 - R130927-0088
	 * @param costAccountIDs
	 * @throws BOSException 
	 *  
	 * @author zhaoqin
	 * @date 2013/12/20
	 */
	private void updateConAndChangeSplitData(Set costAccountIDs) throws BOSException {
		Map contractData = getContractSplitAmt(costAccountIDs);
		Map conChangeData = getConChangeSplitAmt(costAccountIDs);
		
		/* modified by zhaoqin for R140317-0313 on 2014/03/28 */
		Map conChgCostBillIDs = (Map)conChangeData.get("conChgCostBillIDs");
		
		int selectedCount = kdtEntrys.getRowCount();
		for(int i = 0; i < selectedCount; i++) {
    		IRow row = kdtEntrys.getRow(i);
			SettlementCostSplitEntryInfo entry = (SettlementCostSplitEntryInfo)row.getUserObject();
			boolean isContractAccount = false;
			if(null != entry && entry.isIsLeaf() && null != entry.getCostAccount()) {
				String costAccountID = entry.getCostAccount().getId().toString();
				Object conAmount = contractData.get(costAccountID);
				Object chgAmount = conChangeData.get(costAccountID);
				if(null != conAmount) {
					row.getCell("contractAmt").setValue(conAmount);
					entry.setContractAmt(toBigDecimal(conAmount));
					entry.setCostBillId(BOSUuid.read(getContractBillId()));	/* modified by zhaoqin for  on 2014/03/28 */
					isContractAccount = true;
				}
				if(null != chgAmount) {
					if(isContractAccount) {
						SettlementCostSplitEntryInfo entry1= (SettlementCostSplitEntryInfo)createNewDetailData(kdtEntrys);
						entry1.setCostAccount(entry.getCostAccount());  
						entry1.setLevel(0);
						entry1.setIsLeaf(true);
						entry1.setAmount(FDCHelper.ZERO);
						entry1.setChangeAmt((BigDecimal)chgAmount);
						
						/* modified by zhaoqin for R140317-0313 on 2014/03/28 */
						entry1.setCostBillId((BOSUuid)conChgCostBillIDs.get(costAccountID));
						
						row = addEntry(entry1);
						row.getCell("changeAmt").setValue(chgAmount);
					} else {
						Object changeAmt = row.getCell("changeAmt").getValue();
						chgAmount = null == changeAmt ? changeAmt : FDCHelper.add(changeAmt, chgAmount);
						row.getCell("changeAmt").setValue(chgAmount);
						entry.setChangeAmt(toBigDecimal(chgAmount));
						
						/* modified by zhaoqin for R140317-0313 on 2014/03/28 */
						entry.setCostBillId((BOSUuid)conChgCostBillIDs.get(costAccountID));
					}
				}
				
				/* modified by zhaoqin for R140317-0313 on 2014/03/28 */
				if(null == entry.getCostBillId()) {
					entry.setCostBillId(BOSUuid.read(getContractBillId()));
				}
			}
		}
		super.setDisplay();
	}
	
	/**
	 * 引入合同拆分的数据 - R130927-0088
	 * @param costAccountIDs
	 * @throws BOSException 
	 *  
	 * @author zhaoqin
	 * @date 2013/12/20
	 */
	private Map getContractSplitAmt(Set costAccountIDs) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.contractBill.id", getContractBillId()));
		filter.getFilterItems().add(new FilterItemInfo("costAccount.id", costAccountIDs, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("parent.state", FDCBillStateEnum.INVALID_VALUE, 
    			CompareType.NOTEQUALS));
		view.setFilter(filter);
    	view.getSelector().add("id");
    	view.getSelector().add("amount");
    	view.getSelector().add("costAccount.id");
    	ContractCostSplitEntryCollection coll = 
    		ContractCostSplitEntryFactory.getRemoteInstance().getContractCostSplitEntryCollection(view);
    	Map conData = new HashMap();
    	for(int i = 0; i < coll.size(); i++) {
    		ContractCostSplitEntryInfo info = coll.get(i);
    		conData.put(info.getCostAccount().getId().toString(), info.getAmount());
    	}
    	return conData;
	}
	
	/**
	 * 引入变更拆分数据 - R130927-0088
	 * @param costAccountIDs
	 * @throws BOSException
	 * 
	 * @author zhaoqin
	 * @date 2013/12/20
	 */
	private Map getConChangeSplitAmt(Set costAccountIDs) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.contractBill.id", getContractBillId()));
		filter.getFilterItems().add(new FilterItemInfo("costAccount.id", costAccountIDs, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("parent.state", FDCBillStateEnum.INVALID_VALUE, 
    			CompareType.NOTEQUALS));
		view.setFilter(filter);
    	view.getSelector().add("id");
    	view.getSelector().add("amount");
    	view.getSelector().add("costAccount.id");
    	
    	/* modified by zhaoqin for R140317-0313 on 2014/03/28 start */
    	view.getSelector().add("parent.contractChange.id");
    	Map conChgCostBillIDs = new HashMap();

    	ConChangeSplitEntryCollection coll = 
    		ConChangeSplitEntryFactory.getRemoteInstance().getConChangeSplitEntryCollection(view);
    	Map conData = new HashMap();
    	for(int i = 0; i < coll.size(); i++) {
    		ConChangeSplitEntryInfo info = coll.get(i);
    		String key = info.getCostAccount().getId().toString();
    		BigDecimal value = FDCHelper.add(info.getAmount(), conData.get(key));
    		conData.put(key, value);
    		conChgCostBillIDs.put(key, info.getParent().getContractChange().getId());
    	}
    	conData.put("conChgCostBillIDs", conChgCostBillIDs);
    	/* modified by zhaoqin for R140317-0313 on 2014/03/28 end */
    	
    	return conData;
	}
}