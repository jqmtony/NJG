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
public abstract class AbstractContractChangeSettUIHandler extends com.kingdee.eas.framework.app.CoreBillEditUIHandler

{
	public void handleActionAutoCount(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAutoCount(request,response,context);
	}
	protected void _handleActionAutoCount(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}