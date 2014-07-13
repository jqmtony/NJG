/**
 * output package name
 */
package com.kingdee.eas.port.equipment.insurance.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractInsuranceCoverageEditUIHandler extends com.kingdee.eas.xr.app.XRBillBaseEditUIHandler

{
	public void handleActionExcelBxmx(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExcelBxmx(request,response,context);
	}
	protected void _handleActionExcelBxmx(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}