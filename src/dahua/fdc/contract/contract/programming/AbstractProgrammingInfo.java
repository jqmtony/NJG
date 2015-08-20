package com.kingdee.eas.fdc.contract.programming;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProgrammingInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractProgrammingInfo()
    {
        this("id");
    }
    protected AbstractProgrammingInfo(String pkField)
    {
        super(pkField);
        put("entries", new com.kingdee.eas.fdc.contract.programming.ProgrammingContractCollection());
    }
    /**
     * Object:��Լ�滮's �汾property 
     */
    public java.math.BigDecimal getVersion()
    {
        return getBigDecimal("version");
    }
    public void setVersion(java.math.BigDecimal item)
    {
        setBigDecimal("version", item);
    }
    /**
     * Object:��Լ�滮's �Ƿ����°汾property 
     */
    public boolean isIsLatest()
    {
        return getBoolean("isLatest");
    }
    public void setIsLatest(boolean item)
    {
        setBoolean("isLatest", item);
    }
    /**
     * Object: ��Լ�滮 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object: ��Լ�滮 's Ŀ��ɱ� property 
     */
    public com.kingdee.eas.fdc.aimcost.AimCostInfo getAimCost()
    {
        return (com.kingdee.eas.fdc.aimcost.AimCostInfo)get("aimCost");
    }
    public void setAimCost(com.kingdee.eas.fdc.aimcost.AimCostInfo item)
    {
        put("aimCost", item);
    }
    /**
     * Object: ��Լ�滮 's ��¼ property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingContractCollection getEntries()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingContractCollection)get("entries");
    }
    /**
     * Object:��Լ�滮's �汾��property 
     */
    public String getVersionGroup()
    {
        return getString("versionGroup");
    }
    public void setVersionGroup(String item)
    {
        setString("versionGroup", item);
    }
    /**
     * Object:��Լ�滮's �ܽ������property 
     */
    public java.math.BigDecimal getBuildArea()
    {
        return getBigDecimal("buildArea");
    }
    public void setBuildArea(java.math.BigDecimal item)
    {
        setBigDecimal("buildArea", item);
    }
    /**
     * Object:��Լ�滮's �������property 
     */
    public java.math.BigDecimal getSoldArea()
    {
        return getBigDecimal("soldArea");
    }
    public void setSoldArea(java.math.BigDecimal item)
    {
        setBigDecimal("soldArea", item);
    }
    /**
     * Object:��Լ�滮's Ԥ������property 
     */
    public int getYjDays()
    {
        return getInt("yjDays");
    }
    public void setYjDays(int item)
    {
        setInt("yjDays", item);
    }
    /**
     * Object: ��Լ�滮 's Ԥ����Ա property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getYjPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("yjPerson");
    }
    public void setYjPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("yjPerson", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("53B0BDA9");
    }
}