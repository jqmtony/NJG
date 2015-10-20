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
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
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
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CounterclaimTypeCollection;
import com.kingdee.eas.fdc.basedata.CounterclaimTypeFactory;
import com.kingdee.eas.fdc.basedata.CounterclaimTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.DeductModeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.fdc.contract.CompensationBillCollection;
import com.kingdee.eas.fdc.contract.CompensationBillFactory;
import com.kingdee.eas.fdc.contract.CompensationBillInfo;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;

/**
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		
 *		
 * @author		dingwen_yong
 * @version		EAS7.0		
 * @createDate	2011-6-13	 
 * @see						
 */
public class CompensationBillTransmission extends AbstractDataTransmission {

	// 资源文件
	private static String resource = "com.kingdee.eas.fdc.contract.CompensationBillResource";

	/** 工程项目 */
	private CurProjectInfo curProject = null;
	/** 合同 */
	private ContractBillInfo contractBill = null;
	/** 创建人 */
	private UserInfo createUser = null;
	/** 审核人 */
	private UserInfo auditUser = null;
	/** 违约类型 */
	private CounterclaimTypeInfo counterclaimType = null;
	/** 币种 */
	private CurrencyInfo currency = null;
	
	/**
	 * @description		
	 * @author			dingwen_yong		
	 * @createDate		2011-6-13
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	protected ICoreBase getController(Context context) throws TaskExternalException {
		ICoreBase factory = null;
		try {
			factory = CompensationBillFactory.getLocalInstance(context);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return factory;
	}

	/**
	 * @description		
	 * @author			dingwen_yong		
	 * @createDate		2011-6-13
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	public CoreBaseInfo transmit(Hashtable hashtable, Context context) throws TaskExternalException {
		
		CompensationBillInfo info = new CompensationBillInfo();
		/**
		 * 获取Excel文本中对应的值
		 */
		// 1.组织编码 
		String fCostCenterNumber = ((String) ((DataToken) hashtable.get("FOrgUnit_number")).data).trim();
		// 2.工程项目编码
		String fCurProjectCodingNumber = ((String) ((DataToken) hashtable.get("FCurProject_codingNumber")).data).trim();
		// 3.合同编码
		String fContractCodingNumber = ((String) ((DataToken) hashtable.get("FContract_codingNumber")).data).trim();
		// 4.单据编码
		String fNumber = ((String) ((DataToken) hashtable.get("FNumber")).data).trim();
		// 5.单据名称
		String fName = ((String) ((DataToken) hashtable.get("FName")).data).trim();
		// 6.状态
		String fState = ((String) ((DataToken) hashtable.get("FState")).data).trim();
		// 7.币别编码
		String fCurrencyNumber = ((String) ((DataToken) hashtable.get("FCurrency_number")).data).trim();
		// 8.违约类型
		String fCompensationTypeNameL2 = ((String) ((DataToken) hashtable.get("FCompensationType_name_l2")).data).trim();
		// 9.扣款方式
		String fDeductMode = ((String) ((DataToken) hashtable.get("FDeductMode")).data).trim();
		// 10.款项说明
		String fMoneyDes = ((String) ((DataToken) hashtable.get("FMoneyDes")).data).trim();
		// 11.违约描述
		String fBreachFaichDes = ((String) ((DataToken) hashtable.get("FBreachFaichDes")).data).trim();
		// 12.违约依据
		String fCompensationAccording = ((String) ((DataToken) hashtable.get("FCompensationAccording")).data).trim();
		// 13.其他说明
		String fOtherDes = ((String) ((DataToken) hashtable.get("FOtherDes")).data).trim();
		// 14.原币金额
		String fOriginalAmount = ((String) ((DataToken) hashtable.get("FOriginalAmount")).data).trim();
		// 15.汇率
		String fExRate = ((String) ((DataToken) hashtable.get("FExRate")).data).trim();
		// 16.本位币金额
		String fAmount = ((String) ((DataToken) hashtable.get("FAmount")).data).trim();
		// 17.是否已生成凭证
		String fFiVouchered = ((String) ((DataToken) hashtable.get("FFiVouchered")).data).trim();
		// 18.制单人名称
		String fCreatorNameL2 = ((String) ((DataToken) hashtable.get("FCreator_name_l2")).data).trim();
		// 19.制单时间
		String fCreateTime = ((String) ((DataToken) hashtable.get("FCreateTime")).data).trim();
		// 20.审核人名称
		String fAuditorNameL2 = ((String) ((DataToken) hashtable.get("FAuditor_name_l2")).data).trim();
		// 21.审核时间
		String fAuditTime = ((String) ((DataToken) hashtable.get("FAuditTime")).data).trim();
		
		
		/**
		 * 判断部分字段是否为空，以及每个字段的长度(当字段不为空时，判断其长度是否有效)
		 */
		if (!StringUtils.isEmpty(fCostCenterNumber) && fCostCenterNumber.length() > 40) {
			// "组织编码字段长度不能超过40！"
			throw new TaskExternalException(getResource(context, "Import_fCostCenterNumber"));
		}
		if (!StringUtils.isEmpty(fCurProjectCodingNumber)) {
			if (fCurProjectCodingNumber.length() > 40) {
				// "工程项目编码字段长度不能超过40！"
				throw new TaskExternalException(getResource(context, "Import_fCurProjectCodingNumber"));
			}
		} else {
			// "工程项目编码不能为空！"
			throw new TaskExternalException(getResource(context, "Import_fCurProjectCodingNumberNotNull"));
		}
		if (!StringUtils.isEmpty(fContractCodingNumber)) {
			if (fContractCodingNumber.length() > 80) {
				// "合同编码字段长度不能超过80！"
				throw new TaskExternalException(getResource(context, "Import_fContractCodingNumber"));
			}
		} else {
			// "合同编码不能为空！"
			throw new TaskExternalException(getResource(context, "Import_fContractCodingNumberNotNull"));
		}
		if (!StringUtils.isEmpty(fNumber)) {
			if (fNumber.length() > 80) {
				// "单据编码字段长度不能超过80！"
				throw new TaskExternalException(getResource(context, "Import_fNumber"));
			}
		} else {
			// "单据编码不能为空！"
			throw new TaskExternalException(getResource(context, "Import_fNumberNotNull"));
		}
		if (!StringUtils.isEmpty(fName)) {
			if (fName.length() > 80) {
				// "单据名称字段长度不能超过80！"
				throw new TaskExternalException(getResource(context, "Import_fName"));
			}
		} else {
			// "单据名称不能为空！"
			throw new TaskExternalException(getResource(context, "Import_fNameNotNull"));
		}
		if (!StringUtils.isEmpty(fCurrencyNumber) && fCurrencyNumber.length() > 40) {
			// "币别编码字段长度不能超过40！"
			throw new TaskExternalException(getResource(context, "Import_fCurrencyNumber"));
		}
		if (!StringUtils.isEmpty(fCompensationTypeNameL2) && fCompensationTypeNameL2.length() > 40) {
			// "违约类型字段长度不能超过40！"
			throw new TaskExternalException(getResource(context, "Import_fCompensationTypeNameL2"));
		}
		if (!StringUtils.isEmpty(fDeductMode) && fDeductMode.length() > 40) {
			// "扣款方式字段长度不能超过40！"
			throw new TaskExternalException(getResource(context, "Import_fDeductMode"));
		}
		if (!StringUtils.isEmpty(fMoneyDes) && fMoneyDes.length() > 40) {
			// "款项说明字段长度不能超过40！"
			throw new TaskExternalException(getResource(context, "Import_fMoneyDes"));
		}
		if (!StringUtils.isEmpty(fBreachFaichDes) && fBreachFaichDes.length() > 40) {
			// "违约描述字段长度不能超过40！"
			throw new TaskExternalException(getResource(context, "Import_fBreachFaichDes"));
		}
		if (!StringUtils.isEmpty(fCompensationAccording) && fCompensationAccording.length() > 40) {
			// "违约依据字段长度不能超过40！"
			throw new TaskExternalException(getResource(context, "Import_fCompensationAccording"));
		}
		if (!StringUtils.isEmpty(fOtherDes) && fOtherDes.length() > 40) {
			// "其他说明字段长度不能超过40！"
			throw new TaskExternalException(getResource(context, "Import_fOtherDes"));
		}
		if (!StringUtils.isEmpty(fOriginalAmount)) {
			if (!fOriginalAmount.matches("^([1-9]\\d{0,15}\\.\\d{0,2})|(0\\.\\d{0,2})||([1-9]\\d{0,15})||0$")) {
				// "原币金额录入不合法！"
				throw new TaskExternalException(getResource(context, "Import_fOriginalAmount"));
			}
		} else {
			// "原币金额不能为空！"
			throw new TaskExternalException(getResource(context, "Import_fOriginalAmountNotNull"));
		}
		if (!StringUtils.isEmpty(fAmount)) {
			if (!fAmount.matches("^([1-9]\\d{0,15}\\.\\d{0,2})|(0\\.\\d{0,2})||([1-9]\\d{0,15})||0$")) {
				// 本币金额录入不合法
				throw new TaskExternalException(getResource(context, "Import_fAmount5"));
			}
		}
		if (!StringUtils.isEmpty(fCreatorNameL2)) {
			if (fCreatorNameL2.length() > 40) {
				// "制单人编码字段长度不能超过40！"
				throw new TaskExternalException(getResource(context, "Import_fCreatorNameL2"));
			}
		} else {
			// "制单人编码不能为空！"
			throw new TaskExternalException(getResource(context, "Import_fCreatorNameL2NotNull"));
		}
		if (StringUtils.isEmpty(fCreateTime)) {
			// "制单时间不能为空！"
			throw new TaskExternalException(getResource(context, "Import_fCreateTime"));
		} 
		if (!StringUtils.isEmpty(fAuditorNameL2) && fAuditorNameL2.length() > 40) {
			// "审批人编码字段长度不能超过40！"
			throw new TaskExternalException(getResource(context, "Import_fAuditorNameL2"));
		}

