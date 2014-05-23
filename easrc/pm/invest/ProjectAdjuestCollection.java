package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectAdjuestCollection extends AbstractObjectCollection 
{
    public ProjectAdjuestCollection()
    {
        super(ProjectAdjuestInfo.class);
    }
    public boolean add(ProjectAdjuestInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectAdjuestCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectAdjuestInfo item)
    {
        return removeObject(item);
    }
    public ProjectAdjuestInfo get(int index)
    {
        return(ProjectAdjuestInfo)getObject(index);
    }
    public ProjectAdjuestInfo get(Object key)
    {
        return(ProjectAdjuestInfo)getObject(key);
    }
    public void set(int index, ProjectAdjuestInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectAdjuestInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectAdjuestInfo item)
    {
        return super.indexOf(item);
    }
}