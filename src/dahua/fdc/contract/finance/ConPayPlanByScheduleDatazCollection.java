package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ConPayPlanByScheduleDatazCollection extends AbstractObjectCollection 
{
    public ConPayPlanByScheduleDatazCollection()
    {
        super(ConPayPlanByScheduleDatazInfo.class);
    }
    public boolean add(ConPayPlanByScheduleDatazInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ConPayPlanByScheduleDatazCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ConPayPlanByScheduleDatazInfo item)
    {
        return removeObject(item);
    }
    public ConPayPlanByScheduleDatazInfo get(int index)
    {
        return(ConPayPlanByScheduleDatazInfo)getObject(index);
    }
    public ConPayPlanByScheduleDatazInfo get(Object key)
    {
        return(ConPayPlanByScheduleDatazInfo)getObject(key);
    }
    public void set(int index, ConPayPlanByScheduleDatazInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ConPayPlanByScheduleDatazInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ConPayPlanByScheduleDatazInfo item)
    {
        return super.indexOf(item);
    }
}