package com.kingdee.eas.port.pm.qa;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectOAE1Collection extends AbstractObjectCollection 
{
    public ProjectOAE1Collection()
    {
        super(ProjectOAE1Info.class);
    }
    public boolean add(ProjectOAE1Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectOAE1Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectOAE1Info item)
    {
        return removeObject(item);
    }
    public ProjectOAE1Info get(int index)
    {
        return(ProjectOAE1Info)getObject(index);
    }
    public ProjectOAE1Info get(Object key)
    {
        return(ProjectOAE1Info)getObject(key);
    }
    public void set(int index, ProjectOAE1Info item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectOAE1Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectOAE1Info item)
    {
        return super.indexOf(item);
    }
}