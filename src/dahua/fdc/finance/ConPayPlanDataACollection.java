package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ConPayPlanDataACollection extends AbstractObjectCollection 
{
    public ConPayPlanDataACollection()
    {
        super(ConPayPlanDataAInfo.class);
    }
    public boolean add(ConPayPlanDataAInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ConPayPlanDataACollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ConPayPlanDataAInfo item)
    {
        return removeObject(item);
    }
    public ConPayPlanDataAInfo get(int index)
    {
        return(ConPayPlanDataAInfo)getObject(index);
    }
    public ConPayPlanDataAInfo get(Object key)
    {
        return(ConPayPlanDataAInfo)getObject(key);
    }
    public void set(int index, ConPayPlanDataAInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ConPayPlanDataAInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ConPayPlanDataAInfo item)
    {
        return super.indexOf(item);
    }
}