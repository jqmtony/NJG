package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class HisProjProEntrApporDataCollection extends AbstractObjectCollection 
{
    public HisProjProEntrApporDataCollection()
    {
        super(HisProjProEntrApporDataInfo.class);
    }
    public boolean add(HisProjProEntrApporDataInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(HisProjProEntrApporDataCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(HisProjProEntrApporDataInfo item)
    {
        return removeObject(item);
    }
    public HisProjProEntrApporDataInfo get(int index)
    {
        return(HisProjProEntrApporDataInfo)getObject(index);
    }
    public HisProjProEntrApporDataInfo get(Object key)
    {
        return(HisProjProEntrApporDataInfo)getObject(key);
    }
    public void set(int index, HisProjProEntrApporDataInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(HisProjProEntrApporDataInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(HisProjProEntrApporDataInfo item)
    {
        return super.indexOf(item);
    }
}