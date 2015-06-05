/**
 * output package name
 */
package com.kingdee.eas.custom.richinf.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractRichExamTempTabListUIHandler extends com.kingdee.eas.framework.app.ListUIHandler

{
	public void handleActionSyncRichExamed(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSyncRichExamed(request,response,context);
	}
	protected void _handleActionSyncRichExamed(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}