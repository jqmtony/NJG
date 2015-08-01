package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DynamicCostMonitorCAEntriesCollection extends AbstractObjectCollection 
{
    public DynamicCostMonitorCAEntriesCollection()
    {
        super(DynamicCostMonitorCAEntriesInfo.class);
    }
    public boolean add(DynamicCostMonitorCAEntriesInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DynamicCostMonitorCAEntriesCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DynamicCostMonitorCAEntriesInfo item)
    {
        return removeObject(item);
    }
    public DynamicCostMonitorCAEntriesInfo get(int index)
    {
        return(DynamicCostMonitorCAEntriesInfo)getObject(index);
    }
    public DynamicCostMonitorCAEntriesInfo get(Object key)
    {
        return(DynamicCostMonitorCAEntriesInfo)getObject(key);
    }
    public void set(int index, DynamicCostMonitorCAEntriesInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DynamicCostMonitorCAEntriesInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DynamicCostMonitorCAEntriesInfo item)
    {
        return super.indexOf(item);
    }
}