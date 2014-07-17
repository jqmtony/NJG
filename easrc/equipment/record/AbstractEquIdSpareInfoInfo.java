package com.kingdee.eas.port.equipment.record;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEquIdSpareInfoInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractEquIdSpareInfoInfo()
    {
        this("id");
    }
    protected AbstractEquIdSpareInfoInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ������Ϣ 's null property 
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
     * Object:������Ϣ's ��������property 
     */
    public String getMaterialName()
    {
        return getString("materialName");
    }
    public void setMaterialName(String item)
    {
        setString("materialName", item);
    }
    /**
     * Object:������Ϣ's ����ͺ�property 
     */
    public String getSpeModel()
    {
        return getString("speModel");
    }
    public void setSpeModel(String item)
    {
        setString("speModel", item);
    }
    /**
     * Object:������Ϣ's ����property 
     */
    public int getShuliangone()
    {
        return getInt("shuliangone");
    }
    public void setShuliangone(int item)
    {
        setInt("shuliangone", item);
    }
    /**
     * Object:������Ϣ's ��;property 
     */
    public String getUseyong()
    {
        return getString("useyong");
    }
    public void setUseyong(String item)
    {
        setString("useyong", item);
    }
    /**
     * Object:������Ϣ's ����property 
     */
    public String getFachangjia()
    {
        return getString("fachangjia");
    }
    public void setFachangjia(String item)
    {
        setString("fachangjia", item);
    }
    /**
     * Object:������Ϣ's ��עproperty 
     */
    public String getNoteone()
    {
        return getString("noteone");
    }
    public void setNoteone(String item)
    {
        setString("noteone", item);
    }
    /**
     * Object:������Ϣ's ����property 
     */
    public String getAttachone()
    {
        return getString("attachone");
    }
    public void setAttachone(String item)
    {
        setString("attachone", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("59736383");
    }
}