package com.kingdee.eas.fdc.basedata.scheme.app;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSLocaleUtil;
import com.kingdee.bos.Context;
import com.kingdee.bos.SQLDataException;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.data.ColumnInfo;
import com.kingdee.bos.metadata.data.DataTableInfo;
import com.kingdee.bos.metadata.entity.EntityObjectCollection;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.LinkPropertyInfo;
import com.kingdee.bos.metadata.entity.PropertyCollection;
import com.kingdee.bos.metadata.entity.PropertyInfo;
import com.kingdee.bos.metadata.entity.RelationshipInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.scheme.FdcColumnCollection;
import com.kingdee.eas.fdc.basedata.scheme.FdcColumnInfo;
import com.kingdee.eas.fdc.basedata.scheme.FdcDataTableCollection;
import com.kingdee.eas.fdc.basedata.scheme.FdcDataTableFactory;
import com.kingdee.eas.fdc.basedata.scheme.FdcDataTableInfo;
import com.kingdee.eas.fdc.basedata.scheme.FdcEntityObjectCollection;
import com.kingdee.eas.fdc.basedata.scheme.FdcEntityObjectFactory;
import com.kingdee.eas.fdc.basedata.scheme.FdcEntityObjectInfo;
import com.kingdee.eas.fdc.basedata.scheme.FdcPropertyCollection;
import com.kingdee.eas.fdc.basedata.scheme.FdcPropertyInfo;
import com.kingdee.eas.fdc.basedata.util.FdcORMappingDAOUtil;
import com.kingdee.eas.fdc.basedata.util.FdcObjectCollectionUtil;
import com.kingdee.eas.fdc.basedata.util.FdcStringUtil;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.app.DbUtil;

