package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PayPlanNewByScheduleDatazCollection extends AbstractObjectCollection 
{
    public PayPlanNewByScheduleDatazCollection()
    {
        super(PayPlanNewByScheduleDatazInfo.class);
    }
    public boolean add(PayPlanNewByScheduleDatazInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PayPlanNewByScheduleDatazCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PayPlanNewByScheduleDatazInfo item)
    {
        return removeObject(item);
    }
    public PayPlanNewByScheduleDatazInfo get(int index)
    {
        return(PayPlanNewByScheduleDatazInfo)getObject(index);
    }
    public PayPlanNewByScheduleDatazInfo get(Object key)
    {
        return(PayPlanNewByScheduleDatazInfo)getObject(key);
    }
    public void set(int index, PayPlanNewByScheduleDatazInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PayPlanNewByScheduleDatazInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PayPlanNewByScheduleDatazInfo item)
    {
        return super.indexOf(item);
    }
}