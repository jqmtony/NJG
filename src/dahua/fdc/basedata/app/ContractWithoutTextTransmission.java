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
 * 版权：			金蝶国际软件集团有限公司版权所有		 	
 * 描述：			无文本合同——————引入引出工具类
 *		
 * @author			雍定文
 * @version		EAS7.0		
 * @createDate		2011-6-16	 
 * @see						
 */
public class ContractWithoutTextTransmission extends AbstractDataTransmission {
	private static final Logger logger = Logger.getLogger(ContractWithoutTextTransmission.class);

	/** 资源文件 */
	private static String resource = "com.kingdee.eas.fdc.basedata.ContractWTextResource";
	
	/** 审批人 */
	private UserInfo auditoreUser = null;
	/** 制单人 */
	private UserInfo createUser = null;
	/** 工程项目 */
	private CurProjectInfo curProject = null;
	/** 结算方式 */
	private SettlementTypeInfo settlementType = null;
	/** 收款单位 */
	private SupplierInfo supplier = null;
	/** 付款申请单 */
	private PayRequestBillInfo payRequestBill = null;
	/** 付款单 */
	private PaymentBillInfo paymentBill = null;
	/**  合同类型 */
	private ContractTypeInfo contractType = null;
	/** 付款类型 */
	private PaymentTypeInfo paymentType = null;
	/** 用款部门 */
	private AdminOrgUnitInfo adminOrgUnit = null;
	/** 币种 */
	private CurrencyInfo currency = null;
	/** 付款申请单编码 */
	private String payReqNumber = new String("");
	/** 全局的<hashtable>数据访问 */
	private Hashtable hashtableAll = null;
	/** 定义初始金额为0 */
	private BigDecimal bigDecimal = new BigDecimal("0.00");
	/** 奖励单 */
	private GuerdonBillInfo guerdonBill = null;
	/** 付款申请单对应的奖励项 */
	private GuerdonOfPayReqBillInfo guerdonOfPayReqBill = null;
	/** 违约金 */
	private CompensationBillInfo compensationBill = null;
	/** 付款申请单对应的违约金 */
	private CompensationOfPayReqBillInfo compensationOfPayReqBill = null;
	/** 付款申请单对应的扣款项 */
	private DeductOfPayReqBillInfo deductOfPayReqBill = null;
	/** 付款申请单对应的甲供材项 */
	private PartAOfPayReqBillInfo partAOfPayReqBill = null;
	
