package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ConNoCostSplitCollection extends AbstractObjectCollection 
{
    public ConNoCostSplitCollection()
    {
        super(ConNoCostSplitInfo.class);
    }
    public boolean add(ConNoCostSplitInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ConNoCostSplitCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ConNoCostSplitInfo item)
    {
        return removeObject(item);
    }
    public ConNoCostSplitInfo get(int index)
    {
        return(ConNoCostSplitInfo)getObject(index);
    }
    public ConNoCostSplitInfo get(Object key)
    {
        return(ConNoCostSplitInfo)getObject(key);
    }
    public void set(int index, ConNoCostSplitInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ConNoCostSplitInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ConNoCostSplitInfo item)
    {
        return super.indexOf(item);
    }
}