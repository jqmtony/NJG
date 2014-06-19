/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.app;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;
import com.kingdee.eas.port.pm.contract.ContractBillFactory;
import com.kingdee.eas.port.pm.contract.IContractBill;
import com.kingdee.jdbc.rowset.IRowSet;


/**
 * output class name
 */
public class ContractListBaseUIHandler extends AbstractContractListBaseUIHandler
{
	protected void _handleInit(RequestContext request,ResponseContext response, Context context) throws Exception {
	

		
//		EntityViewInfo view = new EntityViewInfo();
//		view.setFilter(getTreeSelectFilter(ContextUtil.getCurrentOrgUnit(context),null,false,authorizedOrgs));		
//		view.setTopCount(2000);
//		view.getSorter().remove(new SorterItemInfo("id"));
//		view.getSorter().add(new SorterItemInfo("state"));
//		view.getSorter().add(new SorterItemInfo("number"));
//		view.getSorter().add(new SorterItemInfo("id"));
//		
//		request.setQuery(view);
		
		super._handleInit(request,response,context);
		
		//运行虚模式，并根据虚模式的结果进行结果集处理
		//response= _tableRequestData(request,response,context);
		
		//onGetRowSet(request, response,  context);
	}
	
	protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	
	}
	
	protected void _handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	
	}
	
	public void afterOnRowSet(RequestContext request,ResponseContext response, Context context) throws Exception{
		_afterOnRowSet(request,response,context);
	}
	
	protected void _afterOnRowSet(RequestContext request,ResponseContext response, Context context) throws Exception {

		Map idMap  =(Map)request.get("ContractListBaseUIHandler.idMap");
		Map amountMap  = ContractBillFactory.getLocalInstance(context).getAmtByAmtWithoutCost(idMap);

		response.put("ContractListBaseUIHandler.amountMap",amountMap);
	}
	
    public void onGetRowSet(RequestContext request,ResponseContext response, Context context) throws Exception {
		/*
		 * 选择不计成本的金额为否,金额录在分录上,显示的时候要从分录上取
		 */
    	IRowSet rowSet = request.getRowSet();
		rowSet.beforeFirst();
			
		Map idMap  = new HashMap();
		while (rowSet.next()) {
			String id  = rowSet.getString("id");	//ContractClientUtils.getUpdateAmtByAmtWithoutCost(rowSet);
			if(id!=null){
				idMap.put(id,id);
			}
		}			
		Map amountMap  = ((IContractBill)this.getBizInterface(request,context)).getAmtByAmtWithoutCost(idMap);
			
		rowSet.beforeFirst();
//		while (rowSet.next()) {
//			ContractClientUtils.updateAmtByAmtWithoutCost( rowSet, amountMap);
//		}			
//		
//		rowSet.beforeFirst();	
		response.put("ContractListBaseUIHandler.amountMap",amountMap);
    }
  
	/**
	 * 选择工程项目节点和合同类型节点后的选择事件
	 * @return
	 * @throws Exception
	 */
	protected FilterInfo getTreeSelectFilter(Object projectNode,Object  typeNode,boolean containConWithoutTxt,Set authorizedOrgs) throws Exception {
		FilterInfo filter = getTreeSelectChangeFilter();
		FilterItemCollection filterItems = filter.getFilterItems();
		
		/*
		 * 工程项目树
		 */
		if (projectNode != null 	&& projectNode instanceof CoreBaseInfo) {

			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) projectNode;
			BOSUuid id = null;
			// 选择的是成本中心，取该成本中心及下级成本中心（如果有）下的所有合同
			if ( projTreeNodeInfo instanceof FullOrgUnitInfo) {
				
				if (projTreeNodeInfo instanceof OrgStructureInfo) {
					id = ((OrgStructureInfo)projTreeNodeInfo).getUnit().getId();	
				}else{
					id = ((FullOrgUnitInfo)projTreeNodeInfo).getId();
				}				
				
				FullOrgUnitInfo orgUnit = ((FullOrgUnitInfo)projTreeNodeInfo);
				String orgUnitLongNumber = null;
				if(orgUnit!=null && id.toString().equals(orgUnit.getId().toString())){					
					orgUnitLongNumber = orgUnit.getLongNumber();
				}else{
					FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getRemoteInstance()
					.getFullOrgUnitInfo(new ObjectUuidPK(id));
					orgUnitLongNumber = orgUnitInfo.getLongNumber();
				}
				
				FilterInfo f = new FilterInfo();
				f.getFilterItems().add(
						new FilterItemInfo("orgUnit.longNumber", orgUnitLongNumber + "%",CompareType.LIKE));

				f.getFilterItems().add(new FilterItemInfo("orgUnit.isCostOrgUnit", Boolean.TRUE));
				f.getFilterItems().add(new FilterItemInfo("orgUnit.id", authorizedOrgs,CompareType.INCLUDE));
				
				f.setMaskString("#0 and #1 and #2");
				
				//update by renliang
				/*if(filter!=null){
					filter.mergeFilter(f,"and");
				}*/
				filter.mergeFilter(f,"and");
			}
			// 选择的是项目，取该项目及下级项目（如果有）下的所有合同
			else if (projTreeNodeInfo instanceof CurProjectInfo) {
				id = projTreeNodeInfo.getId();
				Set idSet = FDCClientUtils.genProjectIdSet(id);
				filterItems.add(new FilterItemInfo("curProject.id", idSet,
						CompareType.INCLUDE));
			}

		}

		FilterInfo typefilter =  new FilterInfo();
		FilterItemCollection typefilterItems = typefilter.getFilterItems();	
		/*
		 * 合同类型树
		 */
		if (typeNode != null&& typeNode instanceof TreeBaseInfo) {
			TreeBaseInfo typeTreeNodeInfo = (TreeBaseInfo)typeNode;
			BOSUuid id = typeTreeNodeInfo.getId();
			Set idSet = FDCClientUtils.genContractTypeIdSet(id);
			typefilterItems.add(new FilterItemInfo("contractType.id", idSet,CompareType.INCLUDE));
		}else if(containConWithoutTxt && typeNode != null &&typeNode.equals("allContract")){
			//如果包含无文本合同，查询所有时，让它查不到合同
			typefilterItems.add(new FilterItemInfo("contractType.id", "allContract"));
		}
		
