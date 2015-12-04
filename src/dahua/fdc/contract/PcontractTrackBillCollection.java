package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PcontractTrackBillCollection extends AbstractObjectCollection 
{
    public PcontractTrackBillCollection()
    {
        super(PcontractTrackBillInfo.class);
    }
    public boolean add(PcontractTrackBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PcontractTrackBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PcontractTrackBillInfo item)
    {
        return removeObject(item);
    }
    public PcontractTrackBillInfo get(int index)
    {
        return(PcontractTrackBillInfo)getObject(index);
    }
    public PcontractTrackBillInfo get(Object key)
    {
        return(PcontractTrackBillInfo)getObject(key);
    }
    public void set(int index, PcontractTrackBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PcontractTrackBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PcontractTrackBillInfo item)
    {
        return super.indexOf(item);
    }
}