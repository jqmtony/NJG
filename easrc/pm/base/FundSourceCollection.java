package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FundSourceCollection extends AbstractObjectCollection 
{
    public FundSourceCollection()
    {
        super(FundSourceInfo.class);
    }
    public boolean add(FundSourceInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FundSourceCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FundSourceInfo item)
    {
        return removeObject(item);
    }
    public FundSourceInfo get(int index)
    {
        return(FundSourceInfo)getObject(index);
    }
    public FundSourceInfo get(Object key)
    {
        return(FundSourceInfo)getObject(key);
    }
    public void set(int index, FundSourceInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FundSourceInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FundSourceInfo item)
    {
        return super.indexOf(item);
    }
}