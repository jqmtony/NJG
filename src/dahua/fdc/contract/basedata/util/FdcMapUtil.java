/*
 * @(#)FdcMapUtil.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.basedata.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 房地产 映射工具类
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @date 2013-9-10
 * @see
 * @since 1.4
 */
public class FdcMapUtil {

	// 空HashMap
	public static final Map EMPTY_MAP = new HashMap();
	// 空TreeMap
	public static final SortedMap EMPTY_SORTED_MAP = new TreeMap(); 

	public FdcMapUtil() {
	}

	/**
	 * 是否为空
	 * 
	 * @param map
	 * @return
	 */
	public static boolean isEmpty(Map map) {
		return (null == map) || map.isEmpty();
	}

	/**
	 * 是否不为空
	 * 
	 * @param map
	 * @return
	 */
	public static boolean isNotEmpty(Map map) {
		return !isEmpty(map);
	}

	// /////////////////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////////////////

	public static Object getObject(Map map, final Object key) {
		if (map != null) {
			return map.get(key);
		}
		return null;
	}

	public static String getString(Map map, final Object key) {
		if (map != null) {
			Object answer = map.get(key);
			if (answer != null) {
				return answer.toString();
			}
		}
		return null;
	}

	public static BigInteger getBigInteger(Map map, final Object key) {
		if (map != null) {
			Object answer = map.get(key);
			if (answer != null && answer instanceof BigInteger) {
				return (BigInteger) answer;
			}
		}
		return null;
	}

	public static BigDecimal getBigDecimal(Map map, final Object key) {
		if (map != null) {
			Object answer = map.get(key);
			if (answer != null && answer instanceof BigDecimal) {
				return (BigDecimal) answer;
			}
		}
		return null;
	}

	public static List getList(Map map, final Object key) {
		if (map != null) {
			Object answer = map.get(key);
			if (answer != null && answer instanceof List) {
				return (List) answer;
			}
		}
		return null;
	}

	public static Set getSet(Map map, final Object key) {
		if (map != null) {
			Object answer = map.get(key);
			if (answer != null && answer instanceof Set) {
				return (Set) answer;
			}
		}
		return null;
	}

	public static Map getMap(Map map, final Object key) {
		if (map != null) {
			Object answer = map.get(key);
			if (answer != null && answer instanceof Map) {
				return (Map) answer;
			}
		}
		return null;
	}

	// /////////////////////////////////////////////////////////////////////////

	public static Boolean getBoolean(Map map, final Object key) {
		if (map != null) {
			Object answer = map.get(key);
			if (answer != null) {
				if (answer instanceof Boolean) {
					return (Boolean) answer;

				} else if (answer instanceof String) {
					return new Boolean((String) answer);

				} else if (answer instanceof Number) {
					Number n = (Number) answer;
					return (n.intValue() != 0) ? Boolean.TRUE : Boolean.FALSE;
				}
			}
		}
		return null;
	}

