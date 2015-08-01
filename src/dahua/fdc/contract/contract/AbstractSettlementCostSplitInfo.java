package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSettlementCostSplitInfo extends com.kingdee.eas.fdc.basedata.FDCSplitBillInfo implements Serializable 
{
    public AbstractSettlementCostSplitInfo()
    {
        this("id");
    }
    protected AbstractSettlementCostSplitInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.contract.SettlementCostSplitEntryCollection());
    }
    /**
     * Object: 结算单拆分 's 分录 property 
     */
    public com.kingdee.eas.fdc.contract.SettlementCostSplitEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.contract.SettlementCostSplitEntryCollection)get("entrys");
    }
    /**
     * Object: 结算单拆分 's 结算单 property 
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
     * Object:结算单拆分's 归属保修金金额property 
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
     * Object:结算单拆分's 是否调整前property 
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
        return new BOSObjectType("BE73CE09");
    }
}