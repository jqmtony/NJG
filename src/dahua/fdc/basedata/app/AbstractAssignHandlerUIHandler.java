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
public abstract class AbstractAssignHandlerUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionBtnConfirm(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBtnConfirm(request,response,context);
	}
	protected void _handleActionBtnConfirm(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionBtnCancle(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBtnCancle(request,response,context);
	}
	protected void _handleActionBtnCancle(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAllSelect(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAllSelect(request,response,context);
	}
	protected void _handleActionAllSelect(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionNoneSelect(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionNoneSelect(request,response,context);
	}
	protected void _handleActionNoneSelect(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}