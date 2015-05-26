package com.kingdee.eas.custom.richbase;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ReceTypeCollection extends AbstractObjectCollection 
{
    public ReceTypeCollection()
    {
        super(ReceTypeInfo.class);
    }
    public boolean add(ReceTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ReceTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ReceTypeInfo item)
    {
        return removeObject(item);
    }
    public ReceTypeInfo get(int index)
    {
        return(ReceTypeInfo)getObject(index);
    }
    public ReceTypeInfo get(Object key)
    {
        return(ReceTypeInfo)getObject(key);
    }
    public void set(int index, ReceTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ReceTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ReceTypeInfo item)
    {
        return super.indexOf(item);
    }
}