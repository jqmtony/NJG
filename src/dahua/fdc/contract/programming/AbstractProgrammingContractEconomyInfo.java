package com.kingdee.eas.fdc.contract.programming;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProgrammingContractEconomyInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractProgrammingContractEconomyInfo()
    {
        this("id");
    }
    protected AbstractProgrammingContractEconomyInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �������� 's ��ܺ�Լ  property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo getContract()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo)get("contract");
    }
    public void setContract(com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo item)
    {
        put("contract", item);
    }
    /**
     * Object: �������� 's �������� property 
     */
    public com.kingdee.eas.fdc.basedata.PaymentTypeInfo getPaymentType()
    {
        return (com.kingdee.eas.fdc.basedata.PaymentTypeInfo)get("paymentType");
    }
    public void setPaymentType(com.kingdee.eas.fdc.basedata.PaymentTypeInfo item)
    {
        put("paymentType", item);
    }
    /**
     * Object:��������'s �������property 
     */
    public java.math.BigDecimal getScale()
    {
        return getBigDecimal("scale");
    }
    public void setScale(java.math.BigDecimal item)
    {
        setBigDecimal("scale", item);
    }
    /**
     * Object:��������'s ������property 
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
     * Object:��������'s ��������property 
     */
    public String getCondition()
    {
        return getString("condition");
    }
    public void setCondition(String item)
    {
        setString("condition", item);
    }
    /**
     * Object:��������'s ��������property 
     */
    public java.sql.Timestamp getPaymentDate()
    {
        return getTimestamp("paymentDate");
    }
    public void setPaymentDate(java.sql.Timestamp item)
    {
        setTimestamp("paymentDate", item);
    }
    /**
     * Object:��������'s ��עproperty 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    /**
     * Object:��������'s �ƻ��������property 
     */
    public java.util.Date getCompletedate()
    {
        return getDate("completedate");
    }
    public void setCompletedate(java.util.Date item)
    {
        setDate("completedate", item);
    }
    /**
     * Object:��������'s �ӳ�����property 
     */
    public int getDelaydays()
    {
        return getInt("delaydays");
    }
    public void setDelaydays(int item)
    {
        setInt("delaydays", item);
    }
    /**
     * Object: �������� 's ��������� property 
     */
    public com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo getScheduletask()
    {
        return (com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo)get("scheduletask");
    }
    public void setScheduletask(com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo item)
    {
        put("scheduletask", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("144467E3");
    }
}