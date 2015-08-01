/**
 * @(#)FdcORMappingDAOUtil.java 1.0 2014-5-22
 * @author ����
 * @email SkyIter@live.com
 * Copyright 2014 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.util;

import java.sql.Connection;
import java.util.Iterator;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ICommonBOSType;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.IORMappingDAO;
import com.kingdee.bos.dao.ormapping.ORMappingDAO;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.PropertyInfo;
import com.kingdee.bos.service.ServiceStateManager;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.EffectedStatusEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.IFWStatus;
import com.kingdee.util.db.SQLUtils;

/**
 * ���������ز�ORMapping������
 * 
 * @author ����
 * @email SkyIter@live.com
 * @createDate 2014-5-22
 * @version 1.0, 2014-5-22
 * @see
 * @since JDK1.4
 */
public class FdcORMappingDAOUtil {

	/**
	 * ȡ��ORM���ݷ��ʶ���
	 * 
	 * @param ctx
	 *            ������
	 * @param cn
	 *            ���ݿ�����
	 * @param coreBase
	 *            ʵ��
	 * @return
	 * @throws BOSException
	 */
	public static IORMappingDAO getDAO(Context ctx, Connection cn, ICoreBase coreBase) throws BOSException {
		BOSObjectType type = coreBase.getType();

		if (coreBase instanceof ICommonBOSType) {
			return ORMappingDAO.getInstance(type, ctx, cn, ((ICommonBOSType) coreBase).getPK());
		} else {
			return ORMappingDAO.getInstance(type, ctx, cn);
		}
	}

	/**
	 * ��������
	 * 
	 * <p>
	 * Ϊ������������ӵ����ݿ⼶�������ӿڣ����ص���������������Ҫôȫ���ɹ���Ҫôȫ��ʧ��(��ĿǰORMappingDAO�ṩ�������ӿڲ�û��ʵ��)
	 * 
	 * <p>
	 * ĿǰORMappingDAO�ṩ��������������Ϊ�������һ�����ݲ����ɹ������������������Բ��ֳɹ�����ʧ�ܣ������һ�����ݲ������ɹ��򲻻��ٶԺ��������ִ����������
	 * 
	 * <p>
	 * �����Ҫ�����ֳɹ�����ʧ�ܵ�����������ο�{@link #addnew(Context ctx, AbstractObjectCollection colls)}����
	 * 
	 * 
	 * @param ctx
	 *            ������
	 * @param cn
	 *            ���ݿ�����
	 * @param coreBase
	 *            ʵ��
	 * @param colls
	 *            ���󼯺�
	 * @return ������������PK����
	 * @throws BOSException
	 * @throws EASBizException
	 * @see com.kingdee.eas.framework.app.CoreBaseControllerBean#addnewBatchData(Context,
	 *      com.kingdee.eas.framework.CoreBaseCollection)
	 */
	public static IObjectPK[] addnewBatchData(Context ctx, Connection cn, ICoreBase coreBase, AbstractObjectCollection colls) throws BOSException, EASBizException {
		// ���������PK����
		IObjectPK[] objectPKArray = null;
		try {
			FdcMethodUtil.invokeNoParameterMethod(coreBase, "setRollbackOnly", true);

			// ��ȡORMappingDAO���ݿ���ʽӿ�
			IORMappingDAO dao = getDAO(ctx, cn, coreBase);
			Iterator objectIter = colls.iterator();
			while (objectIter.hasNext()) {
				IObjectValue value = (IObjectValue) objectIter.next();
				// ׼��������������
				dao.addNewBatch(value);
			}
			// �������� ����
			objectPKArray = dao.executeBatchWithReturn();
		} finally {
			SQLUtils.cleanup(cn);
		}

		return objectPKArray;
	}

