package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DynamicCostMonitorCollection extends AbstractObjectCollection 
{
    public DynamicCostMonitorCollection()
    {
        super(DynamicCostMonitorInfo.class);
    }
    public boolean add(DynamicCostMonitorInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DynamicCostMonitorCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DynamicCostMonitorInfo item)
    {
        return removeObject(item);
    }
    public DynamicCostMonitorInfo get(int index)
    {
        return(DynamicCostMonitorInfo)getObject(index);
    }
    public DynamicCostMonitorInfo get(Object key)
    {
        return(DynamicCostMonitorInfo)getObject(key);
    }
    public void set(int index, DynamicCostMonitorInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DynamicCostMonitorInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DynamicCostMonitorInfo item)
    {
        return super.indexOf(item);
    }
}