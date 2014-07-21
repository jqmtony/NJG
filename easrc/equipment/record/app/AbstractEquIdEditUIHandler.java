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
public abstract class AbstractEquIdEditUIHandler extends com.kingdee.eas.xr.app.XRBillBaseEditUIHandler

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
	public void handleActionExcelFoced(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExcelFoced(request,response,context);
	}
	protected void _handleActionExcelFoced(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionExcelEqu(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExcelEqu(request,response,context);
	}
	protected void _handleActionExcelEqu(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionZhuyao(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionZhuyao(request,response,context);
	}
	protected void _handleActionZhuyao(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionBeijian(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBeijian(request,response,context);
	}
	protected void _handleActionBeijian(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionXiangxi(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionXiangxi(request,response,context);
	}
	protected void _handleActionXiangxi(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}