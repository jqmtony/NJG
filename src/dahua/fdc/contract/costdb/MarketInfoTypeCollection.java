package com.kingdee.eas.fdc.costdb;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketInfoTypeCollection extends AbstractObjectCollection 
{
    public MarketInfoTypeCollection()
    {
        super(MarketInfoTypeInfo.class);
    }
    public boolean add(MarketInfoTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketInfoTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketInfoTypeInfo item)
    {
        return removeObject(item);
    }
    public MarketInfoTypeInfo get(int index)
    {
        return(MarketInfoTypeInfo)getObject(index);
    }
    public MarketInfoTypeInfo get(Object key)
    {
        return(MarketInfoTypeInfo)getObject(key);
    }
    public void set(int index, MarketInfoTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketInfoTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketInfoTypeInfo item)
    {
        return super.indexOf(item);
    }
}