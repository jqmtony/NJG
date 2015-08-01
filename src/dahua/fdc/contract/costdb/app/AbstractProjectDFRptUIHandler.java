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
public abstract class AbstractProjectDFRptUIHandler extends com.kingdee.eas.framework.app.ListUIHandler

{
	public void handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAudit(request,response,context);
	}
	abstract protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionAntiAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAntiAudit(request,response,context);
	}
	abstract protected void _handleActionAntiAudit(RequestContext request,ResponseContext response, Context context)
		throws Exception;
}