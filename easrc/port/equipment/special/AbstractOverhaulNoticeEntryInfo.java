package com.kingdee.eas.port.equipment.special;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractOverhaulNoticeEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractOverhaulNoticeEntryInfo()
    {
        this("id");
    }
    protected AbstractOverhaulNoticeEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ������ϸ 's null property 
     */
    public com.kingdee.eas.port.equipment.special.OverhaulNoticeInfo getParent()
    {
        return (com.kingdee.eas.port.equipment.special.OverhaulNoticeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.equipment.special.OverhaulNoticeInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ������ϸ 's �豸������ property 
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
     * Object:������ϸ's �豸����property 
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
     * Object: ������ϸ 's ���ϸ���Ŀ property 
     */
    public com.kingdee.eas.port.equipment.base.SpecialCheckItemInfo getNoCheckItem()
    {
        return (com.kingdee.eas.port.equipment.base.SpecialCheckItemInfo)get("noCheckItem");
    }
    public void setNoCheckItem(com.kingdee.eas.port.equipment.base.SpecialCheckItemInfo item)
    {
        put("noCheckItem", item);
    }
    /**
     * Object:������ϸ's ���ϸ�����property 
     */
    public String getCheckContent()
    {
        return getString("checkContent");
    }
    public void setCheckContent(String item)
    {
        setString("checkContent", item);
    }
    /**
     * Object:������ϸ's ��עproperty 
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
        return new BOSObjectType("50CA390C");
    }
}