/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractProgrammingUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionImport(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImport(request,response,context);
	}
	protected void _handleActionImport(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionExportPro(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExportPro(request,response,context);
	}
	protected void _handleActionExportPro(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRefresh(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRefresh(request,response,context);
	}
	protected void _handleActionRefresh(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionModify(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionModify(request,response,context);
	}
	protected void _handleActionModify(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionHistoryVersion(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionHistoryVersion(request,response,context);
	}
	protected void _handleActionHistoryVersion(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}