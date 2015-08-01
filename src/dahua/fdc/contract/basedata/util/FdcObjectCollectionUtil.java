/*
 * @(#)FdcObjectCollectionUtil.java
 *
 * �����������������޹�˾��Ȩ���� 
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
 * ���ز� ���󼯺Ϲ�����
 * 
 * @author ����
 * @email SkyIter@live.com
 * @date 2013-9-9
 * @see
 * @since 1.4
 */
public class FdcObjectCollectionUtil {

	/**
	 * �����󼯺� ת����List
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
	 * ��List ��ӵ����󼯺���
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
	 * ���������IDӳ��
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
	 * ������������IDӳ��
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
	 * �������󼯺ϵ�IDӳ��
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parseIdMap(IObjectCollection objectCollection) {
		return parseIdMap(objectCollection.iterator());
	}

	/**
	 * ����Map��IDӳ��
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parseIdMap(List objectList) {
		return parseIdMap(objectList.iterator());
	}

	/**
	 * �������������ӳ��
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
	 * ����������������ӳ��
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
	 * �������󼯺ϵ�����ӳ��
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parseObjectPKMap(IObjectCollection objectCollection) {
		return parseObjectPKMap(objectCollection.iterator());
	}

	/**
	 * ����Map������ӳ��
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parseObjectPKMap(List objectList) {
		return parseObjectPKMap(objectList.iterator());
	}

	/**
	 * ����������ַ������͵�IDӳ��
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
	 * �������������ַ������͵�IDӳ��
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
	 * �������󼯺ϵ��ַ������͵�IDӳ��
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parseStringIdMap(IObjectCollection objectCollection) {
		return parseStringIdMap(objectCollection.iterator());
	}

	/**
	 * ����Map���ַ������͵�IDӳ��
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parseStringIdMap(List objectList) {
		return parseStringIdMap(objectList.iterator());
	}

	/**
	 * ���������ID�б�
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
	 * ������������ID�б�
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
	 * �������󼯺ϵ�ID�б�
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static List parseIdList(IObjectCollection objectCollection) {
		return parseIdList(objectCollection.iterator());
	}

	/**
	 * ����List��ID�б�
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static List parseIdList(List objectList) {
		return parseIdList(objectList.iterator());
	}

	/**
	 * ��������������б�
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
	 * �����������������б�
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
	 * �������󼯺ϵ������б�
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static List parseObjectPKList(IObjectCollection objectCollection) {
		return parseObjectPKList(objectCollection.iterator());
	}

	/**
	 * ����List�������б�
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static List parseObjectPKList(List objectList) {
		return parseObjectPKList(objectList.iterator());
	}

	/**
	 * ����������ַ������͵�ID�б�
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
	 * �������������ַ������͵�ID�б�
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
	 * �������󼯺ϵ��ַ������͵�ID�б�
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static List parseStringIdList(IObjectCollection objectCollection) {
		return parseStringIdList(objectCollection.iterator());
	}

	/**
	 * ����List���ַ������͵�ID�б�
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static List parseStringIdList(List objectList) {
		return parseStringIdList(objectList.iterator());
	}

	/**
	 * ���������Ψһ��IDӳ��
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
				// ����Ƿ�����ظ���ID
				Assert.assertFalse("�����ظ���ID��" + id, idMap.containsKey(id));

				idMap.put(id, coreBaseInfo);
			}
		}

		return idMap;
	}

	/**
	 * ������������Ψһ��IDӳ��
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
				// ����Ƿ�����ظ���ID
				Assert.assertFalse("�����ظ���ID��" + id, idMap.containsKey(id));

				idMap.put(id, coreBaseInfo);
			}
		}

		return idMap;
	}

	/**
	 * �������󼯺ϵ�Ψһ��IDӳ��
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parseUniqueIdMap(IObjectCollection objectCollection) {
		return parseUniqueIdMap(objectCollection.iterator());
	}

	/**
	 * ����List��Ψһ��IDӳ��
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parseUniqueIdMap(List objectList) {
		return parseUniqueIdMap(objectList.iterator());
	}

	/**
	 * ���������Ψһ������ӳ��
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
				// ����Ƿ�����ظ�������
				Assert.assertFalse("�����ظ���������" + pk, objectPKMap.containsKey(id));

				objectPKMap.put(pk, coreBaseInfo);
			}
		}

		return objectPKMap;
	}

	/**
	 * ������������Ψһ������ӳ��
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
				// ����Ƿ�����ظ�������
				Assert.assertFalse("�����ظ���������" + pk, objectPKMap.containsKey(id));

				objectPKMap.put(pk, coreBaseInfo);
			}
		}

		return objectPKMap;
	}

	/**
	 * �������󼯺ϵ�Ψһ������ӳ��
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parseUniqueObjectPKMap(IObjectCollection objectCollection) {
		return parseUniqueObjectPKMap(objectCollection.iterator());
	}

	/**
	 * ����List��Ψһ������ӳ��
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parseUniqueObjectPKMap(List objectList) {
		return parseUniqueObjectPKMap(objectList.iterator());
	}

	/**
	 * ���������Ψһ���ַ������͵�IDӳ��
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
				// ����Ƿ�����ظ�������
				Assert.assertFalse("�����ظ��ַ������͵�ID��" + stringId, stringUniqueIdMap.containsKey(stringId));

				stringUniqueIdMap.put(stringId, coreBaseInfo);
			}
		}

		return stringUniqueIdMap;
	}

	/**
	 * ������������Ψһ���ַ������͵�IDӳ��
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
				// ����Ƿ�����ظ�������
				Assert.assertFalse("�����ظ��ַ������͵�ID��" + stringId, stringUniqueIdMap.containsKey(stringId));

				stringUniqueIdMap.put(stringId, coreBaseInfo);
			}
		}

		return stringUniqueIdMap;
	}

	/**
	 * �������󼯺ϵ�Ψһ���ַ������͵�IDӳ��
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parseUniqueStringIdMap(IObjectCollection objectCollection) {
		return parseUniqueStringIdMap(objectCollection.iterator());
	}

	/**
	 * ����List��Ψһ���ַ������͵�IDӳ��
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parseUniqueStringIdMap(List objectList) {
		return parseUniqueStringIdMap(objectList.iterator());
	}

	/**
	 * ���������Ψһ��ID�б�
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
	 * ������������Ψһ��ID�б�
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
	 * �������󼯺ϵ�Ψһ��ID�б�
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static List parseUniqueIdList(IObjectCollection objectCollection) {
		return parseUniqueIdList(objectCollection.iterator());
	}

	/**
	 * ����List��Ψһ��ID�б�
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static List parseUniqueIdList(List objectList) {
		return parseUniqueIdList(objectList.iterator());
	}

	/**
	 * ���������Ψһ�������б�
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
	 * ������������Ψһ�������б�
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
	 * �������󼯺ϵ�Ψһ�������б�
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static List parseUniqueObjectPKList(IObjectCollection objectCollection) {
		return parseUniqueObjectPKList(objectCollection.iterator());
	}

	/**
	 * ����List��Ψһ�������б�
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static List parseUniqueObjectPKList(List objectList) {
		return parseUniqueObjectPKList(objectList.iterator());
	}

	/**
	 * ���������Ψһ���ַ������͵�ID�б�
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
	 * ������������Ψһ���ַ������͵�ID�б�
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
	 * �������󼯺ϵ�Ψһ���ַ������͵�ID�б�
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static List parseUniqueStringIdList(IObjectCollection objectCollection) {
		return parseUniqueStringIdList(objectCollection.iterator());
	}

	/**
	 * ����List��Ψһ���ַ������͵�ID�б�
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static List parseUniqueStringIdList(List objectList) {
		return parseUniqueStringIdList(objectList.iterator());
	}

	/**
	 * �Ƿ�Ϊ��
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static boolean isEmpty(IObjectCollection objectCollection) {
		return (null == objectCollection || objectCollection.isEmpty());
	}

	/**
	 * �Ƿ�Ϊ��
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static boolean isNotEmpty(IObjectCollection objectCollection) {
		return !isEmpty(objectCollection);
	}

	/**
	 * ����ָ������ֵ������
	 * <p>
	 * 1��֧�ּ�������<br>
	 * 
	 * @param objectCollection
	 *            ���󼯺�
	 * @param propertyName
	 *            ������
	 * @param comparator
	 *            �Ƚ���
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
	 * ����ָ������ֵ������
	 * <p>
	 * 1������Ԫ�ص���Ȼ˳�� �������������<br>
	 * 2��֧�ּ�������<br>
	 * 
	 * @param objectCollection
	 *            ���󼯺�
	 * @param propertyName
	 *            ������
	 */
	public static void sort(IObjectCollection objectCollection, String propertyName) {
		sort(objectCollection, propertyName, null);
	}

