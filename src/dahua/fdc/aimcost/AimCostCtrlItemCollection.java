package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AimCostCtrlItemCollection extends AbstractObjectCollection 
{
    public AimCostCtrlItemCollection()
    {
        super(AimCostCtrlItemInfo.class);
    }
    public boolean add(AimCostCtrlItemInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AimCostCtrlItemCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AimCostCtrlItemInfo item)
    {
        return removeObject(item);
    }
    public AimCostCtrlItemInfo get(int index)
    {
        return(AimCostCtrlItemInfo)getObject(index);
    }
    public AimCostCtrlItemInfo get(Object key)
    {
        return(AimCostCtrlItemInfo)getObject(key);
    }
    public void set(int index, AimCostCtrlItemInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AimCostCtrlItemInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AimCostCtrlItemInfo item)
    {
        return super.indexOf(item);
    }
}