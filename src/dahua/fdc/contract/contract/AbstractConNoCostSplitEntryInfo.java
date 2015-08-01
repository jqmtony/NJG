package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractConNoCostSplitEntryInfo extends com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillEntryInfo implements Serializable 
{
    public AbstractConNoCostSplitEntryInfo()
    {
        this("id");
    }
    protected AbstractConNoCostSplitEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 合同非成本拆分分录 's 父 property 
     */
    public com.kingdee.eas.fdc.contract.ConNoCostSplitInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.ConNoCostSplitInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.ConNoCostSplitInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4F3D28CD");
    }
}