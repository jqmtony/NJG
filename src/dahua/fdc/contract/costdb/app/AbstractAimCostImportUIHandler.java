/**
 * output package name
 */
package com.kingdee.eas.fdc.costdb.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractAimCostImportUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionSubmit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSubmit(request,response,context);
	}
	abstract protected void _handleActionSubmit(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionAddRow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddRow(request,response,context);
	}
	abstract protected void _handleActionAddRow(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionDeleteRow(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDeleteRow(request,response,context);
	}
	abstract protected void _handleActionDeleteRow(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionPreVersion(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPreVersion(request,response,context);
	}
	abstract protected void _handleActionPreVersion(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionNextVersion(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionNextVersion(request,response,context);
	}
	abstract protected void _handleActionNextVersion(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionRecense(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRecense(request,response,context);
	}
	abstract protected void _handleActionRecense(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionExpression(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExpression(request,response,context);
	}
	abstract protected void _handleActionExpression(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionFirstVersion(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionFirstVersion(request,response,context);
	}
	abstract protected void _handleActionFirstVersion(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionLastVersion(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionLastVersion(request,response,context);
	}
	abstract protected void _handleActionLastVersion(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAudit(request,response,context);
	}
	abstract protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnAudit(request,response,context);
	}
	abstract protected void _handleActionUnAudit(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionVersionInfo(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionVersionInfo(request,response,context);
	}
	abstract protected void _handleActionVersionInfo(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionApportion(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionApportion(request,response,context);
	}
	abstract protected void _handleActionApportion(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionRevert(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRevert(request,response,context);
	}
	abstract protected void _handleActionRevert(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionAmountUnit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAmountUnit(request,response,context);
	}
	abstract protected void _handleActionAmountUnit(RequestContext request,ResponseContext response, Context context)
		throws Exception;
}