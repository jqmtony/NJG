package com.kingdee.eas.port.pm.qa;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OATypeCollection extends AbstractObjectCollection 
{
    public OATypeCollection()
    {
        super(OATypeInfo.class);
    }
    public boolean add(OATypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OATypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OATypeInfo item)
    {
        return removeObject(item);
    }
    public OATypeInfo get(int index)
    {
        return(OATypeInfo)getObject(index);
    }
    public OATypeInfo get(Object key)
    {
        return(OATypeInfo)getObject(key);
    }
    public void set(int index, OATypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OATypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OATypeInfo item)
    {
        return super.indexOf(item);
    }
}