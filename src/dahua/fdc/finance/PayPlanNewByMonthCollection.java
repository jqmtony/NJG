package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PayPlanNewByMonthCollection extends AbstractObjectCollection 
{
    public PayPlanNewByMonthCollection()
    {
        super(PayPlanNewByMonthInfo.class);
    }
    public boolean add(PayPlanNewByMonthInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PayPlanNewByMonthCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PayPlanNewByMonthInfo item)
    {
        return removeObject(item);
    }
    public PayPlanNewByMonthInfo get(int index)
    {
        return(PayPlanNewByMonthInfo)getObject(index);
    }
    public PayPlanNewByMonthInfo get(Object key)
    {
        return(PayPlanNewByMonthInfo)getObject(key);
    }
    public void set(int index, PayPlanNewByMonthInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PayPlanNewByMonthInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PayPlanNewByMonthInfo item)
    {
        return super.indexOf(item);
    }
}