package com.kingdee.eas.fdc.contract.contractsplit;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractPCSplitBillEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractContractPCSplitBillEntryInfo()
    {
        this("id");
    }
    protected AbstractContractPCSplitBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:���ں�ͬ��Լ�滮��ַ�¼'s ��ֽ��property 
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
     * Object:���ں�ͬ��Լ�滮��ַ�¼'s ��ֱ���property 
     */
    public java.math.BigDecimal getScale()
    {
        return getBigDecimal("scale");
    }
    public void setScale(java.math.BigDecimal item)
    {
        setBigDecimal("scale", item);
    }
    /**
     * Object: ���ں�ͬ��Լ�滮��ַ�¼ 's ���ں�ͬ��Լ�滮��� property 
     */
    public com.kingdee.eas.fdc.contract.contractsplit.ContractPCSplitBillInfo getHead()
    {
        return (com.kingdee.eas.fdc.contract.contractsplit.ContractPCSplitBillInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.contract.contractsplit.ContractPCSplitBillInfo item)
    {
        put("head", item);
    }
    /**
     * Object: ���ں�ͬ��Լ�滮��ַ�¼ 's ��Լ�滮 property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo getProgrammingContract()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo)get("programmingContract");
    }
    public void setProgrammingContract(com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo item)
    {
        put("programmingContract", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("50C9B837");
    }
}