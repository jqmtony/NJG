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

	// ͬ��Ԫ����
	protected FdcEntityObjectCollection _synchronizeMD(Context ctx, String fullNamePerfix, boolean isPackage)
			throws BOSException, EASBizException {
		FdcEntityObjectCollection fdcEntityObjectCollection = new FdcEntityObjectCollection();
		FdcDataTableCollection fdcDataTableCollection = new FdcDataTableCollection();

		long startTime = -1;
		long endTime = -1;
		double exeTime = -1;

		// //////////////////////////////////////////////////////////////

		// ���������󼯺�
		startTime = System.currentTimeMillis();
		parseIntoObjectCollection(ctx, fullNamePerfix, isPackage, fdcEntityObjectCollection, fdcDataTableCollection);
		endTime = System.currentTimeMillis();
		exeTime = (endTime - startTime) * 1.0 / 1000;
		logger.info("FdcEntityObjectControllerBean.parseIntoObjectCollection, exeTime: " + exeTime + " m");

		// ���ݹ�����ɾ��
		startTime = System.currentTimeMillis();
		deleteByFilter(ctx, fullNamePerfix, isPackage);
		endTime = System.currentTimeMillis();
		exeTime = (endTime - startTime) * 1.0 / 1000;
		logger.info("FdcEntityObjectControllerBean.deleteByFilter, exeTime: " + exeTime + " m");

		// ��������
		startTime = System.currentTimeMillis();
		saveBatchData(ctx, fdcEntityObjectCollection, fdcDataTableCollection);
		endTime = System.currentTimeMillis();
		exeTime = (endTime - startTime) * 1.0 / 1000;
		logger.info("FdcEntityObjectControllerBean.saveBatchData, exeTime: " + exeTime + " m");

		// �������
		startTime = System.currentTimeMillis();
		updateForeignkey(ctx);
		endTime = System.currentTimeMillis();
		exeTime = (endTime - startTime) * 1.0 / 1000;
		logger.info("FdcEntityObjectControllerBean.updateForeignkey, exeTime: " + exeTime + " m");

		// �������
		startTime = System.currentTimeMillis();
		clearAlias(ctx);
		endTime = System.currentTimeMillis();
		exeTime = (endTime - startTime) * 1.0 / 1000;
		logger.info("FdcEntityObjectControllerBean.clearAlias, exeTime: " + exeTime + " m");

		// �������ݿ����Ƿ���ڱ�ʶ
		startTime = System.currentTimeMillis();
		updateIsExist(ctx, fullNamePerfix, isPackage);
		endTime = System.currentTimeMillis();
		exeTime = (endTime - startTime) * 1.0 / 1000;
		logger.info("FdcEntityObjectControllerBean.updateIsExist, exeTime: " + exeTime + " m");

		// //////////////////////////////////////////////////////////////

		return fdcEntityObjectCollection;
	}

	/**
	 * ���������󼯺�
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

		// �����������������б�
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
				// �������
				entityAlias = dealWithAlias(entityObjectInfo, entityAlias, defaultAlias);
				// entityDescription = entityObjectInfo.getDescription();
				// ע�⣺ʹ��getType()��������getBOSType()
				bosTypeStr = entityObjectInfo.getType().toString();

				// ȡ�ø���ʵ��BosType�ַ���
				baseEntityBosTypeStr = getBaseEntityBosTypeStr(entityObjectInfo, false);
				// �ݹ�ȡ�ø���ʵ��BosType�ַ���
				baseEntityFullBosTypeStr = getBaseEntityBosTypeStr(entityObjectInfo, true);
				if (null != baseEntityFullBosTypeStr) {
					fullBosTypeStr = baseEntityFullBosTypeStr + "_" + bosTypeStr;
				} else {
					fullBosTypeStr = bosTypeStr;
				}

				dataTableInfo = entityObjectInfo.getTable();
				if (null != dataTableInfo) {
					// ȡ��ʵ����������������Լ��̳���������, ��ȥ���ظ�������(�����������ж�)
					propertyCollection = entityObjectInfo.getInheritedNoDuplicatedPropertiesRuntime();
					// ȡ���������Զ�Ӧ��ʵ��
					childEntities = EntityObjectInfo.getChildEntities(entityObjectInfo);

					dataTableName = dataTableInfo.getName();
					dataTableAlias = dataTableInfo.getAlias();
					// �������
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
						 * �������ֶο�������FdcObjectValueUtil.copyLocalValue����BUG����ʱ�����ε�
						 */
						// �������ػ�����ֵ
						// FdcObjectValueUtil.copyLocalValue(dataTableInfo, fdcDataTableInfo,
						// "alias", localeArr);
						// fdcDataTableInfo.setDescription(dataTableDescription);
						// �������ػ�����ֵ
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
					// ��ʱ�������������
					// fdcEntityObjectInfo.setDataTable(fdcDataTableInfo);
					fdcEntityObjectInfo.setPackageName(packageName);
					fdcEntityObjectInfo.setName(entityName);
					fdcEntityObjectInfo.setFullName(fullName);
					fdcEntityObjectInfo.setAlias(entityAlias);
					fdcDataTableInfo.setAlias(dataTableAlias);
					/**
					 * �������ֶο�������FdcObjectValueUtil.copyLocalValue����BUG����ʱ�����ε�
					 */
					// �������ػ�����ֵ
					// FdcObjectValueUtil.copyLocalValue(entityObjectInfo, fdcEntityObjectInfo,
					// "alias", localeArr);
					// fdcEntityObjectInfo.setDescription(entityDescription);
					// �������ػ�����ֵ
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
						// �������
						propertyAlias = dealWithAlias(propertyInfo, propertyAlias, defaultAlias);
						// propertyDescription = propertyInfo.getDescription();

						if (propertyInfo instanceof LinkPropertyInfo) {
							linkPropertyInfo = (LinkPropertyInfo) propertyInfo;

							linkEntityObjectInfo = (EntityObjectInfo) childEntities.get(linkPropertyInfo);
							if (null != linkEntityObjectInfo) {
								// ע�⣺ʹ��getType()��������getBOSType()
								if (null != linkEntityObjectInfo.getType()) {
									linkEntityBosTypeStr = linkEntityObjectInfo.getType().toString();
								}
							}

							relationshipInfo = linkPropertyInfo.getRelationship();
							if (null != relationshipInfo) {
								// ע�⣺ʹ��getType()��������getBOSType()
								relationshipUrlName = relationshipInfo.getFullName();
								// ���Ϲ���˫����BOSType
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
							// �������
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
								 * �������ֶο�������FdcObjectValueUtil.copyLocalValue����BUG����ʱ�����ε�
								 */
								// �������ػ�����ֵ
								// FdcObjectValueUtil.copyLocalValue(columnInfo, fdcColumnInfo,
								// "alias", localeArr);
								// fdcColumnInfo.setDescription(columnDescription);
								// �������ػ�����ֵ
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
								
								// ʵ�������������(�����ֶ�)����ӦentityObject.name
								fdcColumnInfo.setEntityObjectFullName(fullName);
								// ���ݱ�����(�����ֶ�)����ӦdataTable.name
								fdcColumnInfo.setDataTableName(dataTableName);

								fdcColumnCollection.add(fdcColumnInfo);
								fdcColumnMap.put(mappingFieldUrlName, fdcColumnInfo);
							}
						} else {
							fdcColumnInfo = null;
						}

						fdcPropertyInfo = new FdcPropertyInfo();
						fdcPropertyInfo.setBill(fdcEntityObjectInfo);
						// ��ʱ�������������
						// fdcPropertyInfo.setMappingField(fdcColumnInfo);
						fdcPropertyInfo.setName(propertyName);
						fdcPropertyInfo.setAlias(propertyAlias);
						fdcDataTableInfo.setAlias(dataTableAlias);
						/**
						 * �������ֶο�������FdcObjectValueUtil.copyLocalValue����BUG����ʱ�����ε�
						 */
						// �������ػ�����ֵ
						// FdcObjectValueUtil.copyLocalValue(propertyInfo, fdcPropertyInfo, "alias",
						// localeArr);
						// fdcPropertyInfo.setDescription(propertyDescription);
						// �������ػ�����ֵ
						// FdcObjectValueUtil.copyLocalValue(propertyInfo, fdcPropertyInfo,
						// "description", localeArr);
						
						// ʵ�������������(�����ֶ�)����ӦentityObject.name
						fdcPropertyInfo.setEntityObjectFullName(fullName);
						// ���ݱ�����(�����ֶ�)����ӦdataTable.name
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
	 * �������������
	 * <p>
	 * 1���������Ϊ�գ������ó�һ������ı�ʶ��������"���ز�ʵ���ֵ���չ"��ѯ����
	 * 
	 * @param entityObjectInfo
	 * @param entityAlias
	 * @param defaultAlias
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2015-5-22
	 */
	private String dealWithAlias(EntityObjectInfo entityObjectInfo, String entityAlias, String defaultAlias) {
		// �����������ڡ����ơ��ĵ��ݣ���Ԫ������ʵ����û�С�������ֵ�ģ�MetaDataLoader����ʱ����Ĭ��ȡ�����ơ�ֵ��
		/**
		 * ���ԣ�������һ��ʵ�����ǲ���Ч��
		 */
		if (FdcStringUtil.isBlank(entityAlias)) {
			entityAlias = defaultAlias;
			entityObjectInfo.setAlias(entityAlias);
		}

		return entityAlias;
	}

	/**
	 * �������������
	 * <p>
	 * 1���������Ϊ�գ������ó�һ������ı�ʶ��������"���ز�ʵ���ֵ���չ"��ѯ����
	 * 
	 * @param dataTableInfo
	 * @param dataTableAlias
	 * @param defaultAlias
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2015-5-22
	 */
	private String dealWithAlias(DataTableInfo dataTableInfo, String dataTableAlias, String defaultAlias) {
		// �����������ڡ����ơ��ĵ��ݣ���Ԫ������ʵ����û�С�������ֵ�ģ�MetaDataLoader����ʱ����Ĭ��ȡ�����ơ�ֵ��
		/**
		 * ���ԣ�������һ��ʵ�����ǲ���Ч��
		 */
		if (FdcStringUtil.isBlank(dataTableAlias)) {
			dataTableAlias = defaultAlias;
			dataTableInfo.setAlias(dataTableAlias);
		}

		return dataTableAlias;
	}

	/**
	 * �������������
	 * <p>
	 * 1���������Ϊ�գ������ó�һ������ı�ʶ��������"���ز�ʵ���ֵ���չ"��ѯ����
	 * 
	 * @param propertyInfo
	 * @param propertyAlias
	 * @param defaultAlias
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2015-5-22
	 */
	private String dealWithAlias(PropertyInfo propertyInfo, String propertyAlias, String defaultAlias) {
		// �����������ڡ����ơ��ĵ��ݣ���Ԫ������ʵ����û�С�������ֵ�ģ�MetaDataLoader����ʱ����Ĭ��ȡ�����ơ�ֵ��
		/**
		 * ���ԣ�������һ��ʵ�����ǲ���Ч��
		 */
		if (FdcStringUtil.isBlank(propertyAlias)) {
			propertyAlias = defaultAlias;
			propertyInfo.setAlias(propertyAlias);
		}

		return propertyAlias;
	}

	/**
	 * �������������
	 * <p>
	 * 1���������Ϊ�գ������ó�һ������ı�ʶ��������"���ز�ʵ���ֵ���չ"��ѯ����
	 * 
	 * @param columnInfo
	 * @param columnAlias
	 * @param defaultAlias
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2015-5-22
	 */
	private String dealWithAlias(ColumnInfo columnInfo, String columnAlias, String defaultAlias) {
		// �����������ڡ����ơ��ĵ��ݣ���Ԫ������ʵ����û�С�������ֵ�ģ�MetaDataLoader����ʱ����Ĭ��ȡ�����ơ�ֵ��
		/**
		 * ���ԣ�������һ��ʵ�����ǲ���Ч��
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
	 * ȡ�ø���ʵ��BosType�ַ���
	 * 
	 * @param entityObjectInfo
	 *            ʵ�����
	 * @param isFull
	 *            �Ƿ�ݹ�ȡ�ø���ʵ��BosType�ַ���
	 * @return
	 */
	private String getBaseEntityBosTypeStr(EntityObjectInfo entityObjectInfo, boolean isFull) {
		String baseEntityBosTypeStr = null;

		EntityObjectInfo baseEntity = entityObjectInfo.getBaseEntity();
		if (null != baseEntity) {
			baseEntityBosTypeStr = baseEntity.getType().toString();

			// �ݹ�ȡ�ø���ʵ��BosType�ַ���
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
	 * ȡ�ø���ʵ��BosType�ַ���
	 * 
	 * @param fdcEntityObjectMap
	 *            ���ز�ʵ��Map
	 * @param fdcEntityObjectInfo
	 *            ���ز�ʵ�����
	 * @return
	 */
	private String getBaseEntityBosTypeStr(Map fdcEntityObjectMap,
			FdcEntityObjectInfo fdcEntityObjectInfo) {
		String baseEntityBosTypeStr = null;

		baseEntityBosTypeStr = fdcEntityObjectInfo.getBaseEntity();
		if (null != baseEntityBosTypeStr) {
			// �ݹ�ȡ�ø���ʵ��BosType�ַ���
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
	 * ���ݹ�����ɾ��
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
	 * ��������
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

		// ����������ֹ�ڴ����
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

		// ����������ֹ�ڴ����
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
	 * ����
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

		// ����������ֹ�ڴ����
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

		// ����������ֹ�ڴ����
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
	 * �������
	 * 
	 * @param ctx
	 * @throws BOSException
	 */
	private void updateForeignkey(Context ctx) throws BOSException {
		StringBuffer sql = null;

		// �������ݱ����ƣ��������ݱ�ID
		sql = new StringBuffer();
		sql.append("	UPDATE T_FDC_FdcEntityObject tfdcEntO	\r\n");
		sql.append("	   SET FDataTableID =	\r\n");
		sql.append("	       (SELECT TOP 1 tfdcDatT.FID	\r\n");
		sql.append("	          FROM T_FDC_FdcDataTable tfdcDatT	\r\n");
		sql.append("	         WHERE tfdcDatT.FName = tfdcEntO.FDataTableName) 	\r\n");
		sql.append("	 WHERE tfdcEntO.FDataTableName IS NOT NULL	\r\n");
		DbUtil.execute(ctx, sql.toString());

		// �������ݱ����ƺ�ӳ���ֶ����ƣ�����ӳ���ֶ�ID
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

		// ������������BOS���ͣ�������������ID
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
	 * ��������BOS����
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
	 * �������
	 * 
	 * @param ctx
	 * @throws BOSException
	 */
	private void clearAlias(Context ctx) throws BOSException {
		StringBuffer sql = null;
		String sqlStr = null;

		// �����������ڡ����ơ��ĵ��ݣ���Ԫ������ʵ����û�С�������ֵ�ģ�MetaDataLoader����ʱ����Ĭ��ȡ�����ơ�ֵ��
		// ����Ԫ�����ǲ��淶�ģ��������õ�ʵ����ϵͳ���ĵط�������BOTP����ʾ��Ҳ��Ӣ�����ơ�
		// �˴������ÿգ�����"���ز�ʵ���ֵ���չ"��ѯ������
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
	 * �������ݿ����Ƿ���ڱ�ʶ
	 * 
	 * @param ctx
	 * @param fullNamePerfix
	 * @param isPackage
	 * @throws BOSException
	 */
	private void updateIsExist_old(Context ctx, String fullNamePerfix, boolean isPackage) throws BOSException {
		StringBuffer sql = null;

		// ΪEAS���ڵ�
		boolean isEasRoot = "com.kingdee.eas".equals(fullNamePerfix);
		// ��ǰ׺ΪEAS���ڵ㣬SQL�����������⣬���Ż�������ʱ������"���ز���.FIsExist"
		isEasRoot = true;
		// ʼ�ղ�����
		if (!isEasRoot) {
			// 1���ֶ��Ƿ����
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

		// 2�����ݱ��Ƿ����
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
	 * �������ݿ����Ƿ���ڱ�ʶ
	 * 
	 * @param ctx
	 * @param fullNamePerfix
	 * @param isPackage
	 * @throws BOSException
	 */
	private void updateIsExist(Context ctx, String fullNamePerfix, boolean isPackage) throws BOSException {
		StringBuffer sql = null;

		// 1���ֶ��Ƿ����
		sql = new StringBuffer();
		sql.append("	UPDATE T_FDC_FdcColumn tfdcCol	\r\n");
		sql.append("	   SET FIsExist = 0	\r\n");
		sql.append("	 WHERE (tfdcCol.FIsExist <> 0 OR tfdcCol.FIsExist IS NULL)	\r\n");
		// ʹ�������ֶ�FEntityObjectFullName��FDataTableName���Ч��
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
		// ʹ�������ֶ�FEntityObjectFullName��FDataTableName���Ч��
		if (isPackage) {
			sql.append("	 	AND tfdcCol.FEntityObjectFullName LIKE '" + fullNamePerfix + ".%'	\r\n");
		} else {
			sql.append("	 	AND tfdcCol.FEntityObjectFullName = '" + fullNamePerfix + "'	\r\n");
		}
		DbUtil.execute(ctx, sql.toString());

		// 2�����ݱ��Ƿ����
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
		// EAS�������ݷǳ���Ҫ���������е��ݶ�Ҫ����������һ��ͬ������Ԫ����
		synchronizeBaseMD(ctx);

		return _synchronizeMD(ctx, "com.kingdee.eas.fdc", true);
	}

}