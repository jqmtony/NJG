/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.extendcontrols.ExtendParser;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.CommitEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleInfo;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.master.account.client.AccountPromptBox;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.basedata.ContractThirdTypeEnum;
import com.kingdee.eas.fdc.basedata.ContractTypeCollection;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.ContractWOTCodingTypeCollection;
import com.kingdee.eas.fdc.basedata.ContractWOTCodingTypeInfo;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.PaymentTypeCollection;
import com.kingdee.eas.fdc.basedata.PaymentTypeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;
import com.kingdee.eas.fdc.basedata.SourceTypeEnum;
import com.kingdee.eas.fdc.basedata.client.AttachmentUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCContractParamUI;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.util.FdcCodingRuleUtil;
import com.kingdee.eas.fdc.basedata.util.FdcObjectCollectionUtil;
import com.kingdee.eas.fdc.contract.ConSplitExecStateEnum;
import com.kingdee.eas.fdc.contract.ContractFacadeFactory;
import com.kingdee.eas.fdc.contract.ContractUtil;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.DepPlanStateEnum;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.PayReqUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.UrgentDegreeEnum;
import com.kingdee.eas.fdc.contract.util.ContractCodingUtil;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractInfo;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanCollection;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanFactory;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanInfo;
import com.kingdee.eas.fi.cas.client.CasRecPayHandler;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.ma.budget.BgControlFacadeFactory;
import com.kingdee.eas.ma.budget.BgCtrlResultCollection;
import com.kingdee.eas.ma.budget.BgCtrlResultInfo;
import com.kingdee.eas.ma.budget.BgHelper;
import com.kingdee.eas.ma.budget.IBgControlFacade;
import com.kingdee.eas.ma.budget.client.BgBalanceViewUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;

/**
 * @(#)			ContractWithoutTextEditUI			
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		无文本合同编辑界面
 *		
 * @author		蒲磊		<p>
 * @createDate	2011-8-9	<p>	 
 * @version		EAS7.0		
 * @see					
 */
public class ContractWithoutTextEditUI extends
		AbstractContractWithoutTextEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ContractWithoutTextEditUI.class);
	
	private static final String BINDING_PROPERTY = "codeType.number";

	// 是否第一次加载,用来控制表格的加载显示
	private boolean isFirstLoad = true;
	// 当前单据是否有附件列表
	private boolean isHasAttachment = false;
	// 付款申请单
	private PayRequestBillInfo prbi = new PayRequestBillInfo();
	
	//付款类型
	private PaymentTypeCollection paymentTypes = null;	
	
	//合同类型
	private ContractTypeCollection contractType = null ;

	//启用财务一体化
	protected boolean isFinacial = false;
	
	//项目合同签约总金额超过项目
	private String controlCost ;
	//控制方式
	private String controlType ;
	
	//是否显示“合同费用项目”
	private boolean isShowCharge = false;
	
	// 累计发票金额
	private BigDecimal allInvoiceAmt = FDCHelper.ZERO;
	
	// 房地产付款单强制不进入出纳系统  at 2010-5-10
	protected boolean isNotEnterCAS = false;

	// 付款申请单及无文本合同发票号、发票金额必录
	private boolean isInvoiceRequired = false;
	
	// 通过合同月度滚动付款计划控制无文本合同
	private String CONTROLNOCONTRACT = "不控制";

	private ArrayList orgIDList = new ArrayList();

	// 责任人是否可以选择其他部门的人员
	private boolean canSelecOtherOrgPerson() {
		boolean canSelectOtherOrgPerson = false;
		try {
			canSelectOtherOrgPerson = FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_SELECTPERSON);
		} catch (EASBizException e) {
			handUIExceptionAndAbort(e);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
		return canSelectOtherOrgPerson;
	}

	/**
	 * output class constructor
	 */
	public ContractWithoutTextEditUI() throws Exception {
		super();
		jbInit() ;
	}

	private void jbInit() {	    
		titleMain = getUITitle();
		this.kdDepPlanState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.contract.DepPlanStateEnum").toArray());
	}
	
	public void onShow() throws Exception {
		super.onShow();
		
		setFocus();
		txtPaymentRequestBillNumber.setRequired(false);
		txtPaymentRequestBillNumber.setEditable(false);
	
	}
	
	protected void afterSubmitAddNew() {
		clearPayRequestFieldCtrl();
		super.afterSubmitAddNew();
		setFocus();

		if (prmtContractType.getValue() != null) {
			try {
				contractTypeChanged(prmtContractType.getValue());
			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			}
		}
	}

	private void setFocus() {
		if (txtNumber.isEnabled() && txtNumber.isEditable()) {
			txtNumber.requestFocus();
		} else {
			txtbillName.requestFocus();
		}
	}
	
	/**
	 * output getEditUIName method
	 */
	protected String getEditUIName() {
		return com.kingdee.eas.fdc.contract.client.ContractWithoutTextEditUI.class
				.getName();
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return ContractWithoutTextFactory.getRemoteInstance();
	}

	/**
	 * output getDetailTable method
	 */
	protected KDTable getDetailTable() {
		return null;
	}

	/**
	 * output createNewDetailData method
	 */
	protected IObjectValue createNewDetailData(KDTable table) {
		return null;
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		
		ContractWithoutTextInfo objectValue = new ContractWithoutTextInfo();
		objectValue.setCreator((SysContext.getSysContext().getCurrentUserInfo()));
		objectValue.setCreateTime(new Timestamp(serverDate.getTime()));
		objectValue.setSignDate(this.serverDate);

		objectValue.setSourceType(SourceTypeEnum.ADDNEW);
		
		objectValue.setIsCostSplit(true);
		objectValue.setConSplitExecState(ConSplitExecStateEnum.COMMON);
		objectValue.setCurrency(this.baseCurrency);
		//项目Id
		BOSUuid projId = (BOSUuid) getUIContext().get("projectId");
		if(projId != null) { 
			CurProjectInfo projInfo = curProject;			
			objectValue.setCurProject(projInfo);
			objectValue.setCU(projInfo.getCU());		
		}
		
		// 根据实际收款单位，得到付款帐户和付款银行 andy_liu 2012-6-15
		if (objectValue.getReceiveUnit() != null) {
			String supplierid = objectValue.getReceiveUnit().getId().toString();
			try {
				PayReqUtils.fillBank(objectValue, supplierid, curProject.getCU().getId().toString());
			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			}
		}
		
		objectValue.setBookedDate(bookedDate);
		objectValue.setPeriod(curPeriod);
		
		//设置无文本合同拆分初始化状态为未拆分
		objectValue.setSplitState(CostSplitStateEnum.NOSPLIT);
		objectValue.setInvoiceDate(serverDate);
		return objectValue;
	}

	private void clearPayRequestFieldCtrl() {
		prmtPayment.setValue(null);
		prmtuseDepartment.setValue(null);
		txtPaymentRequestBillNumber.setText(null);
		pksignDate.setValue(null);
		prmtreceiveUnit.setValue(null);
		prmtrealSupplier.setValue(null);
		txtBank.setText(null);
		prmtsettlementType.setValue(null);
		txtBankAcct.setText(null);
		chkUrgency.setSelected(false);
		chkNeedPaid.setSelected(true);
		txtNoPaidReason.setText(null);
		prmtAccount.setValue(null);
		prmtcurrency.setValue(null);
		txtexchangeRate.setValue(null);
		txtamount.setValue(null);
		txtcapitalAmount.setText(null);
		txtBcAmount.setValue(null);
		txtPaymentProportion.setValue(null);
		txtcompletePrjAmt.setValue(null);
		txtMoneyDesc.setText(null);
		txtattachment.setText(null);
	}
	
	//子类可以重载
	protected  void fetchInitParam() throws Exception{
		
		super.fetchInitParam();
		//项目Id
		BOSUuid projId = (BOSUuid) getUIContext().get("projectId");
		HashMap paramItem = new HashMap();
		HashMap hmParamIn = new HashMap();
		if(projId != null) { 
			CurProjectInfo projInfo = curProject;	
			//传入的参数为其所在工程项目对应的财务组织id，而不是获得当前登陆财务组织的id
			//启用成本财务一体化
			//HashMap paramItem = FDCUtils.getDefaultFDCParam(null,projInfo.getFullOrgUnit().getId().toString());
//			HashMap paramItem = FDCUtils.getDefaultFDCParam(null,SysContext.getSysContext().getCurrentFIUnit().getId().toString());
			
			IObjectPK comPK = new ObjectUuidPK(projInfo.getFullOrgUnit().getId().toString());
			
			 //一体化参数
	        hmParamIn.put(FDCConstants.FDC_PARAM_FINACIAL, comPK);
	        
	        //付款申请单及无文本合同发票号、发票金额必录
	    	hmParamIn.put(FDCConstants.FDC_PARAM_INVOICEREQUIRED, null);
	    	
	    	
	        
			
			
			
		}
		//是否显示“合同费用项目”
    	hmParamIn.put(FDCConstants.FDC_PARAM_CHARGETYPE, null);
        
    	String org = SysContext.getSysContext().getCurrentOrgUnit().getId()
		.toString();
    	
    	hmParamIn.put("FDC326_CONTROLNOCONTRACT", new ObjectUuidPK(org));
        paramItem = FDCUtils.getParamHashMapBatch(null, hmParamIn);
        
        if(projId != null){
        	if(paramItem.get(FDCConstants.FDC_PARAM_FINACIAL)!=null){
				isFinacial = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_FINACIAL).toString()).booleanValue();
			}
			if(paramItem.get(FDCConstants.FDC_PARAM_INVOICEREQUIRED)!=null){
				isInvoiceRequired = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_INVOICEREQUIRED).toString()).booleanValue();
			}
        }
        
		//是否显示“合同费用项目”
		if(paramItem.get(FDCConstants.FDC_PARAM_CHARGETYPE)!=null){
			isShowCharge = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_CHARGETYPE).toString()).booleanValue();
		}
		//isShowCharge = FDCUtils.getDefaultFDCParamByKey(null,null,FDCConstants.FDC_PARAM_CHARGETYPE);			
		
		if(paramItem.get("FDC326_CONTROLNOCONTRACT")!=null){
			CONTROLNOCONTRACT = paramItem.get("FDC326_CONTROLNOCONTRACT").toString();
		}
