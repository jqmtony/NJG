package com.kingdee.eas.fdc.basedata.app;

import java.math.BigDecimal;
import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
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
 * 描述：		合同类型转换类—
 *		
 * @author		xiaochong
 * @version		EAS7.0		
 * @createDate	2011-6-10	 
 * @see
 */
public class ContractTypeTransmission extends AbstractDataTransmission {

	private static final String resource = "com.kingdee.eas.fdc.basedata.FDCBaseDataResource";
	private static String resourceCommon = "com.kingdee.eas.common.EASCommonResource";

	protected ICoreBase getController(Context ctx)
			throws TaskExternalException {
		try {
			return ContractTypeFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e);
		}
	}
	
	// 覆写父类submit方法
	public void submit(CoreBaseInfo coreBaseInfo, Context ctx) throws TaskExternalException {
		try {
			ContractTypeFactory.getLocalInstance(ctx).addnew(coreBaseInfo);
		} catch (Exception e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		}
	}

	/** 转换方法 */
	public CoreBaseInfo transmit(Hashtable hsdata, Context ctx)
			throws TaskExternalException {
		return innerTransform(hsdata, ctx);
	}

	/** 内调转换处理方法 */
	protected CoreBaseInfo innerTransform(Hashtable hsdata, Context ctx)
			throws TaskExternalException {

		String number = getString(hsdata, "FNumber");							// 编码
		String name = getString(hsdata, "FName_l2");							// 名称
		String dutyOrgUnitNumber = getString(hsdata, "FDutyOrgUnit_number");	// 责任部门
		String payScale = getString(hsdata, "FPayScale");						// 付款比例（%）
		String stampTaxRate = getString(hsdata, "FStampTaxRate");				// 印花税率（%）
		String isCost = getString(hsdata, "FIsCost"); 							// 进入动态成本
		String description = getString(hsdata, "FDescription_l2");				// 描述
		String isEnabled = getString(hsdata, "FIsEnabled");						// 启用
		
		
		// 校验必录
		FDCTransmissionHelper.isFitLength(getResource(ctx, "ContractTypeNumberIsNull",resource), number);
		FDCTransmissionHelper.isFitLength(getResource(ctx, "ContractTypeNameIsNull",resource), name);
		// 校验长度
		FDCTransmissionHelper.isFitLength(number, 80, getResource(ctx, "ContractTypeNumberIsOver80",resource));
		FDCTransmissionHelper.isFitLength(name, 80, getResource(ctx, "ContractTypeNameIsOver80",resource));
		FDCTransmissionHelper.isFitLength(description, 200, getResource(ctx, "ContractTypeDescriptionIsOver200",resource));
		
		// 校验比率范围
		BigDecimal payScaleDecmial = FDCTransmissionHelper.isRangedInHundred(payScale);
		BigDecimal stampTaxRateDecmial = FDCTransmissionHelper.isRangedInHundred(stampTaxRate);
		
		boolean bIsCost = getResource(ctx, "no",resourceCommon).equalsIgnoreCase(isCost) ? false : true;
		boolean bIsEnabled = getResource(ctx, "yes",resourceCommon).equalsIgnoreCase(isEnabled) ? true : false;
		
		
		
		AdminOrgUnitInfo dutyOrgUnit=null;
		try {
			if(getController(ctx).exists(getFilterInfoInstance("number", number))){
				FDCTransmissionHelper.isThrow(getResource(ctx, "ContractTypeNumberHasExist",resource));
			}
			if(getController(ctx).exists(getFilterInfoInstance("name", name))){
				FDCTransmissionHelper.isThrow(getResource(ctx, "ContractTypeNameHasExists",resource));
			}

			
			//从数据库中获取责任部门
			AdminOrgUnitCollection dutyOrgUnitColl = AdminOrgUnitFactory.getLocalInstance(ctx).getAdminOrgUnitCollection(
							getEntityViewInfoInstance("number", dutyOrgUnitNumber));
			if (dutyOrgUnitColl.size()>0) {
				dutyOrgUnit = dutyOrgUnitColl.get(0);
			}
		} catch (Exception e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		}
		
		/// 校验后设值
		ContractTypeInfo info=new ContractTypeInfo();
		info.setNumber(number);
		info.setName(name);
		info.setDutyOrgUnit(dutyOrgUnit);
		info.setPayScale(payScaleDecmial);
		info.setStampTaxRate(stampTaxRateDecmial);
		info.setIsCost(bIsCost);
		info.setIsEnabled(bIsEnabled);
		info.setDescription(description);
		info.setIsLeaf(true);
		info.setLevel(1);
		info.setDisplayName(name);
		
		return info;
	}

	/** 获取字符串参数 */
	private String getString(Hashtable hsdata, Object key) {
		return (String) ((DataToken) hsdata.get(key)).data;
	}

	/** 指定过滤条件获取视图实例 */
	private EntityViewInfo getEntityViewInfoInstance(String property, String value) {
		FilterInfo filter = getFilterInfoInstance(property, value);
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);

		return view;
	}

	/** 获取指定过滤条件Filter */
	private FilterInfo getFilterInfoInstance(String property, String value) {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(property, value, CompareType.EQUALS));
		return filter;
	}

	
	
	/**
	 * 得到资源文件
	 * @author 郑杰元
	 */
	public static String getResource(Context ctx, String key,String resourceStr) {
		return ResourceBase.getString(resourceStr, key, ctx.getLocale());
	}
}
