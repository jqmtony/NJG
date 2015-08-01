package com.kingdee.eas.fdc.basedata.client;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.ApportionTypeCollection;
import com.kingdee.eas.fdc.basedata.ApportionTypeFactory;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataCollection;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataEntryCollection;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataEntryInfo;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataFactory;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataInfo;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.TargetTypeFactory;
import com.kingdee.eas.fdc.basedata.TargetTypeInfo;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 指标管理工具类
 * 
 * @author liupd
 * 
 */
public class ProjectIndexDataUtils {
	protected static final String MEASURE_UNIT_NAME = "measureUnit.name";

	protected static final String TARGET_TYPE_ID = "targetType.id";
	
	public static ProjectIndexDataEntryCollection createBlankIndexesColl(
			FilterInfo filter) throws EASBizException, BOSException {
		EntityViewInfo view = new EntityViewInfo();
		
		view.getSelector().add(MEASURE_UNIT_NAME);
		view.getSelector().add("number");
		view.getSelector().add("name");
		view.getSelector().add("description");
		view.getSelector().add(TARGET_TYPE_ID);
		view.getSelector().add("forCostApportion");
		view.getSorter().add(new SorterItemInfo("number"));
		FilterInfo newFilter=new FilterInfo();
		Set set=new HashSet();
		set.add(ApportionTypeInfo.aimCostType);
		set.add(ApportionTypeInfo.appointType);
		newFilter.getFilterItems().add(new FilterItemInfo("id",set,CompareType.NOTINCLUDE));
//		filter.getFilterItems().add(new FilterItemInfo("id", ApportionTypeInfo.aimCostType, CompareType.NOTEQUALS));
//		filter.getFilterItems().add(new FilterItemInfo("id", ApportionTypeInfo.appointType, CompareType.NOTEQUALS));
		newFilter.setMaskString("#0");
		
		filter.mergeFilter(newFilter, "and");		
		view.setFilter(filter);
		
		ApportionTypeCollection apportionTypeCollection = ApportionTypeFactory.getRemoteInstance().getApportionTypeCollection(view);

		ProjectIndexDataEntryCollection indexColl = new ProjectIndexDataEntryCollection();
		for (Iterator iter = apportionTypeCollection.iterator(); iter.hasNext();) {
			ApportionTypeInfo element = (ApportionTypeInfo) iter.next();
			ProjectIndexDataEntryInfo pid = new ProjectIndexDataEntryInfo();
			pid.setApportionType(element);
			indexColl.add(pid);
		}

		return indexColl;
	}

	/**
	 * 
	 * @param targetTypeId
	 * @param onlyCostDistr		仅成本分摊的指标？
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static FilterInfo getAppFilter(String targetTypeId, boolean onlyCostDistr) throws BOSException, EASBizException {
		FilterInfo filter = new FilterInfo();

		if (targetTypeId != null) {
			TargetTypeInfo targetTypeInfo = TargetTypeFactory
					.getRemoteInstance().getTargetTypeInfo(
							new ObjectUuidPK(targetTypeId));
			if (targetTypeInfo.isIsLeaf()) {
				filter.getFilterItems().add(
						new FilterItemInfo(TARGET_TYPE_ID, targetTypeId));
			} else {
				filter.getFilterItems().add(
						new FilterItemInfo("targetType.longNumber",
								targetTypeInfo.getLongNumber() + "%",
								CompareType.LIKE));
			}
		}
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(
				new FilterItemInfo("targetType.isEnabled", Boolean.TRUE));
		
		if(onlyCostDistr) {
			filter.getFilterItems().add(
					new FilterItemInfo("forCostApportion", Boolean.TRUE));
		}
		filter.mergeFilter(ApportionTypeInfo.getCUFilter(SysContext.getSysContext().getCurrentCtrlUnit()), "and");
		return filter;
	}

	/**
	 * 构造全工程树并根据传入的ID返回Node(不包括已禁用的)
	 * @param projOrOrgId
	 * @return
	 * @throws Exception
	 */
	public static TreeNode getNodeById(String projOrOrgId) throws Exception {
		ProjectTreeBuilder builder = new ProjectTreeBuilder();
		TreeModel treeModel = builder.getFullProjTreeModel();
		Map nodeMap = new HashMap();
		builder.genNodeMap((TreeNode)treeModel.getRoot(), nodeMap);
		TreeNode node = (TreeNode)nodeMap.get(projOrOrgId);
		return node;
	}
	
