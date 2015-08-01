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
public abstract class AbstractProjectIndexDataVerMntUIHandler extends com.kingdee.eas.fdc.basedata.app.ProjectIndexDataMntUIHandler

{
	public void handleActionFirst(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionFirst(request,response,context);
	}
	abstract protected void _handleActionFirst(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionNext(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionNext(request,response,context);
	}
	abstract protected void _handleActionNext(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionPre(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPre(request,response,context);
	}
	abstract protected void _handleActionPre(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionLast(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionLast(request,response,context);
	}
	abstract protected void _handleActionLast(RequestContext request,ResponseContext response, Context context)
		throws Exception;
}