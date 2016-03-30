package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectDynamicCostEentryTotalCollection extends AbstractObjectCollection 
{
    public ProjectDynamicCostEentryTotalCollection()
    {
        super(ProjectDynamicCostEentryTotalInfo.class);
    }
    public boolean add(ProjectDynamicCostEentryTotalInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectDynamicCostEentryTotalCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectDynamicCostEentryTotalInfo item)
    {
        return removeObject(item);
    }
    public ProjectDynamicCostEentryTotalInfo get(int index)
    {
        return(ProjectDynamicCostEentryTotalInfo)getObject(index);
    }
    public ProjectDynamicCostEentryTotalInfo get(Object key)
    {
        return(ProjectDynamicCostEentryTotalInfo)getObject(key);
    }
    public void set(int index, ProjectDynamicCostEentryTotalInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectDynamicCostEentryTotalInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectDynamicCostEentryTotalInfo item)
    {
        return super.indexOf(item);
    }
}