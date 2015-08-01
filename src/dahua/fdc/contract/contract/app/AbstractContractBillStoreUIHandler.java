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
public abstract class AbstractContractBillStoreUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionDo(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDo(request,response,context);
	}
	protected void _handleActionDo(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRe(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRe(request,response,context);
	}
	protected void _handleActionRe(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}