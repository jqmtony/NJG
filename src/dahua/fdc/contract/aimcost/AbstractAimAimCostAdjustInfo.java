package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAimAimCostAdjustInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractAimAimCostAdjustInfo()
    {
        this("id");
    }
    protected AbstractAimAimCostAdjustInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.aimcost.AimAimCostAdjustEntryCollection());
    }
    /**
     * Object: 目标成本调整单 's 分录 property 
     */
    public com.kingdee.eas.fdc.aimcost.AimAimCostAdjustEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.aimcost.AimAimCostAdjustEntryCollection)get("entrys");
    }
    /**
     * Object: 目标成本调整单 's 工程项目 property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("04C7B2F3");
    }
}