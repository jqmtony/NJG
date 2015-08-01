package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AimAimCostAdjustCollection extends AbstractObjectCollection 
{
    public AimAimCostAdjustCollection()
    {
        super(AimAimCostAdjustInfo.class);
    }
    public boolean add(AimAimCostAdjustInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AimAimCostAdjustCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AimAimCostAdjustInfo item)
    {
        return removeObject(item);
    }
    public AimAimCostAdjustInfo get(int index)
    {
        return(AimAimCostAdjustInfo)getObject(index);
    }
    public AimAimCostAdjustInfo get(Object key)
    {
        return(AimAimCostAdjustInfo)getObject(key);
    }
    public void set(int index, AimAimCostAdjustInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AimAimCostAdjustInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AimAimCostAdjustInfo item)
    {
        return super.indexOf(item);
    }
}