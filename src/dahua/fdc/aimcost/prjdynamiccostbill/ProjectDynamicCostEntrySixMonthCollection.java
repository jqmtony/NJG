package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectDynamicCostEntrySixMonthCollection extends AbstractObjectCollection 
{
    public ProjectDynamicCostEntrySixMonthCollection()
    {
        super(ProjectDynamicCostEntrySixMonthInfo.class);
    }
    public boolean add(ProjectDynamicCostEntrySixMonthInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectDynamicCostEntrySixMonthCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectDynamicCostEntrySixMonthInfo item)
    {
        return removeObject(item);
    }
    public ProjectDynamicCostEntrySixMonthInfo get(int index)
    {
        return(ProjectDynamicCostEntrySixMonthInfo)getObject(index);
    }
    public ProjectDynamicCostEntrySixMonthInfo get(Object key)
    {
        return(ProjectDynamicCostEntrySixMonthInfo)getObject(key);
    }
    public void set(int index, ProjectDynamicCostEntrySixMonthInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectDynamicCostEntrySixMonthInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectDynamicCostEntrySixMonthInfo item)
    {
        return super.indexOf(item);
    }
}