	/**
	 * ����ָ������ֵ������
	 * <p>
	 * 1��֧�ּ�������<br>
	 * 
	 * @param objectList
	 *            ����List
	 * @param propertyName
	 *            ������
	 * @param comparator
	 *            �Ƚ���
	 */
	public static void sort(List objectList, String propertyName, Comparator comparator) {
		// // ����Ƿ���getter
		// Class classz = objectList.get(0).getClass();
		// Method method = EcBeanUtil.getGetter(classz, propertyName);
		// Assert.assertNotNull(propertyName + " û�ж�Ӧ��getter!", method);

		// �ּ���ͬ����ֵ�Ķ���
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

		// ��������ֵ
		List keyList = new ArrayList(map.keySet());
		boolean hasNull = keyList.contains(null);
		if (hasNull) {
			// �Ƴ���ֵ����ֹ��ָ���쳣
			keyList.remove(null);
		}
		Collections.sort(keyList, comparator);
		if (hasNull) {
			// ��ֵ������ǰ��
			keyList.add(0, null);
		}

		// �������������ֵ��������ȡ����List
		List list = new ArrayList();
		for (Iterator iterator = keyList.iterator(); iterator.hasNext();) {
			Object propertyValue = (Object) iterator.next();
			List subList = (List) map.get(propertyValue);
			list.addAll(subList);
		}

		// �������Ķ���List����������ԭ����List��
		objectList.clear();
		objectList.addAll(list);
	}

