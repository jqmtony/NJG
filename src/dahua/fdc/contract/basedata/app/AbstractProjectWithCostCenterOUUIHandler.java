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
public abstract class AbstractProjectWithCostCenterOUUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionSave(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSave(request,response,context);
	}
	abstract protected void _handleActionSave(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionAddLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddLine(request,response,context);
	}
	abstract protected void _handleActionAddLine(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionInsertLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionInsertLine(request,response,context);
	}
	abstract protected void _handleActionInsertLine(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionDeleteLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDeleteLine(request,response,context);
	}
	abstract protected void _handleActionDeleteLine(RequestContext request,ResponseContext response, Context context)
		throws Exception;
}