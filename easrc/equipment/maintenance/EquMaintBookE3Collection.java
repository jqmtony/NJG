package com.kingdee.eas.port.equipment.maintenance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EquMaintBookE3Collection extends AbstractObjectCollection 
{
    public EquMaintBookE3Collection()
    {
        super(EquMaintBookE3Info.class);
    }
    public boolean add(EquMaintBookE3Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(EquMaintBookE3Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EquMaintBookE3Info item)
    {
        return removeObject(item);
    }
    public EquMaintBookE3Info get(int index)
    {
        return(EquMaintBookE3Info)getObject(index);
    }
    public EquMaintBookE3Info get(Object key)
    {
        return(EquMaintBookE3Info)getObject(key);
    }
    public void set(int index, EquMaintBookE3Info item)
    {
        setObject(index, item);
    }
    public boolean contains(EquMaintBookE3Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EquMaintBookE3Info item)
    {
        return super.indexOf(item);
    }
}