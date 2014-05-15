package com.kingdee.eas.port.pm.invest;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCostTempE1Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractCostTempE1Info()
    {
        this("id");
    }
    protected AbstractCostTempE1Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 费用模板 's null property 
     */
    public com.kingdee.eas.port.pm.invest.CostTempInfo getParent()
    {
        return (com.kingdee.eas.port.pm.invest.CostTempInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.invest.CostTempInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 费用模板 's 费用类型 property 
     */
    public com.kingdee.eas.port.pm.base.CostTypeTreeInfo getCostType()
    {
        return (com.kingdee.eas.port.pm.base.CostTypeTreeInfo)get("costType");
    }
    public void setCostType(com.kingdee.eas.port.pm.base.CostTypeTreeInfo item)
    {
        put("costType", item);
    }
    /**
     * Object: 费用模板 's 费用名称 property 
     */
    public com.kingdee.eas.port.pm.base.CostTypeInfo getCostNames()
    {
        return (com.kingdee.eas.port.pm.base.CostTypeInfo)get("costNames");
    }
    public void setCostNames(com.kingdee.eas.port.pm.base.CostTypeInfo item)
    {
        put("costNames", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E8D3712A");
    }
}