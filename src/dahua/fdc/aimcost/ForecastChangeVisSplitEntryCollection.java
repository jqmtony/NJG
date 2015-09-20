package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ForecastChangeVisSplitEntryCollection extends AbstractObjectCollection 
{
    public ForecastChangeVisSplitEntryCollection()
    {
        super(ForecastChangeVisSplitEntryInfo.class);
    }
    public boolean add(ForecastChangeVisSplitEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ForecastChangeVisSplitEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ForecastChangeVisSplitEntryInfo item)
    {
        return removeObject(item);
    }
    public ForecastChangeVisSplitEntryInfo get(int index)
    {
        return(ForecastChangeVisSplitEntryInfo)getObject(index);
    }
    public ForecastChangeVisSplitEntryInfo get(Object key)
    {
        return(ForecastChangeVisSplitEntryInfo)getObject(key);
    }
    public void set(int index, ForecastChangeVisSplitEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ForecastChangeVisSplitEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ForecastChangeVisSplitEntryInfo item)
    {
        return super.indexOf(item);
    }
}