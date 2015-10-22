package com.kingdee.eas.port.pm.qa;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractLinkBillInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractLinkBillInfo()
    {
        this("id");
    }
    protected AbstractLinkBillInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:关联单据信息's 路径property 
     */
    public String getLink()
    {
        return getString("link");
    }
    public void setLink(String item)
    {
        setString("link", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4084C343");
    }
}