	/**
	 * �����޸�
	 * 
	 * <p>
	 * Ϊ������������ӵ����ݿ⼶�������ӿڣ����ص���������������Ҫôȫ���ɹ���Ҫôȫ��ʧ��(��ĿǰORMappingDAO�ṩ�������ӿڲ�û��ʵ��)
	 * 
	 * <p>
	 * ĿǰORMappingDAO�ṩ��������������Ϊ�������һ�����ݲ����ɹ������������������Բ��ֳɹ�����ʧ�ܣ������һ�����ݲ������ɹ��򲻻��ٶԺ��������ִ����������
	 * 
	 * <p>
	 * �����Ҫ�����ֳɹ�����ʧ�ܵ�����������ο�{@link #update(Context ctx, AbstractObjectCollection colls)}����
	 * 
	 * @param ctx
	 *            ������
	 * @param cn
	 *            ���ݿ�����
	 * @param coreBase
	 *            ʵ��
	 * @param colls
	 *            ���󼯺�
	 * @return ������������PK����
	 * @throws BOSException
	 * @throws EASBizException
	 * @see com.kingdee.eas.framework.app.CoreBaseControllerBean#updateBatchData(Context,
	 *      com.kingdee.eas.framework.CoreBaseCollection)
	 */
	public static IObjectPK[] updateBatchData(Context ctx, Connection cn, ICoreBase coreBase, AbstractObjectCollection colls) throws BOSException, EASBizException {
		// ���������PK����
		IObjectPK[] objectPKArray = null;
		try {
			FdcMethodUtil.invokeNoParameterMethod(coreBase, "setRollbackOnly", true);

			// ��ȡORMappingDAO���ݿ���ʽӿ�
			IORMappingDAO dao = getDAO(ctx, cn, coreBase);
			Iterator objectIter = colls.iterator();
			while (objectIter.hasNext()) {
				CoreBaseInfo value = (CoreBaseInfo) objectIter.next();
				IObjectPK pk = new ObjectUuidPK(value.getId());
				if (value.getId() != null && coreBase.exists(pk)) {
					// ׼�������޸�����
					dao.updateBatch(pk, value);
				}
			}
			// ���������޸�
			objectPKArray = dao.executeBatchWithReturn();
		} finally {
			SQLUtils.cleanup(cn);
		}

		return objectPKArray;
	}

	/**
	 * �����޸�(�Ѿ����ڣ�������exists�жϣ����������)
	 * 
	 * <p>
	 * Ϊ������������ӵ����ݿ⼶�������ӿڣ����ص���������������Ҫôȫ���ɹ���Ҫôȫ��ʧ��(��ĿǰORMappingDAO�ṩ�������ӿڲ�û��ʵ��)
	 * 
	 * <p>
	 * ĿǰORMappingDAO�ṩ��������������Ϊ�������һ�����ݲ����ɹ������������������Բ��ֳɹ�����ʧ�ܣ������һ�����ݲ������ɹ��򲻻��ٶԺ��������ִ����������
	 * 
	 * <p>
	 * �����Ҫ�����ֳɹ�����ʧ�ܵ�����������ο�{@link #update(Context ctx, AbstractObjectCollection colls)}����
	 * 
	 * @param ctx
	 *            ������
	 * @param cn
	 *            ���ݿ�����
	 * @param coreBase
	 *            ʵ��
	 * @param colls
	 *            ���󼯺�
	 * @return ������������PK����
	 * @throws BOSException
	 * @throws EASBizException
	 * @see com.kingdee.eas.framework.app.CoreBaseControllerBean#updateBatchData(Context,
	 *      com.kingdee.eas.framework.CoreBaseCollection)
	 */
	public static IObjectPK[] updateBatchDataWithExists(Context ctx, Connection cn, ICoreBase coreBase, AbstractObjectCollection colls) throws BOSException, EASBizException {
		// ���������PK����
		IObjectPK[] objectPKArray = null;
		try {
			FdcMethodUtil.invokeNoParameterMethod(coreBase, "setRollbackOnly", true);

			// ��ȡORMappingDAO���ݿ���ʽӿ�
			IORMappingDAO dao = getDAO(ctx, cn, coreBase);
			Iterator objectIter = colls.iterator();
			while (objectIter.hasNext()) {
				CoreBaseInfo value = (CoreBaseInfo) objectIter.next();
				IObjectPK pk = new ObjectUuidPK(value.getId());

				// ׼�������޸�����
				dao.updateBatch(pk, value);
			}
			// ���������޸�
			objectPKArray = dao.executeBatchWithReturn();
		} finally {
			SQLUtils.cleanup(cn);
		}

		return objectPKArray;
	}

