/**
 * output package name
 */
package com.kingdee.eas.port.equipment.record.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractMeasurEquipListUIHandler extends com.kingdee.eas.xr.app.XRBillBaseListUIHandler

{
	public void handleActionExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExcel(request,response,context);
	}
	protected void _handleActionExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}