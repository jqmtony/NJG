/*
 * @(#)FdcArrayUtil.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.basedata.util;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 房地产 数组工具类
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @date 2013-9-9
 * @see
 * @since 1.4
 */
public class FdcArrayUtil {

	/**
	 * 一个空的固定 <code>Object</code> 数组.
	 */
	public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
	/**
	 * 一个空的固定 <code>Class</code> 数组.
	 */
	public static final Class[] EMPTY_CLASS_ARRAY = new Class[0];
	/**
	 * 一个空的固定 <code>String</code> 数组.
	 */
	public static final String[] EMPTY_STRING_ARRAY = new String[0];
	/**
	 * 一个空的固定 <code>long</code> 数组.
	 */
	public static final long[] EMPTY_LONG_ARRAY = new long[0];
	/**
	 * 一个空的固定 <code>Long</code> 数组.
	 */
	public static final Long[] EMPTY_LONG_OBJECT_ARRAY = new Long[0];
	/**
	 * 一个空的固定 <code>int</code> 数组.
	 */
	public static final int[] EMPTY_INT_ARRAY = new int[0];
	/**
	 * 一个空的固定 <code>Integer</code> 数组.
	 */
	public static final Integer[] EMPTY_INTEGER_OBJECT_ARRAY = new Integer[0];
	/**
	 * 一个空的固定 <code>short</code> 数组.
	 */
	public static final short[] EMPTY_SHORT_ARRAY = new short[0];
	/**
	 * 一个空的固定 <code>Short</code> 数组.
	 */
	public static final Short[] EMPTY_SHORT_OBJECT_ARRAY = new Short[0];
	/**
	 * 一个空的固定 <code>byte</code> 数组.
	 */
	public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
	/**
	 * 一个空的固定 <code>Byte</code> 数组.
	 */
	public static final Byte[] EMPTY_BYTE_OBJECT_ARRAY = new Byte[0];
	/**
	 * 一个空的固定 <code>double</code> 数组.
	 */
	public static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
	/**
	 * 一个空的固定 <code>Double</code> 数组.
	 */
	public static final Double[] EMPTY_DOUBLE_OBJECT_ARRAY = new Double[0];
	/**
	 * 一个空的固定 <code>float</code> 数组.
	 */
	public static final float[] EMPTY_FLOAT_ARRAY = new float[0];
	/**
	 * 一个空的固定 <code>Float</code> 数组.
	 */
	public static final Float[] EMPTY_FLOAT_OBJECT_ARRAY = new Float[0];
	/**
	 * 一个空的固定 <code>boolean</code> 数组.
	 */
	public static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];
	/**
	 * 一个空的固定 <code>Boolean</code> 数组.
	 */
	public static final Boolean[] EMPTY_BOOLEAN_OBJECT_ARRAY = new Boolean[0];
	/**
	 * 一个空的固定 <code>char</code> 数组.
	 */
	public static final char[] EMPTY_CHAR_ARRAY = new char[0];
	/**
	 * 一个空的固定 <code>Character</code> 数组.
	 */
	public static final Character[] EMPTY_CHARACTER_OBJECT_ARRAY = new Character[0];

	/**
	 * 是否都是空值
	 * <p>
	 * 只要有一个数组值不为空，则false
	 * 
	 * @param objectArr
	 * @return
	 */
	public static boolean isAllNull(Object[] objectArr) {
		boolean flag = true;

		if (null != objectArr) {
			int length = objectArr.length;

			for (int i = 0; i < length; i++) {
				if (null != objectArr[i]) {
					flag = false;

					break;
				}
			}
		}

		return flag;
	}

	/**
	 * 是否部分是空值
	 * <p>
	 * 只要有一个数组值为空，则true
	 * 
	 * @param objectArr
	 * @return
	 */
	public static boolean isSomeNull(Object[] objectArr) {
		boolean flag = false;

		if (null != objectArr) {
			int length = objectArr.length;

			for (int i = 0; i < length; i++) {
				if (null == objectArr[i]) {
					flag = true;

					break;
				}
			}
		}

		return flag;
	}

	/**
	 * 向目标数组末尾添加元素，返回一个新的数组，其长度为原数组的长度加1
	 * 
	 * @param objectArr
	 * @param object
	 * @return
	 */
	public static Object[] add(Object[] objectArr, Object object) {
		Object[] newObjectArr = null;

		int length = objectArr.length;
		newObjectArr = new Object[length + 1];
		System.arraycopy(objectArr, 0, newObjectArr, 0, length);
		newObjectArr[length] = object;

		return newObjectArr;
	}

	/**
	 * 向目标数组末尾添加元素，返回一个新的数组，其长度为原数组的长度加1
	 * 
	 * @param objectArr
	 * @param object
	 * @param castArr
	 * @return
	 */
	public static Object[] add(Object[] objectArr, Object object, Object[] castArr) {
		Object[] newObjectArr = null;

		int length = objectArr.length;
		newObjectArr = new Object[length + 1];
		System.arraycopy(objectArr, 0, newObjectArr, 0, length);
		newObjectArr[length] = object;

		// JDK1.5之前没有泛型，直接强转会报错
		List list = Arrays.asList(newObjectArr);
		castArr = list.toArray(castArr);

		return castArr;
	}

	/**
	 * 向目标数组末尾添加数组，返回一个新的数组，其长度为原2个数组长度之和
	 * 
	 * @param objectArr1
	 * @param objectArr2
	 * @return
	 */
	public static Object[] addAll(Object[] objectArr1, Object[] objectArr2) {
		Object[] newObjectArr = null;

		int length1 = objectArr1.length;
		int length2 = objectArr2.length;
		newObjectArr = new Object[length1 + length2];
		System.arraycopy(objectArr1, 0, newObjectArr, 0, length1);
		System.arraycopy(objectArr2, 0, newObjectArr, length1, length2);

		return newObjectArr;
	}

	/**
	 * 向目标数组末尾添加数组，返回一个新的数组，其长度为原2个数组长度之和
	 * 
	 * @param objectArr1
	 * @param objectArr2
	 * @param castArr
	 *            需要被转换的类型数组
	 * @return
	 */
	public static Object[] addAll(Object[] objectArr1, Object[] objectArr2, Object[] castArr) {
		Object[] newObjectArr = null;

		int length1 = objectArr1.length;
		int length2 = objectArr2.length;
		newObjectArr = new Object[length1 + length2];
		System.arraycopy(objectArr1, 0, newObjectArr, 0, length1);
		System.arraycopy(objectArr2, 0, newObjectArr, length1, length2);

		// JDK1.5之前没有泛型，直接强转会报错
		List list = Arrays.asList(newObjectArr);
		castArr = list.toArray(castArr);

		return castArr;
	}

	/**
	 * 调试打印
	 * 
	 * @param pr
	 * @param arr
	 */
	public static void debugPrint(PrintStream pr, Object[] arr) {
		if (null != arr) {
			List list = new ArrayList(Arrays.asList(arr));
			pr.println(list);
		}
	}

	/**
	 * 转换数组类型
	 * <p>
	 * JDK1.5之前没有泛型，直接强转会报错
	 * 
	 * @param origArr
	 *            源数组
	 * @param destArr
	 *            需要被转换的类型数组
	 * @return
	 */
	public static Object[] cast(Object[] origArr, Object[] destArr) {
		Object[] arrRs = null;

		if (null != origArr) {
			// 取得数组元素的 Class 类型
			Class origCmpType = origArr.getClass().getComponentType();
			Class destCmpType = destArr.getClass().getComponentType();

			// 如果类型相同，直接赋值
			if (origCmpType.equals(destCmpType)) {
				arrRs = origArr;
			} else {
				// JDK1.5之前没有泛型，直接强转会报错
				List list = Arrays.asList(origArr);
				arrRs = list.toArray(destArr);
			}
		}

		return arrRs;
	}

	/**
	 * 转换数组类型
	 * <p>
	 * JDK1.5之前没有泛型，直接强转会报错
	 * 
	 * @param origArr
	 *            源数组
	 * @param destArrCmpType
	 *            数组元素类型
	 * @return
	 */
	public static Object[] cast(Object[] origArr, Class destArrCmpType) {
		// 取得指定类型的长度为0的数组
		Object[] destArr = (Object[]) Array.newInstance(destArrCmpType, 0);
		destArr = cast(origArr, destArr);

		return destArr;
	}

	/**
	 * 转换成 Class 数组
	 * <p>
	 * JDK1.5之前没有泛型，直接强转会报错
	 * 
	 * @param origArr
	 *            源数组
	 * @return
	 */
	public static Class[] toClassArr(Object[] origArr) {
		Class[] destArr = EMPTY_CLASS_ARRAY;
		destArr = (Class[]) cast(origArr, destArr);

		return destArr;
	}

	/**
	 * 转换成 String 数组
	 * <p>
	 * JDK1.5之前没有泛型，直接强转会报错
	 * 
	 * @param origArr
	 *            源数组
	 * @return
	 */
	public static String[] toStringArr(Object[] origArr) {
		String[] destArr = EMPTY_STRING_ARRAY;
		destArr = (String[]) cast(origArr, destArr);

		return destArr;
	}

	/**
	 * 转换成 Long 数组
	 * <p>
	 * JDK1.5之前没有泛型，直接强转会报错
	 * 
	 * @param origArr
	 *            源数组
	 * @return
	 */
	public static Long[] toLongArr(Object[] origArr) {
		Long[] castArr = EMPTY_LONG_OBJECT_ARRAY;
		castArr = (Long[]) cast(origArr, castArr);

		return castArr;
	}

	/**
	 * 转换成 Integer 数组
	 * <p>
	 * JDK1.5之前没有泛型，直接强转会报错
	 * 
	 * @param origArr
	 *            源数组
	 * @return
	 */
	public static Integer[] toIntegerArr(Object[] origArr) {
		Integer[] castArr = EMPTY_INTEGER_OBJECT_ARRAY;
		castArr = (Integer[]) cast(origArr, castArr);

		return castArr;
	}

	/**
	 * 转换成 Short 数组
	 * <p>
	 * JDK1.5之前没有泛型，直接强转会报错
	 * 
	 * @param origArr
	 *            源数组
	 * @return
	 */
	public static Short[] toShortArr(Object[] origArr) {
		Short[] castArr = EMPTY_SHORT_OBJECT_ARRAY;
		castArr = (Short[]) cast(origArr, castArr);

		return castArr;
	}

	/**
	 * 转换成 Byte 数组
	 * <p>
	 * JDK1.5之前没有泛型，直接强转会报错
	 * 
	 * @param origArr
	 *            源数组
	 * @return
	 */
	public static Byte[] toByteArr(Object[] origArr) {
		Byte[] castArr = EMPTY_BYTE_OBJECT_ARRAY;
		castArr = (Byte[]) cast(origArr, castArr);

		return castArr;
	}

	/**
	 * 转换成 Double 数组
	 * <p>
	 * JDK1.5之前没有泛型，直接强转会报错
	 * 
	 * @param origArr
	 *            源数组
	 * @return
	 */
	public static Double[] toDoubleArr(Object[] origArr) {
		Double[] castArr = EMPTY_DOUBLE_OBJECT_ARRAY;
		castArr = (Double[]) cast(origArr, castArr);

		return castArr;
	}

	/**
	 * 转换成 Float 数组
	 * <p>
	 * JDK1.5之前没有泛型，直接强转会报错
	 * 
	 * @param origArr
	 *            源数组
	 * @return
	 */
	public static Float[] toFloatArr(Object[] origArr) {
		Float[] castArr = EMPTY_FLOAT_OBJECT_ARRAY;
		castArr = (Float[]) cast(origArr, castArr);

		return castArr;
	}

	/**
	 * 转换成 Boolean 数组
	 * <p>
	 * JDK1.5之前没有泛型，直接强转会报错
	 * 
	 * @param origArr
	 *            源数组
	 * @return
	 */
	public static Boolean[] toBooleanArr(Object[] origArr) {
		Boolean[] castArr = EMPTY_BOOLEAN_OBJECT_ARRAY;
		castArr = (Boolean[]) cast(origArr, castArr);

		return castArr;
	}

	/**
	 * 转换成 Character 数组
	 * <p>
	 * JDK1.5之前没有泛型，直接强转会报错
	 * 
	 * @param origArr
	 *            源数组
	 * @return
	 */
	public static Character[] toCharacterArr(Object[] origArr) {
		Character[] castArr = EMPTY_CHARACTER_OBJECT_ARRAY;
		castArr = (Character[]) cast(origArr, castArr);

		return castArr;
	}

	/**
	 * 返回指定数组内容的字符串表示形式。字符串表示形式由数组的元素列表组成，括在方括号（"[]"）中。相邻元素用字符 ", "（逗号加空格）分隔。<br>
	 * 这些元素通过 String.valueOf(Object) 转换为字符串。如果 array 为 null，则返回 "null"。
	 * <p>
	 * 如果数组包含作为元素的其他数组，则通过从 Object 中继承的 Object.toString()
	 * 方法将它们转换为字符串，这描述了它们的标识，而不是它们的内容。
	 * <p>
	 * 此方法返回的值等于 Arrays.toString(array)或Arrays.asList(array).toString() 返回的值，除非
	 * array 为 null，在这种情况下返回 "null"。
	 * 
	 * @param
	 * @param array
	 *            数组
	 * @return
	 */
	public static String toString(Object[] array) {
		String stringResonse = null;

		if (null == array) {
			stringResonse = "null";
		} else if (0 == array.length) {
			stringResonse = "[]";
		} else {
			StringBuffer stringBuffer = new StringBuffer();
			// 用逗号加空格连接
			String joinFrag = ", ";

			stringBuffer.append("[");
			Object object = null;
			for (int i = 0, len = array.length; i < len; i++) {
				object = array[i];
				stringBuffer.append(object).append(joinFrag);
			}
			// 移除最后面的逗号加空格
			stringBuffer.delete(stringBuffer.length() - 2, stringBuffer.length());
			stringBuffer.append("]");

			stringResonse = stringBuffer.toString();
		}

		return stringResonse;
	}

	/**
	 * 返回指定数组内容的字符串表示形式。字符串表示形式由数组的元素列表组成，括在方括号（"[]"）中。相邻元素用joinFrag 分隔。<br>
	 * 这些元素通过 String.valueOf(Object) 转换为字符串。如果 array 为 null，则返回 "null"。
	 * <p>
	 * 如果数组包含作为元素的其他数组，则通过从 Object 中继承的 Object.toString()
	 * 方法将它们转换为字符串，这描述了它们的标识，而不是它们的内容。
	 * <p>
	 * 此方法返回的值等于 Arrays.toString(array)或Arrays.asList(array).toString() 返回的值，除非
	 * array 为 null，在这种情况下返回 "null"。
	 * 
	 * @param
	 * @param array
	 *            数组
	 * @param leftFrag
	 *            左边片段
	 * @param rightFrag
	 *            右边片段
	 * @param joinFrag
	 *            连接片段
	 * @return
	 */
	public static String toString(Object[] array, String leftFrag, String rightFrag, String joinFrag) {
		String stringResonse = null;

		leftFrag = (null == leftFrag) ? "null" : leftFrag;
		rightFrag = (null == rightFrag) ? "null" : rightFrag;
		joinFrag = (null == joinFrag) ? "null" : joinFrag;
		if (null == array) {
			stringResonse = "null";
		} else if (0 == array.length) {
			stringResonse = leftFrag + rightFrag;
		} else {
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(leftFrag);
			Object object = null;
			for (int i = 0, len = array.length; i < len; i++) {
				object = array[i];
				stringBuffer.append(object).append(joinFrag);
			}
			// 移除最后面的连接片段
			stringBuffer.delete(stringBuffer.length() - joinFrag.length(), stringBuffer.length());
			stringBuffer.append(rightFrag);

			stringResonse = stringBuffer.toString();
		}

		return stringResonse;
	}

	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////

	public static int indexOf(Object[] array, Object objectToFind) {
		return indexOf(array, objectToFind, 0);
	}

	public static int indexOf(Object[] array, Object objectToFind, int startIndex) {
		if (array == null) {
			return -1;
		}
		if (startIndex < 0) {
			startIndex = 0;
		}
		if (objectToFind == null) {
			for (int i = startIndex; i < array.length; i++) {
				if (array[i] == null)
					return i;
			}
		} else {
			for (int i = startIndex; i < array.length; i++) {
				if (objectToFind.equals(array[i])) {
					return i;
				}
			}
		}
		return -1;
	}

	public static int lastIndexOf(Object[] array, Object objectToFind) {
		return lastIndexOf(array, objectToFind, 2147483647);
	}

	public static int lastIndexOf(Object[] array, Object objectToFind, int startIndex) {
		if (array == null) {
			return -1;
		}
		if (startIndex < 0)
			return -1;
		if (startIndex >= array.length) {
			startIndex = array.length - 1;
		}
		if (objectToFind == null) {
			for (int i = startIndex; i >= 0; i--) {
				if (array[i] == null)
					return i;
			}
		} else {
			for (int i = startIndex; i >= 0; i--) {
				if (objectToFind.equals(array[i])) {
					return i;
				}
			}
		}
		return -1;
	}

	public static boolean contains(Object[] array, Object objectToFind) {
		return indexOf(array, objectToFind) != -1;
	}

	public static int indexOf(long[] array, long valueToFind) {
		return indexOf(array, valueToFind, 0);
	}

	public static int indexOf(long[] array, long valueToFind, int startIndex) {
		if (array == null) {
			return -1;
		}
		if (startIndex < 0) {
			startIndex = 0;
		}
		for (int i = startIndex; i < array.length; i++) {
			if (valueToFind == array[i]) {
				return i;
			}
		}
		return -1;
	}

	public static int lastIndexOf(long[] array, long valueToFind) {
		return lastIndexOf(array, valueToFind, 2147483647);
	}

	public static int lastIndexOf(long[] array, long valueToFind, int startIndex) {
		if (array == null) {
			return -1;
		}
		if (startIndex < 0)
			return -1;
		if (startIndex >= array.length) {
			startIndex = array.length - 1;
		}
		for (int i = startIndex; i >= 0; i--) {
			if (valueToFind == array[i]) {
				return i;
			}
		}
		return -1;
	}

	public static boolean contains(long[] array, long valueToFind) {
		return indexOf(array, valueToFind) != -1;
	}

	public static int indexOf(int[] array, int valueToFind) {
		return indexOf(array, valueToFind, 0);
	}

	public static int indexOf(int[] array, int valueToFind, int startIndex) {
		if (array == null) {
			return -1;
		}
		if (startIndex < 0) {
			startIndex = 0;
		}
		for (int i = startIndex; i < array.length; i++) {
			if (valueToFind == array[i]) {
				return i;
			}
		}
		return -1;
	}

	public static int lastIndexOf(int[] array, int valueToFind) {
		return lastIndexOf(array, valueToFind, 2147483647);
	}

	public static int lastIndexOf(int[] array, int valueToFind, int startIndex) {
		if (array == null) {
			return -1;
		}
		if (startIndex < 0)
			return -1;
		if (startIndex >= array.length) {
			startIndex = array.length - 1;
		}
		for (int i = startIndex; i >= 0; i--) {
			if (valueToFind == array[i]) {
				return i;
			}
		}
		return -1;
	}

	public static boolean contains(int[] array, int valueToFind) {
		return indexOf(array, valueToFind) != -1;
	}

	public static int indexOf(short[] array, short valueToFind) {
		return indexOf(array, valueToFind, 0);
	}

	public static int indexOf(short[] array, short valueToFind, int startIndex) {
		if (array == null) {
			return -1;
		}
		if (startIndex < 0) {
			startIndex = 0;
		}
		for (int i = startIndex; i < array.length; i++) {
			if (valueToFind == array[i]) {
				return i;
			}
		}
		return -1;
	}

	public static int lastIndexOf(short[] array, short valueToFind) {
		return lastIndexOf(array, valueToFind, 2147483647);
	}

	public static int lastIndexOf(short[] array, short valueToFind, int startIndex) {
		if (array == null) {
			return -1;
		}
		if (startIndex < 0)
			return -1;
		if (startIndex >= array.length) {
			startIndex = array.length - 1;
		}
		for (int i = startIndex; i >= 0; i--) {
			if (valueToFind == array[i]) {
				return i;
			}
		}
		return -1;
	}

	public static boolean contains(short[] array, short valueToFind) {
		return indexOf(array, valueToFind) != -1;
	}

	public static int indexOf(char[] array, char valueToFind) {
		return indexOf(array, valueToFind, 0);
	}

	public static int indexOf(char[] array, char valueToFind, int startIndex) {
		if (array == null) {
			return -1;
		}
		if (startIndex < 0) {
			startIndex = 0;
		}
		for (int i = startIndex; i < array.length; i++) {
			if (valueToFind == array[i]) {
				return i;
			}
		}
		return -1;
	}

	public static int lastIndexOf(char[] array, char valueToFind) {
		return lastIndexOf(array, valueToFind, 2147483647);
	}

	public static int lastIndexOf(char[] array, char valueToFind, int startIndex) {
		if (array == null) {
			return -1;
		}
		if (startIndex < 0)
			return -1;
		if (startIndex >= array.length) {
			startIndex = array.length - 1;
		}
		for (int i = startIndex; i >= 0; i--) {
			if (valueToFind == array[i]) {
				return i;
			}
		}
		return -1;
	}

	public static boolean contains(char[] array, char valueToFind) {
		return indexOf(array, valueToFind) != -1;
	}

	public static int indexOf(byte[] array, byte valueToFind) {
		return indexOf(array, valueToFind, 0);
	}

	public static int indexOf(byte[] array, byte valueToFind, int startIndex) {
		if (array == null) {
			return -1;
		}
		if (startIndex < 0) {
			startIndex = 0;
		}
		for (int i = startIndex; i < array.length; i++) {
			if (valueToFind == array[i]) {
				return i;
			}
		}
		return -1;
	}

	public static int lastIndexOf(byte[] array, byte valueToFind) {
		return lastIndexOf(array, valueToFind, 2147483647);
	}

	public static int lastIndexOf(byte[] array, byte valueToFind, int startIndex) {
		if (array == null) {
			return -1;
		}
		if (startIndex < 0)
			return -1;
		if (startIndex >= array.length) {
			startIndex = array.length - 1;
		}
		for (int i = startIndex; i >= 0; i--) {
			if (valueToFind == array[i]) {
				return i;
			}
		}
		return -1;
	}

	public static boolean contains(byte[] array, byte valueToFind) {
		return indexOf(array, valueToFind) != -1;
	}

	public static int indexOf(double[] array, double valueToFind) {
		return indexOf(array, valueToFind, 0);
	}

	public static int indexOf(double[] array, double valueToFind, double tolerance) {
		return indexOf(array, valueToFind, 0, tolerance);
	}

	public static int indexOf(double[] array, double valueToFind, int startIndex) {
		if (isEmpty(array)) {
			return -1;
		}
		if (startIndex < 0) {
			startIndex = 0;
		}
		for (int i = startIndex; i < array.length; i++) {
			if (valueToFind == array[i]) {
				return i;
			}
		}
		return -1;
	}

	public static int indexOf(double[] array, double valueToFind, int startIndex, double tolerance) {
		if (isEmpty(array)) {
			return -1;
		}
		if (startIndex < 0) {
			startIndex = 0;
		}
		double min = valueToFind - tolerance;
		double max = valueToFind + tolerance;
		for (int i = startIndex; i < array.length; i++) {
			if ((array[i] >= min) && (array[i] <= max)) {
				return i;
			}
		}
		return -1;
	}

	public static int lastIndexOf(double[] array, double valueToFind) {
		return lastIndexOf(array, valueToFind, 2147483647);
	}

	public static int lastIndexOf(double[] array, double valueToFind, double tolerance) {
		return lastIndexOf(array, valueToFind, 2147483647, tolerance);
	}

	public static int lastIndexOf(double[] array, double valueToFind, int startIndex) {
		if (isEmpty(array)) {
			return -1;
		}
		if (startIndex < 0)
			return -1;
		if (startIndex >= array.length) {
			startIndex = array.length - 1;
		}
		for (int i = startIndex; i >= 0; i--) {
			if (valueToFind == array[i]) {
				return i;
			}
		}
		return -1;
	}

	public static int lastIndexOf(double[] array, double valueToFind, int startIndex, double tolerance) {
		if (isEmpty(array)) {
			return -1;
		}
		if (startIndex < 0)
			return -1;
		if (startIndex >= array.length) {
			startIndex = array.length - 1;
		}
		double min = valueToFind - tolerance;
		double max = valueToFind + tolerance;
		for (int i = startIndex; i >= 0; i--) {
			if ((array[i] >= min) && (array[i] <= max)) {
				return i;
			}
		}
		return -1;
	}

	public static boolean contains(double[] array, double valueToFind) {
		return indexOf(array, valueToFind) != -1;
	}

	public static boolean contains(double[] array, double valueToFind, double tolerance) {
		return indexOf(array, valueToFind, 0, tolerance) != -1;
	}

	public static int indexOf(float[] array, float valueToFind) {
		return indexOf(array, valueToFind, 0);
	}

	public static int indexOf(float[] array, float valueToFind, int startIndex) {
		if (isEmpty(array)) {
			return -1;
		}
		if (startIndex < 0) {
			startIndex = 0;
		}
		for (int i = startIndex; i < array.length; i++) {
			if (valueToFind == array[i]) {
				return i;
			}
		}
		return -1;
	}

	public static int lastIndexOf(float[] array, float valueToFind) {
		return lastIndexOf(array, valueToFind, 2147483647);
	}

	public static int lastIndexOf(float[] array, float valueToFind, int startIndex) {
		if (isEmpty(array)) {
			return -1;
		}
		if (startIndex < 0)
			return -1;
		if (startIndex >= array.length) {
			startIndex = array.length - 1;
		}
		for (int i = startIndex; i >= 0; i--) {
			if (valueToFind == array[i]) {
				return i;
			}
		}
		return -1;
	}

	public static boolean contains(float[] array, float valueToFind) {
		return indexOf(array, valueToFind) != -1;
	}

	public static int indexOf(boolean[] array, boolean valueToFind) {
		return indexOf(array, valueToFind, 0);
	}

	public static int indexOf(boolean[] array, boolean valueToFind, int startIndex) {
		if (isEmpty(array)) {
			return -1;
		}
		if (startIndex < 0) {
			startIndex = 0;
		}
		for (int i = startIndex; i < array.length; i++) {
			if (valueToFind == array[i]) {
				return i;
			}
		}
		return -1;
	}

	public static int lastIndexOf(boolean[] array, boolean valueToFind) {
		return lastIndexOf(array, valueToFind, 2147483647);
	}

	public static int lastIndexOf(boolean[] array, boolean valueToFind, int startIndex) {
		if (isEmpty(array)) {
			return -1;
		}
		if (startIndex < 0)
			return -1;
		if (startIndex >= array.length) {
			startIndex = array.length - 1;
		}
		for (int i = startIndex; i >= 0; i--) {
			if (valueToFind == array[i]) {
				return i;
			}
		}
		return -1;
	}

	public static boolean contains(boolean[] array, boolean valueToFind) {
		return indexOf(array, valueToFind) != -1;
	}

	// ////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////

	public static boolean isEmpty(Object[] array) {
		return (array == null) || (array.length == 0);
	}

	public static boolean isEmpty(long[] array) {
		return (array == null) || (array.length == 0);
	}

	public static boolean isEmpty(int[] array) {
		return (array == null) || (array.length == 0);
	}

	public static boolean isEmpty(short[] array) {
		return (array == null) || (array.length == 0);
	}

	public static boolean isEmpty(char[] array) {
		return (array == null) || (array.length == 0);
	}

	public static boolean isEmpty(byte[] array) {
		return (array == null) || (array.length == 0);
	}

	public static boolean isEmpty(double[] array) {
		return (array == null) || (array.length == 0);
	}

	public static boolean isEmpty(float[] array) {
		return (array == null) || (array.length == 0);
	}

	public static boolean isEmpty(boolean[] array) {
		return (array == null) || (array.length == 0);
	}

	// ////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////

	/**
	 * 是否不为空
	 * 
	 * @param objectArr
	 * @return
	 */
	public static boolean isNotEmpty(Object[] objectArr) {
		return !isEmpty(objectArr);
	}

	// ////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////

	public static int indexOf(String[] array, String stringToFind, boolean isEqualsIgnoreCase) {
		return indexOf(array, stringToFind, 0, isEqualsIgnoreCase);
	}

	public static int indexOf(String[] array, String stringToFind, int startIndex, boolean isEqualsIgnoreCase) {
		if (array == null) {
			return -1;
		}
		if (startIndex < 0) {
			startIndex = 0;
		}
		if (stringToFind == null) {
			for (int i = startIndex; i < array.length; i++) {
				if (array[i] == null)
					return i;
			}
		} else {
			if (!isEqualsIgnoreCase) {
				for (int i = startIndex; i < array.length; i++) {
					if (stringToFind.equals(array[i])) {
						return i;
					}
				}
			} else {
				for (int i = startIndex; i < array.length; i++) {
					if (stringToFind.equalsIgnoreCase(array[i])) {
						return i;
					}
				}
			}
		}
		return -1;
	}

	/**
	 * 是否包含
	 * 
	 * @param array
	 *            数组
	 * @param stringToFind
	 *            被查找的String
	 * @param isEqualsIgnoreCase
	 *            是否忽略大小写
	 * @return
	 */
	public static boolean contains(String[] array, String stringToFind, boolean isEqualsIgnoreCase) {
		return indexOf(array, stringToFind, isEqualsIgnoreCase) != -1;
	}

	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////

	/**
	 * 清除掉数组中的重复值
	 * 
	 * @param
	 * @param array
	 * @return
	 */
	public static Object[] clearDuplicate(Object[] array) {
		if (isNotEmpty(array)) {
			// 先用Set过滤掉重复值（同时保持原容器的顺序）
			Set set = new LinkedHashSet(Arrays.asList(array));

			array = set.toArray(array);
		}

		return array;
	}

	/**
	 * 清除掉数组中的Null值
	 * 
	 * @param
	 * @param array
	 * @return
	 */
	public static Object[] clearNull(Object[] array) {
		if (isNotEmpty(array)) {
			List list = Arrays.asList(array);
			List nullList = new ArrayList();
			nullList.add(null);
			// 过滤掉Null值
			list.removeAll(nullList);

			array = list.toArray(array);
		}

		return array;
	}

	/**
	 * 清除掉数组中的重复值和Null值
	 * 
	 * @param
	 * @param array
	 * @return
	 */
	public static Object[] clearDuplicateAndNull(Object[] array) {
		if (isNotEmpty(array)) {
			// 先用Set过滤掉重复值（同时保持原容器的顺序）
			Set set = new LinkedHashSet(Arrays.asList(array));
			// 再过滤掉Null值
			set.remove(null);

			array = set.toArray(array);
		}

		return array;
	}

	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////

}