	/**
	 * ����ָ������ֵ������
	 * <p>
	 * 1������Ԫ�ص���Ȼ˳�� �������������<br>
	 * 2��֧�ּ�������<br>
	 * 
	 * @param objectList
	 *            ����List
	 * @param propertyName
	 *            ������
	 */
	public static void sort(List objectList, String propertyName) {
		sort(objectList, propertyName, null);
	}

	/**
	 * ����ָ������ֵ������
	 * <p>
	 * 1��֧�ּ�������<br>
	 * 
	 * @param objectCollection
	 *            ���󼯺�
	 * @param propertyNames
	 *            ����������
	 * @param comparator
	 *            �Ƚ���
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
	 * ����ָ������ֵ������
	 * <p>
	 * 1������Ԫ�ص���Ȼ˳�� �������������<br>
	 * 2��֧�ּ�������<br>
	 * 
	 * @param objectCollection
	 *            ���󼯺�
	 * @param propertyNames
	 *            ����������
	 */
	public static void sort(IObjectCollection objectCollection, String[] propertyNames) {
		sort(objectCollection, propertyNames, null);
	}

	/**
	 * ����ָ������ֵ������
	 * <p>
	 * 1��֧�ּ�������<br>
	 * 
	 * @param objectList
	 *            ����List
	 * @param propertyNames
	 *            ����������
	 * @param comparator
	 *            �Ƚ���
	 */
	public static void sort(List objectList, String[] propertyNames, Comparator comparator) {
		// // ����Ƿ���getter
		// Class classz = objectList.get(0).getClass();
		// Method method = EcBeanUtil.getGetter(classz, propertyNames);
		// Assert.assertNotNull(propertyName + " û�ж�Ӧ��getter!", method);

		// �ּ���ͬ����ֵ�Ķ���
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

		// ��������ֵ
		List keyList = new ArrayList(map.keySet());
		boolean hasNull = keyList.contains(null);
		if (hasNull) {
			// �Ƴ���ֵ����ֹ��ָ���쳣
			keyList.remove(null);
		}
		Collections.sort(keyList, comparator);
		if (hasNull) {
			// ��ֵ������ǰ��
			keyList.add(0, null);
		}

		// �������������ֵ��������ȡ����List
		List list = new ArrayList();
		for (Iterator iterator = keyList.iterator(); iterator.hasNext();) {
			Object propertyValue = (Object) iterator.next();
			List subList = (List) map.get(propertyValue);
			list.addAll(subList);
		}

		// �������Ķ���List����������ԭ����List��
		objectList.clear();
		objectList.addAll(list);
	}

	/**
	 * ����ָ������ֵ������
	 * <p>
	 * 1������Ԫ�ص���Ȼ˳�� �������������<br>
	 * 2��֧�ּ�������<br>
	 * 
	 * @param objectList
	 *            ����List
	 * @param propertyNames
	 *            ����������
	 */
	public static void sort(List objectList, String[] propertyNames) {
		sort(objectList, propertyNames, null);
	}

