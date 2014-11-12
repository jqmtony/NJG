package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PayBgEntryCollection extends AbstractObjectCollection 
{
    public PayBgEntryCollection()
    {
        super(PayBgEntryInfo.class);
    }
    public boolean add(PayBgEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PayBgEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PayBgEntryInfo item)
    {
        return removeObject(item);
    }
    public PayBgEntryInfo get(int index)
    {
        return(PayBgEntryInfo)getObject(index);
    }
    public PayBgEntryInfo get(Object key)
    {
        return(PayBgEntryInfo)getObject(key);
    }
    public void set(int index, PayBgEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PayBgEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PayBgEntryInfo item)
    {
        return super.indexOf(item);
    }
}