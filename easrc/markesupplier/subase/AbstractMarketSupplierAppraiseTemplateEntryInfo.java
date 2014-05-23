package com.kingdee.eas.port.markesupplier.subase;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSupplierAppraiseTemplateEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMarketSupplierAppraiseTemplateEntryInfo()
    {
        this("id");
    }
    protected AbstractMarketSupplierAppraiseTemplateEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 第1个表体 's null property 
     */
    public com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateInfo getParent()
    {
        return (com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:第1个表体's 评审维度property 
     */
    public String getAccreditationwd()
    {
        return getString("Accreditationwd");
    }
    public void setAccreditationwd(String item)
    {
        setString("Accreditationwd", item);
    }
    /**
     * Object: 第1个表体 's 指标名称 property 
     */
    public com.kingdee.eas.port.markesupplier.subase.MarketSplAuditIndexInfo getIndexName()
    {
        return (com.kingdee.eas.port.markesupplier.subase.MarketSplAuditIndexInfo)get("IndexName");
    }
    public void setIndexName(com.kingdee.eas.port.markesupplier.subase.MarketSplAuditIndexInfo item)
    {
        put("IndexName", item);
    }
    /**
     * Object:第1个表体's 3分标准property 
     */
    public String getThreeStandard()
    {
        return getString("threeStandard");
    }
    public void setThreeStandard(String item)
    {
        setString("threeStandard", item);
    }
    /**
     * Object:第1个表体's 指标描述property 
     */
    public String getIndexDesc()
    {
        return getString("IndexDesc");
    }
    public void setIndexDesc(String item)
    {
        setString("IndexDesc", item);
    }
    /**
     * Object:第1个表体's 评分类别property 
     */
    public com.kingdee.eas.port.markesupplier.subase.AppraiseTypeEnum getScoreType()
    {
        return com.kingdee.eas.port.markesupplier.subase.AppraiseTypeEnum.getEnum(getString("ScoreType"));
    }
    public void setScoreType(com.kingdee.eas.port.markesupplier.subase.AppraiseTypeEnum item)
    {
		if (item != null) {
        setString("ScoreType", item.getValue());
		}
    }
    /**
     * Object:第1个表体's 权重(%)property 
     */
    public java.math.BigDecimal getQz()
    {
        return getBigDecimal("qz");
    }
    public void setQz(java.math.BigDecimal item)
    {
        setBigDecimal("qz", item);
    }
    /**
     * Object:第1个表体's 备注property 
     */
    public String getRemake()
    {
        return getString("remake");
    }
    public void setRemake(String item)
    {
        setString("remake", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B8B6A7CF");
    }
}