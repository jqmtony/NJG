package com.kingdee.eas.port.pm.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteReportE7Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractInviteReportE7Info()
    {
        this("id");
    }
    protected AbstractInviteReportE7Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 符合性审查 's null property 
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
     * Object:符合性审查's 评审指标名称property 
     */
    public String getEvaluationName()
    {
        return getString("EvaluationName");
    }
    public void setEvaluationName(String item)
    {
        setString("EvaluationName", item);
    }
    /**
     * Object:符合性审查's 评审指标类型property 
     */
    public String getEvaluationType()
    {
        return getString("EvaluationType");
    }
    public void setEvaluationType(String item)
    {
        setString("EvaluationType", item);
    }
    /**
     * Object:符合性审查's 权重property 
     */
    public java.math.BigDecimal getWeight()
    {
        return getBigDecimal("weight");
    }
    public void setWeight(java.math.BigDecimal item)
    {
        setBigDecimal("weight", item);
    }
    /**
     * Object:符合性审查's 备注property 
     */
    public String getRemake()
    {
        return getString("remake");
    }
    public void setRemake(String item)
    {
        setString("remake", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BFB19078");
    }
}