package com.kingdee.eas.fdc.basedata;

import java.math.BigDecimal;

import com.kingdee.eas.fdc.basedata.util.FdcArrayUtil;

public class FDCNumberHelper implements FDCNumberConstants{
	/**
	 * Object对象转换为BigDecimal对象,如果转化过程出现格式化异常,则返回0
	 */
	public static BigDecimal toBigDecimal(Object obj) {
		if (obj == null)
			return FDCHelper.ZERO;

		if (obj instanceof BigDecimal) {
			return (BigDecimal) obj;
		} else if (obj instanceof Integer) {
			return new BigDecimal(((Integer) obj).toString());
		} else if (obj instanceof Long) {
			return new BigDecimal(((Long) obj).toString());
		} else if (obj instanceof Double) {
			return new BigDecimal(((Double) obj).doubleValue());
		} else if(obj.toString()==null){
			return FDCNumberHelper.ZERO;
		}else{
			String str = obj.toString().trim();
			// 处理指数表示的数值如 1.00002213E15
			if (str.toLowerCase().indexOf("e") > -1) {
				try {
					return new BigDecimal(str);
				} catch (NumberFormatException e) {
					// @AbortException
					return FDCHelper.ZERO;
				}
			}
			if (str.matches("^[+-]?\\d+[\\.\\d]?\\d*+$")) {
				return new BigDecimal(str);
			}
		}

		return BigDecimal.ZERO;
	}

	public static BigDecimal toBigDecimal(Object obj,int scale) {
		return toBigDecimal(obj).setScale(scale,BigDecimal.ROUND_HALF_UP);
	}
	public static BigDecimal add(Object dec1,Object dec2){
		if(dec1==null&&dec2==null){
			return null;
		}else{
			return toBigDecimal(dec1).add(toBigDecimal(dec2));
		}
	}
	
	public static BigDecimal subtract(Object dec1,Object dec2){
		if(dec1==null&&dec2==null){
			return null;
		}else{
			return toBigDecimal(dec1).subtract(toBigDecimal(dec2));
		}
	}
	public static BigDecimal divide(Object dec1,Object dec2){
		return divide(dec1, dec2, 2, BigDecimal.ROUND_HALF_UP);
	}
	public static BigDecimal divide(Object dec1,Object dec2,int scale, int roundingMode){
		if(dec1==null&&dec2==null){
			return null;
		}
		if(toBigDecimal(dec2).signum()==0){
			return null;
		}
		return toBigDecimal(dec1).divide(toBigDecimal(dec2),scale,roundingMode);
	}

	public static BigDecimal divide(Object dec1, Object dec2, int scale) {
		return divide(dec1, dec2, scale, BigDecimal.ROUND_HALF_UP);
	}
	
	public static boolean isPositiveBigDecimal(Object obj){
		boolean isValid=false;
		if(toBigDecimal(obj).compareTo(FDCHelper.ZERO)>0){
			isValid=true;
		}
		return isValid;
	}

	/**
	 * 处理int类型数据
	 * @author hpw date:2009-10-24
	 * @param obj
	 * @return
	 */
	public static BigDecimal toBigDecimal(int obj){
		return toBigDecimal(new Integer(obj));
	}
	
	public static BigDecimal addInt(int obj1, int obj2){
		return toBigDecimal(obj1 + obj2);
	}
	
	/**
	 *  是否为零
	 * @param obj
	 * @return
	 */
	public static boolean isZero (Object obj){
		return toBigDecimal(obj).compareTo(FDCHelper.ZERO)==0;
	}
	
	public static int compareTo(Object obj1,Object obj2){
		return toBigDecimal(obj1).compareTo(toBigDecimal(obj2));
	}

	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////

	/**
	 * 是否为空或零
	 * 
	 * @param bd
	 * @return
	 */
	public static boolean isNullZero(BigDecimal bd) {
		return (bd == null) || (bd.compareTo(ZERO) == 0);
	}