	/**
	 * 根据当前节点向上汇总指标数据
	 * @param node
	 * @param productTypeId
	 * @throws Exception
	 */
	public static void sumUp(TreeNode node, ProjectStageEnum projStage, String productTypeId)
			throws Exception {
		if(node == null) return;
		TreeNode parent = node.getParent();
		if (parent == null) {
			return;
		}

		Enumeration enumeration = parent.children();
		List idList = new ArrayList();
		CurProjectInfo curProject = null;
		OrgStructureInfo orgStructureInfo = null;
		String parentId = null;

		Object userObject = ((DefaultKingdeeTreeNode) parent).getUserObject();
		if (userObject == null)
			return;
		if (userObject instanceof CurProjectInfo) {
			parentId = ((CurProjectInfo) userObject).getId().toString();
		} else {
			parentId = ((OrgStructureInfo) userObject).getUnit().getId()
					.toString();
		}

		while (enumeration.hasMoreElements()) {
			DefaultKingdeeTreeNode element = (DefaultKingdeeTreeNode) enumeration
					.nextElement();
			if (element.getUserObject() instanceof CurProjectInfo) {
				curProject = (CurProjectInfo) element.getUserObject();
				idList.add(curProject.getId().toString());
			} else {
				orgStructureInfo = (OrgStructureInfo) element.getUserObject();
				idList.add(orgStructureInfo.getUnit().getId().toString());
			}

		}

		IRowSet sumData = ProjectIndexDataFactory.getRemoteInstance().sum(
				idList, productTypeId);

		addRows(sumData, projStage, productTypeId, parentId);

		if (parent.getParent() != null) {
			sumUp(node.getParent(), projStage, productTypeId);
		}
	}

	public static ProjectIndexDataInfo constructAProjectIndexDataInfo(
			String projOrOrgId, ProjectStageEnum projStage, String productTypeId) {
		ProjectIndexDataInfo indexDataInfo = new ProjectIndexDataInfo();

		indexDataInfo.setProjOrOrgID(BOSUuid.read(projOrOrgId));
		indexDataInfo.setProjectStage(projStage);
		
		if (productTypeId != null) {

			ProductTypeInfo productTypeInfo = new ProductTypeInfo();
			productTypeInfo.setId(BOSUuid.read(productTypeId));
			indexDataInfo.setProductType(productTypeInfo);
		}

		indexDataInfo.setVerNo("V1.0");
		indexDataInfo.setVerName(FDCClientUtils.getRes("initialVer"));
		indexDataInfo.setVerTime(new Timestamp(System.currentTimeMillis()));
		indexDataInfo.setIsLatestVer(true);

		return indexDataInfo;
	}

	public static void addRows(IRowSet rowSet, ProjectStageEnum projStage, String productTypeId,
			String parentId) throws BOSException, EASBizException {
		ProjectIndexDataCollection projectIndexDataCollection = getProjectIndexDataCollByCond(projStage, productTypeId, parentId, true);
		ProjectIndexDataInfo info = null;
		if (projectIndexDataCollection.size() == 0) {
			info = constructAProjectIndexDataInfo(parentId, projStage, productTypeId);
		} else {
			info = projectIndexDataCollection.get(0);
		}

		info.setProjOrOrgID(BOSUuid.read(parentId));
		info.setState(FDCBillStateEnum.SUBMITTED);
		ProjectIndexDataEntryCollection entries = info.getEntries();
		Map entryMap = new HashMap();
		for (Iterator iter = entries.iterator(); iter.hasNext();) {
			ProjectIndexDataEntryInfo element = (ProjectIndexDataEntryInfo) iter.next();
			entryMap.put(element.getApportionType().getId().toString(), element);
		}
		ApportionTypeInfo appTypeInfo = null;
		TargetTypeInfo tarTypeInfo = null;
		ProjectIndexDataEntryInfo entryInfo = null;
		try {
			while (rowSet.next()) {
				String appId = rowSet
				.getString("FApportionTypeID");
				if(entryMap.containsKey(appId)) {
					entryInfo = (ProjectIndexDataEntryInfo)entryMap.get(appId);
				}
				else {
					entryInfo = new ProjectIndexDataEntryInfo();
				}

				appTypeInfo = new ApportionTypeInfo();
				
				appTypeInfo.setId(BOSUuid.read(appId));
				entryInfo.setApportionType(appTypeInfo);
				
				String tarTypeID = rowSet.getString("FTargetTypeID");
				tarTypeInfo = new TargetTypeInfo();
				tarTypeInfo.setId(BOSUuid.read(tarTypeID));
				entryInfo.setTargetType(tarTypeInfo);

				BigDecimal bigDecimal = rowSet.getBigDecimal("totalValue");
				if (bigDecimal == null)
					continue;
				entryInfo.setIndexValue(bigDecimal);
				entryInfo.setBySum(true);
				if(!entryMap.containsKey(appId)) {
					info.getEntries().add(entryInfo);
				}

			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}

		if (info.getEntries().size() > 0) {
			ProjectIndexDataFactory.getRemoteInstance().save(info);
		}
		
		entryMap.clear();
	}

	public static ProjectIndexDataCollection getProjectIndexDataCollByCond(ProjectStageEnum projStage, String productTypeId, String projOrOrgId, boolean isLatestVer) throws BOSException {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("projOrOrgID", projOrOrgId));
		;
		filterItems.add(new FilterItemInfo("projectStage", projStage.getValue()));
		filterItems.add(new FilterItemInfo("productType", productTypeId));
		filterItems.add(new FilterItemInfo("isLatestVer", Boolean.valueOf(isLatestVer)));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("entries.*");
		view.getSelector().add("entries.targetType.id");
		view.getSelector().add("entries.apportionType.id");
		ProjectIndexDataCollection projectIndexDataCollection = ProjectIndexDataFactory
				.getRemoteInstance().getProjectIndexDataCollection(view);
		return projectIndexDataCollection;
	}
	
