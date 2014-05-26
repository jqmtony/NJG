/**
 * output package name
 */
package com.kingdee.eas.port.pm.invest.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.port.pm.invest.ObjectStateEnum;
import com.kingdee.eas.port.pm.invest.YearInvestPlanFactory;
import com.kingdee.eas.port.pm.invest.YearInvestPlanInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class YearInvestPlanListUI extends AbstractYearInvestPlanListUI
{
    private static final Logger logger = CoreUIObject.getLogger(YearInvestPlanListUI.class);
    
    /**
     * output class constructor
     */
    public YearInvestPlanListUI() throws Exception
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
    
    protected String getEditUIModal() {
    	return UIFactoryName.NEWTAB;
    }
    
    protected FilterInfo getDefaultFilterForQuery() {
    	FilterInfo  filter = new FilterInfo();
    	String cuNumber=SysContext.getSysContext().getCurrentCtrlUnit().getLongNumber();
    	filter.getFilterItems().add(new FilterItemInfo("CU.longnumber",cuNumber+"%",CompareType.LIKE));
    	return filter;
    }
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
	    IRow row = tblMain.getRow(rowIndex);
		String id = row.getCell("id").getValue().toString();
	    String oql = "select objectState where id='"+id+"'";
     	YearInvestPlanInfo Info = YearInvestPlanFactory.getRemoteInstance().getYearInvestPlanInfo(oql);
	    if(Info.getObjectState().equals(ObjectStateEnum.veto)){
	    	MsgBox.showWarning("此项目已经被否决无法修改!");
			SysUtil.abort();
			}
	    if(Info.getObjectState().equals(ObjectStateEnum.approval)){
	    	MsgBox.showWarning("此项目已经立项无法修改!");
			SysUtil.abort();
	    }
		super.actionEdit_actionPerformed(e);
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

}