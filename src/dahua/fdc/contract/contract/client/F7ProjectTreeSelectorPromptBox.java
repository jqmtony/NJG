package com.kingdee.eas.fdc.contract.client;


import java.awt.Color;
import java.util.List;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.tree.TreeModel;

import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.eas.base.uiframe.client.UIModelDialog;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.util.SysUtil;

public class F7ProjectTreeSelectorPromptBox extends KDCommonPromptDialog implements TreeSelectInteface {
	public TreeModel tree;

	public String companyPath = null;

	public Object[] selectObjects;
	
	public F7ProjectTreeSelectorPromptBox(JComponent ui,TreeModel tree)throws Exception{
		super(FDCClientHelper.getFrameAncestor(ui),"工程项目树选择");
		if (tree == null) {
			SysUtil.abort();
		}
		this.setTree(tree);
//		this.initDialog();
	}
	public F7ProjectTreeSelectorPromptBox(JComponent ui,TreeModel tree,String companyPath,Object []selectObjects)throws Exception{
		super(FDCClientHelper.getFrameAncestor(ui),"工程项目树选择");
		if (tree == null) {
			SysUtil.abort();
		}
		this.setTree(tree);
		this.companyPath = companyPath;
		this.selectObjects = selectObjects;
//		this.initDialog();
	}
	/**
	 * 初始化对话框
	 * 
	 * @throws Exception
	 * 
	 * @throws Exception
	 */
	protected void initDialog() throws Exception {
		TreeSelectUI selectUI = null;
		if (companyPath == null || FDCHelper.isEmpty(selectObjects)) {
			selectUI = new TreeSelectUI(this);
		} else {
			selectUI = new TreeSelectUI(this, companyPath, selectObjects);
		}
		selectUI.setPreferredSize(selectUI.getBounds().getSize());
//		this.getContentPane().setLayout(new BorderLayout());
//		this.getContentPane().setLayout(ScrollPaneLayout);
//		this.getContentPane().add(selectUI);//, BorderLayout.CENTER);
//		this.setSize(640, 480);
//		CtrlSwingUtilities.centerWindow(this);
//		this.setResizable(false);
//		this.setTitle(selectUI.getUITitle());
		UIModelDialog diag=new UIModelDialog(selectUI,this);
		diag.show();
	}
	
	public void setUITitle(boolean isPrice){
		
	}
	public void show() {
		try {
			initDialog();
		} catch (Exception e) {
			e.printStackTrace();
			SysUtil.abort();
		}
//		this.setVisible(true);
//		super.show();
//		Map context = getUIContext();
//		IUIFactory uiFactory = null;
//		try {
//			uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
//			classDlg = uiFactory.create("com.kingdee.eas.fdc.aimcost.client.TemplateMeasureCostF7UI", context,null,null,WinStyle.SHOW_ALL);
////			((Window)classDlg);
//			((JDialog)classDlg).setResizable(false);
//			classDlg.show();
//		} catch (BOSException ex) {
//			ExceptionHandler.handle(this, ex);
//			return;
//		}
	}
	
	/**
	 * 弹出选框关闭后，返回是否取消
	 * 
	 * @return
	 */
	public boolean isCanceled()
	{
		return getUserObject()==null;
	}
	
	public Object getData(){
		Object obj = getUserObject();
		if(obj instanceof List){
			return ((List)obj).toArray();
		}
		return obj;
	}
	
	public TreeModel getTree() {
		return tree;
	}

	public void setTree(TreeModel tree) {
		this.tree = tree;
	}
	public TreeModel getSelectTree(){
		TreeModel treeModel = ((TreeSelectUI)this.getContentPane().getComponent(0)).treeSelected.getModel();
		DefaultKingdeeTreeNode root=(DefaultKingdeeTreeNode) treeModel.getRoot();
		if(root.getChildCount()==0){
			return null;
		}
		DefaultKingdeeTreeNode newRoot=(DefaultKingdeeTreeNode) ((DefaultKingdeeTreeNode)root.getChildAt(0)).clone();
		this.cloneTree(newRoot,(DefaultKingdeeTreeNode) root.getChildAt(0));
		TreeModel newTree=new KingdeeTreeModel(newRoot);
		return newTree;
	}
	private void cloneTree(DefaultKingdeeTreeNode root,
			DefaultKingdeeTreeNode oriRoot) {
		for (int i = 0; i < oriRoot.getChildCount(); i++) {
			DefaultKingdeeTreeNode oriChild = (DefaultKingdeeTreeNode) oriRoot
					.getChildAt(i);
			DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode) oriChild
					.clone();
			root.add(child);
			cloneTree(child, oriChild);
		}
	}
	
	public static void setDisableSelectNodeByProjectType(DefaultKingdeeTreeNode treeNode,Set projectTypeIdSet) {
		if (treeNode == null || projectTypeIdSet == null||projectTypeIdSet.size()==0)
			return;
		Object object = treeNode.getUserObject();
		if(object instanceof CurProjectInfo){
			CurProjectInfo prj=(CurProjectInfo)object;
			if(prj.getProjectType()!=null&&projectTypeIdSet.contains(prj.getProjectType().getId().toString())){
//				treeNode.setTextColor(Color.gray);
			}else{
				treeNode.setTextColor(Color.gray);
				treeNode.setCheckBoxEnabled(false);
			}
		}
		for (int i = 0; i < treeNode.getChildCount(); i++) {
			DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode) treeNode
					.getChildAt(i);
			setDisableSelectNodeByProjectType(child, projectTypeIdSet);
		}
	}
}
