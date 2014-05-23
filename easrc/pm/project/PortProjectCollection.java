package com.kingdee.eas.port.pm.project;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PortProjectCollection extends AbstractObjectCollection 
{
    public PortProjectCollection()
    {
        super(PortProjectInfo.class);
    }
    public boolean add(PortProjectInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PortProjectCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PortProjectInfo item)
    {
        return removeObject(item);
    }
    public PortProjectInfo get(int index)
    {
        return(PortProjectInfo)getObject(index);
    }
    public PortProjectInfo get(Object key)
    {
        return(PortProjectInfo)getObject(key);
    }
    public void set(int index, PortProjectInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PortProjectInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PortProjectInfo item)
    {
        return super.indexOf(item);
    }
}