		/**
		 * 将值存入对象中
		 */ 
		try {
			// 手动判断违约单编码是否重复
			FilterInfo filterNumber = new FilterInfo();
			filterNumber.getFilterItems().add(new FilterItemInfo("number", fNumber));
			EntityViewInfo viewNumber = new EntityViewInfo();
			viewNumber.setFilter(filterNumber);
			CompensationBillCollection compensationBillColl = CompensationBillFactory.getLocalInstance(context).getCompensationBillCollection(viewNumber);
			if (compensationBillColl.size() > 0) {
				// 违约单编码不能重复
				throw new TaskExternalException(getResource(context, "Import_fNumber_isExents"));
			}
			// 手动判断违约单名称是否重复
			FilterInfo filterName = new FilterInfo();
			filterName.getFilterItems().add(new FilterItemInfo("name", fName));
			EntityViewInfo viewName = new EntityViewInfo();
			viewName.setFilter(filterName);
			CompensationBillCollection compensationBillColl1 = CompensationBillFactory.getLocalInstance(context).getCompensationBillCollection(viewName);
			if (compensationBillColl1.size() > 0) {
				// 违约单名称不能重复
				throw new TaskExternalException(getResource(context, "Import_fName_isExents"));
			}
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
				throw new TaskExternalException(getResource(context, "Import_fCurProjectCodingNumber1") + fCurProjectCodingNumber + getResource(context, "Import_NOTNULL"));
			}
			// 合同编码
			FilterInfo filtercontractBill = new FilterInfo();
			filtercontractBill.getFilterItems().add(new FilterItemInfo("number", fContractCodingNumber));
			EntityViewInfo viewcontractBill = new EntityViewInfo();
			viewcontractBill.setFilter(filtercontractBill);
			ContractBillCollection contractBillColl = ContractBillFactory.getLocalInstance(context).getContractBillCollection(viewcontractBill);
			if (contractBillColl.size() > 0){
				contractBill = contractBillColl.get(0);
				info.setContract(contractBill);
			}else{
				// 1 "合同编码不存在,或者该合同编码 "
				// 2 " 在系统中不存在！"
				throw new TaskExternalException(getResource(context, "Import_fContractCodingNumber1") + fContractCodingNumber + getResource(context, "Import_NOTNULL"));
			}
			// 单据编码
			info.setNumber(fNumber);
			// 单据名称
			info.setName(fName);
			// 状态 <如果状态为空时,设置其值为"已审批">
			if (fState.equals(getResource(context, "AUDITTED")) || StringUtils.isEmpty(fState)) {
				info.setState(FDCBillStateEnum.AUDITTED);
				// 当单据状态为"已审批"时,才需要设置审批人和审批时间的值。
				if (!StringUtils.isEmpty(fAuditorNameL2)) {
					// 审核人名称
					FilterInfo filterauditUser = new FilterInfo();
					filterauditUser.getFilterItems().add(new FilterItemInfo("number", fAuditorNameL2));
					EntityViewInfo viewauditUser = new EntityViewInfo();
					viewauditUser.setFilter(filterauditUser);
					UserCollection auditUserColl = UserFactory.getLocalInstance(context).getUserCollection(viewauditUser);
					if (auditUserColl.size() > 0){
						auditUser = auditUserColl.get(0);
						info.setAuditor(auditUser);
					} else {
						// 1 "审核人编码为空,或者审批人编码 "
						// 2 " 在系统中不存在！"
						throw new TaskExternalException(getResource(context, "Import_fAuditorNameL21") + fAuditorNameL2 + getResource(context, "Import_NOTNULL"));
					}
				} else {
					// "在已审批状态下的审批人字段不能为空！"
					throw new TaskExternalException(getResource(context, "Import_fAuditorNameL2NotNull"));
				}
				if (!StringUtils.isEmpty(fAuditTime)) {
					// 审核时间 审核时间录入不正确 格式为：YYYY-MM-DD
					info.setAuditTime(new Timestamp(FDCTransmissionHelper.checkDateFormat(fAuditTime, getResource(context, "Import_shsjcw")).getTime()));
				} else {
					// "在已审批状态下的审批日期字段不能为空！"
					throw new TaskExternalException(getResource(context, "Import_fAuditTime"));
				}
			} else if (fState.equals(getResource(context, "SAVED"))) {
				info.setState(FDCBillStateEnum.SAVED);
			} else if (fState.equals(getResource(context, "SUBMITTED"))) {
				info.setState(FDCBillStateEnum.SUBMITTED);
			} else {
				// "状态录入无效！"
				throw new TaskExternalException(getResource(context, "Import_fState"));
			}
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
			} else {
				// 该币种编码在系统中不存在
				throw new TaskExternalException(getResource(context, "Import_gbzbm") + fCurrencyNumber + getResource(context, "Import_NOTNULL"));
			}
			// 违约类型
			if (!StringUtils.isEmpty(fCompensationTypeNameL2)) {
				FilterInfo filtercounterclaimType = new FilterInfo();
				filtercounterclaimType.getFilterItems().add(new FilterItemInfo("name", fCompensationTypeNameL2));
				EntityViewInfo viewcounterclaimType = new EntityViewInfo();
				viewcounterclaimType.setFilter(filtercounterclaimType);
				CounterclaimTypeCollection counterclaimTypeColl = CounterclaimTypeFactory.getLocalInstance(context).getCounterclaimTypeCollection(viewcounterclaimType);
				if (counterclaimTypeColl.size() > 0){
					counterclaimType = counterclaimTypeColl.get(0);
					info.setCompensationType(counterclaimType);
				}
			}
			// 扣款方式
			if (!StringUtils.isEmpty(fDeductMode)) {
				if (fDeductMode.equals(getResource(context, "JSKK"))) {
					info.setDeductMode(DeductModeEnum.JSKK);
				} else {
					// 1 "扣款方式 "
					// 2 "在系统中不存在"
					throw new TaskExternalException(getResource(context, "Import_fDeductMode1") + fDeductMode + getResource(context, "Import_NOTNULL"));
				}
			}
			// 款项说明
			info.setMoneyDes(fMoneyDes);
			// 违约描述
			info.setBreachFaichDes(fBreachFaichDes);
			// 违约依据
			info.setCompensationAccording(fCompensationAccording);
			// 其他说明
			info.setOtherDes(fOtherDes);
			// 原币金额
			info.setOriginalAmount(new BigDecimal(fOriginalAmount));
			// 汇率
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			ExchangeRateInfo exchangeRateInfo = new ExchangeRateInfo();
			if (StringUtils.isEmpty(fExRate)) {  // 如果汇率为空，则取当前币别汇率
				if (currency.getNumber().equals("RMB")) {
					fExRate = "1.00";
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
							fExRate = bigDecimal.toString();
						}
					} else {
						fExRate = "1.00";
					}
				}
			} else {
				if (FDCTransmissionHelper.isRangedInHundred(fExRate) == null) {
					// 汇率录入不合法
					throw new TaskExternalException(getResource(context, "Import_fExRate10"));
				}
			}
			info.setExRate(new BigDecimal(fExRate));
			// 本位币金额
			if (!StringUtils.isEmpty(fAmount)) {
				if (new BigDecimal(fAmount).compareTo(new BigDecimal("0")) != 0) {
//					info.setAmount(new BigDecimal(fAmount));
					// 如果录入了本位币金额， 则判断当前的<本位币金额是否等于(原币金额*汇率)>
					BigDecimal amount = new BigDecimal(FDCTransmissionHelper.strToDouble(fOriginalAmount) * FDCTransmissionHelper.strToDouble(fExRate));
					if (new BigDecimal(fAmount).compareTo(amount) != 0) {
						// 当前录入的本位币金额不等于原币金额与汇率的乘积(即：<本币金额=汇率*原币金额>)！
						throw new TaskExternalException(getResource(context, "Import_ludbwbjebdyjexyhl"));
					}
					info.setAmount(amount);
				} else {
					// 如果本币金额为空时,设置其值为 (金额*汇率)
					info.setAmount(new BigDecimal(FDCTransmissionHelper.strToDouble(fOriginalAmount) * FDCTransmissionHelper.strToDouble(fExRate)));
				}
			} else {
				// 如果本币金额为空时,设置其值为 (金额*汇率)
				info.setAmount(new BigDecimal(FDCTransmissionHelper.strToDouble(fOriginalAmount) * FDCTransmissionHelper.strToDouble(fExRate)));
			}
			// 是否已生成凭证
			if (fFiVouchered.equals(getResource(context, "NO"))) {
				info.setFiVouchered(false);
			} else {
				info.setIsCompensated(true);
			}
			// 制单人名称
			FilterInfo filtercreateUser = new FilterInfo();
			filtercreateUser.getFilterItems().add(new FilterItemInfo("number", fCreatorNameL2));
			EntityViewInfo viewcreateUser = new EntityViewInfo();
			viewcreateUser.setFilter(filtercreateUser);
			UserCollection createUserColl = UserFactory.getLocalInstance(context).getUserCollection(viewcreateUser);
			if (createUserColl.size() > 0){
				createUser = createUserColl.get(0);
				info.setCreator(createUser);
			}else{
				// 1 "制单人编码为空,或者制单人编码 "
				// 2 " 在系统中不存在！"
				throw new TaskExternalException(getResource(context, "Import_fCreatorNameL21") + fCreatorNameL2 + getResource(context, "Import_NOTNULL"));
			}
			// 制单时间
			info.setCreateTime(new Timestamp(FDCTransmissionHelper.checkDateFormat(fCreateTime, getResource(context, "Import_zdsjcw")).getTime()));
		} catch (BOSException e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage());
		} catch (EASBizException e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage());
		}
		return info;
	}
	
	/**
	 * 得到资源文件
	 */
	public static String getResource(Context context, String key) {
		return ResourceBase.getString(resource, key, context.getLocale());
	}

}
