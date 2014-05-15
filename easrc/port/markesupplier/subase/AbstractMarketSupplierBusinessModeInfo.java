package com.kingdee.eas.port.markesupplier.subase;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSupplierBusinessModeInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractMarketSupplierBusinessModeInfo()
    {
        this("id");
    }
    protected AbstractMarketSupplierBusinessModeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��Ӫģʽ's �Ƿ�����property 
     */
    public boolean isIsEnable()
    {
        return getBoolean("isEnable");
    }
    public void setIsEnable(boolean item)
    {
        setBoolean("isEnable", item);
    }
    /**
     * Object:��Ӫģʽ's ��ע��property 
     */
    public String getRemake()
    {
        return getString("remake");
    }
    public void setRemake(String item)
    {
        setString("remake", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("863FF2E1");
    }
}