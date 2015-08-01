package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectDynaticFundPayPlanEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractProjectDynaticFundPayPlanEntryInfo()
    {
        this("id");
    }
    protected AbstractProjectDynaticFundPayPlanEntryInfo(String pkField)
    {
        super(pkField);
        put("detailEntry", new com.kingdee.eas.fdc.finance.ProjectDynaticFundPayPlanDetailEntryCollection());
    }
    /**
     * Object: 项目动态资金付款计划分录 's 单头 property 
     */
    public com.kingdee.eas.fdc.finance.ProjectDynaticFundPayPlanInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.ProjectDynaticFundPayPlanInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.ProjectDynaticFundPayPlanInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 项目动态资金付款计划分录 's 合约规划 property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo getProgramming()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo)get("programming");
    }
    public void setProgramming(com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo item)
    {
        put("programming", item);
    }
    /**
     * Object:项目动态资金付款计划分录's 规划金额property 
     */
    public java.math.BigDecimal getProgrammingAmount()
    {
        return getBigDecimal("programmingAmount");
    }
    public void setProgrammingAmount(java.math.BigDecimal item)
    {
        setBigDecimal("programmingAmount", item);
    }
    /**
     * Object:项目动态资金付款计划分录's 签约情况property 
     */
    public com.kingdee.eas.fdc.finance.SignStateEnum getSignState()
    {
        return com.kingdee.eas.fdc.finance.SignStateEnum.getEnum(getString("signState"));
    }
    public void setSignState(com.kingdee.eas.fdc.finance.SignStateEnum item)
    {
		if (item != null) {
        setString("signState", item.getValue());
		}
    }
    /**
     * Object:项目动态资金付款计划分录's 总金额property 
     */
    public java.math.BigDecimal getTotalAmount()
    {
        return getBigDecimal("totalAmount");
    }
    public void setTotalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("totalAmount", item);
    }
    /**
     * Object: 项目动态资金付款计划分录 's 明细分录 property 
     */
    public com.kingdee.eas.fdc.finance.ProjectDynaticFundPayPlanDetailEntryCollection getDetailEntry()
    {
        return (com.kingdee.eas.fdc.finance.ProjectDynaticFundPayPlanDetailEntryCollection)get("detailEntry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7A4BD682");
    }
}