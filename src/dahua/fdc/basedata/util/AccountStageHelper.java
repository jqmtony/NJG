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
 * ��Ŀ��ɱ������ȡ��������
 * ����ɱ���Ŀ�����׶��߼�
 * @author pengwei_hou
 * @Date 2011-06-10
 *
 */
public class AccountStageHelper {

	/**
	 * ������׶γ�ʼ���ɱ���Ŀ��
	 * ȥ���ɱ���Ŀ����׶�δѡ�еļ�¼
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
	 * ������׶γ�ʼ���ɱ���Ŀ������ϸ�ڵ�
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
	 * ��ʼ���ɱ���Ŀ�����׶ι�ϵֵΪ1�ĳɱ���ĿIDֵ
	 * 
	 * @param ������Ŀ��projectId
	 * @param ����׶Σ�measureStageId
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
	 * @param projectId:������Ŀ
	 * @param isAimMeasure��Ŀ��ɱ�����/����Ŀ��ɱ�����
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
			if(true)return null;//����û��Ŀ��ɱ�����׶����հ汾�������
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
				FDCMsgBox.showWarning("û�����ò���׶�!");
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
