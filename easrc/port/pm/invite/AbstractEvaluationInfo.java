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
     * Object: ר������ 's �б귽�� property 
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
     * Object:ר������'s ��Ŀ����property 
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
     * Object:ר������'s ����취property 
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
     * Object:ר������'s ��������property 
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
     * Object: ר������ 's ����������¼ property 
     */
    public com.kingdee.eas.port.pm.invite.EvaluationEntryValidCollection getEntryValid()
    {
        return (com.kingdee.eas.port.pm.invite.EvaluationEntryValidCollection)get("EntryValid");
    }
    /**
     * Object: ר������ 's Ͷ�굥λ��¼ property 
     */
    public com.kingdee.eas.port.pm.invite.EvaluationEntryUnitCollection getEntryUnit()
    {
        return (com.kingdee.eas.port.pm.invite.EvaluationEntryUnitCollection)get("EntryUnit");
    }
    /**
     * Object: ר������ 's ���ַ�¼ property 
     */
    public com.kingdee.eas.port.pm.invite.EvaluationEntryScoreCollection getEntryScore()
    {
        return (com.kingdee.eas.port.pm.invite.EvaluationEntryScoreCollection)get("EntryScore");
    }
    /**
     * Object: ר������ 's �ֱܷ����¼ property 
     */
    public com.kingdee.eas.port.pm.invite.EvaluationEntryTotalCollection getEntryTotal()
    {
        return (com.kingdee.eas.port.pm.invite.EvaluationEntryTotalCollection)get("EntryTotal");
    }
    /**
     * Object:ר������'s �����׼��property 
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