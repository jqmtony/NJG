/**
 * 
 */
package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyCollection;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeAuxFactory;
import com.kingdee.eas.basedata.assistant.ExchangeAuxInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateFactory;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.assistant.SettlementTypeCollection;
import com.kingdee.eas.basedata.assistant.SettlementTypeFactory;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.PaymentTypeCollection;
import com.kingdee.eas.fdc.basedata.PaymentTypeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.SourceTypeEnum;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.fdc.contract.CompensationBillCollection;
import com.kingdee.eas.fdc.contract.CompensationBillFactory;
import com.kingdee.eas.fdc.contract.CompensationBillInfo;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.GuerdonBillCollection;
import com.kingdee.eas.fdc.contract.GuerdonBillFactory;
import com.kingdee.eas.fdc.contract.GuerdonBillInfo;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.PartAOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.PartAOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.PartAOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.UrgentDegreeEnum;
import com.kingdee.eas.fi.cas.DifPlaceEnum;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.util.StringUtils;

/**
 * @(#)							
 * ��Ȩ��		�����������������޹�˾��Ȩ����		 	
 * ������		�������뵥����ת����
 *		
 * @description	�������뵥
 * @author			Ӻ����
 * @version		EAS7.0	
 * @createDate		2011-6-13	 
 * @see						
 */
public class PayRequestBillTransmission extends AbstractDataTransmission {

	//  ��Դ�ļ�
	private static String resource = "com.kingdee.eas.fdc.contract.PayReqBTraResource";
	
	/** �������뵥 */
	private PayRequestBillInfo info = null;
	/** ������Ŀ */
	private CurProjectInfo curProject = null;
	/** ��ͬ */
	private ContractBillInfo contractBill = null;
	/** �ÿ�� */
	private AdminOrgUnitInfo adminOrgUnit = new AdminOrgUnitInfo();
	/** �Ƶ��� */
	private UserInfo createUser = null;
	/** ������ */
	private UserInfo auditUser = null;
	/** �������� */
	private PaymentTypeInfo paymentType = null;
	/** ���㷽ʽ */
	private SettlementTypeInfo settlementType = new SettlementTypeInfo();
	/** ʵ���տλ */
	private SupplierInfo supplier = new SupplierInfo();
	/** ���� */
	private CurrencyInfo currency = new CurrencyInfo();
	/** ���Ϊ��ʱĬ��ֵΪ0 */
	private BigDecimal bigDecimal = new BigDecimal(0);
	/** ������ */
	private GuerdonBillInfo guerdonBill = null;
	/** �������뵥��Ӧ�Ľ����� */
	private GuerdonOfPayReqBillInfo guerdonOfPayReqBill = null;
	/** ΥԼ�� */
	private CompensationBillInfo compensationBill = null;
	/** �������뵥��Ӧ��ΥԼ�� */
	private CompensationOfPayReqBillInfo compensationOfPayReqBill = null;
	/** ȫ��<hashtable> */
	private Hashtable hashtableAll = null;
	/** �������뵥��Ӧ�Ŀۿ��� */
	private DeductOfPayReqBillInfo deductOfPayReqBill = null;
	/** �������뵥��Ӧ�ļ׹����� */
	private PartAOfPayReqBillInfo partAOfPayReqBill = null;
	
	/**
	 * @description		
	 * @author			Ӻ����		
	 * @createDate		2011-6-13
	 * @param			ctx
	 * @return			ICoreBase		
	 * @version		EAS1.0
	 * @see	
	 * (non-Javadoc)
	 * @see com.kingdee.eas.tools.datatask.AbstractDataTransmission#getController(com.kingdee.bos.Context)					
	 */
	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		ICoreBase factory = null;
		try {
			factory = PayRequestBillFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return factory;
	}

