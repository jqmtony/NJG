package com.kingdee.eas.port.pm.base;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupEvaluationIndicatorTreeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractSupEvaluationIndicatorTreeInfo()
    {
        this("id");
    }
    protected AbstractSupEvaluationIndicatorTreeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��Ӧ������ָ����� 's ���ڵ� property 
     */
    public com.kingdee.eas.port.pm.base.SupEvaluationIndicatorTreeInfo getParent()
    {
        return (com.kingdee.eas.port.pm.base.SupEvaluationIndicatorTreeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.base.SupEvaluationIndicatorTreeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("769DC344");
    }
}