	/**
	 * @description		
	 * @author				雍定文		
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
	 * @description		无文本合同获取参数并且保存
	 * @author				雍定文		
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
		
		// 组织编码
		String fOrgUnitNumber  = ((String) ((DataToken)hashtable.get("FOrgUnit_number")).data).trim();
		// 工程项目编码
		String fCurProjectLongNumber  = ((String) ((DataToken)hashtable.get("FCurProject_longNumber")).data).trim();
		// 编码
		String fNumber  = ((String) ((DataToken)hashtable.get("FNumber")).data).trim();
		// 名称
		String fName  = ((String) ((DataToken)hashtable.get("FName")).data).trim();
		// 状态
		String fState  = ((String) ((DataToken)hashtable.get("FState")).data).trim();
		// 币别编码
		String fCurrencyNumber  = ((String) ((DataToken)hashtable.get("FCurrency_number")).data).trim();
		// 业务日期
		String fBizDate  = ((String) ((DataToken)hashtable.get("FBizDate")).data).trim();
		// 付款类型编码
		String paymentTypeNumber = ((String) ((DataToken)hashtable.get("PaymentTypeNumber")).data).trim();
		// 用款部门编码
		String fUseDepartmentNumber  = ((String) ((DataToken)hashtable.get("FUseDepartment_number")).data).trim();
		// 付款日期
		String fSignDate  = ((String) ((DataToken)hashtable.get("FSignDate")).data).trim();
		// 原币金额
		String fOriginalAmount  = ((String) ((DataToken)hashtable.get("FOriginalAmount")).data).trim();
		// 汇率
		String exchangeRate = ((String) ((DataToken)hashtable.get("ExchangeRate")).data).trim();
		// 本位币金额
		String fAmount  = ((String) ((DataToken)hashtable.get("FAmount")).data).trim();
		// 结算方式
		String fSettlementTypeNumber  = ((String) ((DataToken)hashtable.get("FSettlementType_number")).data).trim();
		// 收款单位编码
		String fReceiveUnitName  = ((String) ((DataToken)hashtable.get("FReceiveUnit_name_l2")).data).trim();
		// 实际收款单位
		String realSupplierNamel2 = ((String) ((DataToken)hashtable.get("RealSupplierNamel2")).data).trim();
		// 收款银行
		String fBank  = ((String) ((DataToken)hashtable.get("FBank")).data).trim();
		// 银行账号
		String fBankAcct  = ((String) ((DataToken)hashtable.get("FBankAcct")).data).trim();
		// 是否进入动态成本
		String fIsCostSplit  = ((String) ((DataToken)hashtable.get("FIsCostSplit")).data).trim();
		// 是否需要付款
		String fIsNeedPaid  = ((String) ((DataToken)hashtable.get("FIsNeedPaid")).data).trim();
		// 无需付款原因
		String fNoPaidReason  = ((String) ((DataToken)hashtable.get("FNoPaidReason")).data).trim();
		// 附件
		String attachment = ((String) ((DataToken)hashtable.get("Attachment")).data).trim();
		// 款项说明
		String moneyDesc = ((String) ((DataToken)hashtable.get("MoneyDesc")).data).trim();
		// 发票号
		String fInvoiceNumber  = ((String) ((DataToken)hashtable.get("FInvoiceNumber")).data).trim();
		// 发票金额
		String fInvoiceAmt  = ((String) ((DataToken)hashtable.get("FInvoiceAmt")).data).trim();
		// 开票日期
		String fInvoiceDate  = ((String) ((DataToken)hashtable.get("FInvoiceDate")).data).trim();
		// 累计发票金额
		String fAllInvoiceAmt  = ((String) ((DataToken)hashtable.get("FAllInvoiceAmt")).data).trim();
		// 备注
		String fDescription  = ((String) ((DataToken)hashtable.get("FDescription")).data).trim();
		// 制单人
		String fCreatorName = ((String) ((DataToken)hashtable.get("FCreator_name_l2")).data).trim();
		// 制单时间
		String fCreateTime  = ((String) ((DataToken)hashtable.get("FCreateTime")).data).trim();
		// 审核人
		String fAuditorName  = ((String) ((DataToken)hashtable.get("FAuditor_name_l2")).data).trim();
		// 审批时间
		String fAuditTime  = ((String) ((DataToken)hashtable.get("FAuditTime")).data).trim();
		
		
		/**
		 * 判断非空以及字段长度
		 */
		// 组织编码  fOrgUnitNumber
		if (!StringUtils.isEmpty(fOrgUnitNumber)) {
			if (fOrgUnitNumber.length() > 40) {
				// "组织编码长度不能超过40！"
				throw new TaskExternalException(getResource(context, "CWT_Import_fOrgUnitNumber"));
			}
		}
		// 工程项目编码 fCurProjectLongNumber
		if (!StringUtils.isEmpty(fCurProjectLongNumber)) {
			if (fCurProjectLongNumber.length() > 40) {
				// "工程项目编码长度不能超过40！"
				throw new TaskExternalException(getResource(context, "CWT_Import_fCurProjectLongNumber"));
			}
		} else {
			// "工程项目编码不能为空！"
			throw new TaskExternalException(getResource(context, "CWT_Import_fCurProjectLongNumber1"));
		}
		// 编码 fNumber
		if (!StringUtils.isEmpty(fNumber)) {
			if (fNumber.length() > 80) {
				// "编码长度不能超过80！"
				throw new TaskExternalException(getResource(context, "CWT_Import_fNumber"));
			}
		} else {
			// "编码不能为空！"
			throw new TaskExternalException(getResource(context, "CWT_Import_fNumber1"));
		}
		// 名称 fName
		if (!StringUtils.isEmpty(fName)) {
			if (fName.length() > 80) {
				// "名称长度不能超过80！"
				throw new TaskExternalException(getResource(context, "CWT_Import_fName"));
			}
		} else {
			// "名称不能为空！"
			throw new TaskExternalException(getResource(context, "CWT_Import_fName1"));
		}
		// 币别编码 fCurrencyNumber
		if (!StringUtils.isEmpty(fCurrencyNumber)) {
			if (fCurrencyNumber.length() > 40) {
				// "币别编码长度不能超过40！"
				throw new TaskExternalException(getResource(context, "CWT_Import_fCurrencyNumber"));
			}
		}
		// 业务日期 fBizDate
		if (StringUtils.isEmpty(fBizDate)) {
			// "业务日期不能为空！"
			throw new TaskExternalException(getResource(context, "CWT_Import_fBizDate"));
		}
		// 付款类型编码 paymentTypeNumber
		if (!StringUtils.isEmpty(paymentTypeNumber)) {
			if (paymentTypeNumber.length() > 40) {
				// "付款类型编码长度不能超过40！"
				throw new TaskExternalException(getResource(context, "CWT_Import_paymentTypeNumber"));
			}
		} else {
			// "付款类型编码不能为空！"
			throw new TaskExternalException(getResource(context, "CWT_Import_paymentTypeNumber1"));
		}
		// 用款部门编码 fUseDepartmentNumber
		if (!StringUtils.isEmpty(fUseDepartmentNumber)) {
			if (fUseDepartmentNumber.length() >40) {
				// "用款部门长度不能超过80！"
				throw new TaskExternalException(getResource(context, "CWT_Import_fUseDepartmentNumber"));
			}
		} else {
			// "用款部门不能为空！"
			throw new TaskExternalException(getResource(context, "CWT_Import_fUseDepartmentNumber1"));
		}
		// 付款日期 fSignDate
		if (StringUtils.isEmpty(fSignDate)) {
			// "付款日期不能为空！"
			throw new TaskExternalException(getResource(context, "CWT_Import_fSignDate"));
		}
		// 原币金额 fOriginalAmount
		if (!StringUtils.isEmpty(fOriginalAmount)) {
			if (!fOriginalAmount.matches("^([1-9]\\d{0,15}\\.\\d{0,4})|(0\\.\\d{0,4})||([1-9]\\d{0,15})||0$")) {
				// "原币金额录入不合法,必须以 1－19 位整数或加 1－4位小数构成！"
				throw new TaskExternalException(getResource(context, "CWT_Import_fOriginalAmount"));
			}
		} else {
			// "原币金额不能为空！"
			throw new TaskExternalException(getResource(context, "CWT_Import_fOriginalAmount1"));
		}
		// 汇率 exchangeRate
		if (!StringUtils.isEmpty(exchangeRate)) {
			if (!exchangeRate.matches("^([1-9]\\d{0,18}\\.\\d{0,10})|(0\\.\\d{0,10})||([1-9]\\d{0,18})||0$")) {
				// "汇率录入不合法,必须以 1－28 位整数或加 1－10位小数构成！"
				throw new TaskExternalException(getResource(context, "CWT_Import_exchangeRate"));
			}
		}
		// 本位币金额 fAmount
		if (!StringUtils.isEmpty(fAmount) && new BigDecimal(fAmount).compareTo(new BigDecimal("0")) == 0) {
			if (!fAmount.matches("^([1-9]\\d{0,15}\\.\\d{0,4})|(0\\.\\d{0,4})||([1-9]\\d{0,15})||0$")) {
				// "本位币金额录入不合法,必须以 1－19 位整数或加 1－4位小数构成！"
				throw new TaskExternalException(getResource(context, "CWT_Import_fAmount"));
			}
		}
		// 结算方式 fSettlementTypeNumber
		if (!StringUtils.isEmpty(fSettlementTypeNumber)) {
			if (fSettlementTypeNumber.length() > 40) {
				// "结算方式长度不能超过40！"
				throw new TaskExternalException(getResource(context, "CWT_Import_fSettlementTypeNumber"));
			}
		}
		// 收款单位编码 fReceiveUnitName
		if (!StringUtils.isEmpty(fReceiveUnitName)) {
			if (fReceiveUnitName.length() > 40) {
				// "收款单位长度不能超过40！"
				throw new TaskExternalException(getResource(context, "CWT_Import_fReceiveUnitName"));
			}
		} else {
			// "收款单位不能为空！"
			throw new TaskExternalException(getResource(context, "CWT_Import_fReceiveUnitName1"));
		}
		// 实际收款单位 realSupplierNamel2
		if (!StringUtils.isEmpty(realSupplierNamel2)) {
			if (realSupplierNamel2.length() > 40) {
				// "实际收款单位长度不能超过40！"
				throw new TaskExternalException(getResource(context, "CWT_Import_realSupplierNamel2"));
			}
		}
		// 收款银行 fBank
		if (!StringUtils.isEmpty(fBank)) {
			if (fBank.length() > 40) {
				// "收款银行长度不能超过40！"
				throw new TaskExternalException(getResource(context, "CWT_Import_fBank"));
			}
		} else {
			// "收款银行不能为空！"
			throw new TaskExternalException(getResource(context, "CWT_Import_fBank1"));
		}
		// 银行账号 fBankAcct
		if (!StringUtils.isEmpty(fBankAcct)) {
			if (fBankAcct.length() > 40) {
				// "银行账号长度不能超过40！"
				throw new TaskExternalException(getResource(context, "CWT_Import_fBankAcct"));
			}
		} else {
			// "银行账号不能为空！"
			throw new TaskExternalException(getResource(context, "CWT_Import_fBankAcct1"));
		}
		// 是否进入动态成本 fIsCostSplit
		// 是否需要付款 fIsNeedPaid
		// 无需付款原因 fNoPaidReason
		// 是否加急 urgentDegree
		// 附件 attachment
		if (!StringUtils.isEmpty(attachment)) {
			if (attachment.length() > 4) {
				// "附件字符长度不能超过4！"
				throw new TaskExternalException(getResource(context, "CWT_Import_attachment"));
			}
		}
		// 款项说明 moneyDesc
		if (!StringUtils.isEmpty(moneyDesc)) {
			if (moneyDesc.length() > 40) {
				// "款项说明字段长度不能超过40！"
				throw new TaskExternalException(getResource(context, "CWT_Import_moneyDesc"));
			}
		}
		// 发票号 fInvoiceNumber
		// 发票金额 fInvoiceAmt
		if (!StringUtils.isEmpty(fInvoiceAmt)) {
			if (!fInvoiceAmt.matches("^([1-9]\\d{0,15}\\.\\d{0,4})|(0\\.\\d{0,4})||([1-9]\\d{0,15})||0$")) {
				// "发票金额录入不合法,必须以 1－19 位整数或加 1－4位小数构成！"
				throw new TaskExternalException(getResource(context, "CWT_Import_fInvoiceAmt"));
			}
		}
		// 开票日期 fInvoiceDate
		// 累计发票金额 fAllInvoiceAmt
		if (!StringUtils.isEmpty(fAllInvoiceAmt)) {
			if (!fAllInvoiceAmt.matches("^([1-9]\\d{0,15}\\.\\d{0,4})|(0\\.\\d{0,4})||([1-9]\\d{0,15})||0$")) {
				// "累计发票金额录入不合法,必须以 1－19 位整数或加 1－4位小数构成！"
				throw new TaskExternalException(getResource(context, "CWT_Import_fAllInvoiceAmt"));
			}
		}
		// 备注 fDescription
		if (!StringUtils.isEmpty(fDescription)) {
			if (fDescription.length() > 40) {
				// "备注长度不能超过40！"
				throw new TaskExternalException(getResource(context, "CWT_Import_fDescription"));
			}
		}
		// 制单人 fCreatorName
		if (!StringUtils.isEmpty(fCreatorName)) {
			if (fCreatorName.length() > 40) {
				// "制单人编码长度不能超过40！"
				throw new TaskExternalException(getResource(context, "CWT_Import_fCreatorName"));
			}
		} else {
			// "制单人编码不能为空！"
			throw new TaskExternalException(getResource(context, "CWT_Import_fCreatorName1"));
		}
		// 制单时间 fCreateTime
		if (StringUtils.isEmpty(fCreateTime)) {
			// "制单时间不能为空！"
			throw new TaskExternalException(getResource(context, "CWT_Import_fCreateTime"));
		}
		// 审批人编码
		if (!StringUtils.isEmpty(fAuditorName)) {
			if (fAuditorName.length() > 40) {
				// 审批人编码字段长度不能超过40！
				throw new TaskExternalException(getResource(context, "CWT_Import_sprbmgc"));
			}
		}
		
