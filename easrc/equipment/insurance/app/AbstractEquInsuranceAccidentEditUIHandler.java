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
public abstract class AbstractEquInsuranceAccidentEditUIHandler extends com.kingdee.eas.xr.app.XRBillBaseEditUIHandler

{
	public void handleActionEquInfomation(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionEquInfomation(request,response,context);
	}
	protected void _handleActionEquInfomation(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}