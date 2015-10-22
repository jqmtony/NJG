package com.kingdee.eas.port.pm.qa;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectOAE1Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractProjectOAE1Info()
    {
        this("id");
    }
    protected AbstractProjectOAE1Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: È±ÏÝ¸ú×Ù 's null property 
     */
    public com.kingdee.eas.port.pm.qa.ProjectOAInfo getParent()
    {
        return (com.kingdee.eas.port.pm.qa.ProjectOAInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.qa.ProjectOAInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:È±ÏÝ¸ú×Ù's ¸½¼þÃû³Æproperty 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    /**
     * Object:È±ÏÝ¸ú×Ù's ËµÃ÷property 
     */
    public String getShuom()
    {
        return getString("shuom");
    }
    public void setShuom(String item)
    {
        setString("shuom", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("61EB6915");
    }
}