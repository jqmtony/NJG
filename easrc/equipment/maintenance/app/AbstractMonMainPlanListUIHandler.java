/**
 * output package name
 */
package com.kingdee.eas.port.equipment.maintenance.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractMonMainPlanListUIHandler extends com.kingdee.eas.xr.app.XRBillBaseListUIHandler

{
	public void handleActionScrws(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionScrws(request,response,context);
	}
	protected void _handleActionScrws(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}