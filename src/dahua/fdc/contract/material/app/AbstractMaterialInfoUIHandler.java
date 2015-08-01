/**
 * output package name
 */
package com.kingdee.eas.fdc.material.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractMaterialInfoUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillListUIHandler

{
	public void handleactionShowDotsMap(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionShowDotsMap(request,response,context);
	}
	protected void _handleactionShowDotsMap(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionShowLineMap(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionShowLineMap(request,response,context);
	}
	protected void _handleactionShowLineMap(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionImportDataFormMaterial(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionImportDataFormMaterial(request,response,context);
	}
	protected void _handleactionImportDataFormMaterial(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionExportExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionExportExcel(request,response,context);
	}
	protected void _handleactionExportExcel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAudit(request,response,context);
	}
	protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnAudit(request,response,context);
	}
	protected void _handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}