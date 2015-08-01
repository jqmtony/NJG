package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BeforeAccountViewCollection extends AbstractObjectCollection 
{
    public BeforeAccountViewCollection()
    {
        super(BeforeAccountViewInfo.class);
    }
    public boolean add(BeforeAccountViewInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BeforeAccountViewCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BeforeAccountViewInfo item)
    {
        return removeObject(item);
    }
    public BeforeAccountViewInfo get(int index)
    {
        return(BeforeAccountViewInfo)getObject(index);
    }
    public BeforeAccountViewInfo get(Object key)
    {
        return(BeforeAccountViewInfo)getObject(key);
    }
    public void set(int index, BeforeAccountViewInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BeforeAccountViewInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BeforeAccountViewInfo item)
    {
        return super.indexOf(item);
    }
}