		/**
		 * 将值设置到对象中
		 */
		try {
			// 工程项目编码
			FilterInfo filterCurProject = new FilterInfo();
			filterCurProject.getFilterItems().add(new FilterItemInfo("longnumber", fCurProjectLongNumber.replace('.', '!')));
			EntityViewInfo viewCurProject = new EntityViewInfo();
			viewCurProject.setFilter(filterCurProject);
			CurProjectCollection curProjectBillColl = CurProjectFactory.getLocalInstance(context).getCurProjectCollection(viewCurProject);
			if (curProjectBillColl.size() > 0){
				curProject = curProjectBillColl.get(0);
				info.setCurProject(curProject);
				// 组织长编码
				CostCenterOrgUnitInfo costCenterOrgUnit = curProject.getCostCenter(); //  工程项目对应成本中心组织
				FilterInfo filter1 = new FilterInfo();
				filter1.getFilterItems().add(new FilterItemInfo("id", String.valueOf(costCenterOrgUnit.getId())));
				EntityViewInfo view1 = new EntityViewInfo();
				view1.setFilter(filter1);
				CostCenterOrgUnitCollection ccouc = CostCenterOrgUnitFactory.getLocalInstance(context).getCostCenterOrgUnitCollection(view1);
				if (!StringUtils.isEmpty(fOrgUnitNumber) && !fOrgUnitNumber.replace('.', '!').equals(ccouc.get(0).getLongNumber())) {
					// "组织长编码在工程项目所对应的成本中心不存在!"
 					throw new TaskExternalException(getResource(context, "Import_zzbmbcz"));
					// 原转换组织对象方法 <暂不推荐使用> (FullOrgUnitInfo)FullOrgUnitInfo.class.cast(costCenterOrgUnit)
				}
				info.setOrgUnit(ccouc.get(0).castToFullOrgUnitInfo());
			} else {
				// 1 "工程项目编码为空,或该工程项目编码 "
				// 2 " 在系统中不存在！"
				throw new TaskExternalException(getResource(context, "CWT_Import_fCurProjectLongNumber2") + fCurProjectLongNumber + getResource(context, "CWT_Import_NOTNULL"));
			}
			// 编码
			info.setNumber(fNumber);
			// 名称重复
			FilterInfo filterContractWithoutText = new FilterInfo();
			filterContractWithoutText.getFilterItems().add(new FilterItemInfo("curProject.id", curProject.getId().toString()));
			filterContractWithoutText.getFilterItems().add(new FilterItemInfo("name", fName));
			EntityViewInfo viewContractWithoutText = new EntityViewInfo();
			viewContractWithoutText.setFilter(filterContractWithoutText);
			ContractWithoutTextCollection contractWithoutTextColl = ContractWithoutTextFactory.getLocalInstance(context).getContractWithoutTextCollection(viewContractWithoutText);
			if (contractWithoutTextColl.size() > 0){
				// 名称重复时，提示：单据名称已经存在，不可重复！
				throw new TaskExternalException(getResource(context, "CWT_Import_fNameCF"));
			}
			// 名称
			info.setName(fName);
			// NULL
			info.setNull(fNumber + fName);
			//状态 fState
			if (fState.equals(getResource(context, "CWT_Import_saved"))) {
				info.setState(FDCBillStateEnum.SAVED);
			} else if (fState.equals(getResource(context, "CWT_Import_submitted"))) {
				info.setState(FDCBillStateEnum.SUBMITTED);
			} else if (fState.equals(getResource(context, "CWT_Import_auditted")) || StringUtils.isEmpty(fState)) {
				info.setState(FDCBillStateEnum.AUDITTED);
				// 审批人 <"状态"字段需求文档中不存在,需要修改需求文档添加该字段>
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
						// 1 "审批人编码 "
						// 2 " 在系统中不存在！"
						throw new TaskExternalException(getResource(context, "CWT_Import_fAuditorName2") + fAuditorName + getResource(context, "CWT_Import_NOTNULL"));
					}
				} else {
					// "在已审批状态下的审批人字段不能为空！"
					throw new TaskExternalException(getResource(context, "CWT_Import_fAuditorName3"));
				}
				//审批时间
				if (!StringUtils.isEmpty(fAuditTime)) {
					info.setCreateTime(new Timestamp(FDCTransmissionHelper.checkDateFormat(fAuditTime, getResource(context, "CWT_Import_spsjcw")).getTime()));
				} else {
					// "在已审批状态下的审批日期字段不能为空！"
					throw new TaskExternalException(getResource(context, "CWT_Import_fAuditTime3"));
				}
			} else {
				// 1 "状态 "
				// 2 " 在系统中不存在！"
				throw new TaskExternalException(getResource(context, "CWT_Import_fState") + fState + getResource(context, "CWT_Import_NOTNULL"));
			}
			// 币别编码 <如果没有录入,则默认本位币>
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
				// 该币别编码不存在！
				throw new TaskExternalException(getResource(context, "CWT_Import_gbbbmbcz"));
			}
			// 业务日期 
			info.setBizDate(FDCTransmissionHelper.checkDateFormat(fBizDate, getResource(context, "CWT_Import_ywrqcw")));
			// 付款类型编码
			FilterInfo filterpaymentType = new FilterInfo();
			filterpaymentType.getFilterItems().add(new FilterItemInfo("number", paymentTypeNumber));
			EntityViewInfo viewpaymentType = new EntityViewInfo();
			viewpaymentType.setFilter(filterpaymentType);
			PaymentTypeCollection paymentTypeColl = PaymentTypeFactory.getLocalInstance(context).getPaymentTypeCollection(viewpaymentType);
			if (!(paymentTypeColl.size() > 0)) {
				// "该付款类型编码"
				// "在系统中不存在！"
				throw new TaskExternalException(getResource(context, "CWT_Import_paymentTypeNumber2") + paymentTypeNumber + getResource(context, "CWT_Import_NOTNULL"));
			}
			paymentType = paymentTypeColl.get(0);
			// 用款部门编码
			FilterInfo filteradminOrgUnit = new FilterInfo();
			filteradminOrgUnit.getFilterItems().add(new FilterItemInfo("number", fUseDepartmentNumber));
			EntityViewInfo viewadminOrgUnit = new EntityViewInfo();
			viewadminOrgUnit.setFilter(filteradminOrgUnit);
			AdminOrgUnitCollection adminOrgUnitColl = AdminOrgUnitFactory.getLocalInstance(context).getAdminOrgUnitCollection(viewadminOrgUnit);
			if (!(adminOrgUnitColl.size() > 0)) {
				// "该用款部门编码"
				// "在系统中不存在！"
				throw new TaskExternalException(getResource(context, "CWT_Import_fUseDepartmentNumber2") + fUseDepartmentNumber + getResource(context, "CWT_Import_NOTNULL"));
			}
			adminOrgUnit = adminOrgUnitColl.get(0);
			info.setUseDepartment(adminOrgUnit);
			// 原币金额
			DecimalFormat amountFormat = new DecimalFormat("#.##");
			BigDecimal quantity = FDCTransmissionHelper.strToBigDecimal(amountFormat.format(new BigDecimal(fOriginalAmount)));
			info.setOriginalAmount(quantity);
			// 汇率 <汇率为空时,取当前币别编码的汇率>
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			ExchangeRateInfo exchangeRateInfo = new ExchangeRateInfo();
			if (StringUtils.isEmpty(exchangeRate)) {  // 如果汇率为空，则取当前币别汇率
				if (currency.getNumber().equals("RMB")) {
					exchangeRate = "1.00";
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
							exchangeRate = bigDecimal.toString();
						}
					} else {
						exchangeRate = "1.00";
					}
				}
			} else {
				if (FDCTransmissionHelper.isRangedInHundred(exchangeRate) == null) {
					// 汇率录入不合法
					throw new TaskExternalException(getResource(context, "Import_fExRate10"));
				}
			}
			// 本位币金额 (如果为空,则结果为当前金额*汇率)
			if (StringUtils.isEmpty(fAmount) || new BigDecimal(fAmount).compareTo(new BigDecimal("0")) == 0) {
				info.setAmount(new BigDecimal(FDCTransmissionHelper.strToDouble(fOriginalAmount) * FDCTransmissionHelper.strToDouble(exchangeRate)));
			} else {
				info.setAmount(new BigDecimal(fAmount));
			}
			// 结算方式
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
			// 收款单位编码
			FilterInfo filterSupplier = new FilterInfo();
			filterSupplier.getFilterItems().add(new FilterItemInfo("number", fReceiveUnitName));
			EntityViewInfo viewSupplier = new EntityViewInfo();
			viewSupplier.setFilter(filterSupplier);
			SupplierCollection supplierColl = SupplierFactory.getLocalInstance(context).getSupplierCollection(viewSupplier);
			if (!(supplierColl.size() > 0)) {
				// "该收款单位编码"
				// "在系统中不存在"
				throw new TaskExternalException(getResource(context, "CWT_Import_fReceiveUnitName2") + fReceiveUnitName + getResource(context, "CWT_Import_NOTNULL"));
			}
			supplier = supplierColl.get(0);
			info.setReceiveUnit(supplier);
			// 实际收款单位 realSupplierNamel2
			if (!StringUtils.isEmpty(realSupplierNamel2)) {
				FilterInfo filterRealSupplier = new FilterInfo();
				filterRealSupplier.getFilterItems().add(new FilterItemInfo("name", realSupplierNamel2));
				EntityViewInfo viewRealSupplier = new EntityViewInfo();
				viewRealSupplier.setFilter(filterRealSupplier);
				SupplierCollection realSupplierColl = SupplierFactory.getLocalInstance(context).getSupplierCollection(viewRealSupplier);
				if (realSupplierColl.size() > 0) {
					supplier = realSupplierColl.get(0);
				} else {
					// 1 "该实际收款单位 "
					// 2 " 在系统中不存在！"
					throw new TaskExternalException(getResource(context, "CWT_Import_realSupplierNamel22") + realSupplierNamel2 + getResource(context, "CWT_Import_NOTNULL"));
				}
			}
			// 银行账号 fBankAcct
