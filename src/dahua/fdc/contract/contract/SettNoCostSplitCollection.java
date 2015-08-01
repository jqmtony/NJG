package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SettNoCostSplitCollection extends AbstractObjectCollection 
{
    public SettNoCostSplitCollection()
    {
        super(SettNoCostSplitInfo.class);
    }
    public boolean add(SettNoCostSplitInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SettNoCostSplitCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SettNoCostSplitInfo item)
    {
        return removeObject(item);
    }
    public SettNoCostSplitInfo get(int index)
    {
        return(SettNoCostSplitInfo)getObject(index);
    }
    public SettNoCostSplitInfo get(Object key)
    {
        return(SettNoCostSplitInfo)getObject(key);
    }
    public void set(int index, SettNoCostSplitInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SettNoCostSplitInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SettNoCostSplitInfo item)
    {
        return super.indexOf(item);
    }
}