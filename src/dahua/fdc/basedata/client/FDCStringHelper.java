package com.kingdee.eas.fdc.basedata.client;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;

public final class FDCStringHelper {

	/**
	 * 描述：是否为空
	 * 
	 * @param obj
	 *            对象
	 * @param isAllowZero
	 *            是否允许为0
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-28
	 */
	public static boolean isEmpty(Object obj, boolean isAllowZero) {
		if (obj instanceof String) {
			return isEmpty((String) obj);
		} else if (obj instanceof Number) {
			if (isAllowZero)
				return obj == null;
			Number num = (Number) obj;
			BigDecimal tempNum = new BigDecimal(num.toString());

			// 数字0值作empty处理
			return (0 == FDCConstants.ZERO.compareTo(tempNum));
		} else if (obj instanceof Map) {
			Map map = (Map) obj;
			return map.size() <= 0;
		} else if (obj instanceof Collection) {
			Collection c = (Collection) obj;
			return c.size() <= 0;
		} else {
			return obj == null ? true : isEmpty(obj.toString());
		}
	}

	/**
	 * 判断空字符串，所有非String类型判断为空
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		return !(obj instanceof String && ((String) obj).trim().length() > 0);
	}

	/**
	 * 默认返回零
	 * 
	 * @param obj
	 * @return
	 */
	public static BigDecimal toBigDecimal(Object obj) {
		return FDCNumberHelper.toBigDecimal(obj);
	}

	/**
	 * 比较两个对象是否相等
	 * 
	 * @param obj
	 * @param other
	 * @return
	 */
	public static boolean isEquals(Object obj, Object other) {
		return obj == null ? other == null : obj.equals(other);
	}
	
	/**
	 * 是否包含汉字
	 * 
	 * @return
	 */
	public static boolean isIncludeChinese(String str) {
		String regEx = "[\\u4e00-\\u9fa5]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find();
	}

}
