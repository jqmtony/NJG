/**
 * output package name
 */
package com.kingdee.eas.fdc.gcftbiaoa.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractGcftbEditUIHandler extends com.kingdee.eas.framework.app.CoreBillEditUIHandler

{
	public void handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAudit(request,response,context);
	}
	protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnaudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnaudit(request,response,context);
	}
	protected void _handleActionUnaudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionHistoryVersion(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionHistoryVersion(request,response,context);
	}
	protected void _handleactionHistoryVersion(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionModify(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionModify(request,response,context);
	}
	protected void _handleactionModify(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}