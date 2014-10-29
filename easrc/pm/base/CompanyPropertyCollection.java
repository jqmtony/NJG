package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CompanyPropertyCollection extends AbstractObjectCollection 
{
    public CompanyPropertyCollection()
    {
        super(CompanyPropertyInfo.class);
    }
    public boolean add(CompanyPropertyInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CompanyPropertyCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CompanyPropertyInfo item)
    {
        return removeObject(item);
    }
    public CompanyPropertyInfo get(int index)
    {
        return(CompanyPropertyInfo)getObject(index);
    }
    public CompanyPropertyInfo get(Object key)
    {
        return(CompanyPropertyInfo)getObject(key);
    }
    public void set(int index, CompanyPropertyInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CompanyPropertyInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CompanyPropertyInfo item)
    {
        return super.indexOf(item);
    }
}