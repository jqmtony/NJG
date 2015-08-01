package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PayPlanCycleCollection extends AbstractObjectCollection 
{
    public PayPlanCycleCollection()
    {
        super(PayPlanCycleInfo.class);
    }
    public boolean add(PayPlanCycleInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PayPlanCycleCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PayPlanCycleInfo item)
    {
        return removeObject(item);
    }
    public PayPlanCycleInfo get(int index)
    {
        return(PayPlanCycleInfo)getObject(index);
    }
    public PayPlanCycleInfo get(Object key)
    {
        return(PayPlanCycleInfo)getObject(key);
    }
    public void set(int index, PayPlanCycleInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PayPlanCycleInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PayPlanCycleInfo item)
    {
        return super.indexOf(item);
    }
}