package com.kingdee.eas.port.pm.acceptance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCompleteAcceptInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractCompleteAcceptInfo()
    {
        this("id");
    }
    protected AbstractCompleteAcceptInfo(String pkField)
    {
        super(pkField);
        put("E1", new com.kingdee.eas.port.pm.acceptance.CompleteAcceptE1Collection());
    }
    /**
     * Object: 竣工验收单 's 项目名称 property 
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
     * Object:竣工验收单's 工程类型property 
     */
    public com.kingdee.eas.basedata.assistant.ProjectTypeEnum getProjectType()
    {
        return com.kingdee.eas.basedata.assistant.ProjectTypeEnum.getEnum(getInt("projectType"));
    }
    public void setProjectType(com.kingdee.eas.basedata.assistant.ProjectTypeEnum item)
    {
		if (item != null) {
        setInt("projectType", item.getValue());
		}
    }
    /**
     * Object: 竣工验收单 's 工程竣工验收证明单 property 
     */
    public com.kingdee.eas.port.pm.acceptance.CompleteAcceptE1Collection getE1()
    {
        return (com.kingdee.eas.port.pm.acceptance.CompleteAcceptE1Collection)get("E1");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B0E3139C");
    }
}