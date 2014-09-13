/**
 * output package name
 */
package com.kingdee.eas.port.pm.invest.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.port.pm.invest.ObjectStateEnum;
import com.kingdee.eas.port.pm.invest.YearInvestPlanFactory;

/**
 * output class name
 */
public class YearPlanTrialListUI extends AbstractYearPlanTrialListUI
{
    private static final Logger logger = CoreUIObject.getLogger(YearPlanTrialListUI.class);
    
    /**
     * output class constructor
     */
    public YearPlanTrialListUI() throws Exception
    {
        super();
        this.setUITitle("年度计划项目汇总（初审）");
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
    	actionAddNew.setVisible(false);
    	actionEdit.setVisible(false);
    	actionRemove.setVisible(false);
    }
    
    protected FilterInfo getDefaultFilterForQuery() {
    	FilterInfo defFilter = new FilterInfo();
    	FilterInfo  filter = new FilterInfo();
    	String cuNumber=SysContext.getSysContext().getCurrentCtrlUnit().getLongNumber();
    	filter.getFilterItems().add(new FilterItemInfo("CU.longnumber",cuNumber+"%",CompareType.LIKE));
    	try {
			defFilter.mergeFilter(filter, "and");
		} catch (BOSException e) {
			e.printStackTrace();
		}
    	return defFilter;
    }
    
    protected void execQuery() {
    	super.execQuery();
    }

    public void onShow() throws Exception {
    	super.onShow();
    }
    
    protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
    	super.tblMain_tableClicked(e);
    }
    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.pm.invest.YearInvestPlanFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.port.pm.invest.YearInvestPlanInfo objectValue = new com.kingdee.eas.port.pm.invest.YearInvestPlanInfo();
		
        return objectValue;
    }
    
    protected String getEntityBOSType() throws Exception {
    	return createNewData().getBOSType().toString();
    }
    /**
     * output actionNumberSign_actionPerformed
     */
    public void actionNumberSign_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNumberSign_actionPerformed(e);
    }
    protected String getKeyFieldName() {
    	return "id";
    }
	protected String getEditUIName() {
		return YearInvestPlanEditUI.class.getName();
	}
}