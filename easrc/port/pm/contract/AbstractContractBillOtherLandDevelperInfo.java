package com.kingdee.eas.port.pm.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractBillOtherLandDevelperInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractContractBillOtherLandDevelperInfo()
    {
        this("id");
    }
    protected AbstractContractBillOtherLandDevelperInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 其他甲方 's 合同 property 
     */
    public com.kingdee.eas.port.pm.contract.ContractBillInfo getContract()
    {
        return (com.kingdee.eas.port.pm.contract.ContractBillInfo)get("contract");
    }
    public void setContract(com.kingdee.eas.port.pm.contract.ContractBillInfo item)
    {
        put("contract", item);
    }
    /**
     * Object: 其他甲方 's 甲方 property 
     */
    public com.kingdee.eas.fdc.basedata.LandDeveloperInfo getLandDeveloper()
    {
        return (com.kingdee.eas.fdc.basedata.LandDeveloperInfo)get("landDeveloper");
    }
    public void setLandDeveloper(com.kingdee.eas.fdc.basedata.LandDeveloperInfo item)
    {
        put("landDeveloper", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B793D003");
    }
}