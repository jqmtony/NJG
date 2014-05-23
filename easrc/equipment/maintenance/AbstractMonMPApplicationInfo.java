package com.kingdee.eas.port.equipment.maintenance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMonMPApplicationInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractMonMPApplicationInfo()
    {
        this("id");
    }
    protected AbstractMonMPApplicationInfo(String pkField)
    {
        super(pkField);
        put("E1", new com.kingdee.eas.port.equipment.maintenance.MonMPApplicationE1Collection());
    }
    /**
     * Object: �¶�ά���ƻ����뵥 's �ƻ��·� property 
     */
    public com.kingdee.eas.port.equipment.base.MonthTimeInfo getPlanMonth()
    {
        return (com.kingdee.eas.port.equipment.base.MonthTimeInfo)get("planMonth");
    }
    public void setPlanMonth(com.kingdee.eas.port.equipment.base.MonthTimeInfo item)
    {
        put("planMonth", item);
    }
    /**
     * Object: �¶�ά���ƻ����뵥 's ���벿�� property 
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
     * Object:�¶�ά���ƻ����뵥's �ƻ��ܷ���property 
     */
    public java.math.BigDecimal getPlanTotalCost()
    {
        return getBigDecimal("planTotalCost");
    }
    public void setPlanTotalCost(java.math.BigDecimal item)
    {
        setBigDecimal("planTotalCost", item);
    }
    /**
     * Object: �¶�ά���ƻ����뵥 's ����� property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getPreparer()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("preparer");
    }
    public void setPreparer(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("preparer", item);
    }
    /**
     * Object: �¶�ά���ƻ����뵥 's �¶�ά���ƻ����� property 
     */
    public com.kingdee.eas.port.equipment.maintenance.MonMPApplicationE1Collection getE1()
    {
        return (com.kingdee.eas.port.equipment.maintenance.MonMPApplicationE1Collection)get("E1");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C374D671");
    }
}