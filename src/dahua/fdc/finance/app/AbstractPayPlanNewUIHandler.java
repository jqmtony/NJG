/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractPayPlanNewUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleActionByBuilding(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionByBuilding(request,response,context);
	}
	protected void _handleActionByBuilding(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionByMonth(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionByMonth(request,response,context);
	}
	protected void _handleActionByMonth(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUpdateBySchedule(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUpdateBySchedule(request,response,context);
	}
	protected void _handleActionUpdateBySchedule(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}