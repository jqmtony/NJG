package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TargetTypeCollection extends AbstractObjectCollection 
{
    public TargetTypeCollection()
    {
        super(TargetTypeInfo.class);
    }
    public boolean add(TargetTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TargetTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TargetTypeInfo item)
    {
        return removeObject(item);
    }
    public TargetTypeInfo get(int index)
    {
        return(TargetTypeInfo)getObject(index);
    }
    public TargetTypeInfo get(Object key)
    {
        return(TargetTypeInfo)getObject(key);
    }
    public void set(int index, TargetTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TargetTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TargetTypeInfo item)
    {
        return super.indexOf(item);
    }
}