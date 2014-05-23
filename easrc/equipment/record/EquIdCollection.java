package com.kingdee.eas.port.equipment.record;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EquIdCollection extends AbstractObjectCollection 
{
    public EquIdCollection()
    {
        super(EquIdInfo.class);
    }
    public boolean add(EquIdInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EquIdCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EquIdInfo item)
    {
        return removeObject(item);
    }
    public EquIdInfo get(int index)
    {
        return(EquIdInfo)getObject(index);
    }
    public EquIdInfo get(Object key)
    {
        return(EquIdInfo)getObject(key);
    }
    public void set(int index, EquIdInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EquIdInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EquIdInfo item)
    {
        return super.indexOf(item);
    }
}