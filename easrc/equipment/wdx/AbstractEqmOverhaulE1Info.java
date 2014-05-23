package com.kingdee.eas.port.equipment.wdx;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEqmOverhaulE1Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractEqmOverhaulE1Info()
    {
        this("id");
    }
    protected AbstractEqmOverhaulE1Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 设备维大修 's null property 
     */
    public com.kingdee.eas.port.equipment.wdx.EqmOverhaulInfo getParent()
    {
        return (com.kingdee.eas.port.equipment.wdx.EqmOverhaulInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.equipment.wdx.EqmOverhaulInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 设备维大修 's 设备编号 property 
     */
    public com.kingdee.eas.port.equipment.record.EquIdInfo getEquNumber()
    {
        return (com.kingdee.eas.port.equipment.record.EquIdInfo)get("equNumber");
    }
    public void setEquNumber(com.kingdee.eas.port.equipment.record.EquIdInfo item)
    {
        put("equNumber", item);
    }
    /**
     * Object: 设备维大修 's 使用部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getUseDepart()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("useDepart");
    }
    public void setUseDepart(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("useDepart", item);
    }
    /**
     * Object:设备维大修's 设备技术参数property 
     */
    public String getEquTecParametetr()
    {
        return getString("equTecParametetr");
    }
    public void setEquTecParametetr(String item)
    {
        setString("equTecParametetr", item);
    }
    /**
     * Object:设备维大修's 设备名称property 
     */
    public String getEquName()
    {
        return getString("equName");
    }
    public void setEquName(String item)
    {
        setString("equName", item);
    }
    /**
     * Object:设备维大修's 设备类型property 
     */
    public String getEquType()
    {
        return getString("equType");
    }
    public void setEquType(String item)
    {
        setString("equType", item);
    }
    /**
     * Object:设备维大修's 备注property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5D1385B9");
    }
}