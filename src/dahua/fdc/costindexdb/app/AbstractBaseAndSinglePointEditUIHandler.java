/**
 * output package name
 */
package com.kingdee.eas.fdc.costindexdb.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractBaseAndSinglePointEditUIHandler extends com.kingdee.eas.framework.app.CoreBillEditUIHandler

{
	public void handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAudit(request,response,context);
	}
	protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnAdudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnAdudit(request,response,context);
	}
	protected void _handleActionUnAdudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRefix(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRefix(request,response,context);
	}
	protected void _handleActionRefix(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}