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
public abstract class AbstractFDCSplitBillListUIHandler extends com.kingdee.eas.fdc.basedata.app.ProjectTreeListBaseUIHandler

{
	public void handleActionProjectAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionProjectAttachment(request,response,context);
	}
	abstract protected void _handleActionProjectAttachment(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionViewContent(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewContent(request,response,context);
	}
	abstract protected void _handleActionViewContent(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionAddContent(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddContent(request,response,context);
	}
	abstract protected void _handleActionAddContent(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionCostSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCostSplit(request,response,context);
	}
	abstract protected void _handleActionCostSplit(RequestContext request,ResponseContext response, Context context)
		throws Exception;
}