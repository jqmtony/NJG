package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ContextUtils;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.rpc.RPCException;
import com.kingdee.eas.fdc.aimcost.AimCostSplitDataGetter;
import com.kingdee.eas.util.app.ContextUtil;

/**
 * 此类主要关注 execAction 方法，采用回调方式在服务器端执行操作.
 * */
public class FDCCommonServerHelper implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3884309913591667766L;
	private static Logger log = Logger.getLogger("com.kingdee.eas.fdc.basedata.FDCCommonServerHelper");
	private FDCCommonServerHelper(){}
    public static Timestamp getServerTimeStamp() throws BOSException{
    	Map map=new HashMap();
    	map.put("action", new IFDCAction(){
    		/**
			 * 
			 */
			private static final long serialVersionUID = 2199134905565923418L;

			public Object actionPerformed(Context ctx) {
    			return new Timestamp(System.currentTimeMillis());
    		}
    	});
    	Timestamp timestamp = (Timestamp)execAction(map);
    	if(timestamp==null) {
    		throw new NullPointerException();
		}
    	return timestamp;
    }
    
    public static Date getServerTime() throws BOSException{
    	Map map=new HashMap();
    	map.put("action", new IFDCAction(){
    		/**
			 * 
			 */
			private static final long serialVersionUID = 2199134905565923418L;

			public Object actionPerformed(Context ctx) {
    			return new Date();
    		}
    	});
    	Date date = (Date)execAction(map);
    	if(date==null)
    	{
    		 new Date();
		}
    	return date;
    }
    
    
    public static Map getCostSplitApptProjData(Map params) throws BOSException{
    	final CostAccountInfo costAccount=(CostAccountInfo)params.get("costAccount");
    	final String sql=(String)params.get("sql");
    	if(costAccount==null){
    		return new HashMap();
    	}
    	Map map=new HashMap();
    	map.put("action", new IFDCAction(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 5049828591636807024L;

			public Object actionPerformed(Context ctx) throws Exception {
				if(sql!=null){
					FDCSQLBuilder builder=new FDCSQLBuilder(ctx,sql);
					builder.execute();
				}
				Map dataMap=new HashMap();
				//-----------------------------------------------------------------
				//数据列：分摊类型
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
		    	filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
				filter.getFilterItems().add(new FilterItemInfo("forCostApportion", Boolean.TRUE));
				filter.getFilterItems().add(new FilterItemInfo("targetType.isEnabled", Boolean.TRUE));
		    	//不能选择指定分摊
		    	
				// modified by zhaoqin for R131101-0367 on 2013/11/05 start
				//filter.getFilterItems().add(new FilterItemInfo("id",ApportionTypeInfo.appointType,CompareType.NOTEQUALS));
				// modified by zhaoqin for R131101-0367 on 2013/11/05 end
				
				filter.mergeFilter(ApportionTypeInfo.getCUFilter(ContextUtil.getCurrentCtrlUnit(ctx)), "and");
		    	view.setFilter(filter);
		    	view.getSelector().add("id");
		    	view.getSelector().add("name");
		    	view.getSorter().add(new SorterItemInfo("number"));

				ApportionTypeCollection collAppt = ApportionTypeFactory.getLocalInstance(ctx).getApportionTypeCollection(view);
				dataMap.put("collAppt", collAppt);
				
				view = new EntityViewInfo();
				filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("longNumber", 
		    			costAccount.getCurProject().getLongNumber().replace('.','!')+"!%", CompareType.LIKE));
		    	filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		    	view.setFilter(filter);
		    	view.getSelector().add("id");
		    	view.getSelector().add("name");
		    	view.getSelector().add("longNumber");
		    	view.getSelector().add("parent");
		    	//view.getSelector().add("curProjCostEntries.apportionType.id");
		    	//view.getSelector().add("curProjCostEntries.value");
		    	 	
		    	
		    	view.getSorter().add(new SorterItemInfo("longNumber"));
		    	view.getSorter().add(new SorterItemInfo("sortNo"));
		    	
				CurProjectCollection curProjs = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(view);
				//apptObjIds=new String[apptObjs.size()];
				dataMap.put("curProjs", curProjs);
				if(curProjs==null||curProjs.size()<=0){
					return dataMap; 
				}
				
				Set prjSet=new HashSet();
				for(int i=0;i<curProjs.size();i++){
					prjSet.add(curProjs.get(i).getId().toString());
				}
				//成本科目
				view = new EntityViewInfo();
				filter = new FilterInfo();
		    	filter.getFilterItems().add(new FilterItemInfo("curProject.id",prjSet, CompareType.INCLUDE));
		    	filter.getFilterItems().add(new FilterItemInfo("longNumber", 
		    			costAccount.getLongNumber().replace('.','!')+"%", CompareType.LIKE));
		    	filter.getFilterItems().add(new FilterItemInfo("curProject.isEnabled", Boolean.TRUE));
		    	filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		    	view.setFilter(filter);
		    	
		    	view.getSelector().add("id");
		    	view.getSelector().add("name");
		    	view.getSelector().add("longNumber");
		    	view.getSelector().add("displayName");
		    	view.getSelector().add("level");
		    	view.getSelector().add("isLeaf");
		    	view.getSelector().add("parent");
		    	
		    	view.getSelector().add("curProject.id");
		    	view.getSelector().add("curProject.name");
		    	view.getSelector().add("curProject.longNumber");
		    	view.getSelector().add("curProject.displayName");
		    	view.getSelector().add("curProject.level");
		    	view.getSelector().add("curProject.isLeaf");
		    	view.getSelector().add("curProject.parent");

		    	view.getSorter().add(new SorterItemInfo("curProject.id"));
		    	view.getSorter().add(new SorterItemInfo("longNumber"));
		    	
		    	CostAccountCollection costAccts = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);    	
		    	dataMap.put("costAccts", costAccts);

		    	//取指标
		    	final Map curProjCostEntries = ProjectHelper.getCurProjCostEntries(prjSet);
		    	dataMap.put("curProjCostEntries", curProjCostEntries);
				
				return dataMap;
    		}
    	});
    	Map dataMap = (Map)execAction(map);
    	if(dataMap==null) {
    		dataMap=new HashMap();
    	}
    	return dataMap;
    }
    
    
    public static Map getCostSplitApptProdData(Map params) throws BOSException{
    	final CostAccountInfo costAccount=(CostAccountInfo)params.get("costAccount");
    	final String sql=(String)params.get("sql");
    	if(costAccount==null){
    		return new HashMap();
    	}
    	Map map=new HashMap();
    	map.put("action", new IFDCAction(){
			/**
			 * 
			 */
			private static final long serialVersionUID = -8489223153360529503L;

			public Object actionPerformed(Context ctx) throws Exception {
				if(sql!=null){
					FDCSQLBuilder builder=new FDCSQLBuilder(ctx,sql);
					builder.execute();
				}
				Map dataMap=new HashMap();
				//-----------------------------------------------------------------
				//数据列：分摊类型
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
		    	filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		    	filter.getFilterItems().add(new FilterItemInfo("forCostApportion", Boolean.TRUE));
		    	//不能选择指定分摊
		    	filter.getFilterItems().add(new FilterItemInfo("id",ApportionTypeInfo.appointType,CompareType.NOTEQUALS));
		    	filter.mergeFilter(ApportionTypeInfo.getCUFilter(ContextUtil.getCurrentCtrlUnit(ctx)), "and");
		    	view.setFilter(filter);
		    	view.getSelector().add("id");
		    	view.getSelector().add("name");
		    	view.getSorter().add(new SorterItemInfo("number"));

				ApportionTypeCollection collAppt = ApportionTypeFactory.getLocalInstance(ctx).getApportionTypeCollection(view);
				dataMap.put("collAppt", collAppt);
				
				view = new EntityViewInfo();
				filter = new FilterInfo();
		    	filter.getFilterItems().add(new FilterItemInfo("curProject.id", costAccount.getCurProject().getId().toString()));
		    	filter.getFilterItems().add(new FilterItemInfo("productType.isEnabled", Boolean.TRUE));
		    	filter.getFilterItems().add(new FilterItemInfo("isAccObj", Boolean.TRUE));		//是否核算对象
		    	view.setFilter(filter);
		    	//产品类型
		    	view.getSelector().add("productType.id");
		    	view.getSelector().add("productType.name");
		    	//分摊数据
		    	view.getSelector().add("curProjProEntrApporData.apportionType.id");
		    	view.getSelector().add("curProjProEntrApporData.value");
		    	  	
		    	
				ProjProductEntriesCollection collObj = CurProjProductEntriesFactory.getLocalInstance(ctx).getProjProductEntriesCollection(view);
				dataMap.put("ProjProductEntriesCollection", collObj);
				if(collObj==null||collObj.size()<=0){
					return dataMap; 
				}
				dataMap.put("projProdAppt", ProjectHelper.getCurProjProEntrApporData(costAccount.getCurProject().getId().toString(), ProjectStageEnum.DYNCOST));
				AimCostSplitDataGetter aimCost=new AimCostSplitDataGetter(ctx,costAccount.getCurProject().getId().toString());
				aimCost.initProductSplitData();
				dataMap.put("aimCost", aimCost);
				return dataMap;
    		}
    	});
    	Map dataMap = (Map)execAction(map);
    	if(dataMap==null) {
    		dataMap=new HashMap();
    	}
    	return dataMap;
    }
    
    /**
     * 在服务器端执行函数
     * 注意此调用事务为Supports
     * */
    public static Object execAction(Map params) throws BOSException{
    	if(params==null){
    		return null;
    	}
		try {
			return FDCBillFacadeFactory.getRemoteInstance().execAction(params);
		}catch (RPCException ex){ //如果是在服务端调用，则要抛此异常，捕获异常并调用服务器端的方法
			// @AbortException
			log.error(ex.getMessage(), ex);
			Context context = ContextUtils.getContextFromSession();
			try {
				return FDCBillFacadeFactory.getLocalInstance(context).execAction(params);
			} catch (BOSException e) {
				throw new RuntimeException(e);
			}
		} catch (BOSException e) {
			throw new RuntimeException(e);
		}
    }
    
    public static Object execAction(IFDCAction action)throws BOSException{
    	Map map=new HashMap();
    	map.put("action", action);
    	return execAction(map);
    }
    
}
