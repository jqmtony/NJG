package com.kingdee.eas.fdc.costdb;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketInfoTypeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractMarketInfoTypeInfo()
    {
        this("id");
    }
    protected AbstractMarketInfoTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��Ŀ�ļ����� 's ���ڵ� property 
     */
    public com.kingdee.eas.fdc.costdb.MarketInfoTypeInfo getParent()
    {
        return (com.kingdee.eas.fdc.costdb.MarketInfoTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.costdb.MarketInfoTypeInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ��Ŀ�ļ����� 's ������֯ property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getOrg()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("org");
    }
    public void setOrg(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("org", item);
    }
    /**
     * Object:��Ŀ�ļ�����'s �Ƿ�ϵͳԤ��property 
     */
    public boolean isIsDefault()
    {
        return getBoolean("isDefault");
    }
    public void setIsDefault(boolean item)
    {
        setBoolean("isDefault", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9914B046");
    }
}