package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitInfo;
import com.kingdee.eas.fdc.contract.ConChangeSplitInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.contract.IConChangeNoCostSplit;
import com.kingdee.eas.fdc.contract.IConChangeNoCostSplitEntry;
import com.kingdee.eas.fdc.contract.SettlementCostSplitInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;

public class FDCNoCostSplitAdapter
{
	private FDCNoCostSplit fDCNoCostSplit;
	private Context ctx;
	private static BOSObjectType conSplitType=(new ContractCostSplitInfo()).getBOSType();
	private static BOSObjectType conChangeSplitType=(new ConChangeSplitInfo()).getBOSType();
	private static BOSObjectType settlementSplitType=(new SettlementCostSplitInfo()).getBOSType();
	private static BOSObjectType paymentSplitType=(new PaymentSplitInfo()).getBOSType();
	/**
	 * @param ctx  客户端为null 服务器端为Context
	 */
	
	public FDCNoCostSplitAdapter(Context ctx){
		fDCNoCostSplit=new FDCNoCostSplit(ctx);
		this.ctx=ctx;
	}
	/**
	 * 通过id的BOSObjectType确定具体的刷新类型,调用刷新
	 * @author sxhong  		Date 2007-5-29
	 */
	public void refresh(String id)throws Exception{
		final BOSObjectType type = BOSUuid.read(id).getType();
		
		if(type.equals(conChangeSplitType)){
			refreshConNoCostChange(id);
		}
		
	}
	
	public void refreshConNoCostChange(String id) throws Exception{
		if(id==null){
			return;
		}
		
		IConChangeNoCostSplit iConChangeNoCostSplit = null;
		IConChangeNoCostSplitEntry iConChangeNoCostSplitEntry = null;
		SelectorItemCollection selector=getSelectors();
		selector.add("contractChange.id");
		selector.add("contractChange.CU.id");
		if(ctx!=null){
			iConChangeNoCostSplit=ConChangeNoCostSplitFactory.getLocalInstance(ctx);
			iConChangeNoCostSplitEntry=ConChangeNoCostSplitEntryFactory.getLocalInstance(ctx);
		}else{
			iConChangeNoCostSplit=ConChangeNoCostSplitFactory.getRemoteInstance();
			iConChangeNoCostSplitEntry=ConChangeNoCostSplitEntryFactory.getRemoteInstance();
		}
		ConChangeNoCostSplitInfo splitbill=iConChangeNoCostSplit.getConChangeNoCostSplitInfo(new ObjectUuidPK(BOSUuid.read(id)),selector);
		fDCNoCostSplit.refreshApportionAmount(splitbill, iConChangeNoCostSplitEntry);
		
	}
	
	private SelectorItemCollection getSelectors() {
		SelectorItemCollection sic=new SelectorItemCollection();
/*
		String prefix="entrys.";
        sic.add(new SelectorItemInfo(prefix + "*"));

        sic.add(new SelectorItemInfo(prefix + "apportionType.id"));
        sic.add(new SelectorItemInfo(prefix + "apportionType.name"));
        sic.add(new SelectorItemInfo(prefix + "product.*"));

        sic.add(new SelectorItemInfo(prefix + "costAccount"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.id"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.name"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.longNumber"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.displayName"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.isLeaf"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.level"));
        
        sic.add(new SelectorItemInfo(prefix + "costAccount.curProject"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.curProject.id"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.curProject.name"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.curProject.displayName"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.curProject.longNumber"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.curProject.isLeaf"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.curProject.level"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.curProject.parent.id"));*/
                
        
        return this.fDCNoCostSplit.setSelectorsEntry(sic, false);
	}
	

}
