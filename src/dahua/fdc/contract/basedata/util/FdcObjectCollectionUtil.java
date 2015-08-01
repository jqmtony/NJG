/*
 * @(#)FdcObjectCollectionUtil.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.basedata.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.framework.CoreBaseInfo;

/**
 * 房地产 对象集合工具类
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @date 2013-9-9
 * @see
 * @since 1.4
 */
public class FdcObjectCollectionUtil {

	/**
	 * 将对象集合 转换成List
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static List toList(IObjectCollection objectCollection) {
		List objectList = new ArrayList();

		Object[] objectArray = objectCollection.toArray();
		objectList = new ArrayList(Arrays.asList(objectArray));

		return objectList;
	}

	/**
	 * 将List 添加到对象集合中
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static AbstractObjectCollection addToObjectCollection(Collection objectCols,
			AbstractObjectCollection objectCollection) {
		for (Iterator iterator = objectCols.iterator(); iterator.hasNext();) {
			IObjectValue object = (IObjectValue) iterator.next();
			objectCollection.addObject(object);
		}

		return objectCollection;
	}

	/**
	 * 解析数组的ID映射
	 * 
	 * @param objectArray
	 * @return
	 */
	public static Map parseIdMap(Object[] objectArray) {
		Map idMap = new LinkedHashMap();

		for (int i = 0, length = objectArray.length; i < length; i++) {
			IObjectValue object = (IObjectValue) objectArray[i];

			if (object instanceof CoreBaseInfo) {
				CoreBaseInfo coreBaseInfo = (CoreBaseInfo) object;
				BOSUuid id = coreBaseInfo.getId();
				List infoList = (List) idMap.get(id);
				if (null == infoList) {
					infoList = new ArrayList();
					idMap.put(id, infoList);
				}

				infoList.add(coreBaseInfo);
			}
		}

		return idMap;
	}

	/**
	 * 解析迭代器的ID映射
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parseIdMap(Iterator iterator) {
		Map idMap = new LinkedHashMap();

		for (; iterator.hasNext();) {
			IObjectValue object = (IObjectValue) iterator.next();

			if (object instanceof CoreBaseInfo) {
				CoreBaseInfo coreBaseInfo = (CoreBaseInfo) object;
				BOSUuid id = coreBaseInfo.getId();
				List infoList = (List) idMap.get(id);
				if (null == infoList) {
					infoList = new ArrayList();
					idMap.put(id, infoList);
				}

				infoList.add(coreBaseInfo);
			}
		}

		return idMap;
	}

	/**
	 * 解析对象集合的ID映射
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parseIdMap(IObjectCollection objectCollection) {
		return parseIdMap(objectCollection.iterator());
	}

	/**
	 * 解析Map的ID映射
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parseIdMap(List objectList) {
		return parseIdMap(objectList.iterator());
	}

	/**
	 * 解析数组的主键映射
	 * 
	 * @param objectArray
	 * @return
	 */
	public static Map parseObjectPKMap(Object[] objectArray) {
		Map objectPKMap = new LinkedHashMap();

		for (int i = 0, length = objectArray.length; i < length; i++) {
			IObjectValue object = (IObjectValue) objectArray[i];

			if (object instanceof CoreBaseInfo) {
				CoreBaseInfo coreBaseInfo = (CoreBaseInfo) object;
				BOSUuid id = coreBaseInfo.getId();
				IObjectPK pk = new ObjectUuidPK(id);
				List infoList = (List) objectPKMap.get(id);
				if (null == infoList) {
					infoList = new ArrayList();
					objectPKMap.put(pk, infoList);
				}

				infoList.add(coreBaseInfo);
			}
		}

		return objectPKMap;
	}

