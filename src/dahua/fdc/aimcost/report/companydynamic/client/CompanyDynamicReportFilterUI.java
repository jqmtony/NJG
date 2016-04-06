/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.report.companydynamic.client;

import java.awt.event.*;
import java.util.Calendar;

import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * output class name
 */
public class CompanyDynamicReportFilterUI extends AbstractCompanyDynamicReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(CompanyDynamicReportFilterUI.class);
    
    /**
     * output class constructor
     */
    public CompanyDynamicReportFilterUI() throws Exception
    {
        super();
    }

    @Override
    public void onLoad() throws Exception {
    	// TODO Auto-generated method stub
    	this.prmtProject.setQueryInfo("com.kingdee.eas.basedata.org.app.CompanyQuery");
        this.prmtProject.setDisplayFormat("$name$");
        this.prmtProject.setEditFormat("$name$-$number$");
        this.prmtProject.setCommitFormat("$name$");
    	super.onLoad();
    	Calendar cal = Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH)+1;
        this.spYear.setValue(year);
        this.spMonth.setValue(month);
    	this.spYear.setModel(new SpinnerNumberModel(this.spYear.getIntegerVlaue().intValue(),1,10000,1));
		this.spMonth.setModel(new SpinnerNumberModel(this.spMonth.getIntegerVlaue().intValue(),1,12,1));
		
		this.kDLabelContainer1.setVisible(false);
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
		Integer year = this.spYear.getIntegerVlaue();
		Integer month = this.spMonth.getIntegerVlaue();
		CompanyOrgUnitInfo company = (CompanyOrgUnitInfo) prmtProject.getValue();
		RptParams pp = new RptParams();
		pp.setObject("year", year.intValue());
		pp.setObject("month", month.intValue());
		pp.setObject("company", company);
		return pp;
	}

	@Override
	public void onInit(RptParams arg0) throws Exception {
		
	}

	@Override
	public void setCustomCondition(RptParams arg0) {
		
	}
}