package com.kingdee.eas.fdc.contract.contractsplit;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractPCSplitBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractContractPCSplitBillInfo()
    {
        this("id");
    }
    protected AbstractContractPCSplitBillInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.contract.contractsplit.ContractPCSplitBillEntryCollection());
    }
    /**
     * Object:跨期合同拆分's 拆分状态property 
     */
    public com.kingdee.eas.fdc.basedata.CostSplitStateEnum getSplitState()
    {
        return com.kingdee.eas.fdc.basedata.CostSplitStateEnum.getEnum(getString("splitState"));
    }
    public void setSplitState(com.kingdee.eas.fdc.basedata.CostSplitStateEnum item)
    {
		if (item != null) {
        setString("splitState", item.getValue());
		}
    }
    /**
     * Object: 跨期合同拆分 's 工程项目 property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    /**
     * Object: 跨期合同拆分 's 合同 property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getContractBill()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("contractBill");
    }
    public void setContractBill(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("contractBill", item);
    }
    /**
     * Object: 跨期合同拆分 's 合同变更指令 property 
     */
    public com.kingdee.eas.fdc.contract.ContractChangeBillInfo getContractChangeBill()
    {
        return (com.kingdee.eas.fdc.contract.ContractChangeBillInfo)get("contractChangeBill");
    }
    public void setContractChangeBill(com.kingdee.eas.fdc.contract.ContractChangeBillInfo item)
    {
        put("contractChangeBill", item);
    }
    /**
     * Object: 跨期合同拆分 's 合同变更确认 property 
     */
    public com.kingdee.eas.fdc.contract.ChangeAuditBillInfo getContractChangeSettleBill()
    {
        return (com.kingdee.eas.fdc.contract.ChangeAuditBillInfo)get("contractChangeSettleBill");
    }
    public void setContractChangeSettleBill(com.kingdee.eas.fdc.contract.ChangeAuditBillInfo item)
    {
        put("contractChangeSettleBill", item);
    }
    /**
     * Object: 跨期合同拆分 's 合同结算 property 
     */
    public com.kingdee.eas.fdc.contract.ContractSettlementBillInfo getContractSettleBill()
    {
        return (com.kingdee.eas.fdc.contract.ContractSettlementBillInfo)get("contractSettleBill");
    }
    public void setContractSettleBill(com.kingdee.eas.fdc.contract.ContractSettlementBillInfo item)
    {
        put("contractSettleBill", item);
    }
    /**
     * Object: 跨期合同拆分 's 分录 property 
     */
    public com.kingdee.eas.fdc.contract.contractsplit.ContractPCSplitBillEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.contract.contractsplit.ContractPCSplitBillEntryCollection)get("entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8FB144FB");
    }
}