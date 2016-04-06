/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.report.projectdynamic.client;

import java.awt.event.*;
import java.util.Calendar;
import java.util.Date;

import javax.swing.SpinnerNumberModel;

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
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ProjectDynamicCostFilertUI extends AbstractProjectDynamicCostFilertUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectDynamicCostFilertUI.class);
    
    /**
     * output class constructor
     */
    public ProjectDynamicCostFilertUI() throws Exception
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

	public RptParams getCustomCondition() {
		CurProjectInfo curProject = (CurProjectInfo) this.prmtProject.getValue();
		if(curProject == null) {
			MsgBox.showWarning("工程项目不能为空!");
			SysUtil.abort();
		}
		Integer year = this.spYear.getIntegerVlaue();
		Integer month = this.spMonth.getIntegerVlaue();
		
		RptParams pp = new RptParams();
		pp.setObject("year", year.intValue());
		pp.setObject("month", month.intValue());
		pp.setObject("curProject", curProject);
		return pp;
	}
	
	public void onInit(RptParams arg0) throws Exception {
		
	}

	public void setCustomCondition(RptParams arg0) {
		
	}

}