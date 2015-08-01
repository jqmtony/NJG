package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPayPlanNewInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractPayPlanNewInfo()
    {
        this("id");
    }
    protected AbstractPayPlanNewInfo(String pkField)
    {
        super(pkField);
        put("Data", new com.kingdee.eas.fdc.finance.PayPlanNewDataCollection());
        put("Unsign", new com.kingdee.eas.fdc.finance.PayPlanNewUnsignCollection());
        put("BySchedule", new com.kingdee.eas.fdc.finance.PayPlanNewByScheduleCollection());
    }
    /**
     * Object:����滮's �滮ģʽproperty 
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
     * Object: ����滮 's ���ڵ�֧�� property 
     */
    public com.kingdee.eas.fdc.finance.PayPlanNewByScheduleCollection getBySchedule()
    {
        return (com.kingdee.eas.fdc.finance.PayPlanNewByScheduleCollection)get("BySchedule");
    }
    /**
     * Object: ����滮 's �滮ģ�� property 
     */
    public com.kingdee.eas.fdc.finance.PayPlanTemplateInfo getTemplate()
    {
        return (com.kingdee.eas.fdc.finance.PayPlanTemplateInfo)get("template");
    }
    public void setTemplate(com.kingdee.eas.fdc.finance.PayPlanTemplateInfo item)
    {
        put("template", item);
    }
    /**
     * Object: ����滮 's ��Լ�滮 property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo getProgramming()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo)get("programming");
    }
    public void setProgramming(com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo item)
    {
        put("programming", item);
    }
    /**
     * Object: ����滮 's �������� property 
     */
    public com.kingdee.eas.fdc.finance.PayPlanNewDataCollection getData()
    {
        return (com.kingdee.eas.fdc.finance.PayPlanNewDataCollection)get("Data");
    }
    /**
     * Object: ����滮 's δǩԼ��ϸ property 
     */
    public com.kingdee.eas.fdc.finance.PayPlanNewUnsignCollection getUnsign()
    {
        return (com.kingdee.eas.fdc.finance.PayPlanNewUnsignCollection)get("Unsign");
    }
    /**
     * Object:����滮's ״̬property 
     */
    public boolean isIsFinish()
    {
        return getBoolean("isFinish");
    }
    public void setIsFinish(boolean item)
    {
        setBoolean("isFinish", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A633823E");
    }
}