package com.kingdee.eas.port.pm.fi;

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
     * Object:材料确认单分录's 本次申请金额property 
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
     * Object:材料确认单分录's 本次申请的实付金额property 
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
     * Object: 材料确认单分录 's 付款申请单 property 
     */
    public com.kingdee.eas.port.pm.fi.PayRequestBillInfo getParent()
    {
        return (com.kingdee.eas.port.pm.fi.PayRequestBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.fi.PayRequestBillInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("315220AF");
    }
}