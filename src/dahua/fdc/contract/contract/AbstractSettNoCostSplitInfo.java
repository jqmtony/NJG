package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSettNoCostSplitInfo extends com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillInfo implements Serializable 
{
    public AbstractSettNoCostSplitInfo()
    {
        this("id");
    }
    protected AbstractSettNoCostSplitInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.contract.SettNoCostSplitEntryCollection());
    }
    /**
     * Object: 结算非成本拆分 's 非成本拆分分录 property 
     */
    public com.kingdee.eas.fdc.contract.SettNoCostSplitEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.contract.SettNoCostSplitEntryCollection)get("entrys");
    }
    /**
     * Object: 结算非成本拆分 's 结算单 property 
     */
    public com.kingdee.eas.fdc.contract.ContractSettlementBillInfo getSettlementBill()
    {
        return (com.kingdee.eas.fdc.contract.ContractSettlementBillInfo)get("settlementBill");
    }
    public void setSettlementBill(com.kingdee.eas.fdc.contract.ContractSettlementBillInfo item)
    {
        put("settlementBill", item);
    }
    /**
     * Object:结算非成本拆分's 保修金金额property 
     */
    public java.math.BigDecimal getGrtSplitAmt()
    {
        return getBigDecimal("grtSplitAmt");
    }
    public void setGrtSplitAmt(java.math.BigDecimal item)
    {
        setBigDecimal("grtSplitAmt", item);
    }
    /**
     * Object:结算非成本拆分's 是否调整前property 
     */
    public boolean isIsBeforeAdjust()
    {
        return getBoolean("isBeforeAdjust");
    }
    public void setIsBeforeAdjust(boolean item)
    {
        setBoolean("isBeforeAdjust", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2E637F3F");
    }
}