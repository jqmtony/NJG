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
public abstract class AbstractProjectDynaticFundPayPlanListUIHandler extends com.kingdee.eas.fdc.basedata.app.ProjectTreeListBaseUIHandler

{
	public void handleActionSyn(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSyn(request,response,context);
	}
	protected void _handleActionSyn(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnit(request,response,context);
	}
	protected void _handleActionUnit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionMonthPayDistributePic(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionMonthPayDistributePic(request,response,context);
	}
	protected void _handleActionMonthPayDistributePic(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPayProgressPic(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPayProgressPic(request,response,context);
	}
	protected void _handleActionPayProgressPic(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}