//		CONTROLNOCONTRACT = ParamManager.getParamValue(null, new ObjectUuidPK(
//				org), "FDC326_CONTROLNOCONTRACT");
		if ("0".equals(CONTROLNOCONTRACT)) {
			CONTROLNOCONTRACT = "严格控制";
		} else if ("1".equals(CONTROLNOCONTRACT)) {
			CONTROLNOCONTRACT = "提示控制";
		} else if ("2".equals(CONTROLNOCONTRACT)) {
			CONTROLNOCONTRACT = "不控制";
		}
	}
	
	/**
	 * new update by renliang at 2010-5-12
	 */
	public void isNotEnterCAS(){
		
		CurProjectInfo info = editData.getCurProject();
		
		if(info!=null){
			try {
				this.isNotEnterCAS = FDCUtils.getBooleanValue4FDCParamByKey(null, info.getFullOrgUnit().getId().toString(),
						FDCConstants.FDC_PARAM_NOTENTERCAS);
			} catch (EASBizException e) {				
				logger.info(e.getMessage());
				handUIExceptionAndAbort(e);
			} catch (BOSException e) {			
				logger.info(e.getMessage());
				handUIExceptionAndAbort(e);
			}
		}
		
	}
	
	
	//子类可以重载
	protected  void fetchInitData() throws Exception{

		super.fetchInitData();
		
		//查找付款类型
		paymentTypes = (PaymentTypeCollection)ActionCache.get("ContractWithoutTextEditUIHandler.paymentTypes");
		if(paymentTypes==null){
			String sSql = "select * where IsEnabled=1";			
			paymentTypes = PaymentTypeFactory.getRemoteInstance().getPaymentTypeCollection(sSql);
		}

		//查找合同类型
		contractType = (ContractTypeCollection)ActionCache.get("ContractWithoutTextEditUIHandler.contractType");
		if(contractType==null){
			String contractSql = "select * where IsEnabled = 1" ;
			contractType = ContractTypeFactory.getRemoteInstance().getContractTypeCollection(contractSql) ;
		}
		
		Map param = (Map)ActionCache.get("FDCBillEditUIHandler.orgParamItem");
		if(param==null){
			//param = FDCUtils.getDefaultFDCParam(null,orgUnitInfo.getId().toString());	
			IObjectPK comPK = new ObjectUuidPK(orgUnitInfo.getId()
					.toString());
			HashMap hmParamIn = new HashMap();
	     
	        //项目合同签约总金额超过项目
	        hmParamIn.put(FDCConstants.FDC_PARAM_OUTCOST, comPK);
	      //自定义参数
	        hmParamIn.put(FDCConstants.FDC_PARAM_CONTROLTYPE, comPK);
	        
	        
	        param = FDCUtils.getParamHashMapBatch(null, hmParamIn);
		}
		
		if(param.get(FDCConstants.FDC_PARAM_OUTCOST)!=null){
			controlCost = param.get(FDCConstants.FDC_PARAM_OUTCOST).toString();
		}
		if(param.get(FDCConstants.FDC_PARAM_CONTROLTYPE)!=null){
			controlType = param.get(FDCConstants.FDC_PARAM_CONTROLTYPE).toString();
		}
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		
		ExtendParser parserAccountFrom = new ExtendParser(txtBankAcct);
		txtBankAcct.setCommitParser(parserAccountFrom);
		txtBankAcct.setMaxLength(80);
		
		//new update by renliang at 2010-5-14
        isNotEnterCAS();
	    if (isNotEnterCAS) {
	    	chkNeedPaid.setEnabled(false);
			// 添加判断，保证显示正确
			if (OprtState.ADDNEW.equals(getOprtState())) {
				chkNeedPaid.setSelected(true);
				kdDepPlanState.setSelectedItem(DepPlanStateEnum.noPlan);
			}			
		}
	    
		//onLoad后续逻辑抽象为一个方法
		fillAttachmentList();
		afterOnload();
		
		if (prmtPlanProject.getValue() == null) {
			kdDepPlanState.setSelectedItem(DepPlanStateEnum.noPlan);
		}	
		if ("不控制".equals(CONTROLNOCONTRACT)) {
			kDLabelContainer18.setVisible(false);
		}
		
		this.txtBankAcct.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
			public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
				try {
					txtBankAcct_willCommit(e);
				} catch (Exception exc) {
					handUIException(exc);
				} 
			}
		});

		this.txtBankAcct.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
			public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
				try {
					txtBankAcct_willShow(e);
				} catch (Exception exc) {
					handUIException(exc);
				}
			}
		});
		
		 this.txtBankAcct.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
			public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
				try {
					txtBankAcct_dataChanged(e);
				} catch (Exception exc) {
					handUIException(exc);
				}
			}
		});
	}

	/**
	 * 描述：
	 * @Author：keyan_zhao
	 * @CreateTime：2012-11-8
	 */
	private void checkFDCProDep() {
		if (!"不控制".equals(CONTROLNOCONTRACT)) {
			Date bookDate = (Date) pkbookedDate.getValue();
			Calendar instance = Calendar.getInstance();
			instance.setTime(bookDate);
			int year = instance.get(Calendar.YEAR);
			int month = instance.get(Calendar.MONTH) + 1;
			String projectId = editData.getCurProject().getId().toString();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("longNumber");
			sic.add("id");
			try {
				CurProjectInfo projectInfo = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(projectId), sic);
				String longNumber = projectInfo.getLongNumber();
				if (longNumber.indexOf("!") > 0) {
					longNumber = longNumber.substring(0, longNumber.indexOf("!"));
				}
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("year", new Integer(year)));
				filter.getFilterItems().add(new FilterItemInfo("month", new Integer(month)));
				filter.getFilterItems().add(new FilterItemInfo("curProject.longNumber", longNumber));
				filter.getFilterItems().add(new FilterItemInfo("curProject.longNumber like '" + longNumber + "!%'"));
				filter.setMaskString("#0 and #1 and (#2 or #3)");
				view.setFilter(filter);
				view.getSelector().add(new SelectorItemInfo("state"));
				FDCProDepConPayPlanCollection planCollection = FDCProDepConPayPlanFactory.getRemoteInstance()
						.getFDCProDepConPayPlanCollection(view);
				if (planCollection.size() > 0) {
					for (int i = 0; i < planCollection.size(); i++) {
						FDCProDepConPayPlanInfo planInfo = planCollection.get(i);
						if (FDCBillStateEnum.SAVED.equals(planInfo.getState()) || FDCBillStateEnum.SUBMITTED.equals(planInfo.getState())
								|| FDCBillStateEnum.AUDITTING.equals(planInfo.getState())) {
							if ("提示控制".equals(CONTROLNOCONTRACT)) {
								int result = FDCMsgBox.showConfirm2(this, "当月的项目月度付款计划未审批，是否继续？");
								if (result != FDCMsgBox.OK) {
									SysUtil.abort();
								}
							} else {
								FDCMsgBox.showWarning("该项目的“项目月度付款计划”未审批，请先审批。");
								abort();
							}
						}
					}
				} else {
					
					if ("提示控制".equals(CONTROLNOCONTRACT)) {
						int showConfirm2 = FDCMsgBox.showConfirm2(this, "当前单据为无计划付款，是否确认并继续提交？");
						if (showConfirm2 != FDCMsgBox.OK) {
							abort();
						}
					}else{
						FDCMsgBox.showWarning("该项目未做“项目月度付款计划”，请先做计划。");
						abort();
					}
					
				}
			} catch (EASBizException e) {
				handUIExceptionAndAbort(e);
			} catch (BOSException e) {
				handUIExceptionAndAbort(e);
			}

		}
	}

	private void afterOnload() throws BOSException, EASBizException {		
		
		txtAllInvoiceAmt.setMaximumValue(FDCHelper.MAX_DECIMAL);
		
		//设置合同类型F7
//		prmtContractType.setSelector(new ContractTypePromptSelector(this));
		
		//根据参数是否显示合同费用项目
		if(!isShowCharge){
			this.conConCharge.setVisible(false);
			this.prmtConCharge.setRequired(false);
			this.actionViewBgBalance.setVisible(false);
			this.actionViewBgBalance.setEnabled(false);
			this.menuItemViewBgBalance.setVisible(false);
			this.menuItemViewBgBalance.setEnabled(false);
		}else{
			this.conConCharge.setVisible(true);
			this.prmtConCharge.setRequired(true);
			this.actionViewBgBalance.setVisible(true);
			this.actionViewBgBalance.setEnabled(true);
			this.menuItemViewBgBalance.setVisible(true);
			this.menuItemViewBgBalance.setEnabled(true);
		}
		//只显示已启用的合同费用项目
		EntityViewInfo view = this.prmtConCharge.getEntityViewInfo();
		if(view == null){
			view = new EntityViewInfo();
		}
		FilterInfo filterCharge = new FilterInfo();
		filterCharge.getFilterItems().add(new FilterItemInfo("isEnabled",new Integer(1)));
		view.setFilter(filterCharge);
		this.prmtConCharge.setEntityViewInfo(view);
		
		OrgUnitInfo orgUnitInfo = SysContext.getSysContext().getCurrentOrgUnit();
		EntityViewInfo v = new EntityViewInfo();
		FilterInfo filter  = new FilterInfo();
		//当前可用的工程项目
		Set projLeafNodesIdSet = (Set)getUIContext().get("projLeafNodesIdSet");
		if(projLeafNodesIdSet!=null && projLeafNodesIdSet.size()>0){
			filter.getFilterItems().add(new FilterItemInfo("id", projLeafNodesIdSet, CompareType.INCLUDE));			
		}else{
			filter.getFilterItems().add(new FilterItemInfo("isLeaf",new Integer(1)));
			filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.longNumber", orgUnitInfo.getLongNumber() + "%",CompareType.LIKE));
			filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.isCostOrgUnit", Boolean.TRUE));
			filter.getFilterItems().add(new FilterItemInfo("id", " select b.fid from T_FDC_ProjectWithCostCenterOU a "
					+" inner join t_fdc_curproject p on a.fcurprojectid=p.fid "
					+" inner join t_fdc_curproject b on charindex(p.FLongNumber || '!',b.FLongNumber || '!')=1 "
								+" where p.flevel=1 ", CompareType.INNER));
		}

		v.setFilter(filter);
		prmtcurProject.setEntityViewInfo(v);
		
		txtNumber.setMaxLength(80);
    	txtbillName.setMaxLength(80);
    	txtNoPaidReason.setMaxLength(40);
    	txtInvoiceNumber.setMaxLength(80);

    	txtMoneyDesc.setLineWrap(true);
    	this.cbPeriod.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7PeriodQuery");
    	
    	//增加原币金额的可录入范围
//        txtamount.setPrecision(2);
        txtamount.setMinimumValue(FDCHelper.MIN_VALUE);
        txtamount.setMaximumValue(FDCHelper.MAX_VALUE.multiply(FDCHelper.TEN));
        if(txtamount.getText()==null||"".equals(txtamount.getText())){
        	txtamount.setValue(FDCHelper.ZERO);
        }
        txtBcAmount.setPrecision(2);
        txtBcAmount.setMinimumValue(FDCHelper.MIN_VALUE);
        txtBcAmount.setMaximumValue(FDCHelper.MAX_VALUE.multiply(FDCHelper.TEN));
        
//        txtexchangeRate.setPrecision(2);
        txtexchangeRate.setMinimumValue(FDCHelper.MIN_VALUE);
        txtexchangeRate.setMaximumValue(FDCHelper.MAX_VALUE.multiply(FDCHelper.TEN));
    	
        txtcompletePrjAmt.setPrecision(2);
        //txtcompletePrjAmt.setMinimumValue(FDCHelper.MIN_TOTAL_VALUE);
        txtcompletePrjAmt.setMinimumValue(FDCHelper.ZERO);
        txtcompletePrjAmt.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE.multiply(FDCHelper.TEN));
        
    	if(isFirstLoad) isFirstLoad=false;
    	loadPayRequestBillContent();
    	if(!STATUS_ADDNEW.equals(getOprtState())) {
	    	//add by jianxing_zhou
			addListener();
			//提交后,不允许修改项目
			if (!this.editData.getState().getValue().equals(FDCBillStateEnum.SAVED_VALUE)) {
				this.prmtcurProject.setEnabled(false);
			}
    	}
        
    	this.txtPaymentProportion.setEditable(false);
		this.txtcompletePrjAmt.setEditable(false);		
		this.cbPeriod.setEnabled(false);
		
		if (!getOprtState().equals(OprtState.ADDNEW) && !getOprtState().equals(OprtState.EDIT)) {
			chkUrgency.setEnabled(false);
			if (getOprtState().equals(OprtState.VIEW)) {
				chkCostsplit.setEnabled(false);
			}
			
			chkNeedPaid.setEnabled(false);
			txtNoPaidReason.setEnabled(false);
			prmtAccount.setEnabled(false);
		}
		
		String cuId = editData.getCU().getId().toString();	
		FDCClientUtils.setRespDeptF7(prmtuseDepartment, this, canSelecOtherOrgPerson() ? null :cuId);
		//初始化供应商F7
		FDCClientUtils.initSupplierF7(this, prmtreceiveUnit, cuId);

		//在启用财务成本一体化参数时，若勾选“无需付款”，则出来“贷方科目”字段，审批后，该字段金额自动填入“付款科目”，相应的付款单亦自动“已付款”状态；
		//若不启用一体化参数，若勾选“无需付款”，无文本合同审批后，对应的付款单自动变为“已付款”。
		if(isFinacial){
			kDLabelContainer16.setVisible(true);
		}else{
			kDLabelContainer16.setVisible(false);
		}
		
	    setPrecision();
		
		txtBank.setRequired(false);
		txtBankAcct.setRequired(false); 
		
		//无文本合同录入时设置合同类型为必录，付款类型非必录
		prmtContractType.setRequired(true);
		prmtPayment.setRequired(true);
		prmtsettlementType.setRequired(false);
		handleOldData();
		this.actionPrint.setEnabled(true);
		this.actionPrint.setVisible(true);
		this.actionPrintPreview.setEnabled(true);
		this.actionPrintPreview.setVisible(true);
        
        //业务日期判断为空时取期间中断
        if(pkbookedDate!=null&&pkbookedDate.isSupportedEmpty()){
            pkbookedDate.setSupportedEmpty(false);
        }
		//累计发票金额
        txtInvoiceAmt.setPrecision(2);
		txtInvoiceAmt.setSupportedEmpty(true);
		txtAllInvoiceAmt.setSupportedEmpty(true);
		txtInvoiceAmt.setMinimumValue(FDCHelper.MIN_VALUE);
		txtInvoiceAmt.setEnabled(false);
		txtAllInvoiceAmt.setEnabled(false);
        txtInvoiceAmt.setMaximumValue(FDCHelper.MAX_VALUE.multiply(FDCHelper.TEN));
        
		txtInvoiceAmt.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent eventObj) {
				BigDecimal invoiceAmt = txtInvoiceAmt.getBigDecimalValue();
				txtAllInvoiceAmt.setValue(allInvoiceAmt.add(FDCHelper.toBigDecimal(invoiceAmt)));
			}
		});
		/**
		 * 使发票号是否必录与参数的值关联，当参数值为false时，发票号为非必录项。
		 * -- by jiadong 2010-06-29
		 */
//		if(isInvoiceRequired){
			txtInvoiceNumber.setRequired(isInvoiceRequired);
