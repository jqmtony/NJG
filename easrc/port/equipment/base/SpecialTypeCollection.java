package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SpecialTypeCollection extends AbstractObjectCollection 
{
    public SpecialTypeCollection()
    {
        super(SpecialTypeInfo.class);
    }
    public boolean add(SpecialTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SpecialTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SpecialTypeInfo item)
    {
        return removeObject(item);
    }
    public SpecialTypeInfo get(int index)
    {
        return(SpecialTypeInfo)getObject(index);
    }
    public SpecialTypeInfo get(Object key)
    {
        return(SpecialTypeInfo)getObject(key);
    }
    public void set(int index, SpecialTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SpecialTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SpecialTypeInfo item)
    {
        return super.indexOf(item);
    }
}