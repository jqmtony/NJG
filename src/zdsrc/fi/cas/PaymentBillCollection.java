package com.kingdee.eas.fi.cas;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PaymentBillCollection extends AbstractObjectCollection 
{
    public PaymentBillCollection()
    {
        super(PaymentBillInfo.class);
    }
    public boolean add(PaymentBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PaymentBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PaymentBillInfo item)
    {
        return removeObject(item);
    }
    public PaymentBillInfo get(int index)
    {
        return(PaymentBillInfo)getObject(index);
    }
    public PaymentBillInfo get(Object key)
    {
        return(PaymentBillInfo)getObject(key);
    }
    public void set(int index, PaymentBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PaymentBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PaymentBillInfo item)
    {
        return super.indexOf(item);
    }
}