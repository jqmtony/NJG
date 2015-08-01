/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * output class name
 */
public class FDCBIRptBaseFilterUI extends AbstractFDCBIRptBaseFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCBIRptBaseFilterUI.class);
    
    /**
     * output class constructor
     */
    public FDCBIRptBaseFilterUI() throws Exception
    {
        super();
    }

	public boolean verify() {
		return false;
	}

	public void onInit(RptParams initParams) throws Exception {
		/* TODO 自动生成方法存根 */
		
	}

	public RptParams getCustomCondition() {
		/* TODO 自动生成方法存根 */
		return null;
	}

	public void setCustomCondition(RptParams params) {
		/* TODO 自动生成方法存根 */
		
	}


    


}