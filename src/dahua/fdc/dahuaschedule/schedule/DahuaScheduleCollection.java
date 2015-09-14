package com.kingdee.eas.fdc.dahuaschedule.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DahuaScheduleCollection extends AbstractObjectCollection 
{
    public DahuaScheduleCollection()
    {
        super(DahuaScheduleInfo.class);
    }
    public boolean add(DahuaScheduleInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DahuaScheduleCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DahuaScheduleInfo item)
    {
        return removeObject(item);
    }
    public DahuaScheduleInfo get(int index)
    {
        return(DahuaScheduleInfo)getObject(index);
    }
    public DahuaScheduleInfo get(Object key)
    {
        return(DahuaScheduleInfo)getObject(key);
    }
    public void set(int index, DahuaScheduleInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DahuaScheduleInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DahuaScheduleInfo item)
    {
        return super.indexOf(item);
    }
}