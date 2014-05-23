package com.kingdee.eas.port.pm.invest;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectBudget2Info extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractProjectBudget2Info()
    {
        this("id");
    }
    protected AbstractProjectBudget2Info(String pkField)
    {
        super(pkField);
        put("E1", new com.kingdee.eas.port.pm.invest.ProjectBudget2E1Collection());
    }
    /**
     * Object: 项目概算 's 项目概算 property 
     */
    public com.kingdee.eas.port.pm.invest.ProjectBudget2E1Collection getE1()
    {
        return (com.kingdee.eas.port.pm.invest.ProjectBudget2E1Collection)get("E1");
    }
    /**
     * Object: 项目概算 's 项目名称 property 
     */
    public com.kingdee.eas.basedata.assistant.ProjectInfo getProjectName()
    {
        return (com.kingdee.eas.basedata.assistant.ProjectInfo)get("projectName");
    }
    public void setProjectName(com.kingdee.eas.basedata.assistant.ProjectInfo item)
    {
        put("projectName", item);
    }
    /**
     * Object:项目概算's 概算金额property 
     */
    public java.math.BigDecimal getEstimateCost()
    {
        return getBigDecimal("estimateCost");
    }
    public void setEstimateCost(java.math.BigDecimal item)
    {
        setBigDecimal("estimateCost", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("91226D51");
    }
}