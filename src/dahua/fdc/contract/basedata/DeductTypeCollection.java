package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DeductTypeCollection extends AbstractObjectCollection 
{
    public DeductTypeCollection()
    {
        super(DeductTypeInfo.class);
    }
    public boolean add(DeductTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DeductTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DeductTypeInfo item)
    {
        return removeObject(item);
    }
    public DeductTypeInfo get(int index)
    {
        return(DeductTypeInfo)getObject(index);
    }
    public DeductTypeInfo get(Object key)
    {
        return(DeductTypeInfo)getObject(key);
    }
    public void set(int index, DeductTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DeductTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DeductTypeInfo item)
    {
        return super.indexOf(item);
    }
}