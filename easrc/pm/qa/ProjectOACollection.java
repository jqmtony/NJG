package com.kingdee.eas.port.pm.qa;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectOACollection extends AbstractObjectCollection 
{
    public ProjectOACollection()
    {
        super(ProjectOAInfo.class);
    }
    public boolean add(ProjectOAInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectOACollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectOAInfo item)
    {
        return removeObject(item);
    }
    public ProjectOAInfo get(int index)
    {
        return(ProjectOAInfo)getObject(index);
    }
    public ProjectOAInfo get(Object key)
    {
        return(ProjectOAInfo)getObject(key);
    }
    public void set(int index, ProjectOAInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectOAInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectOAInfo item)
    {
        return super.indexOf(item);
    }
}