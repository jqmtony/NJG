package com.kingdee.eas.port.markesupplier.subill;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSupplierStockE4Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMarketSupplierStockE4Info()
    {
        this("id");
    }
    protected AbstractMarketSupplierStockE4Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 符合性审查 's null property 
     */
    public com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo getParent()
    {
        return (com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.markesupplier.subill.MarketSupplierStockInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:符合性审查's 评审指标property 
     */
    public String getEvaluationIndex()
    {
        return getString("EvaluationIndex");
    }
    public void setEvaluationIndex(String item)
    {
        setString("EvaluationIndex", item);
    }
    /**
     * Object:符合性审查's 情况描述property 
     */
    public String getDescription()
    {
        return getString("Description");
    }
    public void setDescription(String item)
    {
        setString("Description", item);
    }
    /**
     * Object:符合性审查's 是否合格property 
     */
    public boolean isIsQualified()
    {
        return getBoolean("isQualified");
    }
    public void setIsQualified(boolean item)
    {
        setBoolean("isQualified", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("949E2FBD");
    }
}