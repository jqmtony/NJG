/**
 * output package name
 */
package com.kingdee.eas.fdc.costdb.client;

import java.math.BigDecimal;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;

/**
 * output class name
 */
public class ProjectDFRptAimCostInfoUI extends AbstractProjectDFRptAimCostInfoUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectDFRptAimCostInfoUI.class);
    
    /**
     * output class constructor
     */
    public ProjectDFRptAimCostInfoUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
       	super.onLoad();
       	
       	tblMain.checkParsed();
       	
       	FDCHelper.formatTableNumber(tblMain, new String[]{"workload", "price", "aimcost", "origIdxValue"});
       	
       	String projId = (String)getUIContext().get("projId");
       	String prodId = (String)getUIContext().get("productTypeId");
       	String acctLNum = (String)getUIContext().get("acctLNum");
       	
       	ResultSet aimDetail = ProjectDFRptHelper.getAimDetail(projId, prodId, acctLNum);
		
		while (aimDetail.next()) {
			String entryName = aimDetail.getString("FEntryName");
			BigDecimal workload = aimDetail.getBigDecimal("FWorkload");
			BigDecimal price = aimDetail.getBigDecimal("FPrice");
			String unit = aimDetail.getString("FUnit");
			BigDecimal amount = aimDetail.getBigDecimal("famount");
			IRow row = tblMain.addRow();
			
			row.getCell("acctName").setValue(entryName);
			row.getCell("workload").setValue(workload);
			row.getCell("unit").setValue(unit);
			row.getCell("price").setValue(price);
			row.getCell("aimcost").setValue(amount);
			row.getCell("aimcost").setValue(amount);
			row.getCell("origIdxValue").setValue(aimDetail.getBigDecimal("origIndexValue"));
			row.getCell("origIdx").setValue(aimDetail.getString("origIndexName"));
			
		}
       	
    }

}