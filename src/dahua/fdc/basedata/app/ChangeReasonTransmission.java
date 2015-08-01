package com.kingdee.eas.fdc.basedata.app;

import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.basedata.ChangeReasonFactory;
import com.kingdee.eas.fdc.basedata.ChangeReasonInfo;
import com.kingdee.eas.fdc.basedata.ChangeTypeCollection;
import com.kingdee.eas.fdc.basedata.ChangeTypeFactory;
import com.kingdee.eas.fdc.basedata.ChangeTypeInfo;
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
 * 描述：		变更原因转换类——易思博
 *		
 * @author		xiaochong
 * @version		EAS7.0		
 * @createDate	2011-6-1	 
 * @see
 */
public class ChangeReasonTransmission extends AbstractDataTransmission {
	

	private static final String resource = "com.kingdee.eas.fdc.basedata.FDCBaseDataResource";
	private static String resourceCommon = "com.kingdee.eas.common.EASCommonResource";

	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try {
			return ChangeReasonFactory.getLocalInstance(ctx);
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
	protected CoreBaseInfo innerTransform(Hashtable hsdata, Context ctx)
			throws TaskExternalException {
		
		String number = getString(hsdata, "FNumber");							// 编码
		String name = getString(hsdata, "FName_l2");							// 名称
		String changeTypeNumber = getString(hsdata, "FChangeType_number");		// 变更类型
		String description = getString(hsdata, "FDescription_l2");				// 描述
		String isEnabled = getString(hsdata, "FIsEnabled");						// 启用

		
		// 必录校验
		isFitLength(ctx, "ChangeReasonNumberIsNull", number);
		isFitLength(ctx, "ChangeReasonNameIsNull", name);
		isFitLength(ctx,"ChangeTypeIsNull", changeTypeNumber);
		// 长度校验
		isFitLength(ctx, number, 80, "ChangeReasonNumberIsOver80");
		isFitLength(ctx, name, 80, "ChangeReasonNameIsOver80");
		isFitLength(ctx, description, 200, "ChangeReasonDescriptionIsOver200");

		boolean bIsEnabled = getResource(ctx, "yes",resourceCommon).equalsIgnoreCase(isEnabled) ? true : false;
		
		
		ChangeTypeInfo changeTypeInfo = null;
		try {
			ChangeTypeCollection changeTypeCol = ChangeTypeFactory.getLocalInstance(ctx).getChangeTypeCollection(
							getEntityViewInfoInstance("number", changeTypeNumber));
			
			if(changeTypeCol==null||changeTypeCol.size()==0){
				this.isThrow(getResource(ctx, "ChangeTypeIsNotExist",resource));
			} else {
				changeTypeInfo = changeTypeCol.get(0); 
			}
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		}
		
		
		ChangeReasonInfo info = new ChangeReasonInfo();
		info.setNumber(number);
		info.setName(name);
		info.setDescription(description);
		info.setChangeType(changeTypeInfo);
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
	private void isThrow(String value, String alertMsg)
			throws TaskExternalException {
		String msg = (value == null) ? ("" + alertMsg) : (value.trim() + " " + alertMsg);
		throw new TaskExternalException(msg);
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
