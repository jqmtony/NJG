/**
 * output package name
 */
package com.kingdee.eas.fdc.costindexdb.database.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;

/**
 * output class name
 */
public class BuildNumberEditUI extends AbstractBuildNumberEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(BuildNumberEditUI.class);
    
    /**
     * output class constructor
     */
    public BuildNumberEditUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
    	super.onLoad();
    	prmtcurProject.setEnabled(false);
    }
    
    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.costindexdb.database.BuildNumberFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.costindexdb.database.BuildNumberInfo objectValue = new com.kingdee.eas.fdc.costindexdb.database.BuildNumberInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
        objectValue.setCurProject((CurProjectInfo)getUIContext().get("treeSelectedObj"));
        return objectValue;
    }

}