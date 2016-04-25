package com.kingdee.eas.fdc.dahuaschedule.schedule.schedulelog;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DhScheduleLogCollection extends AbstractObjectCollection 
{
    public DhScheduleLogCollection()
    {
        super(DhScheduleLogInfo.class);
    }
    public boolean add(DhScheduleLogInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DhScheduleLogCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DhScheduleLogInfo item)
    {
        return removeObject(item);
    }
    public DhScheduleLogInfo get(int index)
    {
        return(DhScheduleLogInfo)getObject(index);
    }
    public DhScheduleLogInfo get(Object key)
    {
        return(DhScheduleLogInfo)getObject(key);
    }
    public void set(int index, DhScheduleLogInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DhScheduleLogInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DhScheduleLogInfo item)
    {
        return super.indexOf(item);
    }
}