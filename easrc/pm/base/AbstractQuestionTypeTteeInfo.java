package com.kingdee.eas.port.pm.base;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractQuestionTypeTteeInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractQuestionTypeTteeInfo()
    {
        this("id");
    }
    protected AbstractQuestionTypeTteeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 问题类型组别 's 组别 property 
     */
    public com.kingdee.eas.port.pm.base.QuestionTypeTteeTreeInfo getTreeid()
    {
        return (com.kingdee.eas.port.pm.base.QuestionTypeTteeTreeInfo)get("treeid");
    }
    public void setTreeid(com.kingdee.eas.port.pm.base.QuestionTypeTteeTreeInfo item)
    {
        put("treeid", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("56AED9C1");
    }
}