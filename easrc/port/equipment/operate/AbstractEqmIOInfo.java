package com.kingdee.eas.port.equipment.operate;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEqmIOInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractEqmIOInfo()
    {
        this("id");
    }
    protected AbstractEqmIOInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 设备调出调入 's 设备编号 property 
     */
    public com.kingdee.eas.port.equipment.record.EquIdInfo getEqmNumber()
    {
        return (com.kingdee.eas.port.equipment.record.EquIdInfo)get("eqmNumber");
    }
    public void setEqmNumber(com.kingdee.eas.port.equipment.record.EquIdInfo item)
    {
        put("eqmNumber", item);
    }
    /**
     * Object:设备调出调入's 设备名称property 
     */
    public String getEqmName()
    {
        return getString("eqmName");
    }
    public void setEqmName(String item)
    {
        setString("eqmName", item);
    }
    /**
     * Object: 设备调出调入 's 调出单位 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getOutOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("outOrgUnit");
    }
    public void setOutOrgUnit(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("outOrgUnit", item);
    }
    /**
     * Object:设备调出调入's 原安装地点property 
     */
    public String getOldInstallAdress()
    {
        return getString("oldInstallAdress");
    }
    public void setOldInstallAdress(String item)
    {
        setString("oldInstallAdress", item);
    }
    /**
     * Object: 设备调出调入 's 调入单位 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getInOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("InOrgUnit");
    }
    public void setInOrgUnit(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("InOrgUnit", item);
    }
    /**
     * Object: 设备调出调入 's 原使用部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getOldUseingDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("oldUseingDept");
    }
    public void setOldUseingDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("oldUseingDept", item);
    }
    /**
     * Object: 设备调出调入 's 使用部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getUseingOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("useingOrgUnit");
    }
    public void setUseingOrgUnit(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("useingOrgUnit", item);
    }
    /**
     * Object:设备调出调入's 安装地点property 
     */
    public String getInstallAdress()
    {
        return getString("installAdress");
    }
    public void setInstallAdress(String item)
    {
        setString("installAdress", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A4EDF708");
    }
}