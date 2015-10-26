package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPayPlanNewDataInfo extends com.kingdee.eas.fdc.finance.PayPlanDataBaseInfo implements Serializable 
{
    public AbstractPayPlanNewDataInfo()
    {
        this("id");
    }
    protected AbstractPayPlanNewDataInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 付款数据 's  property 
     */
    public com.kingdee.eas.fdc.finance.PayPlanNewInfo getParent0()
    {
        return (com.kingdee.eas.fdc.finance.PayPlanNewInfo)get("parent0");
    }
    public void setParent0(com.kingdee.eas.fdc.finance.PayPlanNewInfo item)
    {
        put("parent0", item);
    }
    /**
     * Object:付款数据's 行记录property 
     */
    public String getRecordSeq()
    {
        return getString("recordSeq");
    }
    public void setRecordSeq(String item)
    {
        setString("recordSeq", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("802C9708");
    }
}