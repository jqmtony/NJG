/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractProjectTypeSetUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionOK(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionOK(request,response,context);
	}
	abstract protected void _handleActionOK(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleAcionCancel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleAcionCancel(request,response,context);
	}
	abstract protected void _handleAcionCancel(RequestContext request,ResponseContext response, Context context)
		throws Exception;
}