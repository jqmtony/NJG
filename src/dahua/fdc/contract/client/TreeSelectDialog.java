package com.kingdee.eas.fdc.contract.client;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.tree.TreeModel;

import com.kingdee.bos.ctrl.swing.KDDialog;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.ctrl.swing.util.CtrlSwingUtilities;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.util.SysUtil;

public class TreeSelectDialog extends KDDialog implements TreeSelectInteface{
	public TreeModel tree;

	public String companyPath = null;

	public Object[] selectObjects;

	public TreeSelectDialog(Component ui, TreeModel tree) throws Exception {
		super(FDCClientHelper.getFrameAncestor(ui), true);
		if (tree == null) {
			SysUtil.abort();
		}
		this.setTree(tree);
		this.initDialog();
	}

	public TreeSelectDialog(Component ui, TreeModel tree, String companyPath,
			Object[] selectObjects) throws Exception {
		super(FDCClientHelper.getFrameAncestor(ui), true);
		if (tree == null) {
			SysUtil.abort();
		}
		this.setTree(tree);
		this.companyPath = companyPath;
		this.selectObjects = selectObjects;
		this.initDialog();
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
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(selectUI, BorderLayout.CENTER);
		this.setSize(640, 480);
		CtrlSwingUtilities.centerWindow(this);
		this.setResizable(false);
		this.setTitle(selectUI.getUITitle());
	}

	public Object showDialog() throws Exception {
		this.show();
		return this.userObject;
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
}
