package com.kingdee.eas.custom.richbase;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractOrgUnitEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractOrgUnitEntryInfo()
    {
        this("id");
    }
    protected AbstractOrgUnitEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��Ӧ��Ϣ 's null property 
     */
    public com.kingdee.eas.custom.richbase.OrgUnitInfo getParent()
    {
        return (com.kingdee.eas.custom.richbase.OrgUnitInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.custom.richbase.OrgUnitInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ��Ӧ��Ϣ 's EAS��֯���� property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getEASOrgNumber()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("EASOrgNumber");
    }
    public void setEASOrgNumber(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("EASOrgNumber", item);
    }
    /**
     * Object:��Ӧ��Ϣ's EAS��֯����property 
     */
    public String getEASOrgName()
    {
        return getString("EASOrgName");
    }
    public void setEASOrgName(String item)
    {
        setString("EASOrgName", item);
    }
    /**
     * Object:��Ӧ��Ϣ's ���ϵͳ����property 
     */
    public String getYwNumber()
    {
        return getString("ywNumber");
    }
    public void setYwNumber(String item)
    {
        setString("ywNumber", item);
    }
    /**
     * Object:��Ӧ��Ϣ's ��עproperty 
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
        return new BOSObjectType("3AB256C0");
    }
}