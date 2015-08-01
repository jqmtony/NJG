/**
 * 
 */
package com.kingdee.eas.fdc.basedata.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Hashtable;

import javax.sql.RowSet;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
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
import com.kingdee.eas.basedata.assistant.ProjectCollection;
import com.kingdee.eas.basedata.assistant.ProjectFactory;
import com.kingdee.eas.basedata.assistant.SettlementTypeCollection;
import com.kingdee.eas.basedata.assistant.SettlementTypeFactory;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeCollection;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeFactory;
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
import com.kingdee.eas.fdc.basedata.ContractTypeCollection;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
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
import com.kingdee.eas.fdc.contract.ConSplitExecStateEnum;
import com.kingdee.eas.fdc.contract.ContractWithoutTextCollection;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
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
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.DifBankEnum;
import com.kingdee.eas.fi.cas.DifPlaceEnum;
import com.kingdee.eas.fi.cas.IsMergencyEnum;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fi.cas.PaymentBillTypeCollection;
import com.kingdee.eas.fi.cas.PaymentBillTypeFactory;
import com.kingdee.eas.fi.cas.SettlementStatusEnum;
import com.kingdee.eas.fm.fs.SettBizTypeEnum;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.eas.util.app.DbUtil;

/**
 * @(#)							
 * ��Ȩ��			�����������������޹�˾��Ȩ����		 	
 * ������			���ı���ͬ��������������������������
 *		
 * @author			Ӻ����
 * @version		EAS7.0		
 * @createDate		2011-6-16	 
 * @see						
 */
public class ContractWithoutTextTransmission extends AbstractDataTransmission {
	private static final Logger logger = Logger.getLogger(ContractWithoutTextTransmission.class);

	/** ��Դ�ļ� */
	private static String resource = "com.kingdee.eas.fdc.basedata.ContractWTextResource";
	
	/** ������ */
	private UserInfo auditoreUser = null;
	/** �Ƶ��� */
	private UserInfo createUser = null;
	/** ������Ŀ */
	private CurProjectInfo curProject = null;
	/** ���㷽ʽ */
	private SettlementTypeInfo settlementType = null;
	/** �տλ */
	private SupplierInfo supplier = null;
	/** �������뵥 */
	private PayRequestBillInfo payRequestBill = null;
	/** ��� */
	private PaymentBillInfo paymentBill = null;
	/**  ��ͬ���� */
	private ContractTypeInfo contractType = null;
	/** �������� */
	private PaymentTypeInfo paymentType = null;
	/** �ÿ�� */
	private AdminOrgUnitInfo adminOrgUnit = null;
	/** ���� */
	private CurrencyInfo currency = null;
	/** �������뵥���� */
	private String payReqNumber = new String("");
	/** ȫ�ֵ�<hashtable>���ݷ��� */
	private Hashtable hashtableAll = null;
	/** �����ʼ���Ϊ0 */
	private BigDecimal bigDecimal = new BigDecimal("0.00");
	/** ������ */
	private GuerdonBillInfo guerdonBill = null;
	/** �������뵥��Ӧ�Ľ����� */
	private GuerdonOfPayReqBillInfo guerdonOfPayReqBill = null;
	/** ΥԼ�� */
	private CompensationBillInfo compensationBill = null;
	/** �������뵥��Ӧ��ΥԼ�� */
	private CompensationOfPayReqBillInfo compensationOfPayReqBill = null;
	/** �������뵥��Ӧ�Ŀۿ��� */
	private DeductOfPayReqBillInfo deductOfPayReqBill = null;
	/** �������뵥��Ӧ�ļ׹����� */
	private PartAOfPayReqBillInfo partAOfPayReqBill = null;
	
