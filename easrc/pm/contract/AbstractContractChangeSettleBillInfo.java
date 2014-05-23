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
     * Object:���ǩ֤ȷ�ϵ�'s �Ƿ������ʩ��property 
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
     * Object:���ǩ֤ȷ�ϵ�'s ���ɷ�ʽproperty 
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
     * Object:���ǩ֤ȷ�ϵ�'s ���½��property 
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
     * Object:���ǩ֤ȷ�ϵ�'s �䶯ԭ��˵��property 
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
     * Object:���ǩ֤ȷ�ϵ�'s ����������property 
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
     * Object:���ǩ֤ȷ�ϵ�'s �������property 
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
     * Object: ���ǩ֤ȷ�ϵ� 's ��¼ property 
     */
    public com.kingdee.eas.port.pm.contract.ContractChangeSettleBillEntryCollection getEntrys()
    {
        return (com.kingdee.eas.port.pm.contract.ContractChangeSettleBillEntryCollection)get("entrys");
    }
    /**
     * Object: ���ǩ֤ȷ�ϵ� 's �а���λ property 
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
     * Object: ���ǩ֤ȷ�ϵ� 's ��ͬ property 
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
     * Object:���ǩ֤ȷ�ϵ�'s ʩ����������property 
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
     * Object:���ǩ֤ȷ�ϵ�'s �����󶨽��property 
     */
    public java.math.BigDecimal getAllowAmount()
    {
        return getBigDecimal("allowAmount");
    }
    public void setAllowAmount(java.math.BigDecimal item)
    {
        setBigDecimal("allowAmount", item);
    }
    /**
     * Object: ���ǩ֤ȷ�ϵ� 's ���ָ� property 
     */
    public com.kingdee.eas.fdc.contract.ContractChangeBillInfo getConChangeBill()
    {
        return (com.kingdee.eas.fdc.contract.ContractChangeBillInfo)get("conChangeBill");
    }
    public void setConChangeBill(com.kingdee.eas.fdc.contract.ContractChangeBillInfo item)
    {
        put("conChangeBill", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("442D6414");
    }
}