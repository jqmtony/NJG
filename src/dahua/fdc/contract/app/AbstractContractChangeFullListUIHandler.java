/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractContractChangeFullListUIHandler extends com.kingdee.eas.framework.app.BillListUIHandler

{
	public void handleActionDispatch(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDispatch(request,response,context);
	}
	protected void _handleActionDispatch(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionVias(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionVias(request,response,context);
	}
	protected void _handleActionVias(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSettlement(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSettlement(request,response,context);
	}
	protected void _handleActionSettlement(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAudit(request,response,context);
	}
	protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}