package com.kingdee.eas.port.equipment.record;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TankTechnicalCollection extends AbstractObjectCollection 
{
    public TankTechnicalCollection()
    {
        super(TankTechnicalInfo.class);
    }
    public boolean add(TankTechnicalInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TankTechnicalCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TankTechnicalInfo item)
    {
        return removeObject(item);
    }
    public TankTechnicalInfo get(int index)
    {
        return(TankTechnicalInfo)getObject(index);
    }
    public TankTechnicalInfo get(Object key)
    {
        return(TankTechnicalInfo)getObject(key);
    }
    public void set(int index, TankTechnicalInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TankTechnicalInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TankTechnicalInfo item)
    {
        return super.indexOf(item);
    }
}