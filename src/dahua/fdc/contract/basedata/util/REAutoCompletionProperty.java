package com.kingdee.eas.fdc.basedata.util;

public class REAutoCompletionProperty {
	/**
	 * ѡ��������󣬵��õ�setter
	 */
	private REAutoCompletionDataSetter setter;
	/**
	 * ����������ʾ����������
	 */
	private String rendererFieldName;
	/**
	 * �����������ʾ����
	 */
	private int maxShowLine = 10;
	/**
	 * ��ȡ��������ʱ�Ƿ������̣߳���filter���ܱȽϺ�ʱʱ��������Ա�������Ϊtrue����ע�⵽������Ϊtrueʱ��
	 * filter�в��ɶ�UI�������κβ���
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
