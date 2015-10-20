/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class TreeSelectUI extends AbstractTreeSelectUI {
	private TreeSelectInteface dialog = null;

	private TreeModel bakTreeModel = null;
	/** 已选择树Model备份 */
	private TreeModel bakSelectedTreeModel = null;

	/**
	 * output class constructor
	 */
	public TreeSelectUI(TreeSelectInteface dialog) throws Exception {
		super();
		initUIContentLayout();
		this.dialog = dialog;
		TreeModel tree = dialog.getTree();
		if (tree == null)
			return;
		this.bakTreeModel=tree;
		setOriTree();
//		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) treeOrigin
//				.getModel().getRoot();
//		treeOrigin.expandAllNodes(true, root);

		KingdeeTreeModel treeModel = (KingdeeTreeModel) treeSelected.getModel();

		if (treeModel.getRoot() != null) {
			KDTreeNode root2 = new KDTreeNode("");
			treeModel.setRoot(root2);
			treeSelected.setRootVisible(false);
			treeSelected.setShowsRootHandles(true);
		}
		dialog.setUserObject(new ArrayList());
		initCtrlListener();
	}

	/**
	 * output class constructor
	 */
	public TreeSelectUI(TreeSelectInteface dialog, String comparePath,
			Object[] selectObjects) throws Exception {
		super();
		initUIContentLayout();
		this.dialog = dialog;
		TreeModel tree = dialog.getTree();
		if (tree == null)
			return;
		this.bakTreeModel=tree;
		setOriTree();
//		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) treeOrigin
//				.getModel().getRoot();
//		treeOrigin.expandAllNodes(true, root);

		KingdeeTreeModel treeModel = (KingdeeTreeModel) treeSelected.getModel();

		if (treeModel.getRoot() != null) {
			KDTreeNode root2 = new KDTreeNode("");
			treeModel.setRoot(root2);
			treeSelected.setRootVisible(false);
			treeSelected.setShowsRootHandles(true);
		}

		if (FDCHelper.isEmpty(selectObjects)) {
			return;
		}
		for (int i = 0; i < selectObjects.length; i++) {
			DefaultKingdeeTreeNode node = this.findNodeByCompany(
					(DefaultKingdeeTreeNode) treeOrigin.getModel().getRoot(),
					comparePath, selectObjects[i]);
			if (node == null) {
				continue;
			}
			if (node.getChildCount() > 0) {
				continue;
			}
			TreePath treePath = new TreePath(node.getPath());
			this.addPath(treePath);
		}
		treeSelected.expandAllNodes(true, (KDTreeNode) treeSelected.getModel()
				.getRoot());
		bakTreeSelected();
		KDTreeNode selectRoot = (KDTreeNode) ((KingdeeTreeModel) treeSelected.getModel()).getRoot();
		java.util.List list = new ArrayList();
		popNode(list, selectRoot);
		dialog.setUserObject(list);
		initCtrlListener();
	}

	private DefaultKingdeeTreeNode findNodeByCompany(
			DefaultKingdeeTreeNode des, String comparePath, Object selectObject)
			throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		if (des == null || selectObject == null)
			return null;
		Object object =des.getUserObject();
		String[] pros = comparePath.split(",");
		for (int i = 0; i < pros.length; i++) {
			Class c = object.getClass();
			Method[] methods = c.getMethods();
			Method method=null;
			for (int j = 0; j < methods.length; j++) {
				Method met = methods[j];
				if(met.getName().equals(pros[i].trim())){
					method=met;
				}
			}
			if(method==null){
				break;
			}
			object = method.invoke(object, new Object[] {});
		}
		if (object != null && object.equals(selectObject))
			return des;
		for (int i = 0; i < des.getChildCount(); i++) {
			DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode) des
					.getChildAt(i);
			if ((child = findNodeByCompany(child, comparePath, selectObject)) != null)
				return child;
		}
		return null;
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		super.actionCancel_actionPerformed(e);
		// 恢复
		restoreTreeSelected();

		dialog.setVisible(false);
		dialog.setUserObject(null);
		super.actionCancel_actionPerformed(e);
	}

	public void actionConfirm_actionPerformed(ActionEvent e) throws Exception {

		TreeModel tree = (KingdeeTreeModel) treeSelected.getModel();
		KDTreeNode root = (KDTreeNode) tree.getRoot();
		java.util.List list = new ArrayList();
		popNode(list, root);
		dialog.setVisible(false);
		dialog.setUserObject(list);
		// if (root.getChildCount() == 0) {
		// dialog.setUserObject(null);
		// }
		super.actionConfirm_actionPerformed(e);
		// 备份
		bakTreeSelected();
	}

	private void popNode(java.util.List list, DefaultKingdeeTreeNode root) {
		for (Enumeration c = root.children(); c.hasMoreElements();) {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) c
					.nextElement();
			if(node.isLeaf()) {
				list.add(node.getUserObject());
			}
			popNode(list, node);
		}
	}

	/**
	 * 描述：备份选择的树Model，以便在用户更改了选择，点Cancel之后恢复
	 * 
	 * liupd 创建时间：2005-4-1
	 */
	private void bakTreeSelected() {
		if (treeSelected.getModel().getRoot() != null) {
			KDTreeNode root2 = new KDTreeNode("");
			cloneTree(root2, (KDTreeNode) treeSelected.getModel().getRoot());
			bakSelectedTreeModel = new KingdeeTreeModel(root2);
		}
	}

	/**
	 * 
	 * 描述：点Cancel时，恢复修改前的Model
	 * 
	 * @author:liupd 创建时间：2005-8-31
	 *               <p>
	 */
	private void restoreTreeSelected() {
		// 用户取消操作，如果已有备份，则要恢复前一次的备份
		if (bakSelectedTreeModel != null) {
			KDTreeNode root2 = new KDTreeNode("");
			// 克隆一个新的，以免修改后又影响备份的Model
			cloneTree(root2, (KDTreeNode) bakSelectedTreeModel.getRoot());
			treeSelected.setModel(new KingdeeTreeModel(root2));
			treeSelected.expandAllNodes(true, (TreeNode) treeSelected
					.getModel().getRoot());
		} else { // 没有备份，表明是第一次选择就取消，则恢复空白
			KDTreeNode root = new KDTreeNode("");
			((KingdeeTreeModel) treeSelected.getModel()).setRoot(root);
		}
	}

	public void onLoad() throws Exception {
		super.onLoad();
	}

	public void actionAdd_actionPerformed(ActionEvent e) throws Exception {
/*		int[] selectionRows = treeOrigin.getSelectionRows();
		List selectList=new ArrayList();
		KDTreeNode root = (KDTreeNode)treeOrigin.getModel().getRoot();
		for(int i=0;i<selectionRows.length;i++){
			DefaultKingdeeTreeNode node=(DefaultKingdeeTreeNode)root.getChildAt(selectionRows[i]);
			if(!isDisableSelectNode(node)){
				selectList.add(new Integer(selectionRows[i]));
			}
		}
		int [] rows=new int[selectList.size()];
		for(int i=0;i<rows.length;i++){
			rows[i]=((Integer)selectList.get(i)).intValue();
		}
		
		treeOrigin.setSelectionRows(rows);
		*/
		TreePath[] tps = treeOrigin.getSelectionPaths();
		if (tps == null)
			return;

		for (int i = 0; i < tps.length; i++) {
			TreePath tp = tps[i];
//			for(int j=0;j<tp.getPathCount();j++){
//				DefaultKingdeeTreeNode node=(DefaultKingdeeTreeNode)tp.getPathComponent(i);
//				
//				if(node!=null&&node.isLeaf()&&node.getTextColor().equals(Color.gray)){
//					return;
//				}
//			}
			addPath(tp);
		}
		KDTreeNode root = (KDTreeNode) treeSelected.getModel().getRoot();
		removeDisableNode(root);
		((KingdeeTreeModel) treeSelected.getModel()).nodeStructureChanged(root);
		treeSelected.expandAllNodes(true, root);
		super.actionAdd_actionPerformed(e);
	}

	/**
	 * @param tp
	 */
	private void addPath(TreePath tp) {
		DefaultKingdeeTreeNode selectedNode = (DefaultKingdeeTreeNode) tp
				.getLastPathComponent();
		// 如果已经有该节点，先删除掉
		KDTreeNode rightRoot = (KDTreeNode) ((KingdeeTreeModel) treeSelected
				.getModel()).getRoot();
		DefaultKingdeeTreeNode findNode = findNode(selectedNode, rightRoot);
		if (findNode != null) {
			DefaultKingdeeTreeNode parent = (DefaultKingdeeTreeNode) findNode
					.getParent();
			findNode.removeFromParent();
			((KingdeeTreeModel) treeSelected.getModel())
					.nodeStructureChanged(parent);
		}

		DefaultKingdeeTreeNode toNode = null;
		for (int j = 0; j < tp.getPathCount(); j++) {
			// 从根开始搜索，如果没有，把整条TreePath加过去，如果有，裁减掉根，从下一个节点开始
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) tp
					.getPathComponent(j);
			DefaultKingdeeTreeNode temp = null;
			if ((temp = findNode(node, (DefaultKingdeeTreeNode) treeSelected
					.getModel().getRoot())) == null)
				addTo(toNode, tp, j);
			else
				toNode = temp;
		}
	}

	private DefaultKingdeeTreeNode findNode(DefaultKingdeeTreeNode source,
			DefaultKingdeeTreeNode dest) {
		if (source == null || dest == null)
			return null;

		if (source.getUserObject() != null && dest.getUserObject() != null
				&& source.getUserObject().equals(dest.getUserObject()))
			return dest;

		for (int i = 0; i < dest.getChildCount(); i++) {
			DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode) dest
					.getChildAt(i);
			if ((child = findNode(source, child)) != null)
				return child;
		}
		return null;
	}

	private void addTo(DefaultKingdeeTreeNode toNode, TreePath tp, int from) {
		DefaultKingdeeTreeNode root = null;
		while (from < tp.getPathCount()) {
			DefaultKingdeeTreeNode oriChild = ((DefaultKingdeeTreeNode) tp
					.getPathComponent(from));
			DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode) oriChild
					.clone();

			if (from == tp.getPathCount() - 1) {
				cloneTree(child, oriChild);
			}
			if (toNode == null) {
				root = (DefaultKingdeeTreeNode) ((KingdeeTreeModel) treeSelected
						.getModel()).getRoot();
				// root.add(child);
				((KingdeeTreeModel) treeSelected.getModel()).insertNodeInto(
						child, root, 0);
			} else {
				// 找到应该插入的位置
				DefaultKingdeeTreeNode sibling = oriChild, found = null;
				while ((sibling = (DefaultKingdeeTreeNode) sibling
						.getNextSibling()) != null) {
					found = findNode(sibling, toNode);
					if (found != null)
						break;
				}
				if (found != null)
					toNode.insert(child, toNode.getIndex(found));
				else
					toNode.add(child);

				((KingdeeTreeModel) treeSelected.getModel())
						.nodeStructureChanged(toNode);
			}
			toNode = child;
			from++;
		}
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

	public void actionDelete_actionPerformed(ActionEvent e) throws Exception {
		TreePath[] tps = treeSelected.getSelectionPaths();
		if (tps == null)
			return;
		for (int i = 0; i < tps.length; i++) {
			TreePath tp = tps[i];
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) tp
					.getLastPathComponent();
			while (node.getParent() != null
					&& node.getParent().getChildCount() == 1
					&& node.getParent().getParent() != null) {
				tp = tp.getParentPath();
				node = (DefaultKingdeeTreeNode) tp.getLastPathComponent();
			}
			if (node.getParent() != null
					&& node.getParent().getChildCount() == 1
					&& node.getParent().getParent() != null)
				node = (DefaultKingdeeTreeNode) node.getParent();

			((KingdeeTreeModel) treeSelected.getModel())
					.removeNodeFromParent(node);// .nodeStructureChanged(parent);
		}
		super.actionDelete_actionPerformed(e);
	}

	protected Object getReturnObject() {
		return null;
	}

	protected void isIncludeForbid_actionPerformed(ActionEvent e) throws Exception {
		super.isIncludeForbid_actionPerformed(e);
		setOriTree();
	}

	private void setOriTree() {
		if(this.isIncludeForbid.isSelected()){
			this.treeOrigin.setModel(this.bakTreeModel);
		}else{
			this.treeOrigin.setModel(null);
			DefaultKingdeeTreeNode root = ((DefaultKingdeeTreeNode)this.bakTreeModel.getRoot());
			if(root.isCheckBoxEnabled()){
				DefaultKingdeeTreeNode newRoot = (DefaultKingdeeTreeNode) root.clone();
				this.cloneTreeRemoveForbid(newRoot,root);
				TreeModel newModel=new KingdeeTreeModel(newRoot);
				this.treeOrigin.setModel(newModel);
			}
		}
		if(treeOrigin.getModel() == null) return;
		this.treeOrigin.expandAllNodes(true,(TreeNode) this.treeOrigin.getModel().getRoot());
	}
	private void cloneTreeRemoveForbid(DefaultKingdeeTreeNode root,
			DefaultKingdeeTreeNode oriRoot) {
		for (int i = 0; i < oriRoot.getChildCount(); i++) {
			DefaultKingdeeTreeNode oriChild = (DefaultKingdeeTreeNode) oriRoot
					.getChildAt(i);
			if (oriChild.isCheckBoxEnabled()) {
				DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode) oriChild
						.clone();
				root.add(child);
				cloneTreeRemoveForbid(child, oriChild);
			}
		}
	}
	
	private boolean isDisableSelectNode(DefaultKingdeeTreeNode node){
		if(node!=null&&node.isLeaf()&&node.getTextColor().equals(Color.gray)){
			return true;
		}
		return false;
	}
	
	public static void setDisableSelectNode(DefaultKingdeeTreeNode treeNode,
			String comparePath, Object selectObject) {
		if (treeNode == null || selectObject == null)
			return;
		Object object = treeNode.getUserObject();
		String[] pros = comparePath.split(",");
		for (int i = 0; i < pros.length; i++) {
			Class c = object.getClass();
			Method[] methods = c.getMethods();
			Method method = null;
			for (int j = 0; j < methods.length; j++) {
				Method met = methods[j];
				if (met.getName().equals(pros[i].trim())) {
					method = met;
				}
			}
			if (method == null) {
				object = null;
				break;
			}
			try {
				object = method.invoke(object, new Object[] {});
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				SysUtil.abort();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				SysUtil.abort();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
				SysUtil.abort();
			}
		}
		if (object != null && object.equals(selectObject)) {
			treeNode.setTextColor(Color.gray);
		}
		for (int i = 0; i < treeNode.getChildCount(); i++) {
			DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode) treeNode
					.getChildAt(i);
			setDisableSelectNode(child, comparePath, selectObject);
		}
	}
	
	private void removeDisableNode(DefaultKingdeeTreeNode root){
		if(isDisableSelectNode(root)){
			root.removeFromParent();
			return;
		}
		boolean isLeaf=root.isLeaf();
		for(int i=0;i<root.getChildCount();i++){
			DefaultKingdeeTreeNode node=(DefaultKingdeeTreeNode)root.getChildAt(i);
			removeDisableNode(node);
		}
		if(!isLeaf&&root.getChildCount()==0){
			root.removeFromParent();
			return;
		}
	}
	private void initCtrlListener(){
		MouseAdapter mouseAdapter = new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2){
					if(e.getSource().equals(treeOrigin)){
						DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)treeOrigin.getSelectionPath().getLastPathComponent();
						if(node.isLeaf()&&!isDisableSelectNode(node)){
							try {
								actionAdd_actionPerformed(null);
							} catch (Exception e1) {
								e1.printStackTrace();
								SysUtil.abort();
							}
						}
						
					}else if(e.getSource().equals(treeSelected)){
						if(treeSelected.getSelectionPath() == null || treeSelected.getSelectionPath().getLastPathComponent() == null){
							return ;
						}
						DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)treeSelected.getSelectionPath().getLastPathComponent();
						if(node.isLeaf()&&!isDisableSelectNode(node)){
							try {
								actionDelete_actionPerformed(null);
							} catch (Exception e1) {
								e1.printStackTrace();
								SysUtil.abort();
							}
						}
						
					}
					
				}
			}
		};
		treeSelected.addMouseListener(mouseAdapter);
		treeOrigin.addMouseListener(mouseAdapter);
	}
}