package com.kingdee.eas.port.markesupplier.subase;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSplAuditIndexInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractMarketSplAuditIndexInfo()
    {
        this("id");
    }
    protected AbstractMarketSplAuditIndexInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ����ָ�� 's ��� property 
     */
    public com.kingdee.eas.port.markesupplier.subase.MarketSplAuditIndexTreeInfo getTreeid()
    {
        return (com.kingdee.eas.port.markesupplier.subase.MarketSplAuditIndexTreeInfo)get("treeid");
    }
    public void setTreeid(com.kingdee.eas.port.markesupplier.subase.MarketSplAuditIndexTreeInfo item)
    {
        put("treeid", item);
    }
    /**
     * Object:����ָ��'s 3�ֱ�׼property 
     */
    public String getThreeStandard()
    {
        return getString("threeStandard");
    }
    public void setThreeStandard(String item)
    {
        setString("threeStandard", item);
    }
    /**
     * Object:����ָ��'s ��ע��property 
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
     * Object:����ָ��'s �Ƿ�����property 
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
     * Object: ����ָ�� 's �������� property 
     */
    public com.kingdee.eas.port.markesupplier.subase.MarketAccreditationTypeInfo getAccreditationType()
    {
        return (com.kingdee.eas.port.markesupplier.subase.MarketAccreditationTypeInfo)get("AccreditationType");
    }
    public void setAccreditationType(com.kingdee.eas.port.markesupplier.subase.MarketAccreditationTypeInfo item)
    {
        put("AccreditationType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B1BECB94");
    }
}