package com.kingdee.eas.port.equipment.special;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAnnualYearPlanInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractAnnualYearPlanInfo()
    {
        this("id");
    }
    protected AbstractAnnualYearPlanInfo(String pkField)
    {
        super(pkField);
        put("Entry", new com.kingdee.eas.port.equipment.special.AnnualYearPlanEntryCollection());
    }
    /**
     * Object: 年度检测计划 's 年度检测明细 property 
     */
    public com.kingdee.eas.port.equipment.special.AnnualYearPlanEntryCollection getEntry()
    {
        return (com.kingdee.eas.port.equipment.special.AnnualYearPlanEntryCollection)get("Entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0AFAAEEF");
    }
}