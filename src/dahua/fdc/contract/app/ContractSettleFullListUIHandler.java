/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.app;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public class ContractSettleFullListUIHandler extends AbstractContractSettleFullListUIHandler
{
	protected void _handleInit(RequestContext request,ResponseContext response, Context context) throws Exception {
		super._handleInit(request,response,context);
	}
	/**
	 * 描述：重构：从_handleInit()中抽取代码，作为新方法，可供子类。<p>
	 * 注意：该方法调用的三个方法都是重量级的方法，已经开始影响性能了，PayRequestBillEditUIHandler已经重写该方法
	 * @Author：owen_wen
	 * @CreateTime：2013-2-21
	 */
	protected void fetchFDCInitData(RequestContext request, ResponseContext response, Context context) throws Exception {
		
	}
}