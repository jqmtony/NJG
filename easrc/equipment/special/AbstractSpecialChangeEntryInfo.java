package com.kingdee.eas.port.equipment.special;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSpecialChangeEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSpecialChangeEntryInfo()
    {
        this("id");
    }
    protected AbstractSpecialChangeEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �����ϸ 's null property 
     */
    public com.kingdee.eas.port.equipment.special.SpecialChangeInfo getParent()
    {
        return (com.kingdee.eas.port.equipment.special.SpecialChangeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.equipment.special.SpecialChangeInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: �����ϸ 's ������� property 
     */
    public com.kingdee.eas.port.equipment.record.EquIdInfo getZdaNumber()
    {
        return (com.kingdee.eas.port.equipment.record.EquIdInfo)get("zdaNumber");
    }
    public void setZdaNumber(com.kingdee.eas.port.equipment.record.EquIdInfo item)
    {
        put("zdaNumber", item);
    }
    /**
     * Object:�����ϸ's �豸����property 
     */
    public String getEquipmentName()
    {
        return getString("equipmentName");
    }
    public void setEquipmentName(String item)
    {
        setString("equipmentName", item);
    }
    /**
     * Object:�����ϸ's ��Ʒ���property 
     */
    public String getProductNumber()
    {
        return getString("productNumber");
    }
    public void setProductNumber(String item)
    {
        setString("productNumber", item);
    }
    /**
     * Object:�����ϸ's ԭʹ�õǼ�ע����property 
     */
    public String getOldNumber()
    {
        return getString("oldNumber");
    }
    public void setOldNumber(String item)
    {
        setString("oldNumber", item);
    }
    /**
     * Object:�����ϸ's ��עproperty 
     */
    public String getBeizhu()
    {
        return getString("beizhu");
    }
    public void setBeizhu(String item)
    {
        setString("beizhu", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6C5FBE13");
    }
}