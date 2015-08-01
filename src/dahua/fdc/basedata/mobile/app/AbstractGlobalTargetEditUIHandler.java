/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.mobile.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractGlobalTargetEditUIHandler extends com.kingdee.eas.fdc.basedata.mobile.app.FdcMobileEditUIHandler

{
	public void handleActionAutoGetData(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAutoGetData(request,response,context);
	}
	protected void _handleActionAutoGetData(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}