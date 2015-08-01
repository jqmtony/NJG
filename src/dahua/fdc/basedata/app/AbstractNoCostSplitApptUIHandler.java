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
public abstract class AbstractNoCostSplitApptUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleActionSelectAll(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSelectAll(request,response,context);
	}
	abstract protected void _handleActionSelectAll(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionSelectNone(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSelectNone(request,response,context);
	}
	abstract protected void _handleActionSelectNone(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSplit(request,response,context);
	}
	abstract protected void _handleActionSplit(RequestContext request,ResponseContext response, Context context)
		throws Exception;
}