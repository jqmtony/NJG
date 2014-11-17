package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectStartRequestE2Collection extends AbstractObjectCollection 
{
    public ProjectStartRequestE2Collection()
    {
        super(ProjectStartRequestE2Info.class);
    }
    public boolean add(ProjectStartRequestE2Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectStartRequestE2Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectStartRequestE2Info item)
    {
        return removeObject(item);
    }
    public ProjectStartRequestE2Info get(int index)
    {
        return(ProjectStartRequestE2Info)getObject(index);
    }
    public ProjectStartRequestE2Info get(Object key)
    {
        return(ProjectStartRequestE2Info)getObject(key);
    }
    public void set(int index, ProjectStartRequestE2Info item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectStartRequestE2Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectStartRequestE2Info item)
    {
        return super.indexOf(item);
    }
}