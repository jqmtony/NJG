/**
 * @(#)FdcEntityObjectUtil.java 1.0 2011-5-31
 * Copyright 2011 Kingdee, Inc. All rights reserved
 */
package com.kingdee.eas.fdc.basedata.scheme;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ContextUtils;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.data.DataTableInfo;
import com.kingdee.bos.metadata.data.SQLType;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.IFDCSQLFacade;
import com.kingdee.eas.fdc.basedata.util.FdcObjectCollectionUtil;
import com.kingdee.eas.fdc.basedata.util.FdcObjectUtil;
import com.kingdee.eas.fdc.basedata.util.FdcSqlUtil;
import com.kingdee.eas.fdc.basedata.util.FdcStringUtil;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.util.EASCommonResource;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * ���ز�ʵ����󹤾���
 * 
 * @author ����
 * @email SkyIter@live.com
 * @date 2011-5-31
 * @see
 * @since 1.4
 */
public class FdcEntityObjectUtil {

	/**
	 * ȡ��ʵ�壬������Ӧ�����ݱ�
	 * 
	 * @param ctx
	 *            �����ģ��ɿ�
	 * @param id
	 *            UUID���ǿ�
	 * @param isCheck
	 *            �Ƿ���
	 * @return
	 * @throws EASBizException
	 */
	public static EntityObjectInfo getEntity(Context ctx, String id, boolean isCheck) throws EASBizException {
		EntityObjectInfo entityInfo = null;

		id = id.trim();
		BOSObjectType bosType = null;
		String msg = null;

		IMetaDataLoader metaDataLoader = null;
		String resClassName = "com.kingdee.eas.fm.common.COMMONAutoGenerateResource";
		if (null == ctx) {
			try {
				bosType = BOSUuid.read(id).getType();
			} catch (Exception ex) {
				if (isCheck) {
					msg = EASResource.getString(resClassName, "54_FMIsqlUI");
					FDCUtils.throwEASBizException(msg);
				}
			}

			if (bosType == null && isCheck) {
				msg = EASResource.getString(resClassName, "55_FMIsqlUI");
				FDCUtils.throwEASBizException(msg);
			} else {
				metaDataLoader = MetaDataLoaderFactory.getRemoteMetaDataLoader();
				entityInfo = metaDataLoader.getEntity(bosType);
				if (entityInfo == null && isCheck) {
					msg = EASResource.getString(resClassName, "55_FMIsqlUI");
					FDCUtils.throwEASBizException(msg);
				}
			}
		} else {
			try {
				bosType = BOSUuid.read(id).getType();
			} catch (Exception ex) {
				msg = EASCommonResource.getString(resClassName, "54_FMIsqlUI", null);
				FDCUtils.throwEASBizException(msg);
			}

			if (bosType == null && isCheck) {
				msg = EASCommonResource.getString(resClassName, "55_FMIsqlUI", null);
				FDCUtils.throwEASBizException(msg);
			} else {
				metaDataLoader = MetaDataLoaderFactory.getLocalMetaDataLoader(ctx);
				entityInfo = metaDataLoader.getEntity(bosType);
				if (entityInfo == null && isCheck) {
					msg = EASCommonResource.getString(resClassName, "55_FMIsqlUI", null);
					FDCUtils.throwEASBizException(msg);
				}
			}
		}

		return entityInfo;
	}

	/**
	 * ���ɲ�ѯSQL
	 * 
	 * @param ctx
	 *            �����ģ��ɿ�
	 * @param id
	 *            UUID���ǿ�
	 * @return
	 * @throws Exception
	 */
	public static String generateQuerySql(Context ctx, String id) throws Exception {
		String sql = null;

		EntityObjectInfo entityInfo = getEntity(ctx, id, true);
		DataTableInfo tableInfo = entityInfo.getTable();

		// ���ɲ�ѯSQL
		sql = generateQuerySql(ctx, entityInfo, tableInfo, id);

		return sql;
	}

	/**
	 * ���ɲ�ѯSQL
	 * 
	 * @param ctx
	 *            �����ģ��ɿ�
	 * @param entityInfo
	 *            ʵ����󣻷ǿ�
	 * @param tableInfo
	 *            ���ݱ���󣻷ǿ�
	 * @param id
	 *            UUID���ɿ�
	 * @return
	 * @throws Exception
	 */
	public static String generateQuerySql(Context ctx, EntityObjectInfo entityInfo, DataTableInfo tableInfo, String id)
			throws Exception {
		String sql = null;

		String entityFullName = entityInfo.getFullName();
		String tableName = tableInfo.getName();

		EntityViewInfo view = new EntityViewInfo();

		SelectorItemCollection selector = new SelectorItemCollection();
		view.setSelector(selector);
		selector.add("*");
		selector.add("mappingField.*");

		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.appendFilterItem("bill.fullName", entityFullName);

		IFdcProperty fdcProperty = null;
		if (null == ctx) {
			fdcProperty = FdcPropertyFactory.getRemoteInstance();
		} else {
			fdcProperty = FdcPropertyFactory.getLocalInstance(ctx);
		}
		FdcPropertyCollection fdcPropertyCols = fdcProperty.getFdcPropertyCollection(view);

		// ���ɲ�ѯSQL
		sql = generateQuerySql(ctx, tableName, fdcPropertyCols, id);

		return sql;
	}

