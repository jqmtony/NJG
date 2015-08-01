package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PayPlanTemplateByMonthCollection extends AbstractObjectCollection 
{
    public PayPlanTemplateByMonthCollection()
    {
        super(PayPlanTemplateByMonthInfo.class);
    }
    public boolean add(PayPlanTemplateByMonthInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PayPlanTemplateByMonthCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PayPlanTemplateByMonthInfo item)
    {
        return removeObject(item);
    }
    public PayPlanTemplateByMonthInfo get(int index)
    {
        return(PayPlanTemplateByMonthInfo)getObject(index);
    }
    public PayPlanTemplateByMonthInfo get(Object key)
    {
        return(PayPlanTemplateByMonthInfo)getObject(key);
    }
    public void set(int index, PayPlanTemplateByMonthInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PayPlanTemplateByMonthInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PayPlanTemplateByMonthInfo item)
    {
        return super.indexOf(item);
    }
}