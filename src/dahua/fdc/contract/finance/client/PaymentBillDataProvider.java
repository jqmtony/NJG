package com.kingdee.eas.fdc.finance.client;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.reportone.r1.print.data.R1PrintDataSource;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.util.FDCNoteDataProvider;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.jdbc.rowset.impl.ColInfo;
import com.kingdee.jdbc.rowset.impl.DynamicRowSet;
import com.kingdee.util.UuidException;

public class PaymentBillDataProvider extends FDCNoteDataProvider {
	static public final String[] col = new String[] { "id", "bookedDate",
			"number", "useDepartment.name", "period", "payDate", "dayaccount",
			"feeType.name", "recProvince", "recCity",
			"payerAccountBank.bankAccountNumber", "payeeType.name",
			"isDifferPlace", "payerBank.name", "payee", "actFdcPayeeName.name",
			"payerAccount.name", "payeeBank", "currency.name", "bizType.name",
			"payeeAccountBank", "exchangeRate", "isEmergency", "amount",
			"accessoryAmt", "localAmt", "capitalAmount", "paymentProportion",
			"description", "completePrjAmt", "usage", "fpItem.name", "conceit",
			"summary", "settlementType.name", "settlementNumber",
			"payeeArea.name", "creator.number", "creator.name", "createTime",
			"auditor.number", "auditor.name", "auditDate", "accountant.number",
			"accountant.name", "cashier.number", "cashier.name",
			"projNameWithoutOrg",
			"curProject.displayName", 
			"contractName", "settleAmt", "payTimes", 
		    "deductType", "balance", "curPlannedPayment",
			"curBackPay", "curReqPercent", "allReqPercent", "imageSchedule", "contractNo",
			//TODO ��Ʊ���ۼƷ�Ʊ����״���ʵ���ֶζ�Ӧ�����û�����ⲻ������
			"contractPrice",//��ͬ��۱���
			"contractOriPrice",//��ͬ���ԭ��
			"latestPrice", //������۱���
			"latestOriPrice", //�������ԭ��
			"changeAmt",//���ָ�����
			"changeOriAmt",//���ָ����ԭ��
			"payedAmt",//�����뵥�Ѹ�����
			"payedOriAmt",//�����뵥�Ѹ�ԭ��
			
			"lstPrjAllPaidAmt",//��ͬ�ڹ��̿�_���������ۼ�ʵ��_���� 
			"lstPrjAllPaidOriAmt",//��ͬ�ڹ��̿�_���������ۼ�ʵ��_ԭ��
			"lstPrjAllReqAmt",//��ͬ�ڹ��̿�_���������ۼ�����_����
			"lstPrjAllReqOriAmt",//��ͬ�ڹ��̿�_���������ۼ�����_ԭ��
			"projectPriceInContract", //��ͬ�ڹ��̿�_���ڷ���_����
			"projectPriceInContractOri", //��ͬ�ڹ��̿�_���ڷ���_ԭ��
			"prjAllReqAmt", //��ͬ�ڹ��̿�_���������ۼ�����_����
			"prjAllReqOriAmt", //��ͬ�ڹ��̿�_���������ۼ�����_ԭ��
			"prjAllPaidAmt",//��ͬ�ڹ��̿�_���������ۼ�ʵ��_����
			"prjAllPaidOriAmt",//��ͬ�ڹ��̿�_���������ۼ�ʵ��_ԭ��
			
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
			
	};
	public static String printStringHelper(Object o) {
		if (o == null)
			return "";
		else if(o instanceof BigDecimal){
			if(FDCHelper.ZERO.compareTo(FDCHelper.toBigDecimal(o))==0)
				return "";
			else
				return String.valueOf(((BigDecimal)o).setScale(2,BigDecimal.ROUND_HALF_UP));
		}
			return String.valueOf(o);
	}

	public static String printStringHelper(boolean o) {
		return o ? "��" : "��";
	}
	
