package com.kingdee.eas.fdc.costindexdb.database;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class IndexTypeCollection extends AbstractObjectCollection 
{
    public IndexTypeCollection()
    {
        super(IndexTypeInfo.class);
    }
    public boolean add(IndexTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(IndexTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(IndexTypeInfo item)
    {
        return removeObject(item);
    }
    public IndexTypeInfo get(int index)
    {
        return(IndexTypeInfo)getObject(index);
    }
    public IndexTypeInfo get(Object key)
    {
        return(IndexTypeInfo)getObject(key);
    }
    public void set(int index, IndexTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(IndexTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(IndexTypeInfo item)
    {
        return super.indexOf(item);
    }
}