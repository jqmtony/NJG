/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.app;

import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.assistant.CurrencyCollection;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.ICurrency;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.fdc.basedata.ContractCodingTypeCollection;
import com.kingdee.eas.fdc.basedata.ContractCodingTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractSecondTypeEnum;
import com.kingdee.eas.fdc.basedata.ContractThirdTypeEnum;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fi.gl.GlWebServiceUtil;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;
import com.kingdee.eas.util.app.ContextUtil;


/**
 * output class name
 */
public class FDCBillEditUIHandler extends AbstractFDCBillEditUIHandler
{
	protected void _handleInit(RequestContext request,ResponseContext response, Context context) throws Exception {
		super._handleInit(request,response,context);
		fetchFDCInitData(request, response, context);
	}

	/**
	 * 描述：重构：从_handleInit()中抽取代码，作为新方法，可供子类。<p>
	 * 注意：该方法调用的三个方法都是重量级的方法，已经开始影响性能了，PayRequestBillEditUIHandler已经重写该方法
	 * @Author：owen_wen
	 * @CreateTime：2013-2-21
	 */
	protected void fetchFDCInitData(RequestContext request, ResponseContext response, Context context) throws Exception {
		fetchInitData(request,response, context);		
		fetchInitParam(request,response, context);
		fetchBaseData(request, response, context);		
	}
	
	protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	
	}
	
	protected void _handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	
	}	
	
	//准备初始数据
	protected  void fetchInitData(RequestContext request,ResponseContext response, Context context) throws Exception{
		
		//合同Id
		String contractBillId = (String) request.get("FDCBillEditUIHandler.contractBillId");
		String ID = null;
		if(contractBillId==null){
			Object object  = request.get("FDCBillEditUIHandler.ID");
			if(object instanceof String){
				ID = (String)object;
			}else if(object!=null){
				ID = object.toString();
			}
		}
		//工程项目Id
		BOSUuid projectId = ((BOSUuid) request.get("FDCBillEditUIHandler.projectId"));
		
		Map param = new HashMap();
		param.put("Fw_ID", request.get("Fw_ID")==null?null:String.valueOf((Object)request.get("Fw_ID")));
		param.put("contractBillId",contractBillId);
		if(ID!=null){
			param.put("ID",ID);
		}
		if(projectId!=null){
			param.put("projectId",projectId.toString());
		}
		Map initData = ((IFDCBill)getBizInterface(request,context)).fetchInitData(param);

		//返回
		response.put("FDCBillEditUIHandler.initData",initData);
		
		
		BOSUuid typeId = ((BOSUuid) request.get("FDCBillEditUIHandler.typeId"));
		if(typeId!=null){
			ContractTypeInfo contractTypeInfo = null;
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("id");
			selector.add("number");
			selector.add("name");
			selector.add("longNumber");
			selector.add("isLeaf");
			selector.add("dutyOrgUnit.id");
			selector.add("dutyOrgUnit.number");
			selector.add("dutyOrgUnit.name");
			selector.add("payScale");
			selector.add("stampTaxRate");
			selector.add("isCost");
			contractTypeInfo = ContractTypeFactory.getLocalInstance(context)
						.getContractTypeInfo(new ObjectUuidPK(typeId), selector);
			response.put("FDCBillEditUIHandler.contractTypeInfo",contractTypeInfo);
			
			//设置编码类型
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			ContractCodingTypeCollection cctc = null;
			filter.getFilterItems().add(new FilterItemInfo("contractType.id", contractTypeInfo.getId().toString()));
			//新建的话，所有合同
			filter.getFilterItems().add(new FilterItemInfo("secondType", ContractSecondTypeEnum.OLD_VALUE));
			//新增状态
			filter.getFilterItems().add(new FilterItemInfo("thirdType", ContractThirdTypeEnum.NEW_VALUE));
			evi.setFilter(filter);
			cctc = ContractCodingTypeFactory.getLocalInstance(context).getContractCodingTypeCollection(evi);
			response.put("FDCBillEditUIHandler.cctc",cctc);
		}else{
			//设置编码类型
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			ContractCodingTypeCollection cctc = null;
			filter.getFilterItems().add(new FilterItemInfo("contractType.id", null));
			//新建的话，所有合同
			filter.getFilterItems().add(new FilterItemInfo("secondType", ContractSecondTypeEnum.OLD_VALUE));
			//新增状态
			filter.getFilterItems().add(new FilterItemInfo("thirdType", ContractThirdTypeEnum.NEW_VALUE));

			evi.setFilter(filter);
			cctc = ContractCodingTypeFactory.getLocalInstance(context).getContractCodingTypeCollection(evi);
			response.put("FDCBillEditUIHandler.cctc",cctc);
		}
	}
	
	protected  void fetchInitParam(RequestContext request,ResponseContext response, Context context) throws Exception{
		Map initData = (Map)response.get("FDCBillEditUIHandler.initData");
		
		CompanyOrgUnitInfo company = (CompanyOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_COMPANY);
		HashMap comParamItem = FDCUtils.getDefaultFDCParam(context,company.getId().toString());
		response.put("FDCBillEditUIHandler.comParamItem",comParamItem);
		
		FullOrgUnitInfo orgUnitInfo = (FullOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_ORGUNIT);
		HashMap orgParamItem = FDCUtils.getDefaultFDCParam(context,orgUnitInfo.getId().toString());
		response.put("FDCBillEditUIHandler.orgParamItem",orgParamItem);
	}
	
	protected  void fetchBaseData(RequestContext request,ResponseContext response, Context context) throws Exception{
		// 初始化币别列表
		ICurrency iCurrency = CurrencyFactory.getLocalInstance(context);
		CurrencyCollection currencyCollection = iCurrency.getCurrencyCollection(true);
		
		response.put("FDCBillEditUIHandler.currencyCollection",currencyCollection);
		
		//用户有权的行政组织
		Map orgs = PermissionFactory.getLocalInstance(context).getAuthorizedOrgs(
				 new ObjectUuidPK(ContextUtil.getCurrentUserInfo(context).getId()),
		            OrgType.Admin,  null,  null, null);
		response.put("FDCBillEditUIHandler.authorizedOrgs",orgs);
		
		//供应商过滤条件
		Map initData = (Map)response.get("FDCBillEditUIHandler.initData");
		CurProjectInfo curProject = (CurProjectInfo) initData.get(FDCConstants.FDC_INIT_PROJECT);
		
		FilterInfo dfilter = GlWebServiceUtil.getDFilterInfo(SupplierFactory
				.getLocalInstance(context), curProject.getCU().getId().toString());
		
		response.put("FDCBillEditUIHandler.dfilter",dfilter);
		
		
	}

	protected void _handleActionAttamentCtrl(RequestContext request, ResponseContext response, Context context) throws Exception {
		// TODO 自动生成方法存根
		
	}
}