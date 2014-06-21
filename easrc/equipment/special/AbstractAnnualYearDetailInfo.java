package com.kingdee.eas.port.equipment.special;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAnnualYearDetailInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractAnnualYearDetailInfo()
    {
        this("id");
    }
    protected AbstractAnnualYearDetailInfo(String pkField)
    {
        super(pkField);
        put("Entry", new com.kingdee.eas.port.equipment.special.AnnualYearDetailEntryCollection());
    }
    /**
     * Object: 设备检测明细表 's 年度检测明细 property 
     */
    public com.kingdee.eas.port.equipment.special.AnnualYearDetailEntryCollection getEntry()
    {
        return (com.kingdee.eas.port.equipment.special.AnnualYearDetailEntryCollection)get("Entry");
    }
    /**
     * Object: 设备检测明细表 's 使用单位 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getUseDpatmen()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("useDpatmen");
    }
    public void setUseDpatmen(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("useDpatmen", item);
    }
    /**
     * Object:设备检测明细表's 是否确认property 
     */
    public boolean isIsConfirmation()
    {
        return getBoolean("isConfirmation");
    }
    public void setIsConfirmation(boolean item)
    {
        setBoolean("isConfirmation", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("22366297");
    }
}