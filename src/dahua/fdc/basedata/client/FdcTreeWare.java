package com.kingdee.eas.fdc.basedata.client;

import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;

/**
 * ���ز���֯�� ���
 * 
 * @author ����
 * @email skyiter@live.com
 */
public interface FdcTreeWare {
	
	/**
	 * ȡ����
	 * 
	 * @return
	 */
	public KDTree getTree();

	/**
	 * ȡ��ѡ��ڵ�
	 * 
	 * @return
	 */
	public DefaultKingdeeTreeNode getSelectedNode();

	/**
	 * ȡ��ѡ��ڵ����
	 * 
	 * @return
	 */
	public Object getSelectedNodeObj();
	
	/**
	 * �Ƿ�UI�Ѿ�����
	 * 
	 * @return
	 */
	public boolean isHasUILoaded();

}
