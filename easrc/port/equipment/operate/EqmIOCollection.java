package com.kingdee.eas.port.equipment.operate;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EqmIOCollection extends AbstractObjectCollection 
{
    public EqmIOCollection()
    {
        super(EqmIOInfo.class);
    }
    public boolean add(EqmIOInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EqmIOCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EqmIOInfo item)
    {
        return removeObject(item);
    }
    public EqmIOInfo get(int index)
    {
        return(EqmIOInfo)getObject(index);
    }
    public EqmIOInfo get(Object key)
    {
        return(EqmIOInfo)getObject(key);
    }
    public void set(int index, EqmIOInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EqmIOInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EqmIOInfo item)
    {
        return super.indexOf(item);
    }
}