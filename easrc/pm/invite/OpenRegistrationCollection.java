package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OpenRegistrationCollection extends AbstractObjectCollection 
{
    public OpenRegistrationCollection()
    {
        super(OpenRegistrationInfo.class);
    }
    public boolean add(OpenRegistrationInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OpenRegistrationCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OpenRegistrationInfo item)
    {
        return removeObject(item);
    }
    public OpenRegistrationInfo get(int index)
    {
        return(OpenRegistrationInfo)getObject(index);
    }
    public OpenRegistrationInfo get(Object key)
    {
        return(OpenRegistrationInfo)getObject(key);
    }
    public void set(int index, OpenRegistrationInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OpenRegistrationInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OpenRegistrationInfo item)
    {
        return super.indexOf(item);
    }
}