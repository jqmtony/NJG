package com.kingdee.eas.fdc.contract.client;

import java.util.Iterator;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.ContractProgrammingEntryInfo;
import com.kingdee.eas.fdc.contract.ContractProgrammingInfo;
import com.kingdee.eas.fdc.contract.ContractWithProgramCollection;
import com.kingdee.eas.fdc.contract.ContractWithProgramFactory;
import com.kingdee.eas.fdc.contract.ContractWithProgramInfo;

public class ContractWithProgramHandler {

	public static void fillTable(String contractId, KDTable billListTable, boolean isList) throws BOSException {
		billListTable.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		billListTable.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		billListTable.removeRows();
		billListTable.setUserObject(null);
		EntityViewInfo view = new EntityViewInfo();
		if(view.getFilter()==null){
			view.setFilter(new FilterInfo());
		}
		view.getFilter().getFilterItems().add(new FilterItemInfo("contractBill.id", contractId));
		view.getSelector().add("id");
		view.getSelector().add("description");
		view.getSelector().add("contractBill");
		view.getSelector().add("programming.isfinal");
		view.getSelector().add("programming.isLastVersion");
		view.getSelector().add("programming.number");
		view.getSelector().add("programming.name");
		view.getSelector().add("programming.programmingMoney");
		view.getSelector().add("programming.isImagePay");
		view.getSelector().add("programming.createTime");
		view.getSelector().add("programming.entrys.costAccount.curProject.name");
		view.getSelector().add("programming.entrys.costAccountName");
		view.getSelector().add("programming.entrys.costAccount.longNumber");
		view.getSelector().add("programming.entrys.programmingMoney");
		SorterItemInfo sort = new SorterItemInfo("programming.entrys.costAccount.curproject.longnumber");
    	sort.setSortType(SortType.ASCEND);
    	view.getSorter().add(sort);
    	sort = new SorterItemInfo("programming.entrys.costAccount.longnumber");
    	sort.setSortType(SortType.ASCEND);
    	view.getSorter().add(sort);
		ContractWithProgramCollection contractWithProgramColls = ContractWithProgramFactory.getRemoteInstance().getContractWithProgramCollection(view);
		IRow row = null;
    	for (Iterator iter = contractWithProgramColls.iterator(); iter.hasNext();) {
    		ContractWithProgramInfo element = (ContractWithProgramInfo) iter.next();
    		ContractProgrammingInfo programming = element.getProgramming();
    		if(programming==null){
    			continue;
    		}
    		if(isList){
	    		if(programming.getEntrys()!=null){
	    			for(Iterator iter2 =programming.getEntrys().iterator();iter2.hasNext();){
	    				row = billListTable.addRow();
	    				row.getCell("number").setValue(programming.getNumber());
	    				row.getCell("name").setValue(programming.getName());
	    				row.getCell("date").setValue(programming.getCreateTime());
		    		
	    				ContractProgrammingEntryInfo entryInfo = (ContractProgrammingEntryInfo)iter2.next();
		    			row.getCell("project").setValue(entryInfo.getCostAccount().getCurProject().getName());
		    			row.getCell("acctNumber").setValue(entryInfo.getCostAccount().getLongNumber().replace('!', '.'));
		    			row.getCell("acctName").setValue(entryInfo.getCostAccountName());
		    			row.getCell("amount").setValue(entryInfo.getProgrammingMoney());
		    			row.getCell("desc").setValue(element.getDescription());
		    			row.setUserObject(entryInfo);
		    		}
	    		}
    		}else{
    			row = billListTable.addRow();
    			row.getCell("number").setValue(programming);
    			row.getCell("name").setValue(programming.getName());
    			row.getCell("date").setValue(programming.getCreateTime());
    			row.getCell("amount").setValue(programming.getProgrammingMoney());
    			row.getCell("isImagePay").setValue(Boolean.valueOf(programming.isIsImagePay()));
    			row.getCell("desc").setValue(element.getDescription());
    			row.setUserObject(element);
    		}
    		
    	}
    	FDCHelper.formatTableNumber(billListTable, "amount");
    	FDCHelper.formatTableDate(billListTable, "date");
    	billListTable.getStyleAttributes().setLocked(true);
    	if(isList){
    		billListTable.getMergeManager().mergeBlock(0, 0, billListTable.getRowCount()-1, 0, KDTMergeManager.FREE_MERGE);
    		billListTable.getMergeManager().mergeBlock(0, 1, billListTable.getRowCount()-1, 3, KDTMergeManager.FREE_MERGE);
    		billListTable.getMergeManager().mergeBlock(0, 7, billListTable.getRowCount()-1, 7, KDTMergeManager.FREE_MERGE);
    	}
	}

}
