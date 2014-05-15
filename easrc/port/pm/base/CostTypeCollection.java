package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CostTypeCollection extends AbstractObjectCollection 
{
    public CostTypeCollection()
    {
        super(CostTypeInfo.class);
    }
    public boolean add(CostTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CostTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CostTypeInfo item)
    {
        return removeObject(item);
    }
    public CostTypeInfo get(int index)
    {
        return(CostTypeInfo)getObject(index);
    }
    public CostTypeInfo get(Object key)
    {
        return(CostTypeInfo)getObject(key);
    }
    public void set(int index, CostTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CostTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CostTypeInfo item)
    {
        return super.indexOf(item);
    }
}