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
public abstract class AbstractSettleDeclarationBillListUIHandler extends com.kingdee.eas.framework.app.CoreBillListUIHandler

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
}