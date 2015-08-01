package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PayPlanByMonthBaseCollection extends AbstractObjectCollection 
{
    public PayPlanByMonthBaseCollection()
    {
        super(PayPlanByMonthBaseInfo.class);
    }
    public boolean add(PayPlanByMonthBaseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PayPlanByMonthBaseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PayPlanByMonthBaseInfo item)
    {
        return removeObject(item);
    }
    public PayPlanByMonthBaseInfo get(int index)
    {
        return(PayPlanByMonthBaseInfo)getObject(index);
    }
    public PayPlanByMonthBaseInfo get(Object key)
    {
        return(PayPlanByMonthBaseInfo)getObject(key);
    }
    public void set(int index, PayPlanByMonthBaseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PayPlanByMonthBaseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PayPlanByMonthBaseInfo item)
    {
        return super.indexOf(item);
    }
}