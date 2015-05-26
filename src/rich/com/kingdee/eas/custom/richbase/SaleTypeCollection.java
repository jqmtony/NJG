package com.kingdee.eas.custom.richbase;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SaleTypeCollection extends AbstractObjectCollection 
{
    public SaleTypeCollection()
    {
        super(SaleTypeInfo.class);
    }
    public boolean add(SaleTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SaleTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SaleTypeInfo item)
    {
        return removeObject(item);
    }
    public SaleTypeInfo get(int index)
    {
        return(SaleTypeInfo)getObject(index);
    }
    public SaleTypeInfo get(Object key)
    {
        return(SaleTypeInfo)getObject(key);
    }
    public void set(int index, SaleTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SaleTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SaleTypeInfo item)
    {
        return super.indexOf(item);
    }
}