	/**
	 * 检查上下级数据汇总一致性，未完成
	 * @param owner
	 * @param projId
	 * @param productTypeId
	 * @param projStage
	 */
	public static void checkIndexDataConst(CoreUIObject owner, String projId, String productTypeId, ProjectStageEnum projStage) {
		// TODO 以前的方法没有完成，全是废代码，删除掉
	}
	
	/**@deprecated 
	 * @param coreUI
	 * @param prjId
	 * @param productId
	 * @param apportions
	 */
	public static void idxRefresh(final CoreUI coreUI,String prjId,String productId,Set apportions){

		if(apportions!=null){
			//建筑，可售面积要刷新产品及项目
			for(Iterator iter=apportions.iterator();iter.hasNext();){
				String id=(String)iter.next();
				if(id!=null&&(id.equals(ApportionTypeInfo.buildAreaType)||id.equals(ApportionTypeInfo.sellAreaType))){
					productId=null;
				}
			}
		}
		
		final String pId=prjId;
		final String prodId=productId;
		final List apps=new ArrayList(apportions);
		final FDCProgressDialog diag = FDCProgressDialog.createProgressDialog(coreUI, true);
		diag.run(false,true, new IFDCRunnableWithProgress() {
			public void run(IFDCProgressMonitor monitor) {
				monitor.beginTask("面积(指标)刷新",-1);
				monitor.setTaskName("正在进行面积(指标)刷新，请稍候 ...");
				try {
					int rtnCode = 0;
//					rtnCode = CurProjectFactory.getRemoteInstance().idxRefresh(id);
//					使用新接口by sxhong 2008/1/21 
					rtnCode = CurProjectFactory.getRemoteInstance().idxRefresh(pId,prodId,apps);
					monitor.stopTimer();
					if (rtnCode == 0) {
						FDCClientUtils.showOprtOK(diag);
					} else {
						FDCClientUtils.showOprtFailed(diag);
					}
				} catch (BOSException e) {
					// @AbortException
					coreUI.handUIExceptionAndAbort(e);
				}finally{
					monitor.done();
				}
			}
		});
	}
	
	public static void idxRefresh(final CoreUI coreUI,final Map param){
		final FDCProgressDialog diag = FDCProgressDialog.createProgressDialog(coreUI, true);
		diag.run(false,true, new IFDCRunnableWithProgress() {
			public void run(IFDCProgressMonitor monitor) {
				monitor.beginTask("面积(指标)刷新",-1);
				monitor.setTaskName("正在进行面积(指标)刷新，请稍候 ...");
				try {
//					使用新接口by sxhong 2008/1/21 
					ProjectIndexDataFactory.getRemoteInstance().idxRefresh(param);
					monitor.stopTimer();
					FDCClientUtils.showOprtOK(diag);
				} catch (Exception e) {
					// @AbortException
					coreUI.handUIExceptionAndAbort(e);
				}finally{
					monitor.done();
				}
			}
		});
	}
}
