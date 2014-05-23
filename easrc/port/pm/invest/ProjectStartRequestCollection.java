package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectStartRequestCollection extends AbstractObjectCollection 
{
    public ProjectStartRequestCollection()
    {
        super(ProjectStartRequestInfo.class);
    }
    public boolean add(ProjectStartRequestInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectStartRequestCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectStartRequestInfo item)
    {
        return removeObject(item);
    }
    public ProjectStartRequestInfo get(int index)
    {
        return(ProjectStartRequestInfo)getObject(index);
    }
    public ProjectStartRequestInfo get(Object key)
    {
        return(ProjectStartRequestInfo)getObject(key);
    }
    public void set(int index, ProjectStartRequestInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectStartRequestInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectStartRequestInfo item)
    {
        return super.indexOf(item);
    }
}