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
public abstract class AbstractContractSettlementBillEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSplit(request,response,context);
	}
	protected void _handleActionSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewAttachment(request,response,context);
	}
	protected void _handleActionViewAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAttenTwo(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAttenTwo(request,response,context);
	}
	protected void _handleActionAttenTwo(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewTwo(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewTwo(request,response,context);
	}
	protected void _handleActionViewTwo(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewContent(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewContent(request,response,context);
	}
	protected void _handleActionViewContent(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}