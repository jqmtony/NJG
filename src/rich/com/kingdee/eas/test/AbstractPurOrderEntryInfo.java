package com.kingdee.eas.test;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPurOrderEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractPurOrderEntryInfo()
    {
        this("id");
    }
    protected AbstractPurOrderEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:������¼'s ��Ʒ����property 
     */
    public String getMeterilName()
    {
        return getString("meterilName");
    }
    public void setMeterilName(String item)
    {
        setString("meterilName", item);
    }
    /**
     * Object: ������¼ 's ����ͷ property 
     */
    public com.kingdee.eas.test.PurOrderInfo getParent()
    {
        return (com.kingdee.eas.test.PurOrderInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.test.PurOrderInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B671483A");
    }
}