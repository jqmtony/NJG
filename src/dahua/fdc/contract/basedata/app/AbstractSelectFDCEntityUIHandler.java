/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractSelectFDCEntityUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionBtnConfrim(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBtnConfrim(request,response,context);
	}
	protected void _handleActionBtnConfrim(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionBtnCancel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionBtnCancel(request,response,context);
	}
	protected void _handleActionBtnCancel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}