/**
 * @(#)FdcMetaDataUtil.java 1.0 2014-3-19
 * @author 王正
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
 * 描述：房地产 元数据工具类(客户端、服务端通用)
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @createDate 2014-3-19
 * @version 1.0, 2014-3-19
 * @see
 * @since JDK1.4
 */
public class FdcMetaDataUtil {
	/**
	 * 实体对象_映射 <br>
	 * 其中key存放BosType字符串，value存放实体对象
	 */
	private static final Map ENTITY_OBJECT_INFO_MAP = new LinkedHashMap();

	/**
	 * 属性名_列表_映射 <br>
	 * 其中key存放BosType字符串，value存放属性名_列表
	 */
	private static final Map EPROPERTY_NAME_LIST_MAP = new LinkedHashMap();

	/**
	 * 自身_属性名_列表_映射 <br>
	 * 其中key存放BosType字符串，value存放自身_属性名_列表
	 */
	private static final Map OWN_EPROPERTY_NAME_LIST_MAP = new LinkedHashMap();

	/**
	 * 链接_属性名_列表_映射 <br>
	 * 其中key存放BosType字符串，value存放链接_属性名_列表
	 */
	private static final Map LINK_EPROPERTY_NAME_LIST_MAP = new LinkedHashMap();

	/**
	 * 缓存_映射_最大_尺寸
	 */
	private static final int CACHE_MAP_MAX_SIZE = 500;

	// //////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////

	/**
	 * 取得元数据文件完整名称
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
	 * 取得实体文件完整名称
	 * 
	 * @param metadataRootPath
	 * @param entityFullName
	 * @return
	 */
	public static String getEntityFileFullName(String metadataRootPath, String entityFullName) {
		return getMetaDataFileFullName(metadataRootPath, entityFullName, FdcMetaDataConstant.META_DATA_EXTNAME_ENTITY);
	}

	/**
	 * 取得实体文件XML文档
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
	 * 取得实体文件XML根元素
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
	 * 取得拓展属性XML元素
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
	 * 取得拓展属性XML元素
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
	 * 取得defaultF7Query拓展属性XML元素
	 * 
	 * @param metadataRootPath
	 * @param entityFullName
	 * @return
	 */
	public static Element getDefaultF7QueryElement(String metadataRootPath, String entityFullName) {
		return getExtendedPropertyElement(metadataRootPath, entityFullName, FdcMetaDataConstant.DEFAULT_F7_QUERY_KEY);
	}

