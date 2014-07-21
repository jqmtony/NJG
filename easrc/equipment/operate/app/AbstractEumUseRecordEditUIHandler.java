/**
 * output package name
 */
package com.kingdee.eas.port.equipment.operate.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractEumUseRecordEditUIHandler extends com.kingdee.eas.xr.app.XRBillBaseEditUIHandler

{
	public void handleActionShiyong(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionShiyong(request,response,context);
	}
	protected void _handleActionShiyong(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}