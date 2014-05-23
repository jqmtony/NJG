package com.kingdee.eas.port.equipment.maintenance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MonMPApplicationCollection extends AbstractObjectCollection 
{
    public MonMPApplicationCollection()
    {
        super(MonMPApplicationInfo.class);
    }
    public boolean add(MonMPApplicationInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MonMPApplicationCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MonMPApplicationInfo item)
    {
        return removeObject(item);
    }
    public MonMPApplicationInfo get(int index)
    {
        return(MonMPApplicationInfo)getObject(index);
    }
    public MonMPApplicationInfo get(Object key)
    {
        return(MonMPApplicationInfo)getObject(key);
    }
    public void set(int index, MonMPApplicationInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MonMPApplicationInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MonMPApplicationInfo item)
    {
        return super.indexOf(item);
    }
}