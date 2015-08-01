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
public abstract class AbstractPartAMaterialEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionImportMaterial(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportMaterial(request,response,context);
	}
	protected void _handleActionImportMaterial(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionImportFromTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportFromTemplate(request,response,context);
	}
	protected void _handleActionImportFromTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}