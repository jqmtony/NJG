package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BuildTypeCollection extends AbstractObjectCollection 
{
    public BuildTypeCollection()
    {
        super(BuildTypeInfo.class);
    }
    public boolean add(BuildTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BuildTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BuildTypeInfo item)
    {
        return removeObject(item);
    }
    public BuildTypeInfo get(int index)
    {
        return(BuildTypeInfo)getObject(index);
    }
    public BuildTypeInfo get(Object key)
    {
        return(BuildTypeInfo)getObject(key);
    }
    public void set(int index, BuildTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BuildTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BuildTypeInfo item)
    {
        return super.indexOf(item);
    }
}