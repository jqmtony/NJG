package com.kingdee.eas.port.pm.invest;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPreProjectInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractPreProjectInfo()
    {
        this("id");
    }
    protected AbstractPreProjectInfo(String pkField)
    {
        super(pkField);
        put("E1", new com.kingdee.eas.port.pm.invest.PreProjectE1Collection());
    }
    /**
     * Object: 项目前期 's 模板名称 property 
     */
    public com.kingdee.eas.port.pm.invest.PreProjectTempInfo getTempName()
    {
        return (com.kingdee.eas.port.pm.invest.PreProjectTempInfo)get("tempName");
    }
    public void setTempName(com.kingdee.eas.port.pm.invest.PreProjectTempInfo item)
    {
        put("tempName", item);
    }
    /**
     * Object: 项目前期 's 项目名称 property 
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
     * Object: 项目前期 's 项目前期 property 
     */
    public com.kingdee.eas.port.pm.invest.PreProjectE1Collection getE1()
    {
        return (com.kingdee.eas.port.pm.invest.PreProjectE1Collection)get("E1");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("22677E93");
    }
}