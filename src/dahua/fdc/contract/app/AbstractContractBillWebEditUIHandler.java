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
public abstract class AbstractContractBillWebEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSplit(request,response,context);
	}
	protected void _handleActionSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewContent(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewContent(request,response,context);
	}
	protected void _handleActionViewContent(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionContractPlan(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionContractPlan(request,response,context);
	}
	protected void _handleActionContractPlan(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDelSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDelSplit(request,response,context);
	}
	protected void _handleActionDelSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewCost(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewCost(request,response,context);
	}
	protected void _handleActionViewCost(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionProgram(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionProgram(request,response,context);
	}
	protected void _handleActionProgram(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewProgramCon(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewProgramCon(request,response,context);
	}
	protected void _handleActionViewProgramCon(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}