/*
 * @(#)FdcArrayUtil.java
 *
 * �����������������޹�˾��Ȩ���� 
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
 * ���ز� ���鹤����
 * 
 * @author ����
 * @email SkyIter@live.com
 * @date 2013-9-9
 * @see
 * @since 1.4
 */
public class FdcArrayUtil {

	/**
	 * һ���յĹ̶� <code>Object</code> ����.
	 */
	public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
	/**
	 * һ���յĹ̶� <code>Class</code> ����.
	 */
	public static final Class[] EMPTY_CLASS_ARRAY = new Class[0];
	/**
	 * һ���յĹ̶� <code>String</code> ����.
	 */
	public static final String[] EMPTY_STRING_ARRAY = new String[0];
	/**
	 * һ���յĹ̶� <code>long</code> ����.
	 */
	public static final long[] EMPTY_LONG_ARRAY = new long[0];
	/**
	 * һ���յĹ̶� <code>Long</code> ����.
	 */
	public static final Long[] EMPTY_LONG_OBJECT_ARRAY = new Long[0];
	/**
	 * һ���յĹ̶� <code>int</code> ����.
	 */
	public static final int[] EMPTY_INT_ARRAY = new int[0];
	/**
	 * һ���յĹ̶� <code>Integer</code> ����.
	 */
	public static final Integer[] EMPTY_INTEGER_OBJECT_ARRAY = new Integer[0];
	/**
	 * һ���յĹ̶� <code>short</code> ����.
	 */
	public static final short[] EMPTY_SHORT_ARRAY = new short[0];
	/**
	 * һ���յĹ̶� <code>Short</code> ����.
	 */
	public static final Short[] EMPTY_SHORT_OBJECT_ARRAY = new Short[0];
	/**
	 * һ���յĹ̶� <code>byte</code> ����.
	 */
	public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
	/**
	 * һ���յĹ̶� <code>Byte</code> ����.
	 */
	public static final Byte[] EMPTY_BYTE_OBJECT_ARRAY = new Byte[0];
	/**
	 * һ���յĹ̶� <code>double</code> ����.
	 */
	public static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
	/**
	 * һ���յĹ̶� <code>Double</code> ����.
	 */
	public static final Double[] EMPTY_DOUBLE_OBJECT_ARRAY = new Double[0];
	/**
	 * һ���յĹ̶� <code>float</code> ����.
	 */
	public static final float[] EMPTY_FLOAT_ARRAY = new float[0];
	/**
	 * һ���յĹ̶� <code>Float</code> ����.
	 */
	public static final Float[] EMPTY_FLOAT_OBJECT_ARRAY = new Float[0];
	/**
	 * һ���յĹ̶� <code>boolean</code> ����.
	 */
	public static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];
	/**
	 * һ���յĹ̶� <code>Boolean</code> ����.
	 */
	public static final Boolean[] EMPTY_BOOLEAN_OBJECT_ARRAY = new Boolean[0];
	/**
	 * һ���յĹ̶� <code>char</code> ����.
	 */
	public static final char[] EMPTY_CHAR_ARRAY = new char[0];
	/**
	 * һ���յĹ̶� <code>Character</code> ����.
	 */
	public static final Character[] EMPTY_CHARACTER_OBJECT_ARRAY = new Character[0];

	/**
	 * �Ƿ��ǿ�ֵ
	 * <p>
	 * ֻҪ��һ������ֵ��Ϊ�գ���false
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
	 * �Ƿ񲿷��ǿ�ֵ
	 * <p>
	 * ֻҪ��һ������ֵΪ�գ���true
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
	 * ��Ŀ������ĩβ���Ԫ�أ�����һ���µ����飬�䳤��Ϊԭ����ĳ��ȼ�1
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
	 * ��Ŀ������ĩβ���Ԫ�أ�����һ���µ����飬�䳤��Ϊԭ����ĳ��ȼ�1
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

		// JDK1.5֮ǰû�з��ͣ�ֱ��ǿת�ᱨ��
		List list = Arrays.asList(newObjectArr);
		castArr = list.toArray(castArr);

		return castArr;
	}

	/**
	 * ��Ŀ������ĩβ������飬����һ���µ����飬�䳤��Ϊԭ2�����鳤��֮��
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
	 * ��Ŀ������ĩβ������飬����һ���µ����飬�䳤��Ϊԭ2�����鳤��֮��
	 * 
	 * @param objectArr1
	 * @param objectArr2
	 * @param castArr
	 *            ��Ҫ��ת������������
	 * @return
	 */
	public static Object[] addAll(Object[] objectArr1, Object[] objectArr2, Object[] castArr) {
		Object[] newObjectArr = null;

		int length1 = objectArr1.length;
		int length2 = objectArr2.length;
		newObjectArr = new Object[length1 + length2];
		System.arraycopy(objectArr1, 0, newObjectArr, 0, length1);
		System.arraycopy(objectArr2, 0, newObjectArr, length1, length2);

		// JDK1.5֮ǰû�з��ͣ�ֱ��ǿת�ᱨ��
		List list = Arrays.asList(newObjectArr);
		castArr = list.toArray(castArr);

		return castArr;
	}

	/**
	 * ���Դ�ӡ
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
	 * ת����������
	 * <p>
	 * JDK1.5֮ǰû�з��ͣ�ֱ��ǿת�ᱨ��
	 * 
	 * @param origArr
	 *            Դ����
	 * @param destArr
	 *            ��Ҫ��ת������������
	 * @return
	 */
	public static Object[] cast(Object[] origArr, Object[] destArr) {
		Object[] arrRs = null;

		if (null != origArr) {
			// ȡ������Ԫ�ص� Class ����
			Class origCmpType = origArr.getClass().getComponentType();
			Class destCmpType = destArr.getClass().getComponentType();

			// ���������ͬ��ֱ�Ӹ�ֵ
			if (origCmpType.equals(destCmpType)) {
				arrRs = origArr;
			} else {
				// JDK1.5֮ǰû�з��ͣ�ֱ��ǿת�ᱨ��
				List list = Arrays.asList(origArr);
				arrRs = list.toArray(destArr);
			}
		}

		return arrRs;
	}

	/**
	 * ת����������
	 * <p>
	 * JDK1.5֮ǰû�з��ͣ�ֱ��ǿת�ᱨ��
	 * 
	 * @param origArr
	 *            Դ����
	 * @param destArrCmpType
	 *            ����Ԫ������
	 * @return
	 */
	public static Object[] cast(Object[] origArr, Class destArrCmpType) {
		// ȡ��ָ�����͵ĳ���Ϊ0������
		Object[] destArr = (Object[]) Array.newInstance(destArrCmpType, 0);
		destArr = cast(origArr, destArr);

		return destArr;
	}

	/**
	 * ת���� Class ����
	 * <p>
	 * JDK1.5֮ǰû�з��ͣ�ֱ��ǿת�ᱨ��
	 * 
	 * @param origArr
	 *            Դ����
	 * @return
	 */
	public static Class[] toClassArr(Object[] origArr) {
		Class[] destArr = EMPTY_CLASS_ARRAY;
		destArr = (Class[]) cast(origArr, destArr);

		return destArr;
	}

	/**
	 * ת���� String ����
	 * <p>
	 * JDK1.5֮ǰû�з��ͣ�ֱ��ǿת�ᱨ��
	 * 
	 * @param origArr
	 *            Դ����
	 * @return
	 */
	public static String[] toStringArr(Object[] origArr) {
		String[] destArr = EMPTY_STRING_ARRAY;
		destArr = (String[]) cast(origArr, destArr);

		return destArr;
	}

	/**
	 * ת���� Long ����
	 * <p>
	 * JDK1.5֮ǰû�з��ͣ�ֱ��ǿת�ᱨ��
	 * 
	 * @param origArr
	 *            Դ����
	 * @return
	 */
	public static Long[] toLongArr(Object[] origArr) {
		Long[] castArr = EMPTY_LONG_OBJECT_ARRAY;
		castArr = (Long[]) cast(origArr, castArr);

		return castArr;
	}

	/**
	 * ת���� Integer ����
	 * <p>
	 * JDK1.5֮ǰû�з��ͣ�ֱ��ǿת�ᱨ��
	 * 
	 * @param origArr
	 *            Դ����
	 * @return
	 */
	public static Integer[] toIntegerArr(Object[] origArr) {
		Integer[] castArr = EMPTY_INTEGER_OBJECT_ARRAY;
		castArr = (Integer[]) cast(origArr, castArr);

		return castArr;
	}

	/**
	 * ת���� Short ����
	 * <p>
	 * JDK1.5֮ǰû�з��ͣ�ֱ��ǿת�ᱨ��
	 * 
	 * @param origArr
	 *            Դ����
	 * @return
	 */
	public static Short[] toShortArr(Object[] origArr) {
		Short[] castArr = EMPTY_SHORT_OBJECT_ARRAY;
		castArr = (Short[]) cast(origArr, castArr);

		return castArr;
	}

	/**
	 * ת���� Byte ����
	 * <p>
	 * JDK1.5֮ǰû�з��ͣ�ֱ��ǿת�ᱨ��
	 * 
	 * @param origArr
	 *            Դ����
	 * @return
	 */
	public static Byte[] toByteArr(Object[] origArr) {
		Byte[] castArr = EMPTY_BYTE_OBJECT_ARRAY;
		castArr = (Byte[]) cast(origArr, castArr);

		return castArr;
	}

	/**
	 * ת���� Double ����
	 * <p>
	 * JDK1.5֮ǰû�з��ͣ�ֱ��ǿת�ᱨ��
	 * 
	 * @param origArr
	 *            Դ����
	 * @return
	 */
	public static Double[] toDoubleArr(Object[] origArr) {
		Double[] castArr = EMPTY_DOUBLE_OBJECT_ARRAY;
		castArr = (Double[]) cast(origArr, castArr);

		return castArr;
	}

	/**
	 * ת���� Float ����
	 * <p>
	 * JDK1.5֮ǰû�з��ͣ�ֱ��ǿת�ᱨ��
	 * 
	 * @param origArr
	 *            Դ����
	 * @return
	 */
	public static Float[] toFloatArr(Object[] origArr) {
		Float[] castArr = EMPTY_FLOAT_OBJECT_ARRAY;
		castArr = (Float[]) cast(origArr, castArr);

		return castArr;
	}

	/**
	 * ת���� Boolean ����
	 * <p>
	 * JDK1.5֮ǰû�з��ͣ�ֱ��ǿת�ᱨ��
	 * 
	 * @param origArr
	 *            Դ����
	 * @return
	 */
	public static Boolean[] toBooleanArr(Object[] origArr) {
		Boolean[] castArr = EMPTY_BOOLEAN_OBJECT_ARRAY;
		castArr = (Boolean[]) cast(origArr, castArr);

		return castArr;
	}

	/**
	 * ת���� Character ����
	 * <p>
	 * JDK1.5֮ǰû�з��ͣ�ֱ��ǿת�ᱨ��
	 * 
	 * @param origArr
	 *            Դ����
	 * @return
	 */
	public static Character[] toCharacterArr(Object[] origArr) {
		Character[] castArr = EMPTY_CHARACTER_OBJECT_ARRAY;
		castArr = (Character[]) cast(origArr, castArr);

		return castArr;
	}

	/**
	 * ����ָ���������ݵ��ַ�����ʾ��ʽ���ַ�����ʾ��ʽ�������Ԫ���б���ɣ����ڷ����ţ�"[]"���С�����Ԫ�����ַ� ", "�����żӿո񣩷ָ���<br>
	 * ��ЩԪ��ͨ�� String.valueOf(Object) ת��Ϊ�ַ�������� array Ϊ null���򷵻� "null"��
	 * <p>
	 * ������������ΪԪ�ص��������飬��ͨ���� Object �м̳е� Object.toString()
	 * ����������ת��Ϊ�ַ����������������ǵı�ʶ�����������ǵ����ݡ�
	 * <p>
	 * �˷������ص�ֵ���� Arrays.toString(array)��Arrays.asList(array).toString() ���ص�ֵ������
	 * array Ϊ null������������·��� "null"��
	 * 
	 * @param
	 * @param array
	 *            ����
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
			// �ö��żӿո�����
			String joinFrag = ", ";

			stringBuffer.append("[");
			Object object = null;
			for (int i = 0, len = array.length; i < len; i++) {
				object = array[i];
				stringBuffer.append(object).append(joinFrag);
			}
			// �Ƴ������Ķ��żӿո�
			stringBuffer.delete(stringBuffer.length() - 2, stringBuffer.length());
			stringBuffer.append("]");

			stringResonse = stringBuffer.toString();
		}

		return stringResonse;
	}

	/**
	 * ����ָ���������ݵ��ַ�����ʾ��ʽ���ַ�����ʾ��ʽ�������Ԫ���б���ɣ����ڷ����ţ�"[]"���С�����Ԫ����joinFrag �ָ���<br>
	 * ��ЩԪ��ͨ�� String.valueOf(Object) ת��Ϊ�ַ�������� array Ϊ null���򷵻� "null"��
	 * <p>
	 * ������������ΪԪ�ص��������飬��ͨ���� Object �м̳е� Object.toString()
	 * ����������ת��Ϊ�ַ����������������ǵı�ʶ�����������ǵ����ݡ�
	 * <p>
	 * �˷������ص�ֵ���� Arrays.toString(array)��Arrays.asList(array).toString() ���ص�ֵ������
	 * array Ϊ null������������·��� "null"��
	 * 
	 * @param
	 * @param array
	 *            ����
	 * @param leftFrag
	 *            ���Ƭ��
	 * @param rightFrag
	 *            �ұ�Ƭ��
	 * @param joinFrag
	 *            ����Ƭ��
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
			// �Ƴ�����������Ƭ��
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
	 * �Ƿ�Ϊ��
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
	 * �Ƿ����
	 * 
	 * @param array
	 *            ����
	 * @param stringToFind
	 *            �����ҵ�String
	 * @param isEqualsIgnoreCase
	 *            �Ƿ���Դ�Сд
	 * @return
	 */
	public static boolean contains(String[] array, String stringToFind, boolean isEqualsIgnoreCase) {
		return indexOf(array, stringToFind, isEqualsIgnoreCase) != -1;
	}

	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////

	/**
	 * ����������е��ظ�ֵ
	 * 
	 * @param
	 * @param array
	 * @return
	 */
	public static Object[] clearDuplicate(Object[] array) {
		if (isNotEmpty(array)) {
			// ����Set���˵��ظ�ֵ��ͬʱ����ԭ������˳��
			Set set = new LinkedHashSet(Arrays.asList(array));

			array = set.toArray(array);
		}

		return array;
	}

	/**
	 * ����������е�Nullֵ
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
			// ���˵�Nullֵ
			list.removeAll(nullList);

			array = list.toArray(array);
		}

		return array;
	}

	/**
	 * ����������е��ظ�ֵ��Nullֵ
	 * 
	 * @param
	 * @param array
	 * @return
	 */
	public static Object[] clearDuplicateAndNull(Object[] array) {
		if (isNotEmpty(array)) {
			// ����Set���˵��ظ�ֵ��ͬʱ����ԭ������˳��
			Set set = new LinkedHashSet(Arrays.asList(array));
			// �ٹ��˵�Nullֵ
			set.remove(null);

			array = set.toArray(array);
		}

		return array;
	}

	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////

}
