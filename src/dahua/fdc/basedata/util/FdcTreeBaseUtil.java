/*
 * @(#)FdcTreeBaseUtil.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.basedata.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.ColumnInfo;
import com.kingdee.bos.metadata.data.DataTableInfo;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.PropertyCollection;
import com.kingdee.bos.metadata.entity.PropertyInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.util.MetaDataLoader;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.util.ParamManager;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.IFDCSQLFacade;
import com.kingdee.eas.fdc.basedata.ProjectInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.TreeBase;
import com.kingdee.eas.framework.TreeBaseCollection;
import com.kingdee.eas.framework.TreeBaseException;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.util.StringUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.LocaleUtils;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.db.SQLUtils;

/**
 * 房地产 树形实体工具类
 * 
 * <p> 
 * 参照：com.kingdee.eas.ec.common.EcTreeBaseUtil，建筑树形实体工具类
 * 
 * <p> 
 * 过滤项，“工程项目”的字段名命名不一样：
 * <ol>
 * <li>建筑，project.id，FProjectID
 * <li>房地产，curProject.id，FCurProject
 * </ol>
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @date 2013-9-10
 * @see
 * @since 1.4
 */
public class FdcTreeBaseUtil {
	/**
	 * 起始级次
	 */
	public static final int START_LEVEL = 1;

	/**
	 * 英文
	 */
	public static final Locale LOCALE_L1 = LocaleUtils.getLocale("L1");
	/**
	 * 简体中文
	 */
	public static final Locale LOCALE_L2 = LocaleUtils.getLocale("L2");
	/**
	 * 繁体中文
	 */
	public static final Locale LOCALE_L3 = LocaleUtils.getLocale("L3");

	// /////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

	// /////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

	/**
	 * 获取编码长度，外部可以覆盖而不获取配置的全局参数，建议不予覆盖
	 * 
	 * @param ctx
	 * @param info
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	public static int getLongNumberLen(Context ctx, TreeBaseInfo info) throws EASBizException, BOSException {
		int maxLen = 0;
		String longNumberParam = null;
		longNumberParam = ParamManager.getParamValue(ctx, null, IFWEntityStruct.longNumberParam);
		if (longNumberParam != null) {
			maxLen = Integer.parseInt(longNumberParam);
		}
		return maxLen;
	}

	/**
	 * 对长编码的长度进行限制
	 * 
	 * @param info
	 * @throws BOSException
	 */
	public static void checkLongNumberLen(Context ctx, TreeBaseInfo info) throws EASBizException, BOSException {
		String propertyAlias = null;
		int maxLen = 0;
		propertyAlias = getPropertyAlias(ctx, info, IFWEntityStruct.tree_LongNumber, ctx.getLocale());

		int len = (null == info.getLongNumber()) ? -1 : info.getLongNumber().getBytes().length;
		maxLen = getLongNumberLen(ctx, info);
		// 如果用户进行了设置，并且大于80，就使用用户设置值。
		// if (maxLen > IFWEntityStruct.longNumberLen) {
		// if (len > maxLen) {
		// throw new TreeBaseException(
		// TreeBaseException.CHECKLONGNUMBERLEN, new Object[] {
		// propertyAlias, String.valueOf(maxLen) });
		// }
		// } else {
		// if (len > IFWEntityStruct.longNumberLen) {
		// throw new TreeBaseException(
		// TreeBaseException.CHECKLONGNUMBERLEN, new Object[] {
		// propertyAlias,
		// String.valueOf(IFWEntityStruct.longNumberLen) });
		// }
		// }
		if (len > maxLen) {
			throw new TreeBaseException(TreeBaseException.CHECKLONGNUMBERLEN, new Object[] { propertyAlias, String.valueOf(maxLen) });
		}
	}

	/**
	 * 取得BOS类型
	 * 
	 * @param ctx
	 * @param coreBaseInfo
	 * @return
	 */
	public static EntityObjectInfo getBOSEntity(Context ctx, CoreBaseInfo coreBaseInfo) {
		EntityObjectInfo entity = null;

		// IMetaDataLoader loader =
		// MetaDataLoaderFactory.getMetaDataLoader(ctx);
		// entity = loader.getEntity(coreBaseInfo.getBOSType());

		if (true) {
			throw new RuntimeException("未实现的方法");
		}
		// EcMetaDataUtil.getEntityObjectInfo(ctx, coreBaseInfo.getBOSType().toString());

		return entity;
	}

	/**
	 * 取得属性
	 * 
	 * @param ctx
	 * @param info
	 * @param propertyName
	 * @return
	 */
	public static PropertyInfo getProperty(Context ctx, CoreBaseInfo info, String propertyName) {
		EntityObjectInfo entity = getBOSEntity(ctx, info);
		PropertyInfo property = null;
		property = entity.getPropertyByNameRuntime(propertyName);

		return property;
	}

	/**
	 * 取得属性别名
	 * 
	 * @param ctx
	 * @param info
	 * @param propertyName
	 * @param locale
	 * @return
	 */
	public static String getPropertyAlias(Context ctx, CoreBaseInfo info, String propertyName, Locale locale) {
		String alias = null;
		PropertyInfo property = getProperty(ctx, info, propertyName);
		if (property != null) {
			alias = property.getAlias(locale);
		}
		return alias;
	}

	/**
	 * 处理显示名称的属性
	 * 
	 * @param parent
	 * @param info
	 * @param locale
	 */
	public static void linkDisplayName(TreeBaseInfo parent, TreeBaseInfo info, Locale locale) {
		String disPlayName = null;
		String parentDisplayName = parent.getDisplayName(locale);
		if (parentDisplayName != null) {
			disPlayName = parentDisplayName + IFWEntityStruct.displayNameSeparator + info.getName(locale);
		} else {
			disPlayName = info.getName(locale);
		}
		info.setDisplayName(disPlayName, locale);
	}

	/**
	 * 如果显示名称长度超过80，从头开始截取
	 * 
	 * @param info
	 */
	public static void limitDisplayNameLength(TreeBaseInfo info) {
		String displayName = info.getDisplayName();
		String[] displayNameVar = StringUtil.split(displayName, IFWEntityStruct.displayNameSeparator);
		if (displayNameVar == null) {
			return;
		}
		if (displayNameVar.length > 1) {
			if (displayName.length() > IFWEntityStruct.displayNameLong) {
				int len = displayName.indexOf(IFWEntityStruct.displayNameSeparator);
				displayName = displayName.substring(len + 1);
				info.setDisplayName(displayName);
				limitDisplayNameLength(info);
			}
		}
	}

	/**
	 * 如果显示名称长度超过80，从头开始截取
	 * 
	 * @param tree
	 * @param locale
	 */
	public static void limitDisplayNameLength(TreeBaseInfo tree, Locale locale) {
		String displayName = tree.getDisplayName(locale);
		String[] displayNameVar = StringUtil.split(displayName, IFWEntityStruct.displayNameSeparator);
		if (displayNameVar == null) {
			return;
		}
		if (displayNameVar.length > 1) {
			if (displayName.length() > IFWEntityStruct.displayNameLong) {
				int len = displayName.indexOf(IFWEntityStruct.displayNameSeparator);
				displayName = displayName.substring(len + 1);
				tree.setDisplayName(displayName, locale);
				limitDisplayNameLength(tree, locale);
			}
		}
	}

	// /////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

	/**
	 * 生成 有空值的顶级节点id的 SQL
	 * 
	 * @param ctx
	 *            应用上下文；可以为空
	 * @param bosTypeStr
	 *            BOS对象类型字符串；不能为空
	 * @param filterSql
	 *            过滤器SQL；可以为空；不能加别名
	 * @return
	 * @throws Exception
	 */
	public static String generateHasNullTopIdSql(Context ctx, String bosTypeStr, String filterSql) throws Exception {
		String sql = null;

		ITreeBase iTreeBase = getInstance(ctx, bosTypeStr);
		if (iTreeBase instanceof TreeBase) {
			// 待定
		}

		BOSObjectType bosType = BOSObjectType.create(bosTypeStr);
		EntityObjectInfo entityObjectInfo = MetaDataLoader.getEntity(ctx, bosType);
		DataTableInfo dataTableInfo = entityObjectInfo.getTable();
		String msg = " 实体为 " + entityObjectInfo.getName() + "，没有对应的数据库表!";
		Assert.assertNotNull(msg, dataTableInfo);

		sql = " SELECT COUNT(*) cout FROM {0} " + " WHERE ((FParentID IS NOT NULL AND FTopID IS NULL) OR (FLevel = 1 AND FTopID IS NULL)) "
				+ " {3} ";

		sql = sql.replaceAll("\\{0\\}", dataTableInfo.getName());
		if (FdcStringUtil.isBlank(filterSql)) {
			sql = sql.replaceFirst("\\{3\\}", "");
		} else {
			sql = sql.replaceFirst("\\{3\\}", " AND " + filterSql);
		}

		return sql;
	}

