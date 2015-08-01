package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PayPlanTemplateByScheduleCollection extends AbstractObjectCollection 
{
    public PayPlanTemplateByScheduleCollection()
    {
        super(PayPlanTemplateByScheduleInfo.class);
    }
    public boolean add(PayPlanTemplateByScheduleInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PayPlanTemplateByScheduleCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PayPlanTemplateByScheduleInfo item)
    {
        return removeObject(item);
    }
    public PayPlanTemplateByScheduleInfo get(int index)
    {
        return(PayPlanTemplateByScheduleInfo)getObject(index);
    }
    public PayPlanTemplateByScheduleInfo get(Object key)
    {
        return(PayPlanTemplateByScheduleInfo)getObject(key);
    }
    public void set(int index, PayPlanTemplateByScheduleInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PayPlanTemplateByScheduleInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PayPlanTemplateByScheduleInfo item)
    {
        return super.indexOf(item);
    }
}