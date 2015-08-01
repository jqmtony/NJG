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
public abstract class AbstractFdcTree2ListUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionFieldConfig(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionFieldConfig(request,response,context);
	}
	protected void _handleActionFieldConfig(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSegment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSegment(request,response,context);
	}
	protected void _handleActionSegment(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}