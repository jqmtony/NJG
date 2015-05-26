package com.kingdee.eas.test;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPurOrderInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractPurOrderInfo()
    {
        this("id");
    }
    protected AbstractPurOrderInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.test.PurOrderEntryCollection());
    }
    /**
     * Object:采购订单's 地址property 
     */
    public String getAddress()
    {
        return getString("address");
    }
    public void setAddress(String item)
    {
        setString("address", item);
    }
    /**
     * Object: 采购订单 's 采购员 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getPurPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("purPerson");
    }
    public void setPurPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("purPerson", item);
    }
    /**
     * Object: 采购订单 's 分录 property 
     */
    public com.kingdee.eas.test.PurOrderEntryCollection getEntry()
    {
        return (com.kingdee.eas.test.PurOrderEntryCollection)get("entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("823A7F18");
    }
}