//			FilterInfo filterBank = new FilterInfo();
//			filterBank.getFilterItems().add(new FilterItemInfo("number", fBankAcct));
//			EntityViewInfo viewBank = new EntityViewInfo();
//			viewBank.setFilter(filterBank);
//			SupplierCompanyBankCollection bankColl = SupplierCompanyBankFactory.getLocalInstance(context).getSupplierCompanyBankCollection(viewBank);
//			if (bankColl.size() > 0) {
//				bank = bankColl.get(0);
				info.setBankAcct(fBankAcct);
//				if (fBank.equals(bank.getBank())) {
					// 收款银行 fBank
					info.setBank(fBank);
//				} else {
//					throw new TaskExternalException("银行账号于输入的收款银行不对应！");
//				}
//			} else {
//				throw new TaskExternalException("银行账号" + fBankAcct + "在系统中不存在");
//			}
			// 是否进入动态成本 fIsCostSplit
			if (fIsCostSplit.equals(getResource(context, "CWT_Import_NO"))) {
				// 默认为是,即TRUE
				info.setIsCostSplit(false);
			} else {
				info.setIsCostSplit(true);
			}
			// 是否需要付款 fIsNeedPaid
			if (fIsNeedPaid.equals(getResource(context, "CWT_Import_NO"))) {
				info.setIsNeedPaid(true);
			} else {
				// 默认为是,即TRUE
				info.setIsNeedPaid(false);
			}
			// 无需付款原因 fNoPaidReason
			info.setNoPaidReason(fNoPaidReason);
			// 是否加急 urgentDegree
			// 附件 attachment
			// 款项说明 moneyDesc
			// 发票号 fInvoiceNumber
			if (!StringUtils.isEmpty(fInvoiceNumber)) {
				info.setInvoiceNumber(fInvoiceNumber);
			}
			// 发票金额 fInvoiceAmt
			if (!StringUtils.isEmpty(fInvoiceAmt)) {
				info.setInvoiceAmt(new BigDecimal(fInvoiceAmt));
			}
			// 开票日期 fInvoiceDate
			if (!StringUtils.isEmpty(fInvoiceDate)) {
				info.setInvoiceDate(FDCTransmissionHelper.checkDateFormat(fInvoiceDate, getResource(context, "CWT_Import_kprqcw")));
			}
			// 累计发票金额 fAllInvoiceAmt
			info.setAllInvoiceAmt(FDCTransmissionHelper.strToDouble(fAllInvoiceAmt));
			// 备注 fDescription
			info.setDescription(fDescription);
			// 制单人 fCreatorName
			FilterInfo filterCreateUser = new FilterInfo();
			filterCreateUser.getFilterItems().add(new FilterItemInfo("number", fCreatorName));
			EntityViewInfo viewCreateUser = new EntityViewInfo();
			viewCreateUser.setFilter(filterCreateUser);
			UserCollection createUserColl = UserFactory.getLocalInstance(context).getUserCollection(viewCreateUser);
			if (!(createUserColl.size() > 0)) {
				// 制单人编码   
				// 在系统中不存在
				throw new TaskExternalException(getResource(context, "CWT_Import_fCreatorName3") + fCreatorName + getResource(context, "CWT_Import_NOTNULL"));
			}
			createUser = createUserColl.get(0);
			info.setCreator(createUser);
			// 制单时间 fCreateTime
			info.setCreateTime(new Timestamp(FDCTransmissionHelper.checkDateFormat(fCreateTime, getResource(context, "CWT_Import_zdrqcw")).getTime()));
