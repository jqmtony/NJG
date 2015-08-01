/**
 * @(#)FdcObjectUtil.java 1.0 2013-9-27
 * Copyright 2013 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Map;

import com.kingdee.util.DateTimeUtils;

/**
 * 房地产 对象工具类
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @date 2013-9-27
 * @see
 * @since JDK1.4
 */
public class FdcObjectUtil {

	/**
	 * 是否相等
	 * 
	 * @param object1
	 * @param object2
	 * @return
	 */
	public static boolean isEquals(Object object1, Object object2) {
		return (object1 == object2 || (null != object1 && object1.equals(object2)));
	}

	/**
	 * 返回该对象的字符串表示（空值返回字符串"null"）
	 * 
	 * <p>
	 * 如果该对象没有重新Object.toString方法，则默认会返回如下字符串：<br>
	 * getClass().getName() + '@' + Integer.toHexString(hashCode())
	 * <p>
	 * 
	 * @param object
	 * @return
	 */
	public static String toString(Object object) {
		return (null == object) ? null + "" : object.toString();
	}

	/**
	 * 返回该对象的字符串表示（空值返回null）
	 * 
	 * <p>
	 * 如果该对象没有重新Object.toString方法，则默认会返回如下字符串：<br>
	 * getClass().getName() + '@' + Integer.toHexString(hashCode())
	 * <p>
	 * 
	 * @param object
	 * @return
	 */
	public static String toString2(Object object) {
		return (null == object) ? null : object.toString();
	}

	/**
	 * 返回该对象的哈希码值
	 * 
	 * @param object
	 * @return
	 */
	public static int hashCode(Object object) {
		return (null == object) ? 0 : object.hashCode();
	}

