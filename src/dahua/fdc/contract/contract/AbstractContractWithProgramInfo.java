package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractWithProgramInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractContractWithProgramInfo()
    {
        this("id");
    }
    protected AbstractContractWithProgramInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 关联合约规划 's 合同 property 
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
     * Object: 关联合约规划 's 合约规划 property 
     */
    public com.kingdee.eas.fdc.contract.ContractProgrammingInfo getProgramming()
    {
        return (com.kingdee.eas.fdc.contract.ContractProgrammingInfo)get("programming");
    }
    public void setProgramming(com.kingdee.eas.fdc.contract.ContractProgrammingInfo item)
    {
        put("programming", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D05CF851");
    }
}