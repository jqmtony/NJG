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
public abstract class AbstractPreProjectEditUIHandler extends com.kingdee.eas.xr.app.XRBillBaseEditUIHandler

{
	public void handleactionSubmitTwo(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionSubmitTwo(request,response,context);
	}
	protected void _handleactionSubmitTwo(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionWorkCouldReport(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionWorkCouldReport(request,response,context);
	}
	protected void _handleActionWorkCouldReport(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPreliminaryDesignReport(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPreliminaryDesignReport(request,response,context);
	}
	protected void _handleActionPreliminaryDesignReport(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}