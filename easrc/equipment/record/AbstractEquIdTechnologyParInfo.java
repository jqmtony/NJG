package com.kingdee.eas.port.equipment.record;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEquIdTechnologyParInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractEquIdTechnologyParInfo()
    {
        this("id");
    }
    protected AbstractEquIdTechnologyParInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��ϸ�������� 's null property 
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
     * Object:��ϸ��������'s ��������property 
     */
    public String getParName()
    {
        return getString("parName");
    }
    public void setParName(String item)
    {
        setString("parName", item);
    }
    /**
     * Object:��ϸ��������'s ����ֵproperty 
     */
    public String getParValue()
    {
        return getString("parValue");
    }
    public void setParValue(String item)
    {
        setString("parValue", item);
    }
    /**
     * Object:��ϸ��������'s ����˵��property 
     */
    public String getParInfo()
    {
        return getString("parInfo");
    }
    public void setParInfo(String item)
    {
        setString("parInfo", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("62370453");
    }
}