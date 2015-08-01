package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractConNoCostSplitInfo extends com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillInfo implements Serializable 
{
    public AbstractConNoCostSplitInfo()
    {
        this("id");
    }
    protected AbstractConNoCostSplitInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.contract.ConNoCostSplitEntryCollection());
    }
    /**
     * Object: 合同非成本拆分 's 分录 property 
     */
    public com.kingdee.eas.fdc.contract.ConNoCostSplitEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.contract.ConNoCostSplitEntryCollection)get("entrys");
    }
    /**
     * Object:合同非成本拆分's 是否已确定property 
     */
    public boolean isIsConfirm()
    {
        return getBoolean("isConfirm");
    }
    public void setIsConfirm(boolean item)
    {
        setBoolean("isConfirm", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("10334EA5");
    }
}