package com.kingdee.eas.fdc.contract;

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
     * Object: ���ı���ͬ�����嵥 's ���ı���ͬ property 
     */
    public com.kingdee.eas.fdc.contract.ContractWithoutTextInfo getHead()
    {
        return (com.kingdee.eas.fdc.contract.ContractWithoutTextInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.contract.ContractWithoutTextInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A970AA85");
    }
}