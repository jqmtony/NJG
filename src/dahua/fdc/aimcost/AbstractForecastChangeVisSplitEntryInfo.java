package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractForecastChangeVisSplitEntryInfo extends com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo implements Serializable 
{
    public AbstractForecastChangeVisSplitEntryInfo()
    {
        this("id");
    }
    protected AbstractForecastChangeVisSplitEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 拆分分录 's 变更拆分单据头 property 
     */
    public com.kingdee.eas.fdc.aimcost.ForecastChangeVisInfo getParent()
    {
        return (com.kingdee.eas.fdc.aimcost.ForecastChangeVisInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.aimcost.ForecastChangeVisInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BECD9A36");
    }
}