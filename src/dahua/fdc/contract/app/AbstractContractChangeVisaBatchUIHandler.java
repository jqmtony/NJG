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
public abstract class AbstractContractChangeVisaBatchUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionComfirm(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionComfirm(request,response,context);
	}
	protected void _handleActionComfirm(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCancel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCancel(request,response,context);
	}
	protected void _handleActionCancel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}