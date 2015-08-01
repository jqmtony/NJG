package com.kingdee.eas.fdc.basedata.scheme;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFdcColumnInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractFdcColumnInfo()
    {
        this("id");
    }
    protected AbstractFdcColumnInfo(String pkField)
    {
        super(pkField);
        put("properties", new com.kingdee.eas.fdc.basedata.scheme.FdcPropertyCollection());
    }
    /**
     * Object: 房地产列 's 单据 property 
     */
    public com.kingdee.eas.fdc.basedata.scheme.FdcDataTableInfo getBill()
    {
        return (com.kingdee.eas.fdc.basedata.scheme.FdcDataTableInfo)get("bill");
    }
    public void setBill(com.kingdee.eas.fdc.basedata.scheme.FdcDataTableInfo item)
    {
        put("bill", item);
    }
    /**
     * Object: 房地产列 's 属性 property 
     */
    public com.kingdee.eas.fdc.basedata.scheme.FdcPropertyCollection getProperties()
    {
        return (com.kingdee.eas.fdc.basedata.scheme.FdcPropertyCollection)get("properties");
    }
    /**
     * Object:房地产列's 名称property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object:房地产列's 别名property 
     */
    public String getAlias()
    {
        return getAlias((Locale)null);
    }
    public void setAlias(String item)
    {
		setAlias(item,(Locale)null);
    }
    public String getAlias(Locale local)
    {
        return TypeConversionUtils.objToString(get("alias", local));
    }
    public void setAlias(String item, Locale local)
    {
        put("alias", item, local);
    }
    /**
     * Object:房地产列's 描述property 
     */
    public String getDescription()
    {
        return getDescription((Locale)null);
    }
    public void setDescription(String item)
    {
		setDescription(item,(Locale)null);
    }
    public String getDescription(Locale local)
    {
        return TypeConversionUtils.objToString(get("description", local));
    }
    public void setDescription(String item, Locale local)
    {
        put("description", item, local);
    }
    /**
     * Object:房地产列's 是否用户定义property 
     */
    public boolean isUserDefined()
    {
        return getBoolean("userDefined");
    }
    public void setUserDefined(boolean item)
    {
        setBoolean("userDefined", item);
    }
    /**
     * Object:房地产列's SQL数据类型property 
     */
    public String getSqlType()
    {
        return getString("sqlType");
    }
    public void setSqlType(String item)
    {
        setString("sqlType", item);
    }
    /**
     * Object:房地产列's 长度property 
     */
    public int getLength()
    {
        return getInt("length");
    }
    public void setLength(int item)
    {
        setInt("length", item);
    }
    /**
     * Object:房地产列's 精度property 
     */
    public int getScale()
    {
        return getInt("scale");
    }
    public void setScale(int item)
    {
        setInt("scale", item);
    }
    /**
     * Object:房地产列's 小数位数property 
     */
    public int getPrecision()
    {
        return getInt("precision");
    }
    public void setPrecision(int item)
    {
        setInt("precision", item);
    }
    /**
     * Object:房地产列's 是否允许为空property 
     */
    public boolean isIsNullable()
    {
        return getBoolean("isNullable");
    }
    public void setIsNullable(boolean item)
    {
        setBoolean("isNullable", item);
    }
    /**
     * Object:房地产列's 默认值property 
     */
    public String getDefaultValue()
    {
        return getString("defaultValue");
    }
    public void setDefaultValue(String item)
    {
        setString("defaultValue", item);
    }
    /**
     * Object:房地产列's 是否多语言property 
     */
    public boolean isIsMultilingual()
    {
        return getBoolean("isMultilingual");
    }
    public void setIsMultilingual(boolean item)
    {
        setBoolean("isMultilingual", item);
    }
    /**
     * Object:房地产列's 是否存在property 
     */
    public boolean isIsExist()
    {
        return getBoolean("isExist");
    }
    public void setIsExist(boolean item)
    {
        setBoolean("isExist", item);
    }
    /**
     * Object:房地产列's 实体对象完整名称property 
     */
    public String getEntityObjectFullName()
    {
        return getString("entityObjectFullName");
    }
    public void setEntityObjectFullName(String item)
    {
        setString("entityObjectFullName", item);
    }
    /**
     * Object:房地产列's 数据表名称(冗余字段)property 
     */
    public String getDataTableName()
    {
        return getString("dataTableName");
    }
    public void setDataTableName(String item)
    {
        setString("dataTableName", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("99A6ED8A");
    }
}