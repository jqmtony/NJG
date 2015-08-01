/**
 * @(#)FdcORMappingDAOUtil.java 1.0 2014-5-22
 * @author 王正
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
 * 描述：房地产ORMapping工具类
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @createDate 2014-5-22
 * @version 1.0, 2014-5-22
 * @see
 * @since JDK1.4
 */
public class FdcORMappingDAOUtil {

	/**
	 * 取得ORM数据访问对象
	 * 
	 * @param ctx
	 *            上下文
	 * @param cn
	 *            数据库连接
	 * @param coreBase
	 *            实体
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
	 * 批量新增
	 * 
	 * <p>
	 * 为了提高性能增加的数据库级的批量接口，其特点是所有批量操作要么全部成功，要么全部失败(但目前ORMappingDAO提供的批量接口并没有实现)
	 * 
	 * <p>
	 * 目前ORMappingDAO提供的批量操作的行为是如果第一条数据操作成功则所有批量操作可以部分成功部分失败，如果第一条数据操作不成功则不会再对后面的数据执行批量操作
	 * 
	 * <p>
	 * 如果需要允许部分成功部分失败的批量操作请参考{@link #addnew(Context ctx, AbstractObjectCollection colls)}方法
	 * 
	 * 
	 * @param ctx
	 *            上下文
	 * @param cn
	 *            数据库连接
	 * @param coreBase
	 *            实体
	 * @param colls
	 *            对象集合
	 * @return 返回新增对象PK数组
	 * @throws BOSException
	 * @throws EASBizException
	 * @see com.kingdee.eas.framework.app.CoreBaseControllerBean#addnewBatchData(Context,
	 *      com.kingdee.eas.framework.CoreBaseCollection)
	 */
	public static IObjectPK[] addnewBatchData(Context ctx, Connection cn, ICoreBase coreBase, AbstractObjectCollection colls) throws BOSException, EASBizException {
		// 新增对象的PK数组
		IObjectPK[] objectPKArray = null;
		try {
			FdcMethodUtil.invokeNoParameterMethod(coreBase, "setRollbackOnly", true);

			// 获取ORMappingDAO数据库访问接口
			IORMappingDAO dao = getDAO(ctx, cn, coreBase);
			Iterator objectIter = colls.iterator();
			while (objectIter.hasNext()) {
				IObjectValue value = (IObjectValue) objectIter.next();
				// 准备批量新增数据
				dao.addNewBatch(value);
			}
			// 进行批量 新增
			objectPKArray = dao.executeBatchWithReturn();
		} finally {
			SQLUtils.cleanup(cn);
		}

		return objectPKArray;
	}

	/**
	 * 批量修改
	 * 
	 * <p>
	 * 为了提高性能增加的数据库级的批量接口，其特点是所有批量操作要么全部成功，要么全部失败(但目前ORMappingDAO提供的批量接口并没有实现)
	 * 
	 * <p>
	 * 目前ORMappingDAO提供的批量操作的行为是如果第一条数据操作成功则所有批量操作可以部分成功部分失败，如果第一条数据操作不成功则不会再对后面的数据执行批量操作
	 * 
	 * <p>
	 * 如果需要允许部分成功部分失败的批量操作请参考{@link #update(Context ctx, AbstractObjectCollection colls)}方法
	 * 
	 * @param ctx
	 *            上下文
	 * @param cn
	 *            数据库连接
	 * @param coreBase
	 *            实体
	 * @param colls
	 *            对象集合
	 * @return 返回新增对象PK数组
	 * @throws BOSException
	 * @throws EASBizException
	 * @see com.kingdee.eas.framework.app.CoreBaseControllerBean#updateBatchData(Context,
	 *      com.kingdee.eas.framework.CoreBaseCollection)
	 */
	public static IObjectPK[] updateBatchData(Context ctx, Connection cn, ICoreBase coreBase, AbstractObjectCollection colls) throws BOSException, EASBizException {
		// 新增对象的PK数组
		IObjectPK[] objectPKArray = null;
		try {
			FdcMethodUtil.invokeNoParameterMethod(coreBase, "setRollbackOnly", true);

			// 获取ORMappingDAO数据库访问接口
			IORMappingDAO dao = getDAO(ctx, cn, coreBase);
			Iterator objectIter = colls.iterator();
			while (objectIter.hasNext()) {
				CoreBaseInfo value = (CoreBaseInfo) objectIter.next();
				IObjectPK pk = new ObjectUuidPK(value.getId());
				if (value.getId() != null && coreBase.exists(pk)) {
					// 准备批量修改数据
					dao.updateBatch(pk, value);
				}
			}
			// 进行批量修改
			objectPKArray = dao.executeBatchWithReturn();
		} finally {
			SQLUtils.cleanup(cn);
		}

		return objectPKArray;
	}

