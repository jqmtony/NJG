package com.kingdee.eas.fdc.contract.web;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractSourceEnum;

public class ContractWebTest {
	private void getContractBill(Context ctx) throws BOSException, EASBizException {
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("*");
		selector.add("entrys.*");
		selector.add("curProject.name");
		selector.add("currency.name");
		selector.add("orgUnit.name");
		selector.add("landDeveloper.name");
		selector.add("partB.name");
		selector.add("partC.name");
		selector.add("respDept.name");
		selector.add("respPerson.name");
		selector.add("contractType.name");
		selector.add("partC.name");
		selector.add("creator.name");
		String contractId="";
			ContractBillInfo info = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(contractId), selector);
			info.getNumber();
			info.getCreator().getName();
			info.getPartB().getName();
			info.getRespDept().getName();
			info.getContractPropert().getAlias();
			info.getOrgUnit().getDisplayName();
			info.getCurProject().getDisplayName();
			ContractSourceEnum  contractSource=info.getContractSource();
			//

			if (contractSource == ContractSourceEnum.TRUST) {
				//委托
			} else if (contractSource == ContractSourceEnum.INVITE) {
				//招标
				info.getLowestPrice();
				info.getLowestPriceUnit();
				info.getLowerPrice();
				info.getLowerPriceUnit();
				info.getHigherPrice();
				info.getHigherPriceUnit();
				info.getHighestPrice();
				info.getHighestPriceUni();
				info.getWinPrice();
				info.getWinUnit();
				info.getQuantity();
				info.getInviteType();
				info.getFileNo();
			}
			else if (contractSource == ContractSourceEnum.DISCUSS) {
				//议标
				info.getBasePrice();
				info.getSecondPrice();
				info.getRemark();
			}			
			else if (contractSource == ContractSourceEnum.COOP) {
				//战略
				info.getCoopLevel();
				info.getPriceType();
			}


	}
}
