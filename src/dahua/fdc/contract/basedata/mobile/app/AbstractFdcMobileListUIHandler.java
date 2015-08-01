/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.mobile.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractFdcMobileListUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillListUIHandler

{
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
	public void handleActionExportBillSql(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExportBillSql(request,response,context);
	}
	protected void _handleActionExportBillSql(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionExportEntrySql(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExportEntrySql(request,response,context);
	}
	protected void _handleActionExportEntrySql(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionExportItemSql(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExportItemSql(request,response,context);
	}
	protected void _handleActionExportItemSql(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionExportSql(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExportSql(request,response,context);
	}
	protected void _handleActionExportSql(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}