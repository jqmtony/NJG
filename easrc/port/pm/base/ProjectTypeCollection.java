package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectTypeCollection extends AbstractObjectCollection 
{
    public ProjectTypeCollection()
    {
        super(ProjectTypeInfo.class);
    }
    public boolean add(ProjectTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectTypeInfo item)
    {
        return removeObject(item);
    }
    public ProjectTypeInfo get(int index)
    {
        return(ProjectTypeInfo)getObject(index);
    }
    public ProjectTypeInfo get(Object key)
    {
        return(ProjectTypeInfo)getObject(key);
    }
    public void set(int index, ProjectTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectTypeInfo item)
    {
        return super.indexOf(item);
    }
}