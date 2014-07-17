package com.kingdee.eas.port.equipment.record;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEquIdE3Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractEquIdE3Info()
    {
        this("id");
    }
    protected AbstractEquIdE3Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��3������ 's null property 
     */
    public com.kingdee.eas.port.equipment.record.EquIdInfo getParent1()
    {
        return (com.kingdee.eas.port.equipment.record.EquIdInfo)get("parent1");
    }
    public void setParent1(com.kingdee.eas.port.equipment.record.EquIdInfo item)
    {
        put("parent1", item);
    }
    /**
     * Object:��3������'s ��������property 
     */
    public String getCsmingcheng()
    {
        return getString("csmingcheng");
    }
    public void setCsmingcheng(String item)
    {
        setString("csmingcheng", item);
    }
    /**
     * Object:��3������'s ����ͺ�property 
     */
    public String getCsmodel()
    {
        return getString("csmodel");
    }
    public void setCsmodel(String item)
    {
        setString("csmodel", item);
    }
    /**
     * Object:��3������'s ����property 
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
     * Object:��3������'s ����property 
     */
    public String getPower()
    {
        return getString("power");
    }
    public void setPower(String item)
    {
        setString("power", item);
    }
    /**
     * Object:��3������'s ת��property 
     */
    public String getSpeed()
    {
        return getString("speed");
    }
    public void setSpeed(String item)
    {
        setString("speed", item);
    }
    /**
     * Object:��3������'s ������property 
     */
    public String getChuandong()
    {
        return getString("chuandong");
    }
    public void setChuandong(String item)
    {
        setString("chuandong", item);
    }
    /**
     * Object:��3������'s �Զ�����property 
     */
    public String getZidong()
    {
        return getString("zidong");
    }
    public void setZidong(String item)
    {
        setString("zidong", item);
    }
    /**
     * Object:��3������'s ���쳧��property 
     */
    public String getMadeFac()
    {
        return getString("madeFac");
    }
    public void setMadeFac(String item)
    {
        setString("madeFac", item);
    }
    /**
     * Object:��3������'s ��עproperty 
     */
    public String getNoteoo()
    {
        return getString("noteoo");
    }
    public void setNoteoo(String item)
    {
        setString("noteoo", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("ACA01ED0");
    }
}