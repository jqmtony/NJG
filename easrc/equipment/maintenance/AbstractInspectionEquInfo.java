package com.kingdee.eas.port.equipment.maintenance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInspectionEquInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractInspectionEquInfo()
    {
        this("id");
    }
    protected AbstractInspectionEquInfo(String pkField)
    {
        super(pkField);
        put("E1", new com.kingdee.eas.port.equipment.maintenance.InspectionEquE1Collection());
    }
    /**
     * Object: 设备巡检记录单 's 第1个表体 property 
     */
    public com.kingdee.eas.port.equipment.maintenance.InspectionEquE1Collection getE1()
    {
        return (com.kingdee.eas.port.equipment.maintenance.InspectionEquE1Collection)get("E1");
    }
    /**
     * Object: 设备巡检记录单 's 登录人部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getUserDepatrt()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("userDepatrt");
    }
    public void setUserDepatrt(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("userDepatrt", item);
    }
    /**
     * Object:设备巡检记录单's 登录人部门长编码property 
     */
    public String getChangNumber()
    {
        return getString("changNumber");
    }
    public void setChangNumber(String item)
    {
        setString("changNumber", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("791AE6E5");
    }
}