package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FaLocationCollection extends AbstractObjectCollection 
{
    public FaLocationCollection()
    {
        super(FaLocationInfo.class);
    }
    public boolean add(FaLocationInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FaLocationCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FaLocationInfo item)
    {
        return removeObject(item);
    }
    public FaLocationInfo get(int index)
    {
        return(FaLocationInfo)getObject(index);
    }
    public FaLocationInfo get(Object key)
    {
        return(FaLocationInfo)getObject(key);
    }
    public void set(int index, FaLocationInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FaLocationInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FaLocationInfo item)
    {
        return super.indexOf(item);
    }
}