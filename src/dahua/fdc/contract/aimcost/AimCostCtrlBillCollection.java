package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AimCostCtrlBillCollection extends AbstractObjectCollection 
{
    public AimCostCtrlBillCollection()
    {
        super(AimCostCtrlBillInfo.class);
    }
    public boolean add(AimCostCtrlBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AimCostCtrlBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AimCostCtrlBillInfo item)
    {
        return removeObject(item);
    }
    public AimCostCtrlBillInfo get(int index)
    {
        return(AimCostCtrlBillInfo)getObject(index);
    }
    public AimCostCtrlBillInfo get(Object key)
    {
        return(AimCostCtrlBillInfo)getObject(key);
    }
    public void set(int index, AimCostCtrlBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AimCostCtrlBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AimCostCtrlBillInfo item)
    {
        return super.indexOf(item);
    }
}