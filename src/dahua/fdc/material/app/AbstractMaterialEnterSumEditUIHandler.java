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
public abstract class AbstractMaterialEnterSumEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionImportFormPlan(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportFormPlan(request,response,context);
	}
	protected void _handleActionImportFormPlan(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}