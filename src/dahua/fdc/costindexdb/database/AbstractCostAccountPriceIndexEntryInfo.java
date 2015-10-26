package com.kingdee.eas.fdc.costindexdb.database;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCostAccountPriceIndexEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractCostAccountPriceIndexEntryInfo()
    {
        this("id");
    }
    protected AbstractCostAccountPriceIndexEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ÅäÖÃ·ÖÂ¼ 's null property 
     */
    public com.kingdee.eas.fdc.costindexdb.database.CostAccountPriceIndexInfo getParent()
    {
        return (com.kingdee.eas.fdc.costindexdb.database.CostAccountPriceIndexInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.costindexdb.database.CostAccountPriceIndexInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:ÅäÖÃ·ÖÂ¼'s ×Ö¶ÎÃû³Æproperty 
     */
    public String getFieldName()
    {
        return getString("fieldName");
    }
    public void setFieldName(String item)
    {
        setString("fieldName", item);
    }
    /**
     * Object:ÅäÖÃ·ÖÂ¼'s ×Ö¶ÎÒş²Øproperty 
     */
    public boolean isFieldHide()
    {
        return getBoolean("fieldHide");
    }
    public void setFieldHide(boolean item)
    {
        setBoolean("fieldHide", item);
    }
    /**
     * Object:ÅäÖÃ·ÖÂ¼'s ×Ö¶Î±ØÂ¼property 
     */
    public boolean isFieldInput()
    {
        return getBoolean("fieldInput");
    }
    public void setFieldInput(boolean item)
    {
        setBoolean("fieldInput", item);
    }
    /**
     * Object:ÅäÖÃ·ÖÂ¼'s ¹«Ê½ÄÚÈİproperty 
     */
    public String getFcontent()
    {
        return getString("fcontent");
    }
    public void setFcontent(String item)
    {
        setString("fcontent", item);
    }
    /**
     * Object:ÅäÖÃ·ÖÂ¼'s ×Ö¶ÎÀàĞÍproperty 
     */
    public com.kingdee.eas.fdc.costindexdb.database.FieldType getFieldType()
    {
        return com.kingdee.eas.fdc.costindexdb.database.FieldType.getEnum(getString("fieldType"));
    }
    public void setFieldType(com.kingdee.eas.fdc.costindexdb.database.FieldType item)
    {
		if (item != null) {
        setString("fieldType", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A670C290");
    }
}