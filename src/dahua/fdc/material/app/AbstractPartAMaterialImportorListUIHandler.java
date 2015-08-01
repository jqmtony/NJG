/**
 * output package name
 */
package com.kingdee.eas.fdc.material.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractPartAMaterialImportorListUIHandler extends com.kingdee.eas.fdc.material.app.PartAMaterialListUIHandler

{
	public void handleconfirmImportData(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleconfirmImportData(request,response,context);
	}
	protected void _handleconfirmImportData(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handlecancel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handlecancel(request,response,context);
	}
	protected void _handlecancel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}