package com.kingdee.eas.port.equipment.maintenance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMonMainPlanE1Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMonMainPlanE1Info()
    {
        this("id");
    }
    protected AbstractMonMainPlanE1Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �¶�ά���ƻ��� 's null property 
     */
    public com.kingdee.eas.port.equipment.maintenance.MonMainPlanInfo getParent()
    {
        return (com.kingdee.eas.port.equipment.maintenance.MonMainPlanInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.equipment.maintenance.MonMainPlanInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: �¶�ά���ƻ��� 's �豸��� property 
     */
    public com.kingdee.eas.port.equipment.record.EquIdInfo getEquNumber()
    {
        return (com.kingdee.eas.port.equipment.record.EquIdInfo)get("equNumber");
    }
    public void setEquNumber(com.kingdee.eas.port.equipment.record.EquIdInfo item)
    {
        put("equNumber", item);
    }
    /**
     * Object:�¶�ά���ƻ���'s �豸����property 
     */
    public String getEquName()
    {
        return getString("equName");
    }
    public void setEquName(String item)
    {
        setString("equName", item);
    }
    /**
     * Object: �¶�ά���ƻ��� 's ���벿�� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getAppDepart()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("appDepart");
    }
    public void setAppDepart(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("appDepart", item);
    }
    /**
     * Object:�¶�ά���ƻ���'s ά������property 
     */
    public String getMainContent()
    {
        return getString("mainContent");
    }
    public void setMainContent(String item)
    {
        setString("mainContent", item);
    }
    /**
     * Object:�¶�ά���ƻ���'s �ƻ�����ʱ��property 
     */
    public java.util.Date getPlanStartTime()
    {
        return getDate("planStartTime");
    }
    public void setPlanStartTime(java.util.Date item)
    {
        setDate("planStartTime", item);
    }
    /**
     * Object:�¶�ά���ƻ���'s �ƻ��깤ʱ��property 
     */
    public java.util.Date getPlanCompleteT()
    {
        return getDate("planCompleteT");
    }
    public void setPlanCompleteT(java.util.Date item)
    {
        setDate("planCompleteT", item);
    }
    /**
     * Object:�¶�ά���ƻ���'s �ƻ�����property 
     */
    public java.math.BigDecimal getPlanCost()
    {
        return getBigDecimal("planCost");
    }
    public void setPlanCost(java.math.BigDecimal item)
    {
        setBigDecimal("planCost", item);
    }
    /**
     * Object: �¶�ά���ƻ��� 's ʵʩ��λ property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getImplementDepart()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("implementDepart");
    }
    public void setImplementDepart(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("implementDepart", item);
    }
    /**
     * Object:�¶�ά���ƻ���'s ʵ�����ʱ��property 
     */
    public java.util.Date getActualCompleteT()
    {
        return getDate("actualCompleteT");
    }
    public void setActualCompleteT(java.util.Date item)
    {
        setDate("actualCompleteT", item);
    }
    /**
     * Object:�¶�ά���ƻ���'s ������property 
     */
    public String getComplete()
    {
        return getString("complete");
    }
    public void setComplete(String item)
    {
        setString("complete", item);
    }
    /**
     * Object:�¶�ά���ƻ���'s ��עproperty 
     */
    public String getNote()
    {
        return getString("note");
    }
    public void setNote(String item)
    {
        setString("note", item);
    }
    /**
     * Object:�¶�ά���ƻ���'s ʵʩ��λproperty 
     */
    public String getImplementUnit()
    {
        return getString("implementUnit");
    }
    public void setImplementUnit(String item)
    {
        setString("implementUnit", item);
    }
    /**
     * Object: �¶�ά���ƻ��� 's ʹ�ò��� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getUseDepart()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("useDepart");
    }
    public void setUseDepart(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("useDepart", item);
    }
    /**
     * Object: �¶�ά���ƻ��� 's ά������ property 
     */
    public com.kingdee.eas.port.equipment.base.MaintenanceTypeInfo getWeixiuType()
    {
        return (com.kingdee.eas.port.equipment.base.MaintenanceTypeInfo)get("weixiuType");
    }
    public void setWeixiuType(com.kingdee.eas.port.equipment.base.MaintenanceTypeInfo item)
    {
        put("weixiuType", item);
    }
    /**
     * Object:�¶�ά���ƻ���'s �ƻ���������property 
     */
    public java.math.BigDecimal getPlanWeixiuDay()
    {
        return getBigDecimal("planWeixiuDay");
    }
    public void setPlanWeixiuDay(java.math.BigDecimal item)
    {
        setBigDecimal("planWeixiuDay", item);
    }
    /**
     * Object:�¶�ά���ƻ���'s ��Դ���ݷ�¼IDproperty 
     */
    public String getShangyuandanjuID()
    {
        return getString("shangyuandanjuID");
    }
    public void setShangyuandanjuID(String item)
    {
        setString("shangyuandanjuID", item);
    }
    /**
     * Object:�¶�ά���ƻ���'s ���ڱ��property 
     */
    public String getCnNumber()
    {
        return getString("cnNumber");
    }
    public void setCnNumber(String item)
    {
        setString("cnNumber", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A24DDC6A");
    }
}