package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPayContentTypeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractPayContentTypeInfo()
    {
        this("id");
    }
    protected AbstractPayContentTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��������'s ���û����״̬property 
     */
    public boolean isIsEnabled()
    {
        return getBoolean("isEnabled");
    }
    public void setIsEnabled(boolean item)
    {
        setBoolean("isEnabled", item);
    }
    /**
     * Object: �������� 's ����� property 
     */
    public com.kingdee.eas.fdc.contract.PayContentTypeInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.PayContentTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.PayContentTypeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6DEF5086");
    }
}