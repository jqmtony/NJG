package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PcontractTrackBillEntryCollection extends AbstractObjectCollection 
{
    public PcontractTrackBillEntryCollection()
    {
        super(PcontractTrackBillEntryInfo.class);
    }
    public boolean add(PcontractTrackBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PcontractTrackBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PcontractTrackBillEntryInfo item)
    {
        return removeObject(item);
    }
    public PcontractTrackBillEntryInfo get(int index)
    {
        return(PcontractTrackBillEntryInfo)getObject(index);
    }
    public PcontractTrackBillEntryInfo get(Object key)
    {
        return(PcontractTrackBillEntryInfo)getObject(key);
    }
    public void set(int index, PcontractTrackBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PcontractTrackBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PcontractTrackBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}