	/**
	 * @description		
	 * @author				Ӻ����		
	 * @createDate			2011-7-20
	 * @param				context
	 * @return				ICoreBase	
	 * @version			EAS1.0
	 * @throws				TaskExternalException ICoreBase
	 * @see	
	 * (non-Javadoc)
	 * @see com.kingdee.eas.tools.datatask.AbstractDataTransmission#getController(com.kingdee.bos.Context)
	 */
	protected ICoreBase getController(Context context) throws TaskExternalException {
		ICoreBase factory = null;
		try {
			factory = ContractWithoutTextFactory.getLocalInstance(context);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return factory;
	}
	
	/**
	 * @description		���ı���ͬ��ȡ�������ұ���
	 * @author				Ӻ����		
	 * @createDate			2011-6-23
	 * @param				hashtable
	 * @param				context
	 * @return				CoreBaseInfo
	 * @throws				TaskExternalException	CoreBaseInfo
	 * @version			EAS1.0
	 * @see	
	 * (non-Javadoc)
	 * @see com.kingdee.eas.tools.datatask.IDataTransmission#transmit(java.util.Hashtable, com.kingdee.bos.Context)
	 */
	public CoreBaseInfo transmit(Hashtable hashtable, Context context) throws TaskExternalException {
		
		hashtableAll = hashtable;
		
		ContractWithoutTextInfo info = new ContractWithoutTextInfo();
		
		// ��֯����
		String fOrgUnitNumber  = ((String) ((DataToken)hashtable.get("FOrgUnit_number")).data).trim();
		// ������Ŀ����
		String fCurProjectLongNumber  = ((String) ((DataToken)hashtable.get("FCurProject_longNumber")).data).trim();
		// ����
		String fNumber  = ((String) ((DataToken)hashtable.get("FNumber")).data).trim();
		// ����
		String fName  = ((String) ((DataToken)hashtable.get("FName")).data).trim();
		// ״̬
		String fState  = ((String) ((DataToken)hashtable.get("FState")).data).trim();
		// �ұ����
		String fCurrencyNumber  = ((String) ((DataToken)hashtable.get("FCurrency_number")).data).trim();
		// ҵ������
		String fBizDate  = ((String) ((DataToken)hashtable.get("FBizDate")).data).trim();
		// �������ͱ���
		String paymentTypeNumber = ((String) ((DataToken)hashtable.get("PaymentTypeNumber")).data).trim();
		// �ÿ�ű���
		String fUseDepartmentNumber  = ((String) ((DataToken)hashtable.get("FUseDepartment_number")).data).trim();
		// ��������
		String fSignDate  = ((String) ((DataToken)hashtable.get("FSignDate")).data).trim();
		// ԭ�ҽ��
		String fOriginalAmount  = ((String) ((DataToken)hashtable.get("FOriginalAmount")).data).trim();
		// ����
		String exchangeRate = ((String) ((DataToken)hashtable.get("ExchangeRate")).data).trim();
		// ��λ�ҽ��
		String fAmount  = ((String) ((DataToken)hashtable.get("FAmount")).data).trim();
		// ���㷽ʽ
		String fSettlementTypeNumber  = ((String) ((DataToken)hashtable.get("FSettlementType_number")).data).trim();
		// �տλ����
		String fReceiveUnitName  = ((String) ((DataToken)hashtable.get("FReceiveUnit_name_l2")).data).trim();
		// ʵ���տλ
		String realSupplierNamel2 = ((String) ((DataToken)hashtable.get("RealSupplierNamel2")).data).trim();
		// �տ�����
		String fBank  = ((String) ((DataToken)hashtable.get("FBank")).data).trim();
		// �����˺�
		String fBankAcct  = ((String) ((DataToken)hashtable.get("FBankAcct")).data).trim();
		// �Ƿ���붯̬�ɱ�
		String fIsCostSplit  = ((String) ((DataToken)hashtable.get("FIsCostSplit")).data).trim();
		// �Ƿ���Ҫ����
		String fIsNeedPaid  = ((String) ((DataToken)hashtable.get("FIsNeedPaid")).data).trim();
		// ���踶��ԭ��
		String fNoPaidReason  = ((String) ((DataToken)hashtable.get("FNoPaidReason")).data).trim();
		// ����
		String attachment = ((String) ((DataToken)hashtable.get("Attachment")).data).trim();
		// ����˵��
		String moneyDesc = ((String) ((DataToken)hashtable.get("MoneyDesc")).data).trim();
		// ��Ʊ��
		String fInvoiceNumber  = ((String) ((DataToken)hashtable.get("FInvoiceNumber")).data).trim();
		// ��Ʊ���
		String fInvoiceAmt  = ((String) ((DataToken)hashtable.get("FInvoiceAmt")).data).trim();
		// ��Ʊ����
		String fInvoiceDate  = ((String) ((DataToken)hashtable.get("FInvoiceDate")).data).trim();
		// �ۼƷ�Ʊ���
		String fAllInvoiceAmt  = ((String) ((DataToken)hashtable.get("FAllInvoiceAmt")).data).trim();
		// ��ע
		String fDescription  = ((String) ((DataToken)hashtable.get("FDescription")).data).trim();
		// �Ƶ���
		String fCreatorName = ((String) ((DataToken)hashtable.get("FCreator_name_l2")).data).trim();
		// �Ƶ�ʱ��
		String fCreateTime  = ((String) ((DataToken)hashtable.get("FCreateTime")).data).trim();
		// �����
		String fAuditorName  = ((String) ((DataToken)hashtable.get("FAuditor_name_l2")).data).trim();
		// ����ʱ��
		String fAuditTime  = ((String) ((DataToken)hashtable.get("FAuditTime")).data).trim();
		
		
		/**
		 * �жϷǿ��Լ��ֶγ���
		 */
		// ��֯����  fOrgUnitNumber
		if (!StringUtils.isEmpty(fOrgUnitNumber)) {
			if (fOrgUnitNumber.length() > 40) {
				// "��֯���볤�Ȳ��ܳ���40��"
				throw new TaskExternalException(getResource(context, "CWT_Import_fOrgUnitNumber"));
			}
		}
		// ������Ŀ���� fCurProjectLongNumber
		if (!StringUtils.isEmpty(fCurProjectLongNumber)) {
			if (fCurProjectLongNumber.length() > 40) {
				// "������Ŀ���볤�Ȳ��ܳ���40��"
				throw new TaskExternalException(getResource(context, "CWT_Import_fCurProjectLongNumber"));
			}
		} else {
			// "������Ŀ���벻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "CWT_Import_fCurProjectLongNumber1"));
		}
		// ���� fNumber
		if (!StringUtils.isEmpty(fNumber)) {
			if (fNumber.length() > 80) {
				// "���볤�Ȳ��ܳ���80��"
				throw new TaskExternalException(getResource(context, "CWT_Import_fNumber"));
			}
		} else {
			// "���벻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "CWT_Import_fNumber1"));
		}
		// ���� fName
		if (!StringUtils.isEmpty(fName)) {
			if (fName.length() > 80) {
				// "���Ƴ��Ȳ��ܳ���80��"
				throw new TaskExternalException(getResource(context, "CWT_Import_fName"));
			}
		} else {
			// "���Ʋ���Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "CWT_Import_fName1"));
		}
		// �ұ���� fCurrencyNumber
		if (!StringUtils.isEmpty(fCurrencyNumber)) {
			if (fCurrencyNumber.length() > 40) {
				// "�ұ���볤�Ȳ��ܳ���40��"
				throw new TaskExternalException(getResource(context, "CWT_Import_fCurrencyNumber"));
			}
		}
		// ҵ������ fBizDate
		if (StringUtils.isEmpty(fBizDate)) {
			// "ҵ�����ڲ���Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "CWT_Import_fBizDate"));
		}
		// �������ͱ��� paymentTypeNumber
		if (!StringUtils.isEmpty(paymentTypeNumber)) {
			if (paymentTypeNumber.length() > 40) {
				// "�������ͱ��볤�Ȳ��ܳ���40��"
				throw new TaskExternalException(getResource(context, "CWT_Import_paymentTypeNumber"));
			}
		} else {
			// "�������ͱ��벻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "CWT_Import_paymentTypeNumber1"));
		}
		// �ÿ�ű��� fUseDepartmentNumber
		if (!StringUtils.isEmpty(fUseDepartmentNumber)) {
			if (fUseDepartmentNumber.length() >40) {
				// "�ÿ�ų��Ȳ��ܳ���80��"
				throw new TaskExternalException(getResource(context, "CWT_Import_fUseDepartmentNumber"));
			}
		} else {
			// "�ÿ�Ų���Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "CWT_Import_fUseDepartmentNumber1"));
		}
		// �������� fSignDate
		if (StringUtils.isEmpty(fSignDate)) {
			// "�������ڲ���Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "CWT_Import_fSignDate"));
		}
		// ԭ�ҽ�� fOriginalAmount
		if (!StringUtils.isEmpty(fOriginalAmount)) {
			if (!fOriginalAmount.matches("^([1-9]\\d{0,15}\\.\\d{0,4})|(0\\.\\d{0,4})||([1-9]\\d{0,15})||0$")) {
				// "ԭ�ҽ��¼�벻�Ϸ�,������ 1��19 λ������� 1��4λС�����ɣ�"
				throw new TaskExternalException(getResource(context, "CWT_Import_fOriginalAmount"));
			}
		} else {
			// "ԭ�ҽ���Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "CWT_Import_fOriginalAmount1"));
		}
		// ���� exchangeRate
		if (!StringUtils.isEmpty(exchangeRate)) {
			if (!exchangeRate.matches("^([1-9]\\d{0,18}\\.\\d{0,10})|(0\\.\\d{0,10})||([1-9]\\d{0,18})||0$")) {
				// "����¼�벻�Ϸ�,������ 1��28 λ������� 1��10λС�����ɣ�"
				throw new TaskExternalException(getResource(context, "CWT_Import_exchangeRate"));
			}
		}
		// ��λ�ҽ�� fAmount
		if (!StringUtils.isEmpty(fAmount) && new BigDecimal(fAmount).compareTo(new BigDecimal("0")) == 0) {
			if (!fAmount.matches("^([1-9]\\d{0,15}\\.\\d{0,4})|(0\\.\\d{0,4})||([1-9]\\d{0,15})||0$")) {
				// "��λ�ҽ��¼�벻�Ϸ�,������ 1��19 λ������� 1��4λС�����ɣ�"
				throw new TaskExternalException(getResource(context, "CWT_Import_fAmount"));
			}
		}
		// ���㷽ʽ fSettlementTypeNumber
		if (!StringUtils.isEmpty(fSettlementTypeNumber)) {
			if (fSettlementTypeNumber.length() > 40) {
				// "���㷽ʽ���Ȳ��ܳ���40��"
				throw new TaskExternalException(getResource(context, "CWT_Import_fSettlementTypeNumber"));
			}
		}
		// �տλ���� fReceiveUnitName
		if (!StringUtils.isEmpty(fReceiveUnitName)) {
			if (fReceiveUnitName.length() > 40) {
				// "�տλ���Ȳ��ܳ���40��"
				throw new TaskExternalException(getResource(context, "CWT_Import_fReceiveUnitName"));
			}
		} else {
			// "�տλ����Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "CWT_Import_fReceiveUnitName1"));
		}
		// ʵ���տλ realSupplierNamel2
		if (!StringUtils.isEmpty(realSupplierNamel2)) {
			if (realSupplierNamel2.length() > 40) {
				// "ʵ���տλ���Ȳ��ܳ���40��"
				throw new TaskExternalException(getResource(context, "CWT_Import_realSupplierNamel2"));
			}
		}
		// �տ����� fBank
		if (!StringUtils.isEmpty(fBank)) {
			if (fBank.length() > 40) {
				// "�տ����г��Ȳ��ܳ���40��"
				throw new TaskExternalException(getResource(context, "CWT_Import_fBank"));
			}
		} else {
			// "�տ����в���Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "CWT_Import_fBank1"));
		}
		// �����˺� fBankAcct
		if (!StringUtils.isEmpty(fBankAcct)) {
			if (fBankAcct.length() > 40) {
				// "�����˺ų��Ȳ��ܳ���40��"
				throw new TaskExternalException(getResource(context, "CWT_Import_fBankAcct"));
			}
		} else {
			// "�����˺Ų���Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "CWT_Import_fBankAcct1"));
		}
		// �Ƿ���붯̬�ɱ� fIsCostSplit
		// �Ƿ���Ҫ���� fIsNeedPaid
		// ���踶��ԭ�� fNoPaidReason
		// �Ƿ�Ӽ� urgentDegree
		// ���� attachment
		if (!StringUtils.isEmpty(attachment)) {
			if (attachment.length() > 4) {
				// "�����ַ����Ȳ��ܳ���4��"
				throw new TaskExternalException(getResource(context, "CWT_Import_attachment"));
			}
		}
		// ����˵�� moneyDesc
		if (!StringUtils.isEmpty(moneyDesc)) {
			if (moneyDesc.length() > 40) {
				// "����˵���ֶγ��Ȳ��ܳ���40��"
				throw new TaskExternalException(getResource(context, "CWT_Import_moneyDesc"));
			}
		}
		// ��Ʊ�� fInvoiceNumber
		// ��Ʊ��� fInvoiceAmt
		if (!StringUtils.isEmpty(fInvoiceAmt)) {
			if (!fInvoiceAmt.matches("^([1-9]\\d{0,15}\\.\\d{0,4})|(0\\.\\d{0,4})||([1-9]\\d{0,15})||0$")) {
				// "��Ʊ���¼�벻�Ϸ�,������ 1��19 λ������� 1��4λС�����ɣ�"
				throw new TaskExternalException(getResource(context, "CWT_Import_fInvoiceAmt"));
			}
		}
		// ��Ʊ���� fInvoiceDate
		// �ۼƷ�Ʊ��� fAllInvoiceAmt
		if (!StringUtils.isEmpty(fAllInvoiceAmt)) {
			if (!fAllInvoiceAmt.matches("^([1-9]\\d{0,15}\\.\\d{0,4})|(0\\.\\d{0,4})||([1-9]\\d{0,15})||0$")) {
				// "�ۼƷ�Ʊ���¼�벻�Ϸ�,������ 1��19 λ������� 1��4λС�����ɣ�"
				throw new TaskExternalException(getResource(context, "CWT_Import_fAllInvoiceAmt"));
			}
		}
		// ��ע fDescription
		if (!StringUtils.isEmpty(fDescription)) {
			if (fDescription.length() > 40) {
				// "��ע���Ȳ��ܳ���40��"
				throw new TaskExternalException(getResource(context, "CWT_Import_fDescription"));
			}
		}
		// �Ƶ��� fCreatorName
		if (!StringUtils.isEmpty(fCreatorName)) {
			if (fCreatorName.length() > 40) {
				// "�Ƶ��˱��볤�Ȳ��ܳ���40��"
				throw new TaskExternalException(getResource(context, "CWT_Import_fCreatorName"));
			}
		} else {
			// "�Ƶ��˱��벻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "CWT_Import_fCreatorName1"));
		}
		// �Ƶ�ʱ�� fCreateTime
		if (StringUtils.isEmpty(fCreateTime)) {
			// "�Ƶ�ʱ�䲻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "CWT_Import_fCreateTime"));
		}
		// �����˱���
		if (!StringUtils.isEmpty(fAuditorName)) {
			if (fAuditorName.length() > 40) {
				// �����˱����ֶγ��Ȳ��ܳ���40��
				throw new TaskExternalException(getResource(context, "CWT_Import_sprbmgc"));
			}
		}
		
		/**
		 * ��ֵ���õ�������
		 */
		try {
			// ������Ŀ����
			FilterInfo filterCurProject = new FilterInfo();
			filterCurProject.getFilterItems().add(new FilterItemInfo("longnumber", fCurProjectLongNumber.replace('.', '!')));
			EntityViewInfo viewCurProject = new EntityViewInfo();
			viewCurProject.setFilter(filterCurProject);
			CurProjectCollection curProjectBillColl = CurProjectFactory.getLocalInstance(context).getCurProjectCollection(viewCurProject);
			if (curProjectBillColl.size() > 0){
				curProject = curProjectBillColl.get(0);
				info.setCurProject(curProject);
				// ��֯������
				CostCenterOrgUnitInfo costCenterOrgUnit = curProject.getCostCenter(); //  ������Ŀ��Ӧ�ɱ�������֯
				FilterInfo filter1 = new FilterInfo();
				filter1.getFilterItems().add(new FilterItemInfo("id", String.valueOf(costCenterOrgUnit.getId())));
				EntityViewInfo view1 = new EntityViewInfo();
				view1.setFilter(filter1);
				CostCenterOrgUnitCollection ccouc = CostCenterOrgUnitFactory.getLocalInstance(context).getCostCenterOrgUnitCollection(view1);
				if (!StringUtils.isEmpty(fOrgUnitNumber) && !fOrgUnitNumber.replace('.', '!').equals(ccouc.get(0).getLongNumber())) {
					// "��֯�������ڹ�����Ŀ����Ӧ�ĳɱ����Ĳ�����!"
 					throw new TaskExternalException(getResource(context, "Import_zzbmbcz"));
					// ԭת����֯���󷽷� <�ݲ��Ƽ�ʹ��> (FullOrgUnitInfo)FullOrgUnitInfo.class.cast(costCenterOrgUnit)
				}
				info.setOrgUnit(ccouc.get(0).castToFullOrgUnitInfo());
			} else {
				// 1 "������Ŀ����Ϊ��,��ù�����Ŀ���� "
				// 2 " ��ϵͳ�в����ڣ�"
				throw new TaskExternalException(getResource(context, "CWT_Import_fCurProjectLongNumber2") + fCurProjectLongNumber + getResource(context, "CWT_Import_NOTNULL"));
			}
			// ����
			info.setNumber(fNumber);
			// �����ظ�
			FilterInfo filterContractWithoutText = new FilterInfo();
			filterContractWithoutText.getFilterItems().add(new FilterItemInfo("curProject.id", curProject.getId().toString()));
			filterContractWithoutText.getFilterItems().add(new FilterItemInfo("name", fName));
			EntityViewInfo viewContractWithoutText = new EntityViewInfo();
			viewContractWithoutText.setFilter(filterContractWithoutText);
			ContractWithoutTextCollection contractWithoutTextColl = ContractWithoutTextFactory.getLocalInstance(context).getContractWithoutTextCollection(viewContractWithoutText);
			if (contractWithoutTextColl.size() > 0){
				// �����ظ�ʱ����ʾ�����������Ѿ����ڣ������ظ���
				throw new TaskExternalException(getResource(context, "CWT_Import_fNameCF"));
			}
			// ����
			info.setName(fName);
			// NULL
			info.setNull(fNumber + fName);
			//״̬ fState
			if (fState.equals(getResource(context, "CWT_Import_saved"))) {
				info.setState(FDCBillStateEnum.SAVED);
			} else if (fState.equals(getResource(context, "CWT_Import_submitted"))) {
				info.setState(FDCBillStateEnum.SUBMITTED);
			} else if (fState.equals(getResource(context, "CWT_Import_auditted")) || StringUtils.isEmpty(fState)) {
				info.setState(FDCBillStateEnum.AUDITTED);
				// ������ <"״̬"�ֶ������ĵ��в�����,��Ҫ�޸������ĵ���Ӹ��ֶ�>
				if (!StringUtils.isEmpty(fAuditorName)) {
					FilterInfo filterauditUser = new FilterInfo();
					filterauditUser.getFilterItems().add(new FilterItemInfo("number", fAuditorName));
					EntityViewInfo viewauditUser = new EntityViewInfo();
					viewauditUser.setFilter(filterauditUser);
					UserCollection auditUserColl = UserFactory.getLocalInstance(context).getUserCollection(viewauditUser);
					if (auditUserColl.size() > 0){
						auditoreUser = auditUserColl.get(0);
						info.setCreator(auditoreUser);
					} else {
						// 1 "�����˱��� "
						// 2 " ��ϵͳ�в����ڣ�"
						throw new TaskExternalException(getResource(context, "CWT_Import_fAuditorName2") + fAuditorName + getResource(context, "CWT_Import_NOTNULL"));
					}
				} else {
					// "��������״̬�µ��������ֶβ���Ϊ�գ�"
					throw new TaskExternalException(getResource(context, "CWT_Import_fAuditorName3"));
				}
				//����ʱ��
				if (!StringUtils.isEmpty(fAuditTime)) {
					info.setCreateTime(new Timestamp(FDCTransmissionHelper.checkDateFormat(fAuditTime, getResource(context, "CWT_Import_spsjcw")).getTime()));
				} else {
					// "��������״̬�µ����������ֶβ���Ϊ�գ�"
					throw new TaskExternalException(getResource(context, "CWT_Import_fAuditTime3"));
				}
			} else {
				// 1 "״̬ "
				// 2 " ��ϵͳ�в����ڣ�"
				throw new TaskExternalException(getResource(context, "CWT_Import_fState") + fState + getResource(context, "CWT_Import_NOTNULL"));
			}
			// �ұ���� <���û��¼��,��Ĭ�ϱ�λ��>
			CurrencyCollection currencyCollection = null;
			FilterInfo currencyFilter = new FilterInfo();
			EntityViewInfo currencyView = new EntityViewInfo();
			if (StringUtils.isEmpty(fCurrencyNumber)) {
				currencyFilter.getFilterItems().add(new FilterItemInfo("number", "RMB"));
			} else {
				currencyFilter.getFilterItems().add(new FilterItemInfo("number", fCurrencyNumber));
			}
			currencyView.setFilter(currencyFilter);
			currencyCollection = CurrencyFactory.getLocalInstance(context).getCurrencyCollection(currencyView);
			if (currencyCollection.size() > 0) {
				currency = currencyCollection.get(0);
				info.setCurrency(currency);
			} else {
				// �ñұ���벻���ڣ�
				throw new TaskExternalException(getResource(context, "CWT_Import_gbbbmbcz"));
			}
			// ҵ������ 
			info.setBizDate(FDCTransmissionHelper.checkDateFormat(fBizDate, getResource(context, "CWT_Import_ywrqcw")));
			// �������ͱ���
			FilterInfo filterpaymentType = new FilterInfo();
			filterpaymentType.getFilterItems().add(new FilterItemInfo("number", paymentTypeNumber));
			EntityViewInfo viewpaymentType = new EntityViewInfo();
			viewpaymentType.setFilter(filterpaymentType);
			PaymentTypeCollection paymentTypeColl = PaymentTypeFactory.getLocalInstance(context).getPaymentTypeCollection(viewpaymentType);
			if (!(paymentTypeColl.size() > 0)) {
				// "�ø������ͱ���"
				// "��ϵͳ�в����ڣ�"
				throw new TaskExternalException(getResource(context, "CWT_Import_paymentTypeNumber2") + paymentTypeNumber + getResource(context, "CWT_Import_NOTNULL"));
			}
			paymentType = paymentTypeColl.get(0);
			// �ÿ�ű���
			FilterInfo filteradminOrgUnit = new FilterInfo();
			filteradminOrgUnit.getFilterItems().add(new FilterItemInfo("number", fUseDepartmentNumber));
			EntityViewInfo viewadminOrgUnit = new EntityViewInfo();
			viewadminOrgUnit.setFilter(filteradminOrgUnit);
			AdminOrgUnitCollection adminOrgUnitColl = AdminOrgUnitFactory.getLocalInstance(context).getAdminOrgUnitCollection(viewadminOrgUnit);
			if (!(adminOrgUnitColl.size() > 0)) {
				// "���ÿ�ű���"
				// "��ϵͳ�в����ڣ�"
				throw new TaskExternalException(getResource(context, "CWT_Import_fUseDepartmentNumber2") + fUseDepartmentNumber + getResource(context, "CWT_Import_NOTNULL"));
			}
			adminOrgUnit = adminOrgUnitColl.get(0);
			info.setUseDepartment(adminOrgUnit);
			// ԭ�ҽ��
			DecimalFormat amountFormat = new DecimalFormat("#.##");
			BigDecimal quantity = FDCTransmissionHelper.strToBigDecimal(amountFormat.format(new BigDecimal(fOriginalAmount)));
			info.setOriginalAmount(quantity);
			// ���� <����Ϊ��ʱ,ȡ��ǰ�ұ����Ļ���>
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			ExchangeRateInfo exchangeRateInfo = new ExchangeRateInfo();
			if (StringUtils.isEmpty(exchangeRate)) {  // �������Ϊ�գ���ȡ��ǰ�ұ����
				if (currency.getNumber().equals("RMB")) {
					exchangeRate = "1.00";
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
							exchangeRate = bigDecimal.toString();
						}
					} else {
						exchangeRate = "1.00";
					}
				}
			} else {
				if (FDCTransmissionHelper.isRangedInHundred(exchangeRate) == null) {
					// ����¼�벻�Ϸ�
					throw new TaskExternalException(getResource(context, "Import_fExRate10"));
				}
			}
			// ��λ�ҽ�� (���Ϊ��,����Ϊ��ǰ���*����)
			if (StringUtils.isEmpty(fAmount) || new BigDecimal(fAmount).compareTo(new BigDecimal("0")) == 0) {
				info.setAmount(new BigDecimal(FDCTransmissionHelper.strToDouble(fOriginalAmount) * FDCTransmissionHelper.strToDouble(exchangeRate)));
			} else {
				info.setAmount(new BigDecimal(fAmount));
			}
			// ���㷽ʽ
			if (!StringUtils.isEmpty(fSettlementTypeNumber)) {
				FilterInfo filterSettlementType = new FilterInfo();
				filterSettlementType.getFilterItems().add(new FilterItemInfo("name", fSettlementTypeNumber));
				EntityViewInfo viewSettlementType = new EntityViewInfo();
				viewSettlementType.setFilter(filterSettlementType);
				SettlementTypeCollection settlementTypeColl = SettlementTypeFactory.getLocalInstance(context).getSettlementTypeCollection(viewSettlementType);
				if (settlementTypeColl.size() > 0) {
					settlementType = settlementTypeColl.get(0);
					info.setSettlementType(settlementType);
				}
			}
			// �տλ����
			FilterInfo filterSupplier = new FilterInfo();
			filterSupplier.getFilterItems().add(new FilterItemInfo("number", fReceiveUnitName));
			EntityViewInfo viewSupplier = new EntityViewInfo();
			viewSupplier.setFilter(filterSupplier);
			SupplierCollection supplierColl = SupplierFactory.getLocalInstance(context).getSupplierCollection(viewSupplier);
			if (!(supplierColl.size() > 0)) {
				// "���տλ����"
				// "��ϵͳ�в�����"
				throw new TaskExternalException(getResource(context, "CWT_Import_fReceiveUnitName2") + fReceiveUnitName + getResource(context, "CWT_Import_NOTNULL"));
			}
			supplier = supplierColl.get(0);
			info.setReceiveUnit(supplier);
			// ʵ���տλ realSupplierNamel2
			if (!StringUtils.isEmpty(realSupplierNamel2)) {
				FilterInfo filterRealSupplier = new FilterInfo();
				filterRealSupplier.getFilterItems().add(new FilterItemInfo("name", realSupplierNamel2));
				EntityViewInfo viewRealSupplier = new EntityViewInfo();
				viewRealSupplier.setFilter(filterRealSupplier);
				SupplierCollection realSupplierColl = SupplierFactory.getLocalInstance(context).getSupplierCollection(viewRealSupplier);
				if (realSupplierColl.size() > 0) {
					supplier = realSupplierColl.get(0);
				} else {
					// 1 "��ʵ���տλ "
					// 2 " ��ϵͳ�в����ڣ�"
					throw new TaskExternalException(getResource(context, "CWT_Import_realSupplierNamel22") + realSupplierNamel2 + getResource(context, "CWT_Import_NOTNULL"));
				}
			}
			// �����˺� fBankAcct
//			FilterInfo filterBank = new FilterInfo();
//			filterBank.getFilterItems().add(new FilterItemInfo("number", fBankAcct));
//			EntityViewInfo viewBank = new EntityViewInfo();
//			viewBank.setFilter(filterBank);
//			SupplierCompanyBankCollection bankColl = SupplierCompanyBankFactory.getLocalInstance(context).getSupplierCompanyBankCollection(viewBank);
//			if (bankColl.size() > 0) {
//				bank = bankColl.get(0);
				info.setBankAcct(fBankAcct);
//				if (fBank.equals(bank.getBank())) {
					// �տ����� fBank
					info.setBank(fBank);
//				} else {
//					throw new TaskExternalException("�����˺���������տ����в���Ӧ��");
//				}
//			} else {
//				throw new TaskExternalException("�����˺�" + fBankAcct + "��ϵͳ�в�����");
//			}
			// �Ƿ���붯̬�ɱ� fIsCostSplit
			if (fIsCostSplit.equals(getResource(context, "CWT_Import_NO"))) {
				// Ĭ��Ϊ��,��TRUE
				info.setIsCostSplit(false);
			} else {
				info.setIsCostSplit(true);
			}
			// �Ƿ���Ҫ���� fIsNeedPaid
			if (fIsNeedPaid.equals(getResource(context, "CWT_Import_NO"))) {
				info.setIsNeedPaid(true);
			} else {
				// Ĭ��Ϊ��,��TRUE
				info.setIsNeedPaid(false);
			}
			// ���踶��ԭ�� fNoPaidReason
			info.setNoPaidReason(fNoPaidReason);
			// �Ƿ�Ӽ� urgentDegree
			// ���� attachment
			// ����˵�� moneyDesc
			// ��Ʊ�� fInvoiceNumber
			if (!StringUtils.isEmpty(fInvoiceNumber)) {
				info.setInvoiceNumber(fInvoiceNumber);
			}
			// ��Ʊ��� fInvoiceAmt
			if (!StringUtils.isEmpty(fInvoiceAmt)) {
				info.setInvoiceAmt(new BigDecimal(fInvoiceAmt));
			}
			// ��Ʊ���� fInvoiceDate
			if (!StringUtils.isEmpty(fInvoiceDate)) {
				info.setInvoiceDate(FDCTransmissionHelper.checkDateFormat(fInvoiceDate, getResource(context, "CWT_Import_kprqcw")));
			}
			// �ۼƷ�Ʊ��� fAllInvoiceAmt
			info.setAllInvoiceAmt(FDCTransmissionHelper.strToDouble(fAllInvoiceAmt));
			// ��ע fDescription
			info.setDescription(fDescription);
			// �Ƶ��� fCreatorName
			FilterInfo filterCreateUser = new FilterInfo();
			filterCreateUser.getFilterItems().add(new FilterItemInfo("number", fCreatorName));
			EntityViewInfo viewCreateUser = new EntityViewInfo();
			viewCreateUser.setFilter(filterCreateUser);
			UserCollection createUserColl = UserFactory.getLocalInstance(context).getUserCollection(viewCreateUser);
			if (!(createUserColl.size() > 0)) {
				// �Ƶ��˱���   
				// ��ϵͳ�в�����
				throw new TaskExternalException(getResource(context, "CWT_Import_fCreatorName3") + fCreatorName + getResource(context, "CWT_Import_NOTNULL"));
			}
			createUser = createUserColl.get(0);
			info.setCreator(createUser);
			// �Ƶ�ʱ�� fCreateTime
			info.setCreateTime(new Timestamp(FDCTransmissionHelper.checkDateFormat(fCreateTime, getResource(context, "CWT_Import_zdrqcw")).getTime()));
//			// �Ƿ�ɱ���� Ĭ��Ϊ��
//			info.setIsCostSplit(true);
			// ������״̬ Ĭ��Ϊ COMMON--����
			info.setConSplitExecState(ConSplitExecStateEnum.COMMON);
			// ���ɷ�ʽ Ĭ��Ϊ 0--ϵͳ����
			info.setSourceType(SourceTypeEnum.IMP);
			// ��ͬ���� Ĭ��Ϊ ���ı�(name = '���ı�')
			FilterInfo filter14 = new FilterInfo();
			filter14.getFilterItems().add(new FilterItemInfo("name", "���ı�"));
			EntityViewInfo view14 = new EntityViewInfo();
			view14.setFilter(filter14);
			ContractTypeCollection contractTypeColl = ContractTypeFactory.getLocalInstance(context).getContractTypeCollection(view14);
			if (contractTypeColl.size() > 0) {
				contractType = contractTypeColl.get(0);
				info.setContractType(contractType);
			} else {
				// "ϵͳ�в����ں�ͬ����Ϊ<���ı�>'�Ķ���,����ϵͳ�����Ӻ�ͬ����Ϊ<���ı�>�Ķ�����ٳ��Ե��룡"
				throw new TaskExternalException(getResource(context, "CWT_Import_contractTypeColl"));
			}
			// ������� Ĭ��Ϊ δ���--NOSPLIT
			info.setSplitState(CostSplitStateEnum.NOSPLIT);
			// �Ƿ��ݻ� Ĭ�� ��
			info.setIsRespite(false);		
			info.setSignDate(new Timestamp(FDCTransmissionHelper.checkDateFormat(fCreateTime, "").getTime()));
			
			/** �������ı���ͬ */
			ContractWithoutTextFactory.getLocalInstance(context).addnew(info);
			/** �ڱ������ı���֮ͬ��������ø������뵥�Լ��ɸ������뵥������� */
			this.createPayRequestBill(hashtableAll, context, info);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new TaskExternalException(e.getMessage(), e);
		}
		return null;
	}
	
