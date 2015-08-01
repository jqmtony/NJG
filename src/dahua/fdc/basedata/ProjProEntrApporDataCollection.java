package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjProEntrApporDataCollection extends AbstractObjectCollection 
{
    public ProjProEntrApporDataCollection()
    {
        super(ProjProEntrApporDataInfo.class);
    }
    public boolean add(ProjProEntrApporDataInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjProEntrApporDataCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjProEntrApporDataInfo item)
    {
        return removeObject(item);
    }
    public ProjProEntrApporDataInfo get(int index)
    {
        return(ProjProEntrApporDataInfo)getObject(index);
    }
    public ProjProEntrApporDataInfo get(Object key)
    {
        return(ProjProEntrApporDataInfo)getObject(key);
    }
    public void set(int index, ProjProEntrApporDataInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjProEntrApporDataInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjProEntrApporDataInfo item)
    {
        return super.indexOf(item);
    }
}