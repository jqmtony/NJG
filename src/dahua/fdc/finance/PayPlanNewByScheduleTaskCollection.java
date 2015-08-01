package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PayPlanNewByScheduleTaskCollection extends AbstractObjectCollection 
{
    public PayPlanNewByScheduleTaskCollection()
    {
        super(PayPlanNewByScheduleTaskInfo.class);
    }
    public boolean add(PayPlanNewByScheduleTaskInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PayPlanNewByScheduleTaskCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PayPlanNewByScheduleTaskInfo item)
    {
        return removeObject(item);
    }
    public PayPlanNewByScheduleTaskInfo get(int index)
    {
        return(PayPlanNewByScheduleTaskInfo)getObject(index);
    }
    public PayPlanNewByScheduleTaskInfo get(Object key)
    {
        return(PayPlanNewByScheduleTaskInfo)getObject(key);
    }
    public void set(int index, PayPlanNewByScheduleTaskInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PayPlanNewByScheduleTaskInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PayPlanNewByScheduleTaskInfo item)
    {
        return super.indexOf(item);
    }
}