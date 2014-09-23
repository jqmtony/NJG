/**
 * output package name
 */
package com.kingdee.eas.bpmdemo.client;

import java.awt.event.*;
import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class ContractDemoListUI extends AbstractContractDemoListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractDemoListUI.class);
    
    /**
     * output class constructor
     */
    public ContractDemoListUI() throws Exception
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
    }
    
    protected String getEditUIModal() {
    	return UIFactoryName.NEWTAB;
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.bpmdemo.ContractDemoFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.bpmdemo.ContractDemoInfo objectValue = new com.kingdee.eas.bpmdemo.ContractDemoInfo();
		
        return objectValue;
    }

}