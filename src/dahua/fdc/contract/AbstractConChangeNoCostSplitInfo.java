package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractConChangeNoCostSplitInfo extends com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillInfo implements Serializable 
{
    public AbstractConChangeNoCostSplitInfo()
    {
        this("id");
    }
    protected AbstractConChangeNoCostSplitInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.contract.ConChangeNoCostSplitEntryCollection());
    }
    /**
     * Object: 变更非成本拆分 's 变更签证 property 
     */
    public com.kingdee.eas.fdc.contract.ContractChangeBillInfo getContractChange()
    {
        return (com.kingdee.eas.fdc.contract.ContractChangeBillInfo)get("contractChange");
    }
    public void setContractChange(com.kingdee.eas.fdc.contract.ContractChangeBillInfo item)
    {
        put("contractChange", item);
    }
    /**
     * Object: 变更非成本拆分 's 变更拆分分录 property 
     */
    public com.kingdee.eas.fdc.contract.ConChangeNoCostSplitEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.contract.ConChangeNoCostSplitEntryCollection)get("entrys");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E7C97E15");
    }
}