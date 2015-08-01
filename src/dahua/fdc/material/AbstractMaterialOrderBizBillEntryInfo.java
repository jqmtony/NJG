package com.kingdee.eas.fdc.material;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMaterialOrderBizBillEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMaterialOrderBizBillEntryInfo()
    {
        this("id");
    }
    protected AbstractMaterialOrderBizBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 行明细 's 单据头 property 
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
     * Object: 行明细 's 物料编码 property 
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
     * Object:行明细's 物料名称property 
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
     * Object:行明细's 规格型号property 
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
     * Object: 行明细 's 单位 property 
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
     * Object:行明细's 订货数量property 
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
     * Object:行明细's 累计到货数量property 
     */
    public java.math.BigDecimal getTotalRevQty()
    {
        return getBigDecimal("totalRevQty");
    }
    public void setTotalRevQty(java.math.BigDecimal item)
    {
        setBigDecimal("totalRevQty", item);
    }
    /**
     * Object:行明细's 单价property 
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
     * Object: 行明细 's 币别 property 
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
     * Object: 行明细 's 汇率 property 
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
     * Object:行明细's 本币金额property 
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
     * Object:行明细's 原币金额property 
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
     * Object:行明细's 供货时间property 
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
     * Object: 行明细 's 工程项目 property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    /**
     * Object:行明细's 送货地点property 
     */
    public String getAriverAddr()
    {
        return getString("ariverAddr");
    }
    public void setAriverAddr(String item)
    {
        setString("ariverAddr", item);
    }
    /**
     * Object: 行明细 's 责任部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("dept");
    }
    public void setDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("dept", item);
    }
    /**
     * Object: 行明细 's 计划提单人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getPlanUser()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("planUser");
    }
    public void setPlanUser(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("planUser", item);
    }
    /**
     * Object: 行明细 's 施工单位 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getContractUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("contractUnit");
    }
    public void setContractUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("contractUnit", item);
    }
    /**
     * Object: 行明细 's 施工合同 property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getContractBill()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("contractBill");
    }
    public void setContractBill(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("contractBill", item);
    }
    /**
     * Object: 行明细 's 材料合同 property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getMaterialContract()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("materialContract");
    }
    public void setMaterialContract(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("materialContract", item);
    }
    /**
     * Object:行明细's 来源单据IDproperty 
     */
    public String getSourceBill()
    {
        return getString("sourceBill");
    }
    public void setSourceBill(String item)
    {
        setString("sourceBill", item);
    }
    /**
     * Object:行明细's 汇率值property 
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
        return new BOSObjectType("3F1DF459");
    }
}