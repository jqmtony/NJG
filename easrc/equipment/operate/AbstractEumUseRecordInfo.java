package com.kingdee.eas.port.equipment.operate;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEumUseRecordInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractEumUseRecordInfo()
    {
        this("id");
    }
    protected AbstractEumUseRecordInfo(String pkField)
    {
        super(pkField);
        put("EqmUse", new com.kingdee.eas.port.equipment.operate.EumUseRecordEqmUseCollection());
    }
    /**
     * Object: 设备使用记录 's 设备使用记录 property 
     */
    public com.kingdee.eas.port.equipment.operate.EumUseRecordEqmUseCollection getEqmUse()
    {
        return (com.kingdee.eas.port.equipment.operate.EumUseRecordEqmUseCollection)get("EqmUse");
    }
    /**
     * Object: 设备使用记录 's 填报人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getStaPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("staPerson");
    }
    public void setStaPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("staPerson", item);
    }
    /**
     * Object: 设备使用记录 's 使用单位 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getUseOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("UseOrgUnit");
    }
    public void setUseOrgUnit(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("UseOrgUnit", item);
    }
    /**
     * Object:设备使用记录's 是否初始化记录property 
     */
    public boolean isInitialiRecord()
    {
        return getBoolean("initialiRecord");
    }
    public void setInitialiRecord(boolean item)
    {
        setBoolean("initialiRecord", item);
    }
    /**
     * Object: 设备使用记录 's 报表月份 property 
     */
    public com.kingdee.eas.port.equipment.base.MonthTimeInfo getReportTime()
    {
        return (com.kingdee.eas.port.equipment.base.MonthTimeInfo)get("reportTime");
    }
    public void setReportTime(com.kingdee.eas.port.equipment.base.MonthTimeInfo item)
    {
        put("reportTime", item);
    }
    /**
     * Object: 设备使用记录 's 报表月份 property 
     */
    public com.kingdee.eas.port.equipment.base.MonthTimeInfo getReportMonth()
    {
        return (com.kingdee.eas.port.equipment.base.MonthTimeInfo)get("reportMonth");
    }
    public void setReportMonth(com.kingdee.eas.port.equipment.base.MonthTimeInfo item)
    {
        put("reportMonth", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3ABA977A");
    }
}