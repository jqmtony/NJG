package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PortProjectTypeCollection extends AbstractObjectCollection 
{
    public PortProjectTypeCollection()
    {
        super(PortProjectTypeInfo.class);
    }
    public boolean add(PortProjectTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PortProjectTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PortProjectTypeInfo item)
    {
        return removeObject(item);
    }
    public PortProjectTypeInfo get(int index)
    {
        return(PortProjectTypeInfo)getObject(index);
    }
    public PortProjectTypeInfo get(Object key)
    {
        return(PortProjectTypeInfo)getObject(key);
    }
    public void set(int index, PortProjectTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PortProjectTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PortProjectTypeInfo item)
    {
        return super.indexOf(item);
    }
}