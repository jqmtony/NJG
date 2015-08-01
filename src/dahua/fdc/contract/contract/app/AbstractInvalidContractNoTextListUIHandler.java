/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractInvalidContractNoTextListUIHandler extends com.kingdee.eas.fdc.basedata.app.ProjectTreeListBaseUIHandler

{
	public void handleactionTrace(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionTrace(request,response,context);
	}
	protected void _handleactionTrace(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewAdjust(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewAdjust(request,response,context);
	}
	protected void _handleActionViewAdjust(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}