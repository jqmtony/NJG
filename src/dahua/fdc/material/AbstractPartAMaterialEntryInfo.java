package com.kingdee.eas.fdc.material;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPartAMaterialEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractPartAMaterialEntryInfo()
    {
        this("id");
    }
    protected AbstractPartAMaterialEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ������ϸ��¼ 's �����嵥��ϸ property 
     */
    public com.kingdee.eas.fdc.material.PartAMaterialInfo getParent()
    {
        return (com.kingdee.eas.fdc.material.PartAMaterialInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.material.PartAMaterialInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ������ϸ��¼ 's ���� property 
     */
    public com.kingdee.eas.basedata.master.material.MaterialInfo getMaterial()
    {
        return (com.kingdee.eas.basedata.master.material.MaterialInfo)get("material");
    }
    public void setMaterial(com.kingdee.eas.basedata.master.material.MaterialInfo item)
    {
        put("material", item);
    }
    /**
     * Object: ������ϸ��¼ 's ����ͬ property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getMainContractBill()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("mainContractBill");
    }
    public void setMainContractBill(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("mainContractBill", item);
    }
    /**
     * Object:������ϸ��¼'s ԭ�ҽ��property 
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
     * Object:������ϸ��¼'s ��λ�ҽ��property 
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
     * Object:������ϸ��¼'s ����property 
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
     * Object:������ϸ��¼'s ��עproperty 
     */
    public String getDescription()
    {
        return getDescription((Locale)null);
    }
    public void setDescription(String item)
    {
		setDescription(item,(Locale)null);
    }
    public String getDescription(Locale local)
    {
        return TypeConversionUtils.objToString(get("description", local));
    }
    public void setDescription(String item, Locale local)
    {
        put("description", item, local);
    }
    /**
     * Object:������ϸ��¼'s ��������property 
     */
    public java.util.Date getArriveDate()
    {
        return getDate("arriveDate");
    }
    public void setArriveDate(java.util.Date item)
    {
        setDate("arriveDate", item);
    }
    /**
     * Object:������ϸ��¼'s ԭ�ҵ���property 
     */
    public java.math.BigDecimal getOriginalPrice()
    {
        return getBigDecimal("originalPrice");
    }
    public void setOriginalPrice(java.math.BigDecimal item)
    {
        setBigDecimal("originalPrice", item);
    }
    /**
     * Object:������ϸ��¼'s ��λ�ҵ���property 
     */
    public java.math.BigDecimal getPrice()
    {
        return getBigDecimal("price");
    }
    public void setPrice(java.math.BigDecimal item)
    {
        setBigDecimal("price", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B9A9A143");
    }
}