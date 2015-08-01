package com.kingdee.eas.fdc.contract.client;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.reportone.r1.print.data.R1PrintDataSource;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.multiapprove.MultiApproveCollection;
import com.kingdee.eas.base.multiapprove.MultiApproveFactory;
import com.kingdee.eas.base.multiapprove.MultiApproveInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.DeductTypeCollection;
import com.kingdee.eas.fdc.basedata.DeductTypeFactory;
import com.kingdee.eas.fdc.basedata.DeductTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.util.FDCNoteDataProvider;
import com.kingdee.eas.fdc.contract.ContractBillEntryCollection;
import com.kingdee.eas.fdc.contract.ContractBillEntryFactory;
import com.kingdee.eas.fdc.contract.ContractBillEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.finance.client.PaymentBillContants;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.FeeTypeInfo;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.jdbc.rowset.impl.ColInfo;
import com.kingdee.jdbc.rowset.impl.DynamicRowSet;
import com.kingdee.util.enums.Enum;

public class PayRequestBillRowsetProvider extends FDCNoteDataProvider {
	private static final Logger logger = Logger.getLogger(PayRequestBillRowsetProvider.class);
	
	/**
	 * ��ͬ��Ӧ�Ļ���
	 */
	private BigDecimal exRate = null;

	static public final String[] col = new String[] { "curProject.name",
			"curProject.number", "useDepartment.name", "useDepartment.number",
			"contractNo", "bookedDate", "bizDate", "number", "payDate",
			"period.periodNumber", "period.number", "paymentType.name",
			"paymentType.number", "isDifferPlace", "settlementType.name",
			"settlementType.number", "recBank", "description", "supplier.name",
			"realSupplier.name", "recAccount", "usage", "currency.name",
			"currency.number", "exchangeRate", "attachment", "originalAmount",
			"paymentProportion", "amount", "grtAmount", "completePrjAmt",
			"capitalAmount", "moneyDesc", "urgentDegree", "isPay",
			"createTime", "creator.name", "creator.number", "auditTime",
			"auditor.number", "auditor.name", "contractName", 
			"scheduleAmt",
			"curPlannedPayment", "curBackPay", "paymentPlan", "curReqPercent",
			"allReqPercent",
			"imageSchedule", 
			"contractId", "hasPayoff", "costAmount",
			"settleAmt", "payTimes",			  		 
			"deductType", 
			"restAmount",			
			"auditResult",
			"orgName",
			"oldProjNumber",
			"paidDataAmount",
			"projNameWithoutOrg",
			"paidDetail",
			
			"id",
			"contractBillAmount",//��ͬԭ�ҽ��
			"contractBillType",//��ͬ����
			"totalSettlePrice",//��ʵ�ֲ�ֵ
			"allCompletePrjAmt",
			"LstPrjAllPaidAmtAndthis",
			"latestPriceSub",

			"grtOriAmount",//���޽�ԭ��
			
			"settleOriAmt",//�������
			
			"thisTimeReqLocalAmt",//�������뱾�ҽ��
			"process",//�����������
			"invoiceNumber", //��Ʊ��     
			"invoiceDate", //��Ʊ��  
			"invoiceAmt", //��Ʊ����   
			"allInvoiceAmt", //�ۼƷ�Ʊ����
			"conGrtAmount",//��ͬ���޽�
			"conGrtOriAmount",//��ͬ���޽�ԭ��
			"conGrtRate",//��ͬ���޽����
			"invoiceOriAmt",//��Ʊ���ԭ��
			"allInvoiceOriAmt",//�ۼƷ�Ʊԭ��			
			"contractPrice",//��ͬ��۱���
			"contractOriPrice",//��ͬ���ԭ��
			"changeAmt",//���ָ����� 
			"changeOriAmt",//���ָ����ԭ��
			"latestPrice", //������۱���
			"latestOriPrice",//�������ԭ��
			"payedAmt",//�����뵥�Ѹ�����
			"payedOriAmt",//�����뵥�Ѹ�ԭ��
			"prjAllReqAmt",//��ͬ�ڹ��̿�_���������ۼ�����_����
			"prjAllReqOriAmt", //��ͬ�ڹ��̿�_���������ۼ�����_ԭ��
			"prjAllPaidAmt",//��ͬ�ڹ��̿�_���������ۼ�ʵ��_����
			"prjAllPaidOriAmt",//��ͬ�ڹ��̿�_���������ۼ�ʵ��_ԭ��
			"projectPriceInContract",//��ͬ�ڹ��̿�_���ڷ���_����
			"projectPriceInContractOri",//��ͬ�ڹ��̿�_���ڷ���_ԭ��
			"lstPrjAllPaidAmt", //��ͬ�ڹ��̿�_���������ۼ�ʵ��_����
			"lstPrjAllPaidOriAmt",//��ͬ�ڹ��̿�_���������ۼ�ʵ��_ԭ��
			"lstPrjAllReqAmt",//��ͬ�ڹ��̿�_���������ۼ�����_����
			"lstPrjAllReqOriAmt",//��ͬ�ڹ��̿�_���������ۼ�����_ԭ��
			"lstGuerdonPaidAmt",//����_���������ۼ�ʵ��_����
			"lstGuerdonPaidOriAmt",//����_���������ۼ�ʵ��_ԭ��
			"lstGuerdonReqAmt",//����_���������ۼ�����_����
			"lstGuerdonReqOriAmt",//����_���������ۼ�����_ԭ��
			"guerdonAmt",//����_���ڷ���_����
			"guerdonOriAmt",//����_���ڷ���_ԭ��
			"allGuerdonReqAmt",//����_���������ۼ�����_����
			"allGuerdonReqOriAmt",//����_���������ۼ�����_ԭ��
			"allGuerdonPaidAmt",//����_���������ۼ�ʵ��_����
			"allGuerdonPaidOriAmt",//����_���������ۼ�ʵ��_ԭ��
			"lstCompensationPaidAmt",//ΥԼ_���������ۼ�ʵ��_����
			"lstCompensationPaidOriAmt",//ΥԼ_���������ۼ�ʵ��_ԭ��
			"lstCompensationReqAmt",//ΥԼ_���������ۼ�����_����
			"lstCompensationReqOriAmt",//ΥԼ_���������ۼ�����_ԭ��
			"compensationAmt",//ΥԼ_���ڷ���_����
			"compensationOriAmt",//ΥԼ_���ڷ���_ԭ��
			"allCompensationReqAmt",//ΥԼ_���������ۼ�����_����
			"allCompensationReqOriAmt",//ΥԼ_���������ۼ�����_ԭ��
			"allCompensationPaidAmt", //ΥԼ_���������ۼ�ʵ��_����
			"allCompensationPaidOriAmt", //ΥԼ_���������ۼ�ʵ��_ԭ��
			"lstDeductPaidAmt",//�ۿ�_���������ۼ�ʵ��_����
			"lstDeductPaidOriAmt",//�ۿ�_���������ۼ�ʵ��_ԭ��
			"lstDeductReqAmt",//�ۿ�_���������ۼ�����_����
			"lstDeductReqOriAmt",//�ۿ�_���������ۼ�����_ԭ��
			"deductAmt",//�ۿ�_���ڷ���_����
			"deductOriAmt",//�ۿ�_���ڷ���_ԭ��
			"allDeductReqAmt",//�ۿ�_���������ۼ�����_����
			"allDeductReqOriAmt",//�ۿ�_���������ۼ�����_ԭ��
			"allDeductPaidAmt",//�ۿ�_���������ۼ�ʵ��_����
			"allDeductPaidOriAmt",//�ۿ�_���������ۼ�ʵ��_ԭ��
			"lstDeductSumPaidAmt",//�ۿ�С��_���������ۼ�ʵ��_����
			"lstDeductSumPaidOriAmt",//�ۿ�С��_���������ۼ�ʵ��_ԭ��
			"lstDeductSumReqAmt", //�ۿ�С��_���������ۼ�����_����
			"lstDeductSumReqOriAmt", //�ۿ�С��_���������ۼ�����_ԭ��
			"deductSumAmt",//�ۿ�С��_���ڷ���_����
			"deductSumOriAmt",//�ۿ�С��_���ڷ���_ԭ��
			"allDeductSumReqAmt",//�ۿ�С��_���������ۼ�����_����
			"allDeductSumReqOriAmt",//�ۿ�С��_���������ۼ�����_ԭ��
			"allDeductSumPaidAmt",//�ۿ�С��_���������ۼ�ʵ��_����
			"allDeductSumPaidOriAmt",//�ۿ�С��_���������ۼ�ʵ��_ԭ��
			"lstAMatlAllPaidAmt",//�׹��ۿ�_���������ۼ�ʵ��_����
			"lstAMatlAllPaidOriAmt",//�׹��ۿ�_���������ۼ�ʵ��_ԭ��
			"lstAMatlAllReqAmt",//�׹��ۿ�_���������ۼ�����_����
			"lstAMatlAllReqOriAmt",//�׹��ۿ�_���������ۼ�����_ԭ��
			"payPartAMatlAmt",//�׹��ۿ�_���ڷ���_����
			"payPartAMatlOriAmt",//�׹��ۿ�_���ڷ���_ԭ��
			"allPartAMatlAllPaidAmt", //�׹��ۿ�_���������ۼ�ʵ��_����
			"allPartAMatlAllPaidOriAmt", //�׹��ۿ�_���������ۼ�ʵ��_ԭ��
			"payPartAMatlAllReqAmt", //�׹��ۿ�_���������ۼ�����_����
			"payPartAMatlAllReqOriAmt", //�׹��ۿ�_���������ۼ�����_ԭ��
			"lstRealPaidAmt",//ʵ����_���������ۼ�ʵ��_���� 
			"lstRealPaidOriAmt",//ʵ����_���������ۼ�ʵ��_ԭ��
			"lstRealReqAmt",//ʵ����_���������ۼ�����_����
			"lstRealReqOriAmt",//ʵ����_���������ۼ�����_ԭ��
			"curPaid", //ʵ����_���ڷ���_����
			"curPaidOri", //ʵ����_���ڷ���_ԭ��
			"allRealReqAmt",//ʵ����_���������ۼ�����_����
			"allRealReqOriAmt",//ʵ����_���������ۼ�����_ԭ��
			"allRealPaidAmt",//ʵ����_���������ۼ�ʵ��_����
			"allRealPaidOriAmt",//ʵ����_���������ۼ�ʵ��_ԭ��
			"allPayableReqPercent",// �ۼ�Ӧ������
			"depPlanState",// �ƻ�����״̬
			
			// //////////////////////////////////////////////////////////////////////////
			// �������뵥��������չ�ֶΣ����ڿͻ��Զ��� by skyiter_wang 2014-12-04
			// ˵����
			// 1���������뵥���״�Ƚ�����
			// 2���ڴ��������˴���д����һЩ��ѯ�ֶΣ�����DEP��չ���ֶ��޷�����չʾ
			// 3������Ԥ����5����չ�ֶΣ�����ͻ��Զ���
			// 4���μ�PayRequestBillEditUI.actionPrintPreview_actionPerformed��PayRequestBillRowsetProvider
			
			"extField01",// ��չ�ֶ�01
			"extField02",// ��չ�ֶ�02
			"extField03",// ��չ�ֶ�03
			"extField04",// ��չ�ֶ�04
			"extField05",// ��չ�ֶ�05

			"extBooleanField01",// ��չ�����ֶ�01
			"extBooleanField02",// ��չ�����ֶ�02

			"extEnumField01",// ��չö���ֶ�01
			"extEnumField02"// ��չö���ֶ�02

			// //////////////////////////////////////////////////////////////////////////
	};
	
	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////
	
