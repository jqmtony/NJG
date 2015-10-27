package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProductTypeInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractProductTypeInfo()
    {
        this("id");
    }
    protected AbstractProductTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:产品类型's 指标类型property 
     */
    public com.kingdee.eas.fdc.aimcost.PlanIndexTypeEnum getPlanIndexType()
    {
        return com.kingdee.eas.fdc.aimcost.PlanIndexTypeEnum.getEnum(getString("PlanIndexType"));
    }
    public void setPlanIndexType(com.kingdee.eas.fdc.aimcost.PlanIndexTypeEnum item)
    {
		if (item != null) {
        setString("PlanIndexType", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E1203E97");
    }
}