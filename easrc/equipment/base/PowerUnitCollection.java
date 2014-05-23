package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PowerUnitCollection extends AbstractObjectCollection 
{
    public PowerUnitCollection()
    {
        super(PowerUnitInfo.class);
    }
    public boolean add(PowerUnitInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PowerUnitCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PowerUnitInfo item)
    {
        return removeObject(item);
    }
    public PowerUnitInfo get(int index)
    {
        return(PowerUnitInfo)getObject(index);
    }
    public PowerUnitInfo get(Object key)
    {
        return(PowerUnitInfo)getObject(key);
    }
    public void set(int index, PowerUnitInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PowerUnitInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PowerUnitInfo item)
    {
        return super.indexOf(item);
    }
}