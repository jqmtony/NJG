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
     * Object:项目文件's 资料名称property 
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
     * Object:项目文件's 附件名称property 
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
     * Object: 项目文件 's 组织 property 
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
     * Object: 项目文件 's 组织 property 
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
     * Object: 项目文件 's 资料类型 property 
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
     * Object:项目文件's 作者property 
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
     * Object:项目文件's 作者主要职位对应的组织property 
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
     * Object:项目文件's 是否集团共享property 
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