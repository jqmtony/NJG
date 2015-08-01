package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ConPayPlanDataCollection extends AbstractObjectCollection 
{
    public ConPayPlanDataCollection()
    {
        super(ConPayPlanDataInfo.class);
    }
    public boolean add(ConPayPlanDataInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ConPayPlanDataCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ConPayPlanDataInfo item)
    {
        return removeObject(item);
    }
    public ConPayPlanDataInfo get(int index)
    {
        return(ConPayPlanDataInfo)getObject(index);
    }
    public ConPayPlanDataInfo get(Object key)
    {
        return(ConPayPlanDataInfo)getObject(key);
    }
    public void set(int index, ConPayPlanDataInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ConPayPlanDataInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ConPayPlanDataInfo item)
    {
        return super.indexOf(item);
    }
}