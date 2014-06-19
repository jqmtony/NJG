/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.BizEnumValueDTO;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.OperationPhasesInfo;
import com.kingdee.eas.fdc.basedata.ProjectBaseInfo;
import com.kingdee.eas.fdc.basedata.ProjectDefectStoreFactory;
import com.kingdee.eas.fdc.basedata.StateEnum;
import com.kingdee.eas.framework.AbstractCoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ProjectDefectStoreTreeListUI extends AbstractProjectDefectStoreTreeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectDefectStoreTreeListUI.class);
    
    /**
     * output class constructor
     */
    public ProjectDefectStoreTreeListUI() throws Exception
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
     
     Set set = new HashSet();
     Set productTypeSet = new HashSet();
     Map map = new HashMap();
    protected void tblMain_editValueChanged(KDTEditEvent e) throws Exception {
    	super.tblMain_editValueChanged(e);
    	int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
	    IRow row   = tblMain.getRow(rowIndex);
	    //得到行对象
	    String proDefectID  =(String)row.getCell("id").getValue();
	    String countNumber =(String)row.getCell("countNumber").getValue();
		if(Boolean.TRUE.equals(e.getValue())){  
			 map.put(proDefectID, countNumber);
	    }
		else{
			map.remove(proDefectID);
		}
	}
    /*转入项目归集界面*/
	public void actionProjectToProduct_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionProjectToProduct_actionPerformed(e);
	    if(map.size()>0){
		      UIContext uiContext = new UIContext(this);
		      uiContext.put("proDefectID", map);//缺陷归集界面接收 proDefectID
		      IUIWindow uiWindow = null;
			  uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ProjectDefectStoreListUI.class.getName(),uiContext,null,OprtState.VIEW);
			  uiWindow.show();
		}else{
		      MsgBox.showInfo("请至少选中一条未归集的项目缺陷进行缺陷归集！");
		      SysUtil.abort();
		}
	}
	
	/**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        super.tblMain_tableSelectChanged(e);
    }
    
    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
        super.treeMain_valueChanged(e);
        this.refresh(null);
    }
    
    protected ICoreBase getBizInterface() throws Exception {
    	return ProjectDefectStoreFactory.getRemoteInstance();
    }
    
    public void onLoad() throws Exception {
    	initTree();
    	super.onLoad();
    	this.btnProjectToProduct.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_unite"));//缺陷归集按钮设置图片
    }
    /**
     * 树
     */
	private void initTree() throws Exception {
		ProjectTreeBuilderForXH projectTreeBuilder = new ProjectTreeBuilderForXH();
		projectTreeBuilder.build(this, treeMain, actionOnLoad);
		treeMain.expandAllNodes(true, (TreeNode)treeMain.getModel().getRoot());
	}
	
	protected String getEditUIName() {
    	return ProjectDefectStoreEditUI.class.getName();
    }
	public DefaultKingdeeTreeNode getSelectedTreeNode()
    {
        return (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
    }
	  /**
	 * 描述：为当前单据的新增、编辑、查看准备Context
	 */
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		ItemAction act = getActionFromActionEvent(e);
		if (act.equals(actionAddNew)) {
			Object userObject2 = getSelectedTreeNode().getUserObject();
			EntityViewInfo viewInfo = new EntityViewInfo();//建立一个实体封装对象。 
		    FilterInfo filter = new FilterInfo();//建立一个过滤对象。 
			filter.getFilterItems().add(new FilterItemInfo("projectItem.id",((AbstractCoreBaseInfo) userObject2).getId().toString()));
			viewInfo.setFilter(filter);
			if(userObject2 instanceof ProjectBaseInfo){
				BOSUuid projId = ((ProjectBaseInfo) userObject2).getId();
				uiContext.put("ID", projId);
			}
		}
	}
	 /**
	 * 选中节点时过滤
	 */
	protected IQueryExecutor getQueryExecutor(IMetaDataPK pk,
			EntityViewInfo viewInfo) {
		
		EntityViewInfo view = (EntityViewInfo)viewInfo.clone();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		FilterInfo  filter = new FilterInfo();
		if(getSelectedTreeNode() != null && node.getUserObject() instanceof ProjectBaseInfo )
		{
			ProjectBaseInfo info = (ProjectBaseInfo)node.getUserObject();
			filter.getFilterItems().add(new FilterItemInfo("projectItem.id",info.getId().toString()));
		}else if(getSelectedTreeNode() != null && node.getUserObject() instanceof FullOrgUnitInfo) {
			FullOrgUnitInfo orgInfo = (FullOrgUnitInfo)node.getUserObject();
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",orgInfo.getId().toString()));
		}
		else if(getSelectedTreeNode() != null && node.getUserObject() instanceof OperationPhasesInfo){
			OperationPhasesInfo Info = (OperationPhasesInfo)node.getUserObject();
			filter.getFilterItems().add(new FilterItemInfo("operation.id",Info.getId().toString()));
		}
		view.setFilter(filter);
		return super.getQueryExecutor(pk, view);
	}
	
	protected void afterTableFillData(KDTDataRequestEvent e) {
		super.afterTableFillData(e);
		setTableLock();
	}
    /*设置锁住已归集的行*/
	private void setTableLock() {
		tblMain.getStyleAttributes().setLocked(false);
		for(int i = 0 ; i < tblMain.getRowCount() ; i ++){
			IRow row = tblMain.getRow(i);
			String status =  ((BizEnumValueDTO) row.getCell("status").getValue()).getAlias();
			if(StateEnum.Yes.getAlias().equals(status)){
				row.getStyleAttributes().setLocked(true);
				row.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);/*颜色*/
			}
		}
		
	}
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		setTableLock();
	}
}