//	/**
//	 * @description		�����ı���ͬ�ύ�������Զ����ɸ������뵥
//	 * @author				Ӻ����		
//	 * @createDate			2011-6-24
//	 * @param				coreBaseInfo
//	 * @param				context
//	 * @return				void	
//	 * @throws				TaskExternalException	void
//	 * @version			EAS1.0
//	 * @see	
//	 * (non-Javadoc)
//	 * @see com.kingdee.eas.tools.datatask.AbstractDataTransmission#submit(com.kingdee.eas.framework.CoreBaseInfo, com.kingdee.bos.Context)					
//	 */
//	public void submit(CoreBaseInfo coreBaseInfo, Context context) throws TaskExternalException {
//		
//		super.submit(coreBaseInfo, context);
//		
//		this.createPayRequestBill(hashtableAll, context);
//		
//	}
	
	/**
	 * @description		���ø������뵥��Ҫ����������<�����ı���ͬ���ݵ���ʱ,��Ҫ����һ�Ź������ڸ������뵥��>
	 * @author				Ӻ����	
	 * @createDate			2011-6-24
	 * @param 				hashtable
	 * @param 				context
	 * @return				void
	 * @throws 			TaskExternalException void
	 * @version			EAS1.0
	 * @see
	 */
	private void createPayRequestBill(Hashtable hashtable, Context context,ContractWithoutTextInfo info) throws TaskExternalException {
		
		payRequestBill = new PayRequestBillInfo();
		
		// ��֯����
		String fOrgUnitNumber  = ((String) ((DataToken)hashtable.get("FOrgUnit_number")).data).trim();
		// ����
		String fNumber  = ((String) ((DataToken)hashtable.get("FNumber")).data).trim();
		// ����
		String fName  = ((String) ((DataToken)hashtable.get("FName")).data).trim();
		// ״̬
		String fState  = ((String) ((DataToken)hashtable.get("FState")).data).trim();
		// ��������
		String fSignDate  = ((String) ((DataToken)hashtable.get("FSignDate")).data).trim();
		// ԭ�ҽ��
		String fOriginalAmount  = ((String) ((DataToken)hashtable.get("FOriginalAmount")).data).trim();
		// ����
		String exchangeRate = ((String) ((DataToken)hashtable.get("ExchangeRate")).data).trim();
		// ���㷽ʽ
		String fSettlementTypeNumber  = ((String) ((DataToken)hashtable.get("FSettlementType_number")).data).trim();
		// �տλ����
		String fReceiveUnitName  = ((String) ((DataToken)hashtable.get("FReceiveUnit_name_l2")).data).trim();
		// ��λ�ҽ��
		String fAmount  = ((String) ((DataToken)hashtable.get("FAmount")).data).trim();
		// ʵ���տλ
		String realSupplierNamel2 = ((String) ((DataToken)hashtable.get("RealSupplierNamel2")).data).trim();
		// �տ�����
		String fBank  = ((String) ((DataToken)hashtable.get("FBank")).data).trim();
		// �����˺�
		String fBankAcct  = ((String) ((DataToken)hashtable.get("FBankAcct")).data).trim();
		// �Ƿ���Ҫ����
		String fIsNeedPaid  = ((String) ((DataToken)hashtable.get("FIsNeedPaid")).data).trim();
		// ����
		String attachment = ((String) ((DataToken)hashtable.get("Attachment")).data).trim();
		// ����˵��
		String moneyDesc = ((String) ((DataToken)hashtable.get("MoneyDesc")).data).trim();
		// ��Ʊ��
		String fInvoiceNumber  = ((String) ((DataToken)hashtable.get("FInvoiceNumber")).data).trim();
		// ��Ʊ���
		String fInvoiceAmt  = ((String) ((DataToken)hashtable.get("FInvoiceAmt")).data).trim();
		// ��Ʊ����
		String fInvoiceDate  = ((String) ((DataToken)hashtable.get("FInvoiceDate")).data).trim();
		// �ۼƷ�Ʊ���
		String fAllInvoiceAmt  = ((String) ((DataToken)hashtable.get("FAllInvoiceAmt")).data).trim();
		// ��ע
		String fDescription  = ((String) ((DataToken)hashtable.get("FDescription")).data).trim();
		// �Ƶ�ʱ��
		String fCreateTime  = ((String) ((DataToken)hashtable.get("FCreateTime")).data).trim();
		// ����ʱ��
		String fAuditTime  = ((String) ((DataToken)hashtable.get("FAuditTime")).data).trim();
		// �Ƿ�Ӽ�
		String urgentDegree = ((String) ((DataToken)hashtable.get("UrgentDegree")).data).trim();
		
		/**
		 * ��������Ҫ�Ĳ���ֵ
		 */
		try {
			// ɾ��֮ǰ�Ķ��ึ�����뵥
			String sql = "delete from T_CON_PayRequestBill where FContractId = ? ";
			Object[] params = new Object[] { fNumber };
			DbUtil.execute(context, sql, params);
			checkNumber(context, fNumber);
			// �������뵥�����ظ���У��
			String number = payReqNumber;
			// ��������
			payRequestBill.setPaymentType(paymentType);
			// �ÿ��
			payRequestBill.setUseDepartment(adminOrgUnit);
			// �������뵥����
			payRequestBill.setNumber(number);
			// ��������
			payRequestBill.setPayDate(FDCTransmissionHelper.checkDateFormat(fSignDate, getResource(context, "CWT_Import_fkrqcw")));
			// �տλ
			FilterInfo filterSupplier = new FilterInfo();
			filterSupplier.getFilterItems().add(new FilterItemInfo("number", fReceiveUnitName));
			EntityViewInfo viewSupplier = new EntityViewInfo();
			viewSupplier.setFilter(filterSupplier);
			SupplierCollection supplierColl = SupplierFactory.getLocalInstance(context).getSupplierCollection(viewSupplier);
			if (!(supplierColl.size() > 0)) {
				// "���տλ����"
				// "��ϵͳ�в�����"
				throw new TaskExternalException(getResource(context, "CWT_Import_fReceiveUnitName2") + fReceiveUnitName + getResource(context, "CWT_Import_NOTNULL"));
			}
			supplier = supplierColl.get(0);
			payRequestBill.setSupplier(supplier);
			// ʵ���տλ
			if (!StringUtils.isEmpty(realSupplierNamel2)) {
				FilterInfo filterRealSupplier = new FilterInfo();
				filterRealSupplier.getFilterItems().add(new FilterItemInfo("name", realSupplierNamel2));
				EntityViewInfo viewRealSupplier = new EntityViewInfo();
				viewRealSupplier.setFilter(filterRealSupplier);
				SupplierCollection realSupplierColl = SupplierFactory.getLocalInstance(context).getSupplierCollection(viewRealSupplier);
				if (realSupplierColl.size() > 0) {
					supplier = realSupplierColl.get(0);
					payRequestBill.setRealSupplier(supplier);
				} else {
					// 1 "��ʵ���տλ "
					// 2 " ��ϵͳ�в����ڣ�"
					throw new TaskExternalException(getResource(context, "CWT_Import_realSupplierNamel22") + realSupplierNamel2 + getResource(context, "CWT_Import_NOTNULL"));
				}
			}
			// ���㷽ʽ
			if (!StringUtils.isEmpty(fSettlementTypeNumber)) {
				payRequestBill.setSettlementType(settlementType);
			}
			// �տ�����
			payRequestBill.setRecBank(fBank);
			// �տ��ʺ�
			payRequestBill.setRecAccount(fBankAcct);
			// �����̶� <Ĭ��Ϊ����ͨ--NORMAL>
			payRequestBill.setUrgentDegree(UrgentDegreeEnum.NORMAL);
			// �ұ�
			payRequestBill.setCurrency(currency);
			// ���� <����Ϊ��ʱ,ȡ��ǰ�ұ����Ļ���>
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			ExchangeRateInfo exchangeRateInfo = new ExchangeRateInfo();
			if (StringUtils.isEmpty(exchangeRate)) {  // �������Ϊ�գ���ȡ��ǰ�ұ����
				if (currency.getNumber().equals("RMB")) {
					exchangeRate = "1.00";
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
							exchangeRate = bigDecimal.toString();
						}
					} else {
						exchangeRate = "1.00";
					}
				}
			} else {
				if (FDCTransmissionHelper.isRangedInHundred(exchangeRate) == null) {
					// ����¼�벻�Ϸ�
					throw new TaskExternalException(getResource(context, "Import_fExRate10"));
				}
			}
			payRequestBill.setExchangeRate(new BigDecimal(exchangeRate));
			// ��λ�ҽ�� (���Ϊ��,����Ϊ��ǰ���*����)
			if (StringUtils.isEmpty(fAmount) || new BigDecimal(fAmount).compareTo(new BigDecimal("0")) == 0) {
				fAmount = ((FDCTransmissionHelper.strToDouble(fOriginalAmount) * FDCTransmissionHelper.strToDouble(exchangeRate)) + "").trim();
			}
			// ��д���
			if (currency.getNumber().equals("RMB")) {
				if (currency.getIsoCode() == null) {
					String id = "dfd38d11-00fd-1000-e000-1ebdc0a8100dDEB58FDC";
					currency.setId(BOSUuid.read(id));
					currency.setIsoCode("RMB");
				}
				payRequestBill.setCapitalAmount(FDCHelper.transCap(currency, new BigDecimal(fAmount)));
			}
			// ��λ�ҽ��
			if (!StringUtils.isEmpty(fAmount)) {
				payRequestBill.setAmount(new BigDecimal(fAmount));
			}
			// ԭ�ҽ��
			payRequestBill.setOriginalAmount(new BigDecimal(fOriginalAmount));
			// ��;