	/**
	 * ��������
	 * 
	 * <p>
	 * Ϊ������������ӵ����ݿ⼶�������ӿڣ����ص���������������Ҫôȫ���ɹ���Ҫôȫ��ʧ��(��ĿǰORMappingDAO�ṩ�������ӿڲ�û��ʵ��)
	 * 
	 * <p>
	 * ĿǰORMappingDAO�ṩ��������������Ϊ�������һ�����ݲ����ɹ������������������Բ��ֳɹ�����ʧ�ܣ������һ�����ݲ������ɹ��򲻻��ٶԺ��������ִ����������
	 * 
	 * <p>
	 * �����Ҫ�����ֳɹ�����ʧ�ܵ�����������ο�{@link #save(Context ctx, AbstractObjectCollection colls)}����
	 * 
	 * @param ctx
	 *            ������
	 * @param cn
	 *            ���ݿ�����
	 * @param coreBase
	 *            ʵ��
	 * @param colls
	 *            ���󼯺�
	 * @return ������������PK����
	 * @throws BOSException
	 * @throws EASBizException
	 * @see com.kingdee.eas.framework.app.CoreBaseControllerBean#saveBatchData(Context,
	 *      com.kingdee.eas.framework.CoreBaseCollection)
	 */
	public static IObjectPK[] saveBatchData(Context ctx, Connection cn, ICoreBase coreBase, AbstractObjectCollection colls) throws BOSException, EASBizException {
		// ���������PK����
		IObjectPK[] objectPKArray = null;
		try {
			FdcMethodUtil.invokeNoParameterMethod(coreBase, "setRollbackOnly", true);

			// ��ȡORMappingDAO���ݿ���ʽӿ�
			IORMappingDAO dao = getDAO(ctx, cn, coreBase);
			Iterator objectIter = colls.iterator();
			while (objectIter.hasNext()) {
				CoreBaseInfo value = (CoreBaseInfo) objectIter.next();
				IObjectPK pk = new ObjectUuidPK(value.getId());
				// ���ö������Ч״̬
				if (isExistPropertyName(ctx, value, IFWStatus.effectedStatus)) {
					value.setInt(IFWStatus.effectedStatus, EffectedStatusEnum.TEMPSTORE_VALUE);
				}
				if (value.getId() != null && coreBase.exists(pk)) {
					// ׼�������޸�����
					dao.updateBatch(pk, value);
				} else {
					// ׼��������������
					dao.addNewBatch(value);
				}
			}
			// ������������
			objectPKArray = dao.executeBatchWithReturn();
			// ʹ��Ȩ�޷������
			ServiceStateManager.getInstance().enableNextCallServices();
		} finally {
			SQLUtils.cleanup(cn);
		}
		return objectPKArray;
	}

	/**
	 * �����ύ
	 * 
	 * <p>
	 * Ϊ������������ӵ����ݿ⼶�������ӿڣ����ص���������������Ҫôȫ���ɹ���Ҫôȫ��ʧ��(��ĿǰORMappingDAO�ṩ�������ӿڲ�û��ʵ��)
	 * 
	 * <p>
	 * ĿǰORMappingDAO�ṩ��������������Ϊ�������һ�����ݲ����ɹ������������������Բ��ֳɹ�����ʧ�ܣ������һ�����ݲ������ɹ��򲻻��ٶԺ��������ִ����������
	 * 
	 * <p>
	 * �����Ҫ�����ֳɹ�����ʧ�ܵ�����������ο�{@link #submit(Context ctx, AbstractObjectCollection colls)}����
	 * 
	 * @param ctx
	 *            ������
	 * @param cn
	 *            ���ݿ�����
	 * @param coreBase
	 *            ʵ��
	 * @param colls
	 *            ���󼯺�
	 * @return ������������PK����
	 * @throws BOSException
	 * @throws EASBizException
	 * @see com.kingdee.eas.framework.app.CoreBaseControllerBean#saveBatchData(Context,
	 *      com.kingdee.eas.framework.CoreBaseCollection)
	 */
	public static IObjectPK[] submitBatchData(Context ctx, Connection cn, ICoreBase coreBase, AbstractObjectCollection colls) throws BOSException, EASBizException {
		// ���������PK����
		IObjectPK[] objectPKArray = null;
		try {
			FdcMethodUtil.invokeNoParameterMethod(coreBase, "setRollbackOnly", true);

			// ��ȡORMappingDAO���ݿ���ʽӿ�
			IORMappingDAO dao = getDAO(ctx, cn, coreBase);
			Iterator objectIter = colls.iterator();
			while (objectIter.hasNext()) {
				CoreBaseInfo value = (CoreBaseInfo) objectIter.next();
				IObjectPK pk = new ObjectUuidPK(value.getId());
				// ���ö������Ч״̬
				if (isExistPropertyName(ctx, value, IFWStatus.effectedStatus)) {
					value.setInt(IFWStatus.effectedStatus, EffectedStatusEnum.EFFECTED_VALUE);
				}
				if (value.getId() != null && coreBase.exists(pk)) {
					// ׼�������޸�����
					dao.updateBatch(pk, value);
				} else {
					// ׼��������������
					dao.addNewBatch(value);
				}
			}
			// ���������ύ
			objectPKArray = dao.executeBatchWithReturn();
			// ʹ��Ȩ�޷������
			ServiceStateManager.getInstance().enableNextCallServices();
		} finally {
			SQLUtils.cleanup(cn);
		}

		return objectPKArray;
	}

