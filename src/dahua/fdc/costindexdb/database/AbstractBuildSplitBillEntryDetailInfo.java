package com.kingdee.eas.fdc.costindexdb.database;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBuildSplitBillEntryDetailInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractBuildSplitBillEntryDetailInfo()
    {
        this("id");
    }
    protected AbstractBuildSplitBillEntryDetailInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 明细数据 's null property 
     */
    public com.kingdee.eas.fdc.costindexdb.database.BuildSplitBillEntryInfo getParent1()
    {
        return (com.kingdee.eas.fdc.costindexdb.database.BuildSplitBillEntryInfo)get("parent1");
    }
    public void setParent1(com.kingdee.eas.fdc.costindexdb.database.BuildSplitBillEntryInfo item)
    {
        put("parent1", item);
    }
    /**
     * Object: 明细数据 's 楼号 property 
     */
    public com.kingdee.eas.fdc.costindexdb.database.BuildNumberInfo getBuildNumber()
    {
        return (com.kingdee.eas.fdc.costindexdb.database.BuildNumberInfo)get("buildNumber");
    }
    public void setBuildNumber(com.kingdee.eas.fdc.costindexdb.database.BuildNumberInfo item)
    {
        put("buildNumber", item);
    }
    /**
     * Object: 明细数据 's 产品类型 property 
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
     * Object:明细数据's 是否典型楼property 
     */
    public boolean isModelBuild()
    {
        return getBoolean("modelBuild");
    }
    public void setModelBuild(boolean item)
    {
        setBoolean("modelBuild", item);
    }
    /**
     * Object:明细数据's 数值property 
     */
    public java.math.BigDecimal getDataValue()
    {
        return getBigDecimal("dataValue");
    }
    public void setDataValue(java.math.BigDecimal item)
    {
        setBigDecimal("dataValue", item);
    }
    /**
     * Object:明细数据's 备注property 
     */
    public String getBeizhu()
    {
        return getString("beizhu");
    }
    public void setBeizhu(String item)
    {
        setString("beizhu", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("501915A9");
    }
}