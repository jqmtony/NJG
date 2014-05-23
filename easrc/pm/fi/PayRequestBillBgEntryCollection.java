package com.kingdee.eas.port.pm.fi;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PayRequestBillBgEntryCollection extends AbstractObjectCollection 
{
    public PayRequestBillBgEntryCollection()
    {
        super(PayRequestBillBgEntryInfo.class);
    }
    public boolean add(PayRequestBillBgEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PayRequestBillBgEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PayRequestBillBgEntryInfo item)
    {
        return removeObject(item);
    }
    public PayRequestBillBgEntryInfo get(int index)
    {
        return(PayRequestBillBgEntryInfo)getObject(index);
    }
    public PayRequestBillBgEntryInfo get(Object key)
    {
        return(PayRequestBillBgEntryInfo)getObject(key);
    }
    public void set(int index, PayRequestBillBgEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PayRequestBillBgEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PayRequestBillBgEntryInfo item)
    {
        return super.indexOf(item);
    }
}