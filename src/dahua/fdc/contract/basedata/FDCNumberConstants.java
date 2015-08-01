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

	/** BigDecimal Value : 100 �� */
	public static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

	/** BigDecimal Value : -100 �� */
	public static final BigDecimal _ONE_HUNDRED = new BigDecimal("-100");

	/** BigDecimal Value : 1000 ǧ */
	public static final BigDecimal ONE_THOUSAND = new BigDecimal("1000");
	
	/** BigDecimal Value : 1,000,000 ���� */
	public static final BigDecimal ONE_MILLION = new BigDecimal("1000000");

	/** BigDecimal Value : -1000 ǧ */
	public static final BigDecimal _ONE_THOUSAND = new BigDecimal("-1000");

	/** BigDecimal Value : 10000 �� */
	public static final BigDecimal TEN_THOUSAND = new BigDecimal("10000");

	/** BigDecimal Value : -10000 �� */
	public static final BigDecimal _TEN_THOUSAND = new BigDecimal("-10000");
	
	/** BigDecimal Value : -1,000,000 ���� */
	public static final BigDecimal _ONE_MILLION = new BigDecimal("-1000000");

	/** BigDecimal Value : 100000000 �� */
	public static final BigDecimal ONE_HUNDRED_MILLION = new BigDecimal(
			"100000000");

	/** BigDecimal Value : -100000000 �� */
	public static final BigDecimal _ONE_HUNDRED_MILLION = new BigDecimal(
			"-100000000");

	// public static final BigDecimal MAX_VALUE = new
	// BigDecimal("999999999999999999.9999999999");
	// public static final BigDecimal MIN_VALUE = new
	// BigDecimal("-999999999999999999.9999999999");

	// �����ϸ����1�ף�����С��ϸ������1�ף���������һ�£�����Ҫ����ƾ֤���ֶα��벻�ܴ��ڴ�������13λ��
	public static final BigDecimal MAX_VALUE = GlUtils.maxBigDecimal.divide(
			ONE_HUNDRED, BigDecimal.ROUND_HALF_UP); // 1000000000000

	public static final BigDecimal MIN_VALUE = GlUtils.minBigDecimal.divide(
			ONE_HUNDRED, BigDecimal.ROUND_HALF_UP); // -1000000000000

	// ���ϼ�����100�ף�����С�ϼ�������100�ף�MAX_VALUE/MIN_VALUE * 100 ��15λ��
	public final static BigDecimal MAX_TOTAL_VALUE = MAX_VALUE
			.multiply(ONE_HUNDRED);

	public final static BigDecimal MIN_TOTAL_VALUE = MIN_VALUE
			.multiply(ONE_HUNDRED);

//	 ���ϼ�����1000�ף�����С�ϼ�������1000�ף�MAX_VALUE/MIN_VALUE * 1000 ��16λ��
	public final static BigDecimal MAX_TOTAL_VALUE2 = MAX_VALUE
			.multiply(ONE_THOUSAND);

	public final static BigDecimal MIN_TOTAL_VALUE2 = MIN_VALUE
			.multiply(ONE_THOUSAND);
	
	/** BigDecimal Value : 99 */
	public static final BigDecimal NINETY_NINE = new BigDecimal("99");
	
	/**
	 * KDFormattedTextFieldʹ�õ������Сֵ��Ŀǰ���ݿⶨ��Ľ���ֶ�Ϊ18λ���ؼ�����16λ��ʾ
	 * �������ݾ��ȵ���ƹ淶����ֶ�ͳһʹ�� (19,4)
	 */
	public static final BigDecimal MAX_DECIMAL = new BigDecimal(
			"999999999999999.9999");

	public static final BigDecimal MIN_DECIMAL = new BigDecimal(
			"-999999999999999.9999");
}
