package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ConChangeNoCostSplitEntryCollection extends AbstractObjectCollection 
{
    public ConChangeNoCostSplitEntryCollection()
    {
        super(ConChangeNoCostSplitEntryInfo.class);
    }
    public boolean add(ConChangeNoCostSplitEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ConChangeNoCostSplitEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ConChangeNoCostSplitEntryInfo item)
    {
        return removeObject(item);
    }
    public ConChangeNoCostSplitEntryInfo get(int index)
    {
        return(ConChangeNoCostSplitEntryInfo)getObject(index);
    }
    public ConChangeNoCostSplitEntryInfo get(Object key)
    {
        return(ConChangeNoCostSplitEntryInfo)getObject(key);
    }
    public void set(int index, ConChangeNoCostSplitEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ConChangeNoCostSplitEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ConChangeNoCostSplitEntryInfo item)
    {
        return super.indexOf(item);
    }
}