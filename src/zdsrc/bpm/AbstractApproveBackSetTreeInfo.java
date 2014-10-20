package com.kingdee.eas.bpm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractApproveBackSetTreeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractApproveBackSetTreeInfo()
    {
        this("id");
    }
    protected AbstractApproveBackSetTreeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 审批反写字段设置组别 's 父节点 property 
     */
    public com.kingdee.eas.bpm.ApproveBackSetTreeInfo getParent()
    {
        return (com.kingdee.eas.bpm.ApproveBackSetTreeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.bpm.ApproveBackSetTreeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D6D723AD");
    }
}