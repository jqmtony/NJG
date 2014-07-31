package com.kingdee.eas.port.pm.invest.investplan;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractProgrammingEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractContractProgrammingEntryInfo()
    {
        this("id");
    }
    protected AbstractContractProgrammingEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��Լ�滮��¼'s �滮���property 
     */
    public java.math.BigDecimal getProgrammingMoney()
    {
        return getBigDecimal("programmingMoney");
    }
    public void setProgrammingMoney(java.math.BigDecimal item)
    {
        setBigDecimal("programmingMoney", item);
    }
    /**
     * Object: ��Լ�滮��¼ 's ��ͷ property 
     */
    public com.kingdee.eas.port.pm.invest.investplan.ContractProgrammingInfo getParent()
    {
        return (com.kingdee.eas.port.pm.invest.investplan.ContractProgrammingInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.invest.investplan.ContractProgrammingInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ��Լ�滮��¼ 's �ɱ���Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountInfo getCostAccount()
    {
        return (com.kingdee.eas.fdc.basedata.CostAccountInfo)get("costAccount");
    }
    public void setCostAccount(com.kingdee.eas.fdc.basedata.CostAccountInfo item)
    {
        put("costAccount", item);
    }
    /**
     * Object:��Լ�滮��¼'s Ŀ��ɱ�property 
     */
    public java.math.BigDecimal getAimCostMoney()
    {
        return getBigDecimal("aimCostMoney");
    }
    public void setAimCostMoney(java.math.BigDecimal item)
    {
        setBigDecimal("aimCostMoney", item);
    }
    /**
     * Object:��Լ�滮��¼'s �ɱ���Ŀ����property 
     */
    public String getCostAccountName()
    {
        return getString("costAccountName");
    }
    public void setCostAccountName(String item)
    {
        setString("costAccountName", item);
    }
    /**
     * Object:��Լ�滮��¼'s �������������property 
     */
    public String getPrjLongNumber()
    {
        return getString("prjLongNumber");
    }
    public void setPrjLongNumber(String item)
    {
        setString("prjLongNumber", item);
    }
    /**
     * Object:��Լ�滮��¼'s ��������������property 
     */
    public String getPrjDisplayName()
    {
        return getString("prjDisplayName");
    }
    public void setPrjDisplayName(String item)
    {
        setString("prjDisplayName", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E3DF8B58");
    }
}