package com.kingdee.eas.fdc.contract.client;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;

import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.eas.fdc.aimcost.client.FullDyDetailInfoUI;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.web.ContractWebHelper;
import com.kingdee.eas.framework.client.CoreUI;

public class ContractCostHelper {
    private KDTable tblCost = null;
    private String contractId=null;
	public ContractCostHelper(KDTable tblCost, String contractId){
		this.tblCost = tblCost;
		this.contractId=contractId;
		if(tblCost.getColumnIndex("prjNumber") <0){
			//add column 
			IColumn col = tblCost.addColumn(0);
			col.setKey("prjNumber");
			col = tblCost.addColumn(1);
			col.setKey("prjName");
			IRow headRow=tblCost.getHeadRow(0);
			headRow.getCell("prjNumber").setValue("项目编码");
			headRow.getCell("prjName").setValue("项目名称");
		}
	}

	public  void fillCostTable() throws Exception {
		
		tblCost.removeRows();
		//与web审批一致
		Map costMap=ContractWebHelper.getCostInfo(null, contractId);
		CostAccountCollection accts=(CostAccountCollection)costMap.get("CostAccountCollection");
		if(accts!=null){
			for(Iterator iter=accts.iterator();iter.hasNext();){ 
				CostAccountInfo info=(CostAccountInfo)iter.next();
				String key=info.getId().toString();
				BigDecimal aimCost=(BigDecimal)costMap.get(key+"_aim");
				BigDecimal adjust=(BigDecimal)costMap.get(key+"_adjust");//dif
				BigDecimal happen=(BigDecimal)costMap.get(key+"_happen");
				BigDecimal dyn=(BigDecimal)costMap.get(key+"_dyn");
				BigDecimal intending=(BigDecimal)costMap.get(key+"_intending");
				BigDecimal conSplit=FDCHelper.toBigDecimal(costMap.get(key+"_conSplit"));
				IRow row=this.tblCost.addRow();
				row.getCell("prjNumber").setValue(info.getCurProject().getLongNumber()!=null?info.getCurProject().getLongNumber().replace('!','.'):"");
				row.getCell("prjName").setValue(info.getCurProject().getName());
				row.getCell("acctNumber").setValue(info.getLongNumber()!=null?info.getLongNumber().replace('!','.'):"");
				row.getCell("acctName").setValue(info.getName());
				row.getCell("aimCost").setValue(aimCost);
				row.getCell("hasHappen").setValue(happen);
				row.getCell("dynamicCost").setValue(dyn);
				row.getCell("intendingHappen").setValue(intending);
				row.getCell("chayi").setValue(adjust);
				row.getCell("conSplit").setValue(conSplit);
				row.setUserObject(info);
			}
		}
		//汇总

	}
	
    protected void tblCost_tableClicked(CoreUI ui,KDTMouseEvent e) throws Exception
    {
		if (e.getClickCount() == 2) {
			int rowIndex = e.getRowIndex();

			// if(getMainTable().getCell(rowIndex, colIndex).getValue() == null)
			// return;

			Object value = tblCost.getRow(rowIndex).getUserObject();
			if (value == null)
				return;
			CostAccountInfo acctInfo = (CostAccountInfo) value;

			boolean b = acctInfo.isIsLeaf();
			// 显示出来时有错，暂时不显示非明细工程项目
			if (!b)
				return;

			ui.setCursorOfWair();

			BigDecimal will_happen =(BigDecimal) tblCost.getRow(rowIndex).getCell(
					"intendingHappen").getValue();
			FullDyDetailInfoUI.showDialogWindows(ui, acctInfo, will_happen);
			ui.setCursorOfDefault();
		}
    }
    

}
