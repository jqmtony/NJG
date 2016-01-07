/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.RowSet;
import javax.swing.Action;
import javax.swing.SwingUtilities;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.time.DateFormatUtils;
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
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
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
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.WinStyle;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.param.IParamControl;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.param.util.ParamManager;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.uiframe.client.UIModelDialog;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
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
import com.kingdee.eas.fdc.basedata.client.AttachmentUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCColorConstants;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.util.FdcBooleanUtil;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractFacadeFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.DepPlanStateEnum;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.GuerdonBillCollection;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.IPayRequestBill;
import com.kingdee.eas.fdc.contract.PartAConfmOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.PartAOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.PayReqUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillConfirmEntryCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.UrgentDegreeEnum;
import com.kingdee.eas.fdc.finance.ConPayPlanSplitFactory;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctFacadeFactory;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctItemTypeEnum;
import com.kingdee.eas.fdc.finance.FDCBudgetConstants;
import com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractFactory;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettledConInfo;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctEntryFactory;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanCollection;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanFactory;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanInfo;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillCollection;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillFactory;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetCtrlStrategy.FDCBudgetParam;
import com.kingdee.eas.fdc.finance.client.ContractAssociateAcctPlanUI;
import com.kingdee.eas.fdc.finance.client.ContractPayPlanEditUI;
import com.kingdee.eas.fdc.finance.client.FDCMonthReqMoneyUI;
import com.kingdee.eas.fdc.finance.client.PayReqAcctPayUI;
import com.kingdee.eas.fdc.finance.client.PaymentBillEditUI;
import com.kingdee.eas.fdc.material.client.MaterialConfirmBillSimpleListUI;
import com.kingdee.eas.fdc.schedule.ContractAndTaskRelEntryCollection;
import com.kingdee.eas.fdc.schedule.ContractAndTaskRelEntryFactory;
import com.kingdee.eas.fdc.schedule.FDCSCHUtils;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fi.gl.client.VoucherEditUI;
import com.kingdee.eas.framework.BillBaseCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.client.AbstractCoreBillEditUI;
import com.kingdee.eas.framework.client.workflow.DefaultWorkflowUIEnhancement;
import com.kingdee.eas.framework.client.workflow.IWorkflowUIEnhancement;
import com.kingdee.eas.framework.client.workflow.IWorkflowUISupport;
import com.kingdee.eas.ma.budget.BgControlFacadeFactory;
import com.kingdee.eas.ma.budget.BgCtrlResultCollection;
import com.kingdee.eas.ma.budget.BgCtrlResultInfo;
import com.kingdee.eas.ma.budget.BudgetCtrlUtil;
import com.kingdee.eas.ma.budget.IBgControlFacade;
import com.kingdee.eas.ma.budget.IBudgetCtrlFacade;
import com.kingdee.eas.ma.budget.client.BudgetCtrlClientCaller;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;

/**
 * @(#)			PayRequestBillEditUI 版权： 金蝶国际软件集团有限公司版权所有 描述： 付款申请单 编辑界面
 * 
 * @author 蒲磊
 *         <p>
 * @createDate 2011-8-31
 *             <p>
 * @version EAS7.0
 * @see
 */
public class PayRequestBillEditUI extends AbstractPayRequestBillEditUI implements IWorkflowUISupport {
	/**
	 * 未结算合同的实付款不能大于已实现产值【合同实付款=已关闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申 请单合同内工程款合计】
	 */
	private static final String cantMoreThanTotalSettlePrice = "未结算合同的实付款不能大于已实现产值【合同实付款=已关闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申请单合同内工程款合计】";

	private static final long serialVersionUID = 1L;

	private BigDecimal totalPayAmtByReqId = FDCHelper.ZERO;

	private static final Logger logger = CoreUIObject.getLogger(PayRequestBillEditUI.class);

	private boolean isFirstLoad = true;// 是否第一次加载,用来控制表格的加载显示

	private static final Color noEditColor = PayReqTableHelper.noEditColor;

	protected int rowIndex = 5;// 合同内工程款行号

	protected int columnIndex = 6;// 合同内工程款列号

	/** 是否多次付款 */
	private boolean isMoreSettlement = false;
	/**
	 * 合同（合同或无文本）是否进入动态成本
	 */
	protected boolean isCostSplitContract = false;

	// 付款申请单提交时，是否检查合同未完全拆分
	private boolean checkAllSplit = true;

	// 已实现为0时只能选择预付款的控制
	private boolean isRealizedZeroCtrl = false;

	// 完工工程量的确认是否严格控制
	private boolean isFillBillControlStrict = false;

	// 月度滚动计划控制付款申请策略
	protected String CONTROLPAYREQUEST = "不控制";
	/**
	 * 调整扣款项窗口
	 */
	private IUIWindow deductUIwindow = null;
	/**
	 * 用于绑定cell进行值操作的map key为属性的info属性名，value为cell的引用
	 */
	HashMap bindCellMap = new HashMap(20);

	private PayReqTableHelper tableHelper = new PayReqTableHelper(this);

	// 是否使用预算
	protected boolean isMbgCtrl = false;

	// 房地产付款单强制不进入出纳系统
	protected boolean isNotEnterCAS = false;

	protected FDCBudgetParam fdcBudgetParam = null;

	// 付款比例,已经结算
	private BigDecimal payScale;

	// 供应商
	// private SupplierCompanyInfoInfo supplierCompanyInfoInfo ;未使用，暂注释
	// 设置付款次数为合同的付款次数 从付款单中过滤
	private int payTimes = 0;

	// 变更单
	private ContractChangeBillCollection contractChangeBillCollection = null;

	// 付款单
	private BillBaseCollection paymentBillCollection = null;

	// 付款申请单对应的奖励项
	private GuerdonOfPayReqBillCollection guerdonOfPayReqBillCollection = null;

	// 奖励单
	private GuerdonBillCollection guerdonBillCollection = null;

	// 付款申请单对应的违约金
	private CompensationOfPayReqBillCollection compensationOfPayReqBillCollection = null;

	// 付款申请单对应的甲供材扣款
	private PartAOfPayReqBillCollection partAOfPayReqBillCollection = null;

	// 付款申请单对应的甲村确认单金额
	private PartAConfmOfPayReqBillCollection partAConfmOfPayReqBillCollection = null;

	// 扣款类型
	private DeductTypeCollection deductTypeCollection = null;

	// 工程项目对应的成本中心
	private FullOrgUnitInfo costOrg = null;

	// 是否加载过初始数据
	private boolean hasFetchInit = false;

	// 累计请款超过合同最新造价严格控制
	private boolean isControlCost = false;

	// 用途字段受控
	protected int usageLegth = 90;

	// 付款申请单收款银行和收款账号为必录项
	private boolean isBankRequire = false;

	/**
	 * 申请单进度款付款比例自动为100%
	 */
	private boolean isAutoComplete = false;

	// 甲供材系统参数
	private boolean partAParam = false;

	// 付款申请单合同ID
	private String payReqContractId = null;

	// 付款申请单合同是否甲供材合同
	public static boolean isPartACon = false;

	// 简单模式一体化
	private boolean isSimpleFinancial = false;

	// 累计发票金额/
	private BigDecimal allInvoiceAmt = FDCHelper.ZERO;

	// 累计发票金额原币
	private BigDecimal allInvoiceOriAmt = FDCHelper.ZERO;

	// 累计已完工工程量/
	private BigDecimal allCompletePrjAmt = FDCHelper.ZERO;

	/**
	 * 工程量确认流程与付款流程是否分离
	 */
	protected boolean isSeparate = false;

	// 启用发票管理
	private boolean invoiceMgr = false;

	// 简单模式处理发票
	protected boolean isSimpleInvoice = false;

	// 付款申请单及无文本合同发票号、发票金额必录
	private boolean isInvoiceRequired = false;

	// 甲供材合同，相关的材料确认单的累计确认金额，需要 写入已 完工工程量，一旦 有了这个金额，不能在修改已完工工程量
	// 相关的材料确认单IDS ，保存时，更新这些材料确认单的付款申请单ID
	private BigDecimal confirmAmts = FDCHelper.ZERO;
	private PayRequestBillConfirmEntryCollection confirmBillEntry = null;

	// 是否允许付款申请单累计发票金额大于合同最新造价
	private boolean isOverrun = false;
	/**
	 * 责任人是否可以选择其他部门的人员,用这个参数来控制用款部门是否可以选择其他组织的用款部门
	 */
	private boolean canSelectOtherOrgPerson = false;

	private final String fncResPath = "com.kingdee.eas.fdc.finance.client.FinanceResource";

	// by tim_gao 合同是否工程量确认
	private boolean isWorkLoadConType = true;

	private String invoiceOriAndAmtStr = "";

	/**
	 * 合同完工工程量取进度系统工程量填报数据
	 */
	private boolean isFromProjectFillBill = false;

	// 此单据是否有附件
	private boolean isHasAttchment = false;

	// 预付款次数
	private int advancePaymentNumber = 1;

	private boolean isControlPay = false;

	// 完工工程量从工程量确认单取数
	private boolean isByWorkload = false;

	// 预付款常量
	private static final String YFK = "预付款";

	// 进度款付款比例
	private Object paymentProportionValue = null;
	// 本期完工工程量
	private Object completePrjAmtValue = null;

	// 本期完工工程量常量
	private static final int COMPLETEPRJAMT = 1;
	// 进度款付款比例常量
	private static final int PAYMENTPROPORTION = 2;

	protected boolean ctrl = false;
	//[施工]类型的合同
	protected boolean isShiGongContract = false;
	protected FilterInfo originalAmount = null;

	/**
	 * 构造器
	 */
	public PayRequestBillEditUI() throws Exception {
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
		this.toolBar.add(btnContractExecInfo);

		this.toolBar.add(btnAuditResult);
		actionAuditResult.setVisible(true);
		actionAuditResult.setEnabled(true);

		actionContractAttachment.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_view"));
		this.toolBar.add(btnContractAttachment);
		actionContractAttachment.setVisible(true);
		actionContractAttachment.setEnabled(true);

		this.toolBar.add(this.btnViewMaterialConfirm);
	}

	/**
	 * description 数据变化统一事件，根据传入的不同参数，触发不同的变化事件
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-8-31
	 *             <p>
	 * 
	 * @version EAS 7.0
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#controlDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent,
	 *     
	 *     
	 *      com.kingdee.eas.fdc.basedata.client.FDCBillEditUI.ControlDateChangeListener)
	 */
	protected void controlDate_dataChanged(
			com.kingdee.bos.ctrl.swing.event.DataChangeEvent e,
			ControlDateChangeListener listener) throws Exception {
		if ("bookedDate".equals(listener.getShortCut())) {
			bookedDate_dataChanged(e);
		} else if ("amount".equals(listener.getShortCut())) {
			currencydataChanged(e);
		} else if ("completePrjAmt".equals(listener.getShortCut())
				|| "paymentProp".equals(listener.getShortCut())) {
			if (!isProgressPaymentAndWorkLoadConfirm()) {
				setPropPrjAmount(listener.getShortCut(), e);
			}
		}
	}

	/** 业务日期 变化事件 */
	ControlDateChangeListener bookedDateChangeListener = new ControlDateChangeListener(
			"bookedDate");

	/** 原币金额 变化事件 */
	ControlDateChangeListener amountListener = new ControlDateChangeListener(
			"amount");

	/** 本期完工工程量 变化事件 */
	ControlDateChangeListener completePrjAmtListener = new ControlDateChangeListener(
			"completePrjAmt");

	/** 进度款付款比例 变化事件 */
	ControlDateChangeListener paymentProptListener = new ControlDateChangeListener(
			"paymentProp");

	/**
	 * description 设置监听器
	 * 
	 */
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
		addDataChangeListener(txtcompletePrjAmt);
	}

	/**
	 * description 移除监听
	 * 
	 */
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
		removeDataChangeListener(txtcompletePrjAmt);
	}

	/**
	 * description 加载数据
	 * 
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
		if (editData.getUrgentDegree() == null) {
			editData.setUrgentDegree(UrgentDegreeEnum.NORMAL);
		}

		super.loadFields();
		// 新增没数据，不用设置 by hpw
		// if (OprtState.ADDNEW.equals(getOprtState())) {
		// txtpaymentProportion.setValue(FDCHelper.ZERO);
		// txtcompletePrjAmt.setValue(FDCHelper.ZERO);
		// }
		//		
		// 设置付款次数为合同的付款次数 从付款单中获取
		if (editData.getState() != FDCBillStateEnum.AUDITTED) {
			editData.setPayTimes(payTimes);
		}

		if (editData.getState() != null
				&& !editData.getState().equals(FDCBillStateEnum.SAVED)) {
			btnSave.setEnabled(false);
		}

		if (editData.getCurProject() != null) {
			CurProjectInfo curProjectInfo = editData.getCurProject();
			txtProj.setText(curProjectInfo.getDisplayName());
		}

		if (editData.getOrgUnit() != null) {
			txtOrg.setText(editData.getOrgUnit().getDisplayName());
		}

		// 如果是无文本合同，调整款项按钮灰显
		if (editData.getContractId() != null
				&& PayReqUtils.isConWithoutTxt(editData.getContractId())) {
			actionAdjustDeduct.setEnabled(false);
		} else {
			actionAdjustDeduct.setEnabled(true);
		}

		// 大写金额
		if (editData.getCapitalAmount() == null && editData.getAmount() != null) {
			// 大写金额为本位币金额
			BigDecimal localamount = editData.getAmount();
			if (localamount.compareTo(FDCConstants.ZERO) != 0) {
				localamount = localamount.setScale(2, BigDecimal.ROUND_HALF_UP);
			}
			String cap = FDCClientHelper.getChineseFormat(localamount, false);
			txtcapitalAmount.setText(cap);
			if (localamount.compareTo(FDCConstants.ZERO) == 0) {
				txtcapitalAmount.setText(null);
			}
			editData.setCapitalAmount(cap);
		}

		hasFetchInit = true; // 设置已加载过初始化数据

		// 显示累计发票金额的原币和本币，隐藏原来的累计发票金额
		loadInvoiceAmt();

		// 加载累计已完工工程量和累计应付付款比例
		loadAllCompletePrjAmt();

		// 设置监听器
		attachListeners();

		// 收款帐号修改成F7选择，但是元数据定义的是自有属性， 所以没有进行数据绑定，需要手动装载。 by zhiyuan_tang
		// 2010/12/07 R101026-193
		if (editData.getRealSupplier() != null) {
			txtrecAccount.setValue(getSupplierCompanyBankInfoByAccount(editData
					.getRealSupplier().getId().toString(), editData
					.getRecBank(), editData.getRecAccount()));
		}
		txtrecAccount.setText(editData.getRecAccount());
	}

	/**
	 * description 设置汇率和原币金额 及其精度
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
	protected void setPrecision() {
		CurrencyInfo currency = null;
		if (null != editData.getCurrency()) {
			currency = editData.getCurrency();
		}

		Date bookedDate = null;
		if (null != editData.getBookedDate()) {
			bookedDate = (Date) editData.getBookedDate();
		}
		ExchangeRateInfo exchangeRate = null;
		try {
			exchangeRate = FDCClientHelper.getLocalExRateBySrcCurcy(this,
					currency.getId(), company, bookedDate); // 获取当前汇率
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}

		int curPrecision = FDCClientHelper.getPrecOfCurrency(currency.getId());
		int exPrecision = curPrecision;

		if (exchangeRate != null) {
			exPrecision = exchangeRate.getPrecision();
		}

		txtexchangeRate.setPrecision(exPrecision);
		txtAmount.setPrecision(curPrecision);
		BigDecimal exRate = editData.getExchangeRate();
		txtexchangeRate.setValue(exRate);
		txtAmount.setValue(editData.getOriginalAmount());
	}

	/**
	 * description 页面数据绑定之前
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * 
	 * @version EAS 7.0
	 * @see com.kingdee.eas.framework.client.EditUI#beforeStoreFields(java.awt.event.ActionEvent)
	 */
	public void beforeStoreFields(ActionEvent e) throws Exception {
		super.beforeStoreFields(e);

		String contractId = editData.getContractId();

		/**
		 * 给工作流中的状态做判断 by renliang 2010-5-26
		 */
		if (getUIContext().get("isFromWorkflow") != null
				&& getUIContext().get("isFromWorkflow").toString().equals(
						"true") && getOprtState().equals(OprtState.EDIT)
				&& actionSave.isVisible()) {
			if (!editData.getState().equals(FDCBillStateEnum.SUBMITTED)
					|| !editData.getState().equals(FDCBillStateEnum.SAVED)) {
				editData.setState(FDCBillStateEnum.SUBMITTED);
			}

		}

		// 将分录内的数据存储到info
		if (PayReqUtils.isContractBill(contractId)
				&& (editData.getState() == FDCBillStateEnum.SAVED || editData
						.getState() == FDCBillStateEnum.SUBMITTED)) {
			try {
				tableHelper.updateDynamicValue(editData, contractBill,
						contractChangeBillCollection, paymentBillCollection);
			} catch (Exception e1) {
				handUIExceptionAndAbort(e1);
			}
			PayReqUtils.getValueFromCell(editData, bindCellMap);
			if (editData.getProjectPriceInContract() == null
					|| editData.getProjectPriceInContractOri() == null) {
				editData.setProjectPriceInContract(FDCHelper.ZERO);
				editData.setProjectPriceInContractOri(FDCHelper.ZERO);
			}
			if (isAdvance()) {
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
			setCostAmount();
		}
		try {
			this.btnInputCollect_actionPerformed(null);
		} catch (Exception ex) {
			handUIExceptionAndAbort(ex);
		}
	}

	/**
	 * description 将界面的数据绑定到editdata
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * 
	 * @version EAS 7.0
	 * @see com.kingdee.eas.fdc.contract.client.AbstractPayRequestBillEditUI#storeFields()
	 */
	public void storeFields() {
		try {
			if (editData != null) {// 第一次保存时初始状态
				// 付款申请单增加存储本位币币别，以方便预算系统能取到该字段值 by Cassiel_peng 2009-10-3
				CompanyOrgUnitInfo currentFIUnit = SysContext.getSysContext()
						.getCurrentFIUnit();
				CurrencyInfo baseCurrency = currentFIUnit.getBaseCurrency();
				if (baseCurrency != null) {
					// R110519-0204:编码空，预算余额取不到 、预算控制失效 by hpw 2011.6.2
					if (baseCurrency.getNumber() == null) {
						baseCurrency = CurrencyFactory.getRemoteInstance()
								.getCurrencyInfo(
										new ObjectUuidPK(baseCurrency.getId()));
					}
					this.editData.setLocalCurrency(baseCurrency);
				}
			}
			if (null != editData.getContractId()) { // 设置合同最新本位币造价
				editData.setLatestPrice(FDCHelper.toBigDecimal((FDCUtils
						.getLastAmt_Batch(null, new String[] { editData
								.getContractId().toString() }).get(editData
						.getContractId().toString()))));
			}
		} catch (EASBizException e) {
			handUIExceptionAndAbort(e);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}

		// 收款帐号修改成F7选择，但是元数据定义的是自有属性， 所以没有进行数据绑定，需要手动保存一下。 by zhiyuan_tang
		// 2010/12/07 R101026-193
		if (txtrecAccount.getValue() instanceof String
				|| txtrecAccount.getText() instanceof String) {
			editData.setRecAccount(txtrecAccount.getText());
		} else if (txtrecAccount.getValue() instanceof SupplierCompanyBankInfo) {
			SupplierCompanyBankInfo info = (SupplierCompanyBankInfo) txtrecAccount
					.getValue();
			editData.setRecAccount(info.getBankAccount());
		} else {
			editData.setRecAccount(null);
		}
		super.storeFields();
	}

	/**
	 * description 获取页面所需的数据
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-8-31
	 *             <p>
	 * 
	 * @version EAS 7.0
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#fetchInitData()
	 */
	protected void fetchInitData() throws Exception {
		String contractBillId = (String) getUIContext().get("contractBillId");

		Map initparam = new HashMap();

		if (contractBillId != null) {
			initparam.put("contractBillId", contractBillId);
		} else {
			if (editData != null && editData.getContractId() != null) {
				initparam.put("contractBillId", editData.getContractId());
			} else {
				initparam.put("ID", getUIContext().get("ID"));
			}
		}

		Map initData = (Map) ActionCache.get("FDCBillEditUIHandler.initData");
		if (initData == null) {
			initData = ((IFDCBill) getBizInterface()).fetchInitData(initparam);
		}

		if (initData.get("totalPayAmtByReqId") != null) {
			totalPayAmtByReqId = (BigDecimal) initData
					.get("totalPayAmtByReqId");
		}

		// 本位币
		baseCurrency = (CurrencyInfo) initData
				.get(FDCConstants.FDC_INIT_CURRENCY);
		// 财务组织
		company = (CompanyOrgUnitInfo) initData
				.get(FDCConstants.FDC_INIT_COMPANY);
		// 合同单据
		contractBill = (ContractBillInfo) initData
				.get(FDCConstants.FDC_INIT_CONTRACT);
		// 付款比例
		payScale = (BigDecimal) initData.get("payScale");
		// 供应商
		// supplierCompanyInfoInfo =
		// (SupplierCompanyInfoInfo)initData.get("supplierCompanyInfoInfo");

		// 设置付款次数为合同的付款次数 从付款单中过滤
		payTimes = ((Integer) initData.get("payTimes")).intValue();
		// 变更单
		contractChangeBillCollection = (ContractChangeBillCollection) initData
				.get("ContractChangeBillCollection");
		// 付款单
		paymentBillCollection = (BillBaseCollection) initData
				.get("PaymentBillCollection");
		// 付款申请单对应的奖励项
		guerdonOfPayReqBillCollection = (GuerdonOfPayReqBillCollection) initData
				.get("GuerdonOfPayReqBillCollection");
		// 奖励单
		guerdonBillCollection = (GuerdonBillCollection) initData
				.get("GuerdonBillCollection");
		// 付款申请单对应的违约金
		compensationOfPayReqBillCollection = (CompensationOfPayReqBillCollection) initData
				.get("CompensationOfPayReqBillCollection");
		// 付款申请单对应的甲供材扣款
		partAOfPayReqBillCollection = (PartAOfPayReqBillCollection) initData
				.get("PartAOfPayReqBillCollection");
		// 付款申请单对应的甲供材确认单金额
		partAConfmOfPayReqBillCollection = (PartAConfmOfPayReqBillCollection) initData
				.get("PartAConfmOfPayReqBillCollection");
		// 扣款类型
		deductTypeCollection = (DeductTypeCollection) initData
				.get("DeductTypeCollection");
		// 工程项目对应的成本中心
		costOrg = (FullOrgUnitInfo) initData.get("FullOrgUnitInfo");

		// 日期
		bookedDate = (Date) initData.get(FDCConstants.FDC_INIT_DATE);
		if (bookedDate == null) {
			bookedDate = new Date();
		}
		serverDate = (Date) initData.get("serverDate");
		if (serverDate == null) {
			serverDate = bookedDate;
		}
		// 当前期间
		curPeriod = (PeriodInfo) initData.get(FDCConstants.FDC_INIT_PERIOD);

		curProject = (CurProjectInfo) initData
				.get(FDCConstants.FDC_INIT_PROJECT);

		orgUnitInfo = (FullOrgUnitInfo) initData
				.get(FDCConstants.FDC_INIT_ORGUNIT);
		if (orgUnitInfo == null) {
			orgUnitInfo = SysContext.getSysContext().getCurrentOrgUnit()
					.castToFullOrgUnitInfo();
		}

		// 甲供确认单的确认金额
		confirmAmts = FDCHelper.toBigDecimal(initData.get("confirmAmts"));
		// 甲供确认分录
		confirmBillEntry = (PayRequestBillConfirmEntryCollection) initData
				.get("confirmBillEntry");
		this.isCostSplitContract = isCostSplit();
	}

	/**
	 * description 合同或无文本合同为进入动态成本时返回true，否则返回false
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
	private boolean isCostSplit() throws Exception {
		if (contractBill != null && contractBill.isIsCoseSplit()) {// 合同
			return true;
		}

		String contractBillId = (String) getUIContext().get("contractBillId");
		if (PayReqUtils.isConWithoutTxt(contractBillId)) { // 是不是无文本合同
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("isCostSplit");
			ContractWithoutTextInfo withoutTextInfo = ContractWithoutTextFactory
					.getRemoteInstance().getContractWithoutTextInfo(
							new ObjectUuidPK(BOSUuid.read(contractBillId)),
							selector);
			if (withoutTextInfo != null && withoutTextInfo.isIsCostSplit()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * description 填充初始化参数
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * 
	 * @version EAS 7.0
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#fetchInitParam()
	 */
	protected void fetchInitParam() throws Exception {
		// modify by lihaiou, 2014-09-12, fix bug R140823-0007,严重性能问题
		Map paramItem = getParamItem();
		if (orgUnitInfo != null) {
			Map param = paramItem;
			// param = FDCUtils.getDefaultFDCParam(null,
			// orgUnitInfo.getId().toString());

			// 是否启用预算
			isMbgCtrl = Boolean.valueOf(
					param.get(FDCConstants.FDC_PARAM_STARTMG).toString())
					.booleanValue();

			// 付款申请单付款金额不允许超过可付款额度
			isControlCost = Boolean.valueOf(
					param.get(FDCConstants.FDC_PARAM_OUTPAYAMOUNT).toString())
					.booleanValue();

			// 申请单进度款付款比例自动为100%
			isAutoComplete = Boolean.valueOf(
					param.get(FDCConstants.FDC_PARAM_PAYPROGRESS).toString())
					.booleanValue();

			if (param.get(FDCConstants.FDC_PARAM_SELECTPERSON) != null) {
				canSelectOtherOrgPerson = Boolean.valueOf(
						param.get(FDCConstants.FDC_PARAM_SELECTPERSON)
								.toString()).booleanValue();
			}
			if (param.get(FDCConstants.FDC_PARAM_ADVANCEPAYMENTNUMBER) != null) {
				advancePaymentNumber = Integer.valueOf(
						param.get(FDCConstants.FDC_PARAM_ADVANCEPAYMENTNUMBER)
								.toString()).intValue();
			}
			if (param.get(FDCConstants.FDC_PARAM_ISCONTROLPAYMENT) != null) {
				isControlPay = Boolean.valueOf(
						param.get(FDCConstants.FDC_PARAM_ISCONTROLPAYMENT)
								.toString()).booleanValue();
			}

			if (param.get(FDCConstants.FDC_PARAM_MORESETTER) != null) {
				isMoreSettlement = Boolean
						.valueOf(
								param.get(FDCConstants.FDC_PARAM_MORESETTER)
										.toString()).booleanValue();
			}
		}

		if (company == null) {
			return;
		}
		// 启用成本财务一体化
		// Map paramItem = null;
		// paramItem = FDCUtils.getDefaultFDCParam(null,
		// company.getId().toString());

		if (paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION) != null) {
			isIncorporation = Boolean.valueOf(
					paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION)
							.toString()).booleanValue();
		}

		// 简单模式的一体化
		if (paramItem.get(FDCConstants.FDC_PARAM_SIMPLEFINACIAL) != null) {
			isSimpleFinancial = Boolean.valueOf(
					paramItem.get(FDCConstants.FDC_PARAM_SIMPLEFINACIAL)
							.toString()).booleanValue();
			// isAutoComplete = isAutoComplete;//||isSimpleFinancial;
		}

		// 用途字段受控
		if (paramItem.get("CS050") != null) {
			usageLegth = Integer.valueOf(paramItem.get("CS050").toString())
					.intValue();
		}

		// 付款申请单收款银行和收款账号为必录项
		if (paramItem.get(FDCConstants.FDC_PARAM_BANKREQURE) != null) {
			isBankRequire = Boolean
					.valueOf(
							paramItem.get(FDCConstants.FDC_PARAM_BANKREQURE)
									.toString()).booleanValue();
		}

		// 房地产付款单强制进入出纳系统
		if (paramItem.get(FDCConstants.FDC_PARAM_NOTENTERCAS) != null) {
			isNotEnterCAS = Boolean.valueOf(
					paramItem.get(FDCConstants.FDC_PARAM_NOTENTERCAS)
							.toString()).booleanValue();
		}

		// 甲供材
		if (paramItem.get(FDCConstants.FDC_PARAM_CREATEPARTADEDUCT) != null) {
			partAParam = Boolean.valueOf(
					paramItem.get(FDCConstants.FDC_PARAM_CREATEPARTADEDUCT)
							.toString()).booleanValue();
		}
		fdcBudgetParam = FDCBudgetParam.getInstance(paramItem);

		// HashMap paramMap = FDCUtils.getDefaultFDCParam(null, null);
		checkAllSplit = FDCUtils.getParamValue(paramItem,
				FDCConstants.FDC_PARAM_CHECKALLSPLIT);
		isRealizedZeroCtrl = FDCUtils.getParamValue(paramItem,
				FDCConstants.FDC_PARAM_REALIZEDZEROCTRL);
		// isRealizedZeroCtrl=true;
		if (paramItem.get(FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT) != null) {
			isSeparate = Boolean.valueOf(
					paramItem.get(FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT)
							.toString()).booleanValue();
		}
		if (paramItem.get(FDCConstants.FDC_PARAM_INVOICEREQUIRED) != null) {
			isInvoiceRequired = Boolean.valueOf(
					paramItem.get(FDCConstants.FDC_PARAM_INVOICEREQUIRED)
							.toString()).booleanValue();
		}
		if (paramItem.get(FDCConstants.FDC_PARAM_INVOICEMRG) != null) {
			invoiceMgr = Boolean
					.valueOf(
							paramItem.get(FDCConstants.FDC_PARAM_INVOICEMRG)
									.toString()).booleanValue();
		}
		if (paramItem.get(FDCConstants.FDC_PARAM_OVERRUNCONPRICE) != null) {
			isOverrun = Boolean.valueOf(
					paramItem.get(FDCConstants.FDC_PARAM_OVERRUNCONPRICE)
							.toString()).booleanValue();
		}

		// 合同完工工程量取进度系统工程量填报数据
		if (paramItem.get(FDCConstants.FDC_PARAM_PROJECTFILLBILL) != null) {
			boolean tempBoolean = (Boolean.valueOf(paramItem.get(
					FDCConstants.FDC_PARAM_PROJECTFILLBILL).toString())
					.booleanValue());
			isFromProjectFillBill = tempBoolean && (!isSeparate)
					&& (!isAutoComplete);
		}
		// 因为先在工程量的判断中加入了合同的类型,而且合同的类型高于一切判断所以在这里
		// by tim_gao 2012-03-19
		isWorkLoadContarctType();
		if (paramItem.get(FDCConstants.FDCSCH_PARAM_ISFILLBILLCONTROLSTRICT) != null) {
			boolean tempBoolean = (Boolean.valueOf(paramItem.get(
					FDCConstants.FDCSCH_PARAM_ISFILLBILLCONTROLSTRICT)
					.toString()).booleanValue());
			isFillBillControlStrict = tempBoolean && (!isSeparate)
					&& (!isAutoComplete);
		}
		if (paramItem.get(FDCConstants.FDC_PARAM_SIMPLEINVOICE) != null) {
			isSimpleInvoice = Boolean.valueOf(
					paramItem.get(FDCConstants.FDC_PARAM_SIMPLEINVOICE)
							.toString()).booleanValue();
		}

		/* modified by zhaoqin for BT867964 on 2015/01/19 start */
		if (paramItem.get("FDC325_CONTROLPAYREQUEST") != null) {
			// CONTROLPAYREQUEST =
			// paramItem.get(FDCConstants.FDC_PARAM_SIMPLEINVOICE).toString();
			CONTROLPAYREQUEST = paramItem.get(
					FDCConstants.FDC325_CONTROLPAYREQUEST).toString();
		}
		// CONTROLPAYREQUEST = ParamManager.getParamValue(null, new
		// ObjectUuidPK(org), "FDC325_CONTROLPAYREQUEST");

		if ("0".equals(CONTROLPAYREQUEST)) {
			CONTROLPAYREQUEST = "严格控制";
		} else if ("1".equals(CONTROLPAYREQUEST)) {
			CONTROLPAYREQUEST = "提示控制";
		} else {
			CONTROLPAYREQUEST = "不控制";
		}

		// 转换成布尔对象
		// Boolean flag = FdcBooleanUtil.toBooleanObject(CONTROLPAYREQUEST);
		/*
		 * if (Boolean.FALSE.equals(flag)) { CONTROLPAYREQUEST = "严格控制"; } else
		 * if (Boolean.TRUE.equals(flag)) { CONTROLPAYREQUEST = "提示控制"; } else {
		 * CONTROLPAYREQUEST = "不控制"; }
		 */
		/* modified by zhaoqin for BT867964 on 2015/01/19 end */
	}

	/**
	 * @author RD_haiou_li
	 * @date 2014-09-12
	 * @description:一次性将所需的参数进行选取
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private Map getParamItem() throws BOSException, EASBizException {
		String orgPK = orgUnitInfo == null ? null : orgUnitInfo.getId()
				.toString();
		HashMap hmParamIn = new HashMap();
		if (!StringUtils.isEmpty(orgPK)) {
			IObjectPK comPK = new ObjectUuidPK(orgUnitInfo.getId().toString());
			hmParamIn.put(FDCConstants.FDC_PARAM_STARTMG, comPK);
			// 付款申请单付款金额不允许超过可付款额度
			hmParamIn.put(FDCConstants.FDC_PARAM_OUTPAYAMOUNT, comPK);
			// 申请单进度款付款比例自动为100%
			hmParamIn.put(FDCConstants.FDC_PARAM_PAYPROGRESS, comPK);
			hmParamIn.put(FDCConstants.FDC_PARAM_SELECTPERSON, null);
			hmParamIn.put(FDCConstants.FDC_PARAM_ADVANCEPAYMENTNUMBER, comPK);
			// 未结算合同的实付款大于已实现产值时是否严格控制 集团参数 by jian_wen 2009.12.15
			hmParamIn.put(FDCConstants.FDC_PARAM_ISCONTROLPAYMENT, comPK);
			hmParamIn.put(FDCConstants.FDC_PARAM_MORESETTER, null);

		}

		if (company != null) {
			IObjectPK comPK = new ObjectUuidPK(company.getId().toString());
			hmParamIn.put(FDCConstants.FDC_PARAM_INCORPORATION, comPK);
			hmParamIn.put(FDCConstants.FDC_PARAM_SIMPLEFINACIAL, comPK);
			// 付款申请单收款银行和收款账号为必录项
			hmParamIn.put(FDCConstants.FDC_PARAM_BANKREQURE, comPK);
			// 房地产单据强制不进行进入出纳系统
			hmParamIn.put(FDCConstants.FDC_PARAM_NOTENTERCAS, comPK);
			hmParamIn.put(FDCConstants.FDC_PARAM_CREATEPARTADEDUCT, comPK);
			// 严格控制
			hmParamIn.put(FDCConstants.FDC_PARAM_BUDGET_STRICTCTRL, comPK);
			// 合同计划控制
			hmParamIn.put(FDCConstants.FDC_PARAM_BUDGET_CONTRACTCTRPAY, comPK);
			// 成本科目付款计划控制
			hmParamIn.put(FDCConstants.FDC_PARAM_BUDGET_COSTACCTCTRPAY, comPK);
			// 预算系统控制
			hmParamIn.put(FDCConstants.FDC_PARAM_BUDGET_BGSYSCTRPAY, comPK);
			// 预算系统控制时控制到成本/付款申请金额
			hmParamIn.put(FDCConstants.FDC_PARAM_BUDGET_CTRLCOSTACCOUNT, comPK);
			// 工程量与付款分离
			hmParamIn.put(FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT, comPK);

			// 付款申请单及无文本合同发票号、发票金额必录
			hmParamIn.put(FDCConstants.FDC_PARAM_INVOICEREQUIRED, null);

			hmParamIn.put(FDCConstants.FDC_PARAM_INVOICEMRG, comPK);

			// 是否允许付款申请单累计发票金额大于合同最新造价
			hmParamIn.put(FDCConstants.FDC_PARAM_OVERRUNCONPRICE, comPK);

			// 合同完工工程量取进度系统工程量填报数据
			hmParamIn.put(FDCConstants.FDC_PARAM_PROJECTFILLBILL, null);

			// 进度管理完工工程量的确认是否严格控制
			hmParamIn.put(FDCConstants.FDCSCH_PARAM_ISFILLBILLCONTROLSTRICT,
					comPK);

			hmParamIn.put(FDCConstants.FDC_PARAM_SIMPLEINVOICE, comPK);
		}

		// 合同未拆分，允许录入付款申请单
		hmParamIn.put(FDCConstants.FDC_PARAM_CHECKALLSPLIT, null);

		// 已实现产值为0时的控制
		hmParamIn.put(FDCConstants.FDC_PARAM_REALIZEDZEROCTRL, null);

		String org = SysContext.getSysContext().getCurrentOrgUnit().getId()
				.toString();
		hmParamIn.put("FDC325_CONTROLPAYREQUEST", new ObjectUuidPK(org));

		Map paramItem = FDCUtils.getParamHashMapBatch(null, hmParamIn);
		return paramItem;
	}

	public void onLoad() throws Exception {
		super.onLoad();

		// 初始化预算项目F7
		initPrmtPlanHasCon();

		// 是否来自消息中心
		boolean isFromMsgCenterNoEdit = isFromMsgCenterNoEdit();

		kdDepPlanState.setEnabled(false);
		kdLplanState.setEnabled(false);
		pkbookedDate_dataChanged(null);
		if (getOprtState().equals(OprtState.EDIT)
				|| getOprtState().equals(OprtState.ADDNEW)) {
			try {
				Map param = new HashMap();
				param.put("ContractBillId", contractBill.getId().toString());
				Map totalSettle = ContractFacadeFactory.getRemoteInstance()
						.getTotalSettlePrice(param);
				if (totalSettle != null) {
					editData.setTotalSettlePrice((BigDecimal) totalSettle
							.get("SettlePrice"));
				} else {
					editData.setTotalSettlePrice(FDCConstants.ZERO);
				}
			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			}
			this.txtTotalSettlePrice.setValue(editData.getTotalSettlePrice());
			prmtPlanHasCon.setEnabled(false);
		} else {
			prmtPlanHasCon.setEnabled(false);
			prmtPlanHasCon.setEditable(false);
		}

		if (confirmBillEntry != null && editData != null) {
			editData.put("confirmEntry", confirmBillEntry);
		}

		fillAttachmnetList();

		tableHelper = new PayReqTableHelper(this);
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

			getDetailTable().getScriptManager().setAutoRun(false);
			PayReqUtils.setValueToCell(editData, bindCellMap); // 将单据中的值 填充到表格中

			/* modified by zhaoqin for R140403-0233 on 2014/04/15 start */
			// if (!getOprtState().equals(OprtState.ADDNEW)) { //不是新增
			// tableHelper.updateLstReqAmt(editData, false);
			// }
			/* modified by zhaoqin for R140403-0233 on 2014/04/15 end */
		}

		if (isAdvance()) {
			tableHelper.updateLstAdvanceAmt(editData, false);
			// 公式被吃,重写
			// kdtEntrys.getCell(6, 9).setExpressions("=sum(D6,F6)");
		}

		if (isFirstLoad) {
			isFirstLoad = false;
		}

		if (txtexchangeRate.getNumberValue() == null) {
			txtexchangeRate.setValue(FDCConstants.ONE);
		}

		boolean close = editData.isHasClosed();
		actionClose.setVisible(!close);
		actionClose.setEnabled(!close);
		actionUnClose.setVisible(close);
		actionUnClose.setEnabled(close);

		btnAttachment.setText(getRes("btnAttachment"));

		actionTraceDown.setVisible(true);
		actionTraceDown.setEnabled(true);
		actionTraceUp.setVisible(true);
		actionTraceUp.setEnabled(true);

		actionAuditResult.setVisible(true);
		actionAuditResult.setEnabled(true);

		this.tableHelper.setBeforeAction();

		if (!getOprtState().equals(OprtState.ADDNEW)) {
			tableHelper.reloadGuerdonValue(editData, null);
			tableHelper.reloadCompensationValue(editData, null);
			tableHelper.updateLstReqAmt(editData, true);
		}

		if (isInvoiceRequired) {
			txtInvoiceNumber.setRequired(true);
			txtInvoiceAmt.setRequired(true);
			txtInvoiceOriAmt.setRequired(true);
		}

		initInvoice();
		initInvoiceAndOriAmt();
		calAllCompletePrjAmt();

		// 增加原币金额的可录入范围
		txtAmount.setPrecision(2);
		txtAmount.setMinimumValue(FDCHelper.MIN_VALUE);
		txtAmount.setMaximumValue(FDCHelper.MAX_VALUE.multiply(FDCHelper.TEN));
		// 修改关于本位币金额计算时有错误，只支持汇率小数点后两位
		txtBcAmount.setPrecision(2); // added by Owen_wen 统一改为2位小数，详情请见
										// 提单号R100520-107
		txtBcAmount.setMinimumValue(FDCHelper.MIN_VALUE);
		txtBcAmount
				.setMaximumValue(FDCHelper.MAX_VALUE.multiply(FDCHelper.TEN));

		txtattachment.setNegatived(false);
		txtattachment.setPrecision(0);
		txtattachment.setRemoveingZeroInDispaly(true);
		txtattachment.setRemoveingZeroInEdit(true);

		txtcapitalAmount.setEditable(false);

		txtMoneyDesc.setMaxLength(500);
		txtProcess.setMaxLength(255);

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
		txtcompletePrjAmt.setMinimumValue(FDCHelper.MIN_TOTAL_VALUE);
		if (isAutoComplete) {
			txtcompletePrjAmt.setRequired(false);
			txtpaymentProportion.setEditable(false);
		}

		prmtsupplier.setEditable(false);
		prmtsupplier.setEnabled(false);

		String cuid = this.curProject.getCU().getId().toString();

		if (!isFromMsgCenterNoEdit) {
			FDCClientUtils.initSupplierF7(this, prmtsupplier, cuid);
			FDCClientUtils.initSupplierF7(this, prmtrealSupplier, cuid);
		} else {
			prmtsupplier.setEnabled(false);
			prmtrealSupplier.setEnabled(false);
		}

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

		String cu = null;
		if (editData != null && editData.getCU() != null) {
			cu = editData.getCU().getId().toString();
		} else {
			cu = SysContext.getSysContext().getCurrentCtrlUnit().getId()
					.toString();
		}
		FDCClientUtils.setRespDeptF7(prmtuseDepartment, this,
				canSelectOtherOrgPerson ? null : cu);

		DataChangeEvent e = new DataChangeEvent(pkpayDate, this.editData
				.getPayDate(), null);
		pkpayDate_dataChanged(e);

		if (!fdcBudgetParam.isBgSysCtrl()) {
			actionViewMbgBalance.setVisible(false);
			this.menuItemViewMbgBalance.setVisible(false);
			actionViewMbgBalance.setEnabled(false);
		} else {
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

		if (PayReqUtils.isContractBill(editData.getContractId())
				&& isNotEnterCAS) {
			chkIsPay.setEnabled(false);
			// 添加判断，保证显示正确
			if (OprtState.ADDNEW.equals(getOprtState())) {
				chkIsPay.setSelected(false);
			}
		}

		// 有毛病啊，在onload()调用storeFilds？先注释掉 By Owen_wen 2011-11-04
		// if (getOprtState() != OprtState.VIEW) {
		// this.storeFields();
		// }

		if (contractBill != null
				&& PayReqUtils.isContractBill(editData.getContractId())) {
			isPartACon = this.contractBill.isIsPartAMaterialCon();
		}
		/**
		 * 系统参数设置为真的时候，隐藏进度付款比例和本期完工工程量金额
		 */
		if (fdcBudgetParam.isAcctCtrl() && contractBill != null
				&& contractBill.isIsCoseSplit()) {
			// 关联签定与待签定改进
			actionAssociateAcctPay.setVisible(false);
			actionAssociateAcctPay.setEnabled(false);
			actionAssociateUnSettled.setVisible(false);
			actionAssociateUnSettled.setEnabled(false);
			actionMonthReq.setVisible(true);
			actionMonthReq.setEnabled(true);
		} else {
			actionAssociateAcctPay.setVisible(false);
			actionAssociateAcctPay.setEnabled(false);
			actionAssociateUnSettled.setVisible(false);
			actionAssociateUnSettled.setEnabled(false);
			actionMonthReq.setVisible(false);
			actionMonthReq.setEnabled(false);
		}

		// 业务日期判断为空时取期间中断
		if (pkbookedDate != null && pkbookedDate.isSupportedEmpty()) {
			pkbookedDate.setSupportedEmpty(false);
		}
		this.prmtcurrency.setEditable(false);
		this.prmtcurrency.setEnabled(false);

		Object value = prmtcurrency.getValue();
		if (value instanceof CurrencyInfo) {
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

		this.getDetailTable().setAfterAction(new BeforeActionListener() {
			public void beforeAction(BeforeActionEvent e) {
				if (BeforeActionEvent.ACTION_DELETE == e.getType()) {
					BigDecimal oriAmt = FDCHelper.toBigDecimal(getDetailTable()
							.getCell(rowIndex, columnIndex).getValue());
					if (FDCHelper.ZERO.compareTo(oriAmt) == 0) {
						getDetailTable().getCell(rowIndex, columnIndex + 1)
								.setValue(null); // 本次申请合同内工程款本币
					}
					oriAmt = FDCHelper.toBigDecimal(getDetailTable().getCell(
							rowIndex + 1, columnIndex).getValue());
					if (FDCHelper.ZERO.compareTo(oriAmt) == 0) {
						getDetailTable().getCell(rowIndex + 1, columnIndex + 1)
								.setValue(null); // 本次申请预付款本币
					}
				}
			}

		});

		this.actionViewMaterialConfirm.setVisible(true);

		if (FDCHelper.ZERO.compareTo(this.confirmAmts) != 0) {
			this.setConfirmBillEntryAndPrjAmt();
		}

		updateCompletePrjAmt();

		// 付款单为暂估款类型时其他可录入金额字段仅发票金额可录入(对应的合同内工程款等其他款项不可录入)
		Object obj = prmtPayment.getValue();
		if (obj != null && obj instanceof PaymentTypeInfo) {
			String tempID = PaymentTypeInfo.tempID;// 暂估款
			PaymentTypeInfo type = (PaymentTypeInfo) obj;
			if (type.getPayType().getId().toString().equals(tempID)) {
				this.kdtEntrys.getStyleAttributes().setLocked(true);
				if (this.kdtEntrys.getCell(rowIndex, columnIndex) != null) {
					this.kdtEntrys.getCell(rowIndex, columnIndex)
							.getStyleAttributes().setLocked(true);
				}
			}
		}

		// 本申请单累计实付款（本币）实时取值
		/* modified by zhaoqin for R140227-0281 on 2014/03/21 start */
		/*
		 * if(kdtEntrys.getCell(2, 10)!= null){ if(editData.getId() != null){
		 * totalPayAmtByReqId =
		 * getTotalPayAmtByThisReq(editData.getId().toString());
		 * totalPayAmtByReqId = (BigDecimal)totalPayMap.get("totalPayedAmt"); }
		 * BigDecimal exchangeRate = (BigDecimal)
		 * this.txtexchangeRate.getValue(BigDecimal.class); kdtEntrys.getCell(2,
		 * 7).setValue(FDCHelper.divide(totalPayAmtByReqId, exchangeRate));
		 * kdtEntrys.getCell(2, 10).setValue(totalPayAmtByReqId);
		 * 
		 * if (STATUS_VIEW == this.getOprtState()) { kdtEntrys.getCell(5,
		 * 11).setValue(FDCHelper.add(totalPayAmtByReqId, kdtEntrys.getCell(5,
		 * 11).getValue())); kdtEntrys.getCell(5,
		 * 10).setValue(FDCHelper.multiply(kdtEntrys.getCell(5, 11).getValue(),
		 * exchangeRate)); } }
		 */
		/* modified by zhaoqin for R140227-0281 on 2014/03/21 end */

		// 合同修订和变更都需要将金额同步反映到单据状态为保存或者是提交的付款申请单中的变更指令金额中去 by cassiel 2010-08-06
		if (!FDCBillStateEnum.AUDITTED.equals(this.editData.getState())) {
			if (PayReqUtils.isContractBill(editData.getContractId())) {
				tableHelper.updateDynamicValue(editData, contractBill,
						contractChangeBillCollection, paymentBillCollection);
			}
		} else {// PBG095801..审批的单据，合同付款次数也应更新过来。。。
			if (PayReqUtils.isContractBill(editData.getContractId())) {
				// this.editData.setPayTimes(paymentBillCollection.size());
				// ((ICell)
				// bindCellMap.get(PayRequestBillContants.PAYTIMES)).setValue
				// (String.valueOf(paymentBillCollection.size()));
			}
		}

		reloadPartADeductDetails();

		/* modified by zhaoqin for R140115-0172/R140118-0018 on 2014/01/23 */
		if (getOprtState().equals(OprtState.ADDNEW)
				|| getOprtState().equals(OprtState.EDIT)) {
			addOrgPriceForEntryTable(kdtEntrys, bindCellMap, contractBill);
		} else {
			setOrgAmountForEntry(kdtEntrys);
		}

		ExtendParser parserAccountFrom = new ExtendParser(txtrecAccount);
		txtrecAccount.setCommitParser(parserAccountFrom);
		txtrecAccount.setMaxLength(80);

		initPrmtPlanUnCon();

		if (getOprtState().equals(OprtState.VIEW)) {
			this.kdtEntrys.getCell(rowIndex, columnIndex).getStyleAttributes()
					.setBackground(noEditColor);

			/* modified by zhaoqin for R140417-0212 on 2014/04/24 */
			if (editData.getState() == FDCBillStateEnum.SAVED
					|| editData.getState() == FDCBillStateEnum.SUBMITTED)
				getContractLastAmt();
		}

		/**
		 * 重新显示付款申请单截至上次累计实付 STATUS_FINDVIEW合同成本信息里打开时设置了这个状态，所以这个状态也重新加载
		 * 详见PayRequestFullInfoUI 中的tblMain_tableClicked
		 */
		if (STATUS_VIEW == this.getOprtState()
				|| STATUS_EDIT == this.getOprtState()
				|| STATUS_FINDVIEW == this.getOprtState()) {
			// 处在工作流审批过程中，也不重新加载
			if (!isFromMsgCenterNoEdit) {// check this..工作流看到的单据非实时数据了..
				setLstPriRaiedORPaied();
			}
		}

		/* modified by zhaoqin for R140227-0281 on 2014/03/21 */
		setTotalPayForReqPay();

		/**
		 * 将公式拿到这里来设置，可以解决公式被重复填写 出现被覆盖 导致数据不正确的问题
		 */
		tableHelper.calcTable();
		getDetailTable().getScriptManager().setAutoRun(true);
		kdtEntrys.getScriptManager().runAll();
		// by tim_gao 有关于工程量控制的初始化
		initParamOnLoadForWorkLoad();

		// 前一后一第一最后禁用吧，要不然一堆的问题
		// actionFirst.setVisible(false);
		// actionLast.setVisible(false);
		// actionPre.setVisible(false);
		// actionNext.setVisible(false);

		// 付款申请单“上查”合同付款计划的功能暂时屏蔽了
		actionTraceUp.setVisible(false);

		this.paymentProportionValue = this.editData.getPaymentProportion();
		this.completePrjAmtValue = this.editData.getCompletePrjAmt();
		setPaymentProprotionAndCompletePrjAmtEnabled();
		if (editData.getPaymentType() != null) {
			setTxtEnable(editData.getPaymentType());
		}
		if (prmtPlanUnCon.getValue() == null
				&& prmtPlanHasCon.getValue() == null) {
			if (editData.getContractId() != null) {
				ContractBillInfo contractInfo = new ContractBillInfo();
				if (contractInfo.getBOSType().equals(
						BOSUuid
								.getBOSObjectType(editData.getContractId(),
										true))) {
					kdDepPlanState.setSelectedItem(DepPlanStateEnum.noPlan);
				} else {
					kdDepPlanState.setSelectedItem(null);
				}
			} else {
				kdDepPlanState.setSelectedItem(DepPlanStateEnum.noPlan);

			}
		} else {
			if (OprtState.EDIT.equals(getOprtState())) {
				setDepPlanState();
			}
		}
		if ("不控制".equals(CONTROLPAYREQUEST)) {
			kdLplanState.setVisible(false);
		}
		this.setKdDepPlanStateValue();

		// 取得每行的单元格值Map
		Map rowValuesMap = FDCTableHelper.getRowValuesMap(this.getDetailTable());
		MapUtils.debugPrint(System.out, "每行的单元格值Map", rowValuesMap);

		// 打印绑定单元格Map
		printBindCellMap();
		//合并13年开发的内容，施工类型合同增加三材金额字段
		if(orgUnitInfo != null){
            Map param = null;
            param = FDCUtils.getDefaultFDCParam(null, orgUnitInfo.getId().toString());
            isMbgCtrl = Boolean.valueOf(param.get("FDC001_STARTMG").toString()).booleanValue();
            isAutoComplete = Boolean.valueOf(param.get("FDC072_PAYPROGRESS").toString()).booleanValue();
        }
        Map paramItem = null;
        paramItem = FDCUtils.getDefaultFDCParam(null, company.getId().toString());
        if(paramItem.get("FDC003_INCORPORATION") != null)
            isIncorporation = Boolean.valueOf(paramItem.get("FDC003_INCORPORATION").toString()).booleanValue();
        if(paramItem.get("FDC003_INCORPORATION") != null)
            isIncorporation = Boolean.valueOf(paramItem.get("FDC003_INCORPORATION").toString()).booleanValue();
        if(paramItem.get("FDC025_SIMPLEFINACIAL") != null)
            isSimpleFinancial = Boolean.valueOf(paramItem.get("FDC025_SIMPLEFINACIAL").toString()).booleanValue();
        if(paramItem.get("CS050") != null)
            usageLegth = Integer.valueOf(paramItem.get("CS050").toString()).intValue();
        if(paramItem.get("FDC303_NOTENTERCAS") != null)
            isNotEnterCAS = Boolean.valueOf(paramItem.get("FDC303_NOTENTERCAS").toString()).booleanValue();
        fdcBudgetParam = com.kingdee.eas.fdc.finance.FDCBudgetCtrlStrategy.FDCBudgetParam.getInstance(paramItem);
        HashMap paramMap = FDCUtils.getDefaultFDCParam(null, null);
        isRealizedZeroCtrl = FDCUtils.getParamValue(paramMap, "FDC315_REALIZEDZEROCTRL");
        if(paramItem.get("FDC317_SEPARATEFROMPAYMENT") != null)
            isSeparate = Boolean.valueOf(paramItem.get("FDC317_SEPARATEFROMPAYMENT").toString()).booleanValue();
        if(paramItem.get("FDC068_FROMPROJECTFILL") != null){
            boolean tempBoolean = Boolean.valueOf(paramItem.get("FDC068_FROMPROJECTFILL").toString()).booleanValue();
            isFromProjectFillBill = tempBoolean && !isSeparate && !isAutoComplete;
        }
        isWorkLoadContarctType();
        if("0".equals(CONTROLPAYREQUEST))
            CONTROLPAYREQUEST = "\u4E25\u683C\u63A7\u5236";
        else if("1".equals(CONTROLPAYREQUEST))
            CONTROLPAYREQUEST = "\u63D0\u793A\u63A7\u5236";
        else
            CONTROLPAYREQUEST = "\u4E0D\u63A7\u5236";
		
		completePrjAmtValue = editData.getCompletePrjAmt();
		paymentProportionValue = editData.getPaymentProportion();
		ContractTypeInfo typeInfo=ContractTypeFactory.getRemoteInstance().getContractTypeInfo(new ObjectUuidPK(contractBill.getContractType().getId()));
		if(typeInfo != null && "[施工]".equals(typeInfo.getName()))
			isShiGongContract = true;
	}

	/**
	 * 描述：初始化预算项目F7
	 * 
	 * @author RD_skyiter_wang
	 * @createDate 2014-3-17
	 */
	private void initPrmtPlanHasCon() {
		try {
			if (checkCtrlParam("FDC325_CONTROLPAYREQUEST")) {
				this.btnViewBudget.setText("查看计划");
				this.ctrl = true;
			} else {
				this.contPlanHasCon.setBoundLabelText("预算项目");
				this.contPlanUnCon.setBoundLabelText("预算项目");
				IBudgetCtrlFacade iCtrl = BudgetCtrlUtil
						.getBudgetCtrlFacadeImpl(null);

				BgCtrlResultCollection ctrlResultCol = iCtrl
						.getBudget(this.editData);
				if (ctrlResultCol.size() > 0) {
					String name = ctrlResultCol.get(0).getItemCombinName();
					this.prmtPlanHasCon.setText(name);
				}
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 描述：打印绑定单元格Map
	 * 
	 * @author skyiter_wang
	 * @createDate 2014-1-2
	 */
	private void printBindCellMap() {
		ICell cell;
		String cellIndexStr;
		Object cellValue;
		Set set = bindCellMap.keySet();

		logger.info("======================================================");
		logger.info("打印绑定单元格Map，start");
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			String cellKey = (String) iterator.next();
			cell = (ICell) bindCellMap.get(cellKey);
			int i = cell.getRowIndex();
			int j = cell.getColumnIndex();
			cellValue = cell.getValue();

			cellIndexStr = "[" + i + "," + j + "]";

			logger.info(cellKey + "<--->" + cellIndexStr + ":" + cellValue);
		}
		logger.info("打印绑定单元格Map，end");
		logger.info("======================================================");
	}

	/**
	 * 描述：是否来自消息中心，并且不编辑
	 * 
	 * @see VoucherEditUI.refreshUITitle
	 * @return
	 * @Author：skyiter_wang
	 * @CreateTime：2013-9-6
	 */
	 
	private boolean isFromMsgCenterNoEdit() {
		boolean isFromMsgCenterNoEdit = false;

		Map uiContext = getUIContext();
		boolean isFromWorkflow = false;
		if (uiContext != null) {
			isFromWorkflow = Boolean.TRUE.equals(uiContext
					.get("isFromWorkflow"));
		}

		FDCBillStateEnum state = this.editData.getState();
		boolean flag = FDCBillStateEnum.SAVED.equals(state)
				|| FDCBillStateEnum.SUBMITTED.equals(state);
		isFromMsgCenterNoEdit = isFromWorkflow && !flag;

		logger.info("======================================================");
		logger.info("PayRequestBillEditUI.isFromWorkflow:" + isFromWorkflow);
		logger.info("PayRequestBillEditUI.editData.getState():" + state);
		logger.info("PayRequestBillEditUI.isFromMsgCenterNoEdit:"
				+ isFromMsgCenterNoEdit);
		logger.info("PayRequestBillEditUI.getOprtState():"
				+ this.getOprtState());
		logger.info("======================================================");

		return isFromMsgCenterNoEdit;
	}

	/**
	 * 描述：
	 * 
	 * @param table
	 * @Author：keyan_zhao
	 * @CreateTime：2012-12-10
	 */
	private void setOrgAmountForEntry(KDTable table) {
		if (contractBill == null)
			return;
		// 汇率
		BigDecimal rate = contractBill.getExRate();
		/**
		 * 最新造价原币
		 */
		BigDecimal tmp = FDCHelper.ZERO;
		tmp = editData.getLatestPrice();
		if (null == tmp) {
			((ICell) bindCellMap.get(PayRequestBillContants.LATESTORGPRICE))
					.setValue(FDCHelper.ZERO);
			((ICell) bindCellMap.get(PayRequestBillContants.LATESTPRICE))
					.setValue(FDCHelper.ZERO);
		} else {
			((ICell) bindCellMap.get(PayRequestBillContants.LATESTORGPRICE))
					.setValue(FDCHelper.divide(tmp, rate, 2,
							BigDecimal.ROUND_HALF_UP)); // 原币=本币/汇率
			((ICell) bindCellMap.get(PayRequestBillContants.LATESTPRICE))
					.setValue(tmp);
		}

		/**
		 *合同原币
		 */
		tmp = FDCHelper.ZERO;
		tmp = editData.getContractPrice();
		if (null == tmp) {
			((ICell) bindCellMap.get(PayRequestBillContants.CONTRACTORGPRICE))
					.setValue(FDCHelper.ZERO);
		} else {
			((ICell) bindCellMap.get(PayRequestBillContants.CONTRACTORGPRICE))
					.setValue(FDCHelper.divide(tmp, rate, 2,
							BigDecimal.ROUND_HALF_UP)); // 原币=本币/汇率
		}

		/**
		 *结算金额
		 */
		tmp = FDCHelper.ZERO;
		tmp = editData.getSettleAmt();
		if (null == tmp) {
			((ICell) bindCellMap.get(PayRequestBillContants.SETTLEORGAMT))
					.setValue(FDCHelper.ZERO);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			((ICell) bindCellMap.get(PayRequestBillContants.SETTLEORGAMT))
					.setValue(FDCHelper.divide(tmp, rate)); // 原币=本币/汇率
		}

		/**
		 *变更金额
		 */
		tmp = FDCHelper.ZERO;
		tmp = editData.getChangeAmt();
		if (null == tmp) {
			((ICell) bindCellMap.get(PayRequestBillContants.CHANGEORGAMT))
					.setValue(FDCHelper.ZERO);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			((ICell) bindCellMap.get(PayRequestBillContants.CHANGEORGAMT))
					.setValue(FDCHelper.divide(tmp, rate)); // 原币=本币/汇率
		}

		/**
		 * 合同内工程款
		 */
		tmp = FDCHelper.ZERO;
		tmp = (BigDecimal) table.getCell(5, 3).getValue();
		if (null == tmp) {
			table.getCell(5, 2).setValue(FDCHelper.ZERO);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			table.getCell(5, 2).setValue(FDCHelper.divide(tmp, rate)); //原币=本币/汇率
		}

		/* modified by zhaoqin for R140227-0281 on 2014/03/21 start */
		/*
		 * tmp = (BigDecimal) table.getCell(5, 5).getValue(); if (null == tmp ||
		 * FDCHelper.toBigDecimal(tmp, 2).compareTo(FDCHelper.ZERO) == 0) {
		 * table.getCell(5, 4).setValue(FDCHelper.ZERO); } else { tmp =
		 * FDCHelper.toBigDecimal(tmp, 2); table.getCell(5,
		 * 4).setValue(FDCHelper.divide(tmp, rate)); //原币=本币/汇率 }
		 */
		/* modified by zhaoqin for R140227-0281 on 2014/03/21 end */

		/**
		 * 预付款
		 */
		tmp = (BigDecimal) table.getCell(6, 3).getValue();
		if (null == tmp
				|| FDCHelper.toBigDecimal(tmp, 2).compareTo(FDCHelper.ZERO) == 0) {
			table.getCell(6, 2).setValue(null);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			table.getCell(6, 2).setValue(FDCHelper.divide(tmp, rate)); //原币=本币/汇率
		}

		tmp = (BigDecimal) table.getCell(6, 5).getValue();
		if (null == tmp
				|| FDCHelper.toBigDecimal(tmp, 2).compareTo(FDCHelper.ZERO) == 0) {
			table.getCell(6, 4).setValue(null);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			table.getCell(6, 4).setValue(FDCHelper.divide(tmp, rate)); //原币=本币/汇率
		}

		/**
		 * 奖励
		 */
		tmp = (BigDecimal) table.getCell(8, 3).getValue();
		if (null == tmp
				|| FDCHelper.toBigDecimal(tmp, 2).compareTo(FDCHelper.ZERO) == 0) {
			table.getCell(8, 2).setValue(null);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			table.getCell(8, 2).setValue(FDCHelper.divide(tmp, rate)); //原币=本币/汇率
		}

		tmp = (BigDecimal) table.getCell(8, 5).getValue();
		if (null == tmp
				|| FDCHelper.toBigDecimal(tmp, 2).compareTo(FDCHelper.ZERO) == 0) {
			table.getCell(8, 4).setValue(null);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			table.getCell(8, 4).setValue(FDCHelper.divide(tmp, rate)); //原币=本币/汇率
		}

		/**
		 * 违约金
		 */
		tmp = (BigDecimal) table.getCell(9, 3).getValue();
		if (null == tmp
				|| FDCHelper.toBigDecimal(tmp, 2).compareTo(FDCHelper.ZERO) == 0) {
			table.getCell(9, 2).setValue(null);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			table.getCell(9, 2).setValue(FDCHelper.divide(tmp, rate)); //原币=本币/汇率
		}

		tmp = (BigDecimal) table.getCell(9, 5).getValue();
		if (null == tmp
				|| FDCHelper.toBigDecimal(tmp, 2).compareTo(FDCHelper.ZERO) == 0) {
			table.getCell(9, 4).setValue(null);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			table.getCell(9, 4).setValue(FDCHelper.divide(tmp, rate)); //原币=本币/汇率
		}

		int rowCount = kdtEntrys.getRowCount();
		int aMartIndex = rowCount - 6;
		/**
		 * 应扣甲供材
		 */
		tmp = (BigDecimal) table.getCell(aMartIndex, 3).getValue();
		if (null == tmp
				|| FDCHelper.toBigDecimal(tmp, 2).compareTo(FDCHelper.ZERO) == 0) {
			table.getCell(aMartIndex, 2).setValue(FDCHelper.ZERO);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			table.getCell(aMartIndex, 2).setValue(FDCHelper.divide(tmp, rate)); // 原币
																				// =
																				// 本币
																				// /
																				// 汇率
		}

		tmp = (BigDecimal) table.getCell(aMartIndex, 5).getValue();
		if (null == tmp
				|| FDCHelper.toBigDecimal(tmp, 2).compareTo(FDCHelper.ZERO) == 0) {
			table.getCell(aMartIndex, 4).setValue(FDCHelper.ZERO);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			table.getCell(aMartIndex, 4).setValue(FDCHelper.divide(tmp, rate)); // 原币
																				// =
																				// 本币
																				// /
																				// 汇率
		}
	}

	/**
	 * 描述：重新显示付款申请单截至上次累计实付
	 * 
	 * @param reqPayId
	 * @throws BOSException
	 * @throws SQLException
	 * @Author：keyan_zhao
	 * @CreateTime：2012-11-21
	 */
	private void setLstPriRaiedORPaied() throws BOSException, SQLException {
		BigDecimal beforeTotalPayAmtByThisReq = FDCHelper.ZERO;
		BigDecimal beforeTotalLocalPayAmtByThisReq = FDCHelper.ZERO;
		if (editData.getContractId() != null) {
			// FDCSQLBuilder builder = new FDCSQLBuilder();
			// builder.appendSql(
			// "select sum(pay.famount),sum(pay.flocalamount) from T_CAS_PaymentBill pay "
			// );
			// builder.appendSql(
			// "left join T_CON_PayRequestBill req on req.fid=pay.ffdcPayReqID "
			// );
			// builder.appendSql(
			// "where pay.fcontractBillId = ?  and pay.fbillstatus =15 ");
			// builder.addParam(editData.getContractId());
			// if (editData.getId() == null) {
			// builder.appendSql(" and req.fbookeddate < {ts '" +
			// pkbookedDate.getTimestamp().toLocaleString() + "'}");
			// // builder.addParam(pkbookedDate.getTimestamp());
			// } else {
			// FDCSQLBuilder bookDate = new FDCSQLBuilder();
			// bookDate.appendSql(
			// "select fbookeddate from T_CON_PayRequestBill where fid = ? ");
			// bookDate.addParam(editData.getId().toString());
			// IRowSet rowSet = bookDate.executeQuery();
			// while (rowSet.next()) {
			// // builder.addParam(rowSet.getTimestamp("fbookeddate"));
			// builder.appendSql(" and req.fbookeddate < {ts '" +
			// rowSet.getTimestamp("fbookeddate").toLocaleString() + "'}");
			// }
			// }

			////////////////////////////////////////////////////////////////////
			// ////////////
			////////////////////////////////////////////////////////////////////
			// ////////////

			// 生产上次累计实付取值SQL
			String sql = genenrateLstPriRaiedORPaiedSql();
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql(sql);

			////////////////////////////////////////////////////////////////////
			// ////////////
			////////////////////////////////////////////////////////////////////
			// ////////////

			RowSet rs;
			try {
				rs = builder.executeQuery();
				while (rs.next()) {
					beforeTotalPayAmtByThisReq = FDCHelper.add(
							beforeTotalPayAmtByThisReq, rs.getBigDecimal(1));
					beforeTotalLocalPayAmtByThisReq = FDCHelper.add(
							beforeTotalLocalPayAmtByThisReq, rs
									.getBigDecimal(2));
				}
			} catch (BOSException e1) {
				handUIExceptionAndAbort(e1);
			} catch (SQLException e) {
				handUIExceptionAndAbort(e);
			}
		}

		logger.info("======================================================");
		logger
				.info("PayRequestBillEditUI.setLstPriRaiedORPaied，重新显示付款申请单截至上次累计实付");
		logger.info("beforeTotalPayAmtByThisReq，old："
				+ kdtEntrys.getCell(5, 2).getValue());
		logger.info("beforeTotalLocalPayAmtByThisReq，old："
				+ kdtEntrys.getCell(5, 3).getValue());
		logger.info("beforeTotalPayAmtByThisReq，new："
				+ beforeTotalPayAmtByThisReq);
		logger.info("beforeTotalLocalPayAmtByThisReq，new："
				+ beforeTotalLocalPayAmtByThisReq);
		logger.info("======================================================");

		if (FDCHelper.compareTo(beforeTotalPayAmtByThisReq, FDCHelper.ZERO) != 0
				&& FDCHelper.compareTo(beforeTotalLocalPayAmtByThisReq,
						FDCHelper.ZERO) != 0) {
			kdtEntrys.getCell(5, 2).setValue(beforeTotalPayAmtByThisReq);
			kdtEntrys.getCell(5, 3).setValue(beforeTotalLocalPayAmtByThisReq);
		}

	}

	/**
	 * 描述：生产上次累计实付取值SQL <br/> 业务逻辑参照自PaymentBillEditUI.setLstPriRaiedORPaied by
	 * skyiter_wang 2013-08-14
	 * 
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 * @Author：skyiter_wang
	 * @CreateTime：2013-8-14
	 */
	private String genenrateLstPriRaiedORPaiedSql() throws BOSException,
			SQLException {
		// 截至上次累计实付取值规则：
		// ‘以往付款申请单’所有已付款的付下款单的金额 + '本付款申请单'下创建日期早于'本付款单创建日期'的所有已付款的付款单金额
		// 依然存在的问题：同一天的付款申请单下的数据可能都会被包含，导致数据变大---由于付款申请单业务日期可能只记录了精确到日的日期数据
		// 业务逻辑参照自PaymentBillEditUI.setLstPriRaiedORPaied by skyiter_wang
		// 2013-08-14
		// 和王亮沟通 by skyiter_wang 2013-08-14

		// 取得合同ID
		String contractId = editData.getContractId();
		// 付款申请单ID
		String ffdcPayReqID = editData.getId().toString();

		// 取得业务日期
		Date booDate = null;
		String booDateString = null;
		if (editData.getId() == null) {
			booDate = pkbookedDate.getTimestamp();
		} else {
			FDCSQLBuilder bookDate = new FDCSQLBuilder();
			bookDate
					.appendSql("select fbookeddate from T_CON_PayRequestBill where fid = ? ");
			bookDate.addParam(editData.getId().toString());
			IRowSet rowSet = bookDate.executeQuery();
			while (rowSet.next()) {
				booDate = rowSet.getTimestamp("fbookeddate");
			}
		}
		booDateString = DateFormatUtils.format(booDate, "yyyy-MM-dd");

		// 取得创建日期
		Timestamp createTime = new Timestamp(editData.getCreateTime().getTime());
		String createTimeStr = createTime.toLocaleString();

		StringBuffer sb = new StringBuffer();

		// sb.append(
		// "select sum(famount) as totalAmount, sum(flocalamount) as totalLocalAmount from ("
		// ).append(
		// " select pay.famount, pay.flocalamount from T_CAS_PaymentBill pay").
		// append(
		// " left join T_CON_PayRequestBill req on req.fid=pay.ffdcPayReqID").
		// append(" where pay.fcontractBillId = '").append(
		// editData.getContractId()).append("'").append(
		// " and pay.fbillstatus =15  and req.fbookeddate < {ts '"
		// ).append(booDateString)
		// .append("'}").append(" union").append(
		// " select pay.famount, pay.flocalamount from T_CAS_PaymentBill pay"
		// ).append(
		// " left join T_CON_PayRequestBill req on req.fid=pay.ffdcPayReqID").
		// append(" where pay.fcontractBillId = '").append(
		// editData.getContractId()).append("'").append(
		// " and pay.fbillstatus =15  and req.fbookeddate = {ts '").append(
		//booDateString).append("'}").append(" and pay.fid !='").append(editData
		// .getId().toString()).append("'").append(
		// " and req.FcreateTime < {ts '").append(createTime.toLocaleString()).
		// append("'}) as temp_table");

		/* modified by zhaoqin for R140227-0281 on 2014/03/21 start */
		// sb.append(
		// "  SELECT SUM(famount) AS totalAmount, SUM(flocalamount) AS totalLocalAmount  		\n"
		// );
		sb
				.append("  SELECT SUM(faddProjectAmt) AS totalAmount, SUM(fprojectPriceInContract) AS totalLocalAmount  		\n");
		// sb.append("    FROM (SELECT pay.famount, pay.flocalamount  		\n");
		sb
				.append("    FROM (SELECT pay.faddProjectAmt, pay.fprojectPriceInContract  		\n");
		sb.append("            FROM T_CAS_PaymentBill pay  		\n");
		sb.append("            LEFT JOIN T_CON_PayRequestBill req  		\n");
		sb.append("              ON req.fid = pay.ffdcPayReqID  		\n");
		sb.append("           WHERE pay.fcontractBillId = '{0}'  		\n");
		sb.append("             AND pay.fbillstatus = 15  		\n");
		sb
				.append("             AND TO_CHAR(req.fbookeddate, 'yyyy-MM-dd') < '{1}' 		\n");
		sb.append("          UNION  ALL		\n");
		// sb.append("          SELECT pay.famount, pay.flocalamount  		\n");
		sb
				.append("          SELECT pay.faddProjectAmt, pay.fprojectPriceInContract  		\n");
		/* modified by zhaoqin for R140227-0281 on 2014/03/21 end */

		sb.append("            FROM T_CAS_PaymentBill pay  		\n");
		sb.append("            LEFT JOIN T_CON_PayRequestBill req  		\n");
		sb.append("              ON req.fid = pay.ffdcPayReqID  		\n");
		sb.append("           WHERE pay.fcontractBillId = '{0}'  		\n");
		sb.append("             AND pay.fbillstatus = 15  		\n");
		sb
				.append("             AND TO_CHAR(req.fbookeddate, 'yyyy-MM-dd') = '{1}'  		\n");
		sb.append("             AND req.fid != '{4}'  		\n");
		// 注意：“付款申请单”上使用的是T_CON_PayRequestBill的制单日期，“付款单”
		// 上使用的是T_CAS_PaymentBill的制单日期
		sb
				.append("             AND req.FcreateTime < {ts '{2}' }) AS temp_table  		\n");

		String sqlStr = sb.toString();
		sqlStr = sqlStr.replaceAll("\\{0\\}", contractId);
		sqlStr = sqlStr.replaceAll("\\{1\\}", booDateString);
		sqlStr = sqlStr.replaceAll("\\{2\\}", createTimeStr);
		sqlStr = sqlStr.replaceAll("\\{4\\}", ffdcPayReqID);

		logger.info("======================================================");
		logger
				.info("PayRequestBillEditUI.genenrateLstPriRaiedORPaiedSql，重新显示付款申请单截至上次累计实付SQL");
		logger.info("sql：" + sqlStr);
		logger.info("======================================================");

		return sqlStr;
	}

	/**
	 * 描述：如果是编辑状态或者是查看状态，并且付款类型是YFK,就把“进度款付款比例”和“本期完工工程量“灰显（BT698646）
	 * 
	 * @Author：jian_cao
	 * @CreateTime：2012-8-9
	 */
	 
	private void setPaymentProprotionAndCompletePrjAmtEnabled() {

		if (OprtState.EDIT.equals(getOprtState())
				|| OprtState.VIEW.equals(getOprtState())) {
			if (null != this.editData.getPaymentType()
					&& YFK.equals(this.editData.getPaymentType().toString())) {
				detachListeners();
				this.txtpaymentProportion.setValue(FDCHelper.ZERO);
				this.txtpaymentProportion.setRequired(false);
				this.txtpaymentProportion.setEnabled(false);
				this.txtcompletePrjAmt.setValue(FDCHelper.ZERO);
				this.txtcompletePrjAmt.setRequired(false);
				this.txtcompletePrjAmt.setEnabled(false);
				attachListeners();
			}
		}
	}

	// 抽取出来以便后期使用。。ken_liu
	BigDecimal oriCurrency = FDCHelper.ZERO; // 最新造价原币别
	BigDecimal localCurrency = FDCHelper.ZERO; // 最新造价本币

	/**
	 * 合同最新造价 - R140417-0212
	 * 
	 * @author RD_zhaoqin
	 * @date 2014/04/24
	 */
	private void getContractLastAmt() {
		String contractBillId = null;
		if (null != contractBill && null != contractBill.getId()) {
			contractBillId = contractBill.getId().toString();
		} else if (null != editData && null != editData.getContractId()) {
			contractBillId = editData.getContractId();
		} else {
			return;
		}

		// 最新造价原币
		try {
			oriCurrency = (BigDecimal) FDCUtils.getLastOriginalAmt_Batch(null,
					new String[] { contractBillId }).get(contractBillId);
			localCurrency = (BigDecimal) FDCUtils.getLastAmt_Batch(null,
					new String[] { contractBillId }).get(contractBillId);
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
	}

	/**
	 * description 添加分录原币
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-6
	 *             <p>
	 * @param table
	 *            分录表
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
	private void addOrgPriceForEntryTable(KDTable table, HashMap bindCellMap,
			ContractBillInfo contractBill) {
		if (null == contractBill) {
			return;
		}

		String contractBillId = contractBill.getId().toString();
		// 合同最新造价原币
		if (contractBillId != null && contractBillId.trim().length() > 1) {
			// BigDecimal oriAmount = FDCHelper.ZERO;
			// BigDecimal amount = FDCHelper.ZERO;
			try {
				oriCurrency = (BigDecimal) FDCUtils.getLastOriginalAmt_Batch(
						null, new String[] { contractBillId }).get(
						contractBillId); // 最新造价原币
				localCurrency = (BigDecimal) FDCUtils.getLastAmt_Batch(null,
						new String[] { contractBillId }).get(contractBillId);
			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			}

			if (FDCHelper.toBigDecimal(oriCurrency, 2)
					.compareTo(FDCHelper.ZERO) == 0) {
				((ICell) bindCellMap.get(PayRequestBillContants.LATESTORGPRICE))
						.setValue(FDCHelper.ZERO);
			} else {
				((ICell) bindCellMap.get(PayRequestBillContants.LATESTORGPRICE))
						.setValue(oriCurrency);
			}
			if (FDCHelper.toBigDecimal(localCurrency, 2).compareTo(
					FDCHelper.ZERO) == 0) {
				((ICell) bindCellMap.get(PayRequestBillContants.LATESTPRICE))
						.setValue(FDCHelper.ZERO);
			} else {
				((ICell) bindCellMap.get(PayRequestBillContants.LATESTPRICE))
						.setValue(localCurrency);
			}
		}

		// 合同造价原币
		if (null != contractBill && null != contractBill.getOriginalAmount()) {
			BigDecimal orgAmount = contractBill.getOriginalAmount();
			if (FDCHelper.toBigDecimal(orgAmount, 2).compareTo(FDCHelper.ZERO) == 0) {
				((ICell) bindCellMap
						.get(PayRequestBillContants.CONTRACTORGPRICE))
						.setValue(FDCHelper.ZERO);
			} else {
				((ICell) bindCellMap
						.get(PayRequestBillContants.CONTRACTORGPRICE))
						.setValue(orgAmount);
			}
		}

		// 合同结算金额原币
		HashMap map = getContractAttset(contractBillId);
		BigDecimal amt = (BigDecimal) map.get("amt");
		BigDecimal orgAmt = (BigDecimal) map.get("orgAmt");
		if (FDCHelper.toBigDecimal(amt, 2).compareTo(FDCHelper.ZERO) == 0) { // 本币
			((ICell) bindCellMap.get(PayRequestBillContants.SETTLEAMT))
					.setValue(FDCHelper.ZERO);
		} else {
			((ICell) bindCellMap.get(PayRequestBillContants.SETTLEAMT))
					.setValue(amt);
		}
		if (FDCHelper.toBigDecimal(orgAmt, 2).compareTo(FDCHelper.ZERO) == 0) { // 原币
			((ICell) bindCellMap.get(PayRequestBillContants.SETTLEORGAMT))
					.setValue(FDCHelper.ZERO);
		} else {
			((ICell) bindCellMap.get(PayRequestBillContants.SETTLEORGAMT))
					.setValue(orgAmt);
		}
		// 变更签证金额
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("contractBill.id", contractBillId));
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.VISA_VALUE));
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.ANNOUNCE_VALUE));
		filter.setMaskString("#0 and (#1 or #2 or #3)");
		view = new EntityViewInfo();
		view.setFilter(filter);
		view.getSelector().add("amount");
		view.getSelector().add("originalAmount");
		view.getSelector().add("hasSettled");
		view.getSelector().add("oriBalanceAmount");
		view.getSelector().add("balanceAmount");
		ContractChangeBillCollection collection = null;
		try {
			collection = ContractChangeBillFactory.getRemoteInstance()
					.getContractChangeBillCollection(view);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
		BigDecimal changeOriAmt = FDCHelper.ZERO;
		BigDecimal changeAmt = FDCHelper.ZERO;
		ContractChangeBillInfo billInfo;
		if (collection != null) {
			for (Iterator itor = collection.iterator(); itor.hasNext();) {
				billInfo = (ContractChangeBillInfo) itor.next();
				if (billInfo.isHasSettled()) {
					if (null != billInfo.getOriBalanceAmount()) {
						changeOriAmt = FDCHelper.add(changeOriAmt, billInfo
								.getOriBalanceAmount());
					}
					if (null != billInfo.getBalanceAmount()) {
						changeAmt = FDCHelper.add(changeAmt, billInfo
								.getBalanceAmount());
					}
				} else {
					if (null != billInfo.getOriginalAmount()) {
						changeOriAmt = FDCHelper.add(changeOriAmt, billInfo
								.getOriginalAmount());
					}
					if (null != billInfo.getAmount()) {
						changeAmt = FDCHelper.add(changeAmt, billInfo
								.getAmount());
					}
				}
			}
		}
		if (FDCHelper.toBigDecimal(changeOriAmt, 2).compareTo(FDCHelper.ZERO) == 0) {
			((ICell) bindCellMap.get(PayRequestBillContants.CHANGEORGAMT))
					.setValue(FDCHelper.ZERO);
		} else {
			((ICell) bindCellMap.get(PayRequestBillContants.CHANGEORGAMT))
					.setValue(changeOriAmt);
		}
		if (FDCHelper.toBigDecimal(changeAmt, 2).compareTo(FDCHelper.ZERO) == 0) {
			((ICell) bindCellMap.get(PayRequestBillContants.CHANGEAMT))
					.setValue(FDCHelper.ZERO);
		} else {
			((ICell) bindCellMap.get(PayRequestBillContants.CHANGEAMT))
					.setValue(changeAmt);
		}

		// 汇率
		BigDecimal rate = contractBill.getExRate();
		// 本申请单已付原币
		BigDecimal orgDecimal = FDCHelper.toBigDecimal(((ICell) bindCellMap
				.get(PayRequestBillContants.PAYEDAMT)).getValue());
		if (null == orgDecimal
				|| FDCHelper.toBigDecimal(orgDecimal, 2).compareTo(
						FDCHelper.ZERO) == 0) {
			((ICell) bindCellMap.get(PayRequestBillContants.PAYEDORGAMT))
					.setValue(FDCHelper.ZERO);
		} else {
			((ICell) bindCellMap.get(PayRequestBillContants.PAYEDORGAMT))
					.setValue(orgDecimal);
		}

		// 本申请单已付本币
		if (null == orgDecimal
				|| FDCHelper.toBigDecimal(orgDecimal, 2).compareTo(
						FDCHelper.ZERO) == 0) {
			((ICell) bindCellMap.get(PayRequestBillContants.PAYEDAMT))
					.setValue(FDCHelper.ZERO);
		} else {
			((ICell) bindCellMap.get(PayRequestBillContants.PAYEDAMT))
					.setValue(FDCHelper.multiply(orgDecimal, rate));
		}

		/**
		 * 合同内工程款
		 */
		BigDecimal tmp = FDCHelper.ZERO;
		tmp = (BigDecimal) table.getCell(5, 3).getValue();
		if (null == tmp) {
			table.getCell(5, 2).setValue(FDCHelper.ZERO);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			table.getCell(5, 2).setValue(FDCHelper.divide(tmp, rate)); //原币=本币/汇率
		}

		/* modified by zhaoqin for R140227-0281 on 2014/03/21 start */
		/*
		 * tmp = (BigDecimal) table.getCell(5, 5).getValue(); if (null == tmp ||
		 * FDCHelper.toBigDecimal(tmp,2).compareTo(FDCHelper.ZERO)==0) {
		 * table.getCell(5, 4).setValue(FDCHelper.ZERO); }else { tmp =
		 * FDCHelper.toBigDecimal(tmp,2); table.getCell(5,
		 * 4).setValue(FDCHelper.divide(tmp, rate)); //原币=本币/汇率 }
		 */
		/* modified by zhaoqin for R140227-0281 on 2014/03/21 end */

		/**
		 * 预付款
		 */
		tmp = (BigDecimal) table.getCell(6, 3).getValue();
		if (null == tmp
				|| FDCHelper.toBigDecimal(tmp, 2).compareTo(FDCHelper.ZERO) == 0) {
			table.getCell(6, 2).setValue(null);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			table.getCell(6, 2).setValue(FDCHelper.divide(tmp, rate)); //原币=本币/汇率
		}

		tmp = (BigDecimal) table.getCell(6, 5).getValue();
		if (null == tmp
				|| FDCHelper.toBigDecimal(tmp, 2).compareTo(FDCHelper.ZERO) == 0) {
			table.getCell(6, 4).setValue(null);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			table.getCell(6, 4).setValue(FDCHelper.divide(tmp, rate)); //原币=本币/汇率
		}

		/**
		 * 奖励
		 */
		tmp = (BigDecimal) table.getCell(8, 3).getValue();
		if (null == tmp
				|| FDCHelper.toBigDecimal(tmp, 2).compareTo(FDCHelper.ZERO) == 0) {
			table.getCell(8, 2).setValue(null);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			table.getCell(8, 2).setValue(FDCHelper.divide(tmp, rate)); //原币=本币/汇率
		}

		tmp = (BigDecimal) table.getCell(8, 5).getValue();
		if (null == tmp
				|| FDCHelper.toBigDecimal(tmp, 2).compareTo(FDCHelper.ZERO) == 0) {
			table.getCell(8, 4).setValue(null);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			table.getCell(8, 4).setValue(FDCHelper.divide(tmp, rate)); //原币=本币/汇率
		}

		/**
		 * 违约金
		 */
		tmp = (BigDecimal) table.getCell(9, 3).getValue();
		if (null == tmp
				|| FDCHelper.toBigDecimal(tmp, 2).compareTo(FDCHelper.ZERO) == 0) {
			table.getCell(9, 2).setValue(null);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			table.getCell(9, 2).setValue(FDCHelper.divide(tmp, rate)); //原币=本币/汇率
		}

		tmp = (BigDecimal) table.getCell(9, 5).getValue();
		if (null == tmp
				|| FDCHelper.toBigDecimal(tmp, 2).compareTo(FDCHelper.ZERO) == 0) {
			table.getCell(9, 4).setValue(null);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			table.getCell(9, 4).setValue(FDCHelper.divide(tmp, rate)); //原币=本币/汇率
		}

		int rowCount = kdtEntrys.getRowCount();
		int aMartIndex = rowCount - 6;
		/**
		 * 应扣甲供材
		 */
		tmp = (BigDecimal) table.getCell(aMartIndex, 3).getValue();
		if (null == tmp
				|| FDCHelper.toBigDecimal(tmp, 2).compareTo(FDCHelper.ZERO) == 0) {
			table.getCell(aMartIndex, 2).setValue(FDCHelper.ZERO);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			table.getCell(aMartIndex, 2).setValue(FDCHelper.divide(tmp, rate)); // 原币
																				// =
																				// 本币
																				// /
																				// 汇率
		}

		tmp = (BigDecimal) table.getCell(aMartIndex, 5).getValue();
		if (null == tmp
				|| FDCHelper.toBigDecimal(tmp, 2).compareTo(FDCHelper.ZERO) == 0) {
			table.getCell(aMartIndex, 4).setValue(FDCHelper.ZERO);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			table.getCell(aMartIndex, 4).setValue(FDCHelper.divide(tmp, rate)); // 原币
																				// =
																				// 本币
																				// /
																				// 汇率
		}

	};

	/**
	 * R110824-0401北城致远需求：付款与工程量分离时,付款申请单自动从工程量确认单取数
	 * “累计已完工工程量”：取截止当前该合同下已审批的工程量确认单“确认工程量”合计 “本期完工工程量金额”：默认等于累计已完工工程量-
	 * 截止上期累计申请（本币） “进度款付款比例”:本次申请本币/本期完工量工程量金额
	 * 
	 * @throws BOSException
	 */
	private void updateCompletePrjAmt() throws BOSException {
		// R110824-0401北城致远需求：付款与工程量分离时,付款申请单自动从工程量确认单取数
		if (isSeparate && !isFromProjectFillBill
				&& OprtState.ADDNEW.equals(getOprtState())) {
			isByWorkload = true;
			detachListeners();
			// 付款比例
			txtpaymentProportion.setEditable(true);
			txtpaymentProportion.setEnabled(true);
			txtpaymentProportion.setRequired(false);

			// 累计完工工程量
			BigDecimal allCompletePrjAmt = FDCHelper.ZERO;
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("workload");
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems()
					.add(
							new FilterItemInfo("contractBill", editData
									.getContractId()));
			filter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.AUDITTED));
			view.setFilter(filter);
			WorkLoadConfirmBillCollection workloadCol = WorkLoadConfirmBillFactory
					.getRemoteInstance().getWorkLoadConfirmBillCollection(view);
			if (workloadCol != null && workloadCol.size() > 0) {
				for (Iterator it = workloadCol.iterator(); it.hasNext();) {
					WorkLoadConfirmBillInfo workloadInfo = (WorkLoadConfirmBillInfo) it
							.next();
					if (workloadInfo != null) {
						allCompletePrjAmt = FDCNumberHelper.add(
								allCompletePrjAmt, workloadInfo.getWorkLoad());
					}
				}
			}
			txtAllCompletePrjAmt.setValue(allCompletePrjAmt);
			// 没有工程量，即使负数也不写入
			if (!FDCHelper.isZero(allCompletePrjAmt)) {

				// 本次完工工程量
				// 查出合同下的付款申请单
				view = new EntityViewInfo();
				filter = new FilterInfo();
				// modify by lihaiou,2014-07-14,去合同的ID,而不是合同的编码
				filter.getFilterItems().add(
						new FilterItemInfo("contractId", editData
								.getContractId()));
				// modify end
				view.setFilter(filter);
				view.getSelector().add("completePrjAmt");

				// modified by zhaoqin for R130922-0254 on 2013/10/17
				// view.getSelector().add("amount");
				view.getSelector().add("projectPriceInContract");
				BigDecimal allAmount = FDCHelper.ZERO;

				CoreBaseCollection collections = PayRequestBillFactory
						.getRemoteInstance().getCollection(view);

				// BigDecimal allComPrjAmt = FDCHelper.ZERO;
				if (null != collections && collections.size() > 0) {
					for (int i = 0; i < collections.size(); i++) {
						PayRequestBillInfo info = (PayRequestBillInfo) collections
								.get(i);
						// allComPrjAmt = FDCHelper.add(allComPrjAmt,
						// info.getCompletePrjAmt());
						allAmount = FDCHelper.add(allAmount, info
								.getProjectPriceInContract());
					}
				}

				// 本期完工工程量默认等于累计已完工工程量- 累计已确认完工工程量 2012-8-31 周鹏需求
				// editData.setCompletePrjAmt(FDCNumberHelper.subtract(
				// allCompletePrjAmt, allComPrjAmt));
				// txtcompletePrjAmt.setValue(FDCNumberHelper.subtract(
				// allCompletePrjAmt, allComPrjAmt));

				// modified by zhaoqin for R130922-0254 on 2013/10/17 start
				allAmount = FDCNumberHelper.subtract(allCompletePrjAmt,
						allAmount);
				editData.setCompletePrjAmt(allAmount);
				txtcompletePrjAmt.setValue(allAmount);
				// modified by zhaoqin for R130922-0254 on 2013/10/17 end

				// editData.setCompletePrjAmt(FDCNumberHelper.subtract(
				// allCompletePrjAmt, lstReqAmt));
				// txtcompletePrjAmt.setValue(FDCNumberHelper.subtract(
				// allCompletePrjAmt, lstReqAmt));
			}

			txtcompletePrjAmt.setEditable(true);
			txtcompletePrjAmt.setEnabled(true);

			attachListeners();
		}
	}

	/**
	 * 描述：工程量分离时，保存、提交 如果本期完工工程量金额为0，重新校验计算
	 * 
	 * @throws BOSException
	 * @Author：keyan_zhao
	 * @CreateTime：2013-7-19
	 */
	private void reSetCompleteAmt() throws BOSException {
		if (isSeparate && !isFromProjectFillBill
				&& FDCHelper.isZero(txtcompletePrjAmt.getValue())) {

			if (prmtPayment.getValue() == null) {
				return;
			}
			PaymentTypeInfo type = (PaymentTypeInfo) prmtPayment.getValue();
			if (!type.getPayType().getId().toString().equals(
					PaymentTypeInfo.progressID)) {
				return;
			}
			if (type.getName().toString().equals(YFK)) {
				return;
			}
			// 累计完工工程量
			BigDecimal allCompletePrjAmt = FDCHelper.ZERO;
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("workload");
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems()
					.add(
							new FilterItemInfo("contractBill", editData
									.getContractId()));
			filter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.AUDITTED));
			view.setFilter(filter);
			WorkLoadConfirmBillCollection workloadCol = WorkLoadConfirmBillFactory
					.getRemoteInstance().getWorkLoadConfirmBillCollection(view);
			if (workloadCol != null && workloadCol.size() > 0) {
				for (Iterator it = workloadCol.iterator(); it.hasNext();) {
					WorkLoadConfirmBillInfo workloadInfo = (WorkLoadConfirmBillInfo) it
							.next();
					if (workloadInfo != null) {
						allCompletePrjAmt = FDCNumberHelper.add(
								allCompletePrjAmt, workloadInfo.getWorkLoad());
					}
				}
			}
			BigDecimal allComPrjAmt = FDCHelper.ZERO;
			if (!FDCHelper.isZero(allCompletePrjAmt)) {
				// 查出合同下的付款申请单
				view = new EntityViewInfo();
				filter = new FilterInfo();
				// modify by lihaiou,2014-07-14,去合同的ID,而不是合同的编码
				filter.getFilterItems().add(
						new FilterItemInfo("contractId", editData
								.getContractId()));
				// modify end
				view.setFilter(filter);
				view.getSelector().add("completePrjAmt");
				CoreBaseCollection collections = PayRequestBillFactory
						.getRemoteInstance().getCollection(view);

				if (null != collections && collections.size() > 0) {
					for (int i = 0; i < collections.size(); i++) {
						PayRequestBillInfo info = (PayRequestBillInfo) collections
								.get(i);
						allComPrjAmt = FDCHelper.add(allComPrjAmt, info
								.getCompletePrjAmt());
					}
				}
			}
			// 本期完工工程量默认等于累计已完工工程量- 累计已确认完工工程量 2012-8-31 周鹏需求
			BigDecimal subtract = FDCNumberHelper.subtract(allCompletePrjAmt,
					allComPrjAmt);
			txtcompletePrjAmt.setValue(subtract, false);
			txtpaymentProportion.setValue(FDCHelper.divide(FDCHelper.multiply(
					kdtEntrys.getCell(rowIndex, columnIndex).getValue(),
					FDCHelper.ONE_HUNDRED), subtract, 2,
					BigDecimal.ROUND_HALF_UP), false);
		}
	}

	/**
	 * description 取合同结算金额原币和本币 结算金额原币：合同结算前金额为0.00，结算后取结算单录入模块编辑界面“结算造价原币”
	 * 结算金额本币：合同结算前金额为0.00，结算后取结算单录入模块编辑界面“结算造价本位币”
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-8
	 *             <p>
	 * @param contractId
	 *            合同ID
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
	private HashMap getContractAttset(String contractId) {
		BigDecimal orgAmt = FDCHelper.ZERO;
		BigDecimal amt = FDCHelper.ZERO;
		int hasSettled = 0;
		HashMap map = new HashMap();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
				.appendSql("select a.FtotalOriginalAmount as orgamt,a.FtotalSettlePrice AS amt,b.fhasSettled as hasSettled from t_con_contractsettlementbill  a LEFT OUTER JOIN T_CON_contractBill b");
		builder.appendSql(" ON ");
		builder.appendSql("a.FContractBillID=b.FID");
		builder.appendSql(" where ");
		builder.appendSql("b.FID=?");
		builder.addParam(contractId);
		IRowSet rowSet;
		try {
			rowSet = builder.executeQuery();
			if (rowSet.size() == 1) {
				rowSet.next();
				hasSettled = rowSet.getInt("hasSettled");
				if (hasSettled == 1) {
					orgAmt = FDCHelper.toBigDecimal(rowSet
							.getBigDecimal("orgamt"));
					amt = FDCHelper.toBigDecimal(rowSet.getBigDecimal("amt"));
				}
			}
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		} catch (SQLException e) {
			handUIExceptionAndAbort(e);
		}
		map.put("amt", amt);
		map.put("orgAmt", orgAmt);
		return map;
	}

	/**
	 * description
	 * (当前单据合同下的累计实付款+未付的申请单超过了付款提示比例)时，获取返回的参数，0=严格控制（中断操作），1=提示（只提示、不中断，可继续执行）
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-8-31
	 *             <p>
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
	private String allPaidMoreThanConPrice() {
		IParamControl ipctr = null;
		try {
			ipctr = ParamControlFactory.getRemoteInstance();
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
		String retValue = "";
		try {
			retValue = ipctr.getParamValue(null,
					"FDC444_ALLPAIDMORETHANCONPRICE"); // 根据集团控制标志、参数编号获得参数
		} catch (EASBizException e) {
			handUIExceptionAndAbort(e);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
		return retValue;
	}

	// modified by ken_liu.,.R130301-0367 改为用本币计算
	/**
	 * 根据“本期完工工程量”和“本次申请本币”，则自动反算“进度款付款比例”
	 */
	private void caculatePaymentProp_Old() {
		if (/* isSeparate && */!isFromProjectFillBill && !isAutoComplete) {
			BigDecimal completePrjAmt = FDCNumberHelper
					.toBigDecimal(txtcompletePrjAmt.getBigDecimalValue());
			BigDecimal originalAmount = FDCHelper.toBigDecimal(getDetailTable()
					.getCell(rowIndex, columnIndex).getValue());
			// BigDecimal localAmount = FDCHelper.multiply(originalAmount,
			// txtexchangeRate.getBigDecimalValue());
			if (FDCNumberHelper.ZERO.compareTo(completePrjAmt) == 0) {
				// txtpaymentProportion.setValue(null);
			} else if (FDCNumberHelper.ZERO.compareTo(originalAmount) == 0) {
				txtpaymentProportion.setValue(FDCNumberHelper.ZERO, false);
			} else {
				txtpaymentProportion.setValue(FDCNumberHelper.multiply(
						FDCNumberHelper.divide(originalAmount, completePrjAmt),
						FDCHelper.ONE_HUNDRED), false);
			}
		}
	}

	/**
	 * 描述：判断付款类型是不是进度款，并且合同类型勾选了“工程量确认”
	 * 
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 * @throws Exception
	 * @Author：jian_cao
	 * @CreateTime：2012-11-15
	 */
	private boolean isProgressPaymentAndWorkLoadConfirm() throws BOSException,
			SQLException {
		PaymentTypeInfo type = (PaymentTypeInfo) prmtPayment.getValue();
		// 如果进度款
		if (type != null
				&& type.getPayType().getId().toString().equals(
						PaymentTypeInfo.progressID)) {
			// 查询合同类型是否勾选了“工程量确认”
			FDCSQLBuilder sql = new FDCSQLBuilder();
			sql
					.appendSql(" select FisWorkLoadConfirm from T_FDC_ContractType where fid="
							+ "(select FContractTypeID from t_con_contractBill where fid='"
							+ this.editData.getContractId() + "') ");
			IRowSet rs = sql.executeQuery();
			if (rs.next()) {
				return rs.getBoolean("FisWorkLoadConfirm");
			}
		}
		return false;
	}

	/**
	 * 描述：计算 “本期完工工程量”，“进度款付款比例”，“本次申请金额” 的结果
	 * 
	 * @param key
	 * @throws SQLException
	 * @throws BOSException
	 * @Author：jian_cao
	 * @CreateTime：2012-11-19
	 */
	private void caculateResult(int key) throws BOSException, SQLException {

		PaymentTypeInfo paymentType = (PaymentTypeInfo) prmtPayment.getValue();

		// modify by lihaiou,2014-08-07，去掉是否为自动比例的参数判断
		if (!isFromProjectFillBill
				&& paymentType != null
				&& (!PaymentTypeInfo.settlementID.equals(paymentType
						.getPayType().getId().toString()))) {

			// 本次申请金额原币
			BigDecimal originalAmount = FDCHelper.toBigDecimal(getDetailTable()
					.getCell(rowIndex, columnIndex).getValue());
			BigDecimal localAmount = FDCHelper.multiply(originalAmount,
					txtexchangeRate.getBigDecimalValue());
			// 本期完工工程量
			BigDecimal completePrjAmt = FDCHelper
					.toBigDecimal(txtcompletePrjAmt.getBigDecimalValue());
			// 进度款付款比例
			BigDecimal paymentProportion = FDCHelper
					.toBigDecimal(txtpaymentProportion.getBigDecimalValue());

			// 如果判断付款类型是不是进度款，并且合同类型勾选了“工程量确认”
			if (isProgressPaymentAndWorkLoadConfirm()) {
				switch (key) {

				case COMPLETEPRJAMT: // 如果是 本期完工工程量修改

					// 当 进度款付款比例不为0时，计算 本次申请金额原币
					if (!FDCNumberHelper.isZero(paymentProportion)) {
						caculateReqOriginalAmount(completePrjAmt,
								paymentProportion);
					} else if (!FDCNumberHelper.isZero(originalAmount)) { // 进度款付款比例
																			// 为0时
																			// ，
																			// 并且
																			// “
																			// 本次申请金额原币
																			// ”
																			// 也不为0
																			// ,
																			// 就计算出
																			// “
																			// 进度款付款比例
																			// ”
						/* modified by zhaoqin for R140428-0095 on 2014/05/05 */
						//txtpaymentProportion.setValue(FDCNumberHelper.multiply
						// (FDCNumberHelper.divide(localAmount, completePrjAmt),
						txtpaymentProportion.setValue(FDCNumberHelper.multiply(
								FDCNumberHelper.divide(localAmount,
										completePrjAmt, 4,
										BigDecimal.ROUND_HALF_UP),
								FDCHelper.ONE_HUNDRED), false);
					}
					break;

				case PAYMENTPROPORTION:// 如果是 进度款付款比例修改

					// 当“本期完工工程量”不为0，计算出 “ 本次申请金额原币”
					if (!FDCNumberHelper.isZero(completePrjAmt)) {
						caculateReqOriginalAmount(completePrjAmt,
								paymentProportion);
					} else if (!FDCNumberHelper.isZero(originalAmount)) { // 当“
																			// 本期完工工程量
																			// ”
																			// 为0
																			// ,
																			// 并且
																			// “
																			// 本次申请金额原币
																			// ”
																			// 也不为0
																			// ,
																			// 就计算出
																			// “
																			// 本期完工工程量
																			// ”
						txtcompletePrjAmt.setValue(FDCNumberHelper.divide(
								originalAmount, FDCNumberHelper.divide(
										paymentProportion,
										FDCHelper.ONE_HUNDRED)), false);
					}

					break;
				}
			} else {
				caculateReqOriginalAmount(completePrjAmt, paymentProportion);
			}
		}
	}

	/**
	 * 描述：根据“本期完工工程量”和“进度款付款比例”，则自动反算“本次申请金额”
	 * 
	 * @param completePrjAmt
	 *            “本期完工工程量”
	 * @param paymentProportion
	 *            “进度款付款比例”
	 * @Author：jian_cao
	 * @CreateTime：2012-11-19
	 */
	private void caculateReqOriginalAmount(BigDecimal completePrjAmt,
			BigDecimal paymentProportion) {
		// 本次申请金额(原币)
		// BigDecimal amount = FDCNumberHelper.divide(completePrjAmt,
		// txtexchangeRate.getBigDecimalValue()); //对应的原币
		BigDecimal amount = FDCNumberHelper.multiply(completePrjAmt,
				paymentProportion);// 对应的本币

		amount = FDCNumberHelper.divide(amount, FDCHelper.ONE_HUNDRED);
		getDetailTable().getCell(rowIndex, columnIndex).setValue(
				FDCNumberHelper.divide(amount, txtexchangeRate
						.getBigDecimalValue()));
		// 本次申请金额(本币)
		// getDetailTable().getCell(rowIndex, columnIndex + 1).setValue(
		// FDCNumberHelper.multiply(amount,
		// txtexchangeRate.getBigDecimalValue()));
		getDetailTable().getCell(rowIndex, columnIndex + 1).setValue(amount);
	}

	/**
	 * 工程量确认修改
	 */
	protected void txtcompletePrjAmt_dataChanged(DataChangeEvent e)
			throws Exception {
		if (FDCNumberHelper.ZERO.compareTo((BigDecimal) e.getNewValue()) == 0) { // 当修改任意一个字段为零时
																					// ，
																					// 另外两个字段都显示为零
			// 王亮发现的BUG：当改变表头.本期完成工程量=0的时候，表头.进度款付款比例也被改成0%。由于联动关系，无法改回来。 by
			// skyiter_wang 2015/01/13
			if (!txtpaymentProportion.isEnabled()) {
				txtpaymentProportion.setValue(FDCNumberHelper.ZERO, false);
			}
			getDetailTable().getCell(rowIndex, columnIndex).setValue(
					FDCNumberHelper.ZERO);
			getDetailTable().getCell(rowIndex, columnIndex + 1).setValue(
					FDCNumberHelper.ZERO);
		} else {
			caculateResult(COMPLETEPRJAMT);
		}
	}

	/**
	 * 进度款付款比例修改
	 */
	protected void txtpaymentProportion_dataChanged(DataChangeEvent e)
			throws Exception {
		if (FDCNumberHelper.ZERO.compareTo((BigDecimal) e.getNewValue()) == 0) {// 当修改任意一个字段为零时
																				// ，
																				// 另外两个字段都显示为零
			txtcompletePrjAmt.setValue(FDCNumberHelper.ZERO, false);
			getDetailTable().getCell(rowIndex, columnIndex).setValue(
					FDCNumberHelper.ZERO);
			getDetailTable().getCell(rowIndex, columnIndex + 1).setValue(
					FDCNumberHelper.ZERO);
		} else {
			caculateResult(PAYMENTPROPORTION);
		}
	}

	/**
	 * description 初始化计划项目
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
	private void initPrmtPlanUnCon() throws BOSException {
		// 界面上有两个PRMT控件，但是重合在一起
		if (prmtPlanHasCon.getValue() != null) {
			contPlanHasCon.setVisible(true);
			contPlanUnCon.setVisible(false);
		} else if (prmtPlanUnCon.getValue() != null) {
			contPlanHasCon.setVisible(false);
			contPlanUnCon.setVisible(true);
		}

		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("unConName");
		sic.add("parent.id");
		sic.add("parent.number");
		sic.add("parent.name");
		sic.add("parent.year");
		sic.add("parent.month");
		sic.add("parent.deptment.name");
		prmtPlanUnCon.setSelectorCollection(sic);

		Date bookDate = (Date) pkbookedDate.getValue();
		Date firstDay = BudgetViewUtils.getFirstDay(bookDate);
		Date lastDay = BudgetViewUtils.getLastDay(bookDate);
		FDCSQLBuilder sql = new FDCSQLBuilder();
		sql
				.appendSql(" select DISTINCT uc.FID as fid from T_FNC_FDCDepConPayPlanUC as uc ");
		sql
				.appendSql(" left join T_FNC_FDCDepConPayPlanBill as head on head.FID = uc.FParentId ");
		sql
				.appendSql(" left join T_FNC_FDCDepConPayPlanUE as ue on ue.FParentId = uc.FID ");
		sql
				.appendSql(" where (head.FState = '4AUDITTED' or head.FState = '10PUBLISH') ");
		sql.appendSql(" and uc.FProjectID = ");
		sql.appendParam(editData.getCurProject().getId().toString());
		sql.appendSql(" and ue.FMonth >= ");
		sql.appendParam(firstDay);
		sql.appendSql(" and ue.FMonth <= ");
		sql.appendParam(lastDay);
		IRowSet rs = sql.executeQuery();
		Set ids = new HashSet();
		try {
			while (rs.next()) {
				ids.add(rs.getString("fid"));
			}
		} catch (SQLException e) {
			handUIExceptionAndAbort(e);
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if (ids.size() < 1) {
			filter.getFilterItems().add(new FilterItemInfo("id", "11"));
		} else {
			filter.getFilterItems().add(
					new FilterItemInfo("id", ids, CompareType.INCLUDE));
		}
		view.setFilter(filter);
		prmtPlanUnCon.setEntityViewInfo(view);
	}

	/**
	 * 选择待签订时，校验是否被其余不同合同下的申请单选择过<br>
	 * 如果已被选择过，提示不让选<br>
	 * 因为一个计划控制一个合同
	 */
	protected void prmtPlanUnCon_dataChanged(DataChangeEvent e)
			throws Exception {
		if (!STATUS_ADDNEW.equals(getOprtState())
				&& !STATUS_EDIT.equals(getOprtState())) {
			return;
		}
		FDCDepConPayPlanUnsettledConInfo plan = (FDCDepConPayPlanUnsettledConInfo) prmtPlanUnCon
				.getValue();
		String conID = editData.getContractId();
		if (plan != null && conID != null) {
			String planID = plan.getId().toString();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("planUnCon.id", planID));
			filter.getFilterItems().add(
					new FilterItemInfo("contractId", conID,
							CompareType.NOTEQUALS));
			if (getBizInterface().exists(filter)) {
				FDCMsgBox.showWarning(this, "该待签订合同付款计划已被其他合同下的付款申请单引用，请重新选择。");
				prmtPlanUnCon.setDataNoNotify(e.getOldValue());
			}

			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("planUnCon.id");
			sic.add("planUnCon.UnConNumber");
			view.setSelector(sic);
			filter = new FilterInfo();
			filter.getFilterItems()
					.add(new FilterItemInfo("contractId", conID));
			if (STATUS_EDIT.equals(getOprtState())) {
				filter.getFilterItems().add(
						new FilterItemInfo("id", editData.getId().toString(),
								CompareType.NOTEQUALS));
			}
			view.setFilter(filter);
			PayRequestBillCollection col = ((IPayRequestBill) getBizInterface())
					.getPayRequestBillCollection(view);
			if (col != null && col.size() > 0) {
				for (int i = 0; i < col.size(); i++) {
					FDCDepConPayPlanUnsettledConInfo info = col.get(i)
							.getPlanUnCon();
					if (info != null && !info.getId().toString().equals(planID)) {
						String num = info.getUnConNumber();
						FDCMsgBox.showWarning(this, "该合同下存在付款申请单选择 ‘" + num
								+ "’ 作为待签订合同滚动付款计划，请使用统一计划控制付款！");
						prmtPlanUnCon.setDataNoNotify(e.getOldValue());
						break;
					}
				}
			}
			setDepPlanState();
		} else {
			kdDepPlanState.setSelectedItem(DepPlanStateEnum.noPlan);
		}
	}

	/**
	 * description 重载甲方详细信息
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
	private void reloadPartADeductDetails() throws Exception {
		if (partAParam) {
			tableHelper.reloadPartAValue(editData, null);
		} else {
			tableHelper.reloadPartAConfmValue(editData, null);
		}
	}

	/**
	 * description 完工工程量来自进度系统工程量填报 逻辑控制
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
	private void fromProjectFill() throws Exception {
		detachListeners();
		String contractId = this.contractBill.getId().toString();
		BigDecimal sum = FDCHelper.ZERO;
		List idList = new ArrayList();
		if (isFillBillControlStrict) {// 完工工程量的确认严格控制
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder
					.appendSql(" select fid, sum(FTotalQty) sum from t_fpm_projectfillbill where ");
			builder.appendParam("fcontractid", contractId);
			builder.appendSql(" and");
			builder.appendParam(" fstate", FDCBillStateEnum.AUDITTED_VALUE);
			builder.appendSql(" and fobjectid is null");
			builder.appendSql(" group by fid");
			IRowSet rs = builder.executeQuery();
			while (rs.next()) {
				idList.add(rs.getString("fid"));
				sum = FDCHelper.add(sum, rs.getBigDecimal("sum"));
			}
		} else {
			Set wbsIds = new HashSet();
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("wbs.id");
			view.setFilter(new FilterInfo());
			view.getFilter().getFilterItems().add(
					new FilterItemInfo("parent.contract.id", contractId));
			view.getFilter().getFilterItems().add(
					new FilterItemInfo("parent.isEnabled", Boolean.TRUE));
			ContractAndTaskRelEntryCollection entries = ContractAndTaskRelEntryFactory
					.getRemoteInstance().getContractAndTaskRelEntryCollection(
							view);
			for (int i = 0; i < entries.size(); ++i) {
				wbsIds.add(entries.get(i).getWbs().getId().toString());
			}
			EntityViewInfo taskView = new EntityViewInfo();
			taskView.getSelector().add("workLoad");
			taskView.setFilter(new FilterInfo());
			taskView.getFilter().getFilterItems().add(
					new FilterItemInfo("wbs.id", wbsIds, CompareType.INCLUDE));
			taskView.getFilter().getFilterItems().add(
					new FilterItemInfo("schedule.baseVer.isLatestVer",
							Boolean.TRUE));
			for (int i = 0; i < entries.size(); ++i) {
				wbsIds.add(entries.get(i).getWbs().getId().toString());
			}
			FDCScheduleTaskCollection tasks = FDCScheduleTaskFactory
					.getRemoteInstance().getFDCScheduleTaskCollection(taskView);
			for (int i = 0; i < tasks.size(); ++i) {
				sum = FDCHelper.add(sum, tasks.get(i).getWorkLoad());
			}
		}

		if (this.editData.getId() == null) {
			editData.setId(BOSUuid.create(editData.getBOSType()));
		}
		if (idList.size() > 0) {// 貌似没用 by zhiqiao_yang at 20110310
			FDCSCHUtils.updateTaskRef(null, isFromProjectFillBill,
					isFillBillControlStrict, false, contractId,
					new IObjectPK[] { new ObjectUuidPK(editData.getId()) },
					idList);
		}
		if (sum.compareTo(FDCHelper.ZERO) == 0) {
			kdtEntrys.getCell(rowIndex, columnIndex).getStyleAttributes()
					.setLocked(false);
			txtpaymentProportion.setEditable(false);
			txtcompletePrjAmt.setEditable(false);
			txtpaymentProportion.setValue(FDCHelper.ZERO);
			txtcompletePrjAmt.setValue(FDCHelper.ZERO);
		} else {
			this.txtcompletePrjAmt.setValue(sum);
			BigDecimal allCompleteAmt = FDCHelper
					.toBigDecimal(txtAllCompletePrjAmt.getBigDecimalValue());
			this.txtAllCompletePrjAmt.setValue(FDCHelper.add(allCompleteAmt,
					sum));
			this.txtpaymentProportion.setValue(FDCHelper.ONE_HUNDRED);
			ICell local = (ICell) getDetailTable().getCell(rowIndex,
					columnIndex + 1);
			local.setValue(sum);
			BigDecimal rate = FDCHelper.toBigDecimal(this.txtexchangeRate
					.getBigDecimalValue());
			ICell ori = (ICell) getDetailTable().getCell(rowIndex, columnIndex);
			ori.setValue(FDCHelper.divide(sum, rate));
			btnInputCollect_actionPerformed(null);
		}
		attachListeners();
	}

	// 业务日期变化引起,期间的变化
	protected void bookedDate_dataChanged(
			com.kingdee.bos.ctrl.swing.event.DataChangeEvent e)
			throws Exception {
		String projectId = this.editData.getCurProject().getId().toString();
		fetchPeriod(e, pkbookedDate, cbPeriod, projectId, false);
	}

	/**
	 * description 设置界面控件状态
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * 
	 * @version EAS 7.0
	 * @see com.kingdee.eas.fdc.contract.client.AbstractPayRequestBillEditUI#setOprtState(java.lang.String)
	 */
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (editData != null) {
			String contractId = editData.getContractId();
			if (contractId == null || PayReqUtils.isConWithoutTxt(contractId)) {// 调整款项根据是否合同显示与隐藏
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
			actionRemove.setEnabled(false);
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

		if (getOprtState() == AbstractCoreBillEditUI.STATUS_FINDVIEW) {
			actionUnClose.setEnabled(false);
			actionClose.setEnabled(false);
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

		if (getOprtState().equals(OprtState.ADDNEW))
			btnTraceUp.setEnabled(false);

		if (!getOprtState().equals(OprtState.ADDNEW)
				&& !getOprtState().equals(OprtState.EDIT)) {
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
	}

	public void onShow() throws Exception {

		// 显示info的数据到表格
		super.onShow();
		txtrecAccount.setRequired(false);
		String contractId = editData.getContractId();
		payReqContractId = contractId;
		if ((!getOprtState().equals(OprtState.ADDNEW) && !getOprtState()
				.equals(OprtState.EDIT))) {
			final StyleAttributes sa = kdtEntrys.getStyleAttributes();
			sa.setLocked(true);
			sa.setBackground(noEditColor);
			btnInputCollect.setEnabled(false);
			kdtEntrys.setEnabled(false);
			kdtEntrys.getCell(this.rowIndex, this.columnIndex)
					.getStyleAttributes().setLocked(true);
		}

		// Add by zhiyuan_tang 2010/07/30 处理在查看界面点修改，本次申请原币可用的BUG
		if (isPartACon && this.editData.getConfirmEntry().size() > 0) { // 有材料确认分录时
																		// ，
																		// 要置不可编辑
			kdtEntrys.getCell(rowIndex, columnIndex).getStyleAttributes()
					.setLocked(true);
		}

		// 工作流中可以复制合同名称
		if (editData != null && editData.getId() != null
				&& FDCUtils.isRunningWorkflow(editData.getId().toString())) {
			kdtEntrys.setEnabled(true);
		}

		// new add by renliang at 2010-5-20
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				editAuditPayColumn();
			}
		});

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

		if (!getOprtState().equals(OprtState.ADDNEW)
				&& !getOprtState().equals(OprtState.EDIT)) {
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
		btnContractExecInfo.setVisible(true);
		btnContractExecInfo.setEnabled(true);

		// 如果是无文本合同，将"合同执行信息"屏蔽掉 by Cassiel_peng 2009-10-2
		if (PayReqUtils.isConWithoutTxt(this.editData.getContractId())) {
			this.btnContractExecInfo.setVisible(false);
			this.menuItemContractExecInfo.setVisible(false);
		}

		// 月度请款已经将"关联成本科目付款计划"与"关联待签订合同"合并到一起，这两个菜单需要隐藏 by Cassiel_peng
		// 2009-10-28
		this.menuItemAssociateAcctPay.setVisible(false);
		this.menuItemAssociateUnSettled.setVisible(false);

		if (getOprtState() == AbstractCoreBillEditUI.STATUS_FINDVIEW) {
			try {
				if (editData != null
						&& editData.getId() != null
						&& FDCUtils.isRunningWorkflow(editData.getId()
								.toString())
						&& FDCBillStateEnum.AUDITTING.equals(editData
								.getState())) {
					btnUnAudit.setEnabled(true);
					btnUnAudit.setVisible(true);
					btnAudit.setEnabled(false);
					btnAudit.setVisible(false);

				}
			} catch (BOSException e) {
				handUIExceptionAndAbort(e);
			}
		}
		// 避免在新增单据（未作修改）直接关闭时，出现保存提示, 已由基本框架处理
		handleOldData();
		// 分离
		setEditableAndRequiredBySeparateParam();
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();

		// //////////////////////////////////////////////////////
		sic.add("id");
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
		sic.add("process");
		sic.add("lastUpdateTime");
		sic.add("threeCaiAmount");
		// 款项内容
		sic.add("Kxnr");
		// 是否超资金计划
		sic.add("sfczjjhfk");

		sic.add("paymentProportion");
		sic.add("costAmount");
		sic.add("grtAmount");
		sic.add("capitalAmount");

		// 合同类型
		sic.add("contractType.*");
	
		// 1
		sic.add("contractName");
		sic.add("changeAmt");
		sic.add("payTimes");
		// sic.add("curPlannedPayment");
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

		// 发票
		sic.add("invoiceNumber");
		sic.add("invoiceAmt");
		sic.add("invoiceOriAmt");
		sic.add("allInvoiceAmt");
		sic.add("invoiceDate");

		sic.add("entrys.amount");
		sic.add("entrys.payPartAMatlAmt");
		sic.add("entrys.projectPriceInContract");
		sic.add("entrys.parent.id");
		sic.add("entrys.paymentBill.id");
		sic.add("entrys.advance");
		sic.add("entrys.locAdvance");

		sic.add("orgUnit.name");
		sic.add("orgUnit.number");
		sic.add("orgUnit.displayName");

		sic.add("CU.name");

		sic.add("auditor.name");
		sic.add("creator.name");

		sic.add("useDepartment.number");
		sic.add("useDepartment.name");

		sic.add("curProject.name");
		sic.add("curProject.number");
		sic.add("curProject.displayName");
		sic.add("curProject.fullOrgUnit.name");
		sic.add("curProject.codingNumber");
		sic.add("curProject.longNumber");

		sic.add("currency.number");
		sic.add("currency.name");
		// 付款申请单增加存储本位币币别，以方便预算系统能取到该字段值 by Cassiel_peng 2009-10-5
		sic.add("localCurrency.id");
		sic.add("localCurrency.number");
		sic.add("localCurrency.name");

		sic.add("supplier.number");
		sic.add("supplier.name");

		sic.add("realSupplier.number");
		sic.add("realSupplier.name");

		sic.add("settlementType.number");
		sic.add("settlementType.name");

		sic.add("paymentType.number");
		sic.add("paymentType.name");
		sic.add("paymentType.payType.id");

		sic.add("period.number");
		sic.add("period.beginDate");
		sic.add("period.periodNumber");
		sic.add("period.periodYear");
		sic.add("contractBase.number");
		sic.add("contractBase.name");

		// 计划项目
		sic.add("planHasCon.contract.id");
		sic.add("planHasCon.contractName");
		sic.add("planHasCon.head.deptment.name");
		sic.add("planHasCon.head.year");
		sic.add("planHasCon.head.month");

		sic.add("planUnCon.unConName");
		sic.add("planUnCon.parent.deptment.name");
		sic.add("planUnCon.parent.year");
		sic.add("planUnCon.parent.month");
		sic.add("depPlanState");

		////////////////////////////////////////////////////////////////////////
		// ////
		// 付款申请单：增加拓展字段，用于客户自定义 by skyiter_wang 2014-12-04
		// 说明：
		// 1、付款申请单的套打比较特殊
		// 2、在代码中做了处理，写死了一些查询字段，导致DEP拓展的字段无法正常展示
		// 3、所以预定义5个拓展字段，方便客户自定义
		// 4、参见PayRequestBillEditUI.actionPrintPreview_actionPerformed，
		// PayRequestBillRowsetProvider

		sic.add("extField01");
		sic.add("extField02");
		sic.add("extField03");
		sic.add("extField04");
		sic.add("extField05");

		sic.add("extBooleanField01");
		sic.add("extBooleanField02");

		sic.add("extEnumField01");
		sic.add("extEnumField02");

		sic.add("ZjjhSqje");
		////////////////////////////////////////////////////////////////////////
		// ////

		return sic;
	}

	/**
	 * description 合同实付款不能大于合同结算价
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
	private void checkPrjPriceInConSettlePriceForSubmit()
			throws EASBizException, BOSException {
		storeFields();
		Set payReqBillSet = new HashSet();
		payReqBillSet.add(this.editData);
		boolean isCanPass = PayReqUtils.checkProjectPriceInContract(
				payReqBillSet, this.txtBcAmount.getBigDecimalValue());
		if (!isCanPass) {
			FDCMsgBox
					.showError("合同实付款不能大于合同结算价【合同实付款 =已关闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申请单合同内工程款合计】");
			SysUtil.abort();
		}
	}

	/**
	 * 在合同最终结算后，付款申请单提交、审批时，如果合同实付款(不含本次) 加 本次付款申请单合同内工程款本期发生大于合同结算价给出相应提示 by
	 * cassiel_peng 2009-12-03
	 */
	private void checkPrjPriceInConSettlePriceForUnClose() throws Exception {
		storeFields();
		// 取得该合同的实付款 =已关闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申请单合同内工程款合计
		BigDecimal totalPrjPriceInCon = FDCHelper
				.toBigDecimal(ContractClientUtils.getPayAmt(this.editData
						.getContractId()), 2);
		/**
		 * 在关闭请款单的时候,用合同实付款 =已关闭的付款申请单对应的付款单合同内工程款合计 +
		 * 未关闭的付款申请单合同内工程款合计公式取数的时候需要注意：
		 * 对于当前正要关闭的请款单来说无论是否已经生成过对应的付款单都应该取的是请款单的而非付款单合同内工程款
		 * 。因为需要考虑反关闭成功之后某些逻辑已经不能满足
		 */
		BigDecimal actualPayAmt = totalPrjPriceInCon;
		BigDecimal payAmt = FDCHelper.ZERO;// 该请款单对应的付款单的合同内工程款
		EntityViewInfo _view = new EntityViewInfo();
		_view.getSelector().add("projectPriceInContract");
		FilterInfo _filter = new FilterInfo();
		_filter.getFilterItems().add(
				new FilterItemInfo("contractBillId", this.editData
						.getContractId()));
		_filter.getFilterItems().add(
				new FilterItemInfo("fdcPayReqID", this.editData.getId()
						.toString()));
		_view.setFilter(_filter);
		PaymentBillCollection paymentColl = PaymentBillFactory
				.getRemoteInstance().getPaymentBillCollection(_view);
		if (paymentColl != null && paymentColl.size() > 0) {// 生成过付款单
			for (Iterator iter = paymentColl.iterator(); iter.hasNext();) {
				PaymentBillInfo payment = (PaymentBillInfo) iter.next();
				payAmt = FDCHelper.toBigDecimal(FDCHelper.add(payAmt, payment
						.getProjectPriceInContract()));
			}
			actualPayAmt = FDCHelper.toBigDecimal(FDCHelper.add(this.editData
					.getProjectPriceInContract(), FDCHelper.subtract(
					totalPrjPriceInCon, payAmt)));
		}

		BigDecimal conSettPrice = FDCHelper.ZERO;// 合同结算价
		SelectorItemCollection _selector = new SelectorItemCollection();
		_selector.add("settleAmt");
		_selector.add("hasSettled");
		_selector.add("amount");
		ContractBillInfo contractBill = ContractBillFactory.getRemoteInstance()
				.getContractBillInfo(
						new ObjectUuidPK(BOSUuid.read(this.editData
								.getContractId())), _selector);
		if (contractBill != null) {
			conSettPrice = FDCHelper.toBigDecimal(contractBill.getSettleAmt(),
					2);
		}
		if (actualPayAmt.compareTo(conSettPrice) > 1) {
			FDCMsgBox
					.showError("合同实付款不能大于合同结算价【合同实付款 =已关闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申请单合同内工程款合计】");
			SysUtil.abort();
		}

		// 合同已最终结算后，反关闭时需要校验：进度款+结算款不能超过合同结算价-保修金 by cassiel_peng 2009-12-08
		FDCSQLBuilder builder = new FDCSQLBuilder();
		if (contractBill.isHasSettled()) {
			BigDecimal settAmtSubtractGuarante = FDCHelper.ZERO;// 合同结算价-保修金
			builder
					.appendSql("select (fsettleprice-fqualityGuarante) as amount from t_con_contractsettlementbill where fcontractbillid=?");
			builder.addParam(this.editData.getContractId());
			IRowSet rowSet;
			try {
				rowSet = builder.executeQuery();
				try {
					if (rowSet.next()) {
						settAmtSubtractGuarante = rowSet
								.getBigDecimal("amount");
					}
				} catch (SQLException e) {
					handUIExceptionAndAbort(e);
				}
			} catch (BOSException e) {
				handUIExceptionAndAbort(e);
			}
			// 进度款+结算款(【已关闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申请单合同内工程款合计】)
			BigDecimal processSettlementPrice = FDCHelper.ZERO;
			builder.clear();
			builder.appendSql("select sum(合同实付款) as 合同实付款  from  \n ");
			builder.appendSql("( \n ");
			builder
					.appendSql("(select req.FProjectPriceInContract as 合同实付款 from T_Con_PayRequestBill req \n ");
			builder
					.appendSql("inner join t_fdc_paymentType pType on req.fpaymenttype = pType.fid \n ");
			builder.appendSql("and req.FContractId=? and req.FHasClosed=0 \n ");
			builder.addParam(this.editData.getContractId());
			builder.appendSql("and pType.FPayTypeId in (?,?) )\n ");// 除去保修款类型不算
			builder.addParam(PaymentTypeInfo.progressID);
			builder.addParam(PaymentTypeInfo.settlementID);
			builder.appendSql("union all\n ");
			builder
					.appendSql("(select pay.FProjectPriceInContract as 合同实付款 from T_CAS_PaymentBill pay \n  ");
			builder
					.appendSql("inner join T_Con_PayRequestbill req on pay.FFdcPayReqID=req.FID \n ");
			builder
					.appendSql("inner  join t_fdc_paymentType payType on payType.fid = pay.FFdcPayTypeID \n ");
			builder.appendSql("and pay.FContractBillId=req.FContractId \n ");
			builder.appendSql("and req.FContractId=? and req.FHasClosed=1 \n ");
			builder.addParam(this.editData.getContractId());
			builder.appendSql("and payType.fpayTypeId in (?,?)) \n ");
			builder.addParam(PaymentTypeInfo.progressID);
			builder.addParam(PaymentTypeInfo.settlementID);
			builder.appendSql(") \n ");
			IRowSet rowSet1 = builder.executeQuery();
			if (rowSet1.next()) {
				processSettlementPrice = FDCHelper.toBigDecimal(rowSet1
						.getBigDecimal("合同实付款"), 2);
			}

			BigDecimal finalProcessSettlementPrice = processSettlementPrice;

			if (paymentColl != null && paymentColl.size() > 0) {// 生成过付款单
				finalProcessSettlementPrice = FDCHelper.toBigDecimal(FDCHelper
						.add(
								FDCHelper.subtract(processSettlementPrice,
										payAmt), this.editData
										.getProjectPriceInContract()));
			}
			if (finalProcessSettlementPrice.compareTo(settAmtSubtractGuarante) == 1) {
				FDCMsgBox.showError("进度款+结算款不能超过合同结算价-保修金！");
				SysUtil.abort();
			}
		}

		// 反关闭时需要校验：合同实付款不能大于合同最新造价 by cassiel_peng 2009-12-08
		BigDecimal latestPrice = FDCHelper.ZERO;// 合同最新造价
		if (contractBill.isHasSettled()) {// 如果已最终结算，合同最新造价取结算价
			latestPrice = conSettPrice;
		} else {// 未最终结算，取合同金额+变更金额
			// 合同的变更金额
			BigDecimal changeAmt = FDCHelper.ZERO;
			builder.clear();
			builder
					.appendSql("select FContractBillID,sum(fchangeAmount) as changeAmount from ( ");
			builder
					.appendSql("select FContractBillID,FBalanceAmount as fchangeAmount from T_CON_ContractChangeBill ");
			builder.appendSql("where FHasSettled=1 and ");
			builder.appendSql("FContractBillID=?");
			builder.addParam(this.editData.getContractId());
			builder.appendSql(" and (");
			builder.appendParam("FState", FDCBillStateEnum.AUDITTED_VALUE);
			builder.appendSql(" or ");
			builder.appendParam("FState", FDCBillStateEnum.VISA_VALUE);
			builder.appendSql(" or ");
			builder.appendParam("FState", FDCBillStateEnum.ANNOUNCE_VALUE);
			builder.appendSql(" ) union all ");
			builder
					.appendSql("select FContractBillID,FAmount as fchangeAmount from T_CON_ContractChangeBill ");
			builder.appendSql("where FHasSettled=0 and ");
			builder.appendSql("FContractBillID=?");
			builder.addParam(this.editData.getContractId());
			builder.appendSql(" and (");
			builder.appendParam("FState", FDCBillStateEnum.AUDITTED_VALUE);
			builder.appendSql(" or ");
			builder.appendParam("FState", FDCBillStateEnum.VISA_VALUE);
			builder.appendSql(" or ");
			builder.appendParam("FState", FDCBillStateEnum.ANNOUNCE_VALUE);
			builder.appendSql(" )) change group by FContractBillID");

			IRowSet rowSet2 = builder.executeQuery();
			if (rowSet2.next()) {
				changeAmt = FDCHelper.toBigDecimal(rowSet2
						.getBigDecimal("changeAmount"));
			}

			latestPrice = FDCHelper.toBigDecimal(FDCHelper.add(contractBill
					.getAmount(), changeAmt), 2);
		}
		if (actualPayAmt.compareTo(latestPrice) == 1) {
			if (contractBill.isHasSettled() || isControlCost) {// 若合同已经最终结算或者启用了参数
				// “
				// 累计请款超过合同最新造价严格控制
				// ”
				FDCMsgBox
						.showError("合同实付款不能大于合同最新造价【合同实付款 =已关闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申请单合同内工程款合计】");
				SysUtil.abort();
			} else if (!contractBill.isHasSettled() && !isControlCost) {// 若合同还未最终结算且未启用参数
				// “
				// 累计请款超过合同最新造价严格控制
				// ”时
				FDCMsgBox
						.showWarning("合同实付款不能大于合同最新造价【合同实付款 =已关闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申请单合同内工程款合计】");
			}
		}
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		reSetCompleteAmt();
		// 保证在工作流中单据的状态不能改变by renliang 地下的判断方式真。。
		if (getUIContext().get("isFromWorkflow") != null
				&& getUIContext().get("isFromWorkflow").toString().equals(
						"true") && getOprtState().equals(OprtState.EDIT)
				&& actionSave.isVisible()) {

		} else {
			if (editData != null) {// 第一次保存时初始状态
				editData.setState(FDCBillStateEnum.SAVED);
			}
		}

		btnInputCollect_actionPerformed(null);
		super.actionSave_actionPerformed(e);
		kdtEntrys.getScriptManager().setAutoRun(true);
		kdtEntrys.getScriptManager().runAll();

		BOSUuid jectid = editData.getCurProject().getId();
		CurProjectInfo projectInfo = CurProjectFactory.getRemoteInstance()
				.getCurProjectInfo(new ObjectUuidPK(jectid));
		projectInfo.isQk();
		BigDecimal yb = editData.getAmount();
		BigDecimal zjjhSqje = editData.getZjjhSqje();
		if (projectInfo.isQk()) {
			if (yb.compareTo(zjjhSqje) > 0) {
				MsgBox.showWarning("本次付款申请单大于资金计划申请金额，不能提交，请修改。");
				SysUtil.abort();
			}
		} else {
			if (yb.compareTo(zjjhSqje) > 0) {
				MsgBox.showWarning("本次付款申请单大于资金计划申请金额.");
				contZjjhSqje.getBoundLabel().setForeground(Color.red);
			}else{
				contZjjhSqje.getBoundLabel().setForeground(Color.black);
			}
		}
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		reSetCompleteAmt();
		btnInputCollect_actionPerformed(null);
		if (isMoreSettlement) {
			check();
		}

		checkTempSmallerThanZero();
		// 检查拆分状态
		checkContractSplitState();
		// 为啥要在这里将单据状态显示地设置为"提交"呢？如果不设置为"提交",貌似合同内工程款保存会有误 by cassiel_peng
		// 2009-12-06
		editData.setState(FDCBillStateEnum.SUBMITTED);
		checkPrjPriceInConSettlePriceForSubmit();
		planAcctCtrl();
		this.checkConWorkLoad();

		if (prmtPlanUnCon.getValue() != null
				|| prmtPlanHasCon.getValue() != null) {
			setDepPlanState();
		}
		super.actionSubmit_actionPerformed(e);
		kdtEntrys.getScriptManager().setAutoRun(true);
		kdtEntrys.getScriptManager().runAll();
		pkbookedDate_dataChanged(null);
		if (getOprtState().equals(OprtState.ADDNEW)) {
			setEditableAndRequiredBySeparateParam();
		}
		if (prmtPlanUnCon.getValue() == null
				&& prmtPlanHasCon.getValue() == null) {
			kdDepPlanState.setSelectedItem(DepPlanStateEnum.noPlan);
		}

	}

	/**
	 * 
	 * 描述：
	 * 
	 * @param ctx
	 * @param billId
	 * @param model
	 * @throws BOSException
	 * @throws EASBizException
	 * @Author：jian_cao
	 * @CreateTime：2012-8-14
	 */
	private void checkConWorkLoad() throws Exception {

		PayRequestBillInfo bill = editData;
		String companyID = null;
		if (bill.getCurProject().getFullOrgUnit() != null) {
			companyID = bill.getCurProject().getFullOrgUnit().getId()
					.toString();// 取工程项目财务组织
		}
		boolean isSeperate = FDCUtils.getDefaultFDCParamByKey(null, companyID,
				FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT);
		// if (!isSeperate) {
		// return; //modified by ken_liu... 都提示。。。
		// }
		if (bill != null && bill.getContractId() != null) {
			boolean isConWithout = (new ContractBillInfo().getBOSType())
					.equals(BOSUuid.read(bill.getContractId()).getType());
			if (!isConWithout) {
				return;
			}
		}
		if (bill.getContractId() != null) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("isCoseSplit", Boolean.valueOf(true),
							CompareType.NOTEQUALS));
			filter.getFilterItems().add(
					new FilterItemInfo("id", bill.getContractId()));
			if (ContractBillFactory.getRemoteInstance().exists(filter))
				return;
		}

		boolean isCheckWorkLoad = true;
		// 合同类型确定是否做判断
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("id");
		sels.add("contractType.id");
		sels.add("contractType.isWorkLoadConfirm");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", bill.getContractId()));
		view.setSelector(sels);
		view.setFilter(filter);
		ContractBillCollection contract = ContractBillFactory
				.getRemoteInstance().getContractBillCollection(view);
		if (contract != null && contract.size() > 0) {
			if (!contract.get(0).getContractType().isIsWorkLoadConfirm()) {
				isCheckWorkLoad = false;
			}
		}
		// by tim_gao
		if (isCheckWorkLoad) {
			// 合同内工程款，是否大于累计确认完工工程量
			// 此处之前比较乱，将以前的都注释，重新做一个统一判断
			// 其中，如果是预付款类型的付款申请单，不做校验

			// 不包含付款类型为预付款的申请单 add 2012-08-31 周鹏需求
			if (bill.getPaymentType() != null
					&& bill.getPaymentType().getName().indexOf(YFK) < 0
					&& PaymentTypeInfo.progressID.equals(bill.getPaymentType()
							.getPayType().getId().toString())) {
				BigDecimal payAmt = FDCHelper.ZERO;
				FDCSQLBuilder builder = new FDCSQLBuilder();
				// modified by ken_liu...
				// 进度款累计请款金额取数：该合同下提交状态、审批中、已审批进度款付款申请单上“本次申请_本币”之和；
				// 工程量与付款分离参数启用后，累计完工确认金额取数：取该合同下所有已审批工程量确认单金额之和；
				// 工程量与付款分离参数不启用，取该合同下提交状态、审批中、已审批进度款付款申请单上“本期完工工程量”之和；
				builder
						.appendSql("select sum(t.famount) as famount,sum(t.workload) as workload from \n ");
				builder.appendSql("( \n ");
				builder
						.appendSql("(select pay.FCompletePrjAmt as workload, pay.FProjectPriceInContract as famount from T_Con_PayRequestBill as pay ");
				builder
						.appendSql("left join T_FDC_PaymentType as ptype on ptype.fid=pay.fPaymentType ");
				if (bill.getId() != null) {
					builder
							.appendSql("where pay.FHasClosed=0 and pay.FContractId=?  and ptype.fname_l2 ='进度款'  ");
					builder.appendSql("and pay.fid !='"
							+ bill.getId().toString() + "' ");
				} else {
					builder
							.appendSql("where pay.FHasClosed=0 and pay.FContractId=?  and ptype.fname_l2 ='进度款' ");
				}
				builder
						.appendSql("and pay.FState in ('2SUBMITTED', '3AUDITTING','4AUDITTED') )\n");

				builder.appendSql("union all\n ");
				builder
						.appendSql("(select req.FCompletePrjAmt as workload, pay.FProjectPriceInContract as famount from T_CAS_PaymentBill pay \n  ");
				builder
						.appendSql("inner join T_Con_PayRequestbill req on pay.FFdcPayReqID=req.FID \n ");
				builder
						.appendSql("and pay.FContractBillId=req.FContractId \n ");
				if (bill.getId() != null) {
					builder
							.appendSql("and req.FHasClosed=1 and req.FContractId=?  \n ");
					builder.appendSql("and req.fid !='"
							+ bill.getId().toString() + "'  \n ");
				} else {
					builder
							.appendSql("and req.FHasClosed=1 and req.FContractId=?  \n ");
				}
				builder
						.appendSql("inner join T_FDC_PaymentType as ptype on ptype.fid=req.FPaymentType and ptype.fname_l2='进度款' ");
				builder
						.appendSql("and req.FState in ('2SUBMITTED', '3AUDITTING','4AUDITTED') )");

				builder.appendSql(") t \n ");// sql server 加别名
				builder.addParam(bill.getContractId());
				builder.addParam(bill.getContractId());
				IRowSet rs = builder.executeQuery();

				BigDecimal workLoad = FDCHelper.ZERO;
				try {
					if (rs.next()) {
						// 以前所有的合同内工程款
						payAmt = FDCHelper.toBigDecimal(rs
								.getBigDecimal("famount"));
						workLoad = FDCHelper.toBigDecimal(rs
								.getBigDecimal("workload"));
					}
				} catch (SQLException e) {
					logger.error(e);
					handUIExceptionAndAbort(e);
				}
				// if (this.getOprtState().equals(OprtState.ADDNEW)) {
				// 当前是新增时，把当前单据金额也加上一起判断(由于不是预付款，取本位币和取合同内工程款一样)

				/* modified by zhaoqin for R140227-0281 on 2014/03/20 start */
				// 应取"本次申请本币"
				// payAmt = FDCHelper.add(payAmt,
				// FDCHelper.toBigDecimal(getDetailTable().getCell(rowIndex,
				// columnIndex).getValue()));
				payAmt = FDCHelper.add(payAmt, FDCHelper
						.toBigDecimal(getDetailTable().getCell(rowIndex,
								columnIndex + 1).getValue()));
				/* modified by zhaoqin for R140227-0281 on 2014/03/20 end */

				workLoad = FDCHelper.add(workLoad, bill.getCompletePrjAmt());
				// }
				// 用前面合计的金额与累计确认工程量比较，大于则不允许提交或审批

				if (isSeperate) {
					workLoad = WorkLoadConfirmBillFactory.getRemoteInstance()
							.getWorkLoad(bill.getContractId());
				}

				if (FDCHelper.toBigDecimal(payAmt, 2).compareTo(
						FDCHelper.toBigDecimal(workLoad, 2)) > 0) {
					// throw new
					// ContractException(ContractException.MORETHANCOMPLETEPRJAMT
					// );
					/*
					 * int code =
					 * FDCMsgBox.showConfirm2("进度款请款的累计金额超过完工确认的累计金额，请确认是否继续此操作?"
					 * ); if (code != FDCMsgBox.OK) { SysUtil.abort(); }
					 */

					/* modified by zhaoqin for R140227-0281 on 2014/03/21 start */
					CurrencyInfo cur = (CurrencyInfo) this.prmtcurrency
							.getValue();
					if (!cur.getId().toString().equals(
							baseCurrency.getId().toString())) {
						Object lstPriceOriAmt = getDetailTable().getCell(1, 7)
								.getValue();
						Object lstReqOriAmt = getDetailTable().getCell(5, 8)
								.getValue();
						if (FDCHelper.toBigDecimal(lstReqOriAmt, 2).compareTo(
								FDCHelper.toBigDecimal(lstPriceOriAmt, 2)) <= 0) {
							return;
						}
					}
					/* modified by zhaoqin for R140227-0281 on 2014/03/21 end */

					FDCMsgBox
							.showWarning(this, "进度款累计请款金额超过累计完工工程量金额，不能提交/审批！");
					SysUtil.abort();
				}
			}

		}
	}

	private SelectorItemCollection getSic() {
		// 此过滤为详细信息定义
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("period.id"));
		sic.add(new SelectorItemInfo("period.beginDate"));
		sic.add(new SelectorItemInfo("curProject.fullOrgUnit.id"));
		sic.add(new SelectorItemInfo("curProject.id"));
		sic.add(new SelectorItemInfo("curProject.displayName"));
		sic.add(new SelectorItemInfo("contractId"));
		sic.add(new SelectorItemInfo("amount"));
		sic.add(new SelectorItemInfo("projectPriceInContract"));
		sic.add(new SelectorItemInfo("confirmEntry.*"));
		sic.add(new SelectorItemInfo("payDate"));
		sic.add(new SelectorItemInfo("paymentType.id"));
		sic.add(new SelectorItemInfo("paymentType.name"));
		return sic;
	}

	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		if (!getOprtState().equals(OprtState.VIEW)) {
			if (editData.getState() == null) {
				editData.setState(FDCBillStateEnum.SAVED);
			}
			storeFields();
		}
		if (editData == null || StringUtils.isEmpty(editData.getString("id"))) {
			FDCMsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
					"cantPrint"));
			return;
		}

		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.print("/bim/fdc/finance/payrequest",
				new PayRequestBillRowsetProvider(cloneEditDataForPrint(),
						getTDQueryPK(), bindCellMap, curProject, contractBill),
				SwingUtilities.getWindowAncestor(this));
	}

	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK(
				"com.kingdee.eas.fdc.contract.app.PayRequestBillPrintQuery");
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		if (!getOprtState().equals(OprtState.VIEW)) {
			if (editData.getState() == null) {
				editData.setState(FDCBillStateEnum.SAVED);
			}
			storeFields();
		}
		if (editData == null || StringUtils.isEmpty(editData.getString("id"))) {
			FDCMsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
					"cantPrint"));
			return;
		}

		KDNoteHelper appHlp = new KDNoteHelper();
		editData.setBoolean("isCompletePrjAmtVisible", contcompletePrjAmt
				.isVisible());
		editData.setBigDecimal("allCompletePrjAmt", txtAllCompletePrjAmt
				.getBigDecimalValue());
		appHlp.printPreview("/bim/fdc/finance/payrequest",
				new PayRequestBillRowsetProvider(cloneEditDataForPrint(),
						getTDQueryPK(), bindCellMap, curProject, contractBill),
				SwingUtilities.getWindowAncestor(this));
	}

	/**
	 * 将当前的editData克隆一份，供打印或打印预览使用。
	 * 有些字段界面上已经有了，直接放到editDataForPrint中，Query中已经指定了相应的字段
	 * 
	 * @return editDataForPrint 待套打的数据
	 * @Author：owen_wen
	 * @CreateTime：2012-9-27
	 */
	private PayRequestBillInfo cloneEditDataForPrint() {
		PayRequestBillInfo editDataForPrint = (PayRequestBillInfo) editData
				.clone();
		editDataForPrint.setAllInvoiceAmt(this.txtAllInvoiceAmt
				.getBigDecimalValue()); // 为了让套打显示 的“累计发票金额”与界面一致。
		editDataForPrint.setPayedAmt(totalPayAmtByReqId); // 为了让套打显示的“本申请单已付金额”
															// 与界面一致
		editDataForPrint.setLatestPrice((BigDecimal) ((ICell) bindCellMap
				.get(PayRequestBillContants.LATESTPRICE)).getValue());
		editDataForPrint.put(PayRequestBillContants.CURREQPERCENT,
				((ICell) bindCellMap.get(PayRequestBillContants.CURREQPERCENT))
						.getValue());
		editDataForPrint.put(PayRequestBillContants.ALLREQPERCENT,
				((ICell) bindCellMap.get(PayRequestBillContants.ALLREQPERCENT))
						.getValue());

		/* modified by zhaoqin for R131202-0278 start */
		// 工程付款情况表上的某些字段的取值是通过计算得来的,这里对这些字段重新赋值
		Iterator iter = bindCellMap.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			editDataForPrint
					.put(key, ((ICell) bindCellMap.get(key)).getValue());
		}

		// 合同内工程款_截至上次累计实付_原币
		editDataForPrint.put("lstPrjAllPaidOriAmt", kdtEntrys.getCell(5, 2)
				.getValue());
		// 合同内工程款_截至上次累计实付_本币
		editDataForPrint.put("lstPrjAllPaidAmt", kdtEntrys.getCell(5, 3)
				.getValue());
		// 合同内工程款_截至本次累计实付_原币
		editDataForPrint.put("prjAllPaidOriAmt", kdtEntrys.getCell(5, 10)
				.getValue());
		// 合同内工程款_截至本次累计实付_本币
		editDataForPrint.put("prjAllPaidAmt", kdtEntrys.getCell(5, 11)
				.getValue());
		/* modified by zhaoqin for R131202-0278 end */

		return editDataForPrint;
	}

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
				if (!isSimpleFinancial) {
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
			tableHelper.updateDynamicValue(editData, contractBill,
					contractChangeBillCollection, paymentBillCollection);
			tableHelper.reloadDeductTable(editData, getDetailTable(),
					deductTypeCollection);
			tableHelper.updateGuerdonValue(editData, editData.getContractId(),
					guerdonOfPayReqBillCollection, guerdonBillCollection);
			tableHelper.updateCompensationValue(editData, editData
					.getContractId(), compensationOfPayReqBillCollection);

			reloadPartADeductDetails();
			((ICell) bindCellMap
					.get(PayRequestBillContants.PROJECTPRICEINCONTRACT))
					.setValue(null);
			((ICell) bindCellMap
					.get(PayRequestBillContants.PROJECTPRICEINCONTRACTORI))
					.setValue(null);
			if (this.isAdvance()) {
				((ICell) bindCellMap.get(PayRequestBillContants.ADVANCE))
						.setValue(null);
				((ICell) bindCellMap.get(PayRequestBillContants.LOCALADVANCE))
						.setValue(null);
			}
			((ICell) bindCellMap.get(PayRequestBillContants.ADDPROJECTAMT))
					.setValue(null);
			((ICell) bindCellMap.get(PayRequestBillContants.PAYPARTAMATLAMT))
					.setValue(null);

			if (isView) {
				setTableCellColorAndEdit();
			}

			/* modified by zhaoqin for R140421-0018 on 2014/04/14 */
			getContractLastAmt();
		}
		// txtcapitalAmount.setEditable(false);
		btnSave.setEnabled(true);
		txtAmount.setValue(null);
		// this.mergencyState.setSelected(false);
		chkIsPay.setSelected(editData.isIsPay());
		editData.setSourceType(SourceTypeEnum.ADDNEW);
		setEditState();
		if (isFromProjectFillBill) {
			if (getOprtState().equals(OprtState.ADDNEW)) {
				fromProjectFill();
			}
			// txtcompletePrjAmt.setValue(FDCHelper.ZERO);
			// txtpaymentProportion.setValue(FDCHelper.ZERO);
			txtpaymentProportion.setEditable(true);
			txtcompletePrjAmt.setEditable(false);
		}
		// Add by zhiyuan_tang 2010/07/30 处理在查看界面点修改，本次申请原币可用的BUG
		if (isPartACon) {
			kdtEntrys.getCell(rowIndex, columnIndex).getStyleAttributes()
					.setLocked(true);
		}

		updateCompletePrjAmt();
	}

	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
	}

	public void actionInsertLine_actionPerformed(ActionEvent e)
			throws Exception {
	}

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
			if (this.isAdvance()) {
				((ICell) bindCellMap.get(PayRequestBillContants.ADVANCE))
						.getStyleAttributes().setLocked(false);
				((ICell) bindCellMap.get(PayRequestBillContants.ADVANCE))
						.getStyleAttributes().setBackground(Color.WHITE);
			}
			btnInputCollect.setEnabled(true);
			kdtEntrys.setEditable(true);
			kdtEntrys.setEnabled(true);

			setEditableAndRequiredBySeparateParam();
		}

		if (getOprtState().equals(OprtState.VIEW)) {
			if (null != this.editData && null != this.editData.getPaymentType()
					&& this.editData.getPaymentType().toString().equals(YFK)
					|| isSeparate) {
				this.txtpaymentProportion.setRequired(false);
				this.txtcompletePrjAmt.setRequired(false);
			}
		}

	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		checkContractSplitState();
		this.checkParamForAddNew();
		boolean isView = (getOprtState().equals(OprtState.VIEW));
		CurrencyInfo currency = editData.getCurrency();
		SupplierInfo realSupplier = editData.getRealSupplier();

		confirmAmts = FDCHelper.ZERO;

		((ICell) bindCellMap
				.get(PayRequestBillContants.PROJECTPRICEINCONTRACTORI))
				.getStyleAttributes().setLocked(false);

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
				 * FDCMsgBox.showWarning(this,"你选择的是本位币，但是汇率不等于1"); }
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
			// tableHelper.reloadGuerdonValue(editData, null);
			tableHelper.reloadCompensationValue(editData, null);
			if (partAParam) {
				tableHelper.reloadPartAValue(editData, null);
			} else {
				tableHelper.reloadPartAConfmValue(editData, null);
			}
			((ICell) bindCellMap
					.get(PayRequestBillContants.PROJECTPRICEINCONTRACT))
					.setValue(null);
			((ICell) bindCellMap
					.get(PayRequestBillContants.PROJECTPRICEINCONTRACTORI))
					.setValue(null);
			if (this.isAdvance()) {
				// tableHelper.updateLstAdvanceAmt(editData, false);
				((ICell) bindCellMap.get(PayRequestBillContants.ADVANCE))
						.setValue(null);
				((ICell) bindCellMap.get(PayRequestBillContants.LOCALADVANCE))
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
			FDCMsgBox.showInfo("当前付款申请单关联了无文本合同。无文本合同付款申请单不能直接生成，它由无文本合同自动生成");
			SysUtil.abort();
		}
		// txtcapitalAmount.setEditable(false);
		btnSave.setEnabled(true);
		deductUIwindow = null;

		this.chkIsPay.setEnabled(true);
		this.mergencyState.setEnabled(true);
		prmtsupplier.setEnabled(false);
		txtcapitalAmount.setEnabled(false);

		if (isFromProjectFillBill) {

			if (getOprtState().equals(OprtState.ADDNEW)) {
				fromProjectFill();
			}
			txtcompletePrjAmt.setEditable(false);
			if (FDCHelper.toBigDecimal(txtcompletePrjAmt.getBigDecimalValue())
					.compareTo(FDCHelper.ZERO) == 0) {
				txtpaymentProportion.setEditable(false);
			}
		}
		// 部分结算时，因结算值没带出不能提交
		if (getOprtState().equals(OprtState.EDIT)
				|| getOprtState().equals(OprtState.ADDNEW)) {
			try {
				Map param = new HashMap();
				param.put("ContractBillId", contractBill.getId().toString());
				Map totalSettle = ContractFacadeFactory.getRemoteInstance()
						.getTotalSettlePrice(param);
				if (totalSettle != null) {
					editData.setTotalSettlePrice((BigDecimal) totalSettle
							.get("SettlePrice"));
				} else {
					editData.setTotalSettlePrice(FDCConstants.ZERO);
				}
			} catch (Exception e1) {
				handUIExceptionAndAbort(e1);
			}
			this.txtTotalSettlePrice.setValue(editData.getTotalSettlePrice());
		}

		updateCompletePrjAmt();

		// Add by zhiyuan_tang 2010/07/30 处理在查看界面点修改，本次申请原币可用的BUG
		if (isPartACon) {
			kdtEntrys.getCell(rowIndex, columnIndex).getStyleAttributes()
					.setLocked(true);
		}

		setEditableAndRequiredBySeparateParam();

		if (prmtPlanUnCon.getValue() == null
				&& prmtPlanHasCon.getValue() == null) {
			kdDepPlanState.setSelectedItem(DepPlanStateEnum.noPlan);
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		this.kdtEntrys.getCell(rowIndex, columnIndex).getStyleAttributes()
				.setBackground(FDCColorConstants.requiredColor);
		super.actionEdit_actionPerformed(e);

		// R110530-0639: 查看点修改，付款帐号值被清空了
		txtrecAccount.setText(editData.getRecAccount());
		/*
		 * if(editData.getState()!=null&&!editData.getState().equals(FDCBillStateEnum
		 * .SAVED)){ btnSave.setEnabled(false); }
		 */
		setEditState();
		// 付款单为暂估款类型时其他可录入金额字段仅发票金额可录入(对应的合同内工程款等其他款项不可录入)
		Object obj = prmtPayment.getValue();
		if (obj != null && obj instanceof PaymentTypeInfo) {
			String tempID = PaymentTypeInfo.tempID;// 暂估款
			PaymentTypeInfo type = (PaymentTypeInfo) obj;
			if (type.getPayType().getId().toString().equals(tempID)) {
				this.kdtEntrys.setEnabled(true);
				if (this.kdtEntrys.getCell(4, 4) != null) {
					this.kdtEntrys.getCell(4, 4).getStyleAttributes()
							.setLocked(true);
				}
			}
		}
		prmtsupplier.setEditable(false);
		prmtsupplier.setEnabled(false);
		txtcapitalAmount.setEditable(false);
		prmtcurrency.setEnabled(false);
		setAmount();

		PaymentTypeInfo type = this.editData.getPaymentType();
		if (type != null
				&& !type.getPayType().getId().toString().equals(
						PaymentTypeInfo.progressID)) {
			this.txtpaymentProportion.setEditable(false);
			this.txtcompletePrjAmt.setEditable(false);
			if (isSimpleFinancial && contractBill != null
					&& contractBill.isHasSettled()) {
				this.txtpaymentProportion.setEditable(true);
				this.txtcompletePrjAmt.setEditable(true);
			}
		}
		if (isFromProjectFillBill) {
			txtcompletePrjAmt.setEditable(false);
			kdtEntrys.getCell(rowIndex, columnIndex).getStyleAttributes()
					.setLocked(true);
			if (FDCHelper.toBigDecimal(txtcompletePrjAmt.getBigDecimalValue())
					.compareTo(FDCHelper.ZERO) == 0) {
				kdtEntrys.getCell(rowIndex, columnIndex).getStyleAttributes()
						.setLocked(false);
				txtpaymentProportion.setEditable(false);
			}
		}

		deductUIwindow = null;

		if (null != this.editData && null != this.editData.getPaymentType()
				&& this.editData.getPaymentType().toString().equals(YFK)
				|| isSeparate) {
			this.txtpaymentProportion.setRequired(false);
			this.txtcompletePrjAmt.setRequired(false);
		} else {
			this.contcompletePrjAmt.setEnabled(true);
			this.txtpaymentProportion.setRequired(true);
			this.txtcompletePrjAmt.setRequired(true);
		}

		setPaymentProprotionAndCompletePrjAmtEnabled();

		setTxtEnable(type);

		// 进度款时,非工程类合同的付款申请单，本期完工工程量和进度款付款比例应该灰显，并且去掉计算逻辑（本次申请金额= 本期完工工程量*
		// 进度付款比例%）；

		if (obj instanceof PaymentTypeInfo) {
			PaymentTypeInfo typeinfo = (PaymentTypeInfo) obj;
			if (contractBill != null
					&& contractBill.getContractType() != null
					&& PaymentTypeInfo.progressID.equals(typeinfo.getPayType()
							.getId().toString())) {

				ContractTypeInfo contractType = contractBill.getContractType();
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("isWorkLoadConfirm");
				try {
					ContractTypeInfo contractTypeInfo = ContractTypeFactory
							.getRemoteInstance().getContractTypeInfo(
									new ObjectUuidPK(contractType.getId()
											.toString()), sic);
					if (!contractTypeInfo.isIsWorkLoadConfirm()) {
						txtpaymentProportion.setEnabled(false);
						txtcompletePrjAmt.setEnabled(false);
						txtpaymentProportion.setRequired(false);
						txtcompletePrjAmt.setRequired(false);
					}
				} catch (EASBizException e2) {
					handUIExceptionAndAbort(e2);
				} catch (BOSException e2) {
					handUIExceptionAndAbort(e2);
				}

			}
		}
		if (prmtPlanUnCon.getValue() != null
				|| prmtPlanHasCon.getValue() != null) {
			setDepPlanState();
		}

		/* modified by skyiter_wang for R140127-0065 on 2014/01/27 */
		// 计算分录原币
		addOrgPriceForEntryTable(kdtEntrys, bindCellMap, contractBill);
	}

	private void setTxtEnable_Old(PaymentTypeInfo type) throws EASBizException,
			BOSException, Exception {
		if (type != null) {
			/*
			 * 1.付款保修款时，进度款支付比例、本期完工工程量默认为零、可编辑。
			 * 期望：此场景下，付保修款时，进度款支付比例、本期完工工程量默认为零、不可编辑。
			 */
			if (type.getPayType().getId().toString().equals(
					PaymentTypeInfo.keepID)) {
				txtpaymentProportion.setEditable(false);
				txtpaymentProportion.setRequired(false);
				// 下行改为不触发txtpaymentProportion的dataChange事件，因为触发的话，
				// 会用0填充累计完工和本期合同内工程款
				// Added by Owen_wen 2013-01-05
				txtpaymentProportion.setValue(FDCHelper.ZERO, false);
				txtcompletePrjAmt.setEditable(false);
				txtcompletePrjAmt.setValue(FDCHelper.ZERO, false); // 同上两行
				txtcompletePrjAmt.setRequired(false);
			}

			if (type.getPayType().getId().toString().equals(
					PaymentTypeInfo.settlementID)) {
				// 简单模式，合同结算，完工与比例可修改
				if (contractBill != null && contractBill.isHasSettled()
						&& isSimpleFinancial) {
					txtcompletePrjAmt.setEnabled(false);
					// 下行改为不触发txtpaymentProportion的dataChange事件，因为触发的话，
					// 会用0填充累计完工和本期合同内工程款
					// Added by Owen_wen 2013-01-05
					txtpaymentProportion.setValue(FDCHelper.ZERO, false);
					txtpaymentProportion.setEnabled(false);

					setTxtcompletePrjAmtValue();
				} else {
					txtcompletePrjAmt.setEditable(false);
					txtcompletePrjAmt.setValue(FDCHelper.toBigDecimal(editData
							.getSettleAmt()));
					txtpaymentProportion.setEditable(false);
					txtpaymentProportion.setRequired(false);
				}
			}
		}

	}

	public void actionAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		if (OprtState.ADDNEW.equals(getOprtState())) {
			FDCMsgBox.showWarning(this, getRes("beforeAttachment"));
			SysUtil.abort();
		}
		super.actionAttachment_actionPerformed(e);

		fillAttachmnetList();
	}

	/*
	 * 几个添加的工具栏按钮
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkContractSplitState();
		// super.actionAudit_actionPerformed(e);
		if (isMoreSettlement) {
			check();
		}

		checkBeforeAuditOrUnAudit(FDCBillStateEnum.SUBMITTED, "cantAudit");
		checkPrjPriceInConSettlePriceForSubmit();
		checkAmt(editData);
		// auditAndOpenPayment()代替audit()取得付款单的BOSUudi eric_wang 2010.05.19
		BOSUuid billId = PayRequestBillFactory.getRemoteInstance()
				.auditAndOpenPayment(this.editData.getId());
		// PayRequestBillFactory.getRemoteInstance().audit(editData.getId());
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
		// 打开付款单 eric_wang 2010.05.19
		try {
			if (isOpenPaymentBillEditUI()) {
				UIContext uiContext = new UIContext(this);
				if (billId != null) {
					// 取消弹出框 by Eric_Wang 2010.05.21
					// int result = FDCMsgBox.showConfirm2New(null,
					// "已经生成对应的付款单，是否打开付款单？");
					// if (JOptionPane.YES_OPTION == result) {
					uiContext.put(UIContext.ID, billId);
					IUIFactory uiFactory = null;
					uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
					IUIWindow dialog = uiFactory.create(PaymentBillEditUI.class
							.getName(), uiContext, null, OprtState.EDIT);
					dialog.show();
					// }
				}
			}
		} catch (Throwable e1) {
			this.handUIException(e1);
		}

	}

	/**
	 * 增加参数FDC801_ISOPENPAYMENTEDITUI是否开启判断 eric_wang 2010.05.19
	 * 
	 * @return
	 */
	private boolean isOpenPaymentBillEditUI() {
		boolean isOpenPaymentBillEditUI = false;
		try {
			isOpenPaymentBillEditUI = FDCUtils.getDefaultFDCParamByKey(null,
					null, FDCConstants.FDC_PARAM_ISOPENPAYMENTEDITUI);
		} catch (EASBizException e) {
			handUIExceptionAndAbort(e);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
		return isOpenPaymentBillEditUI;
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		if (FDCBillStateEnum.AUDITTING.equals(editData.getState())
				&& STATUS_FINDVIEW.endsWith(getOprtState())
				&& editData.getId() != null
				&& FDCUtils.isRunningWorkflow(editData.getId().toString())) {
			PayRequestBillFactory.getRemoteInstance().setUnAudited2Auditing(
					editData.getId());
			FDCMsgBox.showWarning("工作流中反审批至审批中状态成功！");
			destroyWindow();
			return;
		}
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

	private void prmtPayment_dataChanged(DataChangeEvent eventObj)
    {
        Object obj = eventObj.getNewValue();
        Object oldObj = eventObj.getOldValue();
        if(obj != null && (obj instanceof PaymentTypeInfo))
        {
            PaymentTypeInfo _type = (PaymentTypeInfo)obj;
            if(!_type.getPayType().getId().toString().equals("Bd2bh+CHRDenvdQS3D72ouwp3Sw=") && kdtEntrys.getCell(4, 4) != null && !isPartACon)
                kdtEntrys.getCell(4, 4).getStyleAttributes().setLocked(false);
        }
        if(prmtPayment.getUserObject() != null && prmtPayment.getUserObject().equals("noExec"))
            return;
        if((obj instanceof PaymentTypeInfo) && isRealizedZeroCtrl)
        {
            PaymentTypeInfo type = (PaymentTypeInfo)obj;
            if(FDCHelper.isNullZero(txtTotalSettlePrice.getBigDecimalValue()) && type.getName() != null && !type.getName().equals("\u9884\u4ED8\u6B3E"))
            {
                java.util.EventListener listeners[] = prmtPayment.getListeners(com.kingdee.bos.ctrl.swing.event.DataChangeListener.class);
                for(int i = 0; i < listeners.length; i++)
                    prmtPayment.removeDataChangeListener((DataChangeListener)listeners[i]);

                prmtPayment.setData(eventObj.getOldValue());
                FDCMsgBox.showError(prmtPayment, "\u5DF2\u5B9E\u73B0\u4EA7\u503C\u4E3A0\u53EA\u5141\u8BB8\u9009\u62E9\"\u9884\u4ED8\u6B3E\"\uFF01");
                for(int i = 0; i < listeners.length; i++)
                    prmtPayment.addDataChangeListener((DataChangeListener)listeners[i]);

                setCellEnabled(oldObj);
                SysUtil.abort();
            }
        }
        if(obj instanceof PaymentTypeInfo)
            try
            {
                String settlementID = "Ga7RLQETEADgAAC/wKgOlOwp3Sw=";
                String progressID = "Ga7RLQETEADgAAC6wKgOlOwp3Sw=";
                String keepID = "Ga7RLQETEADgAADDwKgOlOwp3Sw=";
                String tempID = "Bd2bh+CHRDenvdQS3D72ouwp3Sw=";
                PaymentTypeInfo type = (PaymentTypeInfo)obj;
                if(type.getPayType().getId().toString().equals(tempID))
                    if(!isSimpleInvoice)
                    {
                        prmtPayment.setValue(null);
                        prmtPayment.setText(null);
                        FDCMsgBox.showWarning("\u542F\u7528\u201C\u8D22\u52A1\u5E10\u52A1\u4EE5\u53D1\u7968\u91D1\u989D\u4E3A\u51C6\u8BA1\u5F00\u53D1\u6210\u672C\u201D\u53C2\u6570\u540E\u624D\u80FD\u9009\u62E9\u672C\u7C7B\u522B\u7684\u4ED8\u6B3E\u7C7B\u578B\uFF0C\u8BF7\u5148\u542F\u7528\u5BF9\u5E94\u53C2\u6570\uFF01");
                        SysUtil.abort();
                    } else
                    {
                        kdtEntrys.getStyleAttributes().setLocked(true);
                        if(kdtEntrys.getCell(4, 4) != null)
                        {
                            kdtEntrys.getCell(4, 4).setValue(null);
                            kdtEntrys.getCell(4, 5).setValue(null);
                            kdtEntrys.getCell(4, 4).getStyleAttributes().setLocked(true);
                        }
                    }
                FilterInfo filter;
                if(type.getName().toString().equals("预付款"))
                {
                    filter = new FilterInfo();
                    filter.appendFilterItem("paymentType.name", "\u9884\u4ED8\u6B3E");
                    filter.appendFilterItem("contractId", editData.getContractId());
                    EntityViewInfo evi = new EntityViewInfo();
                    evi.setFilter(filter);
                    PayRequestBillCollection cols = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(evi);
                    int number = getAdvancePaymentNumber();
                    if(!OprtState.EDIT.equals(getOprtState()) && cols.size() >= number)
                    {
                        FDCMsgBox.showWarning((new StringBuilder()).append("\u4E00\u4E2A\u5408\u540C\u53EA\u5141\u8BB8\u5F55").append(number).append("\u6B21\u9884\u4ED8\u6B3E.").toString());
                        prmtPayment.setData(null);
                        setCellEnabled(oldObj);
                        abort();
                    }
                    txtpaymentProportion.setValue(FDCHelper.ZERO, false);
                    txtpaymentProportion.setRequired(false);
                    txtpaymentProportion.setEnabled(false);
                    txtcompletePrjAmt.setValue(FDCHelper.ZERO, false);
                    txtcompletePrjAmt.setRequired(false);
                    txtcompletePrjAmt.setEnabled(false);
                } else
                {
                    txtpaymentProportion.setValue(paymentProportionValue, false);
                    txtcompletePrjAmt.setValue(completePrjAmtValue, false);
                    if(type.getPayType().getId().toString().equals(progressID))
                    {
                        BigDecimal originalAmount = FDCHelper.divide(FDCHelper.multiply(completePrjAmtValue, paymentProportionValue), FDCHelper.ONE_HUNDRED, 2, 4);
                        if(getConfirmAmts().compareTo(FDCHelper.ZERO) == 0)
                        {
                            kdtEntrys.getCell(rowIndex, columnIndex).setValue(originalAmount);
                            setLocalAmountOfThisTime(originalAmount);
                            caculatePaymentProp();
                        }
                    }
                    txtpaymentProportion.setRequired(true);
                    txtpaymentProportion.setEnabled(true);
                    txtcompletePrjAmt.setRequired(!isSeparate);
                    txtcompletePrjAmt.setEnabled(!isSeparate);
                }
                originalAmount = new FilterInfo();
                originalAmount.appendFilterItem("id", editData.getContractId());
                originalAmount.appendFilterItem("sourceBillId", null);// add by wp  -- 不校验从分包合同过来的付款申请
                originalAmount.appendFilterItem("hasSettled", Boolean.TRUE);
                if(type.getPayType().getId().toString().equals(keepID) && !ContractBillFactory.getRemoteInstance().exists(originalAmount))
                {
                    java.util.EventListener listeners[] = prmtPayment.getListeners(com.kingdee.bos.ctrl.swing.event.DataChangeListener.class);
                    for(int i = 0; i < listeners.length; i++)
                        prmtPayment.removeDataChangeListener((DataChangeListener)listeners[i]);

                    prmtPayment.setData(eventObj.getOldValue());
                    FDCMsgBox.showError(prmtPayment, "\u5408\u540C\u7ED3\u7B97\u540E\u624D\u80FD\u4ED8\u4FDD\u4FEE\u6B3E");
                    for(int i = 0; i < listeners.length; i++)
                        prmtPayment.addDataChangeListener((DataChangeListener)listeners[i]);

                    setCellEnabled(oldObj);
                    SysUtil.abort();
                }
                originalAmount = new FilterInfo();
                originalAmount.appendFilterItem("paymentType.payType.id", settlementID);
                originalAmount.appendFilterItem("sourceBillId", null);// add by wp  -- 不校验从分包合同过来的付款申请
                originalAmount.appendFilterItem("contractId", editData.getContractId());
                if(type.getPayType().getId().toString().equals(progressID))
                    if(PayRequestBillFactory.getRemoteInstance().exists(originalAmount))
                    {
                        java.util.EventListener listeners[] = prmtPayment.getListeners(com.kingdee.bos.ctrl.swing.event.DataChangeListener.class);
                        for(int i = 0; i < listeners.length; i++)
                            prmtPayment.removeDataChangeListener((DataChangeListener)listeners[i]);

                        prmtPayment.setData(eventObj.getOldValue());
                        FDCMsgBox.showError(prmtPayment, "\u4ED8\u5B8C\u7ED3\u7B97\u6B3E\u540E\u4E0D\u80FD\u4ED8\u8FDB\u5EA6\u6B3E,\u5373\u5B58\u5728\u4ED8\u6B3E\u7C7B\u578B\u7684\u7C7B\u578B\u4E3A\u201C\u7ED3\u7B97\u6B3E\u201D\u7684\u4ED8\u6B3E\u7533\u8BF7\u5355\u65F6\u4E0D\u80FD\u9009\u62E9\u201C\u8FDB\u5EA6\u6B3E\u201D\u7C7B\u578B\u4ED8\u6B3E\u7C7B\u578B");
                        for(int i = 0; i < listeners.length; i++)
                            prmtPayment.addDataChangeListener((DataChangeListener)listeners[i]);

                        setCellEnabled(oldObj);
                        SysUtil.abort();
                    } else
                    {
                        FilterInfo myfilter = new FilterInfo();
                        myfilter.appendFilterItem("id", editData.getContractId());
                        myfilter.appendFilterItem("sourceBillId", null);// add by wp  -- 不校验从分包合同过来的付款申请
                        myfilter.appendFilterItem("hasSettled", Boolean.TRUE);
                        if(ContractBillFactory.getRemoteInstance().exists(myfilter))
                        {
                            java.util.EventListener listeners[] = prmtPayment.getListeners(com.kingdee.bos.ctrl.swing.event.DataChangeListener.class);
                            for(int i = 0; i < listeners.length; i++)
                                prmtPayment.removeDataChangeListener((DataChangeListener)listeners[i]);

                            prmtPayment.setData(eventObj.getOldValue());
                            FDCMsgBox.showError(prmtPayment, "\u5408\u540C\u7ED3\u7B97\u4E4B\u540E\u4E0D\u80FD\u4ED8\u8FDB\u5EA6\u6B3E\uFF01");
                            for(int i = 0; i < listeners.length; i++)
                                prmtPayment.addDataChangeListener((DataChangeListener)listeners[i]);

                            setCellEnabled(oldObj);
                            SysUtil.abort();
                        }
                    }
                if(type.getPayType().getId().toString().equals(settlementID))
                {
                    FilterInfo myfilter = new FilterInfo();
                    myfilter.appendFilterItem("id", editData.getContractId());
                    myfilter.appendFilterItem("sourceBillId", null);// add by wp  -- 不校验从分包合同过来的付款申请
                    myfilter.appendFilterItem("hasSettled", Boolean.TRUE);
                    if(!ContractBillFactory.getRemoteInstance().exists(myfilter))
                    {
                        java.util.EventListener listeners[] = prmtPayment.getListeners(com.kingdee.bos.ctrl.swing.event.DataChangeListener.class);
                        for(int i = 0; i < listeners.length; i++)
                            prmtPayment.removeDataChangeListener((DataChangeListener)listeners[i]);

                        prmtPayment.setData(eventObj.getOldValue());
                        FDCMsgBox.showError(prmtPayment, "\u5408\u540C\u5FC5\u987B\u7ED3\u7B97\u4E4B\u540E\u624D\u80FD\u505A\u7ED3\u7B97\u6B3E\u7C7B\u522B\u7684\u4ED8\u6B3E\u7533\u8BF7\u5355");
                        for(int i = 0; i < listeners.length; i++)
                            prmtPayment.addDataChangeListener((DataChangeListener)listeners[i]);

                        setCellEnabled(oldObj);
                        SysUtil.abort();
                    }
                    txtpaymentProportion.setValue(FDCConstants.ZERO);
                }
                if(!type.getName().toString().equals("\u9884\u4ED8\u6B3E"))
                {
                    txtpaymentProportion.setRequired(txtpaymentProportion.isVisible() && !isAutoComplete);
                    txtpaymentProportion.setEditable(!isAutoComplete);
                }
                setTxtEnable(type);
            }
            catch(Exception e) {
                handUIExceptionAndAbort(e);
            }
        if(isFromProjectFillBill){
            txtcompletePrjAmt.setEditable(false);
            if(FDCHelper.toBigDecimal(txtcompletePrjAmt.getBigDecimalValue()).compareTo(FDCHelper.ZERO) == 0)
                txtpaymentProportion.setEditable(false);
        }
        if(obj instanceof PaymentTypeInfo){
            PaymentTypeInfo type = (PaymentTypeInfo)obj;
            if(contractBill != null && contractBill.getContractType() != null && "Ga7RLQETEADgAAC6wKgOlOwp3Sw=".equals(type.getPayType().getId().toString()))
            {
                ContractTypeInfo contractType = contractBill.getContractType();
                SelectorItemCollection sic = new SelectorItemCollection();
                sic.add("isWorkLoadConfirm");
                try{
                    ContractTypeInfo contractTypeInfo = ContractTypeFactory.getRemoteInstance().getContractTypeInfo(new ObjectUuidPK(contractType.getId().toString()), sic);
                    if(!contractTypeInfo.isIsWorkLoadConfirm()) {
                        txtpaymentProportion.setEnabled(false);
                        txtcompletePrjAmt.setEnabled(false);
                        txtpaymentProportion.setRequired(false);
                        txtcompletePrjAmt.setRequired(false);
                    } else
                    if(isSeparate && FDCHelper.toBigDecimal(txtcompletePrjAmt.getBigDecimalValue()).compareTo(FDCHelper.ZERO) == 0){
                        if(isAutoComplete)
                            txtpaymentProportion.setValue(FDCHelper.ZERO);
                        txtpaymentProportion.setEnabled(false);
                    }
                }
                catch(EASBizException e){
                    handUIExceptionAndAbort(e);
                }
                catch(BOSException e){
                    handUIExceptionAndAbort(e);
                }
            }
        }
    }
	
	 private void setTxtEnable(PaymentTypeInfo type) throws EASBizException, BOSException, Exception{
	     if(type != null){
	         if(type.getPayType().getId().toString().equals("Ga7RLQETEADgAADDwKgOlOwp3Sw=")){
		         txtpaymentProportion.setEditable(false);
		         txtpaymentProportion.setRequired(false);
		         txtpaymentProportion.setValue(FDCHelper.ZERO, false);
		         txtcompletePrjAmt.setEditable(false);
		         txtcompletePrjAmt.setValue(FDCHelper.ZERO, false);
		         txtcompletePrjAmt.setRequired(false);
	         }
	         if(type.getPayType().getId().toString().equals("Ga7RLQETEADgAAC/wKgOlOwp3Sw="))
	             if(contractBill != null && contractBill.isHasSettled() && isSimpleFinancial){
	                 txtcompletePrjAmt.setEnabled(false);
	                 txtpaymentProportion.setValue(FDCHelper.ZERO, false);
	                 txtpaymentProportion.setEnabled(false);
	                 setTxtcompletePrjAmtValue();
	             } else{
	                 txtcompletePrjAmt.setEditable(false);
	                 txtcompletePrjAmt.setValue(FDCHelper.toBigDecimal(editData.getSettleAmt()));
	                 txtpaymentProportion.setEditable(false);
	                 txtpaymentProportion.setRequired(false);
	             }
	     }
	 }
	 private void setTxtcompletePrjAmtValue() throws EASBizException, BOSException, Exception{
	     if(contractBill == null)
	         return;
	     if(!OprtState.ADDNEW.equals(getOprtState()))
	         return;
	     FilterInfo filter = new FilterInfo();
	     filter.getFilterItems().add(new FilterItemInfo("contractId", contractBill.getId().toString()));
	     filter.getFilterItems().add(new FilterItemInfo("paymentType.payType.id", "Ga7RLQETEADgAAC/wKgOlOwp3Sw="));
	     filter.getFilterItems().add(new FilterItemInfo("sourceBillId", null));// add by wp  -- 不校验从分包合同过来的付款申请
	     if(editData.getId() != null)
	         filter.getFilterItems().add(new FilterItemInfo("id", editData.getId(), CompareType.NOTEQUALS));
	     if(getBizInterface().exists(filter)){
	         txtcompletePrjAmt.setValue(FDCHelper.ZERO);
	     } else{
	         FDCSQLBuilder builder = new FDCSQLBuilder();
	         builder.appendSql((new StringBuilder()).append("select sum(FCompletePrjAmt) as allComAmt from T_CON_PayRequestBill where FContractId ='").append(contractBill.getId().toString()).append("'").toString());
	         IRowSet rowSet = builder.executeQuery();
	         BigDecimal allComAmt = FDCHelper.ZERO;
	         if(rowSet.next())
	             allComAmt = rowSet.getBigDecimal("allComAmt");
	         txtcompletePrjAmt.setEditable(true);
	         BigDecimal bigDecimal = FDCHelper.subtract(contractBill.getSettleAmt(), allComAmt);
	         removeDataChangeListener(txtcompletePrjAmt);
	         txtcompletePrjAmt.setValue(bigDecimal);
	         addDataChangeListener(txtcompletePrjAmt);
	     }
 	}
	private void caculatePaymentProp(){
        if(!isFromProjectFillBill && !isAutoComplete){
            BigDecimal completePrjAmt = FDCNumberHelper.toBigDecimal(txtcompletePrjAmt.getBigDecimalValue());
            BigDecimal originalAmount = FDCHelper.toBigDecimal(getDetailTable().getCell(rowIndex, columnIndex).getValue());
            if(FDCNumberHelper.ZERO.compareTo(completePrjAmt) != 0)
                if(FDCNumberHelper.ZERO.compareTo(originalAmount) == 0)
                    txtpaymentProportion.setValue(FDCNumberHelper.ZERO, false);
                else
                    txtpaymentProportion.setValue(FDCNumberHelper.multiply(FDCNumberHelper.divide(originalAmount, completePrjAmt), FDCHelper.ONE_HUNDRED), false);
        }
    }
	 private void setCellEnabled(Object oldObj){
        if(oldObj != null && (oldObj instanceof PaymentTypeInfo)){
            PaymentTypeInfo _type = (PaymentTypeInfo)oldObj;
            if(_type.getPayType().getId().toString().equals("Bd2bh+CHRDenvdQS3D72ouwp3Sw=") && kdtEntrys.getCell(4, 4) != null)
                kdtEntrys.getCell(4, 4).getStyleAttributes().setLocked(true);
        }
    }
	
	/**
	 * description 调整款项操作
	 * 
	 */
	public void actionAdjustDeduct_actionPerformed(ActionEvent e)
			throws Exception {
		if (OprtState.ADDNEW.equals(getOprtState())) {
			// 须保存警告
			FDCMsgBox.showWarning(getRes("beforeAdjustDeduct"));
			SysUtil.abort();
		}
		showSelectDeductList(e);
	}

	/**
	 * description 付款申请单关闭操作
	 */
	public void actionClose_actionPerformed(ActionEvent e) throws Exception {
		if (OprtState.ADDNEW.equals(getOprtState())) {
			// 须保存警告
			FDCMsgBox.showWarning(getRes("beforeClose"));
			SysUtil.abort();
		}
		if (!editData.getState().equals(FDCBillStateEnum.AUDITTED)) {
			FDCMsgBox.showError("当前单据的状态不能执行关闭操作！");
			return;
		}

		super.actionClose_actionPerformed(e);
		if (editData != null && editData.getId() != null
				&& editData.isHasClosed()) {
			FDCMsgBox.showWarning(this, "付款申请单已经关闭，不需要再关闭");
			SysUtil.abort();
		}
		editData.setHasClosed(true);
		PayRequestBillFactory.getRemoteInstance().close(
				new ObjectUuidPK(editData.getId()));

		actionClose.setVisible(false);
		actionClose.setEnabled(false);
		actionUnClose.setVisible(true);
		actionUnClose.setEnabled(true);
		this.storeFields();
		this.initOldData(this.editData);
		if (isAutoComplete && !isSeparate) {
			// 已完工自动100%,且非工程量模式时,考虑实付金额与申请金额不等情况时 by hpw 2009-12-14
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder
					.appendSql("update t_con_payrequestbill set fcompleteprjamt=(select sum(flocalamount) from t_cas_paymentbill where t_con_payrequestbill.fid=ffdcpayreqid) where fid=? ");
			builder.addParam(editData.getId().toString());
			builder.execute();
		}

		FDCMsgBox.showInfo(getRes("closeSuccess"));
	}

	/**
	 * description 反关闭操作
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * 
	 * @version EAS 7.0
	 * @see com.kingdee.eas.fdc.contract.client.AbstractPayRequestBillEditUI#actionUnClose_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionUnClose_actionPerformed(ActionEvent e) throws Exception {
		if (editData != null && editData.getId() != null
				&& !editData.isHasClosed()) {
			FDCMsgBox.showWarning(this, "付款申请单未关闭，不需要反关闭");
			SysUtil.abort();
		}
		if (!isSeparate && contractBill != null) {
			checkIsUnClose();
			checkPrjPriceInConSettlePriceForUnClose();
		}
		// 反关闭申请单时，如果累计实付款+未付申请金额超过了付款提示比例时，不允许反关闭，给出提示。
		if (this.editData.getContractId() == null) {
			return;
		}
		String contractId = this.editData.getContractId();
		BigDecimal totalpayAmountLocal = FDCHelper.ZERO;// 累计金额=实付款+申请未付数
		BigDecimal payAmtLocal = FDCHelper.ZERO;// 实付款
		BigDecimal noPayAmtLocal = FDCHelper.ZERO;// 申请未付数

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.appendFilterItem("contractId", contractId);
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.SAVED_VALUE,
						CompareType.NOTEQUALS));

		view.getSelector().add("hasClosed");
		view.getSelector().add("number");
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
		PayRequestBillCollection c = PayRequestBillFactory.getRemoteInstance()
				.getPayRequestBillCollection(view);
		for (int i = 0; i < c.size(); i++) {
			final PayRequestBillInfo info = c.get(i);
			// 未关闭的需要用请款单的总额减去该请款单对应付款单的已付款总额,需要注意一种特殊情况:
			// 已经关闭但是整好是本身这张单据需要做反关闭操作也要如此处理
			if ((!info.isHasClosed())
					|| (info.isHasClosed() && this.editData.getId() != null && info
							.getId().toString().equals(
									this.editData.getId().toString()))) {
				BigDecimal totalThisPayReq = FDCHelper.toBigDecimal(info
						.getAmount());
				BigDecimal temp = FDCHelper.ZERO;
				BigDecimal temp1 = FDCHelper.ZERO;
				int tempInt = info.getEntrys().size();
				for (int j = 0; j < tempInt; j++) {
					PaymentBillInfo payment = info.getEntrys().get(j)
							.getPaymentBill();
					if (payment != null
							&& payment.getBillStatus() == BillStatusEnum.PAYED) { // 并且该付款单已经付款
						temp = FDCHelper.add(temp, payment.getAmount());
					}
				}
				temp1 = FDCHelper.subtract(totalThisPayReq, temp);
				noPayAmtLocal = FDCHelper.add(noPayAmtLocal, temp1);
			} else {// 已关闭

			}
		}

		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder
				.appendSql("select sum(FAmount) sumCount from t_cas_paymentbill where fcontractbillid=? ");
		_builder.addParam(contractId);
		final IRowSet rowSet1 = _builder.executeQuery();
		if (rowSet1.size() == 1) {
			rowSet1.next();
			payAmtLocal = FDCHelper.toBigDecimal(rowSet1
					.getBigDecimal("sumCount"));
		}

		totalpayAmountLocal = FDCHelper.add(payAmtLocal, noPayAmtLocal);
		_builder.clear();
		_builder
				.appendSql("select fpayPercForWarn from t_con_contractbill where fid=");
		_builder.appendParam(this.editData.getContractId());
		final IRowSet rowSet = _builder.executeQuery();
		BigDecimal payRate = FDCHelper.ZERO;
		BigDecimal payPercForWarn = FDCHelper.ZERO;
		BigDecimal conLastestPrice = FDCHelper.ZERO;
		Map map = new HashMap();
		if (rowSet.size() == 1) {
			rowSet.next();
			payPercForWarn = FDCHelper.toBigDecimal(rowSet
					.getBigDecimal("fpayPercForWarn"), 2);
		}
		// 合同最新造价
		map = FDCUtils.getLastAmt_Batch(null, new String[] { this.editData
				.getContractId() });
		if (map != null && map.size() == 1) {
			conLastestPrice = (BigDecimal) map.get(this.editData
					.getContractId());
		}
		payRate = FDCHelper.divide(FDCHelper.multiply(conLastestPrice,
				payPercForWarn), FDCHelper.ONE_HUNDRED);

		if (totalpayAmountLocal.compareTo(payRate) > 0) {
			String str = "本币：当前单据合同下的累计实付款+未付的申请单超过了付款提示比例:";
			str = str + "\n累计金额:" + totalpayAmountLocal + " 其中,实付数："
					+ FDCHelper.toBigDecimal(payAmtLocal, 2) + "  申请未付数:"
					+ FDCHelper.toBigDecimal(noPayAmtLocal, 2);
			str = str + "\n付款提示比例金额：" + payRate + "(" + conLastestPrice + "*"
					+ payPercForWarn + "%)";
			if ("0".equals(allPaidMoreThanConPrice())) {// 严格控制
				FDCMsgBox.showDetailAndOK(this, "超过付款提示比例,请查看详细信息", str, 1);
				SysUtil.abort();
			} else if ("1".equals(allPaidMoreThanConPrice())) {// 提示控制
				FDCMsgBox.showDetailAndOK(this, "超过付款提示比例,请查看详细信息", str, 1);
			}
		}

		BigDecimal amount = FDCHelper.toBigDecimal(editData.getAmount());
		BigDecimal paidAmount = FDCHelper.ZERO;
		PayRequestBillEntryInfo entryInfo;
		for (Iterator iter = editData.getEntrys().iterator(); iter.hasNext();) {
			entryInfo = (PayRequestBillEntryInfo) iter.next();
			paidAmount = paidAmount.add(entryInfo.getAmount());
		}

		// 无文本合同允许录入负数，允许多次付款，所以做比较时，改用绝对值比较，不影响正数合同的逻辑
		// added by Owen_wen 2011-05-18 R110130-022
		if (amount.abs().compareTo(paidAmount.abs()) <= 0) {
			FDCMsgBox.showWarning(getRes("canntUnClosed"));
			SysUtil.abort();
		}

		PayRequestBillFactory.getRemoteInstance().unClose(
				new ObjectUuidPK(editData.getId()));
		editData.setHasClosed(false);
		actionClose.setVisible(true);
		actionClose.setEnabled(true);
		actionUnClose.setVisible(false);
		actionUnClose.setEnabled(false);
		this.storeFields();
		this.initOldData(this.editData);
		if (isAutoComplete && !isSeparate) {
			// 已完工自动100%,且非工程量模式时,考虑实付金额与申请金额不等情况时 by hpw 2009-12-14
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder
					.appendSql("update t_con_payrequestbill set fcompleteprjamt=fprojectpriceincontract where fid=? ");
			builder.addParam(editData.getId().toString());
			builder.execute();
		}
		FDCMsgBox.showInfo(getRes("unCloseSuccess"));
	}

	/**
	 * description 下查
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * 
	 * @version EAS 7.0
	 * @see com.kingdee.eas.framework.client.CoreBillEditUI#actionTraceDown_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception {
		// 取数据库内的最新数据
		editData = (PayRequestBillInfo) getBizInterface().getValue(
				new ObjectUuidPK(editData.getId()));
		switch (editData.getEntrys().size()) {
		case 0: {
			FDCMsgBox.showError(getRes("notraceDownBill"));
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
				break;
			}
			if (traceDownUIwindow != null) {
				traceDownUIwindow.show();
			}
			break;
		}
		default: {
			PayReqUtils.traceDownFDCPaymentBill(editData, this);
		}
		}
	}

	/**
	 * description 上查
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * 
	 * @version EAS 7.0
	 * @see com.kingdee.eas.framework.client.CoreBillEditUI#actionTraceUp_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception {
		// 上查合同付款计划
		// super.actionTraceUp_actionPerformed(e);
		if (editData != null && editData.getId() != null
				&& PayReqUtils.isContractBill(editData.getContractId())) {
			String contractId = editData.getContractId();
			ContractPayPlanEditUI.showEditUI(this, contractId, "VIEW");
		} else {
			FDCMsgBox.showWarning(this, "没有合同计划");
		}
	}

	/**
	 * 显示调整款项列表界面
	 * 
	 * @param e
	 * @throws Exception
	 */
	private void showSelectDeductList(ActionEvent e) throws Exception {

		boolean canAdjust = checkCanSubmit();
		String state = canAdjust ? getOprtState() : OprtState.VIEW;

		// uiWindow=null;//暂时每次都实例一个UIWindow
		deductUIwindow = null; // 每次都实例一个UIWindow,否则会缓存UI，导致脏数据。数据的正确性远比性能更重要 by
								// zhiyuan_tang
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
					tableHelper.reloadDeductTable(editData, getDetailTable(),
							deductTypeCollection);
					tableHelper
							.reloadGuerdonValue(editData, u.getGuerdonData());
					tableHelper.reloadCompensationValue(editData, u
							.getCompensationData());
					if (PayReqUtils.isContractBill(editData.getContractId())) {
						if (partAParam) {
							tableHelper.reloadPartAValue(editData, u
									.getPartAData());
						} else {
							tableHelper.reloadPartAConfmValue(editData, u
									.getPartAConfmData());
						}
					}
					tableHelper
							.updateDynamicValue(editData, contractBill,
									contractChangeBillCollection,
									paymentBillCollection);
					this.getDetailTable().getScriptManager().runAll();
				} catch (Exception e1) {
					handUIExceptionAndAbort(e1);
				}
			}
		}
	}

	protected String getEditUIName() {
		return com.kingdee.eas.fdc.contract.client.PayRequestBillEditUI.class
				.getName();
	}

	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return com.kingdee.eas.fdc.contract.PayRequestBillFactory
				.getRemoteInstance();
	}

	/**
	 * 获得分录
	 */
	protected KDTable getDetailTable() {
		return this.kdtEntrys;
	}

	/**
	 * 创建新增分录对象
	 */
	protected IObjectValue createNewDetailData(KDTable table) {

		return new com.kingdee.eas.fdc.contract.PayRequestBillEntryInfo();
	}

	/**
	 * 创建新增对象
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		PayRequestBillInfo objectValue = new PayRequestBillInfo();
		objectValue.setCreator((UserInfo) (SysContext.getSysContext()
				.getCurrentUserInfo()));
		try {
			objectValue.setCreateTime(FDCDateHelper.getServerTimeStamp());
			objectValue.setPayDate(new Date(FDCDateHelper.getServerTimeStamp()
					.getTime()));
		} catch (BOSException e1) {
			handUIExceptionAndAbort(e1);
		}
		objectValue.setSourceType(SourceTypeEnum.ADDNEW);
		objectValue.setPayDate(new Date());
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
		objectValue.setOrgUnit(contractBill.getOrgUnit());
		// editData.setCU(curProject.getCU());

		objectValue.setBookedDate(bookedDate);
		objectValue.setPeriod(curPeriod);

		// 是否需要提交付款（房地产付款单强制不进入出纳系统为‘是’就不需要）
		if (isNotEnterCAS)
			objectValue.setIsPay(false);
		else
			objectValue.setIsPay(true);

		if (isAutoComplete) {// 申请单进度款付款比例自动为100%
			objectValue.setPaymentProportion(FDCConstants.ONE_HUNDRED);
		}
		// 开票日期
		objectValue.setInvoiceDate(serverDate);

		// 资金计划申请金额
		// String id = editData.getId().toString();
		// String contractId = editData.getContractId();
		// Date bizDate = editData.getBizDate();
		Calendar c1 = Calendar.getInstance();
		int year = c1.get(Calendar.YEAR);
		int month = c1.get(Calendar.MONTH);
		month = month + 1;
		if (month != 1) {
			month = month - 1;
		} else {
			month = 12;
			year = year - 1;
		}
		BigDecimal zjjh;
		try {
			BigDecimal je = getZjjh(year, month, objectValue.getCurProject()
					.getId().toString(), contractBillId);
			objectValue.setZjjhSqje(je);
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return objectValue;
	}

	private BigDecimal getZjjh(int year, int month, String id, String contractId)
			throws BOSException, SQLException {
		BigDecimal zjjhje = BigDecimal.ZERO;
		StringBuffer sb = new StringBuffer();
		sb
				.append("select try.fReportAmount from T_FNC_ProjectMonthPlanGather ther ");
		sb
				.append("left join T_FDC_CurProject  ject on ject.fid = ther.fcurprojectid ");
		sb
				.append("left join T_FNC_ProjectMonthPlanGEntry entry on entry.fheadid = ther.fid ");
		sb
				.append("left join T_FNC_ProjectMonthPGDateEntry try on try.fHeadEntryid = entry.fid ");
		sb.append("where ther.FISLATEST = '1' ");
		String m = "";
		if (month < 10)
			m = "0" + month;
		else
			m = month + "";
		// 期间的年
		sb.append("and ther.FBizDate= { ts '").append(year + "-" + m + "-01")
				.append("'} ");
		// //期间的月
		sb.append("and try.FMONTH='").append(month + 1).append("' ");
		// 项目id
		sb.append("and ject.fid='").append(id).append("' ");
		// 公司ID
		sb.append("and entry.FCONTRACTBILLID = '").append(contractId).append(
				"'");
		IRowSet rowset = new FDCSQLBuilder().appendSql(sb.toString())
				.executeQuery();
		while (rowset.next()) {
			zjjhje = rowset.getBigDecimal(1);
		}
		return zjjhje;
	}

	private void reloadDynamicValue() {

		try {
			if (PayReqUtils.isContractBill(editData.getContractId())) {

				tableHelper.updateDynamicValue(editData, contractBill,
						contractChangeBillCollection, paymentBillCollection);
				tableHelper.reloadDeductTable(editData, getDetailTable(),
						deductTypeCollection);
				tableHelper.updateGuerdonValue(editData, editData
						.getContractId(), guerdonOfPayReqBillCollection,
						guerdonBillCollection);
				tableHelper.updateCompensationValue(editData, editData
						.getContractId(), compensationOfPayReqBillCollection);

				reloadPartADeductDetails();
				((ICell) bindCellMap
						.get(PayRequestBillContants.PROJECTPRICEINCONTRACT))
						.setValue(null);
				((ICell) bindCellMap
						.get(PayRequestBillContants.PROJECTPRICEINCONTRACTORI))
						.setValue(null);
				if (this.isAdvance()) {
					((ICell) bindCellMap.get(PayRequestBillContants.ADVANCE))
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

	/**
	 * description 创建无文本合同的付款申请单
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
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
			selector.add("codingNumber");
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

	/**
	 * description 创建合同对应的付款申请单
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * @param contractBillId
	 *            合同ID， objectValue 付款申请单对象
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
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
				if (!isSimpleFinancial) {
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
			// 启用编码规则取长编码 改在服务端
			/*
			 * if (curProject.getLongNumber() == null ||
			 * curProject.getCodingNumber() == null) { SelectorItemCollection
			 * sic = new SelectorItemCollection(); sic.add("id");
			 * sic.add("longNumber"); sic.add("codingNumber"); String pk =
			 * curProject.getId().toString(); CurProjectInfo prj =
			 * CurProjectFactory.getRemoteInstance() .getCurProjectInfo(new
			 * ObjectUuidPK(pk), sic);
			 * curProject.setLongNumber(prj.getLongNumber());
			 * curProject.setCodingNumber(prj.getCodingNumber()); }
			 */

			// 根据实际收款单位，得到付款帐户和付款银行
			if (contractBill.getPartB() != null) {
				String supperid = contractBill.getPartB().getId().toString();
				PayReqUtils.fillBank(objectValue, supperid, curProject.getCU()
						.getId().toString());
			}

			objectValue.setCurProject(curProject);
			objectValue.setCU(contractBill.getCU());

			// 调整款项选择并保存重新取数和数据库一致，去掉选择,提交新增时集合与数据库数据不一致，应再重新取一次 by hpw
			// 2009-08-1
			this.fetchInitData();
			tableHelper.updateDynamicValue(objectValue, contractBill,
					contractChangeBillCollection, paymentBillCollection);
			tableHelper.updateGuerdonValue(objectValue, contractBillId,
					guerdonOfPayReqBillCollection, guerdonBillCollection);
			tableHelper.updateCompensationValue(objectValue, contractBillId,
					compensationOfPayReqBillCollection);
			if (partAParam) {
				tableHelper.updatePartAValue(editData, contractBillId,
						partAOfPayReqBillCollection);
			} else {
				tableHelper.updatePartAConfmValue(editData, contractBillId,
						partAConfmOfPayReqBillCollection);
			}
			if (editData != null
					&& !FDCBillStateEnum.AUDITTED.equals(editData.getState())) {
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
		if (isIncorporation && ((FDCBillInfo) editData).getPeriod() == null) {// 启用成本成本月结且期间为空的时候
																				// 提示
			FDCMsgBox.showWarning(this, "启用成本月结期间不能为空，请在基础资料维护期间后，重新选择业务日期");
			SysUtil.abort();
		}
		return super.checkCanSubmit();
	}

	/**
	 * 描述：检查是否有”项目月度付款计划“，”项目月度付款计划“是否已经审批。当合同不进动态成本时，不需要检查
	 * 
	 * @Author：keyan_zhao
	 * @CreateTime：2012-11-8
	 */
	 
	protected void checkFDCProDep() throws EASBizException, BOSException {
		if ("不控制".equals(CONTROLPAYREQUEST) || !this.isCostSplitContract) {// 不进动态成本的不需要校验
			return;
		}

		Date bookDate = (Date) pkbookedDate.getValue();
		Calendar instance = Calendar.getInstance();
		instance.setTime(bookDate);
		int year = instance.get(Calendar.YEAR);
		int month = instance.get(Calendar.MONTH) + 1;
		String projectId = editData.getCurProject().getId().toString();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("longNumber");
		sic.add("id");
		CurProjectInfo projectInfo = CurProjectFactory.getRemoteInstance()
				.getCurProjectInfo(new ObjectUuidPK(projectId), sic);
		String longNumber = projectInfo.getLongNumber();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("year", new Integer(year)));
		filter.getFilterItems().add(
				new FilterItemInfo("month", new Integer(month)));
		filter.getFilterItems().add(
				new FilterItemInfo("curProject.longNumber", longNumber));
		if (longNumber.indexOf("!") > 0) {
			longNumber = longNumber.substring(0, longNumber.indexOf("!"));
			filter.getFilterItems().add(
					new FilterItemInfo("curProject.longNumber", longNumber));
			filter.setMaskString("#0 and #1 and (#2 or #3)");
		}
		view.setFilter(filter);
		view.getSelector().add(new SelectorItemInfo("state"));
		FDCProDepConPayPlanCollection planCollection = FDCProDepConPayPlanFactory
				.getRemoteInstance().getFDCProDepConPayPlanCollection(view);
		if (planCollection.size() > 0) {
			for (int i = 0; i < planCollection.size(); i++) {
				FDCProDepConPayPlanInfo planInfo = planCollection.get(i);
				if (FDCBillStateEnum.SAVED.equals(planInfo.getState())
						|| FDCBillStateEnum.SUBMITTED.equals(planInfo
								.getState())
						|| FDCBillStateEnum.AUDITTING.equals(planInfo
								.getState())) {
					if ("提示控制".equals(CONTROLPAYREQUEST)) {
						int result = FDCMsgBox.showConfirm2(this,
								"当月的项目月度付款计划未审批，是否继续？");
						if (result != FDCMsgBox.OK) {
							SysUtil.abort();
						}
					} else {
						FDCMsgBox.showWarning("该项目的“项目月度付款计划”未审批，请先审批。");
						abort();
					}
				}
			}
		}
		// else {
		//
		// if ("提示控制".equals(CONTROLPAYREQUEST)) {
		//				
		// int result = FDCMsgBox.showConfirm2(this, "该项目未做“项目月度付款计划”，是否继续？");
		// if (result != FDCMsgBox.OK) {
		// SysUtil.abort();
		// }
		//
		//					
		// } else {
		// FDCMsgBox.showWarning("该项目未做“项目月度付款计划”，请先做计划。");
		// abort();
		// }
		// }
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		checkFDCProDep();
		/*
		 * //付款类型的控制 1. 必须合同结算之后才能做结算款类别的付款申请单 2. 付了结算款之后才能付保修款（已控制） 3.
		 * 保修款总金额不能大于结算单上的质保金
		 */

		/*
		 * if(e.getSource()!=btnSubmit){
		 * if(editData.getNumber()==null||editData.getNumber().length()<1){
		 * FDCMsgBox.showWarning(this, getRes("NullNumber")); SysUtil.abort();
		 * }else{ return; } }
		 */
		/*
		 * 检查原币金额与单元格内的发生额实付金额是否一致
		 */
		if (!isSaveAction()) {
			Object cell = bindCellMap.get(PayRequestBillContants.CURPAID);
			if (cell instanceof ICell) {
				Object value = ((ICell) cell).getValue();
				if (value != null) {
					try {
						BigDecimal cellAmount = FDCHelper.toBigDecimal(value);
						BigDecimal amount = txtAmount.getBigDecimalValue(); // 原币金额
						/*
						 * if (cellAmount.doubleValue() < 0 || amount == null ||
						 * amount.doubleValue() < 0 || (cellAmount.doubleValue()
						 * > 0 && cellAmount .compareTo(amount) != 0)) {
						 * FDCMsgBox.showWarning(this, getRes("verifyAmount"));
						 * SysUtil.abort(); }
						 */
						// 支持负数
						if (amount == null
								|| (cellAmount.compareTo(amount) != 0)) {
							FDCMsgBox.showWarning(this,
									"原币金额与单元格内的发生额实付金额不符合，请检查后保存！");
							SysUtil.abort();
						}
						BigDecimal completePrj = txtcompletePrjAmt
								.getBigDecimalValue(); // 本期完工工程量
						Object obj = prmtPayment.getValue(); // 付款类型
						PaymentTypeInfo type = null;
						if (obj != null && obj instanceof PaymentTypeInfo) {
							type = (PaymentTypeInfo) obj;
						}
						if (completePrj == null) {
							completePrj = FDCHelper.ZERO;
						}

						if (!isSimpleInvoice) {// 非发票校验(简单模式处理发票)
							if (FDCHelper.ZERO.compareTo(completePrj) == 0
									&& FDCHelper.ZERO.compareTo(amount) == 0) {
								String msg = "已完工工程量和实付金额不能同时为0！";
								if (isSimpleFinancial
										&& type != null
										&& !type.getPayType().getId()
												.toString().equals(
														PaymentTypeInfo.tempID)) {
									// 暂估款的话不能有这个提示 by cassiel_peng 2010-03-23
									// msg = "实付金额不能为0!";
								}
								if ((txtcompletePrjAmt.isRequired() && contcompletePrjAmt
										.isVisible())
										&& type != null
										&& !type.getPayType().getId()
												.toString().equals(
														PaymentTypeInfo.tempID)) {
									// 暂估款的话不能有这个提示"已完工工程量和实付金额不能同时为0！" by
									// cassiel_peng 2010-03-26
									if (FDCHelper.isZero(txtInvoiceAmt
											.getBigDecimalValue())) {// 发票也为0时提示byhpw
										FDCMsgBox.showWarning(this, msg);
										SysUtil.abort();
									}
								}
							}
						}

						if (FDCHelper.ZERO.compareTo(amount) == 0) {
							String msg = null;
							// 预付款,发票为零时校验
							if (isAdvance()
									&& FDCHelper.ZERO.compareTo(FDCHelper
											.toBigDecimal(txtInvoiceAmt
													.getBigDecimalValue())) == 0) {
								if (FDCHelper.ZERO.compareTo(FDCHelper
										.toBigDecimal(getDetailTable().getCell(
												rowIndex, columnIndex)
												.getValue())) == 0
										&& FDCHelper.ZERO.compareTo(FDCHelper
												.toBigDecimal(getDetailTable()
														.getCell(rowIndex + 1,
																columnIndex)
														.getValue())) == 0) {
									msg = "实付款与预付款不能同时为 0!";
									FDCMsgBox.showWarning(this, msg);
									SysUtil.abort();
								}

							} else if ((isInvoiceRequired || invoiceMgr)
									&& FDCHelper.ZERO.compareTo(FDCHelper
											.toBigDecimal(txtInvoiceAmt
													.getBigDecimalValue())) == 0) {
								// R130122-0418
								// 付款申请单有扣款单，申请金额不为零，发票为零，付款金额也为零，不让提交
								// FDCMsgBox.showWarning(this,
								// "发票金额与原币金额不能同时为0，不允许提交!");
								// SysUtil.abort();
							} else if (!(isInvoiceRequired || invoiceMgr)
									&& (FDCHelper.ZERO.compareTo(FDCHelper
											.toBigDecimal(txtInvoiceAmt
													.getBigDecimalValue())) == 0)) {// 非发票为0不能提交
								if (type != null
										&& !type.getPayType().getId()
												.toString().equals(
														PaymentTypeInfo.tempID)) {
									// FDCMsgBox.showWarning(this, "实付金额不能为0!");
									// SysUtil.abort();
								}
							}
						}

					} catch (NumberFormatException e1) {
						handUIExceptionAndAbort(e1);
					}
				}
			}
		}

		if (!isSaveAction()) {
			checkAmt(editData);
			checkCompletePrjAmt();
			if (this.isAdvance()) {
				tableHelper.checkAdvance(editData, this.bindCellMap);
			}

			// 发票是否必录
			if (isInvoiceRequired) {
				boolean isNotInput = false;
				if (txtInvoiceAmt.getBigDecimalValue() == null) {
					// 为零时可不录
					if (!FDCHelper.ZERO.equals(txtInvoiceAmt
							.getBigDecimalValue())) {
						isNotInput = true;
					}
				} else if (FDCHelper.ZERO.compareTo(txtInvoiceAmt
						.getBigDecimalValue()) != 0
						&& FDCHelper.isEmpty(txtInvoiceNumber.getText())) {
					isNotInput = true;
				}
				if (isNotInput) {
					FDCMsgBox.showWarning(this, "发票号与发票金额必须录入!");
					SysUtil.abort();
				}
			}
		}

		BigDecimal lastestPrice = FDCHelper.toBigDecimal(editData
				.getLatestPrice(), 2);
		// if (txtpaymentProportion.isRequired() &&
		// txtcompletePrjAmt.isRequired()) { //本期完工工程量和进度款付款比例必录时
		BigDecimal propAmt = txtpaymentProportion.getBigDecimalValue();
		BigDecimal completeAmt = FDCHelper.toBigDecimal(txtcompletePrjAmt
				.getBigDecimalValue(), 2);
		if (propAmt != null) {
			if (propAmt.compareTo(FDCHelper.ZERO) <= 0
					|| propAmt.compareTo(FDCHelper.ONE_HUNDRED) > 0) {
				// FDCMsgBox.showError(this, "付款比例必须大于0,小于等于100%");
				// SysUtil.abort();
				// } else if (FDCHelper.toBigDecimal(completeAmt).signum()
				// == 0) {
				// FDCMsgBox.showError(this, "已完工工程量必须大于0");
				// SysUtil.abort();
			} else if (!(FDCHelper.toBigDecimal(completeAmt).signum() == 0)) {
				// BigDecimal amount = FDCHelper
				// .toBigDecimal(((ICell) bindCellMap
				// .get(PayRequestBillContants.PROJECTPRICEINCONTRACT))
				// .getValue());
				// BigDecimal amount = txtBcAmount.getBigDecimalValue();
				// BigDecimal tmpAmt =
				// amount.setScale(4,BigDecimal.ROUND_HALF_UP
				// ).divide(completeAmt,
				// BigDecimal.ROUND_HALF_UP).multiply(
				// FDCHelper.ONE_HUNDRED);
				// if (tmpAmt.compareTo(propAmt) != 0) {
				// FDCMsgBox.showError(this, "付款比例＝原币金额/已完工工程量 *100% 关系不成立");
				// SysUtil.abort();
				// }
				Object amount = ((ICell) bindCellMap
						.get(PayRequestBillContants.PROJECTPRICEINCONTRACT))
						.getValue();
				if (amount == null) {
					FDCMsgBox.showError(this, "申请金额不允许为空！");
					SysUtil.abort();
				}
				if (isSaveAction()) {
					if (isSimpleFinancial) { // 简单模式一体化
						if (FDCHelper.toBigDecimal(amount, 2).compareTo(
								lastestPrice) > 0) {
							int ok = FDCMsgBox.showConfirm2(this,
									"实付金额大于合同最新造价,是否保存？");
							if (ok != FDCMsgBox.OK) {
								SysUtil.abort();
							}
						}
					}
				}
				if (null == prmtPayment.getValue()) {
					FDCMsgBox.showWarning(this, "付款类型不能为空");
					SysUtil.abort();
				}
				String paymentType = editData.getPaymentType().getPayType()
						.getId().toString();

				/*
				 * modified by zhaoqin for R130922-0254,R140425-0083 on
				 * 2013/11/26 start
				 */
				// completeAmt =
				// PayReqUtils.getConSettleCompletePrjAmt(editData);
				if (!isSeparate && getOprtState().equals(OprtState.ADDNEW)) {
					completeAmt = FDCHelper.add(completeAmt,
							txtAllCompletePrjAmt.getBigDecimalValue());
				} else {
					completeAmt = null == txtAllCompletePrjAmt
							.getBigDecimalValue() ? FDCHelper.ZERO
							: txtAllCompletePrjAmt.getBigDecimalValue();
				}
				/*
				 * modified by zhaoqin for R130922-0254,R140425-0083 on
				 * 2013/11/26 end
				 */

				if (PaymentTypeInfo.settlementID.equals(paymentType)) {
					// 结算款时，判断所有已完工，不能大于最新造价。
					if (completeAmt.compareTo(lastestPrice) > 0) {
						FDCMsgBox.showWarning(this,
								"合同下付款申请单的累计已完工工程量不能大于合同最新造价。");
						SysUtil.abort();
					}
				} else {
					if (completeAmt.compareTo(lastestPrice) > 0) {
						/*
						 * modified by zhaoqin for R130922-0254 on 2013/11/26
						 * start
						 */
						// int ok = FDCMsgBox.showConfirm2(this,
						// "已完工工程量金额大于合同最新造价,是否保存？");
						int ok = FDCMsgBox.showConfirm2(this,
								"累计已完工工程量金额大于合同最新造价,是否保存？");
						/*
						 * modified by zhaoqin for R130922-0254 on 2013/11/26
						 * end
						 */
						if (ok != FDCMsgBox.OK) {
							SysUtil.abort();
						}
					}
				}
			}
		}
		// }

		if (isRealizedZeroCtrl) { // 已实现产值为0时只能选择预付款
			PaymentTypeInfo type = (PaymentTypeInfo) prmtPayment.getValue();
			if (FDCHelper.isNullZero(txtTotalSettlePrice.getBigDecimalValue())
					&& type.getName() != null && !type.getName().equals(YFK)) {
				FDCMsgBox.showError(prmtPayment, "已实现产值为0只允许选择\"预付款\"！");
				SysUtil.abort();
			}
		}

		// if (FDCHelper.ZERO.compareTo(FDCHelper.toBigDecimal(txtAllInvoiceAmt.
		// getBigDecimalValue())) == 1) {
		// FDCMsgBox.showError(this, "累计发票金额不能小于零！");
		// SysUtil.abort();
		// }
		// if
		// (FDCHelper.toBigDecimal(txtAllInvoiceAmt.getBigDecimalValue()).setScale
		// (2, BigDecimal.ROUND_HALF_UP).compareTo(lastestPrice) == 1) {
		// if (isOverrun) {
		// int ok = FDCMsgBox.showConfirm2(this, "累计发票金额大于合同最新造价，是否提交?");
		// if (ok != FDCMsgBox.OK) {
		// SysUtil.abort();
		// }
		// } else {
		// FDCMsgBox.showWarning(this, "累计发票金额不能超过合同最新造价！");
		// SysUtil.abort();
		// }
		// }
		BigDecimal invoiceOriAmt = FDCHelper.ZERO;
		if (null != txtInvoiceOriAmt.getBigDecimalValue()) {
			invoiceOriAmt = txtInvoiceOriAmt.getBigDecimalValue();
		}
		// 累计法票金额原币
		BigDecimal invoiceOriAmtSum = allInvoiceOriAmt.add(FDCHelper
				.toBigDecimal(invoiceOriAmt));
		if (FDCHelper.ZERO.compareTo(FDCHelper.toBigDecimal(invoiceOriAmtSum)) == 1) {
			FDCMsgBox.showError(this, "累计发票金额原币不能小于零！");
			SysUtil.abort();
		}
		// 仅提交校验
		if (!isSaveAction()) {
			// 额滴神外包咋测的原币与本币比较了
			BigDecimal latestOrgPrice = FDCHelper.ZERO;
			if (bindCellMap.get(PayRequestBillContants.LATESTORGPRICE) != null) {
				latestOrgPrice = (BigDecimal) ((ICell) bindCellMap
						.get(PayRequestBillContants.LATESTORGPRICE)).getValue();
			}
			if (FDCHelper.toBigDecimal(invoiceOriAmtSum).setScale(2,
					BigDecimal.ROUND_HALF_UP).compareTo(latestOrgPrice) == 1) {
				if (isOverrun) { // 允许付款申请单累计发票金额大于合同最新造价
					int ok = FDCMsgBox.showConfirm2(this,
							"累计发票金额原币大于合同最新造价，是否提交?");
					if (ok != FDCMsgBox.OK) {
						SysUtil.abort();
					}
				} else {
					FDCMsgBox.showWarning(this, "累计发票金额原币不能超过合同最新造价！");
					SysUtil.abort();
				}
			}
		}
		editData.setAllInvoiceOriAmt(invoiceOriAmtSum);

		// R110510-0022：工作流中提交审批决策结果时e.getActionCommand()为空，导致报中断
		if (e != null && e.getActionCommand() != null
				&& e.getActionCommand().endsWith("ActionSubmit")) {

			if (prmtPlanHasCon.getValue() == null
					&& prmtPlanUnCon.getValue() == null) {
				if (isCostSplitContract) {
					if ("提示控制".equals(CONTROLPAYREQUEST)) {
						int confirm = FDCMsgBox.showConfirm2(this,
								"当前单据为无计划付款，是否确认并继续提交？");
						if (confirm != FDCMsgBox.OK) {
							abort();
						}
					}

					if ("严格控制".equals(CONTROLPAYREQUEST)) {
						FDCMsgBox.showWarning(this, "当前单据为无计划付款，不允许提交");
						abort();
					}
				}
			} else {
				// 11.6.24 不进入动态成本，就不鸟这个控制 add by emanon
				if (isCostSplitContract) {
					// 当参数为"严格控制"时，无文本合同提交时校验：如果无文本合同的"本位币金额"大于"当月可用余额”，
					// 则提示用户："申请金额大于计划可以余额，不能提交"，"确定"后终止提交操作。当参数为"提示控制"时，
					// 无文本合同提交时校验：如果付款申请单的"本位币金额"大于"可用余额"，则提示用户：申请金额大于计划可以余额
					// ，"确定"后提交成功，"取消"后放弃本次操作。当参数为"不控制"时，不做任何校验
					BigDecimal bgBalance = getBgBalance();
					BigDecimal bcAmount = txtBcAmount.getBigDecimalValue();
					if ("严格控制".equals(CONTROLPAYREQUEST)) {
						if (bcAmount.compareTo(bgBalance) > 0) {
							setKdDepPlanStateValue();
							FDCMsgBox
									.showWarning(this, "本次申请原币金额大于本期可用预算，不能提交");
							abort();
						}
					}
					if ("提示控制".equals(CONTROLPAYREQUEST)) {
						if (bcAmount.compareTo(bgBalance) > 0) {
							setKdDepPlanStateValue();
							int result = FDCMsgBox.showConfirm2(this,
									"本次申请原币金额大于本期可用预算，是否提交？");
							if (result != FDCMsgBox.OK) {
								SysUtil.abort();
							}
						}
					}
				}
			}
		}
		if(isShiGongContract && txtThreeCai.getBigDecimalValue()==null){
			MsgBox.showInfo("当合同类型为[施工]时应该填写三材金额信息！");
			txtThreeCai.grabFocus();
			SysUtil.abort();
		}
	}

	/**
	 * 描述：通过计算设置 “计划付款状态”的值
	 * 
	 * @throws Exception
	 * @Author：jian_cao
	 * @CreateTime：2013-7-10
	 */
	private void setKdDepPlanStateValue() throws Exception {
		if (!kdDepPlanState.isVisible()) {
			return;
		}
		BigDecimal bcAmount = txtBcAmount.getBigDecimalValue();
		Object value = prmtPlanUnCon.getValue();
		Object value2 = prmtPlanHasCon.getValue();
		if (bcAmount != null && bcAmount.compareTo(FDCHelper.ZERO) > 0
				&& (value != null || value2 != null)) {
			BigDecimal bgBalance = getBgBalance();
			if (bcAmount.compareTo(bgBalance) > 0) {
				kdDepPlanState.setSelectedItem(DepPlanStateEnum.outPlan);
			}
		}
	}

	protected void verifyInputForSubmint() throws Exception {

		// 付款账号的校验需要自己做，特殊处理一下
		if (!FDCHelper.isEmpty(txtrecAccount.getText())) {
			txtrecAccount.setValue(txtrecAccount.getText());
		} else {
			txtrecAccount.setValue(null);
		}

		super.verifyInputForSubmint();
		// 根据项目上的合同计划金额是否强控合同付款申请单这个字段，校验
		BOSUuid jectid = editData.getCurProject().getId();
		CurProjectInfo projectInfo = CurProjectFactory.getRemoteInstance()
				.getCurProjectInfo(new ObjectUuidPK(jectid));
		projectInfo.isQk();
		BigDecimal yb = editData.getAmount();
		BigDecimal zjjhSqje = editData.getZjjhSqje();
		if(zjjhSqje == null)
			zjjhSqje = BigDecimal.ZERO;
		if (projectInfo.isQk()) {
			if (yb.compareTo(zjjhSqje) > 0) {
				MsgBox.showWarning("本次付款申请单大于资金计划申请金额，不能提交，请修改。");
				SysUtil.abort();
			}
		} else {
			if (yb.compareTo(zjjhSqje) > 0) {
				MsgBox.showWarning("本次付款申请单大于资金计划申请金额.");
				contZjjhSqje.getBoundLabel().setForeground(Color.red);
			}else{
				contZjjhSqje.getBoundLabel().setForeground(Color.black);
			}
		}
	}

	/**
	 * Description:币别
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
			Date bookedDate = (Date) pkbookedDate.getValue();
			//txtexchangeRate.setValue(FDCClientHelper.getLocalExRateBySrcCurcy(
			// this, srcid,company,bookedDate));

			ExchangeRateInfo exchangeRate = FDCClientHelper
					.getLocalExRateBySrcCurcy(this, srcid, company, bookedDate);

			int curPrecision = FDCClientHelper.getPrecOfCurrency(srcid);
			BigDecimal exRate = FDCHelper.ONE;
			int exPrecision = curPrecision;

			if (exchangeRate != null) {
				exRate = exchangeRate.getConvertRate();
				exPrecision = exchangeRate.getPrecision();
			}
			txtexchangeRate.setValue(exRate);
			txtexchangeRate.setPrecision(exPrecision);
			txtAmount.setPrecision(curPrecision);

			setAmount();

			setPropPrjAmount("amount", null);

			caculatePaymentProp();

		}
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

			// 供应商的获取
			String supperid = supplierid.toString();
			PayReqUtils.fillBank(editData, supperid, curProject.getCU().getId()
					.toString());
			txtrecAccount.setText(editData.getRecAccount());
			txtrecBank.setText(editData.getRecBank());
		}
	}

	/**
	 * 描述：根据供应商ID，设置收款帐号的过滤信息
	 */
	private void setRecAccountFilter() {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		SupplierInfo supplier = (SupplierInfo) prmtrealSupplier.getValue();

		if (supplier != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("supplier.id", supplier.getId()
							.toString()));
			// 按财务组织过滤
			CompanyOrgUnitInfo companyOrgUnitInfo = SysContext.getSysContext()
					.getCurrentFIUnit();
			String companyorgunitID = (companyOrgUnitInfo == null ? null
					: companyOrgUnitInfo.getId().toString());
			filter.getFilterItems().add(
					new FilterItemInfo("COMPANYORGUNIT.ID", companyorgunitID));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("id", null));
		}
		view.setFilter(filter);
		txtrecAccount.setEntityViewInfo(view);
		txtrecAccount.getQueryAgent().resetRuntimeEntityView();
	}

	/**
	 * 描述：根据供应商，银行名称，帐号来获取SupplierCompanyBankInfo对象
	 * 
	 * @param supplierId
	 * @param bankName
	 * @param account
	 * @return
	 */
	private SupplierCompanyBankInfo getSupplierCompanyBankInfoByAccount(
			String supplierId, String bankName, String account) {
		if (supplierId == null || bankName == null || account == null) {
			return null;
		}

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("supplierCompanyInfo.supplier.id",
						supplierId));
		filter.getFilterItems().add(new FilterItemInfo("bank", bankName));
		filter.getFilterItems().add(new FilterItemInfo("bankAccount", account));
		view.setFilter(filter);
		SupplierCompanyBankCollection col = null;
		try {
			col = SupplierCompanyBankFactory.getRemoteInstance()
					.getSupplierCompanyBankCollection(view);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
		if (col != null && col.size() > 0) {
			SupplierCompanyBankInfo info = col.get(0);
			return info;
		}
		return null;
	}

	protected void txtrecAccount_willCommit(CommitEvent e) throws Exception {
		setRecAccountFilter();
	}

	protected void txtrecAccount_willShow(SelectorEvent e) throws Exception {
		setRecAccountFilter();
	}

	protected void txtrecAccount_dataChanged(DataChangeEvent e)
			throws Exception {

		if (e.getNewValue() != null && !e.getNewValue().equals(e.getOldValue())
				&& e.getNewValue() instanceof SupplierCompanyBankInfo) {
			SupplierCompanyBankInfo acctbank = (SupplierCompanyBankInfo) e
					.getNewValue();
			txtrecBank.setText(acctbank.getBank());
		}
	}

	public IObjectPK runSubmit() throws Exception {
		// 预算检查时从数据库中取数，所以先保存一下 by hpw 2011.6.20
		this.btnSave.doClick();
		// 预算控制
		// checkMbgCtrlBalance();已经集成在checkFdcBudget方法 2011.6.5
		checkFdcBudget();
		return super.runSubmit();
	}

	protected void txtAmount_dataChanged(DataChangeEvent e) throws Exception {
		super.txtAmount_dataChanged(e);
		setAmount();
	}

	/**
	 * description 填入汇总数操作
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * 
	 * @version EAS 7.0
	 * @see com.kingdee.eas.fdc.contract.client.AbstractPayRequestBillEditUI#btnInputCollect_actionPerformed(java.awt.event.ActionEvent)
	 */
	protected void btnInputCollect_actionPerformed(ActionEvent e)
			throws Exception {
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
				// setAmount();
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
						localamount = localamount.setScale(2,
								BigDecimal.ROUND_HALF_UP);
						// 大写金额为本位币金额
						String cap = FDCClientHelper.getChineseFormat(
								localamount, false);
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
		DataChangeEvent de = new DataChangeEvent(pkpayDate, new Date(), null);
		pkpayDate_dataChanged(de);
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
						 * BigDecimal&&((BigDecimal)exchangeRate).intValue()!=1)
						 * { FDCMsgBox.showWarning(this,"你选择的是本位币，但是汇率不等于1"); }
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

			if (localamount != null
					&& localamount.compareTo(FDCConstants.ZERO) != 0) {
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
						 * BigDecimal&&((BigDecimal)exchangeRate).intValue()!=1)
						 * { FDCMsgBox.showWarning(this,"你选择的是本位币，但是汇率不等于1"); }
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
				|| source == menuItemFirst || source == btnRemove
				|| source == menuItemRemove) {
			// isFirstLoad=true;
			try {
				// isFirstLoad=true;
				// editData=(PayRequestBillInfo)getDataObject();
				// onLoad();
				PayReqUtils.setValueToCell(editData, bindCellMap);
				tableHelper.reloadDeductTable(editData, getDetailTable(),
						deductTypeCollection);
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

			if (e.getActionCommand().indexOf("ActionPre") > 0
					|| e.getActionCommand().indexOf("ActionFirst") > 0
					|| e.getActionCommand().indexOf("ActionNext") > 0
					|| e.getActionCommand().indexOf("ActionLast") > 0) {
				addOrgPriceForEntryTable(kdtEntrys, bindCellMap, contractBill);

				/* modified by zhaoqin for R131008-0190 on 2013/11/29 start */
				// 重新显示付款申请单截至上次累计实付
				try {
					reCalData();
				} catch (Exception e2) {
					handUIExceptionAndAbort(e2);
				}
				/* modified by zhaoqin for R131008-0190 on 2013/11/29 end */
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

		/*
		 * modified by zhaoqin for R131214-0062 on 2014/01/06,
		 * 当前连续新增时，重新计算本期完工工程量
		 */
		if (getOprtState().equals(OprtState.ADDNEW)
				|| getOprtState().equals(OprtState.EDIT)) {
			try {
				updateCompletePrjAmt();
				this.completePrjAmtValue = txtcompletePrjAmt
						.getBigDecimalValue();
			} catch (BOSException e2) {
				e2.printStackTrace();
			}
		}

		try {
			reloadPartADeductDetails();
		} catch (Exception e1) {
			handUIExceptionAndAbort(e1);
		}
		kdtEntrys.getScriptManager().setAutoRun(true);
		kdtEntrys.getScriptManager().runAll();
	}

	/**
	 * 重新显示付款申请单截至上次累计实付 - R131008-0190
	 * 
	 * @author zhaoqin
	 * @date 2013/11/29
	 */
	private void reCalData() throws Exception {
		if (!getOprtState().equals(OprtState.ADDNEW)) { // 不是新增
			tableHelper.updateLstReqAmt(editData, false);
			tableHelper.reloadGuerdonValue(editData, null);
			tableHelper.reloadCompensationValue(editData, null);
			tableHelper.updateLstReqAmt(editData, true);
			setOrgAmountForEntry(kdtEntrys);
		}

		/**
		 * 重新显示付款申请单截至上次累计实付 STATUS_FINDVIEW合同成本信息里打开时设置了这个状态，所以这个状态也重新加载
		 * 详见PayRequestFullInfoUI 中的tblMain_tableClicked
		 */
		if (STATUS_VIEW == this.getOprtState()
				|| STATUS_EDIT == this.getOprtState()
				|| STATUS_FINDVIEW == this.getOprtState()) {
			// 处在工作流审批过程中，也不重新加载
			if (!isFromMsgCenterNoEdit()) {// check this..工作流看到的单据非实时数据了..
				setLstPriRaiedORPaied();
			}
		}

		// 本申请单累计实付款（本币）实时取值
		/* modified by zhaoqin for R140227-0281 on 2014/03/21 start */
		setTotalPayForReqPay();
		/*
		 * if(kdtEntrys.getCell(2, 10)!= null){ if(editData.getId() != null){
		 * totalPayAmtByReqId =
		 * getTotalPayAmtByThisReq(editData.getId().toString());
		 * totalPayAmtByReqId = (BigDecimal)totalPayMap.get("totalPayedAmt"); }
		 * BigDecimal exchangeRate = (BigDecimal)
		 * this.txtexchangeRate.getValue(BigDecimal.class); kdtEntrys.getCell(2,
		 * 7).setValue(FDCHelper.divide(totalPayAmtByReqId, exchangeRate));
		 * kdtEntrys.getCell(2, 10).setValue(totalPayAmtByReqId);
		 * 
		 * if (STATUS_VIEW == this.getOprtState()) { kdtEntrys.getCell(5,
		 * 11).setValue(FDCHelper.add(totalPayAmtByReqId, kdtEntrys.getCell(5,
		 * 3).getValue())); kdtEntrys.getCell(5,
		 * 10).setValue(FDCHelper.multiply(kdtEntrys.getCell(5, 11).getValue(),
		 * exchangeRate)); } }
		 */
		/* modified by zhaoqin for R140227-0281 on 2014/03/21 end */

		// 合同修订和变更都需要将金额同步反映到单据状态为保存或者是提交的付款申请单中的变更指令金额中去 by cassiel 2010-08-06
		if (!FDCBillStateEnum.AUDITTED.equals(this.editData.getState())) {
			if (PayReqUtils.isContractBill(editData.getContractId())) {
				tableHelper.updateDynamicValue(editData, contractBill,
						contractChangeBillCollection, paymentBillCollection);
			}
		} else {// PBG095801..审批的单据，合同付款次数也应更新过来。。。
			if (PayReqUtils.isContractBill(editData.getContractId())) {
				// ((ICell)
				// bindCellMap.get(PayRequestBillContants.PAYTIMES)).setValue
				// (String.valueOf(paymentBillCollection.size()));
			}
		}

		if (isAdvance()) {
			tableHelper.updateLstAdvanceAmt(editData, false);
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
			kdtEntrys.setEnabled(false);
		} else {
			setTableCellColorAndEdit();
			// Add by zhiyuan_tang 2010/07/30 处理在查看界面点修改，本次申请原币可用的BUG
			if (isPartACon) {
				kdtEntrys.getCell(rowIndex, columnIndex).getStyleAttributes()
						.setLocked(true);
			}
			actionAdjustDeduct.setEnabled(true);
		}

		// 修复 R111219-0113 【付款申请单】走网上审批流程，当提交的单据被打回修改时，提交人进入消息中心，
		// 点击“处理”直接修改单据的内容，例如，“本次申请原币金额”修改后，点击“填入汇总数”发现该字段为灰色，无法生效。By Owen_wen
		// btnInputCollect.setEnabled(false);
		mergencyState.setEnabled(true);
		chkIsPay.setEnabled(true);

		prmtPlanUnCon.setEnabled(true);
	}

	protected void afterSubmitAddNew() {
		super.afterSubmitAddNew();
		try {
			if (PayReqUtils.isContractBill(editData.getContractId())) {
				tableHelper.updateGuerdonValue(editData, editData
						.getContractId(), guerdonOfPayReqBillCollection,
						guerdonBillCollection);
				if (isAdvance()) {
					tableHelper.updateLstAdvanceAmt(editData, false);
				}
			}
			handleCodingRule();
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
		if (OprtState.ADDNEW.equals(getOprtState())) {
			txtpaymentProportion.setValue(FDCHelper.ZERO);
			txtcompletePrjAmt.setValue(FDCHelper.ZERO);
		}
		calAllCompletePrjAmt();
		if (isFromProjectFillBill) {
			kdtEntrys.getCell(rowIndex, columnIndex).getStyleAttributes()
					.setLocked(false);
			txtpaymentProportion.setEditable(false);
			txtcompletePrjAmt.setEditable(false);
		}

		try {
			tableHelper.updateLstReqAmt(editData, false);
			updateCompletePrjAmt();
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
	}

	/**
	 * 设置付款比例，已完工工程量与原币金额之间的关系： 已完工工程量金额＝原币金额÷付款比例
	 * 
	 * @author sxhong Date 2007-3-12
	 */
	private void setPropPrjAmount(String cause, DataChangeEvent e) {
		if (isFirstLoad
				|| (!txtpaymentProportion.isRequired())
				|| (isSeparate && contractBill != null && contractBill
						.isIsCoseSplit()))
			return;

		// 用本位币进行计算
		BigDecimal amount = FDCHelper
				.toBigDecimal(((ICell) bindCellMap
						.get(PayRequestBillContants.PROJECTPRICEINCONTRACT))
						.getValue());// txtBcAmount

		BigDecimal paymentProp = txtpaymentProportion.getBigDecimalValue();
		BigDecimal completePrj = txtcompletePrjAmt.getBigDecimalValue();

		if (amount == null)
			amount = FDCHelper.ZERO;
		if (paymentProp == null)
			paymentProp = FDCHelper.ZERO;
		if (completePrj == null)
			completePrj = FDCHelper.ZERO;
		if (cause.equals("amount")) {
			if (isFromProjectFillBill) {
				if (FDCHelper.toBigDecimal(
						txtcompletePrjAmt.getBigDecimalValue()).compareTo(
						FDCHelper.ZERO) == 0) {
					kdtEntrys.getCell(rowIndex, columnIndex).setValue(null);
					kdtEntrys.getCell(rowIndex, columnIndex + 1).setValue(null);
					// return;用else代替return，直接return很可能不运行后面的代码 by hpw
					// 2010-03-23
				} else {
					kdtEntrys.getCell(rowIndex, columnIndex).setValue(
							FDCHelper.divide(kdtEntrys.getCell(rowIndex,
									columnIndex + 1).getValue(),
									txtexchangeRate.getBigDecimalValue()));
				}
			} else {

				if (paymentProp.compareTo(FDCHelper.ZERO) == 0) {
					if (completePrj.compareTo(FDCHelper.ZERO) == 0) {
						// return;
					} else {
						txtpaymentProportion.setRequired(false);
						txtpaymentProportion.setValue(amount.setScale(4,
								BigDecimal.ROUND_HALF_UP).divide(completePrj,
								BigDecimal.ROUND_HALF_UP).multiply(
								FDCHelper.ONE_HUNDRED));
						txtpaymentProportion
								.setRequired(true && !isAutoComplete);
					}
				} else {
					txtcompletePrjAmt.setValue(amount.setScale(4,
							BigDecimal.ROUND_HALF_UP).divide(paymentProp,
							BigDecimal.ROUND_HALF_UP).multiply(
							FDCHelper.ONE_HUNDRED));
				}
			}

		} else if (cause.equals("completePrjAmt")) {
			if (kdtEntrys.getCell(rowIndex, columnIndex).getValue() != null) {
				// this.caculatePaymentProp();
			}

			/*
			 * if (completePrj.compareTo(FDCHelper.ZERO) == 0) { //
			 * txtpaymentProportion.setValue(FDCHelper.ZERO); // return; } else
			 * { txtpaymentProportion.setRequired(false);
			 * 
			 * BigDecimal paymentProportion =
			 * txtpaymentProportion.getBigDecimalValue()==null?BigDecimal.ZERO:
			 * txtpaymentProportion.getBigDecimalValue();
			 * 
			 * if( amount.compareTo(BigDecimal.ZERO) > 0 ) {
			 * txtpaymentProportion.setValue(amount.setScale(4,
			 * BigDecimal.ROUND_HALF_UP).divide(completePrj,
			 * BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED)); }else
			 * if( paymentProportion.compareTo(BigDecimal.ZERO) > 0 ) {
			 * kdtEntrys.getCell(rowIndex,
			 * columnIndex).setValue(FDCHelper.multiply(
			 * txtpaymentProportion.getBigDecimalValue(),
			 * txtcompletePrjAmt.getBigDecimalValue() )); }
			 * 
			 * txtpaymentProportion.setRequired(true && !isAutoComplete);
			 * 
			 * }
			 */
		} else {
			//
			if (isFromProjectFillBill) {
				if (FDCHelper.toBigDecimal(
						txtcompletePrjAmt.getBigDecimalValue()).compareTo(
						FDCHelper.ZERO) == 0) {
					kdtEntrys.getCell(rowIndex, columnIndex).setValue(null);
					kdtEntrys.getCell(rowIndex, columnIndex + 1).setValue(null);
					// return;
				} else {
					BigDecimal loadAmt = FDCHelper.divide(FDCHelper
							.multiply(txtcompletePrjAmt.getBigDecimalValue(),
									paymentProp), FDCHelper.ONE_HUNDRED);
					kdtEntrys.getCell(rowIndex, columnIndex + 1).setValue(
							loadAmt);
					kdtEntrys.getCell(rowIndex, columnIndex).setValue(
							FDCHelper.divide(loadAmt, txtexchangeRate
									.getBigDecimalValue()));
				}
			} else {// modified by ken_liu...
				// 比率被修改，计算金额。
				if (paymentProp != null) {
					// this.caculateReqAmt();
				}

				/*
				 * if (paymentProp.compareTo(FDCHelper.ZERO) == 0) { //
				 * txtcompletePrjAmt.setValue(FDCHelper.ZERO); // return; } else
				 * { BigDecimal prjAmt=
				 * txtcompletePrjAmt.getBigDecimalValue()==null
				 * ?BigDecimal.ZERO:txtcompletePrjAmt.getBigDecimalValue();
				 * 
				 * if( amount.compareTo(BigDecimal.ZERO) > 0 ) {
				 * txtcompletePrjAmt.setValue(amount.setScale(4,
				 * BigDecimal.ROUND_HALF_UP).divide(paymentProp,
				 * BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
				 * }else if( prjAmt.compareTo(BigDecimal.ZERO) > 0 ) {
				 * kdtEntrys.getCell(rowIndex,
				 * columnIndex).setValue(FDCHelper.multiply(
				 * txtpaymentProportion.getBigDecimalValue(),
				 * txtcompletePrjAmt.getBigDecimalValue() )); } }
				 */
			}
		}
		if (isAutoComplete) {
			txtcompletePrjAmt.setValue(amount);
			editData.setCompletePrjAmt(amount);
		}
		calAllCompletePrjAmt();
	}

	/**
	 * description 处理付款比例及已完工工程量的加载 在onLoad中调用
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
	private void initPaymentProp() {
		String contractId = editData.getContractId();
		if (contractId != null && !PayReqUtils.isConWithoutTxt(contractId)) { // 无文本合同
			txtpaymentProportion.setRequired(!isAutoComplete
					&& !(isSeparate && contractBill != null && contractBill
							.isIsCoseSplit())); // 当进度款比例不是自动100% 时必录
			// 当工程量确认流程与付款流程分离参数为 ‘是’ 并且合同是成本拆分的时候，工程量不是必录
			txtcompletePrjAmt.setRequired(!isAutoComplete
					&& !(isSeparate && contractBill != null && contractBill
							.isIsCoseSplit())); //
			try {

				if (!contractBill.isHasSettled()
						&& editData.getPaymentProportion() == null) {
					//editData.setPaymentProportion(contractBill.getPayScale());
					//txtpaymentProportion.setValue(contractBill.getPayScale());
				} else if (contractBill.isHasSettled()
						&& editData.getState() != FDCBillStateEnum.AUDITTED) {
					if (isSimpleFinancial) {
						txtpaymentProportion.setEditable(true);
						txtcompletePrjAmt.setEditable(true);
						txtpaymentProportion.setRequired(true);
						txtcompletePrjAmt.setRequired(true);

					} else {
						editData.setPaymentProportion(null);
						editData.setCompletePrjAmt(contractBill.getSettleAmt());
						txtcompletePrjAmt.setValue(FDCHelper
								.toBigDecimal(contractBill.getSettleAmt()));
						txtpaymentProportion.setEditable(false);
						txtcompletePrjAmt.setEditable(false);
						txtpaymentProportion.setRequired(false);
						txtcompletePrjAmt.setRequired(false);
					}
					// 分离
					setEditableAndRequiredBySeparateParam();
				}

			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			}
		} else {
			txtpaymentProportion.setEditable(false);
			txtcompletePrjAmt.setEditable(false);
		}
	}

	/**
	 * 付款申请单累计额大于合同金额最新造价时提醒（提交，审批）
	 * 
	 * @param ctx
	 * @param billInfo
	 * @throws BOSException
	 */
	private void checkAmt(PayRequestBillInfo billInfo) throws Exception {
		// 初始化表格已经动态加载过一次最新造价，这里却又读取一个死的字段，即使字段有反写,BUT.. ken_liu
		// BigDecimal latestPrice =
		// FDCHelper.toBigDecimal(billInfo.getLatestPrice(), 2);
		BigDecimal latestPrice = FDCHelper.toBigDecimal(this.localCurrency);
		/*********
		 * 付款申请单的币别，必须和合同的币别相同 2008-11-14 周勇 币别是否是本币 如果是本币则比较本币 如果是外币则比较原币
		 */
		boolean isBaseCurrency = true;
		CurrencyInfo cur = (CurrencyInfo) this.prmtcurrency.getValue();
		if (!cur.getId().toString().equals(baseCurrency.getId().toString())) {
			isBaseCurrency = false;
			latestPrice = FDCHelper.toBigDecimal(this.oriCurrency);
		}

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.appendFilterItem("contractId", billInfo.getContractId());
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.SAVED_VALUE,
						CompareType.NOTEQUALS));

		view.getSelector().add("hasClosed");
		view.getSelector().add("amount");
		view.getSelector().add("state");
		view.getSelector().add("originalamount");
		view.getSelector().add("completePrjAmt");
		view.getSelector().add("projectPriceInContract");
		view.getSelector().add("entrys.paymentBill.billStatus");
		view.getSelector().add("entrys.paymentBill.amount");
		view.getSelector().add("entrys.paymentBill.localAmt");
		view.getSelector().add("paymentType.payType.id");
		PayRequestBillCollection c = PayRequestBillFactory.getRemoteInstance()
				.getPayRequestBillCollection(view);

		BigDecimal total = FDCHelper.ZERO;
		BigDecimal completeTotal = FDCHelper.ZERO;
		// BigDecimal projectPriceInContractTotal = FDCHelper.ZERO;
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
			// 统计原币金额 ( 合同内工程款 + 奖励 - 扣款 )
			total = total.add(FDCHelper.toBigDecimal(info.getOriginalAmount()));
			// 本期完成金额（已完工+奖励-索赔-扣款）
			completeTotal = completeTotal.add(FDCHelper.toBigDecimal(info
					.getCompletePrjAmt()));
			// 合同内工程款 （ 本期发生原币 ）
			// projectPriceInContractTotal =
			// projectPriceInContractTotal.add(FDCHelper
			// .toBigDecimal(info.getProjectPriceInContract()));
			boolean isKeepAmt = false;
			if (info.getPaymentType() != null
					&& info.getPaymentType().getPayType() != null
					&& info.getPaymentType().getPayType().getId().toString()
							.equals(PaymentTypeInfo.keepID)) {
				isKeepAmt = true;
			}
			BigDecimal temp = FDCHelper.ZERO;
			BigDecimal tempLocal = FDCHelper.ZERO;
			BigDecimal _tempActuallyPayOriAmt = FDCHelper.ZERO;// 累计实付款原币临时变量
			BigDecimal _tempActuallyPayLocalAmt = FDCHelper.ZERO;// 累计实付款原币临时变量
			if (info.isHasClosed()) {
				if (info.getEntrys().size() > 0) {
					PaymentBillInfo payment = info.getEntrys().get(0)
							.getPaymentBill();
					if (payment != null) {
						_tempActuallyPayOriAmt = payment.getAmount();
						_tempActuallyPayLocalAmt = payment.getLocalAmt();
					} else {
						_tempActuallyPayOriAmt = FDCHelper
								.toBigDecimal(FDCHelper.add(
										_tempActuallyPayOriAmt, info
												.getOriginalAmount()));
						_tempActuallyPayLocalAmt = FDCHelper
								.toBigDecimal(FDCHelper.add(
										_tempActuallyPayLocalAmt, info
												.getAmount()));
					}
				}
			} else {
				_tempActuallyPayOriAmt = FDCHelper.toBigDecimal(FDCHelper.add(
						_tempActuallyPayOriAmt, info.getOriginalAmount()));
				_tempActuallyPayLocalAmt = FDCHelper.toBigDecimal(FDCHelper
						.add(_tempActuallyPayLocalAmt, info.getAmount()));
			}
			// 如果付款申请单已经审批，即已经有关联的付款单
			if (FDCBillStateEnum.AUDITTED.equals(info.getState())) {
				int tempInt = info.getEntrys().size();
				for (int j = 0; j < tempInt; j++) {
					PaymentBillInfo payment = info.getEntrys().get(j)
							.getPaymentBill();
					if (payment != null
							&& payment.getBillStatus() == BillStatusEnum.PAYED) { // 并且该付款单已经付款
						temp = temp.add(FDCHelper.toBigDecimal(payment
								.getAmount()));
						tempLocal = tempLocal.add(FDCHelper
								.toBigDecimal(payment.getLocalAmt()));
						payAmt = payAmt.add(FDCHelper.toBigDecimal(payment
								.getAmount()));
						payAmtLocal = payAmtLocal.add(FDCHelper
								.toBigDecimal(payment.getLocalAmt()));
					} else if (payment != null
							&& payment.getBillStatus() != BillStatusEnum.PAYED) {// 未付款
						// ，
						// 需要记录一下申请未付金额
						noPayAmt = FDCHelper.add(noPayAmt, info
								.getOriginalAmount());
						noPayAmtLocal = FDCHelper.add(noPayAmtLocal, info
								.getAmount());
					}
					if (temp.compareTo(FDCHelper.ZERO) == 0) {
						temp = FDCHelper.toBigDecimal(info.getOriginalAmount());
					}
					if (tempLocal.compareTo(FDCHelper.ZERO) == 0) {
						tempLocal = FDCHelper.toBigDecimal(info.getAmount());
					}
				}
				if (!info.isHasClosed()) {// 已关闭的不应该包含进去
					// 请款单的请款金额 - 该请款单对应付款单的付款金额 = 申请未付金额
					noPayAmt = FDCHelper.add(noPayAmt, FDCHelper.subtract(info
							.getOriginalAmount(), temp));
					// 请款单的请款金额 - 该请款单对应付款单的付款金额 = 申请未付金额
					noPayAmtLocal = FDCHelper.add(noPayAmtLocal, FDCHelper
							.subtract(info.getAmount(), tempLocal));
				}
			} else {// 还没有付款单
				temp = FDCHelper.toBigDecimal(info.getOriginalAmount());
				tempLocal = FDCHelper.toBigDecimal(info.getAmount());
				if (!info.isHasClosed()) {// 已关闭的不应该包含进去
					noPayAmt = FDCHelper.add(noPayAmt, FDCHelper
							.toBigDecimal(info.getOriginalAmount()));
					noPayAmtLocal = FDCHelper.add(noPayAmtLocal, FDCHelper
							.toBigDecimal(info.getAmount()));
				}
			}
			if (!isKeepAmt) {
				// 如果付款申请单已经审批，即已经有关联的付款单.那么在进行"进度款+结算款不能超过合同结算价-保修金"校验的时候
				// 进度款和结算款就应该取付款单上的金额而不是再是请款单上的 by cassiel_peng 2009-12-06
				if (info.isHasClosed()) {
					if (info.getEntrys().size() > 0) {
						PaymentBillInfo payment = info.getEntrys().get(0)
								.getPaymentBill();
						if (payment != null) {
							noKeepAmt = FDCHelper.toBigDecimal(FDCHelper.add(
									noKeepAmt, _tempActuallyPayOriAmt));
							noKeepAmtLocal = FDCHelper.toBigDecimal(FDCHelper
									.add(noKeepAmtLocal,
											_tempActuallyPayLocalAmt));
						} else {
							noKeepAmt = noKeepAmt.add(FDCHelper
									.toBigDecimal(info.getOriginalAmount()));
							noKeepAmtLocal = noKeepAmtLocal.add(FDCHelper
									.toBigDecimal(info.getAmount()));
						}
					}
				} else {
					noKeepAmt = noKeepAmt.add(FDCHelper.toBigDecimal(info
							.getOriginalAmount()));
					noKeepAmtLocal = noKeepAmtLocal.add(FDCHelper
							.toBigDecimal(info.getAmount()));
				}
			}
			// totalpayAmount = totalpayAmount.add(temp);
			// totalpayAmountLocal = totalpayAmountLocal.add(tempLocal);
		}

		total = total.add(FDCHelper.toBigDecimal(billInfo.getOriginalAmount()));
		// totalLocal =
		// totalLocal.add(FDCHelper.toBigDecimal(billInfo.getAmount()));

		completeTotal = completeTotal.add(FDCHelper.toBigDecimal(billInfo
				.getCompletePrjAmt()));
		if (contractBill != null && contractBill.isHasSettled()) {
			completeTotal = contractBill.getSettleAmt();
		}
		// projectPriceInContractTotal =
		// projectPriceInContractTotal.add(FDCHelper
		// .toBigDecimal(billInfo.getProjectPriceInContract()));
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
		if (contractBill != null
				&& contractBill.isHasSettled()
				&& billInfo.getPaymentType() != null
				&& billInfo.getPaymentType().getPayType() != null
				&& billInfo.getPaymentType().getPayType().getId().toString()
						.equals(PaymentTypeInfo.settlementID)) {
			builder.clear();
			if (isBaseCurrency) {
				builder
						.appendSql("select (fsettleprice-fqualityGuarante) as amount from t_con_contractsettlementbill where fcontractbillid=");
			} else {
				builder
						.appendSql("select foriginalamount*(1-isnull(fqualityGuaranteRate,0)/100) as amount from t_con_contractsettlementbill where fcontractbillid=");
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
				BigDecimal amount = FDCHelper.toBigDecimal(rowSet
						.getBigDecimal("amount"), 2);
				if (isBaseCurrency) {
					noKeepAmtLocal = FDCHelper.add(noKeepAmtLocal,
							billInfo.getAmount()).setScale(2,
							BigDecimal.ROUND_HALF_UP);
					if (noKeepAmtLocal.compareTo(amount) > 0) {
						FDCMsgBox.showError(this, "本币：进度款+结算款不能超过合同结算价-保修金");
						SysUtil.abort();
					}
				} else {
					noKeepAmt = FDCHelper.add(noKeepAmt,
							billInfo.getOriginalAmount()).setScale(2,
							BigDecimal.ROUND_HALF_UP);
					if (noKeepAmt.compareTo(amount) > 0) {
						FDCMsgBox.showError(this, "原币：进度款+结算款不能超过合同结算价-保修金");
						SysUtil.abort();
					}
				}

			}
		} else if (billInfo.getPaymentType() != null
				&& billInfo.getPaymentType().getPayType() != null
				&& billInfo.getPaymentType().getPayType().getId().toString()
						.equals(PaymentTypeInfo.keepID)) {
			builder.clear();
			if (isBaseCurrency) {
				builder
						.appendSql("select fqualityGuarante as amount from t_con_contractsettlementbill where fcontractbillid=");
			} else {
				/********
				 * 取原币的保修金金额
				 */
				builder
						.appendSql("select foriginalamount*isnull(fqualityGuaranteRate,0)/100 as amount from t_con_contractsettlementbill where fcontractbillid=");
			}

			builder.appendParam(billInfo.getContractId());
			IRowSet rowSet = builder.executeQuery();
			if (rowSet.size() == 1) {
				rowSet.next();
				BigDecimal amount = FDCHelper.toBigDecimal(rowSet
						.getBigDecimal("amount"), 2);

				view = new EntityViewInfo();
				filter = new FilterInfo();
				filter.appendFilterItem("contractId", billInfo.getContractId());
				filter.appendFilterItem("paymentType.payType.id",
						PaymentTypeInfo.keepID);
				if (billInfo.getId() != null)
					filter.getFilterItems().add(
							new FilterItemInfo("id", billInfo.getId()
									.toString(), CompareType.NOTEQUALS));
				if (billInfo.getId() != null)
					filter.appendFilterItem("id", billInfo.getId().toString());
				view.setFilter(filter);
				view.getSelector().add("amount");
				PayRequestBillCollection coll = PayRequestBillFactory
						.getRemoteInstance().getPayRequestBillCollection(view);
				BigDecimal keepAmt = FDCHelper.ZERO;
				BigDecimal keepAmtLocal = FDCHelper.ZERO;
				for (Iterator iter = coll.iterator(); iter.hasNext();) {
					PayRequestBillInfo keepInfo = (PayRequestBillInfo) iter
							.next();
					if (keepInfo.getOriginalAmount() != null) {
						keepAmt = keepAmt.add(keepInfo.getOriginalAmount());
					}
					if (keepInfo.getAmount() != null) {
						keepAmtLocal = keepAmtLocal.add(keepInfo.getAmount());
					}
				}

				if (isBaseCurrency) {
					keepAmtLocal = FDCHelper.add(keepAmtLocal,
							billInfo.getAmount()).setScale(2,
							BigDecimal.ROUND_HALF_UP);
					if ((keepAmtLocal.compareTo(amount) > 0)) {
						FDCMsgBox.showError(this, "本币：合同累计已付保修款金额超过合同结算保修金额!”");
						SysUtil.abort();
					}
				} else {
					keepAmt = FDCHelper.add(keepAmt,
							billInfo.getOriginalAmount()).setScale(2,
							BigDecimal.ROUND_HALF_UP);
					if ((keepAmt.compareTo(amount) > 0)) {
						FDCMsgBox.showError(this, "原币：合同累计已付保修款金额超过合同结算保修金额!”");
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
				builder
						.appendSql("select fpayPercForWarn from t_con_contractbill where fid=");

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
					payPercForWarn = FDCHelper.toBigDecimal(rowSet
							.getBigDecimal("fpayPercForWarn"), 2);
				}
				// 合同最新造价
				map = FDCUtils.getLastAmt_Batch(null, new String[] { billInfo
						.getContractId() });
				if (map != null && map.size() == 1) {
					conLastestPrice = (BigDecimal) map.get(billInfo
							.getContractId());
				}
				payRate = FDCHelper.divide(FDCHelper.multiply(conLastestPrice,
						payPercForWarn), FDCHelper.ONE_HUNDRED);
				totalpayAmountLocal = FDCHelper.toBigDecimal(
						totalpayAmountLocal, 2);
				// totalpayAmount = FDCHelper.toBigDecimal(totalpayAmount, 2);

				if (totalpayAmountLocal.compareTo(payRate) > 0) {
					String str = "本币：当前单据合同下的累计实付款+未付的申请单超过了付款提示比例:";
					str = str + "\n累计金额:" + totalpayAmountLocal + " 其中,实付数："
							+ FDCHelper.toBigDecimal(payAmtLocal, 2)
							+ "  申请未付数:"
							+ FDCHelper.toBigDecimal(noPayAmtLocal, 2);
					str = str + "\n付款提示比例金额：" + payRate + "(" + conLastestPrice
							+ "*" + payPercForWarn + "%)";
					if ("0".equals(allPaidMoreThanConPrice())) {// 严格控制
						FDCMsgBox.showDetailAndOK(this, "超过付款提示比例,请查看详细信息",
								str, 2);
						SysUtil.abort();
					} else if ("1".equals(allPaidMoreThanConPrice())) {// 提示控制
						FDCMsgBox.showDetailAndOK(this, "超过付款提示比例,请查看详细信息",
								str, 1);
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
			// FDCMsgBox.showDetailAndOK(this, "超过付款提示比例,请查看详细信息", str, 1);
			// }
			// }
		}
		/******
		 * 判断最新造价
		 */
		if (isBaseCurrency) {
			// if (totalLocal.compareTo(latestPrice) > 0) {
			// // 严格控制不允许提交
			// if (isControlCost) {
			// FDCMsgBox.showError(this, "合同下付款申请单的累计金额(本币)大于合同最新造价(本币)！");
			// SysUtil.abort();
			// } else {
			// int result = FDCMsgBox.showConfirm2(this,
			// "合同下付款申请单的累计金额(本币)大于合同最新造价(本币).是否提交?");
			// if (result != FDCMsgBox.OK) {
			// SysUtil.abort();
			// }
			// }
			// }
		} else {
			/**********
			 * 需要使用原币的最新造价比较
			 * 下面单独加了showMsg4TotalPayReqAmtMoreThanConPrice方法只注释了本币不注释外币。。。。。
			 */
			// total = FDCHelper.toBigDecimal(total, 2);
			// lastestPriceOriginal =
			// FDCHelper.toBigDecimal(lastestPriceOriginal, 2);
			// if (total.compareTo(lastestPriceOriginal) > 0) {
			// // 严格控制不允许提交
			// if (isControlCost) {
			// FDCMsgBox.showError(this, "合同下付款申请单的累计金额(原币)大于合同最新造价(原币)！");
			// SysUtil.abort();
			// } else {
			// int result = FDCMsgBox.showConfirm2(this,
			// "合同下付款申请单的累计金额(原币)大于合同最新造价(原币).是否提交?");
			// if (result != FDCMsgBox.OK) {
			// SysUtil.abort();
			// }
			// }
			// }
		}
		// BigDecimal totalReqAmt = payAmtLocal.add(billInfo.getAmount());//上面加了
		/* modified by zhaoqin for R140425-0083 on 2014/05/07 */
		BigDecimal totalReqAmt = FDCHelper.add(payAmtLocal, billInfo
				.getAmount()); // = 截至本次累计实付本币 + 本次申请本币
		/* modified by zhaoqin for R140319-0098 on 2014/03/21 start */
		if (!isBaseCurrency) {
			/* modified by zhaoqin for R140425-0083 on 2014/05/07 */
			// totalReqAmt = payAmt; // = 截至本次累计实付原币 + 本次申请原币
			totalReqAmt = FDCHelper.add(payAmt, billInfo.getOriginalAmount());
		}
		/* modified by zhaoqin for R140319-0098 on 2014/03/21 end */

		if (totalReqAmt.compareTo(latestPrice) == 1) {
			if (isControlCost) {
				FDCMsgBox.showWarning(this, "\"本次申请+累计实付\" 不能大于合同最新造价!");
				SysUtil.abort();
			}
		}

		// 此段功能错误，并且上面已实现校验byhpw
		/*
		 * // if (isBaseCurrency) {不管币别校验本币即可 //
		 * 此处如是查看状态时，当前单据的金额被重复统计了，后面也只判断修改状态减去，没有查看 // 所以查看时，不加当前单据金额 edit by
		 * emanon // BigDecimal totalLocal = //
		 * FDCHelper.add(ContractClientUtils.getReqAmt //
		 * (billInfo.getContractId()), billInfo.getAmount()); BigDecimal
		 * totalLocal = FDCHelper.ZERO; if (STATUS_VIEW.equals(getOprtState())
		 * || STATUS_FINDVIEW.equals(getOprtState())) { totalLocal =
		 * ContractClientUtils.getReqAmt(billInfo.getContractId()); } else {
		 * totalLocal =
		 * FDCHelper.add(ContractClientUtils.getReqAmt(billInfo.getContractId
		 * ()), billInfo.getAmount()); } // 避免在修改时重复记录，需要减去这张单据之前保存在数据库里的值。 if
		 * (STATUS_EDIT.equals(getOprtState())) { EntityViewInfo _view = new
		 * EntityViewInfo(); SelectorItemCollection selector = new
		 * SelectorItemCollection(); selector.add("amount"); FilterInfo _filter
		 * = new FilterInfo(); _filter.getFilterItems().add(new
		 * FilterItemInfo("fdcPayReqID", billInfo.getId().toString()));
		 * _view.setFilter(_filter); PayRequestBillInfo _tempReqInfo = null;
		 * PaymentBillInfo _tempPayInfo = null; if (billInfo.isHasClosed()) { if
		 * (
		 * PaymentBillFactory.getRemoteInstance().getPaymentBillCollection(_view
		 * ).size() > 0) { _tempPayInfo =
		 * PaymentBillFactory.getRemoteInstance().
		 * getPaymentBillCollection(_view).get(0); totalLocal =
		 * FDCHelper.subtract(totalLocal, _tempPayInfo.getAmount()); } } else {
		 * _tempReqInfo =
		 * PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new
		 * ObjectUuidPK(billInfo.getId())); totalLocal =
		 * FDCHelper.subtract(totalLocal, _tempReqInfo.getAmount()); } }
		 * 
		 * if (totalLocal.compareTo(latestPrice) > 0) { // 严格控制不允许提交
		 * showMsg4TotalPayReqAmtMoreThanConPrice(isControlCost); } // }
		 */
		if (isSimpleFinancial) {
			if (billInfo.getPaymentType() == null
					|| billInfo.getPaymentType().getPayType() == null) {
				FDCMsgBox.showError(this, "付款类型有误");
				SysUtil.abort();
			}

			// R131224-0144 合同下付款申请单的累计申请金额（合同内工程款）直接用界面上‘截至本次累计申请’
			int currencyIndex = isBaseCurrency ? 9 : 8; // 本币或原币的比较不同
			BigDecimal projectPriceInContractTotal = FDCNumberHelper
					.toBigDecimal(kdtEntrys.getCell(5, currencyIndex)
							.getValue(), 2);
			if (projectPriceInContractTotal.compareTo(latestPrice) > 0) {
				showMsg4TotalPayReqAmtMoreThanConPrice(isControlCost);
			}
			if (!isAutoComplete && contractBill != null
					&& contractBill.isHasSettled()) {
				if (FDCHelper.toBigDecimal(completeTotal, 2).compareTo(
						latestPrice) > 0) {
					String msg = "合同下付款申请单的累计已完工工程量不能大于合同最新造价";
					FDCMsgBox.showWarning(this, msg);
					SysUtil.abort();
				}
			}
			// 这里有return，后面加校验时注意啦
			return;
			// 简单模式到此结束
		}
		if (FDCHelper.toBigDecimal(completeTotal, 2).compareTo(latestPrice) > 0) {
			if (billInfo.getPaymentType() == null
					|| billInfo.getPaymentType().getPayType() == null) {
				FDCMsgBox.showError(this, "付款类型有误");
				SysUtil.abort();
			}
			if (billInfo.getPaymentType().getPayType().getId().toString()
					.equals(PaymentTypeInfo.progressID)) {
				String msg = "合同下付款申请单的累计已完工工程量大于合同最新造价，是否提交?";
				int result = FDCMsgBox.showConfirm2(this, msg);
				if (result != FDCMsgBox.OK) {
					SysUtil.abort();
				}
			}
		}

	}

	/**
	 * 覆盖抽象类处理编码规则的行为,统一在FDCBillEditUI.handCodingRule方法中处理
	 */
	protected void setAutoNumberByOrg(String orgType) {

	}

	/**
	 * description 查看合同
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
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
		if (windows instanceof UIModelDialog || windows == null) {
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

	/**
	 * description 查看合同执行情况
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
	private void viewContractExecInfo(String id) throws UIException {
		if (id == null)
			return;
		String editUIName = null;
		if (PayReqUtils.isContractBill(id)) {
			editUIName = com.kingdee.eas.fdc.contract.client.ContractFullInfoUI.class
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
		if (windows instanceof UIModelDialog || windows == null) {
			type = UIFactoryName.MODEL;
		}
		IUIWindow contractUiWindow = UIFactory.createUIFactory(type).create(
				editUIName, uiContext, null, "FINDVIEW");
		if (contractUiWindow != null) {
			contractUiWindow.show();
		}
	}

	public void actionContractExecInfo_actionPerformed(ActionEvent e)
			throws Exception {
		String contractId = this.editData.getContractId();
		viewContractExecInfo(contractId);
	}

	/**
	 * description 查看预算余额
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * 
	 * @version EAS 7.0
	 * @see com.kingdee.eas.fdc.contract.client.AbstractPayRequestBillEditUI#actionViewMbgBalance_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionViewMbgBalance_actionPerformed(ActionEvent e)
			throws Exception {

		PayRequestBillInfo payReqInfo = this.editData;
		IBgControlFacade iCtrl = BgControlFacadeFactory.getRemoteInstance();
		if (fdcBudgetParam.isAcctCtrl()) {
			if (this.editData.getId() == null) {
				return;
			}
			SelectorItemCollection selector = new SelectorItemCollection();
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
			// selector.add("acctPays.costAccount.longNumber");
			// selector.add("acctPays.costAccount.displayName");
			selector.add("acctPays.costAccount.name");
			selector.add("acctPays.period.*");
			selector.add("orgUnit.id");
			selector.add("orgUnit.number");
			selector.add("orgUnit.name");
			selector.add("currency.id");
			selector.add("currency.number");
			selector.add("localCurrency.id");
			selector.add("localCurrency.number");
			selector.add("localCurrency.name");

			payReqInfo = PayRequestBillFactory.getRemoteInstance()
					.getPayRequestBillInfo(
							new ObjectUuidPK(this.editData.getId().toString()),
							selector);

			if (payReqInfo.getAcctPays() == null
					|| payReqInfo.getAcctPays().size() == 0) {
				return;
			}
			// for(Iterator
			// iter=payReqInfo.getAcctPays().iterator();iter.hasNext();){
			// PayRequestAcctPayInfo info=(PayRequestAcctPayInfo)iter.next();
			// //处理longnumber以便与预算匹配
			// String lgNumber = info.getCostAccount().getLongNumber();
			// if(lgNumber!=null){
			// lgNumber=lgNumber.replace('!', '.');
			// }
			// info.getCostAccount().setLongNumber(lgNumber);
			//				
			// }

		}
		/*
		 * //接口1：根据界面参数取最新的预算余额 BgCtrlResultCollection coll =
		 * iCtrl.getBudget(FDCConstants.PayRequestBill, null, payReqInfo);
		 * //接口2：取数据库中的余额 BgCtrlResultCollection coll =
		 * BudgetCtrlFacadeFactory.getRemoteInstance
		 * ().getBudget(payReqInfo.getId().toString());
		 * 
		 * UIContext uiContext = new UIContext(this);
		 * uiContext.put(BgHelper.BGBALANCE, coll);
		 * 
		 * IUIWindow uiWindow =
		 * UIFactory.createUIFactory(UIFactoryName.MODEL).create
		 * (BgBalanceViewUI.class.getName(), uiContext, null, STATUS_VIEW);
		 * uiWindow.show();
		 */
		// 与1接口相同 by hpw 2011.6.2
		BudgetCtrlClientCaller.showBalanceViewUI(payReqInfo, this);

		payReqInfo = null;
	}

	protected void initWorkButton() {
		super.initWorkButton();

		/* modified by zhaoqin for BT868138 on 2015/01/18 start */
		// menuItemViewPayDetail.setText(menuItemViewPayDetail.getText() +
		// "(D)");
		// menuItemViewPayDetail.setMnemonic('D');
		// actionViewPayDetail.setEnabled(true);
		// actionViewPayDetail.putValue(Action.SMALL_ICON,
		// EASResource.getIcon("imgTbtn_sequencecheck"));
		// menuItemViewPayDetail.setText(menuItemViewPayDetail.getText() +
		// "(D)");
		// menuItemViewPayDetail.setMnemonic('D');
		actionViewPayDetail.setVisible(false);
		actionViewPayDetail.setEnabled(false);
		menuItemViewPayDetail.setVisible(false);
		menuItemViewPayDetail.setEnabled(false);
		/* modified by zhaoqin for BT868138 on 2015/01/18 end */

		actionViewContract.setEnabled(true);
		actionViewContract.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_sequencecheck"));
		actionViewMbgBalance.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_sequencecheck"));
		actionAssociateUnSettled.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_settingrelating"));
		actionAssociateAcctPay.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_seerelating"));
		actionMonthReq.putValue(Action.SMALL_ICON, EASResource
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
		if (this.isAutoComplete) {
			txtcompletePrjAmt.setRequired(false);

			// R141104-0009: 参数FDC072_PAYPROGRESS为“是”，“累计已完工工程量”和“累计应付付款比例”不显示。
			// 已经和张祖艳、王亮确认过， 这种控制逻辑是错误的。 by skyiter_wang 2015/01/13
			// contAllCompletePrjAmt.setVisible(false);
			// contAllPaymentProportion.setVisible(false);
		}
		menuItemViewMaterialConfirm.setText(menuItemViewMaterialConfirm
				.getText()
				+ "(M)");
		menuItemViewMaterialConfirm.setMnemonic('M');

		actionViewMaterialConfirm.setEnabled(true);
		actionViewMaterialConfirm.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_sequencecheck"));

		this.menuItemContractExecInfo.setIcon(EASResource
				.getIcon("imgTbtn_execute"));
		this.btnContractExecInfo
				.setIcon(EASResource.getIcon("imgTbtn_execute"));
	}

	public void kdtEntrys_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
		if (e.getType() == 0) {
			return;
		}
	}

	/**
	 * 业务日期改变，取对应的合同滚动计划
	 */
	protected void pkbookedDate_dataChanged(DataChangeEvent e) throws Exception {
		StringBuffer format = new StringBuffer();
		Date bookDate = (Date) pkbookedDate.getValue();
		Calendar cal = Calendar.getInstance();
		cal.setTime(bookDate);
		format.append("$contractName$ ");
		format.append(cal.get(Calendar.YEAR));
		format.append("年");
		format.append(cal.get(Calendar.MONTH) + 1);
		format.append("月付款计划");
		prmtPlanHasCon.setDisplayFormat(format.toString());
		initPrmtPlanUnCon();

		if (STATUS_ADDNEW.equals(getOprtState())
				|| STATUS_EDIT.equals(getOprtState())) {
			String con = editData.getContractId();
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder
					.appendSql("select con.FID as id,con.FContractID as conID,con.FContractName as conName from T_FNC_FDCDepConPayPlanContract as con ");
			builder
					.appendSql("left join T_FNC_FDCDepConPayPlanCE as cone on cone.FParentC = con.FID ");
			builder
					.appendSql("left join T_FNC_FDCDepConPayPlanBill as head on head.FID = con.FHeadID ");
			builder
					.appendSql("where (head.FState = '4AUDITTED' or head.FState = '10PUBLISH') ");
			builder.appendSql(" and con.FContractID = ");
			builder.appendParam(con);
			builder.appendSql(" and cone.FMonth >= ");
			builder.appendParam(BudgetViewUtils.getFirstDay(bookDate));
			builder.appendSql(" and cone.FMonth <= ");
			builder.appendParam(BudgetViewUtils.getLastDay(bookDate));
			// added by ken...取最新的记录项
			builder.appendSql(" order by head.FYear desc,head.FMonth DESC");

			IRowSet rowSet = builder.executeQuery();
			if (rowSet != null && rowSet.size() >= 1) {
				rowSet.next();
				String id = rowSet.getString("id");
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("id");
				sic.add("contract.id");
				sic.add("contractName");
				sic.add("head.deptment.name");
				sic.add("head.year");
				sic.add("head.month");
				FDCDepConPayPlanContractInfo info = FDCDepConPayPlanContractFactory
						.getRemoteInstance().getFDCDepConPayPlanContractInfo(
								new ObjectUuidPK(id), sic);
				prmtPlanHasCon.setValue(info);

				if (info != null && info.getId() != null) {
					setDepPlanState();
				} else {
					kdDepPlanState.setSelectedItem(DepPlanStateEnum.noPlan);
				}

				contPlanHasCon.setVisible(true);
				prmtPlanHasCon.setEnabled(false);
				contPlanUnCon.setVisible(false);
				if (!"不控制".equals(CONTROLPAYREQUEST)) {
					prmtPlanHasCon.setRequired(false);
					prmtPlanUnCon.setRequired(false);
				}
			} else {
				contPlanHasCon.setVisible(false);
				contPlanUnCon.setVisible(true);
				if (!"不控制".equals(CONTROLPAYREQUEST)) {
					prmtPlanHasCon.setRequired(false);
					prmtPlanUnCon.setRequired(false);
				}
			}
		}

		if (!isCostSplitContract) {
			prmtPlanHasCon.setRequired(false);
			prmtPlanUnCon.setRequired(false);
		}
	}

	/**
	 * 描述：
	 * 
	 * @param info
	 * @Author：keyan_zhao
	 * @CreateTime：2012-11-6
	 */
	 
	private void setDepPlanState() {
		Date bookDate = (Date) pkbookedDate.getValue();
		Date firstDay = BudgetViewUtils.getFirstDay(bookDate);
		Date lastDay = BudgetViewUtils.getLastDay(bookDate);
		String projectId = editData.getCurProject().getId().toString();

		try {
			BigDecimal localBudget = getLocalBudget(firstDay, lastDay,
					projectId);

			/* modified by zhaoqin for R131218-0367 on 2013/12/25 start */
			// BigDecimal actPaied = FDCBudgetUtil.getActPaied(firstDay,
			// lastDay, editData.getContractId());
			BigDecimal floatFund = FDCBudgetUtil.getPayRequestBillFloatFund(
					firstDay, lastDay, editData.getContractId(), editData
							.getId());
			Object value = null;
			if (bindCellMap.get(PayRequestBillContants.PROJECTPRICEINCONTRACT) != null) {
				value = ((ICell) bindCellMap
						.get(PayRequestBillContants.PROJECTPRICEINCONTRACT))
						.getValue();
			}
			// 本期已付工程款
			BigDecimal paid = FDCBudgetUtil.getPaid(firstDay, lastDay, editData
					.getContractId());
			// BigDecimal subtract = FDCHelper.subtract(localBudget,
			// FDCHelper.add(actPaied, floatFund));
			BigDecimal subtract = FDCHelper.subtract(localBudget, FDCHelper
					.add(paid, floatFund));
			/* modified by zhaoqin for R131218-0367 on 2013/12/25 end */

			if (FDCHelper.compareTo(subtract, value) >= 0) {
				kdDepPlanState.setSelectedItem(DepPlanStateEnum.inPlan);
			} else {
				kdDepPlanState.setSelectedItem(DepPlanStateEnum.outPlan);
			}
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		} catch (SQLException e) {
			handUIExceptionAndAbort(e);
		}

	}

	protected void pkpayDate_dataChanged(DataChangeEvent e) throws Exception {
		// if (this.getOprtState().equals(OprtState.VIEW) || (editData != null
		// && (editData.getState() == FDCBillStateEnum.AUDITTED ||
		// editData.getState() == FDCBillStateEnum.AUDITTING))) {
		// return;
		// }
		Object objNew = pkpayDate.getValue();
		Object objOld = e.getOldValue();
		BigDecimal planAmt = FDCHelper.ZERO;
		String contractId = editData.getContractId();
		String thisBillid = new String();
		Timestamp startTime = null;
		Timestamp endTime = null;
		if (objNew == null) {
			objNew = new Date();
		}

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
				 * dateNew.getYear() == dateOld.getYear() && dateNew.getMonth()
				 * == dateOld.getMonth()) {
				 * planAmt=FDCHelper.toBigDecimal(editData
				 * .getCurPlannedPayment()); } else {}
				 */

				// 相同时期仍然从数据库内取以同步最新的付款计划
				if (contractId == null) {
					return;
				}
				Date dateNew = (Date) objNew;
				Calendar cal = Calendar.getInstance();
				cal.setTime(dateNew);
				Date date = (Date) dateNew.clone();
				date = FDCDateHelper.getFirstDayOfMonth(date);
				startTime = new Timestamp(date.getTime());
				date = (Date) dateNew.clone();
				date = FDCDateHelper.getLastDayOfMonth(date);
				endTime = new Timestamp(date.getTime());

				FDCSQLBuilder build = new FDCSQLBuilder();
				build
						.appendSql("select FPayAmount from T_FNC_ContractPayPlan where fcontractId=");
				build.appendParam(contractId);
				build.appendSql(" and fpaydate>=");

				build.appendParam(startTime);
				build.appendSql(" and fpaydate<=");

				// date = (Date) date.clone();
				// date.setDate(cal.getMaximum(Calendar.DAY_OF_MONTH));

				build.appendParam(endTime);
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

		/*****/
		/*
		 * EntityViewInfo view = new EntityViewInfo(); FilterInfo filter = new
		 * FilterInfo(); view.setFilter(filter);
		 * filter.appendFilterItem("contractId", editData.getContractId()); //
		 * 用时间过滤出所有之前的单 filter.getFilterItems().add( new
		 * FilterItemInfo("createTime", editData.getCreateTime(),
		 * CompareType.LESS)); view.getSelector().add("amount");
		 * view.getSelector().add("entrys.paymentBill.billStatus");
		 * view.getSelector().add("entrys.paymentBill.amount");
		 * //本期欠付款应该用付款单的本币计算，而不是原币
		 * view.getSelector().add("entrys.paymentBill.localAmt");
		 * PayRequestBillCollection c =
		 * PayRequestBillFactory.getRemoteInstance()
		 * .getPayRequestBillCollection(view); BigDecimal totalpayAmount =
		 * FDCHelper.ZERO;// 累计实付款+未付的申请单 for (int i = 0; i < c.size(); i++) {
		 * final PayRequestBillInfo info = c.get(i); if
		 * (info.getId().equals(editData.getId())) { // 排除本单据 continue; }
		 * BigDecimal temp = FDCHelper.ZERO; if (info.getEntrys().size() > 0) {
		 * for (int j = 0; j < info.getEntrys().size(); j++) { PaymentBillInfo
		 * payment = info.getEntrys().get(0) .getPaymentBill(); if (payment !=
		 * null && payment.getBillStatus() == BillStatusEnum.PAYED) { temp =
		 * temp.add(FDCHelper.toBigDecimal(payment .getLocalAmt())); } } if
		 * (temp.compareTo(FDCHelper.ZERO) == 0) { temp =
		 * FDCHelper.toBigDecimal(info.getAmount()); }
		 * 
		 * } else { temp = FDCHelper.toBigDecimal(info.getAmount()); }
		 * totalpayAmount = totalpayAmount.add(temp); }
		 */
		/*****/
		Date dateNew;
		dateNew = (Date) objNew;
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateNew);
		Date date = (Date) dateNew.clone();
		date = FDCDateHelper.getFirstDayOfMonth(date);
		startTime = new Timestamp(date.getTime());
		date = (Date) dateNew.clone();
		date = FDCDateHelper.getLastDayOfMonth(date);
		endTime = new Timestamp(date.getTime());
		BigDecimal totalpayAmount = FDCHelper.ZERO;
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
				.appendSql("select sum(pr.famount) as amount  from T_CON_PayRequestBill pr where pr.fstate='4AUDITTED' "
						+ "	and fcontractid=");
		builder.appendParam(contractId);
		if (editData.getId() != null) {
			thisBillid = editData.getId().toString();
			builder.appendSql("	and pr.fid !=");
			builder.appendParam(thisBillid);
		}

		builder.appendSql(" and fpaydate>=");
		builder.appendParam(startTime);
		builder.appendSql(" and fpaydate<=");
		builder.appendParam(endTime);
		IRowSet rowSet = builder.executeQuery();
		while (rowSet.next()) {
			totalpayAmount = rowSet.getBigDecimal("amount");
			if (totalpayAmount == null) {
				totalpayAmount = FDCHelper.ZERO;
			}
		}
		totalpayAmount = totalpayAmount.add(FDCHelper.toBigDecimal(txtBcAmount
				.getBigDecimalValue()));
		BigDecimal mustPayAmt = planAmt.subtract(totalpayAmount);
		tableHelper.setCellValue(PayRequestBillContants.CURBACKPAY, mustPayAmt);
	}

	/**
	 * description 结算类型变化事件
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * 
	 * @version EAS 7.0
	 * @see com.kingdee.eas.fdc.contract.client.AbstractPayRequestBillEditUI#prmtsettlementType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent)
	 */
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

	/**
	 * description 检查拆分状态
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
	private void checkContractSplitState() {
		if (!checkAllSplit) {// 付款单提交时，是否检查合同未完全拆分
			return;
		}
		String contractBillId = (String) getUIContext().get("contractBillId");
		if (contractBillId != null) {
			if (!ContractClientUtils.getContractSplitState(contractBillId)) {
				// 对应的合同还未进行拆分，不能进行此操作！
				FDCMsgBox.showWarning(this, FDCSplitClientHelper
						.getRes("conNotSplited"));
				SysUtil.abort();
			}
		}
	}

	// 保存时，控制预算余额
	private void checkMbgCtrlBalance() throws Exception {
		try {
			if (!isMbgCtrl) {
				return;
			}

			StringBuffer buffer = new StringBuffer("");
			IBgControlFacade iCtrl = BgControlFacadeFactory.getRemoteInstance();
			BgCtrlResultCollection bgCtrlResultCollection = iCtrl.getBudget(
					FDCConstants.PayRequestBill, null, editData);

			if (bgCtrlResultCollection != null) {
				for (int j = 0, count = bgCtrlResultCollection.size(); j < count; j++) {
					BgCtrlResultInfo bgCtrlResultInfo = bgCtrlResultCollection
							.get(j);

					BigDecimal balance = bgCtrlResultInfo.getBalance();
					if (balance != null
							&& balance.compareTo(bgCtrlResultInfo
									.getReqAmount()) < 0) {
						buffer.append(
								bgCtrlResultInfo.getItemName() + "("
										+ bgCtrlResultInfo.getOrgUnitName()
										+ ")").append(
								EASResource.getString(
										FDCConstants.VoucherResource,
										"BalanceNotEnagh")
										+ "\r\n");
					}
				}
			}

			if (buffer.length() > 0) {
				// if(fdcBudgetParam.isStrictCtrl()){
				// FDCMsgBox.showWarning(this, buffer.toString() + "\r\n" +
				// EASResource.getString(FDCConstants.VoucherResource,
				// "BalanceNotEnagh"));
				// this.abort();
				// }
				int result = FDCMsgBox.showConfirm2(this, buffer.toString()
						+ "\r\n"
						+ EASResource.getString(FDCConstants.VoucherResource,
								"isGoOn"));
				if (result == FDCMsgBox.CANCEL) {
					SysUtil.abort();
				}
			}
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
	}

	/**
	 * 提交的时候进行预算检查
	 * 
	 * @throws Exception
	 */
	private void checkFdcBudget() throws Exception {
		try {
			if (getOprtState().equals(OprtState.ADDNEW)
					&& isFromProjectFillBill && editData.getId() != null) {
				editData.put("isFill", Boolean.TRUE);
			}
			Map retMap = FDCBudgetAcctFacadeFactory.getRemoteInstance()
					.invokeBudgetCtrl(this.editData,
							FDCBudgetConstants.STATE_SUBMIT);
			// 防止某些用户在未提交时修改参数，再取
			fetchInitParam();
			PayReqUtils.handleBudgetCtrl(this, retMap, fdcBudgetParam);
		} catch (EASBizException e) {
			handUIExceptionAndAbort(e);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
	}

	private FDCBudgetPeriodInfo getFDCBudgetPeriod() {
		FDCBudgetPeriodInfo period = null;
		if (fdcBudgetParam.isAcctCtrl()) {
			// 从数据库取
			period = (FDCBudgetPeriodInfo) this.editData.get("fdcPeriod");
			if (period == null && this.editData.getId() != null) {
				FDCSQLBuilder builder = new FDCSQLBuilder();
				builder
						.appendSql("select top 1 period.fid id ,period.fyear Pyear,period.fmonth Pmonth from T_FNC_PayRequestAcctPay acctPay ");
				builder
						.appendSql("inner join T_FNC_FDCBudgetPeriod period on period.fid=acctPay.fperiodid ");
				builder.appendSql("where FPayRequestBillId=?");
				builder.addParam(this.editData.getId().toString());
				try {
					IRowSet rowSet = builder.executeQuery();
					if (rowSet.next()) {
						int year = rowSet.getInt("Pyear");
						int month = rowSet.getInt("Pmonth");
						String id = rowSet.getString("id");
						period = FDCBudgetPeriodInfo.getPeriod(year, month,
								false);
						period.setId(BOSUuid.read(id));
					}
				} catch (Exception e) {
					handUIExceptionAndAbort(e);
				}
			}
		}
		if (period == null) {
			if (editData.getPayDate() == null
					&& this.pkpayDate.getValue() == null) {
				FDCMsgBox.showWarning(this, "付款日期 不能为空!");
				SysUtil.abort();
			} else if (editData.getPayDate() == null
					&& pkpayDate.getValue() != null) {
				editData.setPayDate((Date) pkpayDate.getValue());
			}
			period = FDCBudgetPeriodInfo.getPeriod(this.editData.getPayDate(),
					false);
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

	/**
	 * description 本次请款原币发生变化后，更新本币金额
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * @param originalAmount
	 *            本次申请原币
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
	public void setLocalAmountOfThisTime(BigDecimal originalAmount) {
		if (originalAmount == null) {
			getDetailTable().getCell(rowIndex, columnIndex + 1).setValue(null);
		} else {
			BigDecimal amount = FDCHelper.multiply(originalAmount,
					txtexchangeRate.getBigDecimalValue());
			getDetailTable().getCell(rowIndex, columnIndex + 1).setValue(
					FDCHelper.toBigDecimal(amount, 2)); // 设置本次申请本币
		}
	}

	/**
	 * description 设置预付款本币
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-7
	 *             <p>
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
	public void setAdvanceChange(BigDecimal originalAmount) {
		if (originalAmount == null) {
			getDetailTable().getCell(rowIndex + 1, columnIndex + 1).setValue(
					null);
		} else {
			BigDecimal amount = FDCHelper.multiply(originalAmount,
					txtexchangeRate.getBigDecimalValue());
			getDetailTable().getCell(rowIndex + 1, columnIndex + 1).setValue(
					FDCHelper.toBigDecimal(amount, 2));
		}

	}

	/**
	 * description 设置已完工工程量及付款比例
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
	public void setCompletePrjAmt() {
		if (isByWorkload) {
			// 此情况工程量从确认单带来，不重新计算。
			return;
		}
		BigDecimal amount = null;
		if (getDetailTable().getCell(rowIndex, columnIndex + 1).getValue() != null) {
			amount = FDCHelper.toBigDecimal(getDetailTable().getCell(rowIndex,
					columnIndex + 1).getValue());
		}
		BigDecimal paymentProp = FDCNumberHelper
				.toBigDecimal(txtpaymentProportion.getBigDecimalValue());
		BigDecimal completePrj = FDCNumberHelper.toBigDecimal(txtcompletePrjAmt
				.getBigDecimalValue());

		if (paymentProp.compareTo(FDCHelper.ZERO) == 0) {
			if (completePrj.compareTo(FDCHelper.ZERO) == 0) {
				return;
			} else {
				txtpaymentProportion.setRequired(!isAutoComplete);
				PaymentTypeInfo type = (PaymentTypeInfo) prmtPayment.getValue();
				if (type != null
						&& type.getPayType().getId().toString().equals(
								PaymentTypeInfo.settlementID)) {
					txtpaymentProportion.setValue(FDCHelper.ZERO);
				} else {
					txtpaymentProportion.setValue(amount.setScale(4,
							BigDecimal.ROUND_HALF_UP).divide(completePrj,
							BigDecimal.ROUND_HALF_UP).multiply(
							FDCHelper.ONE_HUNDRED));
				}
			}
		} else {
			txtcompletePrjAmt.setValue(amount.setScale(4,
					BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED)
					.divide(paymentProp, BigDecimal.ROUND_HALF_UP));
			PaymentTypeInfo type = (PaymentTypeInfo) prmtPayment.getValue();
			if (type != null
					&& type.getPayType().getId().toString().equals(
							PaymentTypeInfo.settlementID)) {
				txtpaymentProportion.setValue(FDCHelper.ZERO);
			}
		}
	}

	/**
	 * description 设置甲供材本币
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-7
	 *             <p>
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
	public void setPmtAmoutChange(BigDecimal originalAmount) {

		BigDecimal amount = FDCHelper.multiply(originalAmount, txtexchangeRate
				.getBigDecimalValue());

		((ICell) bindCellMap.get(PayRequestBillContants.PAYPARTAMATLAMT))
				.setValue(amount);

	}

	/**
	 * description 分录编辑完成事件
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
	protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
		if ((e.getRowIndex() == rowIndex) && (e.getColIndex() == columnIndex)) {
			if (FDCNumberHelper.toBigDecimal(e.getValue()).compareTo(
					FDCNumberHelper.toBigDecimal(e.getOldValue())) == 0)
				return;// 值未改变，直接返回
			BigDecimal originalAmount = FDCHelper.toBigDecimal(e.getValue());
			if (FDCNumberHelper.isZero(originalAmount)) {
				txtcompletePrjAmt.setValue(FDCNumberHelper.ZERO, false);

				/* modified by zhaoqin for R140421-0018 on 2014/04/21 start */
				if (!isAutoComplete) {
					txtpaymentProportion.setValue(FDCNumberHelper.ZERO, false);
				}
				/* modified by zhaoqin for R140421-0018 on 2014/04/21 end */

				getDetailTable().getCell(rowIndex, columnIndex + 1).setValue(
						FDCNumberHelper.ZERO);
				return; // 原币金额为0，已经计算完毕
			} else {
				// setLocalAmountOfThisTime(originalAmount); 注释By Owen_wen
				// 2012-12-10 互算的问题有必要搞得这么复杂么，用以下代码替换
				BigDecimal localAmount = FDCHelper.multiply(originalAmount,
						txtexchangeRate.getBigDecimalValue());
				getDetailTable().getCell(rowIndex, columnIndex + 1).setValue(
						FDCHelper.toBigDecimal(localAmount, 2));

				// caculatePaymentProp(); 注释By Owen_wen 2012-12-10
				// 互算的问题有必要搞得这么复杂么，先注释看效果，用以下代码替换
				// 只有进度款时才进行互算
				if (prmtPayment.getValue() != null
						&& ((PaymentTypeInfo) prmtPayment.getValue())
								.getPayType().getId().toString().equals(
										PaymentTypeInfo.progressID)
						&& !((PaymentTypeInfo) prmtPayment.getValue())
								.getName().toString().equals(YFK)) {

					if (!isFromProjectFillBill && !isAutoComplete) {
						BigDecimal completePrjAmt = FDCNumberHelper
								.toBigDecimal(txtcompletePrjAmt
										.getBigDecimalValue());
						BigDecimal paymentProp = FDCNumberHelper
								.toBigDecimal(txtpaymentProportion
										.getBigDecimalValue());
						if (FDCNumberHelper.isZero(completePrjAmt)) {
							if (!FDCNumberHelper.isZero(paymentProp)) {
								txtcompletePrjAmt.setValue(FDCNumberHelper
										.divide(FDCNumberHelper.multiply(
												originalAmount,
												FDCHelper.ONE_HUNDRED),
												paymentProp), false);
							}
						} else {
							txtpaymentProportion.setValue(FDCNumberHelper
									.divide(
											FDCNumberHelper.multiply(
													localAmount,
													FDCHelper.ONE_HUNDRED),
											completePrjAmt), false);
						}
					} else if (!isFromProjectFillBill && isAutoComplete) {// modify
																			// by
																			// lihaiou
																			// ,
																			// 2014
																			// -
																			// 08
																			// -
																			// 07
																			// ,
																			// 计算互算
																			// ，
																			// 如果锁定付款比例
						txtcompletePrjAmt.setValue(originalAmount, false);
					}
				}
			}
		} else if ((e.getRowIndex() == ((ICell) bindCellMap
				.get(PayRequestBillContants.PAYPARTAMATLAMTORI)).getRowIndex()
				+ this.deductTypeCollection.size() + 1)
				&& (e.getColIndex() == 4)) {
			// 录入甲供材
			BigDecimal originalAmount = FDCHelper.toBigDecimal(e.getValue());
			setPmtAmoutChange(originalAmount);
		} else if ((e.getRowIndex() == rowIndex + 1)
				&& (e.getColIndex() == columnIndex)) {
			BigDecimal originalAmount = FDCHelper.toBigDecimal(e.getValue());
			// 单击cell按delete与双击再delete是两个事件都要重新计算
			if (e.getValue() == null) {
				originalAmount = null;
			}
			setAdvanceChange(originalAmount);
		}

		// calAllCompletePrjAmt(); 注释By Owen_wen 2012-12-10
		// 互算的问题有必要搞得这么复杂么，先注释看效果

		if (isFirstLoad
				|| (!isAutoComplete && !txtpaymentProportion.isRequired())) {
			return;
		}

		/* modified by zhaoqin for R140428-0095 on 2014/05/05 */
		// if (!FDCNumberHelper.isZero(e.getValue())) {
		// setCompletePrjAmt();
		// }
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
						.getValue());
		BigDecimal totalAmt = FDCHelper.toBigDecimal(((ICell) bindCellMap
				.get(PayRequestBillContants.CURPAIDLOCAL)).getValue());
		BigDecimal completeAmt = FDCHelper.toBigDecimal(txtcompletePrjAmt
				.getBigDecimalValue());

		if (contractBill.isHasSettled()) {
			if (isSimpleFinancial) {
				editData.setCompletePrjAmt(completeAmt);
				txtcompletePrjAmt.setValue(completeAmt);
				editData.setPaymentProportion(txtpaymentProportion
						.getBigDecimalValue());
			} else {
				if (isAutoComplete) {
					editData.setPaymentProportion(FDCHelper.ONE_HUNDRED);
					editData.setCompletePrjAmt(contractBill.getSettleAmt());
					txtpaymentProportion.setValue(FDCHelper.ONE_HUNDRED);
					txtcompletePrjAmt.setValue(contractBill.getSettleAmt());
				}
			}
		} else {
			editData.setCompletePrjAmt(completeAmt);
			if (isAutoComplete) {
				PaymentTypeInfo typeInfo = (PaymentTypeInfo) prmtPayment
						.getValue();
				if (typeInfo == null) {
					return;
				}
				if (typeInfo.getPayType().getId().toString().equals(
						PaymentTypeInfo.progressID)
						&& typeInfo.getName().equals(YFK)) {

					txtcompletePrjAmt.setValue(amount);
					editData.setCompletePrjAmt(amount);
				}
			}
		}
		// }
	}

	// 改为成本中心参数，fetchInitParam中
	public int getAdvancePaymentNumber() {
		/*
		 * int advancePaymentNumber = 1;
		 * if(SysContext.getSysContext().getCurrentOrgUnit() != null){ Map
		 * paramMap = null; try { paramMap = FDCUtils.getDefaultFDCParam(null,
		 * null); } catch (EASBizException e) { // catch block
		 * e.printStackTrace(); } catch (BOSException e) { Auto-generated catch
		 * block e.printStackTrace(); }
		 * if(paramMap.get(FDCConstants.FDC_PARAM_ADVANCEPAYMENTNUMBER)!=null){
		 * advancePaymentNumber =
		 * Integer.valueOf(paramMap.get(FDCConstants.FDC_PARAM_ADVANCEPAYMENTNUMBER
		 * ).toString()).intValue(); } }
		 */
		return advancePaymentNumber;
	}

	/**
	 * description 付款类型变化事件
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
	private void prmtPayment_dataChanged_Old(DataChangeEvent eventObj) {

		Object obj = eventObj.getNewValue();
		Object oldObj = eventObj.getOldValue();
		if (obj != null && obj instanceof PaymentTypeInfo) {
			PaymentTypeInfo _type = (PaymentTypeInfo) obj;
			if (!_type.getPayType().getId().toString().equals(
					PaymentTypeInfo.tempID)) {
				if (this.kdtEntrys.getCell(4, 4) != null) {
					// Add by zhiyuan_tang 2010/07/27
					// R100709-147 如果关联材料确认单时，金额不允许修改，必须通过关联材料确认单进行修改
					if (!isPartACon) {
						this.kdtEntrys.getCell(4, 4).getStyleAttributes()
								.setLocked(false);
					}
					// 如果是甲供材合同的付款申请单，未关联到材料合同，允许修改金额。Added By Owen_wen
					// 2011-04-27
				}
			}
		}

		if (prmtPayment.getUserObject() != null
				&& prmtPayment.getUserObject().equals("noExec")) {
			return;
		}
		if (obj instanceof PaymentTypeInfo) {
			if (isRealizedZeroCtrl) {
				PaymentTypeInfo type = (PaymentTypeInfo) obj;
				if (FDCHelper.isNullZero(txtTotalSettlePrice
						.getBigDecimalValue())
						&& type.getName() != null
						&& !type.getName().equals(YFK)) {
					EventListener[] listeners = prmtPayment
							.getListeners(DataChangeListener.class);
					for (int i = 0; i < listeners.length; i++) {
						prmtPayment
								.removeDataChangeListener((DataChangeListener) listeners[i]);
					}
					prmtPayment.setData(eventObj.getOldValue());
					FDCMsgBox.showError(prmtPayment, "已实现产值为0只允许选择\"预付款\"！");
					for (int i = 0; i < listeners.length; i++) {
						prmtPayment
								.addDataChangeListener((DataChangeListener) listeners[i]);
					}
					setCellEnabled(oldObj);
					SysUtil.abort();
				}
			}
		}

		if (obj instanceof PaymentTypeInfo) {
			try {
				String settlementID = PaymentTypeInfo.settlementID;// "Ga7RLQETEADgAAC/wKgOlOwp3Sw="
				// ;//
				// 结算款
				String progressID = PaymentTypeInfo.progressID;// "Ga7RLQETEADgAAC6wKgOlOwp3Sw="
				// ;//
				// 进度款
				String keepID = PaymentTypeInfo.keepID;// "Ga7RLQETEADgAADDwKgOlOwp3Sw="
				// ;//
				// 保修款

				String tempID = PaymentTypeInfo.tempID;// "Bd2bh+CHRDenvdQS3D72ouwp3Sw="
				// 暂估款

				PaymentTypeInfo type = (PaymentTypeInfo) obj;
				// 付款单为暂估款类型时
				if (type.getPayType().getId().toString().equals(tempID)) {
					if (!isSimpleInvoice) {
						prmtPayment.setValue(null);
						prmtPayment.setText(null);
						FDCMsgBox
								.showWarning("启用“财务帐务以发票金额为准计开发成本”参数后才能选择本类别的付款类型，请先启用对应参数！");
						SysUtil.abort();
					} else {// 其他可录入金额字段仅发票金额可录入(对应的合同内工程款等其他款项不可录入)
						this.kdtEntrys.getStyleAttributes().setLocked(true);
						if (this.kdtEntrys.getCell(4, 4) != null) {
							this.kdtEntrys.getCell(4, 4).setValue(null);
							this.kdtEntrys.getCell(4, 5).setValue(null);
							this.kdtEntrys.getCell(4, 4).getStyleAttributes()
									.setLocked(true);
						}
					}
				}

				// 保修款修改为只要结算就可以付

				/*
				 * filter.appendFilterItem("paymentType.payType.id",
				 * settlementID); filter.appendFilterItem("contractId",
				 * editData.getContractId()); if (type.getPayType() == null) {
				 * return; } if
				 * (type.getPayType().getId().toString().equals(keepID)) { if
				 * (!PayRequestBillFactory.getRemoteInstance() .exists(filter))
				 * { EventListener[] listeners =
				 * prmtPayment.getListeners(DataChangeListener.class); for(int
				 * i=0;i<listeners.length;i++){
				 * prmtPayment.removeDataChangeListener
				 * ((DataChangeListener)listeners[i]); }
				 * prmtPayment.setData(eventObj.getOldValue());
				 * FDCMsgBox.showError(prmtPayment,
				 * "付过结算款后才能付保修款,即存在付款类型的类型为“结算款”的付款申请单时才能选择“保修款”类型付款类型");
				 * for(int i=0;i<listeners.length;i++){
				 * prmtPayment.addDataChangeListener
				 * ((DataChangeListener)listeners[i]); } SysUtil.abort(); } }
				 */

				if (type.getName().toString().equals(YFK)) {
					FilterInfo filter = new FilterInfo();
					filter.appendFilterItem("paymentType.name", YFK);
					filter.appendFilterItem("contractId", this.editData
							.getContractId());
					EntityViewInfo evi = new EntityViewInfo();
					evi.setFilter(filter);

					PayRequestBillCollection cols = PayRequestBillFactory
							.getRemoteInstance().getPayRequestBillCollection(
									evi);
					int number = getAdvancePaymentNumber();
					// 只有非编辑状态的情况下才检测预付款次数，如果是编辑状态下，如果本身是预付款，
					// 选了其它付款类型再选预付款就会出现不合理的提示
					if (!OprtState.EDIT.equals(getOprtState())
							&& cols.size() >= number) {
						FDCMsgBox.showWarning("一个合同只允许录" + number + "次预付款.");
						prmtPayment.setData(null);
						setCellEnabled(oldObj);
						abort();
					}
					txtpaymentProportion.setValue(FDCHelper.ZERO, false);
					txtpaymentProportion.setRequired(false);
					txtpaymentProportion.setEnabled(false);
					txtcompletePrjAmt.setValue(FDCHelper.ZERO, false);
					txtcompletePrjAmt.setRequired(false);
					txtcompletePrjAmt.setEnabled(false);

				} else {
					// 如果付款类型不是“预付款”就把值还原
					this.txtpaymentProportion.setValue(
							this.paymentProportionValue, false);
					this.txtcompletePrjAmt.setValue(this.completePrjAmtValue,
							false);

					if (type.getPayType().getId().toString().equals(progressID)) {
						BigDecimal originalAmount = FDCHelper.divide(FDCHelper
								.multiply(completePrjAmtValue,
										paymentProportionValue),
								FDCHelper.ONE_HUNDRED, 2,
								BigDecimal.ROUND_HALF_UP);
						// 如果甲供材款为0
						if (confirmAmts.compareTo(FDCHelper.ZERO) == 0) {
							kdtEntrys.getCell(rowIndex, columnIndex).setValue(
									originalAmount);
							setLocalAmountOfThisTime(originalAmount);
							caculatePaymentProp();
						}
					}
					txtpaymentProportion.setRequired(true);
					txtpaymentProportion.setEnabled(true);
					txtcompletePrjAmt.setRequired(!isSeparate);
					txtcompletePrjAmt.setEnabled(!isSeparate);
				}

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
						FDCMsgBox.showError(prmtPayment, "合同结算后才能付保修款");
						for (int i = 0; i < listeners.length; i++) {
							prmtPayment
									.addDataChangeListener((DataChangeListener) listeners[i]);
						}
						setCellEnabled(oldObj);
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
						FDCMsgBox
								.showError(prmtPayment,
										"付完结算款后不能付进度款,即存在付款类型的类型为“结算款”的付款申请单时不能选择“进度款”类型付款类型");
						for (int i = 0; i < listeners.length; i++) {
							prmtPayment
									.addDataChangeListener((DataChangeListener) listeners[i]);
						}
						setCellEnabled(oldObj);
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
							FDCMsgBox.showError(prmtPayment, "合同结算之后不能付进度款！");
							for (int i = 0; i < listeners.length; i++) {
								prmtPayment
										.addDataChangeListener((DataChangeListener) listeners[i]);
							}
							setCellEnabled(oldObj);
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
						FDCMsgBox.showError(prmtPayment,
								"合同必须结算之后才能做结算款类别的付款申请单");
						for (int i = 0; i < listeners.length; i++) {
							prmtPayment
									.addDataChangeListener((DataChangeListener) listeners[i]);
						}
						setCellEnabled(oldObj);
						SysUtil.abort();
					}

					txtpaymentProportion.setValue(FDCConstants.ZERO);
				}

				if (!type.getName().toString().equals(YFK)) {
					// txtpaymentProportion如果不可见，那么requireed应该为false By Owen_wen
					// 2011-11-04
					txtpaymentProportion.setRequired(txtpaymentProportion
							.isVisible()
							&& !isAutoComplete);
					txtpaymentProportion.setEditable(true && !isAutoComplete);
					// txtcompletePrjAmt.setEditable(true);
				}
				/*
				 * 1.付款保修款时，进度款支付比例、本期完工工程量默认为零、可编辑。
				 * 期望：此场景下，付保修款时，进度款支付比例、本期完工工程量默认为零、不可编辑。
				 */
				setTxtEnable(type);
			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			}
		}

		if (isFromProjectFillBill) {
			txtcompletePrjAmt.setEditable(false);
			if (FDCHelper.toBigDecimal(txtcompletePrjAmt.getBigDecimalValue())
					.compareTo(FDCHelper.ZERO) == 0)
				txtpaymentProportion.setEditable(false);
		}
		// 进度款时,非工程类合同的付款申请单，本期完工工程量和进度款付款比例应该灰显，并且去掉计算逻辑（本次申请金额= 本期完工工程量*
		// 进度付款比例%）；

		if (obj instanceof PaymentTypeInfo) {
			PaymentTypeInfo type = (PaymentTypeInfo) obj;
			if (contractBill != null
					&& contractBill.getContractType() != null
					&& PaymentTypeInfo.progressID.equals(type.getPayType()
							.getId().toString())) {

				ContractTypeInfo contractType = contractBill.getContractType();
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("isWorkLoadConfirm");
				try {
					ContractTypeInfo contractTypeInfo = ContractTypeFactory
							.getRemoteInstance().getContractTypeInfo(
									new ObjectUuidPK(contractType.getId()
											.toString()), sic);
					if (!contractTypeInfo.isIsWorkLoadConfirm()) {
						txtpaymentProportion.setEnabled(false);
						txtcompletePrjAmt.setEnabled(false);
						txtpaymentProportion.setRequired(false);
						txtcompletePrjAmt.setRequired(false);
					} else {
						if (isSeparate) {
							if (FDCHelper.toBigDecimal(
									txtcompletePrjAmt.getBigDecimalValue())
									.compareTo(FDCHelper.ZERO) == 0) {
								if (isAutoComplete) {
									txtpaymentProportion
											.setValue(FDCHelper.ZERO);
								}
								txtpaymentProportion.setEnabled(false);
							}

						}
					}
				} catch (EASBizException e) {
					handUIExceptionAndAbort(e);
				} catch (BOSException e) {
					handUIExceptionAndAbort(e);
				}

			}
		}
	}

	/**
	 * 周鹏需求 描述：简单模式（付款确认成本）、启用参数进度款付款比例自动显示为100% 1.付款结算款时，进度款支付比例、本期完工工程量都可录入。
	 * 期望：此场景下，本期完工工程量 = 结算金额 - 本张付款申请之前的累计完工工程量；进度款支付比例为零，不可编辑。
	 * 另外，再付第二笔结算款时，进度款支付比例、本期完工工程量都显示为零且不可编辑。
	 * 
	 * @throws Exception
	 * @throws BOSException
	 * @throws EASBizException
	 * @Author：keyan_zhao
	 * @CreateTime：2012-9-25
	 */
	private void setTxtcompletePrjAmtValue_Old() throws EASBizException,
			BOSException, Exception {
		if (contractBill == null) {
			return;
		}
		if (!OprtState.ADDNEW.equals(getOprtState())) {
			return;
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("contractId", contractBill.getId()
						.toString()));
		filter.getFilterItems().add(
				new FilterItemInfo("paymentType.payType.id",
						PaymentTypeInfo.settlementID));
		if (editData.getId() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", editData.getId(),
							CompareType.NOTEQUALS));
		}
		if (getBizInterface().exists(filter)) {
			txtcompletePrjAmt.setValue(FDCHelper.ZERO);
		} else {
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder
					.appendSql("select sum(FCompletePrjAmt) as allComAmt from T_CON_PayRequestBill where FContractId ='"
							+ contractBill.getId().toString() + "'");
			IRowSet rowSet = builder.executeQuery();
			BigDecimal allComAmt = FDCHelper.ZERO;
			if (rowSet.next()) {
				allComAmt = rowSet.getBigDecimal("allComAmt");
			}
			txtcompletePrjAmt.setEditable(true);
			BigDecimal bigDecimal = FDCHelper.subtract(contractBill
					.getSettleAmt(), allComAmt);
			removeDataChangeListener(txtcompletePrjAmt);
			txtcompletePrjAmt.setValue(bigDecimal);
			addDataChangeListener(txtcompletePrjAmt);
		}
	}

	/**
	 * description 暂估款 锁定本次请款金额单元格
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
	private void setCellEnabled_Old(Object oldObj) {
		if (oldObj != null && oldObj instanceof PaymentTypeInfo) {
			PaymentTypeInfo _type = (PaymentTypeInfo) oldObj;
			if (_type.getPayType().getId().toString().equals(
					PaymentTypeInfo.tempID)) {
				if (this.kdtEntrys.getCell(4, 4) != null) {
					this.kdtEntrys.getCell(4, 4).getStyleAttributes()
							.setLocked(true);
				}
			}
		}
	}

	protected void initListener() {
		// super.initListener();
	}

	protected boolean isUseMainMenuAsTitle() {
		return false;
	}

	protected void planAcctCtrl() throws Exception {
		boolean hasUsed = FDCUtils.getDefaultFDCParamByKey(null, null,
				FDCConstants.FDC_PARAM_ACCTBUDGET);
		if (hasUsed) {
			Map costAcctPlan = ConPayPlanSplitFactory.getRemoteInstance()
					.getCostAcctPlan(
							editData.getCurProject().getId().toString(),
							editData.getPayDate());
			Map planSplitMap = (Map) costAcctPlan.get("planSplitMap");
			Map reqSplitMap = (Map) costAcctPlan.get("reqSplitMap");
			// Map allPlanSplitMap = (Map) costAcctPlan.get("allPlanSplitMap");
			// Map allReqSplitMap = (Map) costAcctPlan.get("allReqSplitMap");
			if (planSplitMap == null) {
				planSplitMap = new HashMap();
			}
			if (reqSplitMap == null) {
				reqSplitMap = new HashMap();
			}
			// if (allPlanSplitMap == null) {
			// allPlanSplitMap = new HashMap();
			// }
			// if (allReqSplitMap == null) {
			// allReqSplitMap = new HashMap();
			// }

			//
			for (Iterator iter = planSplitMap.keySet().iterator(); iter
					.hasNext();) {
				String key = (String) iter.next();
				BigDecimal planAmt = (BigDecimal) planSplitMap.get(key);
				BigDecimal reqAmt = (BigDecimal) reqSplitMap.get(key);
				if (FDCHelper.toBigDecimal(
						FDCNumberHelper.subtract(planAmt, reqAmt)).signum() < 0) {
					String acctId = key.substring(0, 44);
					CostAccountInfo costAccountInfo = CostAccountFactory
							.getRemoteInstance().getCostAccountInfo(
									new ObjectUuidPK(acctId));
					FDCMsgBox.showWarning(this, "'" + costAccountInfo.getName()
							+ "' 科目上已经超付");
					return;
				}
			}
		}
	}

	/**
	 * 累计已完工工程量=之前所有状态的付款申请单已完工工程量金额+本次录入的已完工工程量金额<br>
	 * 累计付款比例=累计申请金额（付款情况况表中累计合同内工程款截至本期）/累计已完工工程量<br>
	 * 合同最终结算后：合同结算价
	 * <p>
	 * 界面显示“累计付款比例”已经改为“累计应付付款比例”，之前是词不达意，修改后客户易于理解。Modified by Owen_wen
	 * 2010-6-30 提单号：R100621-226
	 */
	private void loadAllCompletePrjAmt() {
		// if (isSimpleFinancial) {
		// return;
		// }
		allCompletePrjAmt = FDCHelper.ZERO;
		if ((isSeparate && contractBill != null && contractBill.isIsCoseSplit())) {
			BigDecimal allCompletePrjAmt = FDCHelper.ZERO;
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("workload");
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems()
					.add(
							new FilterItemInfo("contractBill", editData
									.getContractId()));
			filter.getFilterItems()
					.add(
							new FilterItemInfo("state",
									FDCBillStateEnum.AUDITTED_VALUE));
			if (editData.getCreateTime() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("createTime", editData
								.getCreateTime(), CompareType.LESS));
			}
			view.setFilter(filter);
			try {
				WorkLoadConfirmBillCollection workloadCol = WorkLoadConfirmBillFactory
						.getRemoteInstance().getWorkLoadConfirmBillCollection(
								view);
				if (workloadCol != null && workloadCol.size() > 0) {
					for (Iterator it = workloadCol.iterator(); it.hasNext();) {
						WorkLoadConfirmBillInfo workloadInfo = (WorkLoadConfirmBillInfo) it
								.next();
						if (workloadInfo != null) {
							allCompletePrjAmt = FDCNumberHelper.add(
									allCompletePrjAmt, workloadInfo
											.getWorkLoad());
						}
					}
				}
			} catch (BOSException e) {
				handUIExceptionAndAbort(e);
			}
			txtAllCompletePrjAmt.setValue(allCompletePrjAmt);
			BigDecimal prjAllReqAmt = FDCHelper.toBigDecimal(editData
					.getPrjAllReqAmt(), 4);
			if (OprtState.ADDNEW.equals(getOprtState())
					&& bindCellMap.get(PayRequestBillContants.PRJALLREQAMT) != null) {
				prjAllReqAmt = FDCHelper.toBigDecimal(((ICell) bindCellMap
						.get(PayRequestBillContants.PRJALLREQAMT)).getValue(),
						4);
			}
			if (prjAllReqAmt == null
					|| txtAllCompletePrjAmt.getBigDecimalValue() == null
					|| FDCHelper.ZERO.compareTo(txtAllCompletePrjAmt
							.getBigDecimalValue()) == 0) {
				txtAllPaymentProportion.setValue(null);
			} else if (FDCHelper.ZERO.compareTo(prjAllReqAmt) == 0) {
				txtAllPaymentProportion.setValue(FDCHelper.ZERO);
			} else {
				txtAllPaymentProportion.setValue(FDCHelper.divide(prjAllReqAmt,
						txtAllCompletePrjAmt.getBigDecimalValue(), 4,
						BigDecimal.ROUND_HALF_UP).multiply(
						FDCHelper.ONE_HUNDRED));
			}
			return;
		}
		if (contractBill != null && contractBill.isHasSettled()
				&& !isSimpleFinancial) {
			// 已完工工程量不能为0 eric_wang 2010.06.01
			BigDecimal settleAmt = contractBill.getSettleAmt(); // 结算价
			if (FDCHelper.ZERO.equals(settleAmt)) {
				FDCMsgBox.showError(this, "本币：进度款+结算款不能超过合同结算价-保修金");
				SysUtil.abort();
			} else {
				txtAllCompletePrjAmt.setValue(settleAmt);
				BigDecimal prjAllReqAmt = FDCHelper.toBigDecimal(editData
						.getPrjAllReqAmt(), 4);
				if (OprtState.ADDNEW.equals(getOprtState())
						&& bindCellMap.get(PayRequestBillContants.PRJALLREQAMT) != null) {
					prjAllReqAmt = FDCHelper.toBigDecimal(((ICell) bindCellMap
							.get(PayRequestBillContants.PRJALLREQAMT))
							.getValue(), 4);
				}
				if (prjAllReqAmt == null
						|| txtAllCompletePrjAmt.getBigDecimalValue() == null
						|| FDCHelper.ZERO.compareTo(txtAllCompletePrjAmt
								.getBigDecimalValue()) == 0) {
					txtAllPaymentProportion.setValue(null);
				} else if (FDCHelper.ZERO.compareTo(prjAllReqAmt) == 0) {
					txtAllPaymentProportion.setValue(FDCHelper.ZERO);
				} else {
					txtAllPaymentProportion.setValue(FDCHelper.divide(
							prjAllReqAmt,
							txtAllCompletePrjAmt.getBigDecimalValue(), 4,
							BigDecimal.ROUND_HALF_UP).multiply(
							FDCHelper.ONE_HUNDRED));
				}
			}
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
			if (payReqColl != null && payReqColl.size() > 0) {
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
				txtcompletePrjAmt.getBigDecimalValue())); // 累计完工工程量=本期完工工程量+
															// 以前的所有完工工程量
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
			if (prjAllReqAmt == null
					|| txtAllCompletePrjAmt.getBigDecimalValue() == null
					|| FDCHelper.ZERO.compareTo(txtAllCompletePrjAmt
							.getBigDecimalValue()) == 0) {
				txtAllPaymentProportion.setValue(null);
			} else if (FDCHelper.ZERO.compareTo(prjAllReqAmt) == 0) {
				txtAllPaymentProportion.setValue(FDCHelper.ZERO);
			} else {
				txtAllPaymentProportion.setValue(FDCHelper.divide(prjAllReqAmt,
						txtAllCompletePrjAmt.getBigDecimalValue(), 4,
						BigDecimal.ROUND_HALF_UP).multiply(
						FDCHelper.ONE_HUNDRED));
			}

		}
	}

	/**
	 * description 编辑完成之后 重新写入完工工程量和付款比例
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
	private void calAllCompletePrjAmt() {
		if ((isSeparate && contractBill != null && contractBill.isIsCoseSplit())) {
			return;
		}
		// 合同结算
		if (contractBill != null && contractBill.isHasSettled()
				&& !isSimpleFinancial) {
			txtAllCompletePrjAmt.setValue(contractBill.getSettleAmt());
			BigDecimal prjAllReqAmt = FDCHelper.toBigDecimal(editData
					.getPrjAllReqAmt(), 4);
			if (OprtState.ADDNEW.equals(getOprtState())
					&& bindCellMap.get(PayRequestBillContants.PRJALLREQAMT) != null) { // 合同内工程款
																						// 截至本期累计申请
																						// （
																						// 本币
																						// ）
				prjAllReqAmt = FDCHelper.toBigDecimal(((ICell) bindCellMap
						.get(PayRequestBillContants.PRJALLREQAMT)).getValue(),
						4);
			}
			txtAllPaymentProportion.setValue(FDCHelper.divide(prjAllReqAmt,
					txtAllCompletePrjAmt.getBigDecimalValue(), 4,
					BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
			return;
		}

		// 其它情况，包括合同结算+简单模式
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
			txtAllPaymentProportion.setValue(FDCHelper.divide(prjAllReqAmt,
					txtAllCompletePrjAmt.getBigDecimalValue(), 4,
					BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
		}
	}

	/**
	 * description 累计发票本币金额
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-8-31
	 *             <p>
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
	private void loadInvoiceAmt() {
		allInvoiceAmt = FDCHelper.ZERO;
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("invoiceAmt");
		FilterInfo filter = new FilterInfo();
		if (editData.getCreateTime() == null) {
			editData.setCreateTime(new Timestamp(System.currentTimeMillis()));
		}
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
			// 为空时不能return，后边需要计算值
			if (payReqColl != null && payReqColl.size() > 0) {
				for (int i = 0; i < payReqColl.size(); i++) {
					PayRequestBillInfo info = payReqColl.get(i);
					allInvoiceAmt = allInvoiceAmt.add(FDCHelper
							.toBigDecimal(info.getInvoiceAmt()));
				}
			}
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
		// 原来的发票累计金额控件根据需求隐藏了，但是因为很多地方仍然有取值需要，所以继续保留
		txtAllInvoiceAmt.setNumberValue(allInvoiceAmt.add(FDCHelper
				.toBigDecimal(txtInvoiceAmt.getBigDecimalValue())));
	}

	/**
	 * description 累计发票原币金额
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-8-31
	 *             <p>
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
	private void loadInvoiceOriAmt() {
		allInvoiceOriAmt = FDCHelper.ZERO;
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("invoiceOriAmt");
		FilterInfo filter = new FilterInfo();
		if (editData.getCreateTime() == null) {
			editData.setCreateTime(new Timestamp(System.currentTimeMillis()));
		}
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
			// 为空时不能return，后边需要计算值
			if (payReqColl != null) {
				for (int i = 0; i < payReqColl.size(); i++) {
					PayRequestBillInfo info = payReqColl.get(i);
					allInvoiceOriAmt = allInvoiceOriAmt.add(FDCHelper
							.toBigDecimal(info.getInvoiceOriAmt()));
				}
			}
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
	}

	/**
	 * 
	 * 描述: 控制逻辑更改。针对付款类型类别为进度款的付款申请单，允许付款金额为0或者已实现为0的情况（但是总计的已完工必须大于已付款。
	 * 累计已完工=已审批的付款申请单完工工程量之和
	 * +本期完工工程量，累计已付款=所有的付款申请单合同内工程款之和+本期合同内工程款），不允许同时为0的情况。
	 * 对于不满足条件的付款申请单，在保存，提交时给出提示：累计已完工（已审批的付款申请单完工工程量之和+本期完工工程量）
	 * 小于了累计付款（所有的付款申请单合同内工程款之和+本期合同内工程款），不能执行本操作！
	 * 
	 * @author pengwei_hou Date: 2008-12-04
	 * @throws Exception
	 */
	private void checkCompletePrjAmt() throws Exception {
		if ((isSeparate && contractBill != null && contractBill.isIsCoseSplit())
				|| (!isFromProjectFillBill && !isSeparate)) {
			return;
		}
		String paymentType = editData.getPaymentType().getPayType().getId()
				.toString();
		String progressID = TypeInfo.progressID;
		if (!paymentType.equals(progressID)) {
			return;
		}

		// 预付款类型的申请单提交工程量可以为零
		PaymentTypeInfo paymentTypeInfo = (PaymentTypeInfo) prmtPayment
				.getValue();
		if (paymentTypeInfo != null && YFK.equals(paymentTypeInfo.getName())) { // 预付款不是预设数据
			return;
		}

		// 已完工工程量(已完工)
		BigDecimal completePrjAmt = FDCHelper.toBigDecimal(editData
				.getCompletePrjAmt());
		// 合同内工程款(已付款)
		BigDecimal allProjectPriceInContract = FDCHelper.toBigDecimal(editData
				.getProjectPriceInContractOri());

		if (completePrjAmt.compareTo(allProjectPriceInContract) >= 0) {
			return;
		}

		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("projectPriceInContract");
		view.getSelector().add("completePrjAmt");
		view.getSelector().add("state");
		view.getSelector().add("entrys.projectPriceInContract");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("contractId", editData.getContractId()));
		filter.getFilterItems().add(
				new FilterItemInfo("paymentType.payType.id", progressID));
		filter.getFilterItems().add(
				new FilterItemInfo("createTime", editData.getCreateTime(),
						CompareType.LESS_EQUALS));
		// 不包括本次,本次在上面单独加取当前单据最新数据,以避免新增或修改时数据错误
		if (editData.getId() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", editData.getId().toString(),
							CompareType.NOTEQUALS));
		}
		view.setFilter(filter);
		PayRequestBillCollection payReqColl = PayRequestBillFactory
				.getRemoteInstance().getPayRequestBillCollection(view);

		if (payReqColl != null) {
			for (int i = 0; i < payReqColl.size(); i++) {
				PayRequestBillInfo info = payReqColl.get(i);
				// 关闭状态的取付款单
				if (info.getEntrys() != null && info.getEntrys().size() != 0) {
					for (int j = 0; j < info.getEntrys().size(); j++) {
						PayRequestBillEntryInfo entry = info.getEntrys().get(j);
						allProjectPriceInContract = allProjectPriceInContract
								.add(FDCHelper.toBigDecimal(entry
										.getOriginalAmount()));
					}
				} else {
					allProjectPriceInContract = allProjectPriceInContract
							.add(FDCHelper.toBigDecimal(info
									.getProjectPriceInContractOri()));
				}
				if (info.getState() == FDCBillStateEnum.AUDITTED) {
					completePrjAmt = completePrjAmt.add(FDCHelper
							.toBigDecimal(info.getCompletePrjAmt()));
				}
			}
		}
		completePrjAmt = FDCHelper.toBigDecimal(completePrjAmt, 2);
		allProjectPriceInContract = FDCHelper.toBigDecimal(
				allProjectPriceInContract, 2);

		verifyCompletePrjAmt(completePrjAmt, allProjectPriceInContract);
	}

	/**
	 * description
	 * 累计已完工（已审批的付款申请单完工工程量之和+本期完工工程量）小于累计付款（所有的付款申请单合同内工程款之和+本期合同内工程款），不能执行本操作！
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
	private void verifyCompletePrjAmt(BigDecimal completePrjAmt,
			BigDecimal payAmt) {
		if (contractBill != null && contractBill.isIsCoseSplit()
				&& completePrjAmt.compareTo(payAmt) < 0) {
			FDCMsgBox
					.showWarning(this,
							"累计已完工（已审批的付款申请单完工工程量之和+本期完工工程量）小于累计付款（所有的付款申请单合同内工程款之和+本期合同内工程款），不能执行本操作！");
			SysUtil.abort();
		}
	}

	protected void prmtPayment_willCommit(CommitEvent e) throws Exception {
		/***
		 * 42. 提单：R090609-207 刘业平 简单模式也可以选择结算款,保修款 by 周勇
		 */
		// if(isSimpleFinancial){
		// prmtPayment.getQueryAgent().resetRuntimeEntityView();
		// if(prmtPayment.getEntityViewInfo()!=null){
		// EntityViewInfo view = prmtPayment.getEntityViewInfo();
		// view.getFilter().getFilterItems().add(new
		// FilterItemInfo("payType.id",PaymentTypeInfo.progressID));
		// prmtPayment.setEntityViewInfo(view);
		// }else{
		// EntityViewInfo view = new EntityViewInfo();
		// view.setFilter(new FilterInfo());
		// view.getFilter().getFilterItems().add(new
		// FilterItemInfo("payType.id",PaymentTypeInfo.progressID));
		// prmtPayment.setEntityViewInfo(view);
		// }
		// }
	}

	protected void prmtPayment_willShow(SelectorEvent e) throws Exception {
		// if(isSimpleFinancial){
		// prmtPayment.getQueryAgent().resetRuntimeEntityView();
		// if(prmtPayment.getEntityViewInfo()!=null){
		// EntityViewInfo view = prmtPayment.getEntityViewInfo();
		// view.getFilter().getFilterItems().add(new
		// FilterItemInfo("payType.id",PaymentTypeInfo.progressID));
		// prmtPayment.setEntityViewInfo(view);
		// }else{
		// EntityViewInfo view = new EntityViewInfo();
		// FilterInfo filter = new FilterInfo();
		// filter.getFilterItems().add(new
		// FilterItemInfo("payType.id",PaymentTypeInfo.progressID));
		// view.setFilter(filter);
		// prmtPayment.setEntityViewInfo(view);
		// }
		// }
		// FilterInfo filter = new FilterInfo();
		// filter.appendFilterItem("paymentType.name", YFK);
		// filter.appendFilterItem("contractId", this.editData.getContractId());
		// EntityViewInfo evi = new EntityViewInfo();
		// evi.setFilter(filter);
		//		
		// PayRequestBillCollection cols =
		// PayRequestBillFactory.getRemoteInstance
		// ().getPayRequestBillCollection(evi);
		// int number = getAdvancePaymentNumber();
		// if(cols.size() >= number){
		// prmtPayment.getQueryAgent().resetRuntimeEntityView();
		// EntityViewInfo viewinfo = null;
		// if(prmtPayment.getQueryAgent().getEntityViewInfo() != null){
		// viewinfo = prmtPayment.getQueryAgent().getEntityViewInfo();
		// FilterItemCollection collection =
		// viewinfo.getFilter().getFilterItems();
		// if(collection != null && collection.size() > 0 ){
		// for(int i=0;i<collection.size();i++){
		// if("paymentType.name".equalsIgnoreCase(collection.get(i).
		// getPropertyName())){
		// collection.remove(collection.get(i));
		// }
		// }
		//						
		// }
		// FilterItemInfo itemInfo = new FilterItemInfo("paymentType.name",
		// YFK,CompareType.NOTEQUALS);
		// viewinfo.getFilter().getFilterItems().add(itemInfo);
		// prmtPayment.getQueryAgent().setEntityViewInfo(viewinfo);
		// }else{
		// viewinfo = new EntityViewInfo();
		// FilterInfo fi = new FilterInfo();
		// FilterItemInfo itemInfo = new FilterItemInfo("paymentType.name",
		// YFK,CompareType.NOTEQUALS);
		// fi.getFilterItems().add(itemInfo);
		// viewinfo.setFilter(fi);
		// prmtPayment.getQueryAgent().setEntityViewInfo(viewinfo);
		// }
		// }

	}

	/**
	 * 本付款单的合同ID
	 * 
	 * @return contractId
	 */
	public void actionAssociateAcctPay_actionPerformed(ActionEvent e)
			throws Exception {
		if (OprtState.ADDNEW.equals(getOprtState()) || this.editData == null
				|| this.editData.getId() == null) {
			// 须保存警告
			FDCMsgBox.showWarning(getRes("saveBillFirst"));
			SysUtil.abort();
		}
		super.actionAssociateAcctPay_actionPerformed(e);
		FDCBudgetPeriodInfo period = getFDCBudgetPeriod();
		// this.editData.setAmount(txtAmount.getBigDecimalValue());
		this.editData.setAmount(FDCHelper.toBigDecimal(txtBcAmount
				.getBigDecimalValue()));
		PayReqAcctPayUI.showPayReqAcctPayUI(this, this.editData, period,
				getOprtState());
	}

	/**
	 * description 付款申请单合同ID
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
	public String getPayReqContractId() {
		return payReqContractId;
	}

	/**
	 * 月度请款：将关联成本科目付款计划与关联待签订合同合并到一起
	 */
	public void actionMonthReq_actionPerformed(ActionEvent e) throws Exception {
		if (OprtState.ADDNEW.equals(getOprtState()) || this.editData == null
				|| this.editData.getId() == null) {
			// 须保存警告
			FDCMsgBox.showWarning(getRes("saveBillFirst"));
			SysUtil.abort();
		}
		super.actionMonthReq_actionPerformed(e);
		this.editData.setAmount(FDCHelper.toBigDecimal(txtBcAmount
				.getBigDecimalValue()));
		FDCBudgetPeriodInfo period = getFDCBudgetPeriod();
		if (period == null) {
			period = FDCBudgetPeriodInfo.getCurrentPeriod(false);
		}
		FDCMonthReqMoneyUI.showFDCMonthReqMoneyUI(this, editData, period,
				getOprtState());
	}

	public void actionAssociateUnSettled_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAssociateUnSettled_actionPerformed(e);
		String prjId = editData.getCurProject().getId().toString();
		String contractId = editData.getContractId();
		// if exist costaccount month plan it's not a unSettled contract
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("contractBill.id", contractId);
		filter.appendFilterItem("itemType",
				FDCBudgetAcctItemTypeEnum.CONTRACT_VALUE);
		filter.appendFilterItem("isAdd", Boolean.FALSE);
		filter
				.appendFilterItem("parent.state",
						FDCBillStateEnum.AUDITTED_VALUE);

		boolean isExistContractPlan = FDCMonthBudgetAcctEntryFactory
				.getRemoteInstance().exists(filter);
		if (isExistContractPlan) {
			FDCMsgBox.showWarning(this, "当前合同存在项目月度科目付款计划,不属于待签订合同,不需要做关联!");
			SysUtil.abort();
		}
		FDCBudgetPeriodInfo period = getFDCBudgetPeriod();
		if (period == null) {
			period = FDCBudgetPeriodInfo.getCurrentPeriod(false);
		}
		ContractAssociateAcctPlanUI.showContractAssociateAcctPlanUI(this,
				prjId, contractId, period, getOprtState());
	}

	public void actionViewPayDetail_actionPerformed(ActionEvent e)
			throws Exception {
		if (editData == null || editData.getContractId() == null) {
			return;
		}
		viewPayDetail();
	}

	/**
	 * description 查看付款详情
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
	private void viewPayDetail() throws UIException {
		String editUIName = null;

		editUIName = PayRequestViewPayDetailUI.class.getName();

		if (editUIName == null)
			return;
		UIContext uiContext = new UIContext(this);
		if (editData.getContractNo() == null) {
			return;
		}
		uiContext.put("contractId", editData.getContractId());
		uiContext.put("createTime", editData.getCreateTime());
		// 创建UI对象并显示
		IUIWindow windows = this.getUIWindow();
		String type = UIFactoryName.NEWWIN;
		if (windows instanceof UIModelDialog || windows == null) {
			type = UIFactoryName.MODEL;
		}
		IUIWindow contractUiWindow = UIFactory.createUIFactory(type).create(
				editUIName, uiContext, null, "FINDVIEW", WinStyle.SHOW_RESIZE);
		if (contractUiWindow != null) {
			contractUiWindow.show();
		}
	}

	/**
	 * description 汇率变化事件
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * 
	 * @version EAS 7.0
	 * @see com.kingdee.eas.fdc.contract.client.AbstractPayRequestBillEditUI#txtexchangeRate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent)
	 */
	public void txtexchangeRate_dataChanged(
			com.kingdee.bos.ctrl.swing.event.DataChangeEvent e)
			throws Exception {
		if (isFromProjectFillBill) {
			if (kdtEntrys.getCell(rowIndex, columnIndex) != null) {

				kdtEntrys.getCell(rowIndex, columnIndex).setValue(
						FDCHelper.divide(kdtEntrys.getCell(rowIndex,
								columnIndex + 1).getValue(), txtexchangeRate
								.getBigDecimalValue()));
			}
			return;
		}
		if (getDetailTable().getCell(rowIndex, columnIndex) != null) {
			BigDecimal oriAmount = FDCHelper.toBigDecimal(getDetailTable()
					.getCell(rowIndex, columnIndex).getValue());
			setLocalAmountOfThisTime(oriAmount);
		}

		if (((ICell) bindCellMap.get(PayRequestBillContants.PAYPARTAMATLAMT)) != null) {
			BigDecimal oriPMTAmount = FDCHelper
					.toBigDecimal(((ICell) bindCellMap
							.get(PayRequestBillContants.PAYPARTAMATLAMT))
							.getValue());
			setPmtAmoutChange(oriPMTAmount);
			caculatePaymentProp();
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
	protected void PrepareHandlerParam(RequestContext request) {
		super.PrepareHandlerParam(request);

	}

	protected void prepareInitDataHandlerParam(RequestContext request) {
		// 合同Id
		String contractBillId = (String) getUIContext().get("contractBillId");
		if (contractBillId == null) {
			request.put("FDCBillEditUIHandler.ID", getUIContext().get("ID"));
		} else {
			request.put("FDCBillEditUIHandler.contractBillId", contractBillId);
		}
		// 如果在集团处理工作流单据，为空导致后续参数不对
		// 工程项目Id
		BOSUuid projectId = ((BOSUuid) getUIContext().get("projectId"));
		request.put("FDCBillEditUIHandler.projectId", projectId);

		// 合同类型ID
		BOSUuid typeId = (BOSUuid) getUIContext().get("contractTypeId");
		request.put("FDCBillEditUIHandler.typeId", typeId);
	}

	public void actionContractAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionContractAttachment_actionPerformed(e);

		AttachmentClientManager acm = AttachmentManagerFactory
				.getClientManager();
		if (editData.getContractId() != null) {
			acm.showAttachmentListUIByBoID(editData.getContractId(), this,
					false);
		} else {
			return;
		}
	}

	/**
	 * 预付款：工程量启用+合同 当工程量确认流程与付款流程分离参数为‘是’并且是合同 返回true
	 * 
	 * @return
	 */
	private boolean isAdvance() {
		try {
			// 简单发票不显示
			return !isSimpleInvoice && isSeparate
					&& FDCUtils.isContractBill(null, editData.getContractId());
		} catch (EASBizException e) {
			handUIExceptionAndAbort(e);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
		return false;
	}

	/**
	 * description 查看材料确认单
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * 
	 * @version EAS 7.0
	 * @see com.kingdee.eas.fdc.contract.client.AbstractPayRequestBillEditUI#actionViewMaterialConfirm_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionViewMaterialConfirm_actionPerformed(ActionEvent e)
			throws Exception {

		// if (OprtState.ADDNEW.equals(getOprtState())) {
		// // 须保存警告
		// FDCMsgBox.showWarning("请先保存单据，再关联材料确认单");
		// SysUtil.abort();
		// }
		UIContext uiContext = new UIContext(this);
		uiContext.put("PayRequestBillInfo", editData);

		//不能使用this.contractBill.getId().toString()，因为无文本生成的付款申请单contractBill为null
		// ；
		// 因为this.getUIContext().get("contractBillId")本来就有值，再放到uiContext中，
		// 如果可以共享UIContext可以替换此语，Owen_wen 2010-11-15
		uiContext.put("contractBillId", this.getUIContext().get(
				"contractBillId"));

		// 创建UI对象并显示
		String type = UIFactoryName.MODEL;
		IUIWindow contractUiWindow = UIFactory.createUIFactory(type).create(
				MaterialConfirmBillSimpleListUI.class.getName(), uiContext,
				null, this.getOprtState());
		if (contractUiWindow != null) {
			contractUiWindow.show();
		}
	}

	public void setConfirmBillEntryAndPrjAmt() {
		((ICell) bindCellMap
				.get(PayRequestBillContants.PROJECTPRICEINCONTRACTORI))
				.setValue(confirmAmts);
		if (this.isAutoComplete == true && this.isSeparate == false) {
			this.txtpaymentProportion.setValue(FDCHelper.ONE_HUNDRED);
		}
		setLocalAmountOfThisTime(confirmAmts);
		calAllCompletePrjAmt();
		if (FDCHelper.ZERO.compareTo(confirmAmts) != 0) {
			// 设置已完工工程量及付款比例
			setCompletePrjAmt();
			// 本次申请（原币）合同内工程款
			((ICell) bindCellMap
					.get(PayRequestBillContants.PROJECTPRICEINCONTRACTORI))
					.getStyleAttributes().setLocked(true);
		} else {
			// 设置已完工工程量及付款比例
			setCompletePrjAmt();
			// 本次申请（原币）合同内工程款
			((ICell) bindCellMap
					.get(PayRequestBillContants.PROJECTPRICEINCONTRACTORI))
					.getStyleAttributes().setLocked(false);
		}
	}

	public BigDecimal getConfirmAmts() {
		return confirmAmts;
	}

	public void setConfirmAmts(BigDecimal confirmAmts) {
		this.confirmAmts = confirmAmts;
	}

	/**
	 * 实现 IWorkflowUIEnhancement 接口必须实现的方法getWorkflowUIEnhancement by
	 * Cassiel_peng 2009-10-2
	 */
	public IWorkflowUIEnhancement getWorkflowUIEnhancement() {
		return new DefaultWorkflowUIEnhancement() {
			public List getApporveToolButtons(CoreUIObject uiObject) {

				List toolButtionsList = new ArrayList();
				btnContractExecInfo.setVisible(true);
				toolButtionsList.add(btnContractExecInfo);
				btnViewPayDetail.setVisible(true);
				toolButtionsList.add(btnViewPayDetail);
				btnViewContract.setVisible(true);
				toolButtionsList.add(btnViewContract);
				return toolButtionsList;
			}
		};
	}

	/**
	 * description 填充附件列表
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 * @throws Exception
	 * @throws BOSException
	 * @throws EASBizException
	 * @see
	 */

	public void fillAttachmnetList() throws EASBizException, BOSException,
			Exception {
		this.cmbAttachment.removeAllItems();
		String boId = null;
		if (this.editData.getId() == null) {
			return;
		} else {
			boId = this.editData.getId().toString();
		}
		try {
			this.cmbAttachment.addItems(AttachmentUtils
					.getAttachmentListByBillID(boId).toArray());
			this.isHasAttchment = this.cmbAttachment.getItemCount() > 0;
			this.btnViewAttachment.setEnabled(this.isHasAttchment);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}

		if (isHasAttchment) {
			int sum = this.cmbAttachment.getItemCount();
			editData.setAttachment(sum);
			SelectorItemCollection sel = new SelectorItemCollection();
			sel.add("attachment");
			((IPayRequestBill) this.getBizInterface()).updatePartial(editData,
					sel);
			this.txtattachment.setValue(new Integer(sum));
		}

	}

	/**
	 * description 查看附件
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * 
	 * @version EAS 7.0
	 * @see com.kingdee.eas.fdc.contract.client.AbstractPayRequestBillEditUI#actionViewAttachment_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionViewAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionViewAttachment_actionPerformed(e);
		String attchId = null;
		if (this.cmbAttachment.getSelectedItem() != null
				&& this.cmbAttachment.getSelectedItem() instanceof AttachmentInfo) {
			attchId = ((AttachmentInfo) this.cmbAttachment.getSelectedItem())
					.getId().toString();
			AttachmentClientManager acm = AttachmentManagerFactory
					.getClientManager();
			acm.viewAttachment(attchId);

		}
	}

	/**
	 * description 锁定容器
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * 
	 * @version EAS 7.0
	 * @see com.kingdee.eas.framework.client.EditUI#lockContainer(java.awt.Container)
	 */
	protected void lockContainer(java.awt.Container container) {
		if (lblAttachmentContainer.getName().equals(container.getName())) {
			return;
		} else {
			super.lockContainer(container);
		}
	}

	/**
	 * description
	 * 对应付款单为暂估款类型时，提交时对发票金额进行判断，若小于0，则提示：暂估款类型的付款申请单的发票金额累计不能小于0，请修正后提交！
	 * 审批时进行同样的校验及提示。
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
	private void checkTempSmallerThanZero() throws BOSException,
			EASBizException {
		if (contractBill == null) {
			return;
		}
		Object o = this.prmtPayment.getValue();
		if (o == null
				|| !(o instanceof PaymentTypeInfo)
				|| !(PaymentTypeInfo.tempID.equals(((PaymentTypeInfo) o)
						.getPayType().getId().toString()))) {
			return;
		}
		BigDecimal totalInvoiceAmt = FDCHelper.ZERO;
		String contractId = contractBill.getId().toString();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("invoiceAmt");
		filter.getFilterItems().add(
				new FilterItemInfo("contractId", contractId));
		filter.getFilterItems().add(
				new FilterItemInfo("paymentType.payType.id",
						PaymentTypeInfo.tempID));
		view.setFilter(filter);
		view.setSelector(selector);
		PayRequestBillCollection payReqColl = PayRequestBillFactory
				.getRemoteInstance().getPayRequestBillCollection(view);
		for (Iterator iter = payReqColl.iterator(); iter.hasNext();) {
			PayRequestBillInfo payRequest = (PayRequestBillInfo) iter.next();
			totalInvoiceAmt = FDCHelper.add(totalInvoiceAmt, payRequest
					.getInvoiceAmt());
		}
		// 如果ID为空说明尚未保存;如果不为空有可能是保存之后直接提交也有可能是保存或者是提交了之后重修又修改发票金额并重新来提交，所以必须进行校验
		if (this.editData.getId() != null
				&& ("保存".equals(this.editData.getState().toString()) || "已提交"
						.equals(this.editData.getState().toString()))) {
			PayRequestBillInfo billInfo = PayRequestBillFactory
					.getRemoteInstance().getPayRequestBillInfo(
							(new ObjectUuidPK(this.editData.getId())));
			totalInvoiceAmt = FDCHelper.subtract(totalInvoiceAmt, billInfo
					.getInvoiceAmt());
			totalInvoiceAmt = FDCHelper.add(totalInvoiceAmt, this.txtInvoiceAmt
					.getBigDecimalValue());
		}
		if (FDCHelper.ZERO.compareTo(totalInvoiceAmt) == 1) {
			FDCMsgBox.showWarning("暂估款类型的付款申请单的累计发票金额不能小于0，请修正后提交！");
			SysUtil.abort();
		}
	}

	/**
	 * 在申请单提交、审批时，检查合同实付款是否大于已实现产值， 若大于，则提示“未结算合同的实付款不能大于已实现产值【合同实付款=已关闭的
	 * 付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申 请单合同内工程款
	 * 合计】”，若启用了本参数，则不能提交或审批通过，若未启用,则在提示之后允许提交、审批。 by jian_wen 2009.12.16
	 * 
	 * @throws BOSException
	 * @throws EASBizException
	 * */
	private void check() throws EASBizException, BOSException {
		// 如果对应合同已结算 直接返回
		if (contractBill.isHasSettled()) {
			return;
		}
		// 预付款类型的申请单提交时不按此参数控制
		PaymentTypeInfo paymentTypeInfo = (PaymentTypeInfo) prmtPayment
				.getValue();
		if (paymentTypeInfo != null && YFK.equals(paymentTypeInfo.getName())) { // 预付款不是预设数据
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("paymentType.name", "%预付款%",
							CompareType.NOTLIKE));
			filter.getFilterItems().add(
					new FilterItemInfo("contractId", editData.getContractId()));
			if (editData.getId() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("id", editData.getId().toString(),
								CompareType.NOTEQUALS));
			}
			// 存在进度款时受控制
			if (!PayRequestBillFactory.getRemoteInstance().exists(filter)) {
				return;
			}
		}

		BigDecimal payAmt = ContractClientUtils.getPayAmt(contractBill.getId()
				.toString());
		if (payAmt == null) {
			payAmt = FDCHelper.ZERO;
		}
		// 取本次录入的实付款
		BigDecimal amt = FDCHelper.ZERO;
		if (getDetailTable().getCell(rowIndex, columnIndex + 1).getValue() != null) {
			amt = new BigDecimal(getDetailTable().getCell(rowIndex,
					columnIndex + 1).getValue().toString());
		}
		payAmt = FDCHelper.add(payAmt, amt);

		if (payAmt != null
				&& payAmt.compareTo(FDCHelper.toBigDecimal(txtTotalSettlePrice
						.getBigDecimalValue())) == 1) {

			if (isControlPay) {// 启用了参数 提示错误信息并中断
				FDCMsgBox.showError(cantMoreThanTotalSettlePrice);
				abort();
			} else {
				if (isMoreSettlement) {// 只启用了合同是否可进行多次结算 没启用 参数 就只 提示 但可以继续操作
					/* modified by zhaoqin for R140421-0018 on 2014/04/14 */
					// FDCMsgBox.showWarning(cantMoreThanTotalSettlePrice);
				}
			}
		}
	}

	/**
	 * 反关闭付款申请单时，如果合同实付款(不含本次)加本次付款申请单合同内工程款本期发生大于已实现产值，
	 * 若合同还未最终结算且启用了参数“未结算合同的实付款大于已实现产值时是否严格控制”且合同实付款大于累计
	 * 结算金额，则提示“未结算合同的实付款不能大于累计结算金额【合同实付款 =已关闭的付款申请单对应的付款 单合同内工程款合计 +
	 * 未关闭的付款申请单合同内工程款合计】”，则不能提交或审批通过，若未启用,则在提 示之后允许提交、审批。 by jian_wen
	 * 2009.12.25
	 * 
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void checkIsUnClose() throws EASBizException, BOSException {
		boolean b = isControlPay;
		BigDecimal completePrjAmt = PayReqUtils
				.getConCompletePrjAmt(contractBill.getId().toString());
		if (editData.getState().equals(FDCBillStateEnum.SAVED)
				|| editData.getState().equals(FDCBillStateEnum.SUBMITTED)) {

		} else {
			completePrjAmt = FDCHelper.subtract(completePrjAmt, editData
					.getCompletePrjAmt());
		}

		// 取合同实付款(所有保存在数据库的值)
		BigDecimal payAmt = ContractClientUtils.getPayAmt(contractBill.getId()
				.toString());
		if (payAmt == null) {
			payAmt = FDCHelper.ZERO;
		}
		// 加上本次反关闭时 申请单上的 合同内工程款本位币
		// BigDecimal amt = FDCHelper.ZERO;
		// if (getDetailTable().getCell(rowIndex, columnIndex + 1).getValue() !=
		// null) {
		// amt = new BigDecimal(getDetailTable().getCell(rowIndex, columnIndex +
		// 1).getValue().toString());
		// }
		// 本次的
		// payAmt = FDCHelper.add(payAmt, amt);//重复
		// 还要减去 付款单关闭时 付款申请单对应的所有付款单内的合同内工程款本位币
		if (this.editData.getId() != null) {
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("projectPriceInContract");
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("fdcPayReqID", editData.getId()
							.toString()));
			view.setFilter(filter);
			PaymentBillCollection coll = PaymentBillFactory.getRemoteInstance()
					.getPaymentBillCollection(view);
			if (coll != null && coll.size() > 0) {
				for (Iterator it = coll.iterator(); it.hasNext();) {
					PaymentBillInfo info = (PaymentBillInfo) it.next();
					if (editData.getState().equals(FDCBillStateEnum.SAVED)
							|| editData.getState().equals(
									FDCBillStateEnum.SUBMITTED)) {
						// 不做任何处理
					} else {
						payAmt = FDCHelper.subtract(payAmt, info
								.getProjectPriceInContract());
					}

				}
			}
		}
		verifyCompletePrjAmt(completePrjAmt, payAmt);
		if (isMoreSettlement) {
			if (payAmt != null
					&& payAmt.compareTo(FDCHelper
							.toBigDecimal(txtTotalSettlePrice
									.getBigDecimalValue())) == 1) {

				if (b) {// 启用了参数 提示错误信息并中断
					FDCMsgBox.showError(cantMoreThanTotalSettlePrice);
					abort();
				} else {
					if (isMoreSettlement) {// 只启用了合同是否可进行多次结算 没启用 参数 就只 提示
											// 但可以继续操作
						/* modified by zhaoqin for R140421-0018 on 2014/04/14 */
						// FDCMsgBox.showWarning(cantMoreThanTotalSettlePrice);
					}
				}
			}
		}
	}

	public boolean destroyWindow() {
		if (getOprtState().equals(OprtState.ADDNEW) && isFromProjectFillBill
				&& editData.getId() != null) {
			try {
				FDCSCHUtils.updateTaskRef(null, isFromProjectFillBill,
						isFillBillControlStrict, true,
						editData.getContractId(),
						new IObjectPK[] { new ObjectUuidPK(editData.getId()) },
						null);
			} catch (EASBizException e) {
				handUIExceptionAndAbort(e);
			} catch (BOSException e) {
				handUIExceptionAndAbort(e);
			}
		}
		return super.destroyWindow();
	}

	/**
	 * 描述：付款申请单审批过程中可以修改发票金额、发票号、本期完工工程量、本次申请原币。
	 * <p>
	 * 
	 * @author liang_ren969 @date:2010-5-21
	 *         <p>
	 *         <br>
	 *         举例：
	 * 
	 *         <pre>
	 * 	  SwingUtilities.invokeLater(new Runnable(){&lt;p&gt;
	 * 		public void run() {&lt;p&gt;
	 * 			editPayAuditColumn();&lt;p&gt;
	 * 		}
	 * <p>
	 * 	  });
	 */
	private void editAuditPayColumn() {
		Object isFromWorkflow = getUIContext().get("isFromWorkflow");
		if (isFromWorkflow != null && isFromWorkflow.toString().equals("true")
				&& getOprtState().equals(OprtState.EDIT)
				&& actionSave.isVisible()
				&& (editData.getState() == FDCBillStateEnum.AUDITTING)) {// modified
																			// by
																			// ken_liu
																			// .
																			// .
																			// 已提交状态不须锁控件
																			// 。
																			// for
																			// R120719
																			// -
																			// 0117

			// 首先锁定界面上所有的控件
			this.lockUIForViewStatus();

			// boolean s = isControlPay;

			/**
			 * 启用了参数--未结算合同的实付款大于已实现产值时是否严格控制
			 */
			// if(!s){
			// 首先给控件个访问的权限，然后设置是否可编辑就可以了！不能使用unloack方法，这样会使所用的控件处于edit的状态
			this.txtInvoiceNumber
					.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.txtInvoiceNumber.setEditable(true);

			this.txtInvoiceAmt
					.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.txtInvoiceAmt.setEditable(true);

			/**
			 * 系统参数设置为真的时候，本期完工工程量金额处于不可编辑的状态
			 */
			if (!isAutoComplete) {
				this.txtcompletePrjAmt
						.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
				this.txtcompletePrjAmt.setEditable(true);
			}

			// R110513-0372：流程中打回后， 可以修改备注，用途和提交付款、实际收款单位
			this.txtUsage
					.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.txtUsage.setEditable(true);

			this.txtMoneyDesc
					.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.txtMoneyDesc.setEditable(true);

			if (!PayReqUtils.isContractBill(editData.getContractId())
					|| !isNotEnterCAS) {
				this.chkIsPay
						.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
				this.chkIsPay.setEditable(true);
			}

			this.prmtrealSupplier
					.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.prmtrealSupplier.setEditable(true);

			setEditState();

			this.kdtEntrys.setEnabled(true);
			if (this.kdtEntrys.getCell(4, 4) != null) {
				// Add by zhiyuan_tang 2010/07/27
				// R100709-147 关联材料确认单时，金额不允许修改，必须通过关联材料确认单进行修改.
				if (!isPartACon) {
					this.kdtEntrys.getCell(4, 4).getStyleAttributes()
							.setLocked(false);
				} else {
					this.kdtEntrys.getCell(4, 4).getStyleAttributes()
							.setLocked(true);
				}
				// this.kdtEntrys.getCell(4,
				// 4).getStyleAttributes().setLocked(false);
			}

			this.actionSave.setEnabled(true);
		}

		if (isFromWorkflow != null && isFromWorkflow.toString().equals("true")
				&& getOprtState().equals(STATUS_EDIT)) {
			actionRemove.setEnabled(false);
		}
	}

	/**
	 * description 获取本付款单应收（付）金额
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
	private Map getTotalPayAmtByThisReq(String reqPayId) {
		/* modified by zhaoqin for R140227-0281 on 2014/03/21 start */
		BigDecimal totalPayedOriAmt = FDCHelper.ZERO; // 本申请单已付原币
		BigDecimal totalPayedAmt = FDCHelper.ZERO; // 本申请单已付本币
		BigDecimal lstPayedOriAmt = FDCHelper.ZERO; // 截至本次累计实付原币
		BigDecimal lstPayedAmt = FDCHelper.ZERO; // 截至本次累计实付本币
		Map totalPayMap = new HashMap();
		if (reqPayId != null) {
			FDCSQLBuilder builder = new FDCSQLBuilder();
			// builder.appendSql(
			// "select sum(famount) as famount from T_CAS_PaymentBill where ffdcPayReqID = ?  and fbillstatus =15 "
			// );
			builder
					.appendSql("select sum(famount) as famount, sum(flocalAmount) as flocalAmount, ");
			builder
					.appendSql("sum(faddProjectAmt) as flstPayedOriAmt, sum(fprojectPriceInContract) as flstPayedAmt ");
			builder
					.appendSql(" from T_CAS_PaymentBill where ffdcPayReqID = ?  and fbillstatus =15 ");
			// builder.appendSql("union ");
			// builder.appendSql(
			// "select sum(db.famount) as famount from T_CON_DeductOfPayReqBill db "
			// );
			// builder.appendSql(
			// "select sum(db.FOriginalAmount) as famount, sum(db.famount) as flocalAmount from T_CON_DeductOfPayReqBill db "
			// );
			// builder.appendSql(
			// "inner join T_CAS_PaymentBill pay on pay.fid=db.fpaymentbillid  "
			// );
			// builder.appendSql(
			// "where  db.FPayRequestBillId= ? and pay.fbillstatus = 15 ");
			builder.addParam(reqPayId);
			// builder.addParam(reqPayId);
			RowSet rs;
			try {
				rs = builder.executeQuery();
				while (rs.next()) {
					totalPayedOriAmt = FDCHelper.add(totalPayedOriAmt, rs
							.getBigDecimal("famount"));
					// totalPayedAmt = FDCHelper.add(totalPayedAmt,
					// rs.getBigDecimal(1));
					totalPayedAmt = FDCHelper.add(totalPayedAmt, rs
							.getBigDecimal("flocalAmount"));
					lstPayedOriAmt = FDCHelper.add(lstPayedOriAmt, rs
							.getBigDecimal("flstPayedOriAmt"));
					lstPayedAmt = FDCHelper.add(lstPayedAmt, rs
							.getBigDecimal("flstPayedAmt"));
				}
			} catch (BOSException e1) {
				handUIExceptionAndAbort(e1);
			} catch (SQLException e) {
				handUIExceptionAndAbort(e);
			}

			logger
					.info("======================================================");
			logger
					.info("PayRequestBillEditUI.getTotalPayAmtByThisReq，设置付款申请单截至本次计实付SQL");
			logger.info("sql：" + builder.getSql());
			logger.info("paramater：" + builder.getParamaters());
			logger.info("本申请单已付原币，totalPayedOriAmt：" + totalPayedOriAmt);
			logger.info("本申请单已付本币，totalPayedAmt：" + totalPayedAmt);
			logger.info("截至本次累计实付原币，lstPayedOriAmt：" + lstPayedOriAmt);
			logger.info("截至本次累计实付本币，lstPayedAmt：" + lstPayedAmt);
			logger
					.info("======================================================");
		}
		totalPayMap.put("totalPayedOriAmt", totalPayedOriAmt); // 本申请单已付原币
		totalPayMap.put("totalPayedAmt", totalPayedAmt); // 本申请单已付本币
		totalPayMap.put("lstPayedOriAmt", lstPayedOriAmt);
		totalPayMap.put("lstPayedAmt", lstPayedAmt);
		// return totalPayedAmt;
		return totalPayMap;
		/* modified by zhaoqin for R140227-0281 on 2014/03/21 end */
	}

	/**
	 * 新增付款申请单前的参数检查
	 * <p>
	 * 合同是否上传正文控制付款申请单新增。参数值：不提示，提示控制，严格控制。
	 * 默认为不提示。参数值为不提示时，付款申请单新增不受合同是否上传正文的控制；参数值为提示控制时，
	 * 没有上传正文的合同新增付款申请单时，给出提示信息后，仍可以继续操作；参数值为严格控制时， 没有上传正文的合同新增付款申请单时，严格控制不能新增。
	 * 
	 * @author owen_wen 2010-12-07
	 * @throws BOSException
	 * @throws EASBizException
	 * @see PayRequestBillListUI.checkParamForAddNew()
	 */
	private void checkParamForAddNew() {
		// 已上传正文，新增付款申请单不需要检查
		if (ContractClientUtils.isUploadFile4Contract(getUIContext().get(
				"contractBillId").toString()))
			return;

		String returnValue = "";
		BOSUuid companyId = SysContext.getSysContext().getCurrentFIUnit()
				.getId();
		IObjectPK comPK = new ObjectUuidPK(companyId);
		try {
			returnValue = ParamControlFactory
					.getRemoteInstance()
					.getParamValue(
							comPK,
							FDCConstants.FDC_PARAM_FDC324_NEWPAYREQWITHCONTRACTATT);
		} catch (EASBizException e) {
			logger.info("获取参数出现异常：" + e.getMessage());
			handUIExceptionAndAbort(e);
		} catch (BOSException e) {
			logger.info("获取参数出现异常：" + e.getMessage());
			handUIExceptionAndAbort(e);
		}

		if ("0".equals(returnValue)) { // 严格控制
			FDCMsgBox.showInfo("该合同没有上传正文，不能新增付款申请单！");
			SysUtil.abort();
		} else if ("1".equals(returnValue)) { // 提示控制
			int confirmResult = FDCMsgBox.showConfirm2New(this,
					"该合同没有上传正文，是否继续新增付款申请单？");
			if (confirmResult == FDCMsgBox.NO
					|| confirmResult == FDCMsgBox.CANCEL) // 因为用户可以按ESC关闭窗口，
															// 此时confirmResult为FDCMsgBox
															// .CANCEL
				SysUtil.abort();
		}
	}

	/**
	 * 查看预算
	 * 
	 * @author
	 * @date 2011-3-1
	 */
	public void actionViewBudget_actionPerformed(ActionEvent e)
			throws Exception {
		if (!this.ctrl) {
			actionViewMbgBalance_actionPerformed(e);
			return;
		}

		Map uiContext = getUIContext();
		Date bookDate = (Date) pkbookedDate.getValue();
		Date firstDay = BudgetViewUtils.getFirstDay(bookDate);
		Date lastDay = BudgetViewUtils.getLastDay(bookDate);
		String projectId = editData.getCurProject().getId().toString();

		String curProject = editData.getCurProject().getDisplayName();
		uiContext.put("curProject", curProject);
		Object obj = prmtPlanHasCon.getValue();
		if (obj == null) {
			obj = prmtPlanUnCon.getValue();
		}
		String curDept = "";
		if (obj == null) {
			if (this.ctrl)
				FDCMsgBox.showWarning(this, "计划，暂无法查看！");
			else {
				FDCMsgBox.showWarning(this, "无预算，暂无法查看！");
			}
			abort();

			// FDCMsgBox.showWarning(this, "无预算，暂无法查看！");
			// abort();
		} else if (obj instanceof FDCDepConPayPlanContractInfo) {
			curDept = ((FDCDepConPayPlanContractInfo) obj).getHead()
					.getDeptment().getName();
		} else if (obj instanceof FDCDepConPayPlanUnsettledConInfo) {
			curDept = ((FDCDepConPayPlanUnsettledConInfo) obj).getParent()
					.getDeptment().getName();
		}
		uiContext.put("curDept", curDept);
		// 要取界面上的业务日期，否则会出现修改业务日期后未保存，查看预算时月份显示不正确 Added by Owen_wen 2011-05-23
		String planMonth = DateTimeUtils.format(bookDate, "yyyy年MM月");
		uiContext.put("planMonth", planMonth);

		BigDecimal localBudget = getLocalBudget(firstDay, lastDay, projectId);
		uiContext.put("localBudget", localBudget);
		BigDecimal actPaied = FDCBudgetUtil.getActPaied(firstDay, lastDay,
				editData.getContractId());
		uiContext.put("actPaied", actPaied);

		/* modified by zhaoqin for R131218-0367 on 2013/12/25 start */
		// 本期已付工程款
		BigDecimal paid = FDCBudgetUtil.getPaid(firstDay, lastDay, editData
				.getContractId());
		uiContext.put("paid", paid);
		BigDecimal floatFund = FDCBudgetUtil.getPayRequestBillFloatFund(
				firstDay, lastDay, editData.getContractId(), editData.getId());
		uiContext.put("floatFund", floatFund);
		// BigDecimal bgBalance =
		// localBudget.subtract(floatFund).subtract(actPaied);
		BigDecimal bgBalance = localBudget.subtract(floatFund).subtract(paid);
		uiContext.put("bgBalance", bgBalance);
		/* modified by zhaoqin for R131218-0367 on 2013/12/25 end */
		uiContext.put("ctrl", Boolean.valueOf(this.ctrl));

		BudgetViewUtils.showModelUI(uiContext);
	}

	/**
	 * 本期可用预算
	 */
	protected BigDecimal getBgBalance() throws Exception {
		Date bookDate = (Date) pkbookedDate.getValue();
		Date firstDay = BudgetViewUtils.getFirstDay(bookDate);
		Date lastDay = BudgetViewUtils.getLastDay(bookDate);
		String projectId = editData.getCurProject().getId().toString();

		/* modified by zhaoqin for R131218-0367 on 2013/12/25 start */
		// BigDecimal actPaied = FDCBudgetUtil.getActPaied(firstDay, lastDay,
		// editData.getContractId());
		BigDecimal floatFund = FDCBudgetUtil.getPayRequestBillFloatFund(
				firstDay, lastDay, editData.getContractId(), editData.getId());
		BigDecimal localBudget = getLocalBudget(firstDay, lastDay, projectId);
		// 本期已付工程款
		BigDecimal paid = FDCBudgetUtil.getPaid(firstDay, lastDay, editData
				.getContractId());
		// return localBudget.subtract(actPaied).subtract(floatFund);
		return localBudget.subtract(paid).subtract(floatFund);
		/* modified by zhaoqin for R131218-0367 on 2013/12/25 end */
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
	protected BigDecimal getLocalBudget(Date firstDay, Date lastDay,
			String projectId) throws BOSException, SQLException {
		BigDecimal localBudget = FDCHelper.ZERO;
		FDCDepConPayPlanContractInfo hPlan = (FDCDepConPayPlanContractInfo) prmtPlanHasCon
				.getValue();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		if (hPlan != null) {
			builder
					.appendSql("select admin.FName_l2 as curDept,entry.FMonth as planMonth,entry.FOfficialPay as localBudget ");
			builder.appendSql("from T_FNC_FDCDepConPayPlanContract as con ");
			builder
					.appendSql("left join T_FNC_FDCDepConPayPlanBill as head on head.FID = con.FHeadID ");
			builder
					.appendSql("left join T_FNC_FDCDepConPayPlanCE as entry on entry.FParentC = con.FID ");
			builder
					.appendSql("left join T_ORG_Admin as admin on admin.FID = head.FDeptmentID ");
			// builder
			// .appendSql(
			// "where (head.FState = '4AUDITTED' or head.FState = '10PUBLISH') "
			// );
			builder.appendSql("where 1=1 ");
			builder.appendSql(" and entry.FMonth >= ");
			builder.appendParam(firstDay);
			builder.appendSql(" and entry.FMonth <= ");
			builder.appendParam(lastDay);
			builder.appendSql(" and con.FID = '");
			builder.appendSql(hPlan.getId().toString()).appendSql("' ");
			builder.appendSql(" and con.FProjectID = ");
			builder.appendParam(projectId);
			builder.appendSql("order by head.FYear desc,head.FMonth DESC");
			IRowSet rowSet = builder.executeQuery();
			while (rowSet.next()) {
				if (rowSet.getString("localBudget") != null) {
					localBudget = rowSet.getBigDecimal("localBudget");
				}
			}
		} else {
			FDCDepConPayPlanUnsettledConInfo uPlan = (FDCDepConPayPlanUnsettledConInfo) prmtPlanUnCon
					.getValue();
			if (uPlan != null) {
				builder
						.appendSql(" select admin.FName_l2 as curDept,entry.FMonth as planMonth,entry.FOfficialPay as localBudget ");
				builder.appendSql(" from T_FNC_FDCDepConPayPlanUC as con ");
				builder
						.appendSql(" left join T_FNC_FDCDepConPayPlanBill as head on head.FID = con.FParentID ");
				builder
						.appendSql(" left join T_FNC_FDCDepConPayPlanUE as entry on entry.FParentID = con.FID ");
				builder
						.appendSql(" left join T_ORG_Admin as admin on admin.FID = head.FDeptmentID ");
				builder
						.appendSql(" where (head.FState = '4AUDITTED' or head.FState = '10PUBLISH') ");
				builder.appendSql(" and entry.FMonth >= ");
				builder.appendParam(firstDay);
				builder.appendSql(" and entry.FMonth <= ");
				builder.appendParam(lastDay);
				builder.appendSql(" and con.FID = '");
				builder.appendSql(uPlan.getId().toString()).appendSql("' ");
				builder.appendSql(" and con.FProjectID = ");
				builder.appendParam(projectId);
				builder.appendSql("order by head.FYear desc,head.FMonth DESC");
				IRowSet rowSet = builder.executeQuery();
				while (rowSet.next()) {
					if (rowSet.getString("localBudget") != null) {
						localBudget = rowSet.getBigDecimal("localBudget");
					}
				}
			}
		}
		return localBudget;
	}

	/**
	 * 累计申请金额超过合同最新造价给出提示信息
	 * 
	 * @author owen_wen 2011-07-13
	 * @param isControlCost
	 *            启用参数 FDC071_OUTPAYAMOUNT “累计请款超过合同最新造价严格控制”
	 */
	private void showMsg4TotalPayReqAmtMoreThanConPrice(boolean isControlCost) {
		if (isControlCost) { // 严格控制
			FDCMsgBox.showWarning(this, EASResource.getString(fncResPath,
					"totalPayReqAmt>LastPriceCantSubmit"));
			SysUtil.abort();
		} else {
			if (FDCMsgBox.showConfirm2(this, EASResource.getString(fncResPath,
					"totalPayReqAmt>LastPriceCanSubmit")) != FDCMsgBox.OK) {
				SysUtil.abort();
			}
		}
	}

	/**
	 * description 根据"工程量确认流程与付款流程是否分离"参数，来控制“本期完工工程量”“进度款付款比例”列是否隐藏
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-8-15
	 *             <p>
	 * @version EAS7.0
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void setEditableAndRequiredBySeparateParam()
			throws EASBizException, BOSException {
		/**
		 * 该方法已经不适合目前的系统了，此校验逻辑太过于简单了
		 */
		//		
		// if (isSeparate) {
		// this.txtpaymentProportion.setRequired(false);
		// this.contcompletePrjAmt.setEnabled(false);
		// this.txtcompletePrjAmt.setRequired(false);
		// }else {
		// this.txtpaymentProportion.setRequired(true);
		// this.contcompletePrjAmt.setEnabled(true);
		// this.txtcompletePrjAmt.setRequired(true);
		// }
		/*
		 * 标准产品修正付款申请单据内容如下： 1、增加控制：参数FDC317在存在付款申请单或工程量确认单是不允许修改
		 * 2、明确字段表意：参数FDC317为是，即分离时，付款申请单上字段“本期完工工程量”
		 * 更改成“未付已完工工程量”，参数FDC317为否，即不分离时，付款申请单上字段“本期完工工程量”显示不变化
		 * 3、“进度款付款比例”名称不产生明显歧义，暂不作调整，“本次申请”金额=
		 * “进度款付款比例”“未付已完工工程量”计算逻辑不变，其中“未付已完工工程量”取值的数据不允修改且不变化，
		 * “本次申请金额”与“进度款付款比例”值变化后互算，可清0但不支持申请负数金额。
		 */
		/* modified by zhaoqin for R140421-0018 on 2014/04/14 start */
		if (isSeparate) {
			this.contcompletePrjAmt.setBoundLabelText("完工未付款");
		} else {
			this.contcompletePrjAmt.setBoundLabelText("本期完工工程量");
		}
		/* modified by zhaoqin for R140421-0018 on 2014/04/14 start */
	}

	/**
	 * description 初始化累计发票本币和原币 设置数据变化监听
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-9-7
	 *             <p>
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
	private void initInvoice() {
		// 发票金额原币
		txtInvoiceOriAmt.setPrecision(2);
		txtInvoiceOriAmt.setSupportedEmpty(true);
		txtInvoiceOriAmt.setMinimumValue(FDCHelper.MIN_VALUE);
		txtInvoiceOriAmt.setMaximumValue(FDCHelper.MAX_VALUE
				.multiply(FDCHelper.TEN));
		txtInvoiceOriAmt.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent eventObj) {
				BigDecimal invoiceOriAmt = FDCHelper.ZERO;
				if (null != txtInvoiceOriAmt.getBigDecimalValue()) {
					invoiceOriAmt = txtInvoiceOriAmt.getBigDecimalValue();
				}

				DecimalFormat dFormat = new DecimalFormat("0.00");
				// 累计法票金额原币
				BigDecimal invoiceOriAmtSum = allInvoiceOriAmt.add(FDCHelper
						.toBigDecimal(invoiceOriAmt));

				// 累计法票金额本币设值 by tim_gao 取掉 为0 判断,因为第一次填的时候就是0
				if (!FDCHelper.isEmpty(invoiceOriAmtSum)
						&& !FDCHelper.isEmpty(invoiceOriAmt)) {
					BigDecimal rate = FDCHelper.toBigDecimal(txtexchangeRate
							.getBigDecimalValue());
					txtInvoiceAmt.setNumberValue(invoiceOriAmt.multiply(rate));
				}
				// 累讲法票金额原币/本币
				invoiceOriAndAmtStr = "";
				BigDecimal alinvoiceAmt = txtInvoiceAmt.getBigDecimalValue();
				if (!FDCHelper.isEmpty(alinvoiceAmt)) {
					BigDecimal lastAllinvo = allInvoiceAmt.add(alinvoiceAmt);
					invoiceOriAndAmtStr = dFormat.format(invoiceOriAmtSum)
							+ "/" + dFormat.format(lastAllinvo);
				} else {
					invoiceOriAndAmtStr = dFormat.format(invoiceOriAmtSum)
							+ "/" + dFormat.format(allInvoiceAmt);
				}
				txtAllInvAndOriAmt.setText(invoiceOriAndAmtStr);
			}
		});
		// 累计发票金额
		txtInvoiceAmt.setPrecision(2);
		txtInvoiceAmt.setSupportedEmpty(true);
		txtAllInvoiceAmt.setSupportedEmpty(true);
		txtInvoiceAmt.setMinimumValue(FDCHelper.MIN_VALUE);
		txtInvoiceAmt.setMaximumValue(FDCHelper.MAX_VALUE
				.multiply(FDCHelper.TEN));
		txtInvoiceAmt.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent eventObj) {
				DecimalFormat dFormat = new DecimalFormat("0.00");
				BigDecimal invoiceAmt = txtInvoiceAmt.getBigDecimalValue();
				if (!FDCHelper.isEmpty(invoiceAmt)
						&& invoiceAmt.compareTo(FDCHelper.ZERO) == 0) {
					// 为零时非必录
					txtInvoiceNumber.setRequired(false);
				} else if (isInvoiceRequired) {
					txtInvoiceNumber.setRequired(true);
				}
				txtAllInvoiceAmt.setNumberValue(allInvoiceAmt.add(FDCHelper
						.toBigDecimal(invoiceAmt)));
				invoiceOriAndAmtStr = "";
				if (!FDCHelper.isEmpty(invoiceAmt)) {
					BigDecimal lastAllinvo = allInvoiceAmt.add(invoiceAmt);
					invoiceOriAndAmtStr = dFormat.format(allInvoiceOriAmt)
							+ "/" + dFormat.format(lastAllinvo);
				} else {
					invoiceOriAndAmtStr = dFormat.format(allInvoiceOriAmt)
							+ "/" + dFormat.format(allInvoiceAmt);
				}
				txtAllInvAndOriAmt.setText(invoiceOriAndAmtStr);
			}
		});
	}

	/**
	 * description 显示累计发票本币和原币金额
	 * 
	 * @author 蒲磊
	 *         <p>
	 * @createDate 2011-8-31
	 *             <p>
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
	private void initInvoiceAndOriAmt() {
		loadInvoiceOriAmt();
		loadInvoiceAmt();
		BigDecimal tempOri = allInvoiceOriAmt;
		BigDecimal temp = allInvoiceAmt;
		DecimalFormat dFormat = new DecimalFormat("0.00");
		if (!FDCHelper.isEmpty(txtInvoiceOriAmt.getBigDecimalValue())) {
			tempOri = tempOri.add(txtInvoiceOriAmt.getBigDecimalValue());
		}
		if (!FDCHelper.isEmpty(txtInvoiceAmt.getBigDecimalValue())) {
			temp = temp.add(txtInvoiceAmt.getBigDecimalValue());
		}
		invoiceOriAndAmtStr = dFormat.format(tempOri) + "/"
				+ dFormat.format(temp);
		txtAllInvAndOriAmt.setText(invoiceOriAndAmtStr);

	}

	/**
	 * 在onload中处理关于工程量对与界面显示控制的初始化都写在这里 建议如有其他与工程量相关逻辑初始化的修改也都写在这里
	 * 
	 * @author tim_gao
	 * @throws Exception
	 */
	protected void initParamOnLoadForWorkLoad() throws Exception {
		/**
		 * by 2012-03-19 在2011需求任务中新增 按照合同类型区分请款前是否做工程量确认
		 */
		// 因为先在工程量的判断中加入了合同的类型,而且合同的类型高于一切判断所以在这里
		isWorkLoadContarctType();

		if (isWorkLoadConType) {
			if (isAutoComplete && isFromProjectFillBill) {
				txtcompletePrjAmt.setRequired(false);
				txtpaymentProportion.setEditable(false);
				txtAllCompletePrjAmt.setEditable(false);
			}
			/**
			 * 完工工程量从进度系统取值
			 */
			if (isFromProjectFillBill) {
				txtAllCompletePrjAmt.setEditable(false);
				actionCopy.setEnabled(false);
				txtcompletePrjAmt.setEditable(false);
				kdtEntrys.getCell(rowIndex, columnIndex).getStyleAttributes()
						.setLocked(true);
				if (getOprtState().equals(OprtState.ADDNEW)) {
					fromProjectFill();
				} else if (FDCHelper.toBigDecimal(
						txtcompletePrjAmt.getBigDecimalValue()).compareTo(
						FDCHelper.ZERO) == 0) {
					txtpaymentProportion.setEditable(false);
					kdtEntrys.getCell(rowIndex, columnIndex)
							.getStyleAttributes().setLocked(false);
				}
			}

			if (isFromProjectFillBill
					&& (null != this.editData
							&& null != this.editData.getPaymentType()
							&& this.editData.getPaymentType().toString()
									.equals(YFK) || isSeparate)) {
				// 没有不可显示的
				this.contcompletePrjAmt.setEnabled(false);
				this.txtpaymentProportion.setRequired(false);
				this.txtcompletePrjAmt.setRequired(false);
			} else {
				setEditableAndRequiredBySeparateParam();
			}
		} else {
			this.txtpaymentProportion.setRequired(false);
			this.contcompletePrjAmt.setEnabled(false);
			this.txtcompletePrjAmt.setRequired(false);
			this.txtcompletePrjAmt.setEnabled(false);
		}

	}

	/**
	 * 用合同类型是否工程量确认来判断有关工程量的相关控制 三个地方调用
	 * 
	 * @throws BOSException
	 * @remarks 1.fetchinitParam 在onload 时候因为没有值,所以不会走,不影响 2.因为check 的时候重新回调用
	 *          fetchinitParam 所以这里会走
	 *          3.写在initParamOnLoadForWorkLoad中是因为contracttype 已经加载上了,所以要取出来判断下
	 */
	protected void isWorkLoadContarctType() throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("id");
		sels.add("contractType.id");
		sels.add("contractType.isWorkLoadConfirm");
		FilterInfo filter = new FilterInfo();
		// by tim_gao 这里本来是应该用无文本合同判断一下的,因为无文本合同目前还没有做好,所以为null,既为无文本
		String id = null;
		if (contractBill != null && contractBill.getId() != null) {
			id = contractBill.getId().toString();
		} else if (id != null && this.editData.getId() == null
				&& this.editData != null) {
			id = this.editData.getId().toString();
		}
		if (id == null) {
			isWorkLoadConType = false;
			return;
		}
		filter.getFilterItems().add(
				new FilterItemInfo("id", contractBill.getId().toString()));
		view.setSelector(sels);
		view.setFilter(filter);
		ContractBillCollection contract = ContractBillFactory
				.getRemoteInstance().getContractBillCollection(view);
		if (contract != null && contract.size() > 0) {
			/*
			 * 两层判断 1层 合同类型工程量确认 是:走原有逻辑根据参数判断 否：无工程量入
			 */
			if (!contract.get(0).getContractType().isIsWorkLoadConfirm()) {

				isWorkLoadConType = false;
			}
		}

	}

	protected boolean checkCtrlParam(String param) throws EASBizException,
			BOSException {
		String org = SysContext.getSysContext().getCurrentOrgUnit().getId()
				.toString();
		String ctrlCon = ParamManager.getParamValue(null,
				new ObjectUuidPK(org), param);

		return ("0".equals(ctrlCon)) || ("1".equals(ctrlCon));
	}

	/**
	 * 本申请单累计实付款（本币）实时取值 - R140227-0281
	 * 
	 * @author RD_zhaoqin
	 * @date 2014/03/21
	 */
	private void setTotalPayForReqPay() {
		BigDecimal totalPayedOriAmt = null; // 本申请单已付原币
		BigDecimal lstPayedOriAmt = null; // 截至本次累计实付原币
		BigDecimal lstPayedAmt = null; // 截至本次累计实付本币
		if (editData.getId() != null) {
			Map totalPayMap = getTotalPayAmtByThisReq(editData.getId()
					.toString());
			totalPayedOriAmt = (BigDecimal) totalPayMap.get("totalPayedOriAmt");
			totalPayAmtByReqId = (BigDecimal) totalPayMap.get("totalPayedAmt");
			lstPayedOriAmt = (BigDecimal) totalPayMap.get("lstPayedOriAmt");
			lstPayedAmt = (BigDecimal) totalPayMap.get("lstPayedAmt");
		}
		kdtEntrys.getCell(2, 7).setValue(totalPayedOriAmt); // 本申请单已付原币
		kdtEntrys.getCell(2, 10).setValue(totalPayAmtByReqId); // 本申请单已付本币
		// 截至本次累计实付原币 = 本申请单已付原币 + 截至上次累计实付原币
		kdtEntrys.getCell(5, 10).setValue(
				FDCHelper.add(lstPayedOriAmt, kdtEntrys.getCell(5, 2)
						.getValue()));
		// 截至本次累计实付本币 = 本申请单已付本币 + 截至上次累计实付本币
		kdtEntrys.getCell(5, 11).setValue(
				FDCHelper.add(lstPayedAmt, kdtEntrys.getCell(5, 3).getValue()));

		logger.info("======================================================");
		logger.info("PayRequestBillEditUI.setTotalPayForReqPay，设置付款申请单截至本次计实付");
		logger.info("截至上次累计实付原币，kdtEntrys.getCell(5, 2)："
				+ kdtEntrys.getCell(5, 2).getValue());
		logger.info("截至上次累计实付本币，kdtEntrys.getCell(5, 3)："
				+ kdtEntrys.getCell(5, 3).getValue());
		logger.info("已付原币，kdtEntrys.getCell(2, 7)："
				+ kdtEntrys.getCell(2, 7).getValue());
		logger.info("已付原币，kdtEntrys.getCell(2, 10)："
				+ kdtEntrys.getCell(2, 10).getValue());
		logger.info("截至本次累计实付原币，kdtEntrys.getCell(5, 10)："
				+ kdtEntrys.getCell(5, 10).getValue());
		logger.info("截至本次累计实付本币，kdtEntrys.getCell(5, 11)："
				+ kdtEntrys.getCell(5, 11).getValue());
		logger.info("======================================================");
	}
}