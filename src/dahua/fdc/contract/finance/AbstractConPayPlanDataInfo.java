package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractConPayPlanDataInfo extends com.kingdee.eas.fdc.finance.PayPlanDataBaseInfo implements Serializable 
{
    public AbstractConPayPlanDataInfo()
    {
        this("id");
    }
    protected AbstractConPayPlanDataInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 合同付款规划数据 's  property 
     */
    public com.kingdee.eas.fdc.finance.ConPayPlanInfo getParent0()
    {
        return (com.kingdee.eas.fdc.finance.ConPayPlanInfo)get("parent0");
    }
    public void setParent0(com.kingdee.eas.fdc.finance.ConPayPlanInfo item)
    {
        put("parent0", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("24F99F28");
    }
}