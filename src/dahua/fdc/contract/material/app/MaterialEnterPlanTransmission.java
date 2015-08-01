package com.kingdee.eas.fdc.material.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.MeasureUnitCollection;
import com.kingdee.eas.basedata.assistant.MeasureUnitFactory;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.master.material.MaterialCollection;
import com.kingdee.eas.basedata.master.material.MaterialFactory;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.material.MaterialEnterPlanBillCollection;
import com.kingdee.eas.fdc.material.MaterialEnterPlanBillFactory;
import com.kingdee.eas.fdc.material.MaterialEnterPlanBillInfo;
import com.kingdee.eas.fdc.material.MaterialEnterPlanEntryCollection;
import com.kingdee.eas.fdc.material.MaterialEnterPlanEntryInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;

/**
 * 
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		材料进场计划转换类
 *		
 * @author		xiaochong
 * @version		EAS7.0		
 * @createDate	2011-6-13	 
 * @see
 */
public class MaterialEnterPlanTransmission extends AbstractDataTransmission {
	
	private static final String resource = "com.kingdee.eas.fdc.material.MaterialTransmissionResource";
	// 单据状态映射
	private Map stateMap = new HashMap();
	// 材料进场计划实体
	MaterialEnterPlanBillInfo materialEnterPlanBillinfo = null;
	// 工程项目实体
	CurProjectInfo curProjectInfo = null;
	// 组织实体
	FullOrgUnitInfo orgUnitInfo = null;
	// 施工合同实体
	ContractBillInfo contractBillInfo = null;
	// 制单人实体
	UserInfo creatorInfo = null;

