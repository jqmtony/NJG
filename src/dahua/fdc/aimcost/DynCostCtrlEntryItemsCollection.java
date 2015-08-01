package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DynCostCtrlEntryItemsCollection extends AbstractObjectCollection 
{
    public DynCostCtrlEntryItemsCollection()
    {
        super(DynCostCtrlEntryItemsInfo.class);
    }
    public boolean add(DynCostCtrlEntryItemsInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DynCostCtrlEntryItemsCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DynCostCtrlEntryItemsInfo item)
    {
        return removeObject(item);
    }
    public DynCostCtrlEntryItemsInfo get(int index)
    {
        return(DynCostCtrlEntryItemsInfo)getObject(index);
    }
    public DynCostCtrlEntryItemsInfo get(Object key)
    {
        return(DynCostCtrlEntryItemsInfo)getObject(key);
    }
    public void set(int index, DynCostCtrlEntryItemsInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DynCostCtrlEntryItemsInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DynCostCtrlEntryItemsInfo item)
    {
        return super.indexOf(item);
    }
}