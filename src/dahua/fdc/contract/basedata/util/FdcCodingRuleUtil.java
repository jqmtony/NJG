/**
 * @(#)FdcCodingRuleUtil.java 1.0 2013-10-11
 * @author 王正
 * @email SkyIter@live.com
 * Copyright 2013 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.base.codingrule.CodingRuleEntryCollection;
import com.kingdee.eas.base.codingrule.CodingRuleEntryFactory;
import com.kingdee.eas.base.codingrule.CodingRuleInfo;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleEntry;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;

/**
 * 房地产 编码规则工具类
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @createDate 2013-10-11
 * @version 1.0, 2013-10-11
 * @see
 * @since JDK1.4
 */
public class FdcCodingRuleUtil {

	public static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.basedata.util.FdcCodingRuleUtil");

	/**
	 * 描述：编码规则的facade类实例
	 * 
	 * @param ctx
	 *            应用上下文;可空
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author skyiter_wang
	 * @createDate 2013-10-11
	 */
	public static ICodingRuleManager getCodingRuleManagerInstance(Context ctx) throws BOSException, EASBizException {
		ICodingRuleManager iCodingRuleManager = null;

		if (null == ctx) {
			iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		} else {
			iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		}

		return iCodingRuleManager;
	}

	/**
	 * 描述：编码规则分录实例
	 * 
	 * @param ctx
	 *            应用上下文;可空
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author skyiter_wang
	 * @createDate 2013-10-11
	 */
	public static ICodingRuleEntry getCodingRuleEntryInstance(Context ctx) throws BOSException, EASBizException {
		ICodingRuleEntry iCodingRuleEntry = null;

		if (null == ctx) {
			iCodingRuleEntry = CodingRuleEntryFactory.getRemoteInstance();
		} else {
			iCodingRuleEntry = CodingRuleEntryFactory.getLocalInstance(ctx);
		}

		return iCodingRuleEntry;
	}

	/**
	 * 描述：是否存在编码规则
	 * 
	 * @param iCodingRuleManager
	 *            编码规则的facade类;非空
	 * @param bizObject
	 *            业务对象;非空
	 * @param orgId
	 *            组织ID;非空
	 * @param bindingProperty
	 *            绑定属性;可空
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author skyiter_wang
	 * @createDate 2013-10-10
	 */
	public static boolean isExist(ICodingRuleManager iCodingRuleManager, IObjectValue bizObject, String orgId,
			String bindingProperty) throws BOSException, EASBizException {
		boolean isExistCodingRule = false;

		boolean isExistBindingProperty = isExistBindingProperty(bizObject, bindingProperty);
		if (!isExistBindingProperty) {
			isExistCodingRule = iCodingRuleManager.isExist(bizObject, orgId);
		} else {
			isExistCodingRule = iCodingRuleManager.isExist(bizObject, orgId, bindingProperty);
		}

		return isExistCodingRule;
	}

	/**
	 * 描述：是否存在编码规则
	 * 
	 * @param ctx
	 *            应用上下文;可空
	 * @param bizObject
	 *            业务对象;非空
	 * @param orgId
	 *            组织ID;非空
	 * @param bindingProperty
	 *            绑定属性;可空
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author skyiter_wang
	 * @createDate 2013-10-10
	 */
	public static boolean isExist(Context ctx, IObjectValue bizObject, String orgId, String bindingProperty)
			throws BOSException, EASBizException {
		ICodingRuleManager iCodingRuleManager = getCodingRuleManagerInstance(ctx);

		return isExist(iCodingRuleManager, bizObject, orgId, bindingProperty);
	}

