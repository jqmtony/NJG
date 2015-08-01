/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.app;

import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.assistant.CurrencyCollection;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.ICurrency;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.fdc.basedata.ContractTypeCollection;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.PaymentTypeCollection;
import com.kingdee.eas.fdc.basedata.PaymentTypeFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fi.gl.GlWebServiceUtil;
import com.kingdee.eas.framework.batchHandler.RequestConstant;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.StringUtils;


/**
 * output class name
 */
public class ContractWithoutTextEditUIHandler extends AbstractContractWithoutTextEditUIHandler
{
	protected void _handleInit(RequestContext request,ResponseContext response, Context context) throws Exception {
		super._handleInit(request,response,context);
		
		// ��ʼ���б�
		String sSql = "select * where IsEnabled=1";			
		PaymentTypeCollection paymentTypes = PaymentTypeFactory.getLocalInstance(context).getPaymentTypeCollection(sSql);

		response.put("ContractWithoutTextEditUIHandler.paymentTypes",paymentTypes);
		
		//���Һ�ͬ����
		String contractSql = "select * where IsEnabled = 1" ;
		ContractTypeCollection contractType = ContractTypeFactory.getLocalInstance(context).getContractTypeCollection(contractSql) ;
		
		response.put("ContractWithoutTextEditUIHandler.contractType",contractType);
	}
	
	/**
	 * �������ع�����_handleInit()�г�ȡ���룬��Ϊ�·������ɹ����ࡣ<p>
	 * ע�⣺�÷������õ��������������������ķ������Ѿ���ʼӰ�������ˣ�PayRequestBillEditUIHandler�Ѿ���д�÷���
	 * @Author��owen_wen
	 * @CreateTime��2013-2-21
	 */
	protected void fetchFDCInitData(RequestContext request, ResponseContext response, Context context) throws Exception {
		fetchInitData(request,response, context);		
		//fetchInitParam(request,response, context);
		//fetchBaseData(request, response, context);		
	}
	
//	protected  void fetchBaseData(RequestContext request,ResponseContext response, Context context) throws Exception{
//		
//		super.fetchBaseData(request,response, context);	
//		
//		// ��ʼ���б�
//		String sSql = "select * where IsEnabled=1";			
//		PaymentTypeCollection paymentTypes = PaymentTypeFactory.getLocalInstance(context).getPaymentTypeCollection(sSql);
//
//		response.put("ContractWithoutTextEditUIHandler.paymentTypes",paymentTypes);
//		
//		//���Һ�ͬ����
//		String contractSql = "select * where IsEnabled = 1" ;
//		ContractTypeCollection contractType = ContractTypeFactory.getLocalInstance(context).getContractTypeCollection(contractSql) ;
//		
//		response.put("ContractWithoutTextEditUIHandler.contractType",contractType);
//			
//	}
	
	
	/**
	 * ��ȡ���ݺ�ת��request��ҵ����֯,coreBillEditUIHandler�н�����֯ת��
	 * @param request
	 * @param response
	 * @param context
	 * @throws Exception
	 */
	protected void switchRequestOrg(RequestContext request,ResponseContext response, Context context) throws Exception
	{
	   	
	}
	/**
	 * ��ҵ��ʵ���������ݷ�������Ӧҵ���߼�
	 * @param request
	 * @param response
	 * @param context
	 */
	protected IObjectValue createNewData(RequestContext request,ResponseContext response, Context context) throws Exception
	{
		return null;
	}
	/**
	 * ����Ĭ��ֵ
	 * @param vo
	 * @throws Exception
	 */
	protected void applyDefaultValue(IObjectValue vo) throws Exception
	{
		
	}
	/**
	 * �ڷ����ʵ�ֿͻ��˵�rpc
	 * @param request
	 * @param response
	 * @param context
	 * @throws Exception
	 */
	protected void loadFields(RequestContext request,ResponseContext response, Context context) throws Exception
	{
		
	}
	/**
	 * ����У��
	 * @param request
	 * @param response
	 * @param context
	 * @throws Exception
	 */
	protected void verifyInput(RequestContext request,ResponseContext response, Context context) throws Exception
	{
		super.verifyInput(request,response,context);
		
		ContractWithoutTextInfo editData =  (ContractWithoutTextInfo)request.get(RequestConstant.FRAMEWORK_VALUE_KEY);
		super.verifyInput(request,response,context);
		
		//У���¼��
		String msg =null;
		if(StringUtils.isEmpty(editData.getNumber())){
			msg = "���벻��Ϊ��";			
		}
		
		if(msg==null && StringUtils.isEmpty(editData.getName())){
			msg = "���Ʋ���Ϊ��";
			
		}
		
		if(msg==null && editData.getCurProject()==null){
			msg = "��Ŀ����Ϊ��";
			
		}		
		
		PayRequestBillInfo prbi =  (PayRequestBillInfo)editData.get("PayRequestBillInfo");
		if(msg==null && prbi.getPaymentType()==null){
			msg = "�������Ͳ���Ϊ��";
			
		}
		
		if(msg==null && prbi.getUseDepartment()==null){
			msg = "�ÿ�Ų���Ϊ��";
			
		}
		
		if(msg==null && editData.getOriginalAmount()==null){
			msg = "����Ϊ��";
			
		}
		
		if(msg==null && prbi.getSupplier()==null){
			msg = "�տλ����Ϊ��";
			
		}
		
		if(msg==null && prbi.getPayDate()==null){
			msg = "�������ڲ���Ϊ��";
			
		}
		
		
		if(msg==null && prbi.getCurrency()==null){
			msg = "�ұ���Ϊ��";
			
		}		
		
		if(msg!=null){
			throw new ContractException(ContractException.WITHMSG,new Object[]{msg});
		}
	}
	/**
	 * ��������ʱ�������ݶ���
	 * @param request
	 * @param response
	 * @param context
	 * @throws Exception
	 */
	protected void storeFields(RequestContext request,ResponseContext response, Context context) throws Exception
	{
		
	}

	protected void _handleActionViewBgBalance(RequestContext request,
			ResponseContext response, Context context) throws Exception {
		
	}
}