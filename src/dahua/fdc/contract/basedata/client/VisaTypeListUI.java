/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.VisaTypeFactory;
import com.kingdee.eas.fdc.basedata.VisaTypeInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class VisaTypeListUI extends AbstractVisaTypeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(VisaTypeListUI.class);
    
    /**
     * output class constructor
     */
    public VisaTypeListUI() throws Exception
    {
        super();
    }

	protected FDCDataBaseInfo getBaseDataInfo() {
		return new VisaTypeInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return VisaTypeFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return VisaTypeEditUI.class.getName();
	}

}