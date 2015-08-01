package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractConPayPlanDetailInfo extends com.kingdee.eas.fdc.finance.PayPlanDataBaseInfo implements Serializable 
{
    public AbstractConPayPlanDetailInfo()
    {
        this("id");
    }
    protected AbstractConPayPlanDetailInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 合约规划明细 's null property 
     */
    public com.kingdee.eas.fdc.finance.ConPayPlanInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.ConPayPlanInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.ConPayPlanInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 合约规划明细 's 合约规划 property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo getProgramming()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo)get("programming");
    }
    public void setProgramming(com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo item)
    {
        put("programming", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("CD46E04F");
    }
}