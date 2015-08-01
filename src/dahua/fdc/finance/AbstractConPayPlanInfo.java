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
     * Object: 合同付款规划 's 按节点支付 property 
     */
    public com.kingdee.eas.fdc.finance.ConPayPlanByScheduleCollection getBySchedule()
    {
        return (com.kingdee.eas.fdc.finance.ConPayPlanByScheduleCollection)get("BySchedule");
    }
    /**
     * Object:合同付款规划's 模式property 
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
     * Object: 合同付款规划 's 合同付款规划数据 property 
     */
    public com.kingdee.eas.fdc.finance.ConPayPlanDataCollection getData()
    {
        return (com.kingdee.eas.fdc.finance.ConPayPlanDataCollection)get("Data");
    }
    /**
     * Object: 合同付款规划 's 合同 property 
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
     * Object: 合同付款规划 's 合约规划明细 property 
     */
    public com.kingdee.eas.fdc.finance.ConPayPlanDetailCollection getDetail()
    {
        return (com.kingdee.eas.fdc.finance.ConPayPlanDetailCollection)get("Detail");
    }
    /**
     * Object: 合同付款规划 's 计划数据 property 
     */
    public com.kingdee.eas.fdc.finance.ConPayPlanDatapCollection getDatap()
    {
        return (com.kingdee.eas.fdc.finance.ConPayPlanDatapCollection)get("Datap");
    }
    /**
     * Object: 合同付款规划 's 实际数据 property 
     */
    public com.kingdee.eas.fdc.finance.ConPayPlanDataACollection getDataA()
    {
        return (com.kingdee.eas.fdc.finance.ConPayPlanDataACollection)get("DataA");
    }
    /**
     * Object:合同付款规划's 月结月份property 
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