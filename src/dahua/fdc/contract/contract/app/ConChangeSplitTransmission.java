package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
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
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.contract.ConChangeSplitCollection;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;

public class ConChangeSplitTransmission extends AbstractDataTransmission {
	
	private static String resource = "com.kingdee.eas.fdc.contract.ContractTransResource";

	//变更拆分对象
	ConChangeSplitInfo conChangeSplitInfo = null;
	//变更拆分分录对象
	ConChangeSplitEntryInfo conChangeSplitEntryInfo = null;
	//项目工程对象
	CurProjectInfo curProjectInfo = null;
	//组织中心
	FullOrgUnitInfo fullOrgUnitInfo = null;
	//变更签证确认
	ContractChangeBillInfo contractChangeBillInfo = null;
	//拆分人对象
	UserInfo createUserInfo = null;
	//审批人对象
	UserInfo auditorUserInfo = null;
	//归属财务成本科目对象
	CostAccountInfo costAccountInfo = null;
	//产品类型对象
	ProductTypeInfo productTypeInfo = null;
	
	// 归属金额之和
	private BigDecimal sum = FDCHelper.ZERO;
	
	// 变更拆分金额
	private BigDecimal changeSplitAmount = null;
	
	
	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try {
			return ConChangeSplitFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException("BOSException: getLocalInstance", e);
		}
	}
	/**
	 * 描述:将用hashtable保存的数据转化为一个EAS中的CoreBaseInfo对象
	 */	
	public CoreBaseInfo transmit(Hashtable hsData, Context ctx)
			throws TaskExternalException {
		// TODO Auto-generated method stub
		try {
//			for (int i = 0; i < hsData.size(); i++) {
//				Hashtable lineData = (Hashtable) hsData.get(new Integer(i));
//				// 当游标为0，表示第一条记录，此时要拼凑一个表头来
//				if (i == 0) {
					conChangeSplitInfo = transmitHead(hsData, ctx);
//				}
				if (conChangeSplitInfo == null) {
					return null;
				}
				ConChangeSplitEntryInfo entryInfo = transmitEntry(hsData, ctx);
	            int seq = conChangeSplitInfo.getEntrys().size() + 1;
	            entryInfo.setSeq(seq);
	            entryInfo.setParent(conChangeSplitInfo);
	            conChangeSplitInfo.getEntrys().add(entryInfo);
	            // 设置拆分状态
	            StringBuffer sqlStr = new StringBuffer("update T_CON_ContractChangeBill set FSplitState = '");
		        if(FDCHelper.ZERO.compareTo(sum) == 0) {
		        	conChangeSplitInfo.setSplitState(CostSplitStateEnum.NOSPLIT);
		        	sqlStr.append(CostSplitStateEnum.NOSPLIT_VALUE).append("'");
		        } else if(changeSplitAmount.compareTo(sum) == 1) {
		        	conChangeSplitInfo.setSplitState(CostSplitStateEnum.PARTSPLIT);
		        	sqlStr.append(CostSplitStateEnum.PARTSPLIT_VALUE).append("'");
		        } else {
		        	conChangeSplitInfo.setSplitState(CostSplitStateEnum.ALLSPLIT);
		        	sqlStr.append(CostSplitStateEnum.ALLSPLIT_VALUE).append("'");
		        }
		        sqlStr.append(" where fid='").append(contractChangeBillInfo.getId().toString()).append("'");
		        FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(sqlStr.toString());
		        sqlBuilder.executeUpdate(ctx);
		       
		        // 手动保存，用save才能更新相应的全项目动态成本表和待发生成本预测表
		        getController(ctx).save(conChangeSplitInfo);
//			}
		} catch (TaskExternalException e) {
			conChangeSplitInfo = null;
			throw e;
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		} catch (EASBizException e) {
			throw new TaskExternalException(e.getMessage());
		}		
		
		return conChangeSplitInfo;
	}
	
	private ConChangeSplitInfo transmitHead(Hashtable lineData, Context ctx) throws TaskExternalException{
		
		//组织编码
		String FOrgUnitLongNumber = (String) ((DataToken) lineData.get("FOrgUnit_longNumber")).data;
		//工程项目编码
		String fCurProjectLongNumber = (String) ((DataToken) lineData.get("FCurProject_longNumber")).data;
		//变更签证确认编码
		String fContractChangeNumber = (String) ((DataToken) lineData.get("FContractChange_number")).data;
		//变更签证确认名称
		String fContractChangeName = (String) ((DataToken) lineData.get("FContractChange_name")).data;
		//变更金额
		String fContractChangeAmount = (String) ((DataToken) lineData.get("FContractChange_amount")).data;
		//已拆分金额
		String fAmount = (String) ((DataToken) lineData.get("FAmount")).data;
		//未拆分金额
		String fUnSplitAmount = (String) ((DataToken) lineData.get("FUnSplitAmount")).data;
		//拆分人编码
		String fCreatorNumber = (String) ((DataToken) lineData.get("FCreator_number")).data;
		//拆分时间
		String fCreateTime = (String) ((DataToken) lineData.get("FCreateTime")).data;
		//审批人编码
		String fAuditorNumber = (String) ((DataToken) lineData.get("FAuditor_number")).data;
		//审批时间
		String fAuditTime = (String) ((DataToken) lineData.get("FAuditTime")).data;
		//状态
		String fState = (String) ((DataToken) lineData.get("FState")).data;
		
		/*
		 * 判断是否为空
		 */
		if (StringUtils.isEmpty(fCurProjectLongNumber)) {
			throw new TaskExternalException(getResource(ctx,"Import_fCurProjectCodingNumberNotNull"));
		}
		if (StringUtils.isEmpty(fContractChangeNumber)) {
			throw new TaskExternalException(getResource(ctx,"Import_fContractChangeNumberNotNull"));
		}
		if (StringUtils.isEmpty(fCreatorNumber)) {
			throw new TaskExternalException(getResource(ctx,"Import_fCreatorNumberNotNull"));
		}
		if (StringUtils.isEmpty(fCreateTime)) {
			throw new TaskExternalException(getResource(ctx,"Import_fCreateTimeNotNull"));
		}
		
		try {
			// 工程项目编码
			FilterInfo curFilter = new FilterInfo();
			curFilter.getFilterItems().add(new FilterItemInfo("LongNumber", fCurProjectLongNumber.replace('.', '!')));
			EntityViewInfo curView = new EntityViewInfo();
			curView.setFilter(curFilter);
			CurProjectCollection curcoll = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(curView);
			if(curcoll.size()>0){
				curProjectInfo = curcoll.get(0);
			}else{
				throw new TaskExternalException(getResource(ctx,"Import_fCurProjectCodingNumber1")+getResource(ctx, "Import_NOTNULL"));
			}
			// 组织编码
			CostCenterOrgUnitInfo costCenterOrgUnit = curProjectInfo.getCostCenter();
			if (costCenterOrgUnit == null) {
				throw new TaskExternalException(getResource(ctx,"Import_costcenter"));
			} else {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id", costCenterOrgUnit.getId().toString()));
				EntityViewInfo view = new EntityViewInfo();
				view.setFilter(filter);
				CostCenterOrgUnitCollection collection = CostCenterOrgUnitFactory.getLocalInstance(ctx).getCostCenterOrgUnitCollection(view);
				if(collection.size() > 0) {
					costCenterOrgUnit = collection.get(0);
				} 
			}

			// 变更签证确认
			FilterInfo contractChangeFilter = new FilterInfo();
			contractChangeFilter.getFilterItems().add(new FilterItemInfo("number", fContractChangeNumber));
			contractChangeFilter.getFilterItems().add(new FilterItemInfo("curproject.id", curProjectInfo.getId().toString()));
			contractChangeFilter.setMaskString("#0 and #1");
			EntityViewInfo contractChangeView = new EntityViewInfo();
			contractChangeView.setFilter(contractChangeFilter);
			ContractChangeBillCollection contractChangeBillColl = ContractChangeBillFactory.getLocalInstance(ctx).getContractChangeBillCollection(contractChangeView);
			if (contractChangeBillColl.size() > 0) {
				contractChangeBillInfo = contractChangeBillColl.get(0);
			} else {
				throw new TaskExternalException(getResource(ctx,"Import_fContractChangeNumberNotE"));
			}
			// 查询单头对象是否已经存在
			conChangeSplitInfo = this.getConChangeSplitInfoFromNumber(curProjectInfo.getId().toString(), contractChangeBillInfo.getId().toString(), ctx);
			if (conChangeSplitInfo != null) {
				if(conChangeSplitInfo.getSplitState() == CostSplitStateEnum.ALLSPLIT) {
					throw new TaskExternalException(getResource(ctx, "AllSplit"));
				}
				return conChangeSplitInfo;
			} else {
				conChangeSplitInfo = new ConChangeSplitInfo();
			}
			
			conChangeSplitInfo.setOrgUnit(costCenterOrgUnit.castToFullOrgUnitInfo());
			
			// 得到拆分人对象
			FilterInfo creatfilter = new FilterInfo();
			creatfilter.getFilterItems().add(new FilterItemInfo("number",fCreatorNumber));
			EntityViewInfo creatview = new EntityViewInfo();
			creatview.setFilter(creatfilter);
			UserCollection creatcoll = UserFactory.getLocalInstance(ctx).getUserCollection(creatview);
			if(creatcoll.size()>0){
				createUserInfo = creatcoll.get(0);	
			}else{
				throw new TaskExternalException(getResource(ctx,"Import_fCreatorNumber")+ getResource(ctx,"Import_NOTNULL"));
			}
			// 审批人编码，审核时间
			if(fState.trim().equals(getResource(ctx,"yishenpi"))){
				if (!StringUtils.isEmpty(fAuditorNumber)){ 
					FilterInfo audiofilter = new FilterInfo();
					audiofilter.getFilterItems().add(new FilterItemInfo("number",fAuditorNumber));
					EntityViewInfo audioview = new EntityViewInfo();
					audioview.setFilter(audiofilter);
					UserCollection audiocoll = UserFactory.getLocalInstance(ctx).getUserCollection(audioview);
					if(audiocoll.size()>0){
						auditorUserInfo = audiocoll.get(0);	
						conChangeSplitInfo.setAuditor(auditorUserInfo);
					} else {
						throw new TaskExternalException(getResource(ctx,"Import_fAuditorNameL21")+ getResource(ctx, "Import_NOTNULL"));
					}
				} else {
					throw new TaskExternalException(getResource(ctx,"SettlementCostSplit_Import_fAuditorNumberNotNull"));
				}
			}	
			if(fState.trim().equals(getResource(ctx,"yishenpi"))){
				if (StringUtils.isEmpty(fAuditTime)) {
					throw new TaskExternalException(getResource(ctx,"SettlementCostSplit_Import_fAuditTime"));
				}
				conChangeSplitInfo.setAuditTime(checkDate(fAuditTime,ctx));
			}
			//状态
			if(fState.trim().equals(getResource(ctx,"yishenpi"))){
				conChangeSplitInfo.setState(FDCBillStateEnum.AUDITTED);
			}else if(fState.trim().equals(getResource(ctx,"yitijiao"))){
				conChangeSplitInfo.setState(FDCBillStateEnum.SUBMITTED);
			}else{
				conChangeSplitInfo.setState(FDCBillStateEnum.SAVED);
			}
			conChangeSplitInfo.setCurProject(curProjectInfo);
			conChangeSplitInfo.setContractChange(contractChangeBillInfo);
			conChangeSplitInfo.setCreator(createUserInfo);
			conChangeSplitInfo.setCreateTime(new Timestamp(checkDate(fCreateTime, ctx).getTime()));
			conChangeSplitInfo.setPeriod(contractChangeBillInfo.getPeriod());
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		
		return conChangeSplitInfo;
	}
	
	private ConChangeSplitEntryInfo transmitEntry(Hashtable lineData, Context ctx) throws TaskExternalException{
		
		conChangeSplitEntryInfo = new ConChangeSplitEntryInfo();
		
		//归属财务核算对象代码
		String fCurProjectCodingNumber =  (String) ((DataToken) lineData.get("FCurProject_codingNumber")).data;
		//归属财务核算对象
		String FCurProjectName =  (String) ((DataToken) lineData.get("FCurProject_name_l2")).data;
		//归属财务成本科目代码
		String fEntryscostAccountNumber =  (String) ((DataToken) lineData.get("FEntrys$costAccount_number")).data;
		//归属财务成本科目
		String fEntryscostAccountName  =  (String) ((DataToken) lineData.get("FEntrys$costAccount_name_l2")).data;
		//拆分比例(%)
		String fEntrysParentSplitScale =  (String) ((DataToken) lineData.get("FEntrys$parent_splitScale")).data;
		//归属金额
		String fEntrysAmount =  (String) ((DataToken) lineData.get("FEntrys_amount")).data;
		BigDecimal bdEntrysAmount = null;
		//归属标准
		String fEntrysSplitType =  (String) ((DataToken) lineData.get("FEntrys_splitType")).data;
		//直接归属
		String fEntrysProductNumber =  (String) ((DataToken) lineData.get("FEntrys$product_number")).data;
		/*
		 * 判断是否为空
		 */
		if (StringUtils.isEmpty(fCurProjectCodingNumber)) {
			throw new TaskExternalException(getResource(ctx,"Import_fCurProjectCodingNumber2NotNull"));
		}
		if (StringUtils.isEmpty(fEntryscostAccountNumber)) {
			throw new TaskExternalException(getResource(ctx,"Import_fEntryscostAccountNumberNotNull"));
		}
//		if (StringUtils.isEmpty(fEntrysParentSplitScale)) {
//			throw new TaskExternalException(getResource(ctx,"SettlementCostSplit_Import_fEntrysSplitScaleNotNull"));
//		}
//		if (StringUtils.isEmpty(fEntrysAmount)) {
//			throw new TaskExternalException(getResource(ctx,"SettlementCostSplit_Import_fEntrysAmountNotNull"));
//		}
		if (StringUtils.isEmpty(fEntrysParentSplitScale) && StringUtils.isEmpty(fEntrysAmount)) {
			throw new TaskExternalException(getResource(ctx,"EntrysParentSplitScaleOrEntrysAmountNotNull"));
		}
		
		try {
			//归属财务成本科目代码
			FilterInfo costAccountFilter = new FilterInfo();
			fEntryscostAccountNumber = fEntryscostAccountNumber.replace('!', '.');
			costAccountFilter.getFilterItems().add(new FilterItemInfo("codingNumber", fEntryscostAccountNumber));
			costAccountFilter.getFilterItems().add(new FilterItemInfo("curProject.id", curProjectInfo.getId().toString()));
			costAccountFilter.setMaskString("#0 and #1");
			EntityViewInfo costAccountView = new EntityViewInfo();
			costAccountView.setFilter(costAccountFilter);
			CostAccountCollection costAccountColl = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(costAccountView);
			if (costAccountColl.size() > 0) {
				costAccountInfo = costAccountColl.get(0);
			} else {
				throw new TaskExternalException(getResource(ctx,"Import_fEntryscostAccountNumber")+getResource(ctx,"Import_NOTNULL"));
			}

			// 直接归属
			if(!StringUtils.isEmpty(fEntrysProductNumber)) {				
				FilterInfo productTypeFilter = new FilterInfo();
				productTypeFilter.getFilterItems().add(new FilterItemInfo("number", fEntrysProductNumber.trim()));
				EntityViewInfo productTypeView = new EntityViewInfo();
				productTypeView.setFilter(productTypeFilter);
				ProductTypeCollection productTypeCollection = ProductTypeFactory.getLocalInstance(ctx).getProductTypeCollection(productTypeView);
				if(productTypeCollection.size() > 0) {
					productTypeInfo = productTypeCollection.get(0);
				} else {
					throw new TaskExternalException(getResource(ctx, "ProductTypeNotFound"));
				}
			}

			//归属标准
		    CostSplitTypeEnum costSplitTypeEnum = null;
		    if(!StringUtils.isEmpty(fEntrysSplitType)){
			    costSplitTypeEnum = CostSplitTypeEnum.getEnum(fEntrysSplitType);
			    if(costSplitTypeEnum == null) {
				    throw new TaskExternalException(getResource(ctx, "CostSplitTypeEnum"));
			    }
			    conChangeSplitEntryInfo.setSplitType(costSplitTypeEnum);
		    }
		    
			// 判断分录是否存在
			ConChangeSplitEntryCollection coll = conChangeSplitInfo.getEntrys();
			if (coll!=null && coll.size()>0) {
				ConChangeSplitEntryInfo tempInfo = null;
				for (int i = 0; i < coll.size(); i++) {
					tempInfo = coll.get(i);
					if (tempInfo.getCostAccount().getId().toString().equals(costAccountInfo.getId().toString())) {
						throw new TaskExternalException(getResource(ctx, "EntryIsRepeat"));
					}
				}
			}
			
			BigDecimal entrysAmount = null;
			// 待拆分的变更金额
			if (changeSplitAmount == null) {
				changeSplitAmount = contractChangeBillInfo.getBalanceAmount();	// 结算金额
				if (changeSplitAmount == null) {
					changeSplitAmount = contractChangeBillInfo.getAmount();	// 本位币金额
					if (changeSplitAmount == null) {
						changeSplitAmount = FDCHelper.ZERO;
					}
				}
			}
			//拆分比例(%)
		    if (!StringUtils.isEmpty(fEntrysParentSplitScale)) {
		    	try {
				    if (Float.parseFloat(fEntrysParentSplitScale) > 100 || Float.parseFloat(fEntrysParentSplitScale) < 0) {
					    throw new TaskExternalException(getResource(ctx,"Import_fEntrysParentSplitScale"));
				    }
				    entrysAmount = FDCHelper.divide(FDCHelper.multiply(changeSplitAmount, new BigDecimal(fEntrysParentSplitScale)), new BigDecimal(100));
		    	} catch (Exception e) {
		    		throw new TaskExternalException(getResource(ctx, "EntrysParentSplitScaleIsNotNumber"));
		    	}
		    } else {
		    	try {
		    		entrysAmount = new BigDecimal(fEntrysAmount);
		    	} catch (Exception e) {
		    		throw new TaskExternalException(getResource(ctx, "EntrysAmountIsNotNumber"));
		    	}
		    }

		    sum = FDCHelper.ZERO;
			for (int i=0; i<coll.size(); i++) {
				BigDecimal money = coll.get(i).getAmount();
				if (money == null) {
					money = FDCHelper.ZERO;
				}
				sum = sum.add(money);
			}
			sum = sum.add(entrysAmount);
			
			if (sum.compareTo(changeSplitAmount) > 0) {
				throw new TaskExternalException(getResource(ctx, "Amount"));
			}			
			
			//拆分比例(%)
		    if (!StringUtils.isEmpty(fEntrysParentSplitScale)) {
		    	conChangeSplitEntryInfo.setSplitScale(new BigDecimal(fEntrysParentSplitScale));
		    } else {
		    	conChangeSplitEntryInfo.setSplitScale(FDCHelper.multiply(FDCHelper.divide(entrysAmount, changeSplitAmount), new BigDecimal(100)));
		    }
			conChangeSplitEntryInfo.setIsLeaf(true);
			conChangeSplitEntryInfo.setCostAccount(costAccountInfo);
			conChangeSplitEntryInfo.setAmount(entrysAmount);
			conChangeSplitEntryInfo.setProduct(productTypeInfo);		
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		
		return conChangeSplitEntryInfo;
	}
	//日期判断
	private Date checkDate(String dateStr,Context ctx) throws TaskExternalException{
		try {
		    if(StringUtils.isEmpty(dateStr)) return null;
                DateFormat df = null;
		    if(dateStr.trim().length() <= "yyyy-MM-dd".length()){ // 处理 "yyyy-MM-d"
			    df = new SimpleDateFormat("yyyy-MM-dd");
		    }else if(dateStr.trim().length() <= "yyyy-MM-dd  HH:mm".length()){ //处理 yyyy-MM-d HH:mm情况
			    df = new SimpleDateFormat("yyyy-MM-dd HH:mm");	
		    }else if(dateStr.trim().length() <= "yyyy-MM-dd  HH:mm:ss".length()){
			    df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    }else{
			    throw new TaskExternalException(getResource(ctx,"Import_DateForm"));
		    }
		    df.setLenient(false);
		    return df.parse(dateStr);
		} catch (ParseException e) {
//			e.printStackTrace();
			throw new TaskExternalException(getResource(ctx,"Import_DateForm"));
		}
    }
//	public void submit(CoreBaseInfo coreBaseInfo, Context ctx) throws TaskExternalException {
//		if (coreBaseInfo == null || coreBaseInfo instanceof ContractBillInfo == false) {
//			return;
//		}
//		try {
//			ConChangeSplitInfo billBase = (ConChangeSplitInfo) coreBaseInfo;
//			String id = getIdFromNumber(billBase.getNumber(), ctx);
//			if (StringUtil.isEmptyString(id)) {
//				getController(ctx).addnew(coreBaseInfo);
//			} else {
//				coreBaseInfo.setId(BOSUuid.read(id));
//				getController(ctx).update(new ObjectUuidPK(id), coreBaseInfo);
//			}
//
//		} catch (Exception ex) {
//			throw new TaskExternalException(ex.getMessage(), ex.getCause());
//		}
//	}
	/**
	 * 根据number获取对象，如果没有则返回null
	 * @param number
	 * @param ctx
	 * @return
	 * @throws TaskExternalException
	 * @author Robin
	 * @throws EASBizException 
	 */
	private ConChangeSplitInfo getConChangeSplitInfoFromNumber(String curProjectID, String contractChangeBillId, Context ctx) throws TaskExternalException{
		try {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", curProjectID));
			filter.getFilterItems().add(new FilterItemInfo("ContractChange.id", contractChangeBillId));			
			filter.setMaskString("#0 and #1");
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			ConChangeSplitCollection coll = ConChangeSplitFactory.getLocalInstance(ctx).getConChangeSplitCollection(view);
			if (coll != null&&coll.size()>0) {
				return coll.get(0);
			}
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		} 
		return null;
	}

	/**
	 * 得到资源文件
	 * @author 郑杰元
	 */
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}
}
