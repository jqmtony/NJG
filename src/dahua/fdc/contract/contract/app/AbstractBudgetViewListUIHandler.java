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
public abstract class AbstractBudgetViewListUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActioViewPaiedDetail(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActioViewPaiedDetail(request,response,context);
	}
	protected void _handleActioViewPaiedDetail(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewPlanBill(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewPlanBill(request,response,context);
	}
	protected void _handleActionViewPlanBill(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionQuit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionQuit(request,response,context);
	}
	protected void _handleActionQuit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}