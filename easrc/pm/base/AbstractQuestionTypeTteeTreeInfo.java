package com.kingdee.eas.port.pm.base;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractQuestionTypeTteeTreeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractQuestionTypeTteeTreeInfo()
    {
        this("id");
    }
    protected AbstractQuestionTypeTteeTreeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 问题类型组别组别 's 父节点 property 
     */
    public com.kingdee.eas.port.pm.base.QuestionTypeTteeTreeInfo getParent()
    {
        return (com.kingdee.eas.port.pm.base.QuestionTypeTteeTreeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.base.QuestionTypeTteeTreeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4C61F8FF");
    }
}