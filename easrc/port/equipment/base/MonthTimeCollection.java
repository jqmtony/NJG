package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MonthTimeCollection extends AbstractObjectCollection 
{
    public MonthTimeCollection()
    {
        super(MonthTimeInfo.class);
    }
    public boolean add(MonthTimeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MonthTimeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MonthTimeInfo item)
    {
        return removeObject(item);
    }
    public MonthTimeInfo get(int index)
    {
        return(MonthTimeInfo)getObject(index);
    }
    public MonthTimeInfo get(Object key)
    {
        return(MonthTimeInfo)getObject(key);
    }
    public void set(int index, MonthTimeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MonthTimeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MonthTimeInfo item)
    {
        return super.indexOf(item);
    }
}