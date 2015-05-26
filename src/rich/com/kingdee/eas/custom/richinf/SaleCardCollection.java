package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SaleCardCollection extends AbstractObjectCollection 
{
    public SaleCardCollection()
    {
        super(SaleCardInfo.class);
    }
    public boolean add(SaleCardInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SaleCardCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SaleCardInfo item)
    {
        return removeObject(item);
    }
    public SaleCardInfo get(int index)
    {
        return(SaleCardInfo)getObject(index);
    }
    public SaleCardInfo get(Object key)
    {
        return(SaleCardInfo)getObject(key);
    }
    public void set(int index, SaleCardInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SaleCardInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SaleCardInfo item)
    {
        return super.indexOf(item);
    }
}