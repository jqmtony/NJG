package com.kingdee.eas.port.equipment.record;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EquIdE3Collection extends AbstractObjectCollection 
{
    public EquIdE3Collection()
    {
        super(EquIdE3Info.class);
    }
    public boolean add(EquIdE3Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(EquIdE3Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EquIdE3Info item)
    {
        return removeObject(item);
    }
    public EquIdE3Info get(int index)
    {
        return(EquIdE3Info)getObject(index);
    }
    public EquIdE3Info get(Object key)
    {
        return(EquIdE3Info)getObject(key);
    }
    public void set(int index, EquIdE3Info item)
    {
        setObject(index, item);
    }
    public boolean contains(EquIdE3Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EquIdE3Info item)
    {
        return super.indexOf(item);
    }
}