/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.settle.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractSettleDeclarationBillEditUIHandler extends com.kingdee.eas.framework.app.CoreBillEditUIHandler

{
	public void handleActionInTrial(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionInTrial(request,response,context);
	}
	protected void _handleActionInTrial(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionApproved(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionApproved(request,response,context);
	}
	protected void _handleActionApproved(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAudit(request,response,context);
	}
	protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnAudit(request,response,context);
	}
	protected void _handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}