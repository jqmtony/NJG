package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractForecastChangeVisEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractForecastChangeVisEntryInfo()
    {
        this("id");
    }
    protected AbstractForecastChangeVisEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 单据头 property 
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
        return new BOSObjectType("42F3CCF4");
    }
}