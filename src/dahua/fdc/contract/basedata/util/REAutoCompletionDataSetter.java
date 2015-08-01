package com.kingdee.eas.fdc.basedata.util;

/**
 * 选择下拉框中数据后调用此接口设置文本框数据。默认实现为设置文本框文本为下拉框显示文本， 并设置文本框的userObject为下拉框的object
 * 开发可实现自己的DataSetter
 * 
 * @author zhiqiao_yang
 * 
 */
public interface REAutoCompletionDataSetter {
	void setFieldData(Object data);
}
