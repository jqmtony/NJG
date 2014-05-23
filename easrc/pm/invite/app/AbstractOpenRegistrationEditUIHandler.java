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
public abstract class AbstractOpenRegistrationEditUIHandler extends com.kingdee.eas.xr.app.XRBillBaseEditUIHandler

{
	public void handleActionDoCancel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDoCancel(request,response,context);
	}
	protected void _handleActionDoCancel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}