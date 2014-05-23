package com.kingdee.eas.port.equipment.operate;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEqmScrapInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractEqmScrapInfo()
    {
        this("id");
    }
    protected AbstractEqmScrapInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 设备报废单 's 设备单号 property 
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
     * Object:设备报废单's 设备名称property 
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
     * Object: 设备报废单 's 所属单位 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getSsOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("ssOrgUnit");
    }
    public void setSsOrgUnit(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("ssOrgUnit", item);
    }
    /**
     * Object: 设备报废单 's 使用部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getUsedDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("usedDept");
    }
    public void setUsedDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("usedDept", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("08D6068F");
    }
}