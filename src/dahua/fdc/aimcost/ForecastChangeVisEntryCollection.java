package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ForecastChangeVisEntryCollection extends AbstractObjectCollection 
{
    public ForecastChangeVisEntryCollection()
    {
        super(ForecastChangeVisEntryInfo.class);
    }
    public boolean add(ForecastChangeVisEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ForecastChangeVisEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ForecastChangeVisEntryInfo item)
    {
        return removeObject(item);
    }
    public ForecastChangeVisEntryInfo get(int index)
    {
        return(ForecastChangeVisEntryInfo)getObject(index);
    }
    public ForecastChangeVisEntryInfo get(Object key)
    {
        return(ForecastChangeVisEntryInfo)getObject(key);
    }
    public void set(int index, ForecastChangeVisEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ForecastChangeVisEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ForecastChangeVisEntryInfo item)
    {
        return super.indexOf(item);
    }
}