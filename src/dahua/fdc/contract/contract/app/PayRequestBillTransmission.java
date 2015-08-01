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
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		付款申请单导入转换类
 *		
 * @description	付款申请单
 * @author			雍定文
 * @version		EAS7.0	
 * @createDate		2011-6-13	 
 * @see						
 */
public class PayRequestBillTransmission extends AbstractDataTransmission {

	//  资源文件
	private static String resource = "com.kingdee.eas.fdc.contract.PayReqBTraResource";
	
	/** 付款申请单 */
	private PayRequestBillInfo info = null;
	/** 工程项目 */
	private CurProjectInfo curProject = null;
	/** 合同 */
	private ContractBillInfo contractBill = null;
	/** 用款部门 */
	private AdminOrgUnitInfo adminOrgUnit = new AdminOrgUnitInfo();
	/** 制单人 */
	private UserInfo createUser = null;
	/** 审批人 */
	private UserInfo auditUser = null;
	/** 付款类型 */
	private PaymentTypeInfo paymentType = null;
	/** 结算方式 */
	private SettlementTypeInfo settlementType = new SettlementTypeInfo();
	/** 实际收款单位 */
	private SupplierInfo supplier = new SupplierInfo();
	/** 币种 */
	private CurrencyInfo currency = new CurrencyInfo();
	/** 金额为空时默认值为0 */
	private BigDecimal bigDecimal = new BigDecimal(0);
	/** 奖励单 */
	private GuerdonBillInfo guerdonBill = null;
	/** 付款申请单对应的奖励项 */
	private GuerdonOfPayReqBillInfo guerdonOfPayReqBill = null;
	/** 违约金 */
	private CompensationBillInfo compensationBill = null;
	/** 付款申请单对应的违约金 */
	private CompensationOfPayReqBillInfo compensationOfPayReqBill = null;
	/** 全局<hashtable> */
	private Hashtable hashtableAll = null;
	/** 付款申请单对应的扣款项 */
	private DeductOfPayReqBillInfo deductOfPayReqBill = null;
	/** 付款申请单对应的甲供材项 */
	private PartAOfPayReqBillInfo partAOfPayReqBill = null;
	
