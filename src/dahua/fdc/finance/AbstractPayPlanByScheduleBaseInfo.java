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
     * Object: �ڵ㸶��滮���� 's �������� property 
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
     * Object:�ڵ㸶��滮����'s ���㷽ʽproperty 
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
     * Object:�ڵ㸶��滮����'s Լ��֧������property 
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
     * Object: �ڵ㸶��滮���� 's �ڵ�����ģ�� property 
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
     * Object:�ڵ㸶��滮����'s �����׼property 
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
     * Object:�ڵ㸶��滮����'s �ӳ�֧������property 
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
     * Object: �ڵ㸶��滮���� 's ������Ŀ property 
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
     * Object:�ڵ㸶��滮����'s Ԥ��������ʽproperty 
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
     * Object:�ڵ㸶��滮����'s ��Դproperty 
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
     * Object:�ڵ㸶��滮����'s �Ƿ��¥��property 
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