package com.kingdee.eas.fdc.material;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMaterialIndexValueInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractMaterialIndexValueInfo()
    {
        this("id");
    }
    protected AbstractMaterialIndexValueInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:materialIndexValue's 指标值property 
     */
    public String getValue()
    {
        return getString("value");
    }
    public void setValue(String item)
    {
        setString("value", item);
    }
    /**
     * Object: materialIndexValue 's 物料 property 
     */
    public com.kingdee.eas.fdc.material.MaterialInfoInfo getMaterial()
    {
        return (com.kingdee.eas.fdc.material.MaterialInfoInfo)get("material");
    }
    public void setMaterial(com.kingdee.eas.fdc.material.MaterialInfoInfo item)
    {
        put("material", item);
    }
    /**
     * Object: materialIndexValue 's 指标 property 
     */
    public com.kingdee.eas.fdc.material.MaterialIndexInfo getMaterialIndex()
    {
        return (com.kingdee.eas.fdc.material.MaterialIndexInfo)get("materialIndex");
    }
    public void setMaterialIndex(com.kingdee.eas.fdc.material.MaterialIndexInfo item)
    {
        put("materialIndex", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6E5BD60C");
    }
}