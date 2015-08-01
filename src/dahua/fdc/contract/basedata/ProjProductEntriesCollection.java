package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjProductEntriesCollection extends AbstractObjectCollection 
{
    public ProjProductEntriesCollection()
    {
        super(ProjProductEntriesInfo.class);
    }
    public boolean add(ProjProductEntriesInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjProductEntriesCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjProductEntriesInfo item)
    {
        return removeObject(item);
    }
    public ProjProductEntriesInfo get(int index)
    {
        return(ProjProductEntriesInfo)getObject(index);
    }
    public ProjProductEntriesInfo get(Object key)
    {
        return(ProjProductEntriesInfo)getObject(key);
    }
    public void set(int index, ProjProductEntriesInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjProductEntriesInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjProductEntriesInfo item)
    {
        return super.indexOf(item);
    }
}