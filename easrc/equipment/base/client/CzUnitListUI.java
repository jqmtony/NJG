/**
 * output package name
 */
package com.kingdee.eas.port.equipment.base.client;

import java.awt.event.*;
import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class CzUnitListUI extends AbstractCzUnitListUI
{
    private static final Logger logger = CoreUIObject.getLogger(CzUnitListUI.class);
    
    /**
     * output class constructor
     */
    public CzUnitListUI() throws Exception
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

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.equipment.base.CzUnitFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.port.equipment.base.CzUnitInfo objectValue = new com.kingdee.eas.port.equipment.base.CzUnitInfo();
		
        return objectValue;
    }

}