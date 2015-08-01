package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PayPlanNewDataCollection extends AbstractObjectCollection 
{
    public PayPlanNewDataCollection()
    {
        super(PayPlanNewDataInfo.class);
    }
    public boolean add(PayPlanNewDataInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PayPlanNewDataCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PayPlanNewDataInfo item)
    {
        return removeObject(item);
    }
    public PayPlanNewDataInfo get(int index)
    {
        return(PayPlanNewDataInfo)getObject(index);
    }
    public PayPlanNewDataInfo get(Object key)
    {
        return(PayPlanNewDataInfo)getObject(key);
    }
    public void set(int index, PayPlanNewDataInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PayPlanNewDataInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PayPlanNewDataInfo item)
    {
        return super.indexOf(item);
    }
}