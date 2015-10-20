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
public abstract class AbstractPaymentLayoutEditUIHandler extends com.kingdee.eas.framework.app.BillEditUIHandler

{
	public void handleimportReWu(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleimportReWu(request,response,context);
	}
	protected void _handleimportReWu(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleaddNew(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleaddNew(request,response,context);
	}
	protected void _handleaddNew(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleDeletePlanMingxi(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleDeletePlanMingxi(request,response,context);
	}
	protected void _handleDeletePlanMingxi(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}