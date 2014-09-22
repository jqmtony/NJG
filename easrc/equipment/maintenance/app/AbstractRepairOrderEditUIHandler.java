/**
 * output package name
 */
package com.kingdee.eas.port.equipment.maintenance.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractRepairOrderEditUIHandler extends com.kingdee.eas.xr.app.XRBillBaseEditUIHandler

{
	public void handleActionToVoid(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionToVoid(request,response,context);
	}
	protected void _handleActionToVoid(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnToVoid(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnToVoid(request,response,context);
	}
	protected void _handleActionUnToVoid(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionEquInfomation(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionEquInfomation(request,response,context);
	}
	protected void _handleActionEquInfomation(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}