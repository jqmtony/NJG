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
public abstract class AbstractViewContractUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionExit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExit(request,response,context);
	}
	protected void _handleActionExit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}