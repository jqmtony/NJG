package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OpenRegistrationEntryCollection extends AbstractObjectCollection 
{
    public OpenRegistrationEntryCollection()
    {
        super(OpenRegistrationEntryInfo.class);
    }
    public boolean add(OpenRegistrationEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OpenRegistrationEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OpenRegistrationEntryInfo item)
    {
        return removeObject(item);
    }
    public OpenRegistrationEntryInfo get(int index)
    {
        return(OpenRegistrationEntryInfo)getObject(index);
    }
    public OpenRegistrationEntryInfo get(Object key)
    {
        return(OpenRegistrationEntryInfo)getObject(key);
    }
    public void set(int index, OpenRegistrationEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OpenRegistrationEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OpenRegistrationEntryInfo item)
    {
        return super.indexOf(item);
    }
}