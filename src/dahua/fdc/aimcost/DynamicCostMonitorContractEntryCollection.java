package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DynamicCostMonitorContractEntryCollection extends AbstractObjectCollection 
{
    public DynamicCostMonitorContractEntryCollection()
    {
        super(DynamicCostMonitorContractEntryInfo.class);
    }
    public boolean add(DynamicCostMonitorContractEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DynamicCostMonitorContractEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DynamicCostMonitorContractEntryInfo item)
    {
        return removeObject(item);
    }
    public DynamicCostMonitorContractEntryInfo get(int index)
    {
        return(DynamicCostMonitorContractEntryInfo)getObject(index);
    }
    public DynamicCostMonitorContractEntryInfo get(Object key)
    {
        return(DynamicCostMonitorContractEntryInfo)getObject(key);
    }
    public void set(int index, DynamicCostMonitorContractEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DynamicCostMonitorContractEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DynamicCostMonitorContractEntryInfo item)
    {
        return super.indexOf(item);
    }
}