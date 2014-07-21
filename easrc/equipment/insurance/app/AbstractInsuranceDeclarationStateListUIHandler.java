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
public abstract class AbstractInsuranceDeclarationStateListUIHandler extends com.kingdee.eas.xr.app.XRBillBaseListUIHandler

{
	public void handleActionShengbao(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionShengbao(request,response,context);
	}
	protected void _handleActionShengbao(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}