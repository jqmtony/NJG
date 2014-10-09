package com.kingdee.eas.bpmdemo;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChangeVisaAppSendUnitInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractChangeVisaAppSendUnitInfo()
    {
        this("id");
    }
    protected AbstractChangeVisaAppSendUnitInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 下发单位分录 's null property 
     */
    public com.kingdee.eas.bpmdemo.ChangeVisaAppInfo getParent()
    {
        return (com.kingdee.eas.bpmdemo.ChangeVisaAppInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.bpmdemo.ChangeVisaAppInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4134F01E");
    }
}