	/**
	 * ���ɲ�ѯSQL
	 * 
	 * @param ctx
	 *            �����ģ��ɿ�
	 * @param dataTableName
	 *            ���ݱ������ǿ�
	 * @param fdcPropertyCols
	 *            ���ز������������ǿ�
	 * @param id
	 *            UUID���ɿ�
	 * @return
	 * @throws Exception
	 */
	public static String generateQuerySql(Context ctx, String dataTableName, FdcPropertyCollection fdcPropertyCols,
			String id) throws Exception {
		String sql = null;

		// ��ѯKSQL����
		Set colNameSet = FdcSqlUtil.queryKsqlColNameSet(ctx, dataTableName, false, true);
		if (FdcObjectCollectionUtil.isNotEmpty(fdcPropertyCols)) {
			boolean hasId = FdcStringUtil.isNotBlank(id);
			if (hasId && !colNameSet.contains("fid")) {
				String msg = dataTableName + " ���в����ڣ�FID ";
				FDCUtils.throwEASBizException(msg);
			}

			StringBuffer tmpSqlStringBuffer = new StringBuffer();

			FdcPropertyInfo propertyInfo = null;
			FdcColumnInfo columnInfo = null;
			String columnName = null;
			String columnNameLowerCase = null;
			Locale locale = null;
			String language = null;
			String propertyAlias = null;
			String tempPropertyAlias = null;
			byte[] aliasBytes = null;
			int aliasLen = -1;

			String sqlTypeStr = null;
			SQLType sqlType = null;
			// �����
			List lobSqlTypeEnumList = new ArrayList();
			// // �������������ݣ� 1 �� 8,000
			// lobSqlTypeEnumList.add(SQLType.BINARY);
			// // �䳤���������ݣ� 1 �� 8,000
			// lobSqlTypeEnumList.add(SQLType.VARBINARY);
			lobSqlTypeEnumList.add(SQLType.BLOB);
			lobSqlTypeEnumList.add(SQLType.CLOB);
			lobSqlTypeEnumList.add(SQLType.NCLOB);

			// û������
			StringBuffer noColNameStringBuffer = new StringBuffer();
			// �����
			StringBuffer lobStringBuffer = new StringBuffer();
			// ����̫��
			StringBuffer aliasTooLongStringBuffer = new StringBuffer();

			FdcPropertyCollection tmpCols = new FdcPropertyCollection();
			for (int i = 0, size = fdcPropertyCols.size(); i < size; i++) {
				propertyInfo = fdcPropertyCols.get(i);
				columnInfo = propertyInfo.getMappingField();
				if (null == columnInfo) {
					continue;
				}

				columnName = columnInfo.getName();
				propertyAlias = propertyInfo.getAlias();
				propertyAlias = FdcObjectUtil.defaultIfNull(propertyAlias, propertyInfo.getName());
				columnNameLowerCase = columnName.toLowerCase();
				// �������ֶΣ�Ҫ����language��д
				if (columnInfo.isIsMultilingual()) {
					locale = ContextUtils.getLocaleFromEnv();
					if (null != locale) {
						language = locale.getLanguage();
						if (null != language) {
							columnNameLowerCase += "_" + language;
						}
					}
				}

				if (!colNameSet.contains(columnNameLowerCase)) {
					noColNameStringBuffer.append("--�в����ڣ�").append(columnName).append(",").append(propertyAlias)
							.append("\n");
					continue;
				}

				// ����󣬲���ѯ��������ܵ����ڴ����
				sqlTypeStr = columnInfo.getSqlType();
				sqlType = SQLType.getEnum(sqlTypeStr);
				if (lobSqlTypeEnumList.contains(sqlType)) {
					lobStringBuffer.append("--�����").append(columnName).append(",").append(propertyAlias).append(",")
							.append(sqlTypeStr).append("\n");
					continue;
				}

				// char��˫�ֽ�
				aliasBytes = propertyAlias.getBytes();
				aliasLen = aliasBytes.length;
				// SQL�����֧��10������
				if (aliasLen > 18) {
					aliasLen = 18;
					tempPropertyAlias = new String(aliasBytes, 0, aliasLen);

					aliasTooLongStringBuffer.append("--����̫��,�ضϣ�").append(columnName).append(",").append(propertyAlias)
							.append("-->").append(tempPropertyAlias).append("\n");
					propertyAlias = tempPropertyAlias;
				}

				tmpCols.add(propertyInfo);
			}
			for (int i = 0, size = tmpCols.size(); i < size; i++) {
				propertyInfo = tmpCols.get(i);
				columnInfo = propertyInfo.getMappingField();

				columnName = columnInfo.getName();
				propertyAlias = propertyInfo.getAlias();
				propertyAlias = FdcObjectUtil.defaultIfNull(propertyAlias, propertyInfo.getName());
				// �������ֶΣ�Ҫ����language��д
				if (columnInfo.isIsMultilingual()) {
					locale = ContextUtils.getLocaleFromEnv();
					if (null != locale) {
						language = locale.getLanguage();
						if (null != language) {
							columnName += "_" + language;
						}
					}
				}

				// char��˫�ֽ�
				aliasBytes = propertyAlias.getBytes();
				aliasLen = aliasBytes.length;
				// SQL�����֧��10������
				aliasLen = (aliasLen > 20) ? 20 : aliasLen;
				propertyAlias = new String(aliasBytes, 0, aliasLen);

				tmpSqlStringBuffer.append(columnName).append(" \"").append(propertyAlias).append("\"");
				if (size - 1 != i) {
					tmpSqlStringBuffer.append(", ");
				} else {
					tmpSqlStringBuffer.append("\r\n");
				}
			}

			if (!tmpCols.isEmpty()) {
				StringBuffer sqlStringBuffer = new StringBuffer();
				// ��ע��Ϣ
				sqlStringBuffer.append(noColNameStringBuffer);
				sqlStringBuffer.append(lobStringBuffer);
				sqlStringBuffer.append(aliasTooLongStringBuffer);
				if (hasId) {
					sqlStringBuffer.append("SELECT	\r\n");
				} else {
					// ����ѯ1000����¼
					sqlStringBuffer.append("SELECT TOP 1000	\r\n");
				}
				sqlStringBuffer.append("	");
				sqlStringBuffer.append(tmpSqlStringBuffer);
				sqlStringBuffer.append("FROM " + dataTableName);
				if (hasId) {
					sqlStringBuffer.append("\n");
					sqlStringBuffer.append("WHERE FID = '" + id + "'");
				}

				sql = sqlStringBuffer.toString();
			}

		}

		return sql;
	}

