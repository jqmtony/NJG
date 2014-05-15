package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EqmTypeCollection extends AbstractObjectCollection 
{
    public EqmTypeCollection()
    {
        super(EqmTypeInfo.class);
    }
    public boolean add(EqmTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EqmTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EqmTypeInfo item)
    {
        return removeObject(item);
    }
    public EqmTypeInfo get(int index)
    {
        return(EqmTypeInfo)getObject(index);
    }
    public EqmTypeInfo get(Object key)
    {
        return(EqmTypeInfo)getObject(key);
    }
    public void set(int index, EqmTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EqmTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EqmTypeInfo item)
    {
        return super.indexOf(item);
    }
}