	/**
	 * 解析迭代器的主键映射
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parseObjectPKMap(Iterator iterator) {
		Map objectPKMap = new LinkedHashMap();

		for (; iterator.hasNext();) {
			IObjectValue object = (IObjectValue) iterator.next();

			if (object instanceof CoreBaseInfo) {
				CoreBaseInfo coreBaseInfo = (CoreBaseInfo) object;
				BOSUuid id = coreBaseInfo.getId();
				IObjectPK pk = new ObjectUuidPK(id);
				List infoList = (List) objectPKMap.get(id);
				if (null == infoList) {
					infoList = new ArrayList();
					objectPKMap.put(pk, infoList);
				}

				infoList.add(coreBaseInfo);
			}
		}

		return objectPKMap;
	}

	/**
	 * 解析对象集合的主键映射
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parseObjectPKMap(IObjectCollection objectCollection) {
		return parseObjectPKMap(objectCollection.iterator());
	}

	/**
	 * 解析Map的主键映射
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parseObjectPKMap(List objectList) {
		return parseObjectPKMap(objectList.iterator());
	}

	/**
	 * 解析数组的字符串类型的ID映射
	 * 
	 * @param objectArray
	 * @return
	 */
	public static Map parseStringIdMap(Object[] objectArray) {
		Map stringIdMap = new LinkedHashMap();

		for (int i = 0, length = objectArray.length; i < length; i++) {
			IObjectValue object = (IObjectValue) objectArray[i];

			if (object instanceof CoreBaseInfo) {
				CoreBaseInfo coreBaseInfo = (CoreBaseInfo) object;
				BOSUuid id = coreBaseInfo.getId();
				String stringId = id.toString();
				List infoList = (List) stringIdMap.get(id);
				if (null == infoList) {
					infoList = new ArrayList();
					stringIdMap.put(stringId, infoList);
				}

				infoList.add(coreBaseInfo);
			}
		}

		return stringIdMap;
	}

	/**
	 * 解析迭代器的字符串类型的ID映射
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parseStringIdMap(Iterator iterator) {
		Map stringIdMap = new LinkedHashMap();

		for (; iterator.hasNext();) {
			IObjectValue object = (IObjectValue) iterator.next();

			if (object instanceof CoreBaseInfo) {
				CoreBaseInfo coreBaseInfo = (CoreBaseInfo) object;
				BOSUuid id = coreBaseInfo.getId();
				String stringId = id.toString();
				List infoList = (List) stringIdMap.get(id);
				if (null == infoList) {
					infoList = new ArrayList();
					stringIdMap.put(stringId, infoList);
				}

				infoList.add(coreBaseInfo);
			}
		}

		return stringIdMap;
	}

	/**
	 * 解析对象集合的字符串类型的ID映射
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parseStringIdMap(IObjectCollection objectCollection) {
		return parseStringIdMap(objectCollection.iterator());
	}

	/**
	 * 解析Map的字符串类型的ID映射
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parseStringIdMap(List objectList) {
		return parseStringIdMap(objectList.iterator());
	}

	/**
	 * 解析数组的ID列表
	 * 
	 * @param objectArray
	 * @return
	 */
	public static List parseIdList(Object[] objectArray) {
		List idList = new ArrayList();

		Map idMap = parseIdMap(objectArray);
		idList.addAll(idMap.keySet());

		return idList;
	}

	/**
	 * 解析迭代器的ID列表
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static List parseIdList(Iterator iterator) {
		List idList = new ArrayList();

		Map idMap = parseIdMap(iterator);
		idList.addAll(idMap.keySet());

		return idList;
	}

	/**
	 * 解析对象集合的ID列表
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static List parseIdList(IObjectCollection objectCollection) {
		return parseIdList(objectCollection.iterator());
	}

	/**
	 * 解析List的ID列表
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static List parseIdList(List objectList) {
		return parseIdList(objectList.iterator());
	}

	/**
	 * 解析数组的主键列表
	 * 
	 * @param objectArray
	 * @return
	 */
	public static List parseObjectPKList(Object[] objectArray) {
		List objectPKList = new ArrayList();

		Map objectPKMap = parseObjectPKMap(objectArray);
		objectPKList.addAll(objectPKMap.keySet());

		return objectPKList;
	}

	/**
	 * 解析迭代器的主键列表
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static List parseObjectPKList(Iterator iterator) {
		List objectPKList = new ArrayList();

		Map objectPKMap = parseObjectPKMap(iterator);
		objectPKList.addAll(objectPKMap.keySet());

		return objectPKList;
	}

	/**
	 * 解析对象集合的主键列表
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static List parseObjectPKList(IObjectCollection objectCollection) {
		return parseObjectPKList(objectCollection.iterator());
	}

	/**
	 * 解析List的主键列表
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static List parseObjectPKList(List objectList) {
		return parseObjectPKList(objectList.iterator());
	}

	/**
	 * 解析数组的字符串类型的ID列表
	 * 
	 * @param objectArray
	 * @return
	 */
	public static List parseStringIdList(Object[] objectArray) {
		List stringIdList = new ArrayList();

		Map stringIdMap = parseStringIdMap(objectArray);
		stringIdList.addAll(stringIdMap.keySet());

		return stringIdList;
	}

