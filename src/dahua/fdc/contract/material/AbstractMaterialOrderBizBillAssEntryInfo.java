package com.kingdee.eas.fdc.material;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMaterialOrderBizBillAssEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMaterialOrderBizBillAssEntryInfo()
    {
        this("id");
    }
    protected AbstractMaterialOrderBizBillAssEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �л��� 's null property 
     */
    public com.kingdee.eas.fdc.material.MaterialOrderBizBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.material.MaterialOrderBizBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.material.MaterialOrderBizBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: �л��� 's ���ϱ��� property 
     */
    public com.kingdee.eas.basedata.master.material.MaterialInfo getMaterialNumber()
    {
        return (com.kingdee.eas.basedata.master.material.MaterialInfo)get("materialNumber");
    }
    public void setMaterialNumber(com.kingdee.eas.basedata.master.material.MaterialInfo item)
    {
        put("materialNumber", item);
    }
    /**
     * Object:�л���'s ��������property 
     */
    public String getMaterialName()
    {
        return getString("materialName");
    }
    public void setMaterialName(String item)
    {
        setString("materialName", item);
    }
    /**
     * Object:�л���'s ����ͺ�property 
     */
    public String getMaterialModel()
    {
        return getString("materialModel");
    }
    public void setMaterialModel(String item)
    {
        setString("materialModel", item);
    }
    /**
     * Object: �л��� 's ��λ property 
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
     * Object:�л���'s ��������property 
     */
    public java.math.BigDecimal getQuantity()
    {
        return getBigDecimal("quantity");
    }
    public void setQuantity(java.math.BigDecimal item)
    {
        setBigDecimal("quantity", item);
    }
    /**
     * Object:�л���'s ����property 
     */
    public java.math.BigDecimal getPrice()
    {
        return getBigDecimal("price");
    }
    public void setPrice(java.math.BigDecimal item)
    {
        setBigDecimal("price", item);
    }
    /**
     * Object: �л��� 's �ұ� property 
     */
    public com.kingdee.eas.basedata.assistant.CurrencyInfo getCurrency()
    {
        return (com.kingdee.eas.basedata.assistant.CurrencyInfo)get("currency");
    }
    public void setCurrency(com.kingdee.eas.basedata.assistant.CurrencyInfo item)
    {
        put("currency", item);
    }
    /**
     * Object: �л��� 's ���� property 
     */
    public com.kingdee.eas.basedata.assistant.ExchangeRateInfo getRate()
    {
        return (com.kingdee.eas.basedata.assistant.ExchangeRateInfo)get("rate");
    }
    public void setRate(com.kingdee.eas.basedata.assistant.ExchangeRateInfo item)
    {
        put("rate", item);
    }
    /**
     * Object:�л���'s ���ҽ��property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object:�л���'s ԭ�ҽ��property 
     */
    public java.math.BigDecimal getOriginalAmount()
    {
        return getBigDecimal("originalAmount");
    }
    public void setOriginalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("originalAmount", item);
    }
    /**
     * Object:�л���'s ��¼��������property 
     */
    public java.math.BigDecimal getArrivedQty()
    {
        return getBigDecimal("arrivedQty");
    }
    public void setArrivedQty(java.math.BigDecimal item)
    {
        setBigDecimal("arrivedQty", item);
    }
    /**
     * Object:�л���'s ��¼����״̬property 
     */
    public com.kingdee.eas.fdc.material.MaterialBillStateEnum getArrivedState()
    {
        return com.kingdee.eas.fdc.material.MaterialBillStateEnum.getEnum(getString("arrivedState"));
    }
    public void setArrivedState(com.kingdee.eas.fdc.material.MaterialBillStateEnum item)
    {
		if (item != null) {
        setString("arrivedState", item.getValue());
		}
    }
    /**
     * Object:�л���'s ����ֵproperty 
     */
    public java.math.BigDecimal getExRate()
    {
        return getBigDecimal("exRate");
    }
    public void setExRate(java.math.BigDecimal item)
    {
        setBigDecimal("exRate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("639E3D2A");
    }
}