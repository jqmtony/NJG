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
	 * �������ع�����_handleInit()�г�ȡ���룬��Ϊ�·������ɹ����ࡣ<p>
	 * ע�⣺�÷������õ��������������������ķ������Ѿ���ʼӰ�������ˣ�PayRequestBillEditUIHandler�Ѿ���д�÷���
	 * @Author��owen_wen
	 * @CreateTime��2013-2-21
	 */
	protected void fetchFDCInitData(RequestContext request, ResponseContext response, Context context) throws Exception {
		
	}
}