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
     * Object: 第3个表体 's null property 
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
     * Object:第3个表体's 参数名称property 
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
     * Object:第3个表体's 规格型号property 
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
     * Object:第3个表体's 数量property 
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
     * Object:第3个表体's 功率property 
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
     * Object:第3个表体's 转速property 
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
     * Object:第3个表体's 传动比property 
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
     * Object:第3个表体's 自动力矩property 
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
     * Object:第3个表体's 制造厂家property 
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
     * Object:第3个表体's 备注property 
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