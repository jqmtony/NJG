package com.kingdee.eas.port.pm.fi;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PayRequestBillAcctPayCollection extends AbstractObjectCollection 
{
    public PayRequestBillAcctPayCollection()
    {
        super(PayRequestBillAcctPayInfo.class);
    }
    public boolean add(PayRequestBillAcctPayInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PayRequestBillAcctPayCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PayRequestBillAcctPayInfo item)
    {
        return removeObject(item);
    }
    public PayRequestBillAcctPayInfo get(int index)
    {
        return(PayRequestBillAcctPayInfo)getObject(index);
    }
    public PayRequestBillAcctPayInfo get(Object key)
    {
        return(PayRequestBillAcctPayInfo)getObject(key);
    }
    public void set(int index, PayRequestBillAcctPayInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PayRequestBillAcctPayInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PayRequestBillAcctPayInfo item)
    {
        return super.indexOf(item);
    }
}