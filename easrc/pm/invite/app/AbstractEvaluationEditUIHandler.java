/**
 * output package name
 */
package com.kingdee.eas.port.pm.invite.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractEvaluationEditUIHandler extends com.kingdee.eas.xr.app.XRBillBaseEditUIHandler

{
	public void handleactionPrintFHX(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionPrintFHX(request,response,context);
	}
	protected void _handleactionPrintFHX(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionPrintPFXX(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionPrintPFXX(request,response,context);
	}
	protected void _handleactionPrintPFXX(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionPrintZF(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionPrintZF(request,response,context);
	}
	protected void _handleactionPrintZF(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}