	public static String printStringHelper(Object o) {
		if (o == null) {
			return "";
		} else if (o instanceof BigDecimal) {
			return printBigDecimalHelper((BigDecimal) o);
		} else if (o instanceof Boolean) {
			return printBooleanHelper((Boolean) o);
		} else if (o instanceof Enum) {
			return printEnumHelper((Enum) o);
		}

		return String.valueOf(o);
	}

	private static String printBigDecimalHelper(BigDecimal o) {
		if (FDCHelper.ZERO.compareTo(FDCHelper.toBigDecimal(o)) == 0) {
			return "";
		} else {
			return String.valueOf(((BigDecimal) o).setScale(2, BigDecimal.ROUND_HALF_UP));
		}
	}

	public static String printBooleanHelper(Boolean o) {
		return Boolean.TRUE.equals(o) ? "��" : "��";
	}

	public static String printBooleanHelper(boolean o) {
		return o ? "��" : "��";
	}

	public static String printEnumHelper(Enum o) {
		return (o == null) ? "" : o.getAlias();
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	private PayRequestBillInfo fdcBill = null;
	private CurProjectInfo curProjectInfo = null;
	private HashMap bindCellMap =null;
	private ContractBillInfo contractBill = null;
	
	public PayRequestBillRowsetProvider(PayRequestBillInfo editData, IMetaDataPK mainQuery, HashMap bindCellMap, CurProjectInfo curProjectInfo, ContractBillInfo contractBill) {
		super(editData.getId().toString(), mainQuery);
		fdcBill = editData;
		this.bindCellMap = bindCellMap;
		this.curProjectInfo = curProjectInfo;
		this.contractBill = contractBill;
		initAllCompletePrjAmt();//��ʼ��ʱһ���Ը�ֵ
	}

	//	/**
	//	 * ���˽�з����Ƿ���ȿ��ǣ�ԭ�ҿ��Դӱ���/���ʵ�����
	//	 * ����ֻΪ�������뵥�ṩ�״�����Դ��
	//	 * �ѵ���ֻ����ԭ�ң�û�б��ҵ������ǲ�����ȱ���ˡ�����
	//	 * ��ʱ������������Բ��������ɾ����
	//	 * ��ȡ��ͬ���ԭ��
	//	 */
	//	private BigDecimal getContractOriAmt(String contractId) {
	//		//��ͬ���ԭ��
	//		BigDecimal contractOriAmt=FDCHelper.ZERO;
	//		SelectorItemCollection selector=new SelectorItemCollection();
	//		selector.add("originalAmount");
	//		
	//		try {
	//			ContractBillInfo contractBill = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
	//			contractOriAmt=contractBill.getOriginalAmount();
	//		} catch (EASBizException e) {
	//			e.printStackTrace();
	//		} catch (BOSException e) {
	//			e.printStackTrace();
	//		} catch (UuidException e) {
	//			e.printStackTrace();
	//		}
	//		return contractOriAmt;
	//	}
	//
		/**
	 * ���˽�з����Ƿ���ȿ��ǣ�ԭ�ҿ��Դӱ���/���ʵ�����
	 * ����ֻΪ�������뵥�ṩ�״�����Դ��
	 * �ѵ���ֻ����ԭ�ң�û�б��ҵ������ǲ�����ȱ���ˡ�����
	 * ��ʱ������������Բ��������ɾ����
	 * ��ȡ���ǩ֤ȷ��ԭ��
	 */
	/*	private BigDecimal getChangeOriAmt(String contractId) {

			BigDecimal conChangeOriAmt = FDCHelper.ZERO;
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("hasSettled");
			view.getSelector().add("oriBalanceAmount");
			view.getSelector().add("originalAmount");
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractId));
			view.setFilter(filter);
			ContractChangeBillCollection changeBillColl;
			try {
				changeBillColl = ContractChangeBillFactory.getRemoteInstance().getContractChangeBillCollection(view);
				ContractChangeBillInfo billInfo;
				//�������ۼ�=δ������+�ѽ�����
				for (Iterator iter = changeBillColl.iterator(); iter.hasNext();) {
					billInfo = (ContractChangeBillInfo) iter.next();
					if (billInfo.isHasSettled()) {
						conChangeOriAmt = conChangeOriAmt.add(FDCHelper.toBigDecimal(billInfo.getOriBalanceAmount()));
					} else {
						conChangeOriAmt = conChangeOriAmt.add(FDCHelper.toBigDecimal(billInfo.getOriginalAmount()));
					}
				}
			} catch (BOSException e) {
				e.printStackTrace();
			}
			return conChangeOriAmt;
		}*/
	//	/**
	//	 * ���˽�з����Ƿ���ȿ��ǣ�ԭ�ҿ��Դӱ���/���ʵ�����
	//	 * ����ֻΪ�������뵥�ṩ�״�����Դ��
	//	 * �ѵ���ֻ����ԭ�ң�û�б��ҵ������ǲ�����ȱ���ˡ�����
	//	 * ��ʱ������������Բ��������ɾ����
	//	 * ��ȡ����_���ڷ���_ԭ��
	//	 */
	//	private BigDecimal getGuerdonOriAmt(String contractId){
	//		BigDecimal guerdonBillOriAmt=FDCHelper.ZERO;
	//		EntityViewInfo view=new EntityViewInfo();
	//		view.getSelector().add("originalAmount");
	//		FilterInfo filter=new FilterInfo();
	//		filter.getFilterItems().add(new FilterItemInfo("payRequestBill.id",fdcBill.getId().toString()));
	//		/**
	//		 * Ӧ��ȡ��������ԭ�ҽ���ĳһ��ͬ���ۼ������ԭ�ҽ�� by Cassiel_peng 2009-10-21
	//		 */
	//		/*filter.getFilterItems().add(new FilterItemInfo("contract.id",contractId));
	//		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED));
	//		filter.getFilterItems().add(new FilterItemInfo("isGuerdoned",Boolean.TRUE));*/
	//		view.setFilter(filter);
	//		try {
	////			GuerdonBillCollection guerdonBillColl = GuerdonBillFactory.getRemoteInstance().getGuerdonBillCollection(view);
	//			GuerdonOfPayReqBillCollection guerdonBillColl = GuerdonOfPayReqBillFactory.getRemoteInstance().getGuerdonOfPayReqBillCollection(view);
	//			for (Iterator iter = guerdonBillColl.iterator(); iter.hasNext();)
	//			{
	//				GuerdonOfPayReqBillInfo guerdonBill = (GuerdonOfPayReqBillInfo) iter.next();
	//				guerdonBillOriAmt = guerdonBillOriAmt.add(FDCHelper.toBigDecimal(guerdonBill.getOriginalAmount()));
	//			}
	//		} catch (BOSException e) {
	//			e.printStackTrace();
	//		}
	//		return guerdonBillOriAmt;
	//	}
	//	/**
	//	 * ���˽�з����Ƿ���ȿ��ǣ�ԭ�ҿ��Դӱ���/���ʵ�����
	//	 * ����ֻΪ�������뵥�ṩ�״�����Դ��
	//	 * �ѵ���ֻ����ԭ�ң�û�б��ҵ������ǲ�����ȱ���ˡ�����
	//	 * ��ʱ������������Բ��������ɾ����
	//	 * ��ȡΥԼ_���ڷ���_ԭ��
	//	 */
	//	private BigDecimal getCompensationOriAmt(String contractId){
	//		BigDecimal compensationBillOriAmt=FDCHelper.ZERO;
	//		EntityViewInfo view=new EntityViewInfo();
	//		view.getSelector().add("originalAmount");
	//		FilterInfo filter=new FilterInfo();
	//		filter.getFilterItems().add(new FilterItemInfo("payRequestBill.id",fdcBill.getId().toString()));
	//		/**
	//		 * Ӧ��ȡ��������ԭ�ҽ���ĳһ��ͬ���ۼ������ԭ�ҽ�� by Cassiel_peng 2009-10-21
	//		 */
	//		/*filter.getFilterItems().add(new FilterItemInfo("contract.id",contractId));
	//		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED));
	//		filter.getFilterItems().add(new FilterItemInfo("isCompensated",Boolean.TRUE));*/
	//		view.setFilter(filter);
	//		try {
	//			CompensationOfPayReqBillCollection compenstionColl = CompensationOfPayReqBillFactory.getRemoteInstance().getCompensationOfPayReqBillCollection(view);
	////			CompensationBillCollection compenstionColl = CompensationBillFactory.getRemoteInstance().getCompensationBillCollection(view);
	//			for (Iterator iter = compenstionColl.iterator(); iter.hasNext();)
	//			{
	//				CompensationOfPayReqBillInfo billInfo = (CompensationOfPayReqBillInfo) iter.next();
	//				compensationBillOriAmt = compensationBillOriAmt.add(FDCHelper.toBigDecimal(billInfo.getOriginalAmount()));
	//			}
	//		} catch (BOSException e) {
	//			e.printStackTrace();
	//		}
	//		return compensationBillOriAmt;
	//	}
	//	/**
	//	 * ���˽�з����Ƿ���ȿ��ǣ�ԭ�ҿ��Դӱ���/���ʵ�����
	//	 * ����ֻΪ�������뵥�ṩ�״�����Դ��
	//	 * �ѵ���ֻ����ԭ�ң�û�б��ҵ������ǲ�����ȱ���ˡ�����
	//	 * ��ʱ������������Բ��������ɾ����
	//	 * ��ȡ�ۿ�_���ڷ���_ԭ��
	//	 */
	//	private BigDecimal getDeductOriAmt(String contractId) {
	//		BigDecimal DeductOfPayReqBillOriAmt = FDCHelper.ZERO;
	//		EntityViewInfo view=new EntityViewInfo();
	//		view.getSelector().add("originalAmount");
	//		FilterInfo filter=new FilterInfo();
	//		filter.getFilterItems().add(new FilterItemInfo("payRequestBill.id",fdcBill.getId().toString()));
	//		view.setFilter(filter);
	//		try {
	//			DeductOfPayReqBillCollection duductOfPayReqBillColl = DeductOfPayReqBillFactory.getRemoteInstance().getDeductOfPayReqBillCollection(view);
	//			for (Iterator iter = duductOfPayReqBillColl.iterator(); iter.hasNext();)
	//			{
	//				DeductOfPayReqBillInfo billInfo = (DeductOfPayReqBillInfo) iter.next();
	//				DeductOfPayReqBillOriAmt = DeductOfPayReqBillOriAmt.add(FDCHelper.toBigDecimal(billInfo.getOriginalAmount()));
	//			}
	//		} catch (BOSException e) {
	//			e.printStackTrace();
	//		}
	//		return DeductOfPayReqBillOriAmt;
	//	}

	/**
	 * ���� ���ʱ���/����=ԭ�ҡ�
	 * added by adny_liu 2012-4-10
	 */
	public String getOriValue(BigDecimal bg) {
		return printStringHelper(FDCHelper.divide(bg, getConExRate(fdcBill.getContractId()), 4, BigDecimal.ROUND_HALF_UP));
	}

	/**
	 * Ϊ�������뵥�״��������Դ����Ϊ���̸������������excel�����״�query�����㴦��
	 * added by adny_liu 2012-4-10
	 */
	private void insert(DynamicRowSet drs,DeductOfPayReqBillInfo info,Map deductTypeSum) throws Exception{
	
		BigDecimal sumLstDeductPaid = (BigDecimal)deductTypeSum.get("sumLstDeductPaid");
		BigDecimal sumLstDeductReq =  (BigDecimal)deductTypeSum.get("sumLstDeductReq");
		BigDecimal sumDeduct =  (BigDecimal)deductTypeSum.get("sumDeduct");
		BigDecimal sumAllDeductReq =  (BigDecimal)deductTypeSum.get("sumAllDeductReq");
		BigDecimal sumAllDeductPaid = (BigDecimal)deductTypeSum.get("sumAllDeductPaid");
		
		// �ڴ˰����ݴ��ݸ�ʵ���࣬drs.updateString(key,value) key
		// ָ�����״�ģ���ж�����ֶα��룬Valueָ���ǵ�ǰ���ݵ�����ֵ
		String orgName=((CurProjectInfo)fdcBill.getCurProject()).getFullOrgUnit().getName();
		String curProjectName = curProjectInfo.getDisplayName();		
		//ȡ��������Ҫ��ֻȡ��Ŀ����
		String projNameWithoutOrg = curProjectName.replace('_', '\\');
		curProjectName = orgName+"\\"+curProjectName.replace('_', '\\');
		
		//��Ʊ��
		drs.updateString("invoiceNumber", printStringHelper(fdcBill.getInvoiceNumber()));
		//��Ʊ��
		drs.updateString("invoiceDate", printStringHelper(fdcBill.getInvoiceDate()));
		//��Ʊ����  
		drs.updateString("invoiceAmt", printStringHelper(fdcBill.getInvoiceAmt()));
		//��Ʊ���ԭ��
		drs.updateString("invoiceOriAmt", getOriValue(fdcBill.getInvoiceAmt()));
		//�ۼƷ�Ʊ����
		drs.updateString("allInvoiceAmt", printStringHelper(fdcBill.getAllInvoiceAmt()));
		//�ۼƷ�Ʊԭ��
		drs.updateString("allInvoiceOriAmt", getOriValue(fdcBill.getAllInvoiceAmt()));
		drs.updateString("orgName",orgName);
		
		drs.updateString("curProject.name", curProjectName);
		drs.updateString("projNameWithoutOrg", projNameWithoutOrg);
		drs.updateString("curProject.number", fdcBill.getCurProject().getNumber());
		drs.updateString("useDepartment.name", fdcBill.getUseDepartment().getName());
		drs.updateString("useDepartment.number", fdcBill.getUseDepartment().getNumber());
		drs.updateString("contractNo", printStringHelper(fdcBill.getContractNo()));
		drs.updateString("bookedDate", printStringHelper(fdcBill.getBookedDate()));// ҵ������
		drs.updateString("number", printStringHelper(fdcBill.getNumber()));
		drs.updateString("payDate", printStringHelper(fdcBill.getPayDate()));
		drs.updateString("period.periodNumber", printStringHelper(fdcBill.getPeriod()));
		drs.updateString("period.number", fdcBill.getPeriod() == null ? "" : String.valueOf(fdcBill.getPeriod().getNumber()));
		drs.updateString("paymentType.name", fdcBill.getPaymentType() == null ? "" : fdcBill.getPaymentType().getName());
		drs.updateString("paymentType.number", fdcBill.getPaymentType() == null ? "" : fdcBill.getPaymentType().getNumber());
		drs.updateString("isDifferPlace", printStringHelper(fdcBill.getIsDifferPlace()));
		drs.updateString("settlementType.name", fdcBill.getSettlementType() == null ? "" : fdcBill.getSettlementType().getName());
		drs.updateString("settlementType.number", fdcBill.getSettlementType() == null ? "" : fdcBill.getSettlementType().getNumber());
		drs.updateString("recBank", printStringHelper(fdcBill.getRecBank()));
		drs.updateString("description", printStringHelper(fdcBill.getDescription()));
		drs.updateString("supplier.name", fdcBill.getSupplier() == null ? "" : fdcBill.getSupplier().getName());
		drs.updateString("realSupplier.name", fdcBill.getRealSupplier() == null ? "" : fdcBill.getRealSupplier().getName());
		drs.updateString("recAccount", fdcBill.getRecAccount());
		drs.updateString("usage", fdcBill.getUsage());
		drs.updateString("currency.name", fdcBill.getCurrency() == null ? "" : fdcBill.getCurrency().getName());
		drs.updateString("currency.number", fdcBill.getCurrency() == null ? "" : fdcBill.getCurrency().getNumber());
		drs.updateString("exchangeRate", String.valueOf(fdcBill.getExchangeRate()));
		drs.updateString("attachment", String.valueOf(fdcBill.getAttachment()));
		drs.updateString("originalAmount", printStringHelper(fdcBill.getOriginalAmount()));
		drs.updateString("paymentProportion", printStringHelper(fdcBill.getPaymentProportion()) + "%");
		drs.updateString("amount", printStringHelper(fdcBill.getAmount()));
		drs.updateString("grtAmount", printStringHelper(fdcBill.getGrtAmount()));
		//��ͬ���޽��
		if(contractBill!=null){
			drs.updateString("conGrtAmount", printStringHelper(contractBill.getGrtAmount()));
			drs.updateString("conGrtOriAmount", getOriValue(contractBill.getGrtAmount()));
			drs.updateString("conGrtRate", printStringHelper(contractBill.getGrtRate()));
		}
		drs.updateString("completePrjAmt", fdcBill.getBoolean("isCompletePrjAmtVisible") ? printStringHelper(fdcBill.getCompletePrjAmt())
				: "");
		drs.updateString("capitalAmount", printStringHelper(fdcBill.getCapitalAmount()));
		drs.updateString("moneyDesc", printStringHelper(fdcBill.getMoneyDesc()));
		drs.updateString("urgentDegree", printStringHelper(fdcBill.getUrgentDegree()));
		drs.updateString("isPay", printBooleanHelper(fdcBill.isIsPay()));
		drs.updateString("createTime", printStringHelper(fdcBill.getCreateTime()));
		drs.updateString("creator.name", fdcBill.getCreator().getName());
		drs.updateString("creator.number", fdcBill.getCreator().getNumber());
		drs.updateString("auditTime", printStringHelper(fdcBill.getAuditTime()));
		drs.updateString("auditor.number", fdcBill.getAuditor() == null ? "" : fdcBill.getAuditor().getNumber());
		drs.updateString("auditor.name", fdcBill.getAuditor() == null ? "" : fdcBill.getAuditor().getName());
		drs.updateString("contractName", printStringHelper(fdcBill.getContractName()));		
		//��ͬ��۱���
		drs.updateString("contractPrice", printStringHelper(fdcBill.getContractPrice()));
		//��ͬ���ԭ��
		drs.updateString("contractOriPrice", getOriValue(fdcBill.getContractPrice()));
		//drs.updateString("contractOriPrice", printStringHelper(getContractOriAmt(contractId)));
		//������۱���
		drs.updateString("latestPrice", printStringHelper(fdcBill.getLatestPrice()));
		//�������ԭ��
		drs.updateString("latestOriPrice", getOriValue(fdcBill.getLatestPrice()));
		
		//���ָ�����
		drs.updateString("changeAmt", printStringHelper(((ICell) bindCellMap.get(PaymentBillContants.CHANGEAMT)).getValue()));
		//���ָ����ԭ�� 
		drs.updateString("changeOriAmt", getOriValue((BigDecimal) ((ICell) bindCellMap.get(PaymentBillContants.CHANGEAMT)).getValue()));
		//	drs.updateString("changeOriAmt", printStringHelper(getChangeOriAmt(fdcBill.getContractId())));
		//�����뵥�Ѹ�����
		drs.updateString("payedAmt", printStringHelper(fdcBill.getPayedAmt()));
		//�����뵥�Ѹ�ԭ��
		drs.updateString("payedOriAmt", getOriValue(fdcBill.getPayedAmt()));
		//��ͬ�ڹ��̿�_���������ۼ�ʵ��_����
		drs.updateString("lstPrjAllPaidAmt", printStringHelper(fdcBill.getLstPrjAllPaidAmt()));
		//��ͬ�ڹ��̿�_���������ۼ�ʵ��_ԭ��
		drs.updateString("lstPrjAllPaidOriAmt", getOriValue(fdcBill.getLstPrjAllPaidAmt()));
		//��ͬ�ڹ��̿�_���������ۼ�����_����
		drs.updateString("lstPrjAllReqAmt", printStringHelper(fdcBill.getLstPrjAllReqAmt()));
		//��ͬ�ڹ��̿�_���������ۼ�����_ԭ��
		drs.updateString("lstPrjAllReqOriAmt", getOriValue(fdcBill.getLstPrjAllReqAmt()));
		//��ͬ�ڹ��̿�_���ڷ���_����
		drs.updateString("projectPriceInContract", printStringHelper(fdcBill.getProjectPriceInContract()));
		//��ͬ�ڹ��̿�_���ڷ���_ԭ�� old
		//drs.updateString("projectPriceInContractOri", printStringHelper(fdcBill.getProjectPriceInContractOri()));
		//��ͬ�ڹ��̿�_���ڷ���_ԭ��
		drs.updateString("projectPriceInContractOri", getOriValue(fdcBill.getProjectPriceInContract()));
		//��ͬ�ڹ��̿�_���������ۼ�����_����
		drs.updateString("prjAllReqAmt", printStringHelper(fdcBill.getPrjAllReqAmt()));
		//��ͬ�ڹ��̿�_���������ۼ�����_ԭ��
		drs.updateString("prjAllReqOriAmt", getOriValue(fdcBill.getPrjAllReqAmt()));
		
		//��ͬ�ڹ��̿�_���������ۼ�ʵ��_����   = ���������ۼ��ۼ�ʵ�� PS:�������뵥�������Ƶ��߼���
		/* modified by zhaoqin for R140401-0072 on 2014/04/25 start */
		//BigDecimal prjAllPaidAmt = (fdcBill.getLstPrjAllPaidAmt() != null ? fdcBill.getLstPrjAllPaidAmt() : FDCHelper.ZERO);
		BigDecimal prjAllPaidAmt = (fdcBill.getBigDecimal("prjAllPaidAmt") != null ? fdcBill.getBigDecimal("prjAllPaidAmt") : FDCHelper.ZERO);
		/* modified by zhaoqin for R140401-0072 on 2014/04/25 end */
		
		/* modified by zhaoqin for R131202-0278 start */
		drs.updateString("prjAllPaidAmt", printStringHelper(fdcBill.getBigDecimal("prjAllPaidAmt")));
		//drs.updateString("prjAllPaidAmt", printStringHelper(prjAllPaidAmt));
		//��ͬ�ڹ��̿�_���������ۼ�ʵ��_ԭ��
		//drs.updateString("prjAllPaidOriAmt", getOriValue(prjAllPaidAmt));
		drs.updateString("prjAllPaidOriAmt", getOriValue(fdcBill.getBigDecimal("prjAllPaidOriAmt")));
		/* modified by zhaoqin for R131202-0278 end */
		
		drs.updateString("scheduleAmt", printStringHelper(fdcBill.getScheduleAmt()));
		drs.updateString("curPlannedPayment", printStringHelper(fdcBill.getCurPlannedPayment()));
		drs.updateString("curBackPay", printStringHelper(fdcBill.getCurBackPay()));
		drs.updateString("paymentPlan", printStringHelper(fdcBill.getPaymentPlan()));
		//�������
		drs.updateString("payTimes", String.valueOf(fdcBill.getPayTimes()));		
		drs.updateString("contractId", fdcBill.getContractId());
		drs.updateString("hasPayoff", printBooleanHelper(fdcBill.isHasPayoff()));		
		
		//�׹��ۿ�_���������ۼ�ʵ��_����
		drs.updateString("lstAMatlAllPaidAmt", printStringHelper(fdcBill.getLstAMatlAllPaidAmt()));
		//�׹��ۿ�_���������ۼ�ʵ��_ԭ��
		drs.updateString("lstAMatlAllPaidOriAmt", getOriValue(fdcBill.getLstAMatlAllPaidAmt()));
		//�׹��ۿ�_���������ۼ�����_����
		drs.updateString("lstAMatlAllReqAmt", printStringHelper(fdcBill.getLstAMatlAllReqAmt()));
		//�׹��ۿ�_���������ۼ�����_ԭ��
		drs.updateString("lstAMatlAllReqOriAmt", getOriValue(fdcBill.getLstAMatlAllReqAmt()));
		//�׹��ۿ�_���ڷ���_����
		drs.updateString("payPartAMatlAmt", printStringHelper(fdcBill.getPayPartAMatlAmt()));
		//�׹��ۿ�_���ڷ���_ԭ��
		drs.updateString("payPartAMatlOriAmt", getOriValue(fdcBill.getPayPartAMatlAmt()));
		//�׹��ۿ�_���������ۼ�����_����
		drs.updateString("payPartAMatlAllReqAmt", printStringHelper(fdcBill.getPayPartAMatlAllReqAmt()));
		//�׹��ۿ�_���������ۼ�����_ԭ��
		drs.updateString("payPartAMatlAllReqOriAmt", getOriValue(fdcBill.getPayPartAMatlAllReqAmt()));
		//�׹��ۿ�_���������ۼ�ʵ��_���� = �׹��ۿ�_���������ۼ�ʵ��_����
		BigDecimal allPartAMatlAllPaidAmt = (fdcBill.getLstAMatlAllPaidAmt() != null ? fdcBill.getLstAMatlAllPaidAmt() : FDCHelper.ZERO);
		drs.updateString("allPartAMatlAllPaidAmt", printStringHelper(allPartAMatlAllPaidAmt));
		//�׹��ۿ�_���������ۼ�ʵ��_ԭ��
		drs.updateString("allPartAMatlAllPaidOriAmt", getOriValue(allPartAMatlAllPaidAmt));
		drs.updateString("costAmount", fdcBill.getBoolean("isCompletePrjAmtVisible") ? printStringHelper(fdcBill.getCompletePrjAmt()) : "");
		drs.updateString("settleAmt", printStringHelper(fdcBill.getSettleAmt()));

		//����_���������ۼ�ʵ��_����
		BigDecimal lstGuerdonPaidAmt = (BigDecimal) ( (ICell)( bindCellMap.get("lstGuerdonPaidAmt"))).getValue();				
		drs.updateString("lstGuerdonPaidAmt",printStringHelper(lstGuerdonPaidAmt));
		//����_���������ۼ�ʵ��_ԭ��
		drs.updateString("lstGuerdonPaidOriAmt", getOriValue(lstGuerdonPaidAmt));
		//����_���������ۼ�����_����
		BigDecimal lstGuerdonReqAmt = (BigDecimal) ( (ICell)( bindCellMap.get("lstGuerdonReqAmt"))).getValue();
		drs.updateString("lstGuerdonReqAmt", printStringHelper(lstGuerdonReqAmt));
		//����_���������ۼ�����_ԭ��
		drs.updateString("lstGuerdonReqOriAmt", getOriValue(lstGuerdonReqAmt));
		//����_���ڷ���_����			
		BigDecimal guerdonAmt = (BigDecimal)((ICell)( bindCellMap.get("guerdonAmt"))).getValue();
		guerdonAmt = guerdonAmt!= null?guerdonAmt: FDCHelper.ZERO;
		drs.updateString("guerdonAmt", printStringHelper(guerdonAmt));
		//����_���ڷ���_ԭ��
		drs.updateString("guerdonOriAmt", getOriValue(guerdonAmt));
		//drs.updateString("guerdonOriAmt", printStringHelper(getGuerdonOriAmt(contractId)));
		//����_���������ۼ�����_���� = ���� ���������ۼ����� + ����_���ڷ���_���� PS:�������뵥�������Ƶ��߼���
		BigDecimal allGuerdonReqAmt = guerdonAmt.add(lstGuerdonReqAmt!= null ? lstGuerdonReqAmt : FDCHelper.ZERO);
		drs.updateString("allGuerdonReqAmt", printStringHelper(allGuerdonReqAmt));
		//����_���������ۼ�����_ԭ��
		drs.updateString("allGuerdonReqOriAmt", getOriValue(allGuerdonReqAmt));
		//����_���������ۼ�ʵ��_���� = ���� ���������ۼ�ʵ�� 
		BigDecimal allGuerdonPaidAmt = lstGuerdonPaidAmt!= null ?lstGuerdonPaidAmt: FDCHelper.ZERO;
		drs.updateString("allGuerdonPaidAmt",printStringHelper(allGuerdonPaidAmt));
		//����_���������ۼ�ʵ��_ԭ��
		drs.updateString("allGuerdonPaidOriAmt", getOriValue(allGuerdonPaidAmt));
		//ΥԼ_���������ۼ�ʵ��_����
		BigDecimal lstCompensationPaidAmt = (BigDecimal) ( (ICell)( bindCellMap.get("lstCompensationPaidAmt"))).getValue();
		drs.updateString("lstCompensationPaidAmt",printStringHelper(lstCompensationPaidAmt));
		//ΥԼ_���������ۼ�ʵ��_ԭ��
		drs.updateString("lstCompensationPaidOriAmt", getOriValue(lstCompensationPaidAmt));
		//ΥԼ_���������ۼ�����_����
		BigDecimal lstCompensationReqAmt = (BigDecimal) ( (ICell)( bindCellMap.get("lstCompensationReqAmt"))).getValue();
		drs.updateString("lstCompensationReqAmt", printStringHelper(lstCompensationReqAmt));
		//ΥԼ_���������ۼ�����_ԭ��
		drs.updateString("lstCompensationReqOriAmt", getOriValue(lstCompensationReqAmt));		
		//ΥԼ_���ڷ���_����
		BigDecimal compensationAmt = (BigDecimal) ( (ICell)( bindCellMap.get("compensationAmt"))).getValue();
		compensationAmt = compensationAmt != null? compensationAmt: FDCHelper.ZERO;
		drs.updateString("compensationAmt", printStringHelper(compensationAmt));
		//ΥԼ_���ڷ���_ԭ��
		drs.updateString("compensationOriAmt", getOriValue(compensationAmt));
		//drs.updateString("compensationOriAmt", printStringHelper(getCompensationOriAmt(contractId)));		
		//ΥԼ_���������ۼ�����_���� = ΥԼ_���������ۼ�����_���� + ΥԼ_���ڷ���_����						
		BigDecimal allCompensationReqAmt = compensationAmt.add(lstCompensationReqAmt != null ? lstCompensationReqAmt : FDCHelper.ZERO);
		drs.updateString("allCompensationReqAmt", printStringHelper(allCompensationReqAmt));
		//ΥԼ_���������ۼ�����_ԭ��
		drs.updateString("allCompensationReqOriAmt", getOriValue(allCompensationReqAmt));
		//ΥԼ_���������ۼ�ʵ��_���� = ΥԼ_���������ۼ�ʵ��_����
		BigDecimal allCompensationPaidAmt = lstCompensationPaidAmt != null ? lstCompensationPaidAmt : FDCHelper.ZERO;		
		drs.updateString("allCompensationPaidAmt",printStringHelper(allCompensationPaidAmt));
		//ΥԼ_���������ۼ�ʵ��_ԭ��
		drs.updateString("allCompensationPaidOriAmt", getOriValue(allCompensationPaidAmt));
		
		//��ʵ�ֲ�ֵ���ۼƽ���		
		drs.updateString("totalSettlePrice",printStringHelper(fdcBill.getTotalSettlePrice()));
		
		//�ۿ���
		//�ۿ�����				
		if(info!=null){
			drs.updateString("deductType",info==null?"":info.getDeductType().getName());
			BigDecimal lstDeductPaidAmt = info.getAllPaidAmt()==null?FDCHelper.ZERO:info.getAllPaidAmt();
			BigDecimal lstDeductReqAmt  = info.getAllReqAmt()==null?FDCHelper.ZERO:info.getAllReqAmt();
			BigDecimal deductAmt        = info.getAmount()==null?FDCHelper.ZERO:info.getAmount();
			BigDecimal allDeductReqAmt  = lstDeductReqAmt.add(deductAmt);
			BigDecimal allDeductPaidAmt  = lstDeductPaidAmt;
			
			//�ۿ�_���������ۼ�ʵ��_����
			drs.updateString("lstDeductPaidAmt",printStringHelper(lstDeductPaidAmt));
			//�ۿ�_���������ۼ�ʵ��_ԭ��
			drs.updateString("lstDeductPaidOriAmt", getOriValue(lstDeductPaidAmt));
			//�ۿ�_���������ۼ�����_����
			drs.updateString("lstDeductReqAmt",printStringHelper(lstDeductReqAmt));
			//�ۿ�_���������ۼ�����_ԭ��
			drs.updateString("lstDeductReqOriAmt", getOriValue(lstDeductReqAmt));
			//�ۿ�_���ڷ���_����
			drs.updateString("deductAmt",printStringHelper(deductAmt));
			//�ۿ�_���ڷ���_ԭ��
			drs.updateString("deductOriAmt", getOriValue(deductAmt));
			// �ۿ�ڷ���
			//drs.updateString("deductOriAmt", printStringHelper(FDCHelper.toBigDecimal(getDeductOriAmt(contractId))));
			//�ۿ�_���������ۼ�����_����
			drs.updateString("allDeductReqAmt",printStringHelper(allDeductReqAmt));
			//�ۿ�_���������ۼ�����_ԭ��
			drs.updateString("allDeductReqOriAmt", getOriValue(allDeductReqAmt));
			//�ۿ�_���������ۼ�ʵ��_����
			drs.updateString("allDeductPaidAmt", printStringHelper(allDeductPaidAmt));
			//�ۿ�_���������ۼ�ʵ��_ԭ��
			drs.updateString("allDeductPaidOriAmt", getOriValue(allDeductPaidAmt));
			//�ۿ�С��
			sumLstDeductPaid = sumLstDeductPaid.add(lstDeductPaidAmt);
			sumLstDeductReq = sumLstDeductReq.add(lstDeductReqAmt);
			sumDeduct = sumDeduct.add(deductAmt);
			sumAllDeductReq = sumAllDeductReq.add(allDeductReqAmt);
			sumAllDeductPaid = sumAllDeductPaid.add(allDeductPaidAmt);
			
			deductTypeSum.put("sumLstDeductPaid",sumLstDeductPaid);
			deductTypeSum.put("sumLstDeductReq",sumLstDeductReq);
			deductTypeSum.put("sumDeduct",sumDeduct);
			deductTypeSum.put("sumAllDeductReq",sumAllDeductReq);
			deductTypeSum.put("sumAllDeductPaid",sumAllDeductPaid);
		}
		//�ۿ�С��_���������ۼ�ʵ��_����
		drs.updateString("lstDeductSumPaidAmt", printStringHelper(sumLstDeductPaid));
		//�ۿ�С��_���������ۼ�ʵ��_ԭ��
		drs.updateString("lstDeductSumPaidOriAmt", getOriValue(sumLstDeductPaid));
		//�ۿ�С��_���������ۼ�����_����
		drs.updateString("lstDeductSumReqAmt", printStringHelper(sumLstDeductReq));
		//�ۿ�С��_���������ۼ�����_ԭ��
		drs.updateString("lstDeductSumReqOriAmt", getOriValue(sumLstDeductReq));
		//�ۿ�С��_���ڷ���_����
		drs.updateString("deductSumAmt", printStringHelper(sumDeduct));
		//�ۿ�С��_���ڷ���_ԭ��
		drs.updateString("deductSumOriAmt", getOriValue(sumDeduct));
		//�ۿ�С��_���������ۼ�����_����
		drs.updateString("allDeductSumReqAmt", printStringHelper(sumAllDeductReq));
		//�ۿ�С��_���������ۼ�����_ԭ��
		drs.updateString("allDeductSumReqOriAmt", getOriValue(sumAllDeductReq));
		//�ۿ�С��_���������ۼ�ʵ��_����
		drs.updateString("allDeductSumPaidAmt", printStringHelper(sumAllDeductPaid));
		//�ۿ�С��_���������ۼ�ʵ��_ԭ��
		drs.updateString("allDeductSumPaidOriAmt", getOriValue(sumAllDeductPaid));
		
		//ʵ����_���������ۼ�ʵ��_���� = ��ͬ���̿� + ���� - ΥԼ�� - �ۿ�С�� - �׹���
		BigDecimal lstRealPaidAmt = fdcBill.getLstPrjAllPaidAmt()!=null?fdcBill.getLstPrjAllPaidAmt():FDCHelper.ZERO;
		BigDecimal tempLstRealPaidAmt = lstRealPaidAmt.add(FDCHelper.toBigDecimal(lstGuerdonPaidAmt))//����
		.subtract(FDCHelper.toBigDecimal(lstCompensationPaidAmt))//ΥԼ
		.subtract(FDCHelper.toBigDecimal(sumLstDeductPaid))//�ۿ�
		.subtract(fdcBill.getLstAMatlAllPaidAmt() != null ? fdcBill
						.getLstAMatlAllPaidAmt() : FDCHelper.ZERO);//�׹���
		drs.updateString("lstRealPaidAmt", printStringHelper(tempLstRealPaidAmt));
		//ʵ����_���������ۼ�ʵ��_ԭ��
		drs.updateString("lstRealPaidOriAmt", getOriValue(tempLstRealPaidAmt));
		
		//ʵ����_���������ۼ�����_����  = ��ͬ���̿� + ���� - ΥԼ�� - �ۿ�С�� - �׹���
		BigDecimal lstRealReqAmt = fdcBill.getLstPrjAllReqAmt()!=null?fdcBill.getLstPrjAllReqAmt():FDCHelper.ZERO;
		BigDecimal tempLstRealReqAmt = lstRealReqAmt.add(FDCHelper.toBigDecimal(lstGuerdonReqAmt))//����
		.subtract(FDCHelper.toBigDecimal(lstCompensationReqAmt))//ΥԼ
		.subtract(FDCHelper.toBigDecimal(sumLstDeductReq))//�ۿ�
		.subtract(fdcBill.getLstAMatlAllReqAmt() != null ? fdcBill
						.getLstAMatlAllReqAmt() : FDCHelper.ZERO);//�׹���
		drs.updateString("lstRealReqAmt", printStringHelper(tempLstRealReqAmt));
		//ʵ����_���������ۼ�����_ԭ��
		drs.updateString("lstRealReqOriAmt", getOriValue(tempLstRealReqAmt));
		
		//ʵ����_���ڷ���_���� = ��ͬ���̿� + ���� - ΥԼ�� - �ۿ�С�� - �׹���
		BigDecimal projectPriceInContract = fdcBill.getProjectPriceInContract()!=null?fdcBill.getProjectPriceInContract():FDCHelper.ZERO;
		//BigDecimal tempCurPaid = fdcBill.getCurPaid()!= null ? fdcBill.getCurPaid(): FDCHelper.ZERO;
		BigDecimal tempCurPaid = projectPriceInContract.add(guerdonAmt)//����
		.subtract(compensationAmt)//ΥԼ
		.subtract(sumDeduct)//�ۿ�
		.subtract(fdcBill.getPayPartAMatlAmt() != null ? fdcBill
						.getPayPartAMatlAmt() : FDCHelper.ZERO);//�׹���
		drs.updateString("curPaid", printStringHelper(tempCurPaid));
		//ʵ����_���ڷ���_ԭ��
		drs.updateString("curPaidOri", getOriValue(tempCurPaid));
		
		//ʵ����_���������ۼ�����_���� = ��ͬ���̿� + ���� - ΥԼ�� - �ۿ�С�� - �׹���
		BigDecimal prjAllReqAmt = fdcBill.getPrjAllReqAmt()!=null?fdcBill.getPrjAllReqAmt():FDCHelper.ZERO;
		BigDecimal allRealReqAmt = prjAllReqAmt.add(allGuerdonReqAmt)//����
				.subtract(	allCompensationReqAmt)//ΥԼ
				.subtract(sumAllDeductReq)//�ۿ�
				.subtract(fdcBill.getPayPartAMatlAllReqAmt() != null ? fdcBill
								.getPayPartAMatlAllReqAmt()
								: FDCHelper.ZERO);//�׹���
		drs.updateString("allRealReqAmt",printStringHelper(allRealReqAmt));
		//ʵ����_���������ۼ�����_ԭ��
		drs.updateString("allRealReqOriAmt", getOriValue(allRealReqAmt));
		
		//ʵ����_���������ۼ�ʵ��_���� = ��ͬ���̿� + ���� - ΥԼ�� - �ۿ�С�� - �׹���
		
		/* modified by zhaoqin for R140401-0072 on 2014/04/25 */
		//BigDecimal lstPrjAllPaidAmt =  FDCHelper.toBigDecimal(fdcBill.getLstPrjAllPaidAmt());
		BigDecimal allRealPaidAmt = prjAllPaidAmt
				.add(allGuerdonPaidAmt)//����
				.subtract(allCompensationPaidAmt)//ΥԼ
				.subtract(sumAllDeductPaid)//�ۿ�
				.subtract(allPartAMatlAllPaidAmt);//�׹���
		
		drs.updateString("allRealPaidAmt",printStringHelper(allRealPaidAmt));
		//ʵ����_���������ۼ�ʵ��_ԭ��
		drs.updateString("allRealPaidOriAmt", getOriValue(allRealPaidAmt));
		
		// ��� = ������� - ��ͬ�ڸ��� ���������ۼ�ʵ��
		BigDecimal restAmount = (fdcBill
		.getLatestPrice() == null ? FDCHelper.ZERO : fdcBill.getLatestPrice())
		.subtract(prjAllPaidAmt);//��ͬ�ڹ��̿�_���������ۼ�ʵ��_����
		
		drs.updateString("restAmount",printStringHelper(restAmount));
		// ��������% = ʵ���� ���ڷ��� / ������ۡ�����Ϊֱ�Ӵӽ�����ȡ�������Ա�֤�����һ�� Added by Owen_wen 2012-12-11
		drs.updateString("curReqPercent", printStringHelper(fdcBill.get(PayRequestBillContants.CURREQPERCENT)));
		
		// �ۼ�����% = ʵ���� ���������ۼ����� / ������ۡ�����Ϊֱ�Ӵӽ�����ȡ�������Ա�֤�����һ�� Added by Owen_wen 2012-12-11
		drs.updateString("allReqPercent", printStringHelper(fdcBill.get(PayRequestBillContants.ALLREQPERCENT)));

		// �ۼ�Ӧ������%
		drs.updateString("allPayableReqPercent", printStringHelper(FDCHelper.divide(FDCHelper
				.multiply(FDCHelper.ONE_HUNDRED, allRealReqAmt), fdcBill.getLatestPrice(), 2, BigDecimal.ROUND_HALF_UP)));
		
		drs.updateString("imageSchedule", printStringHelper(fdcBill.getImageSchedule()));
		drs.updateString("allCompletePrjAmt", printStringHelper(fdcBill.get("allCompletePrjAmt")));
		// ��ͬ�ڹ��̿��ֹ����ʵ��+��������
		BigDecimal LstPrjAllPaidAmtAndthis = FDCHelper.add(fdcBill.getLstPrjAllPaidAmt(),fdcBill.getProjectPriceInContract());
		drs.updateString("LstPrjAllPaidAmtAndthis",printStringHelper(LstPrjAllPaidAmtAndthis));
		// ��ͬ�������-��ͬ�ڹ��̿��ֹ����ʵ��-��������
		BigDecimal latestPriceSub = FDCHelper.subtract(fdcBill.getLatestPrice(),FDCHelper.add(fdcBill.getLstPrjAllPaidAmt(),fdcBill.getProjectPriceInContract()));
		drs.updateString("latestPriceSub",printStringHelper(latestPriceSub));

		// ���޽���
		drs.updateString("grtOriAmount", printStringHelper(FDCHelper.divide(FDCHelper.toBigDecimal(fdcBill.getGrtAmount()), fdcBill.getExchangeRate(),4,BigDecimal.ROUND_HALF_UP)));

		// ������
		drs.updateString("settleOriAmt", printStringHelper(FDCHelper.divide(FDCHelper.toBigDecimal(fdcBill.getSettleAmt()), fdcBill.getExchangeRate(),4,BigDecimal.ROUND_HALF_UP)));

		// �������뱾�ҽ��    ֱ��ȡ�� amount �ֶε�ֵ������֪���Բ����أ�����
		drs.updateString("thisTimeReqLocalAmt", printStringHelper(FDCHelper.toBigDecimal(fdcBill.getAmount())));
		drs.updateString("process", fdcBill.getProcess());

		// �ƻ�����״̬
		drs.updateString("depPlanState", printEnumHelper(fdcBill.getDepPlanState()));

		// //////////////////////////////////////////////////////////////////////////
		// �������뵥��������չ�ֶΣ����ڿͻ��Զ��� by skyiter_wang 2014-12-04
		// ˵����
		// 1���������뵥���״�Ƚ�����
		// 2���ڴ��������˴���д����һЩ��ѯ�ֶΣ�����DEP��չ���ֶ��޷�����չʾ
		// 3������Ԥ����5����չ�ֶΣ�����ͻ��Զ���
		// 4���μ�PayRequestBillEditUI.actionPrintPreview_actionPerformed��PayRequestBillRowsetProvider

		drs.updateString("extField01", fdcBill.getExtField01());// ��չ�ֶ�01
		drs.updateString("extField02", fdcBill.getExtField02());// ��չ�ֶ�02
		drs.updateString("extField03", fdcBill.getExtField03());// ��չ�ֶ�03
		drs.updateString("extField04", fdcBill.getExtField04());// ��չ�ֶ�04
		drs.updateString("extField05", fdcBill.getExtField05());// ��չ�ֶ�05

		drs.updateString("extBooleanField01", printBooleanHelper(fdcBill.isExtBooleanField01()));// ��չ�����ֶ�01
		drs.updateString("extBooleanField02", printBooleanHelper(fdcBill.isExtBooleanField02()));// ��չ�����ֶ�02

		drs.updateString("extEnumField01", printEnumHelper(fdcBill.getExtEnumField01()));// ��չö���ֶ�01
		drs.updateString("extEnumField02", printEnumHelper(fdcBill.getExtEnumField02()));// ��չö���ֶ�02

		// //////////////////////////////////////////////////////////////////////////
	}

	public IRowSet getOtherSubRowSet(R1PrintDataSource ds) throws Exception {
		/**
		 * �������뵥�״���������Դ�� �������������󡢹����ɱ���Ŀ���롢�����ɱ���Ŀ��������ֱ�ӹ�����
		 * by jian_wen 2009.12.18
		 */
		if (ds.getId().toUpperCase().startsWith("SPLITENTRYPRINTVIEWQUERY")) 
        {   
			IRowSet iRowSet = null;
	        try {
	        	IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(new MetaDataPK ("com.kingdee.eas.fdc.contract.app.SplitEntryPrintViewQuery"));
	            exec.option().isAutoTranslateEnum= true;
	            EntityViewInfo ev = new EntityViewInfo();
	            FilterInfo filter = new FilterInfo();
	            filter.getFilterItems().add(new FilterItemInfo("parent.contractBill.id", contractBill.getId().toString(), CompareType.EQUALS));
	            ev.setFilter(filter);            
	            exec.setObjectView(ev);
	            iRowSet = exec.executeQuery();	
	            iRowSet.beforeFirst();
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				SysUtil.abort();
			}
	        
			return iRowSet;
			
        } else if (ds.getId().toUpperCase().startsWith("CONTRACTBILLENTRYPRINT")) {
        	IRowSet iRowSet = null;
	        try {
	        	IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(new MetaDataPK ("com.kingdee.eas.fdc.contract.app.ContractBillEntryPrintQuery"));
	            exec.option().isAutoTranslateEnum= true;
	            EntityViewInfo ev = new EntityViewInfo();
	            FilterInfo filter = new FilterInfo();
	            filter.getFilterItems().add(new FilterItemInfo("parent.id", contractBill.getId().toString(), CompareType.EQUALS));
	            ev.setFilter(filter);            
	            exec.setObjectView(ev);
	            iRowSet = exec.executeQuery();	
	            iRowSet.beforeFirst();
			} catch (Exception e) {
				e.printStackTrace();
				SysUtil.abort();
			}
	        
			return iRowSet;
        } else if (ds.getId().toUpperCase().startsWith("CONTRACTPAYITEM")) {
        	IRowSet iRowSet = null;
	        try {
	        	IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(new MetaDataPK ("com.kingdee.eas.fdc.contract.app.ContractPayItemPrintQuery"));
	            exec.option().isAutoTranslateEnum= true;
	            EntityViewInfo ev = new EntityViewInfo();
	            FilterInfo filter = new FilterInfo();
	            filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractBill.getId().toString(), CompareType.EQUALS));
	            ev.setFilter(filter);            
	            exec.setObjectView(ev);
	            iRowSet = exec.executeQuery();	
	            iRowSet.beforeFirst();
			} catch (Exception e) {
				e.printStackTrace();
				SysUtil.abort();
			}
			return iRowSet;
        } else if (ds.getId().equalsIgnoreCase("ContractPayItemPrintQuery")) {
			// ��ͬ��������
			IRowSet iRowSet = null;
			try {
				IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(new MetaDataPK(
						"com.kingdee.eas.fdc.contract.app.ContractPayItemPrintQuery"));
				exec.option().isAutoTranslateEnum = true;
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("contractbill.id", contractBill.getId().toString()));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				e.printStackTrace();
				SysUtil.abort();
			}

