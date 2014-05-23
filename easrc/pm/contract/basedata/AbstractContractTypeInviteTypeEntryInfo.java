package com.kingdee.eas.port.pm.contract.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractTypeInviteTypeEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractContractTypeInviteTypeEntryInfo()
    {
        this("id");
    }
    protected AbstractContractTypeInviteTypeEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 采购类别分录 's 采购类别 property 
     */
    public com.kingdee.eas.port.pm.contract.basedata.InviteTypeInfo getInviteType()
    {
        return (com.kingdee.eas.port.pm.contract.basedata.InviteTypeInfo)get("inviteType");
    }
    public void setInviteType(com.kingdee.eas.port.pm.contract.basedata.InviteTypeInfo item)
    {
        put("inviteType", item);
    }
    /**
     * Object: 采购类别分录 's 合同类型 property 
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
        return new BOSObjectType("B7365436");
    }
}