	/**
	 * 描述： 是否启用断号支持功能
	 * 
	 * @param iCodingRuleManager
	 *            编码规则的facade类;非空
	 * @param bizObject
	 *            业务对象;非空
	 * @param orgId
	 *            组织ID;非空
	 * @param bindingProperty
	 *            绑定属性;可空
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author skyiter_wang
	 * @createDate 2013-10-11
	 */
	public static boolean isUseIntermitNumber(ICodingRuleManager iCodingRuleManager, IObjectValue bizObject,
			String orgId, String bindingProperty) throws BOSException, EASBizException {
		boolean isUseIntermitNumber = false;

		boolean isExistBindingProperty = isExistBindingProperty(bizObject, bindingProperty);
		if (!isExistBindingProperty) {
			isUseIntermitNumber = iCodingRuleManager.isUseIntermitNumber(bizObject, orgId);
		} else {
			isUseIntermitNumber = iCodingRuleManager.isUseIntermitNumber(bizObject, orgId, bindingProperty);
		}

		return isUseIntermitNumber;
	}

	/**
	 * 描述： 是否启用断号支持功能
	 * 
	 * @param ctx
	 *            应用上下文;可空
	 * @param bizObject
	 *            业务对象;非空
	 * @param orgId
	 *            组织ID;非空
	 * @param bindingProperty
	 *            绑定属性;可空
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author skyiter_wang
	 * @createDate 2013-10-11
	 */
	public static boolean isUseIntermitNumber(Context ctx, IObjectValue bizObject, String orgId, String bindingProperty)
			throws BOSException, EASBizException {
		ICodingRuleManager iCodingRuleManager = getCodingRuleManagerInstance(ctx);

		return isUseIntermitNumber(iCodingRuleManager, bizObject, orgId, bindingProperty);
	}

	/**
	 * 描述：取得编码规则
	 * 
	 * @param iCodingRuleManager
	 *            编码规则的facade类;非空
	 * @param bizObject
	 *            业务对象;非空
	 * @param orgId
	 *            组织ID;非空
	 * @param bindingProperty
	 *            绑定属性;可空
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author skyiter_wang
	 * @createDate 2013-10-11
	 */
	public static CodingRuleInfo getCodingRule(ICodingRuleManager iCodingRuleManager, IObjectValue bizObject,
			String orgId, String bindingProperty) throws BOSException, EASBizException {
		CodingRuleInfo codingRuleInfo = null;

		boolean isExistBindingProperty = isExistBindingProperty(bizObject, bindingProperty);
		if (!isExistBindingProperty) {
			codingRuleInfo = iCodingRuleManager.getCodingRule(bizObject, orgId);
		} else {
			codingRuleInfo = iCodingRuleManager.getCodingRule(bizObject, orgId, bindingProperty);
		}

		return codingRuleInfo;
	}

	/**
	 * 描述：取得编码规则
	 * 
	 * @param ctx
	 *            应用上下文;可空
	 * @param bizObject
	 *            业务对象;非空
	 * @param orgId
	 *            组织ID;非空
	 * @param bindingProperty
	 *            绑定属性;可空
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author skyiter_wang
	 * @createDate 2013-10-11
	 */
	public static CodingRuleInfo getCodingRule(Context ctx, IObjectValue bizObject, String orgId, String bindingProperty)
			throws BOSException, EASBizException {
		ICodingRuleManager iCodingRuleManager = getCodingRuleManagerInstance(ctx);

		return getCodingRule(iCodingRuleManager, bizObject, orgId, bindingProperty);
	}

	/**
	 * 描述：取得编码规则分录集合
	 * 
	 * @param ctx
	 *            应用上下文;可空
	 * @param bizObject
	 *            业务对象;非空
	 * @param orgId
	 *            组织ID;非空
	 * @param bindingProperty
	 *            绑定属性;可空
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author skyiter_wang
	 * @createDate 2013-10-11
	 */
	public static CodingRuleEntryCollection getCodingRuleEntryCollection(Context ctx, IObjectValue bizObject,
			String orgId, String bindingProperty) throws BOSException, EASBizException {
		CodingRuleEntryCollection codingRuleEntryCollection = new CodingRuleEntryCollection();

		ICodingRuleManager iCodingRuleManager = getCodingRuleManagerInstance(ctx);
		CodingRuleInfo codingRuleInfo = getCodingRule(iCodingRuleManager, bizObject, orgId, bindingProperty);
		if (null != codingRuleInfo) {
			ICodingRuleEntry iCodingRuleEntry = getCodingRuleEntryInstance(ctx);

			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.appendFilterItem("codingRule.id", codingRuleInfo.getId().toString());
			// 编码属性 :属性
			filter.appendFilterItem("codeAttribute", "attribute");
			view.setFilter(filter);
			codingRuleEntryCollection = iCodingRuleEntry.getCodingRuleEntryCollection(view);
		}

		return codingRuleEntryCollection;
	}

