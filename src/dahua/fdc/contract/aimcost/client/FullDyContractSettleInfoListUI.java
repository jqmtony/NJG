package com.kingdee.eas.fdc.aimcost.client;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.client.ContractSettleFullListUI;

public class FullDyContractSettleInfoListUI extends ContractSettleFullListUI {

	public FullDyContractSettleInfoListUI() throws Exception {
		super();
	}

	protected boolean initDefaultFilter() {
		return false;
	}
	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		Map dataMap = (Map) getUIContext().get("dataMap");
		String acctId = (String) dataMap.get("acctId");
		try {
			SettlementCostSplitEntryCollection splitCollection = SettlementCostSplitEntryFactory.getRemoteInstance().getSettlementCostSplitEntryCollection("select id,parent.settlementBill.id where costAccount.id='"+acctId+"'");
			Set idSet = new HashSet();
			for(int i=0;i<splitCollection.size();i++){
				idSet.add(splitCollection.get(i).getParent().getSettlementBill().getId().toString());
			}
			if(idSet.size()==0)idSet.add("1");
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",idSet,CompareType.INCLUDE));
			viewInfo.setFilter(filter);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
		
	}
}
