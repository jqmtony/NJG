/**
 * @(#)FdcMetaDataUtil.java 1.0 2014-3-19
 * @author ����
 * @email SkyIter@live.com
 * Copyright 2014 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import junit.framework.Assert;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;

import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.common.util.FileUtil;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.ColumnCollection;
import com.kingdee.bos.metadata.data.ColumnInfo;
import com.kingdee.bos.metadata.data.DataTableInfo;
import com.kingdee.bos.metadata.entity.EntityObjectCollection;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.LinkPropertyInfo;
import com.kingdee.bos.metadata.entity.PropertyCollection;
import com.kingdee.bos.metadata.entity.PropertyInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.QueryInfo;
import com.kingdee.bos.metadata.util.MetaDataLoader;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.eas.fdc.basedata.FDCConstants;

/**
 * ���������ز� Ԫ���ݹ�����(�ͻ��ˡ������ͨ��)
 * 
 * @author ����
 * @email SkyIter@live.com
 * @createDate 2014-3-19
 * @version 1.0, 2014-3-19
 * @see
 * @since JDK1.4
 */
public class FdcMetaDataUtil {
	/**
	 * ʵ�����_ӳ�� <br>
	 * ����key���BosType�ַ�����value���ʵ�����
	 */
	private static final Map ENTITY_OBJECT_INFO_MAP = new LinkedHashMap();

	/**
	 * ������_�б�_ӳ�� <br>
	 * ����key���BosType�ַ�����value���������_�б�
	 */
	private static final Map EPROPERTY_NAME_LIST_MAP = new LinkedHashMap();

	/**
	 * ����_������_�б�_ӳ�� <br>
	 * ����key���BosType�ַ�����value�������_������_�б�
	 */
	private static final Map OWN_EPROPERTY_NAME_LIST_MAP = new LinkedHashMap();

	/**
	 * ����_������_�б�_ӳ�� <br>
	 * ����key���BosType�ַ�����value�������_������_�б�
	 */
	private static final Map LINK_EPROPERTY_NAME_LIST_MAP = new LinkedHashMap();

	/**
	 * ����_ӳ��_���_�ߴ�
	 */
	private static final int CACHE_MAP_MAX_SIZE = 500;

	// //////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////

	/**
	 * ȡ��Ԫ�����ļ���������
	 * 
	 * @param metadataRootPath
	 * @param metadataFullName
	 * @param extName
	 * @return
	 */
	public static String getMetaDataFileFullName(String metadataRootPath, String metadataFullName, String extName) {
		String entityFileFullName = null;

		entityFileFullName = metadataRootPath + "/" + metadataFullName.replaceAll("\\.", "\\/") + "." + extName;

		return entityFileFullName;
	}

	/**
	 * ȡ��ʵ���ļ���������
	 * 
	 * @param metadataRootPath
	 * @param entityFullName
	 * @return
	 */
	public static String getEntityFileFullName(String metadataRootPath, String entityFullName) {
		return getMetaDataFileFullName(metadataRootPath, entityFullName, FdcMetaDataConstant.META_DATA_EXTNAME_ENTITY);
	}

	/**
	 * ȡ��ʵ���ļ�XML�ĵ�
	 * 
	 * @param metadataRootPath
	 * @param entityFullName
	 * @return
	 */
	public static Document getDocument(String metadataRootPath, String entityFullName) {
		Document document = null;

		String entityFileFullName = getEntityFileFullName(metadataRootPath, entityFullName);
		document = FdcXmlUtil.read(entityFileFullName);

		return document;
	}

	/**
	 * ȡ��ʵ���ļ�XML��Ԫ��
	 * 
	 * @param metadataRootPath
	 * @param entityFullName
	 * @return
	 */
	public static Element getRootElement(String metadataRootPath, String entityFullName) {
		Element rootElement = null;

		Document document = getDocument(metadataRootPath, entityFullName);
		rootElement = document.getRootElement();

		return rootElement;
	}

	/**
	 * ȡ����չ����XMLԪ��
	 * 
	 * @param rootElement
	 * @param extendedPropertyKey
	 * @return
	 */
	public static Element getExtendedPropertyElement(Element rootElement, String extendedPropertyKey) {
		Element extendedPropertyElement = null;

		List rootElementChildren = rootElement.getChildren();

		Element rootElementChild = null;
		List rsElementChildren = null;
		Element rsElementChild = null;
		String keyAttributeValue = null;
		String endWith = FdcMetaDataConstant.RS_TYPE_EXTENDED_PROPERTY + "." + extendedPropertyKey;
		Map resourceKeyMap = new LinkedHashMap();

		for (int i = 0, iMaxValue = rootElementChildren.size(); i < iMaxValue; i++) {
			rootElementChild = (Element) rootElementChildren.get(i);
			if (rootElementChild.getName().equals(FdcMetaDataConstant.TAG_RESOURCE)) {
				rsElementChildren = rootElementChild.getChildren();
				for (int j = 0, jMaxValue = rsElementChildren.size(); j < jMaxValue; j++) {
					rsElementChild = (Element) rsElementChildren.get(j);
					keyAttributeValue = rsElementChild.getAttributeValue(FdcMetaDataConstant.ATTRIBUTE_NAME_KEY);
					resourceKeyMap.put(keyAttributeValue, rsElementChild.getChildText("zh_CN"));

					if (keyAttributeValue.endsWith(endWith)) {
						// if (-1 != keyAttributeValue.lastIndexOf(endWith)) {
						extendedPropertyElement = rsElementChild;
						break;
					}
				}
			}
		}

		// if (!resourceKeyMap.isEmpty()) {
		// System.out.println("===========================================");
		// MapUtils.debugPrint(System.out, "resourceKeyMap", resourceKeyMap);
		// System.out.println("size = " + resourceKeyMap.size());
		// System.out.println("===========================================");
		// }

		return extendedPropertyElement;
	}

	/**
	 * ȡ����չ����XMLԪ��
	 * 
	 * @param metadataRootPath
	 * @param entityFullName
	 * @param extendedPropertyKey
	 * @return
	 */
	public static Element getExtendedPropertyElement(String metadataRootPath, String entityFullName,
			String extendedPropertyKey) {
		Element extendedPropertyElement = null;

		Element rootElement = getRootElement(metadataRootPath, entityFullName);
		extendedPropertyElement = getExtendedPropertyElement(rootElement, extendedPropertyKey);

		return extendedPropertyElement;
	}

	/**
	 * ȡ��defaultF7Query��չ����XMLԪ��
	 * 
	 * @param metadataRootPath
	 * @param entityFullName
	 * @return
	 */
	public static Element getDefaultF7QueryElement(String metadataRootPath, String entityFullName) {
		return getExtendedPropertyElement(metadataRootPath, entityFullName, FdcMetaDataConstant.DEFAULT_F7_QUERY_KEY);
	}

	/**
	 * ȡ����չ����XMLֵ
	 * 
	 * @param extendedPropertyElement
	 * @param locale
	 * @return
	 */
	public static String getExtendedPropertyValue(Element extendedPropertyElement, Locale locale) {
		String extendedPropertyValue = null;

		List langChildren = extendedPropertyElement.getChildren();
		for (Iterator iterator = langChildren.iterator(); iterator.hasNext();) {
			Element langElement = (Element) iterator.next();

			if (langElement.getAttributeValue(FdcMetaDataConstant.ATTRIBUTE_NAME_LOCALE).equals(locale.toString())) {
				extendedPropertyValue = langElement.getAttributeValue(FdcMetaDataConstant.ATTRIBUTE_NAME_VALUE);
				break;
			}
		}

		return extendedPropertyValue;
	}

	/**
	 * ����defaultF7Query��չ����
	 * 
	 * @param metadataRootPath
	 * @param packagePerfix
	 * @param baseClassz
	 * @param querySuffix
	 * @param templateEntityFullName
	 * @param isRepairOld
	 * @param isSave
	 * @return
	 */
	public static Map appendDefaultF7Query(String metadataRootPath, String packagePerfix, Class baseClassz,
			String querySuffix, String templateEntityFullName, boolean isRepairOld, boolean isSave) {
		Map entityMap = new LinkedHashMap();
		Map noImplEntityMap = new LinkedHashMap();
		Map notExistsEntityMap = new LinkedHashMap();
		Map errorEntityMap = new LinkedHashMap();

		EntityObjectCollection entityObjectCollection = MetaDataLoader.getEntityCollection(null);
		int size = entityObjectCollection.size();

		String businessImplName = null;
		Class businessImplClassz = null;

		String entityFullName = null;
		String entityFileFullName = null;

		Element rootElement = null;
		Element template_defaultF7QueryElement = FdcMetaDataUtil.getDefaultF7QueryElement(metadataRootPath,
				templateEntityFullName);

		for (int i = 0; i < size; i++) {
			EntityObjectInfo entityObjectInfo = entityObjectCollection.get(i);

			if (entityObjectInfo.getPackage().startsWith(packagePerfix)) {
				businessImplName = entityObjectInfo.getBusinessImplName();
				try {
					businessImplClassz = Class.forName(businessImplName);
				} catch (ClassNotFoundException e) {
					noImplEntityMap.put(entityObjectInfo.getFullName(), "��ҵ��ʵ�֣�" + businessImplName);

					e.printStackTrace();

				}
			} else {
				businessImplName = null;
				businessImplClassz = null;
				continue;
			}

			// �ų�������
			if (entityObjectInfo.isAbstract()) {
				businessImplName = null;
				businessImplClassz = null;
				continue;
			}

			if (null != businessImplClassz && baseClassz.isAssignableFrom(businessImplClassz)) {
				entityFullName = entityObjectInfo.getFullName();
				entityFileFullName = getEntityFileFullName(metadataRootPath, entityFullName);

				Document document = getDocument(metadataRootPath, entityFullName);
				if (null == document) {
					notExistsEntityMap.put(entityFullName, "�����д��ڣ���BOS͸��ͼ�в�����");
					continue;
				}
				rootElement = document.getRootElement();

				boolean changeFlag = false;
				Element defaultF7QueryElement = getExtendedPropertyElement(rootElement,
						FdcMetaDataConstant.DEFAULT_F7_QUERY_KEY);
				String extendedPropertyValue = null;
				// ��defaultF7Query��չ����
				if (null != defaultF7QueryElement) {
					extendedPropertyValue = getExtendedPropertyValue(defaultF7QueryElement, Locale.CHINA);

					// ��չ����ֵΪ�ջ��߲��Ϸ�
					if (StringUtils.isEmpty(extendedPropertyValue)
							|| !extendedPropertyValue.startsWith(FDCConstants.EAS_PAKAGE_NAME)) {
						setDefaultF7QueryValue(defaultF7QueryElement, entityFullName, querySuffix);

						changeFlag = true;
					} else if (isRepairOld) {
						IMetaDataPK queryMetaDataPK = new MetaDataPK(extendedPropertyValue);
						QueryInfo queryInfo = MetaDataLoader.getQuery(null, queryMetaDataPK);

						if (null == queryInfo) {
							setDefaultF7QueryValue(defaultF7QueryElement, entityFullName, querySuffix);

							changeFlag = true;
						}
					}
				} else {
					Element resourceElement = getSingleChild(rootElement, FdcMetaDataConstant.TAG_RESOURCE);

					defaultF7QueryElement = template_defaultF7QueryElement;
					setDefaultF7QueryValue(defaultF7QueryElement, entityFullName, querySuffix);

					try {
						resourceElement.addContent(defaultF7QueryElement);
						changeFlag = true;
					} catch (Exception e2) {
						errorEntityMap.put(entityObjectInfo.getFullName(), "�����ʵ��");

						e2.printStackTrace();
					}
				}

				if (changeFlag) {
					entityMap.put(entityFullName, extendedPropertyValue);

					if (isSave) {
						if (!FileUtil.isExist(entityFileFullName)) {
							try {
								com.kingdee.bos.metadata.upgrade.FileUtil.createFile(new File(entityFileFullName));
							} catch (IOException e) {
								e.printStackTrace();
							}
						}

						FdcXmlUtil.write(document, entityFileFullName);
					}
				}
			}
		}

		if (!entityMap.isEmpty()) {
			System.out.println("===========================================");
			MapUtils.debugPrint(System.out, null, entityMap);
			System.out.println("size = " + entityMap.size());
			System.out.println("===========================================");
		}
		if (!noImplEntityMap.isEmpty()) {
			System.out.println("===========================================");
			MapUtils.debugPrint(System.out, null, noImplEntityMap);
			System.out.println("size = " + noImplEntityMap.size());
			System.out.println("===========================================");
		}
		if (!notExistsEntityMap.isEmpty()) {
			System.out.println("===========================================");
			MapUtils.debugPrint(System.out, null, notExistsEntityMap);
			System.out.println("size = " + notExistsEntityMap.size());
			System.out.println("===========================================");
		}
		if (!errorEntityMap.isEmpty()) {
			System.out.println("===========================================");
			MapUtils.debugPrint(System.out, null, errorEntityMap);
			System.out.println("size = " + errorEntityMap.size());
			System.out.println("===========================================");
		}
		return entityMap;
	}

