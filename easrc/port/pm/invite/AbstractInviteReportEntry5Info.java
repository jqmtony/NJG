package com.kingdee.eas.port.pm.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteReportEntry5Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractInviteReportEntry5Info()
    {
        this("id");
    }
    protected AbstractInviteReportEntry5Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 专家构成信息 's null property 
     */
    public com.kingdee.eas.port.pm.invite.InviteReportInfo getParent()
    {
        return (com.kingdee.eas.port.pm.invite.InviteReportInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.invite.InviteReportInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:专家构成信息's 人数property 
     */
    public String getAmount()
    {
        return getString("amount");
    }
    public void setAmount(String item)
    {
        setString("amount", item);
    }
    /**
     * Object: 专家构成信息 's 专家类别 property 
     */
    public com.kingdee.eas.port.pm.base.JudgesTreeInfo getJudgeType()
    {
        return (com.kingdee.eas.port.pm.base.JudgesTreeInfo)get("judgeType");
    }
    public void setJudgeType(com.kingdee.eas.port.pm.base.JudgesTreeInfo item)
    {
        put("judgeType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("76421709");
    }
}