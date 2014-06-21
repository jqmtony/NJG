/**
 * output package name
 */
package com.kingdee.eas.port.pm.invest.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.xr.helper.common.PortProjectTreeBuilder;

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
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	buildProjectTree();
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
   
    public void buildProjectTree() throws Exception {
		PortProjectTreeBuilder projectTreeBuilder = new PortProjectTreeBuilder();

		projectTreeBuilder.build(this, this.kDTree1, actionOnLoad);
		
		if(this.kDTree1.getRowCount() > 0)
		{
			this.kDTree1.setSelectionRow(0);
			this.kDTree1.expandAllNodes(true,(DefaultKingdeeTreeNode)  this.kDTree1.getModel().getRoot());
		}
	}
    
//    protected FilterInfo getTreeSelectFilter(Object projectNode) throws Exception {
//		FilterInfo filter = new FilterInfo();
//		if (projectNode != null && projectNode instanceof CoreBaseInfo) {
//	    	String cuNumber=SysContext.getSysContext().getCurrentCtrlUnit().getLongNumber();
//	    	filter.getFilterItems().add(new FilterItemInfo("CU.longnumber",cuNumber+"%",CompareType.LIKE));
//	    	return filter;
//		}
//		return filter;
//	}
    
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