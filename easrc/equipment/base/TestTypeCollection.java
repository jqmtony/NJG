package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TestTypeCollection extends AbstractObjectCollection 
{
    public TestTypeCollection()
    {
        super(TestTypeInfo.class);
    }
    public boolean add(TestTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TestTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TestTypeInfo item)
    {
        return removeObject(item);
    }
    public TestTypeInfo get(int index)
    {
        return(TestTypeInfo)getObject(index);
    }
    public TestTypeInfo get(Object key)
    {
        return(TestTypeInfo)getObject(key);
    }
    public void set(int index, TestTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TestTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TestTypeInfo item)
    {
        return super.indexOf(item);
    }
}