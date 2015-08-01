/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractProjectCostRptUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCRptBaseUIHandler

{
	public void handleActionUpdateData(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUpdateData(request,response,context);
	}
	protected void _handleActionUpdateData(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAll(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAll(request,response,context);
	}
	protected void _handleActionAll(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSplit(request,response,context);
	}
	protected void _handleActionSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}