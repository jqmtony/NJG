package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInnerManagerInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractInnerManagerInfo()
    {
        this("id");
    }
    protected AbstractInnerManagerInfo(String pkField)
    {
        super(pkField);
        put("managerProjectEntry", new com.kingdee.eas.fdc.basedata.ManagerProjectEntryCollection());
    }
    /**
     * Object: �ڲ�������Ϣ 's �����ز���˾ property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getOwnCompany()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("ownCompany");
    }
    public void setOwnCompany(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("ownCompany", item);
    }
    /**
     * Object:�ڲ�������Ϣ's ҵ������property 
     */
    public com.kingdee.eas.fdc.basedata.BusinessTypeEnum getBusinessType()
    {
        return com.kingdee.eas.fdc.basedata.BusinessTypeEnum.getEnum(getString("businessType"));
    }
    public void setBusinessType(com.kingdee.eas.fdc.basedata.BusinessTypeEnum item)
    {
		if (item != null) {
        setString("businessType", item.getValue());
		}
    }
    /**
     * Object:�ڲ�������Ϣ's ��������property 
     */
    public com.kingdee.eas.fdc.basedata.ControlTypeEnum getControlType()
    {
        return com.kingdee.eas.fdc.basedata.ControlTypeEnum.getEnum(getString("controlType"));
    }
    public void setControlType(com.kingdee.eas.fdc.basedata.ControlTypeEnum item)
    {
		if (item != null) {
        setString("controlType", item.getValue());
		}
    }
    /**
     * Object:�ڲ�������Ϣ's ������ʽproperty 
     */
    public com.kingdee.eas.fdc.basedata.GetTypeEnum getGetType()
    {
        return com.kingdee.eas.fdc.basedata.GetTypeEnum.getEnum(getString("getType"));
    }
    public void setGetType(com.kingdee.eas.fdc.basedata.GetTypeEnum item)
    {
		if (item != null) {
        setString("getType", item.getValue());
		}
    }
    /**
     * Object:�ڲ�������Ϣ's �Ƿ���֯�Ŷ�property 
     */
    public boolean isHasUnit()
    {
        return getBoolean("hasUnit");
    }
    public void setHasUnit(boolean item)
    {
        setBoolean("hasUnit", item);
    }
    /**
     * Object:�ڲ�������Ϣ's �Ƿ��ڽ���Ŀproperty 
     */
    public boolean isHasBuildingProject()
    {
        return getBoolean("hasBuildingProject");
    }
    public void setHasBuildingProject(boolean item)
    {
        setBoolean("hasBuildingProject", item);
    }
    /**
     * Object:�ڲ�������Ϣ's ����ʱ��property 
     */
    public java.sql.Timestamp getSetupDate()
    {
        return getTimestamp("setupDate");
    }
    public void setSetupDate(java.sql.Timestamp item)
    {
        setTimestamp("setupDate", item);
    }
    /**
     * Object: �ڲ�������Ϣ 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.ManagerProjectEntryCollection getManagerProjectEntry()
    {
        return (com.kingdee.eas.fdc.basedata.ManagerProjectEntryCollection)get("managerProjectEntry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("76FEE869");
    }
}