	/**
	 * 有空值的顶级节点id
	 * 
	 * @param ctx
	 *            应用上下文；可以为空
	 * @param bosTypeStr
	 *            BOS对象类型字符串；不能为空
	 * @param filterSql
	 *            过滤器SQL；可以为空；不能加别名
	 * @return
	 * @throws Exception
	 */
	public static boolean hasNullTopId(Context ctx, String bosTypeStr, String filterSql) throws Exception {
		boolean flag = false;

		ITreeBase iTreeBase = getInstance(ctx, bosTypeStr);
		if (iTreeBase instanceof TreeBase) {
			// 待定
		}
		String sql = generateHasNullTopIdSql(ctx, bosTypeStr, filterSql);

		// 有空值的顶级节点id
		IFDCSQLFacade eCSQLFacade = null;
		if (null == ctx) {
			eCSQLFacade = FDCSQLFacadeFactory.getRemoteInstance();
		} else {
			eCSQLFacade = FDCSQLFacadeFactory.getLocalInstance(ctx);
		}
		IRowSet rs = null;
		try {
			rs = eCSQLFacade.executeQuery(sql, new ArrayList());
			if (rs.next()) {
				long count = rs.getLong("cout");
				flag = (count > 0) ? true : false;
			}
		} finally {
			// 关闭连接
			SQLUtils.cleanup(rs);
		}

		return flag;
	}

	/**
	 * 生成 有空值的顶级节点id的 SQL
	 * 
	 * @param ctx
	 *            应用上下文；可以为空
	 * @param bosTypeStr
	 *            BOS对象类型字符串；不能为空
	 * @param projectInfo
	 *            工程项目对象；不能为空
	 * @return
	 * @throws Exception
	 */
	public static String generateHasNullTopIdSql(Context ctx, String bosTypeStr, ProjectInfo projectInfo) throws Exception {
		String sql = null;

		// 生成 有空值的顶级节点id的 SQL
		String projectId = projectInfo.getId().toString();
		String filterSql = " FCurProject = '" + projectId + "' ";
		sql = generateHasNullTopIdSql(ctx, bosTypeStr, filterSql);

		return sql;
	}

	/**
	 * 有空值的顶级节点id
	 * 
	 * @param ctx
	 *            应用上下文；可以为空
	 * @param bosTypeStr
	 *            BOS对象类型字符串；不能为空
	 * @param projectInfo
	 *            工程项目对象；不能为空
	 * @return
	 * @throws Exception
	 */
	public static boolean hasNullTopId(Context ctx, String bosTypeStr, ProjectInfo projectInfo) throws Exception {
		boolean flag = false;

		// 有空值的顶级节点id
		String projectId = projectInfo.getId().toString();
		String filterSql = " FCurProject = '" + projectId + "' ";
		flag = hasNullTopId(ctx, bosTypeStr, filterSql);

		return flag;
	}

	// /////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

	/**
	 * 生成 更新所有顶级节点id的 SQL
	 * 
	 * @param ctx
	 *            应用上下文；可以为空
	 * @param bosTypeStr
	 *            BOS对象类型字符串；不能为空
	 * @param filterSql
	 *            过滤器SQL；可以为空；不能加别名
	 * @return
	 * @throws Exception
	 */
	public static String generateUpdateTopIdSql(Context ctx, String bosTypeStr, String filterSql) throws Exception {
		String sql = null;

		ITreeBase iTreeBase = getInstance(ctx, bosTypeStr);
		if (iTreeBase instanceof TreeBase) {
			// 待定
		}

		BOSObjectType bosType = BOSObjectType.create(bosTypeStr);
		EntityObjectInfo entityObjectInfo = MetaDataLoader.getEntity(ctx, bosType);
		DataTableInfo dataTableInfo = entityObjectInfo.getTable();
		String msg = " 实体为 " + entityObjectInfo.getName() + "，没有对应的数据库表!";
		Assert.assertNotNull(msg, dataTableInfo);

		sql = " UPDATE {0} SET FTopID = " + " (" + " 	SELECT patId FROM (" + " 	SELECT pat.FID patId, chd.FID chdId  FROM {0} pat "
				+ " 	INNER JOIN {0} chd on CHARINDEX(pat.FLongNumber||'!', chd.FLongNumber || '!') = 1"
				+ " 	WHERE pat.FLevel = 1 AND chd.FTopID IS NULL {2}) temp WHERE temp.chdId = {0}.FID" + " 	)" + " 	WHERE FTopID IS NULL "
				+ " {3} ";

		sql = sql.replaceAll("\\{0\\}", dataTableInfo.getName());
		if (FdcStringUtil.isNotBlank(filterSql)) {
			sql = sql.replaceFirst("\\{2\\}", " AND pat." + filterSql.trim() + " AND chd." + filterSql.trim());
			sql = sql.replaceFirst("\\{3\\}", " AND " + filterSql);
		} else {
			sql = sql.replaceFirst("\\{2\\}", "");
			sql = sql.replaceFirst("\\{3\\}", "");
		}

		return sql;
	}

	/**
	 * 更新所有顶级节点id
	 * 
	 * @param ctx
	 *            应用上下文；可以为空
	 * @param bosTypeStr
	 *            BOS对象类型字符串；不能为空
	 * @param filterSql
	 *            过滤器SQL；可以为空；不能加别名
	 * @return
	 * @throws Exception
	 */
	public static void updateTopId(Context ctx, String bosTypeStr, String filterSql) throws Exception {
		ITreeBase iTreeBase = getInstance(ctx, bosTypeStr);
		if (iTreeBase instanceof TreeBase) {
			// 待定
		}
		String sql = generateUpdateTopIdSql(ctx, bosTypeStr, filterSql);

		// 更新所有顶级节点id
		IFDCSQLFacade eCSQLFacade = null;
		if (null == ctx) {
			eCSQLFacade = FDCSQLFacadeFactory.getRemoteInstance();
		} else {
			eCSQLFacade = FDCSQLFacadeFactory.getLocalInstance(ctx);
		}
		eCSQLFacade.executeUpdate(sql, new ArrayList());

	}

	/**
	 * 生成 更新所有顶级节点id的 SQL
	 * 
	 * @param ctx
	 *            应用上下文；可以为空
	 * @param bosTypeStr
	 *            BOS对象类型字符串；不能为空
	 * @param projectInfo
	 *            工程项目对象；不能为空
	 * @return
	 * @throws Exception
	 */
	public static String generateUpdateTopIdSql(Context ctx, String bosTypeStr, ProjectInfo projectInfo) throws Exception {
		String sql = null;

		// 生成 更新所有顶级节点id的 SQL
		String projectId = projectInfo.getId().toString();
		String filterSql = " FCurProject = '" + projectId + "' ";
		sql = generateUpdateTopIdSql(ctx, bosTypeStr, filterSql);

		return sql;
	}

	/**
	 * 更新所有顶级节点id
	 * 
	 * @param ctx
	 *            应用上下文；可以为空
	 * @param bosTypeStr
	 *            BOS对象类型字符串；不能为空
	 * @param projectInfo
	 *            工程项目对象；不能为空
	 * @return
	 * @throws Exception
	 */
	public static void updateTopId(Context ctx, String bosTypeStr, ProjectInfo projectInfo) throws Exception {
		// 更新所有顶级节点id
		String projectId = projectInfo.getId().toString();
		String filterSql = " FCurProject = '" + projectId + "' ";
		updateTopId(ctx, bosTypeStr, filterSql);
	}

	// /////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

