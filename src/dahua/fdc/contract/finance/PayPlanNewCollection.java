package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PayPlanNewCollection extends AbstractObjectCollection 
{
    public PayPlanNewCollection()
    {
        super(PayPlanNewInfo.class);
    }
    public boolean add(PayPlanNewInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PayPlanNewCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PayPlanNewInfo item)
    {
        return removeObject(item);
    }
    public PayPlanNewInfo get(int index)
    {
        return(PayPlanNewInfo)getObject(index);
    }
    public PayPlanNewInfo get(Object key)
    {
        return(PayPlanNewInfo)getObject(key);
    }
    public void set(int index, PayPlanNewInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PayPlanNewInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PayPlanNewInfo item)
    {
        return super.indexOf(item);
    }
}