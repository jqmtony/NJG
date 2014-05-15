package com.kingdee.eas.port.pm.invite;

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
        put("EntryTotal", new com.kingdee.eas.port.pm.invite.EvaluationEntryTotalCollection());
        put("EntryValid", new com.kingdee.eas.port.pm.invite.EvaluationEntryValidCollection());
        put("EntryScore", new com.kingdee.eas.port.pm.invite.EvaluationEntryScoreCollection());
        put("EntryUnit", new com.kingdee.eas.port.pm.invite.EvaluationEntryUnitCollection());
    }
    /**
     * Object: 专家评标 's 招标方案 property 
     */
    public com.kingdee.eas.port.pm.invite.InviteReportInfo getInviteReport()
    {
        return (com.kingdee.eas.port.pm.invite.InviteReportInfo)get("inviteReport");
    }
    public void setInviteReport(com.kingdee.eas.port.pm.invite.InviteReportInfo item)
    {
        put("inviteReport", item);
    }
    /**
     * Object:专家评标's 项目名称property 
     */
    public String getPrjName()
    {
        return getString("prjName");
    }
    public void setPrjName(String item)
    {
        setString("prjName", item);
    }
    /**
     * Object:专家评标's 评标办法property 
     */
    public com.kingdee.eas.port.pm.invite.judgeSolution getEvaSolution()
    {
        return com.kingdee.eas.port.pm.invite.judgeSolution.getEnum(getString("evaSolution"));
    }
    public void setEvaSolution(com.kingdee.eas.port.pm.invite.judgeSolution item)
    {
		if (item != null) {
        setString("evaSolution", item.getValue());
		}
    }
    /**
     * Object:专家评标's 评标日期property 
     */
    public java.util.Date getEvaDate()
    {
        return getDate("evaDate");
    }
    public void setEvaDate(java.util.Date item)
    {
        setDate("evaDate", item);
    }
    /**
     * Object: 专家评标 's 符合性审查分录 property 
     */
    public com.kingdee.eas.port.pm.invite.EvaluationEntryValidCollection getEntryValid()
    {
        return (com.kingdee.eas.port.pm.invite.EvaluationEntryValidCollection)get("EntryValid");
    }
    /**
     * Object: 专家评标 's 投标单位分录 property 
     */
    public com.kingdee.eas.port.pm.invite.EvaluationEntryUnitCollection getEntryUnit()
    {
        return (com.kingdee.eas.port.pm.invite.EvaluationEntryUnitCollection)get("EntryUnit");
    }
    /**
     * Object: 专家评标 's 评分分录 property 
     */
    public com.kingdee.eas.port.pm.invite.EvaluationEntryScoreCollection getEntryScore()
    {
        return (com.kingdee.eas.port.pm.invite.EvaluationEntryScoreCollection)get("EntryScore");
    }
    /**
     * Object: 专家评标 's 总分保存分录 property 
     */
    public com.kingdee.eas.port.pm.invite.EvaluationEntryTotalCollection getEntryTotal()
    {
        return (com.kingdee.eas.port.pm.invite.EvaluationEntryTotalCollection)get("EntryTotal");
    }
    /**
     * Object:专家评标's 评标基准价property 
     */
    public java.math.BigDecimal getBasePrice()
    {
        return getBigDecimal("basePrice");
    }
    public void setBasePrice(java.math.BigDecimal item)
    {
        setBigDecimal("basePrice", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4DBE6945");
    }
}