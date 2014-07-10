package com.kingdee.eas.port.pm.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractChangeSettleBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractContractChangeSettleBillInfo()
    {
        this("id");
    }
    protected AbstractContractChangeSettleBillInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.port.pm.contract.ContractChangeSettleBillEntryCollection());
    }
    /**
     * Object:变更签证确认单's 是否已完成施工property 
     */
    public boolean isIsFinish()
    {
        return getBoolean("isFinish");
    }
    public void setIsFinish(boolean item)
    {
        setBoolean("isFinish", item);
    }
    /**
     * Object:变更签证确认单's 包干方式property 
     */
    public com.kingdee.eas.fdc.contract.ResponsibleStyleEnum getResponsibleStyle()
    {
        return com.kingdee.eas.fdc.contract.ResponsibleStyleEnum.getEnum(getString("responsibleStyle"));
    }
    public void setResponsibleStyle(com.kingdee.eas.fdc.contract.ResponsibleStyleEnum item)
    {
		if (item != null) {
        setString("responsibleStyle", item.getValue());
		}
    }
    /**
     * Object:变更签证确认单's 最新金额property 
     */
    public java.math.BigDecimal getLastAmount()
    {
        return getBigDecimal("lastAmount");
    }
    public void setLastAmount(java.math.BigDecimal item)
    {
        setBigDecimal("lastAmount", item);
    }
    /**
     * Object:变更签证确认单's 变动原因说明property 
     */
    public String getReasonDescription()
    {
        return getString("reasonDescription");
    }
    public void setReasonDescription(String item)
    {
        setString("reasonDescription", item);
    }
    /**
     * Object:变更签证确认单's 完成情况描叙property 
     */
    public String getColseDescription()
    {
        return getString("colseDescription");
    }
    public void setColseDescription(String item)
    {
        setString("colseDescription", item);
    }
    /**
     * Object:变更签证确认单's 变更内容property 
     */
    public String getChangeReson()
    {
        return getString("changeReson");
    }
    public void setChangeReson(String item)
    {
        setString("changeReson", item);
    }
    /**
     * Object: 变更签证确认单 's 分录 property 
     */
    public com.kingdee.eas.port.pm.contract.ContractChangeSettleBillEntryCollection getEntrys()
    {
        return (com.kingdee.eas.port.pm.contract.ContractChangeSettleBillEntryCollection)get("entrys");
    }
    /**
     * Object: 变更签证确认单 's 承包单位 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getSupplier()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("supplier");
    }
    public void setSupplier(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("supplier", item);
    }
    /**
     * Object: 变更签证确认单 's 合同 property 
     */
    public com.kingdee.eas.port.pm.contract.ContractBillInfo getContractBill()
    {
        return (com.kingdee.eas.port.pm.contract.ContractBillInfo)get("contractBill");
    }
    public void setContractBill(com.kingdee.eas.port.pm.contract.ContractBillInfo item)
    {
        put("contractBill", item);
    }
    /**
     * Object:变更签证确认单's 施工方报审金额property 
     */
    public java.math.BigDecimal getReportAmount()
    {
        return getBigDecimal("reportAmount");
    }
    public void setReportAmount(java.math.BigDecimal item)
    {
        setBigDecimal("reportAmount", item);
    }
    /**
     * Object:变更签证确认单's 最终审定金额property 
     */
    public java.math.BigDecimal getAllowAmount()
    {
        return getBigDecimal("allowAmount");
    }
    public void setAllowAmount(java.math.BigDecimal item)
    {
        setBigDecimal("allowAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("442D6414");
    }
}