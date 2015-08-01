/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.Action;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.ExtendParser;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.CommitEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.WinStyle;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.param.IParamControl;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.uiframe.client.UIModelDialog;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.DeductTypeCollection;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.SourceTypeEnum;
import com.kingdee.eas.fdc.basedata.TypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractFacadeFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.GuerdonBillCollection;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.PartAConfmOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.PartAOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.PayReqPrjPayEntryInfo;
import com.kingdee.eas.fdc.contract.PayReqUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.UrgentDegreeEnum;
import com.kingdee.eas.fdc.finance.ConPayPlanSplitFactory;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctFacadeFactory;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctItemTypeEnum;
import com.kingdee.eas.fdc.finance.FDCBudgetConstants;
import com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctEntryFactory;
import com.kingdee.eas.fdc.finance.FDCBudgetCtrlStrategy.FDCBudgetParam;
import com.kingdee.eas.fdc.finance.client.ContractAssociateAcctPlanUI;
import com.kingdee.eas.fdc.finance.client.ContractPayPlanEditUI;
import com.kingdee.eas.fdc.finance.client.PayReqAcctPayUI;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.framework.BillBaseCollection;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.client.AbstractCoreBillEditUI;
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
import com.kingdee.util.DateTimeUtils;
/**
 * output class name
 */
public class PayRequestBillWebEditUI extends AbstractPayRequestBillEditUI {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = CoreUIObject
			.getLogger(PayRequestBillWebEditUI.class);

	private boolean isFirstLoad = true;// 是否第一次加载,用来控制表格的加载显示

	private static final Color noEditColor = PayReqTableHelper.noEditColor;

	private int rowIndex = 4;// 合同内工程款行号

	private int columnIndex = 4;// 合同内工程款列号
	
	//付款申请单提交时，是否检查合同未完全拆分
	private boolean checkAllSplit = true;
	//已实现为0时只能选择预付款的控制
	private boolean isRealizedZeroCtrl = false;

	/**
	 * 调整扣款项窗口
	 */
	private IUIWindow deductUIwindow = null;

	/**
	 * 用于绑定cell进行值操作的map key为属性的info属性名，value为cell的引用
	 */
	HashMap bindCellMap = new HashMap(20);

	private PayReqWebTableHelper tableHelper = new PayReqWebTableHelper(this);
	
    // 是否使用预算
    protected boolean isMbgCtrl = false;
    //房地产付款单强制不进入出纳系统
    protected boolean isNotEnterCAS = false;
	protected FDCBudgetParam fdcBudgetParam= null;
    //付款比例,已经结算
    private BigDecimal payScale;
    //供应商 
//    private SupplierCompanyInfoInfo supplierCompanyInfoInfo ;未使用，暂注释
    //设置付款次数为合同的付款次数 从付款单中过滤
    private int payTimes = 0;
    //变更单
    private ContractChangeBillCollection contractChangeBillCollection = null;
    //付款单
    private BillBaseCollection paymentBillCollection = null;
    //付款申请单对应的奖励项
    private GuerdonOfPayReqBillCollection guerdonOfPayReqBillCollection = null;
    //奖励单
    private GuerdonBillCollection guerdonBillCollection = null;
    //付款申请单对应的违约金
    private CompensationOfPayReqBillCollection compensationOfPayReqBillCollection = null;
    //付款申请单对应的甲供材扣款
    private PartAOfPayReqBillCollection partAOfPayReqBillCollection = null;
    //付款申请单对应的甲村确认单金额
    private PartAConfmOfPayReqBillCollection partAConfmOfPayReqBillCollection = null;
    //扣款类型
    private DeductTypeCollection deductTypeCollection = null;
    //工程项目对应的成本中心
    private FullOrgUnitInfo costOrg = null;

    //是否加载过初始数据
    private boolean hasFetchInit = false;
    
    //累计请款超过合同最新造价严格控制
    private boolean isControlCost = false;
    
    //用途字段受控
    protected int usageLegth = 90;
    
    //付款申请单收款银行和收款账号为必录项
    private boolean isBankRequire = false;
    
    //申请单进度款付款比例自动为100%
    private boolean isAutoComplete = false;
    
    //甲供材系统参数
    private boolean partAParam = false;
    
    //付款进度比例
    private boolean payProcess = false ;
    
    
    //付款申请单合同ID
    private String payReqContractId = null;
    
    //付款申请单合同是否甲供材合同
    public static boolean isPartACon = false;
    
    // 简单模式一体化
	private boolean isSimpleFinancial = false;

	// 最新造价原币
	private BigDecimal lastestPriceOriginal = FDCHelper.ZERO;

	// 累计发票金额/
	private BigDecimal allInvoiceAmt = FDCHelper.ZERO;

	// 累计已完工工程量/
	private BigDecimal allCompletePrjAmt = FDCHelper.ZERO;

	// 简单财务成本一体化处理扣款、违约、奖励
	private boolean isSimpleFinancialExtend = false;

	// 工程量确认流程与付款流程是否分离
	protected boolean isSeparate = false;
	
	// 付款申请单及无文本合同发票号、发票金额必录
	private boolean isInvoiceRequired = false;
	
	
	/**
	 * output class constructor
	 */
	public PayRequestBillWebEditUI() throws Exception {
		super();
		jbInit();
	}

	private void jbInit() {
		titleMain = getUITitle();
	}

	/**
	 * 覆盖父类的方法以控制工具栏按钮的显示
	 * 
	 * @author sxhong Date 2006-9-13
	 * @see com.kingdee.eas.fdc.contract.client.AbstractPayRequestBillEditUI#initUIToolBarLayout()
	 */
	public void initUIToolBarLayout() {
		this.toolBar.add(btnAddNew);
		this.toolBar.add(btnEdit);
		this.toolBar.add(btnSave);
		this.toolBar.add(btnSubmit);
		this.toolBar.add(btnCopy);
		this.toolBar.add(btnRemove);
		this.toolBar.add(btnCancelCancel);
		this.toolBar.add(btnCancel);
		this.toolBar.add(btnAttachment);
		this.toolBar.add(separatorFW1);
		this.toolBar.add(btnPageSetup);
		this.toolBar.add(separatorFW3);
		this.toolBar.add(btnFirst);
		this.toolBar.add(btnPre);
		this.toolBar.add(btnNext);
		this.toolBar.add(btnLast);

		// 套打
		btnTaoPrint.setIcon(EASResource.getIcon("imgTbtn_print"));
		this.toolBar.add(btnTaoPrint);
		this.toolBar.add(btnPrint);
		this.toolBar.add(btnPrintPreview);
		this.toolBar.add(separatorFW2);
		// 付款计划
		btnAudit.setIcon(EASResource.getIcon("imgTbtn_auditing"));
		this.toolBar.add(btnAudit);
		btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
		this.toolBar.add(btnUnAudit);
		btnPaymentPlan.setIcon(EASResource.getIcon("imgTbtn_showdata"));
		this.toolBar.add(btnPaymentPlan);

		actionClose.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_close"));
		actionUnClose.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_fclose"));

		this.toolBar.add(btnClose);
		this.toolBar.add(btnUnclose);

		this.toolBar.add(separatorFW4);
		// 调整扣款项按钮
		actionAdjustDeduct.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_showdata"));
		this.toolBar.add(btnAdjustDeduct);
		this.toolBar.add(separatorFW5);
		btnCalc.setIcon(EASResource.getIcon("imgTbtn_counter"));
		this.toolBar.add(btnCalc);

		this.toolBar.add(btnTraceUp);
		this.toolBar.add(btnTraceDown);

		this.toolBar.add(btnViewContract);
		this.toolBar.add(btnViewPayDetail);
		
		this.toolBar.add(btnAuditResult);
		actionAuditResult.setVisible(true);
		actionAuditResult.setEnabled(true);
		
		actionContractAttachment.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_view"));
		this.toolBar.add(btnContractAttachment);
		actionContractAttachment.setVisible(true);
		actionContractAttachment.setEnabled(true);
	}

	// 变化事件
	protected void controlDate_dataChanged(
			com.kingdee.bos.ctrl.swing.event.DataChangeEvent e,
			ControlDateChangeListener listener) throws Exception {
		if ("bookedDate".equals(listener.getShortCut())) {
			bookedDate_dataChanged(e);
		} else if ("amount".equals(listener.getShortCut())) {
			currencydataChanged(e);
		} else if ("completePrjAmt".equals(listener.getShortCut())
				|| "paymentProp".equals(listener.getShortCut())) {
			setPropPrjAmount(listener.getShortCut(), e);
		}
	}

	// 业务日期变化事件
	ControlDateChangeListener bookedDateChangeListener = new ControlDateChangeListener(
			"bookedDate");

	ControlDateChangeListener amountListener = new ControlDateChangeListener(
			"amount");

	ControlDateChangeListener completePrjAmtListener = new ControlDateChangeListener(
			"completePrjAmt");

	ControlDateChangeListener paymentProptListener = new ControlDateChangeListener(
			"paymentProp");

	// 监听器
	protected void attachListeners() {
		txtcompletePrjAmt.addDataChangeListener(completePrjAmtListener);
		prmtcurrency.addDataChangeListener(amountListener);
		txtpaymentProportion.addDataChangeListener(paymentProptListener);
		pkbookedDate.addDataChangeListener(bookedDateChangeListener);

		// 用款部门
		addDataChangeListener(prmtuseDepartment);
		addDataChangeListener(prmtPayment);
		addDataChangeListener(txtAmount);
		addDataChangeListener(pkpayDate);
		addDataChangeListener(prmtsettlementType);
		addDataChangeListener(prmtrealSupplier);
		addDataChangeListener(prmtsupplier);
		addDataChangeListener(txtpaymentProportion);
	}

	protected void detachListeners() {
		txtcompletePrjAmt.removeDataChangeListener(completePrjAmtListener);
		prmtcurrency.removeDataChangeListener(amountListener);
		txtpaymentProportion.removeDataChangeListener(paymentProptListener);
		pkbookedDate.removeDataChangeListener(bookedDateChangeListener);

		removeDataChangeListener(prmtuseDepartment);
		removeDataChangeListener(prmtPayment);
		removeDataChangeListener(txtAmount);
		removeDataChangeListener(pkpayDate);
		removeDataChangeListener(prmtsettlementType);
		removeDataChangeListener(prmtrealSupplier);
		removeDataChangeListener(prmtsupplier);
		removeDataChangeListener(txtpaymentProportion);
	}

	/**
	 * output loadFields method
	 */
	public void loadFields() {

		if ((hasFetchInit && !this.getOprtState().equals(OprtState.VIEW))
				|| this.getUIContext().get("PayRequestFullListUI") != null) {
			try {
				fetchInitData();
			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			}
		}

		detachListeners();

		// 处理付款比例及已完工工程量的加载
		initPaymentProp();
		// setAutoNumber();
		if (editData.getUrgentDegree() == null)
			editData.setUrgentDegree(UrgentDegreeEnum.NORMAL);
		super.loadFields();
		if(OprtState.ADDNEW.equals(getOprtState())){
			txtpaymentProportion.setValue(FDCHelper.ZERO);
			txtcompletePrjAmt.setValue(FDCHelper.ZERO);
		}
		// 更新本位币显示
		//setAmount();

		// 设置付款次数为合同的付款次数 从付款单中过滤
		if (editData.getState() != FDCBillStateEnum.AUDITTED) {
			editData.setPayTimes(payTimes);
		}
		// /*
		// * 审核/反审核按钮的状态
		// */
		// if (editData.getState() != null
		// && editData.getState().equals(FDCBillStateEnum.SUBMITTED)) {
		// actionAudit.setEnabled(true);
		// actionAudit.setVisible(true);
		// actionUnAudit.setEnabled(false);
		// actionUnAudit.setVisible(false);
		// } else if (FDCBillStateEnum.AUDITTED.equals(editData.getState())) {
		// actionAudit.setEnabled(false);
		// actionAudit.setVisible(false);
		// actionUnAudit.setEnabled(true);
		// actionUnAudit.setVisible(true);
		// } else {
		// actionAudit.setEnabled(false);
		// actionAudit.setVisible(true);
		// actionUnAudit.setEnabled(false);
		// actionUnAudit.setVisible(true);
		// }

		if (editData.getState() != null
				&& !editData.getState().equals(FDCBillStateEnum.SAVED)) {
			btnSave.setEnabled(false);
		}

		// if (editData.getUrgentDegree() == UrgentDegreeEnum.URGENT) {
		// chkUrgency.setSelected(true);
		// } else {
		// chkUrgency.setSelected(false);
		// }

		if (editData.getCurProject() != null) {
			CurProjectInfo curProjectInfo = editData.getCurProject();
			txtProj.setText(curProjectInfo.getDisplayName());
		}
		if (editData.getOrgUnit() != null) {
			txtOrg.setText(editData.getOrgUnit().getDisplayName());
		}

		//
		if (editData.getContractId() != null
				&& PayReqUtils.isConWithoutTxt(editData.getContractId())) {
			actionAdjustDeduct.setEnabled(false);
		} else {
			actionAdjustDeduct.setEnabled(true);
		}

		//
		if (editData.getCapitalAmount() == null && editData.getAmount() != null) {
			// 大写金额为本位币金额
			BigDecimal localamount = editData.getAmount();
			if (localamount.compareTo(FDCConstants.ZERO) != 0) {
				localamount = localamount.setScale(2, BigDecimal.ROUND_HALF_UP);
			}
			String cap = FDCClientHelper.getChineseFormat(localamount, false);
			// FDCHelper.transCap((CurrencyInfo) value, amount);
			txtcapitalAmount.setText(cap);
			editData.setCapitalAmount(cap);
		}
		
		hasFetchInit = true;
		
		loadInvoiceAmt();
		
		loadAllCompletePrjAmt();
		
		attachListeners();
		
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
    	txtAmount.setPrecision(curPrecision);
    	BigDecimal exRate =  editData.getExchangeRate();    	
    	txtexchangeRate.setValue(exRate);
    	txtAmount.setValue(editData.getOriginalAmount());
	}

	public void beforeStoreFields(ActionEvent e)  throws Exception {
		super.beforeStoreFields(e);
		
		String contractId = editData.getContractId();
		// 将分录内的数据存储到info
		if (PayReqUtils.isContractBill(contractId)
				&& (editData.getState() == FDCBillStateEnum.SAVED || editData
						.getState() == FDCBillStateEnum.SUBMITTED)) {
			try {
				tableHelper.updateDynamicValue(editData,contractBill,contractChangeBillCollection,paymentBillCollection);
			} catch (Exception e1) {
				handUIExceptionAndAbort(e1);
			}
			PayReqUtils.getValueFromCell(editData, bindCellMap);
			if(this.isAdvance()){
				tableHelper.updateLstAdvanceAmt(editData, true);
			}
		} else {
			// 显示本申请单的付款金额
			if (editData != null && editData.getId() != null
					&& editData.getState() == FDCBillStateEnum.AUDITTED) {
				FDCSQLBuilder builder = new FDCSQLBuilder();
				builder
						.appendSql("select sum(famount) as amount from t_cas_paymentbill where ffdcPayReqID=? and fbillStatus=?");
				builder.addParam(editData.getId().toString());
				builder.addParam(new Integer(BillStatusEnum.PAYED_VALUE));
				try {
					IRowSet rowSet = builder.executeQuery();
					if (rowSet.size() > 0) {
						rowSet.next();
						BigDecimal payedAmt = rowSet.getBigDecimal("amount");
						editData.setPayedAmt(FDCHelper.toBigDecimal(payedAmt));
						tableHelper.setCellValue(
								PayRequestBillContants.PAYEDAMT, payedAmt);
					}
				} catch (Exception e1) {
					handUIExceptionAndAbort(e1);
				}

			}
		}
		editData.setPayDate(DateTimeUtils.truncateDate(editData.getPayDate()));
		if (PayReqUtils.isContractBill(contractId)) {

			// 付款比例
//			if (!contractBill.isHasSettled()) {
				setCostAmount();
//			}
			
		}
		//setAmount();
		try {
			this.btnInputCollect_actionPerformed(null);
		} catch (Exception ex) {
			handUIExceptionAndAbort(ex);
		}
//		/**
//		 * setCostAmount已处理
//		 */
//		if (isSimpleFinancial) {
//			BigDecimal amount = FDCHelper
//			.toBigDecimal(((ICell) bindCellMap
//					.get(PayRequestBillContants.PROJECTPRICEINCONTRACT))
//					.getValue());// txtBcAmount.getBigDecimalValue();
//			editData.setCompletePrjAmt(amount);
//		}
	}
	/**
	 * output storeFields method
	 */
	public void storeFields() {		
		super.storeFields();
	}

	protected void fetchInitData() throws Exception {
		String contractBillId = (String) getUIContext().get("contractBillId");

			Map initparam = new HashMap();
			if(contractBillId!=null){
				initparam.put("contractBillId",contractBillId);
			}else{
				if(editData!=null && editData.getContractId()!=null){
					initparam.put("contractBillId",editData.getContractId());
				}else{
					initparam.put("ID",getUIContext().get("ID"));	
				}		
			}
//			Map initData = ((IFDCBill)getBizInterface()).fetchInitData(initparam);
			Map initData = (Map)ActionCache.get("FDCBillEditUIHandler.initData");
			if(initData==null){
				initData = ((IFDCBill)getBizInterface()).fetchInitData(initparam);
			}
			
			//本位币
			baseCurrency = (CurrencyInfo)initData.get(FDCConstants.FDC_INIT_CURRENCY);
			//
			company = (CompanyOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_COMPANY);
			//合同单据
			contractBill = (ContractBillInfo)initData.get(FDCConstants.FDC_INIT_CONTRACT);
			//付款比例
			payScale = (BigDecimal)initData.get("payScale");
			//供应商
//			supplierCompanyInfoInfo = (SupplierCompanyInfoInfo)initData.get("supplierCompanyInfoInfo");
			//设置付款次数为合同的付款次数 从付款单中过滤
			payTimes = ((Integer)initData.get("payTimes")).intValue();
		    //变更单
			contractChangeBillCollection =  (ContractChangeBillCollection)initData.get("ContractChangeBillCollection");;
		    //付款单
		     paymentBillCollection = (BillBaseCollection)initData.get("PaymentBillCollection");;
		    //付款申请单对应的奖励项
		     guerdonOfPayReqBillCollection = (GuerdonOfPayReqBillCollection)initData.get("GuerdonOfPayReqBillCollection");;
		    //奖励单
		     guerdonBillCollection = (GuerdonBillCollection)initData.get("GuerdonBillCollection");;
		    //付款申请单对应的违约金
		     compensationOfPayReqBillCollection = (CompensationOfPayReqBillCollection)initData.get("CompensationOfPayReqBillCollection");;
		    //付款申请单对应的甲供材扣款
		     partAOfPayReqBillCollection = (PartAOfPayReqBillCollection) initData.get("PartAOfPayReqBillCollection");
		    //付款申请单对应的甲供材确认单金额
		     partAConfmOfPayReqBillCollection = (PartAConfmOfPayReqBillCollection)initData.get("PartAConfmOfPayReqBillCollection");
		     //扣款类型
		     deductTypeCollection = (DeductTypeCollection)initData.get("DeductTypeCollection");;
		    //工程项目对应的成本中心
		    costOrg = (FullOrgUnitInfo)initData.get("FullOrgUnitInfo");
		    
		    //日期
			bookedDate = (Date)initData.get(FDCConstants.FDC_INIT_DATE);
			if(bookedDate==null){
				bookedDate = new Date();
			}
			serverDate = (Date)initData.get("serverDate");
			if(serverDate==null){
				serverDate = bookedDate;
			}
			//当前期间
			curPeriod = (PeriodInfo) initData.get(FDCConstants.FDC_INIT_PERIOD);
			
			curProject = (CurProjectInfo) initData.get(FDCConstants.FDC_INIT_PROJECT);
			
			orgUnitInfo = (FullOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_ORGUNIT);
			if(orgUnitInfo==null){
				orgUnitInfo = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
			}
			lastestPriceOriginal = (BigDecimal)initData.get("lastestPriceOriginal");
	}
	
	//子类可以重载
	protected  void fetchInitParam() throws Exception{

		//是否启用预算控制
		if(orgUnitInfo!=null){
//			HashMap param = FDCUtils.getDefaultFDCParam(null,orgUnitInfo.getId().toString());
			Map param = null;//(Map)ActionCache.get("FDCBillEditUIHandler.orgParamItem");
			/*if(param==null){
				param = FDCUtils.getDefaultFDCParam(null,orgUnitInfo.getId().toString());	
			}*/
			//update by renliang
			param = FDCUtils.getDefaultFDCParam(null,orgUnitInfo.getId().toString());
			
		    isMbgCtrl = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_STARTMG).toString()).booleanValue();
			
		    //付款申请单付款金额不允许超过可付款额度
		    isControlCost = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_OUTPAYAMOUNT).toString()).booleanValue();		    
			
			//申请单进度款付款比例自动为100%
		    isAutoComplete = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_PAYPROGRESS).toString()).booleanValue();		    
			
			//付款比例
			if(param.get(FDCConstants.FDC_PARAM_PAYPROGRESS)!=null){
				payProcess = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_PAYPROGRESS).toString()).booleanValue();
			}	
		}

		if (company == null) {
			return;
		}
		//启用成本财务一体化
