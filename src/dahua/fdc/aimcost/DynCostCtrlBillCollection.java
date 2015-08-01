package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DynCostCtrlBillCollection extends AbstractObjectCollection 
{
    public DynCostCtrlBillCollection()
    {
        super(DynCostCtrlBillInfo.class);
    }
    public boolean add(DynCostCtrlBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DynCostCtrlBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DynCostCtrlBillInfo item)
    {
        return removeObject(item);
    }
    public DynCostCtrlBillInfo get(int index)
    {
        return(DynCostCtrlBillInfo)getObject(index);
    }
    public DynCostCtrlBillInfo get(Object key)
    {
        return(DynCostCtrlBillInfo)getObject(key);
    }
    public void set(int index, DynCostCtrlBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DynCostCtrlBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DynCostCtrlBillInfo item)
    {
        return super.indexOf(item);
    }
}