/**
 * output package name
 */
package com.kingdee.eas.port.pm.contract.app;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public class ContractWithoutTextListUIHandler extends AbstractContractWithoutTextListUIHandler
{
	protected void _handleInit(RequestContext request,ResponseContext response, Context context) throws Exception {
		super._handleInit(request,response,context);
	}
	protected void _handleActionViewPayment(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	protected void _handleActionProgram(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	protected void _handleActionUnProgram(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}