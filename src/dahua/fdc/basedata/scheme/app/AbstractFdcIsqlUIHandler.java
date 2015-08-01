/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.scheme.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractFdcIsqlUIHandler extends com.kingdee.eas.fm.common.app.FMIsqlUIHandler

{
	public void handleActionShowDirectByUUID(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionShowDirectByUUID(request,response,context);
	}
	protected void _handleActionShowDirectByUUID(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}