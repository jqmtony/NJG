/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractForecastChangeVisEditUIHandler extends com.kingdee.eas.framework.app.CoreBillEditUIHandler

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
	public void handleactionAcctSelect(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionAcctSelect(request,response,context);
	}
	protected void _handleactionAcctSelect(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionSplitProj(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionSplitProj(request,response,context);
	}
	protected void _handleactionSplitProj(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionSplitBotUp(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionSplitBotUp(request,response,context);
	}
	protected void _handleactionSplitBotUp(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionSplitProd(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionSplitProd(request,response,context);
	}
	protected void _handleactionSplitProd(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}