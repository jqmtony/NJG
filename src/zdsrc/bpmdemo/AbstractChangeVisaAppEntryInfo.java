package com.kingdee.eas.bpmdemo;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChangeVisaAppEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractChangeVisaAppEntryInfo()
    {
        this("id");
    }
    protected AbstractChangeVisaAppEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 基础资料分录 's 单据头 property 
     */
    public com.kingdee.eas.bpmdemo.ChangeVisaAppInfo getParent()
    {
        return (com.kingdee.eas.bpmdemo.ChangeVisaAppInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.bpmdemo.ChangeVisaAppInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:基础资料分录's 变更内容编码property 
     */
    public String getNumber()
    {
        return getString("number");
    }
    public void setNumber(String item)
    {
        setString("number", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A777E6C0");
    }
}