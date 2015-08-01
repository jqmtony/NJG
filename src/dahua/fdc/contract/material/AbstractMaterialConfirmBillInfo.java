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
     * Object:����ȷ�ϵ�'s ��������property 
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
     * Object:����ȷ�ϵ�'s ���ι������property 
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
     * Object:����ȷ�ϵ�'s ��ͬ�ۼƹ���property 
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
     * Object:����ȷ�ϵ�'s ����ȷ�Ͻ��property 
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
     * Object:����ȷ�ϵ�'s �ۼ�ȷ�Ͻ��property 
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
     * Object:����ȷ�ϵ�'s ����ʵ�����property 
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
     * Object:����ȷ�ϵ�'s �ۼ��Ѹ���property 
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
     * Object: ����ȷ�ϵ� 's ȷ�ϵ���¼ property 
     */
    public com.kingdee.eas.fdc.material.MaterialConfirmBillEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.material.MaterialConfirmBillEntryCollection)get("entrys");
    }
    /**
     * Object: ����ȷ�ϵ� 's ���Ϻ�ͬ property 
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
     * Object: ����ȷ�ϵ� 's ����ͬ property 
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
     * Object:����ȷ�ϵ�'s �Ƿ�����property 
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
     * Object: ����ȷ�ϵ� 's �������뵥 property 
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
     * Object:����ȷ�ϵ�'s ��ȷ�ϵ����ۼ���������property 
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
     * Object:����ȷ�ϵ�'s nullproperty 
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