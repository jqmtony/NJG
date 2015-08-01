/**
 * @(#)FdcReflectUtil.java 1.0 2013-11-14
 * @author ����
 * @email SkyIter@live.com
 * Copyright 2013 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ���������ز����乤����
 * 
 * @author ����
 * @email SkyIter@live.com
 * @createDate 2013-11-14
 * @version 1.0, 2013-11-14
 * @see
 * @since JDK1.4
 */
public class FdcReflectUtil {

	/**
	 * ȡ�÷���
	 * 
	 * @param classz
	 *            ������
	 * @param methodName
	 *            ��������
	 * @param parameterTypes
	 *            ��������
	 * @param isIncludeNotPublic
	 *            �Ƿ�����ǹ����ķ���
	 * @return
	 */
	public static Method getMethod(Class classz, String methodName, Class[] parameterTypes, boolean isIncludeNotPublic) {
		Method method = null;

		/**
		 * JDK1.5��ǰ�ķ�������̫��˴����Ż�
		 */
		try {
			// �Ȼ�ȡ�ɷ��ʵ�
			method = classz.getMethod(methodName, parameterTypes);
		} catch (Exception e) {
		}

		if (null == method) {
			try {
				// �ٻ�ȡ��ǰ�����
				method = classz.getDeclaredMethod(methodName, parameterTypes);
			} catch (Exception e) {
			}
		}

		if (null == method) {
			// �ݹ����
			classz = classz.getSuperclass();
			if (null != classz && isIncludeNotPublic) {
				method = getMethod(classz, methodName, parameterTypes, isIncludeNotPublic);
			}
		}

		return method;
	}

	/**
	 * ȡ���޲�������
	 * 
	 * @param classz
	 * @param methodName
	 * @param isIncludeNotPublic
	 *            �Ƿ�����ǹ����ķ���
	 * @return
	 */
	public static Method getNoParameterMethod(Class classz, String methodName, boolean isIncludeNotPublic) {
		return getMethod(classz, methodName, null, isIncludeNotPublic);
	}

