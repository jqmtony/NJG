package com.kingdee.eas.port.equipment.operate;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEquLeaseE1Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractEquLeaseE1Info()
    {
        this("id");
    }
    protected AbstractEquLeaseE1Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��1������ 's null property 
     */
    public com.kingdee.eas.port.equipment.operate.EquLeaseInfo getParent()
    {
        return (com.kingdee.eas.port.equipment.operate.EquLeaseInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.equipment.operate.EquLeaseInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ��1������ 's �豸���� property 
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
     * Object:��1������'s �豸����property 
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
     * Object:��1������'s ����ͺ�property 
     */
    public String getModel()
    {
        return getString("model");
    }
    public void setModel(String item)
    {
        setString("model", item);
    }
    /**
     * Object:��1������'s ������λproperty 
     */
    public String getUnit()
    {
        return getString("unit");
    }
    public void setUnit(String item)
    {
        setString("unit", item);
    }
    /**
     * Object:��1������'s ����property 
     */
    public int getShuliang()
    {
        return getInt("shuliang");
    }
    public void setShuliang(int item)
    {
        setInt("shuliang", item);
    }
    /**
     * Object:��1������'s ����ܶ�property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object:��1������'s ��;property 
     */
    public String getUse()
    {
        return getString("use");
    }
    public void setUse(String item)
    {
        setString("use", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("FAAE969C");
    }
}