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
 * @(#)			PayRequestBillEditUI ��Ȩ�� �����������������޹�˾��Ȩ���� ������ �������뵥 �༭����
 * 
 * @author ����
 *         <p>
 * @createDate 2011-8-31
 *             <p>
 * @version EAS7.0
 * @see
 */
public class PayRequestBillEditUI extends AbstractPayRequestBillEditUI implements IWorkflowUISupport {
	/**
	 * δ�����ͬ��ʵ����ܴ�����ʵ�ֲ�ֵ����ͬʵ����=�ѹرյĸ������뵥��Ӧ�ĸ����ͬ�ڹ��̿�ϼ� + δ�رյĸ����� �뵥��ͬ�ڹ��̿�ϼơ�
	 */
	private static final String cantMoreThanTotalSettlePrice = "δ�����ͬ��ʵ����ܴ�����ʵ�ֲ�ֵ����ͬʵ����=�ѹرյĸ������뵥��Ӧ�ĸ����ͬ�ڹ��̿�ϼ� + δ�رյĸ������뵥��ͬ�ڹ��̿�ϼơ�";

	private static final long serialVersionUID = 1L;

	private BigDecimal totalPayAmtByReqId = FDCHelper.ZERO;

	private static final Logger logger = CoreUIObject.getLogger(PayRequestBillEditUI.class);

	private boolean isFirstLoad = true;// �Ƿ��һ�μ���,�������Ʊ��ļ�����ʾ

	private static final Color noEditColor = PayReqTableHelper.noEditColor;

	protected int rowIndex = 5;// ��ͬ�ڹ��̿��к�

	protected int columnIndex = 6;// ��ͬ�ڹ��̿��к�

	/** �Ƿ��θ��� */
	private boolean isMoreSettlement = false;
	/**
	 * ��ͬ����ͬ�����ı����Ƿ���붯̬�ɱ�
	 */
	protected boolean isCostSplitContract = false;

	// �������뵥�ύʱ���Ƿ����ͬδ��ȫ���
	private boolean checkAllSplit = true;

	// ��ʵ��Ϊ0ʱֻ��ѡ��Ԥ����Ŀ���
	private boolean isRealizedZeroCtrl = false;

	// �깤��������ȷ���Ƿ��ϸ����
	private boolean isFillBillControlStrict = false;

	// �¶ȹ����ƻ����Ƹ����������
	protected String CONTROLPAYREQUEST = "������";
	/**
	 * �����ۿ����
	 */
	private IUIWindow deductUIwindow = null;
	/**
	 * ���ڰ�cell����ֵ������map keyΪ���Ե�info��������valueΪcell������
	 */
	HashMap bindCellMap = new HashMap(20);

	private PayReqTableHelper tableHelper = new PayReqTableHelper(this);

	// �Ƿ�ʹ��Ԥ��
	protected boolean isMbgCtrl = false;

	// ���ز����ǿ�Ʋ��������ϵͳ
	protected boolean isNotEnterCAS = false;

	protected FDCBudgetParam fdcBudgetParam = null;

	// �������,�Ѿ�����
	private BigDecimal payScale;

	// ��Ӧ��
	// private SupplierCompanyInfoInfo supplierCompanyInfoInfo ;δʹ�ã���ע��
	// ���ø������Ϊ��ͬ�ĸ������ �Ӹ���й���
	private int payTimes = 0;

	// �����
	private ContractChangeBillCollection contractChangeBillCollection = null;

	// ���
	private BillBaseCollection paymentBillCollection = null;

	// �������뵥��Ӧ�Ľ�����
	private GuerdonOfPayReqBillCollection guerdonOfPayReqBillCollection = null;

	// ������
	private GuerdonBillCollection guerdonBillCollection = null;

	// �������뵥��Ӧ��ΥԼ��
	private CompensationOfPayReqBillCollection compensationOfPayReqBillCollection = null;

	// �������뵥��Ӧ�ļ׹��Ŀۿ�
	private PartAOfPayReqBillCollection partAOfPayReqBillCollection = null;

	// �������뵥��Ӧ�ļ״�ȷ�ϵ����
	private PartAConfmOfPayReqBillCollection partAConfmOfPayReqBillCollection = null;

	// �ۿ�����
	private DeductTypeCollection deductTypeCollection = null;

	// ������Ŀ��Ӧ�ĳɱ�����
	private FullOrgUnitInfo costOrg = null;

	// �Ƿ���ع���ʼ����
	private boolean hasFetchInit = false;

	// �ۼ�������ͬ��������ϸ����
	private boolean isControlCost = false;

	// ��;�ֶ��ܿ�
	protected int usageLegth = 90;

	// �������뵥�տ����к��տ��˺�Ϊ��¼��
	private boolean isBankRequire = false;

	/**
	 * ���뵥���ȿ������Զ�Ϊ100%
	 */
	private boolean isAutoComplete = false;

	// �׹���ϵͳ����
	private boolean partAParam = false;

	// �������뵥��ͬID
	private String payReqContractId = null;

	// �������뵥��ͬ�Ƿ�׹��ĺ�ͬ
	public static boolean isPartACon = false;

	// ��ģʽһ�廯
	private boolean isSimpleFinancial = false;

	// �ۼƷ�Ʊ���/
	private BigDecimal allInvoiceAmt = FDCHelper.ZERO;

	// �ۼƷ�Ʊ���ԭ��
	private BigDecimal allInvoiceOriAmt = FDCHelper.ZERO;

	// �ۼ����깤������/
	private BigDecimal allCompletePrjAmt = FDCHelper.ZERO;

	/**
	 * ������ȷ�������븶�������Ƿ����
	 */
	protected boolean isSeparate = false;

	// ���÷�Ʊ����
	private boolean invoiceMgr = false;

	// ��ģʽ����Ʊ
	protected boolean isSimpleInvoice = false;

	// �������뵥�����ı���ͬ��Ʊ�š���Ʊ����¼
	private boolean isInvoiceRequired = false;

	// �׹��ĺ�ͬ����صĲ���ȷ�ϵ����ۼ�ȷ�Ͻ���Ҫ д���� �깤��������һ�� ����������������޸����깤������
	// ��صĲ���ȷ�ϵ�IDS ������ʱ��������Щ����ȷ�ϵ��ĸ������뵥ID
	private BigDecimal confirmAmts = FDCHelper.ZERO;
	private PayRequestBillConfirmEntryCollection confirmBillEntry = null;

	// �Ƿ����������뵥�ۼƷ�Ʊ�����ں�ͬ�������
	private boolean isOverrun = false;
	/**
	 * �������Ƿ����ѡ���������ŵ���Ա,����������������ÿ���Ƿ����ѡ��������֯���ÿ��
	 */
	private boolean canSelectOtherOrgPerson = false;

	private final String fncResPath = "com.kingdee.eas.fdc.finance.client.FinanceResource";

	// by tim_gao ��ͬ�Ƿ񹤳���ȷ��
	private boolean isWorkLoadConType = true;

	private String invoiceOriAndAmtStr = "";

	/**
	 * ��ͬ�깤������ȡ����ϵͳ�����������
	 */
	private boolean isFromProjectFillBill = false;

	// �˵����Ƿ��и���
	private boolean isHasAttchment = false;

	// Ԥ�������
	private int advancePaymentNumber = 1;

	private boolean isControlPay = false;

	// �깤�������ӹ�����ȷ�ϵ�ȡ��
	private boolean isByWorkload = false;

	// Ԥ�����
	private static final String YFK = "Ԥ����";

	// ���ȿ�����
	private Object paymentProportionValue = null;
	// �����깤������
	private Object completePrjAmtValue = null;

	// �����깤����������
	private static final int COMPLETEPRJAMT = 1;
	// ���ȿ���������
	private static final int PAYMENTPROPORTION = 2;

	protected boolean ctrl = false;
	//[ʩ��]���͵ĺ�ͬ
	protected boolean isShiGongContract = false;
	protected FilterInfo originalAmount = null;

	/**
	 * ������
	 */
	public PayRequestBillEditUI() throws Exception {
		super();
		jbInit();
	}

	private void jbInit() {
		titleMain = getUITitle();
	}

	/**
	 * ���Ǹ���ķ����Կ��ƹ�������ť����ʾ
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

		// �״�
		btnTaoPrint.setIcon(EASResource.getIcon("imgTbtn_print"));
		this.toolBar.add(btnTaoPrint);
		this.toolBar.add(btnPrint);
		this.toolBar.add(btnPrintPreview);
		this.toolBar.add(separatorFW2);
		// ����ƻ�
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
		// �����ۿ��ť
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
	 * description ���ݱ仯ͳһ�¼������ݴ���Ĳ�ͬ������������ͬ�ı仯�¼�
	 * 
	 * @author ����
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

	/** ҵ������ �仯�¼� */
	ControlDateChangeListener bookedDateChangeListener = new ControlDateChangeListener(
			"bookedDate");

	/** ԭ�ҽ�� �仯�¼� */
	ControlDateChangeListener amountListener = new ControlDateChangeListener(
			"amount");

	/** �����깤������ �仯�¼� */
	ControlDateChangeListener completePrjAmtListener = new ControlDateChangeListener(
			"completePrjAmt");

	/** ���ȿ����� �仯�¼� */
	ControlDateChangeListener paymentProptListener = new ControlDateChangeListener(
			"paymentProp");

	/**
	 * description ���ü�����
	 * 
	 */
	protected void attachListeners() {
		txtcompletePrjAmt.addDataChangeListener(completePrjAmtListener);
		prmtcurrency.addDataChangeListener(amountListener);
		txtpaymentProportion.addDataChangeListener(paymentProptListener);
		pkbookedDate.addDataChangeListener(bookedDateChangeListener);

		// �ÿ��
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
	 * description �Ƴ�����
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
	 * description ��������
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

		// ��������������깤�������ļ���
		initPaymentProp();

		// setAutoNumber();
		if (editData.getUrgentDegree() == null) {
			editData.setUrgentDegree(UrgentDegreeEnum.NORMAL);
		}

		super.loadFields();
		// ����û���ݣ��������� by hpw
		// if (OprtState.ADDNEW.equals(getOprtState())) {
		// txtpaymentProportion.setValue(FDCHelper.ZERO);
		// txtcompletePrjAmt.setValue(FDCHelper.ZERO);
		// }
		//		
		// ���ø������Ϊ��ͬ�ĸ������ �Ӹ���л�ȡ
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

		// ��������ı���ͬ���������ť����
		if (editData.getContractId() != null
				&& PayReqUtils.isConWithoutTxt(editData.getContractId())) {
			actionAdjustDeduct.setEnabled(false);
		} else {
			actionAdjustDeduct.setEnabled(true);
		}

		// ��д���
		if (editData.getCapitalAmount() == null && editData.getAmount() != null) {
			// ��д���Ϊ��λ�ҽ��
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

		hasFetchInit = true; // �����Ѽ��ع���ʼ������

		// ��ʾ�ۼƷ�Ʊ����ԭ�Һͱ��ң�����ԭ�����ۼƷ�Ʊ���
		loadInvoiceAmt();

		// �����ۼ����깤���������ۼ�Ӧ���������
		loadAllCompletePrjAmt();

		// ���ü�����
		attachListeners();

		// �տ��ʺ��޸ĳ�F7ѡ�񣬵���Ԫ���ݶ�������������ԣ� ����û�н������ݰ󶨣���Ҫ�ֶ�װ�ء� by zhiyuan_tang
		// 2010/12/07 R101026-193
		if (editData.getRealSupplier() != null) {
			txtrecAccount.setValue(getSupplierCompanyBankInfoByAccount(editData
					.getRealSupplier().getId().toString(), editData
					.getRecBank(), editData.getRecAccount()));
		}
		txtrecAccount.setText(editData.getRecAccount());
	}

	/**
	 * description ���û��ʺ�ԭ�ҽ�� ���侫��
	 * 
	 * @author ����
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
					currency.getId(), company, bookedDate); // ��ȡ��ǰ����
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
	 * description ҳ�����ݰ�֮ǰ
	 * 
	 * @author ����
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
		 * ���������е�״̬���ж� by renliang 2010-5-26
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

		// ����¼�ڵ����ݴ洢��info
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
			// ��ʾ�����뵥�ĸ�����
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
	 * description ����������ݰ󶨵�editdata
	 * 
	 * @author ����
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * 
	 * @version EAS 7.0
	 * @see com.kingdee.eas.fdc.contract.client.AbstractPayRequestBillEditUI#storeFields()
	 */
	public void storeFields() {
		try {
			if (editData != null) {// ��һ�α���ʱ��ʼ״̬
				// �������뵥���Ӵ洢��λ�ұұ��Է���Ԥ��ϵͳ��ȡ�����ֶ�ֵ by Cassiel_peng 2009-10-3
				CompanyOrgUnitInfo currentFIUnit = SysContext.getSysContext()
						.getCurrentFIUnit();
				CurrencyInfo baseCurrency = currentFIUnit.getBaseCurrency();
				if (baseCurrency != null) {
					// R110519-0204:����գ�Ԥ�����ȡ���� ��Ԥ�����ʧЧ by hpw 2011.6.2
					if (baseCurrency.getNumber() == null) {
						baseCurrency = CurrencyFactory.getRemoteInstance()
								.getCurrencyInfo(
										new ObjectUuidPK(baseCurrency.getId()));
					}
					this.editData.setLocalCurrency(baseCurrency);
				}
			}
			if (null != editData.getContractId()) { // ���ú�ͬ���±�λ�����
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

		// �տ��ʺ��޸ĳ�F7ѡ�񣬵���Ԫ���ݶ�������������ԣ� ����û�н������ݰ󶨣���Ҫ�ֶ�����һ�¡� by zhiyuan_tang
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
	 * description ��ȡҳ�����������
	 * 
	 * @author ����
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

		// ��λ��
		baseCurrency = (CurrencyInfo) initData
				.get(FDCConstants.FDC_INIT_CURRENCY);
		// ������֯
		company = (CompanyOrgUnitInfo) initData
				.get(FDCConstants.FDC_INIT_COMPANY);
		// ��ͬ����
		contractBill = (ContractBillInfo) initData
				.get(FDCConstants.FDC_INIT_CONTRACT);
		// �������
		payScale = (BigDecimal) initData.get("payScale");
		// ��Ӧ��
		// supplierCompanyInfoInfo =
		// (SupplierCompanyInfoInfo)initData.get("supplierCompanyInfoInfo");

		// ���ø������Ϊ��ͬ�ĸ������ �Ӹ���й���
		payTimes = ((Integer) initData.get("payTimes")).intValue();
		// �����
		contractChangeBillCollection = (ContractChangeBillCollection) initData
				.get("ContractChangeBillCollection");
		// ���
		paymentBillCollection = (BillBaseCollection) initData
				.get("PaymentBillCollection");
		// �������뵥��Ӧ�Ľ�����
		guerdonOfPayReqBillCollection = (GuerdonOfPayReqBillCollection) initData
				.get("GuerdonOfPayReqBillCollection");
		// ������
		guerdonBillCollection = (GuerdonBillCollection) initData
				.get("GuerdonBillCollection");
		// �������뵥��Ӧ��ΥԼ��
		compensationOfPayReqBillCollection = (CompensationOfPayReqBillCollection) initData
				.get("CompensationOfPayReqBillCollection");
		// �������뵥��Ӧ�ļ׹��Ŀۿ�
		partAOfPayReqBillCollection = (PartAOfPayReqBillCollection) initData
				.get("PartAOfPayReqBillCollection");
		// �������뵥��Ӧ�ļ׹���ȷ�ϵ����
		partAConfmOfPayReqBillCollection = (PartAConfmOfPayReqBillCollection) initData
				.get("PartAConfmOfPayReqBillCollection");
		// �ۿ�����
		deductTypeCollection = (DeductTypeCollection) initData
				.get("DeductTypeCollection");
		// ������Ŀ��Ӧ�ĳɱ�����
		costOrg = (FullOrgUnitInfo) initData.get("FullOrgUnitInfo");

		// ����
		bookedDate = (Date) initData.get(FDCConstants.FDC_INIT_DATE);
		if (bookedDate == null) {
			bookedDate = new Date();
		}
		serverDate = (Date) initData.get("serverDate");
		if (serverDate == null) {
			serverDate = bookedDate;
		}
		// ��ǰ�ڼ�
		curPeriod = (PeriodInfo) initData.get(FDCConstants.FDC_INIT_PERIOD);

		curProject = (CurProjectInfo) initData
				.get(FDCConstants.FDC_INIT_PROJECT);

		orgUnitInfo = (FullOrgUnitInfo) initData
				.get(FDCConstants.FDC_INIT_ORGUNIT);
		if (orgUnitInfo == null) {
			orgUnitInfo = SysContext.getSysContext().getCurrentOrgUnit()
					.castToFullOrgUnitInfo();
		}

		// �׹�ȷ�ϵ���ȷ�Ͻ��
		confirmAmts = FDCHelper.toBigDecimal(initData.get("confirmAmts"));
		// �׹�ȷ�Ϸ�¼
		confirmBillEntry = (PayRequestBillConfirmEntryCollection) initData
				.get("confirmBillEntry");
		this.isCostSplitContract = isCostSplit();
	}

	/**
	 * description ��ͬ�����ı���ͬΪ���붯̬�ɱ�ʱ����true�����򷵻�false
	 * 
	 * @author ����
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
		if (contractBill != null && contractBill.isIsCoseSplit()) {// ��ͬ
			return true;
		}

		String contractBillId = (String) getUIContext().get("contractBillId");
		if (PayReqUtils.isConWithoutTxt(contractBillId)) { // �ǲ������ı���ͬ
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
	 * description ����ʼ������
	 * 
	 * @author ����
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * 
	 * @version EAS 7.0
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#fetchInitParam()
	 */
	protected void fetchInitParam() throws Exception {
		// modify by lihaiou, 2014-09-12, fix bug R140823-0007,������������
		Map paramItem = getParamItem();
		if (orgUnitInfo != null) {
			Map param = paramItem;
			// param = FDCUtils.getDefaultFDCParam(null,
			// orgUnitInfo.getId().toString());

			// �Ƿ�����Ԥ��
			isMbgCtrl = Boolean.valueOf(
					param.get(FDCConstants.FDC_PARAM_STARTMG).toString())
					.booleanValue();

			// �������뵥������������ɸ�����
			isControlCost = Boolean.valueOf(
					param.get(FDCConstants.FDC_PARAM_OUTPAYAMOUNT).toString())
					.booleanValue();

			// ���뵥���ȿ������Զ�Ϊ100%
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
		// ���óɱ�����һ�廯
		// Map paramItem = null;
		// paramItem = FDCUtils.getDefaultFDCParam(null,
		// company.getId().toString());

		if (paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION) != null) {
			isIncorporation = Boolean.valueOf(
					paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION)
							.toString()).booleanValue();
		}

		// ��ģʽ��һ�廯
		if (paramItem.get(FDCConstants.FDC_PARAM_SIMPLEFINACIAL) != null) {
			isSimpleFinancial = Boolean.valueOf(
					paramItem.get(FDCConstants.FDC_PARAM_SIMPLEFINACIAL)
							.toString()).booleanValue();
			// isAutoComplete = isAutoComplete;//||isSimpleFinancial;
		}

		// ��;�ֶ��ܿ�
		if (paramItem.get("CS050") != null) {
			usageLegth = Integer.valueOf(paramItem.get("CS050").toString())
					.intValue();
		}

		// �������뵥�տ����к��տ��˺�Ϊ��¼��
		if (paramItem.get(FDCConstants.FDC_PARAM_BANKREQURE) != null) {
			isBankRequire = Boolean
					.valueOf(
							paramItem.get(FDCConstants.FDC_PARAM_BANKREQURE)
									.toString()).booleanValue();
		}

		// ���ز����ǿ�ƽ������ϵͳ
		if (paramItem.get(FDCConstants.FDC_PARAM_NOTENTERCAS) != null) {
			isNotEnterCAS = Boolean.valueOf(
					paramItem.get(FDCConstants.FDC_PARAM_NOTENTERCAS)
							.toString()).booleanValue();
		}

		// �׹���
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

		// ��ͬ�깤������ȡ����ϵͳ�����������
		if (paramItem.get(FDCConstants.FDC_PARAM_PROJECTFILLBILL) != null) {
			boolean tempBoolean = (Boolean.valueOf(paramItem.get(
					FDCConstants.FDC_PARAM_PROJECTFILLBILL).toString())
					.booleanValue());
			isFromProjectFillBill = tempBoolean && (!isSeparate)
					&& (!isAutoComplete);
		}
		// ��Ϊ���ڹ��������ж��м����˺�ͬ������,���Һ�ͬ�����͸���һ���ж�����������
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
			CONTROLPAYREQUEST = "�ϸ����";
		} else if ("1".equals(CONTROLPAYREQUEST)) {
			CONTROLPAYREQUEST = "��ʾ����";
		} else {
			CONTROLPAYREQUEST = "������";
		}

		// ת���ɲ�������
		// Boolean flag = FdcBooleanUtil.toBooleanObject(CONTROLPAYREQUEST);
		/*
		 * if (Boolean.FALSE.equals(flag)) { CONTROLPAYREQUEST = "�ϸ����"; } else
		 * if (Boolean.TRUE.equals(flag)) { CONTROLPAYREQUEST = "��ʾ����"; } else {
		 * CONTROLPAYREQUEST = "������"; }
		 */
		/* modified by zhaoqin for BT867964 on 2015/01/19 end */
	}

	/**
	 * @author RD_haiou_li
	 * @date 2014-09-12
	 * @description:һ���Խ�����Ĳ�������ѡȡ
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
			// �������뵥������������ɸ�����
			hmParamIn.put(FDCConstants.FDC_PARAM_OUTPAYAMOUNT, comPK);
			// ���뵥���ȿ������Զ�Ϊ100%
			hmParamIn.put(FDCConstants.FDC_PARAM_PAYPROGRESS, comPK);
			hmParamIn.put(FDCConstants.FDC_PARAM_SELECTPERSON, null);
			hmParamIn.put(FDCConstants.FDC_PARAM_ADVANCEPAYMENTNUMBER, comPK);
			// δ�����ͬ��ʵ���������ʵ�ֲ�ֵʱ�Ƿ��ϸ���� ���Ų��� by jian_wen 2009.12.15
			hmParamIn.put(FDCConstants.FDC_PARAM_ISCONTROLPAYMENT, comPK);
			hmParamIn.put(FDCConstants.FDC_PARAM_MORESETTER, null);

		}

		if (company != null) {
			IObjectPK comPK = new ObjectUuidPK(company.getId().toString());
			hmParamIn.put(FDCConstants.FDC_PARAM_INCORPORATION, comPK);
			hmParamIn.put(FDCConstants.FDC_PARAM_SIMPLEFINACIAL, comPK);
			// �������뵥�տ����к��տ��˺�Ϊ��¼��
			hmParamIn.put(FDCConstants.FDC_PARAM_BANKREQURE, comPK);
			// ���ز�����ǿ�Ʋ����н������ϵͳ
			hmParamIn.put(FDCConstants.FDC_PARAM_NOTENTERCAS, comPK);
			hmParamIn.put(FDCConstants.FDC_PARAM_CREATEPARTADEDUCT, comPK);
			// �ϸ����
			hmParamIn.put(FDCConstants.FDC_PARAM_BUDGET_STRICTCTRL, comPK);
			// ��ͬ�ƻ�����
			hmParamIn.put(FDCConstants.FDC_PARAM_BUDGET_CONTRACTCTRPAY, comPK);
			// �ɱ���Ŀ����ƻ�����
			hmParamIn.put(FDCConstants.FDC_PARAM_BUDGET_COSTACCTCTRPAY, comPK);
			// Ԥ��ϵͳ����
			hmParamIn.put(FDCConstants.FDC_PARAM_BUDGET_BGSYSCTRPAY, comPK);
			// Ԥ��ϵͳ����ʱ���Ƶ��ɱ�/����������
			hmParamIn.put(FDCConstants.FDC_PARAM_BUDGET_CTRLCOSTACCOUNT, comPK);
			// �������븶�����
			hmParamIn.put(FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT, comPK);

			// �������뵥�����ı���ͬ��Ʊ�š���Ʊ����¼
			hmParamIn.put(FDCConstants.FDC_PARAM_INVOICEREQUIRED, null);

			hmParamIn.put(FDCConstants.FDC_PARAM_INVOICEMRG, comPK);

			// �Ƿ����������뵥�ۼƷ�Ʊ�����ں�ͬ�������
			hmParamIn.put(FDCConstants.FDC_PARAM_OVERRUNCONPRICE, comPK);

			// ��ͬ�깤������ȡ����ϵͳ�����������
			hmParamIn.put(FDCConstants.FDC_PARAM_PROJECTFILLBILL, null);

			// ���ȹ����깤��������ȷ���Ƿ��ϸ����
			hmParamIn.put(FDCConstants.FDCSCH_PARAM_ISFILLBILLCONTROLSTRICT,
					comPK);

			hmParamIn.put(FDCConstants.FDC_PARAM_SIMPLEINVOICE, comPK);
		}

		// ��ͬδ��֣�����¼�븶�����뵥
		hmParamIn.put(FDCConstants.FDC_PARAM_CHECKALLSPLIT, null);

		// ��ʵ�ֲ�ֵΪ0ʱ�Ŀ���
		hmParamIn.put(FDCConstants.FDC_PARAM_REALIZEDZEROCTRL, null);

		String org = SysContext.getSysContext().getCurrentOrgUnit().getId()
				.toString();
		hmParamIn.put("FDC325_CONTROLPAYREQUEST", new ObjectUuidPK(org));

		Map paramItem = FDCUtils.getParamHashMapBatch(null, hmParamIn);
		return paramItem;
	}

	public void onLoad() throws Exception {
		super.onLoad();

		// ��ʼ��Ԥ����ĿF7
		initPrmtPlanHasCon();

		// �Ƿ�������Ϣ����
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
			// �༭������
			public void editStopped(KDTEditEvent e) {
				try {
					kdtEntrys_editStopped(e);
				} catch (Exception e1) {
					handUIExceptionAndAbort(e1);
				}
			}
		});

		if (isFirstLoad) {// ��һ�μ���ʱ��ʼ����������,�Ժ󲻻�ı�

			getDetailTable().getScriptManager().setAutoRun(false);
			PayReqUtils.setValueToCell(editData, bindCellMap); // �������е�ֵ ��䵽�����

			/* modified by zhaoqin for R140403-0233 on 2014/04/15 start */
			// if (!getOprtState().equals(OprtState.ADDNEW)) { //��������
			// tableHelper.updateLstReqAmt(editData, false);
			// }
			/* modified by zhaoqin for R140403-0233 on 2014/04/15 end */
		}

		if (isAdvance()) {
			tableHelper.updateLstAdvanceAmt(editData, false);
			// ��ʽ����,��д
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

		// ����ԭ�ҽ��Ŀ�¼�뷶Χ
		txtAmount.setPrecision(2);
		txtAmount.setMinimumValue(FDCHelper.MIN_VALUE);
		txtAmount.setMaximumValue(FDCHelper.MAX_VALUE.multiply(FDCHelper.TEN));
		// �޸Ĺ��ڱ�λ�ҽ�����ʱ�д���ֻ֧�ֻ���С�������λ
		txtBcAmount.setPrecision(2); // added by Owen_wen ͳһ��Ϊ2λС�����������
										// �ᵥ��R100520-107
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
			// �����������ܸ����޿����������ܸ����ȿ�
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

		// ���ݲ��������Ƿ��¼
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
			// ����жϣ���֤��ʾ��ȷ
			if (OprtState.ADDNEW.equals(getOprtState())) {
				chkIsPay.setSelected(false);
			}
		}

		// ��ë��������onload()����storeFilds����ע�͵� By Owen_wen 2011-11-04
		// if (getOprtState() != OprtState.VIEW) {
		// this.storeFields();
		// }

		if (contractBill != null
				&& PayReqUtils.isContractBill(editData.getContractId())) {
			isPartACon = this.contractBill.isIsPartAMaterialCon();
		}
		/**
		 * ϵͳ��������Ϊ���ʱ�����ؽ��ȸ�������ͱ����깤���������
		 */
		if (fdcBudgetParam.isAcctCtrl() && contractBill != null
				&& contractBill.isIsCoseSplit()) {
			// ����ǩ�����ǩ���Ľ�
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

		// ҵ�������ж�Ϊ��ʱȡ�ڼ��ж�
		if (pkbookedDate != null && pkbookedDate.isSupportedEmpty()) {
			pkbookedDate.setSupportedEmpty(false);
		}
		this.prmtcurrency.setEditable(false);
		this.prmtcurrency.setEnabled(false);

		Object value = prmtcurrency.getValue();
		if (value instanceof CurrencyInfo) {
			// ��λ�Ҵ���
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
								.setValue(null); // ���������ͬ�ڹ��̿��
					}
					oriAmt = FDCHelper.toBigDecimal(getDetailTable().getCell(
							rowIndex + 1, columnIndex).getValue());
					if (FDCHelper.ZERO.compareTo(oriAmt) == 0) {
						getDetailTable().getCell(rowIndex + 1, columnIndex + 1)
								.setValue(null); // ��������Ԥ�����
					}
				}
			}

		});

		this.actionViewMaterialConfirm.setVisible(true);

		if (FDCHelper.ZERO.compareTo(this.confirmAmts) != 0) {
			this.setConfirmBillEntryAndPrjAmt();
		}

		updateCompletePrjAmt();

		// ���Ϊ�ݹ�������ʱ������¼�����ֶν���Ʊ����¼��(��Ӧ�ĺ�ͬ�ڹ��̿�����������¼��)
		Object obj = prmtPayment.getValue();
		if (obj != null && obj instanceof PaymentTypeInfo) {
			String tempID = PaymentTypeInfo.tempID;// �ݹ���
			PaymentTypeInfo type = (PaymentTypeInfo) obj;
			if (type.getPayType().getId().toString().equals(tempID)) {
				this.kdtEntrys.getStyleAttributes().setLocked(true);
				if (this.kdtEntrys.getCell(rowIndex, columnIndex) != null) {
					this.kdtEntrys.getCell(rowIndex, columnIndex)
							.getStyleAttributes().setLocked(true);
				}
			}
		}

		// �����뵥�ۼ�ʵ������ң�ʵʱȡֵ
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

		// ��ͬ�޶��ͱ������Ҫ�����ͬ����ӳ������״̬Ϊ����������ύ�ĸ������뵥�еı��ָ������ȥ by cassiel 2010-08-06
		if (!FDCBillStateEnum.AUDITTED.equals(this.editData.getState())) {
			if (PayReqUtils.isContractBill(editData.getContractId())) {
				tableHelper.updateDynamicValue(editData, contractBill,
						contractChangeBillCollection, paymentBillCollection);
			}
		} else {// PBG095801..�����ĵ��ݣ���ͬ�������ҲӦ���¹���������
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
		 * ������ʾ�������뵥�����ϴ��ۼ�ʵ�� STATUS_FINDVIEW��ͬ�ɱ���Ϣ���ʱ���������״̬���������״̬Ҳ���¼���
		 * ���PayRequestFullInfoUI �е�tblMain_tableClicked
		 */
		if (STATUS_VIEW == this.getOprtState()
				|| STATUS_EDIT == this.getOprtState()
				|| STATUS_FINDVIEW == this.getOprtState()) {
			// ���ڹ��������������У�Ҳ�����¼���
			if (!isFromMsgCenterNoEdit) {// check this..�����������ĵ��ݷ�ʵʱ������..
				setLstPriRaiedORPaied();
			}
		}

		/* modified by zhaoqin for R140227-0281 on 2014/03/21 */
		setTotalPayForReqPay();

		/**
		 * ����ʽ�õ����������ã����Խ����ʽ���ظ���д ���ֱ����� �������ݲ���ȷ������
		 */
		tableHelper.calcTable();
		getDetailTable().getScriptManager().setAutoRun(true);
		kdtEntrys.getScriptManager().runAll();
		// by tim_gao �й��ڹ��������Ƶĳ�ʼ��
		initParamOnLoadForWorkLoad();

		// ǰһ��һ��һ�����ðɣ�Ҫ��Ȼһ�ѵ�����
		// actionFirst.setVisible(false);
		// actionLast.setVisible(false);
		// actionPre.setVisible(false);
		// actionNext.setVisible(false);

		// �������뵥���ϲ顱��ͬ����ƻ��Ĺ�����ʱ������
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
		if ("������".equals(CONTROLPAYREQUEST)) {
			kdLplanState.setVisible(false);
		}
		this.setKdDepPlanStateValue();

		// ȡ��ÿ�еĵ�Ԫ��ֵMap
		Map rowValuesMap = FDCTableHelper.getRowValuesMap(this.getDetailTable());
		MapUtils.debugPrint(System.out, "ÿ�еĵ�Ԫ��ֵMap", rowValuesMap);

		// ��ӡ�󶨵�Ԫ��Map
		printBindCellMap();
		//�ϲ�13�꿪�������ݣ�ʩ�����ͺ�ͬ�������Ľ���ֶ�
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
		if(typeInfo != null && "[ʩ��]".equals(typeInfo.getName()))
			isShiGongContract = true;
	}

	/**
	 * ��������ʼ��Ԥ����ĿF7
	 * 
	 * @author RD_skyiter_wang
	 * @createDate 2014-3-17
	 */
	private void initPrmtPlanHasCon() {
		try {
			if (checkCtrlParam("FDC325_CONTROLPAYREQUEST")) {
				this.btnViewBudget.setText("�鿴�ƻ�");
				this.ctrl = true;
			} else {
				this.contPlanHasCon.setBoundLabelText("Ԥ����Ŀ");
				this.contPlanUnCon.setBoundLabelText("Ԥ����Ŀ");
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
	 * ��������ӡ�󶨵�Ԫ��Map
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
		logger.info("��ӡ�󶨵�Ԫ��Map��start");
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			String cellKey = (String) iterator.next();
			cell = (ICell) bindCellMap.get(cellKey);
			int i = cell.getRowIndex();
			int j = cell.getColumnIndex();
			cellValue = cell.getValue();

			cellIndexStr = "[" + i + "," + j + "]";

			logger.info(cellKey + "<--->" + cellIndexStr + ":" + cellValue);
		}
		logger.info("��ӡ�󶨵�Ԫ��Map��end");
		logger.info("======================================================");
	}

	/**
	 * �������Ƿ�������Ϣ���ģ����Ҳ��༭
	 * 
	 * @see VoucherEditUI.refreshUITitle
	 * @return
	 * @Author��skyiter_wang
	 * @CreateTime��2013-9-6
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
	 * ������
	 * 
	 * @param table
	 * @Author��keyan_zhao
	 * @CreateTime��2012-12-10
	 */
	private void setOrgAmountForEntry(KDTable table) {
		if (contractBill == null)
			return;
		// ����
		BigDecimal rate = contractBill.getExRate();
		/**
		 * �������ԭ��
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
							BigDecimal.ROUND_HALF_UP)); // ԭ��=����/����
			((ICell) bindCellMap.get(PayRequestBillContants.LATESTPRICE))
					.setValue(tmp);
		}

		/**
		 *��ͬԭ��
		 */
		tmp = FDCHelper.ZERO;
		tmp = editData.getContractPrice();
		if (null == tmp) {
			((ICell) bindCellMap.get(PayRequestBillContants.CONTRACTORGPRICE))
					.setValue(FDCHelper.ZERO);
		} else {
			((ICell) bindCellMap.get(PayRequestBillContants.CONTRACTORGPRICE))
					.setValue(FDCHelper.divide(tmp, rate, 2,
							BigDecimal.ROUND_HALF_UP)); // ԭ��=����/����
		}

		/**
		 *������
		 */
		tmp = FDCHelper.ZERO;
		tmp = editData.getSettleAmt();
		if (null == tmp) {
			((ICell) bindCellMap.get(PayRequestBillContants.SETTLEORGAMT))
					.setValue(FDCHelper.ZERO);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			((ICell) bindCellMap.get(PayRequestBillContants.SETTLEORGAMT))
					.setValue(FDCHelper.divide(tmp, rate)); // ԭ��=����/����
		}

		/**
		 *������
		 */
		tmp = FDCHelper.ZERO;
		tmp = editData.getChangeAmt();
		if (null == tmp) {
			((ICell) bindCellMap.get(PayRequestBillContants.CHANGEORGAMT))
					.setValue(FDCHelper.ZERO);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			((ICell) bindCellMap.get(PayRequestBillContants.CHANGEORGAMT))
					.setValue(FDCHelper.divide(tmp, rate)); // ԭ��=����/����
		}

		/**
		 * ��ͬ�ڹ��̿�
		 */
		tmp = FDCHelper.ZERO;
		tmp = (BigDecimal) table.getCell(5, 3).getValue();
		if (null == tmp) {
			table.getCell(5, 2).setValue(FDCHelper.ZERO);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			table.getCell(5, 2).setValue(FDCHelper.divide(tmp, rate)); //ԭ��=����/����
		}

		/* modified by zhaoqin for R140227-0281 on 2014/03/21 start */
		/*
		 * tmp = (BigDecimal) table.getCell(5, 5).getValue(); if (null == tmp ||
		 * FDCHelper.toBigDecimal(tmp, 2).compareTo(FDCHelper.ZERO) == 0) {
		 * table.getCell(5, 4).setValue(FDCHelper.ZERO); } else { tmp =
		 * FDCHelper.toBigDecimal(tmp, 2); table.getCell(5,
		 * 4).setValue(FDCHelper.divide(tmp, rate)); //ԭ��=����/���� }
		 */
		/* modified by zhaoqin for R140227-0281 on 2014/03/21 end */

		/**
		 * Ԥ����
		 */
		tmp = (BigDecimal) table.getCell(6, 3).getValue();
		if (null == tmp
				|| FDCHelper.toBigDecimal(tmp, 2).compareTo(FDCHelper.ZERO) == 0) {
			table.getCell(6, 2).setValue(null);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			table.getCell(6, 2).setValue(FDCHelper.divide(tmp, rate)); //ԭ��=����/����
		}

		tmp = (BigDecimal) table.getCell(6, 5).getValue();
		if (null == tmp
				|| FDCHelper.toBigDecimal(tmp, 2).compareTo(FDCHelper.ZERO) == 0) {
			table.getCell(6, 4).setValue(null);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			table.getCell(6, 4).setValue(FDCHelper.divide(tmp, rate)); //ԭ��=����/����
		}

		/**
		 * ����
		 */
		tmp = (BigDecimal) table.getCell(8, 3).getValue();
		if (null == tmp
				|| FDCHelper.toBigDecimal(tmp, 2).compareTo(FDCHelper.ZERO) == 0) {
			table.getCell(8, 2).setValue(null);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			table.getCell(8, 2).setValue(FDCHelper.divide(tmp, rate)); //ԭ��=����/����
		}

		tmp = (BigDecimal) table.getCell(8, 5).getValue();
		if (null == tmp
				|| FDCHelper.toBigDecimal(tmp, 2).compareTo(FDCHelper.ZERO) == 0) {
			table.getCell(8, 4).setValue(null);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			table.getCell(8, 4).setValue(FDCHelper.divide(tmp, rate)); //ԭ��=����/����
		}

		/**
		 * ΥԼ��
		 */
		tmp = (BigDecimal) table.getCell(9, 3).getValue();
		if (null == tmp
				|| FDCHelper.toBigDecimal(tmp, 2).compareTo(FDCHelper.ZERO) == 0) {
			table.getCell(9, 2).setValue(null);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			table.getCell(9, 2).setValue(FDCHelper.divide(tmp, rate)); //ԭ��=����/����
		}

		tmp = (BigDecimal) table.getCell(9, 5).getValue();
		if (null == tmp
				|| FDCHelper.toBigDecimal(tmp, 2).compareTo(FDCHelper.ZERO) == 0) {
			table.getCell(9, 4).setValue(null);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			table.getCell(9, 4).setValue(FDCHelper.divide(tmp, rate)); //ԭ��=����/����
		}

		int rowCount = kdtEntrys.getRowCount();
		int aMartIndex = rowCount - 6;
		/**
		 * Ӧ�ۼ׹���
		 */
		tmp = (BigDecimal) table.getCell(aMartIndex, 3).getValue();
		if (null == tmp
				|| FDCHelper.toBigDecimal(tmp, 2).compareTo(FDCHelper.ZERO) == 0) {
			table.getCell(aMartIndex, 2).setValue(FDCHelper.ZERO);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			table.getCell(aMartIndex, 2).setValue(FDCHelper.divide(tmp, rate)); // ԭ��
																				// =
																				// ����
																				// /
																				// ����
		}

		tmp = (BigDecimal) table.getCell(aMartIndex, 5).getValue();
		if (null == tmp
				|| FDCHelper.toBigDecimal(tmp, 2).compareTo(FDCHelper.ZERO) == 0) {
			table.getCell(aMartIndex, 4).setValue(FDCHelper.ZERO);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			table.getCell(aMartIndex, 4).setValue(FDCHelper.divide(tmp, rate)); // ԭ��
																				// =
																				// ����
																				// /
																				// ����
		}
	}

	/**
	 * ������������ʾ�������뵥�����ϴ��ۼ�ʵ��
	 * 
	 * @param reqPayId
	 * @throws BOSException
	 * @throws SQLException
	 * @Author��keyan_zhao
	 * @CreateTime��2012-11-21
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

			// �����ϴ��ۼ�ʵ��ȡֵSQL
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
				.info("PayRequestBillEditUI.setLstPriRaiedORPaied��������ʾ�������뵥�����ϴ��ۼ�ʵ��");
		logger.info("beforeTotalPayAmtByThisReq��old��"
				+ kdtEntrys.getCell(5, 2).getValue());
		logger.info("beforeTotalLocalPayAmtByThisReq��old��"
				+ kdtEntrys.getCell(5, 3).getValue());
		logger.info("beforeTotalPayAmtByThisReq��new��"
				+ beforeTotalPayAmtByThisReq);
		logger.info("beforeTotalLocalPayAmtByThisReq��new��"
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
	 * �����������ϴ��ۼ�ʵ��ȡֵSQL <br/> ҵ���߼�������PaymentBillEditUI.setLstPriRaiedORPaied by
	 * skyiter_wang 2013-08-14
	 * 
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 * @Author��skyiter_wang
	 * @CreateTime��2013-8-14
	 */
	private String genenrateLstPriRaiedORPaiedSql() throws BOSException,
			SQLException {
		// �����ϴ��ۼ�ʵ��ȡֵ����
		// �������������뵥�������Ѹ���ĸ��¿�Ľ�� + '���������뵥'�´�����������'�������������'�������Ѹ���ĸ�����
		// ��Ȼ���ڵ����⣺ͬһ��ĸ������뵥�µ����ݿ��ܶ��ᱻ�������������ݱ��---���ڸ������뵥ҵ�����ڿ���ֻ��¼�˾�ȷ���յ���������
		// ҵ���߼�������PaymentBillEditUI.setLstPriRaiedORPaied by skyiter_wang
		// 2013-08-14
		// ��������ͨ by skyiter_wang 2013-08-14

		// ȡ�ú�ͬID
		String contractId = editData.getContractId();
		// �������뵥ID
		String ffdcPayReqID = editData.getId().toString();

		// ȡ��ҵ������
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

		// ȡ�ô�������
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
		// ע�⣺���������뵥����ʹ�õ���T_CON_PayRequestBill���Ƶ����ڣ��������
		// ��ʹ�õ���T_CAS_PaymentBill���Ƶ�����
		sb
				.append("             AND req.FcreateTime < {ts '{2}' }) AS temp_table  		\n");

		String sqlStr = sb.toString();
		sqlStr = sqlStr.replaceAll("\\{0\\}", contractId);
		sqlStr = sqlStr.replaceAll("\\{1\\}", booDateString);
		sqlStr = sqlStr.replaceAll("\\{2\\}", createTimeStr);
		sqlStr = sqlStr.replaceAll("\\{4\\}", ffdcPayReqID);

		logger.info("======================================================");
		logger
				.info("PayRequestBillEditUI.genenrateLstPriRaiedORPaiedSql��������ʾ�������뵥�����ϴ��ۼ�ʵ��SQL");
		logger.info("sql��" + sqlStr);
		logger.info("======================================================");

		return sqlStr;
	}

	/**
	 * ����������Ǳ༭״̬�����ǲ鿴״̬�����Ҹ���������YFK,�Ͱѡ����ȿ��������͡������깤�����������ԣ�BT698646��
	 * 
	 * @Author��jian_cao
	 * @CreateTime��2012-8-9
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

	// ��ȡ�����Ա����ʹ�á���ken_liu
	BigDecimal oriCurrency = FDCHelper.ZERO; // �������ԭ�ұ�
	BigDecimal localCurrency = FDCHelper.ZERO; // ������۱���

	/**
	 * ��ͬ������� - R140417-0212
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

		// �������ԭ��
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
	 * description ��ӷ�¼ԭ��
	 * 
	 * @author ����
	 *         <p>
	 * @createDate 2011-9-6
	 *             <p>
	 * @param table
	 *            ��¼��
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
		// ��ͬ�������ԭ��
		if (contractBillId != null && contractBillId.trim().length() > 1) {
			// BigDecimal oriAmount = FDCHelper.ZERO;
			// BigDecimal amount = FDCHelper.ZERO;
			try {
				oriCurrency = (BigDecimal) FDCUtils.getLastOriginalAmt_Batch(
						null, new String[] { contractBillId }).get(
						contractBillId); // �������ԭ��
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

		// ��ͬ���ԭ��
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

		// ��ͬ������ԭ��
		HashMap map = getContractAttset(contractBillId);
		BigDecimal amt = (BigDecimal) map.get("amt");
		BigDecimal orgAmt = (BigDecimal) map.get("orgAmt");
		if (FDCHelper.toBigDecimal(amt, 2).compareTo(FDCHelper.ZERO) == 0) { // ����
			((ICell) bindCellMap.get(PayRequestBillContants.SETTLEAMT))
					.setValue(FDCHelper.ZERO);
		} else {
			((ICell) bindCellMap.get(PayRequestBillContants.SETTLEAMT))
					.setValue(amt);
		}
		if (FDCHelper.toBigDecimal(orgAmt, 2).compareTo(FDCHelper.ZERO) == 0) { // ԭ��
			((ICell) bindCellMap.get(PayRequestBillContants.SETTLEORGAMT))
					.setValue(FDCHelper.ZERO);
		} else {
			((ICell) bindCellMap.get(PayRequestBillContants.SETTLEORGAMT))
					.setValue(orgAmt);
		}
		// ���ǩ֤���
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

		// ����
		BigDecimal rate = contractBill.getExRate();
		// �����뵥�Ѹ�ԭ��
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

		// �����뵥�Ѹ�����
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
		 * ��ͬ�ڹ��̿�
		 */
		BigDecimal tmp = FDCHelper.ZERO;
		tmp = (BigDecimal) table.getCell(5, 3).getValue();
		if (null == tmp) {
			table.getCell(5, 2).setValue(FDCHelper.ZERO);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			table.getCell(5, 2).setValue(FDCHelper.divide(tmp, rate)); //ԭ��=����/����
		}

		/* modified by zhaoqin for R140227-0281 on 2014/03/21 start */
		/*
		 * tmp = (BigDecimal) table.getCell(5, 5).getValue(); if (null == tmp ||
		 * FDCHelper.toBigDecimal(tmp,2).compareTo(FDCHelper.ZERO)==0) {
		 * table.getCell(5, 4).setValue(FDCHelper.ZERO); }else { tmp =
		 * FDCHelper.toBigDecimal(tmp,2); table.getCell(5,
		 * 4).setValue(FDCHelper.divide(tmp, rate)); //ԭ��=����/���� }
		 */
		/* modified by zhaoqin for R140227-0281 on 2014/03/21 end */

		/**
		 * Ԥ����
		 */
		tmp = (BigDecimal) table.getCell(6, 3).getValue();
		if (null == tmp
				|| FDCHelper.toBigDecimal(tmp, 2).compareTo(FDCHelper.ZERO) == 0) {
			table.getCell(6, 2).setValue(null);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			table.getCell(6, 2).setValue(FDCHelper.divide(tmp, rate)); //ԭ��=����/����
		}

		tmp = (BigDecimal) table.getCell(6, 5).getValue();
		if (null == tmp
				|| FDCHelper.toBigDecimal(tmp, 2).compareTo(FDCHelper.ZERO) == 0) {
			table.getCell(6, 4).setValue(null);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			table.getCell(6, 4).setValue(FDCHelper.divide(tmp, rate)); //ԭ��=����/����
		}

		/**
		 * ����
		 */
		tmp = (BigDecimal) table.getCell(8, 3).getValue();
		if (null == tmp
				|| FDCHelper.toBigDecimal(tmp, 2).compareTo(FDCHelper.ZERO) == 0) {
			table.getCell(8, 2).setValue(null);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			table.getCell(8, 2).setValue(FDCHelper.divide(tmp, rate)); //ԭ��=����/����
		}

		tmp = (BigDecimal) table.getCell(8, 5).getValue();
		if (null == tmp
				|| FDCHelper.toBigDecimal(tmp, 2).compareTo(FDCHelper.ZERO) == 0) {
			table.getCell(8, 4).setValue(null);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			table.getCell(8, 4).setValue(FDCHelper.divide(tmp, rate)); //ԭ��=����/����
		}

		/**
		 * ΥԼ��
		 */
		tmp = (BigDecimal) table.getCell(9, 3).getValue();
		if (null == tmp
				|| FDCHelper.toBigDecimal(tmp, 2).compareTo(FDCHelper.ZERO) == 0) {
			table.getCell(9, 2).setValue(null);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			table.getCell(9, 2).setValue(FDCHelper.divide(tmp, rate)); //ԭ��=����/����
		}

		tmp = (BigDecimal) table.getCell(9, 5).getValue();
		if (null == tmp
				|| FDCHelper.toBigDecimal(tmp, 2).compareTo(FDCHelper.ZERO) == 0) {
			table.getCell(9, 4).setValue(null);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			table.getCell(9, 4).setValue(FDCHelper.divide(tmp, rate)); //ԭ��=����/����
		}

		int rowCount = kdtEntrys.getRowCount();
		int aMartIndex = rowCount - 6;
		/**
		 * Ӧ�ۼ׹���
		 */
		tmp = (BigDecimal) table.getCell(aMartIndex, 3).getValue();
		if (null == tmp
				|| FDCHelper.toBigDecimal(tmp, 2).compareTo(FDCHelper.ZERO) == 0) {
			table.getCell(aMartIndex, 2).setValue(FDCHelper.ZERO);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			table.getCell(aMartIndex, 2).setValue(FDCHelper.divide(tmp, rate)); // ԭ��
																				// =
																				// ����
																				// /
																				// ����
		}

		tmp = (BigDecimal) table.getCell(aMartIndex, 5).getValue();
		if (null == tmp
				|| FDCHelper.toBigDecimal(tmp, 2).compareTo(FDCHelper.ZERO) == 0) {
			table.getCell(aMartIndex, 4).setValue(FDCHelper.ZERO);
		} else {
			tmp = FDCHelper.toBigDecimal(tmp, 2);
			table.getCell(aMartIndex, 4).setValue(FDCHelper.divide(tmp, rate)); // ԭ��
																				// =
																				// ����
																				// /
																				// ����
		}

	};

	/**
	 * R110824-0401������Զ���󣺸����빤��������ʱ,�������뵥�Զ��ӹ�����ȷ�ϵ�ȡ��
	 * ���ۼ����깤����������ȡ��ֹ��ǰ�ú�ͬ���������Ĺ�����ȷ�ϵ���ȷ�Ϲ��������ϼ� �������깤����������Ĭ�ϵ����ۼ����깤������-
	 * ��ֹ�����ۼ����루���ң� �����ȿ�������:�������뱾��/�����깤�����������
	 * 
	 * @throws BOSException
	 */
	private void updateCompletePrjAmt() throws BOSException {
		// R110824-0401������Զ���󣺸����빤��������ʱ,�������뵥�Զ��ӹ�����ȷ�ϵ�ȡ��
		if (isSeparate && !isFromProjectFillBill
				&& OprtState.ADDNEW.equals(getOprtState())) {
			isByWorkload = true;
			detachListeners();
			// �������
			txtpaymentProportion.setEditable(true);
			txtpaymentProportion.setEnabled(true);
			txtpaymentProportion.setRequired(false);

			// �ۼ��깤������
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
			// û�й���������ʹ����Ҳ��д��
			if (!FDCHelper.isZero(allCompletePrjAmt)) {

				// �����깤������
				// �����ͬ�µĸ������뵥
				view = new EntityViewInfo();
				filter = new FilterInfo();
				// modify by lihaiou,2014-07-14,ȥ��ͬ��ID,�����Ǻ�ͬ�ı���
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

				// �����깤������Ĭ�ϵ����ۼ����깤������- �ۼ���ȷ���깤������ 2012-8-31 ��������
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
	 * ����������������ʱ�����桢�ύ ��������깤���������Ϊ0������У�����
	 * 
	 * @throws BOSException
	 * @Author��keyan_zhao
	 * @CreateTime��2013-7-19
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
			// �ۼ��깤������
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
				// �����ͬ�µĸ������뵥
				view = new EntityViewInfo();
				filter = new FilterInfo();
				// modify by lihaiou,2014-07-14,ȥ��ͬ��ID,�����Ǻ�ͬ�ı���
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
			// �����깤������Ĭ�ϵ����ۼ����깤������- �ۼ���ȷ���깤������ 2012-8-31 ��������
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
	 * description ȡ��ͬ������ԭ�Һͱ��� ������ԭ�ң���ͬ����ǰ���Ϊ0.00�������ȡ���㵥¼��ģ��༭���桰�������ԭ�ҡ�
	 * ������ң���ͬ����ǰ���Ϊ0.00�������ȡ���㵥¼��ģ��༭���桰������۱�λ�ҡ�
	 * 
	 * @author ����
	 *         <p>
	 * @createDate 2011-9-8
	 *             <p>
	 * @param contractId
	 *            ��ͬID
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
	 * (��ǰ���ݺ�ͬ�µ��ۼ�ʵ����+δ�������뵥�����˸�����ʾ����)ʱ����ȡ���صĲ�����0=�ϸ���ƣ��жϲ�������1=��ʾ��ֻ��ʾ�����жϣ��ɼ���ִ�У�
	 * 
	 * @author ����
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
					"FDC444_ALLPAIDMORETHANCONPRICE"); // ���ݼ��ſ��Ʊ�־��������Ż�ò���
		} catch (EASBizException e) {
			handUIExceptionAndAbort(e);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
		return retValue;
	}

	// modified by ken_liu.,.R130301-0367 ��Ϊ�ñ��Ҽ���
	/**
	 * ���ݡ������깤���������͡��������뱾�ҡ������Զ����㡰���ȿ�������
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
	 * �������жϸ��������ǲ��ǽ��ȿ���Һ�ͬ���͹�ѡ�ˡ�������ȷ�ϡ�
	 * 
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 * @throws Exception
	 * @Author��jian_cao
	 * @CreateTime��2012-11-15
	 */
	private boolean isProgressPaymentAndWorkLoadConfirm() throws BOSException,
			SQLException {
		PaymentTypeInfo type = (PaymentTypeInfo) prmtPayment.getValue();
		// ������ȿ�
		if (type != null
				&& type.getPayType().getId().toString().equals(
						PaymentTypeInfo.progressID)) {
			// ��ѯ��ͬ�����Ƿ�ѡ�ˡ�������ȷ�ϡ�
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
	 * ���������� �������깤���������������ȿ�������������������� �Ľ��
	 * 
	 * @param key
	 * @throws SQLException
	 * @throws BOSException
	 * @Author��jian_cao
	 * @CreateTime��2012-11-19
	 */
	private void caculateResult(int key) throws BOSException, SQLException {

		PaymentTypeInfo paymentType = (PaymentTypeInfo) prmtPayment.getValue();

		// modify by lihaiou,2014-08-07��ȥ���Ƿ�Ϊ�Զ������Ĳ����ж�
		if (!isFromProjectFillBill
				&& paymentType != null
				&& (!PaymentTypeInfo.settlementID.equals(paymentType
						.getPayType().getId().toString()))) {

			// ����������ԭ��
			BigDecimal originalAmount = FDCHelper.toBigDecimal(getDetailTable()
					.getCell(rowIndex, columnIndex).getValue());
			BigDecimal localAmount = FDCHelper.multiply(originalAmount,
					txtexchangeRate.getBigDecimalValue());
			// �����깤������
			BigDecimal completePrjAmt = FDCHelper
					.toBigDecimal(txtcompletePrjAmt.getBigDecimalValue());
			// ���ȿ�����
			BigDecimal paymentProportion = FDCHelper
					.toBigDecimal(txtpaymentProportion.getBigDecimalValue());

			// ����жϸ��������ǲ��ǽ��ȿ���Һ�ͬ���͹�ѡ�ˡ�������ȷ�ϡ�
			if (isProgressPaymentAndWorkLoadConfirm()) {
				switch (key) {

				case COMPLETEPRJAMT: // ����� �����깤�������޸�

					// �� ���ȿ�������Ϊ0ʱ������ ����������ԭ��
					if (!FDCNumberHelper.isZero(paymentProportion)) {
						caculateReqOriginalAmount(completePrjAmt,
								paymentProportion);
					} else if (!FDCNumberHelper.isZero(originalAmount)) { // ���ȿ�����
																			// Ϊ0ʱ
																			// ��
																			// ����
																			// ��
																			// ����������ԭ��
																			// ��
																			// Ҳ��Ϊ0
																			// ,
																			// �ͼ����
																			// ��
																			// ���ȿ�����
																			// ��
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

				case PAYMENTPROPORTION:// ����� ���ȿ������޸�

					// ���������깤����������Ϊ0������� �� ����������ԭ�ҡ�
					if (!FDCNumberHelper.isZero(completePrjAmt)) {
						caculateReqOriginalAmount(completePrjAmt,
								paymentProportion);
					} else if (!FDCNumberHelper.isZero(originalAmount)) { // ����
																			// �����깤������
																			// ��
																			// Ϊ0
																			// ,
																			// ����
																			// ��
																			// ����������ԭ��
																			// ��
																			// Ҳ��Ϊ0
																			// ,
																			// �ͼ����
																			// ��
																			// �����깤������
																			// ��
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
	 * ���������ݡ������깤���������͡����ȿ������������Զ����㡰���������
	 * 
	 * @param completePrjAmt
	 *            �������깤��������
	 * @param paymentProportion
	 *            �����ȿ�������
	 * @Author��jian_cao
	 * @CreateTime��2012-11-19
	 */
	private void caculateReqOriginalAmount(BigDecimal completePrjAmt,
			BigDecimal paymentProportion) {
		// ����������(ԭ��)
		// BigDecimal amount = FDCNumberHelper.divide(completePrjAmt,
		// txtexchangeRate.getBigDecimalValue()); //��Ӧ��ԭ��
		BigDecimal amount = FDCNumberHelper.multiply(completePrjAmt,
				paymentProportion);// ��Ӧ�ı���

		amount = FDCNumberHelper.divide(amount, FDCHelper.ONE_HUNDRED);
		getDetailTable().getCell(rowIndex, columnIndex).setValue(
				FDCNumberHelper.divide(amount, txtexchangeRate
						.getBigDecimalValue()));
		// ����������(����)
		// getDetailTable().getCell(rowIndex, columnIndex + 1).setValue(
		// FDCNumberHelper.multiply(amount,
		// txtexchangeRate.getBigDecimalValue()));
		getDetailTable().getCell(rowIndex, columnIndex + 1).setValue(amount);
	}

	/**
	 * ������ȷ���޸�
	 */
	protected void txtcompletePrjAmt_dataChanged(DataChangeEvent e)
			throws Exception {
		if (FDCNumberHelper.ZERO.compareTo((BigDecimal) e.getNewValue()) == 0) { // ���޸�����һ���ֶ�Ϊ��ʱ
																					// ��
																					// ���������ֶζ���ʾΪ��
			// �������ֵ�BUG�����ı��ͷ.������ɹ�����=0��ʱ�򣬱�ͷ.���ȿ�����Ҳ���ĳ�0%������������ϵ���޷��Ļ����� by
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
	 * ���ȿ������޸�
	 */
	protected void txtpaymentProportion_dataChanged(DataChangeEvent e)
			throws Exception {
		if (FDCNumberHelper.ZERO.compareTo((BigDecimal) e.getNewValue()) == 0) {// ���޸�����һ���ֶ�Ϊ��ʱ
																				// ��
																				// ���������ֶζ���ʾΪ��
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
	 * description ��ʼ���ƻ���Ŀ
	 * 
	 * @author ����
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
		// ������������PRMT�ؼ��������غ���һ��
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
	 * ѡ���ǩ��ʱ��У���Ƿ����಻ͬ��ͬ�µ����뵥ѡ���<br>
	 * ����ѱ�ѡ�������ʾ����ѡ<br>
	 * ��Ϊһ���ƻ�����һ����ͬ
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
				FDCMsgBox.showWarning(this, "�ô�ǩ����ͬ����ƻ��ѱ�������ͬ�µĸ������뵥���ã�������ѡ��");
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
						FDCMsgBox.showWarning(this, "�ú�ͬ�´��ڸ������뵥ѡ�� ��" + num
								+ "�� ��Ϊ��ǩ����ͬ��������ƻ�����ʹ��ͳһ�ƻ����Ƹ��");
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
	 * description ���ؼ׷���ϸ��Ϣ
	 * 
	 * @author ����
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
	 * description �깤���������Խ���ϵͳ������� �߼�����
	 * 
	 * @author ����
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
		if (isFillBillControlStrict) {// �깤��������ȷ���ϸ����
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
		if (idList.size() > 0) {// ò��û�� by zhiqiao_yang at 20110310
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

	// ҵ�����ڱ仯����,�ڼ�ı仯
	protected void bookedDate_dataChanged(
			com.kingdee.bos.ctrl.swing.event.DataChangeEvent e)
			throws Exception {
		String projectId = this.editData.getCurProject().getId().toString();
		fetchPeriod(e, pkbookedDate, cbPeriod, projectId, false);
	}

	/**
	 * description ���ý���ؼ�״̬
	 * 
	 * @author ����
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
			if (contractId == null || PayReqUtils.isConWithoutTxt(contractId)) {// ������������Ƿ��ͬ��ʾ������
				// ���ı��������޸�
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

		// ��ʾinfo�����ݵ����
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

		// Add by zhiyuan_tang 2010/07/30 �����ڲ鿴������޸ģ���������ԭ�ҿ��õ�BUG
		if (isPartACon && this.editData.getConfirmEntry().size() > 0) { // �в���ȷ�Ϸ�¼ʱ
																		// ��
																		// Ҫ�ò��ɱ༭
			kdtEntrys.getCell(rowIndex, columnIndex).getStyleAttributes()
					.setLocked(true);
		}

		// �������п��Ը��ƺ�ͬ����
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
			// ���ı��������޸�
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

		// onloadҲ������,������Щ��Ԫ����ʽû�б�����,�������ٴε���,������table
		kdtEntrys.getScriptManager().runAll();

		actionAddLine.setEnabled(false);
		actionRemoveLine.setEnabled(false);
		actionInsertLine.setEnabled(false);

		// �鿴״̬��Ҳ���Է����
		// actionUnAudit.setEnabled(editData.getState().equals(
		// FDCBillStateEnum.AUDITTED));

		Boolean disableSplit = (Boolean) getUIContext().get("disableSplit");
		if (disableSplit != null && disableSplit.booleanValue()) {
			actionUnAudit.setVisible(false);
			actionAudit.setVisible(false);
		}
		btnContractExecInfo.setVisible(true);
		btnContractExecInfo.setEnabled(true);

		// ��������ı���ͬ����"��ִͬ����Ϣ"���ε� by Cassiel_peng 2009-10-2
		if (PayReqUtils.isConWithoutTxt(this.editData.getContractId())) {
			this.btnContractExecInfo.setVisible(false);
			this.menuItemContractExecInfo.setVisible(false);
		}

		// �¶�����Ѿ���"�����ɱ���Ŀ����ƻ�"��"������ǩ����ͬ"�ϲ���һ���������˵���Ҫ���� by Cassiel_peng
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
		// �������������ݣ�δ���޸ģ�ֱ�ӹر�ʱ�����ֱ�����ʾ, ���ɻ�����ܴ���
		handleOldData();
		// ����
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
		// ��������
		sic.add("Kxnr");
		// �Ƿ��ʽ�ƻ�
		sic.add("sfczjjhfk");

		sic.add("paymentProportion");
		sic.add("costAmount");
		sic.add("grtAmount");
		sic.add("capitalAmount");

		// ��ͬ����
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

		// ��ͬ�ڹ��̿�
		sic.add("lstPrjAllPaidAmt");
		sic.add("lstPrjAllReqAmt");
		sic.add("projectPriceInContract");
		sic.add("projectPriceInContractOri");
		sic.add("prjAllReqAmt");

		// Ԥ����
		sic.add("prjPayEntry.lstAdvanceAllPaid");
		sic.add("prjPayEntry.lstAdvanceAllReq");
		sic.add("prjPayEntry.advance");
		sic.add("prjPayEntry.locAdvance");
		sic.add("prjPayEntry.advanceAllReq");
		sic.add("prjPayEntry.advanceAllPaid");

		// �׹�
		sic.add("lstAMatlAllPaidAmt");
		sic.add("lstAMatlAllReqAmt");
		sic.add("payPartAMatlAmt");
		sic.add("payPartAMatlOriAmt");
		sic.add("payPartAMatlAllReqAmt");

		// ʵ��
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

		// ��Ʊ
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
		// �������뵥���Ӵ洢��λ�ұұ��Է���Ԥ��ϵͳ��ȡ�����ֶ�ֵ by Cassiel_peng 2009-10-5
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

		// �ƻ���Ŀ
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
		// �������뵥��������չ�ֶΣ����ڿͻ��Զ��� by skyiter_wang 2014-12-04
		// ˵����
		// 1���������뵥���״�Ƚ�����
		// 2���ڴ��������˴���д����һЩ��ѯ�ֶΣ�����DEP��չ���ֶ��޷�����չʾ
		// 3������Ԥ����5����չ�ֶΣ�����ͻ��Զ���
		// 4���μ�PayRequestBillEditUI.actionPrintPreview_actionPerformed��
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
	 * description ��ͬʵ����ܴ��ں�ͬ�����
	 * 
	 * @author ����
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
					.showError("��ͬʵ����ܴ��ں�ͬ����ۡ���ͬʵ���� =�ѹرյĸ������뵥��Ӧ�ĸ����ͬ�ڹ��̿�ϼ� + δ�رյĸ������뵥��ͬ�ڹ��̿�ϼơ�");
			SysUtil.abort();
		}
	}

	/**
	 * �ں�ͬ���ս���󣬸������뵥�ύ������ʱ�������ͬʵ����(��������) �� ���θ������뵥��ͬ�ڹ��̿�ڷ������ں�ͬ����۸�����Ӧ��ʾ by
	 * cassiel_peng 2009-12-03
	 */
	private void checkPrjPriceInConSettlePriceForUnClose() throws Exception {
		storeFields();
		// ȡ�øú�ͬ��ʵ���� =�ѹرյĸ������뵥��Ӧ�ĸ����ͬ�ڹ��̿�ϼ� + δ�رյĸ������뵥��ͬ�ڹ��̿�ϼ�
		BigDecimal totalPrjPriceInCon = FDCHelper
				.toBigDecimal(ContractClientUtils.getPayAmt(this.editData
						.getContractId()), 2);
		/**
		 * �ڹر�����ʱ��,�ú�ͬʵ���� =�ѹرյĸ������뵥��Ӧ�ĸ����ͬ�ڹ��̿�ϼ� +
		 * δ�رյĸ������뵥��ͬ�ڹ��̿�ϼƹ�ʽȡ����ʱ����Ҫע�⣺
		 * ���ڵ�ǰ��Ҫ�رյ�����˵�����Ƿ��Ѿ����ɹ���Ӧ�ĸ����Ӧ��ȡ�������Ķ��Ǹ����ͬ�ڹ��̿�
		 * ����Ϊ��Ҫ���Ƿ��رճɹ�֮��ĳЩ�߼��Ѿ���������
		 */
		BigDecimal actualPayAmt = totalPrjPriceInCon;
		BigDecimal payAmt = FDCHelper.ZERO;// ������Ӧ�ĸ���ĺ�ͬ�ڹ��̿�
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
		if (paymentColl != null && paymentColl.size() > 0) {// ���ɹ����
			for (Iterator iter = paymentColl.iterator(); iter.hasNext();) {
				PaymentBillInfo payment = (PaymentBillInfo) iter.next();
				payAmt = FDCHelper.toBigDecimal(FDCHelper.add(payAmt, payment
						.getProjectPriceInContract()));
			}
			actualPayAmt = FDCHelper.toBigDecimal(FDCHelper.add(this.editData
					.getProjectPriceInContract(), FDCHelper.subtract(
					totalPrjPriceInCon, payAmt)));
		}

		BigDecimal conSettPrice = FDCHelper.ZERO;// ��ͬ�����
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
					.showError("��ͬʵ����ܴ��ں�ͬ����ۡ���ͬʵ���� =�ѹرյĸ������뵥��Ӧ�ĸ����ͬ�ڹ��̿�ϼ� + δ�رյĸ������뵥��ͬ�ڹ��̿�ϼơ�");
			SysUtil.abort();
		}

		// ��ͬ�����ս���󣬷��ر�ʱ��ҪУ�飺���ȿ�+�����ܳ�����ͬ�����-���޽� by cassiel_peng 2009-12-08
		FDCSQLBuilder builder = new FDCSQLBuilder();
		if (contractBill.isHasSettled()) {
			BigDecimal settAmtSubtractGuarante = FDCHelper.ZERO;// ��ͬ�����-���޽�
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
			// ���ȿ�+�����(���ѹرյĸ������뵥��Ӧ�ĸ����ͬ�ڹ��̿�ϼ� + δ�رյĸ������뵥��ͬ�ڹ��̿�ϼơ�)
			BigDecimal processSettlementPrice = FDCHelper.ZERO;
			builder.clear();
			builder.appendSql("select sum(��ͬʵ����) as ��ͬʵ����  from  \n ");
			builder.appendSql("( \n ");
			builder
					.appendSql("(select req.FProjectPriceInContract as ��ͬʵ���� from T_Con_PayRequestBill req \n ");
			builder
					.appendSql("inner join t_fdc_paymentType pType on req.fpaymenttype = pType.fid \n ");
			builder.appendSql("and req.FContractId=? and req.FHasClosed=0 \n ");
			builder.addParam(this.editData.getContractId());
			builder.appendSql("and pType.FPayTypeId in (?,?) )\n ");// ��ȥ���޿����Ͳ���
			builder.addParam(PaymentTypeInfo.progressID);
			builder.addParam(PaymentTypeInfo.settlementID);
			builder.appendSql("union all\n ");
			builder
					.appendSql("(select pay.FProjectPriceInContract as ��ͬʵ���� from T_CAS_PaymentBill pay \n  ");
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
						.getBigDecimal("��ͬʵ����"), 2);
			}

			BigDecimal finalProcessSettlementPrice = processSettlementPrice;

			if (paymentColl != null && paymentColl.size() > 0) {// ���ɹ����
				finalProcessSettlementPrice = FDCHelper.toBigDecimal(FDCHelper
						.add(
								FDCHelper.subtract(processSettlementPrice,
										payAmt), this.editData
										.getProjectPriceInContract()));
			}
			if (finalProcessSettlementPrice.compareTo(settAmtSubtractGuarante) == 1) {
				FDCMsgBox.showError("���ȿ�+�����ܳ�����ͬ�����-���޽�");
				SysUtil.abort();
			}
		}

		// ���ر�ʱ��ҪУ�飺��ͬʵ����ܴ��ں�ͬ������� by cassiel_peng 2009-12-08
		BigDecimal latestPrice = FDCHelper.ZERO;// ��ͬ�������
		if (contractBill.isHasSettled()) {// ��������ս��㣬��ͬ�������ȡ�����
			latestPrice = conSettPrice;
		} else {// δ���ս��㣬ȡ��ͬ���+������
			// ��ͬ�ı�����
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
			if (contractBill.isHasSettled() || isControlCost) {// ����ͬ�Ѿ����ս�����������˲���
				// ��
				// �ۼ�������ͬ��������ϸ����
				// ��
				FDCMsgBox
						.showError("��ͬʵ����ܴ��ں�ͬ������ۡ���ͬʵ���� =�ѹرյĸ������뵥��Ӧ�ĸ����ͬ�ڹ��̿�ϼ� + δ�رյĸ������뵥��ͬ�ڹ��̿�ϼơ�");
				SysUtil.abort();
			} else if (!contractBill.isHasSettled() && !isControlCost) {// ����ͬ��δ���ս�����δ���ò���
				// ��
				// �ۼ�������ͬ��������ϸ����
				// ��ʱ
				FDCMsgBox
						.showWarning("��ͬʵ����ܴ��ں�ͬ������ۡ���ͬʵ���� =�ѹرյĸ������뵥��Ӧ�ĸ����ͬ�ڹ��̿�ϼ� + δ�رյĸ������뵥��ͬ�ڹ��̿�ϼơ�");
			}
		}
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		reSetCompleteAmt();
		// ��֤�ڹ������е��ݵ�״̬���ܸı�by renliang ���µ��жϷ�ʽ�档��
		if (getUIContext().get("isFromWorkflow") != null
				&& getUIContext().get("isFromWorkflow").toString().equals(
						"true") && getOprtState().equals(OprtState.EDIT)
				&& actionSave.isVisible()) {

		} else {
			if (editData != null) {// ��һ�α���ʱ��ʼ״̬
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
				MsgBox.showWarning("���θ������뵥�����ʽ�ƻ�����������ύ�����޸ġ�");
				SysUtil.abort();
			}
		} else {
			if (yb.compareTo(zjjhSqje) > 0) {
				MsgBox.showWarning("���θ������뵥�����ʽ�ƻ�������.");
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
		// �����״̬
		checkContractSplitState();
		// ΪɶҪ�����ｫ����״̬��ʾ������Ϊ"�ύ"�أ����������Ϊ"�ύ",ò�ƺ�ͬ�ڹ��̿������� by cassiel_peng
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
	 * ������
	 * 
	 * @param ctx
	 * @param billId
	 * @param model
	 * @throws BOSException
	 * @throws EASBizException
	 * @Author��jian_cao
	 * @CreateTime��2012-8-14
	 */
	private void checkConWorkLoad() throws Exception {

		PayRequestBillInfo bill = editData;
		String companyID = null;
		if (bill.getCurProject().getFullOrgUnit() != null) {
			companyID = bill.getCurProject().getFullOrgUnit().getId()
					.toString();// ȡ������Ŀ������֯
		}
		boolean isSeperate = FDCUtils.getDefaultFDCParamByKey(null, companyID,
				FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT);
		// if (!isSeperate) {
		// return; //modified by ken_liu... ����ʾ������
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
		// ��ͬ����ȷ���Ƿ����ж�
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
			// ��ͬ�ڹ��̿�Ƿ�����ۼ�ȷ���깤������
			// �˴�֮ǰ�Ƚ��ң�����ǰ�Ķ�ע�ͣ�������һ��ͳһ�ж�
			// ���У������Ԥ�������͵ĸ������뵥������У��

			// ��������������ΪԤ��������뵥 add 2012-08-31 ��������
			if (bill.getPaymentType() != null
					&& bill.getPaymentType().getName().indexOf(YFK) < 0
					&& PaymentTypeInfo.progressID.equals(bill.getPaymentType()
							.getPayType().getId().toString())) {
				BigDecimal payAmt = FDCHelper.ZERO;
				FDCSQLBuilder builder = new FDCSQLBuilder();
				// modified by ken_liu...
				// ���ȿ��ۼ������ȡ�����ú�ͬ���ύ״̬�������С����������ȿ�����뵥�ϡ���������_���ҡ�֮�ͣ�
				// �������븶�����������ú��ۼ��깤ȷ�Ͻ��ȡ����ȡ�ú�ͬ������������������ȷ�ϵ����֮�ͣ�
				// �������븶�������������ã�ȡ�ú�ͬ���ύ״̬�������С����������ȿ�����뵥�ϡ������깤��������֮�ͣ�
				builder
						.appendSql("select sum(t.famount) as famount,sum(t.workload) as workload from \n ");
				builder.appendSql("( \n ");
				builder
						.appendSql("(select pay.FCompletePrjAmt as workload, pay.FProjectPriceInContract as famount from T_Con_PayRequestBill as pay ");
				builder
						.appendSql("left join T_FDC_PaymentType as ptype on ptype.fid=pay.fPaymentType ");
				if (bill.getId() != null) {
					builder
							.appendSql("where pay.FHasClosed=0 and pay.FContractId=?  and ptype.fname_l2 ='���ȿ�'  ");
					builder.appendSql("and pay.fid !='"
							+ bill.getId().toString() + "' ");
				} else {
					builder
							.appendSql("where pay.FHasClosed=0 and pay.FContractId=?  and ptype.fname_l2 ='���ȿ�' ");
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
						.appendSql("inner join T_FDC_PaymentType as ptype on ptype.fid=req.FPaymentType and ptype.fname_l2='���ȿ�' ");
				builder
						.appendSql("and req.FState in ('2SUBMITTED', '3AUDITTING','4AUDITTED') )");

				builder.appendSql(") t \n ");// sql server �ӱ���
				builder.addParam(bill.getContractId());
				builder.addParam(bill.getContractId());
				IRowSet rs = builder.executeQuery();

				BigDecimal workLoad = FDCHelper.ZERO;
				try {
					if (rs.next()) {
						// ��ǰ���еĺ�ͬ�ڹ��̿�
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
				// ��ǰ������ʱ���ѵ�ǰ���ݽ��Ҳ����һ���ж�(���ڲ���Ԥ���ȡ��λ�Һ�ȡ��ͬ�ڹ��̿�һ��)

				/* modified by zhaoqin for R140227-0281 on 2014/03/20 start */
				// Ӧȡ"�������뱾��"
				// payAmt = FDCHelper.add(payAmt,
				// FDCHelper.toBigDecimal(getDetailTable().getCell(rowIndex,
				// columnIndex).getValue()));
				payAmt = FDCHelper.add(payAmt, FDCHelper
						.toBigDecimal(getDetailTable().getCell(rowIndex,
								columnIndex + 1).getValue()));
				/* modified by zhaoqin for R140227-0281 on 2014/03/20 end */

				workLoad = FDCHelper.add(workLoad, bill.getCompletePrjAmt());
				// }
				// ��ǰ��ϼƵĽ�����ۼ�ȷ�Ϲ������Ƚϣ������������ύ������

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
					 * FDCMsgBox.showConfirm2("���ȿ������ۼƽ����깤ȷ�ϵ��ۼƽ���ȷ���Ƿ�����˲���?"
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
							.showWarning(this, "���ȿ��ۼ��������ۼ��깤�������������ύ/������");
					SysUtil.abort();
				}
			}

		}
	}

	private SelectorItemCollection getSic() {
		// �˹���Ϊ��ϸ��Ϣ����
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
	 * ����ǰ��editData��¡һ�ݣ�����ӡ���ӡԤ��ʹ�á�
	 * ��Щ�ֶν������Ѿ����ˣ�ֱ�ӷŵ�editDataForPrint�У�Query���Ѿ�ָ������Ӧ���ֶ�
	 * 
	 * @return editDataForPrint ���״������
	 * @Author��owen_wen
	 * @CreateTime��2012-9-27
	 */
	private PayRequestBillInfo cloneEditDataForPrint() {
		PayRequestBillInfo editDataForPrint = (PayRequestBillInfo) editData
				.clone();
		editDataForPrint.setAllInvoiceAmt(this.txtAllInvoiceAmt
				.getBigDecimalValue()); // Ϊ�����״���ʾ �ġ��ۼƷ�Ʊ�������һ�¡�
		editDataForPrint.setPayedAmt(totalPayAmtByReqId); // Ϊ�����״���ʾ�ġ������뵥�Ѹ���
															// �����һ��
		editDataForPrint.setLatestPrice((BigDecimal) ((ICell) bindCellMap
				.get(PayRequestBillContants.LATESTPRICE)).getValue());
		editDataForPrint.put(PayRequestBillContants.CURREQPERCENT,
				((ICell) bindCellMap.get(PayRequestBillContants.CURREQPERCENT))
						.getValue());
		editDataForPrint.put(PayRequestBillContants.ALLREQPERCENT,
				((ICell) bindCellMap.get(PayRequestBillContants.ALLREQPERCENT))
						.getValue());

		/* modified by zhaoqin for R131202-0278 start */
		// ���̸���������ϵ�ĳЩ�ֶε�ȡֵ��ͨ�����������,�������Щ�ֶ����¸�ֵ
		Iterator iter = bindCellMap.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			editDataForPrint
					.put(key, ((ICell) bindCellMap.get(key)).getValue());
		}

		// ��ͬ�ڹ��̿�_�����ϴ��ۼ�ʵ��_ԭ��
		editDataForPrint.put("lstPrjAllPaidOriAmt", kdtEntrys.getCell(5, 2)
				.getValue());
		// ��ͬ�ڹ��̿�_�����ϴ��ۼ�ʵ��_����
		editDataForPrint.put("lstPrjAllPaidAmt", kdtEntrys.getCell(5, 3)
				.getValue());
		// ��ͬ�ڹ��̿�_���������ۼ�ʵ��_ԭ��
		editDataForPrint.put("prjAllPaidOriAmt", kdtEntrys.getCell(5, 10)
				.getValue());
		// ��ͬ�ڹ��̿�_���������ۼ�ʵ��_����
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
		// Add by zhiyuan_tang 2010/07/30 �����ڲ鿴������޸ģ���������ԭ�ҿ��õ�BUG
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

		// ���ֱ������,���»��һ�³�ʼ����
		// fetchInitData();

		super.actionAddNew_actionPerformed(e);
		// ���ñ�λ��
		prmtcurrency.setValue(null);
		prmtcurrency.setValue(currency);
		prmtrealSupplier.setValue(null);
		prmtrealSupplier.setValue(realSupplier);

		// ��λ�Ҵ���
		CompanyOrgUnitInfo currentFIUnit = SysContext.getSysContext()
				.getCurrentFIUnit();
		CurrencyInfo baseCurrency = currentFIUnit.getBaseCurrency();
		BOSUuid srcid = currency.getId();
		if (baseCurrency != null) {
			if (srcid.equals(baseCurrency.getId())) {
				/*
				 * if (exchangeRate instanceof
				 * BigDecimal&&((BigDecimal)exchangeRate).intValue()!=1) {
				 * FDCMsgBox.showWarning(this,"��ѡ����Ǳ�λ�ң����ǻ��ʲ�����1"); }
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
			FDCMsgBox.showInfo("��ǰ�������뵥���������ı���ͬ�����ı���ͬ�������뵥����ֱ�����ɣ��������ı���ͬ�Զ�����");
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
		// ���ֽ���ʱ�������ֵû���������ύ
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

		// Add by zhiyuan_tang 2010/07/30 �����ڲ鿴������޸ģ���������ԭ�ҿ��õ�BUG
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

		// R110530-0639: �鿴���޸ģ������ʺ�ֵ�������
		txtrecAccount.setText(editData.getRecAccount());
		/*
		 * if(editData.getState()!=null&&!editData.getState().equals(FDCBillStateEnum
		 * .SAVED)){ btnSave.setEnabled(false); }
		 */
		setEditState();
		// ���Ϊ�ݹ�������ʱ������¼�����ֶν���Ʊ����¼��(��Ӧ�ĺ�ͬ�ڹ��̿�����������¼��)
		Object obj = prmtPayment.getValue();
		if (obj != null && obj instanceof PaymentTypeInfo) {
			String tempID = PaymentTypeInfo.tempID;// �ݹ���
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

		// ���ȿ�ʱ,�ǹ������ͬ�ĸ������뵥�������깤�������ͽ��ȿ�����Ӧ�û��ԣ�����ȥ�������߼�������������= �����깤������*
		// ���ȸ������%����

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
		// �����¼ԭ��
		addOrgPriceForEntryTable(kdtEntrys, bindCellMap, contractBill);
	}

	private void setTxtEnable_Old(PaymentTypeInfo type) throws EASBizException,
			BOSException, Exception {
		if (type != null) {
			/*
			 * 1.����޿�ʱ�����ȿ�֧�������������깤������Ĭ��Ϊ�㡢�ɱ༭��
			 * �������˳����£������޿�ʱ�����ȿ�֧�������������깤������Ĭ��Ϊ�㡢���ɱ༭��
			 */
			if (type.getPayType().getId().toString().equals(
					PaymentTypeInfo.keepID)) {
				txtpaymentProportion.setEditable(false);
				txtpaymentProportion.setRequired(false);
				// ���и�Ϊ������txtpaymentProportion��dataChange�¼�����Ϊ�����Ļ���
				// ����0����ۼ��깤�ͱ��ں�ͬ�ڹ��̿�
				// Added by Owen_wen 2013-01-05
				txtpaymentProportion.setValue(FDCHelper.ZERO, false);
				txtcompletePrjAmt.setEditable(false);
				txtcompletePrjAmt.setValue(FDCHelper.ZERO, false); // ͬ������
				txtcompletePrjAmt.setRequired(false);
			}

			if (type.getPayType().getId().toString().equals(
					PaymentTypeInfo.settlementID)) {
				// ��ģʽ����ͬ���㣬�깤��������޸�
				if (contractBill != null && contractBill.isHasSettled()
						&& isSimpleFinancial) {
					txtcompletePrjAmt.setEnabled(false);
					// ���и�Ϊ������txtpaymentProportion��dataChange�¼�����Ϊ�����Ļ���
					// ����0����ۼ��깤�ͱ��ں�ͬ�ڹ��̿�
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
	 * ������ӵĹ�������ť
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
		// auditAndOpenPayment()����audit()ȡ�ø����BOSUudi eric_wang 2010.05.19
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
		actionEdit.setEnabled(false);// �����޸�
		actionSubmit.setEnabled(false);
		actionRemove.setEnabled(false);
		// �򿪸�� eric_wang 2010.05.19
		try {
			if (isOpenPaymentBillEditUI()) {
				UIContext uiContext = new UIContext(this);
				if (billId != null) {
					// ȡ�������� by Eric_Wang 2010.05.21
					// int result = FDCMsgBox.showConfirm2New(null,
					// "�Ѿ����ɶ�Ӧ�ĸ�����Ƿ�򿪸����");
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
	 * ���Ӳ���FDC801_ISOPENPAYMENTEDITUI�Ƿ����ж� eric_wang 2010.05.19
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
			FDCMsgBox.showWarning("�������з�������������״̬�ɹ���");
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
			// �����ύ��
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
                if(type.getName().toString().equals("Ԥ����"))
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
                originalAmount.appendFilterItem("sourceBillId", null);// add by wp  -- ��У��ӷְ���ͬ�����ĸ�������
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
                originalAmount.appendFilterItem("sourceBillId", null);// add by wp  -- ��У��ӷְ���ͬ�����ĸ�������
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
                        myfilter.appendFilterItem("sourceBillId", null);// add by wp  -- ��У��ӷְ���ͬ�����ĸ�������
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
                    myfilter.appendFilterItem("sourceBillId", null);// add by wp  -- ��У��ӷְ���ͬ�����ĸ�������
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
	     filter.getFilterItems().add(new FilterItemInfo("sourceBillId", null));// add by wp  -- ��У��ӷְ���ͬ�����ĸ�������
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
	 * description �����������
	 * 
	 */
	public void actionAdjustDeduct_actionPerformed(ActionEvent e)
			throws Exception {
		if (OprtState.ADDNEW.equals(getOprtState())) {
			// �뱣�澯��
			FDCMsgBox.showWarning(getRes("beforeAdjustDeduct"));
			SysUtil.abort();
		}
		showSelectDeductList(e);
	}

	/**
	 * description �������뵥�رղ���
	 */
	public void actionClose_actionPerformed(ActionEvent e) throws Exception {
		if (OprtState.ADDNEW.equals(getOprtState())) {
			// �뱣�澯��
			FDCMsgBox.showWarning(getRes("beforeClose"));
			SysUtil.abort();
		}
		if (!editData.getState().equals(FDCBillStateEnum.AUDITTED)) {
			FDCMsgBox.showError("��ǰ���ݵ�״̬����ִ�йرղ�����");
			return;
		}

		super.actionClose_actionPerformed(e);
		if (editData != null && editData.getId() != null
				&& editData.isHasClosed()) {
			FDCMsgBox.showWarning(this, "�������뵥�Ѿ��رգ�����Ҫ�ٹر�");
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
			// ���깤�Զ�100%,�ҷǹ�����ģʽʱ,����ʵ�����������������ʱ by hpw 2009-12-14
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder
					.appendSql("update t_con_payrequestbill set fcompleteprjamt=(select sum(flocalamount) from t_cas_paymentbill where t_con_payrequestbill.fid=ffdcpayreqid) where fid=? ");
			builder.addParam(editData.getId().toString());
			builder.execute();
		}

		FDCMsgBox.showInfo(getRes("closeSuccess"));
	}

	/**
	 * description ���رղ���
	 * 
	 * @author ����
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
			FDCMsgBox.showWarning(this, "�������뵥δ�رգ�����Ҫ���ر�");
			SysUtil.abort();
		}
		if (!isSeparate && contractBill != null) {
			checkIsUnClose();
			checkPrjPriceInConSettlePriceForUnClose();
		}
		// ���ر����뵥ʱ������ۼ�ʵ����+δ����������˸�����ʾ����ʱ���������رգ�������ʾ��
		if (this.editData.getContractId() == null) {
			return;
		}
		String contractId = this.editData.getContractId();
		BigDecimal totalpayAmountLocal = FDCHelper.ZERO;// �ۼƽ��=ʵ����+����δ����
		BigDecimal payAmtLocal = FDCHelper.ZERO;// ʵ����
		BigDecimal noPayAmtLocal = FDCHelper.ZERO;// ����δ����

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
			// δ�رյ���Ҫ�������ܶ��ȥ������Ӧ������Ѹ����ܶ�,��Ҫע��һ���������:
			// �Ѿ��رյ��������Ǳ������ŵ�����Ҫ�����رղ���ҲҪ��˴���
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
							&& payment.getBillStatus() == BillStatusEnum.PAYED) { // ���Ҹø���Ѿ�����
						temp = FDCHelper.add(temp, payment.getAmount());
					}
				}
				temp1 = FDCHelper.subtract(totalThisPayReq, temp);
				noPayAmtLocal = FDCHelper.add(noPayAmtLocal, temp1);
			} else {// �ѹر�

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
		// ��ͬ�������
		map = FDCUtils.getLastAmt_Batch(null, new String[] { this.editData
				.getContractId() });
		if (map != null && map.size() == 1) {
			conLastestPrice = (BigDecimal) map.get(this.editData
					.getContractId());
		}
		payRate = FDCHelper.divide(FDCHelper.multiply(conLastestPrice,
				payPercForWarn), FDCHelper.ONE_HUNDRED);

		if (totalpayAmountLocal.compareTo(payRate) > 0) {
			String str = "���ң���ǰ���ݺ�ͬ�µ��ۼ�ʵ����+δ�������뵥�����˸�����ʾ����:";
			str = str + "\n�ۼƽ��:" + totalpayAmountLocal + " ����,ʵ������"
					+ FDCHelper.toBigDecimal(payAmtLocal, 2) + "  ����δ����:"
					+ FDCHelper.toBigDecimal(noPayAmtLocal, 2);
			str = str + "\n������ʾ������" + payRate + "(" + conLastestPrice + "*"
					+ payPercForWarn + "%)";
			if ("0".equals(allPaidMoreThanConPrice())) {// �ϸ����
				FDCMsgBox.showDetailAndOK(this, "����������ʾ����,��鿴��ϸ��Ϣ", str, 1);
				SysUtil.abort();
			} else if ("1".equals(allPaidMoreThanConPrice())) {// ��ʾ����
				FDCMsgBox.showDetailAndOK(this, "����������ʾ����,��鿴��ϸ��Ϣ", str, 1);
			}
		}

		BigDecimal amount = FDCHelper.toBigDecimal(editData.getAmount());
		BigDecimal paidAmount = FDCHelper.ZERO;
		PayRequestBillEntryInfo entryInfo;
		for (Iterator iter = editData.getEntrys().iterator(); iter.hasNext();) {
			entryInfo = (PayRequestBillEntryInfo) iter.next();
			paidAmount = paidAmount.add(entryInfo.getAmount());
		}

		// ���ı���ͬ����¼�븺���������θ���������Ƚ�ʱ�����þ���ֵ�Ƚϣ���Ӱ��������ͬ���߼�
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
			// ���깤�Զ�100%,�ҷǹ�����ģʽʱ,����ʵ�����������������ʱ by hpw 2009-12-14
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder
					.appendSql("update t_con_payrequestbill set fcompleteprjamt=fprojectpriceincontract where fid=? ");
			builder.addParam(editData.getId().toString());
			builder.execute();
		}
		FDCMsgBox.showInfo(getRes("unCloseSuccess"));
	}

	/**
	 * description �²�
	 * 
	 * @author ����
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * 
	 * @version EAS 7.0
	 * @see com.kingdee.eas.framework.client.CoreBillEditUI#actionTraceDown_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception {
		// ȡ���ݿ��ڵ���������
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
	 * description �ϲ�
	 * 
	 * @author ����
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * 
	 * @version EAS 7.0
	 * @see com.kingdee.eas.framework.client.CoreBillEditUI#actionTraceUp_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception {
		// �ϲ��ͬ����ƻ�
		// super.actionTraceUp_actionPerformed(e);
		if (editData != null && editData.getId() != null
				&& PayReqUtils.isContractBill(editData.getContractId())) {
			String contractId = editData.getContractId();
			ContractPayPlanEditUI.showEditUI(this, contractId, "VIEW");
		} else {
			FDCMsgBox.showWarning(this, "û�к�ͬ�ƻ�");
		}
	}

	/**
	 * ��ʾ���������б����
	 * 
	 * @param e
	 * @throws Exception
	 */
	private void showSelectDeductList(ActionEvent e) throws Exception {

		boolean canAdjust = checkCanSubmit();
		String state = canAdjust ? getOprtState() : OprtState.VIEW;

		// uiWindow=null;//��ʱÿ�ζ�ʵ��һ��UIWindow
		deductUIwindow = null; // ÿ�ζ�ʵ��һ��UIWindow,����Ỻ��UI�����������ݡ����ݵ���ȷ��Զ�����ܸ���Ҫ by
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
			 * ���˳�DeductOfPayReqBillEntry�ں��е���ͬ��ͬ�Ҳ��ڱ����뵥�Ŀۿ, �ѷ���DeductListUI��
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
	 * ��÷�¼
	 */
	protected KDTable getDetailTable() {
		return this.kdtEntrys;
	}

	/**
	 * ����������¼����
	 */
	protected IObjectValue createNewDetailData(KDTable table) {

		return new com.kingdee.eas.fdc.contract.PayRequestBillEntryInfo();
	}

	/**
	 * ������������
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
		// ��λ��
		objectValue.setCurrency(baseCurrency);

		String contractBillId = (String) getUIContext().get("contractBillId");
		if (contractBillId == null) {
			contractBillId = this.editData.getContractId();
		}
		if (contractBillId != null && contractBillId.trim().length() > 1) {
			// ���ı�
			if (PayReqUtils.isConWithoutTxt(contractBillId)) {
				createNewData_WithoutTextContract(objectValue, contractBillId);
			} else {
				// ���ı�
				if (BOSUuid.read(contractBillId).getType().equals(
						contractBill.getBOSType())) {
					createNewdata_Contract(objectValue, contractBillId);
				}
			}
		}

		// ���깤���������
		objectValue.setCostAmount(FDCConstants.ZERO);

		// ��֯
		objectValue.setOrgUnit(contractBill.getOrgUnit());
		// editData.setCU(curProject.getCU());

		objectValue.setBookedDate(bookedDate);
		objectValue.setPeriod(curPeriod);

		// �Ƿ���Ҫ�ύ������ز����ǿ�Ʋ��������ϵͳΪ���ǡ��Ͳ���Ҫ��
		if (isNotEnterCAS)
			objectValue.setIsPay(false);
		else
			objectValue.setIsPay(true);

		if (isAutoComplete) {// ���뵥���ȿ������Զ�Ϊ100%
			objectValue.setPaymentProportion(FDCConstants.ONE_HUNDRED);
		}
		// ��Ʊ����
		objectValue.setInvoiceDate(serverDate);

		// �ʽ�ƻ�������
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
		// �ڼ����
		sb.append("and ther.FBizDate= { ts '").append(year + "-" + m + "-01")
				.append("'} ");
		// //�ڼ����
		sb.append("and try.FMONTH='").append(month + 1).append("' ");
		// ��Ŀid
		sb.append("and ject.fid='").append(id).append("' ");
		// ��˾ID
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
	 * description �������ı���ͬ�ĸ������뵥
	 * 
	 * @author ����
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
			objectValue.setAmount(withoutTextInfo.getAmount());// ԭ�ҽ��
			objectValue.setContractPrice(withoutTextInfo.getAmount());//
			objectValue.setContractName(withoutTextInfo.getName());// ��ͬ��
			objectValue.setContractNo(withoutTextInfo.getNumber());
			// ���ı���ͬ����
			// kdtEntrys.getCell(1,1).setValue(withoutTextInfo.getBillName());
			// ���ı���ͬ���
			// kdtEntrys.getCell(0,5).setValue(withoutTextInfo.getAmount());
			// �������ı���ͬ�տλ
			objectValue.setSupplier((SupplierFactory.getRemoteInstance()
					.getSupplierInfo(new ObjectUuidPK((withoutTextInfo
							.getReceiveUnit().getId())))));
			objectValue.setRealSupplier((SupplierFactory.getRemoteInstance()
					.getSupplierInfo(new ObjectUuidPK((withoutTextInfo
							.getReceiveUnit().getId())))));
			// �������ı���ͬ������Ŀ
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
	 * description ������ͬ��Ӧ�ĸ������뵥
	 * 
	 * @author ����
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * @param contractBillId
	 *            ��ͬID�� objectValue �������뵥����
	 * @return
	 * 
	 * @version EAS7.0
	 * @see
	 */
	private void createNewdata_Contract(PayRequestBillInfo objectValue,
			String contractBillId) {
		try {
			// ��ͬ��
			objectValue.setContractId(contractBillId);
			objectValue.setSource(contractBill.getBOSType().toString());
			objectValue.setContractNo(contractBill.getNumber());
			objectValue.setContractName(contractBill.getName());
			objectValue.setContractPrice(contractBill.getAmount());
			objectValue.setCurrency(contractBill.getCurrency());

			objectValue.setExchangeRate(contractBill.getExRate());
			objectValue.setUseDepartment(contractBill.getRespDept());

			// �������
			if (!contractBill.isHasSettled()) {
				objectValue.setPaymentProportion(contractBill.getPayScale());
			} else {
				if (!isSimpleFinancial) {
					objectValue.setPaymentProportion(new BigDecimal("100"));
					objectValue.setCompletePrjAmt(contractBill.getSettleAmt());
					txtcompletePrjAmt.setValue(contractBill.getSettleAmt());
				}
				objectValue.setCostAmount(contractBill.getSettleAmt());
				// �Ѿ�����ĸ������
				objectValue.setGrtAmount(payScale);
				txtGrtAmount.setValue(payScale);
			}

			// �����ۼ�ʵ��
			objectValue
					.setLstPrjAllPaidAmt(contractBill.getPrjPriceInConPaid());
			objectValue.setLstAddPrjAllPaidAmt(contractBill.getAddPrjAmtPaid());
			objectValue.setLstAMatlAllPaidAmt(contractBill
					.getPaidPartAMatlAmt());

			// ������ͬ�ҷ�
			objectValue.setSupplier(contractBill.getPartB());
			objectValue.setRealSupplier(contractBill.getPartB());
			CurProjectInfo curProject = contractBill.getCurProject();
			// ���ñ������ȡ������ ���ڷ����
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

			// ����ʵ���տλ���õ������ʻ��͸�������
			if (contractBill.getPartB() != null) {
				String supperid = contractBill.getPartB().getId().toString();
				PayReqUtils.fillBank(objectValue, supperid, curProject.getCU()
						.getId().toString());
			}

			objectValue.setCurProject(curProject);
			objectValue.setCU(contractBill.getCU());

			// ��������ѡ�񲢱�������ȡ�������ݿ�һ�£�ȥ��ѡ��,�ύ����ʱ���������ݿ����ݲ�һ�£�Ӧ������ȡһ�� by hpw
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
					// Ӧ�ô��ɱ����ģ��뵥��ʵ�����ҵ����֯Ҫ��Ӧ
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
		if (isIncorporation && ((FDCBillInfo) editData).getPeriod() == null) {// ���óɱ��ɱ��½����ڼ�Ϊ�յ�ʱ��
																				// ��ʾ
			FDCMsgBox.showWarning(this, "���óɱ��½��ڼ䲻��Ϊ�գ����ڻ�������ά���ڼ������ѡ��ҵ������");
			SysUtil.abort();
		}
		return super.checkCanSubmit();
	}

	/**
	 * ����������Ƿ��С���Ŀ�¶ȸ���ƻ���������Ŀ�¶ȸ���ƻ����Ƿ��Ѿ�����������ͬ������̬�ɱ�ʱ������Ҫ���
	 * 
	 * @Author��keyan_zhao
	 * @CreateTime��2012-11-8
	 */
	 
	protected void checkFDCProDep() throws EASBizException, BOSException {
		if ("������".equals(CONTROLPAYREQUEST) || !this.isCostSplitContract) {// ������̬�ɱ��Ĳ���ҪУ��
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
					if ("��ʾ����".equals(CONTROLPAYREQUEST)) {
						int result = FDCMsgBox.showConfirm2(this,
								"���µ���Ŀ�¶ȸ���ƻ�δ�������Ƿ������");
						if (result != FDCMsgBox.OK) {
							SysUtil.abort();
						}
					} else {
						FDCMsgBox.showWarning("����Ŀ�ġ���Ŀ�¶ȸ���ƻ���δ����������������");
						abort();
					}
				}
			}
		}
		// else {
		//
		// if ("��ʾ����".equals(CONTROLPAYREQUEST)) {
		//				
		// int result = FDCMsgBox.showConfirm2(this, "����Ŀδ������Ŀ�¶ȸ���ƻ������Ƿ������");
		// if (result != FDCMsgBox.OK) {
		// SysUtil.abort();
		// }
		//
		//					
		// } else {
		// FDCMsgBox.showWarning("����Ŀδ������Ŀ�¶ȸ���ƻ������������ƻ���");
		// abort();
		// }
		// }
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		checkFDCProDep();
		/*
		 * //�������͵Ŀ��� 1. �����ͬ����֮���������������ĸ������뵥 2. ���˽����֮����ܸ����޿�ѿ��ƣ� 3.
		 * ���޿��ܽ��ܴ��ڽ��㵥�ϵ��ʱ���
		 */

		/*
		 * if(e.getSource()!=btnSubmit){
		 * if(editData.getNumber()==null||editData.getNumber().length()<1){
		 * FDCMsgBox.showWarning(this, getRes("NullNumber")); SysUtil.abort();
		 * }else{ return; } }
		 */
		/*
		 * ���ԭ�ҽ���뵥Ԫ���ڵķ�����ʵ������Ƿ�һ��
		 */
		if (!isSaveAction()) {
			Object cell = bindCellMap.get(PayRequestBillContants.CURPAID);
			if (cell instanceof ICell) {
				Object value = ((ICell) cell).getValue();
				if (value != null) {
					try {
						BigDecimal cellAmount = FDCHelper.toBigDecimal(value);
						BigDecimal amount = txtAmount.getBigDecimalValue(); // ԭ�ҽ��
						/*
						 * if (cellAmount.doubleValue() < 0 || amount == null ||
						 * amount.doubleValue() < 0 || (cellAmount.doubleValue()
						 * > 0 && cellAmount .compareTo(amount) != 0)) {
						 * FDCMsgBox.showWarning(this, getRes("verifyAmount"));
						 * SysUtil.abort(); }
						 */
						// ֧�ָ���
						if (amount == null
								|| (cellAmount.compareTo(amount) != 0)) {
							FDCMsgBox.showWarning(this,
									"ԭ�ҽ���뵥Ԫ���ڵķ�����ʵ�������ϣ�����󱣴棡");
							SysUtil.abort();
						}
						BigDecimal completePrj = txtcompletePrjAmt
								.getBigDecimalValue(); // �����깤������
						Object obj = prmtPayment.getValue(); // ��������
						PaymentTypeInfo type = null;
						if (obj != null && obj instanceof PaymentTypeInfo) {
							type = (PaymentTypeInfo) obj;
						}
						if (completePrj == null) {
							completePrj = FDCHelper.ZERO;
						}

						if (!isSimpleInvoice) {// �Ƿ�ƱУ��(��ģʽ����Ʊ)
							if (FDCHelper.ZERO.compareTo(completePrj) == 0
									&& FDCHelper.ZERO.compareTo(amount) == 0) {
								String msg = "���깤��������ʵ������ͬʱΪ0��";
								if (isSimpleFinancial
										&& type != null
										&& !type.getPayType().getId()
												.toString().equals(
														PaymentTypeInfo.tempID)) {
									// �ݹ���Ļ������������ʾ by cassiel_peng 2010-03-23
									// msg = "ʵ������Ϊ0!";
								}
								if ((txtcompletePrjAmt.isRequired() && contcompletePrjAmt
										.isVisible())
										&& type != null
										&& !type.getPayType().getId()
												.toString().equals(
														PaymentTypeInfo.tempID)) {
									// �ݹ���Ļ������������ʾ"���깤��������ʵ������ͬʱΪ0��" by
									// cassiel_peng 2010-03-26
									if (FDCHelper.isZero(txtInvoiceAmt
											.getBigDecimalValue())) {// ��ƱҲΪ0ʱ��ʾbyhpw
										FDCMsgBox.showWarning(this, msg);
										SysUtil.abort();
									}
								}
							}
						}

						if (FDCHelper.ZERO.compareTo(amount) == 0) {
							String msg = null;
							// Ԥ����,��ƱΪ��ʱУ��
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
									msg = "ʵ������Ԥ�����ͬʱΪ 0!";
									FDCMsgBox.showWarning(this, msg);
									SysUtil.abort();
								}

							} else if ((isInvoiceRequired || invoiceMgr)
									&& FDCHelper.ZERO.compareTo(FDCHelper
											.toBigDecimal(txtInvoiceAmt
													.getBigDecimalValue())) == 0) {
								// R130122-0418
								// �������뵥�пۿ�������Ϊ�㣬��ƱΪ�㣬������ҲΪ�㣬�����ύ
								// FDCMsgBox.showWarning(this,
								// "��Ʊ�����ԭ�ҽ���ͬʱΪ0���������ύ!");
								// SysUtil.abort();
							} else if (!(isInvoiceRequired || invoiceMgr)
									&& (FDCHelper.ZERO.compareTo(FDCHelper
											.toBigDecimal(txtInvoiceAmt
													.getBigDecimalValue())) == 0)) {// �Ƿ�ƱΪ0�����ύ
								if (type != null
										&& !type.getPayType().getId()
												.toString().equals(
														PaymentTypeInfo.tempID)) {
									// FDCMsgBox.showWarning(this, "ʵ������Ϊ0!");
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

			// ��Ʊ�Ƿ��¼
			if (isInvoiceRequired) {
				boolean isNotInput = false;
				if (txtInvoiceAmt.getBigDecimalValue() == null) {
					// Ϊ��ʱ�ɲ�¼
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
					FDCMsgBox.showWarning(this, "��Ʊ���뷢Ʊ������¼��!");
					SysUtil.abort();
				}
			}
		}

		BigDecimal lastestPrice = FDCHelper.toBigDecimal(editData
				.getLatestPrice(), 2);
		// if (txtpaymentProportion.isRequired() &&
		// txtcompletePrjAmt.isRequired()) { //�����깤�������ͽ��ȿ�������¼ʱ
		BigDecimal propAmt = txtpaymentProportion.getBigDecimalValue();
		BigDecimal completeAmt = FDCHelper.toBigDecimal(txtcompletePrjAmt
				.getBigDecimalValue(), 2);
		if (propAmt != null) {
			if (propAmt.compareTo(FDCHelper.ZERO) <= 0
					|| propAmt.compareTo(FDCHelper.ONE_HUNDRED) > 0) {
				// FDCMsgBox.showError(this, "��������������0,С�ڵ���100%");
				// SysUtil.abort();
				// } else if (FDCHelper.toBigDecimal(completeAmt).signum()
				// == 0) {
				// FDCMsgBox.showError(this, "���깤�������������0");
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
				// FDCMsgBox.showError(this, "���������ԭ�ҽ��/���깤������ *100% ��ϵ������");
				// SysUtil.abort();
				// }
				Object amount = ((ICell) bindCellMap
						.get(PayRequestBillContants.PROJECTPRICEINCONTRACT))
						.getValue();
				if (amount == null) {
					FDCMsgBox.showError(this, "���������Ϊ�գ�");
					SysUtil.abort();
				}
				if (isSaveAction()) {
					if (isSimpleFinancial) { // ��ģʽһ�廯
						if (FDCHelper.toBigDecimal(amount, 2).compareTo(
								lastestPrice) > 0) {
							int ok = FDCMsgBox.showConfirm2(this,
									"ʵ�������ں�ͬ�������,�Ƿ񱣴棿");
							if (ok != FDCMsgBox.OK) {
								SysUtil.abort();
							}
						}
					}
				}
				if (null == prmtPayment.getValue()) {
					FDCMsgBox.showWarning(this, "�������Ͳ���Ϊ��");
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
					// �����ʱ���ж��������깤�����ܴ���������ۡ�
					if (completeAmt.compareTo(lastestPrice) > 0) {
						FDCMsgBox.showWarning(this,
								"��ͬ�¸������뵥���ۼ����깤���������ܴ��ں�ͬ������ۡ�");
						SysUtil.abort();
					}
				} else {
					if (completeAmt.compareTo(lastestPrice) > 0) {
						/*
						 * modified by zhaoqin for R130922-0254 on 2013/11/26
						 * start
						 */
						// int ok = FDCMsgBox.showConfirm2(this,
						// "���깤�����������ں�ͬ�������,�Ƿ񱣴棿");
						int ok = FDCMsgBox.showConfirm2(this,
								"�ۼ����깤�����������ں�ͬ�������,�Ƿ񱣴棿");
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

		if (isRealizedZeroCtrl) { // ��ʵ�ֲ�ֵΪ0ʱֻ��ѡ��Ԥ����
			PaymentTypeInfo type = (PaymentTypeInfo) prmtPayment.getValue();
			if (FDCHelper.isNullZero(txtTotalSettlePrice.getBigDecimalValue())
					&& type.getName() != null && !type.getName().equals(YFK)) {
				FDCMsgBox.showError(prmtPayment, "��ʵ�ֲ�ֵΪ0ֻ����ѡ��\"Ԥ����\"��");
				SysUtil.abort();
			}
		}

		// if (FDCHelper.ZERO.compareTo(FDCHelper.toBigDecimal(txtAllInvoiceAmt.
		// getBigDecimalValue())) == 1) {
		// FDCMsgBox.showError(this, "�ۼƷ�Ʊ����С���㣡");
		// SysUtil.abort();
		// }
		// if
		// (FDCHelper.toBigDecimal(txtAllInvoiceAmt.getBigDecimalValue()).setScale
		// (2, BigDecimal.ROUND_HALF_UP).compareTo(lastestPrice) == 1) {
		// if (isOverrun) {
		// int ok = FDCMsgBox.showConfirm2(this, "�ۼƷ�Ʊ�����ں�ͬ������ۣ��Ƿ��ύ?");
		// if (ok != FDCMsgBox.OK) {
		// SysUtil.abort();
		// }
		// } else {
		// FDCMsgBox.showWarning(this, "�ۼƷ�Ʊ���ܳ�����ͬ������ۣ�");
		// SysUtil.abort();
		// }
		// }
		BigDecimal invoiceOriAmt = FDCHelper.ZERO;
		if (null != txtInvoiceOriAmt.getBigDecimalValue()) {
			invoiceOriAmt = txtInvoiceOriAmt.getBigDecimalValue();
		}
		// �ۼƷ�Ʊ���ԭ��
		BigDecimal invoiceOriAmtSum = allInvoiceOriAmt.add(FDCHelper
				.toBigDecimal(invoiceOriAmt));
		if (FDCHelper.ZERO.compareTo(FDCHelper.toBigDecimal(invoiceOriAmtSum)) == 1) {
			FDCMsgBox.showError(this, "�ۼƷ�Ʊ���ԭ�Ҳ���С���㣡");
			SysUtil.abort();
		}
		// ���ύУ��
		if (!isSaveAction()) {
			// ��������զ���ԭ���뱾�ұȽ���
			BigDecimal latestOrgPrice = FDCHelper.ZERO;
			if (bindCellMap.get(PayRequestBillContants.LATESTORGPRICE) != null) {
				latestOrgPrice = (BigDecimal) ((ICell) bindCellMap
						.get(PayRequestBillContants.LATESTORGPRICE)).getValue();
			}
			if (FDCHelper.toBigDecimal(invoiceOriAmtSum).setScale(2,
					BigDecimal.ROUND_HALF_UP).compareTo(latestOrgPrice) == 1) {
				if (isOverrun) { // ���������뵥�ۼƷ�Ʊ�����ں�ͬ�������
					int ok = FDCMsgBox.showConfirm2(this,
							"�ۼƷ�Ʊ���ԭ�Ҵ��ں�ͬ������ۣ��Ƿ��ύ?");
					if (ok != FDCMsgBox.OK) {
						SysUtil.abort();
					}
				} else {
					FDCMsgBox.showWarning(this, "�ۼƷ�Ʊ���ԭ�Ҳ��ܳ�����ͬ������ۣ�");
					SysUtil.abort();
				}
			}
		}
		editData.setAllInvoiceOriAmt(invoiceOriAmtSum);

		// R110510-0022�����������ύ�������߽��ʱe.getActionCommand()Ϊ�գ����±��ж�
		if (e != null && e.getActionCommand() != null
				&& e.getActionCommand().endsWith("ActionSubmit")) {

			if (prmtPlanHasCon.getValue() == null
					&& prmtPlanUnCon.getValue() == null) {
				if (isCostSplitContract) {
					if ("��ʾ����".equals(CONTROLPAYREQUEST)) {
						int confirm = FDCMsgBox.showConfirm2(this,
								"��ǰ����Ϊ�޼ƻ�����Ƿ�ȷ�ϲ������ύ��");
						if (confirm != FDCMsgBox.OK) {
							abort();
						}
					}

					if ("�ϸ����".equals(CONTROLPAYREQUEST)) {
						FDCMsgBox.showWarning(this, "��ǰ����Ϊ�޼ƻ�����������ύ");
						abort();
					}
				}
			} else {
				// 11.6.24 �����붯̬�ɱ����Ͳ���������� add by emanon
				if (isCostSplitContract) {
					// ������Ϊ"�ϸ����"ʱ�����ı���ͬ�ύʱУ�飺������ı���ͬ��"��λ�ҽ��"����"���¿�������
					// ����ʾ�û���"��������ڼƻ������������ύ"��"ȷ��"����ֹ�ύ������������Ϊ"��ʾ����"ʱ��
					// ���ı���ͬ�ύʱУ�飺����������뵥��"��λ�ҽ��"����"�������"������ʾ�û�����������ڼƻ��������
					// ��"ȷ��"���ύ�ɹ���"ȡ��"��������β�����������Ϊ"������"ʱ�������κ�У��
					BigDecimal bgBalance = getBgBalance();
					BigDecimal bcAmount = txtBcAmount.getBigDecimalValue();
					if ("�ϸ����".equals(CONTROLPAYREQUEST)) {
						if (bcAmount.compareTo(bgBalance) > 0) {
							setKdDepPlanStateValue();
							FDCMsgBox
									.showWarning(this, "��������ԭ�ҽ����ڱ��ڿ���Ԥ�㣬�����ύ");
							abort();
						}
					}
					if ("��ʾ����".equals(CONTROLPAYREQUEST)) {
						if (bcAmount.compareTo(bgBalance) > 0) {
							setKdDepPlanStateValue();
							int result = FDCMsgBox.showConfirm2(this,
									"��������ԭ�ҽ����ڱ��ڿ���Ԥ�㣬�Ƿ��ύ��");
							if (result != FDCMsgBox.OK) {
								SysUtil.abort();
							}
						}
					}
				}
			}
		}
		if(isShiGongContract && txtThreeCai.getBigDecimalValue()==null){
			MsgBox.showInfo("����ͬ����Ϊ[ʩ��]ʱӦ����д���Ľ����Ϣ��");
			txtThreeCai.grabFocus();
			SysUtil.abort();
		}
	}

	/**
	 * ������ͨ���������� ���ƻ�����״̬����ֵ
	 * 
	 * @throws Exception
	 * @Author��jian_cao
	 * @CreateTime��2013-7-10
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

		// �����˺ŵ�У����Ҫ�Լ��������⴦��һ��
		if (!FDCHelper.isEmpty(txtrecAccount.getText())) {
			txtrecAccount.setValue(txtrecAccount.getText());
		} else {
			txtrecAccount.setValue(null);
		}

		super.verifyInputForSubmint();
		// ������Ŀ�ϵĺ�ͬ�ƻ�����Ƿ�ǿ�غ�ͬ�������뵥����ֶΣ�У��
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
				MsgBox.showWarning("���θ������뵥�����ʽ�ƻ�����������ύ�����޸ġ�");
				SysUtil.abort();
			}
		} else {
			if (yb.compareTo(zjjhSqje) > 0) {
				MsgBox.showWarning("���θ������뵥�����ʽ�ƻ�������.");
				contZjjhSqje.getBoundLabel().setForeground(Color.red);
			}else{
				contZjjhSqje.getBoundLabel().setForeground(Color.black);
			}
		}
	}

	/**
	 * Description:�ұ�
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
				// ���û��ʵ�ֵ����¼����������ʱ���ܵ����
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
			// �״μ���
			if (isFirstLoad && !getOprtState().equals(OprtState.ADDNEW))
				return;
			// ��newValue.equalse(e.getOldValue()) �����,��Ϊ�Ƚϵ��Ƕ�ջ��ֵ
			if ((e.getOldValue() instanceof SupplierInfo)
					&& ((SupplierInfo) e.getOldValue()).getId().equals(
							((SupplierInfo) newValue).getId())
					&& !getOprtState().equals(OprtState.ADDNEW)) {
				return;
			}

			SupplierInfo supplier = (SupplierInfo) newValue;
			BOSUuid supplierid = supplier.getId();

			// ��Ӧ�̵Ļ�ȡ
			String supperid = supplierid.toString();
			PayReqUtils.fillBank(editData, supperid, curProject.getCU().getId()
					.toString());
			txtrecAccount.setText(editData.getRecAccount());
			txtrecBank.setText(editData.getRecBank());
		}
	}

	/**
	 * ���������ݹ�Ӧ��ID�������տ��ʺŵĹ�����Ϣ
	 */
	private void setRecAccountFilter() {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		SupplierInfo supplier = (SupplierInfo) prmtrealSupplier.getValue();

		if (supplier != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("supplier.id", supplier.getId()
							.toString()));
			// ��������֯����
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
	 * ���������ݹ�Ӧ�̣��������ƣ��ʺ�����ȡSupplierCompanyBankInfo����
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
		// Ԥ����ʱ�����ݿ���ȡ���������ȱ���һ�� by hpw 2011.6.20
		this.btnSave.doClick();
		// Ԥ�����
		// checkMbgCtrlBalance();�Ѿ�������checkFdcBudget���� 2011.6.5
		checkFdcBudget();
		return super.runSubmit();
	}

	protected void txtAmount_dataChanged(DataChangeEvent e) throws Exception {
		super.txtAmount_dataChanged(e);
		setAmount();
	}

	/**
	 * description �������������
	 * 
	 * @author ����
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
						// ��д���Ϊ��λ�ҽ��
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
	 * Description: ���ñ�λ�ҽ��,��Сд��
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

				// ��λ�Ҵ���
				CompanyOrgUnitInfo currentFIUnit = SysContext.getSysContext()
						.getCurrentFIUnit();
				CurrencyInfo baseCurrency = currentFIUnit.getBaseCurrency();
				BOSUuid srcid = ((CurrencyInfo) value).getId();
				if (baseCurrency != null) {
					if (srcid.equals(baseCurrency.getId())) {
						/*
						 * if (exchangeRate instanceof
						 * BigDecimal&&((BigDecimal)exchangeRate).intValue()!=1)
						 * { FDCMsgBox.showWarning(this,"��ѡ����Ǳ�λ�ң����ǻ��ʲ�����1"); }
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
				// ��д���Ϊ��λ�ҽ��
				String cap = FDCClientHelper.getChineseFormat(localamount,
						false);
				// FDCHelper.transCap((CurrencyInfo) value, amount);
				txtcapitalAmount.setText(cap);
			} else {
				txtcapitalAmount.setText(null);
			}

		} else {
			// ���ʴ���
			Object value = prmtcurrency.getValue();
			if (value instanceof CurrencyInfo) {
				// ��λ�Ҵ���
				CompanyOrgUnitInfo currentFIUnit = SysContext.getSysContext()
						.getCurrentFIUnit();
				CurrencyInfo baseCurrency = currentFIUnit.getBaseCurrency();
				BOSUuid srcid = ((CurrencyInfo) value).getId();
				if (baseCurrency != null) {
					if (srcid.equals(baseCurrency.getId())) {
						/*
						 * if (exchangeRate instanceof
						 * BigDecimal&&((BigDecimal)exchangeRate).intValue()!=1)
						 * { FDCMsgBox.showWarning(this,"��ѡ����Ǳ�λ�ң����ǻ��ʲ�����1"); }
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
	 * �õ���Դ�ļ�
	 */
	private String getRes(String resName) {
		return PayReqUtils.getRes(resName);
	}

	/**
	 * ���ñ��Ԫ��Ŀɱ༭״̬����ɫ
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
				// ������ʾ�������뵥�����ϴ��ۼ�ʵ��
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
			// ��ȡ���ж���������Ķ���ID
			// idList = (IIDList) getUIContext().get(UIContext.IDLIST);
			if (idList.size() == 0) {
				actionClose.setEnabled(false);
				actionUnClose.setEnabled(false);
				actionTraceDown.setEnabled(false);
			}
		}

		/*
		 * modified by zhaoqin for R131214-0062 on 2014/01/06,
		 * ��ǰ��������ʱ�����¼��㱾���깤������
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
	 * ������ʾ�������뵥�����ϴ��ۼ�ʵ�� - R131008-0190
	 * 
	 * @author zhaoqin
	 * @date 2013/11/29
	 */
	private void reCalData() throws Exception {
		if (!getOprtState().equals(OprtState.ADDNEW)) { // ��������
			tableHelper.updateLstReqAmt(editData, false);
			tableHelper.reloadGuerdonValue(editData, null);
			tableHelper.reloadCompensationValue(editData, null);
			tableHelper.updateLstReqAmt(editData, true);
			setOrgAmountForEntry(kdtEntrys);
		}

		/**
		 * ������ʾ�������뵥�����ϴ��ۼ�ʵ�� STATUS_FINDVIEW��ͬ�ɱ���Ϣ���ʱ���������״̬���������״̬Ҳ���¼���
		 * ���PayRequestFullInfoUI �е�tblMain_tableClicked
		 */
		if (STATUS_VIEW == this.getOprtState()
				|| STATUS_EDIT == this.getOprtState()
				|| STATUS_FINDVIEW == this.getOprtState()) {
			// ���ڹ��������������У�Ҳ�����¼���
			if (!isFromMsgCenterNoEdit()) {// check this..�����������ĵ��ݷ�ʵʱ������..
				setLstPriRaiedORPaied();
			}
		}

		// �����뵥�ۼ�ʵ������ң�ʵʱȡֵ
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

		// ��ͬ�޶��ͱ������Ҫ�����ͬ����ӳ������״̬Ϊ����������ύ�ĸ������뵥�еı��ָ������ȥ by cassiel 2010-08-06
		if (!FDCBillStateEnum.AUDITTED.equals(this.editData.getState())) {
			if (PayReqUtils.isContractBill(editData.getContractId())) {
				tableHelper.updateDynamicValue(editData, contractBill,
						contractChangeBillCollection, paymentBillCollection);
			}
		} else {// PBG095801..�����ĵ��ݣ���ͬ�������ҲӦ���¹���������
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
	 * ���ñ༭ʱ�����״̬,��Ҫ�ǶԱ�������
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
			// Add by zhiyuan_tang 2010/07/30 �����ڲ鿴������޸ģ���������ԭ�ҿ��õ�BUG
			if (isPartACon) {
				kdtEntrys.getCell(rowIndex, columnIndex).getStyleAttributes()
						.setLocked(true);
			}
			actionAdjustDeduct.setEnabled(true);
		}

		// �޸� R111219-0113 ���������뵥���������������̣����ύ�ĵ��ݱ�����޸�ʱ���ύ�˽�����Ϣ���ģ�
		// ���������ֱ���޸ĵ��ݵ����ݣ����磬����������ԭ�ҽ��޸ĺ󣬵������������������ָ��ֶ�Ϊ��ɫ���޷���Ч��By Owen_wen
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
	 * ���ø�����������깤��������ԭ�ҽ��֮��Ĺ�ϵ�� ���깤��������ԭ�ҽ��¸������
	 * 
	 * @author sxhong Date 2007-3-12
	 */
	private void setPropPrjAmount(String cause, DataChangeEvent e) {
		if (isFirstLoad
				|| (!txtpaymentProportion.isRequired())
				|| (isSeparate && contractBill != null && contractBill
						.isIsCoseSplit()))
			return;

		// �ñ�λ�ҽ��м���
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
					// return;��else����return��ֱ��return�ܿ��ܲ����к���Ĵ��� by hpw
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
				// ���ʱ��޸ģ������
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
	 * description ��������������깤�������ļ��� ��onLoad�е���
	 * 
	 * @author ����
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
		if (contractId != null && !PayReqUtils.isConWithoutTxt(contractId)) { // ���ı���ͬ
			txtpaymentProportion.setRequired(!isAutoComplete
					&& !(isSeparate && contractBill != null && contractBill
							.isIsCoseSplit())); // �����ȿ���������Զ�100% ʱ��¼
			// ��������ȷ�������븶�����̷������Ϊ ���ǡ� ���Һ�ͬ�ǳɱ���ֵ�ʱ�򣬹��������Ǳ�¼
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
					// ����
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
	 * �������뵥�ۼƶ���ں�ͬ����������ʱ���ѣ��ύ��������
	 * 
	 * @param ctx
	 * @param billInfo
	 * @throws BOSException
	 */
	private void checkAmt(PayRequestBillInfo billInfo) throws Exception {
		// ��ʼ������Ѿ���̬���ع�һ��������ۣ�����ȴ�ֶ�ȡһ�������ֶΣ���ʹ�ֶ��з�д,BUT.. ken_liu
		// BigDecimal latestPrice =
		// FDCHelper.toBigDecimal(billInfo.getLatestPrice(), 2);
		BigDecimal latestPrice = FDCHelper.toBigDecimal(this.localCurrency);
		/*********
		 * �������뵥�ıұ𣬱���ͺ�ͬ�ıұ���ͬ 2008-11-14 ���� �ұ��Ƿ��Ǳ��� ����Ǳ�����Ƚϱ��� ����������Ƚ�ԭ��
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
		BigDecimal totalpayAmount = FDCHelper.ZERO;// �ۼ�ʵ����+δ�������뵥
		BigDecimal totalpayAmountLocal = FDCHelper.ZERO;// �ۼ�ʵ����+δ�������뵥
		BigDecimal noPayAmt = FDCHelper.ZERO;
		BigDecimal noPayAmtLocal = FDCHelper.ZERO;
		BigDecimal payAmt = FDCHelper.ZERO;
		BigDecimal payAmtLocal = FDCHelper.ZERO;
		BigDecimal noKeepAmt = FDCHelper.ZERO;// ���ȿ�֮��
		BigDecimal noKeepAmtLocal = FDCHelper.ZERO;// ���ȿ�֮��
		for (int i = 0; i < c.size(); i++) {
			final PayRequestBillInfo info = c.get(i);
			if (info.getId().equals(billInfo.getId())) {
				// ���Ѵ漰δ������״̬,ͳ���ں��洦��
				continue;
			}
			// ͳ��ԭ�ҽ�� ( ��ͬ�ڹ��̿� + ���� - �ۿ� )
			total = total.add(FDCHelper.toBigDecimal(info.getOriginalAmount()));
			// ������ɽ����깤+����-����-�ۿ
			completeTotal = completeTotal.add(FDCHelper.toBigDecimal(info
					.getCompletePrjAmt()));
			// ��ͬ�ڹ��̿� �� ���ڷ���ԭ�� ��
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
			BigDecimal _tempActuallyPayOriAmt = FDCHelper.ZERO;// �ۼ�ʵ����ԭ����ʱ����
			BigDecimal _tempActuallyPayLocalAmt = FDCHelper.ZERO;// �ۼ�ʵ����ԭ����ʱ����
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
			// ����������뵥�Ѿ����������Ѿ��й����ĸ��
			if (FDCBillStateEnum.AUDITTED.equals(info.getState())) {
				int tempInt = info.getEntrys().size();
				for (int j = 0; j < tempInt; j++) {
					PaymentBillInfo payment = info.getEntrys().get(j)
							.getPaymentBill();
					if (payment != null
							&& payment.getBillStatus() == BillStatusEnum.PAYED) { // ���Ҹø���Ѿ�����
						temp = temp.add(FDCHelper.toBigDecimal(payment
								.getAmount()));
						tempLocal = tempLocal.add(FDCHelper
								.toBigDecimal(payment.getLocalAmt()));
						payAmt = payAmt.add(FDCHelper.toBigDecimal(payment
								.getAmount()));
						payAmtLocal = payAmtLocal.add(FDCHelper
								.toBigDecimal(payment.getLocalAmt()));
					} else if (payment != null
							&& payment.getBillStatus() != BillStatusEnum.PAYED) {// δ����
						// ��
						// ��Ҫ��¼һ������δ�����
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
				if (!info.isHasClosed()) {// �ѹرյĲ�Ӧ�ð�����ȥ
					// ��������� - ������Ӧ����ĸ����� = ����δ�����
					noPayAmt = FDCHelper.add(noPayAmt, FDCHelper.subtract(info
							.getOriginalAmount(), temp));
					// ��������� - ������Ӧ����ĸ����� = ����δ�����
					noPayAmtLocal = FDCHelper.add(noPayAmtLocal, FDCHelper
							.subtract(info.getAmount(), tempLocal));
				}
			} else {// ��û�и��
				temp = FDCHelper.toBigDecimal(info.getOriginalAmount());
				tempLocal = FDCHelper.toBigDecimal(info.getAmount());
				if (!info.isHasClosed()) {// �ѹرյĲ�Ӧ�ð�����ȥ
					noPayAmt = FDCHelper.add(noPayAmt, FDCHelper
							.toBigDecimal(info.getOriginalAmount()));
					noPayAmtLocal = FDCHelper.add(noPayAmtLocal, FDCHelper
							.toBigDecimal(info.getAmount()));
				}
			}
			if (!isKeepAmt) {
				// ����������뵥�Ѿ����������Ѿ��й����ĸ��.��ô�ڽ���"���ȿ�+�����ܳ�����ͬ�����-���޽�"У���ʱ��
				// ���ȿ�ͽ�����Ӧ��ȡ����ϵĽ��������������ϵ� by cassiel_peng 2009-12-06
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
		// ���ң���ǰ���ݺ�ͬ�µ��ۼ�ʵ����+δ�������뵥�����˸�����ʾ����:
		// �ۼƽ��:1100.00 ����,ʵ������300.00 ����δ����:800.00
		// ������ʾ������850.00(1000*85.00%)
		/**
		 * �������뵥������������ע����ʾ���߼����ƣ��������м�ļ�������н��ۼƽ�������󣬵�������ע����ʾ������ʾ������δ����=�ۼƽ��-
		 * �ۼ�ʵ�������� �������������߼� by cassiel_peng 2010-03-29
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
		 * ����: �����Ҫ���п��ƣ����ú�ͬ�����һ�ʽ�����ʱ��
		 * ϵͳӦ�ñȽϸú�ͬ�ۼ��Ѹ�����+�ñ����������ܶ��Ƿ񳬹�����ͬ������-���޽��� �������ˣ���ϵͳ��Ҫ������ʾ��������ǿ�ƿ��ƣ�
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
			 * "�ڽ��С����ȿ�+�����ܳ�����ͬ�����-���޽�"���߼��ж�ʱ����ԭ��֮ǰϵͳ��֧�ֺ�ͬ�Ķ�ʽ��� by
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
						FDCMsgBox.showError(this, "���ң����ȿ�+�����ܳ�����ͬ�����-���޽�");
						SysUtil.abort();
					}
				} else {
					noKeepAmt = FDCHelper.add(noKeepAmt,
							billInfo.getOriginalAmount()).setScale(2,
							BigDecimal.ROUND_HALF_UP);
					if (noKeepAmt.compareTo(amount) > 0) {
						FDCMsgBox.showError(this, "ԭ�ң����ȿ�+�����ܳ�����ͬ�����-���޽�");
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
				 * ȡԭ�ҵı��޽���
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
						FDCMsgBox.showError(this, "���ң���ͬ�ۼ��Ѹ����޿������ͬ���㱣�޽��!��");
						SysUtil.abort();
					}
				} else {
					keepAmt = FDCHelper.add(keepAmt,
							billInfo.getOriginalAmount()).setScale(2,
							BigDecimal.ROUND_HALF_UP);
					if ((keepAmt.compareTo(amount) > 0)) {
						FDCMsgBox.showError(this, "ԭ�ң���ͬ�ۼ��Ѹ����޿������ͬ���㱣�޽��!��");
						SysUtil.abort();
					}
				}
			}
		}

		/**
		 * ������ʾ�����㷨���ۼ�ʵ����+δ���������� ���� ��ͬ���*������ʾ���� ʱ��ʾ�� ���޸�Ϊ���ۼ�ʵ����+δ���������� ����
		 * ��ͬ�������*������ʾ���� ʱ��ʾ by cassiel_peng 2010-03-17 ��Ϊ֮ǰ�Ǻ�ͬ���*������ʾ����
		 * �������б��Һ�ԭ�ҵ�����ģ������ָ�Ϊ��ͬ�������*������ʾ���� ���ں�ͬ�������ֻ�б��ҵĸ��
		 * �����±ߵ�ԭ����ʾ������У���Ѿ�û��ʵ�������� by cassiel_peng 2010-03-17
		 */
		if (contractBill != null && !contractBill.isHasSettled()) {// δ���ս���
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
				// ��ͬ�������
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
					String str = "���ң���ǰ���ݺ�ͬ�µ��ۼ�ʵ����+δ�������뵥�����˸�����ʾ����:";
					str = str + "\n�ۼƽ��:" + totalpayAmountLocal + " ����,ʵ������"
							+ FDCHelper.toBigDecimal(payAmtLocal, 2)
							+ "  ����δ����:"
							+ FDCHelper.toBigDecimal(noPayAmtLocal, 2);
					str = str + "\n������ʾ������" + payRate + "(" + conLastestPrice
							+ "*" + payPercForWarn + "%)";
					if ("0".equals(allPaidMoreThanConPrice())) {// �ϸ����
						FDCMsgBox.showDetailAndOK(this, "����������ʾ����,��鿴��ϸ��Ϣ",
								str, 2);
						SysUtil.abort();
					} else if ("1".equals(allPaidMoreThanConPrice())) {// ��ʾ����
						FDCMsgBox.showDetailAndOK(this, "����������ʾ����,��鿴��ϸ��Ϣ",
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
			// String str = "ԭ�ң���ǰ���ݺ�ͬ�µ��ۼ�ʵ����+δ�������뵥�����˸�����ʾ����:";
			// str = str + "\n�ۼƽ��:" + totalpayAmount + " ����,ʵ������" +
			// FDCHelper.toBigDecimal(payAmt, 2) + "  ����δ����:" +
			// FDCHelper.toBigDecimal(noPayAmt, 2);
			// str = str + "\n������ʾ������" + payRate + "(" + contractAmt + "*" +
			// payPercForWarn + "%)";
			// FDCMsgBox.showDetailAndOK(this, "����������ʾ����,��鿴��ϸ��Ϣ", str, 1);
			// }
			// }
		}
		/******
		 * �ж��������
		 */
		if (isBaseCurrency) {
			// if (totalLocal.compareTo(latestPrice) > 0) {
			// // �ϸ���Ʋ������ύ
			// if (isControlCost) {
			// FDCMsgBox.showError(this, "��ͬ�¸������뵥���ۼƽ��(����)���ں�ͬ�������(����)��");
			// SysUtil.abort();
			// } else {
			// int result = FDCMsgBox.showConfirm2(this,
			// "��ͬ�¸������뵥���ۼƽ��(����)���ں�ͬ�������(����).�Ƿ��ύ?");
			// if (result != FDCMsgBox.OK) {
			// SysUtil.abort();
			// }
			// }
			// }
		} else {
			/**********
			 * ��Ҫʹ��ԭ�ҵ�������۱Ƚ�
			 * ���浥������showMsg4TotalPayReqAmtMoreThanConPrice����ֻע���˱��Ҳ�ע����ҡ���������
			 */
			// total = FDCHelper.toBigDecimal(total, 2);
			// lastestPriceOriginal =
			// FDCHelper.toBigDecimal(lastestPriceOriginal, 2);
			// if (total.compareTo(lastestPriceOriginal) > 0) {
			// // �ϸ���Ʋ������ύ
			// if (isControlCost) {
			// FDCMsgBox.showError(this, "��ͬ�¸������뵥���ۼƽ��(ԭ��)���ں�ͬ�������(ԭ��)��");
			// SysUtil.abort();
			// } else {
			// int result = FDCMsgBox.showConfirm2(this,
			// "��ͬ�¸������뵥���ۼƽ��(ԭ��)���ں�ͬ�������(ԭ��).�Ƿ��ύ?");
			// if (result != FDCMsgBox.OK) {
			// SysUtil.abort();
			// }
			// }
			// }
		}
		// BigDecimal totalReqAmt = payAmtLocal.add(billInfo.getAmount());//�������
		/* modified by zhaoqin for R140425-0083 on 2014/05/07 */
		BigDecimal totalReqAmt = FDCHelper.add(payAmtLocal, billInfo
				.getAmount()); // = ���������ۼ�ʵ������ + �������뱾��
		/* modified by zhaoqin for R140319-0098 on 2014/03/21 start */
		if (!isBaseCurrency) {
			/* modified by zhaoqin for R140425-0083 on 2014/05/07 */
			// totalReqAmt = payAmt; // = ���������ۼ�ʵ��ԭ�� + ��������ԭ��
			totalReqAmt = FDCHelper.add(payAmt, billInfo.getOriginalAmount());
		}
		/* modified by zhaoqin for R140319-0098 on 2014/03/21 end */

		if (totalReqAmt.compareTo(latestPrice) == 1) {
			if (isControlCost) {
				FDCMsgBox.showWarning(this, "\"��������+�ۼ�ʵ��\" ���ܴ��ں�ͬ�������!");
				SysUtil.abort();
			}
		}

		// �˶ι��ܴ��󣬲���������ʵ��У��byhpw
		/*
		 * // if (isBaseCurrency) {���ܱұ�У�鱾�Ҽ��� //
		 * �˴����ǲ鿴״̬ʱ����ǰ���ݵĽ��ظ�ͳ���ˣ�����Ҳֻ�ж��޸�״̬��ȥ��û�в鿴 // ���Բ鿴ʱ�����ӵ�ǰ���ݽ�� edit by
		 * emanon // BigDecimal totalLocal = //
		 * FDCHelper.add(ContractClientUtils.getReqAmt //
		 * (billInfo.getContractId()), billInfo.getAmount()); BigDecimal
		 * totalLocal = FDCHelper.ZERO; if (STATUS_VIEW.equals(getOprtState())
		 * || STATUS_FINDVIEW.equals(getOprtState())) { totalLocal =
		 * ContractClientUtils.getReqAmt(billInfo.getContractId()); } else {
		 * totalLocal =
		 * FDCHelper.add(ContractClientUtils.getReqAmt(billInfo.getContractId
		 * ()), billInfo.getAmount()); } // �������޸�ʱ�ظ���¼����Ҫ��ȥ���ŵ���֮ǰ���������ݿ����ֵ�� if
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
		 * if (totalLocal.compareTo(latestPrice) > 0) { // �ϸ���Ʋ������ύ
		 * showMsg4TotalPayReqAmtMoreThanConPrice(isControlCost); } // }
		 */
		if (isSimpleFinancial) {
			if (billInfo.getPaymentType() == null
					|| billInfo.getPaymentType().getPayType() == null) {
				FDCMsgBox.showError(this, "������������");
				SysUtil.abort();
			}

			// R131224-0144 ��ͬ�¸������뵥���ۼ��������ͬ�ڹ��̿ֱ���ý����ϡ����������ۼ����롯
			int currencyIndex = isBaseCurrency ? 9 : 8; // ���һ�ԭ�ҵıȽϲ�ͬ
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
					String msg = "��ͬ�¸������뵥���ۼ����깤���������ܴ��ں�ͬ�������";
					FDCMsgBox.showWarning(this, msg);
					SysUtil.abort();
				}
			}
			// ������return�������У��ʱע����
			return;
			// ��ģʽ���˽���
		}
		if (FDCHelper.toBigDecimal(completeTotal, 2).compareTo(latestPrice) > 0) {
			if (billInfo.getPaymentType() == null
					|| billInfo.getPaymentType().getPayType() == null) {
				FDCMsgBox.showError(this, "������������");
				SysUtil.abort();
			}
			if (billInfo.getPaymentType().getPayType().getId().toString()
					.equals(PaymentTypeInfo.progressID)) {
				String msg = "��ͬ�¸������뵥���ۼ����깤���������ں�ͬ������ۣ��Ƿ��ύ?";
				int result = FDCMsgBox.showConfirm2(this, msg);
				if (result != FDCMsgBox.OK) {
					SysUtil.abort();
				}
			}
		}

	}

	/**
	 * ���ǳ����ദ�����������Ϊ,ͳһ��FDCBillEditUI.handCodingRule�����д���
	 */
	protected void setAutoNumberByOrg(String orgType) {

	}

	/**
	 * description �鿴��ͬ
	 * 
	 * @author ����
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
		uiContext.put("source", "listBase"); // �����빤��������������
		// ����UI������ʾ
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
	 * description �鿴��ִͬ�����
	 * 
	 * @author ����
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
		uiContext.put("source", "listBase"); // �����빤��������������
		// ����UI������ʾ
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
	 * description �鿴Ԥ�����
	 * 
	 * @author ����
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
			// //����longnumber�Ա���Ԥ��ƥ��
			// String lgNumber = info.getCostAccount().getLongNumber();
			// if(lgNumber!=null){
			// lgNumber=lgNumber.replace('!', '.');
			// }
			// info.getCostAccount().setLongNumber(lgNumber);
			//				
			// }

		}
		/*
		 * //�ӿ�1�����ݽ������ȡ���µ�Ԥ����� BgCtrlResultCollection coll =
		 * iCtrl.getBudget(FDCConstants.PayRequestBill, null, payReqInfo);
		 * //�ӿ�2��ȡ���ݿ��е���� BgCtrlResultCollection coll =
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
		// ��1�ӿ���ͬ by hpw 2011.6.2
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

			// R141104-0009: ����FDC072_PAYPROGRESSΪ���ǡ������ۼ����깤���������͡��ۼ�Ӧ���������������ʾ��
			// �Ѿ��������ޡ�����ȷ�Ϲ��� ���ֿ����߼��Ǵ���ġ� by skyiter_wang 2015/01/13
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
	 * ҵ�����ڸı䣬ȡ��Ӧ�ĺ�ͬ�����ƻ�
	 */
	protected void pkbookedDate_dataChanged(DataChangeEvent e) throws Exception {
		StringBuffer format = new StringBuffer();
		Date bookDate = (Date) pkbookedDate.getValue();
		Calendar cal = Calendar.getInstance();
		cal.setTime(bookDate);
		format.append("$contractName$ ");
		format.append(cal.get(Calendar.YEAR));
		format.append("��");
		format.append(cal.get(Calendar.MONTH) + 1);
		format.append("�¸���ƻ�");
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
			// added by ken...ȡ���µļ�¼��
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
				if (!"������".equals(CONTROLPAYREQUEST)) {
					prmtPlanHasCon.setRequired(false);
					prmtPlanUnCon.setRequired(false);
				}
			} else {
				contPlanHasCon.setVisible(false);
				contPlanUnCon.setVisible(true);
				if (!"������".equals(CONTROLPAYREQUEST)) {
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
	 * ������
	 * 
	 * @param info
	 * @Author��keyan_zhao
	 * @CreateTime��2012-11-6
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
			// �����Ѹ����̿�
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

				// ��ͬʱ����Ȼ�����ݿ���ȡ��ͬ�����µĸ���ƻ�
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
					// ����ƻ�������ʱ������Ƿ����Ϊ��
					tableHelper.setCellValue(
							PayRequestBillContants.CURPLANNEDPAYMENT, null);
					tableHelper.setCellValue(PayRequestBillContants.CURBACKPAY,
							null);
					return;
				}

			}
		}
		/*
		 * ����Ƿ���� :���㹫ʽΪ���ú�ͬ���ڸ���ƻ�-�ú�ͬ�ı����ۼ����루�������������룩-�ú�ͬ�����ۼ�ʵ������ by sxhong
		 * 2007/09/28
		 */

		/*****/
		/*
		 * EntityViewInfo view = new EntityViewInfo(); FilterInfo filter = new
		 * FilterInfo(); view.setFilter(filter);
		 * filter.appendFilterItem("contractId", editData.getContractId()); //
		 * ��ʱ����˳�����֮ǰ�ĵ� filter.getFilterItems().add( new
		 * FilterItemInfo("createTime", editData.getCreateTime(),
		 * CompareType.LESS)); view.getSelector().add("amount");
		 * view.getSelector().add("entrys.paymentBill.billStatus");
		 * view.getSelector().add("entrys.paymentBill.amount");
		 * //����Ƿ����Ӧ���ø���ı��Ҽ��㣬������ԭ��
		 * view.getSelector().add("entrys.paymentBill.localAmt");
		 * PayRequestBillCollection c =
		 * PayRequestBillFactory.getRemoteInstance()
		 * .getPayRequestBillCollection(view); BigDecimal totalpayAmount =
		 * FDCHelper.ZERO;// �ۼ�ʵ����+δ�������뵥 for (int i = 0; i < c.size(); i++) {
		 * final PayRequestBillInfo info = c.get(i); if
		 * (info.getId().equals(editData.getId())) { // �ų������� continue; }
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
	 * description �������ͱ仯�¼�
	 * 
	 * @author ����
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
			// ���㷽ʽΪ���������м��˺�Ϊ�Ǳ�¼
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
	 * description �����״̬
	 * 
	 * @author ����
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
		if (!checkAllSplit) {// ����ύʱ���Ƿ����ͬδ��ȫ���
			return;
		}
		String contractBillId = (String) getUIContext().get("contractBillId");
		if (contractBillId != null) {
			if (!ContractClientUtils.getContractSplitState(contractBillId)) {
				// ��Ӧ�ĺ�ͬ��δ���в�֣����ܽ��д˲�����
				FDCMsgBox.showWarning(this, FDCSplitClientHelper
						.getRes("conNotSplited"));
				SysUtil.abort();
			}
		}
	}

	// ����ʱ������Ԥ�����
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
	 * �ύ��ʱ�����Ԥ����
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
			// ��ֹĳЩ�û���δ�ύʱ�޸Ĳ�������ȡ
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
			// �����ݿ�ȡ
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
				FDCMsgBox.showWarning(this, "�������� ����Ϊ��!");
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
	 * description �������ԭ�ҷ����仯�󣬸��±��ҽ��
	 * 
	 * @author ����
	 *         <p>
	 * @createDate 2011-9-1
	 *             <p>
	 * @param originalAmount
	 *            ��������ԭ��
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
					FDCHelper.toBigDecimal(amount, 2)); // ���ñ������뱾��
		}
	}

	/**
	 * description ����Ԥ�����
	 * 
	 * @author ����
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
	 * description �������깤���������������
	 * 
	 * @author ����
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
			// �������������ȷ�ϵ������������¼��㡣
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
	 * description ���ü׹��ı���
	 * 
	 * @author ����
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
	 * description ��¼�༭����¼�
	 * 
	 * @author ����
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
				return;// ֵδ�ı䣬ֱ�ӷ���
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
				return; // ԭ�ҽ��Ϊ0���Ѿ��������
			} else {
				// setLocalAmountOfThisTime(originalAmount); ע��By Owen_wen
				// 2012-12-10 ����������б�Ҫ�����ô����ô�������´����滻
				BigDecimal localAmount = FDCHelper.multiply(originalAmount,
						txtexchangeRate.getBigDecimalValue());
				getDetailTable().getCell(rowIndex, columnIndex + 1).setValue(
						FDCHelper.toBigDecimal(localAmount, 2));

				// caculatePaymentProp(); ע��By Owen_wen 2012-12-10
				// ����������б�Ҫ�����ô����ô����ע�Ϳ�Ч���������´����滻
				// ֻ�н��ȿ�ʱ�Ž��л���
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
																			// ���㻥��
																			// ��
																			// ��������������
						txtcompletePrjAmt.setValue(originalAmount, false);
					}
				}
			}
		} else if ((e.getRowIndex() == ((ICell) bindCellMap
				.get(PayRequestBillContants.PAYPARTAMATLAMTORI)).getRowIndex()
				+ this.deductTypeCollection.size() + 1)
				&& (e.getColIndex() == 4)) {
			// ¼��׹���
			BigDecimal originalAmount = FDCHelper.toBigDecimal(e.getValue());
			setPmtAmoutChange(originalAmount);
		} else if ((e.getRowIndex() == rowIndex + 1)
				&& (e.getColIndex() == columnIndex)) {
			BigDecimal originalAmount = FDCHelper.toBigDecimal(e.getValue());
			// ����cell��delete��˫����delete�������¼���Ҫ���¼���
			if (e.getValue() == null) {
				originalAmount = null;
			}
			setAdvanceChange(originalAmount);
		}

		// calAllCompletePrjAmt(); ע��By Owen_wen 2012-12-10
		// ����������б�Ҫ�����ô����ô����ע�Ϳ�Ч��

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
	 * �������򵥲���ɱ�һ�廯����ۿΥԼ����������ۿΥԼ�ͽ�����Ӱ�����ɱ��Ľ��Ժ�ͬ�ڹ��̿�������ɱ���
	 * �򵥲���ɱ�һ�廯������ۿΥԼ������������ʵ�ʸ������������ɱ���
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

	// ��Ϊ�ɱ����Ĳ�����fetchInitParam��
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
	 * description �������ͱ仯�¼�
	 * 
	 * @author ����
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
					// R100709-147 �����������ȷ�ϵ�ʱ���������޸ģ�����ͨ����������ȷ�ϵ������޸�
					if (!isPartACon) {
						this.kdtEntrys.getCell(4, 4).getStyleAttributes()
								.setLocked(false);
					}
					// ����Ǽ׹��ĺ�ͬ�ĸ������뵥��δ���������Ϻ�ͬ�������޸Ľ�Added By Owen_wen
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
					FDCMsgBox.showError(prmtPayment, "��ʵ�ֲ�ֵΪ0ֻ����ѡ��\"Ԥ����\"��");
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
				// �����
				String progressID = PaymentTypeInfo.progressID;// "Ga7RLQETEADgAAC6wKgOlOwp3Sw="
				// ;//
				// ���ȿ�
				String keepID = PaymentTypeInfo.keepID;// "Ga7RLQETEADgAADDwKgOlOwp3Sw="
				// ;//
				// ���޿�

				String tempID = PaymentTypeInfo.tempID;// "Bd2bh+CHRDenvdQS3D72ouwp3Sw="
				// �ݹ���

				PaymentTypeInfo type = (PaymentTypeInfo) obj;
				// ���Ϊ�ݹ�������ʱ
				if (type.getPayType().getId().toString().equals(tempID)) {
					if (!isSimpleInvoice) {
						prmtPayment.setValue(null);
						prmtPayment.setText(null);
						FDCMsgBox
								.showWarning("���á����������Է�Ʊ���Ϊ׼�ƿ����ɱ������������ѡ�����ĸ������ͣ��������ö�Ӧ������");
						SysUtil.abort();
					} else {// ������¼�����ֶν���Ʊ����¼��(��Ӧ�ĺ�ͬ�ڹ��̿�����������¼��)
						this.kdtEntrys.getStyleAttributes().setLocked(true);
						if (this.kdtEntrys.getCell(4, 4) != null) {
							this.kdtEntrys.getCell(4, 4).setValue(null);
							this.kdtEntrys.getCell(4, 5).setValue(null);
							this.kdtEntrys.getCell(4, 4).getStyleAttributes()
									.setLocked(true);
						}
					}
				}

				// ���޿��޸�ΪֻҪ����Ϳ��Ը�

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
				 * "������������ܸ����޿�,�����ڸ������͵�����Ϊ�������ĸ������뵥ʱ����ѡ�񡰱��޿���͸�������");
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
					// ֻ�зǱ༭״̬������²ż��Ԥ�������������Ǳ༭״̬�£����������Ԥ���
					// ѡ����������������ѡԤ����ͻ���ֲ��������ʾ
					if (!OprtState.EDIT.equals(getOprtState())
							&& cols.size() >= number) {
						FDCMsgBox.showWarning("һ����ֻͬ����¼" + number + "��Ԥ����.");
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
					// ����������Ͳ��ǡ�Ԥ����Ͱ�ֵ��ԭ
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
						// ����׹��Ŀ�Ϊ0
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
						FDCMsgBox.showError(prmtPayment, "��ͬ�������ܸ����޿�");
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
										"����������ܸ����ȿ�,�����ڸ������͵�����Ϊ�������ĸ������뵥ʱ����ѡ�񡰽��ȿ���͸�������");
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
							FDCMsgBox.showError(prmtPayment, "��ͬ����֮���ܸ����ȿ");
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
								"��ͬ�������֮���������������ĸ������뵥");
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
					// txtpaymentProportion������ɼ�����ôrequireedӦ��Ϊfalse By Owen_wen
					// 2011-11-04
					txtpaymentProportion.setRequired(txtpaymentProportion
							.isVisible()
							&& !isAutoComplete);
					txtpaymentProportion.setEditable(true && !isAutoComplete);
					// txtcompletePrjAmt.setEditable(true);
				}
				/*
				 * 1.����޿�ʱ�����ȿ�֧�������������깤������Ĭ��Ϊ�㡢�ɱ༭��
				 * �������˳����£������޿�ʱ�����ȿ�֧�������������깤������Ĭ��Ϊ�㡢���ɱ༭��
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
		// ���ȿ�ʱ,�ǹ������ͬ�ĸ������뵥�������깤�������ͽ��ȿ�����Ӧ�û��ԣ�����ȥ�������߼�������������= �����깤������*
		// ���ȸ������%����

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
	 * �������� ��������ģʽ������ȷ�ϳɱ��������ò������ȿ������Զ���ʾΪ100% 1.��������ʱ�����ȿ�֧�������������깤����������¼�롣
	 * �������˳����£������깤������ = ������ - ���Ÿ�������֮ǰ���ۼ��깤�����������ȿ�֧������Ϊ�㣬���ɱ༭��
	 * ���⣬�ٸ��ڶ��ʽ����ʱ�����ȿ�֧�������������깤����������ʾΪ���Ҳ��ɱ༭��
	 * 
	 * @throws Exception
	 * @throws BOSException
	 * @throws EASBizException
	 * @Author��keyan_zhao
	 * @CreateTime��2012-9-25
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
	 * description �ݹ��� ������������Ԫ��
	 * 
	 * @author ����
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
							+ "' ��Ŀ���Ѿ�����");
					return;
				}
			}
		}
	}

	/**
	 * �ۼ����깤������=֮ǰ����״̬�ĸ������뵥���깤���������+����¼������깤���������<br>
	 * �ۼƸ������=�ۼ��������������������ۼƺ�ͬ�ڹ��̿�������ڣ�/�ۼ����깤������<br>
	 * ��ͬ���ս���󣺺�ͬ�����
	 * <p>
	 * ������ʾ���ۼƸ���������Ѿ���Ϊ���ۼ�Ӧ�������������֮ǰ�Ǵʲ����⣬�޸ĺ�ͻ�������⡣Modified by Owen_wen
	 * 2010-6-30 �ᵥ�ţ�R100621-226
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
			// ���깤����������Ϊ0 eric_wang 2010.06.01
			BigDecimal settleAmt = contractBill.getSettleAmt(); // �����
			if (FDCHelper.ZERO.equals(settleAmt)) {
				FDCMsgBox.showError(this, "���ң����ȿ�+�����ܳ�����ͬ�����-���޽�");
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
				txtcompletePrjAmt.getBigDecimalValue())); // �ۼ��깤������=�����깤������+
															// ��ǰ�������깤������
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
	 * description �༭���֮�� ����д���깤�������͸������
	 * 
	 * @author ����
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
		// ��ͬ����
		if (contractBill != null && contractBill.isHasSettled()
				&& !isSimpleFinancial) {
			txtAllCompletePrjAmt.setValue(contractBill.getSettleAmt());
			BigDecimal prjAllReqAmt = FDCHelper.toBigDecimal(editData
					.getPrjAllReqAmt(), 4);
			if (OprtState.ADDNEW.equals(getOprtState())
					&& bindCellMap.get(PayRequestBillContants.PRJALLREQAMT) != null) { // ��ͬ�ڹ��̿�
																						// ���������ۼ�����
																						// ��
																						// ����
																						// ��
				prjAllReqAmt = FDCHelper.toBigDecimal(((ICell) bindCellMap
						.get(PayRequestBillContants.PRJALLREQAMT)).getValue(),
						4);
			}
			txtAllPaymentProportion.setValue(FDCHelper.divide(prjAllReqAmt,
					txtAllCompletePrjAmt.getBigDecimalValue(), 4,
					BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
			return;
		}

		// ���������������ͬ����+��ģʽ
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
	 * description �ۼƷ�Ʊ���ҽ��
	 * 
	 * @author ����
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
			// Ϊ��ʱ����return�������Ҫ����ֵ
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
		// ԭ���ķ�Ʊ�ۼƽ��ؼ��������������ˣ�������Ϊ�ܶ�ط���Ȼ��ȡֵ��Ҫ�����Լ�������
		txtAllInvoiceAmt.setNumberValue(allInvoiceAmt.add(FDCHelper
				.toBigDecimal(txtInvoiceAmt.getBigDecimalValue())));
	}

	/**
	 * description �ۼƷ�Ʊԭ�ҽ��
	 * 
	 * @author ����
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
			// Ϊ��ʱ����return�������Ҫ����ֵ
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
	 * ����: �����߼����ġ���Ը����������Ϊ���ȿ�ĸ������뵥����������Ϊ0������ʵ��Ϊ0������������ܼƵ����깤��������Ѹ��
	 * �ۼ����깤=�������ĸ������뵥�깤������֮��
	 * +�����깤���������ۼ��Ѹ���=���еĸ������뵥��ͬ�ڹ��̿�֮��+���ں�ͬ�ڹ��̿��������ͬʱΪ0�������
	 * ���ڲ����������ĸ������뵥���ڱ��棬�ύʱ������ʾ���ۼ����깤���������ĸ������뵥�깤������֮��+�����깤��������
	 * С�����ۼƸ�����еĸ������뵥��ͬ�ڹ��̿�֮��+���ں�ͬ�ڹ��̿������ִ�б�������
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

		// Ԥ�������͵����뵥�ύ����������Ϊ��
		PaymentTypeInfo paymentTypeInfo = (PaymentTypeInfo) prmtPayment
				.getValue();
		if (paymentTypeInfo != null && YFK.equals(paymentTypeInfo.getName())) { // Ԥ�����Ԥ������
			return;
		}

		// ���깤������(���깤)
		BigDecimal completePrjAmt = FDCHelper.toBigDecimal(editData
				.getCompletePrjAmt());
		// ��ͬ�ڹ��̿�(�Ѹ���)
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
		// ����������,���������浥����ȡ��ǰ������������,�Ա����������޸�ʱ���ݴ���
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
				// �ر�״̬��ȡ���
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
	 * �ۼ����깤���������ĸ������뵥�깤������֮��+�����깤��������С���ۼƸ�����еĸ������뵥��ͬ�ڹ��̿�֮��+���ں�ͬ�ڹ��̿������ִ�б�������
	 * 
	 * @author ����
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
							"�ۼ����깤���������ĸ������뵥�깤������֮��+�����깤��������С���ۼƸ�����еĸ������뵥��ͬ�ڹ��̿�֮��+���ں�ͬ�ڹ��̿������ִ�б�������");
			SysUtil.abort();
		}
	}

	protected void prmtPayment_willCommit(CommitEvent e) throws Exception {
		/***
		 * 42. �ᵥ��R090609-207 ��ҵƽ ��ģʽҲ����ѡ������,���޿� by ����
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
	 * ������ĺ�ͬID
	 * 
	 * @return contractId
	 */
	public void actionAssociateAcctPay_actionPerformed(ActionEvent e)
			throws Exception {
		if (OprtState.ADDNEW.equals(getOprtState()) || this.editData == null
				|| this.editData.getId() == null) {
			// �뱣�澯��
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
	 * description �������뵥��ͬID
	 * 
	 * @author ����
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
	 * �¶����������ɱ���Ŀ����ƻ��������ǩ����ͬ�ϲ���һ��
	 */
	public void actionMonthReq_actionPerformed(ActionEvent e) throws Exception {
		if (OprtState.ADDNEW.equals(getOprtState()) || this.editData == null
				|| this.editData.getId() == null) {
			// �뱣�澯��
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
			FDCMsgBox.showWarning(this, "��ǰ��ͬ������Ŀ�¶ȿ�Ŀ����ƻ�,�����ڴ�ǩ����ͬ,����Ҫ������!");
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
	 * description �鿴��������
	 * 
	 * @author ����
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
		// ����UI������ʾ
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
	 * description ���ʱ仯�¼�
	 * 
	 * @author ����
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
	 * RPC���죬�κ�һ���¼�ֻ��һ��RPC
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
	 * EditUI�ṩ�ĳ�ʼ��handler��������
	 */
	protected void PrepareHandlerParam(RequestContext request) {
		super.PrepareHandlerParam(request);

	}

	protected void prepareInitDataHandlerParam(RequestContext request) {
		// ��ͬId
		String contractBillId = (String) getUIContext().get("contractBillId");
		if (contractBillId == null) {
			request.put("FDCBillEditUIHandler.ID", getUIContext().get("ID"));
		} else {
			request.put("FDCBillEditUIHandler.contractBillId", contractBillId);
		}
		// ����ڼ��Ŵ����������ݣ�Ϊ�յ��º�����������
		// ������ĿId
		BOSUuid projectId = ((BOSUuid) getUIContext().get("projectId"));
		request.put("FDCBillEditUIHandler.projectId", projectId);

		// ��ͬ����ID
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
	 * Ԥ�������������+��ͬ ��������ȷ�������븶�����̷������Ϊ���ǡ������Ǻ�ͬ ����true
	 * 
	 * @return
	 */
	private boolean isAdvance() {
		try {
			// �򵥷�Ʊ����ʾ
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
	 * description �鿴����ȷ�ϵ�
	 * 
	 * @author ����
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
		// // �뱣�澯��
		// FDCMsgBox.showWarning("���ȱ��浥�ݣ��ٹ�������ȷ�ϵ�");
		// SysUtil.abort();
		// }
		UIContext uiContext = new UIContext(this);
		uiContext.put("PayRequestBillInfo", editData);

		//����ʹ��this.contractBill.getId().toString()����Ϊ���ı����ɵĸ������뵥contractBillΪnull
		// ��
		// ��Ϊthis.getUIContext().get("contractBillId")��������ֵ���ٷŵ�uiContext�У�
		// ������Թ���UIContext�����滻���Owen_wen 2010-11-15
		uiContext.put("contractBillId", this.getUIContext().get(
				"contractBillId"));

		// ����UI������ʾ
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
			// �������깤���������������
			setCompletePrjAmt();
			// �������루ԭ�ң���ͬ�ڹ��̿�
			((ICell) bindCellMap
					.get(PayRequestBillContants.PROJECTPRICEINCONTRACTORI))
					.getStyleAttributes().setLocked(true);
		} else {
			// �������깤���������������
			setCompletePrjAmt();
			// �������루ԭ�ң���ͬ�ڹ��̿�
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
	 * ʵ�� IWorkflowUIEnhancement �ӿڱ���ʵ�ֵķ���getWorkflowUIEnhancement by
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
	 * description ��丽���б�
	 * 
	 * @author ����
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
	 * description �鿴����
	 * 
	 * @author ����
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
	 * description ��������
	 * 
	 * @author ����
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
	 * ��Ӧ���Ϊ�ݹ�������ʱ���ύʱ�Է�Ʊ�������жϣ���С��0������ʾ���ݹ������͵ĸ������뵥�ķ�Ʊ����ۼƲ���С��0�����������ύ��
	 * ����ʱ����ͬ����У�鼰��ʾ��
	 * 
	 * @author ����
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
		// ���IDΪ��˵����δ����;�����Ϊ���п����Ǳ���֮��ֱ���ύҲ�п����Ǳ���������ύ��֮���������޸ķ�Ʊ���������ύ�����Ա������У��
		if (this.editData.getId() != null
				&& ("����".equals(this.editData.getState().toString()) || "���ύ"
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
			FDCMsgBox.showWarning("�ݹ������͵ĸ������뵥���ۼƷ�Ʊ����С��0�����������ύ��");
			SysUtil.abort();
		}
	}

	/**
	 * �����뵥�ύ������ʱ������ͬʵ�����Ƿ������ʵ�ֲ�ֵ�� �����ڣ�����ʾ��δ�����ͬ��ʵ����ܴ�����ʵ�ֲ�ֵ����ͬʵ����=�ѹرյ�
	 * �������뵥��Ӧ�ĸ����ͬ�ڹ��̿�ϼ� + δ�رյĸ����� �뵥��ͬ�ڹ��̿�
	 * �ϼơ������������˱������������ύ������ͨ������δ����,������ʾ֮�������ύ�������� by jian_wen 2009.12.16
	 * 
	 * @throws BOSException
	 * @throws EASBizException
	 * */
	private void check() throws EASBizException, BOSException {
		// �����Ӧ��ͬ�ѽ��� ֱ�ӷ���
		if (contractBill.isHasSettled()) {
			return;
		}
		// Ԥ�������͵����뵥�ύʱ�����˲�������
		PaymentTypeInfo paymentTypeInfo = (PaymentTypeInfo) prmtPayment
				.getValue();
		if (paymentTypeInfo != null && YFK.equals(paymentTypeInfo.getName())) { // Ԥ�����Ԥ������
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("paymentType.name", "%Ԥ����%",
							CompareType.NOTLIKE));
			filter.getFilterItems().add(
					new FilterItemInfo("contractId", editData.getContractId()));
			if (editData.getId() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("id", editData.getId().toString(),
								CompareType.NOTEQUALS));
			}
			// ���ڽ��ȿ�ʱ�ܿ���
			if (!PayRequestBillFactory.getRemoteInstance().exists(filter)) {
				return;
			}
		}

		BigDecimal payAmt = ContractClientUtils.getPayAmt(contractBill.getId()
				.toString());
		if (payAmt == null) {
			payAmt = FDCHelper.ZERO;
		}
		// ȡ����¼���ʵ����
		BigDecimal amt = FDCHelper.ZERO;
		if (getDetailTable().getCell(rowIndex, columnIndex + 1).getValue() != null) {
			amt = new BigDecimal(getDetailTable().getCell(rowIndex,
					columnIndex + 1).getValue().toString());
		}
		payAmt = FDCHelper.add(payAmt, amt);

		if (payAmt != null
				&& payAmt.compareTo(FDCHelper.toBigDecimal(txtTotalSettlePrice
						.getBigDecimalValue())) == 1) {

			if (isControlPay) {// �����˲��� ��ʾ������Ϣ���ж�
				FDCMsgBox.showError(cantMoreThanTotalSettlePrice);
				abort();
			} else {
				if (isMoreSettlement) {// ֻ�����˺�ͬ�Ƿ�ɽ��ж�ν��� û���� ���� ��ֻ ��ʾ �����Լ�������
					/* modified by zhaoqin for R140421-0018 on 2014/04/14 */
					// FDCMsgBox.showWarning(cantMoreThanTotalSettlePrice);
				}
			}
		}
	}

	/**
	 * ���رո������뵥ʱ�������ͬʵ����(��������)�ӱ��θ������뵥��ͬ�ڹ��̿�ڷ���������ʵ�ֲ�ֵ��
	 * ����ͬ��δ���ս����������˲�����δ�����ͬ��ʵ���������ʵ�ֲ�ֵʱ�Ƿ��ϸ���ơ��Һ�ͬʵ��������ۼ�
	 * ���������ʾ��δ�����ͬ��ʵ����ܴ����ۼƽ������ͬʵ���� =�ѹرյĸ������뵥��Ӧ�ĸ��� ����ͬ�ڹ��̿�ϼ� +
	 * δ�رյĸ������뵥��ͬ�ڹ��̿�ϼơ����������ύ������ͨ������δ����,������ ʾ֮�������ύ�������� by jian_wen
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

		// ȡ��ͬʵ����(���б��������ݿ��ֵ)
		BigDecimal payAmt = ContractClientUtils.getPayAmt(contractBill.getId()
				.toString());
		if (payAmt == null) {
			payAmt = FDCHelper.ZERO;
		}
		// ���ϱ��η��ر�ʱ ���뵥�ϵ� ��ͬ�ڹ��̿λ��
		// BigDecimal amt = FDCHelper.ZERO;
		// if (getDetailTable().getCell(rowIndex, columnIndex + 1).getValue() !=
		// null) {
		// amt = new BigDecimal(getDetailTable().getCell(rowIndex, columnIndex +
		// 1).getValue().toString());
		// }
		// ���ε�
		// payAmt = FDCHelper.add(payAmt, amt);//�ظ�
		// ��Ҫ��ȥ ����ر�ʱ �������뵥��Ӧ�����и���ڵĺ�ͬ�ڹ��̿λ��
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
						// �����κδ���
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

				if (b) {// �����˲��� ��ʾ������Ϣ���ж�
					FDCMsgBox.showError(cantMoreThanTotalSettlePrice);
					abort();
				} else {
					if (isMoreSettlement) {// ֻ�����˺�ͬ�Ƿ�ɽ��ж�ν��� û���� ���� ��ֻ ��ʾ
											// �����Լ�������
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
	 * �������������뵥���������п����޸ķ�Ʊ����Ʊ�š������깤����������������ԭ�ҡ�
	 * <p>
	 * 
	 * @author liang_ren969 @date:2010-5-21
	 *         <p>
	 *         <br>
	 *         ������
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
																			// ���ύ״̬�������ؼ�
																			// ��
																			// for
																			// R120719
																			// -
																			// 0117

			// �����������������еĿؼ�
			this.lockUIForViewStatus();

			// boolean s = isControlPay;

			/**
			 * �����˲���--δ�����ͬ��ʵ���������ʵ�ֲ�ֵʱ�Ƿ��ϸ����
			 */
			// if(!s){
			// ���ȸ��ؼ������ʵ�Ȩ�ޣ�Ȼ�������Ƿ�ɱ༭�Ϳ����ˣ�����ʹ��unloack������������ʹ���õĿؼ�����edit��״̬
			this.txtInvoiceNumber
					.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.txtInvoiceNumber.setEditable(true);

			this.txtInvoiceAmt
					.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.txtInvoiceAmt.setEditable(true);

			/**
			 * ϵͳ��������Ϊ���ʱ�򣬱����깤���������ڲ��ɱ༭��״̬
			 */
			if (!isAutoComplete) {
				this.txtcompletePrjAmt
						.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
				this.txtcompletePrjAmt.setEditable(true);
			}

			// R110513-0372�������д�غ� �����޸ı�ע����;���ύ���ʵ���տλ
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
				// R100709-147 ��������ȷ�ϵ�ʱ���������޸ģ�����ͨ����������ȷ�ϵ������޸�.
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
	 * description ��ȡ�����Ӧ�գ��������
	 * 
	 * @author ����
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
		BigDecimal totalPayedOriAmt = FDCHelper.ZERO; // �����뵥�Ѹ�ԭ��
		BigDecimal totalPayedAmt = FDCHelper.ZERO; // �����뵥�Ѹ�����
		BigDecimal lstPayedOriAmt = FDCHelper.ZERO; // ���������ۼ�ʵ��ԭ��
		BigDecimal lstPayedAmt = FDCHelper.ZERO; // ���������ۼ�ʵ������
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
					.info("PayRequestBillEditUI.getTotalPayAmtByThisReq�����ø������뵥�������μ�ʵ��SQL");
			logger.info("sql��" + builder.getSql());
			logger.info("paramater��" + builder.getParamaters());
			logger.info("�����뵥�Ѹ�ԭ�ң�totalPayedOriAmt��" + totalPayedOriAmt);
			logger.info("�����뵥�Ѹ����ң�totalPayedAmt��" + totalPayedAmt);
			logger.info("���������ۼ�ʵ��ԭ�ң�lstPayedOriAmt��" + lstPayedOriAmt);
			logger.info("���������ۼ�ʵ�����ң�lstPayedAmt��" + lstPayedAmt);
			logger
					.info("======================================================");
		}
		totalPayMap.put("totalPayedOriAmt", totalPayedOriAmt); // �����뵥�Ѹ�ԭ��
		totalPayMap.put("totalPayedAmt", totalPayedAmt); // �����뵥�Ѹ�����
		totalPayMap.put("lstPayedOriAmt", lstPayedOriAmt);
		totalPayMap.put("lstPayedAmt", lstPayedAmt);
		// return totalPayedAmt;
		return totalPayMap;
		/* modified by zhaoqin for R140227-0281 on 2014/03/21 end */
	}

	/**
	 * �����������뵥ǰ�Ĳ������
	 * <p>
	 * ��ͬ�Ƿ��ϴ����Ŀ��Ƹ������뵥����������ֵ������ʾ����ʾ���ƣ��ϸ���ơ�
	 * Ĭ��Ϊ����ʾ������ֵΪ����ʾʱ���������뵥�������ܺ�ͬ�Ƿ��ϴ����ĵĿ��ƣ�����ֵΪ��ʾ����ʱ��
	 * û���ϴ����ĵĺ�ͬ�����������뵥ʱ��������ʾ��Ϣ���Կ��Լ�������������ֵΪ�ϸ����ʱ�� û���ϴ����ĵĺ�ͬ�����������뵥ʱ���ϸ���Ʋ���������
	 * 
	 * @author owen_wen 2010-12-07
	 * @throws BOSException
	 * @throws EASBizException
	 * @see PayRequestBillListUI.checkParamForAddNew()
	 */
	private void checkParamForAddNew() {
		// ���ϴ����ģ������������뵥����Ҫ���
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
			logger.info("��ȡ���������쳣��" + e.getMessage());
			handUIExceptionAndAbort(e);
		} catch (BOSException e) {
			logger.info("��ȡ���������쳣��" + e.getMessage());
			handUIExceptionAndAbort(e);
		}

		if ("0".equals(returnValue)) { // �ϸ����
			FDCMsgBox.showInfo("�ú�ͬû���ϴ����ģ����������������뵥��");
			SysUtil.abort();
		} else if ("1".equals(returnValue)) { // ��ʾ����
			int confirmResult = FDCMsgBox.showConfirm2New(this,
					"�ú�ͬû���ϴ����ģ��Ƿ���������������뵥��");
			if (confirmResult == FDCMsgBox.NO
					|| confirmResult == FDCMsgBox.CANCEL) // ��Ϊ�û����԰�ESC�رմ��ڣ�
															// ��ʱconfirmResultΪFDCMsgBox
															// .CANCEL
				SysUtil.abort();
		}
	}

	/**
	 * �鿴Ԥ��
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
				FDCMsgBox.showWarning(this, "�ƻ������޷��鿴��");
			else {
				FDCMsgBox.showWarning(this, "��Ԥ�㣬���޷��鿴��");
			}
			abort();

			// FDCMsgBox.showWarning(this, "��Ԥ�㣬���޷��鿴��");
			// abort();
		} else if (obj instanceof FDCDepConPayPlanContractInfo) {
			curDept = ((FDCDepConPayPlanContractInfo) obj).getHead()
					.getDeptment().getName();
		} else if (obj instanceof FDCDepConPayPlanUnsettledConInfo) {
			curDept = ((FDCDepConPayPlanUnsettledConInfo) obj).getParent()
					.getDeptment().getName();
		}
		uiContext.put("curDept", curDept);
		// Ҫȡ�����ϵ�ҵ�����ڣ����������޸�ҵ�����ں�δ���棬�鿴Ԥ��ʱ�·���ʾ����ȷ Added by Owen_wen 2011-05-23
		String planMonth = DateTimeUtils.format(bookDate, "yyyy��MM��");
		uiContext.put("planMonth", planMonth);

		BigDecimal localBudget = getLocalBudget(firstDay, lastDay, projectId);
		uiContext.put("localBudget", localBudget);
		BigDecimal actPaied = FDCBudgetUtil.getActPaied(firstDay, lastDay,
				editData.getContractId());
		uiContext.put("actPaied", actPaied);

		/* modified by zhaoqin for R131218-0367 on 2013/12/25 start */
		// �����Ѹ����̿�
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
	 * ���ڿ���Ԥ��
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
		// �����Ѹ����̿�
		BigDecimal paid = FDCBudgetUtil.getPaid(firstDay, lastDay, editData
				.getContractId());
		// return localBudget.subtract(actPaied).subtract(floatFund);
		return localBudget.subtract(paid).subtract(floatFund);
		/* modified by zhaoqin for R131218-0367 on 2013/12/25 end */
	}

	/**
	 * ȡԤ�㣬����ǩ����ͬԤ��ʹ�ǩ����ͬԤ��2����������ݽ���F7ֵ�ж�<br>
	 * �Ӻ�ͬ��������ƻ���ȡֵ
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
	 * �ۼ����������ͬ������۸�����ʾ��Ϣ
	 * 
	 * @author owen_wen 2011-07-13
	 * @param isControlCost
	 *            ���ò��� FDC071_OUTPAYAMOUNT ���ۼ�������ͬ��������ϸ���ơ�
	 */
	private void showMsg4TotalPayReqAmtMoreThanConPrice(boolean isControlCost) {
		if (isControlCost) { // �ϸ����
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
	 * description ����"������ȷ�������븶�������Ƿ����"�����������ơ������깤�������������ȿ����������Ƿ�����
	 * 
	 * @author ����
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
		 * �÷����Ѿ����ʺ�Ŀǰ��ϵͳ�ˣ���У���߼�̫���ڼ���
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
		 * ��׼��Ʒ�����������뵥���������£� 1�����ӿ��ƣ�����FDC317�ڴ��ڸ������뵥�򹤳���ȷ�ϵ��ǲ������޸�
		 * 2����ȷ�ֶα��⣺����FDC317Ϊ�ǣ�������ʱ���������뵥���ֶΡ������깤��������
		 * ���ĳɡ�δ�����깤��������������FDC317Ϊ�񣬼�������ʱ���������뵥���ֶΡ������깤����������ʾ���仯
		 * 3�������ȿ����������Ʋ������������壬�ݲ������������������롱���=
		 * �����ȿ���������δ�����깤�������������߼����䣬���С�δ�����깤��������ȡֵ�����ݲ����޸��Ҳ��仯��
		 * ������������롰���ȿ�������ֵ�仯���㣬����0����֧�����븺����
		 */
		/* modified by zhaoqin for R140421-0018 on 2014/04/14 start */
		if (isSeparate) {
			this.contcompletePrjAmt.setBoundLabelText("�깤δ����");
		} else {
			this.contcompletePrjAmt.setBoundLabelText("�����깤������");
		}
		/* modified by zhaoqin for R140421-0018 on 2014/04/14 start */
	}

	/**
	 * description ��ʼ���ۼƷ�Ʊ���Һ�ԭ�� �������ݱ仯����
	 * 
	 * @author ����
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
		// ��Ʊ���ԭ��
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
				// �ۼƷ�Ʊ���ԭ��
				BigDecimal invoiceOriAmtSum = allInvoiceOriAmt.add(FDCHelper
						.toBigDecimal(invoiceOriAmt));

				// �ۼƷ�Ʊ������ֵ by tim_gao ȡ�� Ϊ0 �ж�,��Ϊ��һ�����ʱ�����0
				if (!FDCHelper.isEmpty(invoiceOriAmtSum)
						&& !FDCHelper.isEmpty(invoiceOriAmt)) {
					BigDecimal rate = FDCHelper.toBigDecimal(txtexchangeRate
							.getBigDecimalValue());
					txtInvoiceAmt.setNumberValue(invoiceOriAmt.multiply(rate));
				}
				// �۽���Ʊ���ԭ��/����
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
		// �ۼƷ�Ʊ���
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
					// Ϊ��ʱ�Ǳ�¼
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
	 * description ��ʾ�ۼƷ�Ʊ���Һ�ԭ�ҽ��
	 * 
	 * @author ����
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
	 * ��onload�д�����ڹ��������������ʾ���Ƶĳ�ʼ����д������ �������������빤��������߼���ʼ�����޸�Ҳ��д������
	 * 
	 * @author tim_gao
	 * @throws Exception
	 */
	protected void initParamOnLoadForWorkLoad() throws Exception {
		/**
		 * by 2012-03-19 ��2011�������������� ���պ�ͬ�����������ǰ�Ƿ���������ȷ��
		 */
		// ��Ϊ���ڹ��������ж��м����˺�ͬ������,���Һ�ͬ�����͸���һ���ж�����������
		isWorkLoadContarctType();

		if (isWorkLoadConType) {
			if (isAutoComplete && isFromProjectFillBill) {
				txtcompletePrjAmt.setRequired(false);
				txtpaymentProportion.setEditable(false);
				txtAllCompletePrjAmt.setEditable(false);
			}
			/**
			 * �깤�������ӽ���ϵͳȡֵ
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
				// û�в�����ʾ��
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
	 * �ú�ͬ�����Ƿ񹤳���ȷ�����ж��йع���������ؿ��� �����ط�����
	 * 
	 * @throws BOSException
	 * @remarks 1.fetchinitParam ��onload ʱ����Ϊû��ֵ,���Բ�����,��Ӱ�� 2.��Ϊcheck ��ʱ�����»ص���
	 *          fetchinitParam �����������
	 *          3.д��initParamOnLoadForWorkLoad������Ϊcontracttype �Ѿ���������,����Ҫȡ�����ж���
	 */
	protected void isWorkLoadContarctType() throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("id");
		sels.add("contractType.id");
		sels.add("contractType.isWorkLoadConfirm");
		FilterInfo filter = new FilterInfo();
		// by tim_gao ���ﱾ����Ӧ�������ı���ͬ�ж�һ�µ�,��Ϊ���ı���ͬĿǰ��û������,����Ϊnull,��Ϊ���ı�
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
			 * �����ж� 1�� ��ͬ���͹�����ȷ�� ��:��ԭ���߼����ݲ����ж� ���޹�������
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
	 * �����뵥�ۼ�ʵ������ң�ʵʱȡֵ - R140227-0281
	 * 
	 * @author RD_zhaoqin
	 * @date 2014/03/21
	 */
	private void setTotalPayForReqPay() {
		BigDecimal totalPayedOriAmt = null; // �����뵥�Ѹ�ԭ��
		BigDecimal lstPayedOriAmt = null; // ���������ۼ�ʵ��ԭ��
		BigDecimal lstPayedAmt = null; // ���������ۼ�ʵ������
		if (editData.getId() != null) {
			Map totalPayMap = getTotalPayAmtByThisReq(editData.getId()
					.toString());
			totalPayedOriAmt = (BigDecimal) totalPayMap.get("totalPayedOriAmt");
			totalPayAmtByReqId = (BigDecimal) totalPayMap.get("totalPayedAmt");
			lstPayedOriAmt = (BigDecimal) totalPayMap.get("lstPayedOriAmt");
			lstPayedAmt = (BigDecimal) totalPayMap.get("lstPayedAmt");
		}
		kdtEntrys.getCell(2, 7).setValue(totalPayedOriAmt); // �����뵥�Ѹ�ԭ��
		kdtEntrys.getCell(2, 10).setValue(totalPayAmtByReqId); // �����뵥�Ѹ�����
		// ���������ۼ�ʵ��ԭ�� = �����뵥�Ѹ�ԭ�� + �����ϴ��ۼ�ʵ��ԭ��
		kdtEntrys.getCell(5, 10).setValue(
				FDCHelper.add(lstPayedOriAmt, kdtEntrys.getCell(5, 2)
						.getValue()));
		// ���������ۼ�ʵ������ = �����뵥�Ѹ����� + �����ϴ��ۼ�ʵ������
		kdtEntrys.getCell(5, 11).setValue(
				FDCHelper.add(lstPayedAmt, kdtEntrys.getCell(5, 3).getValue()));

		logger.info("======================================================");
		logger.info("PayRequestBillEditUI.setTotalPayForReqPay�����ø������뵥�������μ�ʵ��");
		logger.info("�����ϴ��ۼ�ʵ��ԭ�ң�kdtEntrys.getCell(5, 2)��"
				+ kdtEntrys.getCell(5, 2).getValue());
		logger.info("�����ϴ��ۼ�ʵ�����ң�kdtEntrys.getCell(5, 3)��"
				+ kdtEntrys.getCell(5, 3).getValue());
		logger.info("�Ѹ�ԭ�ң�kdtEntrys.getCell(2, 7)��"
				+ kdtEntrys.getCell(2, 7).getValue());
		logger.info("�Ѹ�ԭ�ң�kdtEntrys.getCell(2, 10)��"
				+ kdtEntrys.getCell(2, 10).getValue());
		logger.info("���������ۼ�ʵ��ԭ�ң�kdtEntrys.getCell(5, 10)��"
				+ kdtEntrys.getCell(5, 10).getValue());
		logger.info("���������ۼ�ʵ�����ң�kdtEntrys.getCell(5, 11)��"
				+ kdtEntrys.getCell(5, 11).getValue());
		logger.info("======================================================");
	}
}