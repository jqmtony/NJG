package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TypeCollection extends AbstractObjectCollection 
{
    public TypeCollection()
    {
        super(TypeInfo.class);
    }
    public boolean add(TypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TypeInfo item)
    {
        return removeObject(item);
    }
    public TypeInfo get(int index)
    {
        return(TypeInfo)getObject(index);
    }
    public TypeInfo get(Object key)
    {
        return(TypeInfo)getObject(key);
    }
    public void set(int index, TypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TypeInfo item)
    {
        return super.indexOf(item);
    }
}