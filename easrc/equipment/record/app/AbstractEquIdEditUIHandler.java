/**
 * output package name
 */
package com.kingdee.eas.port.equipment.record.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractEquIdEditUIHandler extends com.kingdee.eas.xr.app.XRBillBaseEditUIHandler

{
	public void handleActionInUse(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionInUse(request,response,context);
	}
	protected void _handleActionInUse(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionOutUse(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionOutUse(request,response,context);
	}
	protected void _handleActionOutUse(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}