//			payRequestBill.setUsage();
			// ����˵��
			payRequestBill.setMoneyDesc(moneyDesc);
			// ��ע
			payRequestBill.setDescription(fDescription);
			// ����
			if (!StringUtils.isEmpty(attachment)) {
				payRequestBill.setAttachment(Integer.parseInt(attachment));
			} else {
				payRequestBill.setAttachment(0);
			}
			// ��ͬ�������
			payRequestBill.setPayTimes(0);
			// ������Ŀ
			payRequestBill.setCurProject(curProject);
			// �ر�״̬ <Ĭ��Ϊ�� false>
			payRequestBill.setHasClosed(false);
			// ��������
			payRequestBill.setBookedDate(FDCTransmissionHelper.checkDateFormat(fCreateTime, getResource(context, "CWT_Import_sqrqcw")));
			// ҵ������ 
			payRequestBill.setBizDate(FDCTransmissionHelper.checkDateFormat(fCreateTime, ""));
			// �ڼ�
//			payRequestBill.setPeriod();
			// ��ͬ��
			payRequestBill.setContractNo(number);
			// ��ͬID <��Ϊ��ǰ���ı���ͬ��ID>
			FilterInfo filterID = new FilterInfo();
			filterID.getFilterItems().add(new FilterItemInfo("number", fNumber));
			EntityViewInfo viewID = new EntityViewInfo();
			viewID.setFilter(filterID);
			ContractWithoutTextCollection contractWithoutTextColl = ContractWithoutTextFactory.getLocalInstance(context).getContractWithoutTextCollection(viewID);
			if (contractWithoutTextColl.size() > 0) {
				payRequestBill.setContractId(String.valueOf(contractWithoutTextColl.get(0).getId()));
			} else {
				// "�����ı���ͬ���������������뵥ʧ�ܣ����鵼��ģ�����Ƿ���ڲ�����Ҫ������ݣ�"
				throw new TaskExternalException(getResource(context, "CWT_Import_fNumber4"));
			}
			// ��������%    
			payRequestBill.setCurReqPercent(new BigDecimal(FDCTransmissionHelper.strToDouble(String.valueOf(contractWithoutTextColl.get(0).getAmount())) / FDCTransmissionHelper.strToDouble(fOriginalAmount)));
			// �ۼ�����%    
			payRequestBill.setAllReqPercent(new BigDecimal(FDCTransmissionHelper.strToDouble(String.valueOf(contractWithoutTextColl.get(0).getAmount())) / FDCTransmissionHelper.strToDouble(fOriginalAmount)));
			// ��ͬ�ڹ��̿�_��������ԭ��
			payRequestBill.setProjectPriceInContractOri(new BigDecimal(fOriginalAmount));
			// ���ڼƻ�����  
			payRequestBill.setCurPlannedPayment(bigDecimal);
			// ������
			payRequestBill.setSettleAmt(bigDecimal);
			// �������
			payRequestBill.setLatestPrice(contractWithoutTextColl.get(0).getAmount());
			// �����뵥�Ѹ����
			payRequestBill.setPayedAmt(bigDecimal);
			// ���ǩ֤ȷ�Ͻ��
			payRequestBill.setChangeAmt(bigDecimal);
			// ����Ƿ����    
			payRequestBill.setCurBackPay(bigDecimal);
			// <Ӧ�ۼ׹����Ͽ���ڷ�����_TODO>
			payRequestBill.setPayPartAMatlAmt(bigDecimal);
			// <��ͬ�ڹ��̿���ڷ�����_��ͬ�ڹ��̿�_��������ԭ��>
			payRequestBill.setProjectPriceInContract(new BigDecimal(fOriginalAmount));
			// <�׹����ۼ������_Ϊ��>
			payRequestBill.setPayPartAMatlAllReqAmt(bigDecimal);
			// ʵ����ڷ�����
			payRequestBill.setCurPaid(new BigDecimal(fOriginalAmount));
			// ��ͬ�ڹ����ۼ�����
			payRequestBill.setPrjAllReqAmt(new BigDecimal(fOriginalAmount));
			// ���ӹ��̿��ۼ�����
			payRequestBill.setAddPrjAllReqAmt(bigDecimal);
			// ������Դ��ʽ<��������>
			payRequestBill.setSourceType(SourceTypeEnum.CREATE);
			// ��ͬ�ڹ��̿������ۼ�����
			payRequestBill.setLstPrjAllReqAmt(new BigDecimal(FDCTransmissionHelper.strToDouble(String.valueOf(payRequestBill.getCurPaid())) - FDCTransmissionHelper.strToDouble(String.valueOf(payRequestBill.getPrjAllReqAmt()))));
			// ��Դ���� <�����ı���ͬ���� -- 3D9A5388>
			payRequestBill.setSource("3D9A5388");
			// ��ͬ����
			payRequestBill.setContractName(fName);
			payRequestBill.setContractBase(contractWithoutTextColl.get(0).getContractBaseData());
			// ��ͬ���
			payRequestBill.setContractPrice(new BigDecimal(fAmount));
			// �������뵥����
			payRequestBill.setName(fName);
			// �������뵥 NULL
			payRequestBill.setNull(fNumber + fName);
