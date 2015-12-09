/**
 * output package name
 */
package com.kingdee.eas.fdc.costindexdb.database.client;

import java.awt.event.*;
import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class IndexTypeListUI extends AbstractIndexTypeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(IndexTypeListUI.class);
    
    /**
     * output class constructor
     */
    public IndexTypeListUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	String cuID = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		if(!cuID.equals(OrgConstants.DEF_CU_ID)) {
			actionAddNew.setEnabled(false);
			actionEdit.setEnabled(false);
			actionRemove.setEnabled(false);
		}
    }
    
    protected String getEditUIModal() {
    	return super.getEditUIModal();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.costindexdb.database.IndexTypeFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.costindexdb.database.IndexTypeInfo objectValue = new com.kingdee.eas.fdc.costindexdb.database.IndexTypeInfo();
		
        return objectValue;
    }

}