	/**
	 * @description		
	 * @author			雍定文		
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
	 * @author			雍定文			
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
			// 组织长编码
			String fCostCenterNumber  = ((String) ((DataToken)hashtable.get("FCostCenter_number")).data).trim();
			// 工程项目编码
			String fCurProjectCodingNumber  = ((String) ((DataToken)hashtable.get("FCurProject_codingNumber")).data).trim();
			// 合同编号
			String fContractNo = ((String) ((DataToken)hashtable.get("FContractNo")).data).trim();
			// 用款部门-取number
			String fUseDepartmentNamel2 = ((String) ((DataToken) hashtable.get("FUseDepartment_number")).data).trim();
			// 状态
			String fState = ((String) ((DataToken)hashtable.get("FState")).data).trim();
			// 业务日期
			String fBizDate = ((String) ((DataToken)hashtable.get("FBizDate")).data).trim();
			// 付款申请单编码
			String fNumber = ((String) ((DataToken)hashtable.get("FNumber")).data).trim();
			// 付款日期
			String fPayDate = ((String) ((DataToken)hashtable.get("FPayDate")).data).trim();
			// 付款类型
			String fPaymentTypeNumber = ((String) ((DataToken)hashtable.get("FPaymentType_number")).data).trim();
			// 同城异地
			String fIsDifferPlace = ((String) ((DataToken)hashtable.get("FIsDifferPlace")).data).trim();
			// 结算方式
			String fSettlementTypeNamel2 = ((String) ((DataToken)hashtable.get("FSettlementType_name_l2")).data).trim();
			// 收款单位全称
			String fSupplierNamel2 = ((String) ((DataToken)hashtable.get("FSupplier_name_l2")).data).trim();
			// 收款银行
			String fRecBank = ((String) ((DataToken)hashtable.get("FRecBank")).data).trim();
			// 摘要
			String fDescription = ((String) ((DataToken)hashtable.get("FDescription")).data).trim();
			// 实际收款单位-取number
			String fRealSupplierNamel2 = ((String) ((DataToken) hashtable.get("FRealSupplier_number")).data).trim();
			// 收款账号
			String fRecAccount = ((String) ((DataToken)hashtable.get("FRecAccount")).data).trim();
			// 用途
			String fUsage = ((String) ((DataToken)hashtable.get("FUsage")).data).trim();
			// 币别编码
			String fCurrencyNumber = ((String) ((DataToken)hashtable.get("FCurrency_number")).data).trim();
			// 汇率
			String fExchangeRate = ((String) ((DataToken)hashtable.get("FExchangeRate")).data).trim();
			// 进度款付款比例
			String fConPayplanPayProportion = ((String) ((DataToken)hashtable.get("FConPayplan_payProportion")).data).trim();
			// 本期完工工程量
			String fCostAmount = ((String) ((DataToken)hashtable.get("FCostAmount")).data).trim();
			// 保修金金额
			String fGrtAmount = ((String) ((DataToken)hashtable.get("FGrtAmount")).data).trim();
			// 紧急程度
			String fUrgentDegree = ((String) ((DataToken)hashtable.get("FUrgentDegree")).data).trim();
			// 发票号
			String fInvoiceNumber = ((String) ((DataToken)hashtable.get("FInvoiceNumber")).data).trim();
			// 发票金额
			String fInvoiceAmt = ((String) ((DataToken)hashtable.get("FInvoiceAmt")).data).trim();
			// 开票日期
			String fInvoiceDate = ((String) ((DataToken)hashtable.get("FInvoiceDate")).data).trim();
			// 形象进度描述
			String fProcess = ((String) ((DataToken)hashtable.get("FProcess")).data).trim();
			// 备注
			String fMoneyDesc = ((String) ((DataToken)hashtable.get("FMoneyDesc")).data).trim();
			// 项目名称
			String fCurProjectNamel2 = ((String) ((DataToken)hashtable.get("FCurProject_name_l2")).data).trim();
			// 合同名称
			String fContractName = ((String) ((DataToken)hashtable.get("FContractName")).data).trim();
			// 最新造价
			String fLatestPrice = ((String) ((DataToken)hashtable.get("FLatestPrice")).data).trim();
			// 变更签证确认金额
			String fChangeAmt = ((String) ((DataToken)hashtable.get("FChangeAmt")).data).trim();
			// 结算金额
			String fSettleAmt = ((String) ((DataToken)hashtable.get("FSettleAmt")).data).trim();
			// 本申请单已付金额
			String fPayedAmt = ((String) ((DataToken)hashtable.get("FPayedAmt")).data).trim();
			// 合同付款次数
			String fPayTimes = ((String) ((DataToken)hashtable.get("FPayTimes")).data).trim();
			// 合同内工程款_本次申请原币
			String fProjectPriceInContractOri = ((String) ((DataToken)hashtable.get("FProjectPriceInContractOri")).data).trim();
			// 本期计划付款
			String fCurPlannedPayment = ((String) ((DataToken)hashtable.get("FCurPlannedPayment")).data).trim();
			// 本期欠付款
			String fCurBackPay = ((String) ((DataToken)hashtable.get("FCurBackPay")).data).trim();
			// 本次申请%
			String FCurReqPercent = ((String) ((DataToken)hashtable.get("FCurReqPercent")).data).trim();
			// 累计申请%
			String fAllReqPercent = ((String) ((DataToken)hashtable.get("FAllReqPercent")).data).trim();
			// 形象进度%
			String fImageSchedule = ((String) ((DataToken)hashtable.get("FImageSchedule")).data).trim();
			// 应付申请% 	  <自动计算,现有代码不能设值>
			String mustReqPercent = ((String) ((DataToken)hashtable.get("mustReqPercent")).data).trim();
			// 累计应付申请%  <自动计算,现有代码不能设值>
			String addUpMustReqPercent = ((String) ((DataToken)hashtable.get("addUpMustReqPercent")).data).trim();
			// 制单人编码
			String fCreatorNumber = ((String) ((DataToken)hashtable.get("FCreator_number")).data).trim();
			// 制单时间
			String fCreateTime = ((String) ((DataToken)hashtable.get("FCreateTime")).data).trim();
			// 审核人编码
			String fAuditorNumber = ((String) ((DataToken)hashtable.get("FAuditor_number")).data).trim();
			// 审核时间
			String fAuditTime = ((String) ((DataToken)hashtable.get("FAuditTime")).data).trim();
			// 奖励     <存在于奖励单与付款申请单的中间表中>
			String reward = ((String) ((DataToken)hashtable.get("reward")).data).trim();
			// 违约     <存在于违约单与付款申请单的中间表中>
			String breach = ((String) ((DataToken)hashtable.get("breach")).data).trim();
			// 扣款     <存在于扣款单与付款申请单的中间表中>
			String fine = ((String) ((DataToken)hashtable.get("fine")).data).trim();
			// 甲供材扣款   <存在于甲供材扣款与付款申请单的中间表中>
			String stuffFine = ((String) ((DataToken)hashtable.get("stuffFine")).data).trim();
			
			/**
			 * 判断是否为空,以及判断字段长度
			 */
			if (!StringUtils.isEmpty(fCostCenterNumber)) {
				if (fCostCenterNumber.length() > 80) {
					// "组织编码字段长度不能超过80！"
					throw new TaskExternalException(getResource(context, "Import_fCostCenterNumber"));
				}
			}
			if (!StringUtils.isEmpty(fCurProjectCodingNumber)) {
				if (fCurProjectCodingNumber.length() > 80) {
					// "工程项目编码字段长度不能超过80！"
					throw new TaskExternalException(getResource(context, "Import_fCurProjectCodingNumber"));
				}
			} else {
				// "工程项目编码不能为空！"
				throw new TaskExternalException(getResource(context, "Import_fCurProjectCodingNumberNotNull"));
			}
			if (!StringUtils.isEmpty(fContractNo)) {
				if (fContractNo.length() > 80) {
					// "合同编码字段长度不能超过80！"
					throw new TaskExternalException(getResource(context, "Import_fContractNo"));
				}
			} else {
				// "合同编码不能为空！"
				throw new TaskExternalException(getResource(context, "Import_fContractNoNotNull"));
			}
			if (StringUtils.isEmpty(fUseDepartmentNamel2)) {
				// "用款部门不能为空！"
				throw new TaskExternalException(getResource(context, "Import_fUseDepartmentNamel2"));
			}
			if (StringUtils.isEmpty(fBizDate)) {
				// "业务日期不能为空！"
				throw new TaskExternalException(getResource(context, "Import_fBizDate"));
			}
			if (!StringUtils.isEmpty(fNumber)) {
				if (fNumber.length() > 80) {
					// "付款申请单编码字段长度不能超过80！"
					throw new TaskExternalException(getResource(context, "Import_fNumber"));
				}
			} else {
				// "付款申请单编码不能为空！"
				throw new TaskExternalException(getResource(context, "Import_fNumberNotNull"));
			}
			if (StringUtils.isEmpty(fPayDate)) {
				// "付款日期不能为空！"
				throw new TaskExternalException(getResource(context, "Import_fPayDate"));
			}
			if (StringUtils.isEmpty(fPaymentTypeNumber)) {
				// "付款类型不能为空！"
				throw new TaskExternalException(getResource(context, "Import_fPaymentTypeNumber"));
			}
			if (StringUtils.isEmpty(fRealSupplierNamel2)) {
				// "实际收款单位不能为空！"
				throw new TaskExternalException(getResource(context, "Import_fRealSupplierNamel2"));
			}
			if (!StringUtils.isEmpty(fUsage)) {
				if (fUsage.length() > 90) {
					// "用途字段长度不能超过90！"
					throw new TaskExternalException(getResource(context, "Import_fUsage"));
				}
			}
			if (!StringUtils.isEmpty(fExchangeRate)) {
				if (FDCTransmissionHelper.isRangedInHundred(fExchangeRate) == null) {
					// "汇率不在0~100之间！"
					throw new TaskExternalException(getResource(context, "Import_fExchangeRate"));
				}
			}
			if (!StringUtils.isEmpty(fConPayplanPayProportion)) {
				if (FDCTransmissionHelper.isRangedInHundred(fConPayplanPayProportion) == null) {
					// "进度款付款比例不在0~100之间！"
					throw new TaskExternalException(getResource(context, "Import_fConPayplanPayProportion"));
				}
			} else {
				// "进度款付款比例不能为空！"
				throw new TaskExternalException(getResource(context, "Import_fConPayplanPayProportionNotNull"));
			}
			if (!StringUtils.isEmpty(fCostAmount)) {
				// "本期完工工程量"
				this.strTOnumber(fCostAmount, getResource(context, "Import_fCostAmount"), context);
			} else {
				// "本期完工工程量不能为空！"
				throw new TaskExternalException(getResource(context, "Import_fCostAmountNotNull"));
			}
			if (!StringUtils.isEmpty(fGrtAmount)) {
				// "保修金金额"
				this.strTOnumber(fGrtAmount, getResource(context, "Import_fGrtAmount"), context);
			}
			if (!StringUtils.isEmpty(fInvoiceNumber)) {
				if (fInvoiceNumber.length() > 80) {
					// "发票号字段长度不能超过80！"
					throw new TaskExternalException(getResource(context, "Import_fInvoiceNumber"));
				}
			}
			if (!StringUtils.isEmpty(fInvoiceAmt)) {
				// "发票金额"
				this.strTOnumber(fGrtAmount, getResource(context, "Import_fInvoiceAmt"), context);
			}
			if (!StringUtils.isEmpty(fProcess)) {
				if (fProcess.length() > 255) {
					// "形象进度描述字段长度不能超过255！"
					throw new TaskExternalException(getResource(context, "Import_fProcess"));
				}
			}
			if (!StringUtils.isEmpty(fMoneyDesc)) {
				if (fMoneyDesc.length() > 500) {
					// "备注字段长度不能超过500！"
					throw new TaskExternalException(getResource(context, "Import_fMoneyDesc"));
				}
			}
			if (!StringUtils.isEmpty(fLatestPrice)) {
				// "最新造价"
				this.strTOnumber(fLatestPrice, getResource(context, "Import_fLatestPrice"), context);
			}
			if (!StringUtils.isEmpty(fChangeAmt)) {
				// "变更签证确认金额"
				this.strTOnumber(fChangeAmt, getResource(context, "Import_fChangeAmt"), context);
			}
			if (!StringUtils.isEmpty(fSettleAmt)) {
				// "结算金额"
				this.strTOnumber(fSettleAmt, getResource(context, "Import_fSettleAmt"), context);
			}
			if (!StringUtils.isEmpty(fPayedAmt)) {
				// "本申请单已付金额"
				this.strTOnumber(fPayedAmt, getResource(context, "Import_fPayedAmt"), context);
			}
			if (!StringUtils.isEmpty(fPayTimes)) {
				if (FDCTransmissionHelper.strToInt(fPayTimes) == 0) {
					// "合同付款次数应该录入数字型！"
					throw new TaskExternalException(getResource(context, "Import_fPayTimes"));
				}
			}
			if (!StringUtils.isEmpty(fProjectPriceInContractOri)) {
				// "合同内工程款_本次申请原币"
				this.strTOnumber(fProjectPriceInContractOri, getResource(context, "Import_fProjectPriceInContractOri"), context);
			} else {
				// "合同内工程款_本次申请原币不能为空！"
				throw new TaskExternalException(getResource(context, "Import_fProjectPriceInContractOriNotNull"));
			}
			if (!StringUtils.isEmpty(fCurPlannedPayment)) {
				// "本期计划付款"
				this.strTOnumber(fCurPlannedPayment, getResource(context, "Import_fCurPlannedPayment"), context);
			}
			if (!StringUtils.isEmpty(fCurBackPay)) {
				// "本期欠付款"
				this.strTOnumber(fCurBackPay, getResource(context, "Import_fCurBackPay"), context);
			}
			if (!StringUtils.isEmpty(FCurReqPercent)) {
				// "本次申请%"
				if (FDCTransmissionHelper.isRangedInHundred(FCurReqPercent) == null) {
					throw new TaskExternalException(getResource(context, "Import_FCurReqPercent"));
				}
			}
			if (!StringUtils.isEmpty(fAllReqPercent)) {
				// "累计申请%"
				if (FDCTransmissionHelper.isRangedInHundred(fAllReqPercent) == null) {
					throw new TaskExternalException(getResource(context, "Import_fAllReqPercent"));
				}
			}
			if (!StringUtils.isEmpty(fImageSchedule)) {
				// "形象进度%"
				if (FDCTransmissionHelper.isRangedInHundred(fImageSchedule) == null) {
					throw new TaskExternalException(getResource(context, "Import_fImageSchedule"));
				}
			}
			if (!StringUtils.isEmpty(mustReqPercent)) {
				// 应付申请%
				if (FDCTransmissionHelper.isRangedInHundred(mustReqPercent) == null) {
					throw new TaskExternalException(getResource(context, "Import_yfsq"));
				}
			}
			if (!StringUtils.isEmpty(addUpMustReqPercent)) {
				// 累计应付申请% 
				if (FDCTransmissionHelper.isRangedInHundred(addUpMustReqPercent) == null) {
					throw new TaskExternalException(getResource(context, "Import_ljyfsq"));
				}
			}
			if (StringUtils.isEmpty(fCreatorNumber)) {
				// "制单人编码不能为空！"
				throw new TaskExternalException(getResource(context, "Import_fCreatorNumber"));
			}
			if (StringUtils.isEmpty(fCreateTime)) {
				// "制单时间不能为空！"
				throw new TaskExternalException(getResource(context, "Import_fCreateTimeNotNull"));
			}
			// ！！！！以下四个字段均存在于中间表！！！！ 
			if (!StringUtils.isEmpty(reward)) {
				// 奖励字段应该录入数字型
				this.strTOnumber(reward, getResource(context, "Import_jlyglrszx"), context);
			}
			if (!StringUtils.isEmpty(breach)) {
				// 违约字段应该录入数字型
				this.strTOnumber(breach, getResource(context, "Import_wyyglrszx"), context);
			}
			if (!StringUtils.isEmpty(fine)) {
				// 扣款字段应该录入数字型
				this.strTOnumber(fine, getResource(context, "Import_kkyglrszx"), context);
			}
			if (!StringUtils.isEmpty(stuffFine)) {
				// 甲供材扣款字段应该录入数字型
				this.strTOnumber(stuffFine, getResource(context, "Import_jgckkyglrszx"), context);
			}
				
