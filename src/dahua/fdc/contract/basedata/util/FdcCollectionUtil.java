/*
 * @(#)FdcCollectionUtil.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.fdc.basedata.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * ���ز� ���Ϲ�����
 * 
 * @author ����
 * @email SkyIter@live.com
 * @date 2013-9-9
 * @see
 * @since 1.4
 */
public class FdcCollectionUtil {

	/**
	 * �Ƿ�Ϊ��
	 * 
	 * @param col
	 * @return
	 */
	public static boolean isEmpty(Collection col) {
		return (col == null || col.isEmpty());
	}

	/**
	 * �Ƿ�Ϊ��
	 * 
	 * @param col
	 * @return
	 */
	public static boolean isNotEmpty(Collection col) {
		return !FdcCollectionUtil.isEmpty(col);
	}

	/**
	 * ���ϣ�����ظ�ֵ
	 * 
	 * @param col1
	 * @param col2
	 * @return
	 */
	public static Collection union(Collection col1, Collection col2) {
		List list = new ArrayList();

		list.addAll(col1);
		list.addAll(col2);

		// ����ظ�ֵ
		Set set = new LinkedHashSet(list);
		list.clear();
		list.addAll(set);

		return list;
	}

	/**
	 * ����
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
	 * ���ϣ��򵥣����ظ���ӹ���ֵ
	 * 
	 * @param col1
	 * @param col2
	 * @return
	 */
	public static Collection unionSample(Collection col1, Collection col2) {
		List list = new ArrayList();

		list.addAll(col1);
		// �Ƴ�����ֵ
		list.removeAll(col2);
		list.addAll(col2);

		return list;
	}

	/**
	 * ���ϣ�����ظ�ֵ
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

		// ����ظ�ֵ
		Set set = new LinkedHashSet(list);
		list.clear();
		list.addAll(set);

		return list;
	}

	/**
	 * ����
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
	 * ����������е��ظ�ֵ
	 * 
	 * @param
	 * @param collection
	 * @return
	 */
	public static void clearDuplicate(Collection collection) {
		if (isNotEmpty(collection)) {
			// ����Set���˵��ظ�ֵ��ͬʱ����ԭ������˳��
			Set set = new LinkedHashSet(collection);

			// ���ԭ����
			collection.clear();
			// ��Set��Ԫ�����¼���Ԫ����
			collection.addAll(set);
		}
	}

	/**
	 * ����������е�Nullֵ
	 * 
	 * @param
	 * @param collection
	 * @return
	 */
	public static void clearNull(Collection collection) {
		if (isNotEmpty(collection)) {
			List nullList = new ArrayList();
			nullList.add(null);
			// ���˵�Nullֵ
			collection.removeAll(nullList);
		}
	}

	/**
	 * ����������е��ظ�ֵ��Nullֵ
	 * 
	 * @param
	 * @param collection
	 * @return
	 */
	public static void clearDuplicateAndNull(Collection collection) {
		if (isNotEmpty(collection)) {
			// ����Set���˵��ظ�ֵ��ͬʱ����ԭ������˳��
			Set set = new LinkedHashSet(collection);
			// �ٹ��˵�Nullֵ
			set.remove(null);

			// ���ԭ����
			collection.clear();
			// ��Set��Ԫ�����¼���Ԫ����
			collection.addAll(set);
		}
	}

	/**
	 * ����������Ԫ�ص��µļ�����
	 * <p>
	 * ���Ԫ��Ҳ�Ǽ��ϣ���ô�����������Ϊ�Ǽ���Ԫ�أ����뵽�µļ�����
	 * 
	 * @param collection
	 *            ����
	 * @param newCollection
	 *            �¼���
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
				// ����Ԫ�ص��µļ�����
				parseElementToNewCollection((Collection) object, newCollection);
			} else {
				newCollection.add(object);
			}
		}
	}

}
