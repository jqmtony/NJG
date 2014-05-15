package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MaintenanceTypeCollection extends AbstractObjectCollection 
{
    public MaintenanceTypeCollection()
    {
        super(MaintenanceTypeInfo.class);
    }
    public boolean add(MaintenanceTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MaintenanceTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MaintenanceTypeInfo item)
    {
        return removeObject(item);
    }
    public MaintenanceTypeInfo get(int index)
    {
        return(MaintenanceTypeInfo)getObject(index);
    }
    public MaintenanceTypeInfo get(Object key)
    {
        return(MaintenanceTypeInfo)getObject(key);
    }
    public void set(int index, MaintenanceTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MaintenanceTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MaintenanceTypeInfo item)
    {
        return super.indexOf(item);
    }
}