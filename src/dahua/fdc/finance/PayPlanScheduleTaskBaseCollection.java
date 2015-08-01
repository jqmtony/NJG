package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PayPlanScheduleTaskBaseCollection extends AbstractObjectCollection 
{
    public PayPlanScheduleTaskBaseCollection()
    {
        super(PayPlanScheduleTaskBaseInfo.class);
    }
    public boolean add(PayPlanScheduleTaskBaseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PayPlanScheduleTaskBaseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PayPlanScheduleTaskBaseInfo item)
    {
        return removeObject(item);
    }
    public PayPlanScheduleTaskBaseInfo get(int index)
    {
        return(PayPlanScheduleTaskBaseInfo)getObject(index);
    }
    public PayPlanScheduleTaskBaseInfo get(Object key)
    {
        return(PayPlanScheduleTaskBaseInfo)getObject(key);
    }
    public void set(int index, PayPlanScheduleTaskBaseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PayPlanScheduleTaskBaseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PayPlanScheduleTaskBaseInfo item)
    {
        return super.indexOf(item);
    }
}