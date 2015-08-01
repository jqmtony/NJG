package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ConPayPlanCollection extends AbstractObjectCollection 
{
    public ConPayPlanCollection()
    {
        super(ConPayPlanInfo.class);
    }
    public boolean add(ConPayPlanInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ConPayPlanCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ConPayPlanInfo item)
    {
        return removeObject(item);
    }
    public ConPayPlanInfo get(int index)
    {
        return(ConPayPlanInfo)getObject(index);
    }
    public ConPayPlanInfo get(Object key)
    {
        return(ConPayPlanInfo)getObject(key);
    }
    public void set(int index, ConPayPlanInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ConPayPlanInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ConPayPlanInfo item)
    {
        return super.indexOf(item);
    }
}