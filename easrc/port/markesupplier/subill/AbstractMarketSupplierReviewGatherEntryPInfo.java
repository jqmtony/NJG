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
     * Object: ��2������ 's null property 
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
     * Object:��2������'s ����ά��property 
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
     * Object:��2������'s 3�ֱ�׼property 
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
     * Object:��2������'s Ȩ��(%)property 
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
     * Object:��2������'s �������property 
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
     * Object:��2������'s �Ƿ�ϸ�property 
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
     * Object:��2������'s �ۺϵ÷�property 
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
     * Object: ��2������ 's ָ������ property 
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