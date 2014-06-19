/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.app;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;
import com.kingdee.eas.port.pm.contract.ContractBillFactory;
import com.kingdee.eas.port.pm.contract.IContractBill;
import com.kingdee.eas.util.app.ContextUtil;


/**
 * output class name
 */
public class FDCBillListUIHandler extends AbstractFDCBillListUIHandler
{
	protected void _handleInit(RequestContext request,ResponseContext response, Context context) throws Exception {
		
		super._handleInit(request,response,context);
		
		//准备初始数据
		fetchInitData(request,response, context);		
		fetchInitParam(request,response, context);
		fetchBaseData(request,response, context);	
	}
	
	//准备初始数据
	protected  void fetchInitData(RequestContext request,ResponseContext response, Context context) throws Exception{
		//有权限组织
		Set authorizedOrgs = new HashSet();
		Map orgs = PermissionFactory.getLocalInstance(context).getAuthorizedOrgs(
				 new ObjectUuidPK(ContextUtil.getCurrentUserInfo(context).getId()),
		            OrgType.CostCenter,  null,  null, null);
		if(orgs!=null){
			Set orgSet = orgs.keySet();
			Iterator it = orgSet.iterator();
			while(it.hasNext()){
				authorizedOrgs.add(it.next());
			}
		}
		response.put("FDCBillListUIHandler.authorizedOrgs",authorizedOrgs);
		
		Map param = new HashMap();
		Map initData = ((IContractBill)ContractBillFactory.getLocalInstance(context)).fetchInitData(param);
		response.put("FDCBillListUIHandler.initData",initData);
	}
	
	protected  void fetchInitParam(RequestContext request,ResponseContext response, Context context) throws Exception{
		
		HashMap paramItem = FDCUtils.getDefaultFDCParam(context,ContextUtil.getCurrentFIUnit(context).getId().toString());		
		response.put("FDCBillListUIHandler.paramItem",paramItem);
	}
	
	protected  void fetchBaseData(RequestContext request,ResponseContext response, Context context) throws Exception{
		CompanyOrgUnitInfo com = ContextUtil.getCurrentFIUnit(context);
		String exTable = com.getBaseExchangeTable().getId().toString();
		String localCurId = com.getBaseCurrency().getId().toString();
//		Map	mapPrecOfExrate = ContractFacadeFactory.getLocalInstance(context).getExRatePre(exTable,localCurId);
			
//		response.put("FDCBillListUIHandler.mapPrecOfExrate",mapPrecOfExrate);
	}
}