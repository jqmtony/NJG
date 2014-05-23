package com.kingdee.eas.port.markesupplier.subase;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSupplierPersonInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractMarketSupplierPersonInfo()
    {
        this("id");
    }
    protected AbstractMarketSupplierPersonInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:ְԱ����'s �Ƿ�����property 
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
     * Object:ְԱ����'s ��ע��property 
     */
    public String getRemake()
    {
        return getString("remake");
    }
    public void setRemake(String item)
    {
        setString("remake", item);
    }
    /**
     * Object: ְԱ���� 's �������� property 
     */
    public com.kingdee.eas.port.markesupplier.subase.MarketSupplierFileTypInfo getDatype()
    {
        return (com.kingdee.eas.port.markesupplier.subase.MarketSupplierFileTypInfo)get("datype");
    }
    public void setDatype(com.kingdee.eas.port.markesupplier.subase.MarketSupplierFileTypInfo item)
    {
        put("datype", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F456DF33");
    }
}