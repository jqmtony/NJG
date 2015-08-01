package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEntryDescInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractEntryDescInfo()
    {
        this("id");
    }
    protected AbstractEntryDescInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:'s ͷproperty 
     */
    public String getHead()
    {
        return getString("head");
    }
    public void setHead(String item)
    {
        setString("head", item);
    }
    /**
     * Object:'s ����property 
     */
    public String getDesc()
    {
        return getString("desc");
    }
    public void setDesc(String item)
    {
        setString("desc", item);
    }
    /**
     * Object:'s ��¼property 
     */
    public String getEntry()
    {
        return getString("entry");
    }
    public void setEntry(String item)
    {
        setString("entry", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("796F8B71");
    }
}