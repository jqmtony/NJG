package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAimAimCostAdjustEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractAimAimCostAdjustEntryInfo()
    {
        this("id");
    }
    protected AbstractAimAimCostAdjustEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 目标成本调理单分录 's 成本科目 property 
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
     * Object:目标成本调理单分录's 工作量property 
     */
    public java.math.BigDecimal getWorkload()
    {
        return getBigDecimal("workload");
    }
    public void setWorkload(java.math.BigDecimal item)
    {
        setBigDecimal("workload", item);
    }
    /**
     * Object:目标成本调理单分录's 单价property 
     */
    public java.math.BigDecimal getPrice()
    {
        return getBigDecimal("price");
    }
    public void setPrice(java.math.BigDecimal item)
    {
        setBigDecimal("price", item);
    }
    /**
     * Object:目标成本调理单分录's 调整金额property 
     */
    public java.math.BigDecimal getAdjustAmt()
    {
        return getBigDecimal("adjustAmt");
    }
    public void setAdjustAmt(java.math.BigDecimal item)
    {
        setBigDecimal("adjustAmt", item);
    }
    /**
     * Object:目标成本调理单分录's 备注property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    /**
     * Object: 目标成本调理单分录 's 单位 property 
     */
    public com.kingdee.eas.basedata.assistant.MeasureUnitInfo getUnit()
    {
        return (com.kingdee.eas.basedata.assistant.MeasureUnitInfo)get("unit");
    }
    public void setUnit(com.kingdee.eas.basedata.assistant.MeasureUnitInfo item)
    {
        put("unit", item);
    }
    /**
     * Object: 目标成本调理单分录 's 归属产品 property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProduct()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("product");
    }
    public void setProduct(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("product", item);
    }
    /**
     * Object: 目标成本调理单分录 's 目标成本调整单 property 
     */
    public com.kingdee.eas.fdc.aimcost.AimAimCostAdjustInfo getParent()
    {
        return (com.kingdee.eas.fdc.aimcost.AimAimCostAdjustInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.aimcost.AimAimCostAdjustInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 目标成本调理单分录 's 调整类型 property 
     */
    public com.kingdee.eas.fdc.basedata.AdjustTypeInfo getAdjustType()
    {
        return (com.kingdee.eas.fdc.basedata.AdjustTypeInfo)get("adjustType");
    }
    public void setAdjustType(com.kingdee.eas.fdc.basedata.AdjustTypeInfo item)
    {
        put("adjustType", item);
    }
    /**
     * Object:目标成本调理单分录's 变化原因property 
     */
    public String getChangeReason()
    {
        return getString("changeReason");
    }
    public void setChangeReason(String item)
    {
        setString("changeReason", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("40E1453F");
    }
}