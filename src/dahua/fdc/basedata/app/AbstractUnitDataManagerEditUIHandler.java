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
public abstract class AbstractUnitDataManagerEditUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleActionAddRightLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddRightLine(request,response,context);
	}
	abstract protected void _handleActionAddRightLine(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionDelRightLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDelRightLine(request,response,context);
	}
	abstract protected void _handleActionDelRightLine(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionAddTaxLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddTaxLine(request,response,context);
	}
	abstract protected void _handleActionAddTaxLine(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionDelTaxLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDelTaxLine(request,response,context);
	}
	abstract protected void _handleActionDelTaxLine(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionAddDirectorLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddDirectorLine(request,response,context);
	}
	abstract protected void _handleActionAddDirectorLine(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionDelDirectorLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDelDirectorLine(request,response,context);
	}
	abstract protected void _handleActionDelDirectorLine(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionAddSelfDirectorLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddSelfDirectorLine(request,response,context);
	}
	abstract protected void _handleActionAddSelfDirectorLine(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionDelSelfDirectorLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDelSelfDirectorLine(request,response,context);
	}
	abstract protected void _handleActionDelSelfDirectorLine(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionAddWatcherLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddWatcherLine(request,response,context);
	}
	abstract protected void _handleActionAddWatcherLine(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionDelWatcherLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDelWatcherLine(request,response,context);
	}
	abstract protected void _handleActionDelWatcherLine(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionAddPaymanager(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddPaymanager(request,response,context);
	}
	abstract protected void _handleActionAddPaymanager(RequestContext request,ResponseContext response, Context context)
		throws Exception;
	public void handleActionViewPaymanager(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewPaymanager(request,response,context);
	}
	abstract protected void _handleActionViewPaymanager(RequestContext request,ResponseContext response, Context context)
		throws Exception;
}