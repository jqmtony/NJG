/**
 * @(#)FdcReflectUtil.java 1.0 2013-11-14
 * @author 王正
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
 * 描述：房地产反射工具类
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @createDate 2013-11-14
 * @version 1.0, 2013-11-14
 * @see
 * @since JDK1.4
 */
public class FdcReflectUtil {

	/**
	 * 取得方法
	 * 
	 * @param classz
	 *            类类型
	 * @param methodName
	 *            方法名称
	 * @param parameterTypes
	 *            参数类型
	 * @param isIncludeNotPublic
	 *            是否包括非公共的方法
	 * @return
	 */
	public static Method getMethod(Class classz, String methodName, Class[] parameterTypes, boolean isIncludeNotPublic) {
		Method method = null;

		/**
		 * JDK1.5以前的反射性能太差，此处待优化
		 */
		try {
			// 先获取可访问的
			method = classz.getMethod(methodName, parameterTypes);
		} catch (Exception e) {
		}

		if (null == method) {
			try {
				// 再获取当前定义的
				method = classz.getDeclaredMethod(methodName, parameterTypes);
			} catch (Exception e) {
			}
		}

		if (null == method) {
			// 递归查找
			classz = classz.getSuperclass();
			if (null != classz && isIncludeNotPublic) {
				method = getMethod(classz, methodName, parameterTypes, isIncludeNotPublic);
			}
		}

		return method;
	}

	/**
	 * 取得无参数方法
	 * 
	 * @param classz
	 * @param methodName
	 * @param isIncludeNotPublic
	 *            是否包括非公共的方法
	 * @return
	 */
	public static Method getNoParameterMethod(Class classz, String methodName, boolean isIncludeNotPublic) {
		return getMethod(classz, methodName, null, isIncludeNotPublic);
	}

