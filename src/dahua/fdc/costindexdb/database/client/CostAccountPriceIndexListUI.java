/**
 * output package name
 */
package com.kingdee.eas.fdc.costindexdb.database.client;

import java.awt.event.ActionEvent;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CostAccountPriceIndexListUI extends AbstractCostAccountPriceIndexListUI
{
    private static final Logger logger = CoreUIObject.getLogger(CostAccountPriceIndexListUI.class);
    private static final String COST_ACCOUNT_NODE = "costAccountNode";
    /**
     * output class constructor
     */
    public CostAccountPriceIndexListUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
//    	buildProjectTree();
    	buildCostAccountTree(null);
//    	costMain.removeAllChildrenFromParent((DefaultKingdeeTreeNode)costMain.ge);
//    	costMain.setVisible(false);
    	addTreeListener();
    	tblMain.getColumn("curProject.name").getStyleAttributes().setHided(true);
    	String cuID = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		if(!cuID.equals(OrgConstants.DEF_CU_ID)) {
			actionAddNew.setEnabled(false);
			actionEdit.setEnabled(false);
			actionRemove.setEnabled(false);
		}
    }
    
    protected String getEditUIModal() {
    	return super.getEditUIModal();
    }
    
    public void buildProjectTree() throws Exception {
//    	ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();
//		projectTreeBuilder.build(this, projectMain, actionOnLoad);
//		if (projectMain.getRowCount() > 0) {
//			projectMain.setSelectionRow(0);
//			projectMain.expandPath(projectMain.getSelectionPath());
//		}
    }
   
    public void buildCostAccountTree(String cpid) throws Exception {
    	
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", OrgConstants.DEF_CU_ID, CompareType.EQUALS));
    	ITreeBuilder treeBuilder = TreeBuilderFactory.createTreeBuilder(new DefaultLNTreeNodeCtrl(CostAccountFactory.getRemoteInstance()),50,5,filter);
		treeBuilder.buildTree(costMain);
		TreeModel treeModel = new KingdeeTreeModel((TreeNode)((DefaultKingdeeTreeNode)costMain.getModel().getRoot()));
		costMain.setModel(treeModel);
//		costMain.setShowPopMenuDefaultItem(false);
		if (costMain.getRowCount() > 0) {
			costMain.expandAllNodes(true,(DefaultKingdeeTreeNode)costMain.getModel().getRoot());
			costMain.setVisible(true);
		}
    }
    
    public void addTreeListener(){
    	costMain.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                try {
                	costMain_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
    }
    
    protected void costMain_valueChanged(TreeSelectionEvent e) throws Exception {
//    	DefaultKingdeeTreeNode costNode=(DefaultKingdeeTreeNode)costMain.getLastSelectedPathComponent();
    	getUIContext().put(COST_ACCOUNT_NODE,costMain.getLastSelectedPathComponent());
    	refreshList();
    }
    
    public static DefaultKingdeeTreeNode getTreeNode(TreePath path) {
		return path == null ? null : (DefaultKingdeeTreeNode) path.getLastPathComponent();
	}
    
    protected IQueryExecutor getQueryExecutor(IMetaDataPK arg0,EntityViewInfo evi) {
    	EntityViewInfo newviewInfo = (EntityViewInfo)evi.clone();
    	FilterInfo filter = new FilterInfo();
    	if(getUIContext().get(COST_ACCOUNT_NODE) != null){
    		DefaultKingdeeTreeNode costNode=(DefaultKingdeeTreeNode)getUIContext().get(COST_ACCOUNT_NODE);
    		CostAccountInfo cainfo = (CostAccountInfo)costNode.getUserObject();
    		if(costNode.isLeaf()){
    			filter.getFilterItems().add(new FilterItemInfo("costAccount.id",cainfo.getId().toString()));
    		}else{
    			filter.getFilterItems().add(new FilterItemInfo("costAccount.longNumber",cainfo.getLongNumber()+"%",CompareType.LIKE));
    		}
    	}
    	if(newviewInfo.getFilter()!=null){
    		try {
				newviewInfo.getFilter().mergeFilter(filter,"and");
			} catch (BOSException e) {
				handUIException(e);
			}
    	}else{
    		newviewInfo.setFilter(filter);
    	}
    	return super.getQueryExecutor(arg0, newviewInfo);
    }
    
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
    	if(getUIContext().get(COST_ACCOUNT_NODE) == null){
    		MsgBox.showInfo("请选择一个成本科目！");
    		return;
    	}
    	super.actionAddNew_actionPerformed(e);
    }
    
    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }


    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.costindexdb.database.CostAccountPriceIndexFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.costindexdb.database.CostAccountPriceIndexInfo objectValue = new com.kingdee.eas.fdc.costindexdb.database.CostAccountPriceIndexInfo();
		
        return objectValue;
    }

}