	/**
	 * �������ݿ����Ƿ���ڱ�ʶ
	 * 
	 * @param ctx
	 *            �����ģ��ɿ�
	 * @param dataTableName
	 *            ���ݱ������ǿ�
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void updateIsExist(Context ctx, String dataTableName) throws BOSException, EASBizException {
		if (FdcStringUtil.isNotBlank(dataTableName)) {
			return;
		}

		IFDCSQLFacade fDCSQLFacade = null;
		if (null == ctx) {
			fDCSQLFacade = FDCSQLFacadeFactory.getRemoteInstance();
		} else {
			fDCSQLFacade = FDCSQLFacadeFactory.getLocalInstance(ctx);
		}

		StringBuffer sqlSb = null;
		List paramList = new ArrayList();
		IRowSet rs = null;
		String dataTableId = null;

		sqlSb = new StringBuffer();
		sqlSb.append(" SELECT FID FROM T_FDC_FdcDataTable tfdcDatT WHERE 1 = 1 ");
		paramList.clear();
		dataTableName = dataTableName.trim().toUpperCase();
		// ����"KSQL��"Where����
		FdcSqlUtil.appendKsqlColWhereFrag(sqlSb, paramList, "tfdcDatT.FName", dataTableName, false, "AND");

		rs = fDCSQLFacade.executeQuery(sqlSb.toString(), paramList);
		dataTableId = null;
		try {
			while (rs.next()) {
				dataTableId = rs.getString("FID");
				break;
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}

		if (FdcStringUtil.isNotBlank(dataTableId)) {
			paramList.clear();
			paramList.add(dataTableId);

			sqlSb = new StringBuffer();
			sqlSb.append(" UPDATE T_FDC_FdcDataTable tfdcDatT SET FIsExist = 0 WHERE tfdcDatT.FID = ? ");
			// �������ݿ����Ƿ���ڱ�ʶ
			fDCSQLFacade.executeUpdate(sqlSb.toString(), paramList);

			sqlSb = new StringBuffer();
			sqlSb.append(" UPDATE T_FDC_FdcColumn tecPro SET FIsExist = 0 WHERE tecPro.FBillID = ? ");
			// �������ݿ����Ƿ���ڱ�ʶ
			fDCSQLFacade.executeUpdate(sqlSb.toString(), paramList);
		}
	}
}