	/**
	 * 如果为空值，则设置一个默认值
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static Object defaultIfNull(Object value, Object defaultValue) {
		return (null == value) ? defaultValue : value;
	}

	/**
	 * 如果为空值，则设置一个默认值
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static Number defaultIfNull(Number value, Number defaultValue) {
		return (null == value) ? defaultValue : value;
	}

	/**
	 * 如果为空值，则设置一个默认值
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static Byte defaultIfNull(Byte value, Byte defaultValue) {
		return (null == value) ? defaultValue : value;
	}

	/**
	 * 如果为空值，则设置一个默认值
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static Short defaultIfNull(Short value, Short defaultValue) {
		return (null == value) ? defaultValue : value;
	}

	/**
	 * 如果为空值，则设置一个默认值
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static Integer defaultIfNull(Integer value, Integer defaultValue) {
		return (null == value) ? defaultValue : value;
	}

	/**
	 * 如果为空值，则设置一个默认值
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static Long defaultIfNull(Long value, Long defaultValue) {
		return (null == value) ? defaultValue : value;
	}

	/**
	 * 如果为空值，则设置一个默认值
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static Float defaultIfNull(Float value, Float defaultValue) {
		return (null == value) ? defaultValue : value;
	}

	/**
	 * 如果为空值，则设置一个默认值
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static Double defaultIfNull(Double value, Double defaultValue) {
		return (null == value) ? defaultValue : value;
	}

	/**
	 * 如果为空值，则设置一个默认值
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static BigDecimal defaultIfNull(BigDecimal value, BigDecimal defaultValue) {
		return (null == value) ? defaultValue : value;
	}

	/**
	 * 如果为空值，则设置一个默认值
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static BigInteger defaultIfNull(BigInteger value, BigInteger defaultValue) {
		return (null == value) ? defaultValue : value;
	}

	/**
	 * 如果为空值，则设置一个默认值
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static CharSequence defaultIfNull(CharSequence value, CharSequence defaultValue) {
		return (null == value) ? defaultValue : value;
	}

	/**
	 * 如果为空值，则设置一个默认值
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static String defaultIfNull(String value, String defaultValue) {
		return (null == value) ? defaultValue : value;
	}

	/**
	 * 如果为空值，则设置一个默认值
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static StringBuffer defaultIfNull(StringBuffer value, StringBuffer defaultValue) {
		return (null == value) ? defaultValue : value;
	}

	/**
	 * 如果为空值，则设置一个默认值
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static java.util.Date defaultIfNull(java.util.Date value, java.util.Date defaultValue) {
		return (null == value) ? defaultValue : value;
	}

	/**
	 * 如果为空值，则设置一个默认值
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static java.sql.Date defaultIfNull(java.sql.Date value, java.sql.Date defaultValue) {
		return (null == value) ? defaultValue : value;
	}

	/**
	 * 如果为空值，则设置一个默认值
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static java.sql.Timestamp defaultIfNull(java.sql.Timestamp value, java.sql.Timestamp defaultValue) {
		return (null == value) ? defaultValue : value;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	public static Boolean toBoolean(Object value) {
		if (value instanceof Boolean) {
			return (Boolean) value;

		} else if (value instanceof String) {
			return new Boolean((String) value);

		} else if (value instanceof Number) {
			Number n = (Number) value;
			return (n.intValue() != 0) ? Boolean.TRUE : Boolean.FALSE;
		}

		return null;
	}

	public static Character toCharacter(Object value) {
		if (value instanceof Character) {
			return (Character) value;

		} else if (value instanceof CharSequence) {
			CharSequence n = (CharSequence) value;
			if (n.length() > 0) {
				char d = n.charAt(0);
				return Character.valueOf(d);
			}
		}

		return null;
	}

	public static Number toNumber(Object value) {
		if (value instanceof Number) {
			return (Number) value;
		} else if (value instanceof String) {
			try {
				String text = (String) value;
				return NumberFormat.getInstance().parse(text);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public static Byte toByte(Object value) {
		Number tempValue = toNumber(value);
		if (tempValue == null) {
			return null;
		} else if (tempValue instanceof Byte) {
			return (Byte) tempValue;
		}

		return new Byte(tempValue.byteValue());
	}

	public static Short toShort(Object value) {
		Number tempValue = toNumber(value);
		if (tempValue == null) {
			return null;
		} else if (tempValue instanceof Short) {
			return (Short) tempValue;
		}

		return new Short(tempValue.shortValue());
	}

	public static Integer toInteger(Object value) {
		Number tempValue = toNumber(value);
		if (tempValue == null) {
			return null;
		} else if (tempValue instanceof Integer) {
			return (Integer) tempValue;
		}

		return new Integer(tempValue.intValue());
	}

	public static Long toLong(Object value) {
		Number tempValue = toNumber(value);
		if (tempValue == null) {
			return null;
		} else if (tempValue instanceof Long) {
			return (Long) tempValue;
		}

		return new Long(tempValue.longValue());
	}

	public static Float toFloat(Object value) {
		Number tempValue = toNumber(value);
		if (tempValue == null) {
			return null;
		} else if (tempValue instanceof Float) {
			return (Float) tempValue;
		}

		return new Float(tempValue.floatValue());
	}

	public static Double toDouble(Object value) {
		Number tempValue = toNumber(value);
		if (tempValue == null) {
			return null;
		} else if (tempValue instanceof Double) {
			return (Double) tempValue;
		}

		return new Double(tempValue.doubleValue());
	}

	public static BigInteger toBigInteger(Object value) {
		Number tempValue = toNumber(value);
		if (tempValue == null) {
			return null;
		} else if (tempValue instanceof BigInteger) {
			return (BigInteger) tempValue;
		}

		return new BigInteger(tempValue.toString());
	}

	public static BigDecimal toBigDecimal(Object value) {
		Number tempValue = toNumber(value);
		if (tempValue == null) {
			return null;
		} else if (tempValue instanceof BigDecimal) {
			return (BigDecimal) tempValue;
		}

		return new BigDecimal(tempValue.toString());
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	public static char toCharacterValue(Object value) {
		Character characterObject = toCharacter(value);
		if (characterObject == null) {
			return '\0';
		}

		return characterObject.charValue();
	}

	public static boolean toBooleanValue(Object value) {
		Boolean booleanObject = toBoolean(value);
		if (booleanObject == null) {
			return false;
		}
		return booleanObject.booleanValue();
	}

	public static byte toByteValue(Object value) {
		Byte byteObject = toByte(value);
		if (byteObject == null) {
			return 0;
		}
		return byteObject.byteValue();
	}

	public static short toShortValue(Object value) {
		Short shortObject = toShort(value);
		if (shortObject == null) {
			return 0;
		}
		return shortObject.shortValue();
	}

	public static int toIntValue(Object value) {
		Integer integerObject = toInteger(value);
		if (integerObject == null) {
			return 0;
		}
		return integerObject.intValue();
	}

	public static long toLongValue(Object value) {
		Long longObject = toLong(value);
		if (longObject == null) {
			return 0L;
		}
		return longObject.longValue();
	}

	public static float toFloatValue(Object value) {
		Float floatObject = toFloat(value);
		if (floatObject == null) {
			return 0f;
		}
		return floatObject.floatValue();
	}

	public static double toDoubleValue(Object value) {
		Double doubleObject = toDouble(value);
		if (doubleObject == null) {
			return 0d;
		}
		return doubleObject.doubleValue();
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	public static Map toMap(Object value) {
		if (value != null && value instanceof Map) {
			return (Map) value;
		}

		return null;
	}

	public static java.util.Date toDateUtil(Object value) {
		if (value != null && value instanceof java.util.Date) {
			return (java.util.Date) value;
		} else if (value instanceof String) {
			try {
				return (java.util.Date) DateTimeUtils.parseDate(value.toString());
			} catch (ParseException e) {
				throw new RuntimeException(e.getCause());
			}
		}

		return null;
	}

	public static java.sql.Date toDateSql(Object value) {
		if (value != null && value instanceof java.sql.Date) {
			return (java.sql.Date) value;
		} else if (value instanceof String) {
			try {
				java.util.Date date = DateTimeUtils.parseDate(value.toString(), "yyyy-MM-dd");
				if (null != date) {
					return new java.sql.Date(date.getTime());
				}
			} catch (ParseException e) {
				throw new RuntimeException(e.getCause());
			}
		}

		return null;
	}

	public static java.sql.Time toDateTime(Object value) {
		if (value != null && value instanceof java.sql.Time) {
			return (java.sql.Time) value;
		} else if (value instanceof String) {
			try {
				java.util.Date date = DateTimeUtils.parseDate(value.toString(), "HH:mm:ss");
				if (null != date) {
					return new java.sql.Time(date.getTime());
				}
			} catch (ParseException e) {
				throw new RuntimeException(e.getCause());
			}
		}

		return null;
	}

	public static java.sql.Timestamp toDateTimestamp(Object value) {
		if (value != null && value instanceof java.sql.Timestamp) {
			return (java.sql.Timestamp) value;
		} else if (value instanceof String) {
			try {
				java.util.Date date = DateTimeUtils.parseDate(value.toString());
				if (null != date) {
					return new java.sql.Timestamp(date.getTime());
				}
			} catch (ParseException e) {
				throw new RuntimeException(e.getCause());
			}
		}

		return null;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

}