//		HashMap param = FDCUtils.getDefaultFDCParam(null,company.getId().toString());
		Map paramItem = null;//(Map)ActionCache.get("FDCBillEditUIHandler.comParamItem");
		
		/*if(paramItem==null){
			paramItem = FDCUtils.getDefaultFDCParam(null,company.getId().toString());
		}*/
		//update by renliang
		paramItem = FDCUtils.getDefaultFDCParam(null,company.getId().toString());
		
		if(paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION)!=null){
			isIncorporation = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION).toString()).booleanValue();
		}				
		
		// 启用成本月结
		if (paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION) != null) {
			isIncorporation = Boolean.valueOf(
					paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION).toString())
					.booleanValue();
		}

		// 简单模式的一体化
		if (paramItem.get(FDCConstants.FDC_PARAM_SIMPLEFINACIAL) != null) {
			isSimpleFinancial = Boolean
					.valueOf(
							paramItem.get(FDCConstants.FDC_PARAM_SIMPLEFINACIAL)
									.toString()).booleanValue();
			//isAutoComplete = isAutoComplete;//||isSimpleFinancial;
		}
		
		if(paramItem.get(FDCConstants.FDC_PARAM_SIMPLEFINACIALEXTEND)!=null){
			isSimpleFinancialExtend = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_SIMPLEFINACIALEXTEND).toString()).booleanValue();
		}
		
		//用途字段受控
		if(paramItem.get("CS050")!=null){
			usageLegth = Integer.valueOf(paramItem.get("CS050").toString()).intValue();
		}	
		
	    //付款申请单收款银行和收款账号为必录项
		if(paramItem.get(FDCConstants.FDC_PARAM_BANKREQURE)!=null){
			isBankRequire = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_BANKREQURE).toString()).booleanValue();
		}
		
		//房地产付款单强制进入出纳系统
		if(paramItem.get(FDCConstants.FDC_PARAM_NOTENTERCAS)!=null){
			isNotEnterCAS = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_NOTENTERCAS).toString()).booleanValue();
		}
		
		//甲工才
		if(paramItem.get(FDCConstants.FDC_PARAM_CREATEPARTADEDUCT)!=null){
			partAParam = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_CREATEPARTADEDUCT).toString()).booleanValue();
		}
		fdcBudgetParam=FDCBudgetParam.getInstance(paramItem);
		
		HashMap paramMap = FDCUtils.getDefaultFDCParam(null,null);
		checkAllSplit = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_CHECKALLSPLIT);
		isRealizedZeroCtrl= FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_REALIZEDZEROCTRL);
//		isRealizedZeroCtrl=true;
		if(paramItem.get(FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT)!=null){
			isSeparate = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT).toString()).booleanValue();
		}
		if(paramItem.get(FDCConstants.FDC_PARAM_INVOICEREQUIRED)!=null){
			isInvoiceRequired = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_INVOICEREQUIRED).toString()).booleanValue();
		}
	}

	public void onLoad() throws Exception {
		super.onLoad();

		tableHelper = new PayReqWebTableHelper(this);
		kdtEntrys = tableHelper.createPayRequetBillTable(deductTypeCollection);
		kdtEntrys.addKDTEditListener(new KDTEditAdapter() {
			// 编辑结束后
			public void editStopped(KDTEditEvent e) {
				try {
					kdtEntrys_editStopped(e);
				} catch (Exception e1) {
					handUIExceptionAndAbort(e1);
				}
			}
		});

		if (isFirstLoad) {// 第一次加载时初始化表格的内容,以后不会改变
			if (!getOprtState().equals(OprtState.ADDNEW)) {
				tableHelper.updateLstReqAmt(editData, false);
			}

			getDetailTable().getScriptManager().setAutoRun(false);
			PayReqUtils.setValueToCell(editData, bindCellMap);

		}
		if(this.isAdvance()){
			PayReqPrjPayEntryInfo prjPayEntry = new PayReqPrjPayEntryInfo();
//			prjPayEntry.setId(BOSUuid.create(prjPayEntry.getBOSType()));
//			editData.setPrjPayEntry(prjPayEntry);
			tableHelper.updateLstAdvanceAmt(editData, false);
		}
		if (isFirstLoad)
			isFirstLoad = false;
		if (txtexchangeRate.getNumberValue() == null) {
			txtexchangeRate.setValue(FDCConstants.ONE);
		}

		boolean close = editData.isHasClosed();
		actionClose.setVisible(!close);
		actionClose.setEnabled(!close);
		actionUnClose.setVisible(close);
		actionUnClose.setEnabled(close);
		// boolean audit = (editData.getState() == FDCBillStateEnum.SUBMITTED);
		// actionAudit.setVisible(audit);
		// actionAudit.setEnabled(audit);
		// boolean unAudit = (editData.getState() == FDCBillStateEnum.AUDITTED);
		// actionUnAudit.setVisible(unAudit);
		// actionUnAudit.setEnabled(unAudit);

		btnAttachment.setText(getRes("btnAttachment"));

		actionTraceDown.setVisible(true);
		actionTraceDown.setEnabled(true);
		actionTraceUp.setVisible(true);
		actionTraceUp.setEnabled(true);

		actionAuditResult.setVisible(true);
		actionAuditResult.setEnabled(true);

		getDetailTable().getScriptManager().setAutoRun(true);
		kdtEntrys.getScriptManager().runAll();

		//累计发票金额
		txtInvoiceAmt.setPrecision(2);
		txtInvoiceAmt.setSupportedEmpty(true);
		txtAllInvoiceAmt.setSupportedEmpty(true);
		txtInvoiceAmt.setMinimumValue(FDCHelper.MIN_VALUE);
		txtInvoiceAmt.setMaximumValue(FDCHelper.MAX_VALUE.multiply(FDCHelper.TEN));
		txtInvoiceAmt.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent eventObj) {
				BigDecimal invoiceAmt = txtInvoiceAmt.getBigDecimalValue();
				txtAllInvoiceAmt.setNumberValue(allInvoiceAmt.add(FDCHelper.toBigDecimal(invoiceAmt)));
			}
		});
		calAllCompletePrjAmt();
		// 增加原币金额的可录入范围
		txtAmount.setPrecision(2);
		txtAmount.setMinimumValue(FDCHelper.MIN_VALUE);
		txtAmount.setMaximumValue(FDCHelper.MAX_VALUE.multiply(FDCHelper.TEN));
		txtBcAmount.setPrecision(2);
		txtBcAmount.setMinimumValue(FDCHelper.MIN_VALUE);
		txtBcAmount
				.setMaximumValue(FDCHelper.MAX_VALUE.multiply(FDCHelper.TEN));

		txtattachment.setPrecision(0);
		txtattachment.setRemoveingZeroInDispaly(true);
		txtattachment.setRemoveingZeroInEdit(true);

		txtcapitalAmount.setEditable(false);

		txtMoneyDesc.setMaxLength(300);

		txtUsage.setMaxLength(usageLegth);
		prmtDesc.setMaxLength(150);

		txtpaymentProportion.setPrecision(2);
		txtpaymentProportion.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE);
		txtpaymentProportion.setMinimumValue(FDCHelper.ZERO);
		txtpaymentProportion.setRoundingMode(BigDecimal.ROUND_HALF_EVEN);
		txtpaymentProportion.setRemoveingZeroInEdit(false);
		txtpaymentProportion.setRemoveingZeroInDispaly(false);
		txtpaymentProportion.setSupportedEmpty(false);
		txtcompletePrjAmt.setPrecision(2);
		txtcompletePrjAmt.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE
				.multiply(new BigDecimal("100000")));
		txtcompletePrjAmt.setMinimumValue(FDCHelper.ZERO);
		if (isAutoComplete) {
			txtcompletePrjAmt.setRequired(false);
			txtpaymentProportion.setEditable(false);
		}

		if (!getOprtState().equals(OprtState.ADDNEW)) {
			tableHelper.reloadGuerdonValue(editData, null);
			tableHelper.reloadCompensationValue(editData, null);
			tableHelper.updateLstReqAmt(editData, true);
		}
		prmtsupplier.setEditable(false);

		//
		String cuid = this.curProject.getCU().getId().toString();
		// SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();

		FDCClientUtils.initSupplierF7(this, prmtsupplier, cuid);
		FDCClientUtils.initSupplierF7(this, prmtrealSupplier, cuid);

		prmtPayment.addDataChangeListener(new DataChangeListener() {
			// 付完结算款后才能付保修款，付完结算款后不能付进度款
			public void dataChanged(DataChangeEvent eventObj) {
				prmtPayment_dataChanged(eventObj);
			}

		});
		if (PayReqUtils.isConWithoutTxt(editData.getContractId())) {
			actionAddNew.setEnabled(false);
			actionCopy.setEnabled(false);

		}
		prmtsupplier.setEditable(false);
		prmtsupplier.setEnabled(false);

		String cu = null;
		if (editData != null && editData.getCU() != null) {
			cu = editData.getCU().getId().toString();
		} else {
			cu = SysContext.getSysContext().getCurrentCtrlUnit().getId()
					.toString();
		}
		FDCClientUtils.setRespDeptF7(prmtuseDepartment, this, cu);

		if (OprtState.ADDNEW.equals(getOprtState())) {
			DataChangeEvent e = new DataChangeEvent(pkpayDate, new Date(), null);
			pkpayDate_dataChanged(e);
		}
		
		// 新增状态下做最新造价的调整
//		tableHelper.setLstPrict();
		
