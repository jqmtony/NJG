/**
 * output package name
 */
package com.kingdee.eas.fdc.costindexdb.client;

import java.awt.Component;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class BuildPriceQueryUI extends AbstractBuildPriceQueryUI
{
    private static final Logger logger = CoreUIObject.getLogger(BuildPriceQueryUI.class);
    private CommonQueryDialog commDialog = null;
    private CustomerQueryPanel filterUI = null;
    /**
     * output class constructor
     */
    public BuildPriceQueryUI() throws Exception
    {
        super();
    }

    @Override
    public void onLoad() throws Exception {
    	super.onLoad();
    }
    
    @Override
    protected CommonQueryDialog initCommonQueryDialog() {
    	if(commDialog != null)
    		return commDialog;
    	try {
    		commDialog = new CommonQueryDialog();
    		commDialog.setOwner((Component)getUIContext().get("Owner"));
    		commDialog.setParentUIClassName(getClass().getName());
    		commDialog.setQueryObjectPK(mainQueryPK);
//    		commDialog.setHeight(200);
//    		commDialog.setWidth(400);
			commDialog.addUserPanel(getUserPanel());
			commDialog.setShowSorter(false);
			commDialog.setUiObject(this);
		} catch (Exception e) {
			handUIException(e);
		}
    	return commDialog;
    }
    
    protected CustomerQueryPanel getUserPanel() throws Exception{
    	if(filterUI == null)
    		filterUI = new BuildPriceFilterUI();
    	return filterUI;
    }

    protected ICoreBase getBizInterface() throws Exception {
    	return com.kingdee.eas.fdc.costindexdb.BuildPriceIndexFactory.getRemoteInstance();
    }
    
	protected String getEntityBOSType() throws Exception {
		return super.getEntityBOSType();
	}
   
	protected boolean initDefaultFilter() {
		return true;
	}
    
    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        super.tblMain_tableSelectChanged(e);
    }

   

}