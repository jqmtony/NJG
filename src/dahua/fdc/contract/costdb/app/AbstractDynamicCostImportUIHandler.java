/**
 * output package name
 */
package com.kingdee.eas.fdc.costdb.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractDynamicCostImportUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionChart(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionChart(request,response,context);
	}
	abstract protected void _handleActionChart(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionSave(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSave(request,response,context);
	}
	abstract protected void _handleActionSave(RequestContext request,ResponseContext response, Context context)
		throws Exception;
}