//		}
			
		this.btnViewBudget.setEnabled(true);
		// 计划项目
		try {
			initFDCDepConPayPlanF7();
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
	}

	/**
	 * 加载付款申请单内容
	 */
	private void loadPayRequestBillContent() throws BOSException {
		if (STATUS_ADDNEW.equals(getOprtState())) {
			return;
		} else {
			EntityViewInfo evi = new EntityViewInfo();
			evi.getSelector().add(new SelectorItemInfo("*"));
			evi.getSelector().add(new SelectorItemInfo("useDepartment.*"));
			evi.getSelector().add(new SelectorItemInfo("currency.*"));
			evi.getSelector().add(new SelectorItemInfo("supplier.*"));
			evi.getSelector().add(new SelectorItemInfo("settlementType.*"));
			evi.getSelector().add(new SelectorItemInfo("curProject.*"));
			evi.getSelector().add(new SelectorItemInfo("paymentType.*"));
			evi.getSelector().add(new SelectorItemInfo("realSupplier.*"));
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(new FilterItemInfo("contractId", editData.getId().toString()));
			evi.setFilter(filterInfo);
			PayRequestBillCollection prbc = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(evi);
			if (prbc.size() != 0) {
				this.prbi = prbc.get(0);
			} else {
				/* 如果没有对应的付款申请单，不用填充以下控件的值了。因为已经去掉了保存时自动生成付款申请单功能。
				 * 因为也有客户通过BOTP推式生成无文本合同，BOTP会调用后台的保存方法，那里没有传入付款申请单值过去
				 */
				return;
			}
//			this.prmtPayment.setData(prbi.getPaymentType());
			this.prmtuseDepartment.setData(prbi.getUseDepartment());
			this.txtPaymentRequestBillNumber.setText(prbi.getNumber());
			this.pksignDate.setValue(prbi.getPayDate());
			this.prmtrealSupplier.setData(prbi.getRealSupplier());
			this.prmtcurrency.setData(prbi.getCurrency());
			if (prbi.getExchangeRate() != null) {
				this.txtexchangeRate.setText(prbi.getExchangeRate().toString());
			}
			if (prbi.getCapitalAmount() != null) {
				this.txtcapitalAmount.setText(prbi.getCapitalAmount());
			} else {
				BigDecimal localamount = editData.getAmount();
				if (localamount.compareTo(FDCConstants.ZERO) != 0) {
					localamount = localamount.setScale(2, BigDecimal.ROUND_HALF_UP);
				}
				this.txtcapitalAmount.setText(FDCClientHelper.getChineseFormat(localamount, false));
			}
			if (prbi.getPaymentProportion() != null) {
				this.txtPaymentProportion.setValue(prbi.getPaymentProportion());
			}
			if (prbi.getCompletePrjAmt() != null) {
				txtcompletePrjAmt.setValue(prbi.getCompletePrjAmt());
			}
			this.txtMoneyDesc.setText(prbi.getMoneyDesc());
			if (prbi.getOriginalAmount() != null) {
				txtamount.setValue(prbi.getOriginalAmount());
			}

			if (chkUrgency != null) {
				if (prbi.getUrgentDegree() == UrgentDegreeEnum.URGENT) {
					chkUrgency.setSelected(true);
				} else {
					chkUrgency.setSelected(false);
				}
			}

			this.txtattachment.setText(String.valueOf(prbi.getAttachment()));
			txtBankAcct.setText(prbi.getRecAccount());
			txtBank.setText(prbi.getRecBank());
		}
	}
	
    protected void prmtcurProject_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	if(!this.isIncorporation){
    		return ;
    	}
    	
    	if(e.getNewValue()!=null
    			&& !GlUtils.isEqual(e.getOldValue() ,e.getNewValue())		
    	){
    		CurProjectInfo projectInfo = (CurProjectInfo)e.getNewValue();
    		curProject = projectInfo;
    		editData.setCurProject(projectInfo);
    		
    		//bookedDate_dataChanged(e);
    		getUIContext().put("projectId",BOSUuid.read(curProject.getId().toString()));
    		this.fetchInitData();
    		
			editData.setBookedDate(bookedDate);
			editData.setPeriod(curPeriod);
			
			pkbookedDate.setValue(bookedDate);
			cbPeriod.setValue(curPeriod);
    	}
    }

    protected void bookedDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	if(editData.getCurProject()==null){
    		return ;
    	}
    	String projectId = this.editData.getCurProject().getId().toString();    	
    	fetchPeriod(e,pkbookedDate,cbPeriod, projectId,  false);
    	
    	prmtcurrency_dataChanged(null);
    }
    
    protected void beforeStoreFields(ActionEvent e) throws Exception {
    	setAmount();
    	super.beforeStoreFields(e);
    	this.prbi.setPaymentType((PaymentTypeInfo)prmtPayment.getData());//付款类型
    	this.prbi.setUseDepartment((AdminOrgUnitInfo)prmtuseDepartment.getData());//用款部门
    	this.prbi.setNumber(txtPaymentRequestBillNumber.getText());//付款申请单编码
    	if(FDCHelper.isEmpty(prbi.getNumber())){
    		this.prbi.setNumber(txtNumber.getText());
    	}
    	this.prbi.setPayDate(pksignDate.getSqlDate());//付款日期
    	this.prbi.setSupplier((SupplierInfo)prmtreceiveUnit.getData());//收款单位
    	this.prbi.setRealSupplier((SupplierInfo)prmtrealSupplier.getData());//实际收款单位
    	this.prbi.setSettlementType((SettlementTypeInfo)prmtsettlementType.getData());//结算方式    	
    	this.prbi.setRecBank(txtBank.getText());//收款银行
    	this.prbi.setRecAccount(txtBankAcct.getText());//收款帐号
    	//是否加急界面改成chkbox
    	if(this.chkUrgency.isSelected()){
    		this.prbi.setUrgentDegree(UrgentDegreeEnum.URGENT);
    	}else{
    		this.prbi.setUrgentDegree(UrgentDegreeEnum.NORMAL);
    	}
//    	this.prbi.setUrgentDegree((UrgentDegreeEnum)combUrgentDegree.getSelectedItem());//紧急程度
    	this.prbi.setCurrency((CurrencyInfo)prmtcurrency.getData());//币别
    	this.prbi.setLatestPrice(txtBcAmount.getBigDecimalValue());//最新造价
    	this.prbi.setExchangeRate(txtexchangeRate.getBigDecimalValue());//汇率
    	this.prbi.setCapitalAmount(txtcapitalAmount.getText());//大写金额
    	this.prbi.setPaymentProportion(txtPaymentProportion.getBigDecimalValue());//付款比例
    	this.prbi.setCompletePrjAmt(txtcompletePrjAmt.getBigDecimalValue());//已完工工程量金额
    	this.prbi.setAmount(txtBcAmount.getBigDecimalValue());//原币金额
    	this.prbi.setOriginalAmount(txtamount.getBigDecimalValue());//原币金额
//    	this.prbi.setMoneyDesc(txtDescription.getText());//备注
//    	prbi.setUsage(txtMoneyDesc.getText());//款项说明
    	//提单：无文本合同提交后，再查看该单据时，填写的“款项说明字段信息”查看不到   以上注释行导致 by Cassiel_peng 2009-10-10 
    	this.prbi.setMoneyDesc(txtMoneyDesc.getText());//款项说明
    	this.prbi.setUsage(txtMoneyDesc.getText());//用途  也不晓得要赋个啥值，看之前注释的那两行代码也是想把款项说明的值赋给它，那就这样吧！
    	
    	if(txtattachment.getIntegerValue()!=null){
    		this.prbi.setAttachment(txtattachment.getIntegerValue().intValue());//附件
    	}
    	this.prbi.setCurProject(this.editData.getCurProject());//工程项目
    	prbi.setSourceType(SourceTypeEnum.ADDNEW);
    	prbi.setInvoiceNumber(txtInvoiceNumber.getText());//发票号
    	prbi.setInvoiceDate(pkInvoiceDate.getSqlDate());//发票日期
    	prbi.setInvoiceAmt(txtInvoiceAmt.getBigDecimalValue());//发票金额
		prbi.setInvoiceOriAmt(FDCHelper.divide(txtInvoiceAmt.getBigDecimalValue(), txtexchangeRate.getBigDecimalValue(), 2, BigDecimal.ROUND_HALF_UP));// 发票原币金额
    	prbi.setAllInvoiceAmt(txtAllInvoiceAmt.getBigDecimalValue());//累计发票金额
		prbi.setAllInvoiceOriAmt(prbi.getInvoiceOriAmt());// 累计发票原币金额
		this.editData.put("PayRequestBillInfo", prbi);// 传值
    	
    	// 需求:无文本合同序时簿和无文本合同查询序时簿界面增加用款部门列,以前是单独保存在付款申请单上的一个用款部门，满足不了需求   by cassiel_peng 2011-03-15
    	this.editData.setUseDepartment((AdminOrgUnitInfo)prmtuseDepartment.getData());//用款部门

    	//业务日期默认未设置，如果预算控制时以业务日期控制，会导致打印预览中显示的业务日期无内容，手动设置一下
    	editData.setBizDate((Date)this.pkbookedDate.getValue());// added by Owen_wen
    }
    public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		//合同类型不能为空
		if(prmtContractType.getData() == null)
    	{
    		MsgBox.showWarning("合同类型不能为空！");
    		SysUtil.abort() ;
    	}
		//付款类型不能为空
		if(prmtPayment.getData() == null)
		{
			MsgBox.showWarning("付款类型不能为空！");
    		SysUtil.abort() ;
		}
    	super.actionSave_actionPerformed(e);
