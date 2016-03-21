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
public abstract class AbstractIndoorengListUIHandler extends com.kingdee.eas.framework.app.CoreBillListUIHandler

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
	public void handleactionUnAduit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionUnAduit(request,response,context);
	}
	protected void _handleactionUnAduit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionAduit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionAduit(request,response,context);
	}
	protected void _handleactionAduit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactioneRevise(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactioneRevise(request,response,context);
	}
	protected void _handleactioneRevise(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}