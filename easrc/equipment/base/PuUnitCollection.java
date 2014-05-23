package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PuUnitCollection extends AbstractObjectCollection 
{
    public PuUnitCollection()
    {
        super(PuUnitInfo.class);
    }
    public boolean add(PuUnitInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PuUnitCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PuUnitInfo item)
    {
        return removeObject(item);
    }
    public PuUnitInfo get(int index)
    {
        return(PuUnitInfo)getObject(index);
    }
    public PuUnitInfo get(Object key)
    {
        return(PuUnitInfo)getObject(key);
    }
    public void set(int index, PuUnitInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PuUnitInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PuUnitInfo item)
    {
        return super.indexOf(item);
    }
}