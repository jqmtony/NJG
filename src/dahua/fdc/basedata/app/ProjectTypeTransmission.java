package com.kingdee.eas.fdc.basedata.app;

import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basedata.ProjectTypeFactory;
import com.kingdee.eas.fdc.basedata.ProjectTypeInfo;
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
 * 描述：		工程系列转换类——易思博
 *		
 * @author		xiaochong
 * @version		EAS7.0		
 * @createDate	2011-6-9	 
 * @see
 */
public class ProjectTypeTransmission extends AbstractDataTransmission{

	private static final String resource = "com.kingdee.eas.fdc.basedata.FDCBaseDataResource";
	private static String resourceCommon = "com.kingdee.eas.common.EASCommonResource";


	protected ICoreBase getController(Context ctx)
			throws TaskExternalException {
		try {
			return ProjectTypeFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e);
		}
	}

	/**转换方法*/
	public CoreBaseInfo transmit(Hashtable hsdata, Context ctx)
			throws TaskExternalException {
		return innerTransform(hsdata, ctx);
	}

	/**内调转换处理方法*/
	protected CoreBaseInfo innerTransform(Hashtable hsdata, Context ctx) throws TaskExternalException {
		
		String number = getString(hsdata, "FNumber");						// 编码
		String name = getString(hsdata, "FName_l2");						// 名称
		String description = getString(hsdata, "FDescription_l2");			// 描述
		String isEnabled = getString(hsdata, "FIsEnabled");					// 启用
		
		
		// 必录校验
		isFitLength(ctx,"ProjectTypeNumberIsNull", number);
		isFitLength(ctx,"ProjectTypeNameIsNull", name);
		// 长度校验
		isFitLength(ctx, number, 80, "ProjectTypeNumberIsOver80");
		isFitLength(ctx, name, 80, "ProjectTypeNameIsOver80");
		isFitLength(ctx,description, 200, "ProjectTypeDescriptionIsOver200");
		
		boolean bIsEnabled =getResource(ctx, "no",resourceCommon).equalsIgnoreCase(isEnabled) ? false : true;
		
		
		/// 校验后设置
		ProjectTypeInfo info=new ProjectTypeInfo();
		info.setNumber(number);
		info.setName(name);
		info.setDescription(description);
		info.setIsEnabled(bIsEnabled);
		
		return info;
	}
	
	/** 获取字符串参数 */
	private String getString(Hashtable hsdata, Object key) {
		return (String) ((DataToken) hsdata.get(key)).data;
	}
	
	/** 判断对应字符串长度是否符合指定长度 */
	private void isFitLength(Context ctx, String value, int length, String alertMsgKey) 
			throws TaskExternalException {
		if (value != null && value.trim().length() != 0) {
			if (value.trim().length() > length) isThrow(getResource(ctx, alertMsgKey,resource));
		}
	}

	/** 判断字符串是否为空 */
	private void isFitLength(Context ctx, String alertMsgKey, String value)
			throws TaskExternalException {
		if (value == null || value.trim().length() == 0) {
			isThrow(getResource(ctx, alertMsgKey,resource));
		}
	}
	
	/** 向上抛出指定Msg的异常 */
	private void isThrow(String alertMsg) throws TaskExternalException {
		isThrow(null, alertMsg);
	}
	/** 向上抛出指定Msg的异常 */
	private void isThrow(String value, String alertMsg)	throws TaskExternalException {
		String msg = (value == null) ? ("" + alertMsg) :  alertMsg;
		throw new TaskExternalException(msg);
	}

	
	/**
	 * 得到资源文件
	 * @author 郑杰元
	 */
	public static String getResource(Context ctx, String key,String resourceStr) {
		return ResourceBase.getString(resourceStr, key, ctx.getLocale());
	}
}
