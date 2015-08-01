package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DynCostCtrlBillEntryCollection extends AbstractObjectCollection 
{
    public DynCostCtrlBillEntryCollection()
    {
        super(DynCostCtrlBillEntryInfo.class);
    }
    public boolean add(DynCostCtrlBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DynCostCtrlBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DynCostCtrlBillEntryInfo item)
    {
        return removeObject(item);
    }
    public DynCostCtrlBillEntryInfo get(int index)
    {
        return(DynCostCtrlBillEntryInfo)getObject(index);
    }
    public DynCostCtrlBillEntryInfo get(Object key)
    {
        return(DynCostCtrlBillEntryInfo)getObject(key);
    }
    public void set(int index, DynCostCtrlBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DynCostCtrlBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DynCostCtrlBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}