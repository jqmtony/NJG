package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectEstimateCollection extends AbstractObjectCollection 
{
    public ProjectEstimateCollection()
    {
        super(ProjectEstimateInfo.class);
    }
    public boolean add(ProjectEstimateInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectEstimateCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectEstimateInfo item)
    {
        return removeObject(item);
    }
    public ProjectEstimateInfo get(int index)
    {
        return(ProjectEstimateInfo)getObject(index);
    }
    public ProjectEstimateInfo get(Object key)
    {
        return(ProjectEstimateInfo)getObject(key);
    }
    public void set(int index, ProjectEstimateInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectEstimateInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectEstimateInfo item)
    {
        return super.indexOf(item);
    }
}