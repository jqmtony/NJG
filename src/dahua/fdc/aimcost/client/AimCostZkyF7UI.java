/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.framework.ITreeBase;

/**
 * output class name
 */
public class AimCostZkyF7UI extends AbstractAimCostZkyF7UI {

	private boolean isFirstLoad = true;

	/**
	 * ÃèÊö£º¹¹Ôìº¯Êý
	 * @throws Exception
	 */
	public AimCostZkyF7UI() throws Exception {
		super();
	}

	/**
	 * @see com.kingdee.eas.framework.client.TreeListUI#getTreeInterface()
	 */
	protected ITreeBase getTreeInterface() throws Exception {
		return CurProjectFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return null;
	}

	protected void initTree() throws Exception {
		ProjectTreeBuilder treeBuilder = new ProjectTreeBuilder();
		treeBuilder.build(this, this.treeMain, this.actionOnLoad);
		this.treeMain.expandAllNodes(true, (TreeNode) ((TreeModel) this.treeMain.getModel()).getRoot());
	}

	/**
	 * @see com.kingdee.eas.framework.client.ListUI#getQueryExecutor(com.kingdee.bos.metadata.IMetaDataPK, com.kingdee.bos.metadata.entity.EntityViewInfo)
	 */
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		viewInfo = getSelectObjId(viewInfo);
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		if (isFirstLoad && (treeMain == null || treeMain.getRowCount() > 1)) {
			isFirstLoad = false;
			return;
		}

		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
			return;
		}
		execQuery();
	}

	private EntityViewInfo getSelectObjId(EntityViewInfo viewInfo) {
		viewInfo = new EntityViewInfo();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		FilterInfo filter = new FilterInfo();
		filter.setMaskString(null);
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("fullOrgUnit.longNumber");
			filter.getFilterItems().add(new FilterItemInfo("orgOrProId", projectInfo.getId()));
			viewInfo.setFilter(filter);
			return viewInfo;
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo projectInfo = (OrgStructureInfo) node.getUserObject();
			filter.getFilterItems().add(new FilterItemInfo("orgOrProId", projectInfo.getUnit().getId()));
			viewInfo.setFilter(filter);
			return viewInfo;
		}
		return null;
	}

	protected boolean isIgnoreCUFilter() {
		return false;
	}
	
	private void confirm() throws Exception {
		checkSelected();
		getData();
	}
	
	/**
	 * 
	 */
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getClickCount() == 2) {
            if (e.getType() == 0) {
				return;
			}
			confirm();
		}
	}

	/**
	 *
	 */
	protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {
		//		super.tblMain_tableSelectChanged(e);
	}

	public AimCostInfo getData() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("orgOrProId");
		sic.add("versionNumber");
		sic.add("versionName");
		sic.add("isLastVersion");
		sic.add("*");

		String keyValue = getSelectedKeyValue();
		if (keyValue == null) {
			return null;
		}
		try {
			AimCostInfo costInfo = AimCostFactory.getRemoteInstance().getAimCostInfo(new ObjectUuidPK(keyValue));
			disposeUIWindow();
			return costInfo;
		} catch (Exception e) {
			e.printStackTrace();
			handUIExceptionAndAbort(e);
		}
		return null;
	}
	/**
	 * 
	 */
	public void onLoad() throws Exception {
		super.onLoad();
		this.getUIToolBar().setVisible(false);
	}
}