//			// 是否成本拆分 默认为是
//			info.setIsCostSplit(true);
			// 待处理状态 默认为 COMMON--正常
			info.setConSplitExecState(ConSplitExecStateEnum.COMMON);
			// 生成方式 默认为 0--系统导入
			info.setSourceType(SourceTypeEnum.IMP);
			// 合同类型 默认为 无文本(name = '无文本')
			FilterInfo filter14 = new FilterInfo();
			filter14.getFilterItems().add(new FilterItemInfo("name", "无文本"));
			EntityViewInfo view14 = new EntityViewInfo();
			view14.setFilter(filter14);
			ContractTypeCollection contractTypeColl = ContractTypeFactory.getLocalInstance(context).getContractTypeCollection(view14);
			if (contractTypeColl.size() > 0) {
				contractType = contractTypeColl.get(0);
				info.setContractType(contractType);
			} else {
				// "系统中不存在合同类型为<无文本>'的对象,请在系统中增加合同类型为<无文本>的对象后再尝试导入！"
				throw new TaskExternalException(getResource(context, "CWT_Import_contractTypeColl"));
			}
			// 拆分类型 默认为 未拆分--NOSPLIT
			info.setSplitState(CostSplitStateEnum.NOSPLIT);
			// 是否暂缓 默认 否
			info.setIsRespite(false);		
			info.setSignDate(new Timestamp(FDCTransmissionHelper.checkDateFormat(fCreateTime, "").getTime()));
			
			/** 新增无文本合同 */
			ContractWithoutTextFactory.getLocalInstance(context).addnew(info);
			/** 在保存无文本合同之后进行设置付款申请单以及由付款申请单带出付款单 */
			this.createPayRequestBill(hashtableAll, context, info);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new TaskExternalException(e.getMessage(), e);
		}
		return null;
	}
	
//	/**
//	 * @description		在无文本合同提交后设置自动生成付款申请单
//	 * @author				雍定文		
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
	 * @description		设置付款申请单需要新增的数据<在无文本合同单据导入时,需要创建一张关联表在付款申请单中>
	 * @author				雍定文	
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
		
		// 组织编码
		String fOrgUnitNumber  = ((String) ((DataToken)hashtable.get("FOrgUnit_number")).data).trim();
		// 编码
		String fNumber  = ((String) ((DataToken)hashtable.get("FNumber")).data).trim();
		// 名称
		String fName  = ((String) ((DataToken)hashtable.get("FName")).data).trim();
		// 状态
		String fState  = ((String) ((DataToken)hashtable.get("FState")).data).trim();
		// 付款日期
		String fSignDate  = ((String) ((DataToken)hashtable.get("FSignDate")).data).trim();
		// 原币金额
		String fOriginalAmount  = ((String) ((DataToken)hashtable.get("FOriginalAmount")).data).trim();
		// 汇率
		String exchangeRate = ((String) ((DataToken)hashtable.get("ExchangeRate")).data).trim();
		// 结算方式
		String fSettlementTypeNumber  = ((String) ((DataToken)hashtable.get("FSettlementType_number")).data).trim();
		// 收款单位编码
		String fReceiveUnitName  = ((String) ((DataToken)hashtable.get("FReceiveUnit_name_l2")).data).trim();
		// 本位币金额
		String fAmount  = ((String) ((DataToken)hashtable.get("FAmount")).data).trim();
		// 实际收款单位
		String realSupplierNamel2 = ((String) ((DataToken)hashtable.get("RealSupplierNamel2")).data).trim();
		// 收款银行
		String fBank  = ((String) ((DataToken)hashtable.get("FBank")).data).trim();
		// 银行账号
		String fBankAcct  = ((String) ((DataToken)hashtable.get("FBankAcct")).data).trim();
		// 是否需要付款
		String fIsNeedPaid  = ((String) ((DataToken)hashtable.get("FIsNeedPaid")).data).trim();
		// 附件
		String attachment = ((String) ((DataToken)hashtable.get("Attachment")).data).trim();
		// 款项说明
		String moneyDesc = ((String) ((DataToken)hashtable.get("MoneyDesc")).data).trim();
		// 发票号
		String fInvoiceNumber  = ((String) ((DataToken)hashtable.get("FInvoiceNumber")).data).trim();
		// 发票金额
		String fInvoiceAmt  = ((String) ((DataToken)hashtable.get("FInvoiceAmt")).data).trim();
		// 开票日期
		String fInvoiceDate  = ((String) ((DataToken)hashtable.get("FInvoiceDate")).data).trim();
		// 累计发票金额
		String fAllInvoiceAmt  = ((String) ((DataToken)hashtable.get("FAllInvoiceAmt")).data).trim();
		// 备注
		String fDescription  = ((String) ((DataToken)hashtable.get("FDescription")).data).trim();
		// 制单时间
		String fCreateTime  = ((String) ((DataToken)hashtable.get("FCreateTime")).data).trim();
		// 审批时间
		String fAuditTime  = ((String) ((DataToken)hashtable.get("FAuditTime")).data).trim();
		// 是否加急
		String urgentDegree = ((String) ((DataToken)hashtable.get("UrgentDegree")).data).trim();
		
		/**
		 * 设置所需要的参数值
		 */
		try {
			// 删除之前的多余付款申请单
			String sql = "delete from T_CON_PayRequestBill where FContractId = ? ";
			Object[] params = new Object[] { fNumber };
			DbUtil.execute(context, sql, params);
			checkNumber(context, fNumber);
			// 付款申请单编码重复性校验
			String number = payReqNumber;
			// 付款类型
			payRequestBill.setPaymentType(paymentType);
			// 用款部门
			payRequestBill.setUseDepartment(adminOrgUnit);
			// 付款申请单编码
			payRequestBill.setNumber(number);
			// 付款日期
			payRequestBill.setPayDate(FDCTransmissionHelper.checkDateFormat(fSignDate, getResource(context, "CWT_Import_fkrqcw")));
			// 收款单位
			FilterInfo filterSupplier = new FilterInfo();
			filterSupplier.getFilterItems().add(new FilterItemInfo("number", fReceiveUnitName));
			EntityViewInfo viewSupplier = new EntityViewInfo();
			viewSupplier.setFilter(filterSupplier);
			SupplierCollection supplierColl = SupplierFactory.getLocalInstance(context).getSupplierCollection(viewSupplier);
			if (!(supplierColl.size() > 0)) {
				// "该收款单位编码"
				// "在系统中不存在"
				throw new TaskExternalException(getResource(context, "CWT_Import_fReceiveUnitName2") + fReceiveUnitName + getResource(context, "CWT_Import_NOTNULL"));
			}
			supplier = supplierColl.get(0);
			payRequestBill.setSupplier(supplier);
			// 实际收款单位
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
					// 1 "该实际收款单位 "
					// 2 " 在系统中不存在！"
					throw new TaskExternalException(getResource(context, "CWT_Import_realSupplierNamel22") + realSupplierNamel2 + getResource(context, "CWT_Import_NOTNULL"));
				}
			}
			// 结算方式
			if (!StringUtils.isEmpty(fSettlementTypeNumber)) {
				payRequestBill.setSettlementType(settlementType);
			}
			// 收款银行
			payRequestBill.setRecBank(fBank);
			// 收款帐号
			payRequestBill.setRecAccount(fBankAcct);
			// 紧急程度 <默认为：普通--NORMAL>
			payRequestBill.setUrgentDegree(UrgentDegreeEnum.NORMAL);
			// 币别
			payRequestBill.setCurrency(currency);
			// 汇率 <汇率为空时,取当前币别编码的汇率>
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			ExchangeRateInfo exchangeRateInfo = new ExchangeRateInfo();
			if (StringUtils.isEmpty(exchangeRate)) {  // 如果汇率为空，则取当前币别汇率
				if (currency.getNumber().equals("RMB")) {
					exchangeRate = "1.00";
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
							exchangeRate = bigDecimal.toString();
						}
					} else {
						exchangeRate = "1.00";
					}
				}
			} else {
				if (FDCTransmissionHelper.isRangedInHundred(exchangeRate) == null) {
					// 汇率录入不合法
					throw new TaskExternalException(getResource(context, "Import_fExRate10"));
				}
			}
			payRequestBill.setExchangeRate(new BigDecimal(exchangeRate));
			// 本位币金额 (如果为空,则结果为当前金额*汇率)
			if (StringUtils.isEmpty(fAmount) || new BigDecimal(fAmount).compareTo(new BigDecimal("0")) == 0) {
				fAmount = ((FDCTransmissionHelper.strToDouble(fOriginalAmount) * FDCTransmissionHelper.strToDouble(exchangeRate)) + "").trim();
			}
			// 大写金额
			if (currency.getNumber().equals("RMB")) {
				if (currency.getIsoCode() == null) {
					String id = "dfd38d11-00fd-1000-e000-1ebdc0a8100dDEB58FDC";
					currency.setId(BOSUuid.read(id));
					currency.setIsoCode("RMB");
				}
				payRequestBill.setCapitalAmount(FDCHelper.transCap(currency, new BigDecimal(fAmount)));
			}
			// 本位币金额
			if (!StringUtils.isEmpty(fAmount)) {
				payRequestBill.setAmount(new BigDecimal(fAmount));
			}
			// 原币金额
			payRequestBill.setOriginalAmount(new BigDecimal(fOriginalAmount));
			// 用途