	/**
	 * 解析迭代器的字符串类型的ID列表
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static List parseStringIdList(Iterator iterator) {
		List stringIdList = new ArrayList();

		Map stringIdMap = parseStringIdMap(iterator);
		stringIdList.addAll(stringIdMap.keySet());

		return stringIdList;
	}

	/**
	 * 解析对象集合的字符串类型的ID列表
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static List parseStringIdList(IObjectCollection objectCollection) {
		return parseStringIdList(objectCollection.iterator());
	}

	/**
	 * 解析List的字符串类型的ID列表
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static List parseStringIdList(List objectList) {
		return parseStringIdList(objectList.iterator());
	}

	/**
	 * 解析数组的唯一性ID映射
	 * 
	 * @param objectArray
	 * @return
	 */
	public static Map parseUniqueIdMap(Object[] objectArray) {
		Map idMap = new LinkedHashMap();

		for (int i = 0, length = objectArray.length; i < length; i++) {
			IObjectValue object = (IObjectValue) objectArray[i];

			if (object instanceof CoreBaseInfo) {
				CoreBaseInfo coreBaseInfo = (CoreBaseInfo) object;
				BOSUuid id = coreBaseInfo.getId();
				// 检测是否存在重复的ID
				Assert.assertFalse("存在重复的ID：" + id, idMap.containsKey(id));

				idMap.put(id, coreBaseInfo);
			}
		}

		return idMap;
	}

	/**
	 * 解析迭代器的唯一性ID映射
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parseUniqueIdMap(Iterator iterator) {
		Map idMap = new LinkedHashMap();

		for (; iterator.hasNext();) {
			IObjectValue object = (IObjectValue) iterator.next();

			if (object instanceof CoreBaseInfo) {
				CoreBaseInfo coreBaseInfo = (CoreBaseInfo) object;
				BOSUuid id = coreBaseInfo.getId();
				// 检测是否存在重复的ID
				Assert.assertFalse("存在重复的ID：" + id, idMap.containsKey(id));

				idMap.put(id, coreBaseInfo);
			}
		}

		return idMap;
	}

	/**
	 * 解析对象集合的唯一性ID映射
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parseUniqueIdMap(IObjectCollection objectCollection) {
		return parseUniqueIdMap(objectCollection.iterator());
	}

	/**
	 * 解析List的唯一性ID映射
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parseUniqueIdMap(List objectList) {
		return parseUniqueIdMap(objectList.iterator());
	}

	/**
	 * 解析数组的唯一性主键映射
	 * 
	 * @param objectArray
	 * @return
	 */
	public static Map parseUniqueObjectPKMap(Object[] objectArray) {
		Map objectPKMap = new LinkedHashMap();

		for (int i = 0, length = objectArray.length; i < length; i++) {
			IObjectValue object = (IObjectValue) objectArray[i];

			if (object instanceof CoreBaseInfo) {
				CoreBaseInfo coreBaseInfo = (CoreBaseInfo) object;
				BOSUuid id = coreBaseInfo.getId();
				IObjectPK pk = new ObjectUuidPK(id);
				// 检测是否存在重复的主键
				Assert.assertFalse("存在重复的主键：" + pk, objectPKMap.containsKey(id));

				objectPKMap.put(pk, coreBaseInfo);
			}
		}

		return objectPKMap;
	}

	/**
	 * 解析迭代器的唯一性主键映射
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parseUniqueObjectPKMap(Iterator iterator) {
		Map objectPKMap = new LinkedHashMap();

		for (; iterator.hasNext();) {
			IObjectValue object = (IObjectValue) iterator.next();

			if (object instanceof CoreBaseInfo) {
				CoreBaseInfo coreBaseInfo = (CoreBaseInfo) object;
				BOSUuid id = coreBaseInfo.getId();
				IObjectPK pk = new ObjectUuidPK(id);
				// 检测是否存在重复的主键
				Assert.assertFalse("存在重复的主键：" + pk, objectPKMap.containsKey(id));

				objectPKMap.put(pk, coreBaseInfo);
			}
		}

		return objectPKMap;
	}

	/**
	 * 解析对象集合的唯一性主键映射
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parseUniqueObjectPKMap(IObjectCollection objectCollection) {
		return parseUniqueObjectPKMap(objectCollection.iterator());
	}

	/**
	 * 解析List的唯一性主键映射
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parseUniqueObjectPKMap(List objectList) {
		return parseUniqueObjectPKMap(objectList.iterator());
	}

	/**
	 * 解析数组的唯一性字符串类型的ID映射
	 * 
	 * @param objectArray
	 * @return
	 */
	public static Map parseUniqueStringIdMap(Object[] objectArray) {
		Map stringUniqueIdMap = new LinkedHashMap();

		for (int i = 0, length = objectArray.length; i < length; i++) {
			IObjectValue object = (IObjectValue) objectArray[i];

			if (object instanceof CoreBaseInfo) {
				CoreBaseInfo coreBaseInfo = (CoreBaseInfo) object;
				BOSUuid id = coreBaseInfo.getId();
				String stringId = id.toString();
				// 检测是否存在重复的主键
				Assert.assertFalse("存在重复字符串类型的ID：" + stringId, stringUniqueIdMap.containsKey(stringId));

				stringUniqueIdMap.put(stringId, coreBaseInfo);
			}
		}

		return stringUniqueIdMap;
	}

