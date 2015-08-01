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
public abstract class AbstractFIProjectListUIHandler extends com.kingdee.eas.fdc.basedata.app.ProjectListUIHandler

{
	public void handleActionGetted(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionGetted(request,response,context);
	}
	abstract protected void _handleActionGetted(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionFlow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionFlow(request,response,context);
	}
	abstract protected void _handleActionFlow(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionClose(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionClose(request,response,context);
	}
	abstract protected void _handleActionClose(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionAntiGetted(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAntiGetted(request,response,context);
	}
	abstract protected void _handleActionAntiGetted(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionAntiFlow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAntiFlow(request,response,context);
	}
	abstract protected void _handleActionAntiFlow(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionAntiClose(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAntiClose(request,response,context);
	}
	abstract protected void _handleActionAntiClose(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionTransfer(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionTransfer(request,response,context);
	}
	abstract protected void _handleActionTransfer(RequestContext request,ResponseContext response, Context context)
		throws Exception;
}