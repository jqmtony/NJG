package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ForecastChangeVisCollection extends AbstractObjectCollection 
{
    public ForecastChangeVisCollection()
    {
        super(ForecastChangeVisInfo.class);
    }
    public boolean add(ForecastChangeVisInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ForecastChangeVisCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ForecastChangeVisInfo item)
    {
        return removeObject(item);
    }
    public ForecastChangeVisInfo get(int index)
    {
        return(ForecastChangeVisInfo)getObject(index);
    }
    public ForecastChangeVisInfo get(Object key)
    {
        return(ForecastChangeVisInfo)getObject(key);
    }
    public void set(int index, ForecastChangeVisInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ForecastChangeVisInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ForecastChangeVisInfo item)
    {
        return super.indexOf(item);
    }
}