	/**
	 * 解析迭代器的唯一性字符串类型的ID映射
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parseUniqueStringIdMap(Iterator iterator) {
		Map stringUniqueIdMap = new LinkedHashMap();

		for (; iterator.hasNext();) {
			IObjectValue object = (IObjectValue) iterator.next();

			if (object instanceof CoreBaseInfo) {
				CoreBaseInfo coreBaseInfo = (CoreBaseInfo) object;
				BOSUuid id = coreBaseInfo.getId();
				String stringId = id.toString();
				// 检测是否存在重复的主键
				Assert.assertFalse("存在重复字符串类型的ID：" + stringId, stringUniqueIdMap.containsKey(stringId));

				stringUniqueIdMap.put(stringId, coreBaseInfo);
			}
		}

		return stringUniqueIdMap;
	}

	/**
	 * 解析对象集合的唯一性字符串类型的ID映射
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parseUniqueStringIdMap(IObjectCollection objectCollection) {
		return parseUniqueStringIdMap(objectCollection.iterator());
	}

	/**
	 * 解析List的唯一性字符串类型的ID映射
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parseUniqueStringIdMap(List objectList) {
		return parseUniqueStringIdMap(objectList.iterator());
	}

	/**
	 * 解析数组的唯一性ID列表
	 * 
	 * @param objectArray
	 * @return
	 */
	public static List parseUniqueIdList(Object[] objectArray) {
		List idList = new ArrayList();

		Map idMap = parseUniqueIdMap(objectArray);
		idList.addAll(idMap.keySet());

		return idList;
	}

	/**
	 * 解析迭代器的唯一性ID列表
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static List parseUniqueIdList(Iterator iterator) {
		List idList = new ArrayList();

		Map idMap = parseUniqueIdMap(iterator);
		idList.addAll(idMap.keySet());

		return idList;
	}

	/**
	 * 解析对象集合的唯一性ID列表
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static List parseUniqueIdList(IObjectCollection objectCollection) {
		return parseUniqueIdList(objectCollection.iterator());
	}

	/**
	 * 解析List的唯一性ID列表
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static List parseUniqueIdList(List objectList) {
		return parseUniqueIdList(objectList.iterator());
	}

	/**
	 * 解析数组的唯一性主键列表
	 * 
	 * @param objectArray
	 * @return
	 */
	public static List parseUniqueObjectPKList(Object[] objectArray) {
		List objectPKList = new ArrayList();

		Map objectPKMap = parseUniqueObjectPKMap(objectArray);
		objectPKList.addAll(objectPKMap.keySet());

		return objectPKList;
	}

	/**
	 * 解析迭代器的唯一性主键列表
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static List parseUniqueObjectPKList(Iterator iterator) {
		List objectPKList = new ArrayList();

		Map objectPKMap = parseUniqueObjectPKMap(iterator);
		objectPKList.addAll(objectPKMap.keySet());

		return objectPKList;
	}

	/**
	 * 解析对象集合的唯一性主键列表
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static List parseUniqueObjectPKList(IObjectCollection objectCollection) {
		return parseUniqueObjectPKList(objectCollection.iterator());
	}

	/**
	 * 解析List的唯一性主键列表
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static List parseUniqueObjectPKList(List objectList) {
		return parseUniqueObjectPKList(objectList.iterator());
	}

	/**
	 * 解析数组的唯一性字符串类型的ID列表
	 * 
	 * @param objectArray
	 * @return
	 */
	public static List parseUniqueStringIdList(Object[] objectArray) {
		List stringUniqueIdList = new ArrayList();

		Map stringUniqueIdMap = parseUniqueStringIdMap(objectArray);
		stringUniqueIdList.addAll(stringUniqueIdMap.keySet());

		return stringUniqueIdList;
	}