	/**
	 * 生成 查找所有顶级节点id的 SQL
	 * 
	 * @param ctx
	 *            应用上下文；可以为空
	 * @param objectCollection
	 *            对象集合；不能为空；装载TreeBaseInfo类型对象
	 * @param filterSql
	 *            过滤器SQL；可以为空；不能加别名
	 * @return
	 * @throws BOSException
	 */
	public static String generateFindTopIdSql(Context ctx, IObjectCollection objectCollection, String filterSql) throws BOSException {
		String sql = null;

		if (FdcObjectCollectionUtil.isNotEmpty(objectCollection)) {
			// /////////////////////////////////////////////////////////////

			IObjectValue objectValue = objectCollection.getObject(0);
			TreeBaseInfo treeBaseInfo = (TreeBaseInfo) objectValue;
			String msg = objectValue + " 类型为 " + objectValue.getClass() + "，不是继承自 " + TreeBaseInfo.class;
			Assert.assertTrue(msg, objectValue instanceof TreeBaseInfo);

			BOSObjectType bosType = objectValue.getBOSType();
			EntityObjectInfo entityObjectInfo = MetaDataLoader.getEntity(ctx, bosType);
			DataTableInfo dataTableInfo = entityObjectInfo.getTable();
			msg = objectValue + " 实体为 " + entityObjectInfo.getName() + "，没有对应的数据库表!";
			Assert.assertNotNull(msg, dataTableInfo);

			// /////////////////////////////////////////////////////////////

			StringBuffer idInStr = new StringBuffer();
			for (int i = 0, size = objectCollection.size(); i < size; i++) {
				treeBaseInfo = (TreeBaseInfo) objectCollection.getObject(i);

				idInStr.append("'");
				idInStr.append(treeBaseInfo.getId().toString());
				idInStr.append("'");

				if (i != size - 1) {
					idInStr.append(",");
				}
			}
			idInStr.insert(0, "(");
			idInStr.append(")");

			sql = " SELECT DISTINCT bill.FID FROM {0} bill "
					+ " INNER JOIN  (SELECT FLongNumber FROM {0} bill WHERE bill.FID IN {1} {2}) sub "
					+ " ON CHARINDEX(bill.FLongNumber || '!', sub.FLongNumber || '!') = 1 " + " WHERE bill.FLevel = 1 " + " {3} ";
			sql = sql.replaceAll("\\{0\\}", dataTableInfo.getName());
			sql = sql.replaceFirst("\\{1\\}", idInStr.toString());
			if (FdcStringUtil.isNotBlank(filterSql)) {
				sql = sql.replaceFirst("\\{2\\}", " AND " + filterSql);
				sql = sql.replaceFirst("\\{3\\}", " WHERE " + filterSql);
			} else {
				sql = sql.replaceFirst("\\{2\\}", "");
				sql = sql.replaceFirst("\\{3\\}", "");
			}

			// /////////////////////////////////////////////////////////////
		}

		return sql;
	}

	/**
	 * 查找所有顶级节点id
	 * 
	 * @param ctx
	 *            应用上下文；可以为空
	 * @param objectCollection
	 *            对象集合；不能为空；装载TreeBaseInfo类型对象
	 * @param filterSql
	 *            过滤器SQL；可以为空；不能加别名
	 * @return
	 * @throws BOSException
	 */
	public static Set findTopId(Context ctx, IObjectCollection objectCollection, String filterSql) throws BOSException {
		Set topIdSet = new LinkedHashSet();

		String sql = generateFindTopIdSql(ctx, objectCollection, filterSql);
		if (FdcStringUtil.isNotBlank(sql)) {
			IFDCSQLFacade eCSQLFacade = null;
			if (null == ctx) {
				eCSQLFacade = FDCSQLFacadeFactory.getRemoteInstance();
			} else {
				eCSQLFacade = FDCSQLFacadeFactory.getLocalInstance(ctx);
			}

			IRowSet rs = null;
			String tempId = null;
			try {
				rs = eCSQLFacade.executeQuery(sql, null);
				while (rs.next()) {
					tempId = rs.getString("FID");
					topIdSet.add(tempId);
				}
			} catch (SQLException e) {
				throw new BOSException(e);
			} finally {
				// 关闭连接
				SQLUtils.cleanup(rs);
			}
		}

		return topIdSet;
	}

	/**
	 * 生成 级联查找所有父类id（包括自己）的 SQL
	 * 
	 * @param iTreeBase
	 *            TreeBase接口；不能为空
	 * @param objectCollection
	 *            对象集合；不能为空；装载TreeBaseInfo类型对象
	 * @param filterSql
	 *            过滤器SQL；可以为空；不能加别名
	 * @return
	 * @throws BOSException
	 */
	public static String generateFindParentIdWithSelfCascadeSql(ITreeBase iTreeBase, IObjectCollection objectCollection, String filterSql)
			throws BOSException {
		String sql = null;

		if (FdcObjectCollectionUtil.isNotEmpty(objectCollection)) {
			// /////////////////////////////////////////////////////////////

			IObjectValue objectValue = objectCollection.getObject(0);
			TreeBaseInfo treeBaseInfo = (TreeBaseInfo) objectValue;
			String msg = objectValue + " 类型为 " + objectValue.getClass() + "，不是继承自 " + TreeBaseInfo.class;
			Assert.assertTrue(msg, objectValue instanceof TreeBaseInfo);

			BOSObjectType bosType = objectValue.getBOSType();
			EntityObjectInfo entityObjectInfo = MetaDataLoader.getEntity(iTreeBase.getContext(), bosType);
			DataTableInfo dataTableInfo = entityObjectInfo.getTable();
			msg = objectValue + " 实体为 " + entityObjectInfo.getName() + "，没有对应的数据库表!";
			Assert.assertNotNull(msg, dataTableInfo);

			// /////////////////////////////////////////////////////////////

			StringBuffer idInStr = new StringBuffer();
			for (int i = 0, size = objectCollection.size(); i < size; i++) {
				treeBaseInfo = (TreeBaseInfo) objectCollection.getObject(i);

				idInStr.append("'");
				idInStr.append(treeBaseInfo.getId().toString());
				idInStr.append("'");

				if (i != size - 1) {
					idInStr.append(",");
				}
			}
			idInStr.insert(0, "(");
			idInStr.append(")");

			sql = " SELECT DISTINCT bill.FID FROM {0} bill "
					+ " INNER JOIN  (SELECT FLongNumber FROM {0} bill WHERE bill.FID IN {1} {2}) sub "
					+ " ON CHARINDEX(bill.FLongNumber || '!', sub.FLongNumber || '!') = 1 " + " {3} ";
			sql = sql.replaceAll("\\{0\\}", dataTableInfo.getName());
			sql = sql.replaceFirst("\\{1\\}", idInStr.toString());

			if (FdcStringUtil.isBlank(filterSql)) {
				sql = sql.replaceFirst("\\{2\\}", "");
				sql = sql.replaceFirst("\\{3\\}", "");
			} else {
				sql = sql.replaceFirst("\\{2\\}", " AND " + filterSql);
				sql = sql.replaceFirst("\\{3\\}", " WHERE " + filterSql);
			}

			// /////////////////////////////////////////////////////////////
		}

		return sql;
	}

	/**
	 * 级联查找所有父类id（包括自己）
	 * 
	 * @param iTreeBase
	 *            TreeBase接口；不能为空
	 * @param objectCollection
	 *            对象集合；不能为空；装载TreeBaseInfo类型对象
	 * @param filterSql
	 *            过滤器SQL；可以为空；不能加别名
	 * @return
	 * @throws BOSException
	 */
	public static Set findParentIdWithSelfCascade(ITreeBase iTreeBase, IObjectCollection objectCollection, String filterSql)
			throws BOSException {
		Set parentIdSet = new LinkedHashSet();

		String sql = generateFindParentIdWithSelfCascadeSql(iTreeBase, objectCollection, filterSql);
		if (FdcStringUtil.isNotBlank(sql)) {
			IFDCSQLFacade eCSQLFacade = null;
			if (null == iTreeBase) {
				eCSQLFacade = FDCSQLFacadeFactory.getRemoteInstance();
			} else {
				eCSQLFacade = FDCSQLFacadeFactory.getLocalInstance(iTreeBase.getContext());
			}

			IRowSet rs = null;
			String tempId = null;
			try {
				rs = eCSQLFacade.executeQuery(sql, null);
				while (rs.next()) {
					tempId = rs.getString("FID");
					parentIdSet.add(tempId);
				}
			} catch (SQLException e) {
				throw new BOSException(e);
			} finally {
				// 关闭连接
				SQLUtils.cleanup(rs);
			}
		}

		return parentIdSet;
	}

