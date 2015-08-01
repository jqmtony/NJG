package com.kingdee.eas.fdc.basedata;

import java.math.BigDecimal;

public class FDCNumberValueVerifyRule {
	private BigDecimal maxValue;
	
	private BigDecimal minValue;
	
	public BigDecimal getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(BigDecimal maxValue) {
		this.maxValue = maxValue;
	}
	public BigDecimal getMinValue() {
		return minValue;
	}
	public void setMinValue(BigDecimal minValue) {
		this.minValue = minValue;
	}
}
