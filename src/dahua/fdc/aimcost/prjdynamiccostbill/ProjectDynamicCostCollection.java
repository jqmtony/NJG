package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectDynamicCostCollection extends AbstractObjectCollection 
{
    public ProjectDynamicCostCollection()
    {
        super(ProjectDynamicCostInfo.class);
    }
    public boolean add(ProjectDynamicCostInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectDynamicCostCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectDynamicCostInfo item)
    {
        return removeObject(item);
    }
    public ProjectDynamicCostInfo get(int index)
    {
        return(ProjectDynamicCostInfo)getObject(index);
    }
    public ProjectDynamicCostInfo get(Object key)
    {
        return(ProjectDynamicCostInfo)getObject(key);
    }
    public void set(int index, ProjectDynamicCostInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectDynamicCostInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectDynamicCostInfo item)
    {
        return super.indexOf(item);
    }
}