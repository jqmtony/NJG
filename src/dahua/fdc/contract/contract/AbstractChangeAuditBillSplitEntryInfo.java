package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChangeAuditBillSplitEntryInfo extends com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo implements Serializable 
{
    public AbstractChangeAuditBillSplitEntryInfo()
    {
        this("id");
    }
    protected AbstractChangeAuditBillSplitEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 变更拆分 's null property 
     */
    public com.kingdee.eas.fdc.contract.ChangeAuditBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.ChangeAuditBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.ChangeAuditBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 变更拆分 's 合同信息 property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getContractBill()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("contractBill");
    }
    public void setContractBill(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("contractBill", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B837E9EF");
    }
}