			/**
			 * 将值存入对象
			 */
			// 工程项目编码
			FilterInfo filterCurProject = new FilterInfo();
			filterCurProject.getFilterItems().add(new FilterItemInfo("longnumber", fCurProjectCodingNumber.replace('.', '!')));
			EntityViewInfo viewCurProject = new EntityViewInfo();
			viewCurProject.setFilter(filterCurProject);
			CurProjectCollection curProjectBillColl = CurProjectFactory.getLocalInstance(context).getCurProjectCollection(viewCurProject);
			if (curProjectBillColl.size() > 0){
				curProject = curProjectBillColl.get(0);
				info.setCurProject(curProject);
				// 组织长编码
				CostCenterOrgUnitInfo costCenterOrgUnit = curProject.getCostCenter(); //  工程项目对应成本中心组织
				FilterInfo filter1 = new FilterInfo();
				filter1.getFilterItems().add(new FilterItemInfo("id", costCenterOrgUnit.getId().toString()));
				EntityViewInfo view1 = new EntityViewInfo();
				view1.setFilter(filter1);
				CostCenterOrgUnitCollection ccouc = CostCenterOrgUnitFactory.getLocalInstance(context).getCostCenterOrgUnitCollection(view1);
				if (!StringUtils.isEmpty(fCostCenterNumber) && !fCostCenterNumber.replace('.', '!').equals(ccouc.get(0).getLongNumber())) {
					// "组织长编码在工程项目所对应的成本中心不存在!"
 					throw new TaskExternalException(getResource(context, "Import_fCostCenterNumber1"));
					// 原转换组织对象方法 <暂不推荐使用> (FullOrgUnitInfo)FullOrgUnitInfo.class.cast(costCenterOrgUnit)
				}
				info.setOrgUnit(ccouc.get(0).castToFullOrgUnitInfo());
			} else {
				// 1 "工程项目编码为空,或该工程项目编码 "
				// 2 " 在系统中不存在！"
				throw new TaskExternalException(getResource(context, "Import_fCurProjectCodingNumber1") + fCurProjectCodingNumber + getResource(context, "Import_NOTNULl"));
			}
			// 合同编号
			FilterInfo filterContractBill = new FilterInfo();
			filterContractBill.getFilterItems().add(new FilterItemInfo("number", fContractNo));
			EntityViewInfo viewContractBill = new EntityViewInfo();
			viewContractBill.setFilter(filterContractBill);
			ContractBillCollection contractBillColl = ContractBillFactory.getLocalInstance(context).getContractBillCollection(viewContractBill);
			if (contractBillColl.size() > 0){
				contractBill = contractBillColl.get(0);
				// 判断该合同是否存在于录入的工程项目中
				// 得到合同单据所在的工程项目ID
				String contractBillCurProject = String.valueOf(contractBill.getCurProject().getId());
				if (!contractBillCurProject.equals(curProject.getId().toString())) {
					// 在该工程项目下不存在此合同！
					throw new TaskExternalException(getResource(context, "Import_ggcxmxbczcht"));
				}
			}else{
				// 1 "合同编码为空,或该合同编码 "
				// 2 " 在系统中不存在！"
				throw new TaskExternalException(getResource(context, "Import_fContractNo1") + fContractNo + getResource(context, "Import_NOTNULl"));
			}
			info.setContractNo(fContractNo);
			info.setContractId(contractBill.getId().toString());
			info.setContractBase(contractBill.getContractBaseData());
			// 用款部门
			FilterInfo filterAdminOrgUnit = new FilterInfo();
			filterAdminOrgUnit.getFilterItems().add(new FilterItemInfo("number", fUseDepartmentNamel2));
			EntityViewInfo viewAdminOrgUnit = new EntityViewInfo();
			viewAdminOrgUnit.setFilter(filterAdminOrgUnit);
			AdminOrgUnitCollection adminOrgUnitColl = AdminOrgUnitFactory.getLocalInstance(context).getAdminOrgUnitCollection(viewAdminOrgUnit);
			if (adminOrgUnitColl.size() > 0){
				adminOrgUnit = adminOrgUnitColl.get(0);
			}else{
				// "用款部门对象找不到！"
				throw new TaskExternalException(getResource(context, "Import_fUseDepartmentNamel21"));
			}
			info.setUseDepartment(adminOrgUnit);
			// 状态     fState 
			if (fState.equals(getResource(context, "SAVED"))) {
				info.setState(FDCBillStateEnum.SAVED);
				if ((!StringUtils.isEmpty(fAuditorNumber)) || (!StringUtils.isEmpty(fAuditTime))) {
					// 在保存状态下不能录入审批人编码和审批时间！
					throw new TaskExternalException(getResource(context, "SAVED_NotEnteringData"));
				}
			} else if (fState.equals(getResource(context, "SUBMITTED"))) {
				info.setState(FDCBillStateEnum.SUBMITTED);
				if ((!StringUtils.isEmpty(fAuditorNumber)) || (!StringUtils.isEmpty(fAuditTime))) {
					// 在已提交状态下不能录入审批人编码和审批时间！
					throw new TaskExternalException(getResource(context, "SUBMITTED_NotEnteringData"));
				}
			} else if (fState.equals(getResource(context, "AUDITTED")) || StringUtils.isEmpty(fState)) {
				info.setState(FDCBillStateEnum.AUDITTED);
				// 审核人编码 <"状态"字段需求文档中不存在,需要修改需求文档添加该字段>
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
						// 1 "该审核人编码"
						// 2 " 在系统中不存在！"
						throw new TaskExternalException(getResource(context, "Import_fAuditorNumber1") + fAuditorNumber + getResource(context, "Import_NOTNULl"));
					}
				} else {
					// "在已审批状态下的审核人编码字段不能为空！"
					throw new TaskExternalException(getResource(context, "Import_fAuditorNumber"));
				}
				// 审批时间
				if (!StringUtils.isEmpty(fAuditTime)) {
					info.setAuditTime(new Timestamp(FDCTransmissionHelper.checkDateFormat(fAuditTime, getResource(context, "Import_spsjcw")).getTime()));
				} else {
					// "在已审批状态下的审批日期字段不能为空！"
					throw new TaskExternalException(getResource(context, "Import_fAuditTime"));
				}
			} else {
				// 1 "该状态 "
				// 2 " 在系统中不存在！"
				throw new TaskExternalException(getResource(context, "Import_fState") + fState + getResource(context, "Import_NOTNULl"));
			}
			// 业务日期 
			info.setBizDate(FDCTransmissionHelper.checkDateFormat(fBizDate, getResource(context, "Import_ywrqcw")));
			// 付款申请单编码 
			FilterInfo filterfNumber = new FilterInfo();
			filterfNumber.getFilterItems().add(new FilterItemInfo("number", fNumber, CompareType.EQUALS));
			EntityViewInfo viewfNumber = new EntityViewInfo();
			viewfNumber.setFilter(filterfNumber);
			PayRequestBillCollection payReqColl = PayRequestBillFactory.getLocalInstance(context).getPayRequestBillCollection(viewfNumber);
			if (payReqColl.size() > 0){
				// "该付款申请单编码已经存在,不可重复！"
				throw new TaskExternalException(getResource(context, "Import_fNumber11"));
			}
			info.setNumber(fNumber);
			// 付款日期
			info.setPayDate(FDCTransmissionHelper.checkDateFormat(fPayDate, getResource(context, "Import_fkrqcw")));
			// 付款类型     
			FilterInfo filterPaymentType = new FilterInfo();
			filterPaymentType.getFilterItems().add(new FilterItemInfo("name", fPaymentTypeNumber));
			EntityViewInfo viewPaymentType = new EntityViewInfo();
			viewPaymentType.setFilter(filterPaymentType);
			PaymentTypeCollection paymentTypeColl = PaymentTypeFactory.getLocalInstance(context).getPaymentTypeCollection(viewPaymentType);
			if (paymentTypeColl.size() > 0){
				paymentType = paymentTypeColl.get(0);
			}else{
				// 1 "付款类型 "
				// 2 " 在系统中不存在！"
				throw new TaskExternalException(getResource(context, "Import_fPaymentTypeNumber1") + fPaymentTypeNumber + getResource(context, "Import_NOTNULl"));
			}
			info.setPaymentType(paymentType);
			// 同城异地 
			if (fIsDifferPlace.equals(getResource(context, "samePlace")) || StringUtils.isEmpty(fIsDifferPlace)) {
				info.setIsDifferPlace(DifPlaceEnum.samePlace);
			} else if (fIsDifferPlace.trim().equals(getResource(context, "difPlace"))) {
				info.setIsDifferPlace(DifPlaceEnum.difPlace);
			} else {
				// 1 "同城异地 "
				// 2 " 在系统中不存在！"
				throw new TaskExternalException(getResource(context, "Import_fIsDifferPlace") + fIsDifferPlace + getResource(context, "Import_NOTNULl"));
			}
			// 结算方式 <非笔录项,则需要在非空的情况下判断即可>
			if (!StringUtils.isEmpty(fSettlementTypeNamel2)) {
				FilterInfo filterSettlementType = new FilterInfo();
				filterSettlementType.getFilterItems().add(new FilterItemInfo("name", fSettlementTypeNamel2));
				EntityViewInfo viewSettlementType = new EntityViewInfo();
				viewSettlementType.setFilter(filterSettlementType);
				SettlementTypeCollection settlementTypeColl = SettlementTypeFactory.getLocalInstance(context).getSettlementTypeCollection(viewSettlementType);
				if (settlementTypeColl.size() > 0) {
					settlementType = settlementTypeColl.get(0);
				} else {
					// "结算方式对象找不到！"
					throw new TaskExternalException(getResource(context, "Import_fSettlementTypeNamel2"));
				}
				info.setSettlementType(settlementType);
			}
			// 收款单位全称   <由合同编码自动带出> // contractBill.getPartB()该方式不能直接得到收款单位全称对象,只能得到其ID,则需要通过ID查询一次即可
			String supplierID = String.valueOf(contractBill.getPartB().getId()); // 得到收款单位全称的UUID
			if (!StringUtils.isEmpty(supplierID)) {
				// 通过收款单位全称的ID查询其对象<该对象一定存在,有且只有一条数据>
				FilterInfo supplierFilter = new FilterInfo();
				supplierFilter.getFilterItems().add(new FilterItemInfo("id", supplierID));
				EntityViewInfo supplierView = new EntityViewInfo();
				supplierView.setFilter(supplierFilter);
				SupplierCollection supplierColl = SupplierFactory.getLocalInstance(context).getSupplierCollection(supplierView);
				if (supplierColl.size() > 0) {
					info.setSupplier(supplierColl.get(0));
				} else {
					// 如果查询不到结果,则说明该ID对应的收款单位全称对象已经在数据库中不存在!因此不考虑该情况
				}
				
			}
			// 收款账号    
