package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractForecastChangeVisInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractForecastChangeVisInfo()
    {
        this("id");
    }
    protected AbstractForecastChangeVisInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.aimcost.ForecastChangeVisEntryCollection());
    }
    /**
     * Object: Ԥ�����ǩ֤�� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.aimcost.ForecastChangeVisEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.aimcost.ForecastChangeVisEntryCollection)get("entrys");
    }
    /**
     * Object:Ԥ�����ǩ֤��'s �Ƿ�����ƾ֤property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B4926E1E");
    }
}