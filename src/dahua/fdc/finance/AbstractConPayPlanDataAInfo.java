package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractConPayPlanDataAInfo extends com.kingdee.eas.fdc.finance.PayPlanDataBaseInfo implements Serializable 
{
    public AbstractConPayPlanDataAInfo()
    {
        this("id");
    }
    protected AbstractConPayPlanDataAInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 实际数据 's  property 
     */
    public com.kingdee.eas.fdc.finance.ConPayPlanInfo getParent4()
    {
        return (com.kingdee.eas.fdc.finance.ConPayPlanInfo)get("parent4");
    }
    public void setParent4(com.kingdee.eas.fdc.finance.ConPayPlanInfo item)
    {
        put("parent4", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7A3A4619");
    }
}