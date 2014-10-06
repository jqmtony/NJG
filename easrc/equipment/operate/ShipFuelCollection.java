package com.kingdee.eas.port.equipment.operate;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ShipFuelCollection extends AbstractObjectCollection 
{
    public ShipFuelCollection()
    {
        super(ShipFuelInfo.class);
    }
    public boolean add(ShipFuelInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ShipFuelCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ShipFuelInfo item)
    {
        return removeObject(item);
    }
    public ShipFuelInfo get(int index)
    {
        return(ShipFuelInfo)getObject(index);
    }
    public ShipFuelInfo get(Object key)
    {
        return(ShipFuelInfo)getObject(key);
    }
    public void set(int index, ShipFuelInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ShipFuelInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ShipFuelInfo item)
    {
        return super.indexOf(item);
    }
}