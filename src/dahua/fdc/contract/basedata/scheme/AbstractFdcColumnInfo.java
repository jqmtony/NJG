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
     * Object: ���ز��� 's ���� property 
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
     * Object: ���ز��� 's ���� property 
     */
    public com.kingdee.eas.fdc.basedata.scheme.FdcPropertyCollection getProperties()
    {
        return (com.kingdee.eas.fdc.basedata.scheme.FdcPropertyCollection)get("properties");
    }
    /**
     * Object:���ز���'s ����property 
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
     * Object:���ز���'s ����property 
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
     * Object:���ز���'s ����property 
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
     * Object:���ز���'s �Ƿ��û�����property 
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
     * Object:���ز���'s SQL��������property 
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
     * Object:���ز���'s ����property 
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
     * Object:���ز���'s ����property 
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
     * Object:���ز���'s С��λ��property 
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
     * Object:���ز���'s �Ƿ�����Ϊ��property 
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
     * Object:���ز���'s Ĭ��ֵproperty 
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
     * Object:���ز���'s �Ƿ������property 
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
     * Object:���ز���'s �Ƿ����property 
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
     * Object:���ز���'s ʵ�������������property 
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
     * Object:���ز���'s ���ݱ�����(�����ֶ�)property 
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