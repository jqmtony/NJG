package com.kingdee.eas.test;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PurOrderCollection extends AbstractObjectCollection 
{
    public PurOrderCollection()
    {
        super(PurOrderInfo.class);
    }
    public boolean add(PurOrderInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PurOrderCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PurOrderInfo item)
    {
        return removeObject(item);
    }
    public PurOrderInfo get(int index)
    {
        return(PurOrderInfo)getObject(index);
    }
    public PurOrderInfo get(Object key)
    {
        return(PurOrderInfo)getObject(key);
    }
    public void set(int index, PurOrderInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PurOrderInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PurOrderInfo item)
    {
        return super.indexOf(item);
    }
}