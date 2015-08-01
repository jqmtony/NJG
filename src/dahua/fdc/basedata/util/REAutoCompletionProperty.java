package com.kingdee.eas.fdc.basedata.util;

public class REAutoCompletionProperty {
	/**
	 * 选择下拉框后，调用的setter
	 */
	private REAutoCompletionDataSetter setter;
	/**
	 * 下拉框中显示的数据属性
	 */
	private String rendererFieldName;
	/**
	 * 下拉框最多显示多少
	 */
	private int maxShowLine = 10;
	/**
	 * 获取下拉数据时是否启用线程（当filter可能比较耗时时必须此属性必须设置为true）。注意到此属性为true时，
	 * filter中不可对UI对象做任何操作
	 */
	private boolean isUseThread4Filter = true;
	public boolean isUseThread4Filter() {
		return isUseThread4Filter;
	}

	public void setUseThread4Filter(boolean isUseThread4Filter) {
		this.isUseThread4Filter = isUseThread4Filter;
	}

	public REAutoCompletionDataSetter getSetter() {
		return setter;
	}

	public void setSetter(REAutoCompletionDataSetter setter) {
		this.setter = setter;
	}

	public String getRendererFieldName() {
		return rendererFieldName;
	}

	public void setRendererFieldName(String rendererFieldName) {
		this.rendererFieldName = rendererFieldName;
	}

	public int getMaxShowLine() {
		return maxShowLine;
	}

	public void setMaxShowLine(int maxShowLine) {
		this.maxShowLine = maxShowLine;
	}
	
	
	
}
