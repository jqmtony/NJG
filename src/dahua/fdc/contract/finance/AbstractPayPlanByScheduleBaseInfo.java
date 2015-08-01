package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPayPlanByScheduleBaseInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractPayPlanByScheduleBaseInfo()
    {
        this("id");
    }
    protected AbstractPayPlanByScheduleBaseInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 节点付款规划基类 's 付款类型 property 
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
     * Object:节点付款规划基类's 计算方式property 
     */
    public com.kingdee.eas.fdc.finance.CalTypeEnum getCalType()
    {
        return com.kingdee.eas.fdc.finance.CalTypeEnum.getEnum(getInt("calType"));
    }
    public void setCalType(com.kingdee.eas.fdc.finance.CalTypeEnum item)
    {
		if (item != null) {
        setInt("calType", item.getValue());
		}
    }
    /**
     * Object:节点付款规划基类's 约定支付比例property 
     */
    public java.math.BigDecimal getPayScale()
    {
        return getBigDecimal("payScale");
    }
    public void setPayScale(java.math.BigDecimal item)
    {
        setBigDecimal("payScale", item);
    }
    /**
     * Object: 节点付款规划基类 's 节点任务模板 property 
     */
    public com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo getTemplateTask()
    {
        return (com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo)get("templateTask");
    }
    public void setTemplateTask(com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo item)
    {
        put("templateTask", item);
    }
    /**
     * Object:节点付款规划基类's 计算基准property 
     */
    public com.kingdee.eas.fdc.finance.CalStandardEnum getCalStandard()
    {
        return com.kingdee.eas.fdc.finance.CalStandardEnum.getEnum(getInt("calStandard"));
    }
    public void setCalStandard(com.kingdee.eas.fdc.finance.CalStandardEnum item)
    {
		if (item != null) {
        setInt("calStandard", item.getValue());
		}
    }
    /**
     * Object:节点付款规划基类's 延迟支付天数property 
     */
    public int getDelayDay()
    {
        return getInt("delayDay");
    }
    public void setDelayDay(int item)
    {
        setInt("delayDay", item);
    }
    /**
     * Object: 节点付款规划基类 's 关联科目 property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountInfo getCostAccount()
    {
        return (com.kingdee.eas.fdc.basedata.CostAccountInfo)get("costAccount");
    }
    public void setCostAccount(com.kingdee.eas.fdc.basedata.CostAccountInfo item)
    {
        put("costAccount", item);
    }
    /**
     * Object:节点付款规划基类's 预付冲销方式property 
     */
    public com.kingdee.eas.fdc.finance.PrepayWriteOffEnum getWriteOffType()
    {
        return com.kingdee.eas.fdc.finance.PrepayWriteOffEnum.getEnum(getInt("writeOffType"));
    }
    public void setWriteOffType(com.kingdee.eas.fdc.finance.PrepayWriteOffEnum item)
    {
		if (item != null) {
        setInt("writeOffType", item.getValue());
		}
    }
    /**
     * Object:节点付款规划基类's 来源property 
     */
    public com.kingdee.bos.util.BOSUuid getSrcID()
    {
        return getBOSUuid("srcID");
    }
    public void setSrcID(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("srcID", item);
    }
    /**
     * Object:节点付款规划基类's 是否绑定楼栋property 
     */
    public boolean isIsBindBuilding()
    {
        return getBoolean("isBindBuilding");
    }
    public void setIsBindBuilding(boolean item)
    {
        setBoolean("isBindBuilding", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6638B8A1");
    }
}