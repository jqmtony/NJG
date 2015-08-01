package com.kingdee.eas.fdc.basedata.util;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.aimcost.IMeasureCost;
import com.kingdee.eas.fdc.aimcost.MeasureCostCollection;
import com.kingdee.eas.fdc.aimcost.MeasureCostFactory;
import com.kingdee.eas.fdc.aimcost.MeasureCostInfo;
import com.kingdee.eas.fdc.aimcost.client.AimCostClientHelper;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MeasureStageCollection;
import com.kingdee.eas.fdc.basedata.MeasureStageFactory;
import com.kingdee.eas.fdc.basedata.MeasureStageInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 从目标成本测算抽取公共方法
 * 处理成本科目与测算阶段逻辑
 * @author pengwei_hou
 * @Date 2011-06-10
 *
 */
public class AccountStageHelper {

	/**
	 * 按测算阶段初始化成本科目树
	 * 去掉成本科目测算阶段未选中的记录
	 * @param node
	 * @return
	 */
	public static DefaultMutableTreeNode initChild(Map acctStageMap,DefaultMutableTreeNode node) {
		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
			CostAccountInfo costAcct = (CostAccountInfo) child.getUserObject();
			if (acctStageMap.containsKey(costAcct.getId().toString())) {
				initChild(acctStageMap,child);
			} else {
				child.removeFromParent();
				i--;
			}
		}
		return node;
	}

	/**
	 * 按测算阶段初始化成本科目树最明细节点
	 * 
	 * @param node
	 * @return
	 */
	public static DefaultMutableTreeNode initIsLeaf(DefaultMutableTreeNode node) {
		if (node.getChildCount() == 0) {
			CostAccountInfo costAcctLeaf = (CostAccountInfo) node.getUserObject();
			costAcctLeaf.setIsLeaf(true);
		} else {
			for (int i = 0; i < node.getChildCount(); i++) {
				DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
				if (child.getChildCount() == 0) {
					CostAccountInfo costAcctLeaf = (CostAccountInfo) child.getUserObject();
					costAcctLeaf.setIsLeaf(true);
				} else {
					initIsLeaf(child);
				}
			}
		}
		return node;
	}

	/**
	 * 初始化成本科目与测算阶段关系值为1的成本科目ID值
	 * 
	 * @param 工程项目：projectId
	 * @param 测算阶段：measureStageId
	 * @return
	 * @throws BOSException
	 */
	public static Map initAccoutStageMap(Context ctx,String projectId, String measureStageId) throws BOSException {
		if(measureStageId==null){
			measureStageId = getLstStageId(ctx,projectId, true);
		}
		Map acctStageMap = new HashMap();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select acct.fid costAccountID,stage.fvalue value from  T_FDC_AccountStage stage ");
		builder.appendSql("left join t_fdc_costAccount acct on stage.fcostAccountid = acct.fid ");
		builder.appendSql("left join t_fdc_curproject prj on acct.FCurProject = prj.fid ");
		builder.appendSql("where prj.fid = ? ");
		builder.addParam(projectId);
		if (measureStageId != null) {
			builder.appendSql("and stage.fmeasurestageid= ? ");
			builder.addParam(measureStageId);
		}
		builder.appendSql("and stage.fvalue = 1 ");
		builder.appendSql("order by acct.flongnumber");
		IRowSet iRowSet = builder.executeQuery();
		try {
			while (iRowSet.next()) {
				acctStageMap.put(iRowSet.getString("costAccountID"), iRowSet.getString("value"));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return acctStageMap;
	}
	
	/**
	 * 
	 * @param projectId:工作项目
	 * @param isAimMeasure：目标成本测算/可研目标成本测算
	 * @return
	 * @throws BOSException
	 */
	public static String getLstStageId(Context ctx, String projectId, boolean isAimMeasure) throws BOSException {
		IMeasureCost iMeasureCost = null;
		if(ctx!=null){
			iMeasureCost = MeasureCostFactory.getLocalInstance(ctx);
		}else{
			iMeasureCost = MeasureCostFactory.getRemoteInstance();
		}
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("measureStage.number");
		view.getSelector().add("measureStage.name");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("project.id",projectId));
		filter.getFilterItems().add(new FilterItemInfo("isLastVersion",Boolean.TRUE));
		if(isAimMeasure){
			filter.getFilterItems().add(new FilterItemInfo("isAimMeasure",Boolean.TRUE,CompareType.EQUALS));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("isAimMeasure",Boolean.TRUE,CompareType.NOTEQUALS));
		}
		MeasureCostCollection measureCostCollection = iMeasureCost.getMeasureCostCollection(view);
		if (measureCostCollection != null && measureCostCollection.size()==1){
			MeasureCostInfo measureCostInfo = measureCostCollection.get(0);
			if(measureCostInfo.getMeasureStage()!=null){
				MeasureStageInfo lastStageInfo = measureCostInfo.getMeasureStage();
				return lastStageInfo.getId().toString();
			}
		}else{
			if(true)return null;//需求：没有目标成本测算阶段最终版本数据则空
			view = new EntityViewInfo();
			view.getSelector().add("*");
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
			view.setFilter(filter);
			SorterItemInfo sort = new SorterItemInfo("number");
			sort.setSortType(SortType.DESCEND);
			view.getSorter().add(sort);
			MeasureStageCollection measureStageCollection = MeasureStageFactory.getRemoteInstance().getMeasureStageCollection(view);
			if (measureStageCollection == null || measureStageCollection.size() == 0) {
				FDCMsgBox.showWarning("没有启用测算阶段!");
				SysUtil.abort();
			}
			return measureStageCollection.get(0).getId().toString();
		}
		return null;
	}
	
	private CostAccountInfo getMapCostAccount(Map acctMap,String longNumber){
		return CostAccountHelper.getCostAccount(acctMap, longNumber);
	}
}