	/**
	 * 批量修改(已经存在，不进行exists判断，以提高性能)
	 * 
	 * <p>
	 * 为了提高性能增加的数据库级的批量接口，其特点是所有批量操作要么全部成功，要么全部失败(但目前ORMappingDAO提供的批量接口并没有实现)
	 * 
	 * <p>
	 * 目前ORMappingDAO提供的批量操作的行为是如果第一条数据操作成功则所有批量操作可以部分成功部分失败，如果第一条数据操作不成功则不会再对后面的数据执行批量操作
	 * 
	 * <p>
	 * 如果需要允许部分成功部分失败的批量操作请参考{@link #update(Context ctx, AbstractObjectCollection colls)}方法
	 * 
	 * @param ctx
	 *            上下文
	 * @param cn
	 *            数据库连接
	 * @param coreBase
	 *            实体
	 * @param colls
	 *            对象集合
	 * @return 返回新增对象PK数组
	 * @throws BOSException
	 * @throws EASBizException
	 * @see com.kingdee.eas.framework.app.CoreBaseControllerBean#updateBatchData(Context,
	 *      com.kingdee.eas.framework.CoreBaseCollection)
	 */
	public static IObjectPK[] updateBatchDataWithExists(Context ctx, Connection cn, ICoreBase coreBase, AbstractObjectCollection colls) throws BOSException, EASBizException {
		// 新增对象的PK数组
		IObjectPK[] objectPKArray = null;
		try {
			FdcMethodUtil.invokeNoParameterMethod(coreBase, "setRollbackOnly", true);

			// 获取ORMappingDAO数据库访问接口
			IORMappingDAO dao = getDAO(ctx, cn, coreBase);
			Iterator objectIter = colls.iterator();
			while (objectIter.hasNext()) {
				CoreBaseInfo value = (CoreBaseInfo) objectIter.next();
				IObjectPK pk = new ObjectUuidPK(value.getId());

				// 准备批量修改数据
				dao.updateBatch(pk, value);
			}
			// 进行批量修改
			objectPKArray = dao.executeBatchWithReturn();
		} finally {
			SQLUtils.cleanup(cn);
		}

		return objectPKArray;
	}

	/**
	 * 批量保存
	 * 
	 * <p>
	 * 为了提高性能增加的数据库级的批量接口，其特点是所有批量操作要么全部成功，要么全部失败(但目前ORMappingDAO提供的批量接口并没有实现)
	 * 
	 * <p>
	 * 目前ORMappingDAO提供的批量操作的行为是如果第一条数据操作成功则所有批量操作可以部分成功部分失败，如果第一条数据操作不成功则不会再对后面的数据执行批量操作
	 * 
	 * <p>
	 * 如果需要允许部分成功部分失败的批量操作请参考{@link #save(Context ctx, AbstractObjectCollection colls)}方法
	 * 
	 * @param ctx
	 *            上下文
	 * @param cn
	 *            数据库连接
	 * @param coreBase
	 *            实体
	 * @param colls
	 *            对象集合
	 * @return 返回新增对象PK数组
	 * @throws BOSException
	 * @throws EASBizException
	 * @see com.kingdee.eas.framework.app.CoreBaseControllerBean#saveBatchData(Context,
	 *      com.kingdee.eas.framework.CoreBaseCollection)
	 */
	public static IObjectPK[] saveBatchData(Context ctx, Connection cn, ICoreBase coreBase, AbstractObjectCollection colls) throws BOSException, EASBizException {
		// 新增对象的PK数组
		IObjectPK[] objectPKArray = null;
		try {
			FdcMethodUtil.invokeNoParameterMethod(coreBase, "setRollbackOnly", true);

			// 获取ORMappingDAO数据库访问接口
			IORMappingDAO dao = getDAO(ctx, cn, coreBase);
			Iterator objectIter = colls.iterator();
			while (objectIter.hasNext()) {
				CoreBaseInfo value = (CoreBaseInfo) objectIter.next();
				IObjectPK pk = new ObjectUuidPK(value.getId());
				// 设置对象的生效状态
				if (isExistPropertyName(ctx, value, IFWStatus.effectedStatus)) {
					value.setInt(IFWStatus.effectedStatus, EffectedStatusEnum.TEMPSTORE_VALUE);
				}
				if (value.getId() != null && coreBase.exists(pk)) {
					// 准备批量修改数据
					dao.updateBatch(pk, value);
				} else {
					// 准备批量新增数据
					dao.addNewBatch(value);
				}
			}
			// 进行批量保存
			objectPKArray = dao.executeBatchWithReturn();
			// 使用权限服务可用
			ServiceStateManager.getInstance().enableNextCallServices();
		} finally {
			SQLUtils.cleanup(cn);
		}
		return objectPKArray;
	}