//			// ���޽���(Ĭ���ɺ�ͬ����)
//			payRequestBill.setGrtAmount(contractWithoutTextColl.get(0).getGrtAmount());
			// ״̬
			if (fState.equals(getResource(context, "CWT_Import_saved"))) {
				payRequestBill.setState(FDCBillStateEnum.SAVED);
			} else if (fState.equals(getResource(context, "CWT_Import_submitted"))) {
				payRequestBill.setState(FDCBillStateEnum.SUBMITTED);
			} else { //  Ĭ��Ϊ <������>
				payRequestBill.setState(FDCBillStateEnum.AUDITTED);
				// ����˱��� <"״̬"�ֶ������ĵ��в�����,��Ҫ�޸������ĵ���Ӹ��ֶ�>
				payRequestBill.setAuditor(auditoreUser);
				// ����ʱ��
				payRequestBill.setCreateTime(new Timestamp(FDCTransmissionHelper.checkDateFormat(fAuditTime, getResource(context, "CWT_Import_spsjcw")).getTime()));
			}
			// �������
			payRequestBill.setPaymentProportion(FDCHelper.ONE_HUNDRED);
			// ���깤���������
			payRequestBill.setCompletePrjAmt(new BigDecimal(fAmount));// ���깤���������
			// �ɱ�����
			CostCenterOrgUnitInfo costCenterOrgUnit = curProject.getCostCenter(); //  ������Ŀ��Ӧ�ɱ�������֯
			if (!StringUtils.isEmpty(fOrgUnitNumber) && fOrgUnitNumber.equals(costCenterOrgUnit.getLongNumber())) {
				// ԭת����֯���󷽷� <�ݲ��Ƽ�ʹ��> (FullOrgUnitInfo)FullOrgUnitInfo.class.cast(costCenterOrgUnit)
				payRequestBill.setOrgUnit(costCenterOrgUnit.castToFullOrgUnitInfo());
			}
			// ����Ԫ
