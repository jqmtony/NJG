/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.mobile.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractProjectTargetShowEditUIHandler extends com.kingdee.eas.fdc.basedata.mobile.app.FdcMobileEditUIHandler

{
	public void handleActionAddItem(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddItem(request,response,context);
	}
	protected void _handleActionAddItem(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionInsertItem(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionInsertItem(request,response,context);
	}
	protected void _handleActionInsertItem(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRemoveItem(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRemoveItem(request,response,context);
	}
	protected void _handleActionRemoveItem(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAutoGetData(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAutoGetData(request,response,context);
	}
	protected void _handleActionAutoGetData(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}