	/**
	 * 取得 包含合计字段树形结构 对象集合
	 * 
	 * @param iTreeBase
	 *            TreeBase接口；不能为空
	 * @param objectValue
	 *            TreeBaseInfo对象；非空
	 * @param projectId
	 *            工程项目ID；非空
	 * @param propertyNameArr
	 *            属性名称数组；非空
	 * @param filterSql
	 *            过滤器SQL；可以为空；不能加别名
	 * @return
	 * @throws Exception
	 */
	public static TreeBaseCollection getTreeObjectCollectionWithSumField(ITreeBase iTreeBase, TreeBaseInfo objectValue, String projectId,
			String[] propertyNameArr, String filterSql) throws Exception {
		TreeBaseCollection objectCollection = null;

		if (null != objectValue) {
			// /////////////////////////////////////////////////////////////

			String msg = objectValue + " 类型为 " + objectValue.getClass() + "，不是继承自 " + TreeBaseInfo.class;
			Assert.assertTrue(msg, objectValue instanceof TreeBaseInfo);

			BOSObjectType bosType = objectValue.getBOSType();
			EntityObjectInfo entityObjectInfo = MetaDataLoader.getEntity(iTreeBase.getContext(), bosType);
			DataTableInfo dataTableInfo = entityObjectInfo.getTable();
			msg = bosType + " 实体为 " + entityObjectInfo.getName() + "，没有对应的数据库表!";
			Assert.assertNotNull(msg, dataTableInfo);
			String dataTableName = dataTableInfo.getName();

			// 取得实体的所有自有属性以及继承来的属性, 并去除重复的属性(用属性名称判断)
			PropertyCollection propertyCollection = entityObjectInfo.getInheritedNoDuplicatedPropertiesRuntime();

			// /////////////////////////////////////////////////////////////

			Map filerMap = new HashMap();
			filerMap.put("curProject.id", projectId);
			filerMap.put("isLeaf", Boolean.FALSE);
			FilterInfo filter = FdcEntityViewUtil.getFilter(filerMap);
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			objectCollection = iTreeBase.getTreeBaseCollection(view);

			if (FdcObjectCollectionUtil.isEmpty(objectCollection)) {
				return objectCollection;
			}
			// 解析对象集合的字符串类型的ID映射
			Map stringIdMap = FdcObjectCollectionUtil.parseUniqueStringIdMap(objectCollection);

			// /////////////////////////////////////////////////////////////

			List propertyNameList = new ArrayList(Arrays.asList(propertyNameArr));
			FdcCollectionUtil.clearDuplicateAndNull(propertyNameList);

			StringBuffer sumFieldStr = new StringBuffer();
			PropertyInfo propertyInfo = null;
			ColumnInfo columnInfo = null;
			String propertyName = null;
			String columnName = null;
			Map propertyColumnMap = new LinkedHashMap();
			Map columnPropertyMap = new LinkedHashMap();
			for (int i = 0, size = propertyCollection.size(); i < size; i++) {
				propertyInfo = (PropertyInfo) propertyCollection.getObject(i);
				propertyName = propertyInfo.getName();
				columnInfo = propertyInfo.getMappingField();
				if (null == columnInfo) {
					propertyColumnMap.put(propertyName, null);
					continue;
				}
				columnName = columnInfo.getName();
				propertyColumnMap.put(propertyName, columnName);
				columnPropertyMap.put(columnName, propertyName);
			}

			// /////////////////////////////////////////////////////////////

			String sql = generateFindTreeSumFieldSql(dataTableName, propertyColumnMap, columnPropertyMap, projectId, propertyNameList,
					filterSql);

			IFDCSQLFacade iFDCSQLFacade = null;
			if (iTreeBase != null) {
				iFDCSQLFacade = FDCSQLFacadeFactory.getLocalInstance(iTreeBase.getContext());
			} else {
				iFDCSQLFacade = FDCSQLFacadeFactory.getRemoteInstance();
			}

			IRowSet rs = null;
			int size = propertyNameList.size();
			try {
				rs = iFDCSQLFacade.executeQuery(sql, null);
				while (rs.next()) {
					String fID = rs.getString("FID");
					Object fx = null;
					CoreBaseInfo coreBaseInfo = (CoreBaseInfo) stringIdMap.get(fID);
					if (null == coreBaseInfo) {
						continue;
					}

					for (int i = 0; i < size; i++) {
						propertyName = (String) propertyNameList.get(i);
						columnName = (String) propertyColumnMap.get(propertyName);

						fx = rs.getObject(columnName);
						coreBaseInfo.put(propertyName, fx);
					}
				}
			} finally {
				// 关闭连接
				SQLUtils.cleanup(rs);
			}

			// /////////////////////////////////////////////////////////////
		}

		return objectCollection;
	}

	// /////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

	/**
	 * 生成 级联查找所有父类id（包括自己）的 SQL
	 * 
	 * @param ctx
	 *            应用上下文；可以为空
	 * @param objectCollection
	 *            对象集合；不能为空；装载TreeBaseInfo类型对象
	 * @param filterSql
	 *            过滤器SQL；可以为空；不能加别名
	 * @return
	 * @throws BOSException
	 */
	public static String generateFindParentIdWithSelfCascadeSql(Context ctx, IObjectCollection objectCollection, String filterSql)
			throws BOSException {
		String sql = null;

		if (FdcObjectCollectionUtil.isNotEmpty(objectCollection)) {
			// /////////////////////////////////////////////////////////////

			IObjectValue objectValue = objectCollection.getObject(0);
			TreeBaseInfo treeBaseInfo = (TreeBaseInfo) objectValue;
			String msg = objectValue + " 类型为 " + objectValue.getClass() + "，不是继承自 " + TreeBaseInfo.class;
			Assert.assertTrue(msg, objectValue instanceof TreeBaseInfo);

			BOSObjectType bosType = objectValue.getBOSType();
			EntityObjectInfo entityObjectInfo = MetaDataLoader.getEntity(ctx, bosType);
			DataTableInfo dataTableInfo = entityObjectInfo.getTable();
			msg = objectValue + " 实体为 " + entityObjectInfo.getName() + "，没有对应的数据库表!";
			Assert.assertNotNull(msg, dataTableInfo);

			// /////////////////////////////////////////////////////////////

			StringBuffer idInStr = new StringBuffer();
			for (int i = 0, size = objectCollection.size(); i < size; i++) {
				treeBaseInfo = (TreeBaseInfo) objectCollection.getObject(i);

				idInStr.append("'");
				idInStr.append(treeBaseInfo.getId().toString());
				idInStr.append("'");

				if (i != size - 1) {
					idInStr.append(",");
				}
			}
			idInStr.insert(0, "(");
			idInStr.append(")");

			sql = " SELECT DISTINCT bill.FID FROM {0} bill "
					+ " INNER JOIN  (SELECT FLongNumber FROM {0} bill WHERE bill.FID IN {1} {2}) sub "
					+ " ON CHARINDEX(bill.FLongNumber || '!', sub.FLongNumber || '!') = 1 " + " {3} ";
			sql = sql.replaceAll("\\{0\\}", dataTableInfo.getName());
			sql = sql.replaceFirst("\\{1\\}", idInStr.toString());

			if (FdcStringUtil.isBlank(filterSql)) {
				sql = sql.replaceFirst("\\{2\\}", "");
				sql = sql.replaceFirst("\\{3\\}", "");
			} else {
				sql = sql.replaceFirst("\\{2\\}", " AND " + filterSql);
				sql = sql.replaceFirst("\\{3\\}", " WHERE " + filterSql);
			}

			// /////////////////////////////////////////////////////////////
		}

		return sql;
	}

	/**
	 * 级联查找所有父类id（包括自己）
	 * 
	 * @param ctx
	 *            应用上下文；可以为空
	 * @param objectCollection
	 *            对象集合；不能为空；装载TreeBaseInfo类型对象
	 * @param filterSql
	 *            过滤器SQL；可以为空；不能加别名
	 * @return
	 * @throws BOSException
	 */
	public static Set findParentIdWithSelfCascade(Context ctx, IObjectCollection objectCollection, String filterSql) throws BOSException {
		Set parentIdSet = new LinkedHashSet();

		String sql = generateFindParentIdWithSelfCascadeSql(ctx, objectCollection, filterSql);
		if (FdcStringUtil.isNotBlank(sql)) {
			IFDCSQLFacade eCSQLFacade = null;
			if (null == ctx) {
				eCSQLFacade = FDCSQLFacadeFactory.getRemoteInstance();
			} else {
				eCSQLFacade = FDCSQLFacadeFactory.getLocalInstance(ctx);
			}

			IRowSet rs = null;
			String tempId = null;
			try {
				rs = eCSQLFacade.executeQuery(sql, null);
				while (rs.next()) {
					tempId = rs.getString("FID");
					parentIdSet.add(tempId);
				}
			} catch (SQLException e) {
				throw new BOSException(e);
			} finally {
				// 关闭连接
				SQLUtils.cleanup(rs);
			}
		}

		return parentIdSet;
	}

