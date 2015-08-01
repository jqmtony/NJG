package com.kingdee.eas.fdc.basedata.util;

import java.util.ArrayList;

/**
 * 当文本框中文本改变时自动调用此接口获取下拉列表选项，需开发人员自己实现
 * 
 * @author zhiqiao_yang
 * 
 */
public interface REAutoCompletionDataFilter {
	public ArrayList filter(String text);
}
