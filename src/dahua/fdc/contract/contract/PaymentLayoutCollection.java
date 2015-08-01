package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PaymentLayoutCollection extends AbstractObjectCollection 
{
    public PaymentLayoutCollection()
    {
        super(PaymentLayoutInfo.class);
    }
    public boolean add(PaymentLayoutInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PaymentLayoutCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PaymentLayoutInfo item)
    {
        return removeObject(item);
    }
    public PaymentLayoutInfo get(int index)
    {
        return(PaymentLayoutInfo)getObject(index);
    }
    public PaymentLayoutInfo get(Object key)
    {
        return(PaymentLayoutInfo)getObject(key);
    }
    public void set(int index, PaymentLayoutInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PaymentLayoutInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PaymentLayoutInfo item)
    {
        return super.indexOf(item);
    }
}