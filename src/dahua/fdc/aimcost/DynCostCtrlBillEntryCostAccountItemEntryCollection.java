package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DynCostCtrlBillEntryCostAccountItemEntryCollection extends AbstractObjectCollection 
{
    public DynCostCtrlBillEntryCostAccountItemEntryCollection()
    {
        super(DynCostCtrlBillEntryCostAccountItemEntryInfo.class);
    }
    public boolean add(DynCostCtrlBillEntryCostAccountItemEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DynCostCtrlBillEntryCostAccountItemEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DynCostCtrlBillEntryCostAccountItemEntryInfo item)
    {
        return removeObject(item);
    }
    public DynCostCtrlBillEntryCostAccountItemEntryInfo get(int index)
    {
        return(DynCostCtrlBillEntryCostAccountItemEntryInfo)getObject(index);
    }
    public DynCostCtrlBillEntryCostAccountItemEntryInfo get(Object key)
    {
        return(DynCostCtrlBillEntryCostAccountItemEntryInfo)getObject(key);
    }
    public void set(int index, DynCostCtrlBillEntryCostAccountItemEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DynCostCtrlBillEntryCostAccountItemEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DynCostCtrlBillEntryCostAccountItemEntryInfo item)
    {
        return super.indexOf(item);
    }
}