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
     * Object: 材料明细分录 's 材料清单明细 property 
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
     * Object: 材料明细分录 's 物料 property 
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
     * Object: 材料明细分录 's 主合同 property 
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
     * Object:材料明细分录's 原币金额property 
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
     * Object:材料明细分录's 本位币金额property 
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
     * Object:材料明细分录's 数量property 
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
     * Object:材料明细分录's 备注property 
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
     * Object:材料明细分录's 到货日期property 
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
     * Object:材料明细分录's 原币单价property 
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
     * Object:材料明细分录's 本位币单价property 
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