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
public abstract class AbstractCostSplitAcctUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionSave(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSave(request,response,context);
	}
	protected void _handleActionSave(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCancel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCancel(request,response,context);
	}
	protected void _handleActionCancel(RequestContext request,ResponseContext response, Context context) throws Exception {
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
	public void handleActionIsSplitAcct(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionIsSplitAcct(request,response,context);
	}
	protected void _handleActionIsSplitAcct(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}