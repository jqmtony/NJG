/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractQkongUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionStoreData(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionStoreData(request,response,context);
	}
	protected void _handleActionStoreData(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}