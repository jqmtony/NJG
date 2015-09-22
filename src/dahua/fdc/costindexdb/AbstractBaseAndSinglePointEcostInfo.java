package com.kingdee.eas.fdc.costindexdb;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBaseAndSinglePointEcostInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractBaseAndSinglePointEcostInfo()
    {
        this("id");
    }
    protected AbstractBaseAndSinglePointEcostInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 单项要素 's null property 
     */
    public com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointInfo getParent()
    {
        return (com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.costindexdb.BaseAndSinglePointInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 单项要素 's 成本科目 property 
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
     * Object:单项要素's 要素property 
     */
    public String getPointName()
    {
        return getString("pointName");
    }
    public void setPointName(String item)
    {
        setString("pointName", item);
    }
    /**
     * Object:单项要素's 数值property 
     */
    public java.math.BigDecimal getPointValue()
    {
        return getBigDecimal("pointValue");
    }
    public void setPointValue(java.math.BigDecimal item)
    {
        setBigDecimal("pointValue", item);
    }
    /**
     * Object: 单项要素 's 单位 property 
     */
    public com.kingdee.eas.basedata.assistant.MeasureUnitInfo getBaseUnit()
    {
        return (com.kingdee.eas.basedata.assistant.MeasureUnitInfo)get("baseUnit");
    }
    public void setBaseUnit(com.kingdee.eas.basedata.assistant.MeasureUnitInfo item)
    {
        put("baseUnit", item);
    }
    /**
     * Object:单项要素's 是否需要拆分property 
     */
    public boolean isIsCombo()
    {
        return getBoolean("isCombo");
    }
    public void setIsCombo(boolean item)
    {
        setBoolean("isCombo", item);
    }
    /**
     * Object: 单项要素 's 产品类型 property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProductType()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("productType");
    }
    public void setProductType(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("productType", item);
    }
    /**
     * Object:单项要素's 是否典型楼property 
     */
    public boolean isIsModel()
    {
        return getBoolean("isModel");
    }
    public void setIsModel(boolean item)
    {
        setBoolean("isModel", item);
    }
    /**
     * Object:单项要素's 数值property 
     */
    public java.math.BigDecimal getBuildValue()
    {
        return getBigDecimal("buildValue");
    }
    public void setBuildValue(java.math.BigDecimal item)
    {
        setBigDecimal("buildValue", item);
    }
    /**
     * Object:单项要素's 备注property 
     */
    public String getBeizhu()
    {
        return getString("beizhu");
    }
    public void setBeizhu(String item)
    {
        setString("beizhu", item);
    }
    /**
     * Object: 单项要素 's 楼号 property 
     */
    public com.kingdee.eas.fdc.costindexdb.database.BuildNumberInfo getBuildNo()
    {
        return (com.kingdee.eas.fdc.costindexdb.database.BuildNumberInfo)get("buildNo");
    }
    public void setBuildNo(com.kingdee.eas.fdc.costindexdb.database.BuildNumberInfo item)
    {
        put("buildNo", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DA8387EA");
    }
}