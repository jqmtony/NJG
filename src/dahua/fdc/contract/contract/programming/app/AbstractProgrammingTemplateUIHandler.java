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
public abstract class AbstractProgrammingTemplateUIHandler extends com.kingdee.eas.basedata.framework.app.DataBaseSIEditUIHandler

{
	public void handleActionRefresh(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRefresh(request,response,context);
	}
	protected void _handleActionRefresh(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAddTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddTemplate(request,response,context);
	}
	protected void _handleActionAddTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionEditTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionEditTemplate(request,response,context);
	}
	protected void _handleActionEditTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDelTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDelTemplate(request,response,context);
	}
	protected void _handleActionDelTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSaveTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSaveTemplate(request,response,context);
	}
	protected void _handleActionSaveTemplate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}