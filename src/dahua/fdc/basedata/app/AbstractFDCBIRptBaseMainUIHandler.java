/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractFDCBIRptBaseMainUIHandler extends com.kingdee.eas.framework.bireport.app.BireportBaseMainUIHandler

{
	public void handleActionJoinQuery(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionJoinQuery(request,response,context);
	}
	abstract protected void _handleActionJoinQuery(RequestContext request,ResponseContext response, Context context)
		throws Exception;
}