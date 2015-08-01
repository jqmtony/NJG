/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractAimCostCtrlBillListUIHandler extends com.kingdee.eas.fdc.basedata.app.ProjectTreeListBaseUIHandler

{
	public void handleActionImportTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportTemplate(request,response,context);
	}
	protected void _handleActionImportTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAmendment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAmendment(request,response,context);
	}
	protected void _handleActionAmendment(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}