	/**
	 * 取得 包含合计字段树形结构 对象集合
	 * 
	 * @param ctx
	 *            应用上下文；可以为空
	 * @param objectValue
	 *            TreeBaseInfo对象；非空
	 * @param projectId
	 *            工程项目ID；非空
	 * @param propertyNameArr
	 *            属性名称数组；非空
	 * @param filterSql
	 *            过滤器SQL；可以为空；不能加别名
	 * @return
	 * @throws Exception
	 */
	public static TreeBaseCollection getTreeObjectCollectionWithSumField(Context ctx, TreeBaseInfo objectValue, String projectId,
			String[] propertyNameArr, String filterSql) throws Exception {
		TreeBaseCollection objectCollection = null;

		if (null != objectValue) {
			// /////////////////////////////////////////////////////////////

			String msg = objectValue + " 类型为 " + objectValue.getClass() + "，不是继承自 " + TreeBaseInfo.class;
			Assert.assertTrue(msg, objectValue instanceof TreeBaseInfo);

			BOSObjectType bosType = objectValue.getBOSType();
			EntityObjectInfo entityObjectInfo = MetaDataLoader.getEntity(ctx, bosType);
			DataTableInfo dataTableInfo = entityObjectInfo.getTable();
			msg = bosType + " 实体为 " + entityObjectInfo.getName() + "，没有对应的数据库表!";
			Assert.assertNotNull(msg, dataTableInfo);
			String dataTableName = dataTableInfo.getName();

			// 取得实体的所有自有属性以及继承来的属性, 并去除重复的属性(用属性名称判断)
			PropertyCollection propertyCollection = entityObjectInfo.getInheritedNoDuplicatedPropertiesRuntime();

			// /////////////////////////////////////////////////////////////

			ITreeBase iTreeBase = getInstance(ctx, bosType.toString());
			Map filerMap = new HashMap();
			filerMap.put("curProject.id", projectId);
			filerMap.put("isLeaf", Boolean.FALSE);
			FilterInfo filter = FdcEntityViewUtil.getFilter(filerMap);
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			objectCollection = iTreeBase.getTreeBaseCollection(view);

			if (FdcObjectCollectionUtil.isEmpty(objectCollection)) {
				return objectCollection;
			}
			// 解析对象集合的字符串类型的ID映射
			Map stringIdMap = FdcObjectCollectionUtil.parseUniqueStringIdMap(objectCollection);

			// /////////////////////////////////////////////////////////////

			List propertyNameList = new ArrayList(Arrays.asList(propertyNameArr));
			FdcCollectionUtil.clearDuplicateAndNull(propertyNameList);

			StringBuffer sumFieldStr = new StringBuffer();
			PropertyInfo propertyInfo = null;
			ColumnInfo columnInfo = null;
			String propertyName = null;
			String columnName = null;
			Map propertyColumnMap = new LinkedHashMap();
			Map columnPropertyMap = new LinkedHashMap();
			for (int i = 0, size = propertyCollection.size(); i < size; i++) {
				propertyInfo = (PropertyInfo) propertyCollection.getObject(i);
				propertyName = propertyInfo.getName();
				columnInfo = propertyInfo.getMappingField();
				if (null == columnInfo) {
					propertyColumnMap.put(propertyName, null);
					continue;
				}
				columnName = columnInfo.getName();
				propertyColumnMap.put(propertyName, columnName);
				columnPropertyMap.put(columnName, propertyName);
			}

			// /////////////////////////////////////////////////////////////

			String sql = generateFindTreeSumFieldSql(dataTableName, propertyColumnMap, columnPropertyMap, projectId, propertyNameList,
					filterSql);

			IFDCSQLFacade iFDCSQLFacade = null;
			if (ctx != null) {
				iFDCSQLFacade = FDCSQLFacadeFactory.getLocalInstance(ctx);
			} else {
				iFDCSQLFacade = FDCSQLFacadeFactory.getRemoteInstance();
			}

			IRowSet rs = null;
			int size = propertyNameList.size();
			try {
				rs = iFDCSQLFacade.executeQuery(sql, null);
				while (rs.next()) {
					String fID = rs.getString("FID");
					Object fx = null;
					CoreBaseInfo coreBaseInfo = (CoreBaseInfo) stringIdMap.get(fID);
					if (null == coreBaseInfo) {
						continue;
					}

					for (int i = 0; i < size; i++) {
						propertyName = (String) propertyNameList.get(i);
						columnName = (String) propertyColumnMap.get(propertyName);

						fx = rs.getObject(columnName);
						coreBaseInfo.put(propertyName, fx);
					}
				}
			} finally {
				// 关闭连接
				SQLUtils.cleanup(rs);
			}

			// /////////////////////////////////////////////////////////////
		}

		return objectCollection;
	}

	/**
	 * 生成 查找树形结构合计字段的 SQL
	 * 
	 * @param dataTableName
	 *            数据库表名；非空
	 * @param propertyColumnMap
	 *            属性名列名映射；非空
	 * @param columnPropertyMap
	 *            列名属性名映射；非空
	 * @param projectId
	 *            工程项目ID；非空
	 * @param propertyNameList
	 *            属性名数组；非空
	 * @param filterSql
	 *            过滤器SQL；可以为空；不能加别名
	 * @return
	 * @throws BOSException
	 */
	private static String generateFindTreeSumFieldSql(String dataTableName, Map propertyColumnMap, Map columnPropertyMap, String projectId,
			List propertyNameList, String filterSql) throws BOSException {
		String sql = null;

		// /////////////////////////////////////////////////////////////

		Assert.assertEquals("数据库表名 dataTableName 不能为空", true, FdcStringUtil.isNotBlank(dataTableName));
		Assert.assertEquals("属性名列名映射 propertyColumnMap 不能为空", true, FdcMapUtil.isNotEmpty(propertyColumnMap));
		Assert.assertEquals("列名属性名映射 columnPropertyMap 不能为空", true, FdcMapUtil.isNotEmpty(columnPropertyMap));
		Assert.assertEquals("工程项目ID projectId 不能为空", true, FdcStringUtil.isNotBlank(projectId));
		Assert.assertEquals("属性名数组 propertyNameList 不能为空", true, FdcCollectionUtil.isNotEmpty(propertyNameList));

		StringBuffer sumFieldStr = new StringBuffer();
		PropertyInfo propertyInfo = null;
		ColumnInfo columnInfo = null;
		String propertyName = null;
		String columnName = null;

		Set propertySet = propertyColumnMap.keySet();
		Set noPropertySet = new LinkedHashSet(propertyNameList);
		noPropertySet.removeAll(propertySet);
		String msg = null;
		if (FdcCollectionUtil.isNotEmpty(noPropertySet)) {
			msg = "属性 " + noPropertySet + " 不存在 ";
		}
		Assert.assertNull(msg, msg);
		msg = null;
		if (!propertySet.contains("project")) {
			msg = " 属性 project 不存在 ";
		}
		Assert.assertNull(msg, msg);
		msg = null;
		if (null == propertyColumnMap.get("project")) {
			msg = " 属性 project对应的字段 不存在 ";
		}
		Assert.assertNull(msg, msg);

		for (int i = 0, size = propertyNameList.size(); i < size; i++) {
			propertyName = (String) propertyNameList.get(i);
			columnName = (String) propertyColumnMap.get(propertyName);

			sumFieldStr.append("SUM(ISNULL(sub.");
			sumFieldStr.append(columnName);
			sumFieldStr.append(", 0)) ");
			sumFieldStr.append(columnName);

			if (i != size - 1) {
				sumFieldStr.append(",	\r\n");
				sumFieldStr.append("	       ");
			}
		}

		StringBuffer sqlSB = new StringBuffer();

		sqlSB.append("	SELECT bill.FID,	\r\n");
		sqlSB.append("	       bill.FLongNumber,	\r\n");
		sqlSB.append("	       {SumField}	\r\n");
		sqlSB.append("	  FROM {Table} bill	\r\n");
		sqlSB.append("	  LEFT JOIN {Table} sub	\r\n");
		sqlSB.append("	    ON CHARINDEX(bill.FLongNumber || '!', sub.FLongNumber || '!') = 1	\r\n");
		sqlSB.append("	   AND bill.FCurProject = sub.FCurProject	\r\n");
		sqlSB.append("	   AND sub.FIsLeaf = 1	\r\n");
		sqlSB.append("	 WHERE bill.FCurProject = '{FCurProject}' AND bill.FIsLeaf = 0 {FilterSql}	\r\n");
		sqlSB.append("	 GROUP BY bill.FID, bill.FLongNumber	\r\n");
		sqlSB.append("	 ORDER BY bill.FLongNumber	\r\n");

		sql = sqlSB.toString();
		sql = sql.replaceFirst("\\{SumField\\}", sumFieldStr.toString());
		sql = sql.replaceAll("\\{Table\\}", dataTableName);
		sql = sql.replaceFirst("\\{FCurProject\\}", projectId);

		if (FdcStringUtil.isNotBlank(filterSql)) {
			sql = sql.replaceFirst("\\{FilterSql\\}", " AND bill." + filterSql);
		} else {
			sql = sql.replaceFirst("\\{FilterSql\\}", "");
		}

		// /////////////////////////////////////////////////////////////

		return sql;
	}

	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////

