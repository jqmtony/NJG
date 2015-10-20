/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.basedata.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractRealDateRelEditUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleActionImportTemp(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportTemp(request,response,context);
	}
	protected void _handleActionImportTemp(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionImportGroup(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportGroup(request,response,context);
	}
	protected void _handleActionImportGroup(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}