/**
 * output package name
 */
package com.kingdee.eas.port.pm.invest.investplan.client;

import java.awt.BorderLayout;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ui.face.CoreUIObject;

/**
 * output class name
 */
public class ViewPlanDetailUI extends AbstractViewPlanDetailUI
{
    private static final Logger logger = CoreUIObject.getLogger(ViewPlanDetailUI.class);
    
    /**
     * output class constructor
     */
    public ViewPlanDetailUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	setKDcon();
    }
    
    public void onShow() throws Exception {
    	super.onShow();
    }
    
    private void setKDcon()
    {
    	if(getUIContext().get("table")!=null)
    	{
    		KDTable kdtable = (KDTable) getUIContext().get("table");
    		this.kDContainer1.getContentPane().add(kdtable,BorderLayout.CENTER);
    		kdtable.setEnabled(false);
    	}
    }

}