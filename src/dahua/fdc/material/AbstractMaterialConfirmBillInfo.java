package com.kingdee.eas.fdc.material;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMaterialConfirmBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractMaterialConfirmBillInfo()
    {
        this("id");
    }
    protected AbstractMaterialConfirmBillInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.material.MaterialConfirmBillEntryCollection());
    }
    /**
     * Object:材料确认单's 供货日期property 
     */
    public java.util.Date getSupplyDate()
    {
        return getDate("supplyDate");
    }
    public void setSupplyDate(java.util.Date item)
    {
        setDate("supplyDate", item);
    }
    /**
     * Object:材料确认单's 本次供货金额property 
     */
    public java.math.BigDecimal getSupplyAmt()
    {
        return getBigDecimal("supplyAmt");
    }
    public void setSupplyAmt(java.math.BigDecimal item)
    {
        setBigDecimal("supplyAmt", item);
    }
    /**
     * Object:材料确认单's 合同累计供货property 
     */
    public java.math.BigDecimal getToDateSupplyAmt()
    {
        return getBigDecimal("toDateSupplyAmt");
    }
    public void setToDateSupplyAmt(java.math.BigDecimal item)
    {
        setBigDecimal("toDateSupplyAmt", item);
    }
    /**
     * Object:材料确认单's 本次确认金额property 
     */
    public java.math.BigDecimal getConfirmAmt()
    {
        return getBigDecimal("confirmAmt");
    }
    public void setConfirmAmt(java.math.BigDecimal item)
    {
        setBigDecimal("confirmAmt", item);
    }
    /**
     * Object:材料确认单's 累计确认金额property 
     */
    public java.math.BigDecimal getToDateConfirmAmt()
    {
        return getBigDecimal("toDateConfirmAmt");
    }
    public void setToDateConfirmAmt(java.math.BigDecimal item)
    {
        setBigDecimal("toDateConfirmAmt", item);
    }
    /**
     * Object:材料确认单's 本期实付金额property 
     */
    public java.math.BigDecimal getPaidAmt()
    {
        return getBigDecimal("paidAmt");
    }
    public void setPaidAmt(java.math.BigDecimal item)
    {
        setBigDecimal("paidAmt", item);
    }
    /**
     * Object:材料确认单's 累计已付款property 
     */
    public java.math.BigDecimal getToDatePaidAmt()
    {
        return getBigDecimal("toDatePaidAmt");
    }
    public void setToDatePaidAmt(java.math.BigDecimal item)
    {
        setBigDecimal("toDatePaidAmt", item);
    }
    /**
     * Object: 材料确认单 's 确认单分录 property 
     */
    public com.kingdee.eas.fdc.material.MaterialConfirmBillEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.material.MaterialConfirmBillEntryCollection)get("entrys");
    }
    /**
     * Object: 材料确认单 's 材料合同 property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getMaterialContractBill()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("materialContractBill");
    }
    public void setMaterialContractBill(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("materialContractBill", item);
    }
    /**
     * Object: 材料确认单 's 主合同 property 
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
     * Object:材料确认单's 是否申请property 
     */
    public boolean isHasApplied()
    {
        return getBoolean("hasApplied");
    }
    public void setHasApplied(boolean item)
    {
        setBoolean("hasApplied", item);
    }
    /**
     * Object: 材料确认单 's 付款申请单 property 
     */
    public com.kingdee.eas.fdc.contract.PayRequestBillInfo getPayRequestBill()
    {
        return (com.kingdee.eas.fdc.contract.PayRequestBillInfo)get("payRequestBill");
    }
    public void setPayRequestBill(com.kingdee.eas.fdc.contract.PayRequestBillInfo item)
    {
        put("payRequestBill", item);
    }
    /**
     * Object:材料确认单's 本确认单的累计已申请金额property 
     */
    public java.math.BigDecimal getToDateReqAmt()
    {
        return getBigDecimal("toDateReqAmt");
    }
    public void setToDateReqAmt(java.math.BigDecimal item)
    {
        setBigDecimal("toDateReqAmt", item);
    }
    /**
     * Object:材料确认单's nullproperty 
     */
    public String getSrcBillID()
    {
        return getString("srcBillID");
    }
    public void setSrcBillID(String item)
    {
        setString("srcBillID", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1D79BE3A");
    }
}