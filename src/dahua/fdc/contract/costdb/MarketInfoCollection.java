package com.kingdee.eas.fdc.costdb;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketInfoCollection extends AbstractObjectCollection 
{
    public MarketInfoCollection()
    {
        super(MarketInfoInfo.class);
    }
    public boolean add(MarketInfoInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketInfoCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketInfoInfo item)
    {
        return removeObject(item);
    }
    public MarketInfoInfo get(int index)
    {
        return(MarketInfoInfo)getObject(index);
    }
    public MarketInfoInfo get(Object key)
    {
        return(MarketInfoInfo)getObject(key);
    }
    public void set(int index, MarketInfoInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketInfoInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketInfoInfo item)
    {
        return super.indexOf(item);
    }
}