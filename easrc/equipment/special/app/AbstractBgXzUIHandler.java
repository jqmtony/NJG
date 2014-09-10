/**
 * output package name
 */
package com.kingdee.eas.port.equipment.special.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractBgXzUIHandler extends com.kingdee.eas.framework.app.ListUIHandler

{
	public void handleactionAddAtttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionAddAtttachment(request,response,context);
	}
	protected void _handleactionAddAtttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionViewAtttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionViewAtttachment(request,response,context);
	}
	protected void _handleactionViewAtttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionAttRemove(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionAttRemove(request,response,context);
	}
	protected void _handleactionAttRemove(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionDow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionDow(request,response,context);
	}
	protected void _handleactionDow(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionOpen(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionOpen(request,response,context);
	}
	protected void _handleactionOpen(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}