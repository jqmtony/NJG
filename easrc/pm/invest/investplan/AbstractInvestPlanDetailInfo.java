package com.kingdee.eas.port.pm.invest.investplan;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInvestPlanDetailInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractInvestPlanDetailInfo()
    {
        this("id");
    }
    protected AbstractInvestPlanDetailInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.port.pm.invest.investplan.InvestPlanDetailEntryCollection());
    }
    /**
     * Object: 投资规划明细 's 分录 property 
     */
    public com.kingdee.eas.port.pm.invest.investplan.InvestPlanDetailEntryCollection getEntrys()
    {
        return (com.kingdee.eas.port.pm.invest.investplan.InvestPlanDetailEntryCollection)get("entrys");
    }
    /**
     * Object:投资规划明细's 是否生成凭证property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    /**
     * Object:投资规划明细's 上级框架投资规划property 
     */
    public String getParentLongName()
    {
        return getString("parentLongName");
    }
    public void setParentLongName(String item)
    {
        setString("parentLongName", item);
    }
    /**
     * Object:投资规划明细's 框架投资规划名称property 
     */
    public String getInvestPlanName()
    {
        return getString("investPlanName");
    }
    public void setInvestPlanName(String item)
    {
        setString("investPlanName", item);
    }
    /**
     * Object:投资规划明细's 规划金额property 
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
     * Object:投资规划明细's 工作内容property 
     */
    public String getWorkContent()
    {
        return getString("workContent");
    }
    public void setWorkContent(String item)
    {
        setString("workContent", item);
    }
    /**
     * Object:投资规划明细's 备注property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9613BABC");
    }
}