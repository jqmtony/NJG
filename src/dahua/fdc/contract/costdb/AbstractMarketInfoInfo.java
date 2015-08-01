package com.kingdee.eas.fdc.costdb;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketInfoInfo extends com.kingdee.eas.framework.BillBaseInfo implements Serializable 
{
    public AbstractMarketInfoInfo()
    {
        this("id");
    }
    protected AbstractMarketInfoInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��Ŀ�ļ�'s ��������property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object:��Ŀ�ļ�'s ��������property 
     */
    public String getAttchment()
    {
        return getString("attchment");
    }
    public void setAttchment(String item)
    {
        setString("attchment", item);
    }
    /**
     * Object: ��Ŀ�ļ� 's ��֯ property 
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
     * Object: ��Ŀ�ļ� 's ��֯ property 
     */
    public com.kingdee.eas.basedata.org.PositionInfo getPosition()
    {
        return (com.kingdee.eas.basedata.org.PositionInfo)get("position");
    }
    public void setPosition(com.kingdee.eas.basedata.org.PositionInfo item)
    {
        put("position", item);
    }
    /**
     * Object: ��Ŀ�ļ� 's �������� property 
     */
    public com.kingdee.eas.fdc.costdb.MarketInfoTypeInfo getType()
    {
        return (com.kingdee.eas.fdc.costdb.MarketInfoTypeInfo)get("type");
    }
    public void setType(com.kingdee.eas.fdc.costdb.MarketInfoTypeInfo item)
    {
        put("type", item);
    }
    /**
     * Object:��Ŀ�ļ�'s ����property 
     */
    public String getPersonName()
    {
        return getString("personName");
    }
    public void setPersonName(String item)
    {
        setString("personName", item);
    }
    /**
     * Object:��Ŀ�ļ�'s ������Ҫְλ��Ӧ����֯property 
     */
    public String getPersonOrg()
    {
        return getString("personOrg");
    }
    public void setPersonOrg(String item)
    {
        setString("personOrg", item);
    }
    /**
     * Object:��Ŀ�ļ�'s �Ƿ��Ź���property 
     */
    public boolean isIsGroupFile()
    {
        return getBoolean("isGroupFile");
    }
    public void setIsGroupFile(boolean item)
    {
        setBoolean("isGroupFile", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4FC002EC");
    }
}