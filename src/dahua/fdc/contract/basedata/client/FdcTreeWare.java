package com.kingdee.eas.fdc.basedata.client;

import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;

/**
 * 房地产组织树 组件
 * 
 * @author 王正
 * @email skyiter@live.com
 */
public interface FdcTreeWare {
	
	/**
	 * 取得树
	 * 
	 * @return
	 */
	public KDTree getTree();

	/**
	 * 取得选择节点
	 * 
	 * @return
	 */
	public DefaultKingdeeTreeNode getSelectedNode();

	/**
	 * 取得选择节点对象
	 * 
	 * @return
	 */
	public Object getSelectedNodeObj();
	
	/**
	 * 是否UI已经加载
	 * 
	 * @return
	 */
	public boolean isHasUILoaded();

}
