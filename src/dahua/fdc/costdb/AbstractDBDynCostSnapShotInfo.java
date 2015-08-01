package com.kingdee.eas.fdc.costdb;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDBDynCostSnapShotInfo extends com.kingdee.eas.framework.ObjectBaseInfo implements Serializable 
{
    public AbstractDBDynCostSnapShotInfo()
    {
        this("id");
    }
    protected AbstractDBDynCostSnapShotInfo(String pkField)
    {
        super(pkField);
        put("settEntries", new com.kingdee.eas.fdc.costdb.DBDynCostSnapShotSettEntryCollection());
        put("proTypEntries", new com.kingdee.eas.fdc.costdb.DBDynCostSnapShotProTypEntryCollection());
    }
    /**
     * Object:动态成本快照's 快照日期 property 
     */
    public java.util.Date getSnapShotDate()
    {
        return getDate("snapShotDate");
    }
    public void setSnapShotDate(java.util.Date item)
    {
        setDate("snapShotDate", item);
    }
    /**
     * Object:动态成本快照's 未结算签约金额property 
     */
    public java.math.BigDecimal getUnSettSignAmt()
    {
        return getBigDecimal("unSettSignAmt");
    }
    public void setUnSettSignAmt(java.math.BigDecimal item)
    {
        setBigDecimal("unSettSignAmt", item);
    }
    /**
     * Object:动态成本快照's 已结算签约金额property 
     */
    public java.math.BigDecimal getSettSignAmt()
    {
        return getBigDecimal("settSignAmt");
    }
    public void setSettSignAmt(java.math.BigDecimal item)
    {
        setBigDecimal("settSignAmt", item);
    }
    /**
     * Object:动态成本快照's 结算调整金额property 
     */
    public java.math.BigDecimal getSettAdjustAmt()
    {
        return getBigDecimal("settAdjustAmt");
    }
    public void setSettAdjustAmt(java.math.BigDecimal item)
    {
        setBigDecimal("settAdjustAmt", item);
    }
    /**
     * Object:动态成本快照's 非合同性成本金额property 
     */
    public java.math.BigDecimal getUnContractCostAmt()
    {
        return getBigDecimal("unContractCostAmt");
    }
    public void setUnContractCostAmt(java.math.BigDecimal item)
    {
        setBigDecimal("unContractCostAmt", item);
    }
    /**
     * Object:动态成本快照's 目前已发生金额property 
     */
    public java.math.BigDecimal getSoFarHasAmt()
    {
        return getBigDecimal("soFarHasAmt");
    }
    public void setSoFarHasAmt(java.math.BigDecimal item)
    {
        setBigDecimal("soFarHasAmt", item);
    }
    /**
     * Object:动态成本快照's 目前待发生金额property 
     */
    public java.math.BigDecimal getSoFarToAmt()
    {
        return getBigDecimal("soFarToAmt");
    }
    public void setSoFarToAmt(java.math.BigDecimal item)
    {
        setBigDecimal("soFarToAmt", item);
    }
    /**
     * Object:动态成本快照's 动态成本金额property 
     */
    public java.math.BigDecimal getDynCostAmt()
    {
        return getBigDecimal("dynCostAmt");
    }
    public void setDynCostAmt(java.math.BigDecimal item)
    {
        setBigDecimal("dynCostAmt", item);
    }
    /**
     * Object:动态成本快照's 目标成本金额property 
     */
    public java.math.BigDecimal getAimCostAmt()
    {
        return getBigDecimal("aimCostAmt");
    }
    public void setAimCostAmt(java.math.BigDecimal item)
    {
        setBigDecimal("aimCostAmt", item);
    }
    /**
     * Object:动态成本快照's 差异金额property 
     */
    public java.math.BigDecimal getDiffAmt()
    {
        return getBigDecimal("diffAmt");
    }
    public void setDiffAmt(java.math.BigDecimal item)
    {
        setBigDecimal("diffAmt", item);
    }
    /**
     * Object:动态成本快照's 可售单方金额property 
     */
    public java.math.BigDecimal getSalableAmt()
    {
        return getBigDecimal("salableAmt");
    }
    public void setSalableAmt(java.math.BigDecimal item)
    {
        setBigDecimal("salableAmt", item);
    }
    /**
     * Object:动态成本快照's 建筑单方金额property 
     */
    public java.math.BigDecimal getConstrAmt()
    {
        return getBigDecimal("constrAmt");
    }
    public void setConstrAmt(java.math.BigDecimal item)
    {
        setBigDecimal("constrAmt", item);
    }
    /**
     * Object: 动态成本快照 's 结算分录 property 
     */
    public com.kingdee.eas.fdc.costdb.DBDynCostSnapShotSettEntryCollection getSettEntries()
    {
        return (com.kingdee.eas.fdc.costdb.DBDynCostSnapShotSettEntryCollection)get("settEntries");
    }
    /**
     * Object: 动态成本快照 's 产品类型分录 property 
     */
    public com.kingdee.eas.fdc.costdb.DBDynCostSnapShotProTypEntryCollection getProTypEntries()
    {
        return (com.kingdee.eas.fdc.costdb.DBDynCostSnapShotProTypEntryCollection)get("proTypEntries");
    }
    /**
     * Object: 动态成本快照 's 成本科目 property 
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
     * Object: 动态成本快照 's 分摊类型 property 
     */
    public com.kingdee.eas.fdc.basedata.ApportionTypeInfo getApprotionType()
    {
        return (com.kingdee.eas.fdc.basedata.ApportionTypeInfo)get("approtionType");
    }
    public void setApprotionType(com.kingdee.eas.fdc.basedata.ApportionTypeInfo item)
    {
        put("approtionType", item);
    }
    /**
     * Object: 动态成本快照 's 工程项目 property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("project", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("FB1EFB8A");
    }
}