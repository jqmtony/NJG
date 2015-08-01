/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.scheme.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractDelProjectUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionDelProject(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDelProject(request,response,context);
	}
	protected void _handleActionDelProject(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}