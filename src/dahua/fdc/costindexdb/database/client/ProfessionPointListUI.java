/**
 * output package name
 */
package com.kingdee.eas.fdc.costindexdb.database.client;

import java.awt.event.*;
import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class ProfessionPointListUI extends AbstractProfessionPointListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProfessionPointListUI.class);
    
    /**
     * output class constructor
     */
    public ProfessionPointListUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	String cuID = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		if(!cuID.equals(OrgConstants.DEF_CU_ID)) {
			actionEdit.setEnabled(false);
			actionRemove.setEnabled(false);
			if(tblMain.getRowCount() == 0)
				actionAddNew.setEnabled(false);
		}
//		tblMain.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
    }
    
    public void onShow() throws Exception {
    	super.onShow();
    	if(tblMain.getRowCount() > 0)
    		actionAddNew.setVisible(false);
    }
    
    protected boolean isIgnoreCUFilter() {
    	return true;
    }
    
    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.costindexdb.database.ProfessionPointFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.costindexdb.database.ProfessionPointInfo objectValue = new com.kingdee.eas.fdc.costindexdb.database.ProfessionPointInfo();
		
        return objectValue;
    }

}