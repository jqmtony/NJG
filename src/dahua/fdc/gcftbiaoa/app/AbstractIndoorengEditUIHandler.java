/**
 * output package name
 */
package com.kingdee.eas.fdc.gcftbiaoa.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractIndoorengEditUIHandler extends com.kingdee.eas.framework.app.CoreBillEditUIHandler

{
	public void handleactionAduit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionAduit(request,response,context);
	}
	protected void _handleactionAduit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleactionUnAduit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleactionUnAduit(request,response,context);
	}
	protected void _handleactionUnAduit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}