	/**
	 * ȡ���ֶ�
	 * 
	 * @param classz
	 *            ������
	 * @param fieldName
	 *            �ֶ�����
	 * @param isIncludeNotPublic
	 *            �Ƿ�����ǹ������ֶ�
	 * @return
	 */
	public static Field getField(Class classz, String fieldName, boolean isIncludeNotPublic) {
		Field field = null;

		/**
		 * JDK1.5��ǰ�ķ�������̫��˴����Ż�
		 */
		try {
			// �Ȼ�ȡ�ɷ��ʵ�
			field = classz.getField(fieldName);
		} catch (Exception e) {
		}

		if (null == field) {
			try {
				// �ٻ�ȡ��ǰ�����
				field = classz.getDeclaredField(fieldName);
			} catch (Exception e) {
			}
		}

		if (null == field) {
			// �ݹ����
			classz = classz.getSuperclass();
			if (null != classz && isIncludeNotPublic) {
				field = getField(classz, fieldName, isIncludeNotPublic);
			}

		}

		return field;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////

	/**
	 * ȡ��ָ����������ֶΣ������̳еģ��Լ�����Ȩ��Ϊ˽�еģ�
	 * 
	 * @param classType
	 *            ������
	 * @return
	 */
	public static List getAllFieldList(Class classType) {
		List fieldList = new ArrayList();

		try {
			if (null != classType.getSuperclass()) {
				// ȡ�ø����������ֶ�
				fieldList.addAll(getAllFieldList(classType.getSuperclass()));
			}
			Field[] fieldArr = classType.getDeclaredFields();
			fieldList.addAll(Arrays.asList(fieldArr));

			// ����������е��ظ�ֵ
			FdcCollectionUtil.clearDuplicate(fieldList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fieldList;
	}

	/**
	 * ����ָ��ǰ׺��ȡ��ָ����������ֶΣ������̳еģ��Լ�����Ȩ��Ϊ˽�еģ�
	 * 
	 * @param classType
	 *            ������
	 * @return
	 */
	public static List getAllFieldList(Class classType, String prefix) {
		List fieldList = new ArrayList();

		try {
			if (null != classType.getSuperclass()) {
				// ȡ�ø����������ֶ�
				fieldList.addAll(getAllFieldList(classType.getSuperclass(), prefix));
			}
			Field[] fieldArr = classType.getDeclaredFields();

			// ����ָ��ǰ׺�����˽��
			Field field = null;
			for (int i = 0, len = fieldArr.length; i < len; i++) {
				field = fieldArr[i];
				field.setAccessible(true);
				if (field.getName().startsWith(prefix)) {
					fieldList.add(field);
				}
			}

			// ����������е��ظ�ֵ
			FdcCollectionUtil.clearDuplicate(fieldList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fieldList;
	}

	/**
	 * ȡ��ָ����������ֶΣ������̳еģ��Լ�����Ȩ��Ϊ˽�еģ�
	 * 
	 * @param classType
	 * @return
	 */
	public static Map getAllFieldMap(Class classType) {
		Map fieldMap = new HashMap();
		;

		try {
			if (null != classType.getSuperclass()) {
				// ȡ�ø����������ֶ�
				fieldMap.putAll(getAllFieldMap(classType.getSuperclass()));
			}

			Field[] fieldArr = classType.getDeclaredFields();
			Field field = null;
			for (int i = 0; i < fieldArr.length; i++) {
				field = fieldArr[i];
				field.setAccessible(true);
				fieldMap.put(field.getName(), field);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fieldMap;
	}

	/**
	 * ����ָ��ǰ׺��ȡ��ָ����������ֶΣ������̳еģ��Լ�����Ȩ��Ϊ˽�еģ�
	 * 
	 * @param classType
	 *            ������
	 * @param prefix
	 * @return
	 */
	public static Map getAllFieldMap(Class classType, String prefix) {
		Map fieldMap = new HashMap();
		;

		try {
			if (null != classType.getSuperclass()) {
				// ȡ�ø����������ֶ�
				fieldMap.putAll(getAllFieldMap(classType.getSuperclass(), prefix));
			}

			if (FdcStringUtil.isNotBlank(prefix)) {
				Field[] fieldArr = classType.getDeclaredFields();
				Field field = null;
				for (int i = 0; i < fieldArr.length; i++) {
					field = fieldArr[i];

					if (field.getName().startsWith(prefix)) {
						field.setAccessible(true);
						fieldMap.put(field.getName(), field);
					}
				}
			} else {
				Field[] fieldArr = classType.getDeclaredFields();
				Field field = null;
				for (int i = 0; i < fieldArr.length; i++) {
					field = fieldArr[i];

					field.setAccessible(true);
					fieldMap.put(field.getName(), field);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fieldMap;
	}

	/**
	 * ȡ��ָ����������ֶ����֣������̳еģ��Լ�����Ȩ��Ϊ˽�еģ�
	 * 
	 * @param classType
	 *            ������
	 * @return
	 */
	public static List getAllFieldNameList(Class classType) {
		List fieldNameList = new ArrayList();

		try {
			if (null != classType.getSuperclass()) {
				// ȡ�ø����������ֶ�
				fieldNameList.addAll(getAllFieldNameList(classType.getSuperclass()));
			}
			Field[] fieldArr = classType.getDeclaredFields();
			for (int i = 0; i < fieldArr.length; i++) {
				fieldNameList.add(fieldArr[i].getName());
			}

			// ����������е��ظ�ֵ
			FdcCollectionUtil.clearDuplicate(fieldNameList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fieldNameList;
	}

	/**
	 * ����ָ��ǰ׺��ȡ��ָ����������ֶ����֣������̳еģ��Լ�����Ȩ��Ϊ˽�еģ�
	 * 
	 * @param classType
	 *            ������
	 * @param prefix
	 *            ǰ׺
	 * @return
	 */
	public static List getAllFieldNameList(Class classType, String prefix) {
		List fieldNameList = new ArrayList();

		try {
			if (null != classType.getSuperclass()) {
				// ȡ�ø����������ֶ�
				fieldNameList.addAll(getAllFieldNameList(classType.getSuperclass(), prefix));
			}
			Field[] fieldArr = classType.getDeclaredFields();
			Field field = null;
			for (int i = 0, len = fieldArr.length; i < len; i++) {
				field = fieldArr[i];
				// ����ָ��ǰ׺�����˽��
				if (field.getName().startsWith(prefix)) {
					fieldNameList.add(field.getName());
				}
			}

			// ����������е��ظ�ֵ
			FdcCollectionUtil.clearDuplicate(fieldNameList);

			/*
			 * //����ָ��ǰ׺�����˽�� List fieldNameListTemp = new ArrayList(); for (String string :
			 * fieldNameList) { if (string.startsWith(prefix)) { fieldNameListTemp.add(string); } }
			 * fieldNameList = fieldNameListTemp;
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fieldNameList;
	}

	/**
	 * ȡ��ָ��������пɷ��ʵ��������֣������̳еģ�
	 * 
	 * @param classType
	 *            ������
	 * @return
	 */
	public static List getFieldNameList(Class classType) {
		List fieldNameList = new ArrayList();

		try {
			Field[] fieldArr = classType.getFields();
			for (int i = 0; i < fieldArr.length; i++) {
				fieldNameList.add(fieldArr[i].getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fieldNameList;
	}

	/**
	 * ����ָ��ǰ׺��ȡ��ָ��������пɷ��ʵ��������֣������̳еģ�
	 * 
	 * @param classType
	 *            ������
	 * @param prefix
	 *            ǰ׺
	 * @return
	 */
	public static List getFieldNameList(Class classType, String prefix) {
		List fieldNameList = new ArrayList();

		try {
			Field[] fieldArr = classType.getFields();
			for (int i = 0; i < fieldArr.length; i++) {
				if (fieldArr[i].getName().startsWith(prefix)) {
					fieldNameList.add(fieldArr[i].getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fieldNameList;
	}

	/**
	 * ȡ����ָ�����������������ֶ�����
	 * 
	 * @param classType
	 *            ������
	 * @return
	 */
	public static List getDeclaredFieldNameList(Class classType) {
		List fieldNameList = new ArrayList();

		try {
			Field[] fieldArr = classType.getDeclaredFields();
			for (int i = 0; i < fieldArr.length; i++) {
				fieldNameList.add(fieldArr[i].getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fieldNameList;
	}

	/**
	 * ����ָ��ǰ׺��ȡ����ָ�����������������ֶ�����
	 * 
	 * @param classType
	 *            ������
	 * @param prefix
	 *            ǰ׺
	 * @return
	 */
	public static List getDeclaredFieldNameList(Class classType, String prefix) {
		List fieldNameList = new ArrayList();

		try {
			Field[] fieldArr = classType.getDeclaredFields();
			for (int i = 0; i < fieldArr.length; i++) {
				if (fieldArr[i].getName().startsWith(prefix)) {
					fieldNameList.add(fieldArr[i].getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fieldNameList;
	}

	/**
	 * ȡ��ָ���༰�丸������з������֣������̳еģ��Լ�����Ȩ��Ϊ˽�еģ���
	 * 
	 * @param classType
	 *            ������
	 * @return
	 */
	public static List getAllMethodNameList(Class classType) {
		List methodNameList = new ArrayList();

		try {
			// ȡ�ø��������ķ�������
			if (null != classType.getSuperclass()) {
				methodNameList.addAll(getAllMethodNameList(classType.getSuperclass()));
			}
			Method[] methodArr = classType.getDeclaredMethods();
			for (int i = 0; i < methodArr.length; i++) {
				methodNameList.add(methodArr[i].getName());
			}

			// ����������е��ظ�ֵ
			FdcCollectionUtil.clearDuplicate(methodNameList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return methodNameList;
	}

	/**
	 * ����ָ��ǰ׺��ȡ��ָ���༰�丸������з������֣������̳еģ��Լ�����Ȩ��Ϊ˽�еģ�
	 * 
	 * @param classType
	 *            ������
	 * @param prefix
	 *            ǰ׺
	 * @return
	 */
	public static List getAllMethodNameList(Class classType, String prefix) {
		List methodNameList = new ArrayList();

		try {
			if (null != classType.getSuperclass()) {
				// ȡ�ø��������ķ�������
				methodNameList.addAll(getAllMethodNameList(classType.getSuperclass(), prefix));
			}
			Method[] methodArr = classType.getDeclaredMethods();
			Method method = null;
			for (int i = 0, len = methodArr.length; i < len; i++) {
				method = methodArr[i];
				// ����ָ��ǰ׺�����˽��
				if (method.getName().startsWith(prefix)) {
					methodNameList.add(method.getName());
				}
			}

			// ����������е��ظ�ֵ
			FdcCollectionUtil.clearDuplicate(methodNameList);

			/*
			 * //����ָ��ǰ׺�����˽�� List methodNameListTemp = new ArrayList(); for (String string :
			 * methodNameList) { if (string.startsWith(prefix)) { methodNameListTemp.add(string); }
			 * } methodNameList = methodNameListTemp;
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}

		return methodNameList;
	}

	/**
	 * ȡ��ָ��������пɷ��ʷ������֣������̳еģ�
	 * 
	 * @param classType
	 *            ������
	 * @return
	 */
	public static List getMethodNameList(Class classType) {
		List methodNameList = new ArrayList();

		try {
			Method[] methodArr = classType.getMethods();
			for (int i = 0; i < methodArr.length; i++) {
				methodNameList.add(methodArr[i].getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return methodNameList;
	}

	/**
	 * ����ָ��ǰ׺��ȡ��ָ��������пɷ��ʷ������֣������̳еģ�
	 * 
	 * @param classType
	 *            ������
	 * @param prefix
	 *            ǰ׺
	 * @return
	 */
	public static List getMethodNameList(Class classType, String prefix) {
		List methodNameList = new ArrayList();

		try {
			Method[] methodArr = classType.getMethods();
			for (int i = 0; i < methodArr.length; i++) {
				if (methodArr[i].getName().startsWith(prefix)) {
					methodNameList.add(methodArr[i].getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return methodNameList;
	}

	/**
	 * ȡ����ָ���������������з�������
	 * 
	 * @param classType
	 *            ������
	 * @return
	 */
	public static List getDeclaredMethodNameList(Class classType) {
		List methodNameList = new ArrayList();

		try {
			Method[] methodArr = classType.getDeclaredMethods();
			for (int i = 0; i < methodArr.length; i++) {
				methodNameList.add(methodArr[i].getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return methodNameList;
	}

	/**
	 * ����ָ��ǰ׺��ȡ����ָ���������������з�������
	 * 
	 * @param classType
	 *            ������
	 * @param prefix
	 *            ǰ׺
	 * @return
	 */
	public static List getDeclaredMethodNameList(Class classType, String prefix) {
		List methodNameList = new ArrayList();

		try {
			Method[] methodArr = classType.getDeclaredMethods();
			for (int i = 0; i < methodArr.length; i++) {
				if (methodArr[i].getName().startsWith(prefix)) {
					methodNameList.add(methodArr[i].getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return methodNameList;
	}

	/**
	 * ȡ����ָ���������������з���
	 * 
	 * @param classType
	 *            ������
	 * @return
	 */
	public static List getDeclaredMethodList(Class classType) {
		List methodList = new ArrayList();

		try {
			Method[] methodArr = classType.getDeclaredMethods();
			methodList = Arrays.asList(methodArr);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return methodList;
	}

	/**
	 * ����ָ��ǰ׺��ȡ����ָ���������������з���
	 * 
	 * @param classType
	 *            ������
	 * @param prefix
	 *            ǰ׺
	 * @return
	 */
	public static List getDeclaredMethodList(Class classType, String prefix) {
		List methodList = new ArrayList();

		try {
			Method[] methodArr = classType.getDeclaredMethods();
			/* methodList = Arrays.asList(methodArr); */

			Method method = null;
			for (int i = 0, len = methodArr.length; i < len; i++) {
				method = methodArr[i];
				if (method.getName().startsWith(prefix)) {
					methodList.add(method);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return methodList;
	}

	/**
	 * ȡ��ָ���༰�丸������з����������̳еģ��Լ�����Ȩ��Ϊ˽�еģ�
	 * 
	 * @param classType
	 *            ������
	 * @return
	 */
	public static List getAllMethodList(Class classType) {
		List methodList = new ArrayList();

		try {
			// ȡ�ø��������ķ�������
			if (null != classType.getSuperclass()) {
				methodList.addAll(getAllMethodList(classType.getSuperclass()));
			}

			Method[] methodArr = classType.getDeclaredMethods();

			methodList.addAll(Arrays.asList(methodArr));
			// ����������е��ظ�ֵ
			FdcCollectionUtil.clearDuplicate(methodList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return methodList;
	}

	/**
	 * ����ָ��ǰ׺��ȡ��ָ���༰�丸������з����������̳еģ��Լ�����Ȩ��Ϊ˽�еģ�
	 * 
	 * @param classType
	 *            ������
	 * @param prefix
	 *            ǰ׺
	 * @return
	 */
	public static List getAllMethodList(Class classType, String prefix) {
		List methodList = new ArrayList();

		try {
			// ȡ�ø��������ķ�������
			if (null != classType.getSuperclass()) {
				methodList.addAll(getAllMethodList(classType.getSuperclass(), prefix));
			}

			Method[] methodArr = classType.getDeclaredMethods();
			Method method = null;
			for (int i = 0, len = methodArr.length; i < len; i++) {
				method = methodArr[i];
				if (method.getName().startsWith(prefix)) {
					methodList.add(method);
				}
			}

			// ����������е��ظ�ֵ
			FdcCollectionUtil.clearDuplicate(methodList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return methodList;
	}

	/**
	 * ����ת��
	 * 
	 * <pre>
	 * ֻ��ת�����������ͣ�
	 * 1���������Ͷ�Ӧ�İ�װ��
	 * 2������������
	 * 3��ʱ������
	 * 3���ַ���������
	 * </pre>
	 * 
	 * @param
	 * @param object
	 *            ԭʼ����
	 * @param classType
	 *            ��Ҫ��ת��������
	 * @return
	 */
	public static Object cast(Object object, Class classType) {
		Object tResponse = null;

		String objectStr = null;
		if (object instanceof java.util.Date) {
			objectStr = ((java.util.Date) object).getTime() + "";
		} else {
			objectStr = object.toString();
		}
		// �������Ͷ�Ӧ�İ�װ��
		if (classType.equals(Byte.class)) {
			tResponse = new Byte(objectStr);
		} else if (classType.equals(Short.class)) {
			tResponse = new Short(objectStr);
		} else if (classType.equals(Integer.class)) {
			tResponse = new Integer(objectStr);
		} else if (classType.equals(Long.class)) {
			tResponse = new Long(objectStr);
		} else if (classType.equals(Float.class)) {
			tResponse = new Float(objectStr);
		} else if (classType.equals(Double.class)) {
			tResponse = new Double(objectStr);
		} else if (classType.equals(Character.class)) {
			tResponse = new Character(objectStr.charAt(0));
		} else if (classType.equals(Boolean.class)) {
			tResponse = new Boolean(objectStr);
		}
		// ����������
		else if (classType.equals(BigInteger.class)) {
			tResponse = new BigInteger(objectStr);
		} else if (classType.equals(BigDecimal.class)) {
			tResponse = new BigDecimal(objectStr);
		}
		// ʱ������
		else if (classType.equals(java.util.Date.class)) {
			tResponse = new java.util.Date(Long.parseLong(objectStr));
		} else if (classType.equals(java.sql.Date.class)) {
			tResponse = new java.sql.Date(Long.parseLong(objectStr));
		} else if (classType.equals(java.sql.Time.class)) {
			tResponse = new java.sql.Time(Long.parseLong(objectStr));
		} else if (classType.equals(java.sql.Timestamp.class)) {
			tResponse = new java.sql.Timestamp(Long.parseLong(objectStr));
		}
		// �ַ���������
		else if (classType.equals(String.class)) {
			tResponse = objectStr;
		} else if (classType.equals(StringBuffer.class)) {
			tResponse = new StringBuffer(objectStr);
		}

		return tResponse;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////

	/**
	 * ȡ��ָ����������ֶΣ������̳еģ��Լ�����Ȩ��Ϊ˽�еģ�
	 * 
	 * @param classType
	 *            ������
	 * @param lastSuperclass
	 *            �������жϵĳ��� ������
	 * @return
	 */
	public static List getAllFieldList(Class classType, Class lastSuperclass) {
		List fieldList = new ArrayList();

		try {
			Class superclass = classType.getSuperclass();
			if (null != superclass && !lastSuperclass.equals(superclass)) {
				// ȡ�ø����������ֶ�
				fieldList.addAll(getAllFieldList(superclass));
			}
			Field[] fieldArr = classType.getDeclaredFields();
			fieldList.addAll(Arrays.asList(fieldArr));

			// ����������е��ظ�ֵ
			FdcCollectionUtil.clearDuplicate(fieldList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fieldList;
	}

	/**
	 * ����ָ��ǰ׺��ȡ��ָ����������ֶΣ������̳еģ��Լ�����Ȩ��Ϊ˽�еģ�
	 * 
	 * @param classType
	 *            ������
	 * @param lastSuperclass
	 *            �������жϵĳ��� ������
	 * @param prefix
	 *            �ֶ���ǰ׺
	 * @return
	 */
	public static List getAllFieldList(Class classType, Class lastSuperclass, String prefix) {
		List fieldList = new ArrayList();

		try {
			Class superclass = classType.getSuperclass();
			if (null != superclass && !lastSuperclass.equals(superclass)) {
				// ȡ�ø����������ֶ�
				fieldList.addAll(getAllFieldList(superclass, prefix));
			}
			Field[] fieldArr = classType.getDeclaredFields();

			// ����ָ��ǰ׺�����˽��
			Field field = null;
			for (int i = 0, len = fieldArr.length; i < len; i++) {
				field = fieldArr[i];
				field.setAccessible(true);
				if (field.getName().startsWith(prefix)) {
					fieldList.add(field);
				}
			}

			// ����������е��ظ�ֵ
			FdcCollectionUtil.clearDuplicate(fieldList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fieldList;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////

	public static void main(String[] args) {
	}

}
