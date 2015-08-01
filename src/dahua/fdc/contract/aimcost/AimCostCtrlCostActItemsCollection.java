package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AimCostCtrlCostActItemsCollection extends AbstractObjectCollection 
{
    public AimCostCtrlCostActItemsCollection()
    {
        super(AimCostCtrlCostActItemsInfo.class);
    }
    public boolean add(AimCostCtrlCostActItemsInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AimCostCtrlCostActItemsCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AimCostCtrlCostActItemsInfo item)
    {
        return removeObject(item);
    }
    public AimCostCtrlCostActItemsInfo get(int index)
    {
        return(AimCostCtrlCostActItemsInfo)getObject(index);
    }
    public AimCostCtrlCostActItemsInfo get(Object key)
    {
        return(AimCostCtrlCostActItemsInfo)getObject(key);
    }
    public void set(int index, AimCostCtrlCostActItemsInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AimCostCtrlCostActItemsInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AimCostCtrlCostActItemsInfo item)
    {
        return super.indexOf(item);
    }
}