	/**
	 * ����defaultF7Query��չ����
	 * 
	 * @param metadataRootPath
	 * @param packagePerfix
	 * @param isSave
	 * @return
	 */
	public static Map appendDefaultF7Query(String metadataRootPath, String packagePerfix, boolean isSave) {
		Class baseClassz = FDCBill.class;
		String querySuffix = FdcMetaDataConstant.META_DATA_FILENAME_SUFFIX_QUERY;
		String templateEntityFullName = FdcSimpleMetaDataUtil.TEMPLATE_ENTITY_FULL_NAME;
		boolean isRepairOld = false;

		return appendDefaultF7Query(metadataRootPath, packagePerfix, baseClassz, querySuffix, templateEntityFullName,
				isRepairOld, isSave);
	}

	/**
	 * ����defaultF7Query��չ����
	 * 
	 * @param metadataRootPath
	 * @param packagePerfix
	 * @return
	 */
	public static Map appendDefaultF7Query(String metadataRootPath, String packagePerfix) {
		Class baseClassz = FDCBill.class;
		String querySuffix = FdcMetaDataConstant.META_DATA_FILENAME_SUFFIX_QUERY;
		String templateEntityFullName = FdcSimpleMetaDataUtil.TEMPLATE_ENTITY_FULL_NAME;
		boolean isRepairOld = false;
		boolean isSave = true;

		return appendDefaultF7Query(metadataRootPath, packagePerfix, baseClassz, querySuffix, templateEntityFullName,
				isRepairOld, isSave);
	}

	/**
	 * ȡ��XMLԪ�صĵ�����
	 * 
	 * @param element
	 * @param tagName
	 * @return
	 */
	private static Element getSingleChild(Element element, String tagName) {
		Element elementChild = null;

		List elementChildren = element.getChildren();
		Element tempElementChild = null;
		for (int i = 0, iMaxValue = elementChildren.size(); i < iMaxValue; i++) {
			tempElementChild = (Element) elementChildren.get(i);

			if (tempElementChild.getName().equals(tagName)) {
				break;
			} else {
				tempElementChild = null;
			}
		}
		elementChild = tempElementChild;

		return elementChild;
	}

	/**
	 * ������չ����ֵ
	 * 
	 * @param extendedPropertyElement
	 * @param entityFullName
	 * @param value
	 */
	public static void setExtendedPropertyValue(Element extendedPropertyElement, String entityFullName, String value) {
		Attribute valueAttribute = null;

		String extendedPropertyKey = extendedPropertyElement.getAttributeValue(FdcMetaDataConstant.ATTRIBUTE_NAME_KEY);
		if (-1 == extendedPropertyKey.indexOf(entityFullName)) {
			int firstIndex = extendedPropertyKey.indexOf("[");
			int secondIndex = extendedPropertyKey.indexOf("]");

			extendedPropertyKey = extendedPropertyKey.substring(0, firstIndex) + "[" + entityFullName + "]"
					+ extendedPropertyKey.substring(secondIndex + 1);

			extendedPropertyElement.setAttribute(FdcMetaDataConstant.ATTRIBUTE_NAME_KEY, extendedPropertyKey);
		}

		List children = extendedPropertyElement.getChildren();
		Element child = null;
		for (int i = 0, iMaxValue = children.size(); i < iMaxValue; i++) {
			child = ((Element) children.get(i));

			valueAttribute = child.getAttribute(FdcMetaDataConstant.ATTRIBUTE_NAME_VALUE);
			valueAttribute.setValue(value);
		}
	}

	/**
	 * ����defaultF7Query��չ����ֵ
	 * 
	 * @param extendedPropertyElement
	 * @param entityFullName
	 * @param querySuffix
	 */
	public static void setDefaultF7QueryValue(Element extendedPropertyElement, String entityFullName, String querySuffix) {
		QueryInfo queryInfo = getQueryInfo(entityFullName, querySuffix);

		if (null != queryInfo) {
			String queryFullName = queryInfo.getFullName();
			setExtendedPropertyValue(extendedPropertyElement, entityFullName, queryFullName);
		}
	}

	/**
	 * ȡ�ò�ѯʵ�����
	 * 
	 * @param entityFullName
	 * @param querySuffix
	 * @return
	 */
	public static QueryInfo getQueryInfo(String entityFullName, String querySuffix) {
		QueryInfo queryInfo = null;

		String queryFullName = entityFullName + querySuffix;
		IMetaDataPK queryMetaDataPK = new MetaDataPK(queryFullName);
		queryInfo = MetaDataLoader.getQuery(null, queryMetaDataPK);

		if (null == queryInfo) {
			if (entityFullName.endsWith(FDCConstants.BILL_SUFFIX)) {
				int lastIndex = entityFullName.lastIndexOf(FDCConstants.BILL_SUFFIX);
				queryFullName = entityFullName.substring(0, lastIndex) + querySuffix;
			}

			queryMetaDataPK = new MetaDataPK(queryFullName);
			queryInfo = MetaDataLoader.getQuery(null, queryMetaDataPK);
		}

		return queryInfo;
	}

	/**
	 * ȡ������List
	 * 
	 * @param ctx
	 *            Ӧ�������ģ�����Ϊ��
	 * @param bOSObjectType
	 *            BOS��������_�ַ���������Ϊ��
	 * @return
	 */
	public static List getPropertyList(Context ctx, String bOSObjectType) {
		List propertyList = null;
		
		propertyList = (List) EPROPERTY_NAME_LIST_MAP.get(bOSObjectType);
		if (null == propertyList) {
			// ����bosType���봴��BOSObjectType
			BOSObjectType objectType = BOSObjectType.create(bOSObjectType);
			// ����bosTypeȡ�ö�Ӧ��ʵ��
			EntityObjectInfo entityObjectInfo = MetaDataLoader.getEntity(ctx, objectType);
			// ȡ��ʵ����������������Լ��̳���������, ��ȥ���ظ�������(�����������ж�)
			PropertyCollection propertyCollection = entityObjectInfo.getInheritedNoDuplicatedPropertiesRuntime();

			if (FdcObjectCollectionUtil.isNotEmpty(propertyCollection)) {
				// �����󼯺� ת����List
				propertyList = FdcObjectCollectionUtil.toList(propertyCollection);

				// �����������ܳ���CACHE_MAP_MAX_SIZE
				if (EPROPERTY_NAME_LIST_MAP.size() >= CACHE_MAP_MAX_SIZE) {
					String[] keyArr = (String[]) EPROPERTY_NAME_LIST_MAP.keySet().toArray(new String[0]);
					String lastKey = keyArr[keyArr.length - 1];
					EPROPERTY_NAME_LIST_MAP.remove(lastKey);
				}
				EPROPERTY_NAME_LIST_MAP.put(bOSObjectType, propertyList);
			}
		}

		if (null == propertyList) {
			propertyList = new ArrayList();
		}

		return propertyList;
	}

	/**
	 * ȡ��������List
	 * 
	 * @param ctx
	 *            Ӧ�������ģ�����Ϊ��
	 * @param bOSObjectType
	 *            BOS��������_�ַ���������Ϊ��
	 * @return
	 */
	public static List getPropertyNameList(Context ctx, String bOSObjectType) {
		List propertyNameList = new ArrayList();

		// ȡ������List
		List propertyList = getPropertyList(ctx, bOSObjectType);
		// ȡ��������List
		propertyNameList = getPropertyNameList(propertyList.iterator());

		return propertyNameList;
	}

	/**
	 * ȡ��������List
	 * 
	 * @param iterator
	 *            ���Լ��ϵ�����
	 * @return
	 */
	public static List getPropertyNameList(Iterator iterator) {
		List propertyNameList = new ArrayList();

		PropertyInfo propertyInfo = null;
		String propertyName = null;

		for (; iterator.hasNext();) {
			propertyInfo = (PropertyInfo) iterator.next();
			propertyName = propertyInfo.getName();

			propertyNameList.add(propertyName);
		}

		return propertyNameList;
	}

