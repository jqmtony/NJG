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
	 * 合同对应的汇率
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
			"contractBillAmount",//合同原币金额
			"contractBillType",//合同类型
			"totalSettlePrice",//已实现产值
			"allCompletePrjAmt",
			"LstPrjAllPaidAmtAndthis",
			"latestPriceSub",

			"grtOriAmount",//保修金原币
			
			"settleOriAmt",//结算金额本币
			
			"thisTimeReqLocalAmt",//本期申请本币金额
			"process",//形象进度描述
			"invoiceNumber", //发票号     
			"invoiceDate", //开票日  
			"invoiceAmt", //发票金额本币   
			"allInvoiceAmt", //累计发票本币
			"conGrtAmount",//合同保修金
			"conGrtOriAmount",//合同保修金原币
			"conGrtRate",//合同保修金比例
			"invoiceOriAmt",//发票金额原币
			"allInvoiceOriAmt",//累计发票原币			
			"contractPrice",//合同造价本币
			"contractOriPrice",//合同造价原币
			"changeAmt",//变更指令金额本币 
			"changeOriAmt",//变更指令金额原币
			"latestPrice", //最新造价本币
			"latestOriPrice",//最新造价原币
			"payedAmt",//本申请单已付本币
			"payedOriAmt",//本申请单已付原币
			"prjAllReqAmt",//合同内工程款_截至本期累计申请_本币
			"prjAllReqOriAmt", //合同内工程款_截至本期累计申请_原币
			"prjAllPaidAmt",//合同内工程款_截至本期累计实付_本币
			"prjAllPaidOriAmt",//合同内工程款_截至本期累计实付_原币
			"projectPriceInContract",//合同内工程款_本期发生_本币
			"projectPriceInContractOri",//合同内工程款_本期发生_原币
			"lstPrjAllPaidAmt", //合同内工程款_截至上期累计实付_本币
			"lstPrjAllPaidOriAmt",//合同内工程款_截至上期累计实付_原币
			"lstPrjAllReqAmt",//合同内工程款_截至上期累计申请_本币
			"lstPrjAllReqOriAmt",//合同内工程款_截至上期累计申请_原币
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
			"allPayableReqPercent",// 累计应付申请
			"depPlanState",// 计划付款状态
			
			// //////////////////////////////////////////////////////////////////////////
			// 付款申请单：增加拓展字段，用于客户自定义 by skyiter_wang 2014-12-04
			// 说明：
			// 1、付款申请单的套打比较特殊
			// 2、在代码中做了处理，写死了一些查询字段，导致DEP拓展的字段无法正常展示
			// 3、所以预定义5个拓展字段，方便客户自定义
			// 4、参见PayRequestBillEditUI.actionPrintPreview_actionPerformed，PayRequestBillRowsetProvider
			
			"extField01",// 拓展字段01
			"extField02",// 拓展字段02
			"extField03",// 拓展字段03
			"extField04",// 拓展字段04
			"extField05",// 拓展字段05

			"extBooleanField01",// 拓展布尔字段01
			"extBooleanField02",// 拓展布尔字段02

			"extEnumField01",// 拓展枚举字段01
			"extEnumField02"// 拓展枚举字段02

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
		return Boolean.TRUE.equals(o) ? "是" : "否";
	}

	public static String printBooleanHelper(boolean o) {
		return o ? "是" : "否";
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
		initAllCompletePrjAmt();//初始化时一次性赋值
	}

	//	/**
	//	 * 这个私有方法是否过度考虑？原币可以从本币/汇率得来。
	//	 * 本类只为付款申请单提供套打数据源。
	//	 * 难道有只存在原币，没有本币的现象？那不就是缺陷了。。。
	//	 * 暂时保留，如果测试不出问题就删掉。
	//	 * 获取合同造价原币
	//	 */
	//	private BigDecimal getContractOriAmt(String contractId) {
	//		//合同造价原币
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
	 * 这个私有方法是否过度考虑？原币可以从本币/汇率得来。
	 * 本类只为付款申请单提供套打数据源。
	 * 难道有只存在原币，没有本币的现象？那不就是缺陷了。。。
	 * 暂时保留，如果测试不出问题就删掉。
	 * 获取变更签证确认原币
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
				//变更金额累计=未结算变更+已结算变更
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
	//	 * 这个私有方法是否过度考虑？原币可以从本币/汇率得来。
	//	 * 本类只为付款申请单提供套打数据源。
	//	 * 难道有只存在原币，没有本币的现象？那不就是缺陷了。。。
	//	 * 暂时保留，如果测试不出问题就删掉。
	//	 * 获取奖励_本期发生_原币
	//	 */
	//	private BigDecimal getGuerdonOriAmt(String contractId){
	//		BigDecimal guerdonBillOriAmt=FDCHelper.ZERO;
	//		EntityViewInfo view=new EntityViewInfo();
	//		view.getSelector().add("originalAmount");
	//		FilterInfo filter=new FilterInfo();
	//		filter.getFilterItems().add(new FilterItemInfo("payRequestBill.id",fdcBill.getId().toString()));
	//		/**
	//		 * 应该取本次申请原币金额，非某一合同下累计申请的原币金额 by Cassiel_peng 2009-10-21
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
	//	 * 这个私有方法是否过度考虑？原币可以从本币/汇率得来。
	//	 * 本类只为付款申请单提供套打数据源。
	//	 * 难道有只存在原币，没有本币的现象？那不就是缺陷了。。。
	//	 * 暂时保留，如果测试不出问题就删掉。
	//	 * 获取违约_本期发生_原币
	//	 */
	//	private BigDecimal getCompensationOriAmt(String contractId){
	//		BigDecimal compensationBillOriAmt=FDCHelper.ZERO;
	//		EntityViewInfo view=new EntityViewInfo();
	//		view.getSelector().add("originalAmount");
	//		FilterInfo filter=new FilterInfo();
	//		filter.getFilterItems().add(new FilterItemInfo("payRequestBill.id",fdcBill.getId().toString()));
	//		/**
	//		 * 应该取本次申请原币金额，非某一合同下累计申请的原币金额 by Cassiel_peng 2009-10-21
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
	//	 * 这个私有方法是否过度考虑？原币可以从本币/汇率得来。
	//	 * 本类只为付款申请单提供套打数据源。
	//	 * 难道有只存在原币，没有本币的现象？那不就是缺陷了。。。
	//	 * 暂时保留，如果测试不出问题就删掉。
	//	 * 获取扣款_本期发生_原币
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
	 * 根据 汇率本币/汇率=原币。
	 * added by adny_liu 2012-4-10
	 */
	public String getOriValue(BigDecimal bg) {
		return printStringHelper(FDCHelper.divide(bg, getConExRate(fdcBill.getContractId()), 4, BigDecimal.ROUND_HALF_UP));
	}

	/**
	 * 为付款申请单套打插入数据源。因为工程付款情况表类似excel，用套打query不方便处理。
	 * added by adny_liu 2012-4-10
	 */
	private void insert(DynamicRowSet drs,DeductOfPayReqBillInfo info,Map deductTypeSum) throws Exception{
	
		BigDecimal sumLstDeductPaid = (BigDecimal)deductTypeSum.get("sumLstDeductPaid");
		BigDecimal sumLstDeductReq =  (BigDecimal)deductTypeSum.get("sumLstDeductReq");
		BigDecimal sumDeduct =  (BigDecimal)deductTypeSum.get("sumDeduct");
		BigDecimal sumAllDeductReq =  (BigDecimal)deductTypeSum.get("sumAllDeductReq");
		BigDecimal sumAllDeductPaid = (BigDecimal)deductTypeSum.get("sumAllDeductPaid");
		
		// 在此把数据传递给实现类，drs.updateString(key,value) key
		// 指的是套打模板中定义的字段编码，Value指的是当前单据的属性值
		String orgName=((CurProjectInfo)fdcBill.getCurProject()).getFullOrgUnit().getName();
		String curProjectName = curProjectInfo.getDisplayName();		
		//取出的数据要求只取项目名称
		String projNameWithoutOrg = curProjectName.replace('_', '\\');
		curProjectName = orgName+"\\"+curProjectName.replace('_', '\\');
		
		//发票号
		drs.updateString("invoiceNumber", printStringHelper(fdcBill.getInvoiceNumber()));
		//开票日
		drs.updateString("invoiceDate", printStringHelper(fdcBill.getInvoiceDate()));
		//发票金额本币  
		drs.updateString("invoiceAmt", printStringHelper(fdcBill.getInvoiceAmt()));
		//发票金额原币
		drs.updateString("invoiceOriAmt", getOriValue(fdcBill.getInvoiceAmt()));
		//累计发票本币
		drs.updateString("allInvoiceAmt", printStringHelper(fdcBill.getAllInvoiceAmt()));
		//累计发票原币
		drs.updateString("allInvoiceOriAmt", getOriValue(fdcBill.getAllInvoiceAmt()));
		drs.updateString("orgName",orgName);
		
		drs.updateString("curProject.name", curProjectName);
		drs.updateString("projNameWithoutOrg", projNameWithoutOrg);
		drs.updateString("curProject.number", fdcBill.getCurProject().getNumber());
		drs.updateString("useDepartment.name", fdcBill.getUseDepartment().getName());
		drs.updateString("useDepartment.number", fdcBill.getUseDepartment().getNumber());
		drs.updateString("contractNo", printStringHelper(fdcBill.getContractNo()));
		drs.updateString("bookedDate", printStringHelper(fdcBill.getBookedDate()));// 业务日期
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
		//合同保修金额
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
		//合同造价本币
		drs.updateString("contractPrice", printStringHelper(fdcBill.getContractPrice()));
		//合同造价原币
		drs.updateString("contractOriPrice", getOriValue(fdcBill.getContractPrice()));
		//drs.updateString("contractOriPrice", printStringHelper(getContractOriAmt(contractId)));
		//最新造价本币
		drs.updateString("latestPrice", printStringHelper(fdcBill.getLatestPrice()));
		//最新造价原币
		drs.updateString("latestOriPrice", getOriValue(fdcBill.getLatestPrice()));
		
		//变更指令金额本币
		drs.updateString("changeAmt", printStringHelper(((ICell) bindCellMap.get(PaymentBillContants.CHANGEAMT)).getValue()));
		//变更指令金额原币 
		drs.updateString("changeOriAmt", getOriValue((BigDecimal) ((ICell) bindCellMap.get(PaymentBillContants.CHANGEAMT)).getValue()));
		//	drs.updateString("changeOriAmt", printStringHelper(getChangeOriAmt(fdcBill.getContractId())));
		//本申请单已付本币
		drs.updateString("payedAmt", printStringHelper(fdcBill.getPayedAmt()));
		//本申请单已付原币
		drs.updateString("payedOriAmt", getOriValue(fdcBill.getPayedAmt()));
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
		//合同内工程款_本期发生_原币 old
		//drs.updateString("projectPriceInContractOri", printStringHelper(fdcBill.getProjectPriceInContractOri()));
		//合同内工程款_本期发生_原币
		drs.updateString("projectPriceInContractOri", getOriValue(fdcBill.getProjectPriceInContract()));
		//合同内工程款_截至本期累计申请_本币
		drs.updateString("prjAllReqAmt", printStringHelper(fdcBill.getPrjAllReqAmt()));
		//合同内工程款_截至本期累计申请_原币
		drs.updateString("prjAllReqOriAmt", getOriValue(fdcBill.getPrjAllReqAmt()));
		
		//合同内工程款_截至本期累计实付_本币   = 截至上期累计累计实付 PS:付款申请单都是类似的逻辑。
		/* modified by zhaoqin for R140401-0072 on 2014/04/25 start */
		//BigDecimal prjAllPaidAmt = (fdcBill.getLstPrjAllPaidAmt() != null ? fdcBill.getLstPrjAllPaidAmt() : FDCHelper.ZERO);
		BigDecimal prjAllPaidAmt = (fdcBill.getBigDecimal("prjAllPaidAmt") != null ? fdcBill.getBigDecimal("prjAllPaidAmt") : FDCHelper.ZERO);
		/* modified by zhaoqin for R140401-0072 on 2014/04/25 end */
		
		/* modified by zhaoqin for R131202-0278 start */
		drs.updateString("prjAllPaidAmt", printStringHelper(fdcBill.getBigDecimal("prjAllPaidAmt")));
		//drs.updateString("prjAllPaidAmt", printStringHelper(prjAllPaidAmt));
		//合同内工程款_截至本期累计实付_原币
		//drs.updateString("prjAllPaidOriAmt", getOriValue(prjAllPaidAmt));
		drs.updateString("prjAllPaidOriAmt", getOriValue(fdcBill.getBigDecimal("prjAllPaidOriAmt")));
		/* modified by zhaoqin for R131202-0278 end */
		
		drs.updateString("scheduleAmt", printStringHelper(fdcBill.getScheduleAmt()));
		drs.updateString("curPlannedPayment", printStringHelper(fdcBill.getCurPlannedPayment()));
		drs.updateString("curBackPay", printStringHelper(fdcBill.getCurBackPay()));
		drs.updateString("paymentPlan", printStringHelper(fdcBill.getPaymentPlan()));
		//付款次数
		drs.updateString("payTimes", String.valueOf(fdcBill.getPayTimes()));		
		drs.updateString("contractId", fdcBill.getContractId());
		drs.updateString("hasPayoff", printBooleanHelper(fdcBill.isHasPayoff()));		
		
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
		//甲供扣款_截至本期累计实付_本币 = 甲供扣款_截至上期累计实付_本币
		BigDecimal allPartAMatlAllPaidAmt = (fdcBill.getLstAMatlAllPaidAmt() != null ? fdcBill.getLstAMatlAllPaidAmt() : FDCHelper.ZERO);
		drs.updateString("allPartAMatlAllPaidAmt", printStringHelper(allPartAMatlAllPaidAmt));
		//甲供扣款_截至本期累计实付_原币
		drs.updateString("allPartAMatlAllPaidOriAmt", getOriValue(allPartAMatlAllPaidAmt));
		drs.updateString("costAmount", fdcBill.getBoolean("isCompletePrjAmtVisible") ? printStringHelper(fdcBill.getCompletePrjAmt()) : "");
		drs.updateString("settleAmt", printStringHelper(fdcBill.getSettleAmt()));

		//奖励_截至上期累计实付_本币
		BigDecimal lstGuerdonPaidAmt = (BigDecimal) ( (ICell)( bindCellMap.get("lstGuerdonPaidAmt"))).getValue();				
		drs.updateString("lstGuerdonPaidAmt",printStringHelper(lstGuerdonPaidAmt));
		//奖励_截至上期累计实付_原币
		drs.updateString("lstGuerdonPaidOriAmt", getOriValue(lstGuerdonPaidAmt));
		//奖励_截至上期累计申请_本币
		BigDecimal lstGuerdonReqAmt = (BigDecimal) ( (ICell)( bindCellMap.get("lstGuerdonReqAmt"))).getValue();
		drs.updateString("lstGuerdonReqAmt", printStringHelper(lstGuerdonReqAmt));
		//奖励_截至上期累计申请_原币
		drs.updateString("lstGuerdonReqOriAmt", getOriValue(lstGuerdonReqAmt));
		//奖励_本期发生_本币			
		BigDecimal guerdonAmt = (BigDecimal)((ICell)( bindCellMap.get("guerdonAmt"))).getValue();
		guerdonAmt = guerdonAmt!= null?guerdonAmt: FDCHelper.ZERO;
		drs.updateString("guerdonAmt", printStringHelper(guerdonAmt));
		//奖励_本期发生_原币
		drs.updateString("guerdonOriAmt", getOriValue(guerdonAmt));
		//drs.updateString("guerdonOriAmt", printStringHelper(getGuerdonOriAmt(contractId)));
		//奖励_截至本期累计申请_本币 = 奖励 截至上期累计申请 + 奖励_本期发生_本币 PS:付款申请单都是类似的逻辑。
		BigDecimal allGuerdonReqAmt = guerdonAmt.add(lstGuerdonReqAmt!= null ? lstGuerdonReqAmt : FDCHelper.ZERO);
		drs.updateString("allGuerdonReqAmt", printStringHelper(allGuerdonReqAmt));
		//奖励_截至本期累计申请_原币
		drs.updateString("allGuerdonReqOriAmt", getOriValue(allGuerdonReqAmt));
		//奖励_截至本期累计实付_本币 = 奖励 截至上期累计实付 
		BigDecimal allGuerdonPaidAmt = lstGuerdonPaidAmt!= null ?lstGuerdonPaidAmt: FDCHelper.ZERO;
		drs.updateString("allGuerdonPaidAmt",printStringHelper(allGuerdonPaidAmt));
		//奖励_截至本期累计实付_原币
		drs.updateString("allGuerdonPaidOriAmt", getOriValue(allGuerdonPaidAmt));
		//违约_截至上期累计实付_本币
		BigDecimal lstCompensationPaidAmt = (BigDecimal) ( (ICell)( bindCellMap.get("lstCompensationPaidAmt"))).getValue();
		drs.updateString("lstCompensationPaidAmt",printStringHelper(lstCompensationPaidAmt));
		//违约_截至上期累计实付_原币
		drs.updateString("lstCompensationPaidOriAmt", getOriValue(lstCompensationPaidAmt));
		//违约_截至上期累计申请_本币
		BigDecimal lstCompensationReqAmt = (BigDecimal) ( (ICell)( bindCellMap.get("lstCompensationReqAmt"))).getValue();
		drs.updateString("lstCompensationReqAmt", printStringHelper(lstCompensationReqAmt));
		//违约_截至上期累计申请_原币
		drs.updateString("lstCompensationReqOriAmt", getOriValue(lstCompensationReqAmt));		
		//违约_本期发生_本币
		BigDecimal compensationAmt = (BigDecimal) ( (ICell)( bindCellMap.get("compensationAmt"))).getValue();
		compensationAmt = compensationAmt != null? compensationAmt: FDCHelper.ZERO;
		drs.updateString("compensationAmt", printStringHelper(compensationAmt));
		//违约_本期发生_原币
		drs.updateString("compensationOriAmt", getOriValue(compensationAmt));
		//drs.updateString("compensationOriAmt", printStringHelper(getCompensationOriAmt(contractId)));		
		//违约_截至本期累计申请_本币 = 违约_截至上期累计申请_本币 + 违约_本期发生_本币						
		BigDecimal allCompensationReqAmt = compensationAmt.add(lstCompensationReqAmt != null ? lstCompensationReqAmt : FDCHelper.ZERO);
		drs.updateString("allCompensationReqAmt", printStringHelper(allCompensationReqAmt));
		//违约_截至本期累计申请_原币
		drs.updateString("allCompensationReqOriAmt", getOriValue(allCompensationReqAmt));
		//违约_截至本期累计实付_本币 = 违约_截至上期累计实付_本币
		BigDecimal allCompensationPaidAmt = lstCompensationPaidAmt != null ? lstCompensationPaidAmt : FDCHelper.ZERO;		
		drs.updateString("allCompensationPaidAmt",printStringHelper(allCompensationPaidAmt));
		//违约_截至本期累计实付_原币
		drs.updateString("allCompensationPaidOriAmt", getOriValue(allCompensationPaidAmt));
		
		//已实现产值，累计结算		
		drs.updateString("totalSettlePrice",printStringHelper(fdcBill.getTotalSettlePrice()));
		
		//扣款项
		//扣款类型				
		if(info!=null){
			drs.updateString("deductType",info==null?"":info.getDeductType().getName());
			BigDecimal lstDeductPaidAmt = info.getAllPaidAmt()==null?FDCHelper.ZERO:info.getAllPaidAmt();
			BigDecimal lstDeductReqAmt  = info.getAllReqAmt()==null?FDCHelper.ZERO:info.getAllReqAmt();
			BigDecimal deductAmt        = info.getAmount()==null?FDCHelper.ZERO:info.getAmount();
			BigDecimal allDeductReqAmt  = lstDeductReqAmt.add(deductAmt);
			BigDecimal allDeductPaidAmt  = lstDeductPaidAmt;
			
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
			// 扣款本期发生
			//drs.updateString("deductOriAmt", printStringHelper(FDCHelper.toBigDecimal(getDeductOriAmt(contractId))));
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
		
		//实付款_截至上期累计实付_本币 = 合同工程款 + 奖励 - 违约金 - 扣款小计 - 甲供材
		BigDecimal lstRealPaidAmt = fdcBill.getLstPrjAllPaidAmt()!=null?fdcBill.getLstPrjAllPaidAmt():FDCHelper.ZERO;
		BigDecimal tempLstRealPaidAmt = lstRealPaidAmt.add(FDCHelper.toBigDecimal(lstGuerdonPaidAmt))//奖励
		.subtract(FDCHelper.toBigDecimal(lstCompensationPaidAmt))//违约
		.subtract(FDCHelper.toBigDecimal(sumLstDeductPaid))//扣款
		.subtract(fdcBill.getLstAMatlAllPaidAmt() != null ? fdcBill
						.getLstAMatlAllPaidAmt() : FDCHelper.ZERO);//甲供材
		drs.updateString("lstRealPaidAmt", printStringHelper(tempLstRealPaidAmt));
		//实付款_截至上期累计实付_原币
		drs.updateString("lstRealPaidOriAmt", getOriValue(tempLstRealPaidAmt));
		
		//实付款_截至上期累计申请_本币  = 合同工程款 + 奖励 - 违约金 - 扣款小计 - 甲供材
		BigDecimal lstRealReqAmt = fdcBill.getLstPrjAllReqAmt()!=null?fdcBill.getLstPrjAllReqAmt():FDCHelper.ZERO;
		BigDecimal tempLstRealReqAmt = lstRealReqAmt.add(FDCHelper.toBigDecimal(lstGuerdonReqAmt))//奖励
		.subtract(FDCHelper.toBigDecimal(lstCompensationReqAmt))//违约
		.subtract(FDCHelper.toBigDecimal(sumLstDeductReq))//扣款
		.subtract(fdcBill.getLstAMatlAllReqAmt() != null ? fdcBill
						.getLstAMatlAllReqAmt() : FDCHelper.ZERO);//甲供材
		drs.updateString("lstRealReqAmt", printStringHelper(tempLstRealReqAmt));
		//实付款_截至上期累计申请_原币
		drs.updateString("lstRealReqOriAmt", getOriValue(tempLstRealReqAmt));
		
		//实付款_本期发生_本币 = 合同工程款 + 奖励 - 违约金 - 扣款小计 - 甲供材
		BigDecimal projectPriceInContract = fdcBill.getProjectPriceInContract()!=null?fdcBill.getProjectPriceInContract():FDCHelper.ZERO;
		//BigDecimal tempCurPaid = fdcBill.getCurPaid()!= null ? fdcBill.getCurPaid(): FDCHelper.ZERO;
		BigDecimal tempCurPaid = projectPriceInContract.add(guerdonAmt)//奖励
		.subtract(compensationAmt)//违约
		.subtract(sumDeduct)//扣款
		.subtract(fdcBill.getPayPartAMatlAmt() != null ? fdcBill
						.getPayPartAMatlAmt() : FDCHelper.ZERO);//甲供材
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
		
		/* modified by zhaoqin for R140401-0072 on 2014/04/25 */
		//BigDecimal lstPrjAllPaidAmt =  FDCHelper.toBigDecimal(fdcBill.getLstPrjAllPaidAmt());
		BigDecimal allRealPaidAmt = prjAllPaidAmt
				.add(allGuerdonPaidAmt)//奖励
				.subtract(allCompensationPaidAmt)//违约
				.subtract(sumAllDeductPaid)//扣款
				.subtract(allPartAMatlAllPaidAmt);//甲供材
		
		drs.updateString("allRealPaidAmt",printStringHelper(allRealPaidAmt));
		//实付款_截至本期累计实付_原币
		drs.updateString("allRealPaidOriAmt", getOriValue(allRealPaidAmt));
		
		// 余款 = 最新造价 - 合同内付款 截至本期累计实付
		BigDecimal restAmount = (fdcBill
		.getLatestPrice() == null ? FDCHelper.ZERO : fdcBill.getLatestPrice())
		.subtract(prjAllPaidAmt);//合同内工程款_截至本期累计实付_本币
		
		drs.updateString("restAmount",printStringHelper(restAmount));
		// 本次申请% = 实付款 本期发生 / 最新造价——改为直接从界面上取数，可以保证与界面一致 Added by Owen_wen 2012-12-11
		drs.updateString("curReqPercent", printStringHelper(fdcBill.get(PayRequestBillContants.CURREQPERCENT)));
		
		// 累计申请% = 实付款 截至本期累计申请 / 最新造价——改为直接从界面上取数，可以保证与界面一致 Added by Owen_wen 2012-12-11
		drs.updateString("allReqPercent", printStringHelper(fdcBill.get(PayRequestBillContants.ALLREQPERCENT)));

		// 累计应付申请%
		drs.updateString("allPayableReqPercent", printStringHelper(FDCHelper.divide(FDCHelper
				.multiply(FDCHelper.ONE_HUNDRED, allRealReqAmt), fdcBill.getLatestPrice(), 2, BigDecimal.ROUND_HALF_UP)));
		
		drs.updateString("imageSchedule", printStringHelper(fdcBill.getImageSchedule()));
		drs.updateString("allCompletePrjAmt", printStringHelper(fdcBill.get("allCompletePrjAmt")));
		// 合同内工程款截止上期实付+本期申请
		BigDecimal LstPrjAllPaidAmtAndthis = FDCHelper.add(fdcBill.getLstPrjAllPaidAmt(),fdcBill.getProjectPriceInContract());
		drs.updateString("LstPrjAllPaidAmtAndthis",printStringHelper(LstPrjAllPaidAmtAndthis));
		// 合同最新造价-合同内工程款截止上期实付-本期申请
		BigDecimal latestPriceSub = FDCHelper.subtract(fdcBill.getLatestPrice(),FDCHelper.add(fdcBill.getLstPrjAllPaidAmt(),fdcBill.getProjectPriceInContract()));
		drs.updateString("latestPriceSub",printStringHelper(latestPriceSub));

		// 保修金金额
		drs.updateString("grtOriAmount", printStringHelper(FDCHelper.divide(FDCHelper.toBigDecimal(fdcBill.getGrtAmount()), fdcBill.getExchangeRate(),4,BigDecimal.ROUND_HALF_UP)));

		// 结算金额
		drs.updateString("settleOriAmt", printStringHelper(FDCHelper.divide(FDCHelper.toBigDecimal(fdcBill.getSettleAmt()), fdcBill.getExchangeRate(),4,BigDecimal.ROUND_HALF_UP)));

		// 本期申请本币金额    直接取得 amount 字段的值，还不知道对不对呢！！！
		drs.updateString("thisTimeReqLocalAmt", printStringHelper(FDCHelper.toBigDecimal(fdcBill.getAmount())));
		drs.updateString("process", fdcBill.getProcess());

		// 计划付款状态
		drs.updateString("depPlanState", printEnumHelper(fdcBill.getDepPlanState()));

		// //////////////////////////////////////////////////////////////////////////
		// 付款申请单：增加拓展字段，用于客户自定义 by skyiter_wang 2014-12-04
		// 说明：
		// 1、付款申请单的套打比较特殊
		// 2、在代码中做了处理，写死了一些查询字段，导致DEP拓展的字段无法正常展示
		// 3、所以预定义5个拓展字段，方便客户自定义
		// 4、参见PayRequestBillEditUI.actionPrintPreview_actionPerformed，PayRequestBillRowsetProvider

		drs.updateString("extField01", fdcBill.getExtField01());// 拓展字段01
		drs.updateString("extField02", fdcBill.getExtField02());// 拓展字段02
		drs.updateString("extField03", fdcBill.getExtField03());// 拓展字段03
		drs.updateString("extField04", fdcBill.getExtField04());// 拓展字段04
		drs.updateString("extField05", fdcBill.getExtField05());// 拓展字段05

		drs.updateString("extBooleanField01", printBooleanHelper(fdcBill.isExtBooleanField01()));// 拓展布尔字段01
		drs.updateString("extBooleanField02", printBooleanHelper(fdcBill.isExtBooleanField02()));// 拓展布尔字段02

		drs.updateString("extEnumField01", printEnumHelper(fdcBill.getExtEnumField01()));// 拓展枚举字段01
		drs.updateString("extEnumField02", printEnumHelper(fdcBill.getExtEnumField02()));// 拓展枚举字段02

		// //////////////////////////////////////////////////////////////////////////
	}

	public IRowSet getOtherSubRowSet(R1PrintDataSource ds) throws Exception {
		/**
		 * 付款申请单套打增加数据源： 归属财务核算对象、归属成本科目代码、归属成本科目、归属金额、直接归属。
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
			// 合同付款事项
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
			// 合同履约保证金及返还部分
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
			// 合同详细信息
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
			// 合同详细信息
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
			// 无文本合同详细信息
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
			// 该申请单对应的扣款分录信息
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
		} else if (ds.getId().equalsIgnoreCase("PaymentBillF7Query")) {// PaymentBillF7Query需要用DEP动态再添加相关的字段
			// 该申请单对应的合同的付款信息
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
		}else if (ds.getId().equalsIgnoreCase("AttachementQuery")) {// PaymentBillF7Query需要用DEP动态再添加相关的字段
			// 该申请单对应的合同的付款信息
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
				//获得累计实付 转换成String字符串显示
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
			//取已经付款的日期和金额
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
			//取原合同编码
			String oldProjNumber = "";
			if(fdcBill.getId()!=null){
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				FilterItemCollection items = filter.getFilterItems();
				items.add(new FilterItemInfo("parent", fdcBill.getContractId()));
				//陆家嘴，硬编码
				items.add(new FilterItemInfo("detail", "%原合同编码%" ,CompareType.LIKE));
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
			
			//取审批意见 auditResult
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
			
			//取扣款类型
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
			//取合同信息			
			String contractBillAmount = "";
			String contractBillType   = "";
			if(fdcBill.getId()!=null){
				// 修复 R100707-082一张付款申请单套打时取不到数据。 By Owen_wen 2010-7-27
				// 文本合同和无文本合同的付款申请单共用EditUI，所以套打时要分别处理
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
			
			//处理没有循环的打印，将每个drs的值都设置为最后一个
			String lstRealPaidAmt = "";
			/*
			 * R110726-0274:套打-房地产的付款申请单套打默认打印5份  by zhiyuan_tang
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
					// 已付日期金额
					drs.updateString("paidDataAmount", paidDataAmount);
					// 原合同编码
					drs.updateString("oldProjNumber", oldProjNumber);
					drs.updateString("paidDetail", payDetails);

					drs.updateString("contractBillAmount", contractBillAmount);
					drs.updateString("contractBillType", contractBillType);
					// 审批意见
					drs.updateString("auditResult", auditResult);
					drs.insertRow();
				}
			}else{
				
				for(int i=0;i<c.size();i++){
					info = c.get(i);
					
					drs.moveToInsertRow();
					insert( drs,info ,deductTypeSum);
					// 已付日期金额
					drs.updateString("paidDataAmount", paidDataAmount);
					// 原合同编码
					drs.updateString("oldProjNumber", oldProjNumber);
					drs.updateString("paidDetail", payDetails);

					drs.updateString("contractBillAmount", contractBillAmount);
					drs.updateString("contractBillType", contractBillType);

					lstRealPaidAmt = drs.getString("lstRealPaidAmt");
					// 审批意见
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
	 * 根据合同ID获取对应合同的汇率
	 * @param contractId 可能是文本合同，也有可能是无文本合同，要单独处理
	 */
	public BigDecimal getConExRate(String contractId){
		if (this.exRate != null)
			return exRate;
		
		exRate = FDCHelper.ONE;
		try {
			if (new ContractBillInfo().getBOSType().equals(BOSUuid.read(contractId).getBOSObjectType(contractId, false))) { // 普通合同
				ContractBillInfo info = ContractBillFactory.getRemoteInstance().getContractBillInfo(
						new ObjectUuidPK(BOSUuid.read(contractId)));
				exRate = info.getExRate();
			} else {//无文本合同
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
	 * 累计完工工程量取界面上“累计完工工程量”的金额。（当界面上显示本期完工工程量时，此数据源才显示数据，否则，为空 ）
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
