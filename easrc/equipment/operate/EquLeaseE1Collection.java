package com.kingdee.eas.port.equipment.operate;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EquLeaseE1Collection extends AbstractObjectCollection 
{
    public EquLeaseE1Collection()
    {
        super(EquLeaseE1Info.class);
    }
    public boolean add(EquLeaseE1Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(EquLeaseE1Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EquLeaseE1Info item)
    {
        return removeObject(item);
    }
    public EquLeaseE1Info get(int index)
    {
        return(EquLeaseE1Info)getObject(index);
    }
    public EquLeaseE1Info get(Object key)
    {
        return(EquLeaseE1Info)getObject(key);
    }
    public void set(int index, EquLeaseE1Info item)
    {
        setObject(index, item);
    }
    public boolean contains(EquLeaseE1Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EquLeaseE1Info item)
    {
        return super.indexOf(item);
    }
}