	/**
	 * ȡ����������List
	 * 
	 * @param ctx
	 *            Ӧ�������ģ�����Ϊ��
	 * @param bOSObjectType
	 *            BOS��������_�ַ���������Ϊ��
	 * @return
	 */
	public static List getOwnPropertyList(Context ctx, String bOSObjectType) {
		List propertyList = null;

		propertyList = (List) OWN_EPROPERTY_NAME_LIST_MAP.get(bOSObjectType);
		if (null == propertyList) {
			// ����bosType���봴��BOSObjectType
			BOSObjectType objectType = BOSObjectType.create(bOSObjectType);
			// ����bosTypeȡ�ö�Ӧ��ʵ��
			EntityObjectInfo entityObjectInfo = MetaDataLoader.getEntity(ctx, objectType);
			// ȡ��ʵ����������������Լ��̳���������, ��ȥ���ظ�������(�����������ж�)
			PropertyCollection propertyCollection = entityObjectInfo.getInheritedNoDuplicatedPropertiesRuntime();

			if (FdcObjectCollectionUtil.isNotEmpty(propertyCollection)) {
				// �����󼯺� ת����List
				for (Iterator iterator = propertyCollection.iterator(); iterator.hasNext();) {
					PropertyInfo propertyInfo = (PropertyInfo) iterator.next();

					if (!(propertyInfo instanceof LinkPropertyInfo)) {
						propertyList.add(propertyInfo);
					}
				}

				// �����������ܳ���CACHE_MAP_MAX_SIZE
				if (OWN_EPROPERTY_NAME_LIST_MAP.size() >= CACHE_MAP_MAX_SIZE) {
					String[] keyArr = (String[]) OWN_EPROPERTY_NAME_LIST_MAP.keySet().toArray(new String[0]);
					String lastKey = keyArr[keyArr.length - 1];
					OWN_EPROPERTY_NAME_LIST_MAP.remove(lastKey);
				}
				OWN_EPROPERTY_NAME_LIST_MAP.put(bOSObjectType, propertyList);
			}
		}

		if (null == propertyList) {
			propertyList = new ArrayList();
		}

		return propertyList;
	}

	/**
	 * ȡ������������List
	 * 
	 * @param ctx
	 *            Ӧ�������ģ�����Ϊ��
	 * @param bOSObjectType
	 *            BOS��������_�ַ���������Ϊ��
	 * @return
	 */
	public static List getOwnPropertyNameList(Context ctx, String bOSObjectType) {
		List propertyNameList = new ArrayList();

		// ȡ������List
		List propertyList = getOwnPropertyList(ctx, bOSObjectType);
		// ȡ��������List
		propertyNameList = getOwnPropertyNameList(propertyList.iterator());

		return propertyNameList;
	}

	/**
	 * ȡ������������List
	 * 
	 * @param iterator
	 *            ���Լ��ϵ�����
	 * @return
	 */
	public static List getOwnPropertyNameList(Iterator iterator) {
		List propertyNameList = new ArrayList();

		PropertyInfo propertyInfo = null;
		String propertyName = null;

		for (; iterator.hasNext();) {
			propertyInfo = (PropertyInfo) iterator.next();
			propertyName = propertyInfo.getName();

			propertyNameList.add(propertyName);
		}

		return propertyNameList;
	}

	/**
	 * ȡ����������List
	 * 
	 * @param ctx
	 *            Ӧ�������ģ�����Ϊ��
	 * @param bOSObjectType
	 *            BOS��������_�ַ���������Ϊ��
	 * @return
	 */
	public static List getLinkPropertyList(Context ctx, String bOSObjectType) {
		List propertyList = null;

		propertyList = (List) LINK_EPROPERTY_NAME_LIST_MAP.get(bOSObjectType);
		if (null == propertyList) {
			// ����bosType���봴��BOSObjectType
			BOSObjectType objectType = BOSObjectType.create(bOSObjectType);
			// ����bosTypeȡ�ö�Ӧ��ʵ��
			EntityObjectInfo entityObjectInfo = MetaDataLoader.getEntity(ctx, objectType);
			// ȡ��ʵ����������������Լ��̳���������, ��ȥ���ظ�������(�����������ж�)
			PropertyCollection propertyCollection = entityObjectInfo.getInheritedNoDuplicatedPropertiesRuntime();

			if (FdcObjectCollectionUtil.isNotEmpty(propertyCollection)) {
				// �����󼯺� ת����List
				for (Iterator iterator = propertyCollection.iterator(); iterator.hasNext();) {
					PropertyInfo propertyInfo = (PropertyInfo) iterator.next();

					if (propertyInfo instanceof LinkPropertyInfo) {
						propertyList.add(propertyInfo);
					}
				}

				// �����������ܳ���CACHE_MAP_MAX_SIZE
				if (LINK_EPROPERTY_NAME_LIST_MAP.size() >= CACHE_MAP_MAX_SIZE) {
					String[] keyArr = (String[]) LINK_EPROPERTY_NAME_LIST_MAP.keySet().toArray(new String[0]);
					String lastKey = keyArr[keyArr.length - 1];
					LINK_EPROPERTY_NAME_LIST_MAP.remove(lastKey);
				}
				LINK_EPROPERTY_NAME_LIST_MAP.put(bOSObjectType, propertyList);
			}
		}

		if (null == propertyList) {
			propertyList = new ArrayList();
		}

		return propertyList;
	}

	/**
	 * ȡ������������List
	 * 
	 * @param ctx
	 *            Ӧ�������ģ�����Ϊ��
	 * @param bOSObjectType
	 *            BOS��������_�ַ���������Ϊ��
	 * @return
	 */
	public static List getLinkPropertyNameList(Context ctx, String bOSObjectType) {
		List propertyNameList = new ArrayList();

		// ȡ������List
		List propertyList = getLinkPropertyList(ctx, bOSObjectType);
		// ȡ��������List
		propertyNameList = getLinkPropertyNameList(propertyList.iterator());

		return propertyNameList;
	}

	/**
	 * ȡ������������List
	 * 
	 * @param iterator
	 *            ���Լ��ϵ�����
	 * @return
	 */
	public static List getLinkPropertyNameList(Iterator iterator) {
		List propertyNameList = new ArrayList();

		PropertyInfo propertyInfo = null;
		String propertyName = null;

		for (; iterator.hasNext();) {
			propertyInfo = (PropertyInfo) iterator.next();
			propertyName = propertyInfo.getName();

			propertyNameList.add(propertyName);
		}

		return propertyNameList;
	}

	/**
	 * ȡ����List
	 * 
	 * @param ctx
	 *            Ӧ�������ģ�����Ϊ��
	 * @param bOSObjectType
	 *            BOS��������_�ַ���������Ϊ��
	 * @param isSortByProperty
	 *            �Ƿ������������
	 * @return
	 */
	public static List getColumnList(Context ctx, String bOSObjectType, boolean isSortByProperty) {
		List columnList = new ArrayList();

		BOSObjectType objectType = BOSObjectType.create(bOSObjectType);
		EntityObjectInfo entityObjectInfo = MetaDataLoader.getEntity(null, objectType);
		DataTableInfo dataTableInfo = entityObjectInfo.getTable();
		ColumnCollection columnCollection = dataTableInfo.getColumns();
		ColumnInfo columnInfo = null;
		String columnName = null;
		Map columnNameMap = new LinkedHashMap();

		for (Iterator iterator = columnCollection.iterator(); iterator.hasNext();) {
			columnInfo = (ColumnInfo) iterator.next();

			columnName = columnInfo.getName().toUpperCase();
			columnNameMap.put(columnName, columnInfo);
		}

		// ������������
		if (isSortByProperty) {
			List propertyList = getPropertyList(ctx, bOSObjectType);
			PropertyInfo propertyInfo = null;
			String propertyName = null;
			Set mappingColumnNameSet = new LinkedHashSet();

			for (Iterator iterator = propertyList.iterator(); iterator.hasNext();) {
				propertyInfo = (PropertyInfo) iterator.next();
				propertyName = propertyInfo.getName();

				// �ּ���ӳ�����
				columnInfo = propertyInfo.getMappingField();
				if (null != columnInfo) {
					columnName = columnInfo.getName().toUpperCase();
					mappingColumnNameSet.add(columnName);
				}
			}
			for (Iterator iterator = mappingColumnNameSet.iterator(); iterator.hasNext();) {
				columnName = (String) iterator.next();

				// �����ӳ�����
				columnInfo = (ColumnInfo) columnNameMap.get(columnName);
				columnList.add(columnInfo);
				columnNameMap.remove(columnName);
			}
			// ˳��ת��
			Collections.reverse(columnList);

			Set columnNameSet = columnNameMap.keySet();
			for (Iterator iterator = columnNameSet.iterator(); iterator.hasNext();) {
				columnName = (String) iterator.next();

				// ���û����ӳ�����
				columnInfo = (ColumnInfo) columnNameMap.get(columnName);
				columnList.add(columnInfo);
			}
		}
		// ������
		else {
			columnList.addAll(columnNameMap.values());
		}

		return columnList;
	}

	/**
	 * ȡ������List
	 * 
	 * @param ctx
	 *            Ӧ�������ģ�����Ϊ��
	 * @param bOSObjectType
	 *            BOS��������_�ַ���������Ϊ��
	 * @param isSortByProperty
	 *            �Ƿ������������
	 * @return
	 */
	public static List getColumnNameList(Context ctx, String bOSObjectType, boolean isSortByProperty) {
		List columnNameList = new ArrayList();

		// ȡ����List
		List columnList = getColumnList(ctx, bOSObjectType, isSortByProperty);
		// ȡ������List
		columnNameList = getColumnNameList(columnList.iterator());

		return columnNameList;
	}

	/**
	 * ȡ������List
	 * 
	 * @param iterator
	 *            �м��ϵ�����
	 * @return
	 */
	public static List getColumnNameList(Iterator iterator) {
		List columnNameList = new ArrayList();

		ColumnInfo columnInfo = null;
		String columnName = null;

		for (; iterator.hasNext();) {
			columnInfo = (ColumnInfo) iterator.next();
			columnName = columnInfo.getName();

			columnNameList.add(columnName);
		}

		return columnNameList;
	}

