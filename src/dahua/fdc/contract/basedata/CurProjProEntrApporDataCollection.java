package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CurProjProEntrApporDataCollection extends AbstractObjectCollection 
{
    public CurProjProEntrApporDataCollection()
    {
        super(CurProjProEntrApporDataInfo.class);
    }
    public boolean add(CurProjProEntrApporDataInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CurProjProEntrApporDataCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CurProjProEntrApporDataInfo item)
    {
        return removeObject(item);
    }
    public CurProjProEntrApporDataInfo get(int index)
    {
        return(CurProjProEntrApporDataInfo)getObject(index);
    }
    public CurProjProEntrApporDataInfo get(Object key)
    {
        return(CurProjProEntrApporDataInfo)getObject(key);
    }
    public void set(int index, CurProjProEntrApporDataInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CurProjProEntrApporDataInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CurProjProEntrApporDataInfo item)
    {
        return super.indexOf(item);
    }
}