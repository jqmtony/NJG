package com.kingdee.eas.port.equipment.maintenance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEquMaintBookE2Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractEquMaintBookE2Info()
    {
        this("id");
    }
    protected AbstractEquMaintBookE2Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��2������ 's null property 
     */
    public com.kingdee.eas.port.equipment.maintenance.EquMaintBookInfo getParent()
    {
        return (com.kingdee.eas.port.equipment.maintenance.EquMaintBookInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.equipment.maintenance.EquMaintBookInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��2������'s ��Ŀproperty 
     */
    public String getXiangmuone()
    {
        return getString("xiangmuone");
    }
    public void setXiangmuone(String item)
    {
        setString("xiangmuone", item);
    }
    /**
     * Object:��2������'s ��property 
     */
    public String getKongone()
    {
        return getString("kongone");
    }
    public void setKongone(String item)
    {
        setString("kongone", item);
    }
    /**
     * Object:��2������'s ��property 
     */
    public String getFuone()
    {
        return getString("fuone");
    }
    public void setFuone(String item)
    {
        setString("fuone", item);
    }
    /**
     * Object:��2������'s ��property 
     */
    public String getKongtwo()
    {
        return getString("kongtwo");
    }
    public void setKongtwo(String item)
    {
        setString("kongtwo", item);
    }
    /**
     * Object:��2������'s ��property 
     */
    public String getFutwo()
    {
        return getString("futwo");
    }
    public void setFutwo(String item)
    {
        setString("futwo", item);
    }
    /**
     * Object:��2������'s ��property 
     */
    public String getKongthree()
    {
        return getString("kongthree");
    }
    public void setKongthree(String item)
    {
        setString("kongthree", item);
    }
    /**
     * Object:��2������'s ��property 
     */
    public String getFuthree()
    {
        return getString("futhree");
    }
    public void setFuthree(String item)
    {
        setString("futhree", item);
    }
    /**
     * Object:��2������'s ����property 
     */
    public String getJieluntwo()
    {
        return getString("jieluntwo");
    }
    public void setJieluntwo(String item)
    {
        setString("jieluntwo", item);
    }
    /**
     * Object:��2������'s ��עproperty 
     */
    public String getNote()
    {
        return getString("note");
    }
    public void setNote(String item)
    {
        setString("note", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D4DC1038");
    }
}