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
public abstract class AbstractContractListBaseUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillListUIHandler

{
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
	public void handleActionRespite(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRespite(request,response,context);
	}
	protected void _handleActionRespite(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCancelRespite(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCancelRespite(request,response,context);
	}
	protected void _handleActionCancelRespite(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}