	/**
	 * 解析迭代器的唯一性字符串类型的ID列表
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static List parseUniqueStringIdList(Iterator iterator) {
		List stringUniqueIdList = new ArrayList();

		Map stringUniqueIdMap = parseUniqueStringIdMap(iterator);
		stringUniqueIdList.addAll(stringUniqueIdMap.keySet());

		return stringUniqueIdList;
	}

	/**
	 * 解析对象集合的唯一性字符串类型的ID列表
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static List parseUniqueStringIdList(IObjectCollection objectCollection) {
		return parseUniqueStringIdList(objectCollection.iterator());
	}

	/**
	 * 解析List的唯一性字符串类型的ID列表
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static List parseUniqueStringIdList(List objectList) {
		return parseUniqueStringIdList(objectList.iterator());
	}

	/**
	 * 是否为空
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static boolean isEmpty(IObjectCollection objectCollection) {
		return (null == objectCollection || objectCollection.isEmpty());
	}

	/**
	 * 是否不为空
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static boolean isNotEmpty(IObjectCollection objectCollection) {
		return !isEmpty(objectCollection);
	}

	/**
	 * 按照指定属性值，排序
	 * <p>
	 * 1、支持级联属性<br>
	 * 
	 * @param objectCollection
	 *            对象集合
	 * @param propertyName
	 *            属性名
	 * @param comparator
	 *            比较器
	 */
	public static void sort(IObjectCollection objectCollection, String propertyName, Comparator comparator) {
		if (objectCollection instanceof AbstractObjectCollection) {
			List objectList = toList(objectCollection);
			sort(objectList, propertyName, comparator);
			objectCollection.clear();
			addToObjectCollection(objectList, (AbstractObjectCollection) objectCollection);
		}
	}

	/**
	 * 按照指定属性值，排序
	 * <p>
	 * 1、根据元素的自然顺序 按升序进行排序<br>
	 * 2、支持级联属性<br>
	 * 
	 * @param objectCollection
	 *            对象集合
	 * @param propertyName
	 *            属性名
	 */
	public static void sort(IObjectCollection objectCollection, String propertyName) {
		sort(objectCollection, propertyName, null);
	}

	/**
	 * 按照指定属性值，排序
	 * <p>
	 * 1、支持级联属性<br>
	 * 
	 * @param objectList
	 *            对象List
	 * @param propertyName
	 *            属性名
	 * @param comparator
	 *            比较器
	 */
	public static void sort(List objectList, String propertyName, Comparator comparator) {
		// // 检查是否有getter
		// Class classz = objectList.get(0).getClass();
		// Method method = EcBeanUtil.getGetter(classz, propertyName);
		// Assert.assertNotNull(propertyName + " 没有对应的getter!", method);

		// 分拣相同属性值的对象
		Map map = new HashMap();
		for (Iterator iterator = objectList.iterator(); iterator.hasNext();) {
			IObjectValue object = (IObjectValue) iterator.next();
			Object propertyValue = FdcObjectValueUtil.get(object, propertyName);

			List subList = (List) map.get(propertyValue);
			if (null == subList) {
				subList = new ArrayList();
				map.put(propertyValue, subList);
			}
			subList.add(object);
		}

		// 排序属性值
		List keyList = new ArrayList(map.keySet());
		boolean hasNull = keyList.contains(null);
		if (hasNull) {
			// 移除空值，防止空指针异常
			keyList.remove(null);
		}
		Collections.sort(keyList, comparator);
		if (hasNull) {
			// 空值放在最前面
			keyList.add(0, null);
		}

		// 按照排序后属性值，重新提取对象List
		List list = new ArrayList();
		for (Iterator iterator = keyList.iterator(); iterator.hasNext();) {
			Object propertyValue = (Object) iterator.next();
			List subList = (List) map.get(propertyValue);
			list.addAll(subList);
		}

		// 将排序后的对象List重新添加添加原对象List中
		objectList.clear();
		objectList.addAll(list);
	}

	/**
	 * 按照指定属性值，排序
	 * <p>
	 * 1、根据元素的自然顺序 按升序进行排序<br>
	 * 2、支持级联属性<br>
	 * 
	 * @param objectList
	 *            对象List
	 * @param propertyName
	 *            属性名
	 */
	public static void sort(List objectList, String propertyName) {
		sort(objectList, propertyName, null);
	}

