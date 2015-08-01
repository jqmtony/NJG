/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.scheme.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractFdcEntityObjectListUIHandler extends com.kingdee.eas.fdc.basedata.app.FdcTree2ListUIHandler

{
	public void handleActionSynchronizeMD(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSynchronizeMD(request,response,context);
	}
	protected void _handleActionSynchronizeMD(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSynchronizeEasMD(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSynchronizeEasMD(request,response,context);
	}
	protected void _handleActionSynchronizeEasMD(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSynchronizeBaseMD(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSynchronizeBaseMD(request,response,context);
	}
	protected void _handleActionSynchronizeBaseMD(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSynchronizeFdcMD(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSynchronizeFdcMD(request,response,context);
	}
	protected void _handleActionSynchronizeFdcMD(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}