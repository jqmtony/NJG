package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractNodeMeasureEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractNodeMeasureEntryInfo()
    {
        this("id");
    }
    protected AbstractNodeMeasureEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 节点计价分录 's 节点计价 property 
     */
    public com.kingdee.eas.fdc.contract.NodeMeasureInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.NodeMeasureInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.NodeMeasureInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 节点计价分录 's 成本科目 property 
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
     * Object:节点计价分录's 计量单位property 
     */
    public String getUnit()
    {
        return getString("unit");
    }
    public void setUnit(String item)
    {
        setString("unit", item);
    }
    /**
     * Object:节点计价分录's 合同工作量property 
     */
    public java.math.BigDecimal getConWorkLoad()
    {
        return getBigDecimal("conWorkLoad");
    }
    public void setConWorkLoad(java.math.BigDecimal item)
    {
        setBigDecimal("conWorkLoad", item);
    }
    /**
     * Object:节点计价分录's 合同单价property 
     */
    public java.math.BigDecimal getConPrice()
    {
        return getBigDecimal("conPrice");
    }
    public void setConPrice(java.math.BigDecimal item)
    {
        setBigDecimal("conPrice", item);
    }
    /**
     * Object:节点计价分录's 合同合价property 
     */
    public java.math.BigDecimal getConAmount()
    {
        return getBigDecimal("conAmount");
    }
    public void setConAmount(java.math.BigDecimal item)
    {
        setBigDecimal("conAmount", item);
    }
    /**
     * Object:节点计价分录's 变更工作量property 
     */
    public java.math.BigDecimal getChangeWorkLoad()
    {
        return getBigDecimal("changeWorkLoad");
    }
    public void setChangeWorkLoad(java.math.BigDecimal item)
    {
        setBigDecimal("changeWorkLoad", item);
    }
    /**
     * Object:节点计价分录's 变更单价property 
     */
    public java.math.BigDecimal getChangePrice()
    {
        return getBigDecimal("changePrice");
    }
    public void setChangePrice(java.math.BigDecimal item)
    {
        setBigDecimal("changePrice", item);
    }
    /**
     * Object:节点计价分录's 变更合价property 
     */
    public java.math.BigDecimal getChangeAmount()
    {
        return getBigDecimal("changeAmount");
    }
    public void setChangeAmount(java.math.BigDecimal item)
    {
        setBigDecimal("changeAmount", item);
    }
    /**
     * Object:节点计价分录's 结算工作量property 
     */
    public java.math.BigDecimal getSettleWorkLoad()
    {
        return getBigDecimal("settleWorkLoad");
    }
    public void setSettleWorkLoad(java.math.BigDecimal item)
    {
        setBigDecimal("settleWorkLoad", item);
    }
    /**
     * Object:节点计价分录's 结算单价property 
     */
    public java.math.BigDecimal getSettlePrice()
    {
        return getBigDecimal("settlePrice");
    }
    public void setSettlePrice(java.math.BigDecimal item)
    {
        setBigDecimal("settlePrice", item);
    }
    /**
     * Object:节点计价分录's 结算合价property 
     */
    public java.math.BigDecimal getSettleAmount()
    {
        return getBigDecimal("settleAmount");
    }
    public void setSettleAmount(java.math.BigDecimal item)
    {
        setBigDecimal("settleAmount", item);
    }
    /**
     * Object:节点计价分录's 节点计价工作量property 
     */
    public java.math.BigDecimal getNodeWorkLoad()
    {
        return getBigDecimal("nodeWorkLoad");
    }
    public void setNodeWorkLoad(java.math.BigDecimal item)
    {
        setBigDecimal("nodeWorkLoad", item);
    }
    /**
     * Object:节点计价分录's 节点计价单价property 
     */
    public java.math.BigDecimal getNodePrice()
    {
        return getBigDecimal("nodePrice");
    }
    public void setNodePrice(java.math.BigDecimal item)
    {
        setBigDecimal("nodePrice", item);
    }
    /**
     * Object:节点计价分录's 节点计价合价property 
     */
    public java.math.BigDecimal getNodeAmount()
    {
        return getBigDecimal("nodeAmount");
    }
    public void setNodeAmount(java.math.BigDecimal item)
    {
        setBigDecimal("nodeAmount", item);
    }
    /**
     * Object:节点计价分录's 实付工作量property 
     */
    public java.math.BigDecimal getPayWorkLoad()
    {
        return getBigDecimal("payWorkLoad");
    }
    public void setPayWorkLoad(java.math.BigDecimal item)
    {
        setBigDecimal("payWorkLoad", item);
    }
    /**
     * Object:节点计价分录's 实付单价property 
     */
    public java.math.BigDecimal getPayPrice()
    {
        return getBigDecimal("payPrice");
    }
    public void setPayPrice(java.math.BigDecimal item)
    {
        setBigDecimal("payPrice", item);
    }
    /**
     * Object:节点计价分录's 实付合价property 
     */
    public java.math.BigDecimal getPayAmount()
    {
        return getBigDecimal("payAmount");
    }
    public void setPayAmount(java.math.BigDecimal item)
    {
        setBigDecimal("payAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3C97D911");
    }
}