//			payRequestBill.setUsage();
			// 款项说明
			payRequestBill.setMoneyDesc(moneyDesc);
			// 备注
			payRequestBill.setDescription(fDescription);
			// 附件
			if (!StringUtils.isEmpty(attachment)) {
				payRequestBill.setAttachment(Integer.parseInt(attachment));
			} else {
				payRequestBill.setAttachment(0);
			}
			// 合同付款次数
			payRequestBill.setPayTimes(0);
			// 工程项目
			payRequestBill.setCurProject(curProject);
			// 关闭状态 <默认为： false>
			payRequestBill.setHasClosed(false);
			// 申请日期
			payRequestBill.setBookedDate(FDCTransmissionHelper.checkDateFormat(fCreateTime, getResource(context, "CWT_Import_sqrqcw")));
			// 业务日期 
			payRequestBill.setBizDate(FDCTransmissionHelper.checkDateFormat(fCreateTime, ""));
			// 期间
//			payRequestBill.setPeriod();
			// 合同号
			payRequestBill.setContractNo(number);
			// 合同ID <即为当前无文本合同的ID>
			FilterInfo filterID = new FilterInfo();
			filterID.getFilterItems().add(new FilterItemInfo("number", fNumber));
			EntityViewInfo viewID = new EntityViewInfo();
			viewID.setFilter(filterID);
			ContractWithoutTextCollection contractWithoutTextColl = ContractWithoutTextFactory.getLocalInstance(context).getContractWithoutTextCollection(viewID);
			if (contractWithoutTextColl.size() > 0) {
				payRequestBill.setContractId(String.valueOf(contractWithoutTextColl.get(0).getId()));
			} else {
				// "由无文本合同关联创建付款申请单失败！请检查导入模板中是否存在不符合要求的数据！"
				throw new TaskExternalException(getResource(context, "CWT_Import_fNumber4"));
			}
			// 本次申请%    
			payRequestBill.setCurReqPercent(new BigDecimal(FDCTransmissionHelper.strToDouble(String.valueOf(contractWithoutTextColl.get(0).getAmount())) / FDCTransmissionHelper.strToDouble(fOriginalAmount)));
			// 累计申请%    
			payRequestBill.setAllReqPercent(new BigDecimal(FDCTransmissionHelper.strToDouble(String.valueOf(contractWithoutTextColl.get(0).getAmount())) / FDCTransmissionHelper.strToDouble(fOriginalAmount)));
			// 合同内工程款_本次申请原币
			payRequestBill.setProjectPriceInContractOri(new BigDecimal(fOriginalAmount));
			// 本期计划付款  
			payRequestBill.setCurPlannedPayment(bigDecimal);
			// 结算金额
			payRequestBill.setSettleAmt(bigDecimal);
			// 最新造价
			payRequestBill.setLatestPrice(contractWithoutTextColl.get(0).getAmount());
			// 本申请单已付金额
			payRequestBill.setPayedAmt(bigDecimal);
			// 变更签证确认金额
			payRequestBill.setChangeAmt(bigDecimal);
			// 本期欠付款    
			payRequestBill.setCurBackPay(bigDecimal);
			// <应扣甲供材料款（本期发生）_TODO>
			payRequestBill.setPayPartAMatlAmt(bigDecimal);
			// <合同内工程款（本期发生）_合同内工程款_本次申请原币>
			payRequestBill.setProjectPriceInContract(new BigDecimal(fOriginalAmount));
			// <甲供材累计申请款_为零>
			payRequestBill.setPayPartAMatlAllReqAmt(bigDecimal);
			// 实付款本期发生额
			payRequestBill.setCurPaid(new BigDecimal(fOriginalAmount));
			// 合同内工程累计申请
			payRequestBill.setPrjAllReqAmt(new BigDecimal(fOriginalAmount));
			// 增加工程款累计申请
			payRequestBill.setAddPrjAllReqAmt(bigDecimal);
			// 单据来源方式<关联生成>
			payRequestBill.setSourceType(SourceTypeEnum.CREATE);
			// 合同内工程款上期累计申请
			payRequestBill.setLstPrjAllReqAmt(new BigDecimal(FDCTransmissionHelper.strToDouble(String.valueOf(payRequestBill.getCurPaid())) - FDCTransmissionHelper.strToDouble(String.valueOf(payRequestBill.getPrjAllReqAmt()))));
			// 来源对象 <由无文本合同创建 -- 3D9A5388>
			payRequestBill.setSource("3D9A5388");
			// 合同名称
			payRequestBill.setContractName(fName);
			payRequestBill.setContractBase(contractWithoutTextColl.get(0).getContractBaseData());
			// 合同造价
			payRequestBill.setContractPrice(new BigDecimal(fAmount));
			// 付款申请单名称
			payRequestBill.setName(fName);
			// 付款申请单 NULL
			payRequestBill.setNull(fNumber + fName);
//			// 保修金金额(默认由合同带出)
//			payRequestBill.setGrtAmount(contractWithoutTextColl.get(0).getGrtAmount());
			// 状态
			if (fState.equals(getResource(context, "CWT_Import_saved"))) {
				payRequestBill.setState(FDCBillStateEnum.SAVED);
			} else if (fState.equals(getResource(context, "CWT_Import_submitted"))) {
				payRequestBill.setState(FDCBillStateEnum.SUBMITTED);
			} else { //  默认为 <已审批>
				payRequestBill.setState(FDCBillStateEnum.AUDITTED);
				// 审核人编码 <"状态"字段需求文档中不存在,需要修改需求文档添加该字段>
				payRequestBill.setAuditor(auditoreUser);
				// 审批时间
				payRequestBill.setCreateTime(new Timestamp(FDCTransmissionHelper.checkDateFormat(fAuditTime, getResource(context, "CWT_Import_spsjcw")).getTime()));
			}
			// 付款比例
			payRequestBill.setPaymentProportion(FDCHelper.ONE_HUNDRED);
			// 已完工工程量金额
			payRequestBill.setCompletePrjAmt(new BigDecimal(fAmount));// 已完工工程量金额
			// 成本中心
			CostCenterOrgUnitInfo costCenterOrgUnit = curProject.getCostCenter(); //  工程项目对应成本中心组织
			if (!StringUtils.isEmpty(fOrgUnitNumber) && fOrgUnitNumber.equals(costCenterOrgUnit.getLongNumber())) {
				// 原转换组织对象方法 <暂不推荐使用> (FullOrgUnitInfo)FullOrgUnitInfo.class.cast(costCenterOrgUnit)
				payRequestBill.setOrgUnit(costCenterOrgUnit.castToFullOrgUnitInfo());
			}
			// 管理单元
