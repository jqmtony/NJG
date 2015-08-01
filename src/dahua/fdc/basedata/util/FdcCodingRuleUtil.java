/**
 * @(#)FdcCodingRuleUtil.java 1.0 2013-10-11
 * @author ����
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
 * ���ز� ������򹤾���
 * 
 * @author ����
 * @email SkyIter@live.com
 * @createDate 2013-10-11
 * @version 1.0, 2013-10-11
 * @see
 * @since JDK1.4
 */
public class FdcCodingRuleUtil {

	public static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.basedata.util.FdcCodingRuleUtil");

	/**
	 * ��������������facade��ʵ��
	 * 
	 * @param ctx
	 *            Ӧ��������;�ɿ�
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
	 * ��������������¼ʵ��
	 * 
	 * @param ctx
	 *            Ӧ��������;�ɿ�
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
	 * �������Ƿ���ڱ������
	 * 
	 * @param iCodingRuleManager
	 *            ��������facade��;�ǿ�
	 * @param bizObject
	 *            ҵ�����;�ǿ�
	 * @param orgId
	 *            ��֯ID;�ǿ�
	 * @param bindingProperty
	 *            ������;�ɿ�
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
	 * �������Ƿ���ڱ������
	 * 
	 * @param ctx
	 *            Ӧ��������;�ɿ�
	 * @param bizObject
	 *            ҵ�����;�ǿ�
	 * @param orgId
	 *            ��֯ID;�ǿ�
	 * @param bindingProperty
	 *            ������;�ɿ�
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
	 * ������ �Ƿ����öϺ�֧�ֹ���
	 * 
	 * @param iCodingRuleManager
	 *            ��������facade��;�ǿ�
	 * @param bizObject
	 *            ҵ�����;�ǿ�
	 * @param orgId
	 *            ��֯ID;�ǿ�
	 * @param bindingProperty
	 *            ������;�ɿ�
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
	 * ������ �Ƿ����öϺ�֧�ֹ���
	 * 
	 * @param ctx
	 *            Ӧ��������;�ɿ�
	 * @param bizObject
	 *            ҵ�����;�ǿ�
	 * @param orgId
	 *            ��֯ID;�ǿ�
	 * @param bindingProperty
	 *            ������;�ɿ�
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
	 * ������ȡ�ñ������
	 * 
	 * @param iCodingRuleManager
	 *            ��������facade��;�ǿ�
	 * @param bizObject
	 *            ҵ�����;�ǿ�
	 * @param orgId
	 *            ��֯ID;�ǿ�
	 * @param bindingProperty
	 *            ������;�ɿ�
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
	 * ������ȡ�ñ������
	 * 
	 * @param ctx
	 *            Ӧ��������;�ɿ�
	 * @param bizObject
	 *            ҵ�����;�ǿ�
	 * @param orgId
	 *            ��֯ID;�ǿ�
	 * @param bindingProperty
	 *            ������;�ɿ�
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
	 * ������ȡ�ñ�������¼����
	 * 
	 * @param ctx
	 *            Ӧ��������;�ɿ�
	 * @param bizObject
	 *            ҵ�����;�ǿ�
	 * @param orgId
	 *            ��֯ID;�ǿ�
	 * @param bindingProperty
	 *            ������;�ɿ�
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
			// �������� :����
			filter.appendFilterItem("codeAttribute", "attribute");
			view.setFilter(filter);
			codingRuleEntryCollection = iCodingRuleEntry.getCodingRuleEntryCollection(view);
		}

		return codingRuleEntryCollection;
	}

	/**
	 * ���������ձ���
	 * 
	 * @param iCodingRuleManager
	 *            ��������facade��;�ǿ�
	 * @param bizObject
	 *            ҵ�����;�ǿ�
	 * @param orgId
	 *            ��֯ID;�ǿ�
	 * @param bindingProperty
	 *            ������;�ɿ�
	 * @param custom
	 * @param number
	 *            ����;�ǿ�
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
	 * ���������ձ���
	 * 
	 * @param ctx
	 *            Ӧ��������;�ɿ�
	 * @param bizObject
	 *            ҵ�����;�ǿ�
	 * @param orgId
	 *            ��֯ID;�ǿ�
	 * @param bindingProperty
	 *            ������;�ɿ�
	 * @param custom
	 *            ;�ɿ�
	 * @param number
	 *            ����;�ǿ�
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
	 * �������Ƿ���ձ���
	 * 
	 * @param iCodingRuleManager
	 *            ��������facade��;�ǿ�
	 * @param bizObject
	 *            ҵ�����;�ǿ�
	 * @param orgId
	 *            ��֯ID;�ǿ�
	 * @param bindingProperty
	 *            ������;�ɿ�
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

		// �Ƿ���ڰ�����
		boolean isExistBindingProperty = FdcCodingRuleUtil.isExistBindingProperty(bizObject, bindingProperty);
		if (FdcStringUtil.isNotBlank(bindingProperty) && !isExistBindingProperty) {
			logger.info("bindingProperty:" + bindingProperty);
			logger.info("FdcCodingRuleUtil.isExistBindingProperty:" + isExistBindingProperty);
			logger.info("�����Բ�����ʵ����");
		}

		boolean isExistCodingRule = false;
		boolean isUseIntermitNumber = false;

		// �Ƿ���ڱ������
		isExistCodingRule = FdcCodingRuleUtil.isExist(iCodingRuleManager, bizObject, orgId, bindingProperty);
		if (isExistCodingRule) {
			// �Ƿ����öϺ�֧�ֹ���
			isUseIntermitNumber = FdcCodingRuleUtil.isUseIntermitNumber(iCodingRuleManager, bizObject, orgId,
					bindingProperty);
		}

		flag = isExistCodingRule && isUseIntermitNumber;

		logger.info("bindingProperty:" + bindingProperty);
		logger.info("FdcCodingRuleUtil.isExistBindingProperty:" + isExistBindingProperty);
		logger.info("iCodingRuleManager.isExist(info, orgId, bindingProperty)��isExistCodingRule:" + isExistCodingRule);
		logger.info("iCodingRuleManager.isUseIntermitNumber(info, orgId, bindingProperty)��isUseIntermitNumber:"
				+ isUseIntermitNumber);
		logger.info("isExistBindingProperty && isExistCodingRule && isUseIntermitNumber��flag:" + flag);

		logger.info("===============================================================================");
		logger.info("FdcCodingRuleUtil.isRecycleNumber(),end");
		logger.info("===============================================================================");

		return flag;
	}

	/**
	 * �������Ƿ���ձ���
	 * 
	 * @param ctx
	 *            Ӧ��������;�ɿ�
	 * @param bizObject
	 *            ҵ�����;�ǿ�
	 * @param orgId
	 *            ��֯ID;�ǿ�
	 * @param bindingProperty
	 *            ������;�ɿ�
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
	 * �������Ƿ���ڰ�����
	 * 
	 * @return
	 * @author skyiter_wang
	 * @throws EASBizException
	 * @createDate 2013-10-16
	 */
	public static boolean isExistBindingProperty(IObjectValue bizObject, String bindingProperty) throws EASBizException {
		// �Ƿ���ڰ�����
		boolean isExistBindingProperty = FdcStringUtil.isNotBlank(bindingProperty);

		Object bindingPropertyValue = null;
		String className = bizObject.getClass().toString();
		if (isExistBindingProperty) {
			// �������Ƿ���ֵ
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
	 * ��������ȡ��������Ƿ�֧���޸�
	 * <p>
	 * ��Щ�󵥾ݶ����кܶ��¼����ԭ�ĸ����֣������������18865�з�¼��������������ݶ������л�������OutOfMemory�쳣��
	 * ���ڵ��ݵķ�¼�Ա������Ӱ�죬����Ҫ���л���������ˣ������л����ݶ��󵽷����֮ǰ���õ���¼��Ȼ�󽫷�¼��ԭ
	 * 
	 * @param info
	 *            ��ǰ���ݵ�Info
	 * @param orgUnitId
	 *            ��ǰ��֯id
	 * @param bindingProperty
	 *            ������
	 * @return ֧���޸� �� true�� ��֧���޸� �� false�����û�ж����������򷵻�true
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
	 * ��������ȡ��������Ƿ�֧���޸�
	 * <p>
	 * ��Щ�󵥾ݶ����кܶ��¼����ԭ�ĸ����֣������������18865�з�¼��������������ݶ������л�������OutOfMemory�쳣��
	 * ���ڵ��ݵķ�¼�Ա������Ӱ�죬����Ҫ���л���������ˣ������л����ݶ��󵽷����֮ǰ���õ���¼��Ȼ�󽫷�¼��ԭ
	 * 
	 * @param iCodingRuleManager
	 *            �������Manager
	 * @param orgUnitId
	 *            ��ǰ��֯id
	 * @param orgUnitId
	 *            ��ǰ��֯id
	 * @param bindingProperty
	 *            ������
	 * @return ֧���޸� �� true�� ��֧���޸� �� false�����û�ж����������򷵻�true
	 * @throws EASBizException
	 * @throws BOSException
	 * @author skyiter_wang
	 * @createDate 2013-12-20
	 */
	public static boolean isAllowModifyNumber(ICodingRuleManager iCodingRuleManager, IObjectValue info,
			String orgUnitId, String bindingProperty) throws EASBizException, BOSException {

		// 1. �Ȱ����з�¼ȡ�������жϱ��������Ҫ��¼
		Map collectionMap = new HashMap(); // ��¼key-value����
		for (Enumeration e = info.keys(); e.hasMoreElements();) {
			Object key = e.nextElement();
			Object value = info.get((String) key);
			if (value instanceof IObjectCollection) {
				collectionMap.put(key, value);
				info.remove((String) key);// ���õ���¼
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
			// 2. �ٰ����з�¼��ԭ
			for (Iterator it = collectionMap.keySet().iterator(); it.hasNext();) {
				String key = (String) it.next();
				info.put(key, collectionMap.get(key));// �ٲ��Ϸ�¼
			}
		}
	}

	/**
	 * ������ȡ�ñ���
	 * 
	 * @param ctx
	 *            Ӧ��������;�ɿ�
	 * @param info
	 *            ҵ�����;�ǿ�
	 * @param orgId
	 *            ��֯id;�ǿ�
	 * @param bindingProperty
	 *            ������;�ɿ�
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
	 * ������ȡ�ñ���
	 * 
	 * @param iCodingRuleManager
	 *            �������Manager;�ǿ�
	 * @param info
	 *            ҵ�����;�ǿ�
	 * @param orgId
	 *            ��֯id;�ǿ�
	 * @param bindingProperty
	 *            ������;�ɿ�
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
