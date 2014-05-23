package com.kingdee.eas.port.pm.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractBillContractPlanInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractContractBillContractPlanInfo()
    {
        this("id");
    }
    protected AbstractContractBillContractPlanInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 合同计划 's 父 property 
     */
    public com.kingdee.eas.port.pm.contract.ContractBillInfo getParent()
    {
        return (com.kingdee.eas.port.pm.contract.ContractBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.port.pm.contract.ContractBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:合同计划's 付款日期property 
     */
    public java.util.Date getPayDate()
    {
        return getDate("payDate");
    }
    public void setPayDate(java.util.Date item)
    {
        setDate("payDate", item);
    }
    /**
     * Object:合同计划's 付款金额property 
     */
    public java.math.BigDecimal getPayAmount()
    {
        return getBigDecimal("payAmount");
    }
    public void setPayAmount(java.math.BigDecimal item)
    {
        setBigDecimal("payAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F8532154");
    }
}