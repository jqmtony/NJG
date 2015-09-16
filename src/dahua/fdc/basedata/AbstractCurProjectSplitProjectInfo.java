package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCurProjectSplitProjectInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractCurProjectSplitProjectInfo()
    {
        this("id");
    }
    protected AbstractCurProjectSplitProjectInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分期信息 's null property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getParent1()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("parent1");
    }
    public void setParent1(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("parent1", item);
    }
    /**
     * Object: 分期信息 's 分期项目 property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getSplitProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("splitProject");
    }
    public void setSplitProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("splitProject", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7FD7CB6A");
    }
}