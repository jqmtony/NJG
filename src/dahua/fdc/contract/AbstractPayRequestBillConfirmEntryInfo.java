package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPayRequestBillConfirmEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractPayRequestBillConfirmEntryInfo()
    {
        this("id");
    }
    protected AbstractPayRequestBillConfirmEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�������뵥����ȷ�Ϸ�¼'s ����������property 
     */
    public java.math.BigDecimal getReqAmount()
    {
        return getBigDecimal("reqAmount");
    }
    public void setReqAmount(java.math.BigDecimal item)
    {
        setBigDecimal("reqAmount", item);
    }
    /**
     * Object:�������뵥����ȷ�Ϸ�¼'s ���������ʵ�����property 
     */
    public java.math.BigDecimal getPaidAmount()
    {
        return getBigDecimal("paidAmount");
    }
    public void setPaidAmount(java.math.BigDecimal item)
    {
        setBigDecimal("paidAmount", item);
    }
    /**
     * Object: �������뵥����ȷ�Ϸ�¼ 's �������뵥 property 
     */
    public com.kingdee.eas.fdc.contract.PayRequestBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.PayRequestBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.PayRequestBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: �������뵥����ȷ�Ϸ�¼ 's ����ȷ�ϵ� property 
     */
    public com.kingdee.eas.fdc.material.MaterialConfirmBillInfo getConfirmBill()
    {
        return (com.kingdee.eas.fdc.material.MaterialConfirmBillInfo)get("confirmBill");
    }
    public void setConfirmBill(com.kingdee.eas.fdc.material.MaterialConfirmBillInfo item)
    {
        put("confirmBill", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("CF84109B");
    }
}