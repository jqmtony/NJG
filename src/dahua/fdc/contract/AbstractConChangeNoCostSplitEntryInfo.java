package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractConChangeNoCostSplitEntryInfo extends com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillEntryInfo implements Serializable 
{
    public AbstractConChangeNoCostSplitEntryInfo()
    {
        this("id");
    }
    protected AbstractConChangeNoCostSplitEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 变更非成本拆分分录 's 拆分头 property 
     */
    public com.kingdee.eas.fdc.contract.ConChangeNoCostSplitInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.ConChangeNoCostSplitInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.ConChangeNoCostSplitInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B84B1F5D");
    }
}