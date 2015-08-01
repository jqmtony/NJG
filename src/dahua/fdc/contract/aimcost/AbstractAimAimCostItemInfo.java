package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAimAimCostItemInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractAimAimCostItemInfo()
    {
        this("id");
    }
    protected AbstractAimAimCostItemInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:Ŀ��ɱ���������Ŀ's ����IDproperty 
     */
    public String getObjectID()
    {
        return getString("objectID");
    }
    public void setObjectID(String item)
    {
        setString("objectID", item);
    }
    /**
     * Object:Ŀ��ɱ���������Ŀ's nullproperty 
     */
    public String getCostAccountID()
    {
        return getString("costAccountID");
    }
    public void setCostAccountID(String item)
    {
        setString("costAccountID", item);
    }
    /**
     * Object:Ŀ��ɱ���������Ŀ's ��֧˵��property 
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
     * Object:Ŀ��ɱ���������Ŀ's nullproperty 
     */
    public String getParent()
    {
        return getString("parent");
    }
    public void setParent(String item)
    {
        setString("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A12F7F77");
    }
}