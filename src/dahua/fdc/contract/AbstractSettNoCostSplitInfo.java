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
     * Object: ����ǳɱ���� 's �ǳɱ���ַ�¼ property 
     */
    public com.kingdee.eas.fdc.contract.SettNoCostSplitEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.contract.SettNoCostSplitEntryCollection)get("entrys");
    }
    /**
     * Object: ����ǳɱ���� 's ���㵥 property 
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
     * Object:����ǳɱ����'s ���޽���property 
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
     * Object:����ǳɱ����'s �Ƿ����ǰproperty 
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