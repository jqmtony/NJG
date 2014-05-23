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
     * Object: 备件信息 's null property 
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
     * Object:备件信息's 物料名称property 
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
     * Object:备件信息's 规格型号property 
     */
    public String getSpeModel()
    {
        return getString("speModel");
    }
    public void setSpeModel(String item)
    {
        setString("speModel", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("59736383");
    }
}