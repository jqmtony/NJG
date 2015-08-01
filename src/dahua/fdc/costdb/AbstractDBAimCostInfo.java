package com.kingdee.eas.fdc.costdb;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDBAimCostInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractDBAimCostInfo()
    {
        this("id");
    }
    protected AbstractDBAimCostInfo(String pkField)
    {
        super(pkField);
        put("costEntry", new com.kingdee.eas.fdc.costdb.DBCostEntryCollection());
    }
    /**
     * Object:目标成本's 组织或者工程项目Idproperty 
     */
    public String getOrgOrProId()
    {
        return getString("orgOrProId");
    }
    public void setOrgOrProId(String item)
    {
        setString("orgOrProId", item);
    }
    /**
     * Object: 目标成本 's 分录 property 
     */
    public com.kingdee.eas.fdc.costdb.DBCostEntryCollection getCostEntry()
    {
        return (com.kingdee.eas.fdc.costdb.DBCostEntryCollection)get("costEntry");
    }
    /**
     * Object:目标成本's 版本号property 
     */
    public String getVersionNumber()
    {
        return getString("versionNumber");
    }
    public void setVersionNumber(String item)
    {
        setString("versionNumber", item);
    }
    /**
     * Object:目标成本's 版本名称property 
     */
    public String getVersionName()
    {
        return getString("versionName");
    }
    public void setVersionName(String item)
    {
        setString("versionName", item);
    }
    /**
     * Object:目标成本's 修订日期property 
     */
    public java.util.Date getRecenseDate()
    {
        return getDate("recenseDate");
    }
    public void setRecenseDate(java.util.Date item)
    {
        setDate("recenseDate", item);
    }
    /**
     * Object:目标成本's 审批日期property 
     */
    public java.util.Date getAuditDate()
    {
        return getDate("auditDate");
    }
    public void setAuditDate(java.util.Date item)
    {
        setDate("auditDate", item);
    }
    /**
     * Object:目标成本's 是否最新版本property 
     */
    public boolean isIsLastVersion()
    {
        return getBoolean("isLastVersion");
    }
    public void setIsLastVersion(boolean item)
    {
        setBoolean("isLastVersion", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("675AE052");
    }
}