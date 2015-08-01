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

	private boolean isFirstLoad = true;// �Ƿ��һ�μ���,�������Ʊ��ļ�����ʾ

	private static final Color noEditColor = PayReqTableHelper.noEditColor;

	private int rowIndex = 4;// ��ͬ�ڹ��̿��к�

	private int columnIndex = 4;// ��ͬ�ڹ��̿��к�
	
	//�������뵥�ύʱ���Ƿ����ͬδ��ȫ���
	private boolean checkAllSplit = true;
	//��ʵ��Ϊ0ʱֻ��ѡ��Ԥ����Ŀ���
	private boolean isRealizedZeroCtrl = false;

	/**
	 * �����ۿ����
	 */
	private IUIWindow deductUIwindow = null;

	/**
	 * ���ڰ�cell����ֵ������map keyΪ���Ե�info��������valueΪcell������
	 */
	HashMap bindCellMap = new HashMap(20);

	private PayReqWebTableHelper tableHelper = new PayReqWebTableHelper(this);
	
    // �Ƿ�ʹ��Ԥ��
    protected boolean isMbgCtrl = false;
    //���ز����ǿ�Ʋ��������ϵͳ
    protected boolean isNotEnterCAS = false;
	protected FDCBudgetParam fdcBudgetParam= null;
    //�������,�Ѿ�����
    private BigDecimal payScale;
    //��Ӧ�� 
//    private SupplierCompanyInfoInfo supplierCompanyInfoInfo ;δʹ�ã���ע��
    //���ø������Ϊ��ͬ�ĸ������ �Ӹ���й���
    private int payTimes = 0;
    //�����
    private ContractChangeBillCollection contractChangeBillCollection = null;
    //���
    private BillBaseCollection paymentBillCollection = null;
    //�������뵥��Ӧ�Ľ�����
    private GuerdonOfPayReqBillCollection guerdonOfPayReqBillCollection = null;
    //������
    private GuerdonBillCollection guerdonBillCollection = null;
    //�������뵥��Ӧ��ΥԼ��
    private CompensationOfPayReqBillCollection compensationOfPayReqBillCollection = null;
    //�������뵥��Ӧ�ļ׹��Ŀۿ�
    private PartAOfPayReqBillCollection partAOfPayReqBillCollection = null;
    //�������뵥��Ӧ�ļ״�ȷ�ϵ����
    private PartAConfmOfPayReqBillCollection partAConfmOfPayReqBillCollection = null;
    //�ۿ�����
    private DeductTypeCollection deductTypeCollection = null;
    //������Ŀ��Ӧ�ĳɱ�����
    private FullOrgUnitInfo costOrg = null;

    //�Ƿ���ع���ʼ����
    private boolean hasFetchInit = false;
    
    //�ۼ�������ͬ��������ϸ����
    private boolean isControlCost = false;
    
    //��;�ֶ��ܿ�
    protected int usageLegth = 90;
    
    //�������뵥�տ����к��տ��˺�Ϊ��¼��
    private boolean isBankRequire = false;
    
    //���뵥���ȿ������Զ�Ϊ100%
    private boolean isAutoComplete = false;
    
    //�׹���ϵͳ����
    private boolean partAParam = false;
    
    //������ȱ���
    private boolean payProcess = false ;
    
    
    //�������뵥��ͬID
    private String payReqContractId = null;
    
    //�������뵥��ͬ�Ƿ�׹��ĺ�ͬ
    public static boolean isPartACon = false;
    
    // ��ģʽһ�廯
	private boolean isSimpleFinancial = false;

	// �������ԭ��
	private BigDecimal lastestPriceOriginal = FDCHelper.ZERO;

	// �ۼƷ�Ʊ���/
	private BigDecimal allInvoiceAmt = FDCHelper.ZERO;

	// �ۼ����깤������/
	private BigDecimal allCompletePrjAmt = FDCHelper.ZERO;

	// �򵥲���ɱ�һ�廯����ۿΥԼ������
	private boolean isSimpleFinancialExtend = false;

	// ������ȷ�������븶�������Ƿ����
	protected boolean isSeparate = false;
	
	// �������뵥�����ı���ͬ��Ʊ�š���Ʊ����¼
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
		
		this.toolBar.add(btnAuditResult);
		actionAuditResult.setVisible(true);
		actionAuditResult.setEnabled(true);
		
		actionContractAttachment.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_view"));
		this.toolBar.add(btnContractAttachment);
		actionContractAttachment.setVisible(true);
		actionContractAttachment.setEnabled(true);
	}

	// �仯�¼�
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

	// ҵ�����ڱ仯�¼�
	ControlDateChangeListener bookedDateChangeListener = new ControlDateChangeListener(
			"bookedDate");

	ControlDateChangeListener amountListener = new ControlDateChangeListener(
			"amount");

	ControlDateChangeListener completePrjAmtListener = new ControlDateChangeListener(
			"completePrjAmt");

	ControlDateChangeListener paymentProptListener = new ControlDateChangeListener(
			"paymentProp");

	// ������
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

		// ��������������깤�������ļ���
		initPaymentProp();
		// setAutoNumber();
		if (editData.getUrgentDegree() == null)
			editData.setUrgentDegree(UrgentDegreeEnum.NORMAL);
		super.loadFields();
		if(OprtState.ADDNEW.equals(getOprtState())){
			txtpaymentProportion.setValue(FDCHelper.ZERO);
			txtcompletePrjAmt.setValue(FDCHelper.ZERO);
		}
		// ���±�λ����ʾ
		//setAmount();

		// ���ø������Ϊ��ͬ�ĸ������ �Ӹ���й���
		if (editData.getState() != FDCBillStateEnum.AUDITTED) {
			editData.setPayTimes(payTimes);
		}
		// /*
		// * ���/����˰�ť��״̬
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
			// ��д���Ϊ��λ�ҽ��
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
	
	//���þ���
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
		// ����¼�ڵ����ݴ洢��info
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

			// �������
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
//		 * setCostAmount�Ѵ���
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
			
			//��λ��
			baseCurrency = (CurrencyInfo)initData.get(FDCConstants.FDC_INIT_CURRENCY);
			//
			company = (CompanyOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_COMPANY);
			//��ͬ����
			contractBill = (ContractBillInfo)initData.get(FDCConstants.FDC_INIT_CONTRACT);
			//�������
			payScale = (BigDecimal)initData.get("payScale");
			//��Ӧ��
