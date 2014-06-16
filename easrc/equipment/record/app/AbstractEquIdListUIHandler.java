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
public abstract class AbstractEquIdListUIHandler extends com.kingdee.eas.xr.app.XRBillBaseListUIHandler

{
	public void handleActionInUse(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionInUse(request,response,context);
	}
	protected void _handleActionInUse(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionOutUse(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionOutUse(request,response,context);
	}
	protected void _handleActionOutUse(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRegistChange(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRegistChange(request,response,context);
	}
	protected void _handleActionRegistChange(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExcel(request,response,context);
	}
	protected void _handleActionExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionImportFacard(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionImportFacard(request,response,context);
	}
	protected void _handleactionImportFacard(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionExcelFoced(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExcelFoced(request,response,context);
	}
	protected void _handleActionExcelFoced(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}