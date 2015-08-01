package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ConPayPlanDatapCollection extends AbstractObjectCollection 
{
    public ConPayPlanDatapCollection()
    {
        super(ConPayPlanDatapInfo.class);
    }
    public boolean add(ConPayPlanDatapInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ConPayPlanDatapCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ConPayPlanDatapInfo item)
    {
        return removeObject(item);
    }
    public ConPayPlanDatapInfo get(int index)
    {
        return(ConPayPlanDatapInfo)getObject(index);
    }
    public ConPayPlanDatapInfo get(Object key)
    {
        return(ConPayPlanDatapInfo)getObject(key);
    }
    public void set(int index, ConPayPlanDatapInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ConPayPlanDatapInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ConPayPlanDatapInfo item)
    {
        return super.indexOf(item);
    }
}