//			payRequestBill.setCU();
			// 无需付款的申请单
			if (fIsNeedPaid.equals(getResource(context, "CWT_Import_YES"))) {
				payRequestBill.setIsPay(true);
			} else {
				payRequestBill.setIsPay(false);
			}
			// 发票号
			payRequestBill.setInvoiceNumber(fInvoiceNumber);
			// 发票日期
			if (!StringUtils.isEmpty(fInvoiceDate)) {
				payRequestBill.setInvoiceDate(FDCTransmissionHelper.checkDateFormat(fInvoiceDate, getResource(context, "CWT_Import_kprqcw")));
			}
			// 发票金额
			if (!StringUtils.isEmpty(fInvoiceAmt)) {
				payRequestBill.setInvoiceAmt(new BigDecimal(fInvoiceAmt));
			} else {
				payRequestBill.setInvoiceAmt(new BigDecimal(fAmount));
			}
			// 累计发票金额
			if (!StringUtils.isEmpty(fInvoiceAmt)) {
				payRequestBill.setAllInvoiceAmt(new BigDecimal(fAllInvoiceAmt));
			} else {
				payRequestBill.setInvoiceAmt(new BigDecimal(fAmount));
			}
			// 是否加急
			if (urgentDegree.equals(getResource(context, "CWT_Import_YES"))) {
				payRequestBill.setUrgentDegree(UrgentDegreeEnum.URGENT); // 加急
			} else {
				payRequestBill.setUrgentDegree(UrgentDegreeEnum.NORMAL); // 普通
			}
			// 同城异地 默认为同城
			payRequestBill.setIsDifferPlace(DifPlaceEnum.samePlace);
			
			if (payRequestBill.getState().getValue().equals("4AUDITTED")) {
				/** 执行提交付款申请单 */
				PayRequestBillFactory.getLocalInstance(context).submit(payRequestBill);
				/** 执行审批付款申请单 */
				ContractWithoutTextFactory.getLocalInstance(context).audit(info.getId());
			} else {
				/** 执行新增该付款申请单记录 */
				PayRequestBillFactory.getLocalInstance(context).addnew(payRequestBill);
			}
			
