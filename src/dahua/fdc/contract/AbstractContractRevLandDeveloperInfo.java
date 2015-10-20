package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractRevLandDeveloperInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractContractRevLandDeveloperInfo()
    {
        this("id");
    }
    protected AbstractContractRevLandDeveloperInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 合同修订多甲方 's  property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillReviseInfo getContractDev()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillReviseInfo)get("contractDev");
    }
    public void setContractDev(com.kingdee.eas.fdc.contract.ContractBillReviseInfo item)
    {
        put("contractDev", item);
    }
    /**
     * Object: 合同修订多甲方 's  property 
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
        return new BOSObjectType("16FF78A9");
    }
}