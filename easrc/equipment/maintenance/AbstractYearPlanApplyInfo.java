package com.kingdee.eas.port.equipment.maintenance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractYearPlanApplyInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractYearPlanApplyInfo()
    {
        this("id");
    }
    protected AbstractYearPlanApplyInfo(String pkField)
    {
        super(pkField);
        put("E1", new com.kingdee.eas.port.equipment.maintenance.YearPlanApplyE1Collection());
    }
    /**
     * Object: ���ά���ƻ����뵥 's ��1������ property 
     */
    public com.kingdee.eas.port.equipment.maintenance.YearPlanApplyE1Collection getE1()
    {
        return (com.kingdee.eas.port.equipment.maintenance.YearPlanApplyE1Collection)get("E1");
    }
    /**
     * Object:���ά���ƻ����뵥's �ƻ����property 
     */
    public String getPlanYear()
    {
        return getString("planYear");
    }
    public void setPlanYear(String item)
    {
        setString("planYear", item);
    }
    /**
     * Object: ���ά���ƻ����뵥 's ���벿�� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getApplyDepart()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("applyDepart");
    }
    public void setApplyDepart(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("applyDepart", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("22C36DB8");
    }
}