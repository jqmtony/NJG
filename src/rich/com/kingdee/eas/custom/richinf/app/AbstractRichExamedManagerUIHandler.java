/**
 * output package name
 */
package com.kingdee.eas.custom.richinf.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractRichExamedManagerUIHandler extends com.kingdee.eas.framework.app.CoreBillListUIHandler

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
	public void handleactionAddNewInviton(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionAddNewInviton(request,response,context);
	}
	protected void _handleactionAddNewInviton(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionInvoiteCreatToBill(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionInvoiteCreatToBill(request,response,context);
	}
	protected void _handleactionInvoiteCreatToBill(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionCompnyOff(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionCompnyOff(request,response,context);
	}
	protected void _handleactionCompnyOff(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionCustomerOff(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionCustomerOff(request,response,context);
	}
	protected void _handleactionCustomerOff(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionSyncustomer(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionSyncustomer(request,response,context);
	}
	protected void _handleactionSyncustomer(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionViewLog(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionViewLog(request,response,context);
	}
	protected void _handleactionViewLog(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}