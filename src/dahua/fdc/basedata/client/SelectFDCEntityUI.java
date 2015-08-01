/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;

import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.metadata.MetaDataTypeList;
import com.kingdee.bos.metadata.view.IBriefViewTreeNode;
import com.kingdee.bos.metadata.view.MetaDataBriefInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.SubSystemUtils;

/**
 * ѡ�񷿵ز�ʵ�嵥�ݶ���
 * @author owen_wen
 * @date 2012-12-25
 */
public class SelectFDCEntityUI extends AbstractSelectFDCEntityUI {
    private static final Logger logger = CoreUIObject.getLogger(SelectFDCEntityUI.class);
    private static final String BIZENTITY_VIEWNAME = "com_kingdee_eas_base_subsystemEntity";
    private boolean isCanceled = true;
    
    public SelectFDCEntityUI() throws Exception {
        super();
    }
    
    public void onLoad() throws Exception {
		super.onLoad();

		this.setUITitle("ѡ�񷿵ز�ҵ�񵥾�");
		initEntityTree();
	}

	/**
	 * ��ʼ�����ز�ʵ���� 
	 * @Author��owen_wen
	 * @CreateTime��2012-12-25
	 */
    private void initEntityTree() {
		DefaultKingdeeTreeNode root = getKDTreeNodeAfterHandle(SubSystemUtils.getSubSystemByName(BIZENTITY_VIEWNAME,
				MetaDataTypeList.ENTITY));
		
		if (root != null) {
			((KingdeeTreeModel) this.fdcEntityTree.getModel()).setRoot(root);
		}
		fdcEntityTree.expandOnLevel(3); // Ĭ��Ҫ�������ز�������ģ��
	}

	/**
	 * ���˵��Ƿ��ز���ʵ��
	 * @see SubSystemUtils.getKeTreeNodAfterHandle() 
	 * @Author��owen_wen
	 * @CreateTime��2012-12-25
	 */
	public DefaultKingdeeTreeNode getKDTreeNodeAfterHandle(IBriefViewTreeNode root) {
		if (root.getChildCount() == 0) {
			return getNode(root);
		} else {
			DefaultKingdeeTreeNode newRoot = getNode(root);
			IBriefViewTreeNode node = null;
			for (int i = 0; i < root.getChildCount(); i++) {
				node = (IBriefViewTreeNode) root.getChildAt(i);
				// ֻ��Ҫ�����ز����ģ��������˵�
				if (node.getPackageName().equals("com.kingdee.eas") || node.getPackageName().startsWith("com.kingdee.eas.fdc")) {
					newRoot.add(getKDTreeNodeAfterHandle(node));
				}
			}
			return newRoot;
		}
	}
	
	/**
	 * 
	 * @see SubSystemUtils.getNode()
	 * @param node
	 * @Author��owen_wen
	 * @CreateTime��2012-12-25
	 */
	private DefaultKingdeeTreeNode getNode(IBriefViewTreeNode node) {
		DefaultKingdeeTreeNode newNode = new DefaultKingdeeTreeNode();
		newNode.setText(node.getAlias());
		newNode.setUserObject(node.getMetaDataObjectBriefInfo());
		return newNode;
	}

	/**
	 * ��ȡѡ�нڵ��MetaDataBriefInfo
	 * @return
	 */
	public MetaDataBriefInfo getMetaDataBrief() {
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode) fdcEntityTree.getLastSelectedPathComponent();
    	if (treeNode != null && treeNode.getUserObject() != null && treeNode.getUserObject() instanceof MetaDataBriefInfo) {
			return (MetaDataBriefInfo)treeNode.getUserObject();
    	}
    	return null;
	}
	
	protected void fdcEntityTree_valueChanged(TreeSelectionEvent e) throws Exception {

		MetaDataBriefInfo mdInfo = getMetaDataBrief();
		if (mdInfo != null) {
			entityFullName.setText(mdInfo.getMetaDataPK().getFullName());
		}
	}
	
	public boolean isCanceled() {
		return this.isCanceled;
	}
	
	public void actionBtnConfrim_actionPerformed(ActionEvent e) throws Exception {
		MetaDataBriefInfo metaDataBriefInfo = getMetaDataBrief();
		if (metaDataBriefInfo != null && !metaDataBriefInfo.getMetaDataType().toString().equals("enty")) {
			FDCMsgBox.showInfo(this, "��ѡ��ҵ�񵥾ݶ���");
			this.abort();
		}
		this.isCanceled = false;
		this.disposeUIWindow();
	}

	public void actionBtnCancel_actionPerformed(ActionEvent e) throws Exception {
		this.isCanceled = true;
		this.disposeUIWindow();
	}
}