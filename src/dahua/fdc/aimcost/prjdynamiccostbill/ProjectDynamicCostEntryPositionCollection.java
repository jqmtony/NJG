package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectDynamicCostEntryPositionCollection extends AbstractObjectCollection 
{
    public ProjectDynamicCostEntryPositionCollection()
    {
        super(ProjectDynamicCostEntryPositionInfo.class);
    }
    public boolean add(ProjectDynamicCostEntryPositionInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectDynamicCostEntryPositionCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectDynamicCostEntryPositionInfo item)
    {
        return removeObject(item);
    }
    public ProjectDynamicCostEntryPositionInfo get(int index)
    {
        return(ProjectDynamicCostEntryPositionInfo)getObject(index);
    }
    public ProjectDynamicCostEntryPositionInfo get(Object key)
    {
        return(ProjectDynamicCostEntryPositionInfo)getObject(key);
    }
    public void set(int index, ProjectDynamicCostEntryPositionInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectDynamicCostEntryPositionInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectDynamicCostEntryPositionInfo item)
    {
        return super.indexOf(item);
    }
}