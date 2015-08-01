/**
 * 
 */
package com.kingdee.eas.fdc.basedata.app;

import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.eas.fdc.basedata.DeductTypeFactory;
import com.kingdee.eas.fdc.basedata.DeductTypeInfo;
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
 * @author		朱俊
 * @version		EAS7.0		
 * @createDate	2011-6-9	 
 * @see						
 */
public class DeductTypeTransmission extends AbstractDataTransmission {

	private static String resource = "com.kingdee.eas.fdc.basedata.FDCBaseDataResource";
	private static String resourceCommon = "com.kingdee.eas.common.EASCommonResource";
	
	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try {
			return DeductTypeFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException("BOSException: getLocalInstance", e);
		}
	}

	public CoreBaseInfo transmit(Hashtable hsData, Context ctx)
			throws TaskExternalException {
		DeductTypeInfo info = new DeductTypeInfo();

		// 编码
		String fNumber = (String) ((DataToken) hsData.get("FNumber")).data;
		// 名称
		String fNamel2 = (String) ((DataToken) hsData.get("FName_l2")).data;
		// 描述
		String fDescriptionl2 = (String) ((DataToken) hsData.get("FDescription_l2")).data;
		// 启用
		String fIsEnabled = (String) ((DataToken) hsData.get("FIsEnabled")).data;

		/*
		 * 判断是否为空
		 */
		if (StringUtils.isEmpty(fNumber)) {
			throw new TaskExternalException(getResource(ctx, "Import_NumberIsNull",resource));
		}
		if (StringUtils.isEmpty(fNamel2)) {
			throw new TaskExternalException(getResource(ctx, "Import_NameIsNull",resource));
		}
		/*
		 * 判断字符长度
		 */
		if (fNumber.length() > 80) {
			throw new TaskExternalException(getResource(ctx, "Import_NumberIsTooLong",resource));
		}
		if (fNamel2.length() > 80) {
			throw new TaskExternalException(getResource(ctx, "Import_NameIsTooLong",resource));
		}
		if (fDescriptionl2.length() > 200) {
			throw new TaskExternalException(getResource(ctx, "CostAccount_Import_fDescriptionL2",resource));
		}

		
		 /*
		 * 布尔判断
		 */
		// 启用
		if (fIsEnabled.trim().equals(getResource(ctx, "yes",resourceCommon))) {
			info.setIsEnabled(true);
		}else {
			info.setIsEnabled(false);
		}
		info.setNumber(fNumber);
		info.setName(fNamel2);
		info.setDescription(fDescriptionl2);
      
		return info;
	}
	/**
	 * 得到资源文件
	 * @author 朱俊
	 */
	public static String getResource(Context ctx, String key,String resourceStr) {
		return ResourceBase.getString(resourceStr, key, ctx.getLocale());
	}
	
}