	/**
	 * 批量提交
	 * 
	 * <p>
	 * 为了提高性能增加的数据库级的批量接口，其特点是所有批量操作要么全部成功，要么全部失败(但目前ORMappingDAO提供的批量接口并没有实现)
	 * 
	 * <p>
	 * 目前ORMappingDAO提供的批量操作的行为是如果第一条数据操作成功则所有批量操作可以部分成功部分失败，如果第一条数据操作不成功则不会再对后面的数据执行批量操作
	 * 
	 * <p>
	 * 如果需要允许部分成功部分失败的批量操作请参考{@link #submit(Context ctx, AbstractObjectCollection colls)}方法
	 * 
	 * @param ctx
	 *            上下文
	 * @param cn
	 *            数据库连接
	 * @param coreBase
	 *            实体
	 * @param colls
	 *            对象集合
	 * @return 返回新增对象PK数组
	 * @throws BOSException
	 * @throws EASBizException
	 * @see com.kingdee.eas.framework.app.CoreBaseControllerBean#saveBatchData(Context,
	 *      com.kingdee.eas.framework.CoreBaseCollection)
	 */
	public static IObjectPK[] submitBatchData(Context ctx, Connection cn, ICoreBase coreBase, AbstractObjectCollection colls) throws BOSException, EASBizException {
		// 新增对象的PK数组
		IObjectPK[] objectPKArray = null;
		try {
			FdcMethodUtil.invokeNoParameterMethod(coreBase, "setRollbackOnly", true);

			// 获取ORMappingDAO数据库访问接口
			IORMappingDAO dao = getDAO(ctx, cn, coreBase);
			Iterator objectIter = colls.iterator();
			while (objectIter.hasNext()) {
				CoreBaseInfo value = (CoreBaseInfo) objectIter.next();
				IObjectPK pk = new ObjectUuidPK(value.getId());
				// 设置对象的生效状态
				if (isExistPropertyName(ctx, value, IFWStatus.effectedStatus)) {
					value.setInt(IFWStatus.effectedStatus, EffectedStatusEnum.EFFECTED_VALUE);
				}
				if (value.getId() != null && coreBase.exists(pk)) {
					// 准备批量修改数据
					dao.updateBatch(pk, value);
				} else {
					// 准备批量新增数据
					dao.addNewBatch(value);
				}
			}
			// 进行批量提交
			objectPKArray = dao.executeBatchWithReturn();
			// 使用权限服务可用
			ServiceStateManager.getInstance().enableNextCallServices();
		} finally {
			SQLUtils.cleanup(cn);
		}

		return objectPKArray;
	}

	/**
	 * 批量删除
	 * 
	 * <p>
	 * 为了提高性能增加的数据库级的批量接口，其特点是所有批量操作要么全部成功，要么全部失败(但目前ORMappingDAO提供的批量接口并没有实现)
	 * 
	 * <p>
	 * 目前ORMappingDAO提供的批量操作的行为是如果第一条数据操作成功则所有批量操作可以部分成功部分失败，如果第一条数据操作不成功则不会再对后面的数据执行批量操作
	 * 
	 * @param ctx
	 *            上下文
	 * @param cn
	 *            数据库连接
	 * @param coreBase
	 *            实体
	 * @param colls
	 *            对象集合
	 * @return 返回新增对象PK数组
	 * @throws BOSException
	 * @throws EASBizException
	 * @see com.kingdee.eas.framework.app.CoreBaseControllerBean#deleteBatchData(Context,
	 *      IObjectPK[])
	 */
	public static void deleteBatchData(Context ctx, Connection cn, ICoreBase coreBase, IObjectPK[] pkArray) throws BOSException, EASBizException {
		try {
			FdcMethodUtil.invokeNoParameterMethod(coreBase, "setRollbackOnly", true);

			// 获取ORMappingDAO数据库访问接口
			IORMappingDAO dao = getDAO(ctx, cn, coreBase);
			for (int i = 0; i < pkArray.length; i++) {
				// 准备批量删除数据
				dao.deleteBatch(pkArray[i]);
			}
			// 进行批量删除
			dao.executeBatch();
		} finally {
			SQLUtils.cleanup(cn);
		}
	}

	/**
	 * 是否存在拓展属性
	 * 
	 * @param ctx
	 *            上下文
	 * @param coreBaseInfo
	 *            实体对象
	 * @param propertyName
	 *            属性名
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
	 * 取得BOSType
	 * 
	 * @param ctx
	 *            上下文
	 * @param coreBaseInfo
	 *            实体对象
	 * @return
	 */
	private static EntityObjectInfo getBOSEntity(Context ctx, CoreBaseInfo coreBaseInfo) {
		IMetaDataLoader loader = MetaDataLoaderFactory.getMetaDataLoader(ctx);
		EntityObjectInfo entity = null;
		entity = loader.getEntity(coreBaseInfo.getBOSType());
		return entity;
	}

}
