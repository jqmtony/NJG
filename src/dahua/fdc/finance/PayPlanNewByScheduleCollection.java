package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PayPlanNewByScheduleCollection extends AbstractObjectCollection 
{
    public PayPlanNewByScheduleCollection()
    {
        super(PayPlanNewByScheduleInfo.class);
    }
    public boolean add(PayPlanNewByScheduleInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PayPlanNewByScheduleCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PayPlanNewByScheduleInfo item)
    {
        return removeObject(item);
    }
    public PayPlanNewByScheduleInfo get(int index)
    {
        return(PayPlanNewByScheduleInfo)getObject(index);
    }
    public PayPlanNewByScheduleInfo get(Object key)
    {
        return(PayPlanNewByScheduleInfo)getObject(key);
    }
    public void set(int index, PayPlanNewByScheduleInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PayPlanNewByScheduleInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PayPlanNewByScheduleInfo item)
    {
        return super.indexOf(item);
    }
}