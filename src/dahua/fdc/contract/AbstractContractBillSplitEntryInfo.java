package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractBillSplitEntryInfo extends com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo implements Serializable 
{
    public AbstractContractBillSplitEntryInfo()
    {
        this("id");
    }
    protected AbstractContractBillSplitEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 拆分分录 's 变更拆分单据头 property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EA36AA8C");
    }
}