	/**
	 * 比较两个金额字段的大小
	 * 
	 * @param obj1
	 * @param obj2
	 * @return 1 obj1 > obj2 0 obj1 = obj2 -1 obj1 < obj2
	 */
	public static int compareValue(Object obj1, Object obj2) {

		return toBigDecimal(obj1).compareTo(toBigDecimal(obj2));
	}

	/**
	 * 是否大于
	 * 
	 * @param obj1
	 * @param obj2
	 */
	public static boolean isGreaterThan(Object obj1, Object obj2) {
		return 1 == compareValue(obj1, obj2);
	}

	/**
	 * 是否小于
	 * 
	 * @param obj1
	 * @param obj2
	 */
	public static boolean isLessThan(Object obj1, Object obj2) {
		return -1 == compareValue(obj1, obj2);
	}

	/**
	 * 是否等于
	 * 
	 * @param obj1
	 * @param obj2
	 */
	public static boolean isEqual(Object obj1, Object obj2) {
		return 0 == compareValue(obj1, obj2);
	}

	/**
	 * 对象数组累加
	 * 
	 * @param objArr
	 * @return
	 */
	public static BigDecimal add(Object[] objArr) {
		BigDecimal rs = null;

		// 部分空值
		if (!FdcArrayUtil.isAllNull(objArr)) {
			rs = BigDecimal.ZERO;

			int len = objArr.length;
			for (int i = 0; i < len; i++) {
				rs = rs.add(toBigDecimal(objArr[i]));
			}
		}

		return rs;
	}

	/**
	 * 对象数组累减
	 * 
	 * @param objArr
	 * @return
	 */
	public static BigDecimal subtract(Object[] objArr) {
		BigDecimal rs = null;

		// 部分空值
		if (!FdcArrayUtil.isAllNull(objArr)) {
			rs = toBigDecimal(objArr[0]);

			int len = objArr.length;
			for (int i = 1; i < len; i++) {
				rs = rs.subtract(toBigDecimal(objArr[i]));
			}
		}

		return rs;
	}

	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////

	/**
	 * @deprecated 推荐使用divide(Object dec1,Object dec2,int scale)， 需要传入精度
	 * @param dec1
	 * @param dec2
	 * @return
	 */
	public static BigDecimal multiply(Object dec1, Object dec2) {
		if (dec1 == null && dec2 == null) {
			return null;
		} else {
			return toBigDecimal(dec1).multiply(toBigDecimal(dec2));
		}
	}

	public static BigDecimal multiply(Object dec1, Object dec2, int scale) {
		if (dec1 == null || dec2 == null) {
			return null;
		} else {
			BigDecimal obj = toBigDecimal(dec1).multiply(toBigDecimal(dec2));
			return obj.setScale(scale, BigDecimal.ROUND_HALF_UP);
		}
	}

	/**
	 * 对象数组累乘
	 * 
	 * @param objArr
	 * @return
	 */
	public static BigDecimal multiply(Object[] objArr) {
		BigDecimal rs = null;

		// 全部非空值
		if (FdcArrayUtil.isNotEmpty(objArr) && !FdcArrayUtil.isSomeNull(objArr)) {
			rs = toBigDecimal(objArr[0]);

			int len = objArr.length;
			for (int i = 1; i < len; i++) {
				rs = rs.multiply(toBigDecimal(objArr[i]));
			}
		}

		return rs;
	}

	/**
	 * 获取百分数：dividend/divisor
	 * @param dividend	被除数
	 * @param divisor	除数
	 * @param scale	保留位数
	 * @param roundingMode	四舍五入模式
	 * @return
	 */
	public static BigDecimal getPercenValue(Object dividend, Object divisor, int scale, int roundingMode ) {
		BigDecimal result = FDCNumberHelper.divide(dividend, divisor, scale+2, roundingMode);
		result = FDCNumberHelper.multiply(result, new BigDecimal("100"));
		
		return result;
	}
	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////

}
