/**
 * output package name
 */
package com.kingdee.eas.port.pm.invest.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractYearInvestPlanListUIHandler extends com.kingdee.eas.xr.app.XRBillBaseListUIHandler

{
	public void handleActionAdjust(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAdjust(request,response,context);
	}
	protected void _handleActionAdjust(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionChange(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionChange(request,response,context);
	}
	protected void _handleActionChange(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionContinue(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionContinue(request,response,context);
	}
	protected void _handleActionContinue(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}