package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AimAimCostItemCollection extends AbstractObjectCollection 
{
    public AimAimCostItemCollection()
    {
        super(AimAimCostItemInfo.class);
    }
    public boolean add(AimAimCostItemInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AimAimCostItemCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AimAimCostItemInfo item)
    {
        return removeObject(item);
    }
    public AimAimCostItemInfo get(int index)
    {
        return(AimAimCostItemInfo)getObject(index);
    }
    public AimAimCostItemInfo get(Object key)
    {
        return(AimAimCostItemInfo)getObject(key);
    }
    public void set(int index, AimAimCostItemInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AimAimCostItemInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AimAimCostItemInfo item)
    {
        return super.indexOf(item);
    }
}