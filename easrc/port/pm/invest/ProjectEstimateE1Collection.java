package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectEstimateE1Collection extends AbstractObjectCollection 
{
    public ProjectEstimateE1Collection()
    {
        super(ProjectEstimateE1Info.class);
    }
    public boolean add(ProjectEstimateE1Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectEstimateE1Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectEstimateE1Info item)
    {
        return removeObject(item);
    }
    public ProjectEstimateE1Info get(int index)
    {
        return(ProjectEstimateE1Info)getObject(index);
    }
    public ProjectEstimateE1Info get(Object key)
    {
        return(ProjectEstimateE1Info)getObject(key);
    }
    public void set(int index, ProjectEstimateE1Info item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectEstimateE1Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectEstimateE1Info item)
    {
        return super.indexOf(item);
    }
}