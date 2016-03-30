package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectDynamicCostEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractProjectDynamicCostEntryInfo()
    {
        this("id");
    }
    protected AbstractProjectDynamicCostEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostInfo getParent()
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:分录's 合同名称property 
     */
    public String getContract()
    {
        return getString("Contract");
    }
    public void setContract(String item)
    {
        setString("Contract", item);
    }
    /**
     * Object:分录's 合同IDproperty 
     */
    public String getContractBillId()
    {
        return getString("ContractBillId");
    }
    public void setContractBillId(String item)
    {
        setString("ContractBillId", item);
    }
    /**
     * Object:分录's 规划金额property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("Amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("Amount", item);
    }
    /**
     * Object:分录's 合同property 
     */
    public java.math.BigDecimal getContractAmount()
    {
        return getBigDecimal("ContractAmount");
    }
    public void setContractAmount(java.math.BigDecimal item)
    {
        setBigDecimal("ContractAmount", item);
    }
    /**
     * Object:分录's 设计变更property 
     */
    public String getDesignChangeAmount()
    {
        return getString("designChangeAmount");
    }
    public void setDesignChangeAmount(String item)
    {
        setString("designChangeAmount", item);
    }
    /**
     * Object:分录's 现场签证property 
     */
    public java.math.BigDecimal getSiteCertificatAmount()
    {
        return getBigDecimal("siteCertificatAmount");
    }
    public void setSiteCertificatAmount(java.math.BigDecimal item)
    {
        setBigDecimal("siteCertificatAmount", item);
    }
    /**
     * Object:分录's 结算property 
     */
    public java.math.BigDecimal getSettlementAmount()
    {
        return getBigDecimal("settlementAmount");
    }
    public void setSettlementAmount(java.math.BigDecimal item)
    {
        setBigDecimal("settlementAmount", item);
    }
    /**
     * Object:分录's 小计Bproperty 
     */
    public java.math.BigDecimal getSumB()
    {
        return getBigDecimal("sumB");
    }
    public void setSumB(java.math.BigDecimal item)
    {
        setBigDecimal("sumB", item);
    }
    /**
     * Object:分录's 在途成本property 
     */
    public java.math.BigDecimal getOnWayCost()
    {
        return getBigDecimal("onWayCost");
    }
    public void setOnWayCost(java.math.BigDecimal item)
    {
        setBigDecimal("onWayCost", item);
    }
    /**
     * Object:分录's 本月预估变更签证property 
     */
    public java.math.BigDecimal getCurMonthEstimateChange()
    {
        return getBigDecimal("curMonthEstimateChange");
    }
    public void setCurMonthEstimateChange(java.math.BigDecimal item)
    {
        setBigDecimal("curMonthEstimateChange", item);
    }
    /**
     * Object:分录's 预估变更签证余额property 
     */
    public java.math.BigDecimal getEstimateChangeBalance()
    {
        return getBigDecimal("EstimateChangeBalance");
    }
    public void setEstimateChangeBalance(java.math.BigDecimal item)
    {
        setBigDecimal("EstimateChangeBalance", item);
    }
    /**
     * Object:分录's 本月其他调整property 
     */
    public java.math.BigDecimal getCurMonthOtherAmount()
    {
        return getBigDecimal("curMonthOtherAmount");
    }
    public void setCurMonthOtherAmount(java.math.BigDecimal item)
    {
        setBigDecimal("curMonthOtherAmount", item);
    }
    /**
     * Object:分录's 其他调整property 
     */
    public java.math.BigDecimal getOtherAmount()
    {
        return getBigDecimal("otherAmount");
    }
    public void setOtherAmount(java.math.BigDecimal item)
    {
        setBigDecimal("otherAmount", item);
    }
    /**
     * Object:分录's 未签合同property 
     */
    public java.math.BigDecimal getUnSignContract()
    {
        return getBigDecimal("unSignContract");
    }
    public void setUnSignContract(java.math.BigDecimal item)
    {
        setBigDecimal("unSignContract", item);
    }
    /**
     * Object:分录's 小计Cproperty 
     */
    public java.math.BigDecimal getSumC()
    {
        return getBigDecimal("sumC");
    }
    public void setSumC(java.math.BigDecimal item)
    {
        setBigDecimal("sumC", item);
    }
    /**
     * Object:分录's 动态成本property 
     */
    public java.math.BigDecimal getDynamicCost()
    {
        return getBigDecimal("dynamicCost");
    }
    public void setDynamicCost(java.math.BigDecimal item)
    {
        setBigDecimal("dynamicCost", item);
    }
    /**
     * Object:分录's 规划差额property 
     */
    public java.math.BigDecimal getDiffAmount()
    {
        return getBigDecimal("diffAmount");
    }
    public void setDiffAmount(java.math.BigDecimal item)
    {
        setBigDecimal("diffAmount", item);
    }
    /**
     * Object:分录's 差异率property 
     */
    public java.math.BigDecimal getDiffRate()
    {
        return getBigDecimal("diffRate");
    }
    public void setDiffRate(java.math.BigDecimal item)
    {
        setBigDecimal("diffRate", item);
    }
    /**
     * Object:分录's 备注property 
     */
    public String getComment()
    {
        return getString("Comment");
    }
    public void setComment(String item)
    {
        setString("Comment", item);
    }
    /**
     * Object:分录's 本月其他调整IDproperty 
     */
    public String getCurMonthOtherId()
    {
        return getString("curMonthOtherId");
    }
    public void setCurMonthOtherId(String item)
    {
        setString("curMonthOtherId", item);
    }
    /**
     * Object:分录's 本月预估变更IDproperty 
     */
    public String getForecastChangeVisID()
    {
        return getString("ForecastChangeVisID");
    }
    public void setForecastChangeVisID(String item)
    {
        setString("ForecastChangeVisID", item);
    }
    /**
     * Object:分录's 合约规划IDproperty 
     */
    public String getProgrammingId()
    {
        return getString("programmingId");
    }
    public void setProgrammingId(String item)
    {
        setString("programmingId", item);
    }
    /**
     * Object:分录's 合约规划名称property 
     */
    public String getProgrammingName()
    {
        return getString("programmingName");
    }
    public void setProgrammingName(String item)
    {
        setString("programmingName", item);
    }
    /**
     * Object:分录's 无文本property 
     */
    public java.math.BigDecimal getNotextContract()
    {
        return getBigDecimal("notextContract");
    }
    public void setNotextContract(java.math.BigDecimal item)
    {
        setBigDecimal("notextContract", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BF1D2179");
    }
}