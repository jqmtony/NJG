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
     * Object:跨期合同合约规划拆分分录's 拆分金额property 
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
     * Object:跨期合同合约规划拆分分录's 拆分比例property 
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
     * Object: 跨期合同合约规划拆分分录 's 跨期合同合约规划拆分 property 
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
     * Object: 跨期合同合约规划拆分分录 's 合约规划 property 
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