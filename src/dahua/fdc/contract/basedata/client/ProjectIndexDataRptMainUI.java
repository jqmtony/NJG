/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.headfootdesigner.HeadFootModel;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataRptFacadeFactory;
import com.kingdee.eas.fdc.basedata.RptParamConst;
import com.kingdee.eas.framework.bireport.IBireportBaseFacade;
import com.kingdee.eas.framework.bireport.client.BireportBaseFilterUI;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * output class name
 */
public class ProjectIndexDataRptMainUI extends AbstractProjectIndexDataRptMainUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectIndexDataRptMainUI.class);
    
    /**
     * output class constructor
     */
    public ProjectIndexDataRptMainUI() throws Exception
    {
        super();
    }

    public void onLoad()throws Exception{
    	super.onLoad();
    	FDCTableHelper.disableExportByPerimission(this, this.getTableForCommon(), "ActionPrint");
    	
    }
    
    protected boolean isShowDimensionNavigator()
    {
    	return false;
    }
    
    protected BireportBaseFilterUI getQueryDialogUserPanel() throws Exception {
		return new ProjectIndexDataFilterUI(this, this.actionOnLoad);
	}

	protected IBireportBaseFacade getRemoteInstance() throws BOSException {
		return ProjectIndexDataRptFacadeFactory.getRemoteInstance();
	}

	protected RptParams getParamsForInit() {
		/* TODO 自动生成方法存根 */
		return null;
	}

	protected RptParams getParamsForRequest() {
		
		EntityViewInfo result = getQueryDialog().getEntityViewInfoResult();
		
		if(result != null && result.getFilter() != null && result.getFilter().getFilterItems().size() > 0) {
			params.setObject(RptParamConst.KEY_CUSTOMFILTER, result.toString());
		}
		
		return this.params;
	}

	protected KDTable getTableForPrintSetting() {
		
		return tblMain;
	}

	protected void preparePrintPageHeader(HeadFootModel header) {
	}

	protected Map preparePrintVariantMap() {
		return null;
	}
}