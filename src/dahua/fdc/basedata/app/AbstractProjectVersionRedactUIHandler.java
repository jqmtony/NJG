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
public abstract class AbstractProjectVersionRedactUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionSave(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSave(request,response,context);
	}
	abstract protected void _handleActionSave(RequestContext request,ResponseContext response, Context context)
		throws Exception;
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
	public void handleActionLast(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionLast(request,response,context);
	}
	abstract protected void _handleActionLast(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionPrev(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPrev(request,response,context);
	}
	abstract protected void _handleActionPrev(RequestContext request,ResponseContext response, Context context)
		throws Exception;
}