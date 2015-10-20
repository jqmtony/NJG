package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SettNoCostSplitEntryCollection extends AbstractObjectCollection 
{
    public SettNoCostSplitEntryCollection()
    {
        super(SettNoCostSplitEntryInfo.class);
    }
    public boolean add(SettNoCostSplitEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SettNoCostSplitEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SettNoCostSplitEntryInfo item)
    {
        return removeObject(item);
    }
    public SettNoCostSplitEntryInfo get(int index)
    {
        return(SettNoCostSplitEntryInfo)getObject(index);
    }
    public SettNoCostSplitEntryInfo get(Object key)
    {
        return(SettNoCostSplitEntryInfo)getObject(key);
    }
    public void set(int index, SettNoCostSplitEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SettNoCostSplitEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SettNoCostSplitEntryInfo item)
    {
        return super.indexOf(item);
    }
}