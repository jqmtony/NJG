package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractConPayPlanDatapInfo extends com.kingdee.eas.fdc.finance.PayPlanDataBaseInfo implements Serializable 
{
    public AbstractConPayPlanDatapInfo()
    {
        this("id");
    }
    protected AbstractConPayPlanDatapInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 计划数据 's  property 
     */
    public com.kingdee.eas.fdc.finance.ConPayPlanInfo getParent5()
    {
        return (com.kingdee.eas.fdc.finance.ConPayPlanInfo)get("parent5");
    }
    public void setParent5(com.kingdee.eas.fdc.finance.ConPayPlanInfo item)
    {
        put("parent5", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7A3A4648");
    }
}