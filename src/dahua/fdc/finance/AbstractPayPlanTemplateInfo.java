package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPayPlanTemplateInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractPayPlanTemplateInfo()
    {
        this("id");
    }
    protected AbstractPayPlanTemplateInfo(String pkField)
    {
        super(pkField);
        put("BySchedule", new com.kingdee.eas.fdc.finance.PayPlanTemplateByScheduleCollection());
    }
    /**
     * Object:����滮ģ��'s �滮ģʽproperty 
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
     * Object: ����滮ģ�� 's ���ڵ�֧����¼ property 
     */
    public com.kingdee.eas.fdc.finance.PayPlanTemplateByScheduleCollection getBySchedule()
    {
        return (com.kingdee.eas.fdc.finance.PayPlanTemplateByScheduleCollection)get("BySchedule");
    }
    /**
     * Object: ����滮ģ�� 's ��Լ�滮ģ�� property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireInfo getProgrammingTemplate()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireInfo)get("programmingTemplate");
    }
    public void setProgrammingTemplate(com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireInfo item)
    {
        put("programmingTemplate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E60DA69C");
    }
}