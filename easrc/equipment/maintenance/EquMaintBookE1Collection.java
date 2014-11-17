package com.kingdee.eas.port.equipment.maintenance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EquMaintBookE1Collection extends AbstractObjectCollection 
{
    public EquMaintBookE1Collection()
    {
        super(EquMaintBookE1Info.class);
    }
    public boolean add(EquMaintBookE1Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(EquMaintBookE1Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EquMaintBookE1Info item)
    {
        return removeObject(item);
    }
    public EquMaintBookE1Info get(int index)
    {
        return(EquMaintBookE1Info)getObject(index);
    }
    public EquMaintBookE1Info get(Object key)
    {
        return(EquMaintBookE1Info)getObject(key);
    }
    public void set(int index, EquMaintBookE1Info item)
    {
        setObject(index, item);
    }
    public boolean contains(EquMaintBookE1Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EquMaintBookE1Info item)
    {
        return super.indexOf(item);
    }
}