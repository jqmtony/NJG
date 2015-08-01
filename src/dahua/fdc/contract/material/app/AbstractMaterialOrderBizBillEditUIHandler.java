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
public abstract class AbstractMaterialOrderBizBillEditUIHandler extends com.kingdee.eas.framework.app.CoreBillEditUIHandler

{
	public void handleactionImportMes(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionImportMes(request,response,context);
	}
	protected void _handleactionImportMes(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionAudit(request,response,context);
	}
	protected void _handleactionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionUnAudit(request,response,context);
	}
	protected void _handleactionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}