	/**
	 * 按照指定属性值，排序
	 * <p>
	 * 1、支持级联属性<br>
	 * 
	 * @param objectCollection
	 *            对象集合
	 * @param propertyNames
	 *            属性名数组
	 * @param comparator
	 *            比较器
	 */
	public static void sort(IObjectCollection objectCollection, String[] propertyNames, Comparator comparator) {
		if ((objectCollection instanceof AbstractObjectCollection)) {
			List objectList = toList(objectCollection);
			sort(objectList, propertyNames, comparator);
			objectCollection.clear();
			addToObjectCollection(objectList, (AbstractObjectCollection) objectCollection);
		}
	}

	/**
	 * 按照指定属性值，排序
	 * <p>
	 * 1、根据元素的自然顺序 按升序进行排序<br>
	 * 2、支持级联属性<br>
	 * 
	 * @param objectCollection
	 *            对象集合
	 * @param propertyNames
	 *            属性名数组
	 */
	public static void sort(IObjectCollection objectCollection, String[] propertyNames) {
		sort(objectCollection, propertyNames, null);
	}

	/**
	 * 按照指定属性值，排序
	 * <p>
	 * 1、支持级联属性<br>
	 * 
	 * @param objectList
	 *            对象List
	 * @param propertyNames
	 *            属性名数组
	 * @param comparator
	 *            比较器
	 */
	public static void sort(List objectList, String[] propertyNames, Comparator comparator) {
		// // 检查是否有getter
		// Class classz = objectList.get(0).getClass();
		// Method method = EcBeanUtil.getGetter(classz, propertyNames);
		// Assert.assertNotNull(propertyName + " 没有对应的getter!", method);

		// 分拣相同属性值的对象
		Map map = new HashMap();
		for (Iterator iterator = objectList.iterator(); iterator.hasNext();) {
			IObjectValue object = (IObjectValue) iterator.next();
			String propertyValues = "";
			Object propertyValue = null;
			for (int i = 0, length = propertyNames.length; i < length; i++) {
				propertyValue = FdcObjectValueUtil.get(object, propertyNames[i]);
				propertyValue = FdcObjectUtil.defaultIfNull(propertyValue, " ");

				propertyValues += "_" + propertyValue;
			}

			List subList = (List) map.get(propertyValues);
			if (null == subList) {
				subList = new ArrayList();
				map.put(propertyValues, subList);
			}
			subList.add(object);
		}

		// 排序属性值
		List keyList = new ArrayList(map.keySet());
		boolean hasNull = keyList.contains(null);
		if (hasNull) {
			// 移除空值，防止空指针异常
			keyList.remove(null);
		}
		Collections.sort(keyList, comparator);
		if (hasNull) {
			// 空值放在最前面
			keyList.add(0, null);
		}

		// 按照排序后属性值，重新提取对象List
		List list = new ArrayList();
		for (Iterator iterator = keyList.iterator(); iterator.hasNext();) {
			Object propertyValue = (Object) iterator.next();
			List subList = (List) map.get(propertyValue);
			list.addAll(subList);
		}

		// 将排序后的对象List重新添加添加原对象List中
		objectList.clear();
		objectList.addAll(list);
	}

	/**
	 * 按照指定属性值，排序
	 * <p>
	 * 1、根据元素的自然顺序 按升序进行排序<br>
	 * 2、支持级联属性<br>
	 * 
	 * @param objectList
	 *            对象List
	 * @param propertyNames
	 *            属性名数组
	 */
	public static void sort(List objectList, String[] propertyNames) {
		sort(objectList, propertyNames, null);
	}

	/**
	 * 解析数组的属性映射
	 * <p>
	 * 1、支持级联属性<br>
	 * 
	 * @param objectArray
	 * @return
	 */
	public static Map parsePropertyMap(Object[] objectArray, String propertyName) {
		Map propertyMap = new LinkedHashMap();

		for (int i = 0, length = objectArray.length; i < length; i++) {
			IObjectValue object = (IObjectValue) objectArray[i];

			if (object instanceof CoreBaseInfo) {
				CoreBaseInfo coreBaseInfo = (CoreBaseInfo) object;
				Object propertyValue = FdcObjectValueUtil.get(coreBaseInfo, propertyName);
				List infoList = (List) propertyMap.get(propertyValue);
				if (null == infoList) {
					infoList = new ArrayList();
					propertyMap.put(propertyValue, infoList);
				}

				infoList.add(coreBaseInfo);
			}
		}

		return propertyMap;
	}

