package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InvestYearCollection extends AbstractObjectCollection 
{
    public InvestYearCollection()
    {
        super(InvestYearInfo.class);
    }
    public boolean add(InvestYearInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InvestYearCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InvestYearInfo item)
    {
        return removeObject(item);
    }
    public InvestYearInfo get(int index)
    {
        return(InvestYearInfo)getObject(index);
    }
    public InvestYearInfo get(Object key)
    {
        return(InvestYearInfo)getObject(key);
    }
    public void set(int index, InvestYearInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InvestYearInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InvestYearInfo item)
    {
        return super.indexOf(item);
    }
}