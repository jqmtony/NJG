package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CurProjProductEntriesCollection extends AbstractObjectCollection 
{
    public CurProjProductEntriesCollection()
    {
        super(CurProjProductEntriesInfo.class);
    }
    public boolean add(CurProjProductEntriesInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CurProjProductEntriesCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CurProjProductEntriesInfo item)
    {
        return removeObject(item);
    }
    public CurProjProductEntriesInfo get(int index)
    {
        return(CurProjProductEntriesInfo)getObject(index);
    }
    public CurProjProductEntriesInfo get(Object key)
    {
        return(CurProjProductEntriesInfo)getObject(key);
    }
    public void set(int index, CurProjProductEntriesInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CurProjProductEntriesInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CurProjProductEntriesInfo item)
    {
        return super.indexOf(item);
    }
}