	public IRowSet getOtherSubRowSet(R1PrintDataSource ds) throws Exception {
		return getMainBillRowSet(ds);
	}
	private PaymentBillInfo fdcBill = null;
	private PayRequestBillInfo payReqInfo = null;
	private CurProjectInfo curProjectInfo = null;
	private HashMap bindCellMap =null;
	public PaymentBillDataProvider(PaymentBillInfo editData,HashMap bindCellMap,CurProjectInfo curProjectInfo,IMetaDataPK mainQuery) {
		super(editData.getId().toString(), mainQuery);
		fdcBill = editData;
		payReqInfo = (PayRequestBillInfo)editData.get("payReqInfo");
		this.bindCellMap = bindCellMap;
		this.curProjectInfo = curProjectInfo;
	}

	/**
	 * ��ȡ��Ӧ��ͬ�Ļ���
	 * �˷�����ʱ���ʱӦ�ÿ��ǵ��˸���򸶿����뵥�������ܲ��ø������ʡ�
	 * fdcBill.getExchangeRate()���ҲӦ����ȡ��ʱ��ͬ�Ļ��ʰɡ�
	 * added by adny_liu 2012-4-13
	 */
	public BigDecimal getConExRate(String contractId) {
		ContractBillInfo info = null;
		BigDecimal exRate = FDCHelper.ONE;
		try {
			info = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
			exRate = info.getExRate();
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (UuidException e) {
			e.printStackTrace();
		}
		return exRate;
	}

	/**
	 * ���ݻ��ʱ���/����=ԭ�ҡ�
	 * added by adny_liu 2012-4-13
	 */
	public String getOriValue(BigDecimal bg) {
		return printStringHelper(FDCHelper.divide(bg, getConExRate(fdcBill.getContractBillId()), 4, BigDecimal.ROUND_HALF_UP));
	}

	private void insert(IRowSet drs,DeductOfPayReqBillInfo info,Map deductTypeSum) throws Exception{
		
		/**************************************************ͷ��start**************************************************/
		//��ͬ��� 
		drs.updateString("contractNo", fdcBill.getContractNo());
		//��������
		drs.updateString("bookedDate",printStringHelper(payReqInfo.getBookedDate()));
		//�������
		drs.updateString("number",fdcBill.getNumber());
		//�ÿ��
		drs.updateString("useDepartment.name",fdcBill.getUseDepartment()!=null?fdcBill.getUseDepartment().getName():null);
		//�����ڼ�
		if(payReqInfo.getPeriod()!=null)
		drs.updateString("period",payReqInfo.getPeriod().getPeriodYear()+"��"+payReqInfo.getPeriod().getPeriodNumber()+"��");
		//��������
		drs.updateString("payDate",printStringHelper(fdcBill.getPayDate()));
		//�ռ������
		drs.updateString("dayaccount",printStringHelper(fdcBill.getDayaccount()));
		//�ո����
		drs.updateString("feeType.name",fdcBill.getFeeType()!=null?fdcBill.getFeeType().getName():null);
		//�տʡ
		drs.updateString("recProvince",fdcBill.getRecProvince());
		//�տ��/��
		drs.updateString("recCity",fdcBill.getRecCity());
		//�����˺�
		drs.updateString("payerAccountBank.bankAccountNumber",fdcBill.getPayerAccountBank()!=null?fdcBill.getPayerAccountBank().getBankAccountNumber():null);
		//�տ�������
		drs.updateString("payeeType.name",fdcBill.getPayeeType()!=null?fdcBill.getPayeeType().getName():null);
		//ͬ�����
		drs.updateString("isDifferPlace",printStringHelper(fdcBill.getIsDifferPlace()));
		//��������
		drs.updateString("payerBank.name",fdcBill.getPayerBank()!=null?fdcBill.getPayerBank().getName():null);
		//�տ�������
		drs.updateString("payee",fdcBill.getPayeeName());
		//ʵ���տλȫ��
		drs.updateString("actFdcPayeeName.name",fdcBill.getActFdcPayeeName()!=null?fdcBill.getActFdcPayeeName().getName():null);
		//�����Ŀ
		drs.updateString("payerAccount.name",fdcBill.getPayerAccount()!=null?fdcBill.getPayerAccount().getName():null);
		//�տ�����
		drs.updateString("payeeBank",fdcBill.getPayeeBank());
		//�ұ�
		drs.updateString("currency.name",fdcBill.getCurrency()!=null?fdcBill.getCurrency().getName():null);
		//ҵ������
		drs.updateString("bizType.name",fdcBill.getBizType()!=null?fdcBill.getBizType().getName():null);
		//�տ��˻�
		drs.updateString("payeeAccountBank",fdcBill.getPayeeAccountBank());
		//����
		drs.updateString("exchangeRate",printStringHelper(fdcBill.getExchangeRate()));
		//�Ƿ�Ӽ�
		drs.updateString("isEmergency",printStringHelper(fdcBill.getIsEmergency()));
		//ԭ�ҽ��
		drs.updateString("amount",printStringHelper(fdcBill.getAmount()));
		//������
		drs.updateString("accessoryAmt",String.valueOf(fdcBill.getAccessoryAmt()));
		//���ҽ��
		drs.updateString("localAmt",printStringHelper(fdcBill.getLocalAmt()));
		//��д���
		drs.updateString("capitalAmount",fdcBill.getCapitalAmount());
		//���ȿ����(%)
		drs.updateString("paymentProportion",printStringHelper(payReqInfo.getPaymentProportion()));
		//ժҪ
		drs.updateString("description",fdcBill.getDescription());
		//���ڳɱ����
		drs.updateString("completePrjAmt",printStringHelper(payReqInfo.getCompletePrjAmt()));
		//��;
		drs.updateString("usage",fdcBill.getUsage());
		//�ƻ���Ŀ
		drs.updateString("fpItem.name",fdcBill.getFpItem()!=null?fdcBill.getFpItem().getName():null);
		//������
		drs.updateString("conceit",fdcBill.getConceit());
		//��ע
		drs.updateString("summary",fdcBill.getSummary());
		//���㷽ʽ
		drs.updateString("settlementType.name",fdcBill.getSettlementType()!=null?fdcBill.getSettlementType().getName():null);
		//�����
		drs.updateString("settlementNumber",fdcBill.getSettlementNumber());
		//�Ƶ���
		drs.updateString("creator.number",fdcBill.getCreator()!=null?fdcBill.getCreator().getNumber():null);
		//�Ƶ���
		drs.updateString("creator.name",fdcBill.getCreator()!=null?fdcBill.getCreator().getName():null);
		//�Ƶ�ʱ��
		drs.updateString("createTime",printStringHelper(fdcBill.getCreateTime()));
		//�����
		drs.updateString("auditor.number",fdcBill.getAuditor()!=null?fdcBill.getAuditor().getNumber():null);
		//�����
		drs.updateString("auditor.name",fdcBill.getAuditor()!=null?fdcBill.getAuditor().getName():null);
		//�������
		drs.updateString("auditDate",printStringHelper(fdcBill.getAuditDate()));
		//��Ʊ��
		drs.updateString("accountant.number",fdcBill.getAccountant()!=null?fdcBill.getAccountant().getNumber():null);
		//�������
		drs.updateString("accountant.name",fdcBill.getAccountant()!=null?fdcBill.getAccountant().getName():null);
		//���ɱ��
		drs.updateString("cashier.number",fdcBill.getCashier()!=null?fdcBill.getCashier().getNumber():null);
		//��������
		drs.updateString("cashier.number",fdcBill.getCashier()!=null?fdcBill.getCashier().getNumber():null);
		/**************************************************ͷ��start**************************************************/
		
		
		
		/**************************************************���̸��������start**************************************************/
		BigDecimal sumLstDeductPaid = (BigDecimal)deductTypeSum.get("sumLstDeductPaid");
		BigDecimal sumLstDeductReq =  (BigDecimal)deductTypeSum.get("sumLstDeductReq");
		BigDecimal sumDeduct =  (BigDecimal)deductTypeSum.get("sumDeduct");
		BigDecimal sumAllDeductReq =  (BigDecimal)deductTypeSum.get("sumAllDeductReq");
		BigDecimal sumAllDeductPaid = (BigDecimal)deductTypeSum.get("sumAllDeductPaid");
		
//		 �ڴ˰����ݴ��ݸ�ʵ���࣬drs.updateString(key,value) key
		// ָ�����״�ģ���ж�����ֶα��룬Valueָ���ǵ�ǰ���ݵ�����ֵ
		String orgName=((CurProjectInfo)fdcBill.getCurProject()).getFullOrgUnit().getName();
		String curProjectName = curProjectInfo.getDisplayName();		
		//ȡ��������Ҫ��ֻȡ��Ŀ����
		//2008-07-22
		String projNameWithoutOrg = curProjectName.replace('_', '\\');
		curProjectName = orgName+"\\"+curProjectName.replace('_', '\\');
		drs.updateString("curProject.displayName", curProjectName);
		drs.updateString("projNameWithoutOrg", projNameWithoutOrg);
		drs.updateString("contractName",payReqInfo.getContractName());
		//��ͬ��۱���
		drs.updateString("contractPrice", printStringHelper(payReqInfo.getContractPrice()));
		//��ͬ���ԭ��
		drs.updateString("contractOriPrice", getOriValue(payReqInfo.getContractPrice()));
		//������۱���
		drs.updateString("latestPrice",printStringHelper(fdcBill.getLatestPrice()));
		//�������ԭ��
		drs.updateString("latestOriPrice", getOriValue(fdcBill.getLatestPrice()));
		//���ָ�����
		drs.updateString("changeAmt",printStringHelper(fdcBill.getChangeAmt()));
		//���ָ����ԭ��
		drs.updateString("changeOriAmt", getOriValue(fdcBill.getChangeAmt()));
		//�����뵥�Ѹ�����
		drs.updateString("payedAmt", printStringHelper(fdcBill.getPayedAmt()));
		//�����뵥�Ѹ�ԭ��
		drs.updateString("payedOriAmt", getOriValue(fdcBill.getPayedAmt()));
		
		drs.updateString("settleAmt",printStringHelper(fdcBill.getSettleAmt()));
		drs.updateString("payTimes", String.valueOf(fdcBill.getPayTimes()));
		
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
		//��ͬ�ڹ��̿�_���ڷ���_ԭ��
		drs.updateString("projectPriceInContractOri", getOriValue(fdcBill.getProjectPriceInContract()));
		//��ͬ�ڹ��̿�_���������ۼ�����_����
		drs.updateString("prjAllReqAmt", printStringHelper(fdcBill.getPrjAllReqAmt()));
		//��ͬ�ڹ��̿�_���������ۼ�����_ԭ��
		drs.updateString("prjAllReqOriAmt", getOriValue(fdcBill.getPrjAllReqAmt()));
		//��ͬ�ڹ��̿�_���������ۼ�ʵ��_����= ���������ۼ��ۼ�ʵ�� +����
		BigDecimal prjAllPaidAmt = (fdcBill.getLstPrjAllPaidAmt()!=null?fdcBill.getLstPrjAllPaidAmt():FDCHelper.ZERO);
		prjAllPaidAmt = FDCHelper.add(prjAllPaidAmt,fdcBill.getProjectPriceInContract());
		drs.updateString("prjAllPaidAmt", printStringHelper(prjAllPaidAmt));
		//��ͬ�ڹ��̿�_���������ۼ�ʵ��_ԭ��
		drs.updateString("prjAllPaidOriAmt", getOriValue(prjAllPaidAmt));
		
		//����_���������ۼ�ʵ��_����
		BigDecimal lstGuerdonPaidAmt = FDCHelper.toBigDecimal(((ICell)( bindCellMap.get("lstGuerdonPaidAmt"))).getValue());				
		drs.updateString("lstGuerdonPaidAmt",printStringHelper(lstGuerdonPaidAmt));
		//����_���������ۼ�ʵ��_ԭ��
		drs.updateString("lstGuerdonPaidOriAmt", getOriValue(lstGuerdonPaidAmt));
		//����_���������ۼ�����_����
		BigDecimal lstGuerdonReqAmt = FDCHelper.toBigDecimal(( (ICell)( bindCellMap.get("lstGuerdonReqAmt"))).getValue());
		drs.updateString("lstGuerdonReqAmt", printStringHelper(lstGuerdonReqAmt));
		//����_���������ۼ�����_ԭ��
		drs.updateString("lstGuerdonReqOriAmt", getOriValue(lstGuerdonReqAmt));
		//����_���ڷ���_����				
		BigDecimal guerdonAmt = FDCHelper.toBigDecimal(((ICell)( bindCellMap.get("guerdonAmt"))).getValue());
		guerdonAmt = guerdonAmt!= null?guerdonAmt: FDCHelper.ZERO;
		drs.updateString("guerdonAmt", printStringHelper(guerdonAmt));
		//����_���ڷ���_ԭ��
		drs.updateString("guerdonOriAmt", getOriValue(guerdonAmt));
		//����_���������ۼ�����_���� = ���� ���������ۼ����� + ���� ���ڷ���
		BigDecimal allGuerdonReqAmt = guerdonAmt.add(lstGuerdonReqAmt != null ? lstGuerdonReqAmt : FDCHelper.ZERO);
		drs.updateString("allGuerdonReqAmt", printStringHelper(allGuerdonReqAmt));
		//����_���������ۼ�����_ԭ��
		drs.updateString("allGuerdonReqOriAmt", getOriValue(allGuerdonReqAmt));
		//����_���������ۼ�ʵ��_���� = ���� ���������ۼ�ʵ�� + ����
		BigDecimal allGuerdonPaidAmt = guerdonAmt.add(lstGuerdonPaidAmt!= null ?lstGuerdonPaidAmt: FDCHelper.ZERO);
		drs.updateString("allGuerdonPaidAmt",printStringHelper(allGuerdonPaidAmt));
		//����_���������ۼ�ʵ��_ԭ��
		drs.updateString("allGuerdonPaidOriAmt", getOriValue(allGuerdonPaidAmt));
		
		//ΥԼ_���������ۼ�ʵ��_����
		BigDecimal lstCompensationPaidAmt = FDCHelper.toBigDecimal(( (ICell)( bindCellMap.get("lstCompensationPaidAmt"))).getValue());
		drs.updateString("lstCompensationPaidAmt",printStringHelper(lstCompensationPaidAmt));
		//ΥԼ_���������ۼ�ʵ��_ԭ��
		drs.updateString("lstCompensationPaidOriAmt", getOriValue(lstCompensationPaidAmt));
		//ΥԼ_���������ۼ�����_����
		BigDecimal lstCompensationReqAmt = FDCHelper.toBigDecimal(( (ICell)( bindCellMap.get("lstCompensationReqAmt"))).getValue());
		drs.updateString("lstCompensationReqAmt", printStringHelper(lstCompensationReqAmt));
		//ΥԼ_���������ۼ�����_ԭ��
		drs.updateString("lstCompensationReqOriAmt", getOriValue(lstCompensationReqAmt));
		//ΥԼ_���ڷ���_����
		BigDecimal compensationAmt = FDCHelper.toBigDecimal(( (ICell)( bindCellMap.get("compensationAmt"))).getValue());
		compensationAmt = compensationAmt != null? compensationAmt: FDCHelper.ZERO;
		drs.updateString("compensationAmt", printStringHelper(compensationAmt));
		//ΥԼ_���ڷ���_ԭ��
		drs.updateString("compensationOriAmt", getOriValue(compensationAmt));
		//ΥԼ_���������ۼ�����_����= ΥԼ ���������ۼ����� + ΥԼ ���ڷ���					
		BigDecimal allCompensationReqAmt = compensationAmt.add(lstCompensationReqAmt != null ? lstCompensationReqAmt : FDCHelper.ZERO);
		drs.updateString("allCompensationReqAmt", printStringHelper(allCompensationReqAmt));
		//ΥԼ_���������ۼ�����_ԭ��
		drs.updateString("allCompensationReqOriAmt", getOriValue(allCompensationReqAmt));
		//ΥԼ_���������ۼ�ʵ��_����= ΥԼ ���������ۼ�ʵ�� +����	
		BigDecimal allCompensationPaidAmt = compensationAmt.add(lstCompensationPaidAmt != null ? lstCompensationPaidAmt : FDCHelper.ZERO);		
		drs.updateString("allCompensationPaidAmt",printStringHelper(allCompensationPaidAmt));
		//ΥԼ_���������ۼ�ʵ��_ԭ��
		drs.updateString("allCompensationPaidOriAmt", getOriValue(allCompensationPaidAmt));
		//�ۿ���
		//�ۿ�����				
		if(info!=null){
			drs.updateString("deductType",info==null?"":info.getDeductType().getName());
			BigDecimal lstDeductPaidAmt = info.getAllPaidAmt()==null?FDCHelper.ZERO:info.getAllPaidAmt();
			BigDecimal lstDeductReqAmt  = info.getAllReqAmt()==null?FDCHelper.ZERO:info.getAllReqAmt();
			BigDecimal deductAmt        = info.getAmount()==null?FDCHelper.ZERO:info.getAmount();
			BigDecimal allDeductReqAmt  = lstDeductReqAmt.add(deductAmt);
			BigDecimal allDeductPaidAmt  = lstDeductPaidAmt.add(deductAmt);
			
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
		//�׹��ۿ�_���������ۼ�ʵ��_���� = �׹��� ���������ۼ�ʵ�� +����
		BigDecimal allPartAMatlAllPaidAmt = (fdcBill.getLstAMatlAllPaidAmt() != null ? fdcBill.getLstAMatlAllPaidAmt() : FDCHelper.ZERO);
		allPartAMatlAllPaidAmt = FDCHelper.add(allPartAMatlAllPaidAmt,fdcBill.getPayPartAMatlAmt());
		drs.updateString("allPartAMatlAllPaidAmt",printStringHelper(allPartAMatlAllPaidAmt));
		//�׹��ۿ�_���������ۼ�ʵ��_ԭ��
		drs.updateString("allPartAMatlAllPaidOriAmt", getOriValue(allPartAMatlAllPaidAmt));
		

		
		//ʵ����_���������ۼ�ʵ��_���� = ��ͬ���̿� + ���� - ΥԼ�� - �ۿ�С�� - �׹���
		BigDecimal lstRealPaidAmt = FDCHelper.toBigDecimal(fdcBill.getLstPrjAllPaidAmt());
		lstRealPaidAmt = lstRealPaidAmt.add(lstGuerdonPaidAmt)//����
				.subtract(lstCompensationPaidAmt)//ΥԼ
				.subtract(sumLstDeductPaid)//�ۿ�
				.subtract(fdcBill.getLstAMatlAllPaidAmt() != null ? fdcBill
								.getLstAMatlAllPaidAmt()
								: FDCHelper.ZERO);//�׹���
		drs.updateString("lstRealPaidAmt",printStringHelper(lstRealPaidAmt));
		//ʵ����_���������ۼ�ʵ��_ԭ��
		drs.updateString("lstRealPaidOriAmt", getOriValue(lstRealPaidAmt));
		
		//ʵ����_���������ۼ�����_���� = ��ͬ���̿� + ���� - ΥԼ�� - �ۿ�С�� - �׹���
		BigDecimal lstRealReqAmt = FDCHelper.toBigDecimal(fdcBill.getLstPrjAllReqAmt());
		lstRealReqAmt = lstRealReqAmt.add(lstGuerdonReqAmt)//����
				.subtract(lstCompensationReqAmt)//ΥԼ
				.subtract(sumLstDeductReq)//�ۿ�
				.subtract(fdcBill.getLstAMatlAllReqAmt() != null ? fdcBill
								.getLstAMatlAllReqAmt()
								: FDCHelper.ZERO);//�׹���
		drs.updateString("lstRealReqAmt",printStringHelper(lstRealReqAmt));
		//ʵ����_���������ۼ�����_ԭ��
		drs.updateString("lstRealReqOriAmt", getOriValue(lstRealReqAmt));
		
		//ʵ����_���ڷ���_���� = ��ͬ���̿� + ���� - ΥԼ�� - �ۿ�С�� - �׹���
		BigDecimal projectPriceInContract = fdcBill.getProjectPriceInContract() != null ? fdcBill.getProjectPriceInContract()
				: FDCHelper.ZERO;
		//		BigDecimal tempCurPaid = fdcBill.getCurPaid()!= null ? fdcBill.getCurPaid(): FDCHelper.ZERO;
		BigDecimal tempCurPaid = projectPriceInContract.add(guerdonAmt)//����
				.subtract(compensationAmt)//ΥԼ
				.subtract(sumDeduct)//�ۿ�
				.subtract(fdcBill.getPayPartAMatlAmt() != null ? fdcBill.getPayPartAMatlAmt() : FDCHelper.ZERO);//�׹���
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
//		BigDecimal prjAllReqAmt =  fdcBill.getPrjAllReqAmt();
		BigDecimal allRealPaidAmt = prjAllReqAmt
				.add(allGuerdonPaidAmt)//����
				.subtract(allCompensationPaidAmt)//ΥԼ
				.subtract(sumAllDeductPaid)//�ۿ�
				.subtract(allPartAMatlAllPaidAmt);//�׹���
		drs.updateString("allRealPaidAmt",printStringHelper(allRealPaidAmt));
		//ʵ����_���������ۼ�ʵ��_ԭ��
		drs.updateString("allRealPaidOriAmt", getOriValue(allRealPaidAmt));
		
		// ��� = ������� - ��ͬ�ڸ��� ���������ۼ�ʵ��
		BigDecimal balance = (fdcBill
		.getLatestPrice() == null ? FDCHelper.ZERO : fdcBill.getLatestPrice())				
		.subtract(prjAllPaidAmt);//��ͬ�ڸ��� ���������ۼ�ʵ��
		
		drs.updateString("balance",printStringHelper(balance));
		// ��������% = ʵ���� ���ڷ��� / �������
		BigDecimal tempCurReqPercent = fdcBill.getLatestPrice() == null
				|| fdcBill.getLatestPrice().compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO
				: tempCurPaid.divide(fdcBill.getLatestPrice(), 4,
						BigDecimal.ROUND_HALF_UP);
//		BigDecimal tempCurReqPercent = fdcBill.getCurReqPercent()!=null?
//				fdcBill.getCurReqPercent():FDCHelper.ZERO;
		drs.updateString("curReqPercent",
				printStringHelper((tempCurReqPercent.multiply(FDCConstants.ONE_HUNDRED)).setScale(2)));
		
		// �ۼ�����% = ʵ���� ���������ۼ����� / �������
		BigDecimal tempAllCurReqPercent = fdcBill.getLatestPrice() == null
				|| fdcBill.getLatestPrice().compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO
				: allRealReqAmt.divide(fdcBill.getLatestPrice(), 4,
						BigDecimal.ROUND_HALF_UP);
		
		drs.updateString("allReqPercent",
				printStringHelper((tempAllCurReqPercent.multiply(FDCConstants.ONE_HUNDRED)).setScale(2)));
		// ///
		drs.updateString("imageSchedule", printStringHelper(fdcBill
				.getImageSchedule()));
		/**************************************************���̸��������end**************************************************/
	}

	public IRowSet getMainBillRowSet(R1PrintDataSource ds) {
		DynamicRowSet drs = null;
		try {
			drs = new DynamicRowSet(col.length);
			for (int i = 0; i < col.length; i++) {
				ColInfo ci = new ColInfo();
				ci.colType = Types.VARCHAR;
				ci.columnName = col[i];
				ci.nullable = 1;
				drs.setColInfo(i + 1, ci);
			}
			drs.beforeFirst();
			
			//ȡ�ۿ�����
			DeductOfPayReqBillCollection c = null;
			DeductOfPayReqBillInfo info = null;
			
			if(fdcBill.getId()!=null){
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				FilterItemCollection items = filter.getFilterItems();
				items.add(new FilterItemInfo("payRequestBill.id", payReqInfo.getId().toString()));
				view.setFilter(filter);
				SorterItemInfo sorterItemInfo = new SorterItemInfo("deductType.number");
				sorterItemInfo.setSortType(SortType.ASCEND);
				view.getSorter().add(sorterItemInfo);
				view.getSelector().add("deductType.number");
				view.getSelector().add("deductType.name");
				view.getSelector().add("*");
				c = DeductOfPayReqBillFactory.getRemoteInstance().getDeductOfPayReqBillCollection(view);
			}
			if(c!=null){
				Map deductTypeSum = new HashMap();
				deductTypeSum.put("sumLstDeductPaid",FDCHelper.ZERO);
				deductTypeSum.put("sumLstDeductReq",FDCHelper.ZERO);
				deductTypeSum.put("sumDeduct",FDCHelper.ZERO);
				deductTypeSum.put("sumAllDeductReq",FDCHelper.ZERO);
				deductTypeSum.put("sumAllDeductPaid",FDCHelper.ZERO);
				for(int i=0;i<c.size();i++){
					info = c.get(i);
					drs.moveToInsertRow();
					insert( drs,info ,deductTypeSum);
					drs.insertRow();
				}
			}
			drs.beforeFirst();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return drs;
	}


}
