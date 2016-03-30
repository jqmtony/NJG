package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectDynamicCostEentryTotalInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractProjectDynamicCostEentryTotalInfo()
    {
        this("id");
    }
    protected AbstractProjectDynamicCostEentryTotalInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��̬�ɱ����� 's null property 
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
     * Object:��̬�ɱ�����'s �ɱ���Ŀproperty 
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
     * Object:��̬�ɱ�����'s �ɱ���Ŀ����property 
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
     * Object:��̬�ɱ�����'s Ŀ��ɱ�property 
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
     * Object:��̬�ɱ�����'s ��һ��Ŀ��ɱ�property 
     */
    public java.math.BigDecimal getFirstVersionAimcost()
    {
        return getBigDecimal("firstVersionAimcost");
    }
    public void setFirstVersionAimcost(java.math.BigDecimal item)
    {
        setBigDecimal("firstVersionAimcost", item);
    }
    /**
     * Object:��̬�ɱ�����'s ���к�������1property 
     */
    public java.math.BigDecimal getDeletaAimcost()
    {
        return getBigDecimal("deletaAimcost");
    }
    public void setDeletaAimcost(java.math.BigDecimal item)
    {
        setBigDecimal("deletaAimcost", item);
    }
    /**
     * Object:��̬�ɱ�����'s ��ͬproperty 
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
     * Object:��̬�ɱ�����'s ��Ʊ��property 
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
     * Object:��̬�ɱ�����'s �ֳ�ǩ֤property 
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
     * Object:��̬�ɱ�����'s ����property 
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
     * Object:��̬�ɱ�����'s ���ı�property 
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
     * Object:��̬�ɱ�����'s С��Bproperty 
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
     * Object:��̬�ɱ�����'s ��;�ɱ�property 
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
     * Object:��̬�ɱ�����'s Ԥ�����ǩ֤���property 
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
     * Object:��̬�ɱ�����'s ��������property 
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
     * Object:��̬�ɱ�����'s δǩ��ͬproperty 
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
     * Object:��̬�ɱ�����'s С��Cproperty 
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
     * Object:��̬�ɱ�����'s ��̬�ɱ��ϼ�property 
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
     * Object:��̬�ɱ�����'s �ɱ����property 
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
     * Object:��̬�ɱ�����'s ������property 
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
     * Object:��̬�ɱ�����'s ��עproperty 
     */
    public String getComment()
    {
        return getString("Comment");
    }
    public void setComment(String item)
    {
        setString("Comment", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0467D7DE");
    }
}