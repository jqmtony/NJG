package com.kingdee.eas.bpm.viewpz;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PzViewCollection extends AbstractObjectCollection 
{
    public PzViewCollection()
    {
        super(PzViewInfo.class);
    }
    public boolean add(PzViewInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PzViewCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PzViewInfo item)
    {
        return removeObject(item);
    }
    public PzViewInfo get(int index)
    {
        return(PzViewInfo)getObject(index);
    }
    public PzViewInfo get(Object key)
    {
        return(PzViewInfo)getObject(key);
    }
    public void set(int index, PzViewInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PzViewInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PzViewInfo item)
    {
        return super.indexOf(item);
    }
}