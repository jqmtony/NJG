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
public abstract class AbstractContractCostSplitEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCSplitBillEditUIHandler

{
	public void handleActionProgrAcctSelect(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionProgrAcctSelect(request,response,context);
	}
	protected void _handleActionProgrAcctSelect(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCostContractView(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCostContractView(request,response,context);
	}
	protected void _handleActionCostContractView(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}