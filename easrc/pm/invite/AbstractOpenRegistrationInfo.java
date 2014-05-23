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
     * Object:����Ǽ�'s ����ص�property 
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
     * Object:����Ǽ�'s ����ʱ��property 
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
     * Object: ����Ǽ� 's �б귽�� property 
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
     * Object:����Ǽ�'s �ر꿪��ʱ��property 
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
     * Object: ����Ǽ� 's �б굥λ��Ϣ property 
     */
    public com.kingdee.eas.port.pm.invite.OpenRegistrationEntryCollection getEntry()
    {
        return (com.kingdee.eas.port.pm.invite.OpenRegistrationEntryCollection)get("Entry");
    }
    /**
     * Object:����Ǽ�'s �б����property 
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
     * Object:����Ǽ�'s ����Ǽ�����property 
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
     * Object:����Ǽ�'s �����׼ϵ��Xproperty 
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
     * Object:����Ǽ�'s �Ƿ�����property 
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