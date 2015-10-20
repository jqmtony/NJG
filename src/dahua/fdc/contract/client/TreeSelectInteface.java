package com.kingdee.eas.fdc.contract.client;

import javax.swing.JDialog;
import javax.swing.tree.TreeModel;

public interface TreeSelectInteface {
	TreeModel getTree();
	void setUserObject(Object obj);
	Object getUserObject();
	void setVisible(boolean isVisiblee);
}