	/**
	 * 描述：回收编码
	 * 
	 * @param iCodingRuleManager
	 *            编码规则的facade类;非空
	 * @param bizObject
	 *            业务对象;非空
	 * @param orgId
	 *            组织ID;非空
	 * @param bindingProperty
	 *            绑定属性;可空
	 * @param custom
	 * @param number
	 *            编码;非空
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author skyiter_wang
	 * @createDate 2013-10-11
	 */
	public static boolean recycleNumber(ICodingRuleManager iCodingRuleManager, IObjectValue bizObject, String orgId,
			String bindingProperty, String custom, String number) throws BOSException, EASBizException {
		boolean flag = false;

		logger.info("===============================================================================");
		logger.info("FdcCodingRuleUtil.recycleNumber(),start");
		logger.info("===============================================================================");

		if (FdcStringUtil.isBlank(bindingProperty)) {
			flag = iCodingRuleManager.recycleNumber(bizObject, orgId, number);
		} else {
			flag = iCodingRuleManager.recycleNumber(bizObject, orgId, bindingProperty, "", number);
		}

		logger.info("===============================================================================");
		logger.info("bizObject:" + bizObject);
		logger.info("orgId:" + orgId);
		logger.info("bindingProperty:" + bindingProperty);
		logger.info("custom:" + custom);
		logger.info("number:" + number);
		logger.info("iCodingRuleManager.recycleNumber(bizObject, orgId, number), flag:" + flag);
		logger.info("===============================================================================");

		logger.info("===============================================================================");
		logger.info("FdcCodingRuleUtil.recycleNumber(),end");
		logger.info("===============================================================================");

		return flag;
	}

	/**
	 * 描述：回收编码
	 * 
	 * @param ctx
	 *            应用上下文;可空
	 * @param bizObject
	 *            业务对象;非空
	 * @param orgId
	 *            组织ID;非空
	 * @param bindingProperty
	 *            绑定属性;可空
	 * @param custom
	 *            ;可空
	 * @param number
	 *            编码;非空
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author skyiter_wang
	 * @createDate 2013-10-11
	 */
	public static boolean recycleNumber(Context ctx, IObjectValue bizObject, String orgId, String bindingProperty,
			String custom, String number) throws BOSException, EASBizException {
		ICodingRuleManager iCodingRuleManager = getCodingRuleManagerInstance(ctx);

		return recycleNumber(iCodingRuleManager, bizObject, orgId, bindingProperty, custom, number);
	}

	/**
	 * 描述：是否回收编码
	 * 
	 * @param iCodingRuleManager
	 *            编码规则的facade类;非空
	 * @param bizObject
	 *            业务对象;非空
	 * @param orgId
	 *            组织ID;非空
	 * @param bindingProperty
	 *            绑定属性;可空
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author skyiter_wang
	 * @createDate 2013-10-11
	 */
	public static boolean isRecycleNumber(ICodingRuleManager iCodingRuleManager, IObjectValue bizObject, String orgId,
			String bindingProperty) throws BOSException, EASBizException {
		boolean flag = false;

		logger.info("===============================================================================");
		logger.info("FdcCodingRuleUtil.isRecycleNumber(),start");
		logger.info("===============================================================================");

		// 是否存在绑定属性
		boolean isExistBindingProperty = FdcCodingRuleUtil.isExistBindingProperty(bizObject, bindingProperty);
		if (FdcStringUtil.isNotBlank(bindingProperty) && !isExistBindingProperty) {
			logger.info("bindingProperty:" + bindingProperty);
			logger.info("FdcCodingRuleUtil.isExistBindingProperty:" + isExistBindingProperty);
			logger.info("绑定属性不存在实体上");
		}

		boolean isExistCodingRule = false;
		boolean isUseIntermitNumber = false;

		// 是否存在编码规则
		isExistCodingRule = FdcCodingRuleUtil.isExist(iCodingRuleManager, bizObject, orgId, bindingProperty);
		if (isExistCodingRule) {
			// 是否启用断号支持功能
			isUseIntermitNumber = FdcCodingRuleUtil.isUseIntermitNumber(iCodingRuleManager, bizObject, orgId,
					bindingProperty);
		}

		flag = isExistCodingRule && isUseIntermitNumber;

		logger.info("bindingProperty:" + bindingProperty);
		logger.info("FdcCodingRuleUtil.isExistBindingProperty:" + isExistBindingProperty);
		logger.info("iCodingRuleManager.isExist(info, orgId, bindingProperty)，isExistCodingRule:" + isExistCodingRule);
		logger.info("iCodingRuleManager.isUseIntermitNumber(info, orgId, bindingProperty)，isUseIntermitNumber:"
				+ isUseIntermitNumber);
		logger.info("isExistBindingProperty && isExistCodingRule && isUseIntermitNumber，flag:" + flag);

		logger.info("===============================================================================");
		logger.info("FdcCodingRuleUtil.isRecycleNumber(),end");
		logger.info("===============================================================================");

		return flag;
	}

