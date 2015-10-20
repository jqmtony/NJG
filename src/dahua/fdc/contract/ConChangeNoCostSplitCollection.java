package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ConChangeNoCostSplitCollection extends AbstractObjectCollection 
{
    public ConChangeNoCostSplitCollection()
    {
        super(ConChangeNoCostSplitInfo.class);
    }
    public boolean add(ConChangeNoCostSplitInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ConChangeNoCostSplitCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ConChangeNoCostSplitInfo item)
    {
        return removeObject(item);
    }
    public ConChangeNoCostSplitInfo get(int index)
    {
        return(ConChangeNoCostSplitInfo)getObject(index);
    }
    public ConChangeNoCostSplitInfo get(Object key)
    {
        return(ConChangeNoCostSplitInfo)getObject(key);
    }
    public void set(int index, ConChangeNoCostSplitInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ConChangeNoCostSplitInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ConChangeNoCostSplitInfo item)
    {
        return super.indexOf(item);
    }
}