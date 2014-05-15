/**
 * output package name
 */
package com.kingdee.eas.port.pm.invest.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.port.njghelper.NJGhelper;

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
    
//    protected boolean isIgnoreCUFilter() {
//    	String cuNumber=SysContext.getSysContext().getCurrentCtrlUnit().getNumber();
//    	if(cuNumber.equals("NJP")){
//    		return true;	
//		}else{
//			return false;	
//		}
//    }
    
    protected FilterInfo getDefaultFilterForQuery() {
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new
    	FilterItemInfo("CU.id",SysContext.getSysContext().getCurrentCtrlUnit(),CompareType.EQUALS));
    	String cuNumber=SysContext.getSysContext().getCurrentCtrlUnit().getNumber();
    	if(cuNumber.compareTo(NJGhelper.NJP)==0){
    		return null;
    	}
    	
    	return filter;
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