	/**
	 * ȡ����ͬ������List
	 * 
	 * @param ctx
	 *            Ӧ�������ģ�����Ϊ��
	 * @param bOSObjectType
	 *            BOS��������_�ַ���������Ϊ��
	 * @param isSortByProperty
	 *            �Ƿ������������
	 * @return
	 */
	public static List getSameColumnNameList(Context ctx, String bOSObjectType1, String bOSObjectType2,
			boolean isSortByProperty) {
		List columnNameList = new ArrayList();

		// ȡ������List
		List columnNameList1 = getColumnNameList(ctx, bOSObjectType1, isSortByProperty);
		List columnNameList2 = getColumnNameList(ctx, bOSObjectType2, isSortByProperty);

		Map columnNameMap1 = new LinkedHashMap();
		Map columnNameMap2 = new LinkedHashMap();

		String columnName = null;
		for (Iterator iterator = columnNameList1.iterator(); iterator.hasNext();) {
			columnName = (String) iterator.next();
			columnNameMap1.put(columnName.toUpperCase(), columnName);
		}
		for (Iterator iterator = columnNameList2.iterator(); iterator.hasNext();) {
			columnName = (String) iterator.next();
			columnNameMap2.put(columnName.toUpperCase(), columnName);
		}

		Set columnNameSet1 = columnNameMap1.keySet();
		Set columnNameSet2 = columnNameMap2.keySet();
		columnNameSet1.retainAll(columnNameSet2);

		if (FdcCollectionUtil.isNotEmpty(columnNameSet1)) {
			for (Iterator iterator = columnNameSet1.iterator(); iterator.hasNext();) {
				columnName = (String) iterator.next();
				columnName = (String) columnNameMap1.get(columnName);
				columnNameList.add(columnName);
			}
		}

		return columnNameList;
	}

	/**
	 * ȡ�÷����MetaDataPK��Ӧ��model����
	 * 
	 * @param appFullName
	 * @return
	 */
	public static String getModelPackageName(IMetaDataPK appMetaDataPK, boolean isApp) {
		String modelPackageName = null;

		String subPackage = isApp ? "app" : "client";
		modelPackageName = getModelPackageName(appMetaDataPK, subPackage);

		return modelPackageName;
	}

	/**
	 * ȡ�÷����MetaDataPK��Ӧ��model����
	 * 
	 * @param appFullName
	 * @return
	 */
	public static String getModelPackageName(IMetaDataPK appMetaDataPK) {
		return getModelPackageName(appMetaDataPK, "app");
	}

	/**
	 * ȡ��MetaDataPK��Ӧ��model����
	 * 
	 * @param metaDataPK
	 * @param subPackage
	 *            �Ӱ�����ֻ������������ֵ��app, client, null
	 * @return
	 */
	private static String getModelPackageName(IMetaDataPK metaDataPK, String subPackage) {
		String modelPackageName = null;

		// ȡ��MetaDataPK����ģ�����
		String metaDataPackageName = metaDataPK.getPackage();

		if ("app".equals(subPackage)) {
			if (!metaDataPackageName.endsWith(".app")) {
				throw new RuntimeException(metaDataPK + "�����Ƿ����Ԫ����!");
			} else {
				int appIndex = metaDataPackageName.lastIndexOf(".app");
				modelPackageName = metaDataPackageName.substring(0, appIndex);
			}
		} else if ("client".equals(subPackage)) {
			if (!metaDataPackageName.endsWith(".client")) {
				throw new RuntimeException(metaDataPK + "�����ǿͻ���Ԫ����!");
			} else {
				int appIndex = metaDataPackageName.lastIndexOf(".client");
				modelPackageName = metaDataPackageName.substring(0, appIndex);
			}
		} else if (null == subPackage) {
			modelPackageName = metaDataPackageName;
		} else {
			List subPackageList = Arrays.asList(new String[] { "app", "client", null });
			throw new RuntimeException(subPackage + "�����Ϸ��Ĳ�����ֻ����" + subPackageList + "֮һ!");
		}

		return modelPackageName;
	}

	/**
	 * ���� ��_���� ӳ��
	 * 
	 * @param tableNameArr
	 *            ������
	 * @param indexPrefix
	 *            ����ǰ׺������Ϊ�գ�Ĭ��Ϊ"IX_FDC_"
	 * @return
	 */
	public static Map generateTableIndexMap(String[] tableNameArr, String indexPrefix) {
		Map tableIndexMap = new TreeMap();

		String tableName = null;
		String[] tableSubArr = null;
		String indexName = null;
		List tableNameList = null;
		int entityIndex = 4;
		int len = tableNameArr.length;
		int subLen = 0;
		// ����ǰ׺;����Ϊ�գ�Ĭ��Ϊ"IX_FDC_"
		indexPrefix = FdcObjectUtil.defaultIfNull(indexPrefix, "IX_FDC_");

		Map indexTableMap = new LinkedHashMap();
		for (int i = 0; i < len; i++) {
			tableName = tableNameArr[i];
			// �����ַ����͡��շ�״̬����ַ���
			tableSubArr = FdcStringUtil.splitByCharacterTypeCamelCase(tableName);
			subLen = tableSubArr.length;

			indexName = indexPrefix + tableSubArr[entityIndex];
			for (int j = entityIndex + 1; j < subLen; j++) {
				indexName += tableSubArr[j].charAt(0);
			}

			tableNameList = (List) indexTableMap.get(indexName);
			if (null == tableNameList) {
				tableNameList = new ArrayList();
				indexTableMap.put(indexName, tableNameList);
			}

			tableNameList.add(tableName);
		}

		Set indexTableSet = indexTableMap.keySet();
		int size = 0;
		for (Iterator iterator = indexTableSet.iterator(); iterator.hasNext();) {
			indexName = (String) iterator.next();
			tableNameList = (List) indexTableMap.get(indexName);
			size = tableNameList.size();

			for (int i = 0; i < size; i++) {
				tableName = (String) tableNameList.get(i);
				if (i == 0) {
					tableIndexMap.put(tableName, indexName);
				} else {
					tableIndexMap.put(tableName, indexName + i);
				}
			}
		}

		return tableIndexMap;
	}

	/**
	 * �Ƿ����Ԫ����
	 * 
	 * @param metadataRootPath
	 * @param fullName
	 * @param extName
	 * @return
	 */
	public static boolean exists(String metadataRootPath, String fullName, String extName) {
		boolean flag = false;

		// ȡ��Ԫ�����ļ���������
		String bizUnitFileName = getMetaDataFileFullName(metadataRootPath, fullName, extName);
		File bizUnitFile = new File(bizUnitFileName);
		// �Ѿ����ڣ�ֱ�Ӻ���
		flag = bizUnitFile.exists();

		return flag;
	}

	/**
	 * �Ƿ����Ԫ����
	 * 
	 * @param metadataRootPath
	 * @param metaDataPK
	 * @param extName
	 * @return
	 */
	public static boolean exists(String metadataRootPath, IMetaDataPK metaDataPK, String extName) {
		boolean flag = false;

		flag = exists(metadataRootPath, metaDataPK.getFullName(), extName);

		return flag;
	}
	
	// //////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////

	/**
	 * ��ӡ���ʵ���ӳ��
	 * 
	 * @param ctx
	 *            Ӧ�������ģ�����Ϊ��
	 * @param packagePerfix
	 *            ��ǰ׺������Ϊ��
	 * @return
	 */
	public static void printWriteEntityTable(Context ctx, String packagePerfix) {
		String msg = "��ǰ׺ ����Ϊ��";
		Assert.assertNotNull(msg, packagePerfix);

		// //////////////////////////////////////////////////////////////

		IMetaDataLoader metaDataLoader = getMetaDataLoader(ctx);
		EntityObjectCollection entityObjectCollection = metaDataLoader.getEntityCollection();

		EntityObjectInfo entityObjectInfo = null;
		String packageName = null;
		EntityObjectCollection tempEntityObjectCollection = new EntityObjectCollection();

		for (Iterator iterator = entityObjectCollection.iterator(); iterator.hasNext();) {
			entityObjectInfo = (EntityObjectInfo) iterator.next();

			packageName = entityObjectInfo.getPackage();
			if (packageName.startsWith(packagePerfix)) {
				tempEntityObjectCollection.add(entityObjectInfo);
			}
		}

		// //////////////////////////////////////////////////////////////

		// ��ӡʵ���ӳ��
		FdcMetaDataUtil.printWriteEntityTable(tempEntityObjectCollection);
	}

	/**
	 * ��ӡ���ʵ���ӳ��
	 * 
	 * @param ctx
	 *            Ӧ�������ģ�����Ϊ��
	 * @param bOSObjectTypeIterator
	 *            BOS�������ͼ��ϵ�����������Ϊ��
	 * @return
	 */
	public static void printWriteEntityTable(Context ctx, Iterator bOSObjectTypeIterator) {
		String msg = "BOS�������ͼ��ϵ����� ����Ϊ��";
		Assert.assertNotNull(msg, bOSObjectTypeIterator);

		// //////////////////////////////////////////////////////////////

		IMetaDataLoader metaDataLoader = getMetaDataLoader(ctx);

		BOSObjectType bOSObjectType = null;
		EntityObjectInfo entityObjectInfo = null;
		EntityObjectCollection entityObjectCollection = new EntityObjectCollection();
		for (; bOSObjectTypeIterator.hasNext();) {
			bOSObjectType = (BOSObjectType) bOSObjectTypeIterator.next();
			entityObjectInfo = metaDataLoader.getEntity(bOSObjectType);

			if (null != entityObjectInfo) {
				entityObjectCollection.add(entityObjectInfo);
			}
		}

		// //////////////////////////////////////////////////////////////

		// ��ӡʵ���ӳ��
		FdcMetaDataUtil.printWriteEntityTable(entityObjectCollection);
	}

