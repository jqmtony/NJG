/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.report.productdynamic.client;

import java.awt.event.*;
import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * output class name
 */
public class ProductDynamicReportFilterUI extends AbstractProductDynamicReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProductDynamicReportFilterUI.class);
    
    /**
     * output class constructor
     */
    public ProductDynamicReportFilterUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	this.prmtProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.CurProjectQuery");
        this.prmtProject.setDisplayFormat("$name$");
        this.prmtProject.setEditFormat("$name$-$number$");
        this.prmtProject.setCommitFormat("$name$");
        EntityViewInfo evi = new EntityViewInfo();
        FilterInfo filter = new FilterInfo();
        evi.setFilter(filter);
        OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
        filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", orgUnit.getId().toString(), CompareType.EQUALS));
        this.prmtProject.setEntityViewInfo(evi);
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

	@Override
	public RptParams getCustomCondition() {
		// TODO Auto-generated method stub
		CurProjectInfo project = (CurProjectInfo) this.prmtProject.getValue();
		
		RptParams pp = new RptParams();
		pp.setObject("project", project);
		return pp;
	}

	@Override
	public void onInit(RptParams arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCustomCondition(RptParams arg0) {
		// TODO Auto-generated method stub
		
	}

}