/**
 * output package name
 */
package com.kingdee.eas.fdc.costindexdb.client;

import java.awt.event.*;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class ProjectSettleUI extends AbstractProjectSettleUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectSettleUI.class);
    private static final String TREE_SELECTED_OBJ = "treeSelectedObj";
    /**
     * output class constructor
     */
    public ProjectSettleUI() throws Exception
    {
        super();
    }
    
    @Override
    public void onLoad() throws Exception {
    	super.onLoad();
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
//		authorizedOrgs = (Set) ActionCache.get("FDCBillListUIHandler.authorizedOrgs");
//		if (authorizedOrgs == null) {
//			authorizedOrgs = new HashSet();
//			Map orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
//					new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()), OrgType.CostCenter, null, null, null);
//			if (orgs != null) {
//				Set orgSet = orgs.keySet();
//				Iterator it = orgSet.iterator();
//				while (it.hasNext()) {
//					authorizedOrgs.add(it.next());
//				}
//			}
//		}
		if (treeMain.getRowCount() > 0) {
			treeMain.setSelectionRow(0);
			treeMain.expandPath(treeMain.getSelectionPath());
			
		}
	}
    
    protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
    	DefaultKingdeeTreeNode projectNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
    	if(isSelectedProjectNode()) {
			getUIContext().put(TREE_SELECTED_OBJ,projectNode.getUserObject());
			//根据选中的工程项目展示列表 mainQuery
    	}
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

}