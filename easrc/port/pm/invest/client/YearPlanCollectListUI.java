/**
 * output package name
 */
package com.kingdee.eas.port.pm.invest.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.auto4s.bdm.vip.util.UIFactoryName;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.port.pm.invest.YearInvestPlanFactory;

/**
 * output class name
 */
public class YearPlanCollectListUI extends AbstractYearPlanCollectListUI
{
    private static final Logger logger = CoreUIObject.getLogger(YearPlanCollectListUI.class);
    
    /**
     * output class constructor
     */
    public YearPlanCollectListUI() throws Exception
    {
        super();
       this.setUITitle("年度计划项目汇总（评审）");
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    protected FilterInfo getDefaultFilterForQuery() {
    	FilterInfo filter = new FilterInfo();
    	String cuNumber = SysContext.getSysContext().getCurrentCtrlUnit().getLongNumber();
    	filter.getFilterItems().add(new FilterItemInfo("CU.longNumber",cuNumber+"%",CompareType.LIKE));
    	return filter;
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
	public ICoreBase getBizInterface() throws Exception {
		return YearInvestPlanFactory.getRemoteInstance();
	}
	protected String getEditUIName() {
		return YearInvestPlanEditUI.class.getName();
	}
}