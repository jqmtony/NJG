package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CzUnitCollection extends AbstractObjectCollection 
{
    public CzUnitCollection()
    {
        super(CzUnitInfo.class);
    }
    public boolean add(CzUnitInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CzUnitCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CzUnitInfo item)
    {
        return removeObject(item);
    }
    public CzUnitInfo get(int index)
    {
        return(CzUnitInfo)getObject(index);
    }
    public CzUnitInfo get(Object key)
    {
        return(CzUnitInfo)getObject(key);
    }
    public void set(int index, CzUnitInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CzUnitInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CzUnitInfo item)
    {
        return super.indexOf(item);
    }
}