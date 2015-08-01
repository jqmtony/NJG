package com.kingdee.eas.fdc.material;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMaterialConfirmBillEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMaterialConfirmBillEntryInfo()
    {
        this("id");
    }
    protected AbstractMaterialConfirmBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 材料确认单分录 's 材料确认单 property 
     */
    public com.kingdee.eas.fdc.material.MaterialConfirmBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.material.MaterialConfirmBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.material.MaterialConfirmBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 材料确认单分录 's 材料明细分录 property 
     */
    public com.kingdee.eas.fdc.material.PartAMaterialEntryInfo getPartAMaterialEntry()
    {
        return (com.kingdee.eas.fdc.material.PartAMaterialEntryInfo)get("partAMaterialEntry");
    }
    public void setPartAMaterialEntry(com.kingdee.eas.fdc.material.PartAMaterialEntryInfo item)
    {
        put("partAMaterialEntry", item);
    }
    /**
     * Object: 材料确认单分录 's 物料 property 
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
     * Object:材料确认单分录's 确认原币单价property 
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
     * Object:材料确认单分录's 确认本位币单价property 
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
     * Object:材料确认单分录's 确认金额property 
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
     * Object:材料确认单分录's 数量property 
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
     * Object:材料确认单分录's 备注property 
     */
    public String getDesc()
    {
        return getString("desc");
    }
    public void setDesc(String item)
    {
        setString("desc", item);
    }
    /**
     * Object:材料确认单分录's 原币金额property 
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
     * Object:材料确认单分录's 分录源单IDproperty 
     */
    public String getEntrySrcBillID()
    {
        return getString("entrySrcBillID");
    }
    public void setEntrySrcBillID(String item)
    {
        setString("entrySrcBillID", item);
    }
    /**
     * Object:材料确认单分录's 质量评估情况property 
     */
    public String getQualityEvaluate()
    {
        return getString("qualityEvaluate");
    }
    public void setQualityEvaluate(String item)
    {
        setString("qualityEvaluate", item);
    }
    /**
     * Object:材料确认单分录's 标段property 
     */
    public com.kingdee.eas.fdc.invite.SectionEnum getSection()
    {
        return com.kingdee.eas.fdc.invite.SectionEnum.getEnum(getInt("section"));
    }
    public void setSection(com.kingdee.eas.fdc.invite.SectionEnum item)
    {
		if (item != null) {
        setInt("section", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("05E62E58");
    }
}