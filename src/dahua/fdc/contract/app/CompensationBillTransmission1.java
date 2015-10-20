/**
 * 
 */
package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyCollection;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CounterclaimTypeFactory;
import com.kingdee.eas.fdc.basedata.CounterclaimTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.DeductModeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.fdc.contract.CompensationBillFactory;
import com.kingdee.eas.fdc.contract.CompensationBillInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;

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
public class CompensationBillTransmission1 extends AbstractDataTransmission {

	// 引入工具类
	private FDCTransmissionHelper help = new FDCTransmissionHelper();
	
	/** 工程项目 */
	private CurProjectInfo curProject;
	/** 合同 */
	private ContractBillInfo contractBill;
	/** 创建人 */
	private UserInfo createUser;
	/** 审核人 */
	private UserInfo auditUser;
	/** 违约类型 */
	private CounterclaimTypeInfo counterclaimType;
	
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
		String fCostCenterNumber = (String) ((DataToken) hashtable.get("FCostCenter_number")).data;
		// 2.工程项目编码
		String fCurProjectCodingNumber = (String) ((DataToken) hashtable.get("FCurProject_codingNumber")).data;
		// 3.合同编码
		String fContractCodingNumber = (String) ((DataToken) hashtable.get("FContract_codingNumber")).data;
		// 4.单据编码
		String fNumber = (String) ((DataToken) hashtable.get("FNumber")).data;
		// 5.单据名称
		String fName = (String) ((DataToken) hashtable.get("FName")).data;
		// 6.状态
		String fState = (String) ((DataToken) hashtable.get("FState")).data;
		// 7.币别编码
		String fCurrencyNumber = (String) ((DataToken) hashtable.get("FCurrency_number")).data;
		// 8.违约类型
		String fCompensationTypeNameL2 = (String) ((DataToken) hashtable.get("FCompensationType_name_l2")).data;
		// 9.扣款方式
		String fDeductMode = (String) ((DataToken) hashtable.get("FDeductMode")).data;
		// 10.款项说明
		String fMoneyDes = (String) ((DataToken) hashtable.get("FMoneyDes")).data;
		// 11.违约描述
		String fBreachFaichDes = (String) ((DataToken) hashtable.get("FBreachFaichDes")).data;
		// 12.违约依据
		String fCompensationAccording = (String) ((DataToken) hashtable.get("FCompensationAccording")).data;
		// 13.其他说明
		String fOtherDes = (String) ((DataToken) hashtable.get("FOtherDes")).data;
		// 14.原币金额
		String fOriginalAmount = (String) ((DataToken) hashtable.get("FOriginalAmount")).data;
		// 15.汇率
		String fExRate = (String) ((DataToken) hashtable.get("FExRate")).data;
		// 16.本位币金额
		String fAmount = (String) ((DataToken) hashtable.get("FAmount")).data;
		// 17.是否已生成凭证
		String fFiVouchered = (String) ((DataToken) hashtable.get("fFiVouchered")).data;
		// 18.制单人名称
		String fCreatorNameL2 = (String) ((DataToken) hashtable.get("FCreator_name_l2")).data;
		// 19.制单时间
		String fCreateTime = (String) ((DataToken) hashtable.get("FCreateTime")).data;
		// 20.审核人名称
		String fAuditorNameL2 = (String) ((DataToken) hashtable.get("FAuditor_name_l2")).data;
		// 21.审核时间
		String fAuditTime = (String) ((DataToken) hashtable.get("FAuditTime")).data;
		
		
		/**
		 * 判断部分字段是否为空，以及每个字段的长度(当字段不为空时，判断其长度是否有效)
		 */
		if (!StringUtils.isEmpty(fCostCenterNumber) && fCostCenterNumber.length() > 40) {
			throw new TaskExternalException("组织编码字段长度不能超过40！");
		}
		if (!StringUtils.isEmpty(fCurProjectCodingNumber)) {
			if (fCurProjectCodingNumber.length() > 40) {
				throw new TaskExternalException("工程项目编码字段长度不能超过40！");
			}
		} else {
			throw new TaskExternalException("工程项目编码不能为空！");
		}
		if (!StringUtils.isEmpty(fContractCodingNumber)) {
			if (fContractCodingNumber.length() > 80) {
				throw new TaskExternalException("合同编码字段长度不能超过80！");
			}
		} else {
			throw new TaskExternalException("合同编码不能为空！");
		}
		if (!StringUtils.isEmpty(fNumber)) {
			if (fNumber.length() > 80) {
				throw new TaskExternalException("单据编码字段长度不能超过80！");
			}
		} else {
			throw new TaskExternalException("单据编码不能为空！");
		}
		if (!StringUtils.isEmpty(fName)) {
			if (fName.length() > 80) {
				throw new TaskExternalException("单据名称字段长度不能超过80！");
			}
		} else {
			throw new TaskExternalException("单据名称不能为空！");
		}
		if (!StringUtils.isEmpty(fCurrencyNumber) && fCurrencyNumber.length() > 40) {
			throw new TaskExternalException("币别编码字段长度不能超过40！");
		}
		if (!StringUtils.isEmpty(fCompensationTypeNameL2) && fCompensationTypeNameL2.length() > 40) {
			throw new TaskExternalException("违约类型字段长度不能超过40！");
		}
		if (!StringUtils.isEmpty(fDeductMode) && fDeductMode.length() > 40) {
			throw new TaskExternalException("扣款方式字段长度不能超过40！");
		}
		if (!StringUtils.isEmpty(fMoneyDes) && fMoneyDes.length() > 40) {
			throw new TaskExternalException("款项说明字段长度不能超过40！");
		}
		if (!StringUtils.isEmpty(fBreachFaichDes) && fBreachFaichDes.length() > 40) {
			throw new TaskExternalException("违约描述字段长度不能超过40！");
		}
		if (!StringUtils.isEmpty(fCompensationAccording) && fCompensationAccording.length() > 40) {
			throw new TaskExternalException("违约依据字段长度不能超过40！");
		}
		if (!StringUtils.isEmpty(fOtherDes) && fOtherDes.length() > 40) {
			throw new TaskExternalException("其他说明字段长度不能超过40！");
		}
		if (StringUtils.isEmpty(fOriginalAmount)) {
			
			String str = fOriginalAmount.trim();
			// 处理指数表示的数值如 1.00002213E15
			if (str.toLowerCase().indexOf("e") > -1) {
				try {
					new BigDecimal(str);
					help.valueFormat("原币金额", fOriginalAmount, "Double", false, -1);
				} catch (NumberFormatException e) {
					throw new TaskExternalException("原币金额应该录入数字型！");
				}
			}
		} else {
			throw new TaskExternalException("原币金额不能为空！");
		}
		if (!StringUtils.isEmpty(fCreatorNameL2)) {
			if (fCreatorNameL2.length() > 40) {
				throw new TaskExternalException("制单人编码字段长度不能超过40！");
			}
		} else {
			throw new TaskExternalException("制单人编码不能为空！");
		}
		if (StringUtils.isEmpty(fCreateTime)) {
			throw new TaskExternalException("制单时间不能为空！");
		} 
		if (!StringUtils.isEmpty(fAuditorNameL2) && fAuditorNameL2.length() > 40) {
			throw new TaskExternalException("审批人编码字段长度不能超过40！");
		}

