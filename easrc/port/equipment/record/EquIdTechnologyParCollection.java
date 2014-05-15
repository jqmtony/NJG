package com.kingdee.eas.port.equipment.record;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EquIdTechnologyParCollection extends AbstractObjectCollection 
{
    public EquIdTechnologyParCollection()
    {
        super(EquIdTechnologyParInfo.class);
    }
    public boolean add(EquIdTechnologyParInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EquIdTechnologyParCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EquIdTechnologyParInfo item)
    {
        return removeObject(item);
    }
    public EquIdTechnologyParInfo get(int index)
    {
        return(EquIdTechnologyParInfo)getObject(index);
    }
    public EquIdTechnologyParInfo get(Object key)
    {
        return(EquIdTechnologyParInfo)getObject(key);
    }
    public void set(int index, EquIdTechnologyParInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EquIdTechnologyParInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EquIdTechnologyParInfo item)
    {
        return super.indexOf(item);
    }
}