package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InitialConnectionE1Collection extends AbstractObjectCollection 
{
    public InitialConnectionE1Collection()
    {
        super(InitialConnectionE1Info.class);
    }
    public boolean add(InitialConnectionE1Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(InitialConnectionE1Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InitialConnectionE1Info item)
    {
        return removeObject(item);
    }
    public InitialConnectionE1Info get(int index)
    {
        return(InitialConnectionE1Info)getObject(index);
    }
    public InitialConnectionE1Info get(Object key)
    {
        return(InitialConnectionE1Info)getObject(key);
    }
    public void set(int index, InitialConnectionE1Info item)
    {
        setObject(index, item);
    }
    public boolean contains(InitialConnectionE1Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InitialConnectionE1Info item)
    {
        return super.indexOf(item);
    }
}