	/** 单据状态映射初始化 */
	public void initStateMap(Context ctx) {
		stateMap.put(getResource(ctx, "AUDITTED"), "4AUDITTED");
		stateMap.put(getResource(ctx, "SUBMITTED"), "2SUBMITTED");
		stateMap.put(getResource(ctx, "SAVED"), "1SAVED");
	}

	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try {
			return MaterialEnterPlanBillFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e);
		}
	}


	/** 转换方法 */
	public CoreBaseInfo transmit(Hashtable hsdata, Context ctx)
			throws TaskExternalException {
		initStateMap(ctx);
		return innerTransform(hsdata, ctx);
	}

	/** 内调转换处理方法 */
	protected CoreBaseInfo innerTransform(Hashtable hsdata, Context ctx)
			throws TaskExternalException {
		try {
			materialEnterPlanBillinfo = innerTransformHead(hsdata, ctx);
			MaterialEnterPlanEntryInfo entry = innerTransformEntry(hsdata, ctx);
			int seq = materialEnterPlanBillinfo.getEntrys().size() + 1;
			entry.setSeq(seq);
			entry.setParent(materialEnterPlanBillinfo);
			materialEnterPlanBillinfo.getEntrys().add(entry);
		} catch (TaskExternalException e) {
			materialEnterPlanBillinfo = null;
			throw e;
		}
		return materialEnterPlanBillinfo;
	}
	
	
	/** 单据头*转换处理方法 */
	private MaterialEnterPlanBillInfo innerTransformHead(Hashtable hsdata, Context ctx) throws TaskExternalException{
		
		String curProjectCostCenterLongNumber = getString(hsdata, "FCurProjectCostCenter_longNumber"); 		// 组织长编码
		String curProjectLongNumber = getString(hsdata, "FCurProject_longNumber"); 							// 工程项目编码
		String number = getString(hsdata, "FNumber"); 														// 单据编码
		String contractBillNumber = getString(hsdata, "FContractBill_number"); 								// 施工合同
//		String contractBillPartBName = getString(hsdata, "FContractBillPartB_name_l2"); 					// 施工单位
		String address = getString(hsdata, "FAddress"); 													// 送货地址
		String creatorNumber = getString(hsdata, "FCreator_number"); 										// 制单人
		String createTime = getString(hsdata, "FCreateTime"); 												// 制单日期
		String auditorNumber = getString(hsdata, "FAuditor_number"); 										// 审批人
		String auditTime = getString(hsdata, "FAuditTime"); 												// 审批时间
		String state = getString(hsdata, "FState");															// 单据状态
		
		
		
		// 必录校验
		FDCTransmissionHelper.isFitLength(getResource(ctx, "CurProjectLongNumberIsNull"), curProjectLongNumber);
		FDCTransmissionHelper.isFitLength(getResource(ctx, "MaterialEnterPlanNumberIsNull"), number);
		FDCTransmissionHelper.isFitLength(getResource(ctx, "ContractBillNumberIsNull"), contractBillNumber);
		FDCTransmissionHelper.isFitLength(getResource(ctx, "MaterialEnterPlanCreatorNumberIsNull"), creatorNumber);
		FDCTransmissionHelper.isFitLength(getResource(ctx, "MaterialEnterPlanCreateTimeIsNull"), createTime);
		FDCTransmissionHelper.isFitLength(getResource(ctx, "MaterialEnterPlanState"), state);
		// 长度校验
		FDCTransmissionHelper.isFitLength(curProjectCostCenterLongNumber, 40, getResource(ctx, "CurProjectCostCenterLongNumberIsOver40"));
		FDCTransmissionHelper.isFitLength(curProjectLongNumber, 40, getResource(ctx, "CurProjectLongNumberIsOver40"));
		FDCTransmissionHelper.isFitLength(number, 80, getResource(ctx, "MaterialEnterPlanNumberIsOver80"));
		FDCTransmissionHelper.isFitLength(address, 80, getResource(ctx, "MaterialEnterPlanAddressIsOver80"));
		
		// 日期转换校验
		Date createDate = FDCTransmissionHelper.checkDateFormat(createTime, getResource(ctx, "DateFormatIsError"));
		// 单据状态校验
		FDCBillStateEnum stateEnum = getState(ctx, (String) stateMap.get(state.trim()));
		// 根据单据状态校验 审批人、审批日期必录
		if(stateEnum.equals(FDCBillStateEnum.AUDITTED)){
			FDCTransmissionHelper.isFitLength(getResource(ctx, "MaterialEnterPlanAuditor"), auditorNumber);
			FDCTransmissionHelper.isFitLength(getResource(ctx, "MaterialEnterPlanAuditTime"),auditTime);
		}
		
		
		
		
		UserInfo auditorInfo = null;					// 审批人实体
		Date auditorDate = null;						// 审批时间
		
		try {
			// 检查单据是否已存在
			MaterialEnterPlanBillCollection coll = MaterialEnterPlanBillFactory.getLocalInstance(ctx)
							.getMaterialEnterPlanBillCollection(getEntityViewInfoInstance("number", number));
			if (isExist(coll)) {
				return materialEnterPlanBillinfo = coll.get(0);
			} else {
				materialEnterPlanBillinfo = new MaterialEnterPlanBillInfo();
			}
			
			
			
			// 从数据库中获取工程项目
			CurProjectCollection curProjectColl = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(
							getEntityViewInfoInstance("longNumber", curProjectLongNumber.replace('.', '!')));
			if (!isExist(curProjectColl)) {
				FDCTransmissionHelper.isThrow(getResource(ctx, "CurProjectIsNotExist"));
			}
			curProjectInfo = curProjectColl.get(0);
			

			// 校验组织长编码
			CostCenterOrgUnitInfo costCenterOrgUnit = curProjectInfo.getCostCenter();
			String id = getUid(costCenterOrgUnit);
			CostCenterOrgUnitCollection costCenterOrgUnitColl = CostCenterOrgUnitFactory
					.getLocalInstance(ctx).getCostCenterOrgUnitCollection(getEntityViewInfoInstance("id", id));
			
			if (isExist(costCenterOrgUnitColl)) {
				if (!StringUtils.isEmpty(curProjectCostCenterLongNumber)
						&& !costCenterOrgUnitColl.get(0).getLongNumber()
								.equalsIgnoreCase(curProjectCostCenterLongNumber.trim().replace('.', '!'))) {
					FDCTransmissionHelper.isThrow(getResource(ctx, "CurProjectCostCenterIsNotExist"));
				}
				costCenterOrgUnit = costCenterOrgUnitColl.get(0);
			}
			orgUnitInfo = costCenterOrgUnit.castToFullOrgUnitInfo();
			
			
			
			// 从数据库中获取施工合同// 根据施工合同自动带出施工单位
			ContractBillCollection contractBillColl = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(
							getEntityViewInfoInstance("number", contractBillNumber));
			if (!isExist(contractBillColl)) {
					FDCTransmissionHelper.isThrow(getResource(ctx, "ContractBillIsNotExist"));
			}
			contractBillInfo = contractBillColl.get(0);
			
			

			// 从数据库中获取制单人
			UserCollection userColl = UserFactory.getLocalInstance(ctx)
					.getUserCollection( getEntityViewInfoInstance("number", creatorNumber));
			if (!isExist(userColl)) {
					FDCTransmissionHelper.isThrow(getResource(ctx, "CreatorIsNotExist"));
			}
			creatorInfo = userColl.get(0);
			

			
			
			// 在auditorNumber不为空时从数据库中获取审批人
			if (!StringUtils.isEmpty(auditorNumber)) {
				userColl = UserFactory.getLocalInstance(ctx).getUserCollection(
						getEntityViewInfoInstance("number", auditorNumber));
				if (!isExist(userColl)) {
						FDCTransmissionHelper.isThrow(getResource(ctx, "AuditorIsNotExist"));
				}
				auditorInfo = userColl.get(0);
			}
			// 在auditTime不为空时进行转换校验
			if (!StringUtils.isEmpty(auditTime)) {
				auditorDate = FDCTransmissionHelper.checkDateFormat(auditTime, getResource(ctx, "DateFormatIsError"));
			}
			
			
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		materialEnterPlanBillinfo.setOrgUnit(orgUnitInfo);
		materialEnterPlanBillinfo.setCurProject(curProjectInfo);
		materialEnterPlanBillinfo.setNumber(number);
		materialEnterPlanBillinfo.setContractBill(contractBillInfo);
		materialEnterPlanBillinfo.setAddress(address);
		materialEnterPlanBillinfo.setCreator(creatorInfo);
		materialEnterPlanBillinfo.setCreateTime(new Timestamp(createDate.getTime()));
		materialEnterPlanBillinfo.setAuditor(auditorInfo);
		materialEnterPlanBillinfo.setAuditTime(auditorDate);
		materialEnterPlanBillinfo.setState(stateEnum);
		
		return materialEnterPlanBillinfo;
	}
	
	
	/** 分录*转换处理方法 */
	private MaterialEnterPlanEntryInfo innerTransformEntry(Hashtable hsdata, Context ctx) throws TaskExternalException{
		
		String entrysMaterialNumber = getString(hsdata, "FEntrysMaterial_number"); 							// 物料编码
//		String entrysMaterialName = getString(hsdata, "FEntrysMaterial_name_l2"); 							// 物料名称
		String entrysModel = getString(hsdata, "FEntrys_model"); 											// 规格型号
		String entrysProduceLeadTime = getString(hsdata, "FEntrys_produceLeadTime"); 						// 收货可提前天数
		String entrysUnitName = getString(hsdata, "FEntrysUnit_name_l2"); 									// 单位
		String entrysQuantity = getString(hsdata, "FEntrys_quantity"); 										// 申报数量
		String entrysEnterTime = getString(hsdata, "FEntrys_enterTime"); 									// 进场时间
		String entrysAuditQuantity = getString(hsdata, "FEntrys_auditQuantity"); 							// 核定数量
		String entrysRemark = getString(hsdata, "FEntrys_remark"); 											// 备注
		String entrysMaterialCon = getString(hsdata, "FEntrys_materialCon"); 								// 材料合同
		String entrysSupplier = getString(hsdata, "FEntrys_supplier"); 										// 供应商
		
		
		// 必录校验
		FDCTransmissionHelper.isFitLength(getResource(ctx, "EntrysMaterialNumberIsNull"), entrysMaterialNumber);
		FDCTransmissionHelper.isFitLength(getResource(ctx, "EntrysQuantityIsNull"), entrysQuantity);
		FDCTransmissionHelper.isFitLength(getResource(ctx, "EntrysAuditQuantityIsNull"), entrysAuditQuantity);
		// 长度校验
		FDCTransmissionHelper.isFitLength(entrysMaterialNumber, 40, getResource(ctx, "EntrysMaterialNumberIsOver80"));
		FDCTransmissionHelper.isFitLength(entrysRemark, 80, getResource(ctx, "EntrysRemarkIsOver80"));
		
		// BigDecimal数值转换校验
		BigDecimal quantityDecimal=FDCTransmissionHelper.str2BigDecimal(entrysQuantity);						
		BigDecimal auditQuantityDecimal=FDCTransmissionHelper.str2BigDecimal(entrysAuditQuantity);
		
		// 若填写了收获可提前天数，则进行数值转换校验
		BigDecimal produceLeadTime=null;
		if(!StringUtils.isEmpty(entrysProduceLeadTime)){
			if(!entrysProduceLeadTime.matches("^\\d+$")) FDCTransmissionHelper.isThrow(getResource(ctx, "MaterialEnterPlanNumberFormatError"));
			produceLeadTime=new BigDecimal(entrysProduceLeadTime);
		}
		// 若填写了进场时间，则进行格式转换校验
		Date enterTime=null;
		if(!StringUtils.isEmpty(entrysEnterTime)){
			enterTime = FDCTransmissionHelper.checkDateFormat(entrysEnterTime, getResource(ctx, "DateFormatIsError"));
		}
		
		
		
		
			
		
		
		MaterialInfo materialInfo = null; 					// 物料
		MeasureUnitInfo unitInfo = null; 					// 单位
		try {
			
			if(materialEnterPlanBillinfo.getId()!=null)
			{
				MaterialEnterPlanEntryCollection planEntryColl = materialEnterPlanBillinfo.getEntrys();
				if (isExist(planEntryColl)) {
					for (int i = 0; i < planEntryColl.size(); i++) {
						
						String id = getUid(planEntryColl.get(i).getMaterial());
						
						MaterialCollection materialColl = MaterialFactory.getLocalInstance(ctx)
										.getMaterialCollection(getEntityViewInfoInstance("id", id));
						if (isExist(materialColl) && materialColl.get(0).getNumber()
										.equalsIgnoreCase(entrysMaterialNumber)) {
							FDCTransmissionHelper.isThrow(getResource(ctx, "EntryIsRepeated"));
						}
					}
				}
			}
			
			// 从数据库中获取物料
			MaterialCollection materialColl = MaterialFactory.getLocalInstance(
					ctx).getMaterialCollection(getEntityViewInfoInstance("number",entrysMaterialNumber));
			if (!isExist(materialColl)) {
				FDCTransmissionHelper.isThrow(getResource(ctx, "EntrysMaterialIsNotExist"));
			}
			materialInfo = materialColl.get(0);

			// 从数据库中获取单位
			MeasureUnitCollection unitColl = MeasureUnitFactory.getLocalInstance(
					ctx).getMeasureUnitCollection(getEntityViewInfoInstance("name", entrysUnitName));
			if (StringUtils.isEmpty(entrysUnitName) || !isExist(unitColl)) {
				// FDCTransmissionHelper.isThrow(entrysUnitName, getResource(ctx, "MaterialMeasureUnitIsNotExist"));
				String id = getUid(materialInfo.getBaseUnit());

				MeasureUnitCollection coll = MeasureUnitFactory .getLocalInstance(ctx)
									.getMeasureUnitCollection(getEntityViewInfoInstance("id", id));
				if (isExist(coll)) unitInfo = coll.get(0);
			} else {
				unitInfo = unitColl.get(0);
			}
			
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		}
		
		
		
		MaterialEnterPlanEntryInfo info=new MaterialEnterPlanEntryInfo();
		info.setMaterial(materialInfo);
		info.setModel(entrysModel);
		info.setProduceLeadTime(produceLeadTime);
		info.setUnit(unitInfo);	
		info.setQuantity(quantityDecimal);
		info.setEnterTime(enterTime);
		info.setAuditQuantity(auditQuantityDecimal);
		info.setRemark(entrysRemark);
		info.setMaterialCon(entrysMaterialCon);
		info.setSupplier(entrysSupplier);
		
		return info;
	}
	
	
	/** 获取字符串参数 */
	private String getString(Hashtable hsdata, Object key) {
		return (String) ((DataToken) hsdata.get(key)).data;
	}
	
	/** 校验单据状态并获取对应状态枚举对象 */
	private FDCBillStateEnum getState(Context ctx,String state)
			throws TaskExternalException {
		FDCBillStateEnum stateEnum = null;

		if (FDCBillStateEnum.SAVED_VALUE.equalsIgnoreCase(state)) {
			stateEnum = FDCBillStateEnum.SAVED;
		} else if (FDCBillStateEnum.SUBMITTED_VALUE.equalsIgnoreCase(state)) {
			stateEnum = FDCBillStateEnum.SUBMITTED;
		} else if (FDCBillStateEnum.AUDITTED_VALUE.equalsIgnoreCase(state)) {
			stateEnum = FDCBillStateEnum.AUDITTED;
		} else {
			FDCTransmissionHelper.isThrow(state, getResource(ctx, "MaterialEnterPlanUnknownState"));
		}

		return stateEnum;
	}
	
	/** 指定过滤条件获取视图实例 */
	private EntityViewInfo getEntityViewInfoInstance(String property, String value) {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(property, value, CompareType.EQUALS));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);

		return view;
	}
	
	/** 集合是否存在 */
	private boolean isExist(IObjectCollection collection) {
		return (collection == null || collection.size() == 0) ? false : true;
	}
	
	/** 获取Info对象的ID字符串 */
	private String getUid(CoreBaseInfo info) {
		BOSUuid uId = info.getId();
		return (uId != null) ? uId.toString() : "";
	}
	
	/**
	 * 得到资源文件
	 * @author 郑杰元
	 */
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}
}
