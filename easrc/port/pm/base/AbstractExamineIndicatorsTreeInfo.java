package com.kingdee.eas.port.pm.base;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractExamineIndicatorsTreeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractExamineIndicatorsTreeInfo()
    {
        this("id");
    }
    protected AbstractExamineIndicatorsTreeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 考核指标组别 's 父节点 property 
     */
    public com.kingdee.eas.port.pm.base.ExamineIndicatorsTreeInfo getParent()
    {
        return (com.kingdee.eas.port.pm.base.ExamineIndicatorsTreeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.base.ExamineIndicatorsTreeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A3A58222");
    }
}