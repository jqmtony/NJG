package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WatcherCollection extends AbstractObjectCollection 
{
    public WatcherCollection()
    {
        super(WatcherInfo.class);
    }
    public boolean add(WatcherInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WatcherCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WatcherInfo item)
    {
        return removeObject(item);
    }
    public WatcherInfo get(int index)
    {
        return(WatcherInfo)getObject(index);
    }
    public WatcherInfo get(Object key)
    {
        return(WatcherInfo)getObject(key);
    }
    public void set(int index, WatcherInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WatcherInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WatcherInfo item)
    {
        return super.indexOf(item);
    }
}