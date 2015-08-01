package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PayPlanByScheduleBaseCollection extends AbstractObjectCollection 
{
    public PayPlanByScheduleBaseCollection()
    {
        super(PayPlanByScheduleBaseInfo.class);
    }
    public boolean add(PayPlanByScheduleBaseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PayPlanByScheduleBaseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PayPlanByScheduleBaseInfo item)
    {
        return removeObject(item);
    }
    public PayPlanByScheduleBaseInfo get(int index)
    {
        return(PayPlanByScheduleBaseInfo)getObject(index);
    }
    public PayPlanByScheduleBaseInfo get(Object key)
    {
        return(PayPlanByScheduleBaseInfo)getObject(key);
    }
    public void set(int index, PayPlanByScheduleBaseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PayPlanByScheduleBaseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PayPlanByScheduleBaseInfo item)
    {
        return super.indexOf(item);
    }
}