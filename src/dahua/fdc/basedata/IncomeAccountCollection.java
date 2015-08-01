package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class IncomeAccountCollection extends AbstractObjectCollection 
{
    public IncomeAccountCollection()
    {
        super(IncomeAccountInfo.class);
    }
    public boolean add(IncomeAccountInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(IncomeAccountCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(IncomeAccountInfo item)
    {
        return removeObject(item);
    }
    public IncomeAccountInfo get(int index)
    {
        return(IncomeAccountInfo)getObject(index);
    }
    public IncomeAccountInfo get(Object key)
    {
        return(IncomeAccountInfo)getObject(key);
    }
    public void set(int index, IncomeAccountInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(IncomeAccountInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(IncomeAccountInfo item)
    {
        return super.indexOf(item);
    }
}