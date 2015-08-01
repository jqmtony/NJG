package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PayPlanNewByScheduleTaskNameCollection extends AbstractObjectCollection 
{
    public PayPlanNewByScheduleTaskNameCollection()
    {
        super(PayPlanNewByScheduleTaskNameInfo.class);
    }
    public boolean add(PayPlanNewByScheduleTaskNameInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PayPlanNewByScheduleTaskNameCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PayPlanNewByScheduleTaskNameInfo item)
    {
        return removeObject(item);
    }
    public PayPlanNewByScheduleTaskNameInfo get(int index)
    {
        return(PayPlanNewByScheduleTaskNameInfo)getObject(index);
    }
    public PayPlanNewByScheduleTaskNameInfo get(Object key)
    {
        return(PayPlanNewByScheduleTaskNameInfo)getObject(key);
    }
    public void set(int index, PayPlanNewByScheduleTaskNameInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PayPlanNewByScheduleTaskNameInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PayPlanNewByScheduleTaskNameInfo item)
    {
        return super.indexOf(item);
    }
}