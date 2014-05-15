package com.kingdee.eas.port.pm.contract.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractTypeEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractContractTypeEntryInfo()
    {
        this("id");
    }
    protected AbstractContractTypeEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 付款事项分录 's 付款事项 property 
     */
    public com.kingdee.eas.port.pm.contract.basedata.PayContentTypeInfo getPayContentType()
    {
        return (com.kingdee.eas.port.pm.contract.basedata.PayContentTypeInfo)get("payContentType");
    }
    public void setPayContentType(com.kingdee.eas.port.pm.contract.basedata.PayContentTypeInfo item)
    {
        put("payContentType", item);
    }
    /**
     * Object: 付款事项分录 's 合同类型 property 
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
        return new BOSObjectType("81D6F919");
    }
}