//			/** 执行新增该付款申请单记录 */
//			PayRequestBillFactory.getLocalInstance(context).addnew(payRequestBill);
//			/** 在付款申请单记录保存之后进行修改其奖励、扣款、违约、甲供材扣款等 */
//			this.updateAdjustClause(payRequestBill, context);
//			// 在执行完成更改付款申请单之后在执行调用生成付款单
//			/** 调用生成付款单的方法 */
//			this.createPaymentBill(payRequestBill, context);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new TaskExternalException(e.getMessage(), e);
		}
		
	}

	/**
	 * @description		设置付款单需要新增的数据<在无文本合同单据导入时,如果导入一条已审批状态的无文本合同,则自动生成一条已审批状态的付款申请单,在系统中如果有一条已审批状态的付款申请单,那么就会自动带出一条付款单>
	 * @author				雍定文	
	 * @createDate			2011-7-20
	 * @param 				payRequestBill<保存的付款申请单对象>
	 * @param 				context<抛出提示信息是需要使用的参数>
	 * @return				void
	 * @throws 			BOSException void
	 * @version			EAS1.0
	 * @throws EASBizException 
	 * @see
	 */
	private void createPaymentBill(PayRequestBillInfo payRequestBill, Context context) throws BOSException, EASBizException {
			// 付款申请单
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", payRequestBill.getNumber()));
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			PayRequestBillCollection prbColl = PayRequestBillFactory.getLocalInstance(context).getPayRequestBillCollection(view);
			if (prbColl.size() == 0) {
			return;
			}
			
			/** 需要判断付款申请单的状态，如果是已审批时，才创建保存状态的付款单。 */
			if (!String.valueOf(prbColl.get(0).getState()).equals(getResource(context, "CWT_Import_auditted"))) {
				// 如果付款申请单状态为未审批时直接返回。
				return;
			}
			paymentBill = new PaymentBillInfo();
			// 组织编码
			CostCenterOrgUnitInfo costCenterOrgUnit = curProject.getCostCenter(); //  工程项目对应成本中心组织
			FilterInfo filter1 = new FilterInfo();
			filter1.getFilterItems().add(new FilterItemInfo("id", String.valueOf(costCenterOrgUnit.getId())));
			EntityViewInfo view1 = new EntityViewInfo();
			view1.setFilter(filter1);
			CostCenterOrgUnitCollection ccouc = CostCenterOrgUnitFactory.getLocalInstance(context).getCostCenterOrgUnitCollection(view1);
			paymentBill.setCostCenter(ccouc.get(0));
			// 工程项目编码
			paymentBill.setCurProject(payRequestBill.getCurProject());
			// 代理付款公司
			// 付款类型
			paymentBill.setFdcPayType(paymentType);
			// 用款部门
			paymentBill.setUseDepartment(payRequestBill.getUseDepartment());
			// 业务日期
			paymentBill.setBizDate(payRequestBill.getBizDate());
			// 收付类别
			// 收款方省
			// 收款方市/县
			// 付款帐号
//			// 收款人类别
//			paymentBill.setFdcPayeeType(FdcPayeeTypeEnum.getEnum("1OTHER"));
			// 银行信息
			// 收款人名称
			// 收款人id
			// 收款人编号
			// 实际收款单位全称
			paymentBill.setActFdcPayeeName(payRequestBill.getRealSupplier());
			// 收款单位全称
			paymentBill.setFdcPayeeName(payRequestBill.getSupplier());
			// 付款科目
			// 收款银行
			paymentBill.setPayeeBank(payRequestBill.getRecBank());
			// 收款账户
			paymentBill.setPayeeAccountBank(payRequestBill.getRecAccount());
			// 币别编码
			paymentBill.setCurrency(payRequestBill.getCurrency());
			// 汇率
			paymentBill.setExchangeRate(payRequestBill.getExchangeRate());
			// 业务种类
			// 原币金额
			paymentBill.setAmount(payRequestBill.getAmount());
			paymentBill.setActPayLocAmt(payRequestBill.getAmount());
			paymentBill.setCurPaid(payRequestBill.getAmount());
			paymentBill.setLocalAmt(payRequestBill.getAmount());
			paymentBill.setSummary("120340093");
			paymentBill.setSettleAmt(payRequestBill.getAmount());
			FilterInfo filter2 = new FilterInfo();
			filter2.getFilterItems().add(new FilterItemInfo("name", "供应商"));
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
			// 摘要
			paymentBill.setDescription(payRequestBill.getDescription());
			// 用途
			paymentBill.setUsage(payRequestBill.getUsage());
			// 项目
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
			// 项目计划
			// 打回意见
			// 结算方式不为空时进行设置
			if (payRequestBill.getSettlementType() != null) {
				// 结算方式
				paymentBill.setSettlementType(payRequestBill.getSettlementType());
				// 结算号
				paymentBill.setSettlementNumber(payRequestBill.getSettlementType().getNumber());
			}
			// 制单人编码
			FilterInfo filter11 = new FilterInfo();
			filter11.getFilterItems().add(new FilterItemInfo("id", String.valueOf(prbColl.get(0).getCreator().getId())));
			EntityViewInfo view11 = new EntityViewInfo();
			view11.setFilter(filter11);
			UserCollection userColl = UserFactory.getLocalInstance(context).getUserCollection(view11);
			paymentBill.setCreator(userColl.get(0));
			// 制单时间
			paymentBill.setCreateTime(payRequestBill.getCreateTime());
			// 付款类型
			FilterInfo filter22 = new FilterInfo();
			filter22.getFilterItems().add(new FilterItemInfo("name", "其它"));
			EntityViewInfo view22 = new EntityViewInfo();
			view22.setFilter(filter22);
			PaymentBillTypeCollection pbtColl = PaymentBillTypeFactory.getLocalInstance(context).getPaymentBillTypeCollection(view22);
			if (pbtColl.size() > 0) {
				paymentBill.setPayBillType(pbtColl.get(0));
			}
			// 出纳
			// 会计
			// 备注
			// 最新造价
			paymentBill.setLatestPrice(payRequestBill.getLatestPrice());
			// 合同内工程款_本期发生(原币 )
			paymentBill.setProjectPriceInContract(payRequestBill.getProjectPriceInContract());
			// 本期计划付款
			paymentBill.setCurPlannedPayment(payRequestBill.getCurPlannedPayment());
			// 本期欠付款
			paymentBill.setCurBackPay(payRequestBill.getCurBackPay());
			// 本次申请%
			paymentBill.setCurReqPercent(payRequestBill.getCurReqPercent());
			// 累计申请%
			paymentBill.setAllReqPercent(payRequestBill.getAllReqPercent());
			// 形象进度
			paymentBill.setImageSchedule(payRequestBill.getImageSchedule());
	        // "奖励", award 不为空 代表覆盖数据
			// "违约", breakcontract 不为空 代表覆盖数据
			// "扣款", deduct 不为空 代表覆盖数据
			// 甲供材扣款 不为空 代表覆盖数据
			paymentBill.setPayPartAMatlAmt(payRequestBill.getPayPartAMatlAmt());
			// 合同编号
			paymentBill.setContractNo(payRequestBill.getContractNo());
			// 项目名称
			// 合同名称在不为空时设值
			if (payRequestBill.getContractBase() != null) {
				// 合同名称
				paymentBill.setContractBase(payRequestBill.getContractBase());
				// 合同id
				paymentBill.setContractBillId(String.valueOf(payRequestBill.getContractBase().getId()));
			}
			// 付款单编码
			paymentBill.setNumber(payRequestBill.getNumber());
			// 付款单名称
			paymentBill.setNull(payRequestBill.getNumber() + payRequestBill.getName());
			// 付款申请单编码
			paymentBill.setFdcPayReqNumber(payRequestBill.getNumber());
			// 付款申请单
			paymentBill.setFdcPayReqID(String.valueOf(prbColl.get(0).getId()));
			
			/** 以下参数在连动生成的情况下固定不变 */
			paymentBill.setSourceType(com.kingdee.eas.fi.cas.SourceTypeEnum.FDC);// 107 房地产成本管理
			paymentBill.setIsExchanged(false);// 初始默认均为false即为0
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
			paymentBill.setIsNeedVoucher(true);//是否需要支付，默认true即是为1
			paymentBill.setIsNeedPay(true);//是否需要支付，默认true即是为1
			paymentBill.setIsReverseLockAmount(true);// 是否反写锁定金额，默认为true即为1
			paymentBill.setIsDifferPlace(DifPlaceEnum.samePlace);//是否异地，默认否即同城非异地
			paymentBill.setActPayAmtVc(bigDecimal);// 初始默认为0
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
			paymentBill.setIsEmergency(IsMergencyEnum.normal);// 是否加急，默认为普通即为0
			paymentBill.setIsDifBank(DifBankEnum.SameBank);// 同行跨行，默认为同行即为0
			paymentBill.setBillStatus(BillStatusEnum.SAVE);// 单据状态，默认为保存状态即为10
			paymentBill.setSettlementStatus(SettlementStatusEnum.UNSUBMIT);// 集中结算状态，默认为未提交状态即为10
			paymentBill.setAccessoryAmt(0);// 附件数，默认为0个
			paymentBill.setSettleBizType(SettBizTypeEnum.PAYOUTSIDE);// 对外付款
			
			/** 执行保存付款单 */
			PaymentBillFactory.getLocalInstance(context).addnew(paymentBill);
	}

	/**
	 * @description		在付款申请单保存之后修改其调整款项！	
	 * @author				雍定文	
	 * @param 				payRequestBill<保存的付款申请单对象>
	 * @param 				context<抛出提示信息是需要使用的参数>
	 * @createDate			2011-7-20
	 * @throws 			BOSException void
	 * @version			EAS1.0
	 * @throws EASBizException 
	 * @see
	 */
	private void updateAdjustClause(PayRequestBillInfo payRequestBill, Context context) throws BOSException, EASBizException {
			// 付款申请单
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", payRequestBill.getNumber()));
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			PayRequestBillCollection prbColl = PayRequestBillFactory.getLocalInstance(context).getPayRequestBillCollection(view);
			if (prbColl.size() == 0) {
			return;
			}
			
			PayRequestBillInfo payRequestBillInfo = prbColl.get(0);
			// 合同内工程款_本次申请原币
			String fProjectPriceInContractOri = String.valueOf(payRequestBillInfo.getProjectPriceInContractOri());
			// 奖励     <存在于奖励单与付款申请单的中间表中>
			String reward = "";
			// 违约     <存在于违约单与付款申请单的中间表中>
			String breach = "";
			// 扣款     <存在于扣款单与付款申请单的中间表中>
			String fine = "";
			// 甲供材扣款   <存在于甲供材扣款与付款申请单的中间表中>
			String stuffFine = "";
			// 查询付款申请单
			FilterInfo pFilter = new FilterInfo();
			pFilter.getFilterItems().add(new FilterItemInfo("number", payRequestBillInfo.getNumber()));
			EntityViewInfo pView = new EntityViewInfo();
			pView.setFilter(pFilter);
			PayRequestBillCollection payRequestBillColl =PayRequestBillFactory.getLocalInstance(context).getPayRequestBillCollection(pView);
			if (payRequestBillColl.size() > 0) {
				payRequestBillInfo = payRequestBillColl.get(0);
				// 在保存付款申请单之后 设置调整款项<奖励，违约，扣款，甲供材扣款>
				// 为了满足总部测试人员的BT需求，只有这样做了！！！！！！！！！！
				// 奖励        (中间表<T_CON_GuerdonOfPayReqBill>)reward
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
				breachFilter.getFilterItems().add(new FilterItemInfo("contract", payRequestBillInfo.getContractId()));
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
				// 甲供材扣款  (中间表<T_CON_PartAOfPayReqBill>)stuffFine
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
				// 本币金额
				BigDecimal amountAll = new BigDecimal(fProjectPriceInContractOri);
				BigDecimal all = amountAll.add(new BigDecimal(reward)).subtract(new BigDecimal(breach)).add(new BigDecimal(fine)).add(new BigDecimal(stuffFine));
				payRequestBillInfo.setAmount(all);
				if (currency.getIsoCode() == null) {
					String id = "dfd38d11-00fd-1000-e000-1ebdc0a8100dDEB58FDC";
					currency.setId(BOSUuid.read(id));
					currency.setIsoCode("RMB");
				}
				// 大写金额
				payRequestBillInfo.setCapitalAmount(FDCHelper.transCap(currency, all));
				// 原币金额
				payRequestBillInfo.setOriginalAmount(all);
				/** 执行修改此付款申请单 */
				PayRequestBillFactory.getLocalInstance(context).update(new ObjectUuidPK(String.valueOf(payRequestBillInfo.getId())), payRequestBillInfo);
			}
	}

	/**
	 * 检查编码是否相同
	 * @throws BOSException 
	 * @throws SQLException 
	 */
	private void checkNumber(Context ctx, String number) throws BOSException, SQLException {
		if (number == null || number.equals("")) {
			return;
		}
		payReqNumber = number;
		// 付款申请单编码重复性校验
		String sql = "select fid from T_CON_PayRequestBill where FNumber = ? ";
		Object[] params = new Object[] { number };

		RowSet rowset = DbUtil.executeQuery(ctx, sql, params);
		if (rowset.next()) {
			payReqNumber = payReqNumber + getResource(ctx, "CWT_Import_withoutText");
			checkNumber(ctx, payReqNumber);
		}
	}
	
	/**
	 * 得到资源文件
	 */
	public static String getResource(Context context, String key) {
		return ResourceBase.getString(resource, key, context.getLocale());
	}
}
