package com.kingdee.eas.fdc.costdb;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDBDynCostSnapShotProTypEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractDBDynCostSnapShotProTypEntryInfo()
    {
        this("id");
    }
    protected AbstractDBDynCostSnapShotProTypEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 动态成本快照产品类型分录 's 动态成本快照 property 
     */
    public com.kingdee.eas.fdc.costdb.DBDynCostSnapShotInfo getParent()
    {
        return (com.kingdee.eas.fdc.costdb.DBDynCostSnapShotInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.costdb.DBDynCostSnapShotInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:动态成本快照产品类型分录's 目标可售单方金额property 
     */
    public java.math.BigDecimal getAimSaleUnitAmt()
    {
        return getBigDecimal("aimSaleUnitAmt");
    }
    public void setAimSaleUnitAmt(java.math.BigDecimal item)
    {
        setBigDecimal("aimSaleUnitAmt", item);
    }
    /**
     * Object:动态成本快照产品类型分录's 目标金额property 
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
     * Object:动态成本快照产品类型分录's 已发生金额property 
     */
    public java.math.BigDecimal getHasHappenAmt()
    {
        return getBigDecimal("hasHappenAmt");
    }
    public void setHasHappenAmt(java.math.BigDecimal item)
    {
        setBigDecimal("hasHappenAmt", item);
    }
    /**
     * Object:动态成本快照产品类型分录's 未发生金额property 
     */
    public java.math.BigDecimal getNotHappenAmt()
    {
        return getBigDecimal("notHappenAmt");
    }
    public void setNotHappenAmt(java.math.BigDecimal item)
    {
        setBigDecimal("notHappenAmt", item);
    }
    /**
     * Object:动态成本快照产品类型分录's 可售单方金额property 
     */
    public java.math.BigDecimal getDynSaleUnitAmt()
    {
        return getBigDecimal("dynSaleUnitAmt");
    }
    public void setDynSaleUnitAmt(java.math.BigDecimal item)
    {
        setBigDecimal("dynSaleUnitAmt", item);
    }
    /**
     * Object:动态成本快照产品类型分录's 动态成本金额property 
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
     * Object: 动态成本快照产品类型分录 's 产品类型 property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProductType()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("productType");
    }
    public void setProductType(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("productType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4CB5408A");
    }
}