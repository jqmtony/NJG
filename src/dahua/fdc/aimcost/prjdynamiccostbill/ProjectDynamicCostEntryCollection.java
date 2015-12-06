package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectDynamicCostEntryCollection extends AbstractObjectCollection 
{
    public ProjectDynamicCostEntryCollection()
    {
        super(ProjectDynamicCostEntryInfo.class);
    }
    public boolean add(ProjectDynamicCostEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectDynamicCostEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectDynamicCostEntryInfo item)
    {
        return removeObject(item);
    }
    public ProjectDynamicCostEntryInfo get(int index)
    {
        return(ProjectDynamicCostEntryInfo)getObject(index);
    }
    public ProjectDynamicCostEntryInfo get(Object key)
    {
        return(ProjectDynamicCostEntryInfo)getObject(key);
    }
    public void set(int index, ProjectDynamicCostEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectDynamicCostEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectDynamicCostEntryInfo item)
    {
        return super.indexOf(item);
    }
}