/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.framework.ITreeBase;

/**
 * output class name
 */
public abstract class FDCRptBaseUI extends AbstractFDCRptBaseUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCRptBaseUI.class);
    
    /**
     * output class constructor
     */
    public FDCRptBaseUI() throws Exception
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
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionView_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
    	treeMain_valueChanged(null);
    }
 
    /**
     * output actionQuery_actionPerformed
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }
	protected ITreeBase getTreeInterface() throws Exception {
		return CurProjectFactory.getRemoteInstance();
	}
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		String selectObjId = getSelectObjId();
		if (selectObjId == null) {
			return;
		}
		fillTable();
	}
	protected void initWorkButton() {
		super.initWorkButton();
	}
	protected abstract String getEditUIName();
	
	protected void initTree() throws Exception {
		ProjectTreeBuilder treeBuilder = new ProjectTreeBuilder();
		treeBuilder.build(this, this.treeMain, this.actionOnLoad);
		this.treeMain.expandAllNodes(true,
				(TreeNode) ((TreeModel) this.treeMain.getModel()).getRoot());
		this.treeMain.setShowsRootHandles(true);	//Add by zhiyuan_tang 2010/11/02 设置根节点显示节点句柄
		initTable();
		
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		initCtrlListener();
		
	}
	protected void initTable(){
		
	}
	
	protected void fillTable()  throws Exception {
		
	}
	protected void fetchData() throws Exception{
		
	}
	
	protected void setUnionData(){
		
	}
	
	/**
	 * 得到当前选择的对象工程项目,组织ID,或Null
	 * @return 当前选择的对象工程项目,组织ID,或Null
	 */
	protected Object getSelectObj() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			return projectInfo;
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			if (oui.getUnit() == null) {
				return null;
			}
			FullOrgUnitInfo info = oui.getUnit();
			return info;
		}
		return null;
	}
	
	protected String getSelectObjId() {
		Object obj=getSelectObj();
		if(obj!=null){
			return ((IObjectValue)obj).getString("id");
		}
		return null;
	}

	/**
	 * @author yong_zhou modify by zhiqiao_yang at 2010.12.17
	 * @return
	 */
	protected Set getSelectObjLeafIds() {
		return getSelectObjLeafIds(false);
	}

	protected Set getSelectObjLeafIds(boolean isOnlyProject) {
		Set idSet = new HashSet();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
			return idSet;
		}
		getNodeIds(node, idSet, isOnlyProject);
		return idSet;
	}
	protected void getNodeIds(DefaultKingdeeTreeNode node, Set idSet, boolean isOnlyProject) {
		if (node.isLeaf()) {
			if(node.getUserObject() instanceof CurProjectInfo){
				CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
				idSet.add(projectInfo.getId().toString());
			}
			if (!isOnlyProject) {
				if (node.getUserObject() instanceof FullOrgUnitInfo) {
					FullOrgUnitInfo orgUnitInfo = (FullOrgUnitInfo) node.getUserObject();
					idSet.add(orgUnitInfo.getId().toString());
				} else if (node.getUserObject() instanceof OrgStructureInfo) {
					OrgStructureInfo orgUnitInfo = (OrgStructureInfo) node.getUserObject();
					idSet.add(orgUnitInfo.getId().toString());
				}
			}
		}else if(!node.isLeaf()){
			for(int i=0;i<node.getChildCount();i++){
				DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode)node.getChildAt(i);
				getNodeIds(child, idSet, isOnlyProject);
			}
		}
	}
	

	protected CurProjectInfo getSelectProject() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			return projectInfo;
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			return null;
		}
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	protected boolean isSelectLeafPrj(){
		Object obj=getSelectObj();
		if(obj!=null && obj instanceof CurProjectInfo &&((CurProjectInfo)obj).isIsLeaf()){
			return true;
		}
		return false;
	}
	
	/**
	 * 控件事件注册等
	 */
	protected void initCtrlListener(){
	}
	
	private Map paramMap=null;
	/**
	 * 获取参数
	 * @throws Exception
	 */
	protected void fetchParamData() throws Exception {
		CompanyOrgUnitInfo currentFIUnit = SysContext.getSysContext().getCurrentFIUnit();
		String comPK=null;
		if(currentFIUnit!=null&&currentFIUnit.getId()!=null){
			comPK=currentFIUnit.getId().toString();
		}
		paramMap=FDCUtils.getDefaultFDCParam(null, comPK);
	}
	
	/**
	 * 当前公司及集团的所有参数列表
	 * @return
	 * @throws Exception
	 */
	protected Map getParamMap() throws Exception{
		if(paramMap==null){
			fetchParamData();
		}
		return paramMap;
	}
	/**
	 * 参数是否启用
	 * @param paramKey
	 * @return
	 * @throws Exception
	 */
	protected boolean isParamUse(String paramKey) throws Exception {
		if (getParamMap() != null) {
			Object theValue = getParamMap().get(paramKey);
			if (theValue != null) {
				return Boolean.valueOf(theValue.toString()).booleanValue();
			}
		}
		return false;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 描述：是否支持EAS高级统计(EAS800新增的功能)
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2015-4-1
	 */
	// @Override
	protected boolean isSupportEASPivot() {
		// return super.isSupportEASPivot();
		return false;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

}
