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
     * Object: ��¼ 's ����ͷ property 
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
     * Object:��¼'s ��ͬ����property 
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
     * Object:��¼'s ��ͬIDproperty 
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
     * Object:��¼'s �滮���property 
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
     * Object:��¼'s ��ͬproperty 
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
     * Object:��¼'s ��Ʊ��property 
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
     * Object:��¼'s �ֳ�ǩ֤property 
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
     * Object:��¼'s ����property 
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
     * Object:��¼'s С��Bproperty 
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
     * Object:��¼'s ��;�ɱ�property 
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
     * Object:��¼'s ����Ԥ�����ǩ֤property 
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
     * Object:��¼'s Ԥ�����ǩ֤���property 
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
     * Object:��¼'s ������������property 
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
     * Object:��¼'s ��������property 
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
     * Object:��¼'s δǩ��ͬproperty 
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
     * Object:��¼'s С��Cproperty 
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
     * Object:��¼'s ��̬�ɱ�property 
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
     * Object:��¼'s �滮���property 
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
     * Object:��¼'s ������property 
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
     * Object:��¼'s ��עproperty 
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
     * Object:��¼'s ������������IDproperty 
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
     * Object:��¼'s ����Ԥ�����IDproperty 
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
     * Object:��¼'s ��Լ�滮IDproperty 
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
     * Object:��¼'s ��Լ�滮����property 
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
     * Object:��¼'s ���ı�property 
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