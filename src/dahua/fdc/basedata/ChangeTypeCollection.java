package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ChangeTypeCollection extends AbstractObjectCollection 
{
    public ChangeTypeCollection()
    {
        super(ChangeTypeInfo.class);
    }
    public boolean add(ChangeTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ChangeTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ChangeTypeInfo item)
    {
        return removeObject(item);
    }
    public ChangeTypeInfo get(int index)
    {
        return(ChangeTypeInfo)getObject(index);
    }
    public ChangeTypeInfo get(Object key)
    {
        return(ChangeTypeInfo)getObject(key);
    }
    public void set(int index, ChangeTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ChangeTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ChangeTypeInfo item)
    {
        return super.indexOf(item);
    }
}