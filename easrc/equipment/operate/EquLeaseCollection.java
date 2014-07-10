package com.kingdee.eas.port.equipment.operate;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EquLeaseCollection extends AbstractObjectCollection 
{
    public EquLeaseCollection()
    {
        super(EquLeaseInfo.class);
    }
    public boolean add(EquLeaseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EquLeaseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EquLeaseInfo item)
    {
        return removeObject(item);
    }
    public EquLeaseInfo get(int index)
    {
        return(EquLeaseInfo)getObject(index);
    }
    public EquLeaseInfo get(Object key)
    {
        return(EquLeaseInfo)getObject(key);
    }
    public void set(int index, EquLeaseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EquLeaseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EquLeaseInfo item)
    {
        return super.indexOf(item);
    }
}