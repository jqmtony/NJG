/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.report.monthcontractchange.client;

import java.awt.event.*;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class MonContractChangeFilterUI extends AbstractMonContractChangeFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(MonContractChangeFilterUI.class);
    
    /**
     * output class constructor
     */
    public MonContractChangeFilterUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
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
        
        this.prmtContractType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ContractTypeQuery");
        this.prmtContractType.setEnabledMultiSelection(false);
        this.prmtContractType.setDisplayFormat("$name$");
        this.prmtContractType.setEditFormat("$name$");
        this.prmtContractType.setCommitFormat("$name$");
    	super.onLoad();
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

	public RptParams getCustomCondition() {
		RptParams pp = new RptParams();
		CurProjectInfo curProject = (CurProjectInfo) this.prmtProject.getValue();
		ContractTypeInfo contractType = (ContractTypeInfo) this.prmtContractType.getValue();
		Date date = (Date) this.pkDate.getValue();
		
		if(contractType == null) {
			MsgBox.showWarning("合同类型不能为空!!");
			SysUtil.abort();
		}
		pp.setObject("curProject", curProject);
		pp.setObject("contractType", contractType);
		pp.setObject("date", date);
		return pp;
	}

	public void onInit(RptParams arg0) throws Exception {
		
	}

	public void setCustomCondition(RptParams arg0) {
		
	}

}