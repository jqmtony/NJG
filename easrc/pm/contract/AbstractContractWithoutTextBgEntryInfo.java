package com.kingdee.eas.port.pm.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractWithoutTextBgEntryInfo extends com.kingdee.eas.fdc.contract.PayBgEntryInfo implements Serializable 
{
    public AbstractContractWithoutTextBgEntryInfo()
    {
        this("id");
    }
    protected AbstractContractWithoutTextBgEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 无文本合同费用清单 's 无文本合同 property 
     */
    public com.kingdee.eas.port.pm.contract.ContractWithoutTextInfo getHead()
    {
        return (com.kingdee.eas.port.pm.contract.ContractWithoutTextInfo)get("head");
    }
    public void setHead(com.kingdee.eas.port.pm.contract.ContractWithoutTextInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9074D72A");
    }
}