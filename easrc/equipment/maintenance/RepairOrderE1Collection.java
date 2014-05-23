package com.kingdee.eas.port.equipment.maintenance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RepairOrderE1Collection extends AbstractObjectCollection 
{
    public RepairOrderE1Collection()
    {
        super(RepairOrderE1Info.class);
    }
    public boolean add(RepairOrderE1Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(RepairOrderE1Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RepairOrderE1Info item)
    {
        return removeObject(item);
    }
    public RepairOrderE1Info get(int index)
    {
        return(RepairOrderE1Info)getObject(index);
    }
    public RepairOrderE1Info get(Object key)
    {
        return(RepairOrderE1Info)getObject(key);
    }
    public void set(int index, RepairOrderE1Info item)
    {
        setObject(index, item);
    }
    public boolean contains(RepairOrderE1Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RepairOrderE1Info item)
    {
        return super.indexOf(item);
    }
}