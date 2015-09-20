package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractForecastChangeVisInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractForecastChangeVisInfo()
    {
        this("id");
    }
    protected AbstractForecastChangeVisInfo(String pkField)
    {
        super(pkField);
        put("splitEntry", new com.kingdee.eas.fdc.aimcost.ForecastChangeVisSplitEntryCollection());
        put("entrys", new com.kingdee.eas.fdc.aimcost.ForecastChangeVisEntryCollection());
    }
    /**
     * Object: Ԥ�����ǩ֤�� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.aimcost.ForecastChangeVisEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.aimcost.ForecastChangeVisEntryCollection)get("entrys");
    }
    /**
     * Object:Ԥ�����ǩ֤��'s �Ƿ�����ƾ֤property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    /**
     * Object: Ԥ�����ǩ֤�� 's ��ͬ���� property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getContractNumber()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("contractNumber");
    }
    public void setContractNumber(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("contractNumber", item);
    }
    /**
     * Object:Ԥ�����ǩ֤��'s ��ͬ����property 
     */
    public String getContractName()
    {
        return getString("contractName");
    }
    public void setContractName(String item)
    {
        setString("contractName", item);
    }
    /**
     * Object:Ԥ�����ǩ֤��'s �汾��property 
     */
    public String getVersion()
    {
        return getString("version");
    }
    public void setVersion(String item)
    {
        setString("version", item);
    }
    /**
     * Object:Ԥ�����ǩ֤��'s Ԥ�����ǩ֤���property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object:Ԥ�����ǩ֤��'s ��עproperty 
     */
    public String getRemake()
    {
        return getString("remake");
    }
    public void setRemake(String item)
    {
        setString("remake", item);
    }
    /**
     * Object: Ԥ�����ǩ֤�� 's ��ַ�¼ property 
     */
    public com.kingdee.eas.fdc.aimcost.ForecastChangeVisSplitEntryCollection getSplitEntry()
    {
        return (com.kingdee.eas.fdc.aimcost.ForecastChangeVisSplitEntryCollection)get("splitEntry");
    }
    /**
     * Object:Ԥ�����ǩ֤��'s �������property 
     */
    public java.util.Date getAuditDate()
    {
        return getDate("auditDate");
    }
    public void setAuditDate(java.util.Date item)
    {
        setDate("auditDate", item);
    }
    /**
     * Object:Ԥ�����ǩ֤��'s ����״̬property 
     */
    public com.kingdee.eas.fdc.basedata.FDCBillStateEnum getStatus()
    {
        return com.kingdee.eas.fdc.basedata.FDCBillStateEnum.getEnum(getString("status"));
    }
    public void setStatus(com.kingdee.eas.fdc.basedata.FDCBillStateEnum item)
    {
		if (item != null) {
        setString("status", item.getValue());
		}
    }
    /**
     * Object:Ԥ�����ǩ֤��'s �Ƿ����°�property 
     */
    public boolean isIsLast()
    {
        return getBoolean("isLast");
    }
    public void setIsLast(boolean item)
    {
        setBoolean("isLast", item);
    }
    /**
     * Object:Ԥ�����ǩ֤��'s ��ͬ���property 
     */
    public java.math.BigDecimal getContractAmount()
    {
        return getBigDecimal("contractAmount");
    }
    public void setContractAmount(java.math.BigDecimal item)
    {
        setBigDecimal("contractAmount", item);
    }
    /**
     * Object:Ԥ�����ǩ֤��'s ��ֽ��property 
     */
    public java.math.BigDecimal getSplitedAmount()
    {
        return getBigDecimal("SplitedAmount");
    }
    public void setSplitedAmount(java.math.BigDecimal item)
    {
        setBigDecimal("SplitedAmount", item);
    }
    /**
     * Object:Ԥ�����ǩ֤��'s δ��ֽ��property 
     */
    public java.math.BigDecimal getUnSplitAmount()
    {
        return getBigDecimal("UnSplitAmount");
    }
    public void setUnSplitAmount(java.math.BigDecimal item)
    {
        setBigDecimal("UnSplitAmount", item);
    }
    /**
     * Object:Ԥ�����ǩ֤��'s ������property 
     */
    public boolean isBanZreo()
    {
        return getBoolean("banZreo");
    }
    public void setBanZreo(boolean item)
    {
        setBoolean("banZreo", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B4926E1E");
    }
}