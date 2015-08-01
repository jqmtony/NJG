package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class HisProjProductEntriesCollection extends AbstractObjectCollection 
{
    public HisProjProductEntriesCollection()
    {
        super(HisProjProductEntriesInfo.class);
    }
    public boolean add(HisProjProductEntriesInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(HisProjProductEntriesCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(HisProjProductEntriesInfo item)
    {
        return removeObject(item);
    }
    public HisProjProductEntriesInfo get(int index)
    {
        return(HisProjProductEntriesInfo)getObject(index);
    }
    public HisProjProductEntriesInfo get(Object key)
    {
        return(HisProjProductEntriesInfo)getObject(key);
    }
    public void set(int index, HisProjProductEntriesInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(HisProjProductEntriesInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(HisProjProductEntriesInfo item)
    {
        return super.indexOf(item);
    }
}