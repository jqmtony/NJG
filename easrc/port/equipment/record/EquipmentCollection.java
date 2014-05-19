package com.kingdee.eas.port.equipment.record;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EquipmentCollection extends AbstractObjectCollection 
{
    public EquipmentCollection()
    {
        super(EquipmentInfo.class);
    }
    public boolean add(EquipmentInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EquipmentCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EquipmentInfo item)
    {
        return removeObject(item);
    }
    public EquipmentInfo get(int index)
    {
        return(EquipmentInfo)getObject(index);
    }
    public EquipmentInfo get(Object key)
    {
        return(EquipmentInfo)getObject(key);
    }
    public void set(int index, EquipmentInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EquipmentInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EquipmentInfo item)
    {
        return super.indexOf(item);
    }
}