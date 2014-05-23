package com.kingdee.eas.port.pm.invest;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectBudget2E1Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractProjectBudget2E1Info()
    {
        this("id");
    }
    protected AbstractProjectBudget2E1Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 项目概算 's null property 
     */
    public com.kingdee.eas.port.pm.invest.ProjectBudget2Info getParent()
    {
        return (com.kingdee.eas.port.pm.invest.ProjectBudget2Info)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.invest.ProjectBudget2Info item)
    {
        put("parent", item);
    }
    /**
     * Object:项目概算's 合计property 
     */
    public java.math.BigDecimal getTotal()
    {
        return getBigDecimal("total");
    }
    public void setTotal(java.math.BigDecimal item)
    {
        setBigDecimal("total", item);
    }
    /**
     * Object:项目概算's 建筑工程费用property 
     */
    public java.math.BigDecimal getConstructCost()
    {
        return getBigDecimal("constructCost");
    }
    public void setConstructCost(java.math.BigDecimal item)
    {
        setBigDecimal("constructCost", item);
    }
    /**
     * Object:项目概算's 设备购置费property 
     */
    public java.math.BigDecimal getEPCost()
    {
        return getBigDecimal("EPCost");
    }
    public void setEPCost(java.math.BigDecimal item)
    {
        setBigDecimal("EPCost", item);
    }
    /**
     * Object:项目概算's 安装工程费property 
     */
    public java.math.BigDecimal getInstallCost()
    {
        return getBigDecimal("installCost");
    }
    public void setInstallCost(java.math.BigDecimal item)
    {
        setBigDecimal("installCost", item);
    }
    /**
     * Object:项目概算's 其他费用property 
     */
    public java.math.BigDecimal getOtherCost()
    {
        return getBigDecimal("otherCost");
    }
    public void setOtherCost(java.math.BigDecimal item)
    {
        setBigDecimal("otherCost", item);
    }
    /**
     * Object: 项目概算 's 单位 property 
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
     * Object:项目概算's 量数property 
     */
    public java.math.BigDecimal getVolume()
    {
        return getBigDecimal("volume");
    }
    public void setVolume(java.math.BigDecimal item)
    {
        setBigDecimal("volume", item);
    }
    /**
     * Object:项目概算's 指标property 
     */
    public java.math.BigDecimal getIndex()
    {
        return getBigDecimal("index");
    }
    public void setIndex(java.math.BigDecimal item)
    {
        setBigDecimal("index", item);
    }
    /**
     * Object:项目概算's 占总投资property 
     */
    public java.math.BigDecimal getProportion()
    {
        return getBigDecimal("proportion");
    }
    public void setProportion(java.math.BigDecimal item)
    {
        setBigDecimal("proportion", item);
    }
    /**
     * Object: 项目概算 's 费用类型 property 
     */
    public com.kingdee.eas.port.pm.base.CostTypeTreeInfo getCostType()
    {
        return (com.kingdee.eas.port.pm.base.CostTypeTreeInfo)get("costType");
    }
    public void setCostType(com.kingdee.eas.port.pm.base.CostTypeTreeInfo item)
    {
        put("costType", item);
    }
    /**
     * Object: 项目概算 's 工程项目或费用名称 property 
     */
    public com.kingdee.eas.port.pm.base.CostTypeInfo getCostName()
    {
        return (com.kingdee.eas.port.pm.base.CostTypeInfo)get("costName");
    }
    public void setCostName(com.kingdee.eas.port.pm.base.CostTypeInfo item)
    {
        put("costName", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D23C659D");
    }
}