	/**
	 * 取得拓展属性XML值
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
	 * 附加defaultF7Query拓展属性
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
					noImplEntityMap.put(entityObjectInfo.getFullName(), "无业务实现：" + businessImplName);

					e.printStackTrace();

				}
			} else {
				businessImplName = null;
				businessImplClassz = null;
				continue;
			}

			// 排除抽象类
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
					notExistsEntityMap.put(entityFullName, "缓存中存在，而BOS透视图中不存在");
					continue;
				}
				rootElement = document.getRootElement();

				boolean changeFlag = false;
				Element defaultF7QueryElement = getExtendedPropertyElement(rootElement,
						FdcMetaDataConstant.DEFAULT_F7_QUERY_KEY);
				String extendedPropertyValue = null;
				// 有defaultF7Query拓展属性
				if (null != defaultF7QueryElement) {
					extendedPropertyValue = getExtendedPropertyValue(defaultF7QueryElement, Locale.CHINA);

					// 拓展属性值为空或者不合法
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
						errorEntityMap.put(entityObjectInfo.getFullName(), "错误的实体");

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
	 * 附加defaultF7Query拓展属性
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
	 * 附加defaultF7Query拓展属性
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
	 * 取得XML元素的单孩子
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
	 * 设置拓展属性值
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
	 * 设置defaultF7Query拓展属性值
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
	 * 取得查询实体对象
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
	 * 取得属性List
	 * 
	 * @param ctx
	 *            应用上下文；可以为空
	 * @param bOSObjectType
	 *            BOS对象类型_字符串；不能为空
	 * @return
	 */
	public static List getPropertyList(Context ctx, String bOSObjectType) {
		List propertyList = null;
		
		propertyList = (List) EPROPERTY_NAME_LIST_MAP.get(bOSObjectType);
		if (null == propertyList) {
			// 根据bosType编码创建BOSObjectType
			BOSObjectType objectType = BOSObjectType.create(bOSObjectType);
			// 根据bosType取得对应的实体
			EntityObjectInfo entityObjectInfo = MetaDataLoader.getEntity(ctx, objectType);
			// 取得实体的所有自有属性以及继承来的属性, 并去除重复的属性(用属性名称判断)
			PropertyCollection propertyCollection = entityObjectInfo.getInheritedNoDuplicatedPropertiesRuntime();

			if (FdcObjectCollectionUtil.isNotEmpty(propertyCollection)) {
				// 将对象集合 转换成List
				propertyList = FdcObjectCollectionUtil.toList(propertyCollection);

				// 缓存数，不能超过CACHE_MAP_MAX_SIZE
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
	 * 取得属性名List
	 * 
	 * @param ctx
	 *            应用上下文；可以为空
	 * @param bOSObjectType
	 *            BOS对象类型_字符串；不能为空
	 * @return
	 */
	public static List getPropertyNameList(Context ctx, String bOSObjectType) {
		List propertyNameList = new ArrayList();

		// 取得属性List
		List propertyList = getPropertyList(ctx, bOSObjectType);
		// 取得属性名List
		propertyNameList = getPropertyNameList(propertyList.iterator());

		return propertyNameList;
	}

	/**
	 * 取得属性名List
	 * 
	 * @param iterator
	 *            属性集合迭代器
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
	 * 取得自身属性List
	 * 
	 * @param ctx
	 *            应用上下文；可以为空
	 * @param bOSObjectType
	 *            BOS对象类型_字符串；不能为空
	 * @return
	 */
	public static List getOwnPropertyList(Context ctx, String bOSObjectType) {
		List propertyList = null;

		propertyList = (List) OWN_EPROPERTY_NAME_LIST_MAP.get(bOSObjectType);
		if (null == propertyList) {
			// 根据bosType编码创建BOSObjectType
			BOSObjectType objectType = BOSObjectType.create(bOSObjectType);
			// 根据bosType取得对应的实体
			EntityObjectInfo entityObjectInfo = MetaDataLoader.getEntity(ctx, objectType);
			// 取得实体的所有自有属性以及继承来的属性, 并去除重复的属性(用属性名称判断)
			PropertyCollection propertyCollection = entityObjectInfo.getInheritedNoDuplicatedPropertiesRuntime();

			if (FdcObjectCollectionUtil.isNotEmpty(propertyCollection)) {
				// 将对象集合 转换成List
				for (Iterator iterator = propertyCollection.iterator(); iterator.hasNext();) {
					PropertyInfo propertyInfo = (PropertyInfo) iterator.next();

					if (!(propertyInfo instanceof LinkPropertyInfo)) {
						propertyList.add(propertyInfo);
					}
				}

				// 缓存数，不能超过CACHE_MAP_MAX_SIZE
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
	 * 取得自身属性名List
	 * 
	 * @param ctx
	 *            应用上下文；可以为空
	 * @param bOSObjectType
	 *            BOS对象类型_字符串；不能为空
	 * @return
	 */
	public static List getOwnPropertyNameList(Context ctx, String bOSObjectType) {
		List propertyNameList = new ArrayList();

		// 取得属性List
		List propertyList = getOwnPropertyList(ctx, bOSObjectType);
		// 取得属性名List
		propertyNameList = getOwnPropertyNameList(propertyList.iterator());

		return propertyNameList;
	}

	/**
	 * 取得自身属性名List
	 * 
	 * @param iterator
	 *            属性集合迭代器
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
	 * 取得链接属性List
	 * 
	 * @param ctx
	 *            应用上下文；可以为空
	 * @param bOSObjectType
	 *            BOS对象类型_字符串；不能为空
	 * @return
	 */
	public static List getLinkPropertyList(Context ctx, String bOSObjectType) {
		List propertyList = null;

		propertyList = (List) LINK_EPROPERTY_NAME_LIST_MAP.get(bOSObjectType);
		if (null == propertyList) {
			// 根据bosType编码创建BOSObjectType
			BOSObjectType objectType = BOSObjectType.create(bOSObjectType);
			// 根据bosType取得对应的实体
			EntityObjectInfo entityObjectInfo = MetaDataLoader.getEntity(ctx, objectType);
			// 取得实体的所有自有属性以及继承来的属性, 并去除重复的属性(用属性名称判断)
			PropertyCollection propertyCollection = entityObjectInfo.getInheritedNoDuplicatedPropertiesRuntime();

			if (FdcObjectCollectionUtil.isNotEmpty(propertyCollection)) {
				// 将对象集合 转换成List
				for (Iterator iterator = propertyCollection.iterator(); iterator.hasNext();) {
					PropertyInfo propertyInfo = (PropertyInfo) iterator.next();

					if (propertyInfo instanceof LinkPropertyInfo) {
						propertyList.add(propertyInfo);
					}
				}

				// 缓存数，不能超过CACHE_MAP_MAX_SIZE
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
	 * 取得链接属性名List
	 * 
	 * @param ctx
	 *            应用上下文；可以为空
	 * @param bOSObjectType
	 *            BOS对象类型_字符串；不能为空
	 * @return
	 */
	public static List getLinkPropertyNameList(Context ctx, String bOSObjectType) {
		List propertyNameList = new ArrayList();

		// 取得属性List
		List propertyList = getLinkPropertyList(ctx, bOSObjectType);
		// 取得属性名List
		propertyNameList = getLinkPropertyNameList(propertyList.iterator());

		return propertyNameList;
	}

	/**
	 * 取得链接属性名List
	 * 
	 * @param iterator
	 *            属性集合迭代器
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
	 * 取得列List
	 * 
	 * @param ctx
	 *            应用上下文；可以为空
	 * @param bOSObjectType
	 *            BOS对象类型_字符串；不能为空
	 * @param isSortByProperty
	 *            是否根据属性排序
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

		// 根据属性排序
		if (isSortByProperty) {
			List propertyList = getPropertyList(ctx, bOSObjectType);
			PropertyInfo propertyInfo = null;
			String propertyName = null;
			Set mappingColumnNameSet = new LinkedHashSet();

			for (Iterator iterator = propertyList.iterator(); iterator.hasNext();) {
				propertyInfo = (PropertyInfo) iterator.next();
				propertyName = propertyInfo.getName();

				// 分拣有映射的列
				columnInfo = propertyInfo.getMappingField();
				if (null != columnInfo) {
					columnName = columnInfo.getName().toUpperCase();
					mappingColumnNameSet.add(columnName);
				}
			}
			for (Iterator iterator = mappingColumnNameSet.iterator(); iterator.hasNext();) {
				columnName = (String) iterator.next();

				// 添加有映射的列
				columnInfo = (ColumnInfo) columnNameMap.get(columnName);
				columnList.add(columnInfo);
				columnNameMap.remove(columnName);
			}
			// 顺序转换
			Collections.reverse(columnList);

			Set columnNameSet = columnNameMap.keySet();
			for (Iterator iterator = columnNameSet.iterator(); iterator.hasNext();) {
				columnName = (String) iterator.next();

				// 添加没有有映射的列
				columnInfo = (ColumnInfo) columnNameMap.get(columnName);
				columnList.add(columnInfo);
			}
		}
		// 不排序
		else {
			columnList.addAll(columnNameMap.values());
		}

		return columnList;
	}

	/**
	 * 取得列名List
	 * 
	 * @param ctx
	 *            应用上下文；可以为空
	 * @param bOSObjectType
	 *            BOS对象类型_字符串；不能为空
	 * @param isSortByProperty
	 *            是否根据属性排序
	 * @return
	 */
	public static List getColumnNameList(Context ctx, String bOSObjectType, boolean isSortByProperty) {
		List columnNameList = new ArrayList();

		// 取得列List
		List columnList = getColumnList(ctx, bOSObjectType, isSortByProperty);
		// 取得列名List
		columnNameList = getColumnNameList(columnList.iterator());

		return columnNameList;
	}

	/**
	 * 取得列名List
	 * 
	 * @param iterator
	 *            列集合迭代器
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
	 * 取得相同的列名List
	 * 
	 * @param ctx
	 *            应用上下文；可以为空
	 * @param bOSObjectType
	 *            BOS对象类型_字符串；不能为空
	 * @param isSortByProperty
	 *            是否根据属性排序
	 * @return
	 */
	public static List getSameColumnNameList(Context ctx, String bOSObjectType1, String bOSObjectType2,
			boolean isSortByProperty) {
		List columnNameList = new ArrayList();

		// 取得列名List
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
	 * 取得服务端MetaDataPK对应的model包名
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
	 * 取得服务端MetaDataPK对应的model包名
	 * 
	 * @param appFullName
	 * @return
	 */
	public static String getModelPackageName(IMetaDataPK appMetaDataPK) {
		return getModelPackageName(appMetaDataPK, "app");
	}

	/**
	 * 取得MetaDataPK对应的model包名
	 * 
	 * @param metaDataPK
	 * @param subPackage
	 *            子包名，只能是以下三种值：app, client, null
	 * @return
	 */
	private static String getModelPackageName(IMetaDataPK metaDataPK, String subPackage) {
		String modelPackageName = null;

		// 取得MetaDataPK所在模块包名
		String metaDataPackageName = metaDataPK.getPackage();

		if ("app".equals(subPackage)) {
			if (!metaDataPackageName.endsWith(".app")) {
				throw new RuntimeException(metaDataPK + "：不是服务端元数据!");
			} else {
				int appIndex = metaDataPackageName.lastIndexOf(".app");
				modelPackageName = metaDataPackageName.substring(0, appIndex);
			}
		} else if ("client".equals(subPackage)) {
			if (!metaDataPackageName.endsWith(".client")) {
				throw new RuntimeException(metaDataPK + "：不是客户端元数据!");
			} else {
				int appIndex = metaDataPackageName.lastIndexOf(".client");
				modelPackageName = metaDataPackageName.substring(0, appIndex);
			}
		} else if (null == subPackage) {
			modelPackageName = metaDataPackageName;
		} else {
			List subPackageList = Arrays.asList(new String[] { "app", "client", null });
			throw new RuntimeException(subPackage + "：不合法的参数，只能是" + subPackageList + "之一!");
		}

		return modelPackageName;
	}

	/**
	 * 生成 表_索引 映射
	 * 
	 * @param tableNameArr
	 *            表数组
	 * @param indexPrefix
	 *            索引前缀；可以为空，默认为"IX_FDC_"
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
		// 索引前缀;可以为空，默认为"IX_FDC_"
		indexPrefix = FdcObjectUtil.defaultIfNull(indexPrefix, "IX_FDC_");

		Map indexTableMap = new LinkedHashMap();
		for (int i = 0; i < len; i++) {
			tableName = tableNameArr[i];
			// 根据字符类型、驼峰状态拆分字符串
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
	 * 是否存在元数据
	 * 
	 * @param metadataRootPath
	 * @param fullName
	 * @param extName
	 * @return
	 */
	public static boolean exists(String metadataRootPath, String fullName, String extName) {
		boolean flag = false;

		// 取得元数据文件完整名称
		String bizUnitFileName = getMetaDataFileFullName(metadataRootPath, fullName, extName);
		File bizUnitFile = new File(bizUnitFileName);
		// 已经存在，直接忽略
		flag = bizUnitFile.exists();

		return flag;
	}

	/**
	 * 是否存在元数据
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
	 * 打印输出实体表映射
	 * 
	 * @param ctx
	 *            应用上下文；可以为空
	 * @param packagePerfix
	 *            包前缀；不能为空
	 * @return
	 */
	public static void printWriteEntityTable(Context ctx, String packagePerfix) {
		String msg = "包前缀 不能为空";
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

		// 打印实体表映射
		FdcMetaDataUtil.printWriteEntityTable(tempEntityObjectCollection);
	}

	/**
	 * 打印输出实体表映射
	 * 
	 * @param ctx
	 *            应用上下文；可以为空
	 * @param bOSObjectTypeIterator
	 *            BOS对象类型集合迭代器；不能为空
	 * @return
	 */
	public static void printWriteEntityTable(Context ctx, Iterator bOSObjectTypeIterator) {
		String msg = "BOS对象类型集合迭代器 不能为空";
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

		// 打印实体表映射
		FdcMetaDataUtil.printWriteEntityTable(entityObjectCollection);
	}

	/**
	 * 打印输出实体表映射
	 * 
	 * @param entityObjectCollection
	 *            对象类型集合；不能为空
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
		lineStringBuffer.append("包名").append("~!");
		lineStringBuffer.append("实体名").append("~!");
		lineStringBuffer.append("实体别名").append("~!");
		lineStringBuffer.append("BOS对象类型").append("~!");
		lineStringBuffer.append("数据库表名").append("~!");
		lineStringBuffer.append("属性名").append("~!");
		lineStringBuffer.append("属性别名").append("~!");
		lineStringBuffer.append("列名").append("~!");
		lineStringBuffer.append("SQL类型别名").append("~!");
		lineStringBuffer.append("长度").append("~!");
		lineStringBuffer.append("精度").append("~!");
		lineStringBuffer.append("小数位数").append("~!");

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
				// 取得实体的所有自有属性以及继承来的属性, 并去除重复的属性(用属性名称判断)
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
			String fileName = "D:/Temp/Generate/EntityTable/实体表字典.txt";
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
	 * 设置orgType拓展属性值
	 * 
	 * @param extendedPropertyElement
	 * @param entityFullName
	 */
	public static void setOrgTypeValue(Element extendedPropertyElement, String entityFullName) {
		// 属性值
		String value = "Transport";
		setExtendedPropertyValue(extendedPropertyElement, entityFullName, value);
	}

	/**
	 * 附加orgType拓展属性
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
		String msg = "组织类型值不能为空";
		// 值校验
		Assert.assertNotNull(msg, orgTypeValue);
		msg = orgTypeValue + " 是不合法的值，必须为：" + FdcMetaDataConstant.ORG_TYPE_SET;
		// 值校验
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
					noImplEntityMap.put(entityObjectInfo.getFullName(), "无业务实现：" + businessImplName);

					e.printStackTrace();
				}
			} else {
				businessImplName = null;
				businessImplClassz = null;
				continue;
			}

			// 排除抽象类
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
					notExistsEntityMap.put(entityFullName, "缓存中存在，而BOS透视图中不存在");
					continue;
				}
				rootElement = document.getRootElement();

				boolean changeFlag = false;
				Element orgTypeElement = getExtendedPropertyElement(rootElement, FdcMetaDataConstant.ORG_TYPE_KEY);
				String extendedPropertyValue = null;
				// 有orgType拓展属性
				if (null != orgTypeElement) {
					extendedPropertyValue = getExtendedPropertyValue(orgTypeElement, Locale.CHINA);

					// 拓展属性值为空或者不合法
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
						errorEntityMap.put(entityObjectInfo.getFullName(), "错误的实体");

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
			MapUtils.debugPrint(System.out, "实体Map", entityMap);
			System.out.println("size = " + entityMap.size());
			System.out.println("===========================================");
		}
		if (!noImplEntityMap.isEmpty()) {
			System.out.println("===========================================");
			MapUtils.debugPrint(System.out, "无业务实现实体", noImplEntityMap);
			System.out.println("size = " + noImplEntityMap.size());
			System.out.println("===========================================");
		}
		if (!notExistsEntityMap.isEmpty()) {
			System.out.println("===========================================");
			MapUtils.debugPrint(System.out, "BOS透视图中不存在的实体", notExistsEntityMap);
			System.out.println("size = " + notExistsEntityMap.size());
			System.out.println("===========================================");
		}
		if (!errorEntityMap.isEmpty()) {
			System.out.println("===========================================");
			MapUtils.debugPrint(System.out, "错误的实体", errorEntityMap);
			System.out.println("size = " + errorEntityMap.size());
			System.out.println("===========================================");
		}

		return entityMap;
	}

	/**
	 * 附加orgType拓展属性
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
	 * 附加orgType拓展属性
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
	 * 设置crNumberEdit拓展属性值
	 * 
	 * @param extendedPropertyElement
	 * @param entityFullName
	 */
	public static void setCRNumberEditValue(Element extendedPropertyElement, String entityFullName) {
		// 属性值
		String value = "true";
		setExtendedPropertyValue(extendedPropertyElement, entityFullName, value);
	}

	/**
	 * 附加crNumberEdit拓展属性
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
		String msg = "编码规则生成编码可修改 不能为空";
		// 值校验
		Assert.assertNotNull(msg, crNumberEditValue);
		msg = crNumberEditValue + " 是不合法的值，必须为：" + FdcMetaDataConstant.CR_NUMBER_EDIT_SET;
		// 值校验
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
					noImplEntityMap.put(entityObjectInfo.getFullName(), "无业务实现：" + businessImplName);

					e.printStackTrace();
				}
			} else {
				businessImplName = null;
				businessImplClassz = null;
				continue;
			}

			// 排除抽象类
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
					notExistsEntityMap.put(entityFullName, "缓存中存在，而BOS透视图中不存在");
					continue;
				}
				rootElement = document.getRootElement();

				boolean changeFlag = false;
				Element crNumberEditElement = getExtendedPropertyElement(rootElement,
						FdcMetaDataConstant.CR_NUMBER_EDIT_KEY);
				String extendedPropertyValue = null;
				// 有crNumberEdit拓展属性
				if (null != crNumberEditElement) {
					extendedPropertyValue = getExtendedPropertyValue(crNumberEditElement, Locale.CHINA);

					// 拓展属性值为空或者不合法
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
						errorEntityMap.put(entityObjectInfo.getFullName(), "错误的实体");

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
	 * 附加crNumberEdit拓展属性
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
	 * 附加crNumberEdit拓展属性
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
	 * 取得元数据缓存接口
	 * 
	 * @param ctx
	 *            应用上下文；可以为空
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
	 * 取得最近的基类实体对象 <br>
	 * 
	 * <br>
	 * 先后判断顺序依次为：<br>
	 * 1、Project<br>
	 * 2、OrgUnit<br>
	 * 3、Period<br>
	 * 4、User<br>
	 * <br>
	 * 5、BillEntryBase<br>
	 * 6、EcBaseData<br>
	 * <br>
	 * 7、EcTreeBase<br>
	 * 7、TreeBase<br>
	 * 7、DataBase<br>
	 * <br>
	 * 10、EcBill<br>
	 * 11、FDCBill<br>
	 * 12、CoreBillBase<br>
	 * 13、ObjectBase<br>
	 * 14、CoreBase<br>
	 * <br>
	 * 
	 * @param ctx
	 *            应用上下文；可以为空
	 * @param bosTypeStr
	 *            BOS对象类型_字符串；不能为空
	 * @return
	 */
	public static EntityObjectInfo getSuperEntityObjectInfo(Context ctx, String bosTypeStr) {
		EntityObjectInfo superEntityObjectInfo = null;

		// 取得元数据缓存接口
		IMetaDataLoader metaDataLoader = getMetaDataLoader(ctx);
		superEntityObjectInfo = getSuperEntityObjectInfo(metaDataLoader, bosTypeStr);

		return superEntityObjectInfo;
	}

	/**
	 * 取得最近的基类实体对象 <br>
	 * 
	 * <br>
	 * 先后判断顺序依次为：<br>
	 * 1、Project<br>
	 * 2、OrgUnit<br>
	 * 3、Period<br>
	 * 4、User<br>
	 * <br>
	 * 5、BillEntryBase<br>
	 * 6、EcBaseData<br>
	 * <br>
	 * 7、EcTreeBase<br>
	 * 7、TreeBase<br>
	 * 7、DataBase<br>
	 * <br>
	 * 10、EcBill<br>
	 * 11、FDCBill<br>
	 * 12、CoreBillBase<br>
	 * 13、ObjectBase<br>
	 * 14、CoreBase<br>
	 * <br>
	 * 
	 * @param metaDataLoader
	 *            元数据缓存接口；不能为空
	 * @param bosTypeStr
	 *            BOS对象类型_字符串；不能为空
	 * @return
	 */
	public static EntityObjectInfo getSuperEntityObjectInfo(IMetaDataLoader metaDataLoader, String bosTypeStr) {
		EntityObjectInfo superEntityObjectInfo = null;

		EntityObjectInfo entityObjectInfo = getEntityObjectInfo(metaDataLoader, bosTypeStr);
		superEntityObjectInfo = getSuperEntityObjectInfo(metaDataLoader, entityObjectInfo);

		return superEntityObjectInfo;
	}

	/**
	 * 取得最近的基类实体对象 <br>
	 * 
	 * <br>
	 * 先后判断顺序依次为：<br>
	 * 1、Project<br>
	 * 2、OrgUnit<br>
	 * 3、Period<br>
	 * 4、User<br>
	 * <br>
	 * 5、BillEntryBase<br>
	 * 6、EcBaseData<br>
	 * <br>
	 * 7、EcTreeBase<br>
	 * 7、TreeBase<br>
	 * 7、DataBase<br>
	 * <br>
	 * 10、EcBill<br>
	 * 11、FDCBill<br>
	 * 12、CoreBillBase<br>
	 * 13、ObjectBase<br>
	 * 14、CoreBase<br>
	 * <br>
	 * 
	 * @param ctx
	 *            应用上下文；可以为空
	 * @param entityObjectInfo
	 *            BOS对象类型；不能为空
	 * @return
	 */
	public static EntityObjectInfo getSuperEntityObjectInfo(Context ctx, EntityObjectInfo entityObjectInfo) {
		EntityObjectInfo superEntityObjectInfo = null;

		// 取得元数据缓存接口
		IMetaDataLoader metaDataLoader = getMetaDataLoader(ctx);
		superEntityObjectInfo = getSuperEntityObjectInfo(metaDataLoader, entityObjectInfo);

		return superEntityObjectInfo;
	}

	/**
	 * 取得最近的基类实体对象 <br>
	 * 
	 * <br>
	 * 先后判断顺序依次为：<br>
	 * 1、Project<br>
	 * 2、OrgUnit<br>
	 * 3、Period<br>
	 * 4、User<br>
	 * <br>
	 * 5、BillEntryBase<br>
	 * 6、EcBaseData<br>
	 * <br>
	 * 7、EcTreeBase<br>
	 * 7、TreeBase<br>
	 * 7、DataBase<br>
	 * <br>
	 * 10、EcBill<br>
	 * 11、FDCBill<br>
	 * 12、CoreBillBase<br>
	 * 13、ObjectBase<br>
	 * 14、CoreBase<br>
	 * <br>
	 * 
	 * @param metaDataLoader
	 *            元数据缓存接口；不能为空
	 * @param entityObjectInfo
	 *            BOS对象类型；不能为空
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

		// 预定义类
		if (entityObjectInfo.isSubFrom(entityObjectInfo_project)) {
			superEntityObjectInfo = entityObjectInfo_project;
		} else if (entityObjectInfo.isSubFrom(entityObjectInfo_orgUnit)) {
			superEntityObjectInfo = entityObjectInfo_orgUnit;
		} else if (entityObjectInfo.isSubFrom(entityObjectInfo_period)) {
			superEntityObjectInfo = entityObjectInfo_period;
		} else if (entityObjectInfo.isSubFrom(entityObjectInfo_user)) {
			superEntityObjectInfo = entityObjectInfo_user;
		}
		// BillEntryBase派生类
		else if (entityObjectInfo.isSubFrom(entityObjectInfo_billEntryBase)) {
			superEntityObjectInfo = entityObjectInfo_billEntryBase;
		}
		// EcBaseData派生类
		else if (entityObjectInfo.isSubFrom(entityObjectInfo_ecBaseData)) {
			superEntityObjectInfo = entityObjectInfo_ecBaseData;
		}
		// DataBase派生类
		// else if (entityObjectInfo.isSubFrom(entityObjectInfo_ecTreeBase)) {
		// superEntityObjectInfo = entityObjectInfo_ecTreeBase;
		// }
		else if (entityObjectInfo.isSubFrom(entityObjectInfo_treeBase)) {
			superEntityObjectInfo = entityObjectInfo_treeBase;
		} else if (entityObjectInfo.isSubFrom(entityObjectInfo_dataBase)) {
			superEntityObjectInfo = entityObjectInfo_dataBase;
		}
		// FDCBill派生类
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
	 * 生成SelectorItemCollection
	 * 
	 * @param ctx
	 *            应用上下文；可以为空
	 * @param bosTypeStr
	 *            BOS对象类型_字符串；不能为空
	 * @param perfix
	 *            前缀；可以为空
	 * @param isGenLinkProperty
	 *            是否生成连接属性
	 * @return
	 */
	public static SelectorItemCollection generateSelector(Context ctx, String bosTypeStr, String perfix,
			boolean isGenLinkProperty) {
		SelectorItemCollection sic = new SelectorItemCollection();

		// 取得元数据缓存接口
		IMetaDataLoader metaDataLoader = getMetaDataLoader(ctx);

		BOSObjectType objectType = BOSObjectType.create(bosTypeStr);
		EntityObjectInfo entityObjectInfo = metaDataLoader.getEntity(objectType);
		// 取得实体的所有自有属性以及继承来的属性, 并去除重复的属性(用属性名称判断)
		PropertyCollection propertyCollection = entityObjectInfo.getInheritedNoDuplicatedPropertiesRuntime();
		// 取得连接属性对应的实体
		Hashtable childEntities = EntityObjectInfo.getChildEntities(entityObjectInfo);
		// 取得最近的基类实体对象
		EntityObjectInfo superEntityObjectInfo = getSuperEntityObjectInfo(metaDataLoader, entityObjectInfo);
		// 取得属性名List
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

			// 排除基类属性
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

					// 取得最近的基类实体对象
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
	 * 取得实体对象
	 * 
	 * 
	 * @param ctx
	 *            应用上下文；可以为空
	 * @param bosTypeStr
	 *            BOS对象类型_字符串；不能为空
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
	 * 取得实体对象
	 * 
	 * 
	 * @param metaDataLoader
	 *            元数据缓存接口；不能为空
	 * @param bosTypeStr
	 *            BOS对象类型_字符串；不能为空
	 * @return
	 */
	public static EntityObjectInfo getEntityObjectInfo(IMetaDataLoader metaDataLoader, String bosTypeStr) {
		EntityObjectInfo entityObjectInfo = null;

		entityObjectInfo = (EntityObjectInfo) ENTITY_OBJECT_INFO_MAP.get(bosTypeStr);
		if (null == entityObjectInfo) {
			BOSObjectType objectType = BOSObjectType.create(bosTypeStr);
			entityObjectInfo = metaDataLoader.getEntity(objectType);

			// 缓存数，不能超过CACHE_MAP_MAX_SIZE
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
	 * 生成属性代码
	 * 
	 * @param ctx
	 *            应用上下文；可以为空
	 * @param bosTypeStr
	 *            BOS对象类型_字符串；不能为空
	 */
	public static void generatePropertyCode(Context ctx, String bosTypeStr) {
		// 根据bosType编码创建BOSObjectType
		BOSObjectType objectType = BOSObjectType.create(bosTypeStr);
		// 根据bosType取得对应的实体
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

		// 取得实体的所有自有属性以及继承来的属性, 并去除重复的属性(用属性名称判断)
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

		// 取得所有可访问方法
		Method[] methodArr = classz.getMethods();
		Method method = null;
		String methodName = null;
		Class returnType = null;
		String returnTypeSimpleName = null;
		String codeLine = null;
		StringBuffer stringBuffer = new StringBuffer();

		// 原生集合类型
		Class listClassz = List.class;
		Class setClassz = Set.class;
		Class mapClassz = Map.class;
		// 对象集合类型
		Class objectCollectionClassz = IObjectCollection.class;
		// Boolean类型
		Class booleanType = Boolean.TYPE;
		Class booleanClassz = Boolean.TYPE;

		// 倒序输出
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

					// 原生集合类型
					if (listClassz.isAssignableFrom(returnType)) {
						codeLine += " = new ArrayList();\n";
					} else if (setClassz.isAssignableFrom(returnType)) {
						codeLine += " = new LinkedHashSet();\n";
					} else if (mapClassz.isAssignableFrom(returnType)) {
						codeLine += " = new LinkedHashMap();\n";
					}
					// 对象集合类型
					else if (objectCollectionClassz.isAssignableFrom(returnType)) {
						codeLine += " = new " + returnTypeSimpleName + "();\n";
					} else {
						codeLine += ";\n";
					}

					stringBuffer.append(codeLine);
				}
			}
			// Boolean类型
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
