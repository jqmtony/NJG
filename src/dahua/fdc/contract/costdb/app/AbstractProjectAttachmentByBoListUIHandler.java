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
public abstract class AbstractProjectAttachmentByBoListUIHandler extends com.kingdee.eas.framework.app.ListUIHandler

{
	public void handleActionDownload(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDownload(request,response,context);
	}
	abstract protected void _handleActionDownload(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionViewContent(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewContent(request,response,context);
	}
	abstract protected void _handleActionViewContent(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionAddNewAttch(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddNewAttch(request,response,context);
	}
	abstract protected void _handleActionAddNewAttch(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionDisplayFunction(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDisplayFunction(request,response,context);
	}
	abstract protected void _handleActionDisplayFunction(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionAddAlreadyFile(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddAlreadyFile(request,response,context);
	}
	abstract protected void _handleActionAddAlreadyFile(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionSetScan(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSetScan(request,response,context);
	}
	abstract protected void _handleActionSetScan(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionSetDriver(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSetDriver(request,response,context);
	}
	abstract protected void _handleActionSetDriver(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionSelectScan(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSelectScan(request,response,context);
	}
	abstract protected void _handleActionSelectScan(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionAddScan(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddScan(request,response,context);
	}
	abstract protected void _handleActionAddScan(RequestContext request,ResponseContext response, Context context)
		throws Exception;
}