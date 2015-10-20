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
public abstract class AbstractPayRequestBillWebEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionCalc(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCalc(request,response,context);
	}
	protected void _handleActionCalc(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionTaoPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionTaoPrint(request,response,context);
	}
	protected void _handleActionTaoPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPaymentPlan(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPaymentPlan(request,response,context);
	}
	protected void _handleActionPaymentPlan(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAdjustDeduct(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAdjustDeduct(request,response,context);
	}
	protected void _handleActionAdjustDeduct(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionClose(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionClose(request,response,context);
	}
	protected void _handleActionClose(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnClose(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnClose(request,response,context);
	}
	protected void _handleActionUnClose(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewContract(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewContract(request,response,context);
	}
	protected void _handleActionViewContract(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewMbgBalance(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewMbgBalance(request,response,context);
	}
	protected void _handleActionViewMbgBalance(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewPayDetail(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewPayDetail(request,response,context);
	}
	protected void _handleActionViewPayDetail(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAssociateAcctPay(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAssociateAcctPay(request,response,context);
	}
	protected void _handleActionAssociateAcctPay(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAssociateUnSettled(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAssociateUnSettled(request,response,context);
	}
	protected void _handleActionAssociateUnSettled(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionContractAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionContractAttachment(request,response,context);
	}
	protected void _handleActionContractAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}