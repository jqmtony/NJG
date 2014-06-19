/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.IndexVersionFactory;
import com.kingdee.eas.fdc.basedata.IndexVersionInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class IndexVersionListUI extends AbstractIndexVersionListUI
{
    private static final Logger logger = CoreUIObject.getLogger(IndexVersionListUI.class);
    public IndexVersionListUI() throws Exception
    {
        super();
    }
    protected FDCDataBaseInfo getBaseDataInfo() {
		return new IndexVersionInfo();
	}
	protected ICoreBase getBizInterface() throws Exception {
		return IndexVersionFactory.getRemoteInstance();
	}
	protected String getEditUIName() {
		return IndexVersionEditUI.class.getName();
	}
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}

}