			return iRowSet;
        } else if (ds.getId().equalsIgnoreCase("ContractBailPrintQuery")) {
			// ��ͬ��Լ��֤�𼰷�������
			IRowSet iRowSet = null;
			try {
				IQueryExecutor exec = QueryExecutorFactory
						.getRemoteInstance(new MetaDataPK("com.kingdee.eas.fdc.contract.app.ContractBailPrintQuery"));
				exec.option().isAutoTranslateEnum = true;
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id", contractBill.getBail().getId().toString()));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				e.printStackTrace();
				SysUtil.abort();
			}

			return iRowSet;
		} else if (ds.getId().equalsIgnoreCase("ContractBillQueryForPrint")) {
			// ��ͬ��ϸ��Ϣ
			IRowSet iRowSet = null;
			try {
				IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(new MetaDataPK(
						"com.kingdee.eas.fdc.contract.app.ContractBillQueryForPrint"));
				exec.option().isAutoTranslateEnum = true;
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id", fdcBill.getContractId()));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				e.printStackTrace();
				SysUtil.abort();
			}

			return iRowSet;
		} else if (ds.getId().equalsIgnoreCase("ContractBillQuery")) {
			// ��ͬ��ϸ��Ϣ
			IRowSet iRowSet = null;
			try {
				IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(new MetaDataPK(
						"com.kingdee.eas.fdc.contract.app.ContractBillQuery"));
				exec.option().isAutoTranslateEnum = true;
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id", fdcBill.getContractId()));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				e.printStackTrace();
				SysUtil.abort();
			}

			return iRowSet;
		} else if (ds.getId().equalsIgnoreCase("ContractWithoutTextPrintQuery")) {
			// ���ı���ͬ��ϸ��Ϣ
			IRowSet iRowSet = null;
			try {
				IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(new MetaDataPK(
						"com.kingdee.eas.fdc.contract.app.ContractWithoutTextPrintQuery"));
				
				exec.option().isAutoTranslateEnum = true;
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id", fdcBill.getContractId()));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				e.printStackTrace();
				SysUtil.abort();
			}

			return iRowSet;
		} else if (ds.getId().equalsIgnoreCase("DeductOfPayReqBillPrintQuery")) {
			// �����뵥��Ӧ�Ŀۿ��¼��Ϣ
			IRowSet iRowSet = null;
			try {
				IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(new MetaDataPK(
						"com.kingdee.eas.fdc.contract.app.DeductOfPayReqBillPrintQuery"));
				
				exec.option().isAutoTranslateEnum = true;
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("payRequestBill.id", fdcBill.getId()));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				e.printStackTrace();
				SysUtil.abort();
			}

			return iRowSet;
		} else if (ds.getId().equalsIgnoreCase("PaymentBillF7Query")) {// PaymentBillF7Query��Ҫ��DEP��̬�������ص��ֶ�
			// �����뵥��Ӧ�ĺ�ͬ�ĸ�����Ϣ
			IRowSet iRowSet = null;
			try {
				IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(new MetaDataPK("com.kingdee.eas.fi.cas.PaymentBillF7Query"));
				exec.option().isAutoTranslateEnum = true;
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("contractBillId", fdcBill.getContractId()));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				e.printStackTrace();
				SysUtil.abort();
			}
			return iRowSet;
		}else if (ds.getId().equalsIgnoreCase("AttachementQuery")) {// PaymentBillF7Query��Ҫ��DEP��̬�������ص��ֶ�
			// �����뵥��Ӧ�ĺ�ͬ�ĸ�����Ϣ
			IRowSet iRowSet = null;
			try {
				
				FDCSQLBuilder builder = new FDCSQLBuilder();
				builder.appendSql("select a.fattachid fnumber,a.fname_l2 fname,a.fdescription_l2 fdesc,a.fcreatetime fcreateTime,a.flastupdatetime flastupdatetime ,createMan.fname_l2 createMan,updateMan.fname_l2 updateMan from T_BAS_BoAttchAsso  b ");
				builder.appendSql("inner join T_BAS_Attachment a on a.fid=b.FAttachmentID ");
				builder.appendSql("left join T_PM_User createMan on createMan.fid=a.fcreatorid ");
				builder.appendSql("left join T_PM_User updateMan on updateMan.fid=a.FLASTUPDATEUSERID ");
				builder.appendSql("where FBoID ='"+fdcBill.getId().toString()+"' ");
				iRowSet =builder.executeQuery();
				iRowSet.beforeFirst(); 
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				e.printStackTrace();
				SysUtil.abort();
			}
			return iRowSet;
		}
		return getMainBillRowSet(ds);
	}

	public IRowSet getMainBillRowSet(R1PrintDataSource ds) {
		int colCount = col.length;
		DynamicRowSet drs = null;
		try {
			drs = new DynamicRowSet(colCount);
			
			for (int i = 0; i < colCount; i++) {
				ColInfo ci = new ColInfo();
				ci.colType = Types.VARCHAR;
				ci.columnName = col[i];
				ci.nullable = 1;
				drs.setColInfo(i + 1, ci);
			}
			drs.beforeFirst();			
			String payDetails = "";
			
			if(fdcBill.getId()!=null){
				//����ۼ�ʵ�� ת����String�ַ�����ʾ
				String contractNo = fdcBill.getContractNo().toString();
				Timestamp createTime = fdcBill.getCreateTime();
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				view.setFilter(filter);
				filter.appendFilterItem("contractNo", contractNo);
				filter.getFilterItems().add(new FilterItemInfo("createTime", createTime, CompareType.LESS));
				view.getSelector().add("entrys.paymentBill.id");
				PayRequestBillCollection c;
				Set ids = new HashSet();
				try {
					c = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);
					if(c.size()>0){
						for(Iterator it=c.iterator();it.hasNext();){
							PayRequestBillInfo payRequestInfo = (PayRequestBillInfo)it.next();
							if(payRequestInfo.getEntrys().size()>0){
								for(int i=0;i<payRequestInfo.getEntrys().size();i++){
									if(payRequestInfo.getEntrys().get(i).getPaymentBill()!=null){
										ids.add(payRequestInfo.getEntrys().get(i).getPaymentBill().getId());
									}
								}
							}
						}
					}
				} catch (BOSException e) {
					e.printStackTrace();
					SysUtil.abort();
				}
				filter = new FilterInfo();

				if(ids.size()>0)
					filter.getFilterItems().add(new FilterItemInfo("id",ids,CompareType.INCLUDE));
				else
					filter.getFilterItems().add(new FilterItemInfo("id", null));
				filter.getFilterItems().add(new FilterItemInfo("billStatus", new Integer(BillStatusEnum.PAYED_VALUE)));
				
				//filter.appendFilterItem("contractNo",contractNo);				
				Map map = new HashMap();
				EntityViewInfo ev = new EntityViewInfo();
				ev.getSelector().add("contractNo");
				ev.getSelector().add("feeType.name");
				ev.getSelector().add("amount");
				ev.setFilter(filter);
				PaymentBillCollection coll = PaymentBillFactory.getRemoteInstance().getPaymentBillCollection(ev);
				for(int i =0;i < coll.size();i++){
					PaymentBillInfo info = coll.get(i);
					FeeTypeInfo feeType = null;
					String feeTypeName = "";
					if(info.getObjectValue("feeType") == null){
						feeTypeName = "nulltype";
					}
					else if(info.getObjectValue("feeType") instanceof FeeTypeInfo){
						feeType = (FeeTypeInfo)info.getObjectValue("feeType");
						feeTypeName = feeType.getName();
					}
					
					BigDecimal amount = info.getBigDecimal("amount");
					if(map.containsKey(feeTypeName)){
						BigDecimal amt = (BigDecimal)map.get(feeTypeName);
						map.put(feeTypeName,amount.add(amt));
					}else{
						map.put(feeTypeName,amount);
					}
			
				}
				Set set = map.keySet();
				for(Iterator it=set.iterator();it.hasNext();){
					String key = (String)it.next();
					if(key.equals("nulltype"))
						payDetails +=""+map.get(key)+"\r\n";
					else
						payDetails +=key+":"+map.get(key)+"\r\n";
				}
			}

			
			drs.beforeFirst();
			//ȡ�Ѿ���������ںͽ��
			String paidDataAmount = "";
			if(fdcBill.getId()!=null){
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				FilterItemCollection items = filter.getFilterItems();
				items.add(new FilterItemInfo("contractBillId", fdcBill.getContractId()));
				view.setFilter(filter);
				SorterItemInfo sorterItemInfo = new SorterItemInfo("createTime");
				sorterItemInfo.setSortType(SortType.ASCEND);
				view.getSorter().add(sorterItemInfo);
				view.getSelector().add("payDate");
				view.getSelector().add("localAmt");
				PaymentBillCollection col = PaymentBillFactory.getRemoteInstance().getPaymentBillCollection(view);
				for(int i=0;i<col.size();i++){
					PaymentBillInfo info = col.get(i);
					String payDate = printStringHelper(info.getPayDate());
					String Amt = printStringHelper(info.getLocalAmt());
					paidDataAmount += payDate+":"+Amt+"\r\n";
				}
			}
			//ȡԭ��ͬ����
			String oldProjNumber = "";
			if(fdcBill.getId()!=null){
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				FilterItemCollection items = filter.getFilterItems();
				items.add(new FilterItemInfo("parent", fdcBill.getContractId()));
				//½���죬Ӳ����
				items.add(new FilterItemInfo("detail", "%ԭ��ͬ����%" ,CompareType.LIKE));
				view.setFilter(filter);
//				SorterItemInfo sorterItemInfo = new SorterItemInfo("createTime");
//				sorterItemInfo.setSortType(SortType.ASCEND);
//				view.getSorter().add(sorterItemInfo);
				view.getSelector().add("content");				
				ContractBillEntryCollection col = ContractBillEntryFactory.getRemoteInstance().getContractBillEntryCollection(view);
				for(int i=0;i<col.size();i++){
					ContractBillEntryInfo info = col.get(i);
					String temp = printStringHelper(info.getContent());
					oldProjNumber += temp+"\r\n";
				}
			}
			
			//ȡ������� auditResult
			String auditResult = "";
			if(fdcBill.getId()!=null){
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				FilterItemCollection items = filter.getFilterItems();
				items.add(new FilterItemInfo("billId", fdcBill.getId().toString()));
				view.setFilter(filter);
				SorterItemInfo sorterItemInfo = new SorterItemInfo("createTime");
				sorterItemInfo.setSortType(SortType.ASCEND);
				view.getSorter().add(sorterItemInfo);
				view.getSelector().add("opinion");
				view.getSelector().add("creator.person.name");
				MultiApproveCollection col = MultiApproveFactory.getRemoteInstance().getMultiApproveCollection(view);
				for(int i=0;i<col.size();i++){
					MultiApproveInfo info = col.get(i);
					String option = info.getOpinion();
					String person = info.getCreator().getPersonId().getName();
					auditResult += person+":"+option+"\r\n";
				}
			}
			
			//ȡ�ۿ�����
			DeductOfPayReqBillCollection c = null;
			DeductOfPayReqBillInfo info = null;
			
			if(fdcBill.getId()!=null){
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				FilterItemCollection items = filter.getFilterItems();
				items.add(new FilterItemInfo("payRequestBill.id", fdcBill.getId().toString()));
				view.setFilter(filter);
				SorterItemInfo sorterItemInfo = new SorterItemInfo("deductType.number");
				sorterItemInfo.setSortType(SortType.ASCEND);
				view.getSorter().add(sorterItemInfo);
				view.getSelector().add("deductType.number");
				view.getSelector().add("deductType.name");
				view.getSelector().add("*");
				c = DeductOfPayReqBillFactory.getRemoteInstance().getDeductOfPayReqBillCollection(view);
			}
			//ȡ��ͬ��Ϣ			
			String contractBillAmount = "";
			String contractBillType   = "";
			if(fdcBill.getId()!=null){
				// �޸� R100707-082һ�Ÿ������뵥�״�ʱȡ�������ݡ� By Owen_wen 2010-7-27
				// �ı���ͬ�����ı���ͬ�ĸ������뵥����EditUI�������״�ʱҪ�ֱ���
				boolean isCon = FDCUtils.isContractBill(null, fdcBill.getContractId().toString());
				if(isCon){
					ContractBillInfo contract = ContractBillFactory.getRemoteInstance().getContractBillInfo("select amount,contractType.name where id='"+fdcBill.getContractId().toString()+"'");
					contractBillAmount = printStringHelper(contract.getAmount());
					contractBillType   = contract.getContractType().getName();
				}else{
					ContractWithoutTextInfo contract = ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextInfo("select amount,contractType.name where id='"+fdcBill.getContractId().toString()+"'");
					contractBillAmount = printStringHelper(contract.getAmount());
					contractBillType   = contract.getContractType().getName();
				}
			}
			
			
			Map deductTypeSum = new HashMap();
			deductTypeSum.put("sumLstDeductPaid",FDCHelper.ZERO);
			deductTypeSum.put("sumLstDeductReq",FDCHelper.ZERO);
			deductTypeSum.put("sumDeduct",FDCHelper.ZERO);
			deductTypeSum.put("sumAllDeductReq",FDCHelper.ZERO);
			deductTypeSum.put("sumAllDeductPaid",FDCHelper.ZERO);
			
			//����û��ѭ���Ĵ�ӡ����ÿ��drs��ֵ������Ϊ���һ��
			String lstRealPaidAmt = "";
			/*
			 * R110726-0274:�״�-���ز��ĸ������뵥�״�Ĭ�ϴ�ӡ5��  by zhiyuan_tang
			 */
			if(c==null || c.size()==0){
				DeductTypeCollection typeCol  = null;
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				FilterItemCollection items = filter.getFilterItems();
				items.add(new FilterItemInfo("isEnabled", Boolean.TRUE));
				view.setFilter(filter);
				typeCol  = DeductTypeFactory.getRemoteInstance().getDeductTypeCollection(view);
				
				for(int i=0;i<typeCol.size();i++){
					DeductTypeInfo deductTypeInfo = typeCol.get(i);
					drs.moveToInsertRow();
					insert( drs,null,deductTypeSum );
					drs.updateString("deductType",deductTypeInfo==null?"":deductTypeInfo.getName());
					// �Ѹ����ڽ��
					drs.updateString("paidDataAmount", paidDataAmount);
					// ԭ��ͬ����
					drs.updateString("oldProjNumber", oldProjNumber);
					drs.updateString("paidDetail", payDetails);

					drs.updateString("contractBillAmount", contractBillAmount);
					drs.updateString("contractBillType", contractBillType);
					// �������
					drs.updateString("auditResult", auditResult);
					drs.insertRow();
				}
			}else{
				
				for(int i=0;i<c.size();i++){
					info = c.get(i);
					
					drs.moveToInsertRow();
					insert( drs,info ,deductTypeSum);
					// �Ѹ����ڽ��
					drs.updateString("paidDataAmount", paidDataAmount);
					// ԭ��ͬ����
					drs.updateString("oldProjNumber", oldProjNumber);
					drs.updateString("paidDetail", payDetails);

					drs.updateString("contractBillAmount", contractBillAmount);
					drs.updateString("contractBillType", contractBillType);

					lstRealPaidAmt = drs.getString("lstRealPaidAmt");
					// �������
					drs.updateString("auditResult", auditResult);
					drs.insertRow();
				}
			}			

			drs.beforeFirst();
			
			while(drs.next()){
				drs.updateString("lstRealPaidAmt",lstRealPaidAmt);
			}
			drs.beforeFirst();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

		return drs;
	}

	/**
	 * ���ݺ�ͬID��ȡ��Ӧ��ͬ�Ļ���
	 * @param contractId �������ı���ͬ��Ҳ�п��������ı���ͬ��Ҫ��������
	 */
	public BigDecimal getConExRate(String contractId){
		if (this.exRate != null)
			return exRate;
		
		exRate = FDCHelper.ONE;
		try {
			if (new ContractBillInfo().getBOSType().equals(BOSUuid.read(contractId).getBOSObjectType(contractId, false))) { // ��ͨ��ͬ
				ContractBillInfo info = ContractBillFactory.getRemoteInstance().getContractBillInfo(
						new ObjectUuidPK(BOSUuid.read(contractId)));
				exRate = info.getExRate();
			} else {//���ı���ͬ
				ContractWithoutTextInfo info = ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextInfo(
						new ObjectUuidPK(BOSUuid.read(contractId)));
				BOSUuid srcid = info.getCurrency().getId();
				Date bookedDate = info.getBookedDate();

				ExchangeRateInfo exchangeRate = FDCClientHelper.getLocalExRateBySrcCurcy(null, srcid, SysContext.getSysContext()
						.getCurrentFIUnit(), bookedDate);

				if (exchangeRate != null) {
					exRate = exchangeRate.getConvertRate();
				}
			}
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			SysUtil.abort();
		}
		return exRate;
	}
	/**
	 * �ۼ��깤������ȡ�����ϡ��ۼ��깤���������Ľ�������������ʾ�����깤������ʱ��������Դ����ʾ���ݣ�����Ϊ�� ��
	 * @return
	 */
	private void initAllCompletePrjAmt(){
		if(!fdcBill.getBoolean("isCompletePrjAmtVisible")){
			fdcBill.put("allCompletePrjAmt", "");
			return;
		}
//		BigDecimal allCompleteProjAmt = FDCHelper.ZERO;
//		String paymentType = fdcBill.getPaymentType().getPayType().getId().toString();
//    	String progressID = TypeInfo.progressID;
//    	if(!paymentType.equals(progressID)){
//    		allCompleteProjAmt =FDCHelper.toBigDecimal(contractBill.getSettleAmt(),2);
//    		fdcBill.put("allCompletePrjAmt", allCompleteProjAmt);
//    		return;
//    	}
//    	
//    	EntityViewInfo view = new EntityViewInfo();
//    	view.getSelector().add("completePrjAmt");
//    	view.getSelector().add("state");
//    	FilterInfo filter = new FilterInfo();
//    	filter.getFilterItems().add(new FilterItemInfo("contractId", fdcBill.getContractId()));
//    	filter.getFilterItems().add(new FilterItemInfo("paymentType.payType.id", progressID));
//    	filter.getFilterItems().add(new FilterItemInfo("createTime", fdcBill.getCreateTime(), CompareType.LESS_EQUALS));
//    	view.setFilter(filter);
//    	PayRequestBillCollection payReqColl = null;
//		try {
//			payReqColl = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);
//		} catch (BOSException e) {
//			e.printStackTrace();
//		}
//    	
//    	if(payReqColl != null){
//    		for(int i=0;i<payReqColl.size();i++){
//    			PayRequestBillInfo info = payReqColl.get(i);
//    			allCompleteProjAmt = allCompleteProjAmt.add(FDCHelper.toBigDecimal(info.getCompletePrjAmt()));
//    		}
//    	}
//    	allCompleteProjAmt = FDCHelper.toBigDecimal(allCompleteProjAmt, 2);
    	fdcBill.put("allCompletePrjAmt", fdcBill.get("allCompletePrjAmt"));
	}
}
