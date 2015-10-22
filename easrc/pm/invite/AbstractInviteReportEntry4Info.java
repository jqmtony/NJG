package com.kingdee.eas.port.pm.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteReportEntry4Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractInviteReportEntry4Info()
    {
        this("id");
    }
    protected AbstractInviteReportEntry4Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �б�Ԥ�������Ϣ 's null property 
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
     * Object:�б�Ԥ�������Ϣ's Ԥ����property 
     */
    public java.math.BigDecimal getBudgetAmount()
    {
        return getBigDecimal("budgetAmount");
    }
    public void setBudgetAmount(java.math.BigDecimal item)
    {
        setBigDecimal("budgetAmount", item);
    }
    /**
     * Object:�б�Ԥ�������Ϣ's ����Ԥ��property 
     */
    public java.math.BigDecimal getBalance()
    {
        return getBigDecimal("balance");
    }
    public void setBalance(java.math.BigDecimal item)
    {
        setBigDecimal("balance", item);
    }
    /**
     * Object:�б�Ԥ�������Ϣ's �б�����property 
     */
    public String getContent()
    {
        return getString("content");
    }
    public void setContent(String item)
    {
        setString("content", item);
    }
    /**
     * Object:�б�Ԥ�������Ϣ's ʣ��Ԥ��property 
     */
    public java.math.BigDecimal getLastAmount()
    {
        return getBigDecimal("lastAmount");
    }
    public void setLastAmount(java.math.BigDecimal item)
    {
        setBigDecimal("lastAmount", item);
    }
    /**
     * Object:�б�Ԥ�������Ϣ's ��עproperty 
     */
    public String getBeizhu()
    {
        return getString("beizhu");
    }
    public void setBeizhu(String item)
    {
        setString("beizhu", item);
    }
    /**
     * Object:�б�Ԥ�������Ϣ's �����б�Ԥ��property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object: �б�Ԥ�������Ϣ 's ���ñ��� property 
     */
    public com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryCostEntryInfo getBudgetNumber()
    {
        return (com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryCostEntryInfo)get("budgetNumber");
    }
    public void setBudgetNumber(com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryCostEntryInfo item)
    {
        put("budgetNumber", item);
    }
    /**
     * Object:�б�Ԥ�������Ϣ's ��������property 
     */
    public String getBudgetName()
    {
        return getString("budgetName");
    }
    public void setBudgetName(String item)
    {
        setString("budgetName", item);
    }
    /**
     * Object: �б�Ԥ�������Ϣ 's ��Ŀ property 
     */
    public com.kingdee.eas.basedata.assistant.ProjectInfo getEntryProject()
    {
        return (com.kingdee.eas.basedata.assistant.ProjectInfo)get("entryProject");
    }
    public void setEntryProject(com.kingdee.eas.basedata.assistant.ProjectInfo item)
    {
        put("entryProject", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("76421708");
    }
}