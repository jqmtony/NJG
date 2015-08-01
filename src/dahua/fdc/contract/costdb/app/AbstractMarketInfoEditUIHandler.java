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
public abstract class AbstractMarketInfoEditUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleActionUploadAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUploadAttachment(request,response,context);
	}
	abstract protected void _handleActionUploadAttachment(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionRemoveAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRemoveAttachment(request,response,context);
	}
	abstract protected void _handleActionRemoveAttachment(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionViewContent(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewContent(request,response,context);
	}
	abstract protected void _handleActionViewContent(RequestContext request,ResponseContext response, Context context)
		throws Exception;
}