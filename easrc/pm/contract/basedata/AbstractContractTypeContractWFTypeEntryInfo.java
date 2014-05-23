package com.kingdee.eas.port.pm.contract.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractTypeContractWFTypeEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractContractTypeContractWFTypeEntryInfo()
    {
        this("id");
    }
    protected AbstractContractTypeContractWFTypeEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 合同流程类型分录 's 合同类型 property 
     */
    public com.kingdee.eas.port.pm.contract.basedata.ContractTypeInfo getHead()
    {
        return (com.kingdee.eas.port.pm.contract.basedata.ContractTypeInfo)get("head");
    }
    public void setHead(com.kingdee.eas.port.pm.contract.basedata.ContractTypeInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A079FB5E");
    }
}