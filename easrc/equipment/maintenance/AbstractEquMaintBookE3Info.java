package com.kingdee.eas.port.equipment.maintenance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEquMaintBookE3Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractEquMaintBookE3Info()
    {
        this("id");
    }
    protected AbstractEquMaintBookE3Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��3������ 's null property 
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
     * Object:��3������'s �����Ŀproperty 
     */
    public String getJianchaxiangmu()
    {
        return getString("jianchaxiangmu");
    }
    public void setJianchaxiangmu(String item)
    {
        setString("jianchaxiangmu", item);
    }
    /**
     * Object:��3������'s Ҫ��property 
     */
    public String getYaoqiu()
    {
        return getString("yaoqiu");
    }
    public void setYaoqiu(String item)
    {
        setString("yaoqiu", item);
    }
    /**
     * Object:��3������'s ʵ��property 
     */
    public String getShice()
    {
        return getString("shice");
    }
    public void setShice(String item)
    {
        setString("shice", item);
    }
    /**
     * Object:��3������'s ����property 
     */
    public String getJielun()
    {
        return getString("jielun");
    }
    public void setJielun(String item)
    {
        setString("jielun", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D4DC1039");
    }
}