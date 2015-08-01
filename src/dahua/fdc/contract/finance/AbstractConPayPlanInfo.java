package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractConPayPlanInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractConPayPlanInfo()
    {
        this("id");
    }
    protected AbstractConPayPlanInfo(String pkField)
    {
        super(pkField);
        put("Data", new com.kingdee.eas.fdc.finance.ConPayPlanDataCollection());
        put("Datap", new com.kingdee.eas.fdc.finance.ConPayPlanDatapCollection());
        put("BySchedule", new com.kingdee.eas.fdc.finance.ConPayPlanByScheduleCollection());
        put("DataA", new com.kingdee.eas.fdc.finance.ConPayPlanDataACollection());
        put("Detail", new com.kingdee.eas.fdc.finance.ConPayPlanDetailCollection());
    }
    /**
     * Object: ��ͬ����滮 's ���ڵ�֧�� property 
     */
    public com.kingdee.eas.fdc.finance.ConPayPlanByScheduleCollection getBySchedule()
    {
        return (com.kingdee.eas.fdc.finance.ConPayPlanByScheduleCollection)get("BySchedule");
    }
    /**
     * Object:��ͬ����滮's ģʽproperty 
     */
    public com.kingdee.eas.fdc.finance.PayPlanModeEnum getMode()
    {
        return com.kingdee.eas.fdc.finance.PayPlanModeEnum.getEnum(getInt("mode"));
    }
    public void setMode(com.kingdee.eas.fdc.finance.PayPlanModeEnum item)
    {
		if (item != null) {
        setInt("mode", item.getValue());
		}
    }
    /**
     * Object: ��ͬ����滮 's ��ͬ����滮���� property 
     */
    public com.kingdee.eas.fdc.finance.ConPayPlanDataCollection getData()
    {
        return (com.kingdee.eas.fdc.finance.ConPayPlanDataCollection)get("Data");
    }
    /**
     * Object: ��ͬ����滮 's ��ͬ property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getContractBill()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("contractBill");
    }
    public void setContractBill(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("contractBill", item);
    }
    /**
     * Object: ��ͬ����滮 's ��Լ�滮��ϸ property 
     */
    public com.kingdee.eas.fdc.finance.ConPayPlanDetailCollection getDetail()
    {
        return (com.kingdee.eas.fdc.finance.ConPayPlanDetailCollection)get("Detail");
    }
    /**
     * Object: ��ͬ����滮 's �ƻ����� property 
     */
    public com.kingdee.eas.fdc.finance.ConPayPlanDatapCollection getDatap()
    {
        return (com.kingdee.eas.fdc.finance.ConPayPlanDatapCollection)get("Datap");
    }
    /**
     * Object: ��ͬ����滮 's ʵ������ property 
     */
    public com.kingdee.eas.fdc.finance.ConPayPlanDataACollection getDataA()
    {
        return (com.kingdee.eas.fdc.finance.ConPayPlanDataACollection)get("DataA");
    }
    /**
     * Object:��ͬ����滮's �½��·�property 
     */
    public java.util.Date getSettleMonth()
    {
        return getDate("settleMonth");
    }
    public void setSettleMonth(java.util.Date item)
    {
        setDate("settleMonth", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D2099A5E");
    }
}