//    	ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextInfo()
//    	PayRequestBillFactory.getRemoteInstance().addnew(this.prbi);
		modify=false;
    }
	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
	
		
		//附件数据会在点击修改的时候丢失，是因为没有绑定
		String obj = txtattachment.getText();
		super.actionEdit_actionPerformed(e);
		txtBankAcct.setText(editData.getBankAcct());
		
		chkUrgency.setEnabled(true);
		chkCostsplit.setEnabled(true);
		
		chkNeedPaid.setEnabled(true);
		if(chkNeedPaid.isSelected()){
			txtNoPaidReason.setEnabled(true);
			prmtAccount.setEnabled(true);
		}else{
			txtNoPaidReason.setEnabled(false);	
			prmtAccount.setEnabled(false);
			txtNoPaidReason.setText(null);
			prmtAccount.setValue(null);
		}
		
		if(!this.editData.getState().getValue().equals(FDCBillStateEnum.SAVED_VALUE)){
			this.prmtcurProject.setEnabled(false);
		};
		this.txtattachment.setText(obj);
    	txtPaymentProportion.setEditable(false);
		txtcompletePrjAmt.setEditable(false);
		setAmount();
		prmtrealSupplier.setEnabled(true);
		this.menuBiz.setVisible(false);

		//new update by renliang at 2010-5-12
		if(this.isNotEnterCAS){
			chkNeedPaid.setEnabled(false);
			chkNeedPaid.setSelected(true);
		}
	}
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		//合同类型不能为空
		if(prmtContractType.getData() == null)
    	{
    		MsgBox.showWarning("合同类型不能为空！");
    		SysUtil.abort() ;
    	}
		//付款类型不能为空
		if(prmtPayment.getData() == null)
		{
			MsgBox.showWarning("付款类型不能为空！");
    		SysUtil.abort() ;
		}
		
		super.actionSubmit_actionPerformed(e);
		modify=false;
		
		//new update by renliang at 2010-5-12
		if(isNotEnterCAS){
			this.chkNeedPaid.setEnabled(false);
			this.chkNeedPaid.setSelected(true);
		}
		
		if (prmtPlanProject.getValue() == null) {
			kdDepPlanState.setSelectedItem(DepPlanStateEnum.noPlan);
		}
	}
	
    protected void chkNeedPaid_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	if(chkNeedPaid.isSelected()){
    		txtNoPaidReason.setEnabled(true);
    		prmtAccount.setEnabled(true);
    		prmtreceiveUnit.setRequired(false);
    		prmtrealSupplier.setRequired(false);
    		txtBank.setRequired(false);
    		txtBankAcct.setRequired(false); 
    		prmtsettlementType.setRequired(false);
    		prmtPlanProject.setRequired(false);
    	}else{
    		txtNoPaidReason.setText(null);
    		txtNoPaidReason.setEnabled(false);
    		prmtAccount.setEnabled(false);
    		prmtAccount.setValue(null);
    		prmtreceiveUnit.setRequired(true);
    		prmtrealSupplier.setRequired(true);
    		if (!"不控制".equals(CONTROLNOCONTRACT) && chkCostsplit.isSelected()) {
    		prmtPlanProject.setRequired(false);
    		} else {
				prmtPlanProject.setRequired(false);
			}
//    		txtBank.setRequired(true);
//    		txtBankAcct.setRequired(true);
//    		prmtsettlementType.setRequired(true);
    	}
    	txtBank.repaint();
    	txtBankAcct.repaint();
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
		if(STATUS_ADDNEW.equals(oprtType)) {
			prmtcurProject.setEnabled(true);
		}
		else {
			prmtcurProject.setEditable(false);
		}
		txtPaymentRequestBillNumber.setEditable(false);
	}

	/**
     * output actionFirst_actionPerformed
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFirst_actionPerformed(e);
        //之前调用onLoad 会导致父类CoreBillEditUI中多次增加目录菜单“传阅意见查看”
        //故把onLoad后续方法抽象出来 单独执行
        afterOnload();
        prmtrealSupplier.setEnabled(false);
    }
    
    
    /**
     * output actionPre_actionPerformed
     */
    public void actionPre_actionPerformed(ActionEvent e) throws Exception
    {
    	super.actionPre_actionPerformed(e);
        //之前调用onLoad 会导致父类CoreBillEditUI中多次增加目录菜单“传阅意见查看”
        //故把onLoad后续方法抽象出来 单独执行
    	afterOnload();
    	prmtrealSupplier.setEnabled(false);
    }

    /**
     * output actionNext_actionPerformed
     */
    public void actionNext_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNext_actionPerformed(e);
        //之前调用onLoad 会导致父类CoreBillEditUI中多次增加目录菜单“传阅意见查看”
        //故把onLoad后续方法抽象出来 单独执行
        afterOnload();
        prmtrealSupplier.setEnabled(false);
    }

    /**
     * output actionLast_actionPerformed
     */
    public void actionLast_actionPerformed(ActionEvent e) throws Exception
    {
    	super.actionLast_actionPerformed(e);
        //之前调用onLoad 会导致父类CoreBillEditUI中多次增加目录菜单“传阅意见查看”
        //故把onLoad后续方法抽象出来 单独执行
    	afterOnload();
    	prmtrealSupplier.setEnabled(false);
    }

	protected void prmtreceiveUnit_dataChanged(DataChangeEvent e) throws Exception {
		if(e.getNewValue() != null) {
			prmtrealSupplier.setData(prmtreceiveUnit.getData());
			prmtrealSupplier.setEnabled(true);
		}else{
			prmtrealSupplier.setData(null);
			prmtrealSupplier.setEnabled(false);
		}
	}

	/**
	 * output prmtrealSupplier_dataChanged method 编辑 andy_liu 2012-6-18
	 */
    protected void prmtrealSupplier_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
		Object newValue = e.getNewValue();
		if (newValue instanceof SupplierInfo) {
			// 首次加载
			if (isFirstLoad && !getOprtState().equals(OprtState.ADDNEW))
				return;
			// 用newValue.equalse(e.getOldValue()) 会出错,因为比较的是堆栈的值
			if ((e.getOldValue() instanceof SupplierInfo)
					&& ((SupplierInfo) e.getOldValue()).getId().equals(((SupplierInfo) newValue).getId())
					&& !getOprtState().equals(OprtState.ADDNEW)) {
				return;
			}

			SupplierInfo supplier = (SupplierInfo) newValue;
			BOSUuid supplierid = supplier.getId();

			// 供应商的获取
			String supperid = supplierid.toString();
			CompanyOrgUnitInfo companyOrgUnitInfo = SysContext.getSysContext().getCurrentFIUnit();
			String companyorgunitID = (companyOrgUnitInfo == null ? null : companyOrgUnitInfo.getId().toString());
			PayReqUtils.fillBank(editData, supperid, companyorgunitID);
			txtBankAcct.setText(editData.getBankAcct());
			txtBank.setText(editData.getBank());
		}
	
    }
	protected void setFieldsNull(AbstractObjectValue newData) {
		super.setFieldsNull(newData);
		ContractWithoutTextInfo info = (ContractWithoutTextInfo)newData;
		info.setCurProject(editData.getCurProject());
	}

	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add("curProject.id");
		sic.add("curProject.displayName");
		sic.add("curProject.CU.number");
		sic.add(new SelectorItemInfo("curProject.fullOrgUnit.name"));
		
		
		sic.add("orgUnit.id");
		sic.add("state");
		sic.add("currency.number");
		sic.add("currency.name");
		sic.add("amount");
		sic.add("originalAmount");
		
		sic.add(new SelectorItemInfo("period.number"));
		sic.add(new SelectorItemInfo("period.periodNumber"));
		sic.add(new SelectorItemInfo("period.periodYear"));
		sic.add(new SelectorItemInfo("period.beginDate"));
		
		sic.add("fdcDepConPlan.id");
		sic.add("fdcDepConPlan.head.number");
		sic.add("fdcDepConPlan.head.name");
		sic.add("lastUpdateTime");
		sic.add(new SelectorItemInfo("bankAcct"));

		// ////////////////////////////////////////////////////////////////////////////////
		// ////////////////////////////////////////////////////////////////////////////////

		sic.add(new SelectorItemInfo("curProject.id"));
		sic.add(new SelectorItemInfo("curProject.name"));
		sic.add(new SelectorItemInfo("curProject.number"));
		sic.add(new SelectorItemInfo("curProject.codingNumber"));
		sic.add(new SelectorItemInfo("curProject.displayName"));
		sic.add(new SelectorItemInfo("curProject.fullOrgUnit.name"));
		sic.add(new SelectorItemInfo("curProject.costCenter"));

		sic.add(new SelectorItemInfo("curProject.parent.id"));
		sic.add(new SelectorItemInfo("curProject.parent.number"));
		sic.add(new SelectorItemInfo("curProject.parent.name"));

		sic.add(new SelectorItemInfo("currency.number"));
		sic.add(new SelectorItemInfo("currency.name"));
		sic.add(new SelectorItemInfo("currency.precision"));

		sic.add(new SelectorItemInfo("CU.id"));
		sic.add(new SelectorItemInfo("CU.name"));
		sic.add(new SelectorItemInfo("CU.number"));
		sic.add(new SelectorItemInfo("CU.code"));
		sic.add(new SelectorItemInfo("orgUnit.id"));
		sic.add(new SelectorItemInfo("orgUnit.name"));
		sic.add(new SelectorItemInfo("orgUnit.number"));
		sic.add(new SelectorItemInfo("orgUnit.code"));
		
		sic.add(new SelectorItemInfo("contractType.id"));
		sic.add(new SelectorItemInfo("contractType.isLeaf"));
		sic.add(new SelectorItemInfo("contractType.level"));
		sic.add(new SelectorItemInfo("contractType.number"));
		sic.add(new SelectorItemInfo("contractType.longnumber"));
		sic.add(new SelectorItemInfo("contractType.name"));
		sic.add(new SelectorItemInfo("contractType.isRefProgram"));
		sic.add(new SelectorItemInfo("contractType.isWorkLoadConfirm"));

		sic.add(new SelectorItemInfo("codeType.id"));
		sic.add(new SelectorItemInfo("codeType.name"));
		sic.add(new SelectorItemInfo("codeType.number"));
		sic.add(new SelectorItemInfo("codeType.thirdType"));
		sic.add(new SelectorItemInfo("codeType.secondType"));

		// ////////////////////////////////////////////////////////////////////////////////
		// ////////////////////////////////////////////////////////////////////////////////

		FdcObjectCollectionUtil.clearDuplicate(sic);

		return sic;
	}
	   
    
    protected void controlDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e,ControlDateChangeListener listener) throws Exception
    {
    	if("bookedDate".equals(listener.getShortCut())){
    		bookedDate_dataChanged(e);
    	}
    }
    
    //业务日期变化事件
    ControlDateChangeListener bookedDateChangeListener = new ControlDateChangeListener("bookedDate");
    
    
    protected void attachListeners() {   	
    	pkbookedDate.addDataChangeListener(bookedDateChangeListener);
    	
    	addDataChangeListener(txtamount);
    	//Add by zhiyuan_tang 2010/07/20 
    	//解决汇率光标离开，发票金额、本位币金额、本期完工工程量计算错误，添加汇率的dataChange事件
    	addDataChangeListener(txtexchangeRate);
    	
    	addDataChangeListener(txtPaymentProportion);
    	addDataChangeListener(prmtcurProject);
    	addDataChangeListener(prmtcurrency);
    	addDataChangeListener(prmtrealSupplier);
    	// addDataChangeListener(prmtreceiveUnit);
    }
    
    protected void detachListeners() {
    	pkbookedDate.removeDataChangeListener(bookedDateChangeListener);    	
    	removeDataChangeListener(txtamount);
    	removeDataChangeListener(txtPaymentProportion);
    	removeDataChangeListener(prmtcurProject);
    	removeDataChangeListener(prmtcurrency);
    	removeDataChangeListener(prmtrealSupplier);
    	// removeDataChangeListener(prmtreceiveUnit); // 默认实际收款单位等于收款单位全称
    }
		
	public void loadFields() {
		
		//loadFields 最好注销监听器,以免loadFields触发事件
		detachListeners();
		
		super.loadFields();
		setSaveActionStatus();
		if (getOprtState().equals(OprtState.ADDNEW)) { //新增	
			this.menuBiz.setVisible(false);
			chkNeedPaid.setSelected(false);
			chkUrgency.setSelected(false);
			
			if(paymentTypes!=null && paymentTypes.size()>0){
				prmtPayment.setData(paymentTypes.get(0));
			}
			prmtrealSupplier.setEnabled(false);			
		}
		
		txtPaymentProportion.setValue(new BigDecimal(100));
		txtcompletePrjAmt.setValue(txtBcAmount.getBigDecimalValue());
		
		if (editData != null && editData.getCurProject() != null) {		
			
			txtOrg.setText(this.orgUnitInfo.getDisplayName());
			editData.setOrgUnit(orgUnitInfo);
			editData.setCU(editData.getCurProject().getCU());
		}
		
		//币别选择
		if (this.editData.getCurrency() != null) {
			if (editData.getCurrency().getId().toString().equals(baseCurrency.getId().toString())) {
				// 是本位币时将汇率选择框置灰
				txtexchangeRate.setValue(FDCConstants.ONE);
				txtexchangeRate.setRequired(false);
				txtexchangeRate.setEditable(false);
				txtexchangeRate.setEnabled(false);
			}
		}
		
		//无需付款选择变化
		try {
			chkNeedPaid_actionPerformed(null);
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
		
	    attachListeners() ;
	    if(STATUS_EDIT.equals(getOprtState()) && !this.actionAudit.isVisible()
	    		&& !this.actionUnAudit.isVisible()){
	    	this.menuBiz.setVisible(false);
	    }
	    
		//收款单位修改成F7选择，但是元数据定义的是自有属性， 所以没有进行数据绑定，需要手动装载。 
		if (editData.getReceiveUnit() != null) {
			txtBankAcct.setValue(getSupplierCompanyBankInfoByAccount(editData.getReceiveUnit().getId().toString(), editData.getBank(),
					editData.getBankAcct()));
		}
		txtBankAcct.setText(editData.getBankAcct());
	}

	/**
	 * 描述：根据供应商，银行名称，帐号来获取SupplierCompanyBankInfo对象
	 * @param supplierId
	 * @param bankName
	 * @param account
	 * @return
	 */
	private SupplierCompanyBankInfo getSupplierCompanyBankInfoByAccount(String supplierId, String bankName, String account) {
		if (supplierId == null || bankName == null || account == null) {
			return null;
		}

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("supplierCompanyInfo.supplier.id", supplierId));
		filter.getFilterItems().add(new FilterItemInfo("bank", bankName));
		filter.getFilterItems().add(new FilterItemInfo("bankAccount", account));
		view.setFilter(filter);
		SupplierCompanyBankCollection col = null;
		try {
			col = SupplierCompanyBankFactory.getRemoteInstance().getSupplierCompanyBankCollection(view);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
		if (col != null && col.size() > 0) {
			SupplierCompanyBankInfo info = col.get(0);
			return info;
		}
		return null;
	}
	
	public void storeFields() {
		super.storeFields();
		//收款帐号修改成F7选择，但是元数据定义的是自有属性， 所以没有进行数据绑定，需要手动保存一下。
		if (txtBankAcct.getValue() instanceof String || txtBankAcct.getText() instanceof String) {
			editData.setBankAcct(txtBankAcct.getText());
		} else if (txtBankAcct.getValue() instanceof SupplierCompanyBankInfo) {
			SupplierCompanyBankInfo info = (SupplierCompanyBankInfo) txtBankAcct.getValue();
			editData.setBankAcct(info.getBankAccount());
		} else {
			editData.setBankAcct(null);
		}
	}
	
	protected void txtBankAcct_willCommit(CommitEvent e) throws Exception {
		setRecAccountFilter();
	}

	protected void txtBankAcct_willShow(SelectorEvent e) throws Exception {
		setRecAccountFilter();
	}

	/**
	 * 描述：根据供应商ID，设置收款帐号的过滤信息
	 */
	private void setRecAccountFilter() {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		SupplierInfo supplier = (SupplierInfo) prmtrealSupplier.getValue();

		if (supplier != null) {
			filter.getFilterItems().add(new FilterItemInfo("supplier.id", supplier.getId().toString()));
			//按财务组织过滤
			CompanyOrgUnitInfo companyOrgUnitInfo = SysContext.getSysContext().getCurrentFIUnit();
			String companyorgunitID = (companyOrgUnitInfo == null ? null : companyOrgUnitInfo.getId().toString());
			filter.getFilterItems().add(new FilterItemInfo("COMPANYORGUNIT.ID", companyorgunitID));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("id", null));
		}
		view.setFilter(filter);
		txtBankAcct.setEntityViewInfo(view);
		txtBankAcct.getQueryAgent().resetRuntimeEntityView();
	}
	
	protected void txtBankAcct_dataChanged(DataChangeEvent e) throws Exception {

		if (e.getNewValue() != null && !e.getNewValue().equals(e.getOldValue()) && e.getNewValue() instanceof SupplierCompanyBankInfo) {
			SupplierCompanyBankInfo acctbank = (SupplierCompanyBankInfo) e.getNewValue();
			txtBankAcct.setText(acctbank.getBankAccount());
			txtBank.setText(acctbank.getBank());
			this.editData.setBank(acctbank.getBank());
			this.editData.setBankAcct(acctbank.getBankAccount());
		}
	}
	
	//设置精度
	protected void setPrecision(){
		CurrencyInfo currency = editData.getCurrency();	    	
    	Date bookedDate = (Date)editData.getBookedDate();
    	
    	ExchangeRateInfo exchangeRate = null;
		try {
			exchangeRate = FDCClientHelper.getLocalExRateBySrcCurcy(this, currency.getId(),company,bookedDate);
		} catch (Exception e) {			
			handUIExceptionAndAbort(e);
		} 
    	
    	int curPrecision = FDCClientHelper.getPrecOfCurrency(currency.getId());
    	int exPrecision = curPrecision;
    	
    	if(exchangeRate!=null){
    		exPrecision = exchangeRate.getPrecision();
    	}
    		
    	txtexchangeRate.setPrecision(exPrecision);
    	BigDecimal exRate =  prbi.getExchangeRate();    
    	if(exRate!=null){
    		txtexchangeRate.setValue(exRate);
    	}
    	txtamount.setPrecision(curPrecision);
      	txtamount.setValue(editData.getOriginalAmount());
	}
	
	protected void verifyInputForSave()throws Exception  {
		super.verifyInputForSave();
		FDCClientVerifyHelper.verifyEmpty(this, txtbillName);
		FDCClientVerifyHelper.verifyEmpty(this, prmtcurProject);
		checkAmountForSave();
		
	}

	protected boolean checkCanSubmit() throws Exception {		
		if(isIncorporation && ((FDCBillInfo)editData).getPeriod()==null){
			MsgBox.showWarning(this,"启用成本月结期间不能为空，请在基础资料维护期间后，重新选择业务日期");
			SysUtil.abort();
		}
		return super.checkCanSubmit();
	}
	
	private void checkAmountForSave(){
		BigDecimal bd = txtamount.getBigDecimalValue();
		if(FDCHelper.toBigDecimal(bd).compareTo(FDCHelper.ZERO) == 0){
			MsgBox.showWarning(this, "原币金额不能为零!");
			txtamount.requestFocus(true);
			SysUtil.abort();
		}
		
	}
	
	private void checkAmountForSubmit() throws BOSException, SQLException{
		// 11.6.24 不进入动态成本，就不鸟这个控制 add by emanon
		// 11.9.19只有进入动态成本，并且需要付款，才控制 edit by emanon
		//不需要付款也要控制了。。ken_liu
		if (chkCostsplit.isSelected()) {
			//			if ("严格控制".equals(CONTROLNOCONTRACT)
			//					|| "提示控制".equals(CONTROLNOCONTRACT)) {
			//				if (prmtPlanProject.getValue() == null) {
			//					int showConfirm2 = FDCMsgBox.showConfirm2(this, "当前单据为无计划付款，是否确认并继续提交？");
			//					if (showConfirm2 != FDCMsgBox.OK) {
			//						abort();
			//					}
			//				}
			//			}
			
			
			if (prmtPlanProject.getValue() == null) { 
				if ("提示控制".equals(CONTROLNOCONTRACT)) {
					int showConfirm2 = FDCMsgBox.showConfirm2(this, "当前单据为无计划付款，是否确认并继续提交？");
					if (showConfirm2 != FDCMsgBox.OK) {
						abort();
					}
				}
				if ("严格控制".equals(CONTROLNOCONTRACT)) {
					MsgBox.showWarning(this, "计划项目不能为空");
					abort();
				}
			}
			
			if (prmtPlanProject.getValue() != null) {
				if ("严格控制".equals(CONTROLNOCONTRACT)) {
					BigDecimal getBgBalance = getBgBalance();
					BigDecimal bcAmount = txtBcAmount.getBigDecimalValue();
					if (bcAmount.compareTo(getBgBalance) > 0) {
						MsgBox.showWarning(this, "申请金额大于本期可用预算，不能提交");
						abort();
					}
				}
				if ("提示控制".equals(CONTROLNOCONTRACT)) {
					BigDecimal getBgBalance = getBgBalance();
					BigDecimal bcAmount = txtBcAmount.getBigDecimalValue();
					if (bcAmount.compareTo(getBgBalance) > 0) {
						int result = MsgBox.showConfirm2(this, "申请金额大于本期可用预算，是否提交？");
						if (result != MsgBox.OK) {
							SysUtil.abort();
						}
					}
				}
			}
			
		}
	
	}
	
	protected void verifyInputForSubmint()throws Exception {
		checkAmountForSubmit();
		checkAmount();
		//预算控制
		checkMbgCtrlBalance();
		super.verifyInputForSubmint();
	}

	private void checkAmount() throws Exception {
    	String projectId = editData.getCurProject().getId().toString();
    	
    	if(editData.isIsCostSplit() && ( FDCContractParamUI.RD_MSG.equalsIgnoreCase(controlType)
    			|| FDCContractParamUI.RD_CONTROL.equalsIgnoreCase(controlType))){
    		BigDecimal amiCost  = null;
    		//获取成本控制金额
    		String msg = null;
    		if(!FDCContractParamUI.RD_DYMIC.equalsIgnoreCase(controlCost)){
    			amiCost  = FDCCostRptFacadeFactory.getRemoteInstance().getAimCost(projectId);
    			msg = "目标成本";
    		}else{
    			amiCost  = FDCCostRptFacadeFactory.getRemoteInstance().getDynCost(projectId);
    			msg = "动态成本";
    		}
    		//获取项目合同签约总金额，审核状态的数据
    		String id = editData.getId()!=null? editData.getId().toString():null;
    		BigDecimal signAmount  = ContractFacadeFactory.getRemoteInstance().getProjectAmount(projectId,id); 
    		
    		if(amiCost.compareTo(signAmount.add(editData.getAmount()))<0){
    			//提示  严格控制
    			FDCMsgBox.showWarning(this,"项目合同签约总金额已经超过了"+msg);
	    		if(!FDCContractParamUI.RD_MSG.equalsIgnoreCase(controlType)){
	    			SysUtil.abort();
	    		}
    		}
    		
    	}
	}
	   // 提交时，控制预算余额
    private void checkMbgCtrlBalance() throws BOSException, EASBizException{
		//根据参数是否显示合同费用项目
        if (!isShowCharge || editData.getConChargeType() == null) {
			return;
		}

		StringBuffer buffer = new StringBuffer("");
		IBgControlFacade iCtrl = BgControlFacadeFactory.getRemoteInstance();
		editData.setBizDate((Date) this.pkbookedDate.getValue());
		BgCtrlResultCollection bgCtrlResultCollection = iCtrl.getBudget(
				FDCConstants.ContractWithoutText, null, editData);

		if (bgCtrlResultCollection != null) {
			for (int j = 0, count = bgCtrlResultCollection.size(); j < count; j++) {
				BgCtrlResultInfo bgCtrlResultInfo = bgCtrlResultCollection
						.get(j);

				BigDecimal balance = bgCtrlResultInfo.getBalance();
				if (balance != null
						&& balance.compareTo(bgCtrlResultInfo.getReqAmount()) < 0) {
					buffer.append(
							bgCtrlResultInfo.getItemName() + "("
									+ bgCtrlResultInfo.getOrgUnitName() + ")")
							.append(
									EASResource.getString(
											FDCConstants.VoucherResource,
											"BalanceNotEnagh")
											+ "\r\n");
				}
			}
		}

		if (buffer.length() > 0) {
			int result = MsgBox.showConfirm2(this, buffer.toString()
					+ "\r\n"
					+ EASResource.getString(FDCConstants.VoucherResource,
							"isGoOn"));
			if (result == MsgBox.CANCEL) {
				SysUtil.abort();
			}
		}
    }
	protected void verifyInput(ActionEvent e) throws Exception {
		if(!chkCostsplit.isSelected()){
			MsgBox.showInfo(this,ContractClientUtils.getRes("NotEntryCost"));
		}
		
		// 如果启用财务一体化,必须录入期间
		if(this.isIncorporation && cbPeriod.getValue()==null){
			MsgBox.showConfirm2(this,"启用成本月结,必须录入期间");
			SysUtil.abort();
		}		
//		checkFDCProDep();
		checkAmountForSave();
		
		super.verifyInput(e);
		
		checkProjStatus();
		
	}
	
	private void checkProjStatus() {
//		如果项目状态已关闭，则不能选择是否成本拆分 
		if(editData != null && editData.getCurProject() != null) {
			BOSUuid id = editData.getCurProject().getId();
			
			SelectorItemCollection selectors = new SelectorItemCollection();
			selectors.add("projectStatus");
			CurProjectInfo curProjectInfo = null;
			try {
				curProjectInfo = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(id), selectors);
				
			
			}catch (Exception ex) {
				handUIExceptionAndAbort(ex);
			}
			
			if(curProjectInfo.getProjectStatus() != null) {
				String closedState = ProjectStatusInfo.closeID;
				String transferState = ProjectStatusInfo.transferID;
				String projStateId = curProjectInfo.getProjectStatus().getId().toString();
				if(projStateId != null && (projStateId.equals(closedState)||projStateId.equals(transferState))) {
					if(chkCostsplit.isSelected()) {
						MsgBox.showWarning(this, "该合同所在的工程项目已经处于关闭或中转状态，不能进入动态成本，请取消\"进入动态成本\"的选择再保存/提交");
						SysUtil.abort();
					}
				}
			}
		}
	}
	
	protected void checkRef(String id) {
		ContractWithoutTextClientUtils.checkRef(this, id);
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
    /**
     * output txtamount_dataChanged method
     */
    protected void txtamount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	super.txtamount_dataChanged(e);
		setAmount();
		setPropPrjAmount("amount",e);
		txtcompletePrjAmt.setValue(txtBcAmount.getBigDecimalValue());
		//added by ken..金额修改，计划付款相应改变
		if(localBudget != null) {
			if (FDCHelper.compareTo(localBudget, FDCHelper.add(usedBudget,txtBcAmount.getBigDecimalValue())) >= 0) {
				kdDepPlanState.setSelectedItem(DepPlanStateEnum.inPlan);
			} else {
				kdDepPlanState.setSelectedItem(DepPlanStateEnum.outPlan);
			}
		}
    }

    /**
     * output txtamount_focusLost method
     */
    protected void txtamount_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output txtNumber_focusLost method
     */
    protected void txtNumber_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    	txtPaymentRequestBillNumber.setText(txtNumber.getText());
    }
    
    /**
     * output prmtcurrency_dataChanged method
     */
    protected void prmtcurrency_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
		super.prmtcurrency_dataChanged(e);
		//Object newValue = e.getNewValue();
		if (e==null || e.getNewValue() instanceof CurrencyInfo)
		{
			if(isFirstLoad || (STATUS_VIEW.equals(getOprtState()) || STATUS_FINDVIEW.equals(getOprtState()))) return;
			CurrencyInfo currency = (CurrencyInfo)prmtcurrency.getValue();	 
			if(currency==null){
				//设置汇率的值，在录入界面点新增时可能的情况
				return;
			}
			BOSUuid srcid = currency.getId();
			Date bookedDate = (Date)pkbookedDate.getValue();
			
	    	ExchangeRateInfo exchangeRate = FDCClientHelper.getLocalExRateBySrcCurcy(this,srcid,company,bookedDate);
	    	
	    	int curPrecision = FDCClientHelper.getPrecOfCurrency(srcid);
	    	BigDecimal exRate = FDCHelper.ONE;
	    	int exPrecision = curPrecision;
	    	
	    	if(exchangeRate!=null){
	    		exRate = exchangeRate.getConvertRate();
	    		exPrecision = exchangeRate.getPrecision();
	    	}
	    	txtexchangeRate.setValue(exRate);
	    	txtexchangeRate.setPrecision(exPrecision);
	    	txtamount.setPrecision(curPrecision);
//			txtexchangeRate.setValue(FDCClientHelper.getLocalExRateBySrcCurcy(this, srcid,company,bookedDate));
	    	
	    	
			setAmount();
			
			if (srcid.toString().equals(baseCurrency.getId().toString())) {
				// 是本位币时将汇率选择框置灰
				txtexchangeRate.setValue(FDCConstants.ONE);
				txtexchangeRate.setRequired(false);
				txtexchangeRate.setEditable(false);
				txtexchangeRate.setEnabled(false);
			}else{
				txtexchangeRate.setRequired(true);
				txtexchangeRate.setEditable(true);
				txtexchangeRate.setEnabled(true);
			}

		}
	
    }

    /**
     * output txtpaymentProportion_dataChanged method
     */
    protected void txtpaymentProportion_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	super.txtpaymentProportion_dataChanged(e);
    	setPropPrjAmount("paymentProp",e);
    }

    /**
     * output txtcompletePrjAmt_dataChanged method
     */
    protected void txtcompletePrjAmt_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	super.txtcompletePrjAmt_dataChanged(e);
    	setPropPrjAmount("completePrjAmt",e);
    }

    /**
     * output txtexchangeRate_focusLost method
     */
    protected void txtexchangeRate_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    	/*Delete by zhiyuan_tang 2010/07/20
    	 * 为解决汇率光标离开，发票金额、本位币金额、本期完工工程量计算错误，添加汇率的dataChange事件。
    	 * 原来focusLost事件的处理也一并移到dataChange事件中
    	*/
