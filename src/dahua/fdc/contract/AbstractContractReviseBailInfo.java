package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractReviseBailInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractContractReviseBailInfo()
    {
        this("id");
    }
    protected AbstractContractReviseBailInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.contract.ContractReviseBailEntryCollection());
    }
    /**
     * Object:合同修订履约保证金及返还部分's 履约保证金金额（原币）property 
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
     * Object:合同修订履约保证金及返还部分's 履约保证金比例property 
     */
    public java.math.BigDecimal getProp()
    {
        return getBigDecimal("prop");
    }
    public void setProp(java.math.BigDecimal item)
    {
        setBigDecimal("prop", item);
    }
    /**
     * Object: 合同修订履约保证金及返还部分 's 合同修订履约保证金及返还部分分录 property 
     */
    public com.kingdee.eas.fdc.contract.ContractReviseBailEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.contract.ContractReviseBailEntryCollection)get("entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1707FEE7");
    }
}