	/**
	 * 描述：是否回收编码
	 * 
	 * @param ctx
	 *            应用上下文;可空
	 * @param bizObject
	 *            业务对象;非空
	 * @param orgId
	 *            组织ID;非空
	 * @param bindingProperty
	 *            绑定属性;可空
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author skyiter_wang
	 * @createDate 2013-10-11
	 */
	public static boolean isRecycleNumber(Context ctx, IObjectValue bizObject, String orgId, String bindingProperty)
			throws BOSException, EASBizException {
		ICodingRuleManager iCodingRuleManager = getCodingRuleManagerInstance(ctx);

		return isRecycleNumber(iCodingRuleManager, bizObject, orgId, bindingProperty);
	}

	/**
	 * 描述：是否存在绑定属性
	 * 
	 * @return
	 * @author skyiter_wang
	 * @throws EASBizException
	 * @createDate 2013-10-16
	 */
	public static boolean isExistBindingProperty(IObjectValue bizObject, String bindingProperty) throws EASBizException {
		// 是否存在绑定属性
		boolean isExistBindingProperty = FdcStringUtil.isNotBlank(bindingProperty);

		Object bindingPropertyValue = null;
		String className = bizObject.getClass().toString();
		if (isExistBindingProperty) {
			// 绑定属性是否有值
			bindingPropertyValue = FdcObjectValueUtil.get(bizObject, bindingProperty);

			isExistBindingProperty = (null != bindingPropertyValue);
		}

		logger.info("===============================================================================");
		logger.info("FdcCodingRuleUtil.isExistBindingProperty(),start");

		logger.info("className:" + className);
		logger.info("info:" + bizObject);
		logger.info("bindingProperty:" + bindingProperty);
		logger.info("bindingPropertyValue:" + bindingPropertyValue);
		logger.info("isExistBindingProperty:" + isExistBindingProperty);

		logger.info("FdcCodingRuleUtil.isExistBindingProperty(),end");
		logger.info("===============================================================================");

		return isExistBindingProperty;
	}

	/**
	 * 
	 * 描述：获取编码规则是否支持修改
	 * <p>
	 * 有些大单据对象有很多分录（郡原的付款拆分，最多曾见到过18865行分录），如果整个单据对象序列化会引发OutOfMemory异常。
	 * 由于单据的分录对编码规则不影响，不需要序列化传到服务端，故序列化单据对象到服务端之前先拿掉分录，然后将分录还原
	 * 
	 * @param info
	 *            当前单据的Info
	 * @param orgUnitId
	 *            当前组织id
	 * @param bindingProperty
	 *            绑定属性
	 * @return 支持修改 － true， 不支持修改 － false，如果没有定义编码规则则返回true
	 * @throws EASBizException
	 * @throws BOSException
	 * @author skyiter_wang
	 * @createDate 2013-12-20
	 */
	public static boolean isAllowModifyNumber(Context ctx, IObjectValue info, String orgUnitId, String bindingProperty)
			throws EASBizException, BOSException {
		boolean result = false;

		ICodingRuleManager iCodingRuleManager = getCodingRuleManagerInstance(ctx);
		result = isAllowModifyNumber(iCodingRuleManager, info, orgUnitId, bindingProperty);

		return result;
	}

