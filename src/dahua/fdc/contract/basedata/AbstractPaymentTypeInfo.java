package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPaymentTypeInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractPaymentTypeInfo()
    {
        this("id");
    }
    protected AbstractPaymentTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 付款类型 's 类别 property 
     */
    public com.kingdee.eas.fdc.basedata.TypeInfo getPayType()
    {
        return (com.kingdee.eas.fdc.basedata.TypeInfo)get("payType");
    }
    public void setPayType(com.kingdee.eas.fdc.basedata.TypeInfo item)
    {
        put("payType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DE38402E");
    }
}