package com.kingdee.eas.bpm.common;

import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;

public class ConvertToContractBillInfo {
	public static ContractBillInfo getContractBillInfo(IObjectValue info){
		ContractBillInfo billInfo = null;
		if(info instanceof ContractSettlementBillInfo)
		{
			ContractSettlementBillInfo sbInfo = (ContractSettlementBillInfo)info;
			billInfo = sbInfo.getContractBill();
		}else if(info instanceof ChangeAuditBillInfo)
		{
			
		}else if(info instanceof ChangeAuditBillInfo)
		{
			
		}
		return billInfo;
	}
}
