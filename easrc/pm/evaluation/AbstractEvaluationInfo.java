package com.kingdee.eas.port.pm.evaluation;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEvaluationInfo extends com.kingdee.eas.xr.XRBillBaseInfo implements Serializable 
{
    public AbstractEvaluationInfo()
    {
        this("id");
    }
    protected AbstractEvaluationInfo(String pkField)
    {
        super(pkField);
        put("E1", new com.kingdee.eas.port.pm.evaluation.EvaluationE1Collection());
    }
    /**
     * Object: 项目后评估 's 项目成功度评价表 property 
     */
    public com.kingdee.eas.port.pm.evaluation.EvaluationE1Collection getE1()
    {
        return (com.kingdee.eas.port.pm.evaluation.EvaluationE1Collection)get("E1");
    }
    /**
     * Object: 项目后评估 's 工程名称 property 
     */
    public com.kingdee.eas.basedata.assistant.ProjectInfo getProjectName()
    {
        return (com.kingdee.eas.basedata.assistant.ProjectInfo)get("projectName");
    }
    public void setProjectName(com.kingdee.eas.basedata.assistant.ProjectInfo item)
    {
        put("projectName", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E228B132");
    }
}