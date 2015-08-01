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
     * Object: ��̬�ɱ���ء�����ͬ��¼ 's ��̬�ɱ���ص���ͷ property 
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
     * Object: ��̬�ɱ���ء�����ͬ��¼ 's ��ͬ���� property 
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
     * Object:��̬�ɱ���ء�����ͬ��¼'s Ԥ�ƴ��������property 
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
     * Object:��̬�ɱ���ء�����ͬ��¼'s ԭ�����property 
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