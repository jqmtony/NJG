package com.kingdee.eas.fdc.aimcost.app;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.AIMAimCostProductSplitEntryCollection;
import com.kingdee.eas.fdc.aimcost.AIMAimCostProductSplitEntryFactory;
import com.kingdee.eas.fdc.aimcost.AIMAimCostProductSplitEntryInfo;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.aimcost.AimCostProductSplitEntryCollection;
import com.kingdee.eas.fdc.aimcost.AimCostProductSplitEntryFactory;
import com.kingdee.eas.fdc.aimcost.AimCostProductSplitEntryInfo;
import com.kingdee.eas.fdc.aimcost.CostEntryInfo;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.aimcost.ProjectCostChangeLogFactory;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProjectHelper;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.util.AccountStageHelper;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.util.app.ContextUtil;

public class AimCostControllerBean extends AbstractAimCostControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.aimcost.app.AimCostControllerBean");

	protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		AimCostInfo aimCostInfo = this.getAimCostInfo(ctx,new ObjectUuidPK(billId));
		aimCostInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		aimCostInfo.setAuditDate(new Date());
		aimCostInfo.setState(FDCBillStateEnum.AUDITTED);
		/*********若启用参数FDC_PARAM_AIMCOSTAUDIT，目标成本只有审批之后才是最新版本 -by neo*********/
		if(FDCUtils.getDefaultFDCParamByKey(
				ctx, ContextUtil.getCurrentFIUnit(ctx).getId().toString(), FDCConstants.FDC_PARAM_AIMCOSTAUDIT)){
			aimCostInfo.setIsLastVersion(true);
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			builder.appendSql("update T_AIM_AimCost set fisLastVersion=0 where forgOrProId=? and fisLastVersion=1");
			builder.addParam(aimCostInfo.getOrgOrProId());
			builder.executeUpdate(ctx);
		}
		_update(ctx,new ObjectUuidPK(billId),aimCostInfo);

	}

	protected void _audit(Context ctx, List idList) throws BOSException, EASBizException {
	
	}

	protected void _unaudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		AimCostInfo aimCostInfo = this.getAimCostInfo(ctx,new ObjectUuidPK(billId));
		aimCostInfo.setAuditor(null);
		aimCostInfo.setAuditDate(null);
		aimCostInfo.setState(FDCBillStateEnum.SAVED);
		/*********若启用参数FDC_PARAM_AIMCOSTAUDIT，目标成本反审批之后上一版本是最新版本 -by neo*********/
		if(FDCUtils.getDefaultFDCParamByKey(
				ctx, ContextUtil.getCurrentFIUnit(ctx).getId().toString(), FDCConstants.FDC_PARAM_AIMCOSTAUDIT)){
			aimCostInfo.setIsLastVersion(false);
		}
		_update(ctx,new ObjectUuidPK(billId),aimCostInfo);
		if(FDCUtils.getDefaultFDCParamByKey(
				ctx, ContextUtil.getCurrentFIUnit(ctx).getId().toString(), FDCConstants.FDC_PARAM_AIMCOSTAUDIT)){
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("update t_aim_aimcost set fislastVersion=? where fid=(" +
					"	select fid from t_aim_aimcost where fversionnumber=(" +
					"	select  max(TO_NUMBER(fversionnumber)) from t_aim_aimcost " +
					"	where forgOrProId=? and fstate=? and fid!=?) and forgOrProID=?)");
			builder.addParam(Boolean.TRUE);
			builder.addParam(aimCostInfo.getOrgOrProId());
			builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
			builder.addParam(aimCostInfo.getId().toString());
			builder.addParam(aimCostInfo.getOrgOrProId());
			builder.executeUpdate(ctx);
		}
	}

	protected void _unaudit(Context ctx, List idList) throws BOSException, EASBizException {
		// TODO 自动生成方法存根
		
	}
	
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		final BOSObjectType prjBosType = new CurProjectInfo().getBOSType();
		AimCostInfo info = (AimCostInfo)model;
		boolean isAimCostRevise = info.getBoolean("isAimCostRevise");
		if(isAimCostRevise){
			handleAimCostRevise(ctx, info);
		}
		String projectID =null;
		
		if (BOSUuid.read(info.getOrgOrProId()).getType().equals(prjBosType)) {
			/****
			 * 期间的条件单独放到了下面
			 * 需要更新快照表
			 * 周勇 - 2009-11-12
			 */
			projectID= info.getOrgOrProId();
			if(info.getPeriod()==null){
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("id");
				selector.add("fullOrgUnit.id");
				String companyID = CurProjectFactory.getLocalInstance(ctx)
						.getCurProjectInfo(
								new ObjectUuidPK(info.getOrgOrProId()),
								selector).getFullOrgUnit().getId().toString();
				if(FDCUtils.IsInCorporation(ctx, companyID)&&info.getPeriod()==null){
					PeriodInfo currenctCostPeriod = FDCUtils.getCurrentPeriod(ctx,
							projectID, true);
					//以前取当前期间为空为抛异常，现在基类把这个异常取消了。
					if(currenctCostPeriod!=null)
						info.setPeriod(currenctCostPeriod);
				}
			}
		}
		
    	if(projectID!=null){
    		Set prjSet=new HashSet();
    		prjSet.add(projectID);
    		ProjectCostChangeLogFactory.getLocalInstance(ctx).insertLog(prjSet);
    	}
    	info.setState(FDCBillStateEnum.SAVED);
    	
    	if(FDCUtils.getDefaultFDCParamByKey(
				ctx, ContextUtil.getCurrentFIUnit(ctx).getId().toString(), FDCConstants.FDC_PARAM_AIMCOSTAUDIT))
    		info.setIsLastVersion(false);
		return super._submit(ctx, model);
	}

	private void handleAimCostRevise(Context ctx, AimCostInfo info) throws BOSException, EASBizException {
		//处理修订
		Map map=new HashMap();
		for(Iterator iter=info.getCostEntry().iterator();iter.hasNext();){
			CostEntryInfo entry=(CostEntryInfo)iter.next();
			if(entry.getId()==null||entry.getCostAccount()==null||entry.getBOSUuid("oldId")==null){
				continue;
			}
			map.put(entry.getId().toString(),entry.getBOSUuid("oldId").toString());
		}
		
		if(map.size()>0){
			Set oldEntrySet=map.keySet();
			EntityViewInfo view=new EntityViewInfo();
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("costEntryId", oldEntrySet, CompareType.INCLUDE));
			view.getSelector().add("*");
			view.getSelector().add("product.id");
			view.getSelector().add("apportionType.id");
			//各产品类型动态目标分摊
			AimCostProductSplitEntryCollection splitEntrys = AimCostProductSplitEntryFactory.getLocalInstance(ctx).getAimCostProductSplitEntryCollection(view);
			Map tempMap=new HashMap();
			for(Iterator iter=splitEntrys.iterator();iter.hasNext();){
				AimCostProductSplitEntryInfo splitInfo=(AimCostProductSplitEntryInfo)iter.next();
				if(tempMap.get(splitInfo.getCostEntryId())==null){
					AimCostProductSplitEntryCollection newSplits=new AimCostProductSplitEntryCollection();
					newSplits.add(splitInfo);
					tempMap.put(splitInfo.getCostEntryId(), newSplits);
				}else{
					((AimCostProductSplitEntryCollection)tempMap.get(splitInfo.getCostEntryId())).add(splitInfo);
				}
			}
			Map costEntryMap=new HashMap();
			for(Iterator iter=info.getCostEntry().iterator();iter.hasNext();){
				CostEntryInfo entry=(CostEntryInfo)iter.next();
				if(entry.getProduct()!=null||entry.getBOSUuid("oldId")==null||entry.getId()==null){
					//直接指定分摊不需要分摊方案
					continue;
				}
				AimCostProductSplitEntryCollection splits = (AimCostProductSplitEntryCollection)tempMap.get(entry.getString("oldId"));
				if(splits!=null&&splits.size()>0){
					for(int i=0;i<splits.size();i++){
						splits.get(i).setCostEntryId(entry.getId().toString());
					}
					costEntryMap.put(entry.getId().toString(), splits);
				}
			}
			
			if(costEntryMap.size()>0){
				FDCCostRptFacadeFactory.getLocalInstance(ctx).submitAimProductCost(costEntryMap);
			}
			
			
			//各产品目标分摊
			AIMAimCostProductSplitEntryCollection aimSplitEntrys = AIMAimCostProductSplitEntryFactory.getLocalInstance(ctx).getAIMAimCostProductSplitEntryCollection(view);
			tempMap=new HashMap();
			for(Iterator iter=aimSplitEntrys.iterator();iter.hasNext();){
				AIMAimCostProductSplitEntryInfo splitInfo=(AIMAimCostProductSplitEntryInfo)iter.next();
				if(tempMap.get(splitInfo.getCostEntryId())==null){
					AIMAimCostProductSplitEntryCollection newSplits=new AIMAimCostProductSplitEntryCollection();
					newSplits.add(splitInfo);
					tempMap.put(splitInfo.getCostEntryId(), newSplits);
				}else{
					((AIMAimCostProductSplitEntryCollection)tempMap.get(splitInfo.getCostEntryId())).add(splitInfo);
				}
			}
			costEntryMap=new HashMap();
			for(Iterator iter=info.getCostEntry().iterator();iter.hasNext();){
				CostEntryInfo entry=(CostEntryInfo)iter.next();
				if(entry.getProduct()!=null||entry.getBOSUuid("oldId")==null||entry.getId()==null){
					//直接指定分摊不需要分摊方案
					continue;
				}
				AIMAimCostProductSplitEntryCollection splits = (AIMAimCostProductSplitEntryCollection)tempMap.get(entry.getString("oldId"));
				if(splits!=null&&splits.size()>0){
					for(int i=0;i<splits.size();i++){
						splits.get(i).setCostEntryId(entry.getId().toString());
					}
					costEntryMap.put(entry.getId().toString(), splits);
				}
			}
			
			if(costEntryMap.size()>0){
				FDCCostRptFacadeFactory.getLocalInstance(ctx).submitAIMAimProductCost(costEntryMap);
			}
		}
		
		//更新之前版本的最新为不是最新 ,启用FDC_PARAM_AIMCOSTAUDIT参数的话提交不更新最新版本
		if(!FDCUtils.getDefaultFDCParamByKey(
				ctx, ContextUtil.getCurrentFIUnit(ctx).getId().toString(), FDCConstants.FDC_PARAM_AIMCOSTAUDIT)){
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			builder.appendSql("update T_AIM_AimCost set fisLastVersion=0 where FOrgOrProId=? and fisLastVersion=1");
			builder.addParam(info.getOrgOrProId());
			builder.execute();
		}
	}

	protected Map _getAimCostVers(Context ctx, Map param) throws BOSException, EASBizException {
		Map dataMap=new HashMap();
		if(param==null||param.size()<=0){
			return dataMap;
		}
		String prjId=(String)param.get("prjId");
		
		String prjId2 = (String) param.get("prjId2");
		Set idSet=(Set)param.get("idSet");
		
		String baseAimID = (String) param.get("baseAimID");
		if(prjId==null||idSet==null||idSet.size()<=0){
			return dataMap;
		}
		
		if (prjId2 == null || idSet == null || idSet.size() <= 0) {
			return dataMap;
		}
		
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		
		//get aimcost verinfo
		view=new EntityViewInfo();
		filter=new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("id",idSet,CompareType.INCLUDE));
		view.getSelector().add("costEntry.costAmount");
		view.getSelector().add("costEntry.costAccount.id");
		view.getSelector().add("measureStage.number");
		view.getSelector().add("measureStage.id");
		view.getSelector().add("id");
		AimCostCollection aimCostCollecton=AimCostFactory.getLocalInstance(ctx).getAimCostCollection(view);
		String number = null;
		String stageId = null;
		String stageId2 = null;
		for(int i=0;i<aimCostCollecton.size();i++){
			AimCostInfo info=(AimCostInfo)aimCostCollecton.get(i);
			if (info.getMeasureStage() != null
					&& info.getMeasureStage().getId() != null
					&& idSet
							.contains(info.getId().toString())
					&& info.getMeasureStage().getNumber() != null) {
				//				if (FDCHelper.toBigDecimal(info.getMeasureStage().getNumber())
				//						.compareTo(FDCHelper.toBigDecimal(number)) > 0) {
				//					number = info.getMeasureStage().getNumber();
				//					stageId = info.getMeasureStage().getId().toString();
				//				}
				if (info.getId().toString().equals(baseAimID)) {
					//					number = info.getMeasureStage().getNumber();
					stageId = info.getMeasureStage().getId().toString();
				} else {
					stageId2 = info.getMeasureStage().getId().toString();
				}
			}
			for(Iterator iter=info.getCostEntry().iterator();iter.hasNext();){
				CostEntryInfo entry=(CostEntryInfo)iter.next();
				if(entry.getCostAccount()==null){
					continue;
				}
				String key=info.getId().toString()+entry.getCostAccount().getId().toString();
				dataMap.put(key, FDCNumberHelper.add(dataMap.get(key), entry.getCostAmount()));
			}
		}
		Map acctStageMap = AccountStageHelper.initAccoutStageMap(ctx, prjId, stageId);
		
		Map acctStageMap2 = AccountStageHelper.initAccoutStageMap(ctx, prjId2, stageId2);
		//get costaccount
		view=new EntityViewInfo();
		filter=new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", prjId));
		filter.getFilterItems().add(new FilterItemInfo("id", new HashSet(acctStageMap.keySet()), CompareType.INCLUDE));
		
		view.getSelector().add("id");
		view.getSelector().add("number");
		view.getSelector().add("longNumber");
		view.getSelector().add("name");
		view.getSelector().add("isLeaf");
		view.getSelector().add("level");
		view.getSorter().add(new SorterItemInfo("longNumber"));
		CostAccountCollection accts=CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
		dataMap.put("accts", accts);
		
		
		view = new EntityViewInfo();
		filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", prjId2));
		filter.getFilterItems().add(new FilterItemInfo("id", new HashSet(acctStageMap2.keySet()), CompareType.INCLUDE));

		view.getSelector().add("id");
		view.getSelector().add("number");
		view.getSelector().add("longNumber");
		view.getSelector().add("name");
		view.getSelector().add("isLeaf");
		view.getSelector().add("level");
		view.getSorter().add(new SorterItemInfo("longNumber"));
		CostAccountCollection accts2 = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
		dataMap.put("accts2", accts2);
		
		
		Map costAccount = new HashMap();
		Map costAccount2 = new HashMap();
		
		
		
		Map map = new TreeMap();
		Map map2 = new HashMap();
		
		for (Iterator ite = accts.iterator(); ite.hasNext();) {
			CostAccountInfo cAc = (CostAccountInfo) ite.next();
			map.put(cAc.getLongNumber(), cAc.getName());
			
			map2.put(cAc.getLongNumber(), new Integer(cAc.getLevel()));
			
			costAccount.put(cAc.getLongNumber(), cAc);
		}
		for (Iterator ite = accts2.iterator(); ite.hasNext();) {
			CostAccountInfo cAc = (CostAccountInfo) ite.next();
			
			costAccount2.put(cAc.getLongNumber(), cAc);
			
			if (!map.containsKey(cAc.getLongNumber())) {
				map.put(cAc.getLongNumber(), cAc.getName());
				
				map2.put(cAc.getLongNumber(), new Integer(cAc.getLevel()));
			} else {
				String name = (String) map.get(cAc.getLongNumber());
				if (name.equals(cAc.getName())) {
					continue;
				}
				name = name + "/" + cAc.getName();
				map.put(cAc.getLongNumber(), name);
			}
		}
		
		
		
		dataMap.put("maps", map);
		
		dataMap.put("maps2", map2);
		
		dataMap.put("costAccount", costAccount);

		dataMap.put("costAccount2", costAccount2);
		
		//get indexValue
		Map indexValue = ProjectHelper.getIndexValue(ctx, prjId, new String[]{ApportionTypeInfo.buildAreaType,ApportionTypeInfo.sellAreaType}, ProjectStageEnum.AIMCOST, false);
		dataMap.put("buildArea", indexValue.get(prjId+" "+ApportionTypeInfo.buildAreaType));
		dataMap.put("sellArea", indexValue.get(prjId+" "+ApportionTypeInfo.sellAreaType));
		
		
		//get indexValue2
		Map indexValue2 = ProjectHelper.getIndexValue(ctx, prjId2, new String[] { ApportionTypeInfo.buildAreaType,
				ApportionTypeInfo.sellAreaType }, ProjectStageEnum.AIMCOST, false);
		dataMap.put("buildArea2", indexValue2.get(prjId2 + " " + ApportionTypeInfo.buildAreaType));
		dataMap.put("sellArea2", indexValue2.get(prjId2 + " " + ApportionTypeInfo.sellAreaType));
		
		
		
		return dataMap;
	}

	/**
	 * R101123-225目标成本修订支持工作审批流.
	 * 添加一个空的修订方法供修订按钮的 工作流使用 by zhiyuan_tang 2011-03-01
	 */
	protected void _recense(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		
	}
}