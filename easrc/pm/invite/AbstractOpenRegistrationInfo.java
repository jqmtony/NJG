package com.kingdee.eas.port.pm.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractOpenRegistrationInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractOpenRegistrationInfo()
    {
        this("id");
    }
    protected AbstractOpenRegistrationInfo(String pkField)
    {
        super(pkField);
        put("Entry", new com.kingdee.eas.port.pm.invite.OpenRegistrationEntryCollection());
    }
    /**
     * Object:开标登记's 开标地点property 
     */
    public String getOpLocation()
    {
        return getString("opLocation");
    }
    public void setOpLocation(String item)
    {
        setString("opLocation", item);
    }
    /**
     * Object:开标登记's 开标时间property 
     */
    public java.util.Date getOpDate()
    {
        return getDate("opDate");
    }
    public void setOpDate(java.util.Date item)
    {
        setDate("opDate", item);
    }
    /**
     * Object: 开标登记 's 招标方案 property 
     */
    public com.kingdee.eas.port.pm.invite.InviteReportInfo getReportName()
    {
        return (com.kingdee.eas.port.pm.invite.InviteReportInfo)get("reportName");
    }
    public void setReportName(com.kingdee.eas.port.pm.invite.InviteReportInfo item)
    {
        put("reportName", item);
    }
    /**
     * Object:开标登记's 截标开标时间property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    /**
     * Object: 开标登记 's 招标单位信息 property 
     */
    public com.kingdee.eas.port.pm.invite.OpenRegistrationEntryCollection getEntry()
    {
        return (com.kingdee.eas.port.pm.invite.OpenRegistrationEntryCollection)get("Entry");
    }
    /**
     * Object:开标登记's 招标编码property 
     */
    public String getReportNumber()
    {
        return getString("reportNumber");
    }
    public void setReportNumber(String item)
    {
        setString("reportNumber", item);
    }
    /**
     * Object:开标登记's 开标登记名称property 
     */
    public String getRegName()
    {
        return getString("regName");
    }
    public void setRegName(String item)
    {
        setString("regName", item);
    }
    /**
     * Object:开标登记's 评标基准系数Xproperty 
     */
    public String getCoefficient()
    {
        return getString("coefficient");
    }
    public void setCoefficient(String item)
    {
        setString("coefficient", item);
    }
    /**
     * Object:开标登记's 是否作废property 
     */
    public boolean isCancel()
    {
        return getBoolean("cancel");
    }
    public void setCancel(boolean item)
    {
        setBoolean("cancel", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D4333B6C");
    }
}