	public static Number getNumber(Map map, final Object key) {
		if (map != null) {
			Object answer = map.get(key);
			if (answer != null) {
				if (answer instanceof Number) {
					return (Number) answer;

				} else if (answer instanceof String) {
					try {
						String text = (String) answer;
						return NumberFormat.getInstance().parse(text);

					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	public static Byte getByte(Map map, final Object key) {
		Number answer = getNumber(map, key);
		if (answer == null) {
			return null;
		} else if (answer instanceof Byte) {
			return (Byte) answer;
		}
		return new Byte(answer.byteValue());
	}

	public static Short getShort(Map map, final Object key) {
		Number answer = getNumber(map, key);
		if (answer == null) {
			return null;
		} else if (answer instanceof Short) {
			return (Short) answer;
		}
		return new Short(answer.shortValue());
	}

	public static Integer getInteger(Map map, final Object key) {
		Number answer = getNumber(map, key);
		if (answer == null) {
			return null;
		} else if (answer instanceof Integer) {
			return (Integer) answer;
		}
		return new Integer(answer.intValue());
	}

	public static Long getLong(Map map, final Object key) {
		Number answer = getNumber(map, key);
		if (answer == null) {
			return null;
		} else if (answer instanceof Long) {
			return (Long) answer;
		}
		return new Long(answer.longValue());
	}

	public static Float getFloat(Map map, final Object key) {
		Number answer = getNumber(map, key);
		if (answer == null) {
			return null;
		} else if (answer instanceof Float) {
			return (Float) answer;
		}
		return new Float(answer.floatValue());
	}

	public static Double getDouble(Map map, final Object key) {
		Number answer = getNumber(map, key);
		if (answer == null) {
			return null;
		} else if (answer instanceof Double) {
			return (Double) answer;
		}
		return new Double(answer.doubleValue());
	}

	// Type safe getters with default values
	// /////////////////////////////////////////////////////////////////////////

	public static Object getObject(Map map, Object key, Object defaultValue) {
		if (map != null) {
			Object answer = map.get(key);
			if (answer != null) {
				return answer;
			}
		}
		return defaultValue;
	}

	public static String getString(Map map, Object key, String defaultValue) {
		String answer = getString(map, key);
		if (answer == null) {
			answer = defaultValue;
		}
		return answer;
	}

	public static BigInteger getBigInteger(Map map, Object key, BigInteger defaultValue) {
		BigInteger answer = getBigInteger(map, key);
		if (answer == null) {
			answer = defaultValue;
		}
		return answer;
	}

	public static BigDecimal getBigDecimal(Map map, Object key, BigDecimal defaultValue) {
		BigDecimal answer = getBigDecimal(map, key);
		if (answer == null) {
			answer = defaultValue;
		}
		return answer;
	}

	public static List getList(Map map, Object key, List defaultValue) {
		List answer = getList(map, key);
		if (answer == null) {
			answer = defaultValue;
		}
		return answer;
	}

	public static Set getSet(Map map, Object key, Set defaultValue) {
		Set answer = getSet(map, key);
		if (answer == null) {
			answer = defaultValue;
		}
		return answer;
	}

	public static Map getMap(Map map, Object key, Map defaultValue) {
		Map answer = getMap(map, key);
		if (answer == null) {
			answer = defaultValue;
		}
		return answer;
	}

	// /////////////////////////////////////////////////////////////////////////

	public static Boolean getBoolean(Map map, Object key, Boolean defaultValue) {
		Boolean answer = getBoolean(map, key);
		if (answer == null) {
			answer = defaultValue;
		}
		return answer;
	}

	public static Number getNumber(Map map, Object key, Number defaultValue) {
		Number answer = getNumber(map, key);
		if (answer == null) {
			answer = defaultValue;
		}
		return answer;
	}

	public static Byte getByte(Map map, Object key, Byte defaultValue) {
		Byte answer = getByte(map, key);
		if (answer == null) {
			answer = defaultValue;
		}
		return answer;
	}

	public static Short getShort(Map map, Object key, Short defaultValue) {
		Short answer = getShort(map, key);
		if (answer == null) {
			answer = defaultValue;
		}
		return answer;
	}

	public static Integer getInteger(Map map, Object key, Integer defaultValue) {
		Integer answer = getInteger(map, key);
		if (answer == null) {
			answer = defaultValue;
		}
		return answer;
	}

	public static Long getLong(Map map, Object key, Long defaultValue) {
		Long answer = getLong(map, key);
		if (answer == null) {
			answer = defaultValue;
		}
		return answer;
	}

	public static Float getFloat(Map map, Object key, Float defaultValue) {
		Float answer = getFloat(map, key);
		if (answer == null) {
			answer = defaultValue;
		}
		return answer;
	}

	public static Double getDouble(Map map, Object key, Double defaultValue) {
		Double answer = getDouble(map, key);
		if (answer == null) {
			answer = defaultValue;
		}
		return answer;
	}

	// /////////////////////////////////////////////////////////////////////////

	public static boolean getBooleanValue(Map map, final Object key) {
		Boolean booleanObject = getBoolean(map, key);
		if (booleanObject == null) {
			return false;
		}
		return booleanObject.booleanValue();
	}

	public static byte getByteValue(Map map, final Object key) {
		Byte byteObject = getByte(map, key);
		if (byteObject == null) {
			return 0;
		}
		return byteObject.byteValue();
	}

	public static short getShortValue(Map map, final Object key) {
		Short shortObject = getShort(map, key);
		if (shortObject == null) {
			return 0;
		}
		return shortObject.shortValue();
	}

	public static int getIntValue(Map map, final Object key) {
		Integer integerObject = getInteger(map, key);
		if (integerObject == null) {
			return 0;
		}
		return integerObject.intValue();
	}

	public static long getLongValue(Map map, final Object key) {
		Long longObject = getLong(map, key);
		if (longObject == null) {
			return 0L;
		}
		return longObject.longValue();
	}

	public static float getFloatValue(Map map, final Object key) {
		Float floatObject = getFloat(map, key);
		if (floatObject == null) {
			return 0f;
		}
		return floatObject.floatValue();
	}

	public static double getDoubleValue(Map map, final Object key) {
		Double doubleObject = getDouble(map, key);
		if (doubleObject == null) {
			return 0d;
		}
		return doubleObject.doubleValue();
	}

	// /////////////////////////////////////////////////////////////////////////

	public static boolean getBooleanValue(Map map, final Object key, boolean defaultValue) {
		Boolean booleanObject = getBoolean(map, key);
		if (booleanObject == null) {
			return defaultValue;
		}
		return booleanObject.booleanValue();
	}

	public static byte getByteValue(Map map, final Object key, byte defaultValue) {
		Byte byteObject = getByte(map, key);
		if (byteObject == null) {
			return defaultValue;
		}
		return byteObject.byteValue();
	}

	public static short getShortValue(Map map, final Object key, short defaultValue) {
		Short shortObject = getShort(map, key);
		if (shortObject == null) {
			return defaultValue;
		}
		return shortObject.shortValue();
	}

	public static int getIntValue(Map map, final Object key, int defaultValue) {
		Integer integerObject = getInteger(map, key);
		if (integerObject == null) {
			return defaultValue;
		}
		return integerObject.intValue();
	}

	public static long getLongValue(Map map, final Object key, long defaultValue) {
		Long longObject = getLong(map, key);
		if (longObject == null) {
			return defaultValue;
		}
		return longObject.longValue();
	}

	public static float getFloatValue(Map map, final Object key, float defaultValue) {
		Float floatObject = getFloat(map, key);
		if (floatObject == null) {
			return defaultValue;
		}
		return floatObject.floatValue();
	}

	public static double getDoubleValue(Map map, final Object key, double defaultValue) {
		Double doubleObject = getDouble(map, key);
		if (doubleObject == null) {
			return defaultValue;
		}
		return doubleObject.doubleValue();
	}

	// /////////////////////////////////////////////////////////////////////////
}
