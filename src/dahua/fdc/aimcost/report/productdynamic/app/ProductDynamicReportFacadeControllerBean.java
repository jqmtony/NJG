package com.kingdee.eas.fdc.aimcost.report.productdynamic.app;

import org.apache.log4j.Logger;
import javax.ejb.*;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IProjectDynamicCost;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostCollection;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEentryTotalCollection;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostEentryTotalInfo;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostFactory;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;


public class ProductDynamicReportFacadeControllerBean extends AbstractProductDynamicReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.aimcost.report.productdynamic.app.ProductDynamicReportFacadeControllerBean");
    
    protected RptParams _query(Context ctx, RptParams params)
    		throws BOSException, EASBizException {
    	
    	CurProjectInfo project = (CurProjectInfo) params.getObject("project");
    	Map costMap = getDynamicCostMap(ctx, params);
    	
    	Set productSet = new HashSet();
    	RptRowSet completeArea = executeQuery(getAreaFromAreaIndexManage(project, "3COMPLETEAREA"), null, ctx);
    	RptRowSet aimArea = executeQuery(getAreaFromAreaIndexManage(project, "1AIMCOSTAREA"), null, ctx);
    	RptRowSet cqgsArea = executeQuery(getAreaFromCQGS(project), null, ctx);
    	RptRowSet rowSet = null;
    	BigDecimal total = BigDecimal.ZERO;
    	if(completeArea.getRowCount() > 0)
    		rowSet = completeArea;
    	else if(aimArea.getRowCount() > 0)
    		rowSet = aimArea;
    	else
    		rowSet = cqgsArea;
    	try {
			RptRowSet cloneRowSet = (RptRowSet) rowSet.clone();
			while(cloneRowSet.next()) {
				productSet.add(cloneRowSet.getString("product"));
				total = total.add(cloneRowSet.getBigDecimal("buildArea"));
			}
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
    	
		Map productRateMap = rowSetToMap(rowSet, total);
		
		params.setObject("product", productSet);
		params.setObject("cost", costMap);
		params.setObject("productRate", productRateMap);
    	return params;
    }
    private Map rowSetToMap(RptRowSet rowSet, BigDecimal total) {
    	TreeMap map = new TreeMap();
    	
    	while(rowSet.next()) {
    		String product = rowSet.getString("product");
    		BigDecimal area = rowSet.getBigDecimal("buildArea");
    		
    		BigDecimal rate = (total.compareTo(BigDecimal.ZERO) == 0) ? BigDecimal.ZERO : area.divide(total, 3, BigDecimal.ROUND_HALF_UP);
    		map.put(product, rate);
    	}
    	return map;
    }
    /**
     * 获取建安动态跟踪表单据的汇总分录
     * @param ctx
     * @param params
     * @return
     */
    private Map getDynamicCostMap(Context ctx, RptParams params) {
    	TreeMap map = new TreeMap();
    	CurProjectInfo project = (CurProjectInfo) params.getObject("project");
    	try {
			ProjectDynamicCostInfo latestDynamicCost = getLatestDynamicCost(ctx, project);
			ProjectDynamicCostEentryTotalCollection entryTotal = latestDynamicCost.getEentryTotal();
			Set productSet = new HashSet();
			for(int i = 0; i < entryTotal.size(); i++) {
				ProjectDynamicCostEentryTotalInfo info = entryTotal.get(i);
				String costAccountNumber = info.getCostAccountNumber();
				String costAccount = info.getCostAccount();
				BigDecimal aimCost = info.getAimCost();
				BigDecimal dynamicCost = info.getSumBC();
				
				HashMap detailMap = new HashMap();
				detailMap.put("costAccountNumber", costAccountNumber);
				detailMap.put("costAccount", costAccount);
				detailMap.put("aimCost", (aimCost == null||aimCost.compareTo(BigDecimal.ZERO)==0)? BigDecimal.ZERO : aimCost);
				detailMap.put("dynamicCost", (dynamicCost==null||dynamicCost.compareTo(BigDecimal.ZERO)==0) ? BigDecimal.ZERO : dynamicCost);
				
				map.put(costAccountNumber, detailMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
    }
    /**
     * 获取面积指标管理中的面积
     * @param project
     * @param verName (1AIMCOSTAREA,3COMPLETEAREA)
     * @return
     */
    private String getAreaFromAreaIndexManage(CurProjectInfo project, String verName) {
    	StringBuilder sql = new StringBuilder("");
    	
    	sql.append(" select product.fname_l2 product, entry.FIndexValue buildArea");
    	sql.append(" from T_FDC_ProjectIndexData data");
    	sql.append(" left join T_FDC_ProductType product on product.fid=data.fproductTypeid");
    	sql.append(" left join T_FDC_ProjectIndexDataEntry entry on entry.fparentid=data.fid");
    	sql.append(" left join T_FDC_ApportionType pe on pe.fid = entry.FApportionTypeID");
    	sql.append(" where 1=1 ");
    	sql.append(" and data.fprojOrOrgId='"+project.getId()+"'");
    	sql.append(" and (data.fisLatestVer=1 OR data.fisLatestSubVer=1)");
    	sql.append(" and data.FProductTypeID is not null");
    	sql.append(" and pe.fname_l2 ='建筑面积' ");
    	sql.append(" and data.FVerName='"+verName+"'");
    	sql.append(" and data.FProductTypeID in");
    	sql.append(" (");
    	sql.append("  select entry.FProductType from T_FDC_CurProjProductEntries entry ");
    	sql.append("  left join T_FDC_CurProject project on project.fid=entry.FCurProject");
    	sql.append("  where project.fid='"+project.getId()+"'");
    	sql.append(" )");
    	
    	return sql.toString();
    }
    
    /**
     * 获取产权归属中的面积
     * @param project
     * @return
     */
    private String getAreaFromCQGS(CurProjectInfo project) {
    	StringBuilder sql = new StringBuilder("");
    	
    	sql.append(" select productType.fname_l2 product, entry.CFBuidlingArea buildArea");
    	sql.append(" from CT_COS_CQGSEntry entry");
    	sql.append(" left join CT_COS_CQGS cqgs on cqgs.fid=entry.fparentid");
    	sql.append(" left join T_FDC_ProductType productType on productType.fid=entry.CFBuildingNameID");
    	sql.append(" where 1=1 ");
    	sql.append(" and cqgs.CFLasted='1'");
    	sql.append(" and cqgs.CFProjectNameID='"+project.getId()+"'");
    	sql.append(" and entry.CFBuildingNameID is not null");
    	return sql.toString();
    }
    
    private ProjectDynamicCostInfo getLatestDynamicCost(Context ctx, CurProjectInfo project) throws Exception {
    	IProjectDynamicCost iProjectDynamicCost = ProjectDynamicCostFactory.getLocalInstance(ctx);
    	EntityViewInfo evi = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	evi.setFilter(filter);
    	filter.getFilterItems().add(new FilterItemInfo("curProject.id", project.getId().toString(), CompareType.EQUALS));
    	filter.getFilterItems().add(new FilterItemInfo("isLatest", Boolean.TRUE, CompareType.EQUALS));
    	
    	SorterItemCollection sic = new SorterItemCollection();
    	SorterItemInfo sorterItemInfo = new SorterItemInfo("year");
    	sorterItemInfo.setSortType(SortType.DESCEND);
    	sic.add(sorterItemInfo);
    	sorterItemInfo = new SorterItemInfo("month");
    	sorterItemInfo.setSortType(SortType.DESCEND);
    	sic.add(sorterItemInfo);
    	evi.setSorter(sic);
    	
    	SelectorItemCollection sicoll = new SelectorItemCollection();
    	sicoll.add("*");
    	sicoll.add("EntrysAccount.*");
    	sicoll.add("EentryTotal.*");
    	evi.setSelector(sicoll);
    	
    	ProjectDynamicCostInfo info = null;
    	ProjectDynamicCostCollection coll = iProjectDynamicCost.getProjectDynamicCostCollection(evi);
    	if(coll.size() > 0)
    		info = coll.get(0);
    	return info;
    }
}