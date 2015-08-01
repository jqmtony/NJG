package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AimCostCtrlBillEntryCollection extends AbstractObjectCollection 
{
    public AimCostCtrlBillEntryCollection()
    {
        super(AimCostCtrlBillEntryInfo.class);
    }
    public boolean add(AimCostCtrlBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AimCostCtrlBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AimCostCtrlBillEntryInfo item)
    {
        return removeObject(item);
    }
    public AimCostCtrlBillEntryInfo get(int index)
    {
        return(AimCostCtrlBillEntryInfo)getObject(index);
    }
    public AimCostCtrlBillEntryInfo get(Object key)
    {
        return(AimCostCtrlBillEntryInfo)getObject(key);
    }
    public void set(int index, AimCostCtrlBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AimCostCtrlBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AimCostCtrlBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}