package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAccountStageInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractAccountStageInfo()
    {
        this("id");
    }
    protected AbstractAccountStageInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 成本科目与测算阶段对应关系 's 测算阶段 property 
     */
    public com.kingdee.eas.fdc.basedata.MeasureStageInfo getMeasureStage()
    {
        return (com.kingdee.eas.fdc.basedata.MeasureStageInfo)get("measureStage");
    }
    public void setMeasureStage(com.kingdee.eas.fdc.basedata.MeasureStageInfo item)
    {
        put("measureStage", item);
    }
    /**
     * Object: 成本科目与测算阶段对应关系 's 成本科目 property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountInfo getCostAccount()
    {
        return (com.kingdee.eas.fdc.basedata.CostAccountInfo)get("costAccount");
    }
    public void setCostAccount(com.kingdee.eas.fdc.basedata.CostAccountInfo item)
    {
        put("costAccount", item);
    }
    /**
     * Object:成本科目与测算阶段对应关系's nullproperty 
     */
    public boolean isValue()
    {
        return getBoolean("value");
    }
    public void setValue(boolean item)
    {
        setBoolean("value", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("476BD8C3");
    }
}