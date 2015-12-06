package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProjectDynamicCostEntrysAccountCollection extends AbstractObjectCollection 
{
    public ProjectDynamicCostEntrysAccountCollection()
    {
        super(ProjectDynamicCostEntrysAccountInfo.class);
    }
    public boolean add(ProjectDynamicCostEntrysAccountInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProjectDynamicCostEntrysAccountCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProjectDynamicCostEntrysAccountInfo item)
    {
        return removeObject(item);
    }
    public ProjectDynamicCostEntrysAccountInfo get(int index)
    {
        return(ProjectDynamicCostEntrysAccountInfo)getObject(index);
    }
    public ProjectDynamicCostEntrysAccountInfo get(Object key)
    {
        return(ProjectDynamicCostEntrysAccountInfo)getObject(key);
    }
    public void set(int index, ProjectDynamicCostEntrysAccountInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProjectDynamicCostEntrysAccountInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProjectDynamicCostEntrysAccountInfo item)
    {
        return super.indexOf(item);
    }
}