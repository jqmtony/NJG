package com.kingdee.eas.port.equipment.maintenance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InspectionEquCollection extends AbstractObjectCollection 
{
    public InspectionEquCollection()
    {
        super(InspectionEquInfo.class);
    }
    public boolean add(InspectionEquInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InspectionEquCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InspectionEquInfo item)
    {
        return removeObject(item);
    }
    public InspectionEquInfo get(int index)
    {
        return(InspectionEquInfo)getObject(index);
    }
    public InspectionEquInfo get(Object key)
    {
        return(InspectionEquInfo)getObject(key);
    }
    public void set(int index, InspectionEquInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InspectionEquInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InspectionEquInfo item)
    {
        return super.indexOf(item);
    }
}