	/**
	 * @description		
	 * @author			Ӻ����			
	 * @createDate		2011-6-13
	 * @param			hashtable
	 * @param			context
	 * @return			CoreBaseInfo	
	 * @version		EAS1.0
	 * @see	
	 * (non-Javadoc)
	 * @see com.kingdee.eas.tools.datatask.IDataTransmission#transmit(java.util.Hashtable, com.kingdee.bos.Context)					
	 */
	public CoreBaseInfo transmit(Hashtable hashtable, Context context) throws TaskExternalException {
		
		hashtableAll = hashtable;
		
		info = new PayRequestBillInfo();
		
		try {
			// ��֯������
			String fCostCenterNumber  = ((String) ((DataToken)hashtable.get("FCostCenter_number")).data).trim();
			// ������Ŀ����
			String fCurProjectCodingNumber  = ((String) ((DataToken)hashtable.get("FCurProject_codingNumber")).data).trim();
			// ��ͬ���
			String fContractNo = ((String) ((DataToken)hashtable.get("FContractNo")).data).trim();
			// �ÿ��-ȡnumber
			String fUseDepartmentNamel2 = ((String) ((DataToken) hashtable.get("FUseDepartment_number")).data).trim();
			// ״̬
			String fState = ((String) ((DataToken)hashtable.get("FState")).data).trim();
			// ҵ������
			String fBizDate = ((String) ((DataToken)hashtable.get("FBizDate")).data).trim();
			// �������뵥����
			String fNumber = ((String) ((DataToken)hashtable.get("FNumber")).data).trim();
			// ��������
			String fPayDate = ((String) ((DataToken)hashtable.get("FPayDate")).data).trim();
			// ��������
			String fPaymentTypeNumber = ((String) ((DataToken)hashtable.get("FPaymentType_number")).data).trim();
			// ͬ�����
			String fIsDifferPlace = ((String) ((DataToken)hashtable.get("FIsDifferPlace")).data).trim();
			// ���㷽ʽ
			String fSettlementTypeNamel2 = ((String) ((DataToken)hashtable.get("FSettlementType_name_l2")).data).trim();
			// �տλȫ��
			String fSupplierNamel2 = ((String) ((DataToken)hashtable.get("FSupplier_name_l2")).data).trim();
			// �տ�����
			String fRecBank = ((String) ((DataToken)hashtable.get("FRecBank")).data).trim();
			// ժҪ
			String fDescription = ((String) ((DataToken)hashtable.get("FDescription")).data).trim();
			// ʵ���տλ-ȡnumber
			String fRealSupplierNamel2 = ((String) ((DataToken) hashtable.get("FRealSupplier_number")).data).trim();
			// �տ��˺�
			String fRecAccount = ((String) ((DataToken)hashtable.get("FRecAccount")).data).trim();
			// ��;
			String fUsage = ((String) ((DataToken)hashtable.get("FUsage")).data).trim();
			// �ұ����
			String fCurrencyNumber = ((String) ((DataToken)hashtable.get("FCurrency_number")).data).trim();
			// ����
			String fExchangeRate = ((String) ((DataToken)hashtable.get("FExchangeRate")).data).trim();
			// ���ȿ�����
			String fConPayplanPayProportion = ((String) ((DataToken)hashtable.get("FConPayplan_payProportion")).data).trim();
			// �����깤������
			String fCostAmount = ((String) ((DataToken)hashtable.get("FCostAmount")).data).trim();
			// ���޽���
			String fGrtAmount = ((String) ((DataToken)hashtable.get("FGrtAmount")).data).trim();
			// �����̶�
			String fUrgentDegree = ((String) ((DataToken)hashtable.get("FUrgentDegree")).data).trim();
			// ��Ʊ��
			String fInvoiceNumber = ((String) ((DataToken)hashtable.get("FInvoiceNumber")).data).trim();
			// ��Ʊ���
			String fInvoiceAmt = ((String) ((DataToken)hashtable.get("FInvoiceAmt")).data).trim();
			// ��Ʊ����
			String fInvoiceDate = ((String) ((DataToken)hashtable.get("FInvoiceDate")).data).trim();
			// �����������
			String fProcess = ((String) ((DataToken)hashtable.get("FProcess")).data).trim();
			// ��ע
			String fMoneyDesc = ((String) ((DataToken)hashtable.get("FMoneyDesc")).data).trim();
			// ��Ŀ����
			String fCurProjectNamel2 = ((String) ((DataToken)hashtable.get("FCurProject_name_l2")).data).trim();
			// ��ͬ����
			String fContractName = ((String) ((DataToken)hashtable.get("FContractName")).data).trim();
			// �������
			String fLatestPrice = ((String) ((DataToken)hashtable.get("FLatestPrice")).data).trim();
			// ���ǩ֤ȷ�Ͻ��
			String fChangeAmt = ((String) ((DataToken)hashtable.get("FChangeAmt")).data).trim();
			// ������
			String fSettleAmt = ((String) ((DataToken)hashtable.get("FSettleAmt")).data).trim();
			// �����뵥�Ѹ����
			String fPayedAmt = ((String) ((DataToken)hashtable.get("FPayedAmt")).data).trim();
			// ��ͬ�������
			String fPayTimes = ((String) ((DataToken)hashtable.get("FPayTimes")).data).trim();
			// ��ͬ�ڹ��̿�_��������ԭ��
			String fProjectPriceInContractOri = ((String) ((DataToken)hashtable.get("FProjectPriceInContractOri")).data).trim();
			// ���ڼƻ�����
			String fCurPlannedPayment = ((String) ((DataToken)hashtable.get("FCurPlannedPayment")).data).trim();
			// ����Ƿ����
			String fCurBackPay = ((String) ((DataToken)hashtable.get("FCurBackPay")).data).trim();
			// ��������%
			String FCurReqPercent = ((String) ((DataToken)hashtable.get("FCurReqPercent")).data).trim();
			// �ۼ�����%
			String fAllReqPercent = ((String) ((DataToken)hashtable.get("FAllReqPercent")).data).trim();
			// �������%
			String fImageSchedule = ((String) ((DataToken)hashtable.get("FImageSchedule")).data).trim();
			// Ӧ������% 	  <�Զ�����,���д��벻����ֵ>
			String mustReqPercent = ((String) ((DataToken)hashtable.get("mustReqPercent")).data).trim();
			// �ۼ�Ӧ������%  <�Զ�����,���д��벻����ֵ>
			String addUpMustReqPercent = ((String) ((DataToken)hashtable.get("addUpMustReqPercent")).data).trim();
			// �Ƶ��˱���
			String fCreatorNumber = ((String) ((DataToken)hashtable.get("FCreator_number")).data).trim();
			// �Ƶ�ʱ��
			String fCreateTime = ((String) ((DataToken)hashtable.get("FCreateTime")).data).trim();
			// ����˱���
			String fAuditorNumber = ((String) ((DataToken)hashtable.get("FAuditor_number")).data).trim();
			// ���ʱ��
			String fAuditTime = ((String) ((DataToken)hashtable.get("FAuditTime")).data).trim();
			// ����     <�����ڽ������븶�����뵥���м����>
			String reward = ((String) ((DataToken)hashtable.get("reward")).data).trim();
			// ΥԼ     <������ΥԼ���븶�����뵥���м����>
			String breach = ((String) ((DataToken)hashtable.get("breach")).data).trim();
			// �ۿ�     <�����ڿۿ�븶�����뵥���м����>
			String fine = ((String) ((DataToken)hashtable.get("fine")).data).trim();
			// �׹��Ŀۿ�   <�����ڼ׹��Ŀۿ��븶�����뵥���м����>
			String stuffFine = ((String) ((DataToken)hashtable.get("stuffFine")).data).trim();
			
			/**
			 * �ж��Ƿ�Ϊ��,�Լ��ж��ֶγ���
			 */
			if (!StringUtils.isEmpty(fCostCenterNumber)) {
				if (fCostCenterNumber.length() > 80) {
					// "��֯�����ֶγ��Ȳ��ܳ���80��"
					throw new TaskExternalException(getResource(context, "Import_fCostCenterNumber"));
				}
			}
			if (!StringUtils.isEmpty(fCurProjectCodingNumber)) {
				if (fCurProjectCodingNumber.length() > 80) {
					// "������Ŀ�����ֶγ��Ȳ��ܳ���80��"
					throw new TaskExternalException(getResource(context, "Import_fCurProjectCodingNumber"));
				}
			} else {
				// "������Ŀ���벻��Ϊ�գ�"
				throw new TaskExternalException(getResource(context, "Import_fCurProjectCodingNumberNotNull"));
			}
			if (!StringUtils.isEmpty(fContractNo)) {
				if (fContractNo.length() > 80) {
					// "��ͬ�����ֶγ��Ȳ��ܳ���80��"
					throw new TaskExternalException(getResource(context, "Import_fContractNo"));
				}
			} else {
				// "��ͬ���벻��Ϊ�գ�"
				throw new TaskExternalException(getResource(context, "Import_fContractNoNotNull"));
			}
			if (StringUtils.isEmpty(fUseDepartmentNamel2)) {
				// "�ÿ�Ų���Ϊ�գ�"
				throw new TaskExternalException(getResource(context, "Import_fUseDepartmentNamel2"));
			}
			if (StringUtils.isEmpty(fBizDate)) {
				// "ҵ�����ڲ���Ϊ�գ�"
				throw new TaskExternalException(getResource(context, "Import_fBizDate"));
			}
			if (!StringUtils.isEmpty(fNumber)) {
				if (fNumber.length() > 80) {
					// "�������뵥�����ֶγ��Ȳ��ܳ���80��"
					throw new TaskExternalException(getResource(context, "Import_fNumber"));
				}
			} else {
				// "�������뵥���벻��Ϊ�գ�"
				throw new TaskExternalException(getResource(context, "Import_fNumberNotNull"));
			}
			if (StringUtils.isEmpty(fPayDate)) {
				// "�������ڲ���Ϊ�գ�"
				throw new TaskExternalException(getResource(context, "Import_fPayDate"));
			}
			if (StringUtils.isEmpty(fPaymentTypeNumber)) {
				// "�������Ͳ���Ϊ�գ�"
				throw new TaskExternalException(getResource(context, "Import_fPaymentTypeNumber"));
			}
			if (StringUtils.isEmpty(fRealSupplierNamel2)) {
				// "ʵ���տλ����Ϊ�գ�"
				throw new TaskExternalException(getResource(context, "Import_fRealSupplierNamel2"));
			}
			if (!StringUtils.isEmpty(fUsage)) {
				if (fUsage.length() > 90) {
					// "��;�ֶγ��Ȳ��ܳ���90��"
					throw new TaskExternalException(getResource(context, "Import_fUsage"));
				}
			}
			if (!StringUtils.isEmpty(fExchangeRate)) {
				if (FDCTransmissionHelper.isRangedInHundred(fExchangeRate) == null) {
					// "���ʲ���0~100֮�䣡"
					throw new TaskExternalException(getResource(context, "Import_fExchangeRate"));
				}
			}
			if (!StringUtils.isEmpty(fConPayplanPayProportion)) {
				if (FDCTransmissionHelper.isRangedInHundred(fConPayplanPayProportion) == null) {
					// "���ȿ���������0~100֮�䣡"
					throw new TaskExternalException(getResource(context, "Import_fConPayplanPayProportion"));
				}
			} else {
				// "���ȿ���������Ϊ�գ�"
				throw new TaskExternalException(getResource(context, "Import_fConPayplanPayProportionNotNull"));
			}
			if (!StringUtils.isEmpty(fCostAmount)) {
				// "�����깤������"
				this.strTOnumber(fCostAmount, getResource(context, "Import_fCostAmount"), context);
			} else {
				// "�����깤����������Ϊ�գ�"
				throw new TaskExternalException(getResource(context, "Import_fCostAmountNotNull"));
			}
			if (!StringUtils.isEmpty(fGrtAmount)) {
				// "���޽���"
				this.strTOnumber(fGrtAmount, getResource(context, "Import_fGrtAmount"), context);
			}
			if (!StringUtils.isEmpty(fInvoiceNumber)) {
				if (fInvoiceNumber.length() > 80) {
					// "��Ʊ���ֶγ��Ȳ��ܳ���80��"
					throw new TaskExternalException(getResource(context, "Import_fInvoiceNumber"));
				}
			}
			if (!StringUtils.isEmpty(fInvoiceAmt)) {
				// "��Ʊ���"
				this.strTOnumber(fGrtAmount, getResource(context, "Import_fInvoiceAmt"), context);
			}
			if (!StringUtils.isEmpty(fProcess)) {
				if (fProcess.length() > 255) {
					// "������������ֶγ��Ȳ��ܳ���255��"
					throw new TaskExternalException(getResource(context, "Import_fProcess"));
				}
			}
			if (!StringUtils.isEmpty(fMoneyDesc)) {
				if (fMoneyDesc.length() > 500) {
					// "��ע�ֶγ��Ȳ��ܳ���500��"
					throw new TaskExternalException(getResource(context, "Import_fMoneyDesc"));
				}
			}
			if (!StringUtils.isEmpty(fLatestPrice)) {
				// "�������"
				this.strTOnumber(fLatestPrice, getResource(context, "Import_fLatestPrice"), context);
			}
			if (!StringUtils.isEmpty(fChangeAmt)) {
				// "���ǩ֤ȷ�Ͻ��"
				this.strTOnumber(fChangeAmt, getResource(context, "Import_fChangeAmt"), context);
			}
			if (!StringUtils.isEmpty(fSettleAmt)) {
				// "������"
				this.strTOnumber(fSettleAmt, getResource(context, "Import_fSettleAmt"), context);
			}
			if (!StringUtils.isEmpty(fPayedAmt)) {
				// "�����뵥�Ѹ����"
				this.strTOnumber(fPayedAmt, getResource(context, "Import_fPayedAmt"), context);
			}
			if (!StringUtils.isEmpty(fPayTimes)) {
				if (FDCTransmissionHelper.strToInt(fPayTimes) == 0) {
					// "��ͬ�������Ӧ��¼�������ͣ�"
					throw new TaskExternalException(getResource(context, "Import_fPayTimes"));
				}
			}
			if (!StringUtils.isEmpty(fProjectPriceInContractOri)) {
				// "��ͬ�ڹ��̿�_��������ԭ��"
				this.strTOnumber(fProjectPriceInContractOri, getResource(context, "Import_fProjectPriceInContractOri"), context);
			} else {
				// "��ͬ�ڹ��̿�_��������ԭ�Ҳ���Ϊ�գ�"
				throw new TaskExternalException(getResource(context, "Import_fProjectPriceInContractOriNotNull"));
			}
			if (!StringUtils.isEmpty(fCurPlannedPayment)) {
				// "���ڼƻ�����"
				this.strTOnumber(fCurPlannedPayment, getResource(context, "Import_fCurPlannedPayment"), context);
			}
			if (!StringUtils.isEmpty(fCurBackPay)) {
				// "����Ƿ����"
				this.strTOnumber(fCurBackPay, getResource(context, "Import_fCurBackPay"), context);
			}
			if (!StringUtils.isEmpty(FCurReqPercent)) {
				// "��������%"
				if (FDCTransmissionHelper.isRangedInHundred(FCurReqPercent) == null) {
					throw new TaskExternalException(getResource(context, "Import_FCurReqPercent"));
				}
			}
			if (!StringUtils.isEmpty(fAllReqPercent)) {
				// "�ۼ�����%"
				if (FDCTransmissionHelper.isRangedInHundred(fAllReqPercent) == null) {
					throw new TaskExternalException(getResource(context, "Import_fAllReqPercent"));
				}
			}
			if (!StringUtils.isEmpty(fImageSchedule)) {
				// "�������%"
				if (FDCTransmissionHelper.isRangedInHundred(fImageSchedule) == null) {
					throw new TaskExternalException(getResource(context, "Import_fImageSchedule"));
				}
			}
			if (!StringUtils.isEmpty(mustReqPercent)) {
				// Ӧ������%
				if (FDCTransmissionHelper.isRangedInHundred(mustReqPercent) == null) {
					throw new TaskExternalException(getResource(context, "Import_yfsq"));
				}
			}
			if (!StringUtils.isEmpty(addUpMustReqPercent)) {
				// �ۼ�Ӧ������% 
				if (FDCTransmissionHelper.isRangedInHundred(addUpMustReqPercent) == null) {
					throw new TaskExternalException(getResource(context, "Import_ljyfsq"));
				}
			}
			if (StringUtils.isEmpty(fCreatorNumber)) {
				// "�Ƶ��˱��벻��Ϊ�գ�"
				throw new TaskExternalException(getResource(context, "Import_fCreatorNumber"));
			}
			if (StringUtils.isEmpty(fCreateTime)) {
				// "�Ƶ�ʱ�䲻��Ϊ�գ�"
				throw new TaskExternalException(getResource(context, "Import_fCreateTimeNotNull"));
			}
			// �������������ĸ��ֶξ��������м�������� 
			if (!StringUtils.isEmpty(reward)) {
				// �����ֶ�Ӧ��¼��������
				this.strTOnumber(reward, getResource(context, "Import_jlyglrszx"), context);
			}
			if (!StringUtils.isEmpty(breach)) {
				// ΥԼ�ֶ�Ӧ��¼��������
				this.strTOnumber(breach, getResource(context, "Import_wyyglrszx"), context);
			}
			if (!StringUtils.isEmpty(fine)) {
				// �ۿ��ֶ�Ӧ��¼��������
				this.strTOnumber(fine, getResource(context, "Import_kkyglrszx"), context);
			}
			if (!StringUtils.isEmpty(stuffFine)) {
				// �׹��Ŀۿ��ֶ�Ӧ��¼��������
				this.strTOnumber(stuffFine, getResource(context, "Import_jgckkyglrszx"), context);
			}
				
			/**
			 * ��ֵ�������
			 */
			// ������Ŀ����
			FilterInfo filterCurProject = new FilterInfo();
			filterCurProject.getFilterItems().add(new FilterItemInfo("longnumber", fCurProjectCodingNumber.replace('.', '!')));
			EntityViewInfo viewCurProject = new EntityViewInfo();
			viewCurProject.setFilter(filterCurProject);
			CurProjectCollection curProjectBillColl = CurProjectFactory.getLocalInstance(context).getCurProjectCollection(viewCurProject);
			if (curProjectBillColl.size() > 0){
				curProject = curProjectBillColl.get(0);
				info.setCurProject(curProject);
				// ��֯������
				CostCenterOrgUnitInfo costCenterOrgUnit = curProject.getCostCenter(); //  ������Ŀ��Ӧ�ɱ�������֯
				FilterInfo filter1 = new FilterInfo();
				filter1.getFilterItems().add(new FilterItemInfo("id", costCenterOrgUnit.getId().toString()));
				EntityViewInfo view1 = new EntityViewInfo();
				view1.setFilter(filter1);
				CostCenterOrgUnitCollection ccouc = CostCenterOrgUnitFactory.getLocalInstance(context).getCostCenterOrgUnitCollection(view1);
				if (!StringUtils.isEmpty(fCostCenterNumber) && !fCostCenterNumber.replace('.', '!').equals(ccouc.get(0).getLongNumber())) {
					// "��֯�������ڹ�����Ŀ����Ӧ�ĳɱ����Ĳ�����!"
 					throw new TaskExternalException(getResource(context, "Import_fCostCenterNumber1"));
					// ԭת����֯���󷽷� <�ݲ��Ƽ�ʹ��> (FullOrgUnitInfo)FullOrgUnitInfo.class.cast(costCenterOrgUnit)
				}
				info.setOrgUnit(ccouc.get(0).castToFullOrgUnitInfo());
			} else {
				// 1 "������Ŀ����Ϊ��,��ù�����Ŀ���� "
				// 2 " ��ϵͳ�в����ڣ�"
				throw new TaskExternalException(getResource(context, "Import_fCurProjectCodingNumber1") + fCurProjectCodingNumber + getResource(context, "Import_NOTNULl"));
			}
			// ��ͬ���
			FilterInfo filterContractBill = new FilterInfo();
			filterContractBill.getFilterItems().add(new FilterItemInfo("number", fContractNo));
			EntityViewInfo viewContractBill = new EntityViewInfo();
			viewContractBill.setFilter(filterContractBill);
			ContractBillCollection contractBillColl = ContractBillFactory.getLocalInstance(context).getContractBillCollection(viewContractBill);
			if (contractBillColl.size() > 0){
				contractBill = contractBillColl.get(0);
				// �жϸú�ͬ�Ƿ������¼��Ĺ�����Ŀ��
				// �õ���ͬ�������ڵĹ�����ĿID
				String contractBillCurProject = String.valueOf(contractBill.getCurProject().getId());
				if (!contractBillCurProject.equals(curProject.getId().toString())) {
					// �ڸù�����Ŀ�²����ڴ˺�ͬ��
					throw new TaskExternalException(getResource(context, "Import_ggcxmxbczcht"));
				}
			}else{
				// 1 "��ͬ����Ϊ��,��ú�ͬ���� "
				// 2 " ��ϵͳ�в����ڣ�"
				throw new TaskExternalException(getResource(context, "Import_fContractNo1") + fContractNo + getResource(context, "Import_NOTNULl"));
			}
			info.setContractNo(fContractNo);
			info.setContractId(contractBill.getId().toString());
			info.setContractBase(contractBill.getContractBaseData());
			// �ÿ��
			FilterInfo filterAdminOrgUnit = new FilterInfo();
			filterAdminOrgUnit.getFilterItems().add(new FilterItemInfo("number", fUseDepartmentNamel2));
			EntityViewInfo viewAdminOrgUnit = new EntityViewInfo();
			viewAdminOrgUnit.setFilter(filterAdminOrgUnit);
			AdminOrgUnitCollection adminOrgUnitColl = AdminOrgUnitFactory.getLocalInstance(context).getAdminOrgUnitCollection(viewAdminOrgUnit);
			if (adminOrgUnitColl.size() > 0){
				adminOrgUnit = adminOrgUnitColl.get(0);
			}else{
				// "�ÿ�Ŷ����Ҳ�����"
				throw new TaskExternalException(getResource(context, "Import_fUseDepartmentNamel21"));
			}
			info.setUseDepartment(adminOrgUnit);
			// ״̬     fState 
			if (fState.equals(getResource(context, "SAVED"))) {
				info.setState(FDCBillStateEnum.SAVED);
				if ((!StringUtils.isEmpty(fAuditorNumber)) || (!StringUtils.isEmpty(fAuditTime))) {
					// �ڱ���״̬�²���¼�������˱��������ʱ�䣡
					throw new TaskExternalException(getResource(context, "SAVED_NotEnteringData"));
				}
			} else if (fState.equals(getResource(context, "SUBMITTED"))) {
				info.setState(FDCBillStateEnum.SUBMITTED);
				if ((!StringUtils.isEmpty(fAuditorNumber)) || (!StringUtils.isEmpty(fAuditTime))) {
					// �����ύ״̬�²���¼�������˱��������ʱ�䣡
					throw new TaskExternalException(getResource(context, "SUBMITTED_NotEnteringData"));
				}
			} else if (fState.equals(getResource(context, "AUDITTED")) || StringUtils.isEmpty(fState)) {
				info.setState(FDCBillStateEnum.AUDITTED);
				// ����˱��� <"״̬"�ֶ������ĵ��в�����,��Ҫ�޸������ĵ���Ӹ��ֶ�>
				if (!StringUtils.isEmpty(fAuditorNumber)) {
					FilterInfo filterAuditor = new FilterInfo();
					filterAuditor.getFilterItems().add(new FilterItemInfo("number", fAuditorNumber));
					EntityViewInfo viewAuditor = new EntityViewInfo();
					viewAuditor.setFilter(filterAuditor);
					UserCollection auditorColl = UserFactory.getLocalInstance(context).getUserCollection(viewAuditor);
					if (auditorColl.size() > 0){
						auditUser = auditorColl.get(0);
						info.setAuditor(auditUser);
					}else{
						// 1 "������˱���"
						// 2 " ��ϵͳ�в����ڣ�"
						throw new TaskExternalException(getResource(context, "Import_fAuditorNumber1") + fAuditorNumber + getResource(context, "Import_NOTNULl"));
					}
				} else {
					// "��������״̬�µ�����˱����ֶβ���Ϊ�գ�"
					throw new TaskExternalException(getResource(context, "Import_fAuditorNumber"));
				}
				// ����ʱ��
				if (!StringUtils.isEmpty(fAuditTime)) {
					info.setAuditTime(new Timestamp(FDCTransmissionHelper.checkDateFormat(fAuditTime, getResource(context, "Import_spsjcw")).getTime()));
				} else {
					// "��������״̬�µ����������ֶβ���Ϊ�գ�"
					throw new TaskExternalException(getResource(context, "Import_fAuditTime"));
				}
			} else {
				// 1 "��״̬ "
				// 2 " ��ϵͳ�в����ڣ�"
				throw new TaskExternalException(getResource(context, "Import_fState") + fState + getResource(context, "Import_NOTNULl"));
			}
			// ҵ������ 
			info.setBizDate(FDCTransmissionHelper.checkDateFormat(fBizDate, getResource(context, "Import_ywrqcw")));
			// �������뵥���� 
			FilterInfo filterfNumber = new FilterInfo();
			filterfNumber.getFilterItems().add(new FilterItemInfo("number", fNumber, CompareType.EQUALS));
			EntityViewInfo viewfNumber = new EntityViewInfo();
			viewfNumber.setFilter(filterfNumber);
			PayRequestBillCollection payReqColl = PayRequestBillFactory.getLocalInstance(context).getPayRequestBillCollection(viewfNumber);
			if (payReqColl.size() > 0){
				// "�ø������뵥�����Ѿ�����,�����ظ���"
				throw new TaskExternalException(getResource(context, "Import_fNumber11"));
			}
			info.setNumber(fNumber);
			// ��������
			info.setPayDate(FDCTransmissionHelper.checkDateFormat(fPayDate, getResource(context, "Import_fkrqcw")));
			// ��������     
			FilterInfo filterPaymentType = new FilterInfo();
			filterPaymentType.getFilterItems().add(new FilterItemInfo("name", fPaymentTypeNumber));
			EntityViewInfo viewPaymentType = new EntityViewInfo();
			viewPaymentType.setFilter(filterPaymentType);
			PaymentTypeCollection paymentTypeColl = PaymentTypeFactory.getLocalInstance(context).getPaymentTypeCollection(viewPaymentType);
			if (paymentTypeColl.size() > 0){
				paymentType = paymentTypeColl.get(0);
			}else{
				// 1 "�������� "
				// 2 " ��ϵͳ�в����ڣ�"
				throw new TaskExternalException(getResource(context, "Import_fPaymentTypeNumber1") + fPaymentTypeNumber + getResource(context, "Import_NOTNULl"));
			}
			info.setPaymentType(paymentType);
			// ͬ����� 
			if (fIsDifferPlace.equals(getResource(context, "samePlace")) || StringUtils.isEmpty(fIsDifferPlace)) {
				info.setIsDifferPlace(DifPlaceEnum.samePlace);
			} else if (fIsDifferPlace.trim().equals(getResource(context, "difPlace"))) {
				info.setIsDifferPlace(DifPlaceEnum.difPlace);
			} else {
				// 1 "ͬ����� "
				// 2 " ��ϵͳ�в����ڣ�"
				throw new TaskExternalException(getResource(context, "Import_fIsDifferPlace") + fIsDifferPlace + getResource(context, "Import_NOTNULl"));
			}
			// ���㷽ʽ <�Ǳ�¼��,����Ҫ�ڷǿյ�������жϼ���>
			if (!StringUtils.isEmpty(fSettlementTypeNamel2)) {
				FilterInfo filterSettlementType = new FilterInfo();
				filterSettlementType.getFilterItems().add(new FilterItemInfo("name", fSettlementTypeNamel2));
				EntityViewInfo viewSettlementType = new EntityViewInfo();
				viewSettlementType.setFilter(filterSettlementType);
				SettlementTypeCollection settlementTypeColl = SettlementTypeFactory.getLocalInstance(context).getSettlementTypeCollection(viewSettlementType);
				if (settlementTypeColl.size() > 0) {
					settlementType = settlementTypeColl.get(0);
				} else {
					// "���㷽ʽ�����Ҳ�����"
					throw new TaskExternalException(getResource(context, "Import_fSettlementTypeNamel2"));
				}
				info.setSettlementType(settlementType);
			}
			// �տλȫ��   <�ɺ�ͬ�����Զ�����> // contractBill.getPartB()�÷�ʽ����ֱ�ӵõ��տλȫ�ƶ���,ֻ�ܵõ���ID,����Ҫͨ��ID��ѯһ�μ���
			String supplierID = String.valueOf(contractBill.getPartB().getId()); // �õ��տλȫ�Ƶ�UUID
			if (!StringUtils.isEmpty(supplierID)) {
				// ͨ���տλȫ�Ƶ�ID��ѯ�����<�ö���һ������,����ֻ��һ������>
				FilterInfo supplierFilter = new FilterInfo();
				supplierFilter.getFilterItems().add(new FilterItemInfo("id", supplierID));
				EntityViewInfo supplierView = new EntityViewInfo();
				supplierView.setFilter(supplierFilter);
				SupplierCollection supplierColl = SupplierFactory.getLocalInstance(context).getSupplierCollection(supplierView);
				if (supplierColl.size() > 0) {
					info.setSupplier(supplierColl.get(0));
				} else {
					// �����ѯ�������,��˵����ID��Ӧ���տλȫ�ƶ����Ѿ������ݿ��в�����!��˲����Ǹ����
				}
				
			}
			// �տ��˺�    
//			if (!fRecAccount.equals("") && fRecAccount != null) {
//				FilterInfo filterBank = new FilterInfo();
//				filterBank.getFilterItems().add(new FilterItemInfo("bankAccount", fRecAccount));
//				EntityViewInfo viewBank = new EntityViewInfo();
//				viewBank.setFilter(filterBank);
//				SupplierCompanyBankCollection bankColl = SupplierCompanyBankFactory.getLocalInstance(context).getSupplierCompanyBankCollection(viewBank);
//				if (bankColl.size() > 0) {
//					bank = bankColl.get(0);
					info.setRecAccount(fRecAccount);
					// �տ�����  <���տ��˺��Զ�����>
					info.setRecBank(fRecBank);
//				} else {
//					// 1 "���տ��˺� "
//					// 2 " ��ϵͳ�в����ڣ�"
//					throw new TaskExternalException(getResource(context, "Import_fRecAccount11") + fRealSupplierNamel2 + getResource(context, "Import_NOTNULl"));
//				}
//			}
			// ժҪ
			info.setDescription(fDescription);
			// ʵ���տ
			FilterInfo filtersupplier = new FilterInfo();
			filtersupplier.getFilterItems().add(new FilterItemInfo("number", fRealSupplierNamel2));
			EntityViewInfo viewsupplier = new EntityViewInfo();
			viewsupplier.setFilter(filtersupplier);
			SupplierCollection supplierColl = SupplierFactory.getLocalInstance(context).getSupplierCollection(viewsupplier);
			if (supplierColl.size() > 0) {
				supplier = supplierColl.get(0);
			} else {
				// 1 "ʵ���տλ������,���߸�ʵ���տλ "
				// 2 " ��ϵͳ�в����ڣ�"
				throw new TaskExternalException(getResource(context, "Import_fRealSupplierNamel21") + fRealSupplierNamel2 + getResource(context, "Import_NOTNULl"));
			}
			info.setRealSupplier(supplier);
			// ��; 
			info.setUsage(fUsage);
			// �ұ����
			CurrencyCollection currencyCollection = null;
			FilterInfo currencyFilter = new FilterInfo();
			EntityViewInfo currencyView = new EntityViewInfo();
			if (StringUtils.isEmpty(fCurrencyNumber)) {
				currencyFilter.getFilterItems().add(new FilterItemInfo("number", "RMB"));
			} else {
				currencyFilter.getFilterItems().add(new FilterItemInfo("number",fCurrencyNumber));
			}
			currencyView.setFilter(currencyFilter);
			currencyCollection = CurrencyFactory.getLocalInstance(context).getCurrencyCollection(currencyView);
			if (currencyCollection.size() > 0) {
				currency = currencyCollection.get(0);
				info.setCurrency(currency);
			}
			// ����  <ȡ��ͬ����>
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			ExchangeRateInfo exchangeRateInfo = new ExchangeRateInfo();
			if (StringUtils.isEmpty(fExchangeRate)) {  // �������Ϊ�գ���ȡ��ǰ�ұ����
				if ("RMB".equals(currency.getNumber()) || "CNY".equals(currency.getIsoCode())) {
					fExchangeRate = "1.00";
				} else {
					// ��ѯĿ��ұ� <���̶�ԭ��>
					CurrencyCollection rmbColl = null;
					FilterInfo rmbFilter = new FilterInfo();
					EntityViewInfo rmbView = new EntityViewInfo();
					rmbFilter.getFilterItems().add(new FilterItemInfo("number", "RMB"));
					rmbView.setFilter(rmbFilter);
					rmbColl = CurrencyFactory.getLocalInstance(context).getCurrencyCollection(rmbView);
					CurrencyInfo rmb = rmbColl.get(0);
					filter.getFilterItems().add(new FilterItemInfo("targetCurrency", rmb.getId(), CompareType.EQUALS));//Ŀ��ұ�
					filter.getFilterItems().add(new FilterItemInfo("SourceCurrency", currency.getId(), CompareType.EQUALS));//ԭ�ұ�
					filter.setMaskString("#0 and #1");
					view.setFilter(filter);
					ExchangeAuxInfo xinfo = ExchangeAuxFactory.getLocalInstance(context).getExchangeAuxCollection(view).get(0);
					if (xinfo != null) {
						exchangeRateInfo = ExchangeRateFactory.getLocalInstance(context).getExchangeRate(new ObjectUuidPK(xinfo.getExchangeTable().getId()), new ObjectUuidPK(xinfo.getSourceCurrency().getId()), new ObjectUuidPK(xinfo.getTargetCurrency().getId()), Calendar.getInstance().getTime());
						BigDecimal bigDecimal = exchangeRateInfo.getConvertRate();
						if (bigDecimal != null) {
							fExchangeRate = bigDecimal.toString();
						}
					} else {
						fExchangeRate = "1.00";
					}
				}
			} else {
				if (FDCTransmissionHelper.isRangedInHundred(fExchangeRate) == null) {
					// ����¼�벻�Ϸ�
					throw new TaskExternalException(getResource(context, "Import_fExRate10"));
				}
			}
			info.setExchangeRate(contractBill.getExRate());
			// ���ȿ�����
			info.setPaymentProportion(new BigDecimal(fConPayplanPayProportion));
			// ���޽���(�������,��Ĭ���ɺ�ͬ����)
			if (!StringUtils.isEmpty(fGrtAmount)) {
				info.setGrtAmount(new BigDecimal(fGrtAmount));
			} else {
				info.setGrtAmount(contractBill.getGrtAmount());
			}
			// �����깤������
			info.setCompletePrjAmt(new BigDecimal((FDCTransmissionHelper.strToDouble(fProjectPriceInContractOri) / FDCTransmissionHelper.strToDouble(fConPayplanPayProportion)) * 100));
			// �Ƿ�Ӽ� 
			if (fUrgentDegree.equals(getResource(context, "YES"))) {
				info.setUrgentDegree(UrgentDegreeEnum.URGENT);
			} else {
				info.setUrgentDegree(UrgentDegreeEnum.NORMAL);
			}
			// ��Ʊ��
			info.setInvoiceNumber(fInvoiceNumber);
			// ��Ʊ��� 
			if (!StringUtils.isEmpty(fInvoiceAmt)) {
				info.setInvoiceAmt(new BigDecimal(fInvoiceAmt));
			} else {
				info.setInvoiceAmt(bigDecimal);
			}
			// ��Ʊ����
			if (!StringUtils.isEmpty(fInvoiceDate)) {
				info.setInvoiceDate(FDCTransmissionHelper.checkDateFormat(fInvoiceDate, getResource(context, "Import_kprqcw")));
			}
			// �����������
			info.setProcess(fProcess);
			// ��ע
			info.setMoneyDesc(fMoneyDesc);
			// ��Ŀ���� <�ɺ�ͬ�����Զ�����>   // contractBill.getCurProject()�÷�ʽ����ֱ�ӵõ���Ŀ���ƶ���,ֻ�ܵõ���ID,����Ҫͨ��ID��ѯһ�μ���
			String curProjectID = String.valueOf(contractBill.getCurProject().getId()); // �õ��տλȫ�Ƶ�UUID
			if (!StringUtils.isEmpty(curProjectID)) {
				// ͨ���տλȫ�Ƶ�ID��ѯ�����<�ö���һ������,����ֻ��һ������>
				FilterInfo curProjectFilter = new FilterInfo();
				curProjectFilter.getFilterItems().add(new FilterItemInfo("id", curProjectID));
				EntityViewInfo curProjectView = new EntityViewInfo();
				curProjectView.setFilter(curProjectFilter);
				CurProjectCollection curProjectColl = CurProjectFactory.getLocalInstance(context).getCurProjectCollection(curProjectView);
				if (curProjectColl.size() > 0) {
					info.setCurProject(curProjectColl.get(0));
				} else {
					// �����ѯ�������,��˵����ID��Ӧ���տλȫ�ƶ����Ѿ������ݿ��в�����!��˲����Ǹ����
				}
				
			}
			// ��ͬ����  <�ɺ�ͬ�����Զ�����>
			info.setContractName(contractBill.getName());

			// ���ǩ֤ȷ�Ͻ��
			if (!StringUtils.isEmpty(fChangeAmt)) {
				info.setChangeAmt(new BigDecimal(fChangeAmt));
			} else {
				info.setChangeAmt(bigDecimal);
			}
			// ������
			if (!StringUtils.isEmpty(fSettleAmt)) {
				info.setSettleAmt(new BigDecimal(fSettleAmt));
			} else {
				info.setSettleAmt(bigDecimal);
			}
			// ��ͬ�������
			if (!StringUtils.isEmpty(fPayTimes)) {
				info.setPayTimes(Integer.parseInt(fPayTimes));
			} else {
				info.setPayTimes(0);
			}
			// ��ͬ�ڹ��̿�_��������ԭ��    
			info.setProjectPriceInContractOri(new BigDecimal(fProjectPriceInContractOri));
			// ���ڼƻ�����    
			if (!StringUtils.isEmpty(fCurPlannedPayment)) {
				info.setCurPlannedPayment(new BigDecimal(fCurPlannedPayment));
			} else {
				info.setCurPlannedPayment(bigDecimal);
			}
			// ����Ƿ����    
			if (!StringUtils.isEmpty(fCurBackPay)) {
				info.setCurBackPay(new BigDecimal(fCurBackPay));
			} else {
				info.setCurBackPay(bigDecimal);
			}
			// ��������%    
			if (!StringUtils.isEmpty(FCurReqPercent)) {
				info.setCurReqPercent(new BigDecimal(FCurReqPercent));
			} else {
				info.setCurReqPercent(new BigDecimal(FDCTransmissionHelper.strToDouble(contractBill.getAmount().toString()) / FDCTransmissionHelper.strToDouble(fProjectPriceInContractOri)));
			}
			// �ۼ�����%    
			if (!StringUtils.isEmpty(fAllReqPercent)) {
				info.setAllReqPercent(new BigDecimal(fAllReqPercent));
			} else {
				info.setAllReqPercent(new BigDecimal(FDCTransmissionHelper.strToDouble(contractBill.getAmount().toString()) / FDCTransmissionHelper.strToDouble(fProjectPriceInContractOri)));
			}
			// �Ƶ��˱���    
			FilterInfo filtercreateUser = new FilterInfo();
			filtercreateUser.getFilterItems().add(new FilterItemInfo("number", fCreatorNumber));
			EntityViewInfo viewcreateUser = new EntityViewInfo();
			viewcreateUser.setFilter(filtercreateUser);
			UserCollection createUserColl = UserFactory.getLocalInstance(context).getUserCollection(viewcreateUser);
			if (createUserColl.size() > 0){
				createUser = createUserColl.get(0);
				info.setCreator(createUser);
			} else {
				// 1 "�Ƶ��˱���Ϊ��,�����Ƶ��˱��� "
				// 2 " ��ϵͳ�в����ڣ�"
				throw new TaskExternalException(getResource(context, "Import_fCreatorNumber1") + fCreatorNumber + getResource(context, "Import_NOTNULl"));
			}
			// �Ƶ�ʱ��    
			info.setCreateTime(new Timestamp(FDCTransmissionHelper.checkDateFormat(fCreateTime, getResource(context, "Import_zdrqcw")).getTime()));
			// <����_Ĭ���޸���>
			info.setAttachment(0);
			// <�Ƿ�����ƾ֤_Ĭ�Ϸ�>
			info.setFivouchered(false);
			// <Ӧ�ۼ׹����Ͽ���ڷ�����_TODO>
			info.setPayPartAMatlAmt(bigDecimal);
			// <��ͬ�ڹ��̿���ڷ�����_��ͬ�ڹ��̿�_��������ԭ��>
			info.setProjectPriceInContract(new BigDecimal(fProjectPriceInContractOri));
			// <�׹����ۼ������_Ϊ��>
			info.setPayPartAMatlAllReqAmt(bigDecimal);
			// <�Ƿ���_����Ĭ��>
			info.setHasPayoff(false);
			// <�ѹر�_����Ĭ��>
			info.setHasClosed(false);
			// <��λ�ұұ�_ȡ��ͬ�ұ�>
			info.setLocalCurrency(currency);
			// Ӧ������%    ffffff
			
			// ������� 
			info.setContractPrice(contractBill.getAmount());
			if (!StringUtils.isEmpty(fLatestPrice)) {
				info.setLatestPrice(new BigDecimal(fLatestPrice));
			} else {
				// �ɺ�ͬ�Զ�����
				info.setLatestPrice(contractBill.getAmount());
			}
			// �����뵥�Ѹ���� 
			if (!StringUtils.isEmpty(fPayedAmt)) {
				info.setPayedAmt(new BigDecimal(fPayedAmt));
			} else {
				// �к�ͬ�Զ�����
				info.setPayedAmt(bigDecimal);
			}
			// ʵ����ڷ�����
			info.setCurPaid(new BigDecimal(fProjectPriceInContractOri));
			// ��ͬ�ڹ����ۼ�����
			info.setPrjAllReqAmt(new BigDecimal(fProjectPriceInContractOri));
			// ���ӹ��̿��ۼ�����
			info.setAddPrjAllReqAmt(bigDecimal);
			// ������Դ��ʽ
			info.setSourceType(SourceTypeEnum.IMP);
			// ��ͬ�ڹ��̿������ۼ�����
			info.setLstPrjAllReqAmt(new BigDecimal(FDCTransmissionHelper.strToDouble(info.getCurPaid().toString()) - FDCTransmissionHelper.strToDouble(info.getPrjAllReqAmt().toString())));
//			info.setActPaiedAmount(); // ������ԭ��
//			info.setActPaiedLocAmount(); // �������
			
//			// <TODO : �����ĸ��ֶ���Ҫ���������ֵ�Ĵ��롣(��Ŀǰ����ʵ��,��ȱ����ϸ�涨��ʵ�ַ�ʽ)>
//			/** ���渶�����뵥 */
//			PayRequestBillFactory.getLocalInstance(context).addnew(info);
//			/** �ڱ���֮��������õ������� */
//			this.updateAdjustClause(info, context);
//			if (fState.equals("������")) {
//				/** ���������������뵥 */
//				PayRequestBillFactory.getLocalInstance(context).submit(new ObjectUuidPK(String.valueOf(info.getId())), info);
//				PayRequestBillFactory.getLocalInstance(context).audit(info.getId());
//			}
			return info;
			
		} catch (BOSException e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage());
		} catch (EASBizException e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage());
		}
	}
	
	/**
	 * @description		�ڵ��ݱ���֮�����õ�������<������ΥԼ���ۿ�׹��Ŀۿ�>
	 * @author				Ӻ����		
	 * @createDate			2011-7-15
	 * @param				coreBaseInfo
	 * @param				context
	 * @return				void
	 * @version			EAS1.0
	 * @see	
	 * (non-Javadoc)
	 * @see com.kingdee.eas.tools.datatask.AbstractDataTransmission#submit(com.kingdee.eas.framework.CoreBaseInfo, com.kingdee.bos.Context)					
	 */
	public void submit(CoreBaseInfo coreBaseInfo, Context context) throws TaskExternalException {
		
		super.submit(coreBaseInfo, context);
		
		/* ���ύ֮��������õ������� */
		this.updateAdjustClause(coreBaseInfo, context);
		
		/* �ڳɹ��޸ĸ������뵥�ĵ�������֮����������������� */
		this.auditPayRequestBill(context, coreBaseInfo);
	}
	
	/**
	 * @description		�������Ϊ�ύ״̬�����������ɱ���״̬�ĸ������뵥��
	 * @author				Ӻ����	
	 * @createDate			2011-7-21
	 * @param 				context
	 * @param 				coreBaseInfo
	 * @throws				TaskExternalException void
	 * @version			EAS1.0
	 * @see
	 */
	private void auditPayRequestBill(Context context, CoreBaseInfo coreBaseInfo) throws TaskExternalException {
		
		try {
			if (coreBaseInfo instanceof PayRequestBillInfo) {
				PayRequestBillInfo payRequestBillInfo = (PayRequestBillInfo) coreBaseInfo;
				// �õ���ǰ�������뵥��״̬���жϸ�״̬�Ƿ�Ϊ���ύ״̬
				String state = payRequestBillInfo.getState().getValue();
				if (!state.equals("4AUDITTED")) {
					return;
				}
//				/** ���õ�ǰ�������뵥Ϊ���ύ״̬ */
//				PayRequestBillFactory.getLocalInstance(context).submit(new ObjectUuidPK(String.valueOf(payRequestBillInfo.getId())), payRequestBillInfo);
				/** ���������������뵥 */
				PayRequestBillFactory.getLocalInstance(context).audit(coreBaseInfo.getId());
			}
		} catch (EASBizException e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage());
		} catch (BOSException e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage());
		}
		
	}
	
	/**
	 * @description		�ڸ������뵥����֮���޸���������	
	 * @author				Ӻ����	
	 * @param 				coreBaseInfo
	 * @param 				context
	 * @createDate			2011-7-20
	 * @throws 			BOSException void
	 * @version			EAS1.0
	 * @see
	 */
	private void updateAdjustClause(CoreBaseInfo coreBaseInfo, Context context) throws TaskExternalException {
		try {
			PayRequestBillInfo payRequestBillInfo = (PayRequestBillInfo) coreBaseInfo;
			// ��ͬ�ڹ��̿�_��������ԭ��
			String fProjectPriceInContractOri = ((String) ((DataToken)hashtableAll.get("FProjectPriceInContractOri")).data).trim();
			// ����     <�����ڽ������븶�����뵥���м����>
			String reward = ((String) ((DataToken)hashtableAll.get("reward")).data).trim();
			// ΥԼ     <������ΥԼ���븶�����뵥���м����>
			String breach = ((String) ((DataToken)hashtableAll.get("breach")).data).trim();
			// �ۿ�     <�����ڿۿ�븶�����뵥���м����>
			String fine = ((String) ((DataToken)hashtableAll.get("fine")).data).trim();
			// �׹��Ŀۿ�   <�����ڼ׹��Ŀۿ��븶�����뵥���м����>
			String stuffFine = ((String) ((DataToken)hashtableAll.get("stuffFine")).data).trim();
			// ��ѯ�������뵥
			FilterInfo pFilter = new FilterInfo();
			pFilter.getFilterItems().add(new FilterItemInfo("number", payRequestBillInfo.getNumber()));
			EntityViewInfo pView = new EntityViewInfo();
			pView.setFilter(pFilter);
			PayRequestBillCollection payRequestBillColl =PayRequestBillFactory.getLocalInstance(context).getPayRequestBillCollection(pView);
			if (payRequestBillColl.size() > 0) {
				info = payRequestBillColl.get(0);
				// �ڱ��渶�����뵥֮�� ���õ�������<������ΥԼ���ۿ�׹��Ŀۿ�>
				// <TODO : �����ĸ��ֶ���Ҫ���������ֵ�Ĵ��롣(��Ŀǰ����ʵ��,��ȱ����ϸ�涨��ʵ�ַ�ʽ)>
				// ����        (�м��<T_CON_GuerdonOfPayReqBill>)reward
				FilterInfo rewardFilter = new FilterInfo();
				rewardFilter.getFilterItems().add(new FilterItemInfo("contract", info.getContractId()));
				EntityViewInfo rewardView = new EntityViewInfo();
				rewardView.setFilter(rewardFilter);
				GuerdonBillCollection guerdonBillColl = GuerdonBillFactory.getLocalInstance(context).getGuerdonBillCollection(rewardView);
				BigDecimal guerdonBillAmount = new BigDecimal("0.00");
				BigDecimal guerdonOfPayReqBillAmount = new BigDecimal("0.00");
				if (guerdonBillColl.size() > 0) {
					for (int i = 0; i < guerdonBillColl.size(); i++) {
						guerdonBill = guerdonBillColl.get(i);
						// �ۿID
						String guerdonBillID = String.valueOf(guerdonBill.getId());
						rewardFilter = new FilterInfo();
						rewardFilter.getFilterItems().add(new FilterItemInfo("guerdon", guerdonBillID));
						rewardView = new EntityViewInfo();
						rewardView.setFilter(rewardFilter);
						GuerdonOfPayReqBillCollection guerdonOfPayReqBillColl = GuerdonOfPayReqBillFactory.getLocalInstance(context).getGuerdonOfPayReqBillCollection(rewardView);
						if (guerdonOfPayReqBillColl.size() > 0) {
							guerdonOfPayReqBill = guerdonOfPayReqBillColl.get(0);
							guerdonOfPayReqBillAmount = guerdonOfPayReqBill.getAmount();
							guerdonBillAmount = guerdonBillAmount.add(guerdonOfPayReqBillAmount);
						}
					}
				}
				reward = String.valueOf(guerdonBillAmount);
				// ΥԼ        (�м��<T_CON_CompensationOfPayReqBill>)breach
				FilterInfo breachFilter = new FilterInfo();
				breachFilter.getFilterItems().add(new FilterItemInfo("contract", info.getContractId()));
				EntityViewInfo breachView = new EntityViewInfo();
				breachView.setFilter(breachFilter);
				CompensationBillCollection compensationBillColl = CompensationBillFactory.getLocalInstance(context).getCompensationBillCollection(breachView);
				BigDecimal compensationBillAmount = new BigDecimal("0.00");
				BigDecimal compensationOfPayReqBillAmount = new BigDecimal("0.00");
				if (compensationBillColl.size() > 0) {
					for (int i = 0; i < compensationBillColl.size(); i++) {
						compensationBill = compensationBillColl.get(i);
						// ΥԼ��ID
						String compensationBillID = String.valueOf(compensationBill.getId());
						breachFilter = new FilterInfo();
						breachFilter.getFilterItems().add(new FilterItemInfo("compensation", compensationBillID));
						breachView = new EntityViewInfo();
						CompensationOfPayReqBillCollection compensationOfPayReqBillColl = CompensationOfPayReqBillFactory.getLocalInstance(context).getCompensationOfPayReqBillCollection(breachView);
						if (compensationOfPayReqBillColl.size() > 0) {
							compensationOfPayReqBill = compensationOfPayReqBillColl.get(0);
							compensationOfPayReqBillAmount = compensationOfPayReqBill.getAmount();
							compensationBillAmount = compensationBillAmount.add(compensationOfPayReqBillAmount);
						}
					}
				}
				breach = String.valueOf(compensationBillAmount);
				// �ۿ�        (�м��<T_CON_DeductOfPayReqBill>)fine
				FilterInfo fineFilter = new FilterInfo();
				fineFilter.getFilterItems().add(new FilterItemInfo("payRequestBill", String.valueOf(info.getId())));
				EntityViewInfo fineView = new EntityViewInfo();
				fineView.setFilter(fineFilter);
				DeductOfPayReqBillCollection deductOfPayReqBillColl = DeductOfPayReqBillFactory.getLocalInstance(context).getDeductOfPayReqBillCollection(fineView);
				BigDecimal deductTypeAmount = new BigDecimal("0.00");
				BigDecimal deductOfPayReqBillAmount = new BigDecimal("0.00");
				if (deductOfPayReqBillColl.size() > 0) {
					for (int i = 0; i < deductOfPayReqBillColl.size(); i++) {
						deductOfPayReqBill = deductOfPayReqBillColl.get(i);
						deductOfPayReqBillAmount = deductOfPayReqBill.getAmount();
						deductTypeAmount = deductTypeAmount.add(deductOfPayReqBillAmount);
					}
				}
				fine = String.valueOf(deductTypeAmount);
				// �׹��Ŀۿ�  (�м��<T_CON_PartAOfPayReqBill>)stuffFine
				FilterInfo stuffFineFilter = new FilterInfo();
				stuffFineFilter.getFilterItems().add(new FilterItemInfo("payRequestBill", String.valueOf(info.getId())));
				EntityViewInfo stuffFineView = new EntityViewInfo();
				stuffFineView.setFilter(stuffFineFilter);
				PartAOfPayReqBillCollection partAOfPayReqBillColl = PartAOfPayReqBillFactory.getLocalInstance(context).getPartAOfPayReqBillCollection(stuffFineView);
				BigDecimal deductBillAmount = new BigDecimal("0.00");
				BigDecimal partAOfPayReqBillAmount = new BigDecimal("0.00");
				if (partAOfPayReqBillColl.size() > 0) {
					for (int i = 0; i < partAOfPayReqBillColl.size(); i++) {
						partAOfPayReqBill = partAOfPayReqBillColl.get(i);
						partAOfPayReqBillAmount = partAOfPayReqBill.getAmount();
						deductBillAmount = deductBillAmount.add(partAOfPayReqBillAmount);
					}
				}
				stuffFine = String.valueOf(deductBillAmount);
				// ���ҽ��
				BigDecimal amountAll = new BigDecimal(fProjectPriceInContractOri);
				BigDecimal all = amountAll.add(new BigDecimal(reward)).subtract(new BigDecimal(breach)).add(new BigDecimal(fine)).add(new BigDecimal(stuffFine));
				info.setAmount(all);
				if (currency.getIsoCode() == null) {
					String id = "dfd38d11-00fd-1000-e000-1ebdc0a8100dDEB58FDC";
					currency.setId(BOSUuid.read(id));
					currency.setIsoCode("RMB");
				}
				// ��д���
				info.setCapitalAmount(FDCHelper.transCap(currency, all));
				// ԭ�ҽ��
				info.setOriginalAmount(all);
				PayRequestBillFactory.getLocalInstance(context).update(new ObjectUuidPK(String.valueOf(info.getId())), info);
			}
		} catch (BOSException e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage());
		} catch (EASBizException e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @description		�ж�������ַ������Ƿ�����������
	 * @author				Ӻ����
	 * @createDate			2011-6-15
	 * @param 				value			��Ҫ�жϵ��ַ���
	 * @param 				fieldName		Ҫ�жϵ��ַ�������
	 * @version			EAS1.0
	 * @throws 			TaskExternalException 
	 * @see
	 */
	private void strTOnumber(String value, String fieldName, Context context) throws TaskExternalException {
		if (value.matches("^\\d+(\\.\\d+)?$")) {  // �ж��Ƿ��Ǵ�������
			// ���valueֵ������ֵ��, �в�����17,2����ʽʱ���쳣
			if (!value.matches("^([1-9]\\d{0,15}\\.\\d{0,2})|(0\\.\\d{0,2})||([1-9]\\d{0,15})||0$")) {  // ���������������Ƿ����Ҫ��
				// "������ 1��17 λ������� 1��2λС�����ɣ�"
				throw new TaskExternalException(fieldName + getResource(context, "Import_strTOnumber"));
			}
		} else {
			// "Ӧ��¼�������ͣ�"
			throw new TaskExternalException(fieldName + getResource(context, "Import_strTOnumber_number"));
		}
	}
	
	/**
	 * �õ���Դ�ļ�
	 */
	public static String getResource(Context context, String key) {
		return ResourceBase.getString(resource, key, context.getLocale());
	}
	
}
