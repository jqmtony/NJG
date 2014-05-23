package com.kingdee.eas.port.equipment.special;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAnnualYearFeeInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractAnnualYearFeeInfo()
    {
        this("id");
    }
    protected AbstractAnnualYearFeeInfo(String pkField)
    {
        super(pkField);
        put("Entry", new com.kingdee.eas.port.equipment.special.AnnualYearFeeEntryCollection());
    }
    /**
     * Object: 设备检测费用表 's 年度检测明细 property 
     */
    public com.kingdee.eas.port.equipment.special.AnnualYearFeeEntryCollection getEntry()
    {
        return (com.kingdee.eas.port.equipment.special.AnnualYearFeeEntryCollection)get("Entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("29A4D700");
    }
}