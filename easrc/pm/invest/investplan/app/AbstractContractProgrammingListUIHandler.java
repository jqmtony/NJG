/**
 * output package name
 */
package com.kingdee.eas.port.pm.invest.investplan.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractContractProgrammingListUIHandler extends com.kingdee.eas.fdc.basedata.app.ProjectTreeListBaseUIHandler

{
	public void handleActionEmend(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionEmend(request,response,context);
	}
	protected void _handleActionEmend(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}