package com.kingdee.eas.fdc.basedata.scheme;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFdcPropertyInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractFdcPropertyInfo()
    {
        this("id");
    }
    protected AbstractFdcPropertyInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���ز����� 's ���� property 
     */
    public com.kingdee.eas.fdc.basedata.scheme.FdcEntityObjectInfo getBill()
    {
        return (com.kingdee.eas.fdc.basedata.scheme.FdcEntityObjectInfo)get("bill");
    }
    public void setBill(com.kingdee.eas.fdc.basedata.scheme.FdcEntityObjectInfo item)
    {
        put("bill", item);
    }
    /**
     * Object: ���ز����� 's ӳ���ֶ� property 
     */
    public com.kingdee.eas.fdc.basedata.scheme.FdcColumnInfo getMappingField()
    {
        return (com.kingdee.eas.fdc.basedata.scheme.FdcColumnInfo)get("mappingField");
    }
    public void setMappingField(com.kingdee.eas.fdc.basedata.scheme.FdcColumnInfo item)
    {
        put("mappingField", item);
    }
    /**
     * Object: ���ز����� 's �������� property 
     */
    public com.kingdee.eas.fdc.basedata.scheme.FdcEntityObjectInfo getLinkProperty()
    {
        return (com.kingdee.eas.fdc.basedata.scheme.FdcEntityObjectInfo)get("linkProperty");
    }
    public void setLinkProperty(com.kingdee.eas.fdc.basedata.scheme.FdcEntityObjectInfo item)
    {
        put("linkProperty", item);
    }
    /**
     * Object:���ز�����'s ����property 
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
     * Object:���ز�����'s ����property 
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
     * Object:���ز�����'s ����property 
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
     * Object:���ز�����'s ʵ�������������(�����ֶ�)property 
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
     * Object:���ز�����'s ���ݱ�����(�����ֶ�)property 
     */
    public String getDataTableName()
    {
        return getString("dataTableName");
    }
    public void setDataTableName(String item)
    {
        setString("dataTableName", item);
    }
    /**
     * Object:���ز�����'s ӳ���ֶ�����(�����ֶ�)property 
     */
    public String getMappingFieldName()
    {
        return getString("mappingFieldName");
    }
    public void setMappingFieldName(String item)
    {
        setString("mappingFieldName", item);
    }
    /**
     * Object:���ز�����'s ��������BOS����(�����ֶ�)property 
     */
    public String getLinkPropertyBosTypeStr()
    {
        return getString("linkPropertyBosTypeStr");
    }
    public void setLinkPropertyBosTypeStr(String item)
    {
        setString("linkPropertyBosTypeStr", item);
    }
    /**
     * Object:���ز�����'s ��ϵUrl����(�����ֶ�)property 
     */
    public String getRelationshipUrlName()
    {
        return getString("relationshipUrlName");
    }
    public void setRelationshipUrlName(String item)
    {
        setString("relationshipUrlName", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B5983B09");
    }
}