//			payRequestBill.setCU();
			// ���踶������뵥
			if (fIsNeedPaid.equals(getResource(context, "CWT_Import_YES"))) {
				payRequestBill.setIsPay(true);
			} else {
				payRequestBill.setIsPay(false);
			}
			// ��Ʊ��
			payRequestBill.setInvoiceNumber(fInvoiceNumber);
			// ��Ʊ����
			if (!StringUtils.isEmpty(fInvoiceDate)) {
				payRequestBill.setInvoiceDate(FDCTransmissionHelper.checkDateFormat(fInvoiceDate, getResource(context, "CWT_Import_kprqcw")));
			}
			// ��Ʊ���
			if (!StringUtils.isEmpty(fInvoiceAmt)) {
				payRequestBill.setInvoiceAmt(new BigDecimal(fInvoiceAmt));
			} else {
				payRequestBill.setInvoiceAmt(new BigDecimal(fAmount));
			}
			// �ۼƷ�Ʊ���
			if (!StringUtils.isEmpty(fInvoiceAmt)) {
				payRequestBill.setAllInvoiceAmt(new BigDecimal(fAllInvoiceAmt));
			} else {
				payRequestBill.setInvoiceAmt(new BigDecimal(fAmount));
			}
			// �Ƿ�Ӽ�
			if (urgentDegree.equals(getResource(context, "CWT_Import_YES"))) {
				payRequestBill.setUrgentDegree(UrgentDegreeEnum.URGENT); // �Ӽ�
			} else {
				payRequestBill.setUrgentDegree(UrgentDegreeEnum.NORMAL); // ��ͨ
			}
			// ͬ����� Ĭ��Ϊͬ��
			payRequestBill.setIsDifferPlace(DifPlaceEnum.samePlace);
			
			if (payRequestBill.getState().getValue().equals("4AUDITTED")) {
				/** ִ���ύ�������뵥 */
				PayRequestBillFactory.getLocalInstance(context).submit(payRequestBill);
				/** ִ�������������뵥 */
				ContractWithoutTextFactory.getLocalInstance(context).audit(info.getId());
			} else {
				/** ִ�������ø������뵥��¼ */
				PayRequestBillFactory.getLocalInstance(context).addnew(payRequestBill);
			}
			
