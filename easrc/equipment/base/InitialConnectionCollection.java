package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InitialConnectionCollection extends AbstractObjectCollection 
{
    public InitialConnectionCollection()
    {
        super(InitialConnectionInfo.class);
    }
    public boolean add(InitialConnectionInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InitialConnectionCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InitialConnectionInfo item)
    {
        return removeObject(item);
    }
    public InitialConnectionInfo get(int index)
    {
        return(InitialConnectionInfo)getObject(index);
    }
    public InitialConnectionInfo get(Object key)
    {
        return(InitialConnectionInfo)getObject(key);
    }
    public void set(int index, InitialConnectionInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InitialConnectionInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InitialConnectionInfo item)
    {
        return super.indexOf(item);
    }
}