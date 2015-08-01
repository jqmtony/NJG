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
public abstract class AbstractMaterialConfirmBillEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionImportMaterialEntry(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportMaterialEntry(request,response,context);
	}
	protected void _handleActionImportMaterialEntry(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionImportMaterialOrder(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportMaterialOrder(request,response,context);
	}
	protected void _handleActionImportMaterialOrder(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}