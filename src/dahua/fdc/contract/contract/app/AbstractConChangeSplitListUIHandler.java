/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractConChangeSplitListUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCSplitBillListUIHandler

{
	public void handleActionViewInvalid(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewInvalid(request,response,context);
	}
	protected void _handleActionViewInvalid(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionClearSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionClearSplit(request,response,context);
	}
	protected void _handleActionClearSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}