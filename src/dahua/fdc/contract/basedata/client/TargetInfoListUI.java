/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.ProjectTypeFactory;
import com.kingdee.eas.fdc.basedata.TargetInfoFactory;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.client.MsgBox;
/**
 * output class name
 */
public class TargetInfoListUI extends AbstractTargetInfoListUI
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = CoreUIObject.getLogger(TargetInfoListUI.class);
    
    /**
     * output class constructor
     */
    public TargetInfoListUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
//    	this.tblMain.getColumn("id").getStyleAttributes().setHided(true);
//    	this.tblMain.getColumn("curProject.id").getStyleAttributes().setHided(true);
    	buildProjectTree();
    	
    }
    /**
     * 工程项目树
     * @throws Exception
     */
    private void buildProjectTree() throws Exception {
    	ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();
		projectTreeBuilder.build(this, treeMain, actionOnLoad);
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
    }
    
    protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {
    	
    }
   
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}else if(node.getUserObject() instanceof OrgStructureInfo){
			this.actionAddNew.setEnabled(false);
		}else if(node.getUserObject() instanceof CurProjectInfo){
			CurProjectInfo project = (CurProjectInfo) node.getUserObject();
			if(project.isIsLeaf()) {
				this.actionAddNew.setEnabled(true);
			}else {
				this.actionAddNew.setEnabled(false);
			}
		}
		buildTreeFilter();	
		this.execQuery();
    }

    /**
	 * 树的filter。copy from月度付款计划
	 * @see com.kingdee.eas.fdc.finance.client.FDCProDepConPayPlanListUI#treeSelectChange
	 */
	protected void buildTreeFilter() {
		FilterInfo filter = new FilterInfo();
	
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node != null && node.getUserObject() instanceof CoreBaseInfo) {
			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) node.getUserObject();
			// 选中组织节点
			String orgUnitLongNumber = null;
			if (projTreeNodeInfo instanceof OrgStructureInfo) {
				// 根据组织找到工程项目Ids
				orgUnitLongNumber = ((OrgStructureInfo) projTreeNodeInfo).getUnit().getLongNumber();
				filter.getFilterItems().add(
						new FilterItemInfo("curProject.fullOrgUnit.longNumber", orgUnitLongNumber+"%",CompareType.LIKE));
			} else if (projTreeNodeInfo instanceof CurProjectInfo) {// 选中项目节点
				CurProjectInfo project = (CurProjectInfo) projTreeNodeInfo;
				filter.getFilterItems().add(//长编码不重复，一个过滤条件即可
						new FilterItemInfo("curProject.longNumber", project.getLongNumber()+"%",CompareType.LIKE));
			}
			this.mainQuery.setFilter(filter);
		}
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) treeMain.getModel().getRoot();
		if (root == node) {
			tblMain.removeRows();
			tblMain.getSelectManager().removeAll();
		}
	}
    
    
    
	protected ITreeBase getTreeInterface() throws Exception {
		
		return null;
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return TargetInfoFactory.getRemoteInstance();
	}
	
	protected void initTree() throws Exception {

	}
	
	protected String getEditUIName() {
		return TargetInfoEditUI.class.getName();
	}

	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}
	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
//		this.tblMain.getColumn("id").getStyleAttributes().setHided(true);
//    	this.tblMain.getColumn("curProject.id").getStyleAttributes().setHided(true);
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}
	
	/**
	 * 新增
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			MsgBox.showWarning(this, "请选择一个工程项目树结点！");
			return;
		}
		if (OrgViewUtils.isTreeNodeDisable(node)) {
			return;
		}

		prepareUIContext(e);
		super.actionAddNew_actionPerformed(e);
	}
	
	
	protected void prepareUIContext(ActionEvent e) {
		//如果当前焦点在左边树上，则取树上当前选中节点为操作对象
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		ItemAction act = getActionFromActionEvent(e);

		if (node == null) {
			return;
		}
		if (OrgViewUtils.isTreeNodeDisable(node)) {
			return;
		}
		
		if (node.getUserObject() != null) {
			if (node.getUserObject() instanceof CurProjectInfo) {
				CurProjectInfo info = (CurProjectInfo) node.getUserObject();
				if (!act.equals(actionView)) {
					try{
						if(info.getProjectType()!=null&&info.getProjectType().getId()!=null){
							info.setProjectType(ProjectTypeFactory.getRemoteInstance().getProjectTypeInfo(new ObjectUuidPK(info.getProjectType().getId())));
						}
					}catch(Exception exc){
						handUIExceptionAndAbort(exc);
					}
				}
				this.getUIContext().put("curProject", info);
			} else{
				return;
			}
		} else {
			return;
		}
	}
	
	/**
	 * 忽略CU隔离
	 */
	protected boolean isIgnoreCUFilter(){
	      return true;
	 }
	
}