//			supplierCompanyInfoInfo = (SupplierCompanyInfoInfo)initData.get("supplierCompanyInfoInfo");
			//���ø������Ϊ��ͬ�ĸ������ �Ӹ���й���
			payTimes = ((Integer)initData.get("payTimes")).intValue();
		    //�����
			contractChangeBillCollection =  (ContractChangeBillCollection)initData.get("ContractChangeBillCollection");;
		    //���
		     paymentBillCollection = (BillBaseCollection)initData.get("PaymentBillCollection");;
		    //�������뵥��Ӧ�Ľ�����
		     guerdonOfPayReqBillCollection = (GuerdonOfPayReqBillCollection)initData.get("GuerdonOfPayReqBillCollection");;
		    //������
		     guerdonBillCollection = (GuerdonBillCollection)initData.get("GuerdonBillCollection");;
		    //�������뵥��Ӧ��ΥԼ��
		     compensationOfPayReqBillCollection = (CompensationOfPayReqBillCollection)initData.get("CompensationOfPayReqBillCollection");;
		    //�������뵥��Ӧ�ļ׹��Ŀۿ�
		     partAOfPayReqBillCollection = (PartAOfPayReqBillCollection) initData.get("PartAOfPayReqBillCollection");
		    //�������뵥��Ӧ�ļ׹���ȷ�ϵ����
		     partAConfmOfPayReqBillCollection = (PartAConfmOfPayReqBillCollection)initData.get("PartAConfmOfPayReqBillCollection");
		     //�ۿ�����
		     deductTypeCollection = (DeductTypeCollection)initData.get("DeductTypeCollection");;
		    //������Ŀ��Ӧ�ĳɱ�����
		    costOrg = (FullOrgUnitInfo)initData.get("FullOrgUnitInfo");
		    
		    //����
			bookedDate = (Date)initData.get(FDCConstants.FDC_INIT_DATE);
			if(bookedDate==null){
				bookedDate = new Date();
			}
			serverDate = (Date)initData.get("serverDate");
			if(serverDate==null){
				serverDate = bookedDate;
			}
			//��ǰ�ڼ�
			curPeriod = (PeriodInfo) initData.get(FDCConstants.FDC_INIT_PERIOD);
			
			curProject = (CurProjectInfo) initData.get(FDCConstants.FDC_INIT_PROJECT);
			
			orgUnitInfo = (FullOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_ORGUNIT);
			if(orgUnitInfo==null){
				orgUnitInfo = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
			}
			lastestPriceOriginal = (BigDecimal)initData.get("lastestPriceOriginal");
	}
	
	//�����������
	protected  void fetchInitParam() throws Exception{

		//�Ƿ�����Ԥ�����
		if(orgUnitInfo!=null){
//			HashMap param = FDCUtils.getDefaultFDCParam(null,orgUnitInfo.getId().toString());
			Map param = null;//(Map)ActionCache.get("FDCBillEditUIHandler.orgParamItem");
			/*if(param==null){
				param = FDCUtils.getDefaultFDCParam(null,orgUnitInfo.getId().toString());	
			}*/
			//update by renliang
			param = FDCUtils.getDefaultFDCParam(null,orgUnitInfo.getId().toString());
			
		    isMbgCtrl = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_STARTMG).toString()).booleanValue();
			
		    //�������뵥������������ɸ�����
		    isControlCost = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_OUTPAYAMOUNT).toString()).booleanValue();		    
			
			//���뵥���ȿ������Զ�Ϊ100%
		    isAutoComplete = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_PAYPROGRESS).toString()).booleanValue();		    
			
			//�������
			if(param.get(FDCConstants.FDC_PARAM_PAYPROGRESS)!=null){
				payProcess = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_PAYPROGRESS).toString()).booleanValue();
			}	
		}

		if (company == null) {
			return;
		}
		//���óɱ�����һ�廯
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
		
		// ���óɱ��½�
		if (paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION) != null) {
			isIncorporation = Boolean.valueOf(
					paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION).toString())
					.booleanValue();
		}

		// ��ģʽ��һ�廯
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
		
		//��;�ֶ��ܿ�
		if(paramItem.get("CS050")!=null){
			usageLegth = Integer.valueOf(paramItem.get("CS050").toString()).intValue();
		}	
		
	    //�������뵥�տ����к��տ��˺�Ϊ��¼��
		if(paramItem.get(FDCConstants.FDC_PARAM_BANKREQURE)!=null){
			isBankRequire = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_BANKREQURE).toString()).booleanValue();
		}
		
		//���ز����ǿ�ƽ������ϵͳ
		if(paramItem.get(FDCConstants.FDC_PARAM_NOTENTERCAS)!=null){
			isNotEnterCAS = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_NOTENTERCAS).toString()).booleanValue();
		}
		
		//�׹���
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

		//�ۼƷ�Ʊ���
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
		// ����ԭ�ҽ��Ŀ�¼�뷶Χ
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
			// �����������ܸ����޿����������ܸ����ȿ�
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
		
		// ����״̬����������۵ĵ���
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

		// ���ݲ��������Ƿ��¼
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
		 * ϵͳ��������Ϊ���ʱ�����ؽ��ȸ�������ͱ����깤���������
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

        //ҵ�������ж�Ϊ��ʱȡ�ڼ��ж�
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

	// ҵ�����ڱ仯����,�ڼ�ı仯
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

		// �鿴״̬��Ҳ���Է����
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

		// ��ʾinfo�����ݵ����
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
		// �������п��Ը��ƺ�ͬ����
		if(editData!=null&&editData.getId()!=null&&FDCUtils.isRunningWorkflow(editData.getId().toString())){
			kdtEntrys.setEnabled(true);
		}
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

		if (!getOprtState().equals(OprtState.ADDNEW) && !getOprtState().equals(OprtState.EDIT)) {
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

		// �������������ݣ�δ���޸ģ�ֱ�ӹر�ʱ�����ֱ�����ʾ, ���ɻ�����ܴ���
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

		//��Ʊ
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
		if (editData != null) {// ��һ�α���ʱ��ʼ״̬
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

		// �����״̬
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
				 * MsgBox.showWarning(this,"��ѡ����Ǳ�λ�ң����ǻ��ʲ�����1"); }
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
			MsgBox.showInfo("��ǰ�������뵥���������ı���ͬ�����ı���ͬ�������뵥����ֱ�����ɣ��������ı���ͬ�Զ�����");
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
			// �뱣�澯��
			MsgBox.showWarning(this, getRes("beforeAttachment"));
			SysUtil.abort();
		}
		super.actionAttachment_actionPerformed(e);
	}

	/*
	 * ������ӵĹ�������ť
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
		actionEdit.setEnabled(false);// �����޸�
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
			// �����ύ��
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
			// �뱣�澯��
			MsgBox.showWarning(getRes("beforeAdjustDeduct"));
			SysUtil.abort();
		}
		
		// �ұ�
//		CurrencyInfo cur = editData.getCurrency();
//		if (!cur.getId().toString().equals(baseCurrency.getId().toString())) {
//			// �뱣�澯��
//			MsgBox.showWarning("��Ҳ������������");
//			SysUtil.abort();
//		}

		super.actionAdjustDeduct_actionPerformed(e);
		showSelectDeductList(e);
	}

	public void actionClose_actionPerformed(ActionEvent e) throws Exception {
		if (OprtState.ADDNEW.equals(getOprtState())) {
			// �뱣�澯��
			MsgBox.showWarning(getRes("beforeClose"));
			SysUtil.abort();
		}
		super.actionClose_actionPerformed(e);
		if (editData != null && editData.getId() != null
				&& editData.isHasClosed()) {
			MsgBox.showWarning(this, "�������뵥�Ѿ��رգ�����Ҫ�ٹر�");
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
			MsgBox.showWarning(this, "�������뵥δ�رգ�����Ҫ���ر�");
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
		// ȡ���ݿ��ڵ���������
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
		// TODO �ϲ��ͬ����ƻ�
		// super.actionTraceUp_actionPerformed(e);
		if (editData != null && editData.getId() != null
				&& PayReqUtils.isContractBill(editData.getContractId())) {
			String contractId = editData.getContractId();
			ContractPayPlanEditUI.showEditUI(this, contractId, "VIEW");
		} else {
			MsgBox.showWarning(this, "û�к�ͬ�ƻ�");
		}
	}

	private void showSelectDeductList(ActionEvent e) throws Exception {

		boolean canAdjust = checkCanSubmit();
		String state = canAdjust ? getOprtState() : OprtState.VIEW;

		// uiWindow=null;//��ʱÿ�ζ�ʵ��һ��UIWindow
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

		// ��������
//		objectValue.setPayDate(DateTimeUtils.truncateDate(new Date()));
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
		objectValue.setOrgUnit(costOrg);
		// editData.setCU(curProject.getCU());

		objectValue.setBookedDate(bookedDate);
		objectValue.setPeriod(curPeriod);

		// �Ƿ���Ҫ����
		objectValue.setIsPay(true);

		if (isAutoComplete) {
			objectValue.setPaymentProportion(FDCConstants.ONE_HUNDRED);
		}

		// �����ۼƽ����
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
		// ��Ʊ����
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

	// ��ͬ�����뵥
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
				if(!isSimpleFinancial){
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

			// ����ʵ���տλ���õ������ʻ��͸�������
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
		if (isIncorporation && ((FDCBillInfo) editData).getPeriod() == null) {
			MsgBox.showWarning(this, "���óɱ��½��ڼ䲻��Ϊ�գ����ڻ�������ά���ڼ������ѡ��ҵ������");
			SysUtil.abort();
		}
		return super.checkCanSubmit();
	}

	protected void verifyInput(ActionEvent e) throws Exception {

		super.verifyInput(e);

		/*
		 * //�������͵Ŀ��� 1. �����ͬ����֮���������������ĸ������뵥 2. ���˽����֮����ܸ����޿�ѿ��ƣ� 3.
		 * ���޿��ܽ��ܴ��ڽ��㵥�ϵ��ʱ���
		 */

		/*
		 * if(e.getSource()!=btnSubmit){
		 * if(editData.getNumber()==null||editData.getNumber().length()<1){
		 * MsgBox.showWarning(this, getRes("NullNumber")); SysUtil.abort();
		 * }else{ return; } }
		 */
		/*
		 * ���ԭ�ҽ���뵥Ԫ���ڵķ�����ʵ������Ƿ�һ��
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
						String msg = "���깤��������ʵ������ͬʱΪ0��";
						if(isSimpleFinancial){
							msg = "ʵ������Ϊ0!";
						}
						//Ԥ����
						if(isAdvance()){
							if (FDCHelper.ZERO.compareTo(FDCHelper
									.toBigDecimal(getDetailTable().getCell(
											rowIndex, columnIndex).getValue())) == 0
									&& FDCHelper.ZERO.compareTo(FDCHelper.toBigDecimal(getDetailTable().getCell(
											rowIndex+1, columnIndex).getValue())) == 0) {
								msg = "ʵ������Ԥ�����ͬʱΪ 0!";
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
//					MsgBox.showError(this, "��������������0,С�ڵ���100%");
//					SysUtil.abort();
//				} else if (FDCHelper.toBigDecimal(completeAmt).signum() == 0) {
//					MsgBox.showError(this, "���깤�������������0");
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
//						MsgBox.showError(this, "���������ԭ�ҽ��/���깤������ *100% ��ϵ������");
//						SysUtil.abort();
//					}
					Object amount = ((ICell) bindCellMap.get(PayRequestBillContants.PROJECTPRICEINCONTRACT))
							.getValue();
					if(amount == null){
						MsgBox.showError(this, "���������Ϊ�գ�");
						SysUtil.abort();
					}
					if (isSaveAction()) {
						if (isSimpleFinancial) {
							if (FDCHelper.toBigDecimal(amount, 2).compareTo(
									lastestPrice) > 0) {
								int ok = MsgBox.showConfirm2(this,
										"ʵ�������ں�ͬ�������,�Ƿ񱣴棿");
								if (ok != MsgBox.OK) {
									SysUtil.abort();
								}
							}
						}
						if (completeAmt.compareTo(lastestPrice) > 0) {
							int ok = MsgBox.showConfirm2(this,
									"���깤�����������ں�ͬ�������,�Ƿ񱣴棿");
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
					&&type.getName()!=null&&!type.getName().equals("Ԥ����")){
				MsgBox.showError(prmtPayment, "��ʵ�ֲ�ֵΪ0ֻ����ѡ��\"Ԥ����\"��");
				SysUtil.abort();
			}
		}
		
		//��Ʊ�Ƿ��¼
		if (isInvoiceRequired) {
			boolean isNotInput = false;
			if (txtInvoiceAmt.getBigDecimalValue() == null) {
				// Ϊ��ʱ�ɲ�¼
				if (!FDCHelper.ZERO.equals(txtInvoiceAmt.getBigDecimalValue())) {
					isNotInput = true;
				}
			} else if (FDCHelper.ZERO.compareTo(txtInvoiceAmt
					.getBigDecimalValue()) != 0
					&& FDCHelper.isEmpty(txtInvoiceNumber.getText())) {
				isNotInput = true;
			}
			if (isNotInput) {
				MsgBox.showWarning(this, "��Ʊ���뷢Ʊ������¼��!");
				SysUtil.abort();
			}
		}
		if (FDCHelper.toBigDecimal(txtAllInvoiceAmt.getBigDecimalValue())
				.setScale(2, BigDecimal.ROUND_HALF_UP).compareTo(lastestPrice) == 1) {
			MsgBox.showWarning(this, "�ۼƷ�Ʊ���ܳ�����ͬ������ۣ�");
			SysUtil.abort();
		}
	}

	/**
	 * Description:����
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
		// �����տ�����,�տ��˺ŵ�
		super.supplier_dataChanged(e);
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
			
			//��Ӧ�̵Ļ�ȡ			
			String supperid = supplierid.toString();			
			PayReqUtils.fillBank(editData,supperid,curProject.getCU().getId().toString());	
			txtrecAccount.setText(editData.getRecAccount());
			txtrecBank.setText(editData.getRecBank());
		}
	}

    public IObjectPK runSubmit() throws Exception
    {
		//Ԥ�����
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
		// �������������
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
						// ��д���Ϊ��λ�ҽ��
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
						 * BigDecimal&&((BigDecimal)exchangeRate).intValue()!=1) {
						 * MsgBox.showWarning(this,"��ѡ����Ǳ�λ�ң����ǻ��ʲ�����1"); }
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
						 * BigDecimal&&((BigDecimal)exchangeRate).intValue()!=1) {
						 * MsgBox.showWarning(this,"��ѡ����Ǳ�λ�ң����ǻ��ʲ�����1"); }
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

	public IUIObject getInstance(Map uiContext) {
		return super.getInstance(uiContext);
		/*
		 * PayRequestBillEditUI ui=null; try { ui=new PayRequestBillEditUI(); }
		 * catch (Exception e) { handUIException(e); } return ui;
		 */
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
			// ��ȡ���ж���������Ķ���ID
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
	 * ���ø�����������깤��������ԭ�ҽ��֮��Ĺ�ϵ�� ���깤��������ԭ�ҽ��¸������
	 * 
	 * @author sxhong Date 2007-3-12
	 */
	private void setPropPrjAmount(String cause, DataChangeEvent e) {
		if (isFirstLoad || (!txtpaymentProportion.isRequired())
				|| (isSeparate && contractBill!=null&&contractBill.isIsCoseSplit()))
			return;

		// if(PayReqUtils.isContractWithoutText(editData.getcon))
		// BigDecimal amount = txtAmount.getBigDecimalValue();
		// �ñ�λ�ҽ��м���
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
	 * ��������������깤�������ļ��� ��onLoad�е���
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
	 * �������뵥�ۼƶ���ں�ͬ����������ʱ���ѣ��ύ��������
	 * 
	 * @param ctx
	 * @param billInfo
	 * @throws BOSException
	 */
	private void checkAmt(PayRequestBillInfo billInfo) throws Exception {
		BigDecimal latestPrice = FDCHelper.toBigDecimal(billInfo.getLatestPrice(), 2);
		// if (billInfo.getLatestPrice() == null) {//֧�ֽ���Ϊ��ֵ
		// return;
		// }
		/*********
		 * �������뵥�ıұ𣬱���ͺ�ͬ�ıұ���ͬ 2008-11-14 ���� �ұ��Ƿ��Ǳ��� ����Ǳ�����Ƚϱ��� ����������Ƚ�ԭ��
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
				total = total.add(FDCHelper.toBigDecimal(info.getOriginalAmount()));// ͳ��ԭ�ҽ��
				// (
				// ��ͬ�ڹ��̿�
				// +
				// ����
				// -
				// �ۿ�
				// )
//				totalLocal = totalLocal.add(FDCHelper.toBigDecimal(info.getAmount()));// ͳ��ԭ�ҽ��
				// (
				// ��ͬ�ڹ��̿�
				// +
				// ����
				// -
				// �ۿ�
				// )
			completeTotal = completeTotal.add(FDCHelper.toBigDecimal(info//
					.getCompletePrjAmt()));// ������ɽ����깤+����-����-�ۿ
			projectPriceInContractTotal = projectPriceInContractTotal.add(FDCHelper.toBigDecimal(info.getProjectPriceInContract()));// ��ͬ�ڹ��̿�
			// ��
			// ���ڷ���ԭ��
			// ��
			boolean isKeepAmt = false;
			if (info.getPaymentType() != null && info.getPaymentType().getPayType() != null && info.getPaymentType().getPayType().getId().toString().equals(PaymentTypeInfo.keepID)) {
				isKeepAmt = true;
			}
			BigDecimal temp = FDCHelper.ZERO;
			BigDecimal tempLocal = FDCHelper.ZERO;
			BigDecimal _tempActuallyPayOriAmt = FDCHelper.ZERO;// �ۼ�ʵ����ԭ����ʱ����
			BigDecimal _tempActuallyPayLocalAmt = FDCHelper.ZERO;// �ۼ�ʵ����ԭ����ʱ����
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
			// ����������뵥�Ѿ����������Ѿ��й����ĸ��
			if (FDCBillStateEnum.AUDITTED.equals(info.getState())) {
				int tempInt = info.getEntrys().size();
				for (int j = 0; j < tempInt; j++) {
					PaymentBillInfo payment = info.getEntrys().get(j).getPaymentBill();
					if (payment != null && payment.getBillStatus() == BillStatusEnum.PAYED) { // ���Ҹø���Ѿ�����
						temp = temp.add(FDCHelper.toBigDecimal(payment.getAmount()));
						tempLocal = tempLocal.add(FDCHelper.toBigDecimal(payment.getLocalAmt()));
						payAmt = payAmt.add(FDCHelper.toBigDecimal(payment.getAmount()));
						payAmtLocal = payAmtLocal.add(FDCHelper.toBigDecimal(payment.getLocalAmt()));
					} else if (payment != null && payment.getBillStatus() != BillStatusEnum.PAYED) {// δ����
																									// ��
																									// ��Ҫ��¼һ������δ�����
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
				if (!info.isHasClosed()) {// �ѹرյĲ�Ӧ�ð�����ȥ
					noPayAmt = FDCHelper.add(noPayAmt, FDCHelper.subtract(info.getOriginalAmount(), temp));// ���������
																											// -
																											// ������Ӧ����ĸ�����
																											// =
																											// ����δ�����
					noPayAmtLocal = FDCHelper.add(noPayAmtLocal, FDCHelper.subtract(info.getAmount(), tempLocal));// ���������
																													// -
																													// ������Ӧ����ĸ�����
																													// =
																													// ����δ�����
				}
			} else {// ��û�и��
				temp = FDCHelper.toBigDecimal(info.getOriginalAmount());
				tempLocal = FDCHelper.toBigDecimal(info.getAmount());
				if (!info.isHasClosed()) {// �ѹرյĲ�Ӧ�ð�����ȥ
					noPayAmt = FDCHelper.add(noPayAmt, FDCHelper.toBigDecimal(info.getOriginalAmount()));
					noPayAmtLocal = FDCHelper.add(noPayAmtLocal, FDCHelper.toBigDecimal(info.getAmount()));
				}
			}
			if (!isKeepAmt) {
				// ����������뵥�Ѿ����������Ѿ��й����ĸ��.��ô�ڽ���"���ȿ�+�����ܳ�����ͬ�����-���޽�"У���ʱ��
				// ���ȿ�ͽ�����Ӧ��ȡ����ϵĽ��������������ϵ� by cassiel_peng 2009-12-06
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
			 * "�ڽ��С����ȿ�+�����ܳ�����ͬ�����-���޽�"���߼��ж�ʱ����ԭ��֮ǰϵͳ��֧�ֺ�ͬ�Ķ�ʽ��� by
			 * Cassiel_peng
			 */
			if (rowSet.size() == 1) {
				rowSet.next();
				BigDecimal amount = FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"), 2);
				if (isBaseCurrency) {
					noKeepAmtLocal = FDCHelper.add(noKeepAmtLocal, billInfo.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
					if (noKeepAmtLocal.compareTo(amount) > 0) {
						MsgBox.showError(this, "���ң����ȿ�+�����ܳ�����ͬ�����-���޽�");
						SysUtil.abort();
					}
				} else {
					noKeepAmt = FDCHelper.add(noKeepAmt, billInfo.getOriginalAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
					if (noKeepAmt.compareTo(amount) > 0) {
						MsgBox.showError(this, "ԭ�ң����ȿ�+�����ܳ�����ͬ�����-���޽�");
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
				 * ȡԭ�ҵı��޽���
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
						MsgBox.showError(this, "���ң���ͬ�ۼ��Ѹ����޿������ͬ���㱣�޽��!��");
						SysUtil.abort();
					}
				} else {
					keepAmt = FDCHelper.add(keepAmt, billInfo.getOriginalAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
					if ((keepAmt.compareTo(amount) > 0)) {
						MsgBox.showError(this, "ԭ�ң���ͬ�ۼ��Ѹ����޿������ͬ���㱣�޽��!��");
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
				// ��ͬ�������
				map = FDCUtils.getLastAmt_Batch(null, new String[] { billInfo.getContractId() });
				if (map != null && map.size() == 1) {
					conLastestPrice = (BigDecimal) map.get(billInfo.getContractId());
				}
				payRate = FDCHelper.divide(FDCHelper.multiply(conLastestPrice, payPercForWarn), FDCHelper.ONE_HUNDRED);
				totalpayAmountLocal = FDCHelper.toBigDecimal(totalpayAmountLocal, 2);
				// totalpayAmount = FDCHelper.toBigDecimal(totalpayAmount, 2);

				if (totalpayAmountLocal.compareTo(payRate) > 0) {
					String str = "���ң���ǰ���ݺ�ͬ�µ��ۼ�ʵ����+δ�������뵥�����˸�����ʾ����:";
					str = str + "\n�ۼƽ��:" + totalpayAmountLocal + " ����,ʵ������" + FDCHelper.toBigDecimal(payAmtLocal, 2) + "  ����δ����:" + FDCHelper.toBigDecimal(noPayAmtLocal, 2);
					str = str + "\n������ʾ������" + payRate + "(" + conLastestPrice + "*" + payPercForWarn + "%)";
					if ("0".equals(allPaidMoreThanConPrice())) {// �ϸ����
						MsgBox.showDetailAndOK(this, "����������ʾ����,��鿴��ϸ��Ϣ", str, 2);
						SysUtil.abort();
					} else if ("1".equals(allPaidMoreThanConPrice())) {// ��ʾ����
						MsgBox.showDetailAndOK(this, "����������ʾ����,��鿴��ϸ��Ϣ", str, 1);
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
			// MsgBox.showDetailAndOK(this, "����������ʾ����,��鿴��ϸ��Ϣ", str, 1);
			// }
			// }
		}
		/******
		 * �ж��������
		 */
		if (isBaseCurrency) {
//			if (totalLocal.compareTo(latestPrice) > 0) {
//				// �ϸ���Ʋ������ύ
//				if (isControlCost) {
//					MsgBox.showError(this, "��ͬ�¸������뵥���ۼƽ��(����)���ں�ͬ�������(����)��");
//					SysUtil.abort();
//				} else {
//					int result = MsgBox.showConfirm2(this, "��ͬ�¸������뵥���ۼƽ��(����)���ں�ͬ�������(����).�Ƿ��ύ?");
//					if (result != MsgBox.OK) {
//						SysUtil.abort();
//					}
//				}
//			}
		} else {
			/**********
			 * ��Ҫʹ��ԭ�ҵ�������۱Ƚ�
			 * 
			 */
			total = FDCHelper.toBigDecimal(total, 2);
			lastestPriceOriginal = FDCHelper.toBigDecimal(lastestPriceOriginal, 2);
			if (total.compareTo(lastestPriceOriginal) > 0) {
				// �ϸ���Ʋ������ύ
				if (isControlCost) {
					MsgBox.showError(this, "��ͬ�¸������뵥���ۼƽ��(ԭ��)���ں�ͬ�������(ԭ��)��");
					SysUtil.abort();
				} else {
					int result = MsgBox.showConfirm2(this, "��ͬ�¸������뵥���ۼƽ��(ԭ��)���ں�ͬ�������(ԭ��).�Ƿ��ύ?");
					if (result != MsgBox.OK) {
						SysUtil.abort();
					}
				}
			}
		}
		BigDecimal totalReqAmt = payAmtLocal.add(billInfo.getAmount());
		if (totalReqAmt.compareTo(latestPrice) == 1) {
			if(isControlCost){
				MsgBox.showWarning(this, "\"��������+�ۼ�ʵ��\" ���ܴ��ں�ͬ�������!");
				SysUtil.abort();
			}
		}

		if (isSimpleFinancial) {
			if (billInfo.getPaymentType() == null || billInfo.getPaymentType().getPayType() == null) {
				MsgBox.showError(this, "������������");
				SysUtil.abort();
			}
			if (FDCHelper.toBigDecimal(projectPriceInContractTotal, 2).compareTo(latestPrice) > 0) {
				String msg = "��ͬ�¸������뵥���ۼ�ʵ�������ں�ͬ�������.�Ƿ��ύ?";
				int result = MsgBox.showConfirm2(this, msg);
				if (result != MsgBox.OK) {
					SysUtil.abort();
				}
			}
			if (!isAutoComplete && contractBill != null && contractBill.isHasSettled()) {
				if (FDCHelper.toBigDecimal(completeTotal, 2).compareTo(latestPrice) > 0) {
					String msg = "��ͬ�¸������뵥���ۼ����깤���������ܴ��ں�ͬ�������";
					MsgBox.showWarning(this, msg);
					SysUtil.abort();
				}
			}
			return;
			// ��ģʽ���˽���
		}
		if (FDCHelper.toBigDecimal(completeTotal, 2).compareTo(latestPrice) > 0) {
			if (billInfo.getPaymentType() == null || billInfo.getPaymentType().getPayType() == null) {
				MsgBox.showError(this, "������������");
				SysUtil.abort();
			}
			if (billInfo.getPaymentType().getPayType().getId().toString().equals(PaymentTypeInfo.progressID)) {
				String msg = "��ͬ�¸������뵥���ۼ����깤���������ں�ͬ�������.�Ƿ��ύ?";
				int result = MsgBox.showConfirm2(this, msg);
				if (result != MsgBox.OK) {
					SysUtil.abort();
				}
			}
		}
		if (isBaseCurrency) {
			BigDecimal totalLocal = FDCHelper.add(ContractClientUtils.getReqAmt(billInfo.getContractId()), billInfo.getAmount());
			//�������޸�ʱ�ظ���¼����Ҫ��ȥ���ŵ���֮ǰ���������ݿ����ֵ��
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
				// �ϸ���Ʋ������ύ
				if (isControlCost) {
					MsgBox.showError(this, "��ͬ�¸������뵥���ۼƽ��(����)���ں�ͬ�������(����)��");
					SysUtil.abort();
				} else {
					int result = MsgBox.showConfirm2(this, "��ͬ�¸������뵥���ۼƽ��(����)���ں�ͬ�������(����).�Ƿ��ύ?");
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
	 * ���ǳ����ദ�����������Ϊ,ͳһ��FDCBillEditUI.handCodingRule�����д���
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
		uiContext.put("source", "listBase"); // �����빤��������������
		// ����UI������ʾ
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

	//�鿴Ԥ�����
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
//				//����longnumber�Ա���Ԥ��ƥ��
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
				// ��ͬʱ����Ȼ�����ݿ���ȡ��ͬ�����µĸ���ƻ�
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
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		if (editData != null) {
			filter.appendFilterItem("contractId", editData.getContractId());
		}
		// ��ʱ����˳�����֮ǰ�ĵ�
		filter.getFilterItems().add(
				new FilterItemInfo("createTime", editData.getCreateTime(),
						CompareType.LESS));
		view.getSelector().add("amount");
		view.getSelector().add("entrys.paymentBill.billStatus");
		view.getSelector().add("entrys.paymentBill.amount");
		//����Ƿ����Ӧ���ø���ı��Ҽ��㣬������ԭ��
		view.getSelector().add("entrys.paymentBill.localAmt");
		PayRequestBillCollection c = PayRequestBillFactory.getRemoteInstance()
				.getPayRequestBillCollection(view);
		BigDecimal totalpayAmount = FDCHelper.ZERO;// �ۼ�ʵ����+δ�������뵥
		for (int i = 0; i < c.size(); i++) {
			final PayRequestBillInfo info = c.get(i);
			if (info.getId().equals(editData.getId())) {
				// �ų�������
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

	// �����״̬
	private void checkContractSplitState() {
//		if(!isIncorporation){
//			return ;
//		}
		//����ύʱ���Ƿ����ͬδ��ȫ���
		if(!checkAllSplit){
			return;
		}
		String contractBillId = (String) getUIContext().get("contractBillId");
		if (contractBillId != null) {
			if (!ContractClientUtils.getContractSplitState(contractBillId)) {
				// ��Ӧ�ĺ�ͬ��δ���в�֣����ܽ��д˲�����
				MsgBox.showWarning(this, FDCSplitClientHelper
						.getRes("conNotSplited"));
				SysUtil.abort();
			}
		}
	}
	
    // ����ʱ������Ԥ�����
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
	 * �����ʱ�����Ԥ����
	 * @throws Exception 
	 */
	private void checkFdcBudget() throws Exception{
//		FDCBudgetPeriodInfo period=getFDCBudgetPeriod();
		try {
			Map retMap = FDCBudgetAcctFacadeFactory.getRemoteInstance().invokeBudgetCtrl(this.editData, FDCBudgetConstants.STATE_SUBMIT);
			//��ֹĳЩ�û���δ�ύʱ�޸Ĳ�������ȡ
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
			//�����ݿ�ȡ
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
			//¼��׹���
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
		//�����0������������깤����
		if(!isZeroPay)
			setCompletePrjAmt();
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
						&& !type.getName().equals("Ԥ����")) {
					EventListener[] listeners = prmtPayment
							.getListeners(DataChangeListener.class);
					for (int i = 0; i < listeners.length; i++) {
						prmtPayment
								.removeDataChangeListener((DataChangeListener) listeners[i]);
					}
					prmtPayment.setData(eventObj.getOldValue());
					MsgBox.showError(prmtPayment, "��ʵ�ֲ�ֵΪ0ֻ����ѡ��\"Ԥ����\"��");
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
																	// �����
				String progressID = PaymentTypeInfo.progressID;// "Ga7RLQETEADgAAC6wKgOlOwp3Sw=";//
																// ���ȿ�
				String keepID = PaymentTypeInfo.keepID;// "Ga7RLQETEADgAADDwKgOlOwp3Sw=";//
														// ���޿�

				PaymentTypeInfo type = (PaymentTypeInfo) obj;
				
				// ���޿��޸�ΪֻҪ����Ϳ��Ը�

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
				 * "������������ܸ����޿�,�����ڸ������͵�����Ϊ�������ĸ������뵥ʱ����ѡ�񡰱��޿���͸�������");
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
						MsgBox.showError(prmtPayment, "��ͬ�������ܸ����޿�");
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
										"����������ܸ����ȿ�,�����ڸ������͵�����Ϊ�������ĸ������뵥ʱ����ѡ�񡰽��ȿ���͸�������");
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
							MsgBox.showError(prmtPayment, "��ͬ����֮���ܸ����ȿ");
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
						MsgBox.showError(prmtPayment, "��ͬ�������֮���������������ĸ������뵥");
						for (int i = 0; i < listeners.length; i++) {
							prmtPayment
									.addDataChangeListener((DataChangeListener) listeners[i]);
						}
						SysUtil.abort();
					}
					
					txtpaymentProportion.setValue(FDCConstants.ZERO);
				}
				// ����������Ϊ�����ͱ��޿�ʱ�����깤���������ֱ�Ӿ͵��ڽ����
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
					MsgBox.showWarning(this, "'"+costAccountInfo.getName()+"' ��Ŀ���Ѿ�����");
					return;
				}
			}
		}
    }
    
    /**
	 * �ۼ����깤������=֮ǰ����״̬�ĸ������뵥���깤���������+����¼������깤���������<br>
	 * �ۼƸ������=�ۼ��������������������ۼƺ�ͬ�ڹ��̿�������ڣ�/�ۼ����깤������<br>
	 * ��ͬ���ս���󣺺�ͬ�����
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
	 * �ۼƷ�Ʊ���
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
     * ����: �����߼����ġ���Ը����������Ϊ���ȿ�ĸ������뵥����������Ϊ0������ʵ��Ϊ0������������ܼƵ����깤��������Ѹ��
     * �ۼ����깤=�������ĸ������뵥�깤������֮��+�����깤���������ۼ��Ѹ���=���еĸ������뵥��ͬ�ڹ��̿�֮��+���ں�ͬ�ڹ��̿��������ͬʱΪ0�������
     * ���ڲ����������ĸ������뵥���ڱ��棬�ύʱ������ʾ���ۼ����깤���������ĸ������뵥�깤������֮��+�����깤��������
     * С�����ۼƸ�����еĸ������뵥��ͬ�ڹ��̿�֮��+���ں�ͬ�ڹ��̿������ִ�б�������
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
    	
    	//���깤������(���깤)
    	BigDecimal completePrjAmt = FDCHelper.toBigDecimal(editData.getCompletePrjAmt());
    	//��ͬ�ڹ��̿�(�Ѹ���)
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
    	//����������,���������浥����ȡ��ǰ������������,�Ա����������޸�ʱ���ݴ���
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
    		MsgBox.showWarning(this, "�ۼ����깤���������ĸ������뵥�깤������֮��+�����깤��������С���ۼƸ�����еĸ������뵥��ͬ�ڹ��̿�֮��+���ں�ͬ�ڹ��̿������ִ�б�������");
    		SysUtil.abort();
    	}
    }
	
    protected void prmtPayment_willCommit(CommitEvent e) throws Exception {
    	/***
    	 * 42.	�ᵥ��R090609-207  ��ҵƽ
    	 * ��ģʽҲ����ѡ������,���޿�
    	 * by ����
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
     * ������ĺ�ͬID
     * @return contractId
     */
	public void actionAssociateAcctPay_actionPerformed(ActionEvent e) throws Exception {
		if (OprtState.ADDNEW.equals(getOprtState())||this.editData==null||this.editData.getId()==null) {
			// �뱣�澯��
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
    	// TODO �Զ����ɷ������
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
			FDCMsgBox.showWarning(this, "��ǰ��ͬ������Ŀ�¶ȿ�Ŀ����ƻ�,�����ڴ�ǩ����ͬ,����Ҫ������!");
			SysUtil.abort();
		}
		FDCBudgetPeriodInfo period=getFDCBudgetPeriod();
		if(period==null){
			period=FDCBudgetPeriodInfo.getCurrentPeriod(false);
		}
		ContractAssociateAcctPlanUI.showContractAssociateAcctPlanUI(this, prjId, contractId, period, getOprtState());
	}

	public void actionViewPayDetail_actionPerformed(ActionEvent e) throws Exception {
		// TODO �Զ����ɷ������
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
		// ����UI������ʾ
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
    protected void PrepareHandlerParam(RequestContext request)
    {
        super.PrepareHandlerParam(request);
        

    }
    
    protected void prepareInitDataHandlerParam(RequestContext request)
    {
		//��ͬId
		String contractBillId = (String) getUIContext().get("contractBillId");
		if(contractBillId==null){
			request.put("FDCBillEditUIHandler.ID",getUIContext().get("ID"));
		}else{
			request.put("FDCBillEditUIHandler.contractBillId",contractBillId);
		}		
		
		//������ĿId
		BOSUuid projectId = ((BOSUuid) getUIContext().get("projectId"));
		request.put("FDCBillEditUIHandler.projectId",projectId);

		//��ͬ����ID
		BOSUuid typeId = (BOSUuid) getUIContext().get("contractTypeId");
		request.put("FDCBillEditUIHandler.typeId",typeId);
    }

	public void actionContractAttachment_actionPerformed(ActionEvent e) throws Exception {
		// TODO �Զ����ɷ������
		super.actionContractAttachment_actionPerformed(e);
		
		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
		if(editData.getContractId() != null) {
				acm.showAttachmentListUIByBoID(editData.getContractId(),this,false);
		}else{
			return;
		}
	}
	/**
     * Ԥ�������������+��ͬ
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