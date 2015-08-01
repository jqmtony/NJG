/*
 * @(#)FdcCollectionUtil.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.basedata.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 房地产 集合工具类
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @date 2013-9-9
 * @see
 * @since 1.4
 */
public class FdcCollectionUtil {

	/**
	 * 是否为空
	 * 
	 * @param col
	 * @return
	 */
	public static boolean isEmpty(Collection col) {
		return (col == null || col.isEmpty());
	}

	/**
	 * 是否不为空
	 * 
	 * @param col
	 * @return
	 */
	public static boolean isNotEmpty(Collection col) {
		return !FdcCollectionUtil.isEmpty(col);
	}

	/**
	 * 联合，清除重复值
	 * 
	 * @param col1
	 * @param col2
	 * @return
	 */
	public static Collection union(Collection col1, Collection col2) {
		List list = new ArrayList();

		list.addAll(col1);
		list.addAll(col2);

		// 清除重复值
		Set set = new LinkedHashSet(list);
		list.clear();
		list.addAll(set);

		return list;
	}

	/**
	 * 联合
	 * 
	 * @param col1
	 * @param col2
	 * @return
	 */
	public static Collection unionAll(Collection col1, Collection col2) {
		List list = new ArrayList();

		list.addAll(col1);
		list.addAll(col2);

		return list;
	}

	/**
	 * 联合，简单，不重复添加共有值
	 * 
	 * @param col1
	 * @param col2
	 * @return
	 */
	public static Collection unionSample(Collection col1, Collection col2) {
		List list = new ArrayList();

		list.addAll(col1);
		// 移除共有值
		list.removeAll(col2);
		list.addAll(col2);

		return list;
	}

	/**
	 * 联合，清除重复值
	 * 
	 * @param col1
	 * @param col2
	 * @return
	 */
	public static Collection union(Collection[] colArr) {
		List list = new ArrayList();

		int length = colArr.length;
		Collection col = null;
		for (int i = 0; i < length; i++) {
			col = colArr[i];

			if (isNotEmpty(col)) {
				list.addAll(col);
			}
		}

		// 清除重复值
		Set set = new LinkedHashSet(list);
		list.clear();
		list.addAll(set);

		return list;
	}

	/**
	 * 联合
	 * 
	 * @param col1
	 * @param col2
	 * @return
	 */
	public static Collection unionAll(Collection[] colArr) {
		List list = new ArrayList();

		int length = colArr.length;
		Collection col = null;
		for (int i = 0; i < length; i++) {
			col = colArr[i];

			if (isNotEmpty(col)) {
				list.addAll(col);
			}
		}

		return list;
	}

	/**
	 * 清除掉集合中的重复值
	 * 
	 * @param
	 * @param collection
	 * @return
	 */
	public static void clearDuplicate(Collection collection) {
		if (isNotEmpty(collection)) {
			// 先用Set过滤掉重复值（同时保持原容器的顺序）
			Set set = new LinkedHashSet(collection);

			// 清除原容器
			collection.clear();
			// 将Set中元素重新加入元容器
			collection.addAll(set);
		}
	}

	/**
	 * 清除掉集合中的Null值
	 * 
	 * @param
	 * @param collection
	 * @return
	 */
	public static void clearNull(Collection collection) {
		if (isNotEmpty(collection)) {
			List nullList = new ArrayList();
			nullList.add(null);
			// 过滤掉Null值
			collection.removeAll(nullList);
		}
	}

	/**
	 * 清除掉集合中的重复值和Null值
	 * 
	 * @param
	 * @param collection
	 * @return
	 */
	public static void clearDuplicateAndNull(Collection collection) {
		if (isNotEmpty(collection)) {
			// 先用Set过滤掉重复值（同时保持原容器的顺序）
			Set set = new LinkedHashSet(collection);
			// 再过滤掉Null值
			set.remove(null);

			// 清除原容器
			collection.clear();
			// 将Set中元素重新加入元容器
			collection.addAll(set);
		}
	}

	/**
	 * 描述：解析元素到新的集合中
	 * <p>
	 * 如果元素也是集合，那么将其迭代，变为非集合元素，加入到新的集合中
	 * 
	 * @param collection
	 *            集合
	 * @param newCollection
	 *            新集合
	 * @author rd_skyiter_wang
	 * @createDate 2014-8-18
	 */
	public static void parseElementToNewCollection(Collection collection, Collection newCollection) {
		if (isEmpty(collection) || isEmpty(newCollection)) {
			return;
		}

		for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();

			if (object instanceof Collection) {
				// 解析元素到新的集合中
				parseElementToNewCollection((Collection) object, newCollection);
			} else {
				newCollection.add(object);
			}
		}
	}

}
