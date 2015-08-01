package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PaymentLayoutPlanMingxiCollection extends AbstractObjectCollection 
{
    public PaymentLayoutPlanMingxiCollection()
    {
        super(PaymentLayoutPlanMingxiInfo.class);
    }
    public boolean add(PaymentLayoutPlanMingxiInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PaymentLayoutPlanMingxiCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PaymentLayoutPlanMingxiInfo item)
    {
        return removeObject(item);
    }
    public PaymentLayoutPlanMingxiInfo get(int index)
    {
        return(PaymentLayoutPlanMingxiInfo)getObject(index);
    }
    public PaymentLayoutPlanMingxiInfo get(Object key)
    {
        return(PaymentLayoutPlanMingxiInfo)getObject(key);
    }
    public void set(int index, PaymentLayoutPlanMingxiInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PaymentLayoutPlanMingxiInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PaymentLayoutPlanMingxiInfo item)
    {
        return super.indexOf(item);
    }
}