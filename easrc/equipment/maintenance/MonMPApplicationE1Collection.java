package com.kingdee.eas.port.equipment.maintenance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MonMPApplicationE1Collection extends AbstractObjectCollection 
{
    public MonMPApplicationE1Collection()
    {
        super(MonMPApplicationE1Info.class);
    }
    public boolean add(MonMPApplicationE1Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(MonMPApplicationE1Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MonMPApplicationE1Info item)
    {
        return removeObject(item);
    }
    public MonMPApplicationE1Info get(int index)
    {
        return(MonMPApplicationE1Info)getObject(index);
    }
    public MonMPApplicationE1Info get(Object key)
    {
        return(MonMPApplicationE1Info)getObject(key);
    }
    public void set(int index, MonMPApplicationE1Info item)
    {
        setObject(index, item);
    }
    public boolean contains(MonMPApplicationE1Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MonMPApplicationE1Info item)
    {
        return super.indexOf(item);
    }
}