	/**
	 * 解析迭代器的属性映射
	 * <p>
	 * 1、支持级联属性<br>
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parsePropertyMap(Iterator iterator, String propertyName) {
		Map propertyMap = new LinkedHashMap();

		for (; iterator.hasNext();) {
			IObjectValue object = (IObjectValue) iterator.next();

			if (object instanceof CoreBaseInfo) {
				CoreBaseInfo coreBaseInfo = (CoreBaseInfo) object;
				Object propertyValue = FdcObjectValueUtil.get(coreBaseInfo, propertyName);
				List infoList = (List) propertyMap.get(propertyValue);
				if (null == infoList) {
					infoList = new ArrayList();
					propertyMap.put(propertyValue, infoList);
				}

				infoList.add(coreBaseInfo);
			}
		}

		return propertyMap;
	}

	/**
	 * 解析对象集合的属性映射
	 * <p>
	 * 1、支持级联属性<br>
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parsePropertyMap(IObjectCollection objectCollection, String propertyName) {
		return parsePropertyMap(objectCollection.iterator(), propertyName);
	}

	/**
	 * 解析Map的属性映射
	 * <p>
	 * 1、支持级联属性<br>
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parsePropertyMap(List objectList, String propertyName) {
		return parsePropertyMap(objectList.iterator(), propertyName);
	}

	/**
	 * 解析数组的唯一性属性映射
	 * <p>
	 * 1、支持级联唯一性属性<br>
	 * 
	 * @param objectArray
	 * @return
	 */
	public static Map parseUniquePropertyMap(Object[] objectArray, String propertyName) {
		Map propertyMap = new LinkedHashMap();

		for (int i = 0, length = objectArray.length; i < length; i++) {
			IObjectValue object = (IObjectValue) objectArray[i];

			if (object instanceof CoreBaseInfo) {
				CoreBaseInfo coreBaseInfo = (CoreBaseInfo) object;
				Object propertyValue = FdcObjectValueUtil.get(coreBaseInfo, propertyName);

				// 检测是否存在重复的唯一性属性
				Assert.assertFalse("存在重复的唯一性属性：" + propertyValue, propertyMap.containsKey(propertyValue));

				propertyMap.put(propertyValue, coreBaseInfo);
			}
		}

		return propertyMap;
	}

	/**
	 * 解析迭代器的唯一性属性映射
	 * <p>
	 * 1、支持级联唯一性属性<br>
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parseUniquePropertyMap(Iterator iterator, String propertyName) {
		Map propertyMap = new LinkedHashMap();

		for (; iterator.hasNext();) {
			IObjectValue object = (IObjectValue) iterator.next();

			if (object instanceof CoreBaseInfo) {
				CoreBaseInfo coreBaseInfo = (CoreBaseInfo) object;
				Object propertyValue = FdcObjectValueUtil.get(coreBaseInfo, propertyName);

				// 检测是否存在重复的唯一性属性
				Assert.assertFalse("存在重复的唯一性属性：" + propertyValue, propertyMap.containsKey(propertyValue));

				propertyMap.put(propertyValue, coreBaseInfo);
			}
		}

		return propertyMap;
	}

	/**
	 * 解析对象集合的唯一性属性映射
	 * <p>
	 * 1、支持级联唯一性属性<br>
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parseUniquePropertyMap(IObjectCollection objectCollection, String propertyName) {
		return parseUniquePropertyMap(objectCollection.iterator(), propertyName);
	}

	/**
	 * 解析Map的唯一性属性映射
	 * <p>
	 * 1、支持级联唯一性属性<br>
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parseUniquePropertyMap(List objectList, String propertyName) {
		return parseUniquePropertyMap(objectList.iterator(), propertyName);
	}

	/**
	 * 解析数组的唯一性属性列表
	 * <p>
	 * 1、支持级联唯一性属性<br>
	 * 
	 * @param objectArray
	 * @return
	 */
	public static List parseUniquePropertyList(Object[] objectArray, String propertyName) {
		List stringUniqueIdList = new ArrayList();

		Map stringUniqueIdMap = parseUniquePropertyMap(objectArray, propertyName);
		stringUniqueIdList.addAll(stringUniqueIdMap.keySet());

		return stringUniqueIdList;
	}

