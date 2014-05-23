package com.kingdee.eas.port.markesupplier.subill;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSupplierReviewGatherEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMarketSupplierReviewGatherEntryInfo()
    {
        this("id");
    }
    protected AbstractMarketSupplierReviewGatherEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��¼ 's ����ͷ property 
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
     * Object:��¼'s ���޺�ͬproperty 
     */
    public boolean isIsHasContract()
    {
        return getBoolean("isHasContract");
    }
    public void setIsHasContract(boolean item)
    {
        setBoolean("isHasContract", item);
    }
    /**
     * Object:��¼'s ��ͬ���(Ԫ)property 
     */
    public java.math.BigDecimal getContractAmount()
    {
        return getBigDecimal("contractAmount");
    }
    public void setContractAmount(java.math.BigDecimal item)
    {
        setBigDecimal("contractAmount", item);
    }
    /**
     * Object:��¼'s ��Ŀ����property 
     */
    public String getManager()
    {
        return getString("manager");
    }
    public void setManager(String item)
    {
        setString("manager", item);
    }
    /**
     * Object:��¼'s ��Ŀ����绰property 
     */
    public String getManagerPhone()
    {
        return getString("managerPhone");
    }
    public void setManagerPhone(String item)
    {
        setString("managerPhone", item);
    }
    /**
     * Object:��¼'s ��ͬ����(Ԫ)property 
     */
    public java.math.BigDecimal getContractPrice()
    {
        return getBigDecimal("contractPrice");
    }
    public void setContractPrice(java.math.BigDecimal item)
    {
        setBigDecimal("contractPrice", item);
    }
    /**
     * Object:��¼'s ����property 
     */
    public java.math.BigDecimal getQuantity()
    {
        return getBigDecimal("Quantity");
    }
    public void setQuantity(java.math.BigDecimal item)
    {
        setBigDecimal("Quantity", item);
    }
    /**
     * Object: ��¼ 's ��λ property 
     */
    public com.kingdee.eas.basedata.assistant.MeasureUnitInfo getUniy()
    {
        return (com.kingdee.eas.basedata.assistant.MeasureUnitInfo)get("uniy");
    }
    public void setUniy(com.kingdee.eas.basedata.assistant.MeasureUnitInfo item)
    {
        put("uniy", item);
    }
    /**
     * Object:��¼'s �Լ۱�property 
     */
    public String getValueForMoney()
    {
        return getString("ValueForMoney");
    }
    public void setValueForMoney(String item)
    {
        setString("ValueForMoney", item);
    }
    /**
     * Object:��¼'s ��ͬ����property 
     */
    public String getContract()
    {
        return getString("contract");
    }
    public void setContract(String item)
    {
        setString("contract", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("970BE857");
    }
}