//			if (!fRecAccount.equals("") && fRecAccount != null) {
//				FilterInfo filterBank = new FilterInfo();
//				filterBank.getFilterItems().add(new FilterItemInfo("bankAccount", fRecAccount));
//				EntityViewInfo viewBank = new EntityViewInfo();
//				viewBank.setFilter(filterBank);
//				SupplierCompanyBankCollection bankColl = SupplierCompanyBankFactory.getLocalInstance(context).getSupplierCompanyBankCollection(viewBank);
//				if (bankColl.size() > 0) {
//					bank = bankColl.get(0);
					info.setRecAccount(fRecAccount);
					// 收款银行  <由收款账号自动带出>
					info.setRecBank(fRecBank);
//				} else {
//					// 1 "该收款账号 "
//					// 2 " 在系统中不存在！"
//					throw new TaskExternalException(getResource(context, "Import_fRecAccount11") + fRealSupplierNamel2 + getResource(context, "Import_NOTNULl"));
//				}
//			}
			// 摘要
			info.setDescription(fDescription);
			// 实际收款单
			FilterInfo filtersupplier = new FilterInfo();
			filtersupplier.getFilterItems().add(new FilterItemInfo("number", fRealSupplierNamel2));
			EntityViewInfo viewsupplier = new EntityViewInfo();
			viewsupplier.setFilter(filtersupplier);
			SupplierCollection supplierColl = SupplierFactory.getLocalInstance(context).getSupplierCollection(viewsupplier);
			if (supplierColl.size() > 0) {
				supplier = supplierColl.get(0);
			} else {
				// 1 "实际收款单位不存在,或者该实际收款单位 "
				// 2 " 在系统中不存在！"
				throw new TaskExternalException(getResource(context, "Import_fRealSupplierNamel21") + fRealSupplierNamel2 + getResource(context, "Import_NOTNULl"));
			}
			info.setRealSupplier(supplier);
			// 用途 
			info.setUsage(fUsage);
			// 币别编码
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
			// 汇率  <取合同汇率>
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			ExchangeRateInfo exchangeRateInfo = new ExchangeRateInfo();
			if (StringUtils.isEmpty(fExchangeRate)) {  // 如果汇率为空，则取当前币别汇率
				if ("RMB".equals(currency.getNumber()) || "CNY".equals(currency.getIsoCode())) {
					fExchangeRate = "1.00";
				} else {
					// 查询目标币别 <即固定原币>
					CurrencyCollection rmbColl = null;
					FilterInfo rmbFilter = new FilterInfo();
					EntityViewInfo rmbView = new EntityViewInfo();
					rmbFilter.getFilterItems().add(new FilterItemInfo("number", "RMB"));
					rmbView.setFilter(rmbFilter);
					rmbColl = CurrencyFactory.getLocalInstance(context).getCurrencyCollection(rmbView);
					CurrencyInfo rmb = rmbColl.get(0);
					filter.getFilterItems().add(new FilterItemInfo("targetCurrency", rmb.getId(), CompareType.EQUALS));//目标币别
					filter.getFilterItems().add(new FilterItemInfo("SourceCurrency", currency.getId(), CompareType.EQUALS));//原币别
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
					// 汇率录入不合法
					throw new TaskExternalException(getResource(context, "Import_fExRate10"));
				}
			}
			info.setExchangeRate(contractBill.getExRate());
			// 进度款付款比例
			info.setPaymentProportion(new BigDecimal(fConPayplanPayProportion));
			// 保修金金额(如果不填,则默认由合同带出)
			if (!StringUtils.isEmpty(fGrtAmount)) {
				info.setGrtAmount(new BigDecimal(fGrtAmount));
			} else {
				info.setGrtAmount(contractBill.getGrtAmount());
			}
			// 本期完工工程量
			info.setCompletePrjAmt(new BigDecimal((FDCTransmissionHelper.strToDouble(fProjectPriceInContractOri) / FDCTransmissionHelper.strToDouble(fConPayplanPayProportion)) * 100));
			// 是否加急 
			if (fUrgentDegree.equals(getResource(context, "YES"))) {
				info.setUrgentDegree(UrgentDegreeEnum.URGENT);
			} else {
				info.setUrgentDegree(UrgentDegreeEnum.NORMAL);
			}
			// 发票号
			info.setInvoiceNumber(fInvoiceNumber);
			// 发票金额 
			if (!StringUtils.isEmpty(fInvoiceAmt)) {
				info.setInvoiceAmt(new BigDecimal(fInvoiceAmt));
			} else {
				info.setInvoiceAmt(bigDecimal);
			}
			// 开票日期
			if (!StringUtils.isEmpty(fInvoiceDate)) {
				info.setInvoiceDate(FDCTransmissionHelper.checkDateFormat(fInvoiceDate, getResource(context, "Import_kprqcw")));
			}
			// 形象进度描述
			info.setProcess(fProcess);
			// 备注
			info.setMoneyDesc(fMoneyDesc);
			// 项目名称 <由合同编码自动带出>   // contractBill.getCurProject()该方式不能直接得到项目名称对象,只能得到其ID,则需要通过ID查询一次即可
			String curProjectID = String.valueOf(contractBill.getCurProject().getId()); // 得到收款单位全称的UUID
			if (!StringUtils.isEmpty(curProjectID)) {
				// 通过收款单位全称的ID查询其对象<该对象一定存在,有且只有一条数据>
				FilterInfo curProjectFilter = new FilterInfo();
				curProjectFilter.getFilterItems().add(new FilterItemInfo("id", curProjectID));
				EntityViewInfo curProjectView = new EntityViewInfo();
				curProjectView.setFilter(curProjectFilter);
				CurProjectCollection curProjectColl = CurProjectFactory.getLocalInstance(context).getCurProjectCollection(curProjectView);
				if (curProjectColl.size() > 0) {
					info.setCurProject(curProjectColl.get(0));
				} else {
					// 如果查询不到结果,则说明该ID对应的收款单位全称对象已经在数据库中不存在!因此不考虑该情况
				}
				
			}
			// 合同名称  <由合同编码自动带出>
			info.setContractName(contractBill.getName());

			// 变更签证确认金额
			if (!StringUtils.isEmpty(fChangeAmt)) {
				info.setChangeAmt(new BigDecimal(fChangeAmt));
			} else {
				info.setChangeAmt(bigDecimal);
			}
			// 结算金额
			if (!StringUtils.isEmpty(fSettleAmt)) {
				info.setSettleAmt(new BigDecimal(fSettleAmt));
			} else {
				info.setSettleAmt(bigDecimal);
			}
			// 合同付款次数
			if (!StringUtils.isEmpty(fPayTimes)) {
				info.setPayTimes(Integer.parseInt(fPayTimes));
			} else {
				info.setPayTimes(0);
			}
			// 合同内工程款_本次申请原币    
			info.setProjectPriceInContractOri(new BigDecimal(fProjectPriceInContractOri));
			// 本期计划付款    
			if (!StringUtils.isEmpty(fCurPlannedPayment)) {
				info.setCurPlannedPayment(new BigDecimal(fCurPlannedPayment));
			} else {
				info.setCurPlannedPayment(bigDecimal);
			}
			// 本期欠付款    
			if (!StringUtils.isEmpty(fCurBackPay)) {
				info.setCurBackPay(new BigDecimal(fCurBackPay));
			} else {
				info.setCurBackPay(bigDecimal);
			}
			// 本次申请%    
			if (!StringUtils.isEmpty(FCurReqPercent)) {
				info.setCurReqPercent(new BigDecimal(FCurReqPercent));
			} else {
				info.setCurReqPercent(new BigDecimal(FDCTransmissionHelper.strToDouble(contractBill.getAmount().toString()) / FDCTransmissionHelper.strToDouble(fProjectPriceInContractOri)));
			}
			// 累计申请%    
			if (!StringUtils.isEmpty(fAllReqPercent)) {
				info.setAllReqPercent(new BigDecimal(fAllReqPercent));
			} else {
				info.setAllReqPercent(new BigDecimal(FDCTransmissionHelper.strToDouble(contractBill.getAmount().toString()) / FDCTransmissionHelper.strToDouble(fProjectPriceInContractOri)));
			}
			// 制单人编码    
			FilterInfo filtercreateUser = new FilterInfo();
			filtercreateUser.getFilterItems().add(new FilterItemInfo("number", fCreatorNumber));
			EntityViewInfo viewcreateUser = new EntityViewInfo();
			viewcreateUser.setFilter(filtercreateUser);
			UserCollection createUserColl = UserFactory.getLocalInstance(context).getUserCollection(viewcreateUser);
			if (createUserColl.size() > 0){
				createUser = createUserColl.get(0);
				info.setCreator(createUser);
			} else {
				// 1 "制单人编码为空,或者制单人编码 "
				// 2 " 在系统中不存在！"
				throw new TaskExternalException(getResource(context, "Import_fCreatorNumber1") + fCreatorNumber + getResource(context, "Import_NOTNULl"));
			}
			// 制单时间    
			info.setCreateTime(new Timestamp(FDCTransmissionHelper.checkDateFormat(fCreateTime, getResource(context, "Import_zdrqcw")).getTime()));
			// <附件_默认无附件>
			info.setAttachment(0);
			// <是否生成凭证_默认否>
			info.setFivouchered(false);
			// <应扣甲供材料款（本期发生）_TODO>
			info.setPayPartAMatlAmt(bigDecimal);
			// <合同内工程款（本期发生）_合同内工程款_本次申请原币>
			info.setProjectPriceInContract(new BigDecimal(fProjectPriceInContractOri));
			// <甲供材累计申请款_为零>
			info.setPayPartAMatlAllReqAmt(bigDecimal);
			// <是否付清_设置默认>
			info.setHasPayoff(false);
			// <已关闭_设置默认>
			info.setHasClosed(false);
			// <本位币币别_取合同币别>
			info.setLocalCurrency(currency);
			// 应付申请%    ffffff
			
			// 最新造价 
			info.setContractPrice(contractBill.getAmount());
			if (!StringUtils.isEmpty(fLatestPrice)) {
				info.setLatestPrice(new BigDecimal(fLatestPrice));
			} else {
				// 由合同自动带出
				info.setLatestPrice(contractBill.getAmount());
			}
			// 本申请单已付金额 
			if (!StringUtils.isEmpty(fPayedAmt)) {
				info.setPayedAmt(new BigDecimal(fPayedAmt));
			} else {
				// 有合同自动带出
				info.setPayedAmt(bigDecimal);
			}
			// 实付款本期发生额
			info.setCurPaid(new BigDecimal(fProjectPriceInContractOri));
			// 合同内工程累计申请
			info.setPrjAllReqAmt(new BigDecimal(fProjectPriceInContractOri));
			// 增加工程款累计申请
			info.setAddPrjAllReqAmt(bigDecimal);
			// 单据来源方式
			info.setSourceType(SourceTypeEnum.IMP);
			// 合同内工程款上期累计申请
			info.setLstPrjAllReqAmt(new BigDecimal(FDCTransmissionHelper.strToDouble(info.getCurPaid().toString()) - FDCTransmissionHelper.strToDouble(info.getPrjAllReqAmt().toString())));
