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
public abstract class AbstractConPayPlanUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

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
	public void handleActionImportPayPlan(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportPayPlan(request,response,context);
	}
	protected void _handleActionImportPayPlan(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionMonthSettle(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionMonthSettle(request,response,context);
	}
	protected void _handleActionMonthSettle(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionContractFullInfo(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionContractFullInfo(request,response,context);
	}
	protected void _handleActionContractFullInfo(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionContractView(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionContractView(request,response,context);
	}
	protected void _handleActionContractView(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}