package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PayRequestBillConfirmEntryCollection extends AbstractObjectCollection 
{
    public PayRequestBillConfirmEntryCollection()
    {
        super(PayRequestBillConfirmEntryInfo.class);
    }
    public boolean add(PayRequestBillConfirmEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PayRequestBillConfirmEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PayRequestBillConfirmEntryInfo item)
    {
        return removeObject(item);
    }
    public PayRequestBillConfirmEntryInfo get(int index)
    {
        return(PayRequestBillConfirmEntryInfo)getObject(index);
    }
    public PayRequestBillConfirmEntryInfo get(Object key)
    {
        return(PayRequestBillConfirmEntryInfo)getObject(key);
    }
    public void set(int index, PayRequestBillConfirmEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PayRequestBillConfirmEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PayRequestBillConfirmEntryInfo item)
    {
        return super.indexOf(item);
    }
}