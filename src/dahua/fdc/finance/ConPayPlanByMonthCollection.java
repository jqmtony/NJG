package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ConPayPlanByMonthCollection extends AbstractObjectCollection 
{
    public ConPayPlanByMonthCollection()
    {
        super(ConPayPlanByMonthInfo.class);
    }
    public boolean add(ConPayPlanByMonthInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ConPayPlanByMonthCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ConPayPlanByMonthInfo item)
    {
        return removeObject(item);
    }
    public ConPayPlanByMonthInfo get(int index)
    {
        return(ConPayPlanByMonthInfo)getObject(index);
    }
    public ConPayPlanByMonthInfo get(Object key)
    {
        return(ConPayPlanByMonthInfo)getObject(key);
    }
    public void set(int index, ConPayPlanByMonthInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ConPayPlanByMonthInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ConPayPlanByMonthInfo item)
    {
        return super.indexOf(item);
    }
}