package com.kingdee.eas.port.pm.qa;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectOAE2Collection extends AbstractObjectCollection 
{
    public ProjectOAE2Collection()
    {
        super(ProjectOAE2Info.class);
    }
    public boolean add(ProjectOAE2Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectOAE2Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectOAE2Info item)
    {
        return removeObject(item);
    }
    public ProjectOAE2Info get(int index)
    {
        return(ProjectOAE2Info)getObject(index);
    }
    public ProjectOAE2Info get(Object key)
    {
        return(ProjectOAE2Info)getObject(key);
    }
    public void set(int index, ProjectOAE2Info item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectOAE2Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectOAE2Info item)
    {
        return super.indexOf(item);
    }
}