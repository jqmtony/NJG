package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectWithCostCenterOUCollection extends AbstractObjectCollection 
{
    public ProjectWithCostCenterOUCollection()
    {
        super(ProjectWithCostCenterOUInfo.class);
    }
    public boolean add(ProjectWithCostCenterOUInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectWithCostCenterOUCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectWithCostCenterOUInfo item)
    {
        return removeObject(item);
    }
    public ProjectWithCostCenterOUInfo get(int index)
    {
        return(ProjectWithCostCenterOUInfo)getObject(index);
    }
    public ProjectWithCostCenterOUInfo get(Object key)
    {
        return(ProjectWithCostCenterOUInfo)getObject(key);
    }
    public void set(int index, ProjectWithCostCenterOUInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectWithCostCenterOUInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectWithCostCenterOUInfo item)
    {
        return super.indexOf(item);
    }
}