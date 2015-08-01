package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AimAimCostAdjustEntryCollection extends AbstractObjectCollection 
{
    public AimAimCostAdjustEntryCollection()
    {
        super(AimAimCostAdjustEntryInfo.class);
    }
    public boolean add(AimAimCostAdjustEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AimAimCostAdjustEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AimAimCostAdjustEntryInfo item)
    {
        return removeObject(item);
    }
    public AimAimCostAdjustEntryInfo get(int index)
    {
        return(AimAimCostAdjustEntryInfo)getObject(index);
    }
    public AimAimCostAdjustEntryInfo get(Object key)
    {
        return(AimAimCostAdjustEntryInfo)getObject(key);
    }
    public void set(int index, AimAimCostAdjustEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AimAimCostAdjustEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AimAimCostAdjustEntryInfo item)
    {
        return super.indexOf(item);
    }
}