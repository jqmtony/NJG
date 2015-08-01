package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PaymentLayoutPlamCollection extends AbstractObjectCollection 
{
    public PaymentLayoutPlamCollection()
    {
        super(PaymentLayoutPlamInfo.class);
    }
    public boolean add(PaymentLayoutPlamInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PaymentLayoutPlamCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PaymentLayoutPlamInfo item)
    {
        return removeObject(item);
    }
    public PaymentLayoutPlamInfo get(int index)
    {
        return(PaymentLayoutPlamInfo)getObject(index);
    }
    public PaymentLayoutPlamInfo get(Object key)
    {
        return(PaymentLayoutPlamInfo)getObject(key);
    }
    public void set(int index, PaymentLayoutPlamInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PaymentLayoutPlamInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PaymentLayoutPlamInfo item)
    {
        return super.indexOf(item);
    }
}