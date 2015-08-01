/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractDynaticFundPlanUIHandler extends com.kingdee.eas.fdc.schedule.framework.app.ScheduleRateAchievedBaseUIHandler

{
	public void handleActionQueryData(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionQueryData(request,response,context);
	}
	protected void _handleActionQueryData(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}