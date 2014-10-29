package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CompanySetupEntryCollection extends AbstractObjectCollection 
{
    public CompanySetupEntryCollection()
    {
        super(CompanySetupEntryInfo.class);
    }
    public boolean add(CompanySetupEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CompanySetupEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CompanySetupEntryInfo item)
    {
        return removeObject(item);
    }
    public CompanySetupEntryInfo get(int index)
    {
        return(CompanySetupEntryInfo)getObject(index);
    }
    public CompanySetupEntryInfo get(Object key)
    {
        return(CompanySetupEntryInfo)getObject(key);
    }
    public void set(int index, CompanySetupEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CompanySetupEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CompanySetupEntryInfo item)
    {
        return super.indexOf(item);
    }
}