	/**
	 * 获得实例
	 * 
	 * @param ctx
	 *            应用上下文
	 * @param bosTypeStr
	 *            BOS对象类型字符串
	 * @return
	 * @throws Exception
	 */
	public static ITreeBase getInstance(Context ctx, String bosTypeStr) throws Exception {
		ITreeBase iTreeBase = null;

		BOSObjectType bosType = BOSObjectType.create(bosTypeStr);
		if (ctx != null) {
			iTreeBase = (ITreeBase) BOSObjectFactory.createBOSObject(ctx, bosType);
		} else {
			EntityObjectInfo entityObjectInfo = MetaDataLoader.getEntity(ctx, bosType);
			String businessInterfaceClassName = entityObjectInfo.getBusinessInterface();
			iTreeBase = (ITreeBase) BOSObjectFactory.createRemoteBOSObject(bosType, Class.forName(businessInterfaceClassName));
		}

		return iTreeBase;
	}

	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////

	/**
	 * 根据ID取得TreeBase
	 * 
	 * @param iTreeBase
	 *            TreeBase接口
	 * @param id
	 *            ID字符串
	 * @return
	 * @throws Exception
	 */
	public static TreeBaseInfo findTreeBaseInfo(ITreeBase iTreeBase, String idStr) throws Exception {
		TreeBaseInfo info = null;

		if (null == iTreeBase || FdcStringUtil.isBlank(idStr)) {
			return null;
		}
		BOSUuid id = BOSUuid.read(idStr);

		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = getSelector();
		view.setSelector(sic);

		IObjectPK pk = new ObjectUuidPK(id);

		info = iTreeBase.getTreeBaseInfo(pk, sic);

		return info;
	}

	/**
	 * 根据长编码取得TreeBase
	 * 
	 * @param iTreeBase
	 *            TreeBase接口
	 * @param bosTypeStr
	 *            BOS对象类型字符串
	 * @param projectId
	 *            工程项目ID
	 * @param longNumber
	 *            长编码
	 * @return
	 * @throws Exception
	 */
	public static TreeBaseInfo findTreeBaseInfoByLongNumber(ITreeBase iTreeBase, String bosTypeStr, String projectId, String longNumber)
			throws Exception {
		TreeBaseInfo info = null;

		if (null == iTreeBase || FdcStringUtil.isBlank(longNumber)) {
			return null;
		}

		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = getSelector();
		view.setSelector(sic);

		FilterInfo filter = new FilterInfo();
		FilterItemCollection fic = filter.getFilterItems();
		fic.add(new FilterItemInfo("longNumber", longNumber));
		fic.add(new FilterItemInfo("curProject.id", projectId));

		view.setFilter(filter);

		TreeBaseCollection cols = iTreeBase.getTreeBaseCollection(view);
		if (FdcObjectCollectionUtil.isNotEmpty(cols)) {
			info = (TreeBaseInfo) cols.get(0);
		}

		return info;
	}

	/**
	 * 根据唯一性编码取得TreeBase
	 * 
	 * @param iTreeBase
	 *            TreeBase接口
	 * @param bosTypeStr
	 *            BOS对象类型字符串
	 * @param projectId
	 *            工程项目ID
	 * @param uniqueNumber
	 *            唯一性编码
	 * @return
	 * @throws Exception
	 */
	public static TreeBaseInfo findTreeBaseInfoByUniqueNumber(ITreeBase iTreeBase, String bosTypeStr, String projectId, String uniqueNumber)
			throws Exception {
		TreeBaseInfo info = null;

		if (null == iTreeBase || FdcStringUtil.isBlank(uniqueNumber)) {
			return null;
		}

		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = getSelector();
		view.setSelector(sic);

		FilterInfo filter = new FilterInfo();
		FilterItemCollection fic = filter.getFilterItems();
		fic.add(new FilterItemInfo("number", uniqueNumber));
		fic.add(new FilterItemInfo("curProject.id", projectId));

		view.setFilter(filter);

		TreeBaseCollection cols = iTreeBase.getTreeBaseCollection(view);
		if (FdcObjectCollectionUtil.isNotEmpty(cols)) {
			if (cols.size() > 1) {
				FdcTreeBaseUtil.throwEASBizException("编码 {0} 不唯一", new String[] { uniqueNumber });
			} else {
				info = (TreeBaseInfo) cols.get(0);
			}
		}

		return info;
	}

	/**
	 * 级联查找所有父类TreeBase
	 * 
	 * @param iTreeBase
	 *            TreeBase接口
	 * @param bosTypeStr
	 *            BOS对象类型字符串
	 * @param projectId
	 *            工程项目ID
	 * @param cols
	 *            TreeBase集合
	 * @param isQueryBaseEntityView
	 *            是否只查询基本的实体视图
	 * @return
	 * @throws Exception
	 */
	public static IObjectCollection findParentTreeBaseInfo(ITreeBase iTreeBase, String bosTypeStr, String projectId,
			IObjectCollection cols, boolean isQueryBaseEntityView) throws Exception {
		IObjectCollection parentCols = null;

		if (null == iTreeBase || FdcObjectCollectionUtil.isEmpty(cols)) {
			return null;
		}

		// 生成 级联查找所有父类id（包括自己）的 SQL
		String filterSql = null;
		if (FdcStringUtil.isNotBlank(projectId)) {
			filterSql = " FCurProject = '" + projectId + "' ";
		}
		String idInSql = generateFindParentIdWithSelfCascadeSql(iTreeBase, cols, filterSql);

		// 取得查询实体视图
		EntityViewInfo view = null;
		if (isQueryBaseEntityView) {
			view = getBaseEntityViewInfo(idInSql);
		} else {
			view = getEntityViewInfo(idInSql);
		}
		// 取得集合
		parentCols = iTreeBase.getTreeBaseCollection(view);

		return parentCols;
	}

	/**
	 * 根据ID取得TreeBase
	 * 
	 * @param ctx
	 *            应用上下文
	 * @param id
	 *            ID字符串
	 * @return
	 * @throws Exception
	 */
	public static TreeBaseInfo findTreeBaseInfo(Context ctx, String idStr) throws Exception {
		TreeBaseInfo info = null;

		if (FdcStringUtil.isBlank(idStr)) {
			return null;
		}
		BOSUuid id = BOSUuid.read(idStr);
		String bosTypeStr = id.getType().toString();
		ITreeBase iTreeBase = getInstance(ctx, bosTypeStr);

		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = getSelector();
		view.setSelector(sic);

		IObjectPK pk = new ObjectUuidPK(id);

		info = iTreeBase.getTreeBaseInfo(pk, sic);

		return info;
	}

	/**
	 * 根据长编码取得TreeBase
	 * 
	 * @param ctx
	 *            应用上下文
	 * @param bosTypeStr
	 *            BOS对象类型字符串
	 * @param projectId
	 *            工程项目ID
	 * @param longNumber
	 *            长编码
	 * @return
	 * @throws Exception
	 */
	public static TreeBaseInfo findTreeBaseInfoByLongNumber(Context ctx, String bosTypeStr, String projectId, String longNumber)
			throws Exception {
		TreeBaseInfo info = null;

		if (FdcStringUtil.isBlank(longNumber)) {
			return null;
		}
		ITreeBase iTreeBase = getInstance(ctx, bosTypeStr);

		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = getSelector();
		view.setSelector(sic);

		FilterInfo filter = new FilterInfo();
		FilterItemCollection fic = filter.getFilterItems();
		fic.add(new FilterItemInfo("longNumber", longNumber));
		fic.add(new FilterItemInfo("curProject.id", projectId));

		view.setFilter(filter);

		TreeBaseCollection cols = iTreeBase.getTreeBaseCollection(view);
		if (FdcObjectCollectionUtil.isNotEmpty(cols)) {
			info = (TreeBaseInfo) cols.get(0);
		}

		return info;
	}

	/**
	 * 根据唯一性编码取得TreeBase
	 * 
	 * @param ctx
	 *            应用上下文
	 * @param bosTypeStr
	 *            BOS对象类型字符串
	 * @param projectId
	 *            工程项目ID
	 * @param uniqueNumber
	 *            唯一性编码
	 * @return
	 * @throws Exception
	 */
	public static TreeBaseInfo findTreeBaseInfoByUniqueNumber(Context ctx, String bosTypeStr, String projectId, String uniqueNumber)
			throws Exception {
		TreeBaseInfo info = null;

		if (FdcStringUtil.isBlank(uniqueNumber)) {
			return null;
		}
		ITreeBase iTreeBase = getInstance(ctx, bosTypeStr);

		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = getSelector();
		view.setSelector(sic);

		FilterInfo filter = new FilterInfo();
		FilterItemCollection fic = filter.getFilterItems();
		fic.add(new FilterItemInfo("number", uniqueNumber));
		fic.add(new FilterItemInfo("curProject.id", projectId));

		view.setFilter(filter);

		TreeBaseCollection cols = iTreeBase.getTreeBaseCollection(view);
		if (FdcObjectCollectionUtil.isNotEmpty(cols)) {
			if (cols.size() > 1) {
				FdcTreeBaseUtil.throwEASBizException("编码 {0} 不唯一", new String[] { uniqueNumber });
			} else {
				info = (TreeBaseInfo) cols.get(0);
			}
		}

		return info;
	}

