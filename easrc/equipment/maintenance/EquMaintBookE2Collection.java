package com.kingdee.eas.port.equipment.maintenance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EquMaintBookE2Collection extends AbstractObjectCollection 
{
    public EquMaintBookE2Collection()
    {
        super(EquMaintBookE2Info.class);
    }
    public boolean add(EquMaintBookE2Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(EquMaintBookE2Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EquMaintBookE2Info item)
    {
        return removeObject(item);
    }
    public EquMaintBookE2Info get(int index)
    {
        return(EquMaintBookE2Info)getObject(index);
    }
    public EquMaintBookE2Info get(Object key)
    {
        return(EquMaintBookE2Info)getObject(key);
    }
    public void set(int index, EquMaintBookE2Info item)
    {
        setObject(index, item);
    }
    public boolean contains(EquMaintBookE2Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EquMaintBookE2Info item)
    {
        return super.indexOf(item);
    }
}