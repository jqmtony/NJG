package com.kingdee.eas.port.equipment.maintenance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRepairOrderE1Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractRepairOrderE1Info()
    {
        this("id");
    }
    protected AbstractRepairOrderE1Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ά����ϸ 's null property 
     */
    public com.kingdee.eas.port.equipment.maintenance.RepairOrderInfo getParent()
    {
        return (com.kingdee.eas.port.equipment.maintenance.RepairOrderInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.equipment.maintenance.RepairOrderInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:ά����ϸ's ά������property 
     */
    public String getRepairContent()
    {
        return getString("repairContent");
    }
    public void setRepairContent(String item)
    {
        setString("repairContent", item);
    }
    /**
     * Object: ά����ϸ 's �������� property 
     */
    public com.kingdee.eas.basedata.master.material.MaterialInfo getReplaceSparePart()
    {
        return (com.kingdee.eas.basedata.master.material.MaterialInfo)get("replaceSparePart");
    }
    public void setReplaceSparePart(com.kingdee.eas.basedata.master.material.MaterialInfo item)
    {
        put("replaceSparePart", item);
    }
    /**
     * Object:ά����ϸ's ��ʱproperty 
     */
    public java.math.BigDecimal getWorkTime()
    {
        return getBigDecimal("workTime");
    }
    public void setWorkTime(java.math.BigDecimal item)
    {
        setBigDecimal("workTime", item);
    }
    /**
     * Object: ά����ϸ 's ������Ա property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getRepairPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("repairPerson");
    }
    public void setRepairPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("repairPerson", item);
    }
    /**
     * Object:ά����ϸ's ��עproperty 
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
        return new BOSObjectType("58358BBD");
    }
}