	/**
	 * ��ӡ���ʵ���ӳ��
	 * 
	 * @param entityObjectCollection
	 *            �������ͼ��ϣ�����Ϊ��
	 * @return
	 */
	private static void printWriteEntityTable(EntityObjectCollection entityObjectCollection) {
		if (FdcObjectCollectionUtil.isEmpty(entityObjectCollection)) {
			return;
		}

		// //////////////////////////////////////////////////////////////

		EntityObjectInfo entityObjectInfo = null;
		String packageName = null;
		String entityName = null;
		String entityAlias = null;
		String bosTypeStr = null;

		DataTableInfo dataTableInfo = null;
		String dataTableName = null;

		PropertyInfo propertyInfo = null;
		String propertyName = null;
		String propertyAlias = null;

		PropertyCollection propertyCollection = null;
		ColumnInfo columnInfo = null;
		String columnName = null;
		String sqlTypeAlias = null;
		String columnLength = null;
		String columnPrecision = null;
		String columnScale = null;

		StringBuffer lineStringBuffer = null;
		List lineList = new ArrayList();

		// //////////////////////////////////////////////////////////////

		lineStringBuffer = new StringBuffer();
		lineStringBuffer.append("����").append("~!");
		lineStringBuffer.append("ʵ����").append("~!");
		lineStringBuffer.append("ʵ�����").append("~!");
		lineStringBuffer.append("BOS��������").append("~!");
		lineStringBuffer.append("���ݿ����").append("~!");
		lineStringBuffer.append("������").append("~!");
		lineStringBuffer.append("���Ա���").append("~!");
		lineStringBuffer.append("����").append("~!");
		lineStringBuffer.append("SQL���ͱ���").append("~!");
		lineStringBuffer.append("����").append("~!");
		lineStringBuffer.append("����").append("~!");
		lineStringBuffer.append("С��λ��").append("~!");

		lineList.add(lineStringBuffer.toString());

		// //////////////////////////////////////////////////////////////

		for (Iterator iterator = entityObjectCollection.iterator(); iterator.hasNext();) {
			entityObjectInfo = (EntityObjectInfo) iterator.next();

			packageName = entityObjectInfo.getPackage();
			entityName = entityObjectInfo.getName();
			entityAlias = FdcObjectUtil.defaultIfNull(entityObjectInfo.getAlias(), "");
			bosTypeStr = entityObjectInfo.getType().toString();

			dataTableInfo = entityObjectInfo.getTable();
			if (null != dataTableInfo) {
				dataTableName = dataTableInfo.getName();
				// ȡ��ʵ����������������Լ��̳���������, ��ȥ���ظ�������(�����������ж�)
				propertyCollection = entityObjectInfo.getInheritedNoDuplicatedPropertiesRuntime();

				for (Iterator iterator2 = propertyCollection.iterator(); iterator2.hasNext();) {
					propertyInfo = (PropertyInfo) iterator2.next();
					propertyName = propertyInfo.getName();
					propertyAlias = FdcObjectUtil.defaultIfNull(propertyInfo.getAlias(), "");

					columnInfo = propertyInfo.getMappingField();
					if (null != columnInfo) {
						columnName = columnInfo.getName();
						sqlTypeAlias = columnInfo.getTypeName().getAlias();
						columnLength = columnInfo.getLength() + "";
						columnPrecision = columnInfo.getPrecision() + "";
						columnScale = columnInfo.getScale() + "";
					} else {
						columnName = "";
						sqlTypeAlias = "";
						columnLength = "";
						columnPrecision = "";
						columnScale = "";
					}

					lineStringBuffer = new StringBuffer();
					lineStringBuffer.append(packageName).append("~!");
					lineStringBuffer.append(entityName).append("~!");
					lineStringBuffer.append(entityAlias).append("~!");
					lineStringBuffer.append(bosTypeStr).append("~!");
					lineStringBuffer.append(dataTableName).append("~!");
					lineStringBuffer.append(propertyName).append("~!");
					lineStringBuffer.append(propertyAlias).append("~!");
					lineStringBuffer.append(columnName).append("~!");
					lineStringBuffer.append(sqlTypeAlias).append("~!");
					lineStringBuffer.append(columnLength).append("~!");
					lineStringBuffer.append(columnPrecision).append("~!");
					lineStringBuffer.append(columnScale).append("~!");

					lineList.add(lineStringBuffer.toString());
				}
			}
		}

		// //////////////////////////////////////////////////////////////

		if (FdcCollectionUtil.isNotEmpty(lineList)) {
			String fileName = "D:/Temp/Generate/EntityTable/ʵ����ֵ�.txt";
			File file = new File(fileName);

			try {
				FdcFileUtil.writeLines(file, lineList);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// //////////////////////////////////////////////////////////////
	}

	// //////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////

	/**
	 * ����orgType��չ����ֵ
	 * 
	 * @param extendedPropertyElement
	 * @param entityFullName
	 */
	public static void setOrgTypeValue(Element extendedPropertyElement, String entityFullName) {
		// ����ֵ
		String value = "Transport";
		setExtendedPropertyValue(extendedPropertyElement, entityFullName, value);
	}

	/**
	 * ����orgType��չ����
	 * 
	 * @param metadataRootPath
	 * @param packagePerfix
	 * @param baseClassz
	 * @param orgTypeValue
	 * @param templateEntityFullName
	 * @param isRepairOld
	 * @param isSave
	 * @return
	 */
	public static Map appendOrgType(String metadataRootPath, String packagePerfix, Class baseClassz,
			String orgTypeValue, String templateEntityFullName, boolean isRepairOld, boolean isSave) {
		String msg = "��֯����ֵ����Ϊ��";
		// ֵУ��
		Assert.assertNotNull(msg, orgTypeValue);
		msg = orgTypeValue + " �ǲ��Ϸ���ֵ������Ϊ��" + FdcMetaDataConstant.ORG_TYPE_SET;
		// ֵУ��
		Assert.assertTrue(msg, FdcMetaDataConstant.ORG_TYPE_SET.contains(orgTypeValue));

		Map entityMap = new LinkedHashMap();
		Map noImplEntityMap = new LinkedHashMap();
		Map notExistsEntityMap = new LinkedHashMap();
		Map errorEntityMap = new LinkedHashMap();

		EntityObjectCollection entityObjectCollection = MetaDataLoader.getEntityCollection(null);
		int size = entityObjectCollection.size();

		String businessImplName = null;
		Class businessImplClassz = null;

		String entityFullName = null;
		String entityFileFullName = null;

		Element rootElement = null;
		Element template_orgTypeElement = getExtendedPropertyElement(metadataRootPath, templateEntityFullName,
				FdcMetaDataConstant.ORG_TYPE_KEY);

		for (int i = 0; i < size; i++) {
			EntityObjectInfo entityObjectInfo = entityObjectCollection.get(i);

			if (entityObjectInfo.getPackage().startsWith(packagePerfix)) {
				businessImplName = entityObjectInfo.getBusinessImplName();
				try {
					businessImplClassz = Class.forName(businessImplName);
				} catch (ClassNotFoundException e) {
					noImplEntityMap.put(entityObjectInfo.getFullName(), "��ҵ��ʵ�֣�" + businessImplName);

					e.printStackTrace();
				}
			} else {
				businessImplName = null;
				businessImplClassz = null;
				continue;
			}

			// �ų�������
			if (entityObjectInfo.isAbstract()) {
				businessImplName = null;
				businessImplClassz = null;
				continue;
			}

			if (null != businessImplClassz && baseClassz.isAssignableFrom(businessImplClassz)) {
				entityFullName = entityObjectInfo.getFullName();
				entityFileFullName = getEntityFileFullName(metadataRootPath, entityFullName);

				Document document = getDocument(metadataRootPath, entityFullName);
				if (null == document) {
					notExistsEntityMap.put(entityFullName, "�����д��ڣ���BOS͸��ͼ�в�����");
					continue;
				}
				rootElement = document.getRootElement();

				boolean changeFlag = false;
				Element orgTypeElement = getExtendedPropertyElement(rootElement, FdcMetaDataConstant.ORG_TYPE_KEY);
				String extendedPropertyValue = null;
				// ��orgType��չ����
				if (null != orgTypeElement) {
					extendedPropertyValue = getExtendedPropertyValue(orgTypeElement, Locale.CHINA);

					// ��չ����ֵΪ�ջ��߲��Ϸ�
					if (StringUtils.isEmpty(extendedPropertyValue)
							|| !FdcMetaDataConstant.ORG_TYPE_SET.contains(extendedPropertyValue)) {
						setOrgTypeValue(orgTypeElement, entityFullName);

						changeFlag = true;
					} else if (isRepairOld) {
						if (!FdcMetaDataConstant.ORG_TYPE_SET.contains(extendedPropertyValue)) {
						}

						setOrgTypeValue(orgTypeElement, entityFullName);
						changeFlag = true;
					}
				} else {
					Element resourceElement = getSingleChild(rootElement, FdcMetaDataConstant.TAG_RESOURCE);

					orgTypeElement = template_orgTypeElement;
					setOrgTypeValue(orgTypeElement, entityFullName);

					try {
						resourceElement.addContent(orgTypeElement);
						changeFlag = true;
					} catch (Exception e2) {
						errorEntityMap.put(entityObjectInfo.getFullName(), "�����ʵ��");

						e2.printStackTrace();
					}

				}

				if (changeFlag) {
					entityMap.put(entityFullName, extendedPropertyValue);

					if (isSave) {
						if (!FileUtil.isExist(entityFileFullName)) {
							try {
								com.kingdee.bos.metadata.upgrade.FileUtil.createFile(new File(entityFileFullName));
							} catch (IOException e) {
								e.printStackTrace();
							}
						}

						FdcXmlUtil.write(document, entityFileFullName);
					}
				}
			}
		}

		if (!entityMap.isEmpty()) {
			System.out.println("===========================================");
			MapUtils.debugPrint(System.out, "ʵ��Map", entityMap);
			System.out.println("size = " + entityMap.size());
			System.out.println("===========================================");
		}
		if (!noImplEntityMap.isEmpty()) {
			System.out.println("===========================================");
			MapUtils.debugPrint(System.out, "��ҵ��ʵ��ʵ��", noImplEntityMap);
			System.out.println("size = " + noImplEntityMap.size());
			System.out.println("===========================================");
		}
		if (!notExistsEntityMap.isEmpty()) {
			System.out.println("===========================================");
			MapUtils.debugPrint(System.out, "BOS͸��ͼ�в����ڵ�ʵ��", notExistsEntityMap);
			System.out.println("size = " + notExistsEntityMap.size());
			System.out.println("===========================================");
		}
		if (!errorEntityMap.isEmpty()) {
			System.out.println("===========================================");
			MapUtils.debugPrint(System.out, "�����ʵ��", errorEntityMap);
			System.out.println("size = " + errorEntityMap.size());
			System.out.println("===========================================");
		}

		return entityMap;
	}

	/**
	 * ����orgType��չ����
	 * 
	 * @param metadataRootPath
	 * @param packagePerfix
	 * @param isSave
	 * @return
	 */
	public static Map appendOrgType(String metadataRootPath, String packagePerfix, boolean isSave) {
		Class baseClassz = FDCBill.class;
		String orgTypeValue = "Transport";

		String templateEntityFullName = FdcSimpleMetaDataUtil.TEMPLATE_ENTITY_FULL_NAME;
		boolean isRepairOld = false;

		return appendOrgType(metadataRootPath, packagePerfix, baseClassz, orgTypeValue, templateEntityFullName,
				isRepairOld, isSave);
	}

	/**
	 * ����orgType��չ����
	 * 
	 * @param metadataRootPath
	 * @param packagePerfix
	 * @return
	 */
	public static Map appendOrgType(String metadataRootPath, String packagePerfix) {
		Class baseClassz = FDCBill.class;
		String orgTypeValue = "Transport";
		String templateEntityFullName = FdcSimpleMetaDataUtil.TEMPLATE_ENTITY_FULL_NAME;
		boolean isRepairOld = false;
		boolean isSave = true;

		return appendOrgType(metadataRootPath, packagePerfix, baseClassz, orgTypeValue, templateEntityFullName,
				isRepairOld, isSave);
	}

	// //////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////

	/**
	 * ����crNumberEdit��չ����ֵ
	 * 
	 * @param extendedPropertyElement
	 * @param entityFullName
	 */
	public static void setCRNumberEditValue(Element extendedPropertyElement, String entityFullName) {
		// ����ֵ
		String value = "true";
		setExtendedPropertyValue(extendedPropertyElement, entityFullName, value);
	}

	/**
	 * ����crNumberEdit��չ����
	 * 
	 * @param metadataRootPath
	 * @param packagePerfix
	 * @param baseClassz
	 * @param crNumberEditValue
	 * @param templateEntityFullName
	 * @param isRepairOld
	 * @param isSave
	 * @return
	 */
	public static Map appendCRNumberEdit(String metadataRootPath, String packagePerfix, Class baseClassz,
			String crNumberEditValue, String templateEntityFullName, boolean isRepairOld, boolean isSave) {
		String msg = "����������ɱ�����޸� ����Ϊ��";
		// ֵУ��
		Assert.assertNotNull(msg, crNumberEditValue);
		msg = crNumberEditValue + " �ǲ��Ϸ���ֵ������Ϊ��" + FdcMetaDataConstant.CR_NUMBER_EDIT_SET;
		// ֵУ��
		Assert.assertTrue(msg, FdcMetaDataConstant.CR_NUMBER_EDIT_SET.contains(crNumberEditValue));
		
		Map entityMap = new LinkedHashMap();
		Map noImplEntityMap = new LinkedHashMap();
		Map notExistsEntityMap = new LinkedHashMap();
		Map errorEntityMap = new LinkedHashMap();

		EntityObjectCollection entityObjectCollection = MetaDataLoader.getEntityCollection(null);
		int size = entityObjectCollection.size();

		String businessImplName = null;
		Class businessImplClassz = null;

		String entityFullName = null;
		String entityFileFullName = null;

		Element rootElement = null;
		Element template_crNumberEditElement = getExtendedPropertyElement(metadataRootPath, templateEntityFullName,
				FdcMetaDataConstant.CR_NUMBER_EDIT_KEY);
		System.out.println("FdcMetaDataUtil.appendCRNumberEdit()");

		for (int i = 0; i < size; i++) {
			EntityObjectInfo entityObjectInfo = entityObjectCollection.get(i);

			if (entityObjectInfo.getPackage().startsWith(packagePerfix)) {
				businessImplName = entityObjectInfo.getBusinessImplName();
				try {
					businessImplClassz = Class.forName(businessImplName);
				} catch (ClassNotFoundException e) {
					noImplEntityMap.put(entityObjectInfo.getFullName(), "��ҵ��ʵ�֣�" + businessImplName);

					e.printStackTrace();
				}
			} else {
				businessImplName = null;
				businessImplClassz = null;
				continue;
			}

			// �ų�������
			if (entityObjectInfo.isAbstract()) {
				businessImplName = null;
				businessImplClassz = null;
				continue;
			}

			if (null != businessImplClassz && baseClassz.isAssignableFrom(businessImplClassz)) {
				entityFullName = entityObjectInfo.getFullName();
				entityFileFullName = getEntityFileFullName(metadataRootPath, entityFullName);

				Document document = getDocument(metadataRootPath, entityFullName);
				if (null == document) {
					notExistsEntityMap.put(entityFullName, "�����д��ڣ���BOS͸��ͼ�в�����");
					continue;
				}
				rootElement = document.getRootElement();

				boolean changeFlag = false;
				Element crNumberEditElement = getExtendedPropertyElement(rootElement,
						FdcMetaDataConstant.CR_NUMBER_EDIT_KEY);
				String extendedPropertyValue = null;
				// ��crNumberEdit��չ����
				if (null != crNumberEditElement) {
					extendedPropertyValue = getExtendedPropertyValue(crNumberEditElement, Locale.CHINA);

					// ��չ����ֵΪ�ջ��߲��Ϸ�
					if (StringUtils.isEmpty(extendedPropertyValue)
							|| !extendedPropertyValue.startsWith(FDCConstants.EAS_PAKAGE_NAME)) {
						setCRNumberEditValue(crNumberEditElement, entityFullName);

						changeFlag = true;
					} else if (isRepairOld) {
						if (!FdcMetaDataConstant.CR_NUMBER_EDIT_SET.contains(extendedPropertyValue)) {
						}

						setCRNumberEditValue(crNumberEditElement, entityFullName);
						changeFlag = true;
					}
				} else {
					Element resourceElement = getSingleChild(rootElement, FdcMetaDataConstant.TAG_RESOURCE);

					crNumberEditElement = template_crNumberEditElement;
					crNumberEditElement = (Element) crNumberEditElement.clone();
					setCRNumberEditValue(crNumberEditElement, entityFullName);

					try {
						resourceElement.getChildren().add(crNumberEditElement);
						changeFlag = true;
					} catch (Exception e2) {
						errorEntityMap.put(entityObjectInfo.getFullName(), "�����ʵ��");

						e2.printStackTrace();
					}

				}

				if (changeFlag) {
					entityMap.put(entityFullName, extendedPropertyValue);

					if (isSave) {
						if (!FileUtil.isExist(entityFileFullName)) {
							try {
								com.kingdee.bos.metadata.upgrade.FileUtil.createFile(new File(entityFileFullName));
							} catch (IOException e) {
								e.printStackTrace();
							}
						}

						FdcXmlUtil.write(document, entityFileFullName);
					}
				}
			}
		}

		if (!entityMap.isEmpty()) {
			System.out.println("===========================================");
			MapUtils.debugPrint(System.out, null, entityMap);
			System.out.println("size = " + entityMap.size());
			System.out.println("===========================================");
		}
		if (!noImplEntityMap.isEmpty()) {
			System.out.println("===========================================");
			MapUtils.debugPrint(System.out, null, noImplEntityMap);
			System.out.println("size = " + noImplEntityMap.size());
			System.out.println("===========================================");
		}
		if (!notExistsEntityMap.isEmpty()) {
			System.out.println("===========================================");
			MapUtils.debugPrint(System.out, null, notExistsEntityMap);
			System.out.println("size = " + notExistsEntityMap.size());
			System.out.println("===========================================");
		}
		if (!errorEntityMap.isEmpty()) {
			System.out.println("===========================================");
			MapUtils.debugPrint(System.out, null, errorEntityMap);
			System.out.println("size = " + errorEntityMap.size());
			System.out.println("===========================================");
		}

		return entityMap;
	}

	/**
	 * ����crNumberEdit��չ����
	 * 
	 * @param metadataRootPath
	 * @param packagePerfix
	 * @param isSave
	 * @return
	 */
	public static Map appendCRNumberEdit(String metadataRootPath, String packagePerfix, boolean isSave) {
		Class baseClassz = FDCBill.class;
		String crNumberEditValue = "true";

		String templateEntityFullName = FdcSimpleMetaDataUtil.TEMPLATE_ENTITY_FULL_NAME;
		boolean isRepairOld = false;

		return appendCRNumberEdit(metadataRootPath, packagePerfix, baseClassz, crNumberEditValue,
				templateEntityFullName, isRepairOld, isSave);
	}

	/**
	 * ����crNumberEdit��չ����
	 * 
	 * @param metadataRootPath
	 * @param packagePerfix
	 * @return
	 */
	public static Map appendCRNumberEdit(String metadataRootPath, String packagePerfix) {
		Class baseClassz = FDCBill.class;
		String crNumberEditValue = "true";
		String templateEntityFullName = FdcSimpleMetaDataUtil.TEMPLATE_ENTITY_FULL_NAME;
		boolean isRepairOld = false;
		boolean isSave = true;

		return appendCRNumberEdit(metadataRootPath, packagePerfix, baseClassz, crNumberEditValue,
				templateEntityFullName, isRepairOld, isSave);
	}

	// //////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////

	/**
	 * ȡ��Ԫ���ݻ���ӿ�
	 * 
	 * @param ctx
	 *            Ӧ�������ģ�����Ϊ��
	 * @return
	 */
	public static IMetaDataLoader getMetaDataLoader(Context ctx) {
		IMetaDataLoader metaDataLoader = null;
		if (null != ctx) {
			metaDataLoader = MetaDataLoaderFactory.getLocalMetaDataLoader(ctx);
		} else {
			metaDataLoader = MetaDataLoaderFactory.getRemoteMetaDataLoader();
		}

		return metaDataLoader;
	}

	// //////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////

	/**
	 * ȡ������Ļ���ʵ����� <br>
	 * 
	 * <br>
	 * �Ⱥ��ж�˳������Ϊ��<br>
	 * 1��Project<br>
	 * 2��OrgUnit<br>
	 * 3��Period<br>
	 * 4��User<br>
	 * <br>
	 * 5��BillEntryBase<br>
	 * 6��EcBaseData<br>
	 * <br>
	 * 7��EcTreeBase<br>
	 * 7��TreeBase<br>
	 * 7��DataBase<br>
	 * <br>
	 * 10��EcBill<br>
	 * 11��FDCBill<br>
	 * 12��CoreBillBase<br>
	 * 13��ObjectBase<br>
	 * 14��CoreBase<br>
	 * <br>
	 * 
	 * @param ctx
	 *            Ӧ�������ģ�����Ϊ��
	 * @param bosTypeStr
	 *            BOS��������_�ַ���������Ϊ��
	 * @return
	 */
	public static EntityObjectInfo getSuperEntityObjectInfo(Context ctx, String bosTypeStr) {
		EntityObjectInfo superEntityObjectInfo = null;

		// ȡ��Ԫ���ݻ���ӿ�
		IMetaDataLoader metaDataLoader = getMetaDataLoader(ctx);
		superEntityObjectInfo = getSuperEntityObjectInfo(metaDataLoader, bosTypeStr);

		return superEntityObjectInfo;
	}

	/**
	 * ȡ������Ļ���ʵ����� <br>
	 * 
	 * <br>
	 * �Ⱥ��ж�˳������Ϊ��<br>
	 * 1��Project<br>
	 * 2��OrgUnit<br>
	 * 3��Period<br>
	 * 4��User<br>
	 * <br>
	 * 5��BillEntryBase<br>
	 * 6��EcBaseData<br>
	 * <br>
	 * 7��EcTreeBase<br>
	 * 7��TreeBase<br>
	 * 7��DataBase<br>
	 * <br>
	 * 10��EcBill<br>
	 * 11��FDCBill<br>
	 * 12��CoreBillBase<br>
	 * 13��ObjectBase<br>
	 * 14��CoreBase<br>
	 * <br>
	 * 
	 * @param metaDataLoader
	 *            Ԫ���ݻ���ӿڣ�����Ϊ��
	 * @param bosTypeStr
	 *            BOS��������_�ַ���������Ϊ��
	 * @return
	 */
	public static EntityObjectInfo getSuperEntityObjectInfo(IMetaDataLoader metaDataLoader, String bosTypeStr) {
		EntityObjectInfo superEntityObjectInfo = null;

		EntityObjectInfo entityObjectInfo = getEntityObjectInfo(metaDataLoader, bosTypeStr);
		superEntityObjectInfo = getSuperEntityObjectInfo(metaDataLoader, entityObjectInfo);

		return superEntityObjectInfo;
	}

	/**
	 * ȡ������Ļ���ʵ����� <br>
	 * 
	 * <br>
	 * �Ⱥ��ж�˳������Ϊ��<br>
	 * 1��Project<br>
	 * 2��OrgUnit<br>
	 * 3��Period<br>
	 * 4��User<br>
	 * <br>
	 * 5��BillEntryBase<br>
	 * 6��EcBaseData<br>
	 * <br>
	 * 7��EcTreeBase<br>
	 * 7��TreeBase<br>
	 * 7��DataBase<br>
	 * <br>
	 * 10��EcBill<br>
	 * 11��FDCBill<br>
	 * 12��CoreBillBase<br>
	 * 13��ObjectBase<br>
	 * 14��CoreBase<br>
	 * <br>
	 * 
	 * @param ctx
	 *            Ӧ�������ģ�����Ϊ��
	 * @param entityObjectInfo
	 *            BOS�������ͣ�����Ϊ��
	 * @return
	 */
	public static EntityObjectInfo getSuperEntityObjectInfo(Context ctx, EntityObjectInfo entityObjectInfo) {
		EntityObjectInfo superEntityObjectInfo = null;

		// ȡ��Ԫ���ݻ���ӿ�
		IMetaDataLoader metaDataLoader = getMetaDataLoader(ctx);
		superEntityObjectInfo = getSuperEntityObjectInfo(metaDataLoader, entityObjectInfo);

		return superEntityObjectInfo;
	}

	/**
	 * ȡ������Ļ���ʵ����� <br>
	 * 
	 * <br>
	 * �Ⱥ��ж�˳������Ϊ��<br>
	 * 1��Project<br>
	 * 2��OrgUnit<br>
	 * 3��Period<br>
	 * 4��User<br>
	 * <br>
	 * 5��BillEntryBase<br>
	 * 6��EcBaseData<br>
	 * <br>
	 * 7��EcTreeBase<br>
	 * 7��TreeBase<br>
	 * 7��DataBase<br>
	 * <br>
	 * 10��EcBill<br>
	 * 11��FDCBill<br>
	 * 12��CoreBillBase<br>
	 * 13��ObjectBase<br>
	 * 14��CoreBase<br>
	 * <br>
	 * 
	 * @param metaDataLoader
	 *            Ԫ���ݻ���ӿڣ�����Ϊ��
	 * @param entityObjectInfo
	 *            BOS�������ͣ�����Ϊ��
	 * @return
	 */
	public static EntityObjectInfo getSuperEntityObjectInfo(IMetaDataLoader metaDataLoader,
			EntityObjectInfo entityObjectInfo) {
		EntityObjectInfo superEntityObjectInfo = null;

		String bosTypeStr_project = FdcMetaDataConstant.BOS_TYPE_STR_PROJECT;
		String bosTypeStr_orgUnit = FdcMetaDataConstant.BOS_TYPE_STR_ORG_UNIT;
		String bosTypeStr_period = FdcMetaDataConstant.BOS_TYPE_STR_PERIOD;
		String bosTypeStr_user = FdcMetaDataConstant.BOS_TYPE_STR_USER;

		String bosTypeStr_billEntryBase = FdcMetaDataConstant.BOS_TYPE_STR_BILL_ENTRY_BASE;
		String bosTypeStr_ecBaseData = FdcMetaDataConstant.BOS_TYPE_STR_FDC_BASE_DATA;

		String bosTypeStr_ecTreeBase = FdcMetaDataConstant.BOS_TYPE_STR_FDC_TREE_BASE;
		String bosTypeStr_treeBase = FdcMetaDataConstant.BOS_TYPE_STR_TREE_BASE;
		String bosTypeStr_dataBase = FdcMetaDataConstant.BOS_TYPE_STR_DATA_BASE;

		String bosTypeStr_ecBill = FdcMetaDataConstant.BOS_TYPE_STR_FDC_BILL;
		String bosTypeStr_FDCBill = FdcMetaDataConstant.BOS_TYPE_STR_BASE_BILL;
		String bosTypeStr_coreBillBase = FdcMetaDataConstant.BOS_TYPE_STR_CORE_BILL_BASE;
		String bosTypeStr_objectBase = FdcMetaDataConstant.BOS_TYPE_STR_OBJECT_BASE;
		String bosTypeStr_coreBase = FdcMetaDataConstant.BOS_TYPE_STR_CORE_BASE;

		EntityObjectInfo entityObjectInfo_project = getEntityObjectInfo(metaDataLoader, bosTypeStr_project);
		EntityObjectInfo entityObjectInfo_orgUnit = getEntityObjectInfo(metaDataLoader, bosTypeStr_orgUnit);
		EntityObjectInfo entityObjectInfo_period = getEntityObjectInfo(metaDataLoader, bosTypeStr_period);
		EntityObjectInfo entityObjectInfo_user = getEntityObjectInfo(metaDataLoader, bosTypeStr_user);

		EntityObjectInfo entityObjectInfo_billEntryBase = getEntityObjectInfo(metaDataLoader, bosTypeStr_billEntryBase);
		EntityObjectInfo entityObjectInfo_ecBaseData = getEntityObjectInfo(metaDataLoader, bosTypeStr_ecBaseData);

		EntityObjectInfo entityObjectInfo_ecTreeBase = getEntityObjectInfo(metaDataLoader, bosTypeStr_ecTreeBase);
		EntityObjectInfo entityObjectInfo_treeBase = getEntityObjectInfo(metaDataLoader, bosTypeStr_treeBase);
		EntityObjectInfo entityObjectInfo_dataBase = getEntityObjectInfo(metaDataLoader, bosTypeStr_dataBase);

		EntityObjectInfo entityObjectInfo_ecBill = getEntityObjectInfo(metaDataLoader, bosTypeStr_ecBill);
		EntityObjectInfo entityObjectInfo_FDCBill = getEntityObjectInfo(metaDataLoader, bosTypeStr_FDCBill);
		EntityObjectInfo entityObjectInfo_coreBillBase = getEntityObjectInfo(metaDataLoader, bosTypeStr_coreBillBase);
		EntityObjectInfo entityObjectInfo_objectBase = getEntityObjectInfo(metaDataLoader, bosTypeStr_objectBase);
		EntityObjectInfo entityObjectInfo_coreBase = getEntityObjectInfo(metaDataLoader, bosTypeStr_coreBase);

		// //////////////////////////////////////////////////////////////

		// Ԥ������
		if (entityObjectInfo.isSubFrom(entityObjectInfo_project)) {
			superEntityObjectInfo = entityObjectInfo_project;
		} else if (entityObjectInfo.isSubFrom(entityObjectInfo_orgUnit)) {
			superEntityObjectInfo = entityObjectInfo_orgUnit;
		} else if (entityObjectInfo.isSubFrom(entityObjectInfo_period)) {
			superEntityObjectInfo = entityObjectInfo_period;
		} else if (entityObjectInfo.isSubFrom(entityObjectInfo_user)) {
			superEntityObjectInfo = entityObjectInfo_user;
		}
		// BillEntryBase������
		else if (entityObjectInfo.isSubFrom(entityObjectInfo_billEntryBase)) {
			superEntityObjectInfo = entityObjectInfo_billEntryBase;
		}
		// EcBaseData������
		else if (entityObjectInfo.isSubFrom(entityObjectInfo_ecBaseData)) {
			superEntityObjectInfo = entityObjectInfo_ecBaseData;
		}
		// DataBase������
		// else if (entityObjectInfo.isSubFrom(entityObjectInfo_ecTreeBase)) {
		// superEntityObjectInfo = entityObjectInfo_ecTreeBase;
		// }
		else if (entityObjectInfo.isSubFrom(entityObjectInfo_treeBase)) {
			superEntityObjectInfo = entityObjectInfo_treeBase;
		} else if (entityObjectInfo.isSubFrom(entityObjectInfo_dataBase)) {
			superEntityObjectInfo = entityObjectInfo_dataBase;
		}
		// FDCBill������
		// else if (entityObjectInfo.isSubFrom(entityObjectInfo_ecBill)) {
		// superEntityObjectInfo = entityObjectInfo_ecBill;
		// }
		else if (entityObjectInfo.isSubFrom(entityObjectInfo_FDCBill)) {
			superEntityObjectInfo = entityObjectInfo_FDCBill;
		} else if (entityObjectInfo.isSubFrom(entityObjectInfo_coreBillBase)) {
			superEntityObjectInfo = entityObjectInfo_coreBillBase;
		} else if (entityObjectInfo.isSubFrom(entityObjectInfo_objectBase)) {
			superEntityObjectInfo = entityObjectInfo_objectBase;
		} else if (entityObjectInfo.isSubFrom(entityObjectInfo_coreBase)) {
			superEntityObjectInfo = entityObjectInfo_coreBase;
		}

		return superEntityObjectInfo;
	}

	/**
	 * ����SelectorItemCollection
	 * 
	 * @param ctx
	 *            Ӧ�������ģ�����Ϊ��
	 * @param bosTypeStr
	 *            BOS��������_�ַ���������Ϊ��
	 * @param perfix
	 *            ǰ׺������Ϊ��
	 * @param isGenLinkProperty
	 *            �Ƿ�������������
	 * @return
	 */
	public static SelectorItemCollection generateSelector(Context ctx, String bosTypeStr, String perfix,
			boolean isGenLinkProperty) {
		SelectorItemCollection sic = new SelectorItemCollection();

		// ȡ��Ԫ���ݻ���ӿ�
		IMetaDataLoader metaDataLoader = getMetaDataLoader(ctx);

		BOSObjectType objectType = BOSObjectType.create(bosTypeStr);
		EntityObjectInfo entityObjectInfo = metaDataLoader.getEntity(objectType);
		// ȡ��ʵ����������������Լ��̳���������, ��ȥ���ظ�������(�����������ж�)
		PropertyCollection propertyCollection = entityObjectInfo.getInheritedNoDuplicatedPropertiesRuntime();
		// ȡ���������Զ�Ӧ��ʵ��
		Hashtable childEntities = EntityObjectInfo.getChildEntities(entityObjectInfo);
		// ȡ������Ļ���ʵ�����
		EntityObjectInfo superEntityObjectInfo = getSuperEntityObjectInfo(metaDataLoader, entityObjectInfo);
		// ȡ��������List
		List superPropertyNameList = getPropertyNameList(ctx, superEntityObjectInfo.getType().toString());

		PropertyInfo propertyInfo = null;
		LinkPropertyInfo linkPropertyInfo = null;
		EntityObjectInfo linkEntityObjectInfo = null;
		String propertyName = null;

		EntityObjectInfo tempEntityObjectInfo = null;
		String tempBosTypeStr = null;
		List tempPropertyNameList = new ArrayList();
		String tempPropertyName = null;
		if (FdcStringUtil.isBlank(perfix)) {
			perfix = "";
		}

		StringBuffer selfStringBuffer = new StringBuffer();
		StringBuffer linkStringBuffer = new StringBuffer();
		StringBuffer superStringBuffer = new StringBuffer();

		for (Iterator iterator = propertyCollection.iterator(); iterator.hasNext();) {
			propertyInfo = (PropertyInfo) iterator.next();
			propertyName = propertyInfo.getName();

			// �ų���������
			if (superPropertyNameList.contains(propertyName)) {
				continue;
			}

			if (!(propertyInfo instanceof LinkPropertyInfo)) {
				tempPropertyName = propertyName;
				tempPropertyName = perfix + tempPropertyName;

				sic.add(tempPropertyName);
				selfStringBuffer.append("sic.add(new SelectorItemInfo(\"").append(tempPropertyName).append("\"));\n");
			} else {
				if (!isGenLinkProperty) {
					tempPropertyName = propertyName + ".id";
					tempPropertyName = perfix + tempPropertyName;

					sic.add(tempPropertyName);
					linkStringBuffer.append("sic.add(new SelectorItemInfo(\"").append(tempPropertyName).append(
							"\"));\n");
				} else {
					linkPropertyInfo = (LinkPropertyInfo) propertyInfo;
					linkEntityObjectInfo = (EntityObjectInfo) childEntities.get(linkPropertyInfo);

					// ȡ������Ļ���ʵ�����
					tempEntityObjectInfo = getSuperEntityObjectInfo(metaDataLoader, linkEntityObjectInfo);

					tempBosTypeStr = tempEntityObjectInfo.getType().toString();
					tempPropertyNameList = (List) FdcMetaDataConstant.COMMON_PROPERTY_NAME_LIST_MAP.get(tempBosTypeStr);
					for (int i = 0, size = tempPropertyNameList.size(); i < size; i++) {
						tempPropertyName = (String) tempPropertyNameList.get(i);
						tempPropertyName = propertyName + "." + tempPropertyName;
						tempPropertyName = perfix + tempPropertyName;

						sic.add(tempPropertyName);
						linkStringBuffer.append("sic.add(new SelectorItemInfo(\"").append(tempPropertyName).append(
								"\"));\n");
					}
				}
			}
		}

		if (null != superEntityObjectInfo) {
			tempEntityObjectInfo = superEntityObjectInfo;

			tempBosTypeStr = tempEntityObjectInfo.getType().toString();
			tempPropertyNameList = (List) FdcMetaDataConstant.COMMON_PROPERTY_NAME_LIST_MAP.get(tempBosTypeStr);
			for (int i = 0, size = tempPropertyNameList.size(); i < size; i++) {
				tempPropertyName = (String) tempPropertyNameList.get(i);
				// tempPropertyName = propertyName + "." + tempPropertyName;
				tempPropertyName = perfix + tempPropertyName;

				sic.add(tempPropertyName);
				superStringBuffer.append("sic.add(\"").append(tempPropertyName).append("\");\n");
			}
		}

		System.out.println("===========================================");
		System.out.println("SelectorItemCollection sic = new SelectorItemCollection();\n");
		if (superStringBuffer.length() > 0) {
			System.out.println(superStringBuffer);
			System.out.println("///////////////////////////////////////////\n");
		}
		if (linkStringBuffer.length() > 0) {
			System.out.println(linkStringBuffer);
			System.out.println("///////////////////////////////////////////\n");
		}
		System.out.println(selfStringBuffer);
		System.out.println("===========================================");

		return sic;
	}

	// //////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////

	/**
	 * ȡ��ʵ�����
	 * 
	 * 
	 * @param ctx
	 *            Ӧ�������ģ�����Ϊ��
	 * @param bosTypeStr
	 *            BOS��������_�ַ���������Ϊ��
	 * @return
	 */
	public static EntityObjectInfo getEntityObjectInfo(Context ctx, String bosTypeStr) {
		EntityObjectInfo entityObjectInfo = null;

		entityObjectInfo = (EntityObjectInfo) ENTITY_OBJECT_INFO_MAP.get(bosTypeStr);
		if (null == entityObjectInfo) {
			IMetaDataLoader metaDataLoader = getMetaDataLoader(ctx);
			entityObjectInfo = getEntityObjectInfo(metaDataLoader, bosTypeStr);
		}

		return entityObjectInfo;
	}

	/**
	 * ȡ��ʵ�����
	 * 
	 * 
	 * @param metaDataLoader
	 *            Ԫ���ݻ���ӿڣ�����Ϊ��
	 * @param bosTypeStr
	 *            BOS��������_�ַ���������Ϊ��
	 * @return
	 */
	public static EntityObjectInfo getEntityObjectInfo(IMetaDataLoader metaDataLoader, String bosTypeStr) {
		EntityObjectInfo entityObjectInfo = null;

		entityObjectInfo = (EntityObjectInfo) ENTITY_OBJECT_INFO_MAP.get(bosTypeStr);
		if (null == entityObjectInfo) {
			BOSObjectType objectType = BOSObjectType.create(bosTypeStr);
			entityObjectInfo = metaDataLoader.getEntity(objectType);

			// �����������ܳ���CACHE_MAP_MAX_SIZE
			if (ENTITY_OBJECT_INFO_MAP.size() >= CACHE_MAP_MAX_SIZE) {
				String[] keyArr = (String[]) ENTITY_OBJECT_INFO_MAP.keySet().toArray(new String[0]);
				String lastKey = keyArr[keyArr.length - 1];
				ENTITY_OBJECT_INFO_MAP.remove(lastKey);
			}
			ENTITY_OBJECT_INFO_MAP.put(bosTypeStr, entityObjectInfo);
		}

		return entityObjectInfo;
	}

	// //////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////

	/**
	 * �������Դ���
	 * 
	 * @param ctx
	 *            Ӧ�������ģ�����Ϊ��
	 * @param bosTypeStr
	 *            BOS��������_�ַ���������Ϊ��
	 */
	public static void generatePropertyCode(Context ctx, String bosTypeStr) {
		// ����bosType���봴��BOSObjectType
		BOSObjectType objectType = BOSObjectType.create(bosTypeStr);
		// ����bosTypeȡ�ö�Ӧ��ʵ��
		EntityObjectInfo entityObjectInfo = MetaDataLoader.getEntity(ctx, objectType);

		Class classz = null;
		try {
			String businessImplName = entityObjectInfo.getBusinessImplName();
			String infoName = businessImplName + "Info";
			classz = Class.forName(infoName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (null == classz) {
			return;
		}

		// ȡ��ʵ����������������Լ��̳���������, ��ȥ���ظ�������(�����������ж�)
		PropertyCollection propertyCollection = entityObjectInfo.getInheritedNoDuplicatedPropertiesRuntime();
		if (FdcObjectCollectionUtil.isEmpty(propertyCollection)) {
			return;
		}

		List propertyNameList = new ArrayList();
		PropertyInfo propertyInfo = null;
		for (Iterator iterator = propertyCollection.iterator(); iterator.hasNext();) {
			propertyInfo = (PropertyInfo) iterator.next();
			propertyNameList.add(propertyInfo.getName());
		}

		// ȡ�����пɷ��ʷ���
		Method[] methodArr = classz.getMethods();
		Method method = null;
		String methodName = null;
		Class returnType = null;
		String returnTypeSimpleName = null;
		String codeLine = null;
		StringBuffer stringBuffer = new StringBuffer();

		// ԭ����������
		Class listClassz = List.class;
		Class setClassz = Set.class;
		Class mapClassz = Map.class;
		// ���󼯺�����
		Class objectCollectionClassz = IObjectCollection.class;
		// Boolean����
		Class booleanType = Boolean.TYPE;
		Class booleanClassz = Boolean.TYPE;

		// �������
		for (int length = methodArr.length, i = length - 1; i >= 0; i--) {
			method = methodArr[i];
			methodName = method.getName();
			returnType = method.getReturnType();

			// getter
			if (methodName.startsWith("get") && methodName.length() > 3 && !Void.TYPE.equals(returnType)) {
				methodName = StringUtils.uncapitalize(methodName.substring(3));

				if (propertyNameList.contains(methodName)) {
					returnTypeSimpleName = FdcClassUtil.getSimpleName(returnType);
					codeLine = "protected " + returnTypeSimpleName + " " + methodName;

					// ԭ����������
					if (listClassz.isAssignableFrom(returnType)) {
						codeLine += " = new ArrayList();\n";
					} else if (setClassz.isAssignableFrom(returnType)) {
						codeLine += " = new LinkedHashSet();\n";
					} else if (mapClassz.isAssignableFrom(returnType)) {
						codeLine += " = new LinkedHashMap();\n";
					}
					// ���󼯺�����
					else if (objectCollectionClassz.isAssignableFrom(returnType)) {
						codeLine += " = new " + returnTypeSimpleName + "();\n";
					} else {
						codeLine += ";\n";
					}

					stringBuffer.append(codeLine);
				}
			}
			// Boolean����
			else if (methodName.startsWith("is") && methodName.length() > 2
					&& (booleanType.equals(returnType) || booleanClassz.equals(returnType))) {
				methodName = StringUtils.uncapitalize(methodName.substring(2));

				if (propertyNameList.contains(methodName)) {
					returnTypeSimpleName = FdcClassUtil.getSimpleName(returnType);
					codeLine = "protected " + returnTypeSimpleName + " " + methodName + ";\n";

					stringBuffer.append(codeLine);
				}
			}
		}

		System.out.println("===========================================");
		System.out.println();

		System.out.println(stringBuffer);

		System.out.println("===========================================");
		System.out.println();
	}

	// ///////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////
	
}
