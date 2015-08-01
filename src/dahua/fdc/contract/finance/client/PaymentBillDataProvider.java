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
			//TODO 发票金额及累计发票金额套打有实体字段对应，如果没有问题不做处理。
			"contractPrice",//合同造价本币
			"contractOriPrice",//合同造价原币
			"latestPrice", //最新造价本币
			"latestOriPrice", //最新造价原币
			"changeAmt",//变更指令金额本币
			"changeOriAmt",//变更指令金额原币
			"payedAmt",//本申请单已付本币
			"payedOriAmt",//本申请单已付原币
			
			"lstPrjAllPaidAmt",//合同内工程款_截至上期累计实付_本币 
			"lstPrjAllPaidOriAmt",//合同内工程款_截至上期累计实付_原币
			"lstPrjAllReqAmt",//合同内工程款_截至上期累计申请_本币
			"lstPrjAllReqOriAmt",//合同内工程款_截至上期累计申请_原币
			"projectPriceInContract", //合同内工程款_本期发生_本币
			"projectPriceInContractOri", //合同内工程款_本期发生_原币
			"prjAllReqAmt", //合同内工程款_截至本期累计申请_本币
			"prjAllReqOriAmt", //合同内工程款_截至本期累计申请_原币
			"prjAllPaidAmt",//合同内工程款_截至本期累计实付_本币
			"prjAllPaidOriAmt",//合同内工程款_截至本期累计实付_原币
			
			"lstGuerdonPaidAmt",//奖励_截至上期累计实付_本币
			"lstGuerdonPaidOriAmt",//奖励_截至上期累计实付_原币
			"lstGuerdonReqAmt",//奖励_截至上期累计申请_本币
			"lstGuerdonReqOriAmt",//奖励_截至上期累计申请_原币
			"guerdonAmt",//奖励_本期发生_本币
			"guerdonOriAmt",//奖励_本期发生_原币
			"allGuerdonReqAmt",//奖励_截至本期累计申请_本币
			"allGuerdonReqOriAmt",//奖励_截至本期累计申请_原币
			"allGuerdonPaidAmt",//奖励_截至本期累计实付_本币
			"allGuerdonPaidOriAmt",//奖励_截至本期累计实付_原币

			"lstCompensationPaidAmt",//违约_截至上期累计实付_本币
			"lstCompensationPaidOriAmt",//违约_截至上期累计实付_原币
			"lstCompensationReqAmt",//违约_截至上期累计申请_本币
			"lstCompensationReqOriAmt",//违约_截至上期累计申请_原币
			"compensationAmt",//违约_本期发生_本币
			"compensationOriAmt",//违约_本期发生_原币
			"allCompensationReqAmt",//违约_截至本期累计申请_本币
			"allCompensationReqOriAmt",//违约_截至本期累计申请_原币
			"allCompensationPaidAmt", //违约_截至本期累计实付_本币
			"allCompensationPaidOriAmt", //违约_截至本期累计实付_原币

			"lstDeductPaidAmt",//扣款_截至上期累计实付_本币
			"lstDeductPaidOriAmt",//扣款_截至上期累计实付_原币
			"lstDeductReqAmt",//扣款_截至上期累计申请_本币
			"lstDeductReqOriAmt",//扣款_截至上期累计申请_原币
			"deductAmt",//扣款_本期发生_本币
			"deductOriAmt",//扣款_本期发生_原币
			"allDeductReqAmt",//扣款_截至本期累计申请_本币
			"allDeductReqOriAmt",//扣款_截至本期累计申请_原币
			"allDeductPaidAmt",//扣款_截至本期累计实付_本币
			"allDeductPaidOriAmt",//扣款_截至本期累计实付_原币

			"lstDeductSumPaidAmt",//扣款小计_截至上期累计实付_本币
			"lstDeductSumPaidOriAmt",//扣款小计_截至上期累计实付_原币
			"lstDeductSumReqAmt", //扣款小计_截至上期累计申请_本币
			"lstDeductSumReqOriAmt", //扣款小计_截至上期累计申请_原币
			"deductSumAmt",//扣款小计_本期发生_本币
			"deductSumOriAmt",//扣款小计_本期发生_原币
			"allDeductSumReqAmt",//扣款小计_截至本期累计申请_本币
			"allDeductSumReqOriAmt",//扣款小计_截至本期累计申请_原币
			"allDeductSumPaidAmt",//扣款小计_截至本期累计实付_本币
			"allDeductSumPaidOriAmt",//扣款小计_截至本期累计实付_原币

			"lstAMatlAllPaidAmt",//甲供扣款_截至上期累计实付_本币
			"lstAMatlAllPaidOriAmt",//甲供扣款_截至上期累计实付_原币
			"lstAMatlAllReqAmt",//甲供扣款_截至上期累计申请_本币
			"lstAMatlAllReqOriAmt",//甲供扣款_截至上期累计申请_原币
			"payPartAMatlAmt",//甲供扣款_本期发生_本币
			"payPartAMatlOriAmt",//甲供扣款_本期发生_原币
			"allPartAMatlAllPaidAmt", //甲供扣款_截至本期累计实付_本币
			"allPartAMatlAllPaidOriAmt", //甲供扣款_截至本期累计实付_原币
			"payPartAMatlAllReqAmt", //甲供扣款_截至本期累计申请_本币
			"payPartAMatlAllReqOriAmt", //甲供扣款_截至本期累计申请_原币

			"lstRealPaidAmt",//实付款_截至上期累计实付_本币 
			"lstRealPaidOriAmt",//实付款_截至上期累计实付_原币
			"lstRealReqAmt",//实付款_截至上期累计申请_本币
			"lstRealReqOriAmt",//实付款_截至上期累计申请_原币
			"curPaid", //实付款_本期发生_本币
			"curPaidOri", //实付款_本期发生_原币
			"allRealReqAmt",//实付款_截至本期累计申请_本币
			"allRealReqOriAmt",//实付款_截至本期累计申请_原币
			"allRealPaidAmt",//实付款_截至本期累计实付_本币
			"allRealPaidOriAmt",//实付款_截至本期累计实付_原币
			
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
		return o ? "是" : "否";
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
	 * 获取对应合同的汇率
	 * 此方法当时设计时应该考虑到了付款单或付款申请单将来可能采用浮动汇率。
	 * fdcBill.getExchangeRate()这个也应该是取当时合同的汇率吧。
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
	 * 根据汇率本币/汇率=原币。
	 * added by adny_liu 2012-4-13
	 */
	public String getOriValue(BigDecimal bg) {
		return printStringHelper(FDCHelper.divide(bg, getConExRate(fdcBill.getContractBillId()), 4, BigDecimal.ROUND_HALF_UP));
	}

	private void insert(IRowSet drs,DeductOfPayReqBillInfo info,Map deductTypeSum) throws Exception{
		
		/**************************************************头部start**************************************************/
		//合同编号 
		drs.updateString("contractNo", fdcBill.getContractNo());
		//申请日期
		drs.updateString("bookedDate",printStringHelper(payReqInfo.getBookedDate()));
		//付款单编码
		drs.updateString("number",fdcBill.getNumber());
		//用款部门
		drs.updateString("useDepartment.name",fdcBill.getUseDepartment()!=null?fdcBill.getUseDepartment().getName():null);
		//申请期间
		if(payReqInfo.getPeriod()!=null)
		drs.updateString("period",payReqInfo.getPeriod().getPeriodYear()+"年"+payReqInfo.getPeriod().getPeriodNumber()+"期");
		//付款日期
		drs.updateString("payDate",printStringHelper(fdcBill.getPayDate()));
		//日记账余额
		drs.updateString("dayaccount",printStringHelper(fdcBill.getDayaccount()));
		//收付类别
		drs.updateString("feeType.name",fdcBill.getFeeType()!=null?fdcBill.getFeeType().getName():null);
		//收款方省
		drs.updateString("recProvince",fdcBill.getRecProvince());
		//收款方市/县
		drs.updateString("recCity",fdcBill.getRecCity());
		//付款账号
		drs.updateString("payerAccountBank.bankAccountNumber",fdcBill.getPayerAccountBank()!=null?fdcBill.getPayerAccountBank().getBankAccountNumber():null);
		//收款人类型
		drs.updateString("payeeType.name",fdcBill.getPayeeType()!=null?fdcBill.getPayeeType().getName():null);
		//同城异地
		drs.updateString("isDifferPlace",printStringHelper(fdcBill.getIsDifferPlace()));
		//付款银行
		drs.updateString("payerBank.name",fdcBill.getPayerBank()!=null?fdcBill.getPayerBank().getName():null);
		//收款人名称
		drs.updateString("payee",fdcBill.getPayeeName());
		//实际收款单位全称
		drs.updateString("actFdcPayeeName.name",fdcBill.getActFdcPayeeName()!=null?fdcBill.getActFdcPayeeName().getName():null);
		//付款科目
		drs.updateString("payerAccount.name",fdcBill.getPayerAccount()!=null?fdcBill.getPayerAccount().getName():null);
		//收款银行
		drs.updateString("payeeBank",fdcBill.getPayeeBank());
		//币别
		drs.updateString("currency.name",fdcBill.getCurrency()!=null?fdcBill.getCurrency().getName():null);
		//业务种类
		drs.updateString("bizType.name",fdcBill.getBizType()!=null?fdcBill.getBizType().getName():null);
		//收款账户
		drs.updateString("payeeAccountBank",fdcBill.getPayeeAccountBank());
		//汇率
		drs.updateString("exchangeRate",printStringHelper(fdcBill.getExchangeRate()));
		//是否加急
		drs.updateString("isEmergency",printStringHelper(fdcBill.getIsEmergency()));
		//原币金额
		drs.updateString("amount",printStringHelper(fdcBill.getAmount()));
		//附件数
		drs.updateString("accessoryAmt",String.valueOf(fdcBill.getAccessoryAmt()));
		//本币金额
		drs.updateString("localAmt",printStringHelper(fdcBill.getLocalAmt()));
		//大写金额
		drs.updateString("capitalAmount",fdcBill.getCapitalAmount());
		//进度款比例(%)
		drs.updateString("paymentProportion",printStringHelper(payReqInfo.getPaymentProportion()));
		//摘要
		drs.updateString("description",fdcBill.getDescription());
		//本期成本金额
		drs.updateString("completePrjAmt",printStringHelper(payReqInfo.getCompletePrjAmt()));
		//用途
		drs.updateString("usage",fdcBill.getUsage());
		//计划项目
		drs.updateString("fpItem.name",fdcBill.getFpItem()!=null?fdcBill.getFpItem().getName():null);
		//打回意见
		drs.updateString("conceit",fdcBill.getConceit());
		//备注
		drs.updateString("summary",fdcBill.getSummary());
		//结算方式
		drs.updateString("settlementType.name",fdcBill.getSettlementType()!=null?fdcBill.getSettlementType().getName():null);
		//结算号
		drs.updateString("settlementNumber",fdcBill.getSettlementNumber());
		//制单人
		drs.updateString("creator.number",fdcBill.getCreator()!=null?fdcBill.getCreator().getNumber():null);
		//制单人
		drs.updateString("creator.name",fdcBill.getCreator()!=null?fdcBill.getCreator().getName():null);
		//制单时间
		drs.updateString("createTime",printStringHelper(fdcBill.getCreateTime()));
		//审核人
		drs.updateString("auditor.number",fdcBill.getAuditor()!=null?fdcBill.getAuditor().getNumber():null);
		//审核人
		drs.updateString("auditor.name",fdcBill.getAuditor()!=null?fdcBill.getAuditor().getName():null);
		//审核日期
		drs.updateString("auditDate",printStringHelper(fdcBill.getAuditDate()));
		//会计编号
		drs.updateString("accountant.number",fdcBill.getAccountant()!=null?fdcBill.getAccountant().getNumber():null);
		//会计名称
		drs.updateString("accountant.name",fdcBill.getAccountant()!=null?fdcBill.getAccountant().getName():null);
		//出纳编号
		drs.updateString("cashier.number",fdcBill.getCashier()!=null?fdcBill.getCashier().getNumber():null);
		//出纳名称
		drs.updateString("cashier.number",fdcBill.getCashier()!=null?fdcBill.getCashier().getNumber():null);
		/**************************************************头部start**************************************************/
		
		
		
		/**************************************************工程付款情况表start**************************************************/
		BigDecimal sumLstDeductPaid = (BigDecimal)deductTypeSum.get("sumLstDeductPaid");
		BigDecimal sumLstDeductReq =  (BigDecimal)deductTypeSum.get("sumLstDeductReq");
		BigDecimal sumDeduct =  (BigDecimal)deductTypeSum.get("sumDeduct");
		BigDecimal sumAllDeductReq =  (BigDecimal)deductTypeSum.get("sumAllDeductReq");
		BigDecimal sumAllDeductPaid = (BigDecimal)deductTypeSum.get("sumAllDeductPaid");
		
//		 在此把数据传递给实现类，drs.updateString(key,value) key
		// 指的是套打模板中定义的字段编码，Value指的是当前单据的属性值
		String orgName=((CurProjectInfo)fdcBill.getCurProject()).getFullOrgUnit().getName();
		String curProjectName = curProjectInfo.getDisplayName();		
		//取出的数据要求只取项目名称
		//2008-07-22
		String projNameWithoutOrg = curProjectName.replace('_', '\\');
		curProjectName = orgName+"\\"+curProjectName.replace('_', '\\');
		drs.updateString("curProject.displayName", curProjectName);
		drs.updateString("projNameWithoutOrg", projNameWithoutOrg);
		drs.updateString("contractName",payReqInfo.getContractName());
		//合同造价本币
		drs.updateString("contractPrice", printStringHelper(payReqInfo.getContractPrice()));
		//合同造价原币
		drs.updateString("contractOriPrice", getOriValue(payReqInfo.getContractPrice()));
		//最新造价本币
		drs.updateString("latestPrice",printStringHelper(fdcBill.getLatestPrice()));
		//最新造价原币
		drs.updateString("latestOriPrice", getOriValue(fdcBill.getLatestPrice()));
		//变更指令金额本币
		drs.updateString("changeAmt",printStringHelper(fdcBill.getChangeAmt()));
		//变更指令金额原币
		drs.updateString("changeOriAmt", getOriValue(fdcBill.getChangeAmt()));
		//本申请单已付本币
		drs.updateString("payedAmt", printStringHelper(fdcBill.getPayedAmt()));
		//本申请单已付原币
		drs.updateString("payedOriAmt", getOriValue(fdcBill.getPayedAmt()));
		
		drs.updateString("settleAmt",printStringHelper(fdcBill.getSettleAmt()));
		drs.updateString("payTimes", String.valueOf(fdcBill.getPayTimes()));
		
		//合同内工程款_截至上期累计实付_本币
		drs.updateString("lstPrjAllPaidAmt", printStringHelper(fdcBill.getLstPrjAllPaidAmt()));
		//合同内工程款_截至上期累计实付_原币
		drs.updateString("lstPrjAllPaidOriAmt", getOriValue(fdcBill.getLstPrjAllPaidAmt()));
		//合同内工程款_截至上期累计申请_本币
		drs.updateString("lstPrjAllReqAmt", printStringHelper(fdcBill.getLstPrjAllReqAmt()));
		//合同内工程款_截至上期累计申请_原币
		drs.updateString("lstPrjAllReqOriAmt", getOriValue(fdcBill.getLstPrjAllReqAmt()));
		//合同内工程款_本期发生_本币
		drs.updateString("projectPriceInContract", printStringHelper(fdcBill.getProjectPriceInContract()));
		//合同内工程款_本期发生_原币
		drs.updateString("projectPriceInContractOri", getOriValue(fdcBill.getProjectPriceInContract()));
		//合同内工程款_截至本期累计申请_本币
		drs.updateString("prjAllReqAmt", printStringHelper(fdcBill.getPrjAllReqAmt()));
		//合同内工程款_截至本期累计申请_原币
		drs.updateString("prjAllReqOriAmt", getOriValue(fdcBill.getPrjAllReqAmt()));
		//合同内工程款_截至本期累计实付_本币= 截至上期累计累计实付 +本期
		BigDecimal prjAllPaidAmt = (fdcBill.getLstPrjAllPaidAmt()!=null?fdcBill.getLstPrjAllPaidAmt():FDCHelper.ZERO);
		prjAllPaidAmt = FDCHelper.add(prjAllPaidAmt,fdcBill.getProjectPriceInContract());
		drs.updateString("prjAllPaidAmt", printStringHelper(prjAllPaidAmt));
		//合同内工程款_截至本期累计实付_原币
		drs.updateString("prjAllPaidOriAmt", getOriValue(prjAllPaidAmt));
		
		//奖励_截至上期累计实付_本币
		BigDecimal lstGuerdonPaidAmt = FDCHelper.toBigDecimal(((ICell)( bindCellMap.get("lstGuerdonPaidAmt"))).getValue());				
		drs.updateString("lstGuerdonPaidAmt",printStringHelper(lstGuerdonPaidAmt));
		//奖励_截至上期累计实付_原币
		drs.updateString("lstGuerdonPaidOriAmt", getOriValue(lstGuerdonPaidAmt));
		//奖励_截至上期累计申请_本币
		BigDecimal lstGuerdonReqAmt = FDCHelper.toBigDecimal(( (ICell)( bindCellMap.get("lstGuerdonReqAmt"))).getValue());
		drs.updateString("lstGuerdonReqAmt", printStringHelper(lstGuerdonReqAmt));
		//奖励_截至上期累计申请_原币
		drs.updateString("lstGuerdonReqOriAmt", getOriValue(lstGuerdonReqAmt));
		//奖励_本期发生_本币				
		BigDecimal guerdonAmt = FDCHelper.toBigDecimal(((ICell)( bindCellMap.get("guerdonAmt"))).getValue());
		guerdonAmt = guerdonAmt!= null?guerdonAmt: FDCHelper.ZERO;
		drs.updateString("guerdonAmt", printStringHelper(guerdonAmt));
		//奖励_本期发生_原币
		drs.updateString("guerdonOriAmt", getOriValue(guerdonAmt));
		//奖励_截至本期累计申请_本币 = 奖励 截至上期累计申请 + 奖励 本期发生
		BigDecimal allGuerdonReqAmt = guerdonAmt.add(lstGuerdonReqAmt != null ? lstGuerdonReqAmt : FDCHelper.ZERO);
		drs.updateString("allGuerdonReqAmt", printStringHelper(allGuerdonReqAmt));
		//奖励_截至本期累计申请_原币
		drs.updateString("allGuerdonReqOriAmt", getOriValue(allGuerdonReqAmt));
		//奖励_截至本期累计实付_本币 = 奖励 截至上期累计实付 + 本期
		BigDecimal allGuerdonPaidAmt = guerdonAmt.add(lstGuerdonPaidAmt!= null ?lstGuerdonPaidAmt: FDCHelper.ZERO);
		drs.updateString("allGuerdonPaidAmt",printStringHelper(allGuerdonPaidAmt));
		//奖励_截至本期累计实付_原币
		drs.updateString("allGuerdonPaidOriAmt", getOriValue(allGuerdonPaidAmt));
		
		//违约_截至上期累计实付_本币
		BigDecimal lstCompensationPaidAmt = FDCHelper.toBigDecimal(( (ICell)( bindCellMap.get("lstCompensationPaidAmt"))).getValue());
		drs.updateString("lstCompensationPaidAmt",printStringHelper(lstCompensationPaidAmt));
		//违约_截至上期累计实付_原币
		drs.updateString("lstCompensationPaidOriAmt", getOriValue(lstCompensationPaidAmt));
		//违约_截至上期累计申请_本币
		BigDecimal lstCompensationReqAmt = FDCHelper.toBigDecimal(( (ICell)( bindCellMap.get("lstCompensationReqAmt"))).getValue());
		drs.updateString("lstCompensationReqAmt", printStringHelper(lstCompensationReqAmt));
		//违约_截至上期累计申请_原币
		drs.updateString("lstCompensationReqOriAmt", getOriValue(lstCompensationReqAmt));
		//违约_本期发生_本币
		BigDecimal compensationAmt = FDCHelper.toBigDecimal(( (ICell)( bindCellMap.get("compensationAmt"))).getValue());
		compensationAmt = compensationAmt != null? compensationAmt: FDCHelper.ZERO;
		drs.updateString("compensationAmt", printStringHelper(compensationAmt));
		//违约_本期发生_原币
		drs.updateString("compensationOriAmt", getOriValue(compensationAmt));
		//违约_截至本期累计申请_本币= 违约 截至上期累计申请 + 违约 本期发生					
		BigDecimal allCompensationReqAmt = compensationAmt.add(lstCompensationReqAmt != null ? lstCompensationReqAmt : FDCHelper.ZERO);
		drs.updateString("allCompensationReqAmt", printStringHelper(allCompensationReqAmt));
		//违约_截至本期累计申请_原币
		drs.updateString("allCompensationReqOriAmt", getOriValue(allCompensationReqAmt));
		//违约_截至本期累计实付_本币= 违约 截至上期累计实付 +本期	
		BigDecimal allCompensationPaidAmt = compensationAmt.add(lstCompensationPaidAmt != null ? lstCompensationPaidAmt : FDCHelper.ZERO);		
		drs.updateString("allCompensationPaidAmt",printStringHelper(allCompensationPaidAmt));
		//违约_截至本期累计实付_原币
		drs.updateString("allCompensationPaidOriAmt", getOriValue(allCompensationPaidAmt));
		//扣款项
		//扣款类型				
		if(info!=null){
			drs.updateString("deductType",info==null?"":info.getDeductType().getName());
			BigDecimal lstDeductPaidAmt = info.getAllPaidAmt()==null?FDCHelper.ZERO:info.getAllPaidAmt();
			BigDecimal lstDeductReqAmt  = info.getAllReqAmt()==null?FDCHelper.ZERO:info.getAllReqAmt();
			BigDecimal deductAmt        = info.getAmount()==null?FDCHelper.ZERO:info.getAmount();
			BigDecimal allDeductReqAmt  = lstDeductReqAmt.add(deductAmt);
			BigDecimal allDeductPaidAmt  = lstDeductPaidAmt.add(deductAmt);
			
			//扣款_截至上期累计实付_本币
			drs.updateString("lstDeductPaidAmt",printStringHelper(lstDeductPaidAmt));
			//扣款_截至上期累计实付_原币
			drs.updateString("lstDeductPaidOriAmt", getOriValue(lstDeductPaidAmt));
			//扣款_截至上期累计申请_本币
			drs.updateString("lstDeductReqAmt",printStringHelper(lstDeductReqAmt));
			//扣款_截至上期累计申请_原币
			drs.updateString("lstDeductReqOriAmt", getOriValue(lstDeductReqAmt));
			//扣款_本期发生_本币
			drs.updateString("deductAmt",printStringHelper(deductAmt));
			//扣款_本期发生_原币
			drs.updateString("deductOriAmt", getOriValue(deductAmt));
			//扣款_截至本期累计申请_本币
			drs.updateString("allDeductReqAmt",printStringHelper(allDeductReqAmt));
			//扣款_截至本期累计申请_原币
			drs.updateString("allDeductReqOriAmt", getOriValue(allDeductReqAmt));
			//扣款_截至本期累计实付_本币
			drs.updateString("allDeductPaidAmt", printStringHelper(allDeductPaidAmt));
			//扣款_截至本期累计实付_原币
			drs.updateString("allDeductPaidOriAmt", getOriValue(allDeductPaidAmt));
			
			//扣款小计
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
							
		//扣款小计_截至上期累计实付_本币
		drs.updateString("lstDeductSumPaidAmt", printStringHelper(sumLstDeductPaid));
		//扣款小计_截至上期累计实付_原币
		drs.updateString("lstDeductSumPaidOriAmt", getOriValue(sumLstDeductPaid));
		//扣款小计_截至上期累计申请_本币
		drs.updateString("lstDeductSumReqAmt", printStringHelper(sumLstDeductReq));
		//扣款小计_截至上期累计申请_原币
		drs.updateString("lstDeductSumReqOriAmt", getOriValue(sumLstDeductReq));
		//扣款小计_本期发生_本币
		drs.updateString("deductSumAmt", printStringHelper(sumDeduct));
		//扣款小计_本期发生_原币
		drs.updateString("deductSumOriAmt", getOriValue(sumDeduct));
		//扣款小计_截至本期累计申请_本币
		drs.updateString("allDeductSumReqAmt", printStringHelper(sumAllDeductReq));
		//扣款小计_截至本期累计申请_原币
		drs.updateString("allDeductSumReqOriAmt", getOriValue(sumAllDeductReq));
		//扣款小计_截至本期累计实付_本币
		drs.updateString("allDeductSumPaidAmt", printStringHelper(sumAllDeductPaid));
		//扣款小计_截至本期累计实付_原币
		drs.updateString("allDeductSumPaidOriAmt", getOriValue(sumAllDeductPaid));

		//甲供扣款_截至上期累计实付_本币
		drs.updateString("lstAMatlAllPaidAmt", printStringHelper(fdcBill.getLstAMatlAllPaidAmt()));
		//甲供扣款_截至上期累计实付_原币
		drs.updateString("lstAMatlAllPaidOriAmt", getOriValue(fdcBill.getLstAMatlAllPaidAmt()));
		//甲供扣款_截至上期累计申请_本币
		drs.updateString("lstAMatlAllReqAmt", printStringHelper(fdcBill.getLstAMatlAllReqAmt()));
		//甲供扣款_截至上期累计申请_原币
		drs.updateString("lstAMatlAllReqOriAmt", getOriValue(fdcBill.getLstAMatlAllReqAmt()));
		//甲供扣款_本期发生_本币
		drs.updateString("payPartAMatlAmt", printStringHelper(fdcBill.getPayPartAMatlAmt()));
		//甲供扣款_本期发生_原币
		drs.updateString("payPartAMatlOriAmt", getOriValue(fdcBill.getPayPartAMatlAmt()));
		//甲供扣款_截至本期累计申请_本币
		drs.updateString("payPartAMatlAllReqAmt", printStringHelper(fdcBill.getPayPartAMatlAllReqAmt()));
		//甲供扣款_截至本期累计申请_原币
		drs.updateString("payPartAMatlAllReqOriAmt", getOriValue(fdcBill.getPayPartAMatlAllReqAmt()));
		//甲供扣款_截至本期累计实付_本币 = 甲供材 截至上期累计实付 +本期
		BigDecimal allPartAMatlAllPaidAmt = (fdcBill.getLstAMatlAllPaidAmt() != null ? fdcBill.getLstAMatlAllPaidAmt() : FDCHelper.ZERO);
		allPartAMatlAllPaidAmt = FDCHelper.add(allPartAMatlAllPaidAmt,fdcBill.getPayPartAMatlAmt());
		drs.updateString("allPartAMatlAllPaidAmt",printStringHelper(allPartAMatlAllPaidAmt));
		//甲供扣款_截至本期累计实付_原币
		drs.updateString("allPartAMatlAllPaidOriAmt", getOriValue(allPartAMatlAllPaidAmt));
		

		
		//实付款_截至上期累计实付_本币 = 合同工程款 + 奖励 - 违约金 - 扣款小计 - 甲供材
		BigDecimal lstRealPaidAmt = FDCHelper.toBigDecimal(fdcBill.getLstPrjAllPaidAmt());
		lstRealPaidAmt = lstRealPaidAmt.add(lstGuerdonPaidAmt)//奖励
				.subtract(lstCompensationPaidAmt)//违约
				.subtract(sumLstDeductPaid)//扣款
				.subtract(fdcBill.getLstAMatlAllPaidAmt() != null ? fdcBill
								.getLstAMatlAllPaidAmt()
								: FDCHelper.ZERO);//甲供材
		drs.updateString("lstRealPaidAmt",printStringHelper(lstRealPaidAmt));
		//实付款_截至上期累计实付_原币
		drs.updateString("lstRealPaidOriAmt", getOriValue(lstRealPaidAmt));
		
		//实付款_截至上期累计申请_本币 = 合同工程款 + 奖励 - 违约金 - 扣款小计 - 甲供材
		BigDecimal lstRealReqAmt = FDCHelper.toBigDecimal(fdcBill.getLstPrjAllReqAmt());
		lstRealReqAmt = lstRealReqAmt.add(lstGuerdonReqAmt)//奖励
				.subtract(lstCompensationReqAmt)//违约
				.subtract(sumLstDeductReq)//扣款
				.subtract(fdcBill.getLstAMatlAllReqAmt() != null ? fdcBill
								.getLstAMatlAllReqAmt()
								: FDCHelper.ZERO);//甲供材
		drs.updateString("lstRealReqAmt",printStringHelper(lstRealReqAmt));
		//实付款_截至上期累计申请_原币
		drs.updateString("lstRealReqOriAmt", getOriValue(lstRealReqAmt));
		
		//实付款_本期发生_本币 = 合同工程款 + 奖励 - 违约金 - 扣款小计 - 甲供材
		BigDecimal projectPriceInContract = fdcBill.getProjectPriceInContract() != null ? fdcBill.getProjectPriceInContract()
				: FDCHelper.ZERO;
		//		BigDecimal tempCurPaid = fdcBill.getCurPaid()!= null ? fdcBill.getCurPaid(): FDCHelper.ZERO;
		BigDecimal tempCurPaid = projectPriceInContract.add(guerdonAmt)//奖励
				.subtract(compensationAmt)//违约
				.subtract(sumDeduct)//扣款
				.subtract(fdcBill.getPayPartAMatlAmt() != null ? fdcBill.getPayPartAMatlAmt() : FDCHelper.ZERO);//甲供材
		drs.updateString("curPaid", printStringHelper(tempCurPaid));
		//实付款_本期发生_原币
		drs.updateString("curPaidOri", getOriValue(tempCurPaid));
		
		//实付款_截至本期累计申请_本币 = 合同工程款 + 奖励 - 违约金 - 扣款小计 - 甲供材
		BigDecimal prjAllReqAmt = fdcBill.getPrjAllReqAmt()!=null?fdcBill.getPrjAllReqAmt():FDCHelper.ZERO;
		BigDecimal allRealReqAmt = prjAllReqAmt.add(allGuerdonReqAmt)//奖励
				.subtract(	allCompensationReqAmt)//违约
				.subtract(sumAllDeductReq)//扣款
				.subtract(fdcBill.getPayPartAMatlAllReqAmt() != null ? fdcBill
								.getPayPartAMatlAllReqAmt()
								: FDCHelper.ZERO);//甲供材
		drs.updateString("allRealReqAmt",printStringHelper(allRealReqAmt));
		//实付款_截至本期累计申请_原币
		drs.updateString("allRealReqOriAmt", getOriValue(allRealReqAmt));
		
		//实付款_截至本期累计实付_本币 = 合同工程款 + 奖励 - 违约金 - 扣款小计 - 甲供材
//		BigDecimal prjAllReqAmt =  fdcBill.getPrjAllReqAmt();
		BigDecimal allRealPaidAmt = prjAllReqAmt
				.add(allGuerdonPaidAmt)//奖励
				.subtract(allCompensationPaidAmt)//违约
				.subtract(sumAllDeductPaid)//扣款
				.subtract(allPartAMatlAllPaidAmt);//甲供材
		drs.updateString("allRealPaidAmt",printStringHelper(allRealPaidAmt));
		//实付款_截至本期累计实付_原币
		drs.updateString("allRealPaidOriAmt", getOriValue(allRealPaidAmt));
		
		// 余款 = 最新造价 - 合同内付款 截至本期累计实付
		BigDecimal balance = (fdcBill
		.getLatestPrice() == null ? FDCHelper.ZERO : fdcBill.getLatestPrice())				
		.subtract(prjAllPaidAmt);//合同内付款 截至本期累计实付
		
		drs.updateString("balance",printStringHelper(balance));
		// 本次申请% = 实付款 本期发生 / 最新造价
		BigDecimal tempCurReqPercent = fdcBill.getLatestPrice() == null
				|| fdcBill.getLatestPrice().compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO
				: tempCurPaid.divide(fdcBill.getLatestPrice(), 4,
						BigDecimal.ROUND_HALF_UP);
//		BigDecimal tempCurReqPercent = fdcBill.getCurReqPercent()!=null?
//				fdcBill.getCurReqPercent():FDCHelper.ZERO;
		drs.updateString("curReqPercent",
				printStringHelper((tempCurReqPercent.multiply(FDCConstants.ONE_HUNDRED)).setScale(2)));
		
		// 累计申请% = 实付款 截至本期累计申请 / 最新造价
		BigDecimal tempAllCurReqPercent = fdcBill.getLatestPrice() == null
				|| fdcBill.getLatestPrice().compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO
				: allRealReqAmt.divide(fdcBill.getLatestPrice(), 4,
						BigDecimal.ROUND_HALF_UP);
		
		drs.updateString("allReqPercent",
				printStringHelper((tempAllCurReqPercent.multiply(FDCConstants.ONE_HUNDRED)).setScale(2)));
		// ///
		drs.updateString("imageSchedule", printStringHelper(fdcBill
				.getImageSchedule()));
		/**************************************************工程付款情况表end**************************************************/
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
			
			//取扣款类型
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
