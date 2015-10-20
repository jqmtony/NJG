package com.kingdee.eas.fdc.contract.programming;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PcTypeCollection extends AbstractObjectCollection 
{
    public PcTypeCollection()
    {
        super(PcTypeInfo.class);
    }
    public boolean add(PcTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PcTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PcTypeInfo item)
    {
        return removeObject(item);
    }
    public PcTypeInfo get(int index)
    {
        return(PcTypeInfo)getObject(index);
    }
    public PcTypeInfo get(Object key)
    {
        return(PcTypeInfo)getObject(key);
    }
    public void set(int index, PcTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PcTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PcTypeInfo item)
    {
        return super.indexOf(item);
    }
}