	/**
	 * �������������ӳ��
	 * <p>
	 * 1��֧�ּ�������<br>
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
	 * ����������������ӳ��
	 * <p>
	 * 1��֧�ּ�������<br>
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
	 * �������󼯺ϵ�����ӳ��
	 * <p>
	 * 1��֧�ּ�������<br>
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parsePropertyMap(IObjectCollection objectCollection, String propertyName) {
		return parsePropertyMap(objectCollection.iterator(), propertyName);
	}

	/**
	 * ����Map������ӳ��
	 * <p>
	 * 1��֧�ּ�������<br>
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parsePropertyMap(List objectList, String propertyName) {
		return parsePropertyMap(objectList.iterator(), propertyName);
	}

	/**
	 * ���������Ψһ������ӳ��
	 * <p>
	 * 1��֧�ּ���Ψһ������<br>
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

				// ����Ƿ�����ظ���Ψһ������
				Assert.assertFalse("�����ظ���Ψһ�����ԣ�" + propertyValue, propertyMap.containsKey(propertyValue));

				propertyMap.put(propertyValue, coreBaseInfo);
			}
		}

		return propertyMap;
	}

	/**
	 * ������������Ψһ������ӳ��
	 * <p>
	 * 1��֧�ּ���Ψһ������<br>
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

				// ����Ƿ�����ظ���Ψһ������
				Assert.assertFalse("�����ظ���Ψһ�����ԣ�" + propertyValue, propertyMap.containsKey(propertyValue));

				propertyMap.put(propertyValue, coreBaseInfo);
			}
		}

		return propertyMap;
	}

	/**
	 * �������󼯺ϵ�Ψһ������ӳ��
	 * <p>
	 * 1��֧�ּ���Ψһ������<br>
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parseUniquePropertyMap(IObjectCollection objectCollection, String propertyName) {
		return parseUniquePropertyMap(objectCollection.iterator(), propertyName);
	}

	/**
	 * ����Map��Ψһ������ӳ��
	 * <p>
	 * 1��֧�ּ���Ψһ������<br>
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static Map parseUniquePropertyMap(List objectList, String propertyName) {
		return parseUniquePropertyMap(objectList.iterator(), propertyName);
	}

	/**
	 * ���������Ψһ�������б�
	 * <p>
	 * 1��֧�ּ���Ψһ������<br>
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
	 * ������������Ψһ�������б�
	 * <p>
	 * 1��֧�ּ���Ψһ������<br>
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
	 * �������󼯺ϵ�Ψһ�������б�
	 * <p>
	 * 1��֧�ּ���Ψһ������<br>
	 * 
	 * @param objectCollection
	 * @return
	 */
	public static List parseUniquePropertyList(IObjectCollection objectCollection, String propertyName) {
		return parseUniquePropertyList(objectCollection.iterator(), propertyName);
	}

	/**
	 * ����List��Ψһ�������б�
	 * <p>
	 * 1��֧�ּ���Ψһ������<br>
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
	 * ���������ID����
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
	 * ����������ַ������͵�ID����
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
	 * ���������Ψһ��ID����
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
	 * ���������Ψһ���ַ�������ID����
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
	 * ����������е��ظ�ֵ
	 * 
	 * @param
	 * @param objectCollection
	 * @return
	 */
	public static void clearDuplicate(IObjectCollection objectCollection) {
		if (isNotEmpty(objectCollection)) {
			List objectList = toList(objectCollection);

			// ����������е��ظ�ֵ
			FdcCollectionUtil.clearDuplicate(objectList);

			objectCollection.clear();
			addToObjectCollection(objectList, (AbstractObjectCollection) objectCollection);
		}
	}

	/**
	 * ����������е�Nullֵ
	 * 
	 * @param
	 * @param collection
	 * @return
	 */
	public static void clearNull(IObjectCollection objectCollection) {
		if (isNotEmpty(objectCollection)) {
			List objectList = toList(objectCollection);

			// ����������е�Nullֵ
			FdcCollectionUtil.clearNull(objectList);

			objectCollection.clear();
			addToObjectCollection(objectList, (AbstractObjectCollection) objectCollection);
		}
	}

	/**
	 * ����������е��ظ�ֵ��Nullֵ
	 * 
	 * @param
	 * @param objectCollection
	 * @return
	 */
	public static void clearDuplicateAndNull(IObjectCollection objectCollection) {
		if (isNotEmpty(objectCollection)) {
			List objectList = toList(objectCollection);

			// ����������е��ظ�ֵ��Nullֵ
			FdcCollectionUtil.clearDuplicateAndNull(objectList);

			objectCollection.clear();
			addToObjectCollection(objectList, (AbstractObjectCollection) objectCollection);
		}
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////
}