//    	super.txtexchangeRate_focusLost(e);
//		setAmount();
    }
    
    //Add by zhiyuan_tang 2010/07/20 
	/**
	 * output txtexchangeRate_focusLost method
	 * @author zhiyuan_tang 2010/07/20 <p>
	 * 解决汇率光标离开，发票金额、本位币金额、本期完工工程量计算错误，添加汇率的dataChange事件<p>
	 */
    protected void txtexchangeRate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	super.txtexchangeRate_dataChanged(e);
		setAmount();
		setPropPrjAmount("amount",e);
		txtcompletePrjAmt.setValue(txtBcAmount.getBigDecimalValue());
    }
    
	/**
	 * Description: 设置本位币金额,大小写等
	 * 
	 * @author sxhong Date 2006-9-6
	 */
	private void setAmount()
	{
		
		BigDecimal amount = (BigDecimal) txtamount.getNumberValue();
		Object exchangeRate = txtexchangeRate.getNumberValue();
		if(amount==null){
			amount = FDCConstants.ZERO;
		}
		if (amount != null)
		{
			Object value = prmtcurrency.getValue();
			boolean isBaseCurrency = false;
			
			if (value instanceof CurrencyInfo)
			{				
				//本位币处理
				CompanyOrgUnitInfo currentFIUnit = SysContext.getSysContext().getCurrentFIUnit();
				CurrencyInfo baseCurrency = currentFIUnit.getBaseCurrency();
				BOSUuid srcid = ((CurrencyInfo) value).getId();

				if(baseCurrency!=null){
					if(srcid.equals(baseCurrency.getId())){
						isBaseCurrency = true;
						/*if (exchangeRate instanceof BigDecimal&&((BigDecimal)exchangeRate).intValue()!=1)
						{
							MsgBox.showWarning(this,"你选择的是本位币，但是汇率不等于1");
						}*/
						txtBcAmount.setValue(amount);
						txtexchangeRate.setValue(new BigDecimal("1"));
						txtexchangeRate.setEditable(false);
						//return;
					}else{
						this.txtexchangeRate.setEditable(true);
						txtexchangeRate.setEnabled(true);
					}
				}

			}
			
			BigDecimal localamount = FDCConstants.ZERO;
			if (!isBaseCurrency && exchangeRate instanceof BigDecimal)
			{
				localamount = amount.multiply((BigDecimal)exchangeRate);				
				txtBcAmount.setValue(localamount);
			}else{
				localamount = amount;
			}
			//发票金额
			
			if (!"view".equalsIgnoreCase(getOprtState())) {
				txtInvoiceAmt.setValue(txtBcAmount.getBigDecimalValue());
				txtAllInvoiceAmt.setValue(txtBcAmount.getBigDecimalValue());
			}
			
			//本期已完工工程量
			txtcompletePrjAmt.setValue(txtBcAmount.getBigDecimalValue());
			//大写金额为本位币金额
			if(localamount.compareTo(FDCConstants.ZERO)!=0){
				localamount = localamount.setScale(2,BigDecimal.ROUND_HALF_UP);
				String cap = FDCClientHelper.getChineseFormat(localamount,false);
				//FDCHelper.transCap((CurrencyInfo) value, amount);
				txtcapitalAmount.setText(cap);
			}else{
				txtcapitalAmount.setText(null);
			}


		}
	}	
    /**
     * 设置付款比例，已完工工程量与原币金额之间的关系：
     * 				已完工工程量金额＝原币金额÷付款比例
     * @author sxhong  		Date 2007-3-12
     */
    private void setPropPrjAmount(String cause,DataChangeEvent e){
    	if(isFirstLoad||(!txtPaymentProportion.isRequired())) return;
//    	if(PayReqUtils.isContractWithoutText(editData.getcon))
    	BigDecimal amount=txtBcAmount.getBigDecimalValue();
    	BigDecimal paymentProp=txtPaymentProportion.getBigDecimalValue();
    	BigDecimal completePrj=txtcompletePrjAmt.getBigDecimalValue();
    	
    	if(amount==null) amount=FDCHelper.ZERO;
    	if(paymentProp==null) paymentProp=FDCHelper.ZERO;
    	if(completePrj==null) completePrj=FDCHelper.ZERO;
    	
    	if(cause.equals("amount")){
    		if(paymentProp.compareTo(FDCHelper.ZERO)==0){
    			if(completePrj.compareTo(FDCHelper.ZERO)==0){
    				return;
    			}else{
    				txtPaymentProportion.setValue(amount.setScale(4).divide(completePrj, BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
    			}
    		}else{
    			txtcompletePrjAmt.setValue(amount.divide(paymentProp, BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
    		}
    	}else if(cause.equals("completePrjAmt")){
    		/*
    		if(completePrj.subtract(amount).compareTo(new BigDecimal("0.01"))<0){
				MsgBox.showWarning(this, "付款比例必须大于0,请重新输入的已完工工程量，保证付款比例＝原币金额/已完工工程量>0 即0.01%");
				if(e.getOldValue() instanceof BigDecimal){
					if(((BigDecimal)e.getOldValue()).subtract(amount).compareTo(new BigDecimal("0.01"))>=0){
						txtcompletePrjAmt.setValue(e.getOldValue());
					}
						
				}
//				SysUtil.abort();
				return;
			}else{
				txtpaymentProportion.setValue(amount.divide(completePrj, BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
			}*/
//    		if(completePrj.compareTo(FDCHelper.ZERO)==0){
//    			MsgBox.showWarning(this, "已完工工程量不能为0");
//    			SysUtil.abort();
//    		}
    		if(completePrj.compareTo(FDCHelper.ZERO)==0){
    			return;
    		}
    		txtPaymentProportion.setValue(amount.setScale(4).divide(completePrj, BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
    	}else{
			if(paymentProp.compareTo(FDCHelper.ZERO)==0){
				return;
			}else{
    			txtcompletePrjAmt.setValue(amount.setScale(4).divide(paymentProp, BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
			}
    	}
    }
    
	
    /**
     * 覆盖抽象类处理编码规则的行为,统一在FDCBillEditUI.handCodingRule方法中处理
     */
    protected void setAutoNumberByOrg(String orgType) {
    	
    }
    protected void prmtsettlementType_dataChanged(DataChangeEvent e)
    		throws Exception {
    	super.prmtsettlementType_dataChanged(e);
		Object objNew=e.getNewValue();
		if(objNew==null) return;
		if(objNew instanceof SettlementTypeInfo
				&&((SettlementTypeInfo)objNew).getId().toString().equals("e09a62cd-00fd-1000-e000-0b32c0a8100dE96B2B8E")){
			//结算方式为付款则银行及账号为非必录
			txtBank.setRequired(false);
			txtBank.repaint();
			txtBankAcct.setRequired(false);
			txtBankAcct.repaint();
		}else{
//			txtBank.setRequired(true);
//			txtBank.repaint();
//			txtBankAcct.setRequired(true);
//			txtBankAcct.repaint();
		}
    }
    
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		
		//handleCodingRule();
		txtPaymentRequestBillNumber.setText("");
		
    	if(prmtcurrency.getValue()!=null){
    		CurrencyInfo cur = (CurrencyInfo)prmtcurrency.getValue();
    		if(!this.baseCurrency.getId().toString().equals(cur.getId().toString())){
    			//txtexchangeRate.setEnabled(false);
    			prmtcurrency.setValue(baseCurrency);
    		}
    	}
    	this.txtMoneyDesc.setText(null);
    	this.chkUrgency.setEnabled(true);
		chkNeedPaid.setEnabled(true);
		if(chkNeedPaid.isSelected()){
			txtNoPaidReason.setEnabled(true);
		}else{
			txtNoPaidReason.setEnabled(false);	
			txtNoPaidReason.setText(null);
		}
				
    	this.chkCostsplit.setEnabled(true);
    	prmtuseDepartment.setValue(null);
    	txtamount.setValue(FDCHelper.ZERO);
    	txtBcAmount.setValue(FDCHelper.ZERO);
    	
    	this.txtPaymentProportion.setEditable(false);
		this.txtcompletePrjAmt.setEditable(false);
		if(isNotEnterCAS){
			this.chkNeedPaid.setEnabled(false);
			this.chkNeedPaid.setSelected(true);
		}
		
		if (prmtPlanProject.getValue() == null) {
			kdDepPlanState.setSelectedItem(DepPlanStateEnum.noPlan);
		}
	}
    
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception {

    	boolean isNeedPaid = chkNeedPaid.isSelected();
    	boolean chkUrgencyCopy = chkUrgency.isSelected();
    	String txtNoPaidReasonCopy = txtNoPaidReason.getText();
    	Object prmtAccountCopy = prmtAccount.getValue();
		String obj = txtattachment.getText();
		
    	super.actionCopy_actionPerformed(e);
    	//handleCodingRule();
    	txtPaymentRequestBillNumber.setText("");
    	prmtrealSupplier.setEnabled(true);
    	
    	if(prmtcurrency.getValue()!=null){
    		CurrencyInfo cur = (CurrencyInfo)prmtcurrency.getValue();
    		if(this.baseCurrency.getId().toString().equals(cur.getId().toString())){
    			txtexchangeRate.setEnabled(false);
    		}
    	}
    	
    	this.txtattachment.setText(obj);
    	this.chkUrgency.setEnabled(true);
    	this.chkCostsplit.setEnabled(true);
		chkNeedPaid.setEnabled(true);
		chkNeedPaid.setSelected(isNeedPaid);
		if(chkNeedPaid.isSelected()){
			txtNoPaidReason.setEnabled(true);
			txtNoPaidReason.setText(txtNoPaidReasonCopy);
			prmtAccount.setEnabled(true);
			prmtAccount.setValue(prmtAccountCopy);
		}else{
			txtNoPaidReason.setEnabled(false);	
			prmtAccount.setEnabled(false);
			txtNoPaidReason.setText(null);
			prmtAccount.setValue(null);
		}
		chkUrgency.setSelected(chkUrgencyCopy);
		//如果业务日期在当前期间之前的，设置业务日期为当前期间第一天
		Date bookedDate  = (Date)pkbookedDate.getValue();
		if(bookedDate!=null && canBookedPeriod!=null && bookedDate.before(canBookedPeriod.getBeginDate()) ){
			this.editData.setBookedDate(canBookedPeriod.getBeginDate());
			pkbookedDate.setValue(canBookedPeriod.getBeginDate());
		}
		editData.setSourceType(SourceTypeEnum.ADDNEW);
		//new update by renliang
		if(isNotEnterCAS){
			this.chkNeedPaid.setEnabled(false);
			this.chkNeedPaid.setSelected(true);
		}
    }
    
    //修改 款项说明和附件 没有提示保存
    boolean modify;
    public boolean isModify(){
    	if (STATUS_VIEW.equals(getOprtState())) {
			return false;
		}
    	if(modify)return true;
    	return super.isModify();
    }
    
    private void addListener(){
    	txtMoneyDesc.addKeyListener(new KeyListener(){

			public void keyPressed(KeyEvent e) {
				
			}

			public void keyReleased(KeyEvent e) {
				
			}

			public void keyTyped(KeyEvent e) {
				modify=true;
			}
    		
    	});
    	
    	txtattachment.addKeyListener(new KeyListener(){

			public void keyPressed(KeyEvent e) {
				
			}

			public void keyReleased(KeyEvent e) {
				
			}

			public void keyTyped(KeyEvent e) {
				modify=true;
			}
    		
    	});
    }
    
	private void setPayerAcctEvi(CurrencyInfo currencyInfo) throws EASBizException, BOSException {
		String currencyId = currencyInfo.getId().toString();
		String companyId = company.getId().toString();
		String cuId = company.getCU().getId().toString();
		
		prmtAccount.getQueryAgent().resetRuntimeEntityView();
		
		// 根据付款方式取得过滤条件
		EntityViewInfo treeevi = CasRecPayHandler.getAccountViewEvi(cuId,
				companyId, currencyId, false);
		
		AccountPromptBox opseelect = new AccountPromptBox(this, company,
				treeevi.getFilter(), false, true);
		prmtAccount.setSelector(opseelect);
		EntityViewInfo evi = CasRecPayHandler.getAccountViewEvi(cuId,
				companyId, currencyId, true);
		
		prmtAccount.setEntityViewInfo(evi);
	}


    /**
     * output prmtAccount_willCommit method
     */
    protected void prmtAccount_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
		CurrencyInfo currencyInfo = (CurrencyInfo) prmtcurrency.getValue();
		if (currencyInfo == null) {
			e.setCanceled(true);
			return;
		}
		setPayerAcctEvi(currencyInfo);
    }

    /**
     * output prmtAccount_willShow method
     */
    protected void prmtAccount_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
		CurrencyInfo currencyInfo = (CurrencyInfo) prmtcurrency.getValue();
		if (currencyInfo == null) {
			e.setCanceled(true);
			return;
		}
		setPayerAcctEvi(currencyInfo);
    }

    /**
     * output prmtAccount_dataChanged method
     */
    protected void prmtAccount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	
    }
    
    protected boolean isUseMainMenuAsTitle(){
    	return false;
    }
    
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
    	if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null){
			MsgBox.showWarning(this,FDCClientUtils.getRes("cantPrint"));
			return;
        }          
        ContractWithoutTextPrintDataProvider data = new ContractWithoutTextPrintDataProvider(editData.getString("id"),getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
        ArrayList idList = new ArrayList();
        if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null){
			MsgBox.showWarning(this,FDCClientUtils.getRes("cantPrint"));
			return;
        }
        ContractWithoutTextPrintDataProvider data = new ContractWithoutTextPrintDataProvider(editData.getString("id"),getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
	}
	//获得无文本合同套打对应的目录
	protected String getTDFileName() {
    	return "/bim/fdc/contract/ContractWithoutText";
	}
	//对应的套打Query
	protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.contract.app.ContractWithoutTextPrintQuery");
	}
    
	/**
	 * RPC改造，任何一次事件只有一次RPC
	 */
	
	public boolean isPrepareInit() {
    	return true;
    }
	
	public boolean isPrepareActionSubmit() {
    	return false;
    }
	
	public boolean isPrepareActionSave() {
    	return false;
    }
	
	public RequestContext prepareActionSubmit(IItemAction itemAction)
	throws Exception {
		RequestContext request = super.prepareActionSubmit(itemAction);
		
		return request;
	}
	
    /**
     * EditUI提供的初始化handler参数方法
     */
    protected void PrepareHandlerParam(RequestContext request)
    {
        super.PrepareHandlerParam(request);
        

    }
	protected void initWorkButton() {
		super.initWorkButton();
		actionViewBgBalance.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_sequencecheck"));
	}
	//查看预算余额
	public void actionViewBgBalance_actionPerformed(ActionEvent e) throws Exception {
		storeFields();
		//业务日期默认未设置，如果预算控制时以业务日期控制，会导致查看预算时查不到，手动设置一下
		editData.setBizDate((Date) this.pkbookedDate.getValue());
		ContractWithoutTextInfo info = editData;
		IBgControlFacade iCtrl = BgControlFacadeFactory.getRemoteInstance();
		BgCtrlResultCollection coll = iCtrl.getBudget(
				FDCConstants.ContractWithoutText, null, info);

		UIContext uiContext = new UIContext(this);
		uiContext.put(BgHelper.BGBALANCE, coll);

		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(BgBalanceViewUI.class.getName(), uiContext, null,
						STATUS_VIEW);
		uiWindow.show();
		info=null;
	}

	 public void fillAttachmentList(){
    	this.cmbAttachment.removeAllItems();
		String boId = null;
		if (this.editData.getId() == null) {
			return;
		} else {
			boId = this.editData.getId().toString();
		}
		try {
			this.cmbAttachment.addItems(AttachmentUtils.getAttachmentListByBillID(boId).toArray());
			this.btnViewAttachment.setEnabled(this.cmbAttachment.getItemCount() > 0);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
	}
	 
	 public void actionAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAttachment_actionPerformed(e);
		fillAttachmentList();
	}
	 
	 public void actionViewAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionViewAttachment_actionPerformed(e);
		String attachId = null;
    	if(this.cmbAttachment.getSelectedItem() != null && this.cmbAttachment.getSelectedItem() instanceof AttachmentInfo){
    		attachId = ((AttachmentInfo)this.cmbAttachment.getSelectedItem()).getId().toString();
    		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
    		acm.viewAttachment(attachId);
    	}
	}
	 
	protected void lockContainer(Container container) {
		 if(lblAttachmentContainer.getName().equals(container.getName())){
	        	return;
	        }
	        else{
	        	super.lockContainer(container);
	        }
	}
	
	/**
	 * 需求来源：R100806-236合同录入、变更、结算单据界面增加审批按钮
	 * <P>
	 * 为实现此BT需求，特重写以下方法
	 * 
	 * @author owen_wen 2011-03-08
	 */
	protected void setAuditButtonStatus(String oprtType) {
		if (STATUS_VIEW.equals(oprtType) || STATUS_EDIT.equals(oprtType)) {
			actionAudit.setVisible(true);
			actionUnAudit.setVisible(true);
			actionAudit.setEnabled(true);
			actionUnAudit.setEnabled(true);

			FDCBillInfo bill = (FDCBillInfo) editData;
			if (editData != null) {
				if (FDCBillStateEnum.AUDITTED.equals(bill.getState())) {
					actionUnAudit.setVisible(true);
					actionUnAudit.setEnabled(true);
					actionAudit.setVisible(false);
					actionAudit.setEnabled(false);
				} else {
					actionUnAudit.setVisible(false);
					actionUnAudit.setEnabled(false);
					actionAudit.setVisible(true);
					actionAudit.setEnabled(true);
				}
			}
		} else {
			actionAudit.setVisible(false);
			actionUnAudit.setVisible(false);
			actionAudit.setEnabled(false);
			actionUnAudit.setEnabled(false);
		}
	}

	/**
	 * 需求来源：R100806-236合同录入、变更、结算单据界面增加审批按钮
	 * <P>
	 * 为实现此BT需求，特重写以下方法
	 * 
	 * @author owen_wen 2011-03-08
	 */
	public void checkBeforeAuditOrUnAudit(FDCBillStateEnum state, String warning) throws Exception {

		isSameUserForUnAudit = FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_AUDITORMUSTBETHESAMEUSER);

		if (isSameUserForUnAudit && editData.getAuditor() != null) {

			if (!SysContext.getSysContext().getCurrentUserInfo().getId().equals(editData.getAuditor().getId())) {
				try {
					throw new FDCBasedataException(FDCBasedataException.AUDITORMUSTBETHESAMEUSER);
				} catch (FDCBasedataException e) {
					handUIExceptionAndAbort(e);
				}
			}
		}
		// 检查单据是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this, getSelectBOID());

		if (editData == null || editData.getState() == null || !editData.getState().equals(state)) {
			MsgBox.showWarning(this, FDCClientUtils.getRes(warning));
			SysUtil.abort();
		}
	}
	
	/**
	 * 描述：业务日期发生变化后需要传递到UIContext，通过UIContext在传递到计划项目F7
	 */
	protected void pkbookedDate_dataChanged(DataChangeEvent e) throws Exception {
		this.getUIContext().put("bookedDate", this.pkbookedDate.getSqlDate());
		Calendar cal = Calendar.getInstance();
		cal.setTime((Date) pkbookedDate.getValue());
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		prmtPlanProject.setDisplayFormat("$payMattersName$ " + year + "年" + month + "月 无合同计划");
		this.prmtPlanProject.setData(null);
	}

	/**
	 * 描述：初始化计划项目F7
	 * <p>
	 * added by cp 2011/03-04
	 */
	public void initFDCDepConPayPlanF7() throws Exception {
		if (chkCostsplit.isSelected() && !chkNeedPaid.isSelected()
				&& !"不控制".equals(CONTROLNOCONTRACT)) {
			prmtPlanProject.setRequired(false);
		}
		prmtPlanProject
				.setQueryInfo("com.kingdee.eas.fdc.finance.app.FDCDepConPayPlanNoContractF7Query");
		prmtPlanProject.setEditFormat("$payMattersName$");
		FDCDepConPayPlanSelector f7Selector = new FDCDepConPayPlanSelector(this);
		this.prmtPlanProject.setSelector(f7Selector);
	}

	/**
	 * 描述：查看预算
	 * <p>
	 * added by cp 2011/03-04
	 */
	public void actionViewBudget_actionPerformed(ActionEvent e)
			throws Exception {
		FDCDepConPayPlanNoContractInfo plan = (FDCDepConPayPlanNoContractInfo) prmtPlanProject
		.getValue();
		if (plan == null) {
			MsgBox.showWarning("无预算，暂不能查看");
			SysUtil.abort();
		}
		String payMatters = plan.getPayMatters();
		String planNum = plan.getHead().getNumber();

		Map uiContext = getUIContext();
		Date bookDate = (Date) pkbookedDate.getValue();
		Date firstDay = BudgetViewUtils.getFirstDay(bookDate);
		Date lastDay = BudgetViewUtils.getLastDay(bookDate);
		String projectId = editData.getCurProject().getId().toString();
		Map rt = getLocalBudget(firstDay, lastDay, projectId, planNum,
				payMatters);
		String curPrj = (String) rt.get("curProject");
		uiContext.put("curProject", curPrj);
		String curDept = (String) rt.get("curDept");
		uiContext.put("curDept", curDept);
		String planMonth = (String) rt.get("planMonth");
		uiContext.put("planMonth", planMonth);
		BigDecimal localBudget = (BigDecimal) rt.get("localBudget");
		uiContext.put("localBudget", localBudget);
		BigDecimal actPaied = getActPaied(firstDay, lastDay, projectId, planNum, payMatters);
		uiContext.put("actPaied", actPaied);
		BigDecimal floatFund = getFloatFund(firstDay, lastDay, projectId, planNum, payMatters);
		uiContext.put("floatFund", floatFund);
		BigDecimal bgBalance = localBudget.subtract(floatFund).subtract(actPaied);
		uiContext.put("bgBalance", bgBalance);
		BudgetViewUtils.showModelUI(uiContext);
	}

	/*
	 * 获取当月可用余额
	 */
	protected BigDecimal getBgBalance() throws BOSException, SQLException {
		FDCDepConPayPlanNoContractInfo plan = (FDCDepConPayPlanNoContractInfo) prmtPlanProject
				.getValue();
		if (plan != null) {
		String payMatters = plan.getPayMatters();
		String planNum = plan.getHead().getNumber();

		Map uiContext = getUIContext();
		Date bookDate = (Date) pkbookedDate.getValue();
		Date firstDay = BudgetViewUtils.getFirstDay(bookDate);
		Date lastDay = BudgetViewUtils.getLastDay(bookDate);
		String projectId = editData.getCurProject().getId().toString();
		Map rt = getLocalBudget(firstDay, lastDay, projectId, planNum,
				payMatters);
		String curDept = (String) rt.get("curDept");
		uiContext.put("curDept", curDept);
		String planMonth = (String) rt.get("planMonth");
		uiContext.put("planMonth", planMonth);
		BigDecimal localBudget = (BigDecimal) rt.get("localBudget");
		uiContext.put("localBudget", localBudget);
		BigDecimal actPaied = getActPaied(firstDay, lastDay, projectId, planNum, payMatters);
		uiContext.put("actPaied", actPaied);
		BigDecimal floatFund = getFloatFund(firstDay, lastDay, projectId, planNum, payMatters);
		uiContext.put("floatFund", floatFund);
		
		return localBudget.subtract(floatFund).subtract(actPaied);
		}else{
			return FDCHelper.ZERO;
		}
	}

	/**
	 * 计算已付
	 * 
	 * @param firstDay
	 * @param lastDay
	 * @param projectId
	 * @param planNum
	 * @param payMatters
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	protected BigDecimal getActPaied(Date firstDay, Date lastDay,
			String projectId, String planNum, String payMatters)
			throws BOSException, SQLException {
		BigDecimal actPaied = FDCHelper.ZERO;
		FDCSQLBuilder builderActPaied = new FDCSQLBuilder();
		builderActPaied
				.appendSql(" select sum(pay.FLocalAmount) as actPaied from t_cas_paymentbill as pay ");
		builderActPaied
				.appendSql(" left join T_CON_ContractWithoutText as con on con.FID = pay.FContractBillID ");
		builderActPaied
				.appendSql(" left join T_FNC_FDCDepConPayPlanNoCon as np on np.FID = con.FFdcDepConPlanID ");
		builderActPaied
				.appendSql(" left join T_FNC_FDCDepConPayPlanBill as pb on pb.FID = np.FHeadID ");
		builderActPaied.appendSql(" where pay.FBillStatus = 15 ");
		builderActPaied.appendSql(" and pay.FBizDate >= ");
		builderActPaied.appendParam(firstDay);
		builderActPaied.appendSql(" and pay.FBizDate <= ");
		builderActPaied.appendParam(lastDay);
		builderActPaied.appendSql(" and pay.FCurProjectID = ");
		builderActPaied.appendParam(projectId);
		builderActPaied.appendSql(" and pb.FNumber = ");
		builderActPaied.appendParam(planNum);
		builderActPaied.appendSql(" and np.FPayMatters = ");
		builderActPaied.appendParam(payMatters);
		// 查看状态查以前的
		if (STATUS_VIEW.equals(getOprtState())) {
			builderActPaied.appendSql(" and pay.FLastUpdateTime <= ");
			builderActPaied.appendSql("{ts '");
			DateFormat FORMAT_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			builderActPaied.appendSql(FORMAT_TIME.format(editData
					.getLastUpdateTime()));
			builderActPaied.appendSql("' }");
			// 此处精确到时分秒，所以不能用下句
			// builderActPaied.appendParam(editData.getLastUpdateTime());
		}
		IRowSet rowSetActPaied = builderActPaied.executeQuery();
		while (rowSetActPaied.next()) {
			if (rowSetActPaied.getString("actPaied") != null) {
				actPaied = rowSetActPaied.getBigDecimal("actPaied");
			}
		}
		return actPaied;
	}
	
	/**
	 * 计算在途
	 * 在途金额 = “业务日期”所在月份中所有状态为“已提交”、“审批中”、“已审批”（且对应付款单状态不为“已付款”）的无文本合同中“本位币金额”累计值。 by zhiyuan_tang
	 * 
	 * @param firstDay
	 * @param lastDay
	 * @param projectId
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	protected BigDecimal getFloatFund(Date firstDay, Date lastDay,
			String projectId, String planNum, String payMatters) throws BOSException, SQLException {
		
		BigDecimal floatFund = FDCHelper.ZERO;
		FDCSQLBuilder builderFloatFund = new FDCSQLBuilder();
		builderFloatFund
				.appendSql(" select sum(con.FAmount) as floatFund from T_CON_ContractWithoutText as con ");
		builderFloatFund
				.appendSql(" left join T_FNC_FDCDepConPayPlanNoCon as np on np.FID = con.FFdcDepConPlanID ");
		builderFloatFund
				.appendSql(" left join T_FNC_FDCDepConPayPlanBill as pb on pb.FID = np.FHeadID ");
		builderFloatFund.appendSql(" left join T_CON_PayRequestBill as req on con.FID = req.FContractId  ");
		builderFloatFund.appendSql(" left join T_CAS_PaymentBill as pay on req.FID = pay.FFdcPayReqID  ");
		builderFloatFund
				.appendSql(" where (con.FState = '2SUBMITTED' or con.FState = '3AUDITTING' or ");
		builderFloatFund.appendSql(" (con.FState = '4AUDITTED' and pay.FBillStatus <> 15)) ");
		builderFloatFund.appendSql(" and con.FBookedDate >= ");
		builderFloatFund.appendParam(firstDay);
		builderFloatFund.appendSql(" and con.FBookedDate <= ") 	;
		builderFloatFund.appendParam(lastDay);
		builderFloatFund.appendSql(" and con.FCurProjectID = ");
		builderFloatFund.appendParam(projectId);
		builderFloatFund.appendSql(" and pb.FNumber =  ");
		builderFloatFund.appendParam(planNum);
		builderFloatFund.appendSql(" and np.FPayMatters = ");
		builderFloatFund.appendParam(payMatters);
		// 查看状态查以前的
		if (STATUS_VIEW.equals(getOprtState())) {
			builderFloatFund.appendSql(" and con.FLastUpdateTime <= ");
			builderFloatFund.appendSql("{ts '");
			DateFormat FORMAT_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			builderFloatFund.appendSql(FORMAT_TIME.format(editData
					.getLastUpdateTime()));
			builderFloatFund.appendSql("' }");
			// 此处精确到时分秒，所以不能用下句
			// builderFloatFund.appendParam(editData.getLastUpdateTime());
		}
		if (editData.getId() != null) {
			builderFloatFund.appendSql(" and con.FID != ");
			builderFloatFund.appendParam(editData.getId().toString());
		}
		IRowSet rowSetFloatFund = builderFloatFund.executeQuery();
		while (rowSetFloatFund.next()) {
			if (rowSetFloatFund.getString("floatFund") != null) {
				floatFund = rowSetFloatFund.getBigDecimal("floatFund");
			}
		}
		return floatFund;
	}
	
	/**
	 * 取预算，分已签订合同预算和待签订合同预算2种情况，根据界面F7值判断<br>
	 * 从合同滚动付款计划中取值
	 * 
	 * @param firstDay
	 * @param lastDay
	 * @param projectId
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	protected Map getLocalBudget(Date firstDay, Date lastDay, String projectId,
			String planNum, String payMatters) throws BOSException,
			SQLException {
		Map rt = new HashMap();
		FDCDepConPayPlanNoContractInfo plan = (FDCDepConPayPlanNoContractInfo) prmtPlanProject
				.getValue();
		if (plan == null) {
			CurProjectInfo prj = (CurProjectInfo) prmtcurProject.getValue();
			String prjName = "";
			if (prj != null) {
				prjName = prj.getName();
			}
			rt.put("curProject", prjName);
			AdminOrgUnitInfo dpt = (AdminOrgUnitInfo) prmtuseDepartment
					.getValue();
			String curDept = "";
			if (dpt != null) {
				curDept = dpt.getName();
			}
			rt.put("curDept", curDept);
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM");
			String planMonth = ft.format(editData.getBookedDate());
			rt.put("planMonth", planMonth);
			rt.put("localBudget", FDCHelper.ZERO);
			return rt;
		}
		
		BigDecimal localBudget = FDCHelper.ZERO;
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
				.appendSql("select prj.FName_l2 as prjName,admin.FName_l2 as curDept,entry.FMonth as planMonth,entry.FOfficialPay as localBudget ");
		builder.appendSql("from T_FNC_FDCDepConPayPlanNoCon as con ");
		builder
				.appendSql("left join T_FNC_FDCDepConPayPlanBill as head on head.FID = con.FHeadID ");
		builder
				.appendSql("left join T_FNC_FDCDepConPayPlanNCEntry as entry on entry.FParentNC = con.FID ");
		builder
				.appendSql("left join T_ORG_Admin as admin on admin.FID = head.FDeptmentID ");
		builder.appendSql(" left join T_FDC_CurProject as prj on prj.FID = con.FProjectID ");
		builder
				.appendSql("where ");
		builder.appendSql(" entry.FMonth >= ");
		builder.appendParam(firstDay);
		builder.appendSql(" and entry.FMonth <= ");
		builder.appendParam(lastDay);
		// builder.appendSql(" and con.FProjectID = ");
		// builder.appendParam(projectId);
		builder.appendSql(" and con.FID = ");
		builder.appendParam(plan.getId().toString());
//		builder.appendSql(" and head.FNumber = ");
//		builder.appendParam(planNum);
		builder.appendSql(" order by head.FYear desc,head.FMonth DESC");
		IRowSet rowSet = builder.executeQuery();
		while (rowSet.next()) {
			String curPrj = rowSet.getString("prjName");
			rt.put("curProject", curPrj);
			String curDept = rowSet.getString("curDept");
			rt.put("curDept", curDept);
			String planMonth = rowSet.getString("planMonth");
			rt.put("planMonth", planMonth);
			if (rowSet.getString("localBudget") != null) {
				localBudget = rowSet.getBigDecimal("localBudget");
				rt.put("localBudget", localBudget);
			}
		}
		return rt;
	}
	
	protected void chkCostsplit_actionPerformed(ActionEvent e) throws Exception {
		if (!"不控制".equals(CONTROLNOCONTRACT) && chkCostsplit.isSelected()
				&& !chkNeedPaid.isSelected()) {
			prmtPlanProject.setRequired(false);
		} else {
			prmtPlanProject.setRequired(false);
		}
	}
	protected void prmtPlanProject_dataChanged(DataChangeEvent e) throws Exception {
		if (e.getNewValue() == null) {
			kdDepPlanState.setSelectedItem(DepPlanStateEnum.noPlan);
			localBudget = null;
		} else {
			FDCDepConPayPlanNoContractInfo plan = (FDCDepConPayPlanNoContractInfo) e.getNewValue();
			setDepPlanState(plan);
		}
	}

	//抽出变量以供别处使用..ken_liu..
	BigDecimal localBudget = null;
	BigDecimal usedBudget = FDCConstants.ZERO;
	private void setDepPlanState(FDCDepConPayPlanNoContractInfo plan) throws BOSException, SQLException {
		String payMatters = plan.getPayMatters();
		String planNum = plan.getHead().getNumber();

		Date bookDate = (Date) pkbookedDate.getValue();
		Date firstDay = BudgetViewUtils.getFirstDay(bookDate);
		Date lastDay = BudgetViewUtils.getLastDay(bookDate);
		String projectId = editData.getCurProject().getId().toString();
		Map rt = getLocalBudget(firstDay, lastDay, projectId, planNum, payMatters);
		localBudget = (BigDecimal) rt.get("localBudget");
		BigDecimal actPaied = getActPaied(firstDay, lastDay, projectId, planNum, payMatters);
		BigDecimal floatFund = getFloatFund(firstDay, lastDay, projectId, planNum, payMatters);
		usedBudget = FDCHelper.add(actPaied, floatFund);
		if (FDCHelper.compareTo(localBudget, FDCHelper.add(usedBudget,txtBcAmount.getBigDecimalValue())) >= 0) {
			kdDepPlanState.setSelectedItem(DepPlanStateEnum.inPlan);
		} else {
			kdDepPlanState.setSelectedItem(DepPlanStateEnum.outPlan);
		}
	}

	/**
	 * 打回状态的单据不让删除 - add by zhaoqin for R130812-0007 on 2013/08/21
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#actionRemove_actionPerformed(java.awt.event.ActionEvent)
	 * @Author：zhaoqin
	 * @CreateTime：2013-8-22
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeRemove();
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * 描述：不能删除的单据状态
	 * R130812-0007 ： 添加“打回”状态
	 * @Author：zhaoqin
	 * @CreateTime：2013-8-22
	 */
	private void checkBeforeRemove() {
		FDCBillStateEnum state = editData.getState();
		if (state != null
				&& (state == FDCBillStateEnum.AUDITTING || state == FDCBillStateEnum.AUDITTED || state == FDCBillStateEnum.CANCEL || state == FDCBillStateEnum.BACK)) {
			logger.info("ContractBill can't be removed, because the ContractBill has been canceled or sent back.");
			MsgBox.showWarning(this, ContractClientUtils.getRes("cantRemove"));
			SysUtil.abort();
		}
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////
	
	/**
	 * 描述：合同类型变更事件
	 * 
	 * @param e
	 * @throws Exception
	 * @author skyiter_wang
	 * @createDate 2013-11-22
	 * @see com.kingdee.eas.fdc.contract.client.AbstractContractWithoutTextEditUI#prmtContractType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent)
	 */
	protected void prmtContractType_dataChanged(DataChangeEvent e) throws Exception {
		Object newValue = e.getNewValue();

		try {
			setCodePropertyChanged(true);
			contractTypeChanged(newValue);
		} finally {
			setCodePropertyChanged(false);
		}

		super.prmtContractType_dataChanged(e);
	}

	/**
	 * 合同类型发生改变时，要做的工作
	 * 
	 * @param newValue
	 */
	private void contractTypeChanged(Object newValue) throws Exception, BOSException, EASBizException,
			CodingRuleException {
		if (newValue != null && STATUS_ADDNEW.equals(getOprtState())) {
			ContractTypeInfo info = (ContractTypeInfo) newValue;
			if ((this.editData.getContractType() == null || !this.editData.getContractType().getId().equals(
					info.getId()))) {
				this.editData.setContractType(info);
				this.setNumberByCodingRule();
			}
		} else {
			if (newValue != null) {
				// 没有完成审批(也包括归档，因为归档前提是完成审批)的合同的合同类型可以修改，合同编码也可以修改
				if (STATUS_EDIT.equals(getOprtState())) {
					if ((this.editData.getState().equals(com.kingdee.eas.fdc.basedata.FDCBillStateEnum.AUDITTED))) {
					} else {
						ContractTypeInfo info = (ContractTypeInfo) newValue;
						if (!info.isIsLeaf()) {
							MsgBox.showWarning(this, FDCClientUtils.getRes("selectLeaf"));
							prmtContractType.setData(null);
							prmtContractType.requestFocus();
							SysUtil.abort();
						}
						if (this.editData.getContractType() != null
								&& !this.editData.getContractType().getId().equals(info.getId())) {
							this.editData.setContractType(info);
							this.setNumberByCodingRule();
						}
					}
				}
			}
		}
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {
		if (STATUS_ADDNEW.equals(this.oprtState) || STATUS_EDIT.equals(this.oprtState)) {
			setNumberByCodingRule();
		}
	}

	/**
	 * 描述：根据编码规则设置编码
	 * 
	 * @author skyiter_wang
	 * @createDate 2013-11-22
	 */
	private void setNumberByCodingRule() {
		// 合同类型
		ContractTypeInfo contractType = (ContractTypeInfo) this.prmtContractType.getValue();
		try {
			// // 获取无文本合同编码规则的编码集合
			// ContractWOTCodingTypeCollection codingTypeList =
			// ContractWithoutTextFactory.getRemoteInstance()
			// .getContractWOTCodingType(contractType, ContractThirdTypeEnum.NEW_VALUE);
			// 取得无文本合同编码类型对象集合（精确，模糊，递归）
			ContractWOTCodingTypeCollection codingTypeList = ContractCodingUtil.getContractWOTCodingTypeCollection(
					null, contractType, ContractThirdTypeEnum.NEW_VALUE);

			int size = codingTypeList.size();
			String bindingProperty = getBindingProperty();
			String orgId = null;
			if (size > 0) {
				// 只有当组织列表为空集合的时候才去找
				if (orgIDList.size() == 0) {
					ContractUtil.findParentOrgUnitIdToList(null, this.orgUnitInfo.getId().toString(), orgIDList);
				}
				ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
				for (int i = 0; i < size; i++) {
					ContractWOTCodingTypeInfo codingType = codingTypeList.get(i);
					// 设置合同编码类型
					this.editData.setCodeType(codingType);
					// 循环组织列表树查询是否有编码规则有就使用
					for (int j = 0; j < orgIDList.size(); j++) {
						orgId = orgIDList.get(j).toString();
						if (setNumber(iCodingRuleManager, orgId, bindingProperty)) {
							return;
						}
					}
				}
			}

			this.txtNumber.setEnabled(true);
			this.txtNumber.setEditable(true);
		} catch (Exception err) {
			setNumberTextEnabled();
			handUIExceptionAndAbort(err);
		}
	}

	/**
	 * 描述：转换编码number，将"!"变为"."
	 * 
	 * @param orgNumber
	 * @return
	 * @author skyiter_wang
	 * @createDate 2013-11-22
	 */
	private String convertNumber(String orgNumber) {
		return orgNumber.replaceAll("!", ".");
	}

	/**
	 * 描述：判断编码规则状态,包括启用、新增设置、断码等
	 * 
	 * @param iCodingRuleManager
	 * @param orgId
	 * @param bindingProperty
	 * @return
	 * @throws CodingRuleException
	 * @throws EASBizException
	 * @throws BOSException
	 * @author skyiter_wang
	 * @createDate 2013-11-22
	 */
	protected boolean setNumber(ICodingRuleManager iCodingRuleManager, String orgId, String bindingProperty)
			throws CodingRuleException, EASBizException, BOSException {

		if (editData == null || orgId == null || bindingProperty == null) {
			return false;
		}

		this.editData.setContractType((ContractTypeInfo) this.prmtContractType.getValue());

		// 如果存在无文本合同编码规则
		if (iCodingRuleManager.isExist(this.editData, orgId, bindingProperty)) {
			CodingRuleInfo codingRuleInfo = iCodingRuleManager.getCodingRule(editData, orgId, bindingProperty);
			this.txtNumber.setEnabled(false);
			this.txtNumber.setEditable(false);
			// 编码规则中启用了"新增显示"
			if (codingRuleInfo.isIsAddView()) {

				String costCenterId = null;
				if (editData instanceof FDCBillInfo && ((FDCBillInfo) editData).getOrgUnit() != null) {
					costCenterId = ((FDCBillInfo) editData).getOrgUnit().getId().toString();
				} else {
					costCenterId = SysContext.getSysContext().getCurrentCostUnit().getId().toString();
				}
				try {
					// 新增时，或绑定属性发生改变时才需要去取number
					if (STATUS_ADDNEW.equals(this.oprtState) || isCodePropertyChanged()) {
						String number = prepareNumberForAddnew(iCodingRuleManager, (FDCBillInfo) editData, orgId,
								costCenterId, bindingProperty);
						// 把number的值赋予caller中相应的属性，并把TEXT控件的编辑状态设置好。
						prepareNumber(editData, convertNumber(number));

						// 获取编码规则是否支持修改
						boolean flag = FDCClientUtils.isAllowModifyNumber(this.editData, orgId, bindingProperty);
						this.txtNumber.setEnabled(flag);
						this.txtNumber.setEditable(flag);

						// 获取编码规则定义中，参与编码的属性Map
						fetchValueAttributeMap(orgId, bindingProperty);

						return true;
					}
				} catch (Exception e) {
					handUIExceptionAndAbort(e);
				}
			}

			if (STATUS_ADDNEW.equals(this.oprtState)
					&& iCodingRuleManager.isUseIntermitNumber(editData, orgId, bindingProperty)) {
				this.txtNumber.setText("");
			}
			return true;
		}

		return false;
	}

	/**
	 * 描述：编码规则中启用了“新增显示”,必须检验是否已经重复
	 * 
	 * @param iCodingRuleManager
	 * @param info
	 * @param orgId
	 * @param costerId
	 * @param bindingProperty
	 * @return
	 * @throws Exception
	 * @author skyiter_wang
	 * @createDate 2013-11-22
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#prepareNumberForAddnew(com.kingdee.eas.base.codingrule.ICodingRuleManager,
	 *      com.kingdee.eas.fdc.basedata.FDCBillInfo, java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	protected String prepareNumberForAddnew(ICodingRuleManager iCodingRuleManager, FDCBillInfo info, String orgId,
			String costerId, String bindingProperty) throws Exception {

		String number = null;
		FilterInfo filter = null;
		int i = 0;
		do {
			// 如果编码重复重新取编码
			if (bindingProperty != null) {
				number = iCodingRuleManager.getNumber(editData, orgId, bindingProperty, "");
			} else {
				number = iCodingRuleManager.getNumber(editData, orgId);
			}

			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", number));
			filter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID.getValue(), CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", costerId));
			if (info.getId() != null) {
				filter.getFilterItems().add(new FilterItemInfo("id", info.getId().toString(), CompareType.NOTEQUALS));
			}
			i++;
		} while (((IFDCBill) getBizInterface()).exists(filter) && i < 1000);

		return number;
	}

	/**
	 * 描述：设置编码控件是否可编辑
	 * 
	 * @author RD_skyiter_wang
	 * @createDate 2013-11-28
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#setNumberTextEnabled()
	 */
	protected void setNumberTextEnabled() {
		super.setNumberTextEnabled();
	}

	private boolean isExistCodingRule() {
		Context ctx = null;
		String currentOrgId = this.orgUnitInfo.getId().toString();
		String bindingProperty = getBindingProperty();

		boolean isExist = false;
		try {
			isExist = FdcCodingRuleUtil.isExist(ctx, editData, currentOrgId, bindingProperty);
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}

		return isExist;
	}

	// 取编码，编码规则必须设置绑定属性
	protected String getBindingProperty() {
		return BINDING_PROPERTY;
	}
	
}