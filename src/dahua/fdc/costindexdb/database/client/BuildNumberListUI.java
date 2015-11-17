/**
 * output package name
 */
package com.kingdee.eas.fdc.costindexdb.database.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.encryptalgorithm.DesCrypto;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class BuildNumberListUI extends AbstractBuildNumberListUI
{
    private static final Logger logger = CoreUIObject.getLogger(BuildNumberListUI.class);
    private static final String TREE_SELECTED_OBJ = "treeSelectedObj";
    private static final String ORG_OBJ = "treeOrg";
    private Set ids = new HashSet();
    /**
     * output class constructor
     */
    public BuildNumberListUI() throws Exception
    {
        super();
    }

    @Override
    public void onLoad() throws Exception {
    	// TODO Auto-generated method stub
    	super.onLoad();
//    	com.kingdee.eas.util.CryptoTean.decrypt("authadmin","mEe54Mjx19LfLiL2SzFNZw==");
//    	DesCrypto des = new DesCrypto();
//    	String user = "";
//        String pwd = "0505";
//        String res = des.encrypt(user, pwd);
////        logger.info((new StringBuilder()).append("res is:").append(res).toString());
//        String lres = des.decrypt(user, res);
    	treeMain.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    treeMain_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
    	buildProjectTree();
    }
    
    public void buildProjectTree() throws Exception {
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();
		projectTreeBuilder.build(this, treeMain, actionOnLoad);
		if (treeMain.getRowCount() > 0) {
			treeMain.setSelectionRow(0);
			treeMain.expandPath(treeMain.getSelectionPath());
			
		}
	}
    
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
    	if(getUIContext().get(TREE_SELECTED_OBJ) == null){
			MsgBox.showInfo("请选择具体的项目！");
			return;
		}
    	super.actionAddNew_actionPerformed(e);
    }
    
    protected void getChildProject(DefaultKingdeeTreeNode treeNode, Set ids) {
		if (treeNode != null && treeNode.isLeaf()) {
			Object userObject = treeNode.getUserObject();
			if (userObject != null && userObject instanceof CurProjectInfo) {
				ids.add(((CurProjectInfo) userObject).getId().toString());
			}
			return;
		} else {
			if (treeNode == null) {
				return;
			}
			Object userObject = treeNode.getUserObject();
			if (userObject == null) {
				return;
			}
			if (userObject != null && userObject instanceof CurProjectInfo) {
				ids.add(((CurProjectInfo) userObject).getId().toString());
			}
			for (int i = 0; i < treeNode.getChildCount(); i++) {
				DefaultKingdeeTreeNode childNode = (DefaultKingdeeTreeNode) treeNode.getChildAt(i);
				getChildProject(childNode, ids);
			}
		}
	}
    
    protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
    	DefaultKingdeeTreeNode projectNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
    	if(isSelectedProjectNode()) {
			getUIContext().put(TREE_SELECTED_OBJ,projectNode.getUserObject());
			//根据选中的工程项目展示列表 mainQuery
			refreshList();
//			if(tblMain.getRowCount3() > 0)
//				actionAddNew.setEnabled(false);
//			else
//				actionAddNew.setEnabled(true);
    	}else{
    		getUIContext().put(TREE_SELECTED_OBJ,null);
    		getUIContext().put(ORG_OBJ,getTreeNode(e.getNewLeadSelectionPath()));
    		//展示所有列表
    		refreshList();
//    		actionAddNew.setEnabled(false);
    	}
    }
    
    protected IQueryExecutor getQueryExecutor(IMetaDataPK arg0,EntityViewInfo evi) {
    	EntityViewInfo newviewInfo = (EntityViewInfo)evi.clone();
    	FilterInfo filter = new FilterInfo();
    	if(getUIContext().get(TREE_SELECTED_OBJ) != null){
    		String pid = ((CurProjectInfo)getUIContext().get(TREE_SELECTED_OBJ)).getId().toString();
    		filter.getFilterItems().add(new FilterItemInfo("curProject.id",pid));
    	}else if(getUIContext().get(ORG_OBJ) != null){
    		ids.clear();
    		getChildProject((DefaultKingdeeTreeNode)getUIContext().get(ORG_OBJ),ids);
    		if(ids.size() > 0)
    			filter.getFilterItems().add(new FilterItemInfo("curProject.id",ids,CompareType.INCLUDE));
    		else
    			filter.getFilterItems().add(new FilterItemInfo("curProject.id","999"));
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
    
    private boolean isSelectedProjectNode() {
		boolean result = false;
		DefaultKingdeeTreeNode treeNode = getTreeNode(treeMain.getSelectionPath());
		if (treeNode != null && treeNode.isLeaf()) {
			if (treeNode.getUserObject() instanceof CurProjectInfo) {
				result = true;
			}
		}
		return result;
	}
    
    public static DefaultKingdeeTreeNode getTreeNode(TreePath path) {
		return path == null ? null : (DefaultKingdeeTreeNode) path.getLastPathComponent();
	}
    
    protected boolean isIgnoreCUFilter() {
    	return super.isIgnoreCUFilter();
    }
    
    protected String getEditUIModal() {
    	return super.getEditUIModal();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.costindexdb.database.BuildNumberFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.costindexdb.database.BuildNumberInfo objectValue = new com.kingdee.eas.fdc.costindexdb.database.BuildNumberInfo();
		
        return objectValue;
    }

}