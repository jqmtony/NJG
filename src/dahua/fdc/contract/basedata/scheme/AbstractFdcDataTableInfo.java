package com.kingdee.eas.fdc.basedata.scheme;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFdcDataTableInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractFdcDataTableInfo()
    {
        this("id");
    }
    protected AbstractFdcDataTableInfo(String pkField)
    {
        super(pkField);
        put("entityObjects", new com.kingdee.eas.fdc.basedata.scheme.FdcEntityObjectCollection());
        put("entries", new com.kingdee.eas.fdc.basedata.scheme.FdcColumnCollection());
    }
    /**
     * Object: 房地产数据表 's 分录 property 
     */
    public com.kingdee.eas.fdc.basedata.scheme.FdcColumnCollection getEntries()
    {
        return (com.kingdee.eas.fdc.basedata.scheme.FdcColumnCollection)get("entries");
    }
    /**
     * Object: 房地产数据表 's 实体对象 property 
     */
    public com.kingdee.eas.fdc.basedata.scheme.FdcEntityObjectCollection getEntityObjects()
    {
        return (com.kingdee.eas.fdc.basedata.scheme.FdcEntityObjectCollection)get("entityObjects");
    }
    /**
     * Object:房地产数据表's 包名property 
     */
    public String getPackageName()
    {
        return getString("packageName");
    }
    public void setPackageName(String item)
    {
        setString("packageName", item);
    }
    /**
     * Object:房地产数据表's 名称property 
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
     * Object:房地产数据表's 完整名称property 
     */
    public String getFullName()
    {
        return getString("fullName");
    }
    public void setFullName(String item)
    {
        setString("fullName", item);
    }
    /**
     * Object:房地产数据表's 别名property 
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
     * Object:房地产数据表's 描述property 
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
     * Object:房地产数据表's 是否用户定义property 
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
     * Object:房地产数据表's 主键property 
     */
    public String getPrimaryKey()
    {
        return getString("primaryKey");
    }
    public void setPrimaryKey(String item)
    {
        setString("primaryKey", item);
    }
    /**
     * Object:房地产数据表's 是否存在property 
     */
    public boolean isIsExist()
    {
        return getBoolean("isExist");
    }
    public void setIsExist(boolean item)
    {
        setBoolean("isExist", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1234DF90");
    }
}