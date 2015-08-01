package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractConPayPlanByScheduleDatazInfo extends com.kingdee.eas.fdc.finance.PayPlanDataBaseInfo implements Serializable 
{
    public AbstractConPayPlanByScheduleDatazInfo()
    {
        this("id");
    }
    protected AbstractConPayPlanByScheduleDatazInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 节点数据 's  property 
     */
    public com.kingdee.eas.fdc.finance.ConPayPlanByScheduleInfo getParent12()
    {
        return (com.kingdee.eas.fdc.finance.ConPayPlanByScheduleInfo)get("parent12");
    }
    public void setParent12(com.kingdee.eas.fdc.finance.ConPayPlanByScheduleInfo item)
    {
        put("parent12", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("CC600864");
    }
}