		/**
		 * 将值存入对象中
		 */
		try {
			curProject = CurProjectFactory.getLocalInstance(context).getCurProjectInfo("where fLongNumber = '" + fCurProjectCodingNumber + "'");
			if (curProject != null) {
				info.setCurProject(curProject);
				// 组织编码
				CostCenterOrgUnitInfo costCenterOrgUnit = curProject.getCostCenter(); // 工程项目对应成本中心组织
//				curProject.getCostCenter().getLongNumber().toString();  // 工程项目对应成本中心长编码
				if (!StringUtils.isEmpty(fCostCenterNumber) || !fCostCenterNumber.trim().equals(costCenterOrgUnit.getLongNumber().toString())) {
					throw new TaskExternalException("组织长编码在工程项目所对应的成本中心不存在!");
				}
				info.setOrgUnit(costCenterOrgUnit.castToFullOrgUnitInfo());				
			} else {
				throw new TaskExternalException("工程项目编码为空,或该工程项目编码 " + fCurProjectCodingNumber + " 在系统中不存在！");
			}
			// 合同编码
			contractBill = ContractBillFactory.getLocalInstance(context).getContractBillInfo("where fNumber = '" + fContractCodingNumber + "'");
			if (contractBill != null) {
				info.setContract(contractBill);
			} else {
				throw new TaskExternalException("合同编码为空,或该合同编码 " + fContractCodingNumber + " 在系统中不存在！");
			}
			// 单据编码
			info.setNumber(fNumber);
			// 单据名称
			info.setName(fName);
			// 状态 <如果状态为空时,设置其值为"已审批">
			if (!StringUtils.isEmpty(fState)) {
				if (fState.length() > 40) {
					throw new TaskExternalException("状态字段长度不能超过40！");
				}
				if (fState.trim().equals("已审批") || fState.trim().equals("") || fState.trim() == null) {
					info.setState(FDCBillStateEnum.AUDITTED);
					// 当单据状态为"已审批"时,才需要设置审批人和审批时间的值。
					if (!StringUtils.isEmpty(fAuditorNameL2)) {
						// 审核人名称
						auditUser = UserFactory.getLocalInstance(context).getUserInfo("where fNumber = '" + fAuditorNameL2 + "'");
						if (auditUser != null) {
							info.setCreator(auditUser);
						} else {
							throw new TaskExternalException("审核人编码为空,或者审批人编码 " + fAuditorNameL2 + " 在系统中不存在！");
						}
					} else {
						throw new TaskExternalException("在已审批状态下的审批人字段不能为空！");
					}
					if (!StringUtils.isEmpty(fAuditTime)) {
						// 审核时间
//						info.setCreateTime(new Timestamp(help.checkDateFormat(fAuditTime).getTime()));
					} else {
						throw new TaskExternalException("在已审批状态下的审批日期字段不能为空！");
					}
				} else if (fState.trim().equals("保存")) {
					info.setState(FDCBillStateEnum.SAVED);
				} else if (fState.trim().equals("已提交")) {
					info.setState(FDCBillStateEnum.SUBMITTED);
				} else {
					throw new TaskExternalException("状态录入无效！");
				}
				if (fState.trim().equals("已审批")) {
					
				}
			}
			// 币别编码
			CurrencyCollection currencyCollection = null;
			if (fCurrencyNumber.trim().equals("") || fCurrencyNumber.trim() == null) {
				try {
					currencyCollection = CurrencyFactory.getLocalInstance(context).getCurrencyCollection("where FNumber = 'glc'");
				} catch (BOSException e) {
					e.printStackTrace();
					throw new TaskExternalException(e.getMessage());
				}
			} else {
				try {
					currencyCollection = CurrencyFactory.getLocalInstance(context).getCurrencyCollection("where FNumber = '"+fCurrencyNumber+"'");
				} catch (BOSException e) {
					e.printStackTrace();
					throw new TaskExternalException(e.getMessage());
				}
			}
			if (currencyCollection!=null) {
				info.setCurrency(currencyCollection.get(0));
			}
			// 违约类型
			counterclaimType = CounterclaimTypeFactory.getLocalInstance(context).getCounterclaimTypeInfo("where FName = '"+fCompensationTypeNameL2+"'");
			if (counterclaimType != null) {
				info.setCompensationType(counterclaimType);
			} else {
				
			}
			// 扣款方式
			if (fDeductMode.trim().equals("结算扣款")) {
				info.setDeductMode(DeductModeEnum.JSKK);
			} else {
				throw new TaskExternalException("扣款方式 " + fDeductMode + " 录入无效！");
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
			if(StringUtils.isEmpty(fExRate)){
				info.setExRate(FDCHelper.ONE);
			} else {
				info.setExRate(new BigDecimal(fExRate));
			}
			// 本位币金额
			if (Integer.parseInt(fAmount) == 0) {
				// 如果本币金额为空时,设置其值为 (金额*汇率)
				info.setAmount(new BigDecimal(Integer.parseInt(fOriginalAmount) * Integer.parseInt(fExRate)));
			} else {
				info.setAmount(new BigDecimal(fAmount));
			}
			// 是否已生成凭证
			if (fFiVouchered.trim().equals("是") || fFiVouchered.trim().equals("true") || fFiVouchered.trim().equals("1") || fFiVouchered.trim().equals("")) {
				info.setFiVouchered(true);
			} else {
				info.setFiVouchered(false);
			}
			// 制单人名称
			createUser = UserFactory.getLocalInstance(context).getUserInfo("where fNumber = '" + fCreatorNameL2 + "'");
			if (createUser != null) {
				info.setCreator(createUser);
			} else {
				throw new TaskExternalException("制单人编码为空,或者制单人编码 " + fCreatorNameL2 + " 在系统中不存在！");
			}
			// 制单时间
//			info.setCreateTime(new Timestamp(help.checkDateFormat(fCreateTime).getTime()));
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
			throw new TaskExternalException(e.getMessage());
		}
		return info;
	}


}
