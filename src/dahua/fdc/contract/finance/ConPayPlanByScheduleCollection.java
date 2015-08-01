package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ConPayPlanByScheduleCollection extends AbstractObjectCollection 
{
    public ConPayPlanByScheduleCollection()
    {
        super(ConPayPlanByScheduleInfo.class);
    }
    public boolean add(ConPayPlanByScheduleInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ConPayPlanByScheduleCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ConPayPlanByScheduleInfo item)
    {
        return removeObject(item);
    }
    public ConPayPlanByScheduleInfo get(int index)
    {
        return(ConPayPlanByScheduleInfo)getObject(index);
    }
    public ConPayPlanByScheduleInfo get(Object key)
    {
        return(ConPayPlanByScheduleInfo)getObject(key);
    }
    public void set(int index, ConPayPlanByScheduleInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ConPayPlanByScheduleInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ConPayPlanByScheduleInfo item)
    {
        return super.indexOf(item);
    }
}