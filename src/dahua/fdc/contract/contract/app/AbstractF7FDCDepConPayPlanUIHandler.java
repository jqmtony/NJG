/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractF7FDCDepConPayPlanUIHandler extends com.kingdee.eas.framework.app.ListUIHandler

{
	public void handleActionQuit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionQuit(request,response,context);
	}
	protected void _handleActionQuit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}