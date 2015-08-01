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
public abstract class AbstractBeforeAccountViewListUIHandler extends com.kingdee.eas.framework.app.ListUIHandler

{
	public void handleActionImpTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImpTemplate(request,response,context);
	}
	abstract protected void _handleActionImpTemplate(RequestContext request,ResponseContext response, Context context)
		throws Exception;
}