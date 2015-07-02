/**
 * output package name
 */
package com.kingdee.eas.fi.ar.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractOtherBillEditUIHandler extends com.kingdee.eas.fi.ar.app.ArApBillEditUIHandler

{
	public void handleActionFetchPricePolicy(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionFetchPricePolicy(request,response,context);
	}
	protected void _handleActionFetchPricePolicy(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRemainAmountQuery(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRemainAmountQuery(request,response,context);
	}
	protected void _handleActionRemainAmountQuery(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionEditVouchers(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionEditVouchers(request,response,context);
	}
	protected void _handleActionEditVouchers(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewWriteOffRecord(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewWriteOffRecord(request,response,context);
	}
	protected void _handleActionViewWriteOffRecord(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}