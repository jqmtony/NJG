package com.kingdee.eas.port.pm.project;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectFeeCollection extends AbstractObjectCollection 
{
    public ProjectFeeCollection()
    {
        super(ProjectFeeInfo.class);
    }
    public boolean add(ProjectFeeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectFeeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectFeeInfo item)
    {
        return removeObject(item);
    }
    public ProjectFeeInfo get(int index)
    {
        return(ProjectFeeInfo)getObject(index);
    }
    public ProjectFeeInfo get(Object key)
    {
        return(ProjectFeeInfo)getObject(key);
    }
    public void set(int index, ProjectFeeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectFeeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectFeeInfo item)
    {
        return super.indexOf(item);
    }
}