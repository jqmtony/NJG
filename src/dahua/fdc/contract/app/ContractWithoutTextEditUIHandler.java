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
		
		// 初始化列表
		String sSql = "select * where IsEnabled=1";			
		PaymentTypeCollection paymentTypes = PaymentTypeFactory.getLocalInstance(context).getPaymentTypeCollection(sSql);

		response.put("ContractWithoutTextEditUIHandler.paymentTypes",paymentTypes);
		
		//查找合同类型
		String contractSql = "select * where IsEnabled = 1" ;
		ContractTypeCollection contractType = ContractTypeFactory.getLocalInstance(context).getContractTypeCollection(contractSql) ;
		
		response.put("ContractWithoutTextEditUIHandler.contractType",contractType);
	}
	
	/**
	 * 描述：重构：从_handleInit()中抽取代码，作为新方法，可供子类。<p>
	 * 注意：该方法调用的三个方法都是重量级的方法，已经开始影响性能了，PayRequestBillEditUIHandler已经重写该方法
	 * @Author：owen_wen
	 * @CreateTime：2013-2-21
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
//		// 初始化列表
//		String sSql = "select * where IsEnabled=1";			
//		PaymentTypeCollection paymentTypes = PaymentTypeFactory.getLocalInstance(context).getPaymentTypeCollection(sSql);
//
//		response.put("ContractWithoutTextEditUIHandler.paymentTypes",paymentTypes);
//		
//		//查找合同类型
//		String contractSql = "select * where IsEnabled = 1" ;
//		ContractTypeCollection contractType = ContractTypeFactory.getLocalInstance(context).getContractTypeCollection(contractSql) ;
//		
//		response.put("ContractWithoutTextEditUIHandler.contractType",contractType);
//			
//	}
	
	
	/**
	 * 获取数据后转换request的业务组织,coreBillEditUIHandler中进行组织转换
	 * @param request
	 * @param response
	 * @param context
	 * @throws Exception
	 */
	protected void switchRequestOrg(RequestContext request,ResponseContext response, Context context) throws Exception
	{
	   	
	}
	/**
	 * 由业务实现新增数据方法的相应业务逻辑
	 * @param request
	 * @param response
	 * @param context
	 */
	protected IObjectValue createNewData(RequestContext request,ResponseContext response, Context context) throws Exception
	{
		return null;
	}
	/**
	 * 设置默认值
	 * @param vo
	 * @throws Exception
	 */
	protected void applyDefaultValue(IObjectValue vo) throws Exception
	{
		
	}
	/**
	 * 在服务端实现客户端的rpc
	 * @param request
	 * @param response
	 * @param context
	 * @throws Exception
	 */
	protected void loadFields(RequestContext request,ResponseContext response, Context context) throws Exception
	{
		
	}
	/**
	 * 数据校验
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
		
		//校验必录项
		String msg =null;
		if(StringUtils.isEmpty(editData.getNumber())){
			msg = "编码不能为空";			
		}
		
		if(msg==null && StringUtils.isEmpty(editData.getName())){
			msg = "名称不能为空";
			
		}
		
		if(msg==null && editData.getCurProject()==null){
			msg = "项目不能为空";
			
		}		
		
		PayRequestBillInfo prbi =  (PayRequestBillInfo)editData.get("PayRequestBillInfo");
		if(msg==null && prbi.getPaymentType()==null){
			msg = "付款类型不能为空";
			
		}
		
		if(msg==null && prbi.getUseDepartment()==null){
			msg = "用款部门不能为空";
			
		}
		
		if(msg==null && editData.getOriginalAmount()==null){
			msg = "金额不能为空";
			
		}
		
		if(msg==null && prbi.getSupplier()==null){
			msg = "收款单位不能为空";
			
		}
		
		if(msg==null && prbi.getPayDate()==null){
			msg = "付款日期不能为空";
			
		}
		
		
		if(msg==null && prbi.getCurrency()==null){
			msg = "币别不能为空";
			
		}		
		
		if(msg!=null){
			throw new ContractException(ContractException.WITHMSG,new Object[]{msg});
		}
	}
	/**
	 * 保存数据时更给数据对象
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