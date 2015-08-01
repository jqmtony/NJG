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
public abstract class AbstractCostAccountAssignUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionAssign(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAssign(request,response,context);
	}
	abstract protected void _handleActionAssign(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionAssigned(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAssigned(request,response,context);
	}
	abstract protected void _handleActionAssigned(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionNotAssigned(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionNotAssigned(request,response,context);
	}
	abstract protected void _handleActionNotAssigned(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionAllSelect(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAllSelect(request,response,context);
	}
	abstract protected void _handleActionAllSelect(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionAllDisselect(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAllDisselect(request,response,context);
	}
	abstract protected void _handleActionAllDisselect(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionSelectUpper(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSelectUpper(request,response,context);
	}
	abstract protected void _handleActionSelectUpper(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionDisSelectUpper(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDisSelectUpper(request,response,context);
	}
	abstract protected void _handleActionDisSelectUpper(RequestContext request,ResponseContext response, Context context)
		throws Exception;
}