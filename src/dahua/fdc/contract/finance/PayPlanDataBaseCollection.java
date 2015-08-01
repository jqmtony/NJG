package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PayPlanDataBaseCollection extends AbstractObjectCollection 
{
    public PayPlanDataBaseCollection()
    {
        super(PayPlanDataBaseInfo.class);
    }
    public boolean add(PayPlanDataBaseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PayPlanDataBaseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PayPlanDataBaseInfo item)
    {
        return removeObject(item);
    }
    public PayPlanDataBaseInfo get(int index)
    {
        return(PayPlanDataBaseInfo)getObject(index);
    }
    public PayPlanDataBaseInfo get(Object key)
    {
        return(PayPlanDataBaseInfo)getObject(key);
    }
    public void set(int index, PayPlanDataBaseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PayPlanDataBaseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PayPlanDataBaseInfo item)
    {
        return super.indexOf(item);
    }
}