//			/** ִ�������ø������뵥��¼ */
//			PayRequestBillFactory.getLocalInstance(context).addnew(payRequestBill);
//			/** �ڸ������뵥��¼����֮������޸��佱�����ۿΥԼ���׹��Ŀۿ�� */
//			this.updateAdjustClause(payRequestBill, context);
//			// ��ִ����ɸ��ĸ������뵥֮����ִ�е������ɸ��
//			/** �������ɸ���ķ��� */
//			this.createPaymentBill(payRequestBill, context);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new TaskExternalException(e.getMessage(), e);
		}
		
	}

	/**
	 * @description		���ø����Ҫ����������<�����ı���ͬ���ݵ���ʱ,�������һ��������״̬�����ı���ͬ,���Զ�����һ��������״̬�ĸ������뵥,��ϵͳ�������һ��������״̬�ĸ������뵥,��ô�ͻ��Զ�����һ�����>
	 * @author				Ӻ����	
	 * @createDate			2011-7-20
	 * @param 				payRequestBill<����ĸ������뵥����>
	 * @param 				context<�׳���ʾ��Ϣ����Ҫʹ�õĲ���>
	 * @return				void
	 * @throws 			BOSException void
	 * @version			EAS1.0
	 * @throws EASBizException 
	 * @see
	 */
	private void createPaymentBill(PayRequestBillInfo payRequestBill, Context context) throws BOSException, EASBizException {
			// �������뵥
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", payRequestBill.getNumber()));
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			PayRequestBillCollection prbColl = PayRequestBillFactory.getLocalInstance(context).getPayRequestBillCollection(view);
			if (prbColl.size() == 0) {
			return;
			}
			
			/** ��Ҫ�жϸ������뵥��״̬�������������ʱ���Ŵ�������״̬�ĸ���� */
			if (!String.valueOf(prbColl.get(0).getState()).equals(getResource(context, "CWT_Import_auditted"))) {
				// ����������뵥״̬Ϊδ����ʱֱ�ӷ��ء�
				return;
			}
			paymentBill = new PaymentBillInfo();
			// ��֯����
			CostCenterOrgUnitInfo costCenterOrgUnit = curProject.getCostCenter(); //  ������Ŀ��Ӧ�ɱ�������֯
			FilterInfo filter1 = new FilterInfo();
			filter1.getFilterItems().add(new FilterItemInfo("id", String.valueOf(costCenterOrgUnit.getId())));
			EntityViewInfo view1 = new EntityViewInfo();
			view1.setFilter(filter1);
			CostCenterOrgUnitCollection ccouc = CostCenterOrgUnitFactory.getLocalInstance(context).getCostCenterOrgUnitCollection(view1);
			paymentBill.setCostCenter(ccouc.get(0));
			// ������Ŀ����
			paymentBill.setCurProject(payRequestBill.getCurProject());
			// �����˾
			// ��������
			paymentBill.setFdcPayType(paymentType);
			// �ÿ��
			paymentBill.setUseDepartment(payRequestBill.getUseDepartment());
			// ҵ������
			paymentBill.setBizDate(payRequestBill.getBizDate());
			// �ո����
			// �տʡ
			// �տ��/��
			// �����ʺ�
//			// �տ������
//			paymentBill.setFdcPayeeType(FdcPayeeTypeEnum.getEnum("1OTHER"));
			// ������Ϣ
			// �տ�������
			// �տ���id
			// �տ��˱��
			// ʵ���տλȫ��
			paymentBill.setActFdcPayeeName(payRequestBill.getRealSupplier());
			// �տλȫ��
			paymentBill.setFdcPayeeName(payRequestBill.getSupplier());
			// �����Ŀ
			// �տ�����
			paymentBill.setPayeeBank(payRequestBill.getRecBank());
			// �տ��˻�
			paymentBill.setPayeeAccountBank(payRequestBill.getRecAccount());
			// �ұ����
			paymentBill.setCurrency(payRequestBill.getCurrency());
			// ����
			paymentBill.setExchangeRate(payRequestBill.getExchangeRate());
			// ҵ������
			// ԭ�ҽ��
			paymentBill.setAmount(payRequestBill.getAmount());
			paymentBill.setActPayLocAmt(payRequestBill.getAmount());
			paymentBill.setCurPaid(payRequestBill.getAmount());
			paymentBill.setLocalAmt(payRequestBill.getAmount());
			paymentBill.setSummary("120340093");
			paymentBill.setSettleAmt(payRequestBill.getAmount());
			FilterInfo filter2 = new FilterInfo();
			filter2.getFilterItems().add(new FilterItemInfo("name", "��Ӧ��"));
			EntityViewInfo view2 = new EntityViewInfo();
			view2.setFilter(filter2);
			AsstActTypeCollection aatColl = AsstActTypeFactory.getLocalInstance(context).getAsstActTypeCollection(view2);
			if (aatColl.size() > 0) {
				paymentBill.setPayeeType(aatColl.get(0));
			}
			paymentBill.setPayeeID(String.valueOf(payRequestBill.getSupplier().getId()));
			FilterInfo filter3 = new FilterInfo();
			filter3.getFilterItems().add(new FilterItemInfo("id", payRequestBill.getSupplier().getId()));
			EntityViewInfo view3 = new EntityViewInfo();
			view3.setFilter(filter3);
			SupplierCollection sColl = SupplierFactory.getLocalInstance(context).getSupplierCollection(view3);
			if (sColl.size() > 0) {
				paymentBill.setPayeeNumber(sColl.get(0).getNumber());
				paymentBill.setPayeeName(sColl.get(0).getName());
			}
			paymentBill.setPayDate(payRequestBill.getPayDate());
			// ժҪ
			paymentBill.setDescription(payRequestBill.getDescription());
			// ��;
			paymentBill.setUsage(payRequestBill.getUsage());
			// ��Ŀ
			FilterInfo filter4 = new FilterInfo();
			filter4.getFilterItems().add(new FilterItemInfo("id", payRequestBill.getCurProject().getId()));
			EntityViewInfo view4 = new EntityViewInfo();
			view4.setFilter(filter4);
			CurProjectCollection cpColl = CurProjectFactory.getLocalInstance(context).getCurProjectCollection(view4);
			if (cpColl.size() > 0) {
				filter4 = new FilterInfo();
				filter4.getFilterItems().add(new FilterItemInfo("name", cpColl.get(0).getName()));
				view4 = new EntityViewInfo();
				view4.setFilter(filter4);
				ProjectCollection projcetColl = ProjectFactory.getLocalInstance(context).getProjectCollection(view4);
				if (projcetColl.size() > 0) {
					paymentBill.setProject(projcetColl.get(0));
				}
			}
			// ��Ŀ�ƻ�
			// ������
			// ���㷽ʽ��Ϊ��ʱ��������
			if (payRequestBill.getSettlementType() != null) {
				// ���㷽ʽ
				paymentBill.setSettlementType(payRequestBill.getSettlementType());
				// �����
				paymentBill.setSettlementNumber(payRequestBill.getSettlementType().getNumber());
			}
			// �Ƶ��˱���
			FilterInfo filter11 = new FilterInfo();
			filter11.getFilterItems().add(new FilterItemInfo("id", String.valueOf(prbColl.get(0).getCreator().getId())));
			EntityViewInfo view11 = new EntityViewInfo();
			view11.setFilter(filter11);
			UserCollection userColl = UserFactory.getLocalInstance(context).getUserCollection(view11);
			paymentBill.setCreator(userColl.get(0));
			// �Ƶ�ʱ��
			paymentBill.setCreateTime(payRequestBill.getCreateTime());
			// ��������
			FilterInfo filter22 = new FilterInfo();
			filter22.getFilterItems().add(new FilterItemInfo("name", "����"));
			EntityViewInfo view22 = new EntityViewInfo();
			view22.setFilter(filter22);
			PaymentBillTypeCollection pbtColl = PaymentBillTypeFactory.getLocalInstance(context).getPaymentBillTypeCollection(view22);
			if (pbtColl.size() > 0) {
				paymentBill.setPayBillType(pbtColl.get(0));
			}
			// ����
			// ���
			// ��ע
			// �������
			paymentBill.setLatestPrice(payRequestBill.getLatestPrice());
			// ��ͬ�ڹ��̿�_���ڷ���(ԭ�� )
			paymentBill.setProjectPriceInContract(payRequestBill.getProjectPriceInContract());
			// ���ڼƻ�����
			paymentBill.setCurPlannedPayment(payRequestBill.getCurPlannedPayment());
			// ����Ƿ����
			paymentBill.setCurBackPay(payRequestBill.getCurBackPay());
			// ��������%
			paymentBill.setCurReqPercent(payRequestBill.getCurReqPercent());
			// �ۼ�����%
			paymentBill.setAllReqPercent(payRequestBill.getAllReqPercent());
			// �������
			paymentBill.setImageSchedule(payRequestBill.getImageSchedule());
	        // "����", award ��Ϊ�� ����������
			// "ΥԼ", breakcontract ��Ϊ�� ����������
			// "�ۿ�", deduct ��Ϊ�� ����������
			// �׹��Ŀۿ� ��Ϊ�� ����������
			paymentBill.setPayPartAMatlAmt(payRequestBill.getPayPartAMatlAmt());
			// ��ͬ���
			paymentBill.setContractNo(payRequestBill.getContractNo());
			// ��Ŀ����
			// ��ͬ�����ڲ�Ϊ��ʱ��ֵ
			if (payRequestBill.getContractBase() != null) {
				// ��ͬ����
				paymentBill.setContractBase(payRequestBill.getContractBase());
				// ��ͬid
				paymentBill.setContractBillId(String.valueOf(payRequestBill.getContractBase().getId()));
			}
			// �������
			paymentBill.setNumber(payRequestBill.getNumber());
			// �������
			paymentBill.setNull(payRequestBill.getNumber() + payRequestBill.getName());
			// �������뵥����
			paymentBill.setFdcPayReqNumber(payRequestBill.getNumber());
			// �������뵥
			paymentBill.setFdcPayReqID(String.valueOf(prbColl.get(0).getId()));
			
			/** ���²������������ɵ�����¹̶����� */
			paymentBill.setSourceType(com.kingdee.eas.fi.cas.SourceTypeEnum.FDC);// 107 ���ز��ɱ�����
			paymentBill.setIsExchanged(false);// ��ʼĬ�Ͼ�Ϊfalse��Ϊ0
			paymentBill.setIsRelateCheque(false);
			paymentBill.setIsCtrlOppAcct(false);
			paymentBill.setIsRedBill(false);
			paymentBill.setIsTransBill(false);
			paymentBill.setIsTransOtherBill(false);
			paymentBill.setIsCommittoBe(false);
			paymentBill.setIsImpFromGL(false);
			paymentBill.setIsCoopBuild(false);
			paymentBill.setIsInitializeBill(false);
			paymentBill.setFiVouchered(false);
			paymentBill.setIsImport(false);
			paymentBill.setIsNeedVoucher(true);//�Ƿ���Ҫ֧����Ĭ��true����Ϊ1
			paymentBill.setIsNeedPay(true);//�Ƿ���Ҫ֧����Ĭ��true����Ϊ1
			paymentBill.setIsReverseLockAmount(true);// �Ƿ�д������Ĭ��Ϊtrue��Ϊ1
			paymentBill.setIsDifferPlace(DifPlaceEnum.samePlace);//�Ƿ���أ�Ĭ�Ϸ�ͬ�Ƿ����
			paymentBill.setActPayAmtVc(bigDecimal);// ��ʼĬ��Ϊ0
			paymentBill.setActPayLocAmtVc(bigDecimal);
			paymentBill.setScheduleAmt(bigDecimal);
			paymentBill.setPaymentPlan(bigDecimal);
			paymentBill.setAddPrjAllReqAmt(bigDecimal);
			paymentBill.setLstAddPrjAllPaidAmt(bigDecimal);
			paymentBill.setLstAMatlAllPaidAmt(bigDecimal);
			paymentBill.setLstAddPrjAllReqAmt(bigDecimal);
			paymentBill.setLstAMatlAllReqAmt(bigDecimal);
			paymentBill.setBgAmount(bigDecimal);
			paymentBill.setDayaccount(bigDecimal);
			paymentBill.setIsEmergency(IsMergencyEnum.normal);// �Ƿ�Ӽ���Ĭ��Ϊ��ͨ��Ϊ0
			paymentBill.setIsDifBank(DifBankEnum.SameBank);// ͬ�п��У�Ĭ��Ϊͬ�м�Ϊ0
			paymentBill.setBillStatus(BillStatusEnum.SAVE);// ����״̬��Ĭ��Ϊ����״̬��Ϊ10
			paymentBill.setSettlementStatus(SettlementStatusEnum.UNSUBMIT);// ���н���״̬��Ĭ��Ϊδ�ύ״̬��Ϊ10
			paymentBill.setAccessoryAmt(0);// ��������Ĭ��Ϊ0��
			paymentBill.setSettleBizType(SettBizTypeEnum.PAYOUTSIDE);// ���⸶��
			
			/** ִ�б��渶� */
			PaymentBillFactory.getLocalInstance(context).addnew(paymentBill);
	}

	/**
	 * @description		�ڸ������뵥����֮���޸���������	
	 * @author				Ӻ����	
	 * @param 				payRequestBill<����ĸ������뵥����>
	 * @param 				context<�׳���ʾ��Ϣ����Ҫʹ�õĲ���>
	 * @createDate			2011-7-20
	 * @throws 			BOSException void
	 * @version			EAS1.0
	 * @throws EASBizException 
	 * @see
	 */
	private void updateAdjustClause(PayRequestBillInfo payRequestBill, Context context) throws BOSException, EASBizException {
			// �������뵥
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", payRequestBill.getNumber()));
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			PayRequestBillCollection prbColl = PayRequestBillFactory.getLocalInstance(context).getPayRequestBillCollection(view);
			if (prbColl.size() == 0) {
			return;
			}
			
			PayRequestBillInfo payRequestBillInfo = prbColl.get(0);
			// ��ͬ�ڹ��̿�_��������ԭ��
			String fProjectPriceInContractOri = String.valueOf(payRequestBillInfo.getProjectPriceInContractOri());
			// ����     <�����ڽ������븶�����뵥���м����>
			String reward = "";
			// ΥԼ     <������ΥԼ���븶�����뵥���м����>
			String breach = "";
			// �ۿ�     <�����ڿۿ�븶�����뵥���м����>
			String fine = "";
			// �׹��Ŀۿ�   <�����ڼ׹��Ŀۿ��븶�����뵥���м����>
			String stuffFine = "";
			// ��ѯ�������뵥
			FilterInfo pFilter = new FilterInfo();
			pFilter.getFilterItems().add(new FilterItemInfo("number", payRequestBillInfo.getNumber()));
			EntityViewInfo pView = new EntityViewInfo();
			pView.setFilter(pFilter);
			PayRequestBillCollection payRequestBillColl =PayRequestBillFactory.getLocalInstance(context).getPayRequestBillCollection(pView);
			if (payRequestBillColl.size() > 0) {
				payRequestBillInfo = payRequestBillColl.get(0);
				// �ڱ��渶�����뵥֮�� ���õ�������<������ΥԼ���ۿ�׹��Ŀۿ�>
				// Ϊ�������ܲ�������Ա��BT����ֻ���������ˣ�������������������
				// ����        (�м��<T_CON_GuerdonOfPayReqBill>)reward
				FilterInfo rewardFilter = new FilterInfo();
				rewardFilter.getFilterItems().add(new FilterItemInfo("contract", payRequestBillInfo.getContractId()));
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
				breachFilter.getFilterItems().add(new FilterItemInfo("contract", payRequestBillInfo.getContractId()));
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
				fineFilter.getFilterItems().add(new FilterItemInfo("payRequestBill", String.valueOf(payRequestBillInfo.getId())));
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
				stuffFineFilter.getFilterItems().add(new FilterItemInfo("payRequestBill", String.valueOf(payRequestBillInfo.getId())));
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
				payRequestBillInfo.setAmount(all);
				if (currency.getIsoCode() == null) {
					String id = "dfd38d11-00fd-1000-e000-1ebdc0a8100dDEB58FDC";
					currency.setId(BOSUuid.read(id));
					currency.setIsoCode("RMB");
				}
				// ��д���
				payRequestBillInfo.setCapitalAmount(FDCHelper.transCap(currency, all));
				// ԭ�ҽ��
				payRequestBillInfo.setOriginalAmount(all);
				/** ִ���޸Ĵ˸������뵥 */
				PayRequestBillFactory.getLocalInstance(context).update(new ObjectUuidPK(String.valueOf(payRequestBillInfo.getId())), payRequestBillInfo);
			}
	}

	/**
	 * �������Ƿ���ͬ
	 * @throws BOSException 
	 * @throws SQLException 
	 */
	private void checkNumber(Context ctx, String number) throws BOSException, SQLException {
		if (number == null || number.equals("")) {
			return;
		}
		payReqNumber = number;
		// �������뵥�����ظ���У��
		String sql = "select fid from T_CON_PayRequestBill where FNumber = ? ";
		Object[] params = new Object[] { number };

		RowSet rowset = DbUtil.executeQuery(ctx, sql, params);
		if (rowset.next()) {
			payReqNumber = payReqNumber + getResource(ctx, "CWT_Import_withoutText");
			checkNumber(ctx, payReqNumber);
		}
	}
	
	/**
	 * �õ���Դ�ļ�
	 */
	public static String getResource(Context context, String key) {
		return ResourceBase.getString(resource, key, context.getLocale());
	}
}
