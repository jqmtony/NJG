package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AdjustTypeCollection extends AbstractObjectCollection 
{
    public AdjustTypeCollection()
    {
        super(AdjustTypeInfo.class);
    }
    public boolean add(AdjustTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AdjustTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AdjustTypeInfo item)
    {
        return removeObject(item);
    }
    public AdjustTypeInfo get(int index)
    {
        return(AdjustTypeInfo)getObject(index);
    }
    public AdjustTypeInfo get(Object key)
    {
        return(AdjustTypeInfo)getObject(key);
    }
    public void set(int index, AdjustTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AdjustTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AdjustTypeInfo item)
    {
        return super.indexOf(item);
    }
}