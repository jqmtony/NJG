package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectDynamicCostEntrySixMonthInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractProjectDynamicCostEntrySixMonthInfo()
    {
        this("id");
    }
    protected AbstractProjectDynamicCostEntrySixMonthInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���6�»��� 's null property 
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
     * Object:���6�»���'s Ŀ��ɱ�property 
     */
    public java.math.BigDecimal getFirstAimCost()
    {
        return getBigDecimal("firstAimCost");
    }
    public void setFirstAimCost(java.math.BigDecimal item)
    {
        setBigDecimal("firstAimCost", item);
    }
    /**
     * Object:���6�»���'s ��̬�ɱ�property 
     */
    public java.math.BigDecimal getFirstDynaCost()
    {
        return getBigDecimal("firstDynaCost");
    }
    public void setFirstDynaCost(java.math.BigDecimal item)
    {
        setBigDecimal("firstDynaCost", item);
    }
    /**
     * Object:���6�»���'s �ɱ����property 
     */
    public java.math.BigDecimal getFirstDiffAmount()
    {
        return getBigDecimal("firstDiffAmount");
    }
    public void setFirstDiffAmount(java.math.BigDecimal item)
    {
        setBigDecimal("firstDiffAmount", item);
    }
    /**
     * Object:���6�»���'s ������property 
     */
    public java.math.BigDecimal getFirstDiffRate()
    {
        return getBigDecimal("firstDiffRate");
    }
    public void setFirstDiffRate(java.math.BigDecimal item)
    {
        setBigDecimal("firstDiffRate", item);
    }
    /**
     * Object:���6�»���'s Ŀ��ɱ�property 
     */
    public java.math.BigDecimal getSecondAimCost()
    {
        return getBigDecimal("secondAimCost");
    }
    public void setSecondAimCost(java.math.BigDecimal item)
    {
        setBigDecimal("secondAimCost", item);
    }
    /**
     * Object:���6�»���'s ��̬�ɱ�property 
     */
    public java.math.BigDecimal getSecondDynaCost()
    {
        return getBigDecimal("secondDynaCost");
    }
    public void setSecondDynaCost(java.math.BigDecimal item)
    {
        setBigDecimal("secondDynaCost", item);
    }
    /**
     * Object:���6�»���'s �ɱ����property 
     */
    public java.math.BigDecimal getSecondDiffAmount()
    {
        return getBigDecimal("secondDiffAmount");
    }
    public void setSecondDiffAmount(java.math.BigDecimal item)
    {
        setBigDecimal("secondDiffAmount", item);
    }
    /**
     * Object:���6�»���'s ������property 
     */
    public java.math.BigDecimal getSecondDiffRate()
    {
        return getBigDecimal("secondDiffRate");
    }
    public void setSecondDiffRate(java.math.BigDecimal item)
    {
        setBigDecimal("secondDiffRate", item);
    }
    /**
     * Object:���6�»���'s Ŀ��ɱ�property 
     */
    public java.math.BigDecimal getThirdAimCost()
    {
        return getBigDecimal("thirdAimCost");
    }
    public void setThirdAimCost(java.math.BigDecimal item)
    {
        setBigDecimal("thirdAimCost", item);
    }
    /**
     * Object:���6�»���'s ��̬�ɱ�property 
     */
    public java.math.BigDecimal getThirdDynaCost()
    {
        return getBigDecimal("thirdDynaCost");
    }
    public void setThirdDynaCost(java.math.BigDecimal item)
    {
        setBigDecimal("thirdDynaCost", item);
    }
    /**
     * Object:���6�»���'s �ɱ����property 
     */
    public java.math.BigDecimal getThirdDiffAmount()
    {
        return getBigDecimal("thirdDiffAmount");
    }
    public void setThirdDiffAmount(java.math.BigDecimal item)
    {
        setBigDecimal("thirdDiffAmount", item);
    }
    /**
     * Object:���6�»���'s ������property 
     */
    public java.math.BigDecimal getThirdDiffRate()
    {
        return getBigDecimal("thirdDiffRate");
    }
    public void setThirdDiffRate(java.math.BigDecimal item)
    {
        setBigDecimal("thirdDiffRate", item);
    }
    /**
     * Object:���6�»���'s Ŀ��ɱ�property 
     */
    public java.math.BigDecimal getFourthAimCost()
    {
        return getBigDecimal("fourthAimCost");
    }
    public void setFourthAimCost(java.math.BigDecimal item)
    {
        setBigDecimal("fourthAimCost", item);
    }
    /**
     * Object:���6�»���'s ��̬�ɱ�property 
     */
    public java.math.BigDecimal getFourthDynaCost()
    {
        return getBigDecimal("fourthDynaCost");
    }
    public void setFourthDynaCost(java.math.BigDecimal item)
    {
        setBigDecimal("fourthDynaCost", item);
    }
    /**
     * Object:���6�»���'s �ɱ����property 
     */
    public java.math.BigDecimal getFourthDiffAmount()
    {
        return getBigDecimal("fourthDiffAmount");
    }
    public void setFourthDiffAmount(java.math.BigDecimal item)
    {
        setBigDecimal("fourthDiffAmount", item);
    }
    /**
     * Object:���6�»���'s ������property 
     */
    public java.math.BigDecimal getFourthDiffRate()
    {
        return getBigDecimal("fourthDiffRate");
    }
    public void setFourthDiffRate(java.math.BigDecimal item)
    {
        setBigDecimal("fourthDiffRate", item);
    }
    /**
     * Object:���6�»���'s Ŀ��ɱ�property 
     */
    public java.math.BigDecimal getFifthAimCost()
    {
        return getBigDecimal("fifthAimCost");
    }
    public void setFifthAimCost(java.math.BigDecimal item)
    {
        setBigDecimal("fifthAimCost", item);
    }
    /**
     * Object:���6�»���'s ��̬�ɱ�property 
     */
    public java.math.BigDecimal getFifthDynaCost()
    {
        return getBigDecimal("fifthDynaCost");
    }
    public void setFifthDynaCost(java.math.BigDecimal item)
    {
        setBigDecimal("fifthDynaCost", item);
    }
    /**
     * Object:���6�»���'s �ɱ����property 
     */
    public java.math.BigDecimal getFifthDiffAmount()
    {
        return getBigDecimal("fifthDiffAmount");
    }
    public void setFifthDiffAmount(java.math.BigDecimal item)
    {
        setBigDecimal("fifthDiffAmount", item);
    }
    /**
     * Object:���6�»���'s ������property 
     */
    public java.math.BigDecimal getFifthDiffRate()
    {
        return getBigDecimal("fifthDiffRate");
    }
    public void setFifthDiffRate(java.math.BigDecimal item)
    {
        setBigDecimal("fifthDiffRate", item);
    }
    /**
     * Object:���6�»���'s Ŀ��ɱ�property 
     */
    public java.math.BigDecimal getSixthAimCost()
    {
        return getBigDecimal("sixthAimCost");
    }
    public void setSixthAimCost(java.math.BigDecimal item)
    {
        setBigDecimal("sixthAimCost", item);
    }
    /**
     * Object:���6�»���'s ��̬�ɱ�property 
     */
    public java.math.BigDecimal getSixthDynaCost()
    {
        return getBigDecimal("sixthDynaCost");
    }
    public void setSixthDynaCost(java.math.BigDecimal item)
    {
        setBigDecimal("sixthDynaCost", item);
    }
    /**
     * Object:���6�»���'s �ɱ����property 
     */
    public java.math.BigDecimal getSixthDiffAmount()
    {
        return getBigDecimal("sixthDiffAmount");
    }
    public void setSixthDiffAmount(java.math.BigDecimal item)
    {
        setBigDecimal("sixthDiffAmount", item);
    }
    /**
     * Object:���6�»���'s ������property 
     */
    public java.math.BigDecimal getSixthDiffRate()
    {
        return getBigDecimal("sixthDiffRate");
    }
    public void setSixthDiffRate(java.math.BigDecimal item)
    {
        setBigDecimal("sixthDiffRate", item);
    }
    /**
     * Object:���6�»���'s �ɱ���Ŀproperty 
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
     * Object:���6�»���'s �ɱ���Ŀ����property 
     */
    public String getCostAccountNumber()
    {
        return getString("costAccountNumber");
    }
    public void setCostAccountNumber(String item)
    {
        setString("costAccountNumber", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6A6B2F57");
    }
}