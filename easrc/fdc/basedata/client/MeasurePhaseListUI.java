/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.MeasurePhaseFactory;
import com.kingdee.eas.fdc.basedata.MeasurePhaseInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class MeasurePhaseListUI extends AbstractMeasurePhaseListUI
{
    private static final Logger logger = CoreUIObject.getLogger(MeasurePhaseListUI.class);
    
    /**
     * output class constructor
     */
    public MeasurePhaseListUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    public void onLoad() throws Exception {
		super.onLoad();
//		initTree();
//		this.actionCancel.setVisible(false);
//		this.actionCancelCancel.setVisible(false);
	}
	private void initTree() throws Exception {
//		ProjectTreeBuilderForXH projectTreeBuilder = new ProjectTreeBuilderForXH();
//		projectTreeBuilder.build(this, treeMain, actionOnLoad);
//		treeMain.expandAllNodes(true, (TreeNode)treeMain.getModel().getRoot());//设置是否打开
//		this.treeMain.setModel(FDCTreeHelper.getSellProjectTree(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
//		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}
    protected FDCDataBaseInfo getBaseDataInfo() {
		return new MeasurePhaseInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return MeasurePhaseFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return MeasurePhaseEditUI.class.getName();
	}
//	public DefaultKingdeeTreeNode getSelectedTreeNode()
//    {
//        return (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
//    }
//	protected IQueryExecutor getQueryExecutor(IMetaDataPK pk,
//			EntityViewInfo viewInfo) {
//		
//		EntityViewInfo view = (EntityViewInfo)viewInfo.clone();
//		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
//		FilterInfo  filter = new FilterInfo();
//		if(getSelectedTreeNode() != null && node.getUserObject() instanceof ProjectBaseInfo )
//		{
//			ProjectBaseInfo info = (ProjectBaseInfo)node.getUserObject();
//			filter.getFilterItems().add(new FilterItemInfo("projectItem.id",info.getId().toString()));
//		}else if(getSelectedTreeNode() != null && node.getUserObject() instanceof FullOrgUnitInfo) {
//			FullOrgUnitInfo orgInfo = (FullOrgUnitInfo)node.getUserObject();
//			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",orgInfo.getId().toString()));
//		}
//		else if(getSelectedTreeNode() != null && node.getUserObject() instanceof OperationPhasesInfo){
//			OperationPhasesInfo Info = (OperationPhasesInfo)node.getUserObject();
//			filter.getFilterItems().add(new FilterItemInfo("operation.id",Info.getId().toString()));
//		}
//		view.setFilter(filter);
//		return super.getQueryExecutor(pk, view);
//	}

}