/**
 * output package name
 */
package com.kingdee.eas.xr.helper.common;

import javax.swing.tree.DefaultTreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.CtrlUnitFactory;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.port.pm.project.PortProjectFactory;

/**
 * output class name
 */
public class PortProjectTree extends AbstractPortProjectTree
{
    private static final Logger logger = CoreUIObject.getLogger(PortProjectTree.class);
    
    /**
     * output class constructor
     */
    public PortProjectTree() throws Exception
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
    public void onLoad() throws Exception {
    	super.onLoad();
    	PortProjectTreeBuilder projectTreeBuilder = new PortProjectTreeBuilder();
		projectTreeBuilder.build(this, this.kDTree1, actionOnLoad);
    }

}