package com.kingdee.eas.fdc.basedata;

import java.math.BigDecimal;

import com.kingdee.eas.fi.gl.GlUtils;

public interface FDCNumberConstants {
	public static final int MAX_LENGTH_TXT = 80;

	public static final BigDecimal ZERO = new BigDecimal("0");

	/** BigDecimal Value : 1 */
	public static final BigDecimal ONE = new BigDecimal("1");

	/** BigDecimal Value : -1 */
	public static final BigDecimal _ONE = new BigDecimal("-1");

	/** BigDecimal Value : 10 */
	public static final BigDecimal TEN = new BigDecimal("10");

	/** BigDecimal Value : -10 */
	public static final BigDecimal _TEN = new BigDecimal("-10");

	/** BigDecimal Value : 100 百 */
	public static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

	/** BigDecimal Value : -100 百 */
	public static final BigDecimal _ONE_HUNDRED = new BigDecimal("-100");

	/** BigDecimal Value : 1000 千 */
	public static final BigDecimal ONE_THOUSAND = new BigDecimal("1000");
	
	/** BigDecimal Value : 1,000,000 百万 */
	public static final BigDecimal ONE_MILLION = new BigDecimal("1000000");

	/** BigDecimal Value : -1000 千 */
	public static final BigDecimal _ONE_THOUSAND = new BigDecimal("-1000");

	/** BigDecimal Value : 10000 万 */
	public static final BigDecimal TEN_THOUSAND = new BigDecimal("10000");

	/** BigDecimal Value : -10000 万 */
	public static final BigDecimal _TEN_THOUSAND = new BigDecimal("-10000");
	
	/** BigDecimal Value : -1,000,000 百万 */
	public static final BigDecimal _ONE_MILLION = new BigDecimal("-1000000");

	/** BigDecimal Value : 100000000 亿 */
	public static final BigDecimal ONE_HUNDRED_MILLION = new BigDecimal(
			"100000000");

	/** BigDecimal Value : -100000000 亿 */
	public static final BigDecimal _ONE_HUNDRED_MILLION = new BigDecimal(
			"-100000000");

	// public static final BigDecimal MAX_VALUE = new
	// BigDecimal("999999999999999999.9999999999");
	// public static final BigDecimal MIN_VALUE = new
	// BigDecimal("-999999999999999999.9999999999");

	// 最大明细数（1兆），最小明细数（负1兆），和总帐一致，所以要生成凭证的字段必须不能大于此数，（13位）
	public static final BigDecimal MAX_VALUE = GlUtils.maxBigDecimal.divide(
			ONE_HUNDRED, BigDecimal.ROUND_HALF_UP); // 1000000000000

	public static final BigDecimal MIN_VALUE = GlUtils.minBigDecimal.divide(
			ONE_HUNDRED, BigDecimal.ROUND_HALF_UP); // -1000000000000

	// 最大合计数（100兆），最小合计数（负100兆）MAX_VALUE/MIN_VALUE * 100 （15位）
	public final static BigDecimal MAX_TOTAL_VALUE = MAX_VALUE
			.multiply(ONE_HUNDRED);

	public final static BigDecimal MIN_TOTAL_VALUE = MIN_VALUE
			.multiply(ONE_HUNDRED);

//	 最大合计数（1000兆），最小合计数（负1000兆）MAX_VALUE/MIN_VALUE * 1000 （16位）
	public final static BigDecimal MAX_TOTAL_VALUE2 = MAX_VALUE
			.multiply(ONE_THOUSAND);

	public final static BigDecimal MIN_TOTAL_VALUE2 = MIN_VALUE
			.multiply(ONE_THOUSAND);
	
	/** BigDecimal Value : 99 */
	public static final BigDecimal NINETY_NINE = new BigDecimal("99");
	
	/**
	 * KDFormattedTextField使用的最大最小值。目前数据库定义的金额字段为18位，控件采用16位显示
	 * 关于数据精度的设计规范金额字段统一使用 (19,4)
	 */
	public static final BigDecimal MAX_DECIMAL = new BigDecimal(
			"999999999999999.9999");

	public static final BigDecimal MIN_DECIMAL = new BigDecimal(
			"-999999999999999.9999");
}
