package com.kingdee.eas.fdc.basedata.scheme;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFdcEntityObjectInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractFdcEntityObjectInfo()
    {
        this("id");
    }
    protected AbstractFdcEntityObjectInfo(String pkField)
    {
        super(pkField);
        put("entries", new com.kingdee.eas.fdc.basedata.scheme.FdcPropertyCollection());
    }
    /**
     * Object: ���ز�ʵ����� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.basedata.scheme.FdcPropertyCollection getEntries()
    {
        return (com.kingdee.eas.fdc.basedata.scheme.FdcPropertyCollection)get("entries");
    }
    /**
     * Object: ���ز�ʵ����� 's ���ݱ� property 
     */
    public com.kingdee.eas.fdc.basedata.scheme.FdcDataTableInfo getDataTable()
    {
        return (com.kingdee.eas.fdc.basedata.scheme.FdcDataTableInfo)get("dataTable");
    }
    public void setDataTable(com.kingdee.eas.fdc.basedata.scheme.FdcDataTableInfo item)
    {
        put("dataTable", item);
    }
    /**
     * Object:���ز�ʵ�����'s ����property 
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
     * Object:���ز�ʵ�����'s ����property 
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
     * Object:���ز�ʵ�����'s ��������property 
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
     * Object:���ز�ʵ�����'s ����property 
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
     * Object:���ز�ʵ�����'s ����property 
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
     * Object:���ز�ʵ�����'s �Ƿ��û�����property 
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
     * Object:���ز�ʵ�����'s BOS����property 
     */
    public String getBosTypeStr()
    {
        return getString("bosTypeStr");
    }
    public void setBosTypeStr(String item)
    {
        setString("bosTypeStr", item);
    }
    /**
     * Object:���ز�ʵ�����'s ����BOS����property 
     */
    public String getFullBosTypeStr()
    {
        return getString("fullBosTypeStr");
    }
    public void setFullBosTypeStr(String item)
    {
        setString("fullBosTypeStr", item);
    }
    /**
     * Object:���ز�ʵ�����'s ҵ��ʵ��������property 
     */
    public String getBusinessImplName()
    {
        return getString("businessImplName");
    }
    public void setBusinessImplName(String item)
    {
        setString("businessImplName", item);
    }
    /**
     * Object:���ز�ʵ�����'s ҵ�������������property 
     */
    public String getBusinessControllerName()
    {
        return getString("businessControllerName");
    }
    public void setBusinessControllerName(String item)
    {
        setString("businessControllerName", item);
    }
    /**
     * Object:���ز�ʵ�����'s ���ʼ���property 
     */
    public com.kingdee.eas.fdc.basedata.scheme.AccessLevelEnum getAccessLevel()
    {
        return com.kingdee.eas.fdc.basedata.scheme.AccessLevelEnum.getEnum(getString("accessLevel"));
    }
    public void setAccessLevel(com.kingdee.eas.fdc.basedata.scheme.AccessLevelEnum item)
    {
		if (item != null) {
        setString("accessLevel", item.getValue());
		}
    }
    /**
     * Object:���ز�ʵ�����'s ����ģʽproperty 
     */
    public com.kingdee.eas.fdc.basedata.scheme.SubClassingModeEnum getSubClassingMode()
    {
        return com.kingdee.eas.fdc.basedata.scheme.SubClassingModeEnum.getEnum(getString("subClassingMode"));
    }
    public void setSubClassingMode(com.kingdee.eas.fdc.basedata.scheme.SubClassingModeEnum item)
    {
		if (item != null) {
        setString("subClassingMode", item.getValue());
		}
    }
    /**
     * Object:���ز�ʵ�����'s �Ƿ����̳�ģʽproperty 
     */
    public boolean isIsAbstract()
    {
        return getBoolean("isAbstract");
    }
    public void setIsAbstract(boolean item)
    {
        setBoolean("isAbstract", item);
    }
    /**
     * Object:���ز�ʵ�����'s ��ʵ�����property 
     */
    public String getBaseEntity()
    {
        return getString("baseEntity");
    }
    public void setBaseEntity(String item)
    {
        setString("baseEntity", item);
    }
    /**
     * Object:���ز�ʵ�����'s ҵ��Ԫ����property 
     */
    public String getBizUnitPK()
    {
        return getString("bizUnitPK");
    }
    public void setBizUnitPK(String item)
    {
        setString("bizUnitPK", item);
    }
    /**
     * Object:���ز�ʵ�����'s �߼�����property 
     */
    public String getLogicalKey()
    {
        return getString("logicalKey");
    }
    public void setLogicalKey(String item)
    {
        setString("logicalKey", item);
    }
    /**
     * Object:���ز�ʵ�����'s ���ݱ�����(�����ֶ�)property 
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
        return new BOSObjectType("C1246036");
    }
}