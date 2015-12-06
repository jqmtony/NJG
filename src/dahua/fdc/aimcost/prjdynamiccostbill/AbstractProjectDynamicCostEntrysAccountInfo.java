package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectDynamicCostEntrysAccountInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractProjectDynamicCostEntrysAccountInfo()
    {
        this("id");
    }
    protected AbstractProjectDynamicCostEntrysAccountInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 科目维度分录 's null property 
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
     * Object:科目维度分录's 成本科目property 
     */
    public String getCostAccount()
    {
        return getString("costAccount");
    }
    public void setCostAccount(String item)
    {
        setString("costAccount", item);
    }
    /**
     * Object:科目维度分录's 成本科目编码property 
     */
    public String getCostAccountNumber()
    {
        return getString("costAccountNumber");
    }
    public void setCostAccountNumber(String item)
    {
        setString("costAccountNumber", item);
    }
    /**
     * Object:科目维度分录's 目标成本property 
     */
    public java.math.BigDecimal getAimCost()
    {
        return getBigDecimal("aimCost");
    }
    public void setAimCost(java.math.BigDecimal item)
    {
        setBigDecimal("aimCost", item);
    }
    /**
     * Object:科目维度分录's 合同property 
     */
    public java.math.BigDecimal getContract()
    {
        return getBigDecimal("Contract");
    }
    public void setContract(java.math.BigDecimal item)
    {
        setBigDecimal("Contract", item);
    }
    /**
     * Object:科目维度分录's 设计变更property 
     */
    public java.math.BigDecimal getDesignChangeAmount()
    {
        return getBigDecimal("designChangeAmount");
    }
    public void setDesignChangeAmount(java.math.BigDecimal item)
    {
        setBigDecimal("designChangeAmount", item);
    }
    /**
     * Object:科目维度分录's 现场签证property 
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
     * Object:科目维度分录's 结算property 
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
     * Object:科目维度分录's 无文本property 
     */
    public java.math.BigDecimal getWithouttxt()
    {
        return getBigDecimal("withouttxt");
    }
    public void setWithouttxt(java.math.BigDecimal item)
    {
        setBigDecimal("withouttxt", item);
    }
    /**
     * Object:科目维度分录's 小计Bproperty 
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
     * Object:科目维度分录's 在途成本property 
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
     * Object:科目维度分录's 预估变更签证余额property 
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
     * Object:科目维度分录's 其他调整property 
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
     * Object:科目维度分录's 未签合同property 
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
     * Object:科目维度分录's 小计Cproperty 
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
     * Object:科目维度分录's 动态成本合计property 
     */
    public java.math.BigDecimal getSumBC()
    {
        return getBigDecimal("sumBC");
    }
    public void setSumBC(java.math.BigDecimal item)
    {
        setBigDecimal("sumBC", item);
    }
    /**
     * Object:科目维度分录's 成本差额property 
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
     * Object:科目维度分录's 差异率property 
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
     * Object:科目维度分录's 备注property 
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
     * Object:科目维度分录's 科目大类property 
     */
    public com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.AccountIndexType getAccountIndex()
    {
        return com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.AccountIndexType.getEnum(getString("accountIndex"));
    }
    public void setAccountIndex(com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.AccountIndexType item)
    {
		if (item != null) {
        setString("accountIndex", item.getValue());
		}
    }
    /**
     * Object:科目维度分录's 科目级次property 
     */
    public int getLevel()
    {
        return getInt("level");
    }
    public void setLevel(int item)
    {
        setInt("level", item);
    }
    /**
     * Object:科目维度分录's 警示指标property 
     */
    public java.math.BigDecimal getAlertIndex()
    {
        return getBigDecimal("alertIndex");
    }
    public void setAlertIndex(java.math.BigDecimal item)
    {
        setBigDecimal("alertIndex", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("FFF7E2B3");
    }
}