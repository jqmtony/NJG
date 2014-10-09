package com.kingdee.eas.bpmdemo;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChangeVisaAppAssEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractChangeVisaAppAssEntryInfo()
    {
        this("id");
    }
    protected AbstractChangeVisaAppAssEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 辅助资料分录 's null property 
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
     * Object:辅助资料分录's 事项property 
     */
    public String getMatter()
    {
        return getString("matter");
    }
    public void setMatter(String item)
    {
        setString("matter", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DED5DD63");
    }
}