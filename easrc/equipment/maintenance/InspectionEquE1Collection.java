package com.kingdee.eas.port.equipment.maintenance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InspectionEquE1Collection extends AbstractObjectCollection 
{
    public InspectionEquE1Collection()
    {
        super(InspectionEquE1Info.class);
    }
    public boolean add(InspectionEquE1Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(InspectionEquE1Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InspectionEquE1Info item)
    {
        return removeObject(item);
    }
    public InspectionEquE1Info get(int index)
    {
        return(InspectionEquE1Info)getObject(index);
    }
    public InspectionEquE1Info get(Object key)
    {
        return(InspectionEquE1Info)getObject(key);
    }
    public void set(int index, InspectionEquE1Info item)
    {
        setObject(index, item);
    }
    public boolean contains(InspectionEquE1Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InspectionEquE1Info item)
    {
        return super.indexOf(item);
    }
}