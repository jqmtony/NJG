package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CompanySetupCollection extends AbstractObjectCollection 
{
    public CompanySetupCollection()
    {
        super(CompanySetupInfo.class);
    }
    public boolean add(CompanySetupInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CompanySetupCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CompanySetupInfo item)
    {
        return removeObject(item);
    }
    public CompanySetupInfo get(int index)
    {
        return(CompanySetupInfo)getObject(index);
    }
    public CompanySetupInfo get(Object key)
    {
        return(CompanySetupInfo)getObject(key);
    }
    public void set(int index, CompanySetupInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CompanySetupInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CompanySetupInfo item)
    {
        return super.indexOf(item);
    }
}