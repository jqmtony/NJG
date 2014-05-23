/**
 * output package name
 */
package com.kingdee.eas.port.pm.contract.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractContractWithoutTextListUIHandler extends com.kingdee.eas.fdc.basedata.app.ProjectTreeListBaseUIHandler

{
	public void handleActionViewPayment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewPayment(request,response,context);
	}
	protected void _handleActionViewPayment(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionProgram(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionProgram(request,response,context);
	}
	protected void _handleActionProgram(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnProgram(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnProgram(request,response,context);
	}
	protected void _handleActionUnProgram(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}