	/**
	 * 
	 * 描述：获取编码规则是否支持修改
	 * <p>
	 * 有些大单据对象有很多分录（郡原的付款拆分，最多曾见到过18865行分录），如果整个单据对象序列化会引发OutOfMemory异常。
	 * 由于单据的分录对编码规则不影响，不需要序列化传到服务端，故序列化单据对象到服务端之前先拿掉分录，然后将分录还原
	 * 
	 * @param iCodingRuleManager
	 *            编码规则Manager
	 * @param orgUnitId
	 *            当前组织id
	 * @param orgUnitId
	 *            当前组织id
	 * @param bindingProperty
	 *            绑定属性
	 * @return 支持修改 － true， 不支持修改 － false，如果没有定义编码规则则返回true
	 * @throws EASBizException
	 * @throws BOSException
	 * @author skyiter_wang
	 * @createDate 2013-12-20
	 */
	public static boolean isAllowModifyNumber(ICodingRuleManager iCodingRuleManager, IObjectValue info,
			String orgUnitId, String bindingProperty) throws EASBizException, BOSException {

		// 1. 先把所有分录取出来，判断编码规则不需要分录
		Map collectionMap = new HashMap(); // 分录key-value对象
		for (Enumeration e = info.keys(); e.hasMoreElements();) {
			Object key = e.nextElement();
			Object value = info.get((String) key);
			if (value instanceof IObjectCollection) {
				collectionMap.put(key, value);
				info.remove((String) key);// 先拿掉分录
			}
		}

		try {
			if (FdcStringUtil.isNotBlank(bindingProperty)
					&& iCodingRuleManager.isExist(info, orgUnitId, bindingProperty)) {
				return iCodingRuleManager.isModifiable(info, orgUnitId, bindingProperty);
			} else if (iCodingRuleManager.isExist(info, orgUnitId)) {
				return iCodingRuleManager.isModifiable(info, orgUnitId);
			} else {
				return true;
			}
		} finally {
			// 2. 再把所有分录还原
			for (Iterator it = collectionMap.keySet().iterator(); it.hasNext();) {
				String key = (String) it.next();
				info.put(key, collectionMap.get(key));// 再补上分录
			}
		}
	}

	/**
	 * 描述：取得编码
	 * 
	 * @param ctx
	 *            应用上下文;可空
	 * @param info
	 *            业务对象;非空
	 * @param orgId
	 *            组织id;非空
	 * @param bindingProperty
	 *            绑定属性;可空
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 * @author skyiter_wang
	 * @createDate 2013-12-20
	 */
	public static String getNumber(Context ctx, FDCBillInfo info, String orgId, String bindingProperty)
			throws EASBizException, BOSException {
		String number = null;

		ICodingRuleManager iCodingRuleManager = getCodingRuleManagerInstance(ctx);
		number = getNumber(iCodingRuleManager, info, orgId, bindingProperty);

		return number;
	}

	/**
	 * 描述：取得编码
	 * 
	 * @param iCodingRuleManager
	 *            编码规则Manager;非空
	 * @param info
	 *            业务对象;非空
	 * @param orgId
	 *            组织id;非空
	 * @param bindingProperty
	 *            绑定属性;可空
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 * @author skyiter_wang
	 * @createDate 2013-12-20
	 */
	public static String getNumber(ICodingRuleManager iCodingRuleManager, FDCBillInfo info, String orgId,
			String bindingProperty) throws EASBizException, BOSException {
		String number = null;

		if (FdcStringUtil.isNotBlank(bindingProperty)) {
			number = iCodingRuleManager.getNumber(info, orgId, bindingProperty, "");
		} else {
			number = iCodingRuleManager.getNumber(info, orgId);
		}

		return number;
	}

}
