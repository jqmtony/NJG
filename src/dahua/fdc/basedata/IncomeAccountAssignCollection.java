package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class IncomeAccountAssignCollection extends AbstractObjectCollection 
{
    public IncomeAccountAssignCollection()
    {
        super(IncomeAccountAssignInfo.class);
    }
    public boolean add(IncomeAccountAssignInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(IncomeAccountAssignCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(IncomeAccountAssignInfo item)
    {
        return removeObject(item);
    }
    public IncomeAccountAssignInfo get(int index)
    {
        return(IncomeAccountAssignInfo)getObject(index);
    }
    public IncomeAccountAssignInfo get(Object key)
    {
        return(IncomeAccountAssignInfo)getObject(key);
    }
    public void set(int index, IncomeAccountAssignInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(IncomeAccountAssignInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(IncomeAccountAssignInfo item)
    {
        return super.indexOf(item);
    }
}