	/**
	 * 取得字段
	 * 
	 * @param classz
	 *            类类型
	 * @param fieldName
	 *            字段名称
	 * @param isIncludeNotPublic
	 *            是否包括非公共的字段
	 * @return
	 */
	public static Field getField(Class classz, String fieldName, boolean isIncludeNotPublic) {
		Field field = null;

		/**
		 * JDK1.5以前的反射性能太差，此处待优化
		 */
		try {
			// 先获取可访问的
			field = classz.getField(fieldName);
		} catch (Exception e) {
		}

		if (null == field) {
			try {
				// 再获取当前定义的
				field = classz.getDeclaredField(fieldName);
			} catch (Exception e) {
			}
		}

		if (null == field) {
			// 递归查找
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
	 * 取得指定类的所有字段（包括继承的，以及访问权限为私有的）
	 * 
	 * @param classType
	 *            类类型
	 * @return
	 */
	public static List getAllFieldList(Class classType) {
		List fieldList = new ArrayList();

		try {
			if (null != classType.getSuperclass()) {
				// 取得父类声明的字段
				fieldList.addAll(getAllFieldList(classType.getSuperclass()));
			}
			Field[] fieldArr = classType.getDeclaredFields();
			fieldList.addAll(Arrays.asList(fieldArr));

			// 清除掉集合中的重复值
			FdcCollectionUtil.clearDuplicate(fieldList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fieldList;
	}

	/**
	 * 按照指定前缀，取得指定类的所有字段（包括继承的，以及访问权限为私有的）
	 * 
	 * @param classType
	 *            类类型
	 * @return
	 */
	public static List getAllFieldList(Class classType, String prefix) {
		List fieldList = new ArrayList();

		try {
			if (null != classType.getSuperclass()) {
				// 取得父类声明的字段
				fieldList.addAll(getAllFieldList(classType.getSuperclass(), prefix));
			}
			Field[] fieldArr = classType.getDeclaredFields();

			// 按照指定前缀，过滤结果
			Field field = null;
			for (int i = 0, len = fieldArr.length; i < len; i++) {
				field = fieldArr[i];
				field.setAccessible(true);
				if (field.getName().startsWith(prefix)) {
					fieldList.add(field);
				}
			}

			// 清除掉集合中的重复值
			FdcCollectionUtil.clearDuplicate(fieldList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fieldList;
	}

	/**
	 * 取得指定类的所有字段（包括继承的，以及访问权限为私有的）
	 * 
	 * @param classType
	 * @return
	 */
	public static Map getAllFieldMap(Class classType) {
		Map fieldMap = new HashMap();
		;

		try {
			if (null != classType.getSuperclass()) {
				// 取得父类声明的字段
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
	 * 按照指定前缀，取得指定类的所有字段（包括继承的，以及访问权限为私有的）
	 * 
	 * @param classType
	 *            类类型
	 * @param prefix
	 * @return
	 */
	public static Map getAllFieldMap(Class classType, String prefix) {
		Map fieldMap = new HashMap();
		;

		try {
			if (null != classType.getSuperclass()) {
				// 取得父类声明的字段
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
	 * 取得指定类的所有字段名字（包括继承的，以及访问权限为私有的）
	 * 
	 * @param classType
	 *            类类型
	 * @return
	 */
	public static List getAllFieldNameList(Class classType) {
		List fieldNameList = new ArrayList();

		try {
			if (null != classType.getSuperclass()) {
				// 取得父类声明的字段
				fieldNameList.addAll(getAllFieldNameList(classType.getSuperclass()));
			}
			Field[] fieldArr = classType.getDeclaredFields();
			for (int i = 0; i < fieldArr.length; i++) {
				fieldNameList.add(fieldArr[i].getName());
			}

			// 清除掉集合中的重复值
			FdcCollectionUtil.clearDuplicate(fieldNameList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fieldNameList;
	}

	/**
	 * 按照指定前缀，取得指定类的所有字段名字（包括继承的，以及访问权限为私有的）
	 * 
	 * @param classType
	 *            类类型
	 * @param prefix
	 *            前缀
	 * @return
	 */
	public static List getAllFieldNameList(Class classType, String prefix) {
		List fieldNameList = new ArrayList();

		try {
			if (null != classType.getSuperclass()) {
				// 取得父类声明的字段
				fieldNameList.addAll(getAllFieldNameList(classType.getSuperclass(), prefix));
			}
			Field[] fieldArr = classType.getDeclaredFields();
			Field field = null;
			for (int i = 0, len = fieldArr.length; i < len; i++) {
				field = fieldArr[i];
				// 按照指定前缀，过滤结果
				if (field.getName().startsWith(prefix)) {
					fieldNameList.add(field.getName());
				}
			}

			// 清除掉集合中的重复值
			FdcCollectionUtil.clearDuplicate(fieldNameList);

			/*
			 * //按照指定前缀，过滤结果 List fieldNameListTemp = new ArrayList(); for (String string :
			 * fieldNameList) { if (string.startsWith(prefix)) { fieldNameListTemp.add(string); } }
			 * fieldNameList = fieldNameListTemp;
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fieldNameList;
	}

	/**
	 * 取得指定类的所有可访问的属性名字（包括继承的）
	 * 
	 * @param classType
	 *            类类型
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
	 * 按照指定前缀，取得指定类的所有可访问的属性名字（包括继承的）
	 * 
	 * @param classType
	 *            类类型
	 * @param prefix
	 *            前缀
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
	 * 取得在指定类中声明的所有字段名字
	 * 
	 * @param classType
	 *            类类型
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
	 * 按照指定前缀，取得在指定类中声明的所有字段名字
	 * 
	 * @param classType
	 *            类类型
	 * @param prefix
	 *            前缀
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
	 * 取得指定类及其父类的所有方法名字（包括继承的，以及访问权限为私有的））
	 * 
	 * @param classType
	 *            类类型
	 * @return
	 */
	public static List getAllMethodNameList(Class classType) {
		List methodNameList = new ArrayList();

		try {
			// 取得父类声明的方法名字
			if (null != classType.getSuperclass()) {
				methodNameList.addAll(getAllMethodNameList(classType.getSuperclass()));
			}
			Method[] methodArr = classType.getDeclaredMethods();
			for (int i = 0; i < methodArr.length; i++) {
				methodNameList.add(methodArr[i].getName());
			}

			// 清除掉集合中的重复值
			FdcCollectionUtil.clearDuplicate(methodNameList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return methodNameList;
	}

	/**
	 * 按照指定前缀，取得指定类及其父类的所有方法名字（包括继承的，以及访问权限为私有的）
	 * 
	 * @param classType
	 *            类类型
	 * @param prefix
	 *            前缀
	 * @return
	 */
	public static List getAllMethodNameList(Class classType, String prefix) {
		List methodNameList = new ArrayList();

		try {
			if (null != classType.getSuperclass()) {
				// 取得父类声明的方法名字
				methodNameList.addAll(getAllMethodNameList(classType.getSuperclass(), prefix));
			}
			Method[] methodArr = classType.getDeclaredMethods();
			Method method = null;
			for (int i = 0, len = methodArr.length; i < len; i++) {
				method = methodArr[i];
				// 按照指定前缀，过滤结果
				if (method.getName().startsWith(prefix)) {
					methodNameList.add(method.getName());
				}
			}

			// 清除掉集合中的重复值
			FdcCollectionUtil.clearDuplicate(methodNameList);

			/*
			 * //按照指定前缀，过滤结果 List methodNameListTemp = new ArrayList(); for (String string :
			 * methodNameList) { if (string.startsWith(prefix)) { methodNameListTemp.add(string); }
			 * } methodNameList = methodNameListTemp;
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}

		return methodNameList;
	}

	/**
	 * 取得指定类的所有可访问方法名字（包括继承的）
	 * 
	 * @param classType
	 *            类类型
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
	 * 按照指定前缀，取得指定类的所有可访问方法名字（包括继承的）
	 * 
	 * @param classType
	 *            类类型
	 * @param prefix
	 *            前缀
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
	 * 取得在指定类中声明的所有方法名字
	 * 
	 * @param classType
	 *            类类型
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
	 * 按照指定前缀，取得在指定类中声明的所有方法名字
	 * 
	 * @param classType
	 *            类类型
	 * @param prefix
	 *            前缀
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
	 * 取得在指定类中声明的所有方法
	 * 
	 * @param classType
	 *            类类型
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
	 * 按照指定前缀，取得在指定类中声明的所有方法
	 * 
	 * @param classType
	 *            类类型
	 * @param prefix
	 *            前缀
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
	 * 取得指定类及其父类的所有方法（包括继承的，以及访问权限为私有的）
	 * 
	 * @param classType
	 *            类类型
	 * @return
	 */
	public static List getAllMethodList(Class classType) {
		List methodList = new ArrayList();

		try {
			// 取得父类声明的方法名字
			if (null != classType.getSuperclass()) {
				methodList.addAll(getAllMethodList(classType.getSuperclass()));
			}

			Method[] methodArr = classType.getDeclaredMethods();

			methodList.addAll(Arrays.asList(methodArr));
			// 清除掉集合中的重复值
			FdcCollectionUtil.clearDuplicate(methodList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return methodList;
	}

	/**
	 * 按照指定前缀，取得指定类及其父类的所有方法（包括继承的，以及访问权限为私有的）
	 * 
	 * @param classType
	 *            类类型
	 * @param prefix
	 *            前缀
	 * @return
	 */
	public static List getAllMethodList(Class classType, String prefix) {
		List methodList = new ArrayList();

		try {
			// 取得父类声明的方法名字
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

			// 清除掉集合中的重复值
			FdcCollectionUtil.clearDuplicate(methodList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return methodList;
	}

	/**
	 * 类型转换
	 * 
	 * <pre>
	 * 只能转换成以下类型：
	 * 1、基本类型对应的包装类
	 * 2、大数字类型
	 * 3、时间类型
	 * 3、字符序列类型
	 * </pre>
	 * 
	 * @param
	 * @param object
	 *            原始对象
	 * @param classType
	 *            需要被转化的类型
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
		// 基本类型对应的包装类
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
		// 大数字类型
		else if (classType.equals(BigInteger.class)) {
			tResponse = new BigInteger(objectStr);
		} else if (classType.equals(BigDecimal.class)) {
			tResponse = new BigDecimal(objectStr);
		}
		// 时间类型
		else if (classType.equals(java.util.Date.class)) {
			tResponse = new java.util.Date(Long.parseLong(objectStr));
		} else if (classType.equals(java.sql.Date.class)) {
			tResponse = new java.sql.Date(Long.parseLong(objectStr));
		} else if (classType.equals(java.sql.Time.class)) {
			tResponse = new java.sql.Time(Long.parseLong(objectStr));
		} else if (classType.equals(java.sql.Timestamp.class)) {
			tResponse = new java.sql.Timestamp(Long.parseLong(objectStr));
		}
		// 字符序列类型
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
	 * 取得指定类的所有字段（包括继承的，以及访问权限为私有的）
	 * 
	 * @param classType
	 *            类类型
	 * @param lastSuperclass
	 *            最终需判断的超类 类类型
	 * @return
	 */
	public static List getAllFieldList(Class classType, Class lastSuperclass) {
		List fieldList = new ArrayList();

		try {
			Class superclass = classType.getSuperclass();
			if (null != superclass && !lastSuperclass.equals(superclass)) {
				// 取得父类声明的字段
				fieldList.addAll(getAllFieldList(superclass));
			}
			Field[] fieldArr = classType.getDeclaredFields();
			fieldList.addAll(Arrays.asList(fieldArr));

			// 清除掉集合中的重复值
			FdcCollectionUtil.clearDuplicate(fieldList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fieldList;
	}

	/**
	 * 按照指定前缀，取得指定类的所有字段（包括继承的，以及访问权限为私有的）
	 * 
	 * @param classType
	 *            类类型
	 * @param lastSuperclass
	 *            最终需判断的超类 类类型
	 * @param prefix
	 *            字段名前缀
	 * @return
	 */
	public static List getAllFieldList(Class classType, Class lastSuperclass, String prefix) {
		List fieldList = new ArrayList();

		try {
			Class superclass = classType.getSuperclass();
			if (null != superclass && !lastSuperclass.equals(superclass)) {
				// 取得父类声明的字段
				fieldList.addAll(getAllFieldList(superclass, prefix));
			}
			Field[] fieldArr = classType.getDeclaredFields();

			// 按照指定前缀，过滤结果
			Field field = null;
			for (int i = 0, len = fieldArr.length; i < len; i++) {
				field = fieldArr[i];
				field.setAccessible(true);
				if (field.getName().startsWith(prefix)) {
					fieldList.add(field);
				}
			}

			// 清除掉集合中的重复值
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
