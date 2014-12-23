package com.kingdee.eas.port.pm.JavaDataSet;

import java.awt.Window;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.eas.rpts.ctrlsqldesign.param.AbstractJavaDataSet;
import com.kingdee.jdbc.rowset.IRowSet;

public class ReportDataSet extends AbstractJavaDataSet{

	public String getCustomSQL(Window parent) throws Exception {
		return super.getCustomSQL(parent);
	}
	
	public IRowSet[] getCustomRowSet(Window parent, String otherDataCenter)
			throws Exception {
		return super.getCustomRowSet(parent, otherDataCenter);
	}
	
	public Map getOutputParam() throws Exception {
		
		HashMap outputParamMap = new HashMap();
		
		outputParamMap.put("title","\u6269\u5C55\u62A5\u8868\u9898\u5934\u793A\u4F8B");
		return super.getOutputParam();
	}
}
