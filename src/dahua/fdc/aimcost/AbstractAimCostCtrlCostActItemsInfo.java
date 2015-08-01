package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAimCostCtrlCostActItemsInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractAimCostCtrlCostActItemsInfo()
    {
        this("id");
    }
    protected AbstractAimCostCtrlCostActItemsInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: null 's null property 
     */
    public com.kingdee.eas.fdc.aimcost.AimCostCtrlBillEntryInfo getCtrlEntry()
    {
        return (com.kingdee.eas.fdc.aimcost.AimCostCtrlBillEntryInfo)get("ctrlEntry");
    }
    public void setCtrlEntry(com.kingdee.eas.fdc.aimcost.AimCostCtrlBillEntryInfo item)
    {
        put("ctrlEntry", item);
    }
    /**
     * Object: null 's  property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountInfo getCostAccount()
    {
        return (com.kingdee.eas.fdc.basedata.CostAccountInfo)get("costAccount");
    }
    public void setCostAccount(com.kingdee.eas.fdc.basedata.CostAccountInfo item)
    {
        put("costAccount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3B2706E1");
    }
}