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
     * Object: 第1个表体 's null property 
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
     * Object: 第1个表体 's 设备编码 property 
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
     * Object:第1个表体's 设备名称property 
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
     * Object:第1个表体's 规格型号property 
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
     * Object:第1个表体's 计量单位property 
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
     * Object:第1个表体's 数量property 
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
     * Object:第1个表体's 租金总额property 
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
     * Object:第1个表体's 用途property 
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