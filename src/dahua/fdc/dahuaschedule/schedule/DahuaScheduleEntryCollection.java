package com.kingdee.eas.fdc.dahuaschedule.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DahuaScheduleEntryCollection extends AbstractObjectCollection 
{
    public DahuaScheduleEntryCollection()
    {
        super(DahuaScheduleEntryInfo.class);
    }
    public boolean add(DahuaScheduleEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DahuaScheduleEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DahuaScheduleEntryInfo item)
    {
        return removeObject(item);
    }
    public DahuaScheduleEntryInfo get(int index)
    {
        return(DahuaScheduleEntryInfo)getObject(index);
    }
    public DahuaScheduleEntryInfo get(Object key)
    {
        return(DahuaScheduleEntryInfo)getObject(key);
    }
    public void set(int index, DahuaScheduleEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DahuaScheduleEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DahuaScheduleEntryInfo item)
    {
        return super.indexOf(item);
    }
}