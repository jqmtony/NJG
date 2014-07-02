package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EquTypeCollection extends AbstractObjectCollection 
{
    public EquTypeCollection()
    {
        super(EquTypeInfo.class);
    }
    public boolean add(EquTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EquTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EquTypeInfo item)
    {
        return removeObject(item);
    }
    public EquTypeInfo get(int index)
    {
        return(EquTypeInfo)getObject(index);
    }
    public EquTypeInfo get(Object key)
    {
        return(EquTypeInfo)getObject(key);
    }
    public void set(int index, EquTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EquTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EquTypeInfo item)
    {
        return super.indexOf(item);
    }
}