/**
 * output package name
 */
package com.kingdee.eas.port.equipment.special.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractOverhaulNoticeEditUIHandler extends com.kingdee.eas.xr.app.XRBillBaseEditUIHandler

{
	public void handleActionActitonConRect(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionActitonConRect(request,response,context);
	}
	protected void _handleActionActitonConRect(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnConRet(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnConRet(request,response,context);
	}
	protected void _handleActionUnConRet(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionFeedInfor(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionFeedInfor(request,response,context);
	}
	protected void _handleActionFeedInfor(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}