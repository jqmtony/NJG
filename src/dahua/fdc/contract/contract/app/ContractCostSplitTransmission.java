/**
 * 
 */
package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;

/**
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		合同拆分引入引出数据转化类	
 *		
 * @author		王川
 * @version		EAS7.0		
 * @createDate	2011-6-9	 
 * @see						
 */
public class ContractCostSplitTransmission extends AbstractDataTransmission {

	private static String resource = "com.kingdee.eas.fdc.contract.ContractTransmissionResource";
	private static String resourceCommon = "com.kingdee.eas.common.EASCommonResource";
	
	ContractCostSplitInfo info = null;;
	
	ContractCostSplitEntryInfo infoEntry = null;

	ContractBillInfo con = null;
	
//	static Map stateMap = new HashedMap();
	
	private BigDecimal sum = FDCHelper.ZERO;
	
	protected ICoreBase getController(Context ctx)
			throws TaskExternalException {
		ICoreBase factory = null;
		try {
			factory = ContractCostSplitFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return factory;
	}

	public CoreBaseInfo transmit(Hashtable hsData, Context ctx)
			throws TaskExternalException {
		try {
			info = transmitHead(hsData, ctx);
			if (info == null) {
				return null;
			}
			ContractCostSplitEntryInfo entry = transmitEntry(hsData, ctx);
	        int seq = info.getEntrys().size() + 1;
	        entry.setSeq(seq);
	        entry.setParent(info);
	        info.getEntrys().add(entry);
	        // 设置拆分状态
	        StringBuffer sqlStr = new StringBuffer("update T_CON_ContractBill set FSplitState = '");
	        if(FDCHelper.ZERO.compareTo(sum) == 0) {
	        	info.setSplitState(CostSplitStateEnum.NOSPLIT);
	        	sqlStr.append(CostSplitStateEnum.NOSPLIT_VALUE).append("'");
	        } else if(con.getAmount().compareTo(sum) == 1) {
	        	info.setSplitState(CostSplitStateEnum.PARTSPLIT);
	        	sqlStr.append(CostSplitStateEnum.PARTSPLIT_VALUE).append("'");
	        } else {
	        	info.setSplitState(CostSplitStateEnum.ALLSPLIT);
	        	sqlStr.append(CostSplitStateEnum.ALLSPLIT_VALUE).append("'");
	        }
	        sqlStr.append(" where fid='").append(con.getId().toString()).append("'");
	        FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(sqlStr.toString());
	        sqlBuilder.executeUpdate(ctx);
	       
	        // 手动保存，用save才能更新相应的全项目动态成本表和待发生成本预测表
	        getController(ctx).save(info);
		} catch (TaskExternalException e) {
			info = null;
			throw e;
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		} catch (EASBizException e) {
			throw new TaskExternalException(e.getMessage());
		}
		
		return null;
	}
	
	private ContractCostSplitInfo getContractCostSplit(String curProjectID, String contractBillId, Context ctx) throws TaskExternalException{
		try {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractBillId));
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", curProjectID));
			filter.setMaskString("#0 and #1");
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			ContractCostSplitCollection info = ContractCostSplitFactory.getLocalInstance(ctx).getContractCostSplitCollection(view);
			if (info != null) {
				return info.get(0);
			}
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		}
		return null;
	}
	
	private ContractCostSplitInfo transmitHead(Hashtable hsdata, Context ctx) throws TaskExternalException {
		//组织编码
		String fcostOrg_longNumber  = (String) ((DataToken)hsdata.get("FCurProject$costOrg_longNumber")).data;
		//工程项目编码
		String fCurProjectLongNumber = (String) ((DataToken)hsdata.get("FCurProject_longNumber")).data;			
	    //合同编码
		String fContractBillNumber = (String) ((DataToken)hsdata.get("FContractBill_number")).data;
		//合同名称
		String fContractBillName = (String) ((DataToken)hsdata.get("FContractBill_name")).data;
		//合同造价
		String fContractBillAmount = (String) ((DataToken)hsdata.get("FContractBill_amount")).data;
		//已拆分合同造价
		String fAmount = (String) ((DataToken)hsdata.get("FAmount")).data;
		//未拆分合同造价
		String fDCSplitBillAmount  = (String) ((DataToken)hsdata.get("UnSplitAmount")).data;
		//是否已确定
		String fIsConfirm = (String) ((DataToken)hsdata.get("FIsConfirm")).data;
		// 状态
		String fState=(String) ((DataToken) hsdata.get("FState")).data;
		//拆分人
		String fCreatorNumber = (String) ((DataToken)hsdata.get("FCreator_number")).data;
		//拆分时间
		String fCreateTime = (String) ((DataToken)hsdata.get("FCreateTime")).data;
		//审批人
		String fAuditorNumber = (String) ((DataToken)hsdata.get("FAuditor_number")).data;
		//审批时间
		String fAuditTime = (String) ((DataToken)hsdata.get("FAuditTime")).data;
		
		String stateStr = this.getStateStr(fState, ctx);
		/**
		 * 必录项校验
		 */
		if(StringUtils.isEmpty(fCurProjectLongNumber)) {
			throw new TaskExternalException(getResource(ctx, "CurProjectLongNumberNotNull",resource));
		}
		if(StringUtils.isEmpty(fContractBillNumber)) {
			throw new TaskExternalException(getResource(ctx, "ContractCodingNumberNotNull",resource));
		}
		if(StringUtils.isEmpty(fCreatorNumber)) {
			throw new TaskExternalException(getResource(ctx, "CreatorNumberIsNull",resource));
		}
		if(StringUtils.isEmpty(fCreateTime)) {
			throw new TaskExternalException(getResource(ctx, "CreateTime",resource));
		}
		if(StringUtils.isEmpty(fIsConfirm)) {
			fIsConfirm = getResource(ctx, "no",resourceCommon);
		}
		if(!fIsConfirm.trim().equals(getResource(ctx, "yes",resourceCommon)) && !fIsConfirm.trim().equals(getResource(ctx, "no",resourceCommon))) {
			throw new TaskExternalException(getResource(ctx, "IsConfirm",resource));
		}
		
		if(stateStr != null && stateStr.length() > 40) {
			throw new TaskExternalException(getResource(ctx, "StateIsOver40",resource));
		}
		
		/**
		 * 日期格式验证
		 */
		FDCTransmissionHelper.checkDateFormat(fCreateTime, getResource(ctx, "CreateTimeError",resource));
		
		/**
		 * 查询数据验证
		 */
		UserInfo creatot = null;
		UserInfo auditor = null;
		Date auditDate = null;
		CostCenterOrgUnitInfo costCenter = null;
		CurProjectInfo curProjectInfo = null;
		ContractBillInfo contractBillInfo = null;
		try {
			// 工程项目编码
			FilterInfo curProjectFilter = new FilterInfo();
			curProjectFilter.getFilterItems().add(new FilterItemInfo("longnumber", fCurProjectLongNumber.replace('.', '!')));
			EntityViewInfo curProjectView = new EntityViewInfo();
			curProjectView.setFilter(curProjectFilter);
			CurProjectCollection curProjectCollection = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(curProjectView);
			if(curProjectCollection.size() > 0) {
				curProjectInfo = curProjectCollection.get(0);
			} else {
				throw new TaskExternalException(getResource(ctx, "CurProjectNumberNotFound",resource));
			}
			
			// 组织编码
			costCenter = curProjectInfo.getCostCenter();
			String costCenterLongNumber = "";
			if(costCenter == null) {
				throw new TaskExternalException(getResource(ctx, "CostCenterNotFound",resource));
			}
			BOSUuid id = costCenter.getId();
			FilterInfo costCenterFilter = new FilterInfo();
			costCenterFilter.getFilterItems().add(new FilterItemInfo("id", id.toString()));
			EntityViewInfo costCenterView = new EntityViewInfo();
			costCenterView.setFilter(costCenterFilter);
			CostCenterOrgUnitCollection costCenterOrgUnitCollection = CostCenterOrgUnitFactory.getLocalInstance(ctx).getCostCenterOrgUnitCollection(costCenterView);
			if(costCenterOrgUnitCollection.size() > 0) {
				costCenter = costCenterOrgUnitCollection.get(0);
				costCenterLongNumber = costCenter.getLongNumber();
				if(!StringUtils.isEmpty(fcostOrg_longNumber) && !costCenterLongNumber.equals(fcostOrg_longNumber.replace('.', '!'))) {
					throw new TaskExternalException(getResource(ctx, "CostOrgLongNumberNotFound",resource));
				}
			}
			
			// 合同编码
			FilterInfo contractBillFile = new FilterInfo();
			contractBillFile.getFilterItems().add(new FilterItemInfo("number", fContractBillNumber));
			contractBillFile.getFilterItems().add(new FilterItemInfo("curproject.id", curProjectInfo.getId().toString()));
			contractBillFile.setMaskString("#0 and #1");
			EntityViewInfo contractBillView = new EntityViewInfo();
			contractBillView.setFilter(contractBillFile);
			ContractBillCollection contractBillCollection = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(contractBillView);
			if(contractBillCollection.size() > 0) {
				contractBillInfo = contractBillCollection.get(0);
				con = contractBillInfo;
			} else {
				throw new TaskExternalException(getResource(ctx, "ContractBillNumberNotFound",resource));
			}
			
			// 判断单头是否存在
			info = getContractCostSplit(curProjectInfo.getId().toString(), contractBillInfo.getId().toString(), ctx);
			if(info != null) {
				if(info.getSplitState() == CostSplitStateEnum.ALLSPLIT) {
					throw new TaskExternalException(getResource(ctx, "AllSplit",resource));
				}
				return info;
			} else {
				info = new ContractCostSplitInfo();
			}
			
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		
		// 拆分人
		creatot = isUserNumber(fCreatorNumber, ctx);
		if(creatot == null) {
			throw new TaskExternalException(getResource(ctx, "CreatorNotFound",resource));
		}
		
		if(stateStr.trim().equals(FDCBillStateEnum.AUDITTED_VALUE)) {
			if(StringUtils.isEmpty(fAuditorNumber)) {
				throw new TaskExternalException(getResource(ctx, "AuditorNumberNotNull",resource));
			}
			if(StringUtils.isEmpty(fAuditTime)){
				throw new TaskExternalException(getResource(ctx, "AuditTimeNotNull",resource));
			}
		}
		if(!StringUtils.isEmpty(fAuditTime)) {
			auditDate = FDCTransmissionHelper.checkDateFormat(fAuditTime, getResource(ctx, "AuditTimeError",resource));
		}
		if(!StringUtils.isEmpty(fAuditorNumber)) {
			auditor = isUserNumber(fAuditorNumber, ctx);
			if(auditor == null){
				throw new TaskExternalException(getResource(ctx, "AuditorNumberNotFound",resource));
			}
		}
		
		/**
		 * 填充值
		 */
		info.setOrgUnit(costCenter.castToFullOrgUnitInfo());
		info.setCurProject(curProjectInfo);
		info.setContractBill(contractBillInfo);
		info.setState(FDCBillStateEnum.getEnum(stateStr));
		// 新拆分的已拆分合同造价为零
		info.setAmount(FDCHelper.ZERO);
		if(fIsConfirm.trim().equals(getResource(ctx, "yes",resourceCommon))) {
			info.setIsConfirm(true);
		} else {
			info.setIsConfirm(false);
		}
		info.setCreator(creatot);
		info.setCreateTime(new Timestamp(FDCDateHelper.stringToTimeStamp(fCreateTime).getTime()));
		info.setAuditor(auditor);
		info.setAuditTime(auditDate);
		return info;
	}
	
	private ContractCostSplitEntryInfo transmitEntry(Hashtable hsdata, Context ctx) throws TaskExternalException{
		//归属财务核算对象代码
		String curProjectLongNumber = (String) ((DataToken)hsdata.get("FEntrys$costAccount$curProject_longNumber")).data;
		//curProjectLongNumber = curProjectLongNumber.replace('!', '.');
		//归属财务核算对象
		String curProjectName = (String) ((DataToken)hsdata.get("FEntrys$costAccount$curProject_name_l2")).data;
		//归属成本科目代码
		String fCostAccountNumber = (String) ((DataToken)hsdata.get("FEntrys$costAccount_number")).data;
	    //归属成本科目
		String fCostAccountNamel2 = (String) ((DataToken)hsdata.get("FEntrys$costAccount_name_l2")).data;
		//拆分比例(%)
		String fEntrysSplitScale = (String) ((DataToken)hsdata.get("FEntrys_splitScale")).data;
		//归属金额
		String fEntrysAmount = (String) ((DataToken)hsdata.get("FEntrys_amount")).data;
		// 归属标准
		String fEntrysSplitType = (String) ((DataToken)hsdata.get("FEntrys_splitType")).data;
		//直接归属
		String productNumber = (String) ((DataToken)hsdata.get("FEntrys$product_number")).data;
		
		/**
		 * 必录项校验
		 */
		if(StringUtils.isEmpty(curProjectLongNumber)) {
			throw new TaskExternalException(getResource(ctx, "CurProjectCodingNumberNotNull",resource));
		}
		if(StringUtils.isEmpty(fCostAccountNumber)) {
			throw new TaskExternalException(getResource(ctx, "CostAccountNumberNotNull",resource));
		}
		// Added by Owen_wen 拆分比例不做必录校验了，以归属金额的数据为主，反算拆分比例
		// if(StringUtils.isEmpty(fEntrysSplitScale)) {
		// throw new TaskExternalException(getResource(ctx, "SplitScaleNotNull",resource));
		// }
		if(StringUtils.isEmpty(fEntrysAmount)) {
			throw new TaskExternalException(getResource(ctx, "AmountNotNull",resource));
		}
		
		if(!StringUtils.isEmpty(fEntrysSplitType)) {
			if(!fEntrysSplitType.equals(getResource(ctx, "Import_StateProdsplit",resource))) {
				//请输入正确的归属标准
				throw new TaskExternalException(getResource(ctx, "CostSplitTypeEnum",resource));
			}else
			{
				fEntrysSplitType = CostSplitTypeEnum.PRODSPLIT_VALUE;
			}
		}
		
		BigDecimal splitScale = FDCTransmissionHelper.strToBigDecimal(fEntrysSplitScale);
		if (!StringUtils.isEmpty(fEntrysSplitScale) ){ // 必须不为空时校验，为空不需要校验（因为非必录项）
			if(!FDCTransmissionHelper.isNumber(fEntrysSplitScale)) {
				throw new TaskExternalException(getResource(ctx, "EntrysSplitScaleNotNumber",resource));
			} else if(splitScale.compareTo(FDCHelper.ZERO) == -1 || splitScale.compareTo(FDCHelper.ONE_HUNDRED) == 1) {
				throw new TaskExternalException(getResource(ctx, "splitScaleError",resource));
			}
		}
		
		if(!FDCTransmissionHelper.isNumber(fEntrysAmount)) {
			throw new TaskExternalException(getResource(ctx, "EntrysAmountNotNumber",resource));
		}
		
		/**
		 * 查询数据校验
		 */
		CostAccountInfo costAccountInfo = null;
		ProductTypeInfo productTypeInfo = null;
		CurProjectInfo curProjectInfo = null;
		ContractBillInfo contractBillInfo = null;
		BigDecimal entrysAmount = null;
		BigDecimal amount = null;
		try {
			//归属财务核算对象代码
			FilterInfo curProjectFilter = new FilterInfo();
			curProjectFilter.getFilterItems().add(new FilterItemInfo("codingNumber", curProjectLongNumber.trim().replace('!', '.')));
			EntityViewInfo curProjectView = new EntityViewInfo();
			curProjectView.setFilter(curProjectFilter);
			CurProjectCollection curProjectCollection = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(curProjectView);
			if(curProjectCollection.size() > 0) {
				curProjectInfo = curProjectCollection.get(0);
			} else {
				throw new TaskExternalException(getResource(ctx, "curProjectInfo",resource));
			}
			
			// 归属成本科目代码
			FilterInfo costAccountFilter = new FilterInfo();
			costAccountFilter.getFilterItems().add(new FilterItemInfo("codingNumber", fCostAccountNumber.trim().replace('!', '.')));
			costAccountFilter.getFilterItems().add(new FilterItemInfo("curProject", curProjectInfo.getId().toString()));
			costAccountFilter.setMaskString("#0 and #1");
			EntityViewInfo costAccountView = new EntityViewInfo();
			costAccountView.setFilter(costAccountFilter);
			CostAccountCollection costAccountCollection = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(costAccountView);
			if(costAccountCollection.size() > 0) {
				costAccountInfo = costAccountCollection.get(0);
			} else {
				throw new TaskExternalException(getResource(ctx, "CostAccountNotFound",resource));
			}
			
			// 直接归属
			if(!StringUtils.isEmpty(productNumber)) {				
				FilterInfo productTypeFilter = new FilterInfo();
				productTypeFilter.getFilterItems().add(new FilterItemInfo("number", productNumber.trim()));
				EntityViewInfo productTypeView = new EntityViewInfo();
				productTypeView.setFilter(productTypeFilter);
				ProductTypeCollection productTypeCollection = ProductTypeFactory.getLocalInstance(ctx).getProductTypeCollection(productTypeView);
				if(productTypeCollection.size() > 0) {
					productTypeInfo = productTypeCollection.get(0);
				} else {
					throw new TaskExternalException(getResource(ctx, "ProductTypeNotFound",resource));
				}
			}
			
			// 判断分录是否存在
			ContractCostSplitEntryCollection coll = info.getEntrys();
			if(coll!=null&&coll.size()>0)
			{
				ContractCostSplitEntryInfo tempInfo = null;
				for(int i=0;i<coll.size();i++)
				{
					tempInfo = coll.get(i);
					if(tempInfo.getCostAccount().getId().toString().equals(costAccountInfo.getId().toString())) {
						throw new TaskExternalException(getResource(ctx, "EntryIsRepeat",resource));
					}
				}
			}
			
			// 获得主对象的合同
			String id = info.getContractBill().getId().toString();
			FilterInfo contractBillFile = new FilterInfo();
			contractBillFile.getFilterItems().add(new FilterItemInfo("id", id));
			EntityViewInfo contractBillView = new EntityViewInfo();
			contractBillView.setFilter(contractBillFile);
			ContractBillCollection contractBillCollection = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(contractBillView);
			if(contractBillCollection.size() > 0) {
				contractBillInfo = contractBillCollection.get(0);
			} else {
				throw new TaskExternalException(getResource(ctx, "ContractBillNumberNotFound",resource));
			}
			entrysAmount = FDCTransmissionHelper.strToBigDecimal(fEntrysAmount);
			amount = contractBillInfo.getAmount();
			if(entrysAmount.compareTo(amount.subtract(info.getAmount())) > 0) {
				throw new TaskExternalException(getResource(ctx, "Amount",resource));
			}
			
			for(int i=0;i<coll.size();i++) {
				BigDecimal money = coll.get(i).getAmount();
				if(money == null) {
					money = FDCHelper.ZERO;
				}
				sum = sum.add(money);
			}
			sum = sum.add(entrysAmount);
			
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		infoEntry = new ContractCostSplitEntryInfo();
		costAccountInfo.setCurProject(curProjectInfo);
		infoEntry.setCostAccount(costAccountInfo);
		// 拆分比例
		infoEntry.setSplitScale(entrysAmount.divide(amount, 6, BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
		infoEntry.setAmount(entrysAmount);
		infoEntry.setSplitType(CostSplitTypeEnum.getEnum(fEntrysSplitType));
		infoEntry.setProduct(productTypeInfo);
		// 是否子节点
		if(fCostAccountNumber.indexOf(".") == -1) {
			infoEntry.setIsLeaf(false);
		} else {
			infoEntry.setIsLeaf(true);
		}
		return infoEntry;
	}
	
	/**
	 * 	判断用户是否存在
	 * @param number 用户编码
	 * @param ctx
	 * @return	true/false  存在/不存在
	 */
	private UserInfo isUserNumber(String number, Context ctx) throws TaskExternalException {
		UserInfo user = null;
		try {
			FilterInfo userFilter = new FilterInfo();
			userFilter.getFilterItems().add(new FilterItemInfo("number",number));
			EntityViewInfo userView = new EntityViewInfo();
			userView.setFilter(userFilter);
			UserCollection userCollection;
			userCollection = UserFactory.getLocalInstance(ctx).getUserCollection(userView);
			if(userCollection.size() > 0) {
				user = userCollection.get(0);
			}
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return user;
	}
	
	
	private String  getStateStr(String fState, Context ctx) {
		String stateStr = FDCBillStateEnum.SAVED_VALUE;
		if(fState.equals(getResource(ctx, "FDCBillState_AUDITTED",resource)))
		{
			stateStr = FDCBillStateEnum.AUDITTED_VALUE;
		}else if(fState.equals(getResource(ctx, "FDCBillState_SUBMITTED",resource)))
		{
			stateStr = FDCBillStateEnum.SUBMITTED_VALUE;
		}
		return stateStr;
	}
	
	public static String getResource(Context ctx, String key,String resourceStr) {
		return ResourceBase.getString(resourceStr, key, ctx.getLocale());
	}
}
