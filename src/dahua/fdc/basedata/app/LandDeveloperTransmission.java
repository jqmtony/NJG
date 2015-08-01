package com.kingdee.eas.fdc.basedata.app;

import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.org.CtrlUnitCollection;
import com.kingdee.eas.basedata.org.CtrlUnitFactory;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.LandDeveloperFactory;
import com.kingdee.eas.fdc.basedata.LandDeveloperInfo;
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
 * 描述：		甲方（开发商）转换类
 *		
 * @author		xiaochong
 * @version		EAS7.0		
 * @createDate	2011-6-13	 
 * @see
 */
public class LandDeveloperTransmission extends AbstractDataTransmission {

	private static final String resource = "com.kingdee.eas.fdc.basedata.FDCBaseDataResource";
	private static String resourceCommon = "com.kingdee.eas.common.EASCommonResource";

	protected ICoreBase getController(Context ctx)
			throws TaskExternalException {
		try {
			return LandDeveloperFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e);
		}
	}

	/** 转换方法 */
	public CoreBaseInfo transmit(Hashtable hsdata, Context ctx)
			throws TaskExternalException {
		return innerTransform(hsdata, ctx);
	}

	/** 内调转换处理方法 */
	protected CoreBaseInfo innerTransform(Hashtable hsdata, Context ctx) throws TaskExternalException {
		
		String fORGLongNumber = getString(hsdata, "FCU_longNumber"); // 组织编码
		String number = getString(hsdata, "FNumber");							// 编码
		String name = getString(hsdata, "FName_l2");							// 名称
		String description = getString(hsdata, "FDescription_l2");				// 描述
		String isEnabled = getString(hsdata, "FIsEnabled");						// 启用
		
		// 校验必录
		//		FDCTransmissionHelper.isFitLength(getResource(ctx, "LandDeveloperCULongNumberIsNull", resource), fORGLongNumber);
		FDCTransmissionHelper.isFitLength(getResource(ctx, "LandDeveloperNumberIsNull",resource), number);
		FDCTransmissionHelper.isFitLength(getResource(ctx, "LandDeveloperNameIsNull",resource), name);
		FDCTransmissionHelper.isFitLength(getResource(ctx, "LandDeveloperORGLongNumberIsNull", resource), fORGLongNumber);

		// 校验长度
		FDCTransmissionHelper.isFitLength(number, 80, getResource(ctx, "LandDeveloperNumberIsOver80",resource));
		FDCTransmissionHelper.isFitLength(name, 80, getResource(ctx, "LandDeveloperNameIsOver80",resource));
		FDCTransmissionHelper.isFitLength(description, 200, getResource(ctx, "LandDeveloperDescriptionIsOver200",resource));
		
		boolean bIsEnabled = getResource(ctx, "no",resourceCommon).equalsIgnoreCase(isEnabled) ? false : true;
		
		
		//从数据库中获取控制单元并校验是否为实体财务组织
		CtrlUnitInfo unitInfo = null;
		FullOrgUnitInfo orgInfo = null;
		try {
			CtrlUnitCollection unitColl = CtrlUnitFactory.getLocalInstance(ctx).getCtrlUnitCollection(
							getEntityViewInfoInstance("longNumber", fORGLongNumber.replace('.', '!')));
			FullOrgUnitCollection orgColl = FullOrgUnitFactory.getLocalInstance(ctx).getFullOrgUnitCollection(
					getEntityViewInfoInstance("longNumber", fORGLongNumber.replace('.', '!')));
			if (orgColl == null || orgColl.size() == 0) {
				FDCTransmissionHelper.isThrow(getResource(ctx, "LandDeveloperORGLongNumberIsNull", resource));
			} else {
				unitInfo = unitColl.get(0);
				orgInfo = orgColl.get(0);
				if (!orgInfo.isIsCompanyOrgUnit()) {
					FDCTransmissionHelper.isThrow(getResource(ctx, "LandDeveloperIsNotCompanyOrgUnitEntity", resource));
				}
			}
			
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		}
		
		
		/// 校验后设值
		LandDeveloperInfo info=new LandDeveloperInfo();
		info.setCU(unitInfo);
		info.setNumber(number);
		info.setName(name);
		info.setDescription(description);
		info.setIsEnabled(bIsEnabled);
		info.setOrgUnit(orgInfo);

		return info;
	}
	
	/** 获取字符串参数 */
	private String getString(Hashtable hsdata, Object key) {
		return (String) ((DataToken) hsdata.get(key)).data;
	}
	
	/** 指定过滤条件获取视图实例 */
	private EntityViewInfo getEntityViewInfoInstance(String property,String value) {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(property,value,CompareType.EQUALS));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		
		return view;
	}
	/**
	 * 得到资源文件
	 * @author 郑杰元
	 */
	public static String getResource(Context ctx, String key,String resourceStr) {
		return ResourceBase.getString(resourceStr, key, ctx.getLocale());
	}
}
