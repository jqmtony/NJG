/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.app;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.fdc.basedata.FDCBillWFFacadeFactory;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractContentCollection;
import com.kingdee.eas.fdc.contract.ContractContentFactory;
import com.kingdee.eas.fdc.contract.IContractBill;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;
import com.kingdee.jdbc.rowset.IRowSet;


/**
 * output class name
 */
public class ContractBillListUIHandler extends AbstractContractBillListUIHandler
{
	protected void _handleInit(RequestContext request,ResponseContext response, Context context) throws Exception {
		super._handleInit(request,response,context);
		
	}
	/**
	 * 描述：重构：从_handleInit()中抽取代码，作为新方法，可供子类。<p>
	 * 注意：该方法调用的三个方法都是重量级的方法，已经开始影响性能了，PayRequestBillEditUIHandler已经重写该方法
	 * @Author：haiou_li
	 * @CreateTime：2014-09-20
	 */
	protected void fetchFDCInitData(RequestContext request, ResponseContext response, Context context) throws Exception {
		fetchInitData(request,response, context);		
		//fetchInitParam(request,response, context);
		//fetchBaseData(request, response, context);		
	}
	protected void _handleActionProjectAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
	
	}
	
	protected void _handleActionViewContent(RequestContext request,ResponseContext response, Context context) throws Exception {
	
	}
	
	protected void _handleActionAddContent(RequestContext request,ResponseContext response, Context context) throws Exception {
	
	}
	
	protected void _handleActionStore(RequestContext request,ResponseContext response, Context context) throws Exception {
	
	}
	
	protected void _handleActionConMove(RequestContext request,ResponseContext response, Context context) throws Exception {
	
	}
	
	protected void _handleActionViewContract(RequestContext request,ResponseContext response, Context context) throws Exception {
	
	}
	
	protected void _handleActionAntiStore(RequestContext request,ResponseContext response, Context context) throws Exception {
	
	}
	
	protected  void fetchInitData(RequestContext request,ResponseContext response, Context context) throws Exception{
		super.fetchInitData(request,response, context);	

	}
	
	protected void _afterOnRowSet(RequestContext request,ResponseContext response, Context context) throws Exception {

		super._afterOnRowSet(request,response,context);
		
    	Map contentMap = new HashMap();
    	Map attachMap = new HashMap();
    	Map auditMap = new HashMap();
		Set contractIds =(Set)request.get("ContractListBaseUIHandler.contractIds");


		/**
		 * 填充正文列,从列表中获取合同的ID（表中的ID就是合同本身的ID）
		 * 然后通过设置过滤信息后使用远程调用，查看合同正文附件集合中是否有合同的ID.
		 * 如果该合同有正文附件就进行标记，否则不标记
		 */
		if(contractIds.size()>0){
			EntityViewInfo viewContent = new EntityViewInfo();
			viewContent.getSelector().add("contract.id");
			FilterInfo filterContent = new FilterInfo();
			filterContent.getFilterItems().add(new FilterItemInfo("contract.id", contractIds, CompareType.INCLUDE));
			viewContent.setFilter(filterContent);
			ContractContentCollection colContent = ContractContentFactory.getLocalInstance(context).getContractContentCollection(viewContent);				
			for(int j = 0; j < colContent.size(); ++j){
				contentMap.put(colContent.get(j).getContract().getId().toString(),Boolean.TRUE);
			}						
		}
		response.put("ContractBillListUIHandler.contentMap",contentMap);
		
		/**
		 * 填充附件列,从列表中获取合同的ID（表中的ID就是合同本身的ID）
		 * 然后通过设置过滤信息后使用远程调用，查看合同正文附件集合中是否有合同的ID.
		 * 如果该合同有业务相关附件就进行标记，否则不标记
		 */
		if(contractIds.size()>0){
			EntityViewInfo viewAttachment = new EntityViewInfo();
			viewAttachment.getSelector().add("boID");
			FilterInfo filterAttachment = new FilterInfo();
			filterAttachment.getFilterItems().add(new FilterItemInfo("boID", contractIds, CompareType.INCLUDE));
			viewAttachment.setFilter(filterAttachment);
			BoAttchAssoCollection colAttachment = BoAttchAssoFactory.getLocalInstance(context).getBoAttchAssoCollection(viewAttachment);
				
			for(int j = 0; j < colAttachment.size(); ++j){
				attachMap.put(colAttachment.get(j).getBoID().toString(),Boolean.TRUE);
			}			
		}
		response.put("ContractBillListUIHandler.attachMap",attachMap);
		auditMap = FDCBillWFFacadeFactory.getLocalInstance(context).getWFBillLastAuditorAndTime(contractIds);
		response.put("ContractBillListUIHandler.auditMap",auditMap);
	}
	
    public void onGetRowSet(RequestContext request,ResponseContext response, Context context) throws Exception {
    
    	Map contentMap = new HashMap();
    	Map attachMap = new HashMap();
    	
    	IRowSet rowSet = request.getRowSet();
    	rowSet.beforeFirst();

		Set contractIds = new HashSet() ;
		while (rowSet.next()) {
			String id  = rowSet.getString("id");		//ContractClientUtils.getUpdateAmtByAmtWithoutCost(rowSet);
			if(id!=null){
				contractIds.add(id);
			}
		}			

		/**
		 * 填充正文列,从列表中获取合同的ID（表中的ID就是合同本身的ID）
		 * 然后通过设置过滤信息后使用远程调用，查看合同正文附件集合中是否有合同的ID.
		 * 如果该合同有正文附件就进行标记，否则不标记
		 */
		if(contractIds.size()>0){
			EntityViewInfo viewContent = new EntityViewInfo();
			FilterInfo filterContent = new FilterInfo();
			filterContent.getFilterItems().add(new FilterItemInfo("contract.id", contractIds, CompareType.INCLUDE));
			viewContent.setFilter(filterContent);
			ContractContentCollection colContent = ContractContentFactory.getLocalInstance(context).getContractContentCollection(viewContent);				
			for(int j = 0; j < colContent.size(); ++j){
				contentMap.put(colContent.get(j).getContract().getId().toString(),Boolean.TRUE);
			}						
		}
		response.put("ContractBillListUIHandler.contentMap",contentMap);
		
		/**
		 * 填充附件列,从列表中获取合同的ID（表中的ID就是合同本身的ID）
		 * 然后通过设置过滤信息后使用远程调用，查看合同正文附件集合中是否有合同的ID.
		 * 如果该合同有业务相关附件就进行标记，否则不标记
		 */
		if(contractIds.size()>0){
			EntityViewInfo viewAttachment = new EntityViewInfo();
			FilterInfo filterAttachment = new FilterInfo();
			filterAttachment.getFilterItems().add(new FilterItemInfo("boID", contractIds, CompareType.INCLUDE));
			viewAttachment.setFilter(filterAttachment);
			BoAttchAssoCollection colAttachment = BoAttchAssoFactory.getLocalInstance(context).getBoAttchAssoCollection(viewAttachment);
				
			for(int j = 0; j < colAttachment.size(); ++j){
				attachMap.put(colAttachment.get(j).getBoID().toString(),Boolean.TRUE);
			}			
		}
		response.put("ContractBillListUIHandler.attachMap",attachMap);
		//具体填充在afterDataFill

		rowSet.beforeFirst();		
		
		super.onGetRowSet(request, response,  context);
    }
}