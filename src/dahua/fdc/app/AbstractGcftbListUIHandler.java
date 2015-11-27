/**
 * output package name
 */
package com.kingdee.eas.fdc.gcftbiaoa.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractGcftbListUIHandler extends com.kingdee.eas.framework.app.CoreBillListUIHandler

{
	public void handleActionTDPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionTDPrint(request,response,context);
	}
	protected void _handleActionTDPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionTDPrintPreview(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionTDPrintPreview(request,response,context);
	}
	protected void _handleActionTDPrintPreview(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAudit(request,response,context);
	}
	protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnaudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnaudit(request,response,context);
	}
	protected void _handleActionUnaudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionModify(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionModify(request,response,context);
	}
	protected void _handleActionModify(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionAddTree(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionAddTree(request,response,context);
	}
	protected void _handleactionAddTree(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionDelTree(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionDelTree(request,response,context);
	}
	protected void _handleactionDelTree(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionEditTreeName(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionEditTreeName(request,response,context);
	}
	protected void _handleactionEditTreeName(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}