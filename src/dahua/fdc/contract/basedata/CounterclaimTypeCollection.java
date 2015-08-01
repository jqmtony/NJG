package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CounterclaimTypeCollection extends AbstractObjectCollection 
{
    public CounterclaimTypeCollection()
    {
        super(CounterclaimTypeInfo.class);
    }
    public boolean add(CounterclaimTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CounterclaimTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CounterclaimTypeInfo item)
    {
        return removeObject(item);
    }
    public CounterclaimTypeInfo get(int index)
    {
        return(CounterclaimTypeInfo)getObject(index);
    }
    public CounterclaimTypeInfo get(Object key)
    {
        return(CounterclaimTypeInfo)getObject(key);
    }
    public void set(int index, CounterclaimTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CounterclaimTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CounterclaimTypeInfo item)
    {
        return super.indexOf(item);
    }
}