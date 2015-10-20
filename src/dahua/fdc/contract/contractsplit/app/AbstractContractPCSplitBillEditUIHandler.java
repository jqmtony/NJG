/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.contractsplit.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractContractPCSplitBillEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionPCSelect(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPCSelect(request,response,context);
	}
	protected void _handleActionPCSelect(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}