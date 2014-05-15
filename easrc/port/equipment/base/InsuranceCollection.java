package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InsuranceCollection extends AbstractObjectCollection 
{
    public InsuranceCollection()
    {
        super(InsuranceInfo.class);
    }
    public boolean add(InsuranceInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InsuranceCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InsuranceInfo item)
    {
        return removeObject(item);
    }
    public InsuranceInfo get(int index)
    {
        return(InsuranceInfo)getObject(index);
    }
    public InsuranceInfo get(Object key)
    {
        return(InsuranceInfo)getObject(key);
    }
    public void set(int index, InsuranceInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InsuranceInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InsuranceInfo item)
    {
        return super.indexOf(item);
    }
}