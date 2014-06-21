/**
 * output package name
 */
package com.kingdee.eas.port.equipment.special.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractAnnualYearDetailEditUIHandler extends com.kingdee.eas.xr.app.XRBillBaseEditUIHandler

{
	public void handleActionEntry(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionEntry(request,response,context);
	}
	protected void _handleActionEntry(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionConfirmation(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionConfirmation(request,response,context);
	}
	protected void _handleActionConfirmation(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnConfirmation(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnConfirmation(request,response,context);
	}
	protected void _handleActionUnConfirmation(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionConfirm(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionConfirm(request,response,context);
	}
	protected void _handleActionConfirm(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnConfirm(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnConfirm(request,response,context);
	}
	protected void _handleActionUnConfirm(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}