//			info.setActPaiedAmount(); // 付款金额原币
//			info.setActPaiedLocAmount(); // 付款金额本币
			
//			// <TODO : 以下四个字段需要添加设置其值的代码。(到目前不能实现,因缺少详细规定的实现方式)>
//			/** 保存付款申请单 */
//			PayRequestBillFactory.getLocalInstance(context).addnew(info);
//			/** 在保存之后进行设置调整款项 */
//			this.updateAdjustClause(info, context);
//			if (fState.equals("已审批")) {
//				/** 设置审批付款申请单 */
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
	 * @description		在单据保存之后设置调整款项<奖励，违约，扣款，甲供材扣款>
	 * @author				雍定文		
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
		
		/* 在提交之后进行设置调整款项 */
		this.updateAdjustClause(coreBaseInfo, context);
		
		/* 在成功修改付款申请单的调整款项之后，设置连动带出付款单 */
		this.auditPayRequestBill(context, coreBaseInfo);
	}
	
	/**
	 * @description		如果单据为提交状态，则连带生成保存状态的付款申请单！
	 * @author				雍定文	
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
				// 得到当前付款申请单的状态，判断该状态是否为已提交状态
				String state = payRequestBillInfo.getState().getValue();
				if (!state.equals("4AUDITTED")) {
					return;
				}
//				/** 设置当前付款申请单为已提交状态 */
//				PayRequestBillFactory.getLocalInstance(context).submit(new ObjectUuidPK(String.valueOf(payRequestBillInfo.getId())), payRequestBillInfo);
				/** 设置审批付款申请单 */
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
	 * @description		在付款申请单保存之后修改其调整款项！	
	 * @author				雍定文	
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
			// 合同内工程款_本次申请原币
			String fProjectPriceInContractOri = ((String) ((DataToken)hashtableAll.get("FProjectPriceInContractOri")).data).trim();
			// 奖励     <存在于奖励单与付款申请单的中间表中>
			String reward = ((String) ((DataToken)hashtableAll.get("reward")).data).trim();
			// 违约     <存在于违约单与付款申请单的中间表中>
			String breach = ((String) ((DataToken)hashtableAll.get("breach")).data).trim();
			// 扣款     <存在于扣款单与付款申请单的中间表中>
			String fine = ((String) ((DataToken)hashtableAll.get("fine")).data).trim();
			// 甲供材扣款   <存在于甲供材扣款与付款申请单的中间表中>
			String stuffFine = ((String) ((DataToken)hashtableAll.get("stuffFine")).data).trim();
			// 查询付款申请单
			FilterInfo pFilter = new FilterInfo();
			pFilter.getFilterItems().add(new FilterItemInfo("number", payRequestBillInfo.getNumber()));
			EntityViewInfo pView = new EntityViewInfo();
			pView.setFilter(pFilter);
			PayRequestBillCollection payRequestBillColl =PayRequestBillFactory.getLocalInstance(context).getPayRequestBillCollection(pView);
			if (payRequestBillColl.size() > 0) {
				info = payRequestBillColl.get(0);
				// 在保存付款申请单之后 设置调整款项<奖励，违约，扣款，甲供材扣款>
				// <TODO : 以下四个字段需要添加设置其值的代码。(到目前不能实现,因缺少详细规定的实现方式)>
				// 奖励        (中间表<T_CON_GuerdonOfPayReqBill>)reward
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
						// 扣款单ID
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
				// 违约        (中间表<T_CON_CompensationOfPayReqBill>)breach
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
						// 违约金ID
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
				// 扣款        (中间表<T_CON_DeductOfPayReqBill>)fine
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
				// 甲供材扣款  (中间表<T_CON_PartAOfPayReqBill>)stuffFine
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
				// 本币金额
				BigDecimal amountAll = new BigDecimal(fProjectPriceInContractOri);
				BigDecimal all = amountAll.add(new BigDecimal(reward)).subtract(new BigDecimal(breach)).add(new BigDecimal(fine)).add(new BigDecimal(stuffFine));
				info.setAmount(all);
				if (currency.getIsoCode() == null) {
					String id = "dfd38d11-00fd-1000-e000-1ebdc0a8100dDEB58FDC";
					currency.setId(BOSUuid.read(id));
					currency.setIsoCode("RMB");
				}
				// 大写金额
				info.setCapitalAmount(FDCHelper.transCap(currency, all));
				// 原币金额
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
	 * @description		判断输入的字符类型是否属于数字型
	 * @author				雍定文
	 * @createDate			2011-6-15
	 * @param 				value			需要判断的字符串
	 * @param 				fieldName		要判断的字符串名称
	 * @version			EAS1.0
	 * @throws 			TaskExternalException 
	 * @see
	 */
	private void strTOnumber(String value, String fieldName, Context context) throws TaskExternalException {
		if (value.matches("^\\d+(\\.\\d+)?$")) {  // 判断是否是纯数字型
			// 如果value值不是数值型, 切不满足17,2的形式时抛异常
			if (!value.matches("^([1-9]\\d{0,15}\\.\\d{0,2})|(0\\.\\d{0,2})||([1-9]\\d{0,15})||0$")) {  // 检查输入的数字型是否符合要求
				// "必须以 1－17 位整数或加 1－2位小数构成！"
				throw new TaskExternalException(fieldName + getResource(context, "Import_strTOnumber"));
			}
		} else {
			// "应该录入数字型！"
			throw new TaskExternalException(fieldName + getResource(context, "Import_strTOnumber_number"));
		}
	}
	
	/**
	 * 得到资源文件
	 */
	public static String getResource(Context context, String key) {
		return ResourceBase.getString(resource, key, context.getLocale());
	}
	
}
