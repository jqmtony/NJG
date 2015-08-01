package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAimCostCtrlBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractAimCostCtrlBillInfo()
    {
        this("id");
    }
    protected AbstractAimCostCtrlBillInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.aimcost.AimCostCtrlBillEntryCollection());
    }
    /**
     * Object: 目标成本控制单 's null property 
     */
    public com.kingdee.eas.fdc.aimcost.AimCostCtrlBillEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.aimcost.AimCostCtrlBillEntryCollection)get("entrys");
    }
    /**
     * Object:目标成本控制单's 是否最终版本property 
     */
    public boolean isIsLatestVer()
    {
        return getBoolean("isLatestVer");
    }
    public void setIsLatestVer(boolean item)
    {
        setBoolean("isLatestVer", item);
    }
    /**
     * Object:目标成本控制单's 版本号property 
     */
    public java.math.BigDecimal getVerNumber()
    {
        return getBigDecimal("verNumber");
    }
    public void setVerNumber(java.math.BigDecimal item)
    {
        setBigDecimal("verNumber", item);
    }
    /**
     * Object: 目标成本控制单 's 工程项目 property 
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
     * Object:目标成本控制单's 是否是模板property 
     */
    public boolean isIsTemplate()
    {
        return getBoolean("isTemplate");
    }
    public void setIsTemplate(boolean item)
    {
        setBoolean("isTemplate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("84F12FAD");
    }
}