public class FdcEntityObjectControllerBean extends AbstractFdcEntityObjectControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.basedata.scheme.app.FdcEntityObjectControllerBean");

	// 同步元数据
	protected FdcEntityObjectCollection _synchronizeMD(Context ctx, String fullNamePerfix, boolean isPackage)
			throws BOSException, EASBizException {
		FdcEntityObjectCollection fdcEntityObjectCollection = new FdcEntityObjectCollection();
		FdcDataTableCollection fdcDataTableCollection = new FdcDataTableCollection();

		long startTime = -1;
		long endTime = -1;
		double exeTime = -1;

		// //////////////////////////////////////////////////////////////

		// 解析到对象集合
		startTime = System.currentTimeMillis();
		parseIntoObjectCollection(ctx, fullNamePerfix, isPackage, fdcEntityObjectCollection, fdcDataTableCollection);
		endTime = System.currentTimeMillis();
		exeTime = (endTime - startTime) * 1.0 / 1000;
		logger.info("FdcEntityObjectControllerBean.parseIntoObjectCollection, exeTime: " + exeTime + " m");

		// 根据过滤器删除
		startTime = System.currentTimeMillis();
		deleteByFilter(ctx, fullNamePerfix, isPackage);
		endTime = System.currentTimeMillis();
		exeTime = (endTime - startTime) * 1.0 / 1000;
		logger.info("FdcEntityObjectControllerBean.deleteByFilter, exeTime: " + exeTime + " m");

		// 批量保存
		startTime = System.currentTimeMillis();
		saveBatchData(ctx, fdcEntityObjectCollection, fdcDataTableCollection);
		endTime = System.currentTimeMillis();
		exeTime = (endTime - startTime) * 1.0 / 1000;
		logger.info("FdcEntityObjectControllerBean.saveBatchData, exeTime: " + exeTime + " m");

		// 更新外键
		startTime = System.currentTimeMillis();
		updateForeignkey(ctx);
		endTime = System.currentTimeMillis();
		exeTime = (endTime - startTime) * 1.0 / 1000;
		logger.info("FdcEntityObjectControllerBean.updateForeignkey, exeTime: " + exeTime + " m");

		// 清除别名
		startTime = System.currentTimeMillis();
		clearAlias(ctx);
		endTime = System.currentTimeMillis();
		exeTime = (endTime - startTime) * 1.0 / 1000;
		logger.info("FdcEntityObjectControllerBean.clearAlias, exeTime: " + exeTime + " m");

		// 更新数据库中是否存在标识
		startTime = System.currentTimeMillis();
		updateIsExist(ctx, fullNamePerfix, isPackage);
		endTime = System.currentTimeMillis();
		exeTime = (endTime - startTime) * 1.0 / 1000;
		logger.info("FdcEntityObjectControllerBean.updateIsExist, exeTime: " + exeTime + " m");

		// //////////////////////////////////////////////////////////////

		return fdcEntityObjectCollection;
	}

	/**
	 * 解析到对象集合
	 * 
	 * @param ctx
	 * @param fullNamePerfix
	 * @param isPackage
	 * @param fdcEntityObjectCollection
	 * @param fdcDataTableCollection
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void parseIntoObjectCollection(Context ctx, String fullNamePerfix, boolean isPackage,
			FdcEntityObjectCollection fdcEntityObjectCollection, FdcDataTableCollection fdcDataTableCollection)
			throws BOSException, EASBizException {
		// String fdcPackageName = "com.kingdee.eas.fdc";
		String defaultAlias = "{null}";

		// 解决方案定义的语言列表
		Locale[] localeArr = BOSLocaleUtil.getSupportedLocales();

		IMetaDataLoader metaDataLoader = MetaDataLoaderFactory.getLocalMetaDataLoader(ctx);
		EntityObjectCollection entityObjectCollection = metaDataLoader.getEntityCollection();

		FdcEntityObjectInfo fdcEntityObjectInfo = null;
		EntityObjectInfo entityObjectInfo = null;
		String packageName = null;
		String fullName = null;
		String entityName = null;
		String entityAlias = null;
		// String entityDescription = null;
		String bosTypeStr = null;
		String fullBosTypeStr = null;
		String baseEntityBosTypeStr = null;
		String baseEntityFullBosTypeStr = null;

		FdcDataTableInfo fdcDataTableInfo = null;
		DataTableInfo dataTableInfo = null;
		String dataTableName = null;
		String dataTableAlias = null;
		// String dataTableDescription = null;

		FdcPropertyCollection fdcPropertyCollection = null;
		FdcPropertyInfo fdcPropertyInfo = null;
		PropertyInfo propertyInfo = null;
		LinkPropertyInfo linkPropertyInfo = null;
		RelationshipInfo relationshipInfo = null;
		String mappingFieldName = null;
		String mappingFieldUrlName = null;
		String relationshipUrlName = null;
		String propertyName = null;
		String propertyAlias = null;
		// String propertyDescription = null;

		PropertyCollection propertyCollection = null;
		Hashtable childEntities = null;

		FdcColumnCollection fdcColumnCollection = null;
		FdcColumnInfo fdcColumnInfo = null;
		EntityObjectInfo linkEntityObjectInfo = null;
		String linkEntityBosTypeStr = null;
		ColumnInfo columnInfo = null;
		String columnName = null;
		String columnAlias = null;
		// String columnDescription = null;
		boolean columnUserDefined = false;
		String sqlTypeAlias = null;
		int columnLength = 0;
		int columnPrecision = 0;
		int columnScale = 0;
		boolean isNullable = false;
		String defaultValue = null;
		boolean isMultilingual = false;

		Map fdcDataTableMap = new HashMap();
		Map fdcColumnMap = new HashMap();

		for (Iterator iterator = entityObjectCollection.iterator(); iterator.hasNext();) {
			entityObjectInfo = (EntityObjectInfo) iterator.next();

			fullName = entityObjectInfo.getFullName();
			boolean flag = (isPackage && fullName.startsWith(fullNamePerfix + ".")) || fullName.equals(fullNamePerfix);
			if (flag) {
				packageName = entityObjectInfo.getPackage();
				entityName = entityObjectInfo.getName();
				entityAlias = entityObjectInfo.getAlias();
				// 处理别名
				entityAlias = dealWithAlias(entityObjectInfo, entityAlias, defaultAlias);
				// entityDescription = entityObjectInfo.getDescription();
				// 注意：使用getType()，而不是getBOSType()
				bosTypeStr = entityObjectInfo.getType().toString();

				// 取得父级实体BosType字符串
				baseEntityBosTypeStr = getBaseEntityBosTypeStr(entityObjectInfo, false);
				// 递归取得父级实体BosType字符串
				baseEntityFullBosTypeStr = getBaseEntityBosTypeStr(entityObjectInfo, true);
				if (null != baseEntityFullBosTypeStr) {
					fullBosTypeStr = baseEntityFullBosTypeStr + "_" + bosTypeStr;
				} else {
					fullBosTypeStr = bosTypeStr;
				}

				dataTableInfo = entityObjectInfo.getTable();
				if (null != dataTableInfo) {
					// 取得实体的所有自有属性以及继承来的属性, 并去除重复的属性(用属性名称判断)
					propertyCollection = entityObjectInfo.getInheritedNoDuplicatedPropertiesRuntime();
					// 取得连接属性对应的实体
					childEntities = EntityObjectInfo.getChildEntities(entityObjectInfo);

					dataTableName = dataTableInfo.getName();
					dataTableAlias = dataTableInfo.getAlias();
					// 处理别名
					dataTableAlias = dealWithAlias(dataTableInfo, dataTableAlias, defaultAlias);
					// dataTableDescription = dataTableInfo.getDescription();
					fdcDataTableInfo = (FdcDataTableInfo) fdcDataTableMap.get(dataTableName);
					if (null == fdcDataTableInfo) {
						fdcDataTableInfo = new FdcDataTableInfo();
						fdcDataTableInfo.setPackageName(dataTableInfo.getPackage());
						fdcDataTableInfo.setName(dataTableName);
						fdcDataTableInfo.setFullName(dataTableInfo.getFullName());
						fdcDataTableInfo.setAlias(dataTableAlias);
						/**
						 * 多语言字段拷贝方法FdcObjectValueUtil.copyLocalValue，有BUG，暂时先屏蔽掉
						 */
						// 拷贝本地化属性值
						// FdcObjectValueUtil.copyLocalValue(dataTableInfo, fdcDataTableInfo,
						// "alias", localeArr);
						// fdcDataTableInfo.setDescription(dataTableDescription);
						// 拷贝本地化属性值
						// FdcObjectValueUtil.copyLocalValue(dataTableInfo, fdcDataTableInfo,
						// "description", localeArr);
						fdcDataTableInfo.setUserDefined(dataTableInfo.isUserDefined());
						fdcDataTableInfo.setPrimaryKey(null != dataTableInfo.getPrimaryKey() ? dataTableInfo
								.getPrimaryKey().getName() : null);

						fdcDataTableCollection.add(fdcDataTableInfo);
						fdcDataTableMap.put(dataTableName, fdcDataTableInfo);
					}

					fdcEntityObjectInfo = new FdcEntityObjectInfo();
					fdcEntityObjectCollection.add(fdcEntityObjectInfo);
					// 暂时不建立外键关联
					// fdcEntityObjectInfo.setDataTable(fdcDataTableInfo);
					fdcEntityObjectInfo.setPackageName(packageName);
					fdcEntityObjectInfo.setName(entityName);
					fdcEntityObjectInfo.setFullName(fullName);
					fdcEntityObjectInfo.setAlias(entityAlias);
					fdcDataTableInfo.setAlias(dataTableAlias);
					/**
					 * 多语言字段拷贝方法FdcObjectValueUtil.copyLocalValue，有BUG，暂时先屏蔽掉
					 */
					// 拷贝本地化属性值
					// FdcObjectValueUtil.copyLocalValue(entityObjectInfo, fdcEntityObjectInfo,
					// "alias", localeArr);
					// fdcEntityObjectInfo.setDescription(entityDescription);
					// 拷贝本地化属性值
					// FdcObjectValueUtil.copyLocalValue(entityObjectInfo, fdcEntityObjectInfo,
					// "description", localeArr);
					fdcEntityObjectInfo.setBosTypeStr(bosTypeStr);
					fdcEntityObjectInfo.setFullBosTypeStr(fullBosTypeStr);
					fdcEntityObjectInfo.setBaseEntity(baseEntityBosTypeStr);
					fdcEntityObjectInfo.setDataTableName(dataTableName);

					fdcPropertyCollection = fdcEntityObjectInfo.getEntries();
					fdcColumnCollection = fdcDataTableInfo.getEntries();

					for (Iterator iterator2 = propertyCollection.iterator(); iterator2.hasNext();) {
						propertyInfo = (PropertyInfo) iterator2.next();
						propertyName = propertyInfo.getName();
						propertyAlias = propertyInfo.getAlias();
						// 处理别名
						propertyAlias = dealWithAlias(propertyInfo, propertyAlias, defaultAlias);
						// propertyDescription = propertyInfo.getDescription();

						if (propertyInfo instanceof LinkPropertyInfo) {
							linkPropertyInfo = (LinkPropertyInfo) propertyInfo;

							linkEntityObjectInfo = (EntityObjectInfo) childEntities.get(linkPropertyInfo);
							if (null != linkEntityObjectInfo) {
								// 注意：使用getType()，而不是getBOSType()
								if (null != linkEntityObjectInfo.getType()) {
									linkEntityBosTypeStr = linkEntityObjectInfo.getType().toString();
								}
							}

							relationshipInfo = linkPropertyInfo.getRelationship();
							if (null != relationshipInfo) {
								// 注意：使用getType()，而不是getBOSType()
								relationshipUrlName = relationshipInfo.getFullName();
								// 加上关联双方的BOSType
								relationshipUrlName = bosTypeStr + "-" + propertyName + "-" + relationshipUrlName + "-"
										+ linkEntityBosTypeStr;
							}
						} else {
							linkEntityBosTypeStr = null;
							relationshipUrlName = null;
						}

						columnInfo = propertyInfo.getMappingField();
						if (null != columnInfo) {
							columnName = columnInfo.getName();
							columnAlias = columnInfo.getAlias();
							// 处理别名
							columnAlias = dealWithAlias(columnInfo, columnAlias, defaultAlias);
							// columnDescription = columnInfo.getDescription();
							columnUserDefined = columnInfo.isUserDefined();
							sqlTypeAlias = columnInfo.getTypeName().getAlias();
							columnLength = columnInfo.getLength();
							columnPrecision = columnInfo.getPrecision();
							columnScale = columnInfo.getScale();
							isNullable = columnInfo.isNullable();
							defaultValue = columnInfo.getDefaultValue();
							isMultilingual = columnInfo.isMultilingual();

							mappingFieldName = columnName;
							mappingFieldUrlName = fdcDataTableInfo.getName() + "-" + mappingFieldName;
							fdcColumnInfo = (FdcColumnInfo) fdcColumnMap.get(mappingFieldUrlName);
							if (null == fdcColumnInfo) {
								fdcColumnInfo = new FdcColumnInfo();
								fdcColumnInfo.setBill(fdcDataTableInfo);
								fdcColumnInfo.setName(columnName);
								fdcColumnInfo.setAlias(columnAlias);
								fdcDataTableInfo.setAlias(dataTableAlias);
								/**
								 * 多语言字段拷贝方法FdcObjectValueUtil.copyLocalValue，有BUG，暂时先屏蔽掉
								 */
								// 拷贝本地化属性值
								// FdcObjectValueUtil.copyLocalValue(columnInfo, fdcColumnInfo,
								// "alias", localeArr);
								// fdcColumnInfo.setDescription(columnDescription);
								// 拷贝本地化属性值
								// FdcObjectValueUtil.copyLocalValue(columnInfo, fdcColumnInfo,
								// "description", localeArr);
								fdcColumnInfo.setUserDefined(columnUserDefined);
								fdcColumnInfo.setSqlType(sqlTypeAlias);
								fdcColumnInfo.setLength(columnLength);
								fdcColumnInfo.setPrecision(columnPrecision);
								fdcColumnInfo.setScale(columnScale);
								fdcColumnInfo.setIsNullable(isNullable);
								fdcColumnInfo.setDefaultValue(defaultValue);
								fdcColumnInfo.setIsMultilingual(isMultilingual);
								
								// 实体对象完整名称(冗余字段)，对应entityObject.name
								fdcColumnInfo.setEntityObjectFullName(fullName);
								// 数据表名称(冗余字段)，对应dataTable.name
								fdcColumnInfo.setDataTableName(dataTableName);

								fdcColumnCollection.add(fdcColumnInfo);
								fdcColumnMap.put(mappingFieldUrlName, fdcColumnInfo);
							}
						} else {
							fdcColumnInfo = null;
						}

						fdcPropertyInfo = new FdcPropertyInfo();
						fdcPropertyInfo.setBill(fdcEntityObjectInfo);
						// 暂时不建立外键关联
						// fdcPropertyInfo.setMappingField(fdcColumnInfo);
						fdcPropertyInfo.setName(propertyName);
						fdcPropertyInfo.setAlias(propertyAlias);
						fdcDataTableInfo.setAlias(dataTableAlias);
						/**
						 * 多语言字段拷贝方法FdcObjectValueUtil.copyLocalValue，有BUG，暂时先屏蔽掉
						 */
						// 拷贝本地化属性值
						// FdcObjectValueUtil.copyLocalValue(propertyInfo, fdcPropertyInfo, "alias",
						// localeArr);
						// fdcPropertyInfo.setDescription(propertyDescription);
						// 拷贝本地化属性值
						// FdcObjectValueUtil.copyLocalValue(propertyInfo, fdcPropertyInfo,
						// "description", localeArr);
						
						// 实体对象完整名称(冗余字段)，对应entityObject.name
						fdcPropertyInfo.setEntityObjectFullName(fullName);
						// 数据表名称(冗余字段)，对应dataTable.name
						fdcPropertyInfo.setDataTableName(dataTableName);
						fdcPropertyInfo.setMappingFieldName(mappingFieldName);
						fdcPropertyInfo.setLinkPropertyBosTypeStr(linkEntityBosTypeStr);
						fdcPropertyInfo.setRelationshipUrlName(relationshipUrlName);

						fdcPropertyCollection.add(fdcPropertyInfo);
					}

				}
			}
		}
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 描述：处理别名
	 * <p>
	 * 1、如果别名为空，则设置成一个特殊的标识符，便于"房地产实体字典拓展"查询分析
	 * 
	 * @param entityObjectInfo
	 * @param entityAlias
	 * @param defaultAlias
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2015-5-22
	 */
	private String dealWithAlias(EntityObjectInfo entityObjectInfo, String entityAlias, String defaultAlias) {
		// “别名”等于“名称”的单据，在元数据中实际是没有“别名”值的，MetaDataLoader加载时，会默认取“名称”值。
		/**
		 * 所以：下面这一段实际上是不生效的
		 */
		if (FdcStringUtil.isBlank(entityAlias)) {
			entityAlias = defaultAlias;
			entityObjectInfo.setAlias(entityAlias);
		}

		return entityAlias;
	}

	/**
	 * 描述：处理别名
	 * <p>
	 * 1、如果别名为空，则设置成一个特殊的标识符，便于"房地产实体字典拓展"查询分析
	 * 
	 * @param dataTableInfo
	 * @param dataTableAlias
	 * @param defaultAlias
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2015-5-22
	 */
	private String dealWithAlias(DataTableInfo dataTableInfo, String dataTableAlias, String defaultAlias) {
		// “别名”等于“名称”的单据，在元数据中实际是没有“别名”值的，MetaDataLoader加载时，会默认取“名称”值。
		/**
		 * 所以：下面这一段实际上是不生效的
		 */
		if (FdcStringUtil.isBlank(dataTableAlias)) {
			dataTableAlias = defaultAlias;
			dataTableInfo.setAlias(dataTableAlias);
		}

		return dataTableAlias;
	}

	/**
	 * 描述：处理别名
	 * <p>
	 * 1、如果别名为空，则设置成一个特殊的标识符，便于"房地产实体字典拓展"查询分析
	 * 
	 * @param propertyInfo
	 * @param propertyAlias
	 * @param defaultAlias
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2015-5-22
	 */
	private String dealWithAlias(PropertyInfo propertyInfo, String propertyAlias, String defaultAlias) {
		// “别名”等于“名称”的单据，在元数据中实际是没有“别名”值的，MetaDataLoader加载时，会默认取“名称”值。
		/**
		 * 所以：下面这一段实际上是不生效的
		 */
		if (FdcStringUtil.isBlank(propertyAlias)) {
			propertyAlias = defaultAlias;
			propertyInfo.setAlias(propertyAlias);
		}

		return propertyAlias;
	}

	/**
	 * 描述：处理别名
	 * <p>
	 * 1、如果别名为空，则设置成一个特殊的标识符，便于"房地产实体字典拓展"查询分析
	 * 
	 * @param columnInfo
	 * @param columnAlias
	 * @param defaultAlias
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2015-5-22
	 */
	private String dealWithAlias(ColumnInfo columnInfo, String columnAlias, String defaultAlias) {
		// “别名”等于“名称”的单据，在元数据中实际是没有“别名”值的，MetaDataLoader加载时，会默认取“名称”值。
		/**
		 * 所以：下面这一段实际上是不生效的
		 */
		if (FdcStringUtil.isBlank(columnAlias)) {
			columnAlias = defaultAlias;
			columnInfo.setAlias(columnAlias);
		}

		return columnAlias;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 取得父级实体BosType字符串
	 * 
	 * @param entityObjectInfo
	 *            实体对象
	 * @param isFull
	 *            是否递归取得父级实体BosType字符串
	 * @return
	 */
	private String getBaseEntityBosTypeStr(EntityObjectInfo entityObjectInfo, boolean isFull) {
		String baseEntityBosTypeStr = null;

		EntityObjectInfo baseEntity = entityObjectInfo.getBaseEntity();
		if (null != baseEntity) {
			baseEntityBosTypeStr = baseEntity.getType().toString();

			// 递归取得父级实体BosType字符串
			if (isFull) {
				EntityObjectInfo baseBaseEntity = baseEntity.getBaseEntity();
				if (null != baseBaseEntity) {
					String baseBaseEntityBosTypeStr = getBaseEntityBosTypeStr(baseBaseEntity,
							isFull);
					if (null != baseBaseEntityBosTypeStr) {
						baseEntityBosTypeStr = baseBaseEntityBosTypeStr + "_"
								+ baseEntityBosTypeStr;
					}
				}
			}
		}

		return baseEntityBosTypeStr;
	}

	/**
	 * 取得父级实体BosType字符串
	 * 
	 * @param fdcEntityObjectMap
	 *            房地产实体Map
	 * @param fdcEntityObjectInfo
	 *            房地产实体对象
	 * @return
	 */
	private String getBaseEntityBosTypeStr(Map fdcEntityObjectMap,
			FdcEntityObjectInfo fdcEntityObjectInfo) {
		String baseEntityBosTypeStr = null;

		baseEntityBosTypeStr = fdcEntityObjectInfo.getBaseEntity();
		if (null != baseEntityBosTypeStr) {
			// 递归取得父级实体BosType字符串
			FdcEntityObjectInfo baseBaseEntity = (FdcEntityObjectInfo) fdcEntityObjectMap
					.get(baseEntityBosTypeStr);
			if (null != baseBaseEntity) {
				String baseBaseEntityBosTypeStr = getBaseEntityBosTypeStr(fdcEntityObjectMap,
						baseBaseEntity);
				if (null != baseBaseEntityBosTypeStr) {
					baseEntityBosTypeStr = baseBaseEntityBosTypeStr + "_" + baseEntityBosTypeStr;
				}
			}
		}

		return baseEntityBosTypeStr;
	}

	/**
	 * 根据过滤器删除
	 * 
	 * @param ctx
	 * @param fullNamePerfix
	 * @param isPackage
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void deleteByFilter(Context ctx, String fullNamePerfix, boolean isPackage) throws BOSException,
			EASBizException {
		StringBuffer sql = null;
		String sqlStr = null;

		if ("com.kingdee.eas".equalsIgnoreCase(fullNamePerfix)) {
			sqlStr = "	DELETE FROM T_FDC_FdcColumn	\r\n";
			DbUtil.execute(ctx, sqlStr);

			sqlStr = "	DELETE FROM T_FDC_FdcDataTable	\r\n";
			DbUtil.execute(ctx, sqlStr);

			sqlStr = "	DELETE FROM T_FDC_FdcProperty	\r\n";
			DbUtil.execute(ctx, sqlStr);

			sqlStr = "	DELETE FROM T_FDC_FdcEntityObject	\r\n";
			DbUtil.execute(ctx, sqlStr);
		} else {
			FilterInfo fdcDataTableFilter = new FilterInfo();
			if (isPackage) {
				sql = new StringBuffer();
				sql.append("	SELECT DISTINCT entityObject.FDataTableID	\r\n");
				sql.append("	  FROM T_FDC_FdcEntityObject entityObject	\r\n");
				sql.append("	 INNER JOIN T_FDC_FdcDataTable dataTable	\r\n");
				sql.append("	    ON dataTable.FID = entityObject.FDataTableID	\r\n");
				sql.append("	 WHERE entityObject.FPackageName LIKE '" + fullNamePerfix + ".%'	\r\n");

				fdcDataTableFilter.getFilterItems().add(new FilterItemInfo("ID", sql.toString(), CompareType.INNER));
			} else {
				sql = new StringBuffer();
				sql.append("	SELECT DISTINCT entityObject.FDataTableID	\r\n");
				sql.append("	  FROM T_FDC_FdcEntityObject entityObject	\r\n");
				sql.append("	 INNER JOIN T_FDC_FdcDataTable dataTable	\r\n");
				sql.append("	    ON dataTable.FID = entityObject.FDataTableID	\r\n");
				sql.append("	 WHERE entityObject.FPackageName = '" + fullNamePerfix + "'	\r\n");

				fdcDataTableFilter.getFilterItems().add(new FilterItemInfo("ID", sql.toString(), CompareType.INNER));
			}
			FdcDataTableFactory.getLocalInstance(ctx).delete(fdcDataTableFilter);

			FilterInfo fdcEntityObjectFilter = new FilterInfo();
			if (isPackage) {
				fdcEntityObjectFilter.getFilterItems().add(
						new FilterItemInfo("packageName", fullNamePerfix + ".%", CompareType.LIKE));
			} else {
				fdcEntityObjectFilter.getFilterItems().add(
						new FilterItemInfo("fullName", fullNamePerfix, CompareType.EQUALS));
			}
			FdcEntityObjectFactory.getLocalInstance(ctx).delete(fdcEntityObjectFilter);
		}
	}

	/**
	 * 批量保存
	 * 
	 * @param ctx
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws BOSException
	 * @throws SQLDataException
	 */
	private void saveBatchData(Context ctx, FdcEntityObjectCollection fdcEntityObjectCollection,
			FdcDataTableCollection fdcDataTableCollection) throws EASBizException, SQLDataException, BOSException {
		AbstractObjectCollection colls = null;
		AbstractObjectCollection subColls = null;
		int batchSize = 1000;

		// 分批处理，防止内存溢出
		colls = fdcDataTableCollection;
		subColls = new FdcDataTableCollection();
		for (int i = 0, size = colls.size(); i < size; i++) {
			IObjectValue value = (IObjectValue) colls.getObject(i);
			subColls.addObject(value);

			if ((i + 1) % batchSize == 0) {
				FdcORMappingDAOUtil.addnewBatchData(ctx, this.getConnection(ctx), FdcDataTableFactory
						.getLocalInstance(ctx), subColls);
				subColls = new FdcDataTableCollection();
			}
		}
		FdcORMappingDAOUtil.addnewBatchData(ctx, this.getConnection(ctx), FdcDataTableFactory.getLocalInstance(ctx),
				subColls);

		// 分批处理，防止内存溢出
		colls = fdcEntityObjectCollection;
		subColls = new FdcEntityObjectCollection();
		for (int i = 0, size = colls.size(); i < size; i++) {
			IObjectValue value = (IObjectValue) colls.getObject(i);
			subColls.addObject(value);

			if ((i + 1) % batchSize == 0) {
				FdcORMappingDAOUtil.addnewBatchData(ctx, this.getConnection(ctx), FdcEntityObjectFactory
						.getLocalInstance(ctx), subColls);
				subColls = new FdcEntityObjectCollection();
			}
		}
		FdcORMappingDAOUtil.addnewBatchData(ctx, this.getConnection(ctx), FdcEntityObjectFactory.getLocalInstance(ctx),
				subColls);

		// FdcORMappingDAOUtil.addnewBatchData(ctx, this.getConnection(ctx), FdcDataTableFactory.getLocalInstance(ctx),
		// fdcDataTableCollection);
		// FdcORMappingDAOUtil.addnewBatchData(ctx, this.getConnection(ctx), FdcEntityObjectFactory.getLocalInstance(ctx),
		// fdcEntityObjectCollection);
	}
	
	/**
	 * 保存
	 * 
	 * @param ctx
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws BOSException
	 * @throws SQLDataException
	 */
	private void saveData(Context ctx, FdcEntityObjectCollection fdcEntityObjectCollection,
			FdcDataTableCollection fdcDataTableCollection) throws EASBizException, SQLDataException, BOSException {
		ICoreBase iCoreBase = null;
		AbstractObjectCollection colls = null;
		CoreBaseCollection subColls = null;
		int batchSize = 1000;

		// 分批处理，防止内存溢出
		colls = fdcDataTableCollection;
		subColls = new CoreBaseCollection();
		for (int i = 0, size = colls.size(); i < size; i++) {
			IObjectValue value = (IObjectValue) colls.getObject(i);
			subColls.addObject(value);

			if ((i + 1) % batchSize == 0) {
				iCoreBase = FdcDataTableFactory.getLocalInstance(ctx);
				iCoreBase.addnew(subColls);

				subColls = new CoreBaseCollection();
			}
		}
		iCoreBase = FdcDataTableFactory.getLocalInstance(ctx);
		iCoreBase.addnew(subColls);

		// 分批处理，防止内存溢出
		colls = fdcEntityObjectCollection;
		subColls = new CoreBaseCollection();
		for (int i = 0, size = colls.size(); i < size; i++) {
			IObjectValue value = (IObjectValue) colls.getObject(i);
			subColls.addObject(value);

			if ((i + 1) % batchSize == 0) {
				iCoreBase = FdcEntityObjectFactory.getLocalInstance(ctx);
				iCoreBase.addnew(subColls);

				subColls = new CoreBaseCollection();
			}
		}
		iCoreBase = FdcEntityObjectFactory.getLocalInstance(ctx);
		iCoreBase.addnew(subColls);
	}

	/**
	 * 更新外键
	 * 
	 * @param ctx
	 * @throws BOSException
	 */
	private void updateForeignkey(Context ctx) throws BOSException {
		StringBuffer sql = null;

		// 根据数据表名称，更新数据表ID
		sql = new StringBuffer();
		sql.append("	UPDATE T_FDC_FdcEntityObject tfdcEntO	\r\n");
		sql.append("	   SET FDataTableID =	\r\n");
		sql.append("	       (SELECT TOP 1 tfdcDatT.FID	\r\n");
		sql.append("	          FROM T_FDC_FdcDataTable tfdcDatT	\r\n");
		sql.append("	         WHERE tfdcDatT.FName = tfdcEntO.FDataTableName) 	\r\n");
		sql.append("	 WHERE tfdcEntO.FDataTableName IS NOT NULL	\r\n");
		DbUtil.execute(ctx, sql.toString());

		// 根据数据表名称和映射字段名称，更新映射字段ID
		sql = new StringBuffer();
		sql.append("	UPDATE T_FDC_FdcProperty tfdcPro	\r\n");
		sql.append("	   SET FMappingFieldID =	\r\n");
		sql.append("	       (SELECT TOP 1 tecCol.FID	\r\n");
		sql.append("	          FROM T_FDC_FdcColumn tecCol	\r\n");
		sql.append("	          	INNER JOIN T_FDC_FdcDataTable tfdcDatT	\r\n");
		sql.append("	          		ON tfdcDatT.FID = tecCol.FBillID	\r\n");
		sql.append("	         WHERE tfdcDatT.FName = tfdcPro.FDataTableName 	\r\n");
		sql.append("	         	AND tecCol.FName = tfdcPro.FMappingFieldName)	\r\n");
		sql.append("	 WHERE (tfdcPro.FDataTableName IS NOT NULL) AND (tfdcPro.FMappingFieldName IS NOT NULL)	\r\n");
		DbUtil.execute(ctx, sql.toString());

		// 根据连接属性BOS类型，更新连接属性ID
		sql = new StringBuffer();
		sql.append("	UPDATE T_FDC_FdcProperty tfdcPro	\r\n");
		sql.append("	   SET FLinkPropertyID =	\r\n");
		sql.append("	       (SELECT TOP 1 tfdcEntO.FID	\r\n");
		sql.append("	          FROM T_FDC_FdcEntityObject tfdcEntO	\r\n");
		sql.append("	         WHERE tfdcEntO.FBosTypeStr = tfdcPro.FLinkPropertyBosTypeStr)	\r\n");
		sql.append("	 WHERE tfdcPro.FLinkPropertyBosTypeStr IS NOT NULL	\r\n");
		DbUtil.execute(ctx, sql.toString());
	}

	/**
	 * 更新完整BOS类型
	 * 
	 * @param ctx
	 * @throws BOSException
	 */
	private void updateFullBosTypeStr(Context ctx) throws BOSException {
		StringBuffer sql = null;

		EntityViewInfo view = new EntityViewInfo();

		SelectorItemCollection selector = new SelectorItemCollection();
		view.setSelector(selector);
		selector.add("bosTypeStr");
		selector.add("fullBosTypeStr");

		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("baseEntity", null, CompareType.NOTEQUALS));

		FdcEntityObjectCollection clos = getFdcEntityObjectCollection(ctx, view);
		if (FdcObjectCollectionUtil.isNotEmpty(clos)) {
			FdcEntityObjectInfo info = null;
			String bosTypeStr = null;
			String baseEntity = null;
			Map fdcEntityObjectInfoMap = new HashMap();

			for (int i = 0, size = clos.size(); i < size; i++) {
				info = clos.get(i);

				bosTypeStr = info.getBosTypeStr();
				fdcEntityObjectInfoMap.put(bosTypeStr, info);
			}
		}
	}

	/**
	 * 清除别名
	 * 
	 * @param ctx
	 * @throws BOSException
	 */
	private void clearAlias(Context ctx) throws BOSException {
		StringBuffer sql = null;
		String sqlStr = null;

		// “别名”等于“名称”的单据，在元数据中实际是没有“别名”值的，MetaDataLoader加载时，会默认取“名称”值。
		// 这种元数据是不规范的，否则在用到实体子系统树的地方，例如BOTP，显示的也是英文名称。
		// 此处将其置空，便于"房地产实体字典拓展"查询分析。
		sql = new StringBuffer();
		sql.append("	UPDATE {DataTable}	\r\n");
		sql.append("	   SET	\r\n");
		sql.append("	   		FAlias_l1 = NULLIF(FAlias_l1, FName),	\r\n");
		sql.append("	   		FAlias_l2 = NULLIF(FAlias_l2, FName),	\r\n");
		sql.append("	   		FAlias_l3 = NULLIF(FAlias_l3, FName)	\r\n");
		sql.append("	 WHERE (FAlias_l1 = FName) OR (FAlias_l2 = FName) OR (FAlias_l3 = FName)	\r\n");

		sqlStr = sql.toString();
		sqlStr = sqlStr.replaceFirst("\\{DataTable\\}", "T_FDC_FdcColumn");
		DbUtil.execute(ctx, sqlStr);

		sqlStr = sql.toString();
		sqlStr = sqlStr.replaceFirst("\\{DataTable\\}", "T_FDC_FdcDataTable");
		DbUtil.execute(ctx, sqlStr);

		sqlStr = sql.toString();
		sqlStr = sqlStr.replaceFirst("\\{DataTable\\}", "T_FDC_FdcProperty");
		DbUtil.execute(ctx, sqlStr);

		sqlStr = sql.toString();
		sqlStr = sqlStr.replaceFirst("\\{DataTable\\}", "T_FDC_FdcEntityObject");
		DbUtil.execute(ctx, sqlStr);
	}
	
	/**
	 * 更新数据库中是否存在标识
	 * 
	 * @param ctx
	 * @param fullNamePerfix
	 * @param isPackage
	 * @throws BOSException
	 */
	private void updateIsExist_old(Context ctx, String fullNamePerfix, boolean isPackage) throws BOSException {
		StringBuffer sql = null;

		// 为EAS根节点
		boolean isEasRoot = "com.kingdee.eas".equals(fullNamePerfix);
		// 包前缀为EAS根节点，SQL存在性能问题，待优化，故暂时不更新"房地产列.FIsExist"
		isEasRoot = true;
		// 始终不更新
		if (!isEasRoot) {
			// 1、字段是否存在
			sql = new StringBuffer();
			sql.append("	UPDATE T_FDC_FdcColumn tfdcCol	\r\n");
			sql.append("	   SET FIsExist = 0	\r\n");
			sql.append("	 WHERE tfdcCol.FIsExist <> 0 OR tfdcCol.FIsExist IS NULL	\r\n");
			sql.append("	 	AND EXISTS (SELECT 1	\r\n");
			sql.append("	          FROM T_FDC_FdcDataTable tfdcDatT	\r\n");
			sql.append("	         WHERE tfdcDatT.FID = tfdcCol.FBillID	\r\n");
			if (isPackage) {
				sql.append("	           AND tfdcDatT.FFullName LIKE '" + fullNamePerfix + ".%'	\r\n");
			} else {
				sql.append("	           AND tfdcDatT.FFullName LIKE '" + fullNamePerfix + "%'	\r\n");
			}
			sql.append("	           )	\r\n");
			DbUtil.execute(ctx, sql.toString());

			sql = new StringBuffer();
			sql.append("	UPDATE T_FDC_FdcColumn tfdcCol	\r\n");
			sql.append("	   SET FIsExist = 1	\r\n");
			sql.append("	 WHERE EXISTS (SELECT 1	\r\n");
			sql.append("	          FROM KSQL_USERCOLUMNS	\r\n");
			sql.append("	         INNER JOIN T_FDC_FdcDataTable tfdcDatT	\r\n");
			sql.append("	            ON UCASE(tfdcDatT.FName) = UCASE(KSQL_TABNAME)	\r\n");
			sql.append("	         WHERE tfdcDatT.FID = tfdcCol.FBillID	\r\n");
			if (isPackage) {
				sql.append("	           AND tfdcDatT.FFullName LIKE '" + fullNamePerfix + ".%'	\r\n");
			} else {
				sql.append("	           AND tfdcDatT.FFullName LIKE '" + fullNamePerfix + "%'	\r\n");
			}
			sql.append("	           AND UCASE(KSQL_COL_NAME) = UCASE(tfdcCol.FName))	\r\n");
			DbUtil.execute(ctx, sql.toString());
		}

		// 2、数据表是否存在
		sql = new StringBuffer();
		sql.append("	UPDATE T_FDC_FdcDataTable tfdcDatT	\r\n");
		sql.append("	   SET FIsExist = 0	\r\n");
		sql.append("	 WHERE tfdcDatT.FIsExist <> 0 OR tfdcDatT.FIsExist IS NULL	\r\n");
		if (isPackage) {
			sql.append("	 	AND tfdcDatT.FFullName LIKE '" + fullNamePerfix + ".%'	\r\n");
		} else {
			sql.append("	 	AND tfdcDatT.FFullName = '" + fullNamePerfix + "'	\r\n");
		}
		DbUtil.execute(ctx, sql.toString());

		sql = new StringBuffer();
		sql.append("	UPDATE T_FDC_FdcDataTable tfdcDatT	\r\n");
		sql.append("	   SET FIsExist = 1	\r\n");
		sql.append("	 WHERE EXISTS (SELECT 1	\r\n");
		sql.append("	          FROM KSQL_USERTABLES	\r\n");
		sql.append("	         WHERE UCASE(KSQL_TABNAME) = UCASE(tfdcDatT.FName))	\r\n");
		if (isPackage) {
			sql.append("	 	AND tfdcDatT.FFullName LIKE '" + fullNamePerfix + ".%'	\r\n");
		} else {
			sql.append("	 	AND tfdcDatT.FFullName = '" + fullNamePerfix + "'	\r\n");
		}
		DbUtil.execute(ctx, sql.toString());
	}

	/**
	 * 更新数据库中是否存在标识
	 * 
	 * @param ctx
	 * @param fullNamePerfix
	 * @param isPackage
	 * @throws BOSException
	 */
	private void updateIsExist(Context ctx, String fullNamePerfix, boolean isPackage) throws BOSException {
		StringBuffer sql = null;

		// 1、字段是否存在
		sql = new StringBuffer();
		sql.append("	UPDATE T_FDC_FdcColumn tfdcCol	\r\n");
		sql.append("	   SET FIsExist = 0	\r\n");
		sql.append("	 WHERE (tfdcCol.FIsExist <> 0 OR tfdcCol.FIsExist IS NULL)	\r\n");
		// 使用冗余字段FEntityObjectFullName、FDataTableName提高效率
		if (isPackage) {
			sql.append("	 	AND tfdcCol.FEntityObjectFullName LIKE '" + fullNamePerfix + ".%'	\r\n");
		} else {
			sql.append("	 	AND tfdcCol.FEntityObjectFullName = '" + fullNamePerfix + "'	\r\n");
		}
		DbUtil.execute(ctx, sql.toString());

		sql = new StringBuffer();
		sql.append("	UPDATE T_FDC_FdcColumn tfdcCol	\r\n");
		sql.append("	   SET FIsExist = 1	\r\n");
		sql.append("	 WHERE EXISTS (SELECT 1	\r\n");
		sql.append("	          FROM KSQL_USERCOLUMNS	\r\n");
		sql.append("	         WHERE UCASE(KSQL_COL_TABNAME) = UCASE(tfdcCol.FDataTableName)	\r\n");
		sql.append("	           AND UCASE(KSQL_COL_NAME) = UCASE(tfdcCol.FName))	\r\n");
		// 使用冗余字段FEntityObjectFullName、FDataTableName提高效率
		if (isPackage) {
			sql.append("	 	AND tfdcCol.FEntityObjectFullName LIKE '" + fullNamePerfix + ".%'	\r\n");
		} else {
			sql.append("	 	AND tfdcCol.FEntityObjectFullName = '" + fullNamePerfix + "'	\r\n");
		}
		DbUtil.execute(ctx, sql.toString());

		// 2、数据表是否存在
		sql = new StringBuffer();
		sql.append("	UPDATE T_FDC_FdcDataTable tfdcDatT	\r\n");
		sql.append("	   SET FIsExist = 0	\r\n");
		sql.append("	 WHERE (tfdcDatT.FIsExist <> 0 OR tfdcDatT.FIsExist IS NULL)	\r\n");
		if (isPackage) {
			sql.append("	 	AND tfdcDatT.FFullName LIKE '" + fullNamePerfix + ".%'	\r\n");
		} else {
			sql.append("	 	AND tfdcDatT.FFullName = '" + fullNamePerfix + "'	\r\n");
		}
		DbUtil.execute(ctx, sql.toString());

		sql = new StringBuffer();
		sql.append("	UPDATE T_FDC_FdcDataTable tfdcDatT	\r\n");
		sql.append("	   SET FIsExist = 1	\r\n");
		sql.append("	 WHERE EXISTS (SELECT 1	\r\n");
		sql.append("	          FROM KSQL_USERTABLES	\r\n");
		sql.append("	         WHERE UCASE(KSQL_TABNAME) = UCASE(tfdcDatT.FName))	\r\n");
		if (isPackage) {
			sql.append("	 	AND tfdcDatT.FFullName LIKE '" + fullNamePerfix + ".%'	\r\n");
		} else {
			sql.append("	 	AND tfdcDatT.FFullName = '" + fullNamePerfix + "'	\r\n");
		}
		DbUtil.execute(ctx, sql.toString());
	}

	protected FdcEntityObjectCollection _synchronizeEasMD(Context ctx) throws BOSException, EASBizException {
		return _synchronizeMD(ctx, "com.kingdee.eas", true);
	}

	protected FdcEntityObjectCollection _synchronizeBaseMD(Context ctx) throws BOSException, EASBizException {
		_synchronizeMD(ctx, "com.kingdee.eas.base", true);
		_synchronizeMD(ctx, "com.kingdee.eas.basedata", true);

		return null;
	}

	protected FdcEntityObjectCollection _synchronizeFdcMD(Context ctx) throws BOSException, EASBizException {
		// EAS基础数据非常重要，几乎所有单据都要依赖，所以一并同步基础元数据
		synchronizeBaseMD(ctx);

		return _synchronizeMD(ctx, "com.kingdee.eas.fdc", true);
	}

}