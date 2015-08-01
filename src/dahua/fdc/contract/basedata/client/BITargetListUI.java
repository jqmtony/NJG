/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.BITargetFactory;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class BITargetListUI extends AbstractBITargetListUI
{
    private static final Logger logger = CoreUIObject.getLogger(BITargetListUI.class);
    
    /**
     * output class constructor
     */
    public BITargetListUI() throws Exception
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
    	this.tblMain.getColumn("desc").getStyleAttributes().setHided(true);
    	this.tblMain.getColumn("id").getStyleAttributes().setHided(true);
    	this.tblMain.getColumn("targetId").getStyleAttributes().setHided(true);
    	this.tblMain.getColumn("seq").getStyleAttributes().setHided(true);
    	this.tblMain.getColumn("width").getStyleAttributes().setWeight(200);
    }

	protected ICoreBase getBizInterface() throws Exception {
		return BITargetFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return null;
	}
	
	/**
	 * ∫ˆ¬‘CU∏Ù¿Î
	 */
	  protected boolean isIgnoreCUFilter()
	   {
	        return true;
	    }
   
}