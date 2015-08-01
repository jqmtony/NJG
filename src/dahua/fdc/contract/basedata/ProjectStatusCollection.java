package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectStatusCollection extends AbstractObjectCollection 
{
    public ProjectStatusCollection()
    {
        super(ProjectStatusInfo.class);
    }
    public boolean add(ProjectStatusInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectStatusCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectStatusInfo item)
    {
        return removeObject(item);
    }
    public ProjectStatusInfo get(int index)
    {
        return(ProjectStatusInfo)getObject(index);
    }
    public ProjectStatusInfo get(Object key)
    {
        return(ProjectStatusInfo)getObject(key);
    }
    public void set(int index, ProjectStatusInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectStatusInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectStatusInfo item)
    {
        return super.indexOf(item);
    }
}