//		//三方合同
//		if(!(this instanceof ContractBillListUI)){
//			typefilter.appendFilterItem("isAmtWithoutCost", String.valueOf(0));
//		}
		
		/**
		 * findbug 发现此处的非空判断是多余的
		 */
		/*if(filter!=null && typefilter!=null){
			filter.mergeFilter(typefilter,"and");
		}*/
		
		//update by renliang
		filter.mergeFilter(typefilter,"and");
		
		return filter;
	}
	
	/**
	 * 
	 * 描述：当左边的树选择变化时的缺省条件（提供默认实现，合同状态为审核，子类可以覆盖，如果没有条件，也要返回一个new FilterInfo()，不能直接返回null）
	 * @return
	 * @author:liupd
	 * 创建时间：2006-9-5 <p>
	 */
	protected FilterInfo getTreeSelectChangeFilter() {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		Set set = getContractBillStateSet();

//		filterItems.add(new FilterItemInfo("state",set,CompareType.INCLUDE));
		filterItems.add(new FilterItemInfo("contractType.isEnabled", Boolean.TRUE));
		filterItems.add(new FilterItemInfo("curProject.isEnabled", Boolean.TRUE));
		
/*		String maskString="(#0 or #1) and #2 and #3";
		filter.setMaskString(maskString);*/
		return filter;
	}
	
	/**
	 * 要显示的合同的状态集合,用于过滤合同
	 * @return
	 */
	protected Set getContractBillStateSet() {
		Set set=new HashSet();
		set.add(FDCBillStateEnum.AUDITTED_VALUE);
		return set;
	}
	
	protected FilterInfo getDefaultFilterForQuery(RequestContext request, Context context) throws Exception
	{
		return null;
	}

	protected void _handleActionCancelRespite(RequestContext request,
			ResponseContext response, Context context) throws Exception {
		// TODO Auto-generated method stub
		
	}

	protected void _handleActionRespite(RequestContext request,
			ResponseContext response, Context context) throws Exception {
		// TODO Auto-generated method stub
		
	}
}