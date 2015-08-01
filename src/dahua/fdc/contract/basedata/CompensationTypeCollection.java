package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CompensationTypeCollection extends AbstractObjectCollection 
{
    public CompensationTypeCollection()
    {
        super(CompensationTypeInfo.class);
    }
    public boolean add(CompensationTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CompensationTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CompensationTypeInfo item)
    {
        return removeObject(item);
    }
    public CompensationTypeInfo get(int index)
    {
        return(CompensationTypeInfo)getObject(index);
    }
    public CompensationTypeInfo get(Object key)
    {
        return(CompensationTypeInfo)getObject(key);
    }
    public void set(int index, CompensationTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CompensationTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CompensationTypeInfo item)
    {
        return super.indexOf(item);
    }
}