	/**
	 * ����ɾ��
	 * 
	 * <p>
	 * Ϊ������������ӵ����ݿ⼶�������ӿڣ����ص���������������Ҫôȫ���ɹ���Ҫôȫ��ʧ��(��ĿǰORMappingDAO�ṩ�������ӿڲ�û��ʵ��)
	 * 
	 * <p>
	 * ĿǰORMappingDAO�ṩ��������������Ϊ�������һ�����ݲ����ɹ������������������Բ��ֳɹ�����ʧ�ܣ������һ�����ݲ������ɹ��򲻻��ٶԺ��������ִ����������
	 * 
	 * @param ctx
	 *            ������
	 * @param cn
	 *            ���ݿ�����
	 * @param coreBase
	 *            ʵ��
	 * @param colls
	 *            ���󼯺�
	 * @return ������������PK����
	 * @throws BOSException
	 * @throws EASBizException
	 * @see com.kingdee.eas.framework.app.CoreBaseControllerBean#deleteBatchData(Context,
	 *      IObjectPK[])
	 */
	public static void deleteBatchData(Context ctx, Connection cn, ICoreBase coreBase, IObjectPK[] pkArray) throws BOSException, EASBizException {
		try {
			FdcMethodUtil.invokeNoParameterMethod(coreBase, "setRollbackOnly", true);

			// ��ȡORMappingDAO���ݿ���ʽӿ�
			IORMappingDAO dao = getDAO(ctx, cn, coreBase);
			for (int i = 0; i < pkArray.length; i++) {
				// ׼������ɾ������
				dao.deleteBatch(pkArray[i]);
			}
			// ��������ɾ��
			dao.executeBatch();
		} finally {
			SQLUtils.cleanup(cn);
		}
	}

	/**
	 * �Ƿ������չ����
	 * 
	 * @param ctx
	 *            ������
	 * @param coreBaseInfo
	 *            ʵ�����
	 * @param propertyName
	 *            ������
	 * @return
	 */
	private static boolean isExistPropertyName(Context ctx, CoreBaseInfo coreBaseInfo, String propertyName) {
		EntityObjectInfo entity = getBOSEntity(ctx, coreBaseInfo);
		PropertyInfo property = null;
		property = entity.getPropertyByName(propertyName);
		if (property == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * ȡ��BOSType
	 * 
	 * @param ctx
	 *            ������
	 * @param coreBaseInfo
	 *            ʵ�����
	 * @return
	 */
	private static EntityObjectInfo getBOSEntity(Context ctx, CoreBaseInfo coreBaseInfo) {
		IMetaDataLoader loader = MetaDataLoaderFactory.getMetaDataLoader(ctx);
		EntityObjectInfo entity = null;
		entity = loader.getEntity(coreBaseInfo.getBOSType());
		return entity;
	}

}
