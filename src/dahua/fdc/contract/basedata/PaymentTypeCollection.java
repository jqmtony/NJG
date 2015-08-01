package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PaymentTypeCollection extends AbstractObjectCollection 
{
    public PaymentTypeCollection()
    {
        super(PaymentTypeInfo.class);
    }
    public boolean add(PaymentTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PaymentTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PaymentTypeInfo item)
    {
        return removeObject(item);
    }
    public PaymentTypeInfo get(int index)
    {
        return(PaymentTypeInfo)getObject(index);
    }
    public PaymentTypeInfo get(Object key)
    {
        return(PaymentTypeInfo)getObject(key);
    }
    public void set(int index, PaymentTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PaymentTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PaymentTypeInfo item)
    {
        return super.indexOf(item);
    }
}