	/**
	 * 解析迭代器的唯一性属性列表
	 * <p>
	 * 1、支持级联唯一性属性<br>
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static List parseUniquePropertyList(Iterator iterator, String propertyName) {
		List stringUniqueIdList = new ArrayList();

		Map stringUniqueIdMap = parseUniquePropertyMap(iterator, propertyName);
		stringUniqueIdList.addAll(stringUniqueIdMap.keySet());

		return stringUniqueIdList;
	}

	/**
	 * 解析对象集合的唯一性属性列表
	 * <p>
	 * 1、支持级联唯一性属性<br>
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static List parseUniquePropertyList(IObjectCollection objectCollection, String propertyName) {
		return parseUniquePropertyList(objectCollection.iterator(), propertyName);
	}

	/**
	 * 解析List的唯一性属性列表
	 * <p>
	 * 1、支持级联唯一性属性<br>
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static List parseUniquePropertyList(List objectList, String propertyName) {
		return parseUniquePropertyList(objectList.iterator(), propertyName);
	}

	// //////////////////////////////////////////////////////////////////////////
	// Simple
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 解析数组的ID数组
	 * 
	 * @param objectArray
	 * @return
	 */
	public static BOSUuid[] parseIdArray(Object[] objectArray) {
		BOSUuid[] idArray = new BOSUuid[objectArray.length];

		for (int i = 0, length = objectArray.length; i < length; i++) {
			IObjectValue object = (IObjectValue) objectArray[i];

			if (object instanceof CoreBaseInfo) {
				CoreBaseInfo coreBaseInfo = (CoreBaseInfo) object;
				BOSUuid id = coreBaseInfo.getId();
				idArray[i] = id;
			}
		}

		return idArray;
	}

	/**
	 * 解析数组的字符串类型的ID数组
	 * 
	 * @param objectArray
	 * @return
	 */
	public static String[] parseStringIdArray(Object[] objectArray) {
		String[] stringIdArray = new String[objectArray.length];

		for (int i = 0, length = objectArray.length; i < length; i++) {
			IObjectValue object = (IObjectValue) objectArray[i];

			if (object instanceof CoreBaseInfo) {
				CoreBaseInfo coreBaseInfo = (CoreBaseInfo) object;
				BOSUuid id = coreBaseInfo.getId();
				String stringId = id.toString();
				stringIdArray[i] = stringId;
			}
		}

		return stringIdArray;
	}

	/**
	 * 解析数组的唯一性ID数组
	 * 
	 * @param objectArray
	 * @return
	 */
	public static BOSUuid[] parseUniqueIdArray(Object[] objectArray) {
		BOSUuid[] idArray = null;

		List idList = parseUniqueIdList(objectArray);
		idArray = (BOSUuid[]) idList.toArray(new BOSUuid[0]);

		return idArray;
	}

	/**
	 * 解析数组的唯一性字符串类型ID数组
	 * 
	 * @param objectArray
	 * @return
	 */
	public static String[] parseUniqueStringIdArray(Object[] objectArray) {
		String[] stringIdArray = null;

		List stringUniqueIdList = parseUniqueStringIdList(objectArray);
		stringIdArray = (String[]) stringUniqueIdList.toArray(new String[0]);

		return stringIdArray;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 清除掉集合中的重复值
	 * 
	 * @param
	 * @param objectCollection
	 * @return
	 */
	public static void clearDuplicate(IObjectCollection objectCollection) {
		if (isNotEmpty(objectCollection)) {
			List objectList = toList(objectCollection);

			// 清除掉集合中的重复值
			FdcCollectionUtil.clearDuplicate(objectList);

			objectCollection.clear();
			addToObjectCollection(objectList, (AbstractObjectCollection) objectCollection);
		}
	}

	/**
	 * 清除掉集合中的Null值
	 * 
	 * @param
	 * @param collection
	 * @return
	 */
	public static void clearNull(IObjectCollection objectCollection) {
		if (isNotEmpty(objectCollection)) {
			List objectList = toList(objectCollection);

			// 清除掉集合中的Null值
			FdcCollectionUtil.clearNull(objectList);

			objectCollection.clear();
			addToObjectCollection(objectList, (AbstractObjectCollection) objectCollection);
		}
	}

	/**
	 * 清除掉集合中的重复值和Null值
	 * 
	 * @param
	 * @param objectCollection
	 * @return
	 */
	public static void clearDuplicateAndNull(IObjectCollection objectCollection) {
		if (isNotEmpty(objectCollection)) {
			List objectList = toList(objectCollection);

			// 清除掉集合中的重复值和Null值
			FdcCollectionUtil.clearDuplicateAndNull(objectList);

			objectCollection.clear();
			addToObjectCollection(objectList, (AbstractObjectCollection) objectCollection);
		}
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////
}
