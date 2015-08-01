/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractProjectCostAnalysisUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCRptBaseUIHandler

{
	public void handleActionAll(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAll(request,response,context);
	}
	protected void _handleActionAll(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}