//		if(!isMbgCtrl){
//			actionViewMbgBalance.setVisible(false);
//			this.menuItemViewMbgBalance.setVisible(false);
//			actionViewMbgBalance.setEnabled(false);
//		}
//		
		if (!fdcBudgetParam.isBgSysCtrl()) {
			actionViewMbgBalance.setVisible(false);
			this.menuItemViewMbgBalance.setVisible(false);
			actionViewMbgBalance.setEnabled(false);
		}else{
			actionViewMbgBalance.setVisible(true);
			this.menuItemViewMbgBalance.setVisible(true);
			actionViewMbgBalance.setEnabled(true);
		}
		
		ExtendParser parserCity = new ExtendParser(prmtDesc);
		prmtDesc.setCommitParser(parserCity);

		// 根据参数设置是否必录
		txtrecBank.setRequired(isBankRequire);
		txtrecAccount.setRequired(isBankRequire);

		setPrecision();

		actionPrintPreview.setVisible(true);
		actionPrintPreview.setEnabled(true);
		actionPrint.setVisible(true);
		actionPrint.setEnabled(true);
		
		if(PayReqUtils.isContractBill(editData.getContractId())&&isNotEnterCAS){
			chkIsPay.setEnabled(false);
			chkIsPay.setSelected(false);
		}
		
		if (!getOprtState().equals(OprtState.VIEW)) {
			this.storeFields();
		}
		
		if(contractBill!=null && PayReqUtils.isContractBill(editData.getContractId())){
			isPartACon = this.contractBill.isIsPartAMaterialCon();
		}
		/**
		 * 系统参数设置为真的时候，隐藏进度付款比例和本期完工工程量金额
		 */
		if(payProcess)
		{
			this.contpaymentProportion.setVisible(false) ;
			this.contcompletePrjAmt.setVisible(false) ;
			
			this.txtpaymentProportion.setRequired(false);
			this.txtcompletePrjAmt.setRequired(false);
		} 
		
		if(fdcBudgetParam.isAcctCtrl()&&contractBill!=null&&contractBill.isIsCoseSplit()){
			actionAssociateAcctPay.setVisible(true);
			actionAssociateAcctPay.setEnabled(true);
			actionAssociateUnSettled.setVisible(true);
			actionAssociateUnSettled.setEnabled(true);
		}else{
			actionAssociateAcctPay.setVisible(false);
			actionAssociateAcctPay.setEnabled(false);
			actionAssociateUnSettled.setVisible(false);
			actionAssociateUnSettled.setEnabled(false);
		}

        //业务日期判断为空时取期间中断
        if(pkbookedDate!=null&&pkbookedDate.isSupportedEmpty()){
            pkbookedDate.setSupportedEmpty(false);
        }
        this.prmtcurrency.setEditable(false);
        this.prmtcurrency.setEnabled(false);
        

		Object value = prmtcurrency.getValue();
		if (value instanceof CurrencyInfo) {
			// String cap = FDCHelper.transCap((CurrencyInfo) value,
			// amount);
			// txtcapitalAmount.setText(cap);

			// 本位币处理
			CompanyOrgUnitInfo currentFIUnit = SysContext.getSysContext()
					.getCurrentFIUnit();
			CurrencyInfo baseCurrency = currentFIUnit.getBaseCurrency();
			BOSUuid srcid = ((CurrencyInfo) value).getId();
			if (baseCurrency != null) {
				if (srcid.equals(baseCurrency.getId())) {
					txtexchangeRate.setValue(FDCConstants.ONE);
					txtexchangeRate.setEditable(false);
					// return;
				} else
					txtexchangeRate.setEditable(true);
			}
		}
		
		this.getDetailTable().setAfterAction(new BeforeActionListener(){
			public void beforeAction(BeforeActionEvent e){
				if(BeforeActionEvent.ACTION_DELETE==e.getType()){
					BigDecimal oriAmt = FDCHelper.toBigDecimal(getDetailTable().getCell(rowIndex,columnIndex)
							.getValue());
					if(FDCHelper.ZERO.compareTo(oriAmt)==0){
						getDetailTable().getCell(rowIndex,columnIndex+1).setValue(null);
					}
					oriAmt = FDCHelper.toBigDecimal(getDetailTable().getCell(rowIndex+1,columnIndex)
							.getValue());
					if(FDCHelper.ZERO.compareTo(oriAmt)==0){
						getDetailTable().getCell(rowIndex+1,columnIndex+1).setValue(null);
					}
				}
			}
			
		});
	}

	// 业务日期变化引起,期间的变化
	protected void bookedDate_dataChanged(
			com.kingdee.bos.ctrl.swing.event.DataChangeEvent e)
			throws Exception {
		String projectId = this.editData.getCurProject().getId().toString();
		fetchPeriod(e, pkbookedDate, cbPeriod, projectId, false);
	}

	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (editData != null) {
			String contractId = editData.getContractId();
			if (contractId == null || PayReqUtils.isConWithoutTxt(contractId)) {
				// 无文本不允许修改
				actionEdit.setEnabled(false);
				actionAdjustDeduct.setEnabled(false);
			} else {
				actionAdjustDeduct.setEnabled(true);
			}
		}
		if ((!getOprtState().equals(OprtState.ADDNEW) && !getOprtState()
				.equals(OprtState.EDIT))) {
			final StyleAttributes sa = kdtEntrys.getStyleAttributes();
			sa.setLocked(true);
			sa.setBackground(noEditColor);
			btnInputCollect.setEnabled(false);
			kdtEntrys.setEnabled(false);
			// actionAudit.setEnabled(false);
			// actionUnAudit.setEnabled(false);
			actionRemove.setEnabled(false);
			// this.getActionMap().

		}
		if (editData == null || editData.getState() == null)
			return;
		if (editData.getState().equals(FDCBillStateEnum.AUDITTED)
				|| editData.getState().equals(FDCBillStateEnum.AUDITTING)) {
			actionEdit.setEnabled(false);
			btnEdit.setEnabled(false);
		}

		menuTable1.setVisible(false);

		actionCopyFrom.setVisible(false);
		actionCopyFrom.setEnabled(false);
		menuItemCopyFrom.setVisible(false);
		menuItemCreateFrom.setVisible(false);
		// contsupplier.setEnabled(enabled);
		if (getOprtState() == AbstractCoreBillEditUI.STATUS_FINDVIEW) {
			actionUnClose.setEnabled(false);
			actionClose.setEnabled(false);
			// actionAudit.setEnabled(false);
			// actionUnAudit.setEnabled(false);
			actionTraceDown.setEnabled(false);
			actionTraceUp.setEnabled(false);
			btnUnclose.setEnabled(false);
			btnClose.setEnabled(false);
			btnAudit.setEnabled(false);
			btnUnAudit.setEnabled(false);
			btnTraceDown.setEnabled(false);
			btnTraceUp.setEnabled(false);
			// actionUnAudit.setVisible(false);
			// actionAudit.setVisible(false);

		} else if (getOprtState() == AbstractPayRequestBillEditUI.STATUS_CLOSE) {
			if (editData.isHasClosed()) {
				actionUnClose.setVisible(true);
			} else {
				actionClose.setVisible(true);
			}
			// actionAudit.setVisible(false);
			// actionUnAudit.setVisible(false);
			actionTraceDown.setVisible(false);
			actionTraceUp.setVisible(false);
			actionExitCurrent.setVisible(true);
			actionSave.setEnabled(false);
			menuItemSave.setVisible(false);
			actionSave.setVisible(false);
			actionSubmit.setEnabled(false);
			actionEdit.setEnabled(false);
			actionEdit.setVisible(false);
			actionRemove.setEnabled(false);
			actionCopy.setEnabled(false);
			actionCopy.setVisible(false);
			actionCopyFrom.setVisible(false);
			actionAttachment.setVisible(false);
			actionCopyFrom.setEnabled(false);
			menuEdit.setVisible(false);
			menuTool.setVisible(false);
			lockUIForViewStatus();
		}

		if (getOprtState().equals(OprtState.ADDNEW)) {
			btnTraceUp.setEnabled(false);
		} else {
			// actionr
		}

		if (!getOprtState().equals(OprtState.ADDNEW) && !getOprtState().equals(OprtState.EDIT)) {
			mergencyState.setEnabled(false);

			chkIsPay.setEnabled(false);
		}

		actionAddLine.setEnabled(false);
		actionRemoveLine.setEnabled(false);
		actionInsertLine.setEnabled(false);

		if (PayReqUtils.isConWithoutTxt(editData.getContractId())) {
			actionAddNew.setEnabled(false);
			actionCopy.setEnabled(false);
		}

		// 查看状态下也可以反审核
		// actionUnAudit.setEnabled(editData.getState().equals(
		// FDCBillStateEnum.AUDITTED));
		// actionAudit.setEnabled(editData.getState().equals(
		// FDCBillStateEnum.SUBMITTED));
		// actionUnAudit.setVisible(editData.getState().equals(
		// FDCBillStateEnum.AUDITTED));
		// actionAudit.setVisible(editData.getState().equals(
		// FDCBillStateEnum.SUBMITTED));

	}

	public void onShow() throws Exception {

		// 显示info的数据到表格
		super.onShow();

		String contractId = editData.getContractId();
		payReqContractId = contractId;
		if ((!getOprtState().equals(OprtState.ADDNEW) && !getOprtState()
				.equals(OprtState.EDIT))) {
			final StyleAttributes sa = kdtEntrys.getStyleAttributes();
			sa.setLocked(true);
			sa.setBackground(noEditColor);
			btnInputCollect.setEnabled(false);
			kdtEntrys.setEnabled(false);
			kdtEntrys.getCell(this.rowIndex, this.columnIndex).getStyleAttributes().setLocked(true);

		}
		// 工作流中可以复制合同名称
		if(editData!=null&&editData.getId()!=null&&FDCUtils.isRunningWorkflow(editData.getId().toString())){
			kdtEntrys.setEnabled(true);
		}
		if (contractId == null || PayReqUtils.isConWithoutTxt(contractId)) {
			// 无文本不允许修改
			actionEdit.setEnabled(false);
			actionAdjustDeduct.setEnabled(false);
		} else {
			actionAdjustDeduct.setEnabled(true);
		}

		if (editData.getState() == null)
			return;
		if (editData.getState().equals(FDCBillStateEnum.AUDITTED)
				|| editData.getState().equals(FDCBillStateEnum.AUDITTING)) {
			actionEdit.setEnabled(false);
			btnEdit.setEnabled(false);
		}

		menuTable1.setVisible(false);

		actionCopyFrom.setVisible(false);
		actionCopyFrom.setEnabled(false);
		menuItemCopyFrom.setVisible(false);
		menuItemCreateFrom.setVisible(false);

		if (getOprtState() == AbstractCoreBillEditUI.STATUS_FINDVIEW) {
			actionUnClose.setEnabled(false);
			actionClose.setEnabled(false);
			// actionAudit.setEnabled(false);
			// actionUnAudit.setEnabled(false);
			actionTraceDown.setEnabled(false);
			actionTraceUp.setEnabled(false);
			btnUnclose.setEnabled(false);
			btnClose.setEnabled(false);
			btnAudit.setEnabled(false);
			btnUnAudit.setEnabled(false);
			btnTraceDown.setEnabled(false);
			btnTraceUp.setEnabled(false);

		} else if (getOprtState() == AbstractPayRequestBillEditUI.STATUS_CLOSE) {
			if (editData.isHasClosed()) {
				actionUnClose.setVisible(true);
			} else {
				actionClose.setVisible(true);
			}
			// actionAudit.setVisible(false);
			// actionUnAudit.setVisible(false);
			actionTraceDown.setVisible(false);
			actionTraceUp.setVisible(false);
			actionAttachment.setVisible(false);
			actionExitCurrent.setVisible(true);
			actionSave.setEnabled(false);
			actionSave.setVisible(false);
			menuItemSave.setVisible(false);
			actionSubmit.setEnabled(false);
			actionEdit.setEnabled(false);
			actionEdit.setVisible(false);
			actionRemove.setEnabled(false);
			actionCopy.setEnabled(false);
			actionCopy.setVisible(false);
			actionCopyFrom.setVisible(false);
			actionCopyFrom.setEnabled(false);
			menuEdit.setVisible(false);
			menuTool.setVisible(false);
			lockUIForViewStatus();
		}

		if (!getOprtState().equals(OprtState.ADDNEW) && !getOprtState().equals(OprtState.EDIT)) {
			mergencyState.setEnabled(false);
			chkIsPay.setEnabled(false);
		}
		// onload也调用了,但是有些单元格表达式没有被计算,不得以再次调用,该死的table
		kdtEntrys.getScriptManager().runAll();
		
		actionAddLine.setEnabled(false);
		actionRemoveLine.setEnabled(false);
		actionInsertLine.setEnabled(false);

		// 查看状态下也可以反审核
		// actionUnAudit.setEnabled(editData.getState().equals(
		// FDCBillStateEnum.AUDITTED));

		Boolean disableSplit = (Boolean) getUIContext().get("disableSplit");
		if (disableSplit != null && disableSplit.booleanValue()) {
			actionUnAudit.setVisible(false);
			actionAudit.setVisible(false);
		}

		// 避免在新增单据（未作修改）直接关闭时，出现保存提示, 已由基本框架处理
		handleOldData();
	}

	public SelectorItemCollection getSelectors() {

		SelectorItemCollection sic = new SelectorItemCollection();

		// addProjectAmt
		// scheduleAmt
		// paymentPlan

		// addPrjAllReqAmt
		// lstAddPrjAllReqAmt
		// lstAddPrjAllPaidAmt

		sic.add("number");
		sic.add("state");
		sic.add("name");
		sic.add("payDate");
		sic.add("recBank");
		sic.add("recAccount");
		sic.add("moneyDesc");
		sic.add("contractNo");
		sic.add("description");
		sic.add("urgentDegree");
		sic.add("attachment");
		sic.add("bookedDate");
		sic.add("originalAmount");
		sic.add("amount");
		sic.add("exchangeRate");

		sic.add("paymentProportion");
		sic.add("costAmount");
		sic.add("grtAmount");
		sic.add("capitalAmount");

		// 1
		sic.add("contractName");
		sic.add("changeAmt");
		sic.add("payTimes");
		sic.add("curPlannedPayment");
		sic.add("curReqPercent");

		// 2
		sic.add("settleAmt");
		sic.add("curBackPay");
		sic.add("allReqPercent");
		
		//
		sic.add("guerdonOriginalAmt");
		sic.add("compensationOriginalAmt");

		// 合同内工程款
		sic.add("lstPrjAllPaidAmt");
		sic.add("lstPrjAllReqAmt");
		sic.add("projectPriceInContract");
		sic.add("projectPriceInContractOri");
		sic.add("prjAllReqAmt");
		
		// 预付款
		sic.add("prjPayEntry.lstAdvanceAllPaid");
		sic.add("prjPayEntry.lstAdvanceAllReq");
		sic.add("prjPayEntry.advance");
		sic.add("prjPayEntry.locAdvance");
		sic.add("prjPayEntry.advanceAllReq");
		sic.add("prjPayEntry.advanceAllPaid");

		// 甲供
		sic.add("lstAMatlAllPaidAmt");
		sic.add("lstAMatlAllReqAmt");
		sic.add("payPartAMatlAmt");
		sic.add("payPartAMatlOriAmt");
		sic.add("payPartAMatlAllReqAmt");

		// 实付
		sic.add("curPaid");

		// 5
		sic.add("contractPrice");
		sic.add("latestPrice");
		sic.add("payedAmt");
		sic.add("imageSchedule");
		sic.add("completePrjAmt");
		//
		sic.add("contractId");
		sic.add("hasPayoff");
		sic.add("hasClosed");
		sic.add("isPay");
		sic.add("auditTime");
		sic.add("createTime");
		sic.add("fivouchered");
		sic.add("sourceType");

		sic.add("isDifferPlace");
		sic.add("usage");

		// totalSettlePrice
		sic.add("totalSettlePrice");

		//发票
		sic.add("invoiceNumber");
		sic.add("invoiceAmt");
		sic.add("allInvoiceAmt");
		sic.add("invoiceDate");
		
		sic.add(new SelectorItemInfo("entrys.amount"));
		sic.add(new SelectorItemInfo("entrys.payPartAMatlAmt"));
		sic.add(new SelectorItemInfo("entrys.projectPriceInContract"));
		sic.add(new SelectorItemInfo("entrys.parent.id"));
		sic.add(new SelectorItemInfo("entrys.paymentBill.id"));
		sic.add(new SelectorItemInfo("entrys.advance"));
		sic.add(new SelectorItemInfo("entrys.locAdvance"));

		sic.add(new SelectorItemInfo("orgUnit.name"));
		sic.add(new SelectorItemInfo("orgUnit.number"));
		sic.add(new SelectorItemInfo("orgUnit.displayName"));

		sic.add(new SelectorItemInfo("CU.name"));

		sic.add(new SelectorItemInfo("auditor.name"));
		sic.add(new SelectorItemInfo("creator.name"));

		sic.add(new SelectorItemInfo("useDepartment.number"));
		sic.add(new SelectorItemInfo("useDepartment.name"));

		sic.add(new SelectorItemInfo("curProject.name"));
		sic.add(new SelectorItemInfo("curProject.number"));
		sic.add(new SelectorItemInfo("curProject.displayName"));
		sic.add(new SelectorItemInfo("curProject.fullOrgUnit.name"));
		sic.add(new SelectorItemInfo("curProject.codingNumber"));

		sic.add(new SelectorItemInfo("currency.number"));
		sic.add(new SelectorItemInfo("currency.name"));

		sic.add(new SelectorItemInfo("supplier.number"));
		sic.add(new SelectorItemInfo("supplier.name"));

		sic.add(new SelectorItemInfo("realSupplier.number"));
		sic.add(new SelectorItemInfo("realSupplier.name"));

		sic.add(new SelectorItemInfo("settlementType.number"));
		sic.add(new SelectorItemInfo("settlementType.name"));

		sic.add(new SelectorItemInfo("paymentType.number"));
		sic.add(new SelectorItemInfo("paymentType.name"));
		sic.add(new SelectorItemInfo("paymentType.payType.id"));

		sic.add(new SelectorItemInfo("period.number"));
		sic.add(new SelectorItemInfo("period.beginDate"));
		sic.add(new SelectorItemInfo("period.periodNumber"));
		sic.add(new SelectorItemInfo("period.periodYear"));
		sic.add(new SelectorItemInfo("contractBase.number"));
		sic.add(new SelectorItemInfo("contractBase.name"));

		return sic;
	}

	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		if (editData != null) {// 第一次保存时初始状态
			editData.setState(FDCBillStateEnum.SAVED);
		}
		btnInputCollect_actionPerformed(null);
		super.actionSave_actionPerformed(e);

	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		btnInputCollect_actionPerformed(null);

		// 检查拆分状态
		checkContractSplitState();

		editData.setState(FDCBillStateEnum.SUBMITTED);
		// checkAmt(editData);
		planAcctCtrl();
		super.actionSubmit_actionPerformed(e);
	}

	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		
		if (!getOprtState().equals(OprtState.VIEW)) {
			if (editData.getState() == null) {
				editData.setState(FDCBillStateEnum.SAVED);
			}
			storeFields();
		}
		if (editData == null || StringUtils.isEmpty(editData.getString("id"))){
			MsgBox.showWarning(this,EASResource
    				.getString(
    						"com.kingdee.eas.fdc.basedata.client.FdcResource",
    						"cantPrint"));
        	return;
		}

		// super.actionPrint_actionPerformed(e);
		KDNoteHelper appHlp = new KDNoteHelper();
		editData.setBoolean("isCompletePrjAmtVisible",contcompletePrjAmt.isVisible());
		appHlp.print("/bim/fdc/finance/payrequest",
				new PayRequestBillRowsetProvider(editData, getTDQueryPK(), bindCellMap,
						curProject,contractBill), SwingUtilities.getWindowAncestor(this));
	}

	/**
	 * output actionPrintPreview_actionPerformed
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		
		if (!getOprtState().equals(OprtState.VIEW)) {
			if (editData.getState() == null) {
				editData.setState(FDCBillStateEnum.SAVED);
			}
			storeFields();
		}
		if (editData == null || StringUtils.isEmpty(editData.getString("id"))){
			MsgBox.showWarning(this,EASResource
    				.getString(
    						"com.kingdee.eas.fdc.basedata.client.FdcResource",
    						"cantPrint"));
        	return;
		}

		// super.actionPrintPreview_actionPerformed(e);
		KDNoteHelper appHlp = new KDNoteHelper();
		editData.setBoolean("isCompletePrjAmtVisible",contcompletePrjAmt.isVisible());
		appHlp.printPreview("/bim/fdc/finance/payrequest",
				new PayRequestBillRowsetProvider(editData, getTDQueryPK(), bindCellMap,
						curProject,contractBill), SwingUtilities.getWindowAncestor(this));
	}

	/**
	 * output actionCopy_actionPerformed
	 */
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		boolean isView = (getOprtState().equals(OprtState.VIEW));
		super.actionCopy_actionPerformed(e);
		final Timestamp createTime = new Timestamp(System.currentTimeMillis());
		editData.setCreateTime(createTime);
		dateCreateTime.setValue(createTime);
		editData.setAuditor(null);
		editData.setAuditTime(null);
		editData.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		editData.setPaymentType(null);
		prmtPayment.setData(null);
		editData.getEntrys().clear();
		editData.setHasClosed(false);
		deductUIwindow = null;

		if (PayReqUtils.isContractBill(editData.getContractId())) {

			if (!contractBill.isHasSettled()) {
				editData.setPaymentProportion(contractBill.getPayScale());
			} else {
				if(!isSimpleFinancial){
					editData.setPaymentProportion(new BigDecimal("100"));
					editData.setCompletePrjAmt(contractBill.getSettleAmt());
				}
				editData.setCostAmount(contractBill.getSettleAmt());
				FDCSQLBuilder builder = new FDCSQLBuilder();
				builder.clear();
				builder
						.appendSql("select fqualityGuarante as amount from t_con_contractsettlementbill where fcontractbillid=");
				builder.appendParam(editData.getContractId());
				IRowSet rowSet = builder.executeQuery();
				if (rowSet.size() == 1) {
					rowSet.next();
					BigDecimal amount = FDCHelper.toBigDecimal(rowSet
							.getBigDecimal("amount"));
					editData.setGrtAmount(amount);
					txtGrtAmount.setValue(amount);
				}
			}
			tableHelper.updateDynamicValue(editData,contractBill,contractChangeBillCollection,paymentBillCollection);
			tableHelper.reloadDeductTable(editData, getDetailTable(),deductTypeCollection);
			tableHelper.updateGuerdonValue(editData, editData.getContractId(),guerdonOfPayReqBillCollection, guerdonBillCollection);
			tableHelper.updateCompensationValue(editData, editData.getContractId(),compensationOfPayReqBillCollection);
			
			if(partAParam){
				tableHelper.updatePartAValue(editData, editData.getContractId(), partAOfPayReqBillCollection);
			}else{
				tableHelper.updatePartAConfmValue(editData, editData.getContractId(), partAConfmOfPayReqBillCollection);
			}
			((ICell) bindCellMap
					.get(PayRequestBillContants.PROJECTPRICEINCONTRACT))
					.setValue(null);
			((ICell) bindCellMap
					.get(PayRequestBillContants.PROJECTPRICEINCONTRACTORI))
					.setValue(null);
			if(this.isAdvance()){
				((ICell) bindCellMap
						.get(PayRequestBillContants.ADVANCE))
						.setValue(null);
				((ICell) bindCellMap
						.get(PayRequestBillContants.LOCALADVANCE))
						.setValue(null);
			}
			((ICell) bindCellMap.get(PayRequestBillContants.ADDPROJECTAMT))
					.setValue(null);
			((ICell) bindCellMap.get(PayRequestBillContants.PAYPARTAMATLAMT))
					.setValue(null);

			if (isView) {
				setTableCellColorAndEdit();
			}
		}
		// txtcapitalAmount.setEditable(false);
		btnSave.setEnabled(true);
		txtAmount.setValue(null);
		// this.mergencyState.setSelected(false);
		chkIsPay.setSelected(true);
		editData.setSourceType(SourceTypeEnum.ADDNEW);
		setEditState();
	}

	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
	}

	/**
	 * output actionInsertLine_actionPerformed
	 */
	public void actionInsertLine_actionPerformed(ActionEvent e)
			throws Exception {
	}

	/**
	 * output actionRemoveLine_actionPerformed
	 */
	public void actionRemoveLine_actionPerformed(ActionEvent e)
			throws Exception {
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
		
		if (getOprtState().equals(OprtState.ADDNEW)) {
			btnTraceUp.setEnabled(false);
			((ICell) bindCellMap
					.get(PayRequestBillContants.PROJECTPRICEINCONTRACTORI))
					.getStyleAttributes().setLocked(false);
			((ICell) bindCellMap
					.get(PayRequestBillContants.PROJECTPRICEINCONTRACTORI))
					.getStyleAttributes().setBackground(Color.WHITE);
			if(this.isAdvance()){
				((ICell) bindCellMap
						.get(PayRequestBillContants.ADVANCE))
						.getStyleAttributes().setLocked(false);
				((ICell) bindCellMap
						.get(PayRequestBillContants.ADVANCE))
						.getStyleAttributes().setBackground(Color.WHITE);
			}
			btnInputCollect.setEnabled(true);
			kdtEntrys.setEditable(true);
			kdtEntrys.setEnabled(true);
		}
	}
	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		checkContractSplitState();
		boolean isView = (getOprtState().equals(OprtState.VIEW));
		CurrencyInfo currency = editData.getCurrency();
		SupplierInfo realSupplier = editData.getRealSupplier();

		// 如果直接新增,重新获得一下初始数据
		// fetchInitData();

		super.actionAddNew_actionPerformed(e);
		// 设置本位币
		prmtcurrency.setValue(null);
		prmtcurrency.setValue(currency);
		prmtrealSupplier.setValue(null);
		prmtrealSupplier.setValue(realSupplier);

		// 本位币处理
		CompanyOrgUnitInfo currentFIUnit = SysContext.getSysContext()
				.getCurrentFIUnit();
		CurrencyInfo baseCurrency = currentFIUnit.getBaseCurrency();
		BOSUuid srcid = currency.getId();
		if (baseCurrency != null) {
			if (srcid.equals(baseCurrency.getId())) {
				/*
				 * if (exchangeRate instanceof
				 * BigDecimal&&((BigDecimal)exchangeRate).intValue()!=1) {
				 * MsgBox.showWarning(this,"你选择的是本位币，但是汇率不等于1"); }
				 */
				txtexchangeRate.setValue(FDCConstants.ONE);
				txtexchangeRate.setEditable(false);
				// return;
			} else
				txtexchangeRate.setEditable(true);
		}
		
		if (PayReqUtils.isContractBill(editData.getContractId())) {
			tableHelper.updateDynamicValue(editData, contractBill,
					contractChangeBillCollection, paymentBillCollection);
			tableHelper.reloadDeductTable(editData, getDetailTable(),
					deductTypeCollection);
//			tableHelper.reloadGuerdonValue(editData, null);
			tableHelper.reloadCompensationValue(editData, null);
			if(partAParam){
				tableHelper.reloadPartAValue(editData, null);
			}else{
				tableHelper.reloadPartAConfmValue(editData, null);
			}
			((ICell) bindCellMap
					.get(PayRequestBillContants.PROJECTPRICEINCONTRACT))
					.setValue(null);
			((ICell) bindCellMap
					.get(PayRequestBillContants.PROJECTPRICEINCONTRACTORI))
					.setValue(null);
			if(this.isAdvance()){
//				tableHelper.updateLstAdvanceAmt(editData, false);
				((ICell) bindCellMap
						.get(PayRequestBillContants.ADVANCE))
						.setValue(null);
				((ICell) bindCellMap
						.get(PayRequestBillContants.LOCALADVANCE))
						.setValue(null);
			}
			((ICell) bindCellMap.get(PayRequestBillContants.ADDPROJECTAMT))
					.setValue(null);
			((ICell) bindCellMap.get(PayRequestBillContants.PAYPARTAMATLAMT))
					.setValue(null);

			if (isView) {
				setTableCellColorAndEdit();
			}
		} else {
			MsgBox.showInfo("当前付款申请单关联了无文本合同。无文本合同付款申请单不能直接生成，它由无文本合同自动生成");
			SysUtil.abort();
		}
		// txtcapitalAmount.setEditable(false);
		btnSave.setEnabled(true);
		deductUIwindow = null;

		this.chkIsPay.setEnabled(true);
		this.mergencyState.setEnabled(true);
		prmtsupplier.setEnabled(false);
		txtcapitalAmount.setEnabled(false);
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		/*
		 * if(editData.getState()!=null&&!editData.getState().equals(FDCBillStateEnum.SAVED)){
		 * btnSave.setEnabled(false); }
		 */
		setEditState();
		prmtsupplier.setEditable(false);
		prmtsupplier.setEnabled(false);
		txtcapitalAmount.setEditable(false);
		setAmount();

		PaymentTypeInfo type = this.editData.getPaymentType();
		if (type != null
				&& !type.getPayType().getId().toString().equals(
						PaymentTypeInfo.progressID)) {
			this.txtpaymentProportion.setEditable(false);
			this.txtcompletePrjAmt.setEditable(false);
		}
	}

	/**
	 * output actionAttachment_actionPerformed
	 */
	public void actionAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		if (OprtState.ADDNEW.equals(getOprtState())) {
			// 须保存警告
			MsgBox.showWarning(this, getRes("beforeAttachment"));
			SysUtil.abort();
		}
		super.actionAttachment_actionPerformed(e);
	}

	/*
	 * 几个添加的工具栏按钮
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkContractSplitState();
		// super.actionAudit_actionPerformed(e);
		checkBeforeAuditOrUnAudit(FDCBillStateEnum.SUBMITTED, "cantAudit");
		checkAmt(editData);
		PayRequestBillFactory.getRemoteInstance().audit(editData.getId());
		editData.setState(FDCBillStateEnum.AUDITTED);
		bizPromptAuditor.setValue(SysContext.getSysContext()
				.getCurrentUserInfo());
		pkauditDate.setValue(DateTimeUtils.truncateDate(new Date()));
		FDCClientUtils.showOprtOK(this);
		actionAudit.setEnabled(false);
		actionAudit.setVisible(false);
		actionUnAudit.setVisible(true);
		actionUnAudit.setEnabled(true);
		actionEdit.setEnabled(false);// 禁用修改
		actionSubmit.setEnabled(false);
		actionRemove.setEnabled(false);

	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		// super.actionAudit_actionPerformed(e);
		checkBeforeAuditOrUnAudit(FDCBillStateEnum.AUDITTED, "cantUnAudit");
		PayRequestBillFactory.getRemoteInstance().unAudit(editData.getId());
		editData.setState(FDCBillStateEnum.SUBMITTED);

		bizPromptAuditor.setValue(null);
		pkauditDate.setValue(null);
		FDCClientUtils.showOprtOK(this);
		actionAudit.setEnabled(true);
		actionAudit.setVisible(true);
		actionUnAudit.setVisible(false);
		actionUnAudit.setEnabled(false);
		if (getOprtState().equals(OprtState.EDIT)) {
			// 启用提交等
			actionSubmit.setEnabled(true);
			actionRemove.setEnabled(true);
		} else {
			actionEdit.setEnabled(true);
		}
	}

	public void actionTaoPrint_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionTaoPrint_actionPerformed(e);

	}

	public void actionAdjustDeduct_actionPerformed(ActionEvent e)
			throws Exception {
		if (OprtState.ADDNEW.equals(getOprtState())) {
			// 须保存警告
			MsgBox.showWarning(getRes("beforeAdjustDeduct"));
			SysUtil.abort();
		}
		
		// 币别
//		CurrencyInfo cur = editData.getCurrency();
//		if (!cur.getId().toString().equals(baseCurrency.getId().toString())) {
//			// 须保存警告
//			MsgBox.showWarning("外币不允许调整款项");
//			SysUtil.abort();
//		}

		super.actionAdjustDeduct_actionPerformed(e);
		showSelectDeductList(e);
	}

	public void actionClose_actionPerformed(ActionEvent e) throws Exception {
		if (OprtState.ADDNEW.equals(getOprtState())) {
			// 须保存警告
			MsgBox.showWarning(getRes("beforeClose"));
			SysUtil.abort();
		}
		super.actionClose_actionPerformed(e);
		if (editData != null && editData.getId() != null
				&& editData.isHasClosed()) {
			MsgBox.showWarning(this, "付款申请单已经关闭，不需要再关闭");
			SysUtil.abort();
		}
		editData.setHasClosed(true);
//		SelectorItemCollection selector = new SelectorItemCollection();
//		selector.add("hasClosed");
//		getBizInterface().updatePartial(editData, selector);
		PayRequestBillFactory.getRemoteInstance().close(new ObjectUuidPK(editData.getId()));
		
		actionClose.setVisible(false);
		actionClose.setEnabled(false);
		actionUnClose.setVisible(true);
		actionUnClose.setEnabled(true);
		this.storeFields();
		this.initOldData(this.editData);
		MsgBox.showInfo(getRes("closeSuccess"));
	}

	public void actionUnClose_actionPerformed(ActionEvent e) throws Exception {
		if (editData != null && editData.getId() != null
				&& !editData.isHasClosed()) {
			MsgBox.showWarning(this, "付款申请单未关闭，不需要反关闭");
			SysUtil.abort();
		}
		BigDecimal amount = FDCHelper.toBigDecimal(editData.getAmount());
		BigDecimal paidAmount = FDCHelper.ZERO;
		PayRequestBillEntryInfo entryInfo;
		for (Iterator iter = editData.getEntrys().iterator(); iter.hasNext();) {
			entryInfo = (PayRequestBillEntryInfo) iter.next();
			paidAmount = paidAmount.add(entryInfo.getAmount());
		}
		if (amount.compareTo(paidAmount) <= 0) {
			MsgBox.showWarning(getRes("canntUnClosed"));
			SysUtil.abort();
		}
		editData.setHasClosed(false);
//		SelectorItemCollection selector = new SelectorItemCollection();
//		selector.add("hasClosed");
//		getBizInterface().updatePartial(editData, selector);
		PayRequestBillFactory.getRemoteInstance().unClose(new ObjectUuidPK(editData.getId()));
		
		actionClose.setVisible(true);
		actionClose.setEnabled(true);
		actionUnClose.setVisible(false);
		actionUnClose.setEnabled(false);
		this.storeFields();
		this.initOldData(this.editData);
		MsgBox.showInfo(getRes("unCloseSuccess"));

	}

	public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception {
		// 取数据库内的最新数据
		editData = (PayRequestBillInfo) getBizInterface().getValue(
				new ObjectUuidPK(editData.getId()));
		switch (editData.getEntrys().size()) {
		case 0: {
			MsgBox.showError(getRes("notraceDownBill"));
			SysUtil.abort();
			break;
			// super.actionTraceDown_actionPerformed(e);
		}
		case 1: {
			IUIWindow traceDownUIwindow = null;
			UIContext uiContext = new UIContext(this);
			String uiName = "com.kingdee.eas.fdc.finance.client.PaymentBillEditUI";
			String paymentId = editData.getEntrys().get(0).getPaymentBill()
					.getId().toString();
			uiContext.put(UIContext.ID, paymentId);
			try {
				traceDownUIwindow = UIFactory.createUIFactory(
						UIFactoryName.MODEL).create(uiName, uiContext, null,
						OprtState.VIEW, WinStyle.SHOW_KINGDEELOGO);
			} catch (Exception e1) {
				handUIExceptionAndAbort(e1);
			}
			if (traceDownUIwindow != null) {
				// EditUI billEdit=(EditUI)traceDownUIwindow.getUIObject();
				// billEdit.getUIToolBar().setVisible(false);
				traceDownUIwindow.show();
			}
			break;
		}
		default: {
			// super.actionTraceDown_actionPerformed(e);
			PayReqUtils.traceDownFDCPaymentBill(editData, this);
		}
		}

		// super.actionTraceDown_actionPerformed(e);
	}

	public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception {
		// TODO 上查合同付款计划
		// super.actionTraceUp_actionPerformed(e);
		if (editData != null && editData.getId() != null
				&& PayReqUtils.isContractBill(editData.getContractId())) {
			String contractId = editData.getContractId();
			ContractPayPlanEditUI.showEditUI(this, contractId, "VIEW");
		} else {
			MsgBox.showWarning(this, "没有合同计划");
		}
	}

	private void showSelectDeductList(ActionEvent e) throws Exception {

		boolean canAdjust = checkCanSubmit();
		String state = canAdjust ? getOprtState() : OprtState.VIEW;

		// uiWindow=null;//暂时每次都实例一个UIWindow
		if (deductUIwindow == null) {
			UIContext uiContext = new UIContext(this);

			uiContext.put("contractBillId", editData.getContractId());
			uiContext.put("payRequestBillId", editData.getId().toString());
			uiContext.put("createTime", editData.getCreateTime().clone());
			uiContext.put("billState", state);
			uiContext.put("exRate", contractBill.getExRate());
			// FilterInfo filter = getDeductFilter();
			/*
			 * 过滤出DeductOfPayReqBillEntry内含有的相同合同且不在本申请单的扣款单, 已放入DeductListUI中
			 */
			// uiContext.put("defaultFilter", filter);
			// uiContext.put("selectSet", selectSet);
			try {
				deductUIwindow = UIFactory
						.createUIFactory(UIFactoryName.MODEL)
						.create(
								"com.kingdee.eas.fdc.contract.client.DeductListUI",
								uiContext, null, null);

			} catch (Exception e1) {
				handUIExceptionAndAbort(e1);
			}

		} else {
			IUIObject myui = deductUIwindow.getUIObject();
			if (myui instanceof DeductListUI) {
				DeductListUI u = (DeductListUI) myui;
				myui.getUIContext().put("billState", state);
				u.beforeExcutQuery(null);
				u.execQuery();
			}

		}
		if (deductUIwindow == null) {
			return;
		}
		deductUIwindow.show();

		IUIObject myui = deductUIwindow.getUIObject();
		if (myui instanceof DeductListUI) {
			DeductListUI u = (DeductListUI) myui;
			if (u.isOkClicked()) {
				try {
					DeductOfPayReqBillFactory.getRemoteInstance().reCalcAmount(
							editData.getId().toString());
					tableHelper.reloadDeductTable(editData, getDetailTable(),deductTypeCollection);
					tableHelper.reloadGuerdonValue(editData, u.getGuerdonData());
					tableHelper.reloadCompensationValue(editData, u.getCompensationData());
					if (PayReqUtils.isContractBill(editData.getContractId())) {
						if (partAParam) {
							tableHelper.reloadPartAValue(editData, u
									.getPartAData());
						} else {
							tableHelper.reloadPartAConfmValue(editData, u
									.getPartAConfmData());
						}
					}
					tableHelper.updateDynamicValue(editData,contractBill,contractChangeBillCollection,paymentBillCollection);
				} catch (Exception e1) {
					handUIExceptionAndAbort(e1);
				}
			}
		}

	}

	/**
	 * output getEditUIName method
	 */
	protected String getEditUIName() {
		return com.kingdee.eas.fdc.contract.client.PayRequestBillEditUI.class
				.getName();
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return com.kingdee.eas.fdc.contract.PayRequestBillFactory
				.getRemoteInstance();
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

		return new com.kingdee.eas.fdc.contract.PayRequestBillEntryInfo();
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		PayRequestBillInfo objectValue = new PayRequestBillInfo();
		objectValue.setCreator((UserInfo) (SysContext.getSysContext().getCurrentUserInfo()));
//		objectValue.setCreateTime(new Timestamp(System.currentTimeMillis()));
		
		try {
			objectValue.setCreateTime(FDCDateHelper.getServerTimeStamp());
//			objectValue.setSignDate(FDCDateHelper.getServerTimeStamp());
			objectValue.setPayDate(new Date(FDCDateHelper.getServerTimeStamp().getTime()));
		} catch (BOSException e1) {
			handUIExceptionAndAbort(e1);
		}
		objectValue.setSourceType(SourceTypeEnum.ADDNEW);

		// 付款日期
//		objectValue.setPayDate(DateTimeUtils.truncateDate(new Date()));
		objectValue.setHasClosed(false);
		// 本位币
		objectValue.setCurrency(baseCurrency);

		String contractBillId = (String) getUIContext().get("contractBillId");
		if (contractBillId == null) {
			contractBillId = this.editData.getContractId();
		}
		if (contractBillId != null && contractBillId.trim().length() > 1) {
			// 无文本
			if (PayReqUtils.isConWithoutTxt(contractBillId)) {
				createNewData_WithoutTextContract(objectValue, contractBillId);
			} else {
				// 有文本
				if (BOSUuid.read(contractBillId).getType().equals(
						contractBill.getBOSType())) {
					createNewdata_Contract(objectValue, contractBillId);
				}
			}
		}

		// 已完工工程量金额
		objectValue.setCostAmount(FDCConstants.ZERO);

		// 组织
		objectValue.setOrgUnit(costOrg);
		// editData.setCU(curProject.getCU());

		objectValue.setBookedDate(bookedDate);
		objectValue.setPeriod(curPeriod);

		// 是否需要付款
		objectValue.setIsPay(true);

		if (isAutoComplete) {
			objectValue.setPaymentProportion(FDCConstants.ONE_HUNDRED);
		}

		// 计算累计结算加
		try {
			Map param = new HashMap();
			param.put("ContractBillId", contractBill.getId().toString());
			Map totalSettle = ContractFacadeFactory.getRemoteInstance()
					.getTotalSettlePrice(param);
			if (totalSettle != null) {
				objectValue.setTotalSettlePrice((BigDecimal) totalSettle
						.get("SettlePrice"));
			} else {
				objectValue.setTotalSettlePrice(FDCConstants.ZERO);
			}
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
		// 开票日期
		objectValue.setInvoiceDate(serverDate);
		
		return objectValue;
	}

	private void reloadDynamicValue() {
		
		try {
			if (PayReqUtils.isContractBill(editData.getContractId())) {
								
				tableHelper.updateDynamicValue(editData,contractBill,contractChangeBillCollection,paymentBillCollection);
				tableHelper.reloadDeductTable(editData, getDetailTable(),deductTypeCollection);
				tableHelper.updateGuerdonValue(editData, editData.getContractId(),guerdonOfPayReqBillCollection, guerdonBillCollection);
				tableHelper.updateCompensationValue(editData, editData.getContractId(),compensationOfPayReqBillCollection);
				
				if(partAParam){
					tableHelper.updatePartAValue(editData, editData.getContractId(), partAOfPayReqBillCollection);
				}else{
					tableHelper.updatePartAConfmValue(editData, editData.getContractId(), partAConfmOfPayReqBillCollection);
				}
				((ICell) bindCellMap
						.get(PayRequestBillContants.PROJECTPRICEINCONTRACT))
						.setValue(null);
				((ICell) bindCellMap
						.get(PayRequestBillContants.PROJECTPRICEINCONTRACTORI))
						.setValue(null);
				if(this.isAdvance()){
					((ICell) bindCellMap
							.get(PayRequestBillContants.ADVANCE))
							.setValue(null);
					((ICell) bindCellMap
							.get(PayRequestBillContants.LOCALADVANCE))
							.setValue(null);
				}
				((ICell) bindCellMap.get(PayRequestBillContants.ADDPROJECTAMT))
						.setValue(null);
				((ICell) bindCellMap
						.get(PayRequestBillContants.PAYPARTAMATLAMT))
						.setValue(null);
			}
			handleCodingRule();
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
	}

	private void createNewData_WithoutTextContract(
			com.kingdee.eas.fdc.contract.PayRequestBillInfo objectValue,
			String contractBillId) {
		ContractWithoutTextInfo withoutTextInfo;
		try {
			withoutTextInfo = ContractWithoutTextFactory.getRemoteInstance()
					.getContractWithoutTextInfo(
							new ObjectUuidPK(BOSUuid.read(contractBillId)));
			objectValue.setContractId(contractBillId);
			objectValue.setSource(withoutTextInfo.getBOSType().toString());
			objectValue.setAmount(withoutTextInfo.getAmount());// 原币金额
			objectValue.setContractPrice(withoutTextInfo.getAmount());//
			objectValue.setContractName(withoutTextInfo.getName());// 合同名
			objectValue.setContractNo(withoutTextInfo.getNumber());
			// 无文本合同名称
			// kdtEntrys.getCell(1,1).setValue(withoutTextInfo.getBillName());
			// 无文本合同造价
			// kdtEntrys.getCell(0,5).setValue(withoutTextInfo.getAmount());
			// 带出无文本合同收款单位
			objectValue.setSupplier((SupplierFactory.getRemoteInstance()
					.getSupplierInfo(new ObjectUuidPK((withoutTextInfo
							.getReceiveUnit().getId())))));
			objectValue.setRealSupplier((SupplierFactory.getRemoteInstance()
					.getSupplierInfo(new ObjectUuidPK((withoutTextInfo
							.getReceiveUnit().getId())))));
			// 带出无文本合同工程项目
			CurProjectInfo curProject = withoutTextInfo.getCurProject();
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("name");
			selector.add("displayName");
			selector.add("fullOrgUnit.name");
			curProject = CurProjectFactory.getRemoteInstance()
					.getCurProjectInfo(new ObjectUuidPK(curProject.getId()),
							selector);
			objectValue.setCurProject(curProject);
			objectValue.setCU(withoutTextInfo.getCU());
			objectValue.setCompletePrjAmt(withoutTextInfo.getAmount());
			objectValue.setCostAmount(withoutTextInfo.getAmount());
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}

	}

	// 合同的申请单
	private void createNewdata_Contract(PayRequestBillInfo objectValue,
			String contractBillId) {
		try {
			// 合同号
			objectValue.setContractId(contractBillId);
			objectValue.setSource(contractBill.getBOSType().toString());
			objectValue.setContractNo(contractBill.getNumber());
			objectValue.setContractName(contractBill.getName());
			objectValue.setContractPrice(contractBill.getAmount());
			objectValue.setCurrency(contractBill.getCurrency());
			
			objectValue.setExchangeRate(contractBill.getExRate());
			objectValue.setUseDepartment(contractBill.getRespDept());

			// 付款比例
			if (!contractBill.isHasSettled()) {
				objectValue.setPaymentProportion(contractBill.getPayScale());
			} else {
				if(!isSimpleFinancial){
					objectValue.setPaymentProportion(new BigDecimal("100"));
					objectValue.setCompletePrjAmt(contractBill.getSettleAmt());
					txtcompletePrjAmt.setValue(contractBill.getSettleAmt());
				}
				objectValue.setCostAmount(contractBill.getSettleAmt());
				// 已经结算的付款比例
				objectValue.setGrtAmount(payScale);
				txtGrtAmount.setValue(payScale);
			}

			// 上期累计实付
			objectValue
					.setLstPrjAllPaidAmt(contractBill.getPrjPriceInConPaid());
			objectValue.setLstAddPrjAllPaidAmt(contractBill.getAddPrjAmtPaid());
			objectValue.setLstAMatlAllPaidAmt(contractBill
					.getPaidPartAMatlAmt());

			// 带出合同乙方
			objectValue.setSupplier(contractBill.getPartB());
			objectValue.setRealSupplier(contractBill.getPartB());
			CurProjectInfo curProject = contractBill.getCurProject();

			// 根据实际收款单位，得到付款帐户和付款银行
			if (contractBill.getPartB() != null) {
				String supperid = contractBill.getPartB().getId().toString();
				PayReqUtils.fillBank(objectValue, supperid, curProject
						.getCU().getId().toString());
			}

			objectValue.setCurProject(curProject);
			objectValue.setCU(contractBill.getCU());

			tableHelper.updateDynamicValue(objectValue,contractBill,contractChangeBillCollection,paymentBillCollection);
			tableHelper.updateGuerdonValue(objectValue, contractBillId,guerdonOfPayReqBillCollection, guerdonBillCollection);
			tableHelper.updateCompensationValue(objectValue, contractBillId,compensationOfPayReqBillCollection);
			if (partAParam) {
				tableHelper.updatePartAValue(editData, contractBillId,
				partAOfPayReqBillCollection);
			} else {
				tableHelper.updatePartAConfmValue(editData, contractBillId,
				partAConfmOfPayReqBillCollection);
			}
			if(STATUS_ADDNEW.equals(getOprtState()) && editData != null && !FDCBillStateEnum.AUDITTED.equals(editData.getState())){
				reloadDynamicValue();
			}
			
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
	}

	private void setAutoNumber() {
		if (editData.getNumber() == null) {
			String sysNumber = null;
			try {
				if (com.kingdee.eas.common.client.SysContext.getSysContext()
						.getCurrentFIUnit() != null) {
					// 应该传成本中心，与单据实体的主业务组织要对应
					sysNumber = com.kingdee.eas.framework.FrameWorkUtils
							.getCodeRuleClient(
									editData,
									((com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo) com.kingdee.eas.common.client.SysContext
											.getSysContext()
											.getCurrentCostUnit()).getId()
											.toString());

				}
			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			}

			if (sysNumber != null && sysNumber.trim().length() > 0) {
				editData.setNumber(sysNumber);
				txtPaymentRequestBillNumber.setEnabled(false);
			} else {
				txtPaymentRequestBillNumber.setEnabled(true);
			}
		} else {
			if (editData.getNumber().trim().length() > 0) {
				txtPaymentRequestBillNumber.setText(editData.getNumber());
				// txtPaymentRequestBillNumber.setEnabled(false);
			} else {
				txtPaymentRequestBillNumber.setEnabled(true);
			}
		} // end if
	}

	protected boolean checkCanSubmit() throws Exception {
		if (isIncorporation && ((FDCBillInfo) editData).getPeriod() == null) {
			MsgBox.showWarning(this, "启用成本月结期间不能为空，请在基础资料维护期间后，重新选择业务日期");
			SysUtil.abort();
		}
		return super.checkCanSubmit();
	}

	protected void verifyInput(ActionEvent e) throws Exception {

		super.verifyInput(e);

		/*
		 * //付款类型的控制 1. 必须合同结算之后才能做结算款类别的付款申请单 2. 付了结算款之后才能付保修款（已控制） 3.
		 * 保修款总金额不能大于结算单上的质保金
		 */

		/*
		 * if(e.getSource()!=btnSubmit){
		 * if(editData.getNumber()==null||editData.getNumber().length()<1){
		 * MsgBox.showWarning(this, getRes("NullNumber")); SysUtil.abort();
		 * }else{ return; } }
		 */
		/*
		 * 检查原币金额与单元格内的发生额实付金额是否一致
		 */
		Object cell = bindCellMap.get(PayRequestBillContants.CURPAID);
		if (cell instanceof ICell) {
			Object value = ((ICell) cell).getValue();
			if (value != null) {
				try {
					BigDecimal cellAmount = new BigDecimal(value.toString());
					BigDecimal amount = txtAmount.getBigDecimalValue();
					if (cellAmount.doubleValue() < 0
							|| amount == null
							|| amount.doubleValue() < 0
							|| (cellAmount.doubleValue() > 0 && cellAmount
									.compareTo(amount) != 0)) {
						MsgBox.showWarning(this, getRes("verifyAmount"));
						SysUtil.abort();
					}
					BigDecimal completePrj = txtcompletePrjAmt.getBigDecimalValue();
					if(completePrj == null)
						completePrj = FDCHelper.ZERO;
					if(FDCHelper.ZERO.compareTo(completePrj)==0 && FDCHelper.ZERO.compareTo(amount)==0){
						String msg = "已完工工程量和实付金额不能同时为0！";
						if(isSimpleFinancial){
							msg = "实付金额不能为0!";
						}
						//预付款
						if(isAdvance()){
							if (FDCHelper.ZERO.compareTo(FDCHelper
									.toBigDecimal(getDetailTable().getCell(
											rowIndex, columnIndex).getValue())) == 0
									&& FDCHelper.ZERO.compareTo(FDCHelper.toBigDecimal(getDetailTable().getCell(
											rowIndex+1, columnIndex).getValue())) == 0) {
								msg = "实付款与预付款不能同时为 0!";
								MsgBox.showWarning(this, msg);
								SysUtil.abort();
							}
							
						}else{
							MsgBox.showWarning(this,msg);
							SysUtil.abort();
						}
					}
				} catch (NumberFormatException e1) {
					handUIExceptionAndAbort(e1);
				}
			}
		}
		if (!isSaveAction()) {
			checkAmt(editData);
			checkCompletePrjAmt();
			if(this.isAdvance()){
				tableHelper.checkAdvance(editData,this.bindCellMap);
			}
		}

		BigDecimal lastestPrice = FDCHelper.toBigDecimal(editData.getLatestPrice(),2);
		if (txtpaymentProportion.isRequired() && txtcompletePrjAmt.isRequired()) {
			BigDecimal propAmt = txtpaymentProportion.getBigDecimalValue();
			BigDecimal completeAmt = FDCHelper.toBigDecimal(txtcompletePrjAmt.getBigDecimalValue(),2);
			if (propAmt != null) {
				if (propAmt.compareTo(FDCHelper.ZERO) <= 0
						|| propAmt.compareTo(FDCHelper.ONE_HUNDRED) > 0) {
//					MsgBox.showError(this, "付款比例必须大于0,小于等于100%");
//					SysUtil.abort();
//				} else if (FDCHelper.toBigDecimal(completeAmt).signum() == 0) {
//					MsgBox.showError(this, "已完工工程量必须大于0");
//					SysUtil.abort();
				} else if(!(FDCHelper.toBigDecimal(completeAmt).signum()==0)){
//					BigDecimal amount = FDCHelper
//							.toBigDecimal(((ICell) bindCellMap
//									.get(PayRequestBillContants.PROJECTPRICEINCONTRACT))
//									.getValue());
					// BigDecimal amount = txtBcAmount.getBigDecimalValue();
//					BigDecimal tmpAmt = amount.setScale(4,BigDecimal.ROUND_HALF_UP).divide(completeAmt,
//							BigDecimal.ROUND_HALF_UP).multiply(
//							FDCHelper.ONE_HUNDRED);
//					if (tmpAmt.compareTo(propAmt) != 0) {
//						MsgBox.showError(this, "付款比例＝原币金额/已完工工程量 *100% 关系不成立");
//						SysUtil.abort();
//					}
					Object amount = ((ICell) bindCellMap.get(PayRequestBillContants.PROJECTPRICEINCONTRACT))
							.getValue();
					if(amount == null){
						MsgBox.showError(this, "申请金额不允许为空！");
						SysUtil.abort();
					}
					if (isSaveAction()) {
						if (isSimpleFinancial) {
							if (FDCHelper.toBigDecimal(amount, 2).compareTo(
									lastestPrice) > 0) {
								int ok = MsgBox.showConfirm2(this,
										"实付金额大于合同最新造价,是否保存？");
								if (ok != MsgBox.OK) {
									SysUtil.abort();
								}
							}
						}
						if (completeAmt.compareTo(lastestPrice) > 0) {
							int ok = MsgBox.showConfirm2(this,
									"已完工工程量金额大于合同最新造价,是否保存？");
							if (ok != MsgBox.OK) {
								SysUtil.abort();
							}
						}
					}

				}
			}
		}

		if(isRealizedZeroCtrl) {
			PaymentTypeInfo type=(PaymentTypeInfo)prmtPayment.getValue();
			if(FDCHelper.isNullZero(txtTotalSettlePrice.getBigDecimalValue())
					&&type.getName()!=null&&!type.getName().equals("预付款")){
				MsgBox.showError(prmtPayment, "已实现产值为0只允许选择\"预付款\"！");
				SysUtil.abort();
			}
		}
		
		//发票是否必录
		if (isInvoiceRequired) {
			boolean isNotInput = false;
			if (txtInvoiceAmt.getBigDecimalValue() == null) {
				// 为零时可不录
				if (!FDCHelper.ZERO.equals(txtInvoiceAmt.getBigDecimalValue())) {
					isNotInput = true;
				}
			} else if (FDCHelper.ZERO.compareTo(txtInvoiceAmt
					.getBigDecimalValue()) != 0
					&& FDCHelper.isEmpty(txtInvoiceNumber.getText())) {
				isNotInput = true;
			}
			if (isNotInput) {
				MsgBox.showWarning(this, "发票号与发票金额必须录入!");
				SysUtil.abort();
			}
		}
		if (FDCHelper.toBigDecimal(txtAllInvoiceAmt.getBigDecimalValue())
				.setScale(2, BigDecimal.ROUND_HALF_UP).compareTo(lastestPrice) == 1) {
			MsgBox.showWarning(this, "累计发票金额不能超过合同最新造价！");
			SysUtil.abort();
		}
	}

	/**
	 * Description:币种
	 * 
	 * @author sxhong Date 2006-8-30
	 * @param e
	 * @throws Exception
	 * @see com.kingdee.eas.fdc.contract.client.AbstractPayRequestBillEditUI#currency_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent)
	 */
	protected void currencydataChanged(DataChangeEvent e) throws Exception {

		super.currency_dataChanged(e);
		Object newValue = e.getNewValue();
		if (newValue instanceof CurrencyInfo) {
			if (isFirstLoad && !getOprtState().equals(OprtState.ADDNEW))
				return;
			if (e.getOldValue() != null
					&& ((CurrencyInfo) e.getOldValue()).getId().equals(
							((CurrencyInfo) newValue).getId())) {
				// 设置汇率的值，在录入界面点新增时可能的情况
				return;
			}
			BOSUuid srcid = ((CurrencyInfo) newValue).getId();
			Date bookedDate = (Date)pkbookedDate.getValue();
			//txtexchangeRate.setValue(FDCClientHelper.getLocalExRateBySrcCurcy(this, srcid,company,bookedDate));
			
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
	    	txtAmount.setPrecision(curPrecision);
	    	
			setAmount();

			setPropPrjAmount("amount", null);

		}
	}

	protected void txtexchangeRate_focusLost(FocusEvent e) throws Exception {
		super.txtexchangeRate_focusLost(e);
		//setAmount();
		
	}

	protected void supplier_dataChanged(DataChangeEvent e) throws Exception {
		// 带出收款银行,收款账号等
		super.supplier_dataChanged(e);
	}

	protected void realSupplier_dataChanged(DataChangeEvent e) throws Exception {
		super.realSupplier_dataChanged(e);
		Object newValue = e.getNewValue();
		if (newValue instanceof SupplierInfo) {
			// 首次加载
			if (isFirstLoad && !getOprtState().equals(OprtState.ADDNEW))
				return;
			// 用newValue.equalse(e.getOldValue()) 会出错,因为比较的是堆栈的值
			if ((e.getOldValue() instanceof SupplierInfo)
					&& ((SupplierInfo) e.getOldValue()).getId().equals(
							((SupplierInfo) newValue).getId())
					&& !getOprtState().equals(OprtState.ADDNEW)) {
				return;
			}

			SupplierInfo supplier = (SupplierInfo) newValue;
			BOSUuid supplierid = supplier.getId();
			
			//供应商的获取			
			String supperid = supplierid.toString();			
			PayReqUtils.fillBank(editData,supperid,curProject.getCU().getId().toString());	
			txtrecAccount.setText(editData.getRecAccount());
			txtrecBank.setText(editData.getRecBank());
		}
	}

    public IObjectPK runSubmit() throws Exception
    {
		//预算控制
		checkMbgCtrlBalance();
		checkFdcBudget();
		return super.runSubmit();
	}

	protected void txtAmount_focusLost(FocusEvent e) throws Exception {
		/*
		 * super.txtAmount_focusLost(e); setAmount();
		 * setPropPrjAmount("amount");
		 */
	}

	protected void txtAmount_dataChanged(DataChangeEvent e) throws Exception {
		super.txtAmount_dataChanged(e);
		setAmount();
	}

	protected void btnInputCollect_actionPerformed(ActionEvent e)
			throws Exception {
		// 填入汇总数操作
		super.btnInputCollect_actionPerformed(e);
		Object cell = bindCellMap.get(PayRequestBillContants.CURPAID);
		if (cell instanceof ICell) {
			Object value = ((ICell) cell).getValue();
			if (value != null) {
				try {
					txtAmount.setValue(new BigDecimal(value.toString()));
				} catch (NumberFormatException e1) {
					handUIExceptionAndAbort(e1);
				}
//				setAmount();
			}
		}
		cell = bindCellMap.get(PayRequestBillContants.CURPAIDLOCAL);
		if (cell instanceof ICell) {
			Object value = ((ICell) cell).getValue();
			if (value != null) {
				try {
					BigDecimal localamount = FDCHelper.toBigDecimal(value);
					txtBcAmount.setValue(localamount);
					if (localamount.compareTo(FDCConstants.ZERO) != 0) {
						localamount = localamount.setScale(2, BigDecimal.ROUND_HALF_UP);
						// 大写金额为本位币金额
						String cap = FDCClientHelper.getChineseFormat(localamount,
								false);
						// FDCHelper.transCap((CurrencyInfo) value, amount);
						txtcapitalAmount.setText(cap);
					} else {
						txtcapitalAmount.setText(null);
					}
				} catch (NumberFormatException e1) {
					handUIExceptionAndAbort(e1);
				}
				
			}
		}
	}

	/**
	 * Description: 设置本位币金额,大小写等
	 * 
	 * @author sxhong Date 2006-9-6
	 */
	private void setAmount() {

		BigDecimal amount = (BigDecimal) txtAmount.getNumberValue();
		Object exchangeRate = txtexchangeRate.getNumberValue();
		if (amount != null) {
			Object value = prmtcurrency.getValue();
			if (value instanceof CurrencyInfo) {
				// String cap = FDCHelper.transCap((CurrencyInfo) value,
				// amount);
				// txtcapitalAmount.setText(cap);

				// 本位币处理
				CompanyOrgUnitInfo currentFIUnit = SysContext.getSysContext()
						.getCurrentFIUnit();
				CurrencyInfo baseCurrency = currentFIUnit.getBaseCurrency();
				BOSUuid srcid = ((CurrencyInfo) value).getId();
				if (baseCurrency != null) {
					if (srcid.equals(baseCurrency.getId())) {
						/*
						 * if (exchangeRate instanceof
						 * BigDecimal&&((BigDecimal)exchangeRate).intValue()!=1) {
						 * MsgBox.showWarning(this,"你选择的是本位币，但是汇率不等于1"); }
						 */
						txtBcAmount.setValue(amount);
						txtexchangeRate.setValue(FDCConstants.ONE);
						txtexchangeRate.setEditable(false);
						// return;
					} else
						txtexchangeRate.setEditable(true);
				}
			}

			BigDecimal localamount = (BigDecimal) txtBcAmount.getNumberValue();
			if (exchangeRate instanceof BigDecimal) {
				localamount = amount.multiply((BigDecimal) exchangeRate);
				txtBcAmount.setValue(localamount);
			}

			if (localamount!=null&&localamount.compareTo(FDCConstants.ZERO) != 0) {
				localamount = localamount.setScale(2, BigDecimal.ROUND_HALF_UP);
				// 大写金额为本位币金额
				String cap = FDCClientHelper.getChineseFormat(localamount,
						false);
				// FDCHelper.transCap((CurrencyInfo) value, amount);
				txtcapitalAmount.setText(cap);
			} else {
				txtcapitalAmount.setText(null);
			}

		} else {
			// 汇率处理
			Object value = prmtcurrency.getValue();
			if (value instanceof CurrencyInfo) {
				// 本位币处理
				CompanyOrgUnitInfo currentFIUnit = SysContext.getSysContext()
						.getCurrentFIUnit();
				CurrencyInfo baseCurrency = currentFIUnit.getBaseCurrency();
				BOSUuid srcid = ((CurrencyInfo) value).getId();
				if (baseCurrency != null) {
					if (srcid.equals(baseCurrency.getId())) {
						/*
						 * if (exchangeRate instanceof
						 * BigDecimal&&((BigDecimal)exchangeRate).intValue()!=1) {
						 * MsgBox.showWarning(this,"你选择的是本位币，但是汇率不等于1"); }
						 */
						txtBcAmount.setValue(amount);
						txtexchangeRate.setValue(FDCConstants.ONE);
						txtexchangeRate.setEditable(false);
						return;
					} else
						txtexchangeRate.setEditable(true);
				}

			}
			txtBcAmount.setValue(FDCConstants.ZERO);
			txtcapitalAmount.setText(null);
		}
	}

	/*
	 * 得到资源文件
	 */
	private String getRes(String resName) {
		return PayReqUtils.getRes(resName);
	}

	public IUIObject getInstance(Map uiContext) {
		return super.getInstance(uiContext);
		/*
		 * PayRequestBillEditUI ui=null; try { ui=new PayRequestBillEditUI(); }
		 * catch (Exception e) { handUIException(e); } return ui;
		 */
	}

	/**
	 * 设置表格单元格的可编辑状态及颜色
	 * 
	 * @author sxhong Date 2006-9-28
	 */
	private void setTableCellColorAndEdit() {
		tableHelper.setTableCellColorAndEdit(getDetailTable());
	}

	public void beforeActionPerformed(ActionEvent e) {

		Object source = e.getSource();
		super.beforeActionPerformed(e);
		if (getOprtState().equals(OprtState.VIEW) && source.equals(btnEdit)) {
			// setTableCellColorAndEdit();
			actionAddNew.setEnabled(true);
		}
	}

	public void afterActionPerformed(ActionEvent e) {
		super.afterActionPerformed(e);
		Object source = e.getSource();
		if (source == btnNext || source == btnPre || source == btnFirst
				|| source == btnLast || source == menuItemNext
				|| source == menuItemPre || source == menuItemLast
				|| source == menuItemFirst || source == btnRemove || source == menuItemRemove  ) {
			// isFirstLoad=true;
			try {
				// isFirstLoad=true;
				// editData=(PayRequestBillInfo)getDataObject();
				// onLoad();
				PayReqUtils.setValueToCell(editData, bindCellMap);
				tableHelper.reloadDeductTable(editData, getDetailTable(),deductTypeCollection);
				tableHelper.reloadGuerdonValue(editData, null);
				tableHelper.reloadCompensationValue(editData, null);
				if (PayReqUtils.isContractBill(editData.getContractId())) {
					if (partAParam) {
						tableHelper.reloadPartAValue(editData, null);
					} else {
						tableHelper.reloadPartAConfmValue(editData, null);
					}
				}
				setOprtState(getOprtState());
			} catch (Exception e1) {
				handUIExceptionAndAbort(e1);
			}

			if (editData.isHasClosed()) {
				actionUnClose.setEnabled(true);
				actionUnClose.setVisible(true);
				actionClose.setEnabled(false);
				actionClose.setVisible(false);
			} else {
				actionClose.setEnabled(true);
				actionClose.setVisible(true);
				actionUnClose.setEnabled(false);
				actionUnClose.setVisible(false);
			}
			deductUIwindow = null;
		}

		if (source == btnRemove || source == menuItemRemove) {
			// 获取具有对象更新锁的对象ID
			// idList = (IIDList) getUIContext().get(UIContext.IDLIST);
			if (idList.size() == 0) {
				actionClose.setEnabled(false);
				actionUnClose.setEnabled(false);
				actionTraceDown.setEnabled(false);
			}
		}
	}

	protected KDTextField getNumberCtrl() {
		return txtPaymentRequestBillNumber;
	}

	/**
	 * 设置编辑时界面的状态,主要是对表格的设置
	 * 
	 * @author sxhong Date 2007-1-28
	 */
	private void setEditState() {
		String contractId = editData.getContractId();
		if (contractId == null || PayReqUtils.isConWithoutTxt(contractId)) {

			final StyleAttributes sa = kdtEntrys.getStyleAttributes();
			sa.setLocked(true);
			sa.setBackground(noEditColor);
			btnInputCollect.setEnabled(false);
			kdtEntrys.setEnabled(false);
		} else {
			btnInputCollect.setEnabled(false);
			setTableCellColorAndEdit();

			actionAdjustDeduct.setEnabled(true);
		}
		mergencyState.setEnabled(true);
		chkIsPay.setEnabled(true);
	}

	protected void afterSubmitAddNew() {
		super.afterSubmitAddNew();
		try {
			if (PayReqUtils.isContractBill(editData.getContractId())) {
				tableHelper.updateGuerdonValue(editData, editData.getContractId(),guerdonOfPayReqBillCollection, guerdonBillCollection);
			}
			handleCodingRule();
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
		if(OprtState.ADDNEW.equals(getOprtState())){
			txtpaymentProportion.setValue(FDCHelper.ZERO);
			txtcompletePrjAmt.setValue(FDCHelper.ZERO);
		}
		calAllCompletePrjAmt();
	}

	protected void txtpaymentProportion_dataChanged(DataChangeEvent e)
			throws Exception {
		super.txtpaymentProportion_dataChanged(e);
		// setPropPrjAmount("paymentProp", e);

	}

	protected void txtcompletePrjAmt_dataChanged(DataChangeEvent e)
			throws Exception {
		super.txtcompletePrjAmt_dataChanged(e);
		// setPropPrjAmount("completePrjAmt", e);
//		calAllCompletePrjAmt();
	}

	/**
	 * 设置付款比例，已完工工程量与原币金额之间的关系： 已完工工程量金额＝原币金额÷付款比例
	 * 
	 * @author sxhong Date 2007-3-12
	 */
	private void setPropPrjAmount(String cause, DataChangeEvent e) {
		if (isFirstLoad || (!txtpaymentProportion.isRequired())
				|| (isSeparate && contractBill!=null&&contractBill.isIsCoseSplit()))
			return;

		// if(PayReqUtils.isContractWithoutText(editData.getcon))
		// BigDecimal amount = txtAmount.getBigDecimalValue();
		// 用本位币进行计算
		BigDecimal amount = FDCHelper
				.toBigDecimal(((ICell) bindCellMap
						.get(PayRequestBillContants.PROJECTPRICEINCONTRACT))
						.getValue());// txtBcAmount.getBigDecimalValue();
		BigDecimal paymentProp = txtpaymentProportion.getBigDecimalValue();
		BigDecimal completePrj = txtcompletePrjAmt.getBigDecimalValue();

		if (amount == null)
			amount = FDCHelper.ZERO;
		if (paymentProp == null)
			paymentProp = FDCHelper.ZERO;
		if (completePrj == null)
			completePrj = FDCHelper.ZERO;
		if (cause.equals("amount")) {
			if (paymentProp.compareTo(FDCHelper.ZERO) == 0) {
				if (completePrj.compareTo(FDCHelper.ZERO) == 0) {
					return;
				} else {
					txtpaymentProportion.setRequired(false);
					txtpaymentProportion.setValue(amount.setScale(4,BigDecimal.ROUND_HALF_UP).divide(
							completePrj, BigDecimal.ROUND_HALF_UP).multiply(
							FDCHelper.ONE_HUNDRED));
					txtpaymentProportion.setRequired(true && !isAutoComplete);
				}
			} else {
				txtcompletePrjAmt.setValue(amount.setScale(4,BigDecimal.ROUND_HALF_UP).divide(
						paymentProp, BigDecimal.ROUND_HALF_UP).multiply(
						FDCHelper.ONE_HUNDRED));
			}
		} else if (cause.equals("completePrjAmt")) {
			if (completePrj.compareTo(FDCHelper.ZERO) == 0) {
				return;
			}
			txtpaymentProportion.setRequired(false);
			txtpaymentProportion.setValue(amount.setScale(4,BigDecimal.ROUND_HALF_UP).divide(
					completePrj, BigDecimal.ROUND_HALF_UP).multiply(
					FDCHelper.ONE_HUNDRED));
			txtpaymentProportion.setRequired(true && !isAutoComplete);
		} else {
			if (paymentProp.compareTo(FDCHelper.ZERO) == 0) {
				return;
			} else {
				txtcompletePrjAmt.setValue(amount.setScale(4,BigDecimal.ROUND_HALF_UP).divide(
						paymentProp, BigDecimal.ROUND_HALF_UP).multiply(
						FDCHelper.ONE_HUNDRED));
			}
		}
		if(isAutoComplete){
			txtcompletePrjAmt.setValue(amount);
			editData.setCompletePrjAmt(amount);
		}
		calAllCompletePrjAmt();
	}

	/**
	 * 处理付款比例及已完工工程量的加载 在onLoad中调用
	 * 
	 * @author sxhong Date 2007-3-13
	 */
	private void initPaymentProp() {
		String contractId = editData.getContractId();
		if (contractId != null && !PayReqUtils.isConWithoutTxt(contractId)) {
			txtpaymentProportion.setRequired(true && !isAutoComplete);
			txtcompletePrjAmt.setRequired(true && !isAutoComplete);
			try {

				if (!contractBill.isHasSettled()
						&& editData.getPaymentProportion() == null) {
					// editData.setPaymentProportion(contractBill.getPayScale());
					// txtpaymentProportion.setValue(contractBill.getPayScale());
				} else if (contractBill.isHasSettled()
						&& editData.getState() != FDCBillStateEnum.AUDITTED) {
					// editData.setPaymentProportion(null);
					// editData.setCompletePrjAmt(null);
					// editData.setPaymentProportion(new BigDecimal("100"));
					editData.setPaymentProportion(null);
					editData.setCompletePrjAmt(contractBill.getSettleAmt());
					txtcompletePrjAmt.setValue(contractBill.getSettleAmt());
					// txtpaymentProportion.setValue(new BigDecimal("100"));
					 txtcompletePrjAmt.setValue(FDCHelper.toBigDecimal(contractBill.getSettleAmt()));
					txtpaymentProportion.setEditable(false);
					txtcompletePrjAmt.setEditable(false);

					txtpaymentProportion.setRequired(false);
					txtcompletePrjAmt.setRequired(false);

				}

			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			}
		} else {
			txtpaymentProportion.setEditable(false);
			txtcompletePrjAmt.setEditable(false);
		}

		/*
		 * txtpaymentProportion.setPrecision(2);
		 * txtpaymentProportion.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE);
		 * txtpaymentProportion.setMinimumValue(FDCHelper.ZERO);
		 * txtpaymentProportion.setRoundingMode(BigDecimal.ROUND_HALF_EVEN); //
		 * txtpaymentProportion.setPercentDisplay(true);
		 * txtcompletePrjAmt.setPrecision(2);
		 * txtcompletePrjAmt.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE.multiply(new
		 * BigDecimal("100000")));
		 * txtcompletePrjAmt.setMinimumValue(FDCHelper.ZERO);
		 */
	}

	/**
	 * 付款申请单累计额大于合同金额最新造价时提醒（提交，审批）
	 * 
	 * @param ctx
	 * @param billInfo
	 * @throws BOSException
	 */
	private void checkAmt(PayRequestBillInfo billInfo) throws Exception {
		BigDecimal latestPrice = FDCHelper.toBigDecimal(billInfo.getLatestPrice(), 2);
		// if (billInfo.getLatestPrice() == null) {//支持结算为零值
		// return;
		// }
		/*********
		 * 付款申请单的币别，必须和合同的币别相同 2008-11-14 周勇 币别是否是本币 如果是本币则比较本币 如果是外币则比较原币
		 */
		boolean isBaseCurrency = true;
		CurrencyInfo cur = (CurrencyInfo) this.prmtcurrency.getValue();
		if (!cur.getId().toString().equals(baseCurrency.getId().toString())) {
			isBaseCurrency = false;
		}
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.appendFilterItem("contractId", billInfo.getContractId());
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SAVED_VALUE, CompareType.NOTEQUALS));

		view.getSelector().add("hasClosed");
		view.getSelector().add("amount");
		view.getSelector().add("state");
		view.getSelector().add("originalamount");
		view.getSelector().add("completePrjAmt");
		view.getSelector().add("projectPriceInContract");
		// view.getSelector().add("costAmount");
		view.getSelector().add("entrys.paymentBill.billStatus");
		view.getSelector().add("entrys.paymentBill.amount");
		view.getSelector().add("entrys.paymentBill.localAmt");
		view.getSelector().add("paymentType.payType.id");
		PayRequestBillCollection c = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);

		BigDecimal total = FDCHelper.ZERO;
//		BigDecimal totalLocal = FDCHelper.ZERO;
		BigDecimal completeTotal = FDCHelper.ZERO;
		BigDecimal projectPriceInContractTotal = FDCHelper.ZERO;
		BigDecimal totalpayAmount = FDCHelper.ZERO;// 累计实付款+未付的申请单
		BigDecimal totalpayAmountLocal = FDCHelper.ZERO;// 累计实付款+未付的申请单
		BigDecimal noPayAmt = FDCHelper.ZERO;
		BigDecimal noPayAmtLocal = FDCHelper.ZERO;
		BigDecimal payAmt = FDCHelper.ZERO;
		BigDecimal payAmtLocal = FDCHelper.ZERO;
		BigDecimal noKeepAmt = FDCHelper.ZERO;// 进度款之和
		BigDecimal noKeepAmtLocal = FDCHelper.ZERO;// 进度款之和
		for (int i = 0; i < c.size(); i++) {
			final PayRequestBillInfo info = c.get(i);
			if (info.getId().equals(billInfo.getId())) {
				// 分已存及未存两种状态,统计在后面处理
				continue;
			}
				total = total.add(FDCHelper.toBigDecimal(info.getOriginalAmount()));// 统计原币金额
				// (
				// 合同内工程款
				// +
				// 奖励
				// -
				// 扣款
				// )
//				totalLocal = totalLocal.add(FDCHelper.toBigDecimal(info.getAmount()));// 统计原币金额
				// (
				// 合同内工程款
				// +
				// 奖励
				// -
				// 扣款
				// )
			completeTotal = completeTotal.add(FDCHelper.toBigDecimal(info//
					.getCompletePrjAmt()));// 本期完成金额（已完工+奖励-索赔-扣款）
			projectPriceInContractTotal = projectPriceInContractTotal.add(FDCHelper.toBigDecimal(info.getProjectPriceInContract()));// 合同内工程款
			// （
			// 本期发生原币
			// ）
			boolean isKeepAmt = false;
			if (info.getPaymentType() != null && info.getPaymentType().getPayType() != null && info.getPaymentType().getPayType().getId().toString().equals(PaymentTypeInfo.keepID)) {
				isKeepAmt = true;
			}
			BigDecimal temp = FDCHelper.ZERO;
			BigDecimal tempLocal = FDCHelper.ZERO;
			BigDecimal _tempActuallyPayOriAmt = FDCHelper.ZERO;// 累计实付款原币临时变量
			BigDecimal _tempActuallyPayLocalAmt = FDCHelper.ZERO;// 累计实付款原币临时变量
			if (info.isHasClosed()) {
				if (info.getEntrys().size() > 0) {
					PaymentBillInfo payment = info.getEntrys().get(0).getPaymentBill();
					if (payment != null) {
						_tempActuallyPayOriAmt = payment.getAmount();
						_tempActuallyPayLocalAmt = payment.getLocalAmt();
					} else {
						_tempActuallyPayOriAmt = FDCHelper.toBigDecimal(FDCHelper.add(_tempActuallyPayOriAmt, info.getOriginalAmount()));
						_tempActuallyPayLocalAmt = FDCHelper.toBigDecimal(FDCHelper.add(_tempActuallyPayLocalAmt, info.getAmount()));
					}
				}
			} else {
				_tempActuallyPayOriAmt = FDCHelper.toBigDecimal(FDCHelper.add(_tempActuallyPayOriAmt, info.getOriginalAmount()));
				_tempActuallyPayLocalAmt = FDCHelper.toBigDecimal(FDCHelper.add(_tempActuallyPayLocalAmt, info.getAmount()));
			}
			// 如果付款申请单已经审批，即已经有关联的付款单
			if (FDCBillStateEnum.AUDITTED.equals(info.getState())) {
				int tempInt = info.getEntrys().size();
				for (int j = 0; j < tempInt; j++) {
					PaymentBillInfo payment = info.getEntrys().get(j).getPaymentBill();
					if (payment != null && payment.getBillStatus() == BillStatusEnum.PAYED) { // 并且该付款单已经付款
						temp = temp.add(FDCHelper.toBigDecimal(payment.getAmount()));
						tempLocal = tempLocal.add(FDCHelper.toBigDecimal(payment.getLocalAmt()));
						payAmt = payAmt.add(FDCHelper.toBigDecimal(payment.getAmount()));
						payAmtLocal = payAmtLocal.add(FDCHelper.toBigDecimal(payment.getLocalAmt()));
					} else if (payment != null && payment.getBillStatus() != BillStatusEnum.PAYED) {// 未付款
																									// ，
																									// 需要记录一下申请未付金额
						noPayAmt = FDCHelper.add(noPayAmt, info.getOriginalAmount());
						noPayAmtLocal = FDCHelper.add(noPayAmtLocal, info.getAmount());
					}
					if (temp.compareTo(FDCHelper.ZERO) == 0) {
						temp = FDCHelper.toBigDecimal(info.getOriginalAmount());
					}
					if (tempLocal.compareTo(FDCHelper.ZERO) == 0) {
						tempLocal = FDCHelper.toBigDecimal(info.getAmount());
					}
				}
				if (!info.isHasClosed()) {// 已关闭的不应该包含进去
					noPayAmt = FDCHelper.add(noPayAmt, FDCHelper.subtract(info.getOriginalAmount(), temp));// 请款单的请款金额
																											// -
																											// 该请款单对应付款单的付款金额
																											// =
																											// 申请未付金额
					noPayAmtLocal = FDCHelper.add(noPayAmtLocal, FDCHelper.subtract(info.getAmount(), tempLocal));// 请款单的请款金额
																													// -
																													// 该请款单对应付款单的付款金额
																													// =
																													// 申请未付金额
				}
			} else {// 还没有付款单
				temp = FDCHelper.toBigDecimal(info.getOriginalAmount());
				tempLocal = FDCHelper.toBigDecimal(info.getAmount());
				if (!info.isHasClosed()) {// 已关闭的不应该包含进去
					noPayAmt = FDCHelper.add(noPayAmt, FDCHelper.toBigDecimal(info.getOriginalAmount()));
					noPayAmtLocal = FDCHelper.add(noPayAmtLocal, FDCHelper.toBigDecimal(info.getAmount()));
				}
			}
			if (!isKeepAmt) {
				// 如果付款申请单已经审批，即已经有关联的付款单.那么在进行"进度款+结算款不能超过合同结算价-保修金"校验的时候
				// 进度款和结算款就应该取付款单上的金额而不是再是请款单上的 by cassiel_peng 2009-12-06
				if (info.isHasClosed()) {
					if (info.getEntrys().size() > 0) {
						PaymentBillInfo payment = info.getEntrys().get(0).getPaymentBill();
						if (payment != null) {
							noKeepAmt = FDCHelper.toBigDecimal(FDCHelper.add(noKeepAmt, _tempActuallyPayOriAmt));
							noKeepAmtLocal = FDCHelper.toBigDecimal(FDCHelper.add(noKeepAmtLocal, _tempActuallyPayLocalAmt));
						} else {
							noKeepAmt = noKeepAmt.add(FDCHelper.toBigDecimal(info.getOriginalAmount()));
							noKeepAmtLocal = noKeepAmtLocal.add(FDCHelper.toBigDecimal(info.getAmount()));
						}
					}
				} else {
					noKeepAmt = noKeepAmt.add(FDCHelper.toBigDecimal(info.getOriginalAmount()));
					noKeepAmtLocal = noKeepAmtLocal.add(FDCHelper.toBigDecimal(info.getAmount()));
				}
			}
			// totalpayAmount = totalpayAmount.add(temp);
			// totalpayAmountLocal = totalpayAmountLocal.add(tempLocal);
		}

			total = total.add(FDCHelper.toBigDecimal(billInfo.getOriginalAmount()));
//			totalLocal = totalLocal.add(FDCHelper.toBigDecimal(billInfo.getAmount()));
			
		completeTotal = completeTotal.add(FDCHelper.toBigDecimal(billInfo.getCompletePrjAmt()));
		if(contractBill!=null&&contractBill.isHasSettled()){
			completeTotal=contractBill.getSettleAmt();
		}
		projectPriceInContractTotal = projectPriceInContractTotal.add(FDCHelper.toBigDecimal(billInfo.getProjectPriceInContract()));
		// 本币：当前单据合同下的累计实付款+未付的申请单超过了付款提示比例:
		// 累计金额:1100.00 其中,实付数：300.00 申请未付数:800.00
		// 付款提示比例金额：850.00(1000*85.00%)
		/**
		 * 付款申请单有类似与上文注释所示的逻辑控制，但是在中间的计算过程中将累计金额计算错误，导致下文注释所示代码演示的申请未付数=累计金额-
		 * 累计实付数错误 现在修正计算逻辑 by cassiel_peng 2010-03-29
		 */
		// totalpayAmount =
		// totalpayAmount.add(FDCHelper.toBigDecimal(billInfo.getOriginalAmount
		// ()));
		// totalpayAmountLocal =
		// totalpayAmountLocal.add(FDCHelper.toBigDecimal(billInfo
		// .getAmount()));
		// noPayAmt = totalpayAmount.subtract(payAmt);
		// noPayAmtLocal = totalpayAmountLocal.subtract(payAmtLocal);
		noPayAmt = FDCHelper.add(noPayAmt, billInfo.getOriginalAmount());
		noPayAmtLocal = FDCHelper.add(noPayAmtLocal, billInfo.getAmount());
		totalpayAmountLocal = FDCHelper.add(payAmtLocal, noPayAmtLocal);
		/*
		 * 需求: 软件需要进行控制，当该合同付最后一笔结算款的时候，
		 * 系统应该比较该合同累计已付款金额+该笔申请款金额的总额是否超过“合同结算金额-保修金额”， 若超过了，则系统需要进行提示，但不做强制控制；
		 */
		FDCSQLBuilder builder = new FDCSQLBuilder();
		if (contractBill != null && contractBill.isHasSettled() && billInfo.getPaymentType() != null && billInfo.getPaymentType().getPayType() != null
				&& billInfo.getPaymentType().getPayType().getId().toString().equals(PaymentTypeInfo.settlementID)) {
			builder.clear();
			if (isBaseCurrency) {
				builder.appendSql("select (fsettleprice-fqualityGuarante) as amount from t_con_contractsettlementbill where fcontractbillid=");
			} else {
				builder.appendSql("select foriginalamount*(1-isnull(fqualityGuaranteRate,0)/100) as amount from t_con_contractsettlementbill where fcontractbillid=");
			}
			builder.appendParam(billInfo.getContractId());
			builder.appendSql(" and FIsFinalSettle = 1 ");
			IRowSet rowSet = builder.executeQuery();
			/**
			 * "在进行“进度款+结算款不能超过合同结算价-保修金"的逻辑判断时出错，原因：之前系统不支持合同的多笔结算 by
			 * Cassiel_peng
			 */
			if (rowSet.size() == 1) {
				rowSet.next();
				BigDecimal amount = FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"), 2);
				if (isBaseCurrency) {
					noKeepAmtLocal = FDCHelper.add(noKeepAmtLocal, billInfo.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
					if (noKeepAmtLocal.compareTo(amount) > 0) {
						MsgBox.showError(this, "本币：进度款+结算款不能超过合同结算价-保修金");
						SysUtil.abort();
					}
				} else {
					noKeepAmt = FDCHelper.add(noKeepAmt, billInfo.getOriginalAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
					if (noKeepAmt.compareTo(amount) > 0) {
						MsgBox.showError(this, "原币：进度款+结算款不能超过合同结算价-保修金");
						SysUtil.abort();
					}
				}

			}
		} else if (billInfo.getPaymentType() != null && billInfo.getPaymentType().getPayType() != null && billInfo.getPaymentType().getPayType().getId().toString().equals(PaymentTypeInfo.keepID)) {
			builder.clear();
			if (isBaseCurrency) {
				builder.appendSql("select fqualityGuarante as amount from t_con_contractsettlementbill where fcontractbillid=");
			} else {
				/********
				 * 取原币的保修金金额
				 */
				builder.appendSql("select foriginalamount*isnull(fqualityGuaranteRate,0)/100 as amount from t_con_contractsettlementbill where fcontractbillid=");
			}

			builder.appendParam(billInfo.getContractId());
			IRowSet rowSet = builder.executeQuery();
			if (rowSet.size() == 1) {
				rowSet.next();
				BigDecimal amount = FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"), 2);

				view = new EntityViewInfo();
				filter = new FilterInfo();
				filter.appendFilterItem("contractId", billInfo.getContractId());
				filter.appendFilterItem("paymentType.payType.id", PaymentTypeInfo.keepID);
				if (billInfo.getId() != null)
					filter.getFilterItems().add(new FilterItemInfo("id", billInfo.getId().toString(), CompareType.NOTEQUALS));
				if (billInfo.getId() != null)
					filter.appendFilterItem("id", billInfo.getId().toString());
				view.setFilter(filter);
				view.getSelector().add("amount");
				PayRequestBillCollection coll = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);
				BigDecimal keepAmt = FDCHelper.ZERO;
				BigDecimal keepAmtLocal = FDCHelper.ZERO;
				for (Iterator iter = coll.iterator(); iter.hasNext();) {
					PayRequestBillInfo keepInfo = (PayRequestBillInfo) iter.next();
					if (keepInfo.getOriginalAmount() != null) {
						keepAmt = keepAmt.add(keepInfo.getOriginalAmount());
					}
					if (keepInfo.getAmount() != null) {
						keepAmtLocal = keepAmtLocal.add(keepInfo.getAmount());
					}
				}

				if (isBaseCurrency) {
					keepAmtLocal = FDCHelper.add(keepAmtLocal, billInfo.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
					if ((keepAmtLocal.compareTo(amount) > 0)) {
						MsgBox.showError(this, "本币：合同累计已付保修款金额超过合同结算保修金额!”");
						SysUtil.abort();
					}
				} else {
					keepAmt = FDCHelper.add(keepAmt, billInfo.getOriginalAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
					if ((keepAmt.compareTo(amount) > 0)) {
						MsgBox.showError(this, "原币：合同累计已付保修款金额超过合同结算保修金额!”");
						SysUtil.abort();
					}
				}
			}
		}

		/**
		 * 付款提示比例算法：累计实付款+未付的申请金额 大于 合同金额*付款提示比例 时提示。 现修改为：累计实付款+未付的申请金额 大于
		 * 合同最新造价*付款提示比例 时提示 by cassiel_peng 2010-03-17 因为之前是合同金额*付款提示比例
		 * 所以是有本币和原币的区别的，但是现改为合同最新造价*付款提示比例 由于合同最新造价只有本币的概念，
		 * 所以下边的原币提示比例的校验已经没有实际意义了 by cassiel_peng 2010-03-17
		 */
		if (contractBill != null && !contractBill.isHasSettled()) {// 未最终结算
			builder.clear();
			if (isBaseCurrency) {
				// builder
				// .appendSql(
				// "select (famount * fpayPercForWarn)/100 as fsumamt,famount as amount,fpayPercForWarn from t_con_contractbill where fid="
				// );
				builder.appendSql("select fpayPercForWarn from t_con_contractbill where fid=");

				builder.appendParam(billInfo.getContractId());
				final IRowSet rowSet = builder.executeQuery();
				BigDecimal payRate = FDCHelper.ZERO;
				// BigDecimal contractAmt = FDCHelper.ZERO;
				BigDecimal payPercForWarn = FDCHelper.ZERO;
				BigDecimal conLastestPrice = FDCHelper.ZERO;
				Map map = new HashMap();
				if (rowSet.size() == 1) {
					rowSet.next();
					// payRate =
					// FDCHelper.toBigDecimal(rowSet.getBigDecimal("fsumamt"),
					// 2);
					// contractAmt = FDCHelper.toBigDecimal(
					// rowSet.getBigDecimal("amount"), 2);
					payPercForWarn = FDCHelper.toBigDecimal(rowSet.getBigDecimal("fpayPercForWarn"), 2);
				}
				// 合同最新造价
				map = FDCUtils.getLastAmt_Batch(null, new String[] { billInfo.getContractId() });
				if (map != null && map.size() == 1) {
					conLastestPrice = (BigDecimal) map.get(billInfo.getContractId());
				}
				payRate = FDCHelper.divide(FDCHelper.multiply(conLastestPrice, payPercForWarn), FDCHelper.ONE_HUNDRED);
				totalpayAmountLocal = FDCHelper.toBigDecimal(totalpayAmountLocal, 2);
				// totalpayAmount = FDCHelper.toBigDecimal(totalpayAmount, 2);

				if (totalpayAmountLocal.compareTo(payRate) > 0) {
					String str = "本币：当前单据合同下的累计实付款+未付的申请单超过了付款提示比例:";
					str = str + "\n累计金额:" + totalpayAmountLocal + " 其中,实付数：" + FDCHelper.toBigDecimal(payAmtLocal, 2) + "  申请未付数:" + FDCHelper.toBigDecimal(noPayAmtLocal, 2);
					str = str + "\n付款提示比例金额：" + payRate + "(" + conLastestPrice + "*" + payPercForWarn + "%)";
					if ("0".equals(allPaidMoreThanConPrice())) {// 严格控制
						MsgBox.showDetailAndOK(this, "超过付款提示比例,请查看详细信息", str, 2);
						SysUtil.abort();
					} else if ("1".equals(allPaidMoreThanConPrice())) {// 提示控制
						MsgBox.showDetailAndOK(this, "超过付款提示比例,请查看详细信息", str, 1);
					}
				}
			} else {
				// builder
				// .appendSql(
				// "select (foriginalamount * fpayPercForWarn)/100 as fsumamt,foriginalamount as amount ,fpayPercForWarn from t_con_contractbill where fid="
				// );
			}

			// if (isBaseCurrency) {
			// } else {
			// if (totalpayAmount.compareTo(payRate) > 0) {
			// String str = "原币：当前单据合同下的累计实付款+未付的申请单超过了付款提示比例:";
			// str = str + "\n累计金额:" + totalpayAmount + " 其中,实付数：" +
			// FDCHelper.toBigDecimal(payAmt, 2) + "  申请未付数:" +
			// FDCHelper.toBigDecimal(noPayAmt, 2);
			// str = str + "\n付款提示比例金额：" + payRate + "(" + contractAmt + "*" +
			// payPercForWarn + "%)";
			// MsgBox.showDetailAndOK(this, "超过付款提示比例,请查看详细信息", str, 1);
			// }
			// }
		}
		/******
		 * 判断最新造价
		 */
		if (isBaseCurrency) {
//			if (totalLocal.compareTo(latestPrice) > 0) {
//				// 严格控制不允许提交
//				if (isControlCost) {
//					MsgBox.showError(this, "合同下付款申请单的累计金额(本币)大于合同最新造价(本币)！");
//					SysUtil.abort();
//				} else {
//					int result = MsgBox.showConfirm2(this, "合同下付款申请单的累计金额(本币)大于合同最新造价(本币).是否提交?");
//					if (result != MsgBox.OK) {
//						SysUtil.abort();
//					}
//				}
//			}
		} else {
			/**********
			 * 需要使用原币的最新造价比较
			 * 
			 */
			total = FDCHelper.toBigDecimal(total, 2);
			lastestPriceOriginal = FDCHelper.toBigDecimal(lastestPriceOriginal, 2);
			if (total.compareTo(lastestPriceOriginal) > 0) {
				// 严格控制不允许提交
				if (isControlCost) {
					MsgBox.showError(this, "合同下付款申请单的累计金额(原币)大于合同最新造价(原币)！");
					SysUtil.abort();
				} else {
					int result = MsgBox.showConfirm2(this, "合同下付款申请单的累计金额(原币)大于合同最新造价(原币).是否提交?");
					if (result != MsgBox.OK) {
						SysUtil.abort();
					}
				}
			}
		}
		BigDecimal totalReqAmt = payAmtLocal.add(billInfo.getAmount());
		if (totalReqAmt.compareTo(latestPrice) == 1) {
			if(isControlCost){
				MsgBox.showWarning(this, "\"本次申请+累计实付\" 不能大于合同最新造价!");
				SysUtil.abort();
			}
		}

		if (isSimpleFinancial) {
			if (billInfo.getPaymentType() == null || billInfo.getPaymentType().getPayType() == null) {
				MsgBox.showError(this, "付款类型有误");
				SysUtil.abort();
			}
			if (FDCHelper.toBigDecimal(projectPriceInContractTotal, 2).compareTo(latestPrice) > 0) {
				String msg = "合同下付款申请单的累计实付金额大于合同最新造价.是否提交?";
				int result = MsgBox.showConfirm2(this, msg);
				if (result != MsgBox.OK) {
					SysUtil.abort();
				}
			}
			if (!isAutoComplete && contractBill != null && contractBill.isHasSettled()) {
				if (FDCHelper.toBigDecimal(completeTotal, 2).compareTo(latestPrice) > 0) {
					String msg = "合同下付款申请单的累计已完工工程量不能大于合同最新造价";
					MsgBox.showWarning(this, msg);
					SysUtil.abort();
				}
			}
			return;
			// 简单模式到此结束
		}
		if (FDCHelper.toBigDecimal(completeTotal, 2).compareTo(latestPrice) > 0) {
			if (billInfo.getPaymentType() == null || billInfo.getPaymentType().getPayType() == null) {
				MsgBox.showError(this, "付款类型有误");
				SysUtil.abort();
			}
			if (billInfo.getPaymentType().getPayType().getId().toString().equals(PaymentTypeInfo.progressID)) {
				String msg = "合同下付款申请单的累计已完工工程量大于合同最新造价.是否提交?";
				int result = MsgBox.showConfirm2(this, msg);
				if (result != MsgBox.OK) {
					SysUtil.abort();
				}
			}
		}
		if (isBaseCurrency) {
			BigDecimal totalLocal = FDCHelper.add(ContractClientUtils.getReqAmt(billInfo.getContractId()), billInfo.getAmount());
			//避免在修改时重复记录，需要减去这张单据之前保存在数据库里的值。
			if(STATUS_EDIT.equals(getOprtState())){
				EntityViewInfo _view = new EntityViewInfo();
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("amount");
				FilterInfo _filter = new FilterInfo();
				_filter.getFilterItems().add(new FilterItemInfo("fdcPayReqID",billInfo.getId().toString()));
				_view.setFilter(_filter);
				PayRequestBillInfo _tempReqInfo = null;
				PaymentBillInfo _tempPayInfo = null;
				if(billInfo.isHasClosed()){
					if(PaymentBillFactory.getRemoteInstance().getPaymentBillCollection(_view).size()>0){
						_tempPayInfo = PaymentBillFactory.getRemoteInstance().getPaymentBillCollection(_view).get(0);
						totalLocal = FDCHelper.subtract(totalLocal, _tempPayInfo.getAmount());
					}
				}else{
					_tempReqInfo = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(billInfo.getId()));
					totalLocal = FDCHelper.subtract(totalLocal, _tempReqInfo.getAmount());
				}
			}
			
			if (totalLocal.compareTo(latestPrice) > 0) {
				// 严格控制不允许提交
				if (isControlCost) {
					MsgBox.showError(this, "合同下付款申请单的累计金额(本币)大于合同最新造价(本币)！");
					SysUtil.abort();
				} else {
					int result = MsgBox.showConfirm2(this, "合同下付款申请单的累计金额(本币)大于合同最新造价(本币).是否提交?");
					if (result != MsgBox.OK) {
						SysUtil.abort();
					}
				}
			}
		} 
		
	}
	
	private String allPaidMoreThanConPrice() {
		IParamControl ipctr = null;
		try {
			ipctr = ParamControlFactory.getRemoteInstance();
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
		String retValue = "";
		try {
			retValue = ipctr.getParamValue(null, "FDC444_ALLPAIDMORETHANCONPRICE");
		} catch (EASBizException e) {
			handUIExceptionAndAbort(e);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
		return retValue;
	}

	/**
	 * 覆盖抽象类处理编码规则的行为,统一在FDCBillEditUI.handCodingRule方法中处理
	 */
	protected void setAutoNumberByOrg(String orgType) {

	}

	private void viewContract(String id) throws UIException {
		if (id == null)
			return;
		String editUIName = null;
		if (PayReqUtils.isContractBill(id)) {
			editUIName = com.kingdee.eas.fdc.contract.client.ContractBillEditUI.class
					.getName();

		} else if (PayReqUtils.isConWithoutTxt(id)) {
			editUIName = com.kingdee.eas.fdc.contract.client.ContractWithoutTextEditUI.class
					.getName();

		}

		if (editUIName == null)
			return;
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, id);
		uiContext.put("source", "listBase"); // 用于与工作流过来的区分
		// 创建UI对象并显示
		IUIWindow windows = this.getUIWindow();
		String type = UIFactoryName.NEWTAB;
		if (windows instanceof UIModelDialog) {
			type = UIFactoryName.MODEL;
		}
		IUIWindow contractUiWindow = UIFactory.createUIFactory(type).create(
				editUIName, uiContext, null, "FINDVIEW");
		if (contractUiWindow != null) {
			contractUiWindow.show();
		}
	}

	public void actionViewContract_actionPerformed(ActionEvent e)
			throws Exception {
		// super.actionViewContract_actionPerformed(e);
		if (editData == null || editData.getContractId() == null) {
			return;
		}
		// tableHelper.debugCellExp();
		viewContract(editData.getContractId());
	}

	//查看预算余额
    public void actionViewMbgBalance_actionPerformed(ActionEvent e) throws Exception
    {
    	
    	PayRequestBillInfo payReqInfo=this.editData;
		IBgControlFacade iCtrl = BgControlFacadeFactory.getRemoteInstance();
		if(fdcBudgetParam.isAcctCtrl()){
			if(this.editData.getId()==null){
				return;
			}
			SelectorItemCollection selector=new SelectorItemCollection();
			selector.add("id");
			selector.add("payDate");
			selector.add("contractId");
			selector.add("contractNo");
			selector.add("contractName");
			selector.add("state");
			selector.add("amount");
			selector.add("acctPays.*");
			selector.add("acctPays.payRequestBill.id");
			selector.add("acctPays.costAccount.id");
			selector.add("acctPays.costAccount.codingNumber");
//			selector.add("acctPays.costAccount.longNumber");
//			selector.add("acctPays.costAccount.displayName");
			selector.add("acctPays.costAccount.name");
			selector.add("acctPays.period.*");
			selector.add("orgUnit.id");
			selector.add("orgUnit.number");
			selector.add("orgUnit.name");
			selector.add("currency.id");
			selector.add("currency.number");
			
			payReqInfo=PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(this.editData.getId().toString()),selector);
			
			if(payReqInfo.getAcctPays()==null||payReqInfo.getAcctPays().size()==0){
				return;
			}
//			for(Iterator iter=payReqInfo.getAcctPays().iterator();iter.hasNext();){
//				PayRequestAcctPayInfo info=(PayRequestAcctPayInfo)iter.next();
//				//处理longnumber以便与预算匹配
//				String lgNumber = info.getCostAccount().getLongNumber();
//				if(lgNumber!=null){
//					lgNumber=lgNumber.replace('!', '.');
//				}
//				info.getCostAccount().setLongNumber(lgNumber);
//				
//			}

		}
		BgCtrlResultCollection coll = iCtrl.getBudget(
				FDCConstants.PayRequestBill, null, payReqInfo);

		UIContext uiContext = new UIContext(this);
		uiContext.put(BgHelper.BGBALANCE, coll);

		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(BgBalanceViewUI.class.getName(), uiContext, null,
						STATUS_VIEW);
		uiWindow.show();
		payReqInfo=null;
	}

	protected void initWorkButton() {
		super.initWorkButton();
		
		menuItemViewPayDetail.setText(menuItemViewPayDetail.getText() + "(D)");
		menuItemViewPayDetail.setMnemonic('D');
		
		actionViewPayDetail.setEnabled(true);
		actionViewPayDetail.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_sequencecheck"));
		menuItemViewPayDetail.setText(menuItemViewPayDetail.getText() + "(D)");
		menuItemViewPayDetail.setMnemonic('D');
		
		actionViewContract.setEnabled(true);
		actionViewContract.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_sequencecheck"));
		actionViewMbgBalance.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_sequencecheck"));
		actionAssociateUnSettled.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_settingrelating"));
		actionAssociateAcctPay.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_seerelating"));
		menuItemViewContract.setText(menuItemViewContract.getText() + "(V)");
		menuItemViewContract.setMnemonic('V');
		menuItemAdjustDeduct.setText(menuItemAdjustDeduct.getText() + "(T)");
		menuItemAdjustDeduct.setMnemonic('T');

		menuItemClose.setText(menuItemClose.getText() + "(C)");
		menuItemUnClose.setText(menuItemUnClose.getText() + "(F)");
		menuItemClose.setMnemonic('C');
		menuItemUnClose.setMnemonic('F');

		actionTaoPrint.setVisible(false);
		actionTaoPrint.setEnabled(false);

		actionPaymentPlan.setVisible(false);
		actionPaymentPlan.setVisible(false);
		if ((isSeparate && contractBill!=null && contractBill.isIsCoseSplit())) {
			contcompletePrjAmt.setVisible(false);
			contpaymentProportion.setVisible(false);
			contAllCompletePrjAmt.setVisible(false);
			contAllPaymentProportion.setVisible(false);
		}
	}

	public void kdtEntrys_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
		if (e.getType() == 0) {
			return;
		}
	}

	protected void pkpayDate_dataChanged(DataChangeEvent e) throws Exception {
		if (this.getOprtState().equals(OprtState.VIEW)
				|| (editData != null && (editData.getState() == FDCBillStateEnum.AUDITTED || editData
						.getState() == FDCBillStateEnum.AUDITTING))) {
			return;
		}
		Object objNew = e.getNewValue();
		Object objOld = e.getOldValue();
		BigDecimal planAmt = FDCHelper.ZERO;
		if (objNew == null) {
			// PayRequestBillContants.CURPLANNEDPAYMENT
			tableHelper.setCellValue(PayRequestBillContants.CURPLANNEDPAYMENT,
					null);
		} else {
			if (objNew.equals(objOld)) {
				planAmt = FDCHelper.toBigDecimal(editData
						.getCurPlannedPayment());
			} else {
				/*
				 * Date dateNew = (Date) objNew; Date dateOld = objOld != null ?
				 * (Date) objOld : null; if (dateOld != null &&
				 * dateNew.getYear() == dateOld.getYear() && dateNew.getMonth() ==
				 * dateOld.getMonth()) {
				 * planAmt=FDCHelper.toBigDecimal(editData.getCurPlannedPayment()); }
				 * else {}
				 */
				Date dateNew = (Date) objNew;
				// 相同时期仍然从数据库内取以同步最新的付款计划
				String id = editData.getContractId();
				if (id == null) {
					return;
				}
				FDCSQLBuilder build = new FDCSQLBuilder();
				build
						.appendSql("select FPayAmount from T_FNC_ContractPayPlan where fcontractId=");
				build.appendParam(id);
				build.appendSql(" and fpaydate>=");
				Calendar cal = Calendar.getInstance();
				cal.setTime(dateNew);
				Date date = (Date) dateNew.clone();
				
//				date.setDate(cal.getMinimum(Calendar.DAY_OF_MONTH));
				date=FDCDateHelper.getFirstDayOfMonth(date);
				build.appendParam(new Timestamp(date.getTime()));
				build.appendSql(" and fpaydate<=");
				
//				date = (Date) date.clone();
//				date.setDate(cal.getMaximum(Calendar.DAY_OF_MONTH));
				date = (Date) dateNew.clone();
				date=FDCDateHelper.getLastDayOfMonth(date);
				build.appendParam(new Timestamp(date.getTime()));
				IRowSet rowSet = build.executeQuery();
				if (rowSet.size() > 0) {
					rowSet.next();
					planAmt = rowSet.getBigDecimal("FPayAmount");
					tableHelper.setCellValue(
							PayRequestBillContants.CURPLANNEDPAYMENT, planAmt);
				} else {
					// 付款计划不存在时，本期欠付款为空
					tableHelper.setCellValue(
							PayRequestBillContants.CURPLANNEDPAYMENT, null);
					tableHelper.setCellValue(PayRequestBillContants.CURBACKPAY,
							null);
					return;
				}

			}
		}
		/*
		 * 本期欠付款 :计算公式为“该合同本期付款计划-该合同的本期累计申请（不包含本次申请）-该合同本期累计实付”； by sxhong
		 * 2007/09/28
		 */
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		if (editData != null) {
			filter.appendFilterItem("contractId", editData.getContractId());
		}
		// 用时间过滤出所有之前的单
		filter.getFilterItems().add(
				new FilterItemInfo("createTime", editData.getCreateTime(),
						CompareType.LESS));
		view.getSelector().add("amount");
		view.getSelector().add("entrys.paymentBill.billStatus");
		view.getSelector().add("entrys.paymentBill.amount");
		//本期欠付款应该用付款单的本币计算，而不是原币
		view.getSelector().add("entrys.paymentBill.localAmt");
		PayRequestBillCollection c = PayRequestBillFactory.getRemoteInstance()
				.getPayRequestBillCollection(view);
		BigDecimal totalpayAmount = FDCHelper.ZERO;// 累计实付款+未付的申请单
		for (int i = 0; i < c.size(); i++) {
			final PayRequestBillInfo info = c.get(i);
			if (info.getId().equals(editData.getId())) {
				// 排除本单据
				continue;
			}
			BigDecimal temp = FDCHelper.ZERO;
			if (info.getEntrys().size() > 0) {
				for (int j = 0; j < info.getEntrys().size(); j++) {
					PaymentBillInfo payment = info.getEntrys().get(0)
							.getPaymentBill();
					if (payment != null
							&& payment.getBillStatus() == BillStatusEnum.PAYED) {
						temp = temp.add(FDCHelper.toBigDecimal(payment
								.getLocalAmt()));
					}
				}
				if (temp.compareTo(FDCHelper.ZERO) == 0) {
					temp = FDCHelper.toBigDecimal(info.getAmount());
				}

			} else {
				temp = FDCHelper.toBigDecimal(info.getAmount());
			}
			totalpayAmount = totalpayAmount.add(temp);
		}
		BigDecimal mustPayAmt = planAmt.subtract(totalpayAmount);
		tableHelper.setCellValue(PayRequestBillContants.CURBACKPAY, mustPayAmt);
	}

	protected void prmtsettlementType_dataChanged(DataChangeEvent e)
			throws Exception {
		super.prmtsettlementType_dataChanged(e);
		Object objNew = e.getNewValue();
		if (objNew == null) {
			txtrecBank.setRequired(isBankRequire);
			txtrecAccount.setRequired(isBankRequire);
			txtrecAccount.repaint();
			txtrecBank.repaint();
			return;
		}
		if (objNew instanceof SettlementTypeInfo
				&& ((SettlementTypeInfo) objNew).getId().toString().equals(
						"e09a62cd-00fd-1000-e000-0b32c0a8100dE96B2B8E")) {
			// 结算方式为付款则银行及账号为非必录
			txtrecBank.setRequired(false);
			txtrecAccount.setRequired(false);
		} else {
			txtrecBank.setRequired(isBankRequire);
			txtrecAccount.setRequired(isBankRequire);
		}
		txtrecAccount.repaint();
		txtrecBank.repaint();
	}

	// 检查拆分状态
	private void checkContractSplitState() {
//		if(!isIncorporation){
//			return ;
//		}
		//付款单提交时，是否检查合同未完全拆分
		if(!checkAllSplit){
			return;
		}
		String contractBillId = (String) getUIContext().get("contractBillId");
		if (contractBillId != null) {
			if (!ContractClientUtils.getContractSplitState(contractBillId)) {
				// 对应的合同还未进行拆分，不能进行此操作！
				MsgBox.showWarning(this, FDCSplitClientHelper
						.getRes("conNotSplited"));
				SysUtil.abort();
			}
		}
	}
	
    // 保存时，控制预算余额
    private void checkMbgCtrlBalance() {
        try {
            if (!isMbgCtrl ) {
                return;
            }

            StringBuffer buffer = new StringBuffer("");
            IBgControlFacade iCtrl = BgControlFacadeFactory.getRemoteInstance();
                BgCtrlResultCollection bgCtrlResultCollection = iCtrl.getBudget(FDCConstants.PayRequestBill, null,editData);

                if (bgCtrlResultCollection != null) {
                    for (int j = 0, count = bgCtrlResultCollection.size(); j < count; j++) {
                        BgCtrlResultInfo bgCtrlResultInfo = bgCtrlResultCollection.get(j);

                        BigDecimal balance = bgCtrlResultInfo.getBalance();
                        if (balance != null && balance.compareTo(bgCtrlResultInfo.getReqAmount())< 0) {
                                buffer.append(bgCtrlResultInfo.getItemName()+"("+bgCtrlResultInfo.getOrgUnitName()+")").append( 
                                		EASResource.getString(FDCConstants.VoucherResource,"BalanceNotEnagh")+"\r\n");
                        }
                    }
                }


            if (buffer.length() > 0) {
                int result = MsgBox.showConfirm2(this,buffer.toString() + "\r\n" +EASResource.getString(FDCConstants.VoucherResource, "isGoOn"));
                if (result == MsgBox.CANCEL) {
                	 SysUtil.abort();
                }
            }
        } catch (Exception e) {
        	handUIExceptionAndAbort(e);
        }
    }

	/**
	 * 保存的时候进行预算检查
	 * @throws Exception 
	 */
	private void checkFdcBudget() throws Exception{
//		FDCBudgetPeriodInfo period=getFDCBudgetPeriod();
		try {
			Map retMap = FDCBudgetAcctFacadeFactory.getRemoteInstance().invokeBudgetCtrl(this.editData, FDCBudgetConstants.STATE_SUBMIT);
			//防止某些用户在未提交时修改参数，再取
			fetchInitParam();
			PayReqUtils.handleBudgetCtrl(this, retMap, fdcBudgetParam);
		} catch (EASBizException e) {
			handUIExceptionAndAbort(e);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
	}
	private FDCBudgetPeriodInfo getFDCBudgetPeriod() {
		FDCBudgetPeriodInfo period=null;
		if(fdcBudgetParam.isAcctCtrl()){
			//从数据库取
			period=(FDCBudgetPeriodInfo)this.editData.get("fdcPeriod");
			if(period==null&&this.editData.getId()!=null){
				FDCSQLBuilder builder=new FDCSQLBuilder();
				builder.appendSql("select top 1 period.fid id ,period.fyear year,period.fmonth month from T_FNC_PayRequestAcctPay acctPay ");
				builder.appendSql("inner join T_FNC_FDCBudgetPeriod period on period.fid=acctPay.fperiodid ");
				builder.appendSql("where FPayRequestBillId=?");
				builder.addParam(this.editData.getId().toString());
				try {
					IRowSet rowSet = builder.executeQuery();
					if (rowSet.next()) {
						int year=rowSet.getInt("year");
						int month=rowSet.getInt("month");
						String id=rowSet.getString("id");
						period=FDCBudgetPeriodInfo.getPeriod(year, month, false);
						period.setId(BOSUuid.read(id));
					}
				}catch(Exception e){
					handUIExceptionAndAbort(e);
				}
			}
		}
		if(period==null){
			period=FDCBudgetPeriodInfo.getPeriod(this.editData.getPayDate(),false);
		}
		
		return period;
	}

	protected void kdtEntrys_activeCellChanged(KDTActiveCellEvent e)
			throws Exception {
		if ((e.getRowIndex() == rowIndex)
				&& (e.getColumnIndex() == columnIndex)) {
			// if(e.getSource().g)
		}
	}

	public void setAmountChange(BigDecimal originalAmount){
		BigDecimal amount = FDCHelper.multiply(originalAmount,txtexchangeRate.getBigDecimalValue());
		getDetailTable().getCell(rowIndex,columnIndex+1).setValue(FDCHelper.toBigDecimal(amount, 2));
		
	}
	public void setAdvanceChange(BigDecimal originalAmount){
		BigDecimal amount = FDCHelper.multiply(originalAmount,txtexchangeRate.getBigDecimalValue());
		getDetailTable().getCell(rowIndex+1,columnIndex+1).setValue(FDCHelper.toBigDecimal(amount, 2));
		
	}
	
	public void setCompletePrjAmt(){
		BigDecimal amount = null;
		if(getDetailTable().getCell(rowIndex,columnIndex+1).getValue()!=null){
			amount = FDCHelper.toBigDecimal(getDetailTable().getCell(rowIndex,columnIndex+1).getValue());
		}
		BigDecimal paymentProp = txtpaymentProportion.getBigDecimalValue();
		BigDecimal completePrj = txtcompletePrjAmt.getBigDecimalValue();
		if (paymentProp == null)
			paymentProp = FDCHelper.ZERO;
		if (completePrj == null)
			completePrj = FDCHelper.ZERO;
		if (paymentProp.compareTo(FDCHelper.ZERO) == 0) {
			if (completePrj.compareTo(FDCHelper.ZERO) == 0) {
				return;
			} else {
				txtpaymentProportion.setRequired(false);

				txtpaymentProportion.setRequired(true && !isAutoComplete);

				String settlementID = PaymentTypeInfo.settlementID;
				PaymentTypeInfo type = (PaymentTypeInfo) prmtPayment.getValue();
				if (type != null
						&& type.getPayType().getId().toString().equals(
								settlementID)) {
					txtpaymentProportion.setValue(FDCHelper.ZERO);
				} else {
					txtpaymentProportion.setValue(amount.setScale(4,BigDecimal.ROUND_HALF_UP).divide(
							completePrj, BigDecimal.ROUND_HALF_UP).multiply(
							FDCHelper.ONE_HUNDRED));
				}
			}
		} else {
			txtcompletePrjAmt.setValue(amount.setScale(4,BigDecimal.ROUND_HALF_UP).divide(paymentProp,
					BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED));

			String settlementID = PaymentTypeInfo.settlementID;
			PaymentTypeInfo type = (PaymentTypeInfo) prmtPayment.getValue();
			if (type != null
					&& type.getPayType().getId().toString()
							.equals(settlementID)) {
				txtpaymentProportion.setValue(FDCHelper.ZERO);
			}
		}
	}
	public void setPmtAmoutChange(BigDecimal originalAmount){
		
		BigDecimal amount = FDCHelper.multiply(originalAmount,txtexchangeRate.getBigDecimalValue());
		
		((ICell) bindCellMap
				.get(PayRequestBillContants.PAYPARTAMATLAMT)).setValue(amount);
		
	}

	protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
		boolean isZeroPay = false;
		if ((e.getRowIndex() == rowIndex) && (e.getColIndex() == columnIndex)) {
			if (e.getValue() != null && e.getValue().toString().trim().length() > 0 ) {
				BigDecimal originalAmount = FDCHelper.toBigDecimal(e.getValue());
				if(FDCHelper.ZERO.compareTo(originalAmount)==0){
					isZeroPay = true;
				}
				setAmountChange(originalAmount);
			}
		}
		else if ((e.getRowIndex() == ((ICell) bindCellMap
				.get(PayRequestBillContants.PAYPARTAMATLAMTORI)).getRowIndex()+this.deductTypeCollection.size()+1) && (e.getColIndex() == 4)){
			//录入甲供材
			BigDecimal originalAmount = FDCHelper.toBigDecimal(e.getValue());
			setPmtAmoutChange(originalAmount);
		}
		else if ((e.getRowIndex() == rowIndex+1) && (e.getColIndex() == columnIndex)) {
			if (e.getValue() != null && e.getValue().toString().trim().length() > 0 ) {
				BigDecimal originalAmount = FDCHelper.toBigDecimal(e.getValue());
				setAdvanceChange(originalAmount);
			}
		}
		calAllCompletePrjAmt();
		if (isFirstLoad || (!isAutoComplete && !txtpaymentProportion.isRequired()))
			return;
		//如果是0付款，则不填入已完工数据
		if(!isZeroPay)
			setCompletePrjAmt();
	}
	
	/**
	 * 
	 * 描述：简单财务成本一体化处理扣款、违约、奖励，则扣款、违约和奖励不影响财务成本的金额，以合同内工程款计入财务成本。
	 * 简单财务成本一体化不处理扣款、违约、奖励，则以实际付款数计入财务成本。
	 * 
	 */
	private void setCostAmount() {
		BigDecimal amount = FDCHelper
				.toBigDecimal(((ICell) bindCellMap
						.get(PayRequestBillContants.PROJECTPRICEINCONTRACT))
						.getValue());// txtBcAmount.getBigDecimalValue();
		BigDecimal totalAmt = FDCHelper.toBigDecimal(((ICell) bindCellMap
				.get(PayRequestBillContants.CURPAIDLOCAL)).getValue());
		BigDecimal completeAmt = FDCHelper.toBigDecimal(txtcompletePrjAmt
				.getBigDecimalValue());

//		if(this.isSimpleFinancial){
//			if(this.isSimpleFinancialExtend){
//				editData.setCompletePrjAmt(amount);
//				txtcompletePrjAmt.setValue(amount);
//			}
//			else{
//				editData.setCompletePrjAmt(totalAmt);
//				txtcompletePrjAmt.setValue(totalAmt);
//			}
//		}else{
			if(contractBill.isHasSettled()){
				editData.setPaymentProportion(FDCHelper.ONE_HUNDRED);
				editData.setCompletePrjAmt(contractBill.getSettleAmt());
				txtpaymentProportion.setValue(FDCHelper.ONE_HUNDRED);
				txtcompletePrjAmt.setValue(contractBill.getSettleAmt());
			}else{
				editData.setCompletePrjAmt(completeAmt);
				if(isAutoComplete){
					txtcompletePrjAmt.setValue(amount);
					editData.setCompletePrjAmt(completeAmt);
				}
			}
//		}
	}

	private void prmtPayment_dataChanged(DataChangeEvent eventObj) {
	
//		if (prmtPayment.getUserObject() != null
//				&& prmtPayment.getUserObject().equals("noExec")) {
//			return;
//		}
		Object obj = eventObj.getNewValue();
		if (obj instanceof PaymentTypeInfo){
		if (isRealizedZeroCtrl) {
				PaymentTypeInfo type = (PaymentTypeInfo) obj;
				if (FDCHelper.isNullZero(txtTotalSettlePrice
						.getBigDecimalValue())
						&& type.getName() != null
						&& !type.getName().equals("预付款")) {
					EventListener[] listeners = prmtPayment
							.getListeners(DataChangeListener.class);
					for (int i = 0; i < listeners.length; i++) {
						prmtPayment
								.removeDataChangeListener((DataChangeListener) listeners[i]);
					}
					prmtPayment.setData(eventObj.getOldValue());
					MsgBox.showError(prmtPayment, "已实现产值为0只允许选择\"预付款\"！");
					for (int i = 0; i < listeners.length; i++) {
						prmtPayment
								.addDataChangeListener((DataChangeListener) listeners[i]);
					}
					SysUtil.abort();
				}
			}
		}
		
		if (obj instanceof PaymentTypeInfo) {
			try {
				String settlementID = PaymentTypeInfo.settlementID;// "Ga7RLQETEADgAAC/wKgOlOwp3Sw=";//
																	// 结算款
				String progressID = PaymentTypeInfo.progressID;// "Ga7RLQETEADgAAC6wKgOlOwp3Sw=";//
																// 进度款
				String keepID = PaymentTypeInfo.keepID;// "Ga7RLQETEADgAADDwKgOlOwp3Sw=";//
														// 保修款

				PaymentTypeInfo type = (PaymentTypeInfo) obj;
				
				// 保修款修改为只要结算就可以付

				/*
				 * filter.appendFilterItem("paymentType.payType.id",
				 * settlementID); filter.appendFilterItem("contractId",
				 * editData.getContractId()); if (type.getPayType() == null) {
				 * return; } if
				 * (type.getPayType().getId().toString().equals(keepID)) { if
				 * (!PayRequestBillFactory.getRemoteInstance() .exists(filter)) {
				 * EventListener[] listeners =
				 * prmtPayment.getListeners(DataChangeListener.class); for(int
				 * i=0;i<listeners.length;i++){
				 * prmtPayment.removeDataChangeListener((DataChangeListener)listeners[i]); }
				 * prmtPayment.setData(eventObj.getOldValue());
				 * MsgBox.showError(prmtPayment,
				 * "付过结算款后才能付保修款,即存在付款类型的类型为“结算款”的付款申请单时才能选择“保修款”类型付款类型");
				 * for(int i=0;i<listeners.length;i++){
				 * prmtPayment.addDataChangeListener((DataChangeListener)listeners[i]); }
				 * SysUtil.abort(); } }
				 */
				FilterInfo filter = new FilterInfo();
				filter.appendFilterItem("id", editData.getContractId());
				filter.appendFilterItem("hasSettled", Boolean.TRUE);
				if (type.getPayType().getId().toString().equals(keepID)) {
					if (!ContractBillFactory.getRemoteInstance().exists(filter)) {
						EventListener[] listeners = prmtPayment
								.getListeners(DataChangeListener.class);
						for (int i = 0; i < listeners.length; i++) {
							prmtPayment
									.removeDataChangeListener((DataChangeListener) listeners[i]);
						}
						prmtPayment.setData(eventObj.getOldValue());
						MsgBox.showError(prmtPayment, "合同结算后才能付保修款");
						for (int i = 0; i < listeners.length; i++) {
							prmtPayment
									.addDataChangeListener((DataChangeListener) listeners[i]);
						}
						SysUtil.abort();
					}
				}

				filter = new FilterInfo();
				filter.appendFilterItem("paymentType.payType.id", settlementID);
				filter.appendFilterItem("contractId", editData.getContractId());
				if (type.getPayType().getId().toString().equals(progressID)) {
					if (PayRequestBillFactory.getRemoteInstance()
							.exists(filter)) {
						EventListener[] listeners = prmtPayment
								.getListeners(DataChangeListener.class);
						for (int i = 0; i < listeners.length; i++) {
							prmtPayment
									.removeDataChangeListener((DataChangeListener) listeners[i]);
						}
						prmtPayment.setData(eventObj.getOldValue());
						MsgBox
								.showError(prmtPayment,
										"付完结算款后不能付进度款,即存在付款类型的类型为“结算款”的付款申请单时不能选择“进度款”类型付款类型");
						for (int i = 0; i < listeners.length; i++) {
							prmtPayment
									.addDataChangeListener((DataChangeListener) listeners[i]);
						}
						SysUtil.abort();
					} else {
						FilterInfo myfilter = new FilterInfo();
						myfilter.appendFilterItem("id", editData
								.getContractId());
						myfilter.appendFilterItem("hasSettled", Boolean.TRUE);
						if (ContractBillFactory.getRemoteInstance().exists(
								myfilter)) {
							EventListener[] listeners = prmtPayment
									.getListeners(DataChangeListener.class);
							for (int i = 0; i < listeners.length; i++) {
								prmtPayment
										.removeDataChangeListener((DataChangeListener) listeners[i]);
							}
							prmtPayment.setData(eventObj.getOldValue());
							MsgBox.showError(prmtPayment, "合同结算之后不能付进度款！");
							for (int i = 0; i < listeners.length; i++) {
								prmtPayment
										.addDataChangeListener((DataChangeListener) listeners[i]);
							}
							SysUtil.abort();
						}
					}
				}

				if (type.getPayType().getId().toString().equals(settlementID)) {
					FilterInfo myfilter = new FilterInfo();
					myfilter.appendFilterItem("id", editData.getContractId());
					myfilter.appendFilterItem("hasSettled", Boolean.TRUE);
					if (!ContractBillFactory.getRemoteInstance().exists(
							myfilter)) {
						EventListener[] listeners = prmtPayment
								.getListeners(DataChangeListener.class);
						for (int i = 0; i < listeners.length; i++) {
							prmtPayment
									.removeDataChangeListener((DataChangeListener) listeners[i]);
						}
						prmtPayment.setData(eventObj.getOldValue());
						MsgBox.showError(prmtPayment, "合同必须结算之后才能做结算款类别的付款申请单");
						for (int i = 0; i < listeners.length; i++) {
							prmtPayment
									.addDataChangeListener((DataChangeListener) listeners[i]);
						}
						SysUtil.abort();
					}
					
					txtpaymentProportion.setValue(FDCConstants.ZERO);
				}
				// 当付款类型为结算款和保修款时，已完工工程量金额直接就等于结算金额。
				if (type.getPayType().getId().toString().equals(keepID)
						|| type.getPayType().getId().toString().equals(
								settlementID)) {
					txtpaymentProportion.setEditable(false);
					txtcompletePrjAmt.setEditable(false);
					txtcompletePrjAmt.setValue(FDCHelper.toBigDecimal(editData.getSettleAmt()));
					txtpaymentProportion.setRequired(false);
				} else {
					txtpaymentProportion.setRequired(true && !isAutoComplete);
					txtpaymentProportion.setEditable(true && !isAutoComplete);
					txtcompletePrjAmt.setEditable(true);
				}

			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			}
		}
	}
	
    protected void initListener()
    {
    	//super.initListener();
    }
    
    protected boolean isUseMainMenuAsTitle(){
    	return false;
    }
    
    protected void planAcctCtrl() throws Exception{
		boolean hasUsed=FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_ACCTBUDGET);
		if(hasUsed){
			Map costAcctPlan = ConPayPlanSplitFactory.getRemoteInstance().getCostAcctPlan(editData.getCurProject().getId().toString(), editData.getPayDate());
			Map planSplitMap=(Map)costAcctPlan.get("planSplitMap");
			Map reqSplitMap=(Map)costAcctPlan.get("reqSplitMap");
			//			Map allPlanSplitMap=(Map)costAcctPlan.get("allPlanSplitMap");
			//			Map allReqSplitMap=(Map)costAcctPlan.get("allReqSplitMap");
			if(planSplitMap==null){
				planSplitMap=new HashMap();
			}
			if(reqSplitMap==null){
				reqSplitMap=new HashMap();
			}
			//			if(allPlanSplitMap==null){
			//				allPlanSplitMap=new HashMap();
			//			}
			//			if(allReqSplitMap==null){
			//				allReqSplitMap=new HashMap();
			//			}
			
			//
			for(Iterator iter=planSplitMap.keySet().iterator();iter.hasNext();){
				String key=(String)iter.next();
				BigDecimal planAmt=(BigDecimal)planSplitMap.get(key);
				BigDecimal reqAmt=(BigDecimal)reqSplitMap.get(key);
				if(FDCHelper.toBigDecimal(FDCNumberHelper.subtract(planAmt, reqAmt)).signum()<0){
					String acctId=key.substring(0, 44);
					CostAccountInfo costAccountInfo=CostAccountFactory.getRemoteInstance().getCostAccountInfo(new ObjectUuidPK(acctId));
					MsgBox.showWarning(this, "'"+costAccountInfo.getName()+"' 科目上已经超付");
					return;
				}
			}
		}
    }
    
    /**
	 * 累计已完工工程量=之前所有状态的付款申请单已完工工程量金额+本次录入的已完工工程量金额<br>
	 * 累计付款比例=累计申请金额（付款情况况表中累计合同内工程款截至本期）/累计已完工工程量<br>
	 * 合同最终结算后：合同结算价
	 */
	private void loadAllCompletePrjAmt() {
//		if (isSimpleFinancial) {
//			return;
//		}
		allCompletePrjAmt = FDCHelper.ZERO;
		if (contractBill != null && contractBill.isHasSettled()) {
			txtAllCompletePrjAmt.setValue(contractBill.getSettleAmt());
			BigDecimal prjAllReqAmt = FDCHelper.toBigDecimal(editData
					.getPrjAllReqAmt(), 4);
			if (OprtState.ADDNEW.equals(getOprtState())
					&& bindCellMap.get(PayRequestBillContants.PRJALLREQAMT) != null) {
				prjAllReqAmt = FDCHelper.toBigDecimal(((ICell) bindCellMap
						.get(PayRequestBillContants.PRJALLREQAMT)).getValue(),
						4);
			}
			txtAllPaymentProportion.setValue(prjAllReqAmt.divide(
					FDCHelper.toBigDecimal(txtAllCompletePrjAmt
							.getBigDecimalValue()), BigDecimal.ROUND_HALF_UP)
					.multiply(FDCHelper.ONE_HUNDRED));
			return;
		}
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("completePrjAmt");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("createTime", editData.getCreateTime(),
						CompareType.LESS));
		filter.getFilterItems().add(
				new FilterItemInfo("contractId", editData.getContractId()));
		view.setFilter(filter);

		PayRequestBillCollection payReqColl = null;
		try {
			payReqColl = PayRequestBillFactory.getRemoteInstance()
					.getPayRequestBillCollection(view);

			if (payReqColl != null) {
				for (int i = 0; i < payReqColl.size(); i++) {
					PayRequestBillInfo info = payReqColl.get(i);
					allCompletePrjAmt = allCompletePrjAmt.add(FDCHelper
							.toBigDecimal(info.getCompletePrjAmt()));
				}
			}
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}

		txtAllCompletePrjAmt.setValue(FDCHelper.add(allCompletePrjAmt,
				txtcompletePrjAmt.getBigDecimalValue()));
		if (FDCHelper.toBigDecimal(txtAllCompletePrjAmt.getBigDecimalValue())
				.compareTo(FDCHelper.ZERO) > 0) {
			BigDecimal prjAllReqAmt = FDCHelper.toBigDecimal(editData
					.getPrjAllReqAmt(), 4);
			if (OprtState.ADDNEW.equals(getOprtState())
					&& bindCellMap.get(PayRequestBillContants.PRJALLREQAMT) != null) {
				prjAllReqAmt = FDCHelper.toBigDecimal(((ICell) bindCellMap
						.get(PayRequestBillContants.PRJALLREQAMT)).getValue(),
						4);
			}
			txtAllPaymentProportion.setValue(prjAllReqAmt.divide(
					FDCHelper.toBigDecimal(txtAllCompletePrjAmt
							.getBigDecimalValue()), BigDecimal.ROUND_HALF_UP)
					.multiply(FDCHelper.ONE_HUNDRED));
		}
	}

	private void calAllCompletePrjAmt() {
//		if (isSimpleFinancial) {
//			return;
//		}
		if (contractBill != null && contractBill.isHasSettled()) {
			txtAllCompletePrjAmt.setValue(contractBill.getSettleAmt());
			BigDecimal prjAllReqAmt = FDCHelper.toBigDecimal(editData
					.getPrjAllReqAmt(), 4);
			if (OprtState.ADDNEW.equals(getOprtState())
					&& bindCellMap.get(PayRequestBillContants.PRJALLREQAMT) != null) {
				prjAllReqAmt = FDCHelper.toBigDecimal(((ICell) bindCellMap
						.get(PayRequestBillContants.PRJALLREQAMT)).getValue(),
						4);
			}
			txtAllPaymentProportion.setValue(prjAllReqAmt.divide(
					FDCHelper.toBigDecimal(txtAllCompletePrjAmt
							.getBigDecimalValue()), BigDecimal.ROUND_HALF_UP)
					.multiply(FDCHelper.ONE_HUNDRED));
			return;
		}
		txtAllCompletePrjAmt.setValue(FDCHelper.add(allCompletePrjAmt,
				txtcompletePrjAmt.getBigDecimalValue()));
		if (bindCellMap.get(PayRequestBillContants.PRJALLREQAMT) != null
				&& FDCHelper.toBigDecimal(
						txtAllCompletePrjAmt.getBigDecimalValue()).compareTo(
						FDCHelper.ZERO) > 0) {
			BigDecimal prjAllReqAmt = FDCHelper.toBigDecimal(
					((ICell) bindCellMap
							.get(PayRequestBillContants.PRJALLREQAMT))
							.getValue(), 4);
			txtAllPaymentProportion.setValue(prjAllReqAmt.divide(
					FDCHelper.toBigDecimal(txtAllCompletePrjAmt
							.getBigDecimalValue()), BigDecimal.ROUND_HALF_UP)
					.multiply(FDCHelper.ONE_HUNDRED));
		}
	}
    
    /**
	 * 累计发票金额
	 */
    private void loadInvoiceAmt(){
    	allInvoiceAmt = FDCHelper.ZERO;
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("invoiceAmt");
		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED));
		if(editData.getCreateTime()==null){
			editData.setCreateTime(new Timestamp(System.currentTimeMillis()));
		}
		filter.getFilterItems().add(new FilterItemInfo("createTime", editData.getCreateTime(), CompareType.LESS));
		filter.getFilterItems().add(
				new FilterItemInfo("contractId", editData.getContractId()));
		view.setFilter(filter);
		
		PayRequestBillCollection payReqColl = null;
		try {
			payReqColl = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);
			
			if(payReqColl == null || payReqColl.size() == 0){
				return;
			}
			for(int i=0;i<payReqColl.size();i++){
				PayRequestBillInfo info = payReqColl.get(i);
				allInvoiceAmt = allInvoiceAmt.add(FDCHelper.toBigDecimal(info.getInvoiceAmt()));
			}
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
		
		txtAllInvoiceAmt.setNumberValue(allInvoiceAmt.add(FDCHelper
				.toBigDecimal(txtInvoiceAmt.getBigDecimalValue())));
	}
    
	/**
     * 
     * 描述: 控制逻辑更改。针对付款类型类别为进度款的付款申请单，允许付款金额为0或者已实现为0的情况（但是总计的已完工必须大于已付款。
     * 累计已完工=已审批的付款申请单完工工程量之和+本期完工工程量，累计已付款=所有的付款申请单合同内工程款之和+本期合同内工程款），不允许同时为0的情况。
     * 对于不满足条件的付款申请单，在保存，提交时给出提示：累计已完工（已审批的付款申请单完工工程量之和+本期完工工程量）
     * 小于了累计付款（所有的付款申请单合同内工程款之和+本期合同内工程款），不能执行本操作！
     * 
     * @author pengwei_hou Date: 2008-12-04
     * @throws Exception 
     */
    private void checkCompletePrjAmt() throws Exception{
    	if ((isSeparate && contractBill !=null && contractBill.isIsCoseSplit())
				) {
			return;
		}
    	String paymentType = editData.getPaymentType().getPayType().getId().toString();
    	String progressID = TypeInfo.progressID;
    	if(!paymentType.equals(progressID)){
    		return;
    	}
    	
    	//已完工工程量(已完工)
    	BigDecimal completePrjAmt = FDCHelper.toBigDecimal(editData.getCompletePrjAmt());
    	//合同内工程款(已付款)
    	BigDecimal allProjectPriceInContract = FDCHelper.toBigDecimal(editData.getProjectPriceInContract());
    	
    	if(completePrjAmt.compareTo(allProjectPriceInContract) >= 0){
    		return;
    	}
    	
    	EntityViewInfo view = new EntityViewInfo();
    	view.getSelector().add("projectPriceInContract");
    	view.getSelector().add("completePrjAmt");
    	view.getSelector().add("state");
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("contractId", editData.getContractId()));
    	filter.getFilterItems().add(new FilterItemInfo("paymentType.payType.id", progressID));
    	filter.getFilterItems().add(new FilterItemInfo("createTime", editData.getCreateTime(), CompareType.LESS_EQUALS));
    	//不包括本次,本次在上面单独加取当前单据最新数据,以避免新增或修改时数据错误
    	if(editData.getId() != null){
    		filter.getFilterItems().add(new FilterItemInfo("id", editData.getId().toString(), CompareType.NOTEQUALS));
    	}
    	view.setFilter(filter);
    	PayRequestBillCollection payReqColl = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);
    	
    	if(payReqColl != null && payReqColl.size() > 0){
    		for(int i=0;i<payReqColl.size();i++){
    			PayRequestBillInfo info = payReqColl.get(i);
    			allProjectPriceInContract = allProjectPriceInContract.add(FDCHelper.toBigDecimal(info.getProjectPriceInContract()));
    			if(info.getState() == FDCBillStateEnum.AUDITTED){
    				completePrjAmt = completePrjAmt.add(FDCHelper.toBigDecimal(info.getCompletePrjAmt()));
    			}
    		}
    	}
    	completePrjAmt = FDCHelper.toBigDecimal(completePrjAmt, 2);
    	allProjectPriceInContract = FDCHelper.toBigDecimal(allProjectPriceInContract, 2);
    	if(completePrjAmt.compareTo(allProjectPriceInContract) < 0){
    		MsgBox.showWarning(this, "累计已完工（已审批的付款申请单完工工程量之和+本期完工工程量）小于累计付款（所有的付款申请单合同内工程款之和+本期合同内工程款），不能执行本操作！");
    		SysUtil.abort();
    	}
    }
	
    protected void prmtPayment_willCommit(CommitEvent e) throws Exception {
    	/***
    	 * 42.	提单：R090609-207  刘业平
    	 * 简单模式也可以选择结算款,保修款
    	 * by 周勇
    	 */
//		if(isSimpleFinancial){
//			prmtPayment.getQueryAgent().resetRuntimeEntityView();
//			if(prmtPayment.getEntityViewInfo()!=null){
//				EntityViewInfo view = prmtPayment.getEntityViewInfo();
//				view.getFilter().getFilterItems().add(new FilterItemInfo("payType.id",PaymentTypeInfo.progressID));
//				prmtPayment.setEntityViewInfo(view);
//			}else{
//				EntityViewInfo view = new EntityViewInfo();
//				view.setFilter(new FilterInfo());
//				view.getFilter().getFilterItems().add(new FilterItemInfo("payType.id",PaymentTypeInfo.progressID));
//				prmtPayment.setEntityViewInfo(view);
//			}
//		}
	}
	
	protected void prmtPayment_willShow(SelectorEvent e) throws Exception {
//		if(isSimpleFinancial){
//			prmtPayment.getQueryAgent().resetRuntimeEntityView();
//			if(prmtPayment.getEntityViewInfo()!=null){
//				EntityViewInfo view = prmtPayment.getEntityViewInfo();
//				view.getFilter().getFilterItems().add(new FilterItemInfo("payType.id",PaymentTypeInfo.progressID));
//				prmtPayment.setEntityViewInfo(view);
//			}else{
//				EntityViewInfo view = new EntityViewInfo();
//				FilterInfo filter = new FilterInfo();
//				filter.getFilterItems().add(new FilterItemInfo("payType.id",PaymentTypeInfo.progressID));
//				view.setFilter(filter);
//				prmtPayment.setEntityViewInfo(view);
//			}
//		}
	}
    
    /**
     * 本付款单的合同ID
     * @return contractId
     */
	public void actionAssociateAcctPay_actionPerformed(ActionEvent e) throws Exception {
		if (OprtState.ADDNEW.equals(getOprtState())||this.editData==null||this.editData.getId()==null) {
			// 须保存警告
			MsgBox.showWarning(getRes("saveBillFirst"));
			SysUtil.abort();
		}
		super.actionAssociateAcctPay_actionPerformed(e);
		FDCBudgetPeriodInfo period=getFDCBudgetPeriod();
//		this.editData.setAmount(txtAmount.getBigDecimalValue());
		this.editData.setAmount(FDCHelper.toBigDecimal(txtBcAmount.getBigDecimalValue()));
		PayReqAcctPayUI.showPayReqAcctPayUI(this, this.editData,period,getOprtState());
	}
    public String getPayReqContractId() {
    	return payReqContractId;
    }
    
    public void actionAssociateUnSettled_actionPerformed(ActionEvent e) throws Exception {
    	// TODO 自动生成方法存根
    	super.actionAssociateUnSettled_actionPerformed(e);
    	String prjId=editData.getCurProject().getId().toString();
		String contractId=editData.getContractId();
		//if exist costaccount month plan it's not a unSettled contract
		FilterInfo filter=new FilterInfo();
		filter.appendFilterItem("contractBill.id", contractId);
		filter.appendFilterItem("itemType", FDCBudgetAcctItemTypeEnum.CONTRACT_VALUE);
		filter.appendFilterItem("isAdd", Boolean.FALSE);
		filter.appendFilterItem("parent.state", FDCBillStateEnum.AUDITTED_VALUE);
		
		boolean isExistContractPlan=FDCMonthBudgetAcctEntryFactory.getRemoteInstance().exists(filter);
		if(isExistContractPlan){
			FDCMsgBox.showWarning(this, "当前合同存在项目月度科目付款计划,不属于待签订合同,不需要做关联!");
			SysUtil.abort();
		}
		FDCBudgetPeriodInfo period=getFDCBudgetPeriod();
		if(period==null){
			period=FDCBudgetPeriodInfo.getCurrentPeriod(false);
		}
		ContractAssociateAcctPlanUI.showContractAssociateAcctPlanUI(this, prjId, contractId, period, getOprtState());
	}

	public void actionViewPayDetail_actionPerformed(ActionEvent e) throws Exception {
		// TODO 自动生成方法存根
		if (editData == null || editData.getContractId() == null) {
			return;
		}
		// tableHelper.debugCellExp();
		viewPayDetail();
		
	}
	private void viewPayDetail() throws UIException {
//		if (editData.getId() == null)
//			return;
		String editUIName = null;
		
		editUIName = PayRequestViewPayDetailUI.class
				.getName();

		if (editUIName == null)
			return;
		UIContext uiContext = new UIContext(this);
		if(editData.getContractNo() == null){
			return;
		}
		uiContext.put("contractId", editData.getContractId());
		uiContext.put("createTime", editData.getCreateTime());
		// 创建UI对象并显示
		IUIWindow windows = this.getUIWindow();
		String type = UIFactoryName.NEWWIN;
		if (windows instanceof UIModelDialog) {
			type = UIFactoryName.MODEL;
		}
		IUIWindow contractUiWindow = UIFactory.createUIFactory(type).create(
				editUIName, uiContext, null, "FINDVIEW",WinStyle.SHOW_RESIZE);
		if (contractUiWindow != null) {
			contractUiWindow.show();
		}
	}
    
    public void txtexchangeRate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception{
    	if(getDetailTable().getCell(rowIndex,columnIndex)!=null){
    		BigDecimal oriAmount = FDCHelper.toBigDecimal(getDetailTable().getCell(rowIndex,columnIndex).getValue());
    		setAmountChange(oriAmount);
    	}
    	
    	if(((ICell) bindCellMap
				.get(PayRequestBillContants.PAYPARTAMATLAMT))!=null){
    		BigDecimal oriPMTAmount = FDCHelper.toBigDecimal(((ICell) bindCellMap
    				.get(PayRequestBillContants.PAYPARTAMATLAMT)).getValue());
    		setPmtAmoutChange(oriPMTAmount);
    	}
		
    	
		
		btnInputCollect_actionPerformed(null);
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
    
    protected void prepareInitDataHandlerParam(RequestContext request)
    {
		//合同Id
		String contractBillId = (String) getUIContext().get("contractBillId");
		if(contractBillId==null){
			request.put("FDCBillEditUIHandler.ID",getUIContext().get("ID"));
		}else{
			request.put("FDCBillEditUIHandler.contractBillId",contractBillId);
		}		
		
		//工程项目Id
		BOSUuid projectId = ((BOSUuid) getUIContext().get("projectId"));
		request.put("FDCBillEditUIHandler.projectId",projectId);

		//合同类型ID
		BOSUuid typeId = (BOSUuid) getUIContext().get("contractTypeId");
		request.put("FDCBillEditUIHandler.typeId",typeId);
    }

	public void actionContractAttachment_actionPerformed(ActionEvent e) throws Exception {
		// TODO 自动生成方法存根
		super.actionContractAttachment_actionPerformed(e);
		
		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
		if(editData.getContractId() != null) {
				acm.showAttachmentListUIByBoID(editData.getContractId(),this,false);
		}else{
			return;
		}
	}
	/**
     * 预付款：工程量启用+合同
     * @return
     */
    private boolean isAdvance(){
    	try {
			return isSeparate&&FDCUtils.isContractBill(null, editData.getContractId());
		} catch (EASBizException e) {
			handUIExceptionAndAbort(e);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
		return false;
    }
}