	/**
	 * 级联查找所有父类TreeBase
	 * 
	 * @param ctx
	 *            应用上下文
	 * @param bosTypeStr
	 *            BOS对象类型字符串
	 * @param projectId
	 *            工程项目ID
	 * @param cols
	 *            TreeBase集合
	 * @param isQueryBaseEntityView
	 *            是否只查询基本的实体视图
	 * @return
	 * @throws Exception
	 */
	public static IObjectCollection findParentTreeBaseInfo(Context ctx, String bosTypeStr, String projectId, IObjectCollection cols,
			boolean isQueryBaseEntityView) throws Exception {
		IObjectCollection parentCols = null;

		if (FdcObjectCollectionUtil.isEmpty(cols)) {
			return null;
		}
		ITreeBase iTreeBase = getInstance(ctx, bosTypeStr);

		// 生成 级联查找所有父类id（包括自己）的 SQL
		String filterSql = null;
		if (FdcStringUtil.isNotBlank(projectId)) {
			filterSql = " FCurProject = '" + projectId + "' ";
		}
		String idInSql = generateFindParentIdWithSelfCascadeSql(ctx, cols, filterSql);

		// 取得查询实体视图
		EntityViewInfo view = null;
		if (isQueryBaseEntityView) {
			view = getBaseEntityViewInfo(idInSql);
		} else {
			view = getEntityViewInfo(idInSql);
		}
		// 取得集合
		parentCols = iTreeBase.getTreeBaseCollection(view);

		return parentCols;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 查找直接父类TreeBase集合
	 * 
	 * @param iTreeBase
	 *            TreeBase接口
	 * @param bosTypeStr
	 *            BOS对象类型字符串
	 * @param projectId
	 *            工程项目ID
	 * @param cols
	 *            TreeBase集合
	 * @param isQueryBaseEntityView
	 *            是否只查询基本的实体视图
	 * @return
	 * @throws Exception
	 */
	public static IObjectCollection findDirectParentTreeBaseCollection(ITreeBase iTreeBase, String bosTypeStr,
			String projectId, IObjectCollection cols, boolean isQueryBaseEntityView) throws Exception {
		IObjectCollection parentCols = null;

		if (null == iTreeBase || FdcObjectCollectionUtil.isEmpty(cols)) {
			return null;
		}

		// 解析对象集合的属性映射
		Map parentIdMap = FdcObjectCollectionUtil.parsePropertyMap(cols, "parent.id");
		if (FdcMapUtil.isEmpty(parentIdMap)) {
			return null;
		}

		Collection parentIdCols = parentIdMap.values();
		Object[] parentIdArray = parentIdCols.toArray();
		// 解析数组的字符串类型的ID列表
		List stringParentIdList = FdcObjectCollectionUtil.parseStringIdList(parentIdArray);

		// 取得查询实体视图
		EntityViewInfo view = null;
		if (isQueryBaseEntityView) {
			view = getBaseEntityViewInfo(stringParentIdList);
		} else {
			view = getEntityViewInfo(stringParentIdList);
		}

		if (FdcStringUtil.isNotBlank(projectId)) {
			view.getFilter().appendFilterItem("curProject.id", projectId);
		}

		// 取得集合
		parentCols = iTreeBase.getTreeBaseCollection(view);

		return parentCols;
	}

	/**
	 * 查找直接父类TreeBase集合
	 * 
	 * @param ctx
	 *            应用上下文
	 * @param bosTypeStr
	 *            BOS对象类型字符串
	 * @param projectId
	 *            工程项目ID
	 * @param cols
	 *            TreeBase集合
	 * @param isQueryBaseEntityView
	 *            是否只查询基本的实体视图
	 * @return
	 * @throws Exception
	 */
	public static IObjectCollection findDirectParentTreeBaseCollection(Context ctx, String bosTypeStr,
			String projectId, IObjectCollection cols, boolean isQueryBaseEntityView) throws Exception {
		IObjectCollection parentCols = null;

		ITreeBase iTreeBase = getInstance(ctx, bosTypeStr);
		// 查找直接父类TreeBase集合
		parentCols = FdcTreeBaseUtil.findDirectParentTreeBaseCollection(iTreeBase, bosTypeStr, projectId, parentCols,
				isQueryBaseEntityView);

		return parentCols;
	}

	/**
	 * 取得直接父类TreeBase
	 * 
	 * @param iTreeBase
	 *            TreeBase接口
	 * @param bosTypeStr
	 *            BOS对象类型字符串
	 * @param projectId
	 *            工程项目ID
	 * @param treeBaseInfo
	 *            TreeBase对象
	 * @param isQueryBaseEntityView
	 *            是否只查询基本的实体视图
	 * @return
	 * @throws Exception
	 */
	public static TreeBaseInfo getDirectParentTreeBaseInfo(ITreeBase iTreeBase, String bosTypeStr, String projectId,
			TreeBaseInfo treeBaseInfo, boolean isQueryBaseEntityView) throws Exception {
		TreeBaseInfo parentInfo = null;

		if (null == iTreeBase || null == treeBaseInfo) {
			return null;
		}

		// 取得父类对象ID
		Object parentId = FdcObjectValueUtil.get(treeBaseInfo, "parent.id");
		String stringParentId = null;

		if (null != parentId) {
			stringParentId = parentId.toString();
		} else {
			// 取得ID
			Object id = FdcObjectValueUtil.get(treeBaseInfo, "id");
			String stringId = id.toString();
			IObjectPK idPk = new ObjectStringPK(stringId);

			// 取得基本查询字段集合,包括父类
			SelectorItemCollection sic = FdcTreeBaseUtil.getBaseSelector();
			TreeBaseInfo info = iTreeBase.getTreeBaseInfo(idPk, sic);

			parentId = FdcObjectValueUtil.get(info, "parent.id");
			if (null != parentId) {
				stringParentId = parentId.toString();
			}
		}

		if (FdcStringUtil.isBlank(stringParentId)) {
			return null;
		}

		IObjectPK parentIdPk = new ObjectStringPK(stringParentId);

		// 取得查询字段集合
		SelectorItemCollection sic = null;
		if (isQueryBaseEntityView) {
			sic = FdcTreeBaseUtil.getBaseSelector();
		} else {
			sic = FdcTreeBaseUtil.getSelector();
		}

		parentInfo = iTreeBase.getTreeBaseInfo(parentIdPk, sic);

		return parentInfo;
	}

	/**
	 * 取得直接父类TreeBase
	 * 
	 * @param ctx
	 *            应用上下文
	 * @param bosTypeStr
	 *            BOS对象类型字符串
	 * @param projectId
	 *            工程项目ID
	 * @param treeBaseInfo
	 *            TreeBase对象
	 * @param isQueryBaseEntityView
	 *            是否只查询基本的实体视图
	 * @return
	 * @throws Exception
	 */
	public static TreeBaseInfo getDirectParentTreeBaseInfo(Context ctx, String bosTypeStr, String projectId,
			TreeBaseInfo treeBaseInfo, boolean isQueryBaseEntityView) throws Exception {
		TreeBaseInfo parentInfo = null;

		ITreeBase iTreeBase = getInstance(ctx, bosTypeStr);
		// 取得直接父类TreeBase
		parentInfo = FdcTreeBaseUtil.getDirectParentTreeBaseInfo(iTreeBase, bosTypeStr, projectId, treeBaseInfo,
				isQueryBaseEntityView);

		return parentInfo;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 取得查询实体视图
	 * 
	 * @param filter
	 *            过滤器
	 * @return
	 */
	public static EntityViewInfo getEntityViewInfo(FilterInfo filter) {
		EntityViewInfo view = new EntityViewInfo();

		SelectorItemCollection sic = getSelector();
		view.setSelector(sic);

		view.setFilter(filter);

		view.getSorter().add(new SorterItemInfo("longNumber"));

		return view;
	}

	/**
	 * 取得查询实体视图
	 * 
	 * @param idInSql
	 *            ID区间SQL
	 * @return
	 */
	public static EntityViewInfo getEntityViewInfo(String idInSql) {
		EntityViewInfo view = null;

		FilterInfo filter = new FilterInfo();
		FilterItemCollection fic = filter.getFilterItems();
		fic.add(new FilterItemInfo("id", idInSql, CompareType.INNER));

		view = getEntityViewInfo(filter);

		return view;
	}

	/**
	 * 取得查询实体视图
	 * 
	 * @param idCols
	 *            ID集合
	 * @return
	 */
	public static EntityViewInfo getEntityViewInfo(Collection idCols) {
		EntityViewInfo view = null;

		FilterInfo filter = new FilterInfo();
		FilterItemCollection fic = filter.getFilterItems();

		Set idSet = new LinkedHashSet(idCols);
		if (1 == idSet.size()) {
			fic.add(new FilterItemInfo("id", idSet.iterator().next(), CompareType.EQUALS));
		} else {
			fic.add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		}

		view = getEntityViewInfo(filter);

		return view;
	}

	/**
	 * 取得查询字段集合
	 * 
	 * @return
	 */
	public static SelectorItemCollection getSelector() {
		SelectorItemCollection sic = new SelectorItemCollection();

		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("parent.*"));

		return sic;
	}

	/**
	 * 取得基本查询实体视图
	 * 
	 * @param filter
	 *            过滤器
	 * @return
	 */
	public static EntityViewInfo getBaseEntityViewInfo(FilterInfo filter) {
		EntityViewInfo view = new EntityViewInfo();

		SelectorItemCollection sic = getBaseSelector();
		view.setSelector(sic);

		view.setFilter(filter);

		view.getSorter().add(new SorterItemInfo("longNumber"));

		return view;
	}

	/**
	 * 取得基本查询实体视图
	 * 
	 * @param idInSql
	 *            ID区间SQL
	 * @return
	 */
	public static EntityViewInfo getBaseEntityViewInfo(String idInSql) {
		EntityViewInfo view = null;

		FilterInfo filter = new FilterInfo();
		FilterItemCollection fic = filter.getFilterItems();
		fic.add(new FilterItemInfo("id", idInSql, CompareType.INNER));

		view = getBaseEntityViewInfo(filter);

		return view;
	}

	/**
	 * 取得基本查询实体视图
	 * 
	 * @param idCols
	 *            ID集合
	 * @return
	 */
	public static EntityViewInfo getBaseEntityViewInfo(Collection idCols) {
		EntityViewInfo view = null;

		FilterInfo filter = new FilterInfo();
		FilterItemCollection fic = filter.getFilterItems();

		Set idSet = new LinkedHashSet(idCols);
		if (1 == idSet.size()) {
			fic.add(new FilterItemInfo("id", idSet.iterator().next(), CompareType.EQUALS));
		} else {
			fic.add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		}

		view = getBaseEntityViewInfo(filter);

		return view;
	}

	/**
	 * 取得基本查询字段集合
	 * 
	 * @return
	 */
	public static SelectorItemCollection getBaseSelector() {
		SelectorItemCollection sic = new SelectorItemCollection();

		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("longNumber"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("displayName"));
		sic.add(new SelectorItemInfo("level"));
		sic.add(new SelectorItemInfo("isLeaf"));
		sic.add(new SelectorItemInfo("parent.id"));
		sic.add(new SelectorItemInfo("parent.number"));
		sic.add(new SelectorItemInfo("parent.longNumber"));
		sic.add(new SelectorItemInfo("parent.name"));
		sic.add(new SelectorItemInfo("parent.displayName"));
		sic.add(new SelectorItemInfo("parent.level"));
		sic.add(new SelectorItemInfo("parent.isLeaf"));

		return sic;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 抛出EAS业务异常
	 * 
	 * @param msg
	 *            消息
	 * @throws EASBizException
	 *             EAS业务异常
	 */
	public static void throwEASBizException(String msg) throws EASBizException {
		// 虚拟异常号
		String subCode = "EC_00001";
		throw new EASBizException(new NumericExceptionSubItem(subCode, msg));
	}

	/**
	 * 抛出EAS业务异常
	 * 
	 * @param msg
	 *            消息
	 * @param params
	 *            参数
	 * @throws EASBizException
	 *             EAS业务异常
	 */
	public static void throwEASBizException(String msg, Object[] params) throws EASBizException {
		// 虚拟异常号
		String subCode = "EC_00001";
		throw new EASBizException(new NumericExceptionSubItem(subCode, msg), params);
	}
	
	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 描述：生成 级联查找所有父类（包括自己）的 SQL
	 * 
	 * @param iTreeBase
	 *            TreeBase接口 对象集合；不能为空；装载TreeBaseInfo类型对象
	 * @param treeBaseInfo
	 *            TreeBaseInfo类型对象；不能为空
	 * @param filterSql
	 *            过滤器SQL；可以为空；不能加别名
	 * @param isQueryBaseEntityView
	 *            是否只查询基本的实体视图
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-13
	 */
	public static String generateFindParentTreeBaseInfoSql(ITreeBase iTreeBase, String filterSql,
			TreeBaseInfo treeBaseInfo, boolean isQueryBaseEntityView) {
		String sql = null;

		// 生成 级联查找所有父类id（包括自己）的 SQL
		// /////////////////////////////////////////////////////////////

		String msg = "入口参数 treeBaseInfo 不能为空";
		Assert.assertNotNull(msg, treeBaseInfo);

		BOSObjectType bosType = treeBaseInfo.getBOSType();
		EntityObjectInfo entityObjectInfo = MetaDataLoader.getEntity(iTreeBase.getContext(), bosType);
		DataTableInfo dataTableInfo = entityObjectInfo.getTable();
		msg = treeBaseInfo + " 实体为 " + entityObjectInfo.getName() + "，没有对应的数据库表!";
		Assert.assertNotNull(msg, dataTableInfo);

		// /////////////////////////////////////////////////////////////

		StringBuffer sqlSb = new StringBuffer();

		if (isQueryBaseEntityView) {
			sqlSb.append("	SELECT t0.FID             FID,	\r\n");
			sqlSb.append("	       t0.FName_L2        FName_L2,	\r\n");
			sqlSb.append("	       t0.FNumber         FNumber,	\r\n");
			sqlSb.append("	       t0.FLongNumber     FLongNumber,	\r\n");
			sqlSb.append("	       t0.FLevel          FLevel,	\r\n");
			sqlSb.append("	       t0.FIsLeaf         FIsLeaf,	\r\n");
			sqlSb.append("	       t1.FID             FParentID,	\r\n");
			sqlSb.append("	       t1.FName_L2        FParentName_L2,	\r\n");
			sqlSb.append("	       t1.FNumber         FParentNumber,	\r\n");
			sqlSb.append("	       t1.FLongNumber     FParentLongNumber,	\r\n");
			sqlSb.append("	       t1.FLevel          FParentLevel,	\r\n");
			sqlSb.append("	       t1.FIsLeaf         FParentIsLeaf	\r\n");
		} else {
			List columnNameList = FdcMetaDataUtil.getColumnNameList(iTreeBase.getContext(), bosType.toString(), true);

			sqlSb.append("	SELECT	\r\n");
			for (int i = 0, size = columnNameList.size(); i < size; i++) {
				String columnName = (String) columnNameList.get(i);

				sqlSb.append("	       t0.").append(columnName).append(",		\r\n");
				sqlSb.append("	       t1.").append(columnName).append(" parent" + columnName);
				if (i < size - 1) {
					sqlSb.append(",		\r\n");
				} else {
					sqlSb.append("		\r\n");
				}
			}
		}
		sqlSb.append("	  FROM {0} t0	\r\n");
		sqlSb.append("	  LEFT OUTER JOIN T_FDC_COSTACCOUNT t1	\r\n");
		sqlSb.append("	    ON t0.FPARENTID = T1.FID	\r\n");
		sqlSb.append("	 WHERE EXISTS	\r\n");
		sqlSb.append("	       (SELECT bill.FID	\r\n");
		sqlSb.append("	          FROM {0} bill	\r\n");
		sqlSb.append("	         INNER JOIN (SELECT FLongNumber	\r\n");
		sqlSb.append("	                      FROM {0} bill	\r\n");
		sqlSb.append("	                     WHERE 1 = 1 {1} \r\n");
		sqlSb.append("	                       AND bill.FCurProject = ?) sub	\r\n");
		sqlSb.append("	            ON CHARINDEX((bill.FLongNumber || '!'), (sub.FLongNumber || '!')) = 1	\r\n");
		sqlSb.append("	         WHERE bill.FID = t0.FID AND bill.FCurProject = ?)	\r\n");
		sqlSb.append("	 ORDER BY t0.FLONGNUMBER ASC	\r\n");

		sql = sqlSb.toString();
		sql = sql.replaceAll("\\{0\\}", dataTableInfo.getName());
		if (FdcStringUtil.isBlank(filterSql)) {
			sql = sql.replaceFirst("\\{1\\}", "");
		} else {
			sql = sql.replaceFirst("\\{1\\}", " AND " + filterSql);
		}

		return sql;
	}

	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
}
