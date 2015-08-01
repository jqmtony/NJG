package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAimCostCtrlItemInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractAimCostCtrlItemInfo()
    {
        this("id");
    }
    protected AbstractAimCostCtrlItemInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: null 's null property 
     */
    public com.kingdee.eas.fdc.aimcost.AimCostCtrlBillEntryInfo getEntry()
    {
        return (com.kingdee.eas.fdc.aimcost.AimCostCtrlBillEntryInfo)get("entry");
    }
    public void setEntry(com.kingdee.eas.fdc.aimcost.AimCostCtrlBillEntryInfo item)
    {
        put("entry", item);
    }
    /**
     * Object: null 's ²âËã½×¶Î property 
     */
    public com.kingdee.eas.fdc.basedata.MeasureStageInfo getMeasuStage()
    {
        return (com.kingdee.eas.fdc.basedata.MeasureStageInfo)get("measuStage");
    }
    public void setMeasuStage(com.kingdee.eas.fdc.basedata.MeasureStageInfo item)
    {
        put("measuStage", item);
    }
    /**
     * Object:null's °Ù·Ö±Èproperty 
     */
    public float getValue()
    {
        return getFloat("value");
    }
    public void setValue(float item)
    {
        setFloat("value", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("84F486B9");
    }
}