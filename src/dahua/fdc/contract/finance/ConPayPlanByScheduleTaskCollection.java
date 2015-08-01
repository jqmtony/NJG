package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ConPayPlanByScheduleTaskCollection extends AbstractObjectCollection 
{
    public ConPayPlanByScheduleTaskCollection()
    {
        super(ConPayPlanByScheduleTaskInfo.class);
    }
    public boolean add(ConPayPlanByScheduleTaskInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ConPayPlanByScheduleTaskCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ConPayPlanByScheduleTaskInfo item)
    {
        return removeObject(item);
    }
    public ConPayPlanByScheduleTaskInfo get(int index)
    {
        return(ConPayPlanByScheduleTaskInfo)getObject(index);
    }
    public ConPayPlanByScheduleTaskInfo get(Object key)
    {
        return(ConPayPlanByScheduleTaskInfo)getObject(key);
    }
    public void set(int index, ConPayPlanByScheduleTaskInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ConPayPlanByScheduleTaskInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ConPayPlanByScheduleTaskInfo item)
    {
        return super.indexOf(item);
    }
}