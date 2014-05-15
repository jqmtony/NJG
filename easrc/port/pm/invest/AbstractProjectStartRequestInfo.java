package com.kingdee.eas.port.pm.invest;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectStartRequestInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractProjectStartRequestInfo()
    {
        this("id");
    }
    protected AbstractProjectStartRequestInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 项目启动申请 's 项目名称 property 
     */
    public com.kingdee.eas.port.pm.project.PortProjectInfo getProjectName()
    {
        return (com.kingdee.eas.port.pm.project.PortProjectInfo)get("projectName");
    }
    public void setProjectName(com.kingdee.eas.port.pm.project.PortProjectInfo item)
    {
        put("projectName", item);
    }
    /**
     * Object:项目启动申请's 实施方案property 
     */
    public String getScheme()
    {
        return getString("scheme");
    }
    public void setScheme(String item)
    {
        setString("scheme", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3BA1FA89");
    }
}