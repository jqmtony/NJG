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
public abstract class AbstractPreProjectListUIHandler extends com.kingdee.eas.xr.app.XRBillBaseListUIHandler

{
	public void handleActionWorkReport(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionWorkReport(request,response,context);
	}
	protected void _handleActionWorkReport(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}