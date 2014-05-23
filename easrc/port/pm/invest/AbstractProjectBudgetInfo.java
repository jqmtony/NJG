package com.kingdee.eas.port.pm.invest;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectBudgetInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractProjectBudgetInfo()
    {
        this("id");
    }
    protected AbstractProjectBudgetInfo(String pkField)
    {
        super(pkField);
        put("E1", new com.kingdee.eas.port.pm.invest.ProjectBudgetE1Collection());
    }
    /**
     * Object: 项目预算 's 项目预算 property 
     */
    public com.kingdee.eas.port.pm.invest.ProjectBudgetE1Collection getE1()
    {
        return (com.kingdee.eas.port.pm.invest.ProjectBudgetE1Collection)get("E1");
    }
    /**
     * Object: 项目预算 's 项目名称 property 
     */
    public com.kingdee.eas.port.pm.project.PortProjectInfo getProjectName()
    {
        return (com.kingdee.eas.port.pm.project.PortProjectInfo)get("projectName");
    }
    public void setProjectName(com.kingdee.eas.port.pm.project.PortProjectInfo item)
    {
        put("projectName", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BA5BF301");
    }
}