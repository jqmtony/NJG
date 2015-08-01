package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDynamicCostMonitorContractEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractDynamicCostMonitorContractEntryInfo()
    {
        this("id");
    }
    protected AbstractDynamicCostMonitorContractEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 动态成本监控——合同分录 's 动态成本监控单据头 property 
     */
    public com.kingdee.eas.fdc.aimcost.DynamicCostMonitorInfo getParent()
    {
        return (com.kingdee.eas.fdc.aimcost.DynamicCostMonitorInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.aimcost.DynamicCostMonitorInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 动态成本监控——合同分录 's 合同单据 property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getContractbill()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("contractbill");
    }
    public void setContractbill(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("contractbill", item);
    }
    /**
     * Object:动态成本监控——合同分录's 预计待发生金额property 
     */
    public java.math.BigDecimal getEnxpectedToHappenAmt()
    {
        return getBigDecimal("enxpectedToHappenAmt");
    }
    public void setEnxpectedToHappenAmt(java.math.BigDecimal item)
    {
        setBigDecimal("enxpectedToHappenAmt", item);
    }
    /**
     * Object:动态成本监控——合同分录's 原因分析property 
     */
    public String getReason()
    {
        return getString("reason");
    }
    public void setReason(String item)
    {
        setString("reason", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3A8F15DB");
    }
}