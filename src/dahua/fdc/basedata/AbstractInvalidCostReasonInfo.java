package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInvalidCostReasonInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractInvalidCostReasonInfo()
    {
        this("id");
    }
    protected AbstractInvalidCostReasonInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��Ч�ɱ�ԭ��'s ���û����״̬property 
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
     * Object: ��Ч�ɱ�ԭ�� 's ���ڵ� property 
     */
    public com.kingdee.eas.fdc.basedata.InvalidCostReasonInfo getParent()
    {
        return (com.kingdee.eas.fdc.basedata.InvalidCostReasonInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.basedata.InvalidCostReasonInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F042D116");
    }
}