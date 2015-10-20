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
public class PayRequestBillEditUIHandler extends AbstractPayRequestBillEditUIHandler
{
	// 父类中调用fetchInitParam()和fetchBaseData()，它们的取数在EditUI中没有用到，而且影响性能，所以重写父类方法	去掉之
	protected void fetchFDCInitData(RequestContext request, ResponseContext response, Context context) throws Exception {
		fetchInitData(request, response, context);
	}
	
	protected void _handleActionCalc(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	
	protected void _handleActionTaoPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	
	protected void _handleActionPaymentPlan(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	
	protected void _handleActionAdjustDeduct(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	
	protected void _handleActionClose(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	
	protected void _handleActionUnClose(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	
	protected void _handleActionViewContract(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	
	protected void _handleActionViewMbgBalance(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	
	protected void _handleActionViewContent(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	
	protected void _handleActionViewPayDetail(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	
	protected void _handleActionAssociateAcctPay(RequestContext request, ResponseContext response, Context context) throws Exception {
		
	}
	
	protected void _handleActionAssociateUnSettled(RequestContext request, ResponseContext response, Context context) throws Exception {
	
	}

	protected void _handleActionContractAttachment(RequestContext request, ResponseContext response, Context context) throws Exception {

	}

	protected void _handleActionViewMaterialConfirm(RequestContext request, ResponseContext response, Context context) throws Exception {
		
	}

	protected void _handleActionContractExecInfo(RequestContext request,ResponseContext response, Context context) throws Exception {
		
	}

	protected void _handleActionMonthReq(RequestContext request,ResponseContext response, Context context) throws Exception {
		
	}

	protected void _handleActionViewAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
		
	}
}