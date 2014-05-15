package com.kingdee.eas.port.equipment.operate;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEqmScrapInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractEqmScrapInfo()
    {
        this("id");
    }
    protected AbstractEqmScrapInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �豸���ϵ� 's �豸���� property 
     */
    public com.kingdee.eas.port.equipment.record.EquIdInfo getEqmNumber()
    {
        return (com.kingdee.eas.port.equipment.record.EquIdInfo)get("eqmNumber");
    }
    public void setEqmNumber(com.kingdee.eas.port.equipment.record.EquIdInfo item)
    {
        put("eqmNumber", item);
    }
    /**
     * Object:�豸���ϵ�'s �豸����property 
     */
    public String getEqmName()
    {
        return getString("eqmName");
    }
    public void setEqmName(String item)
    {
        setString("eqmName", item);
    }
    /**
     * Object: �豸���ϵ� 's ������λ property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getSsOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("ssOrgUnit");
    }
    public void setSsOrgUnit(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("ssOrgUnit", item);
    }
    /**
     * Object: �豸���ϵ� 's ʹ�ò��� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getUsedDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("usedDept");
    }
    public void setUsedDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("usedDept", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("08D6068F");
    }
}