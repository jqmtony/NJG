package com.kingdee.eas.port.markesupplier.subill;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSupplierReviewGatherEntryPInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMarketSupplierReviewGatherEntryPInfo()
    {
        this("id");
    }
    protected AbstractMarketSupplierReviewGatherEntryPInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 第2个表体 's null property 
     */
    public com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherInfo getParent()
    {
        return (com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.markesupplier.subill.MarketSupplierReviewGatherInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:第2个表体's 评审维度property 
     */
    public String getGuideType()
    {
        return getString("guideType");
    }
    public void setGuideType(String item)
    {
        setString("guideType", item);
    }
    /**
     * Object:第2个表体's 3分标准property 
     */
    public String getFullNum()
    {
        return getString("fullNum");
    }
    public void setFullNum(String item)
    {
        setString("fullNum", item);
    }
    /**
     * Object:第2个表体's 权重(%)property 
     */
    public java.math.BigDecimal getWeight()
    {
        return getBigDecimal("weight");
    }
    public void setWeight(java.math.BigDecimal item)
    {
        setBigDecimal("weight", item);
    }
    /**
     * Object:第2个表体's 情况描述property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object:第2个表体's 是否合格property 
     */
    public com.kingdee.eas.port.markesupplier.subase.IsGradeEnum getIsPass()
    {
        return com.kingdee.eas.port.markesupplier.subase.IsGradeEnum.getEnum(getString("isPass"));
    }
    public void setIsPass(com.kingdee.eas.port.markesupplier.subase.IsGradeEnum item)
    {
		if (item != null) {
        setString("isPass", item.getValue());
		}
    }
    /**
     * Object:第2个表体's 综合得分property 
     */
    public java.math.BigDecimal getScore()
    {
        return getBigDecimal("score");
    }
    public void setScore(java.math.BigDecimal item)
    {
        setBigDecimal("score", item);
    }
    /**
     * Object: 第2个表体 's 指标名称 property 
     */
    public com.kingdee.eas.port.markesupplier.subase.MarketSplAuditIndexInfo getGuideName()
    {
        return (com.kingdee.eas.port.markesupplier.subase.MarketSplAuditIndexInfo)get("guideName");
    }
    public void setGuideName(com